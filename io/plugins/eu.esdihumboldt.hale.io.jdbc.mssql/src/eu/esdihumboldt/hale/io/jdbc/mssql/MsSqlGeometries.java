/*
 * Copyright (c) 2016 wetransform GmbH
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     wetransform GmbH <http://www.wetransform.to>
 */

package eu.esdihumboldt.hale.io.jdbc.mssql;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Supplier;

import org.geotools.geometry.jts.CurvedGeometryFactory;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import com.google.common.io.BaseEncoding;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import eu.esdihumboldt.hale.common.core.report.SimpleLog;
import eu.esdihumboldt.hale.common.instance.geometry.DefaultGeometryProperty;
import eu.esdihumboldt.hale.common.instance.geometry.impl.CodeDefinition;
import eu.esdihumboldt.hale.common.instance.geometry.impl.WKTDefinition;
import eu.esdihumboldt.hale.common.schema.geometry.CRSDefinition;
import eu.esdihumboldt.hale.common.schema.geometry.GeometryProperty;
import eu.esdihumboldt.hale.common.schema.model.TypeDefinition;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.GeometryMetadata;
import eu.esdihumboldt.hale.common.schema.model.impl.DefaultTypeDefinition;
import eu.esdihumboldt.hale.io.jdbc.GeometryAdvisor;
import eu.esdihumboldt.hale.io.jdbc.mssql.util.SRSUtil;
import schemacrawler.schema.BaseColumn;
import schemacrawler.schema.ColumnDataType;

/**
 * Geometry advisor for MSSQL database
 * 
 * @author Arun
 */
public class MsSqlGeometries implements GeometryAdvisor<SQLServerConnection> {

	@Override
	public Object convertGeometry(GeometryProperty<?> geom, TypeDefinition columnType,
			SQLServerConnection connection, SimpleLog log) throws Exception {

		// We need Column Data type
		String columnDataType = columnType.getName().getLocalPart();

		CoordinateReferenceSystem targetCRS = null;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			int srId = 4326;

			if (geom.getCRSDefinition() != null) {

				// SRS Code
				String srsName = CRS.toSRS(geom.getCRSDefinition().getCRS());

				if (columnDataType.equals("geography")) {
					// getting Axis order of geometry CRS
					CRS.AxisOrder axisOrder = CRS.getAxisOrder(geom.getCRSDefinition().getCRS());
					// if axis order is not x/y ordering then will need to
					// transform geometry to target crs with longiture first
					if (axisOrder != CRS.AxisOrder.EAST_NORTH) {
						if (srsName != null && srsName.startsWith("EPSG")) {
							targetCRS = CRS.decode(srsName, true);
						}
					}
				}
				try {
					if (srsName != null) {
						final int index = srsName.lastIndexOf(':');
						if (index > 0) {
							srsName = srsName.substring(index + 1).trim();
						}
						srId = Integer.parseInt(srsName);
					}
				} catch (NumberFormatException nfEx) {
					// TODO::Using UI ask user to enter default SRId, if
					// can not extract it.
				}
			}

			Geometry targetGeometry;
			if (targetCRS != null) {
				MathTransform transform = CRS.findMathTransform(geom.getCRSDefinition().getCRS(),
						targetCRS);
				targetGeometry = JTS.transform(geom.getGeometry(), transform);
			}
			else {
				targetGeometry = geom.getGeometry();
			}

			String sqlForBinaryValue = "DECLARE @g " + columnDataType + ";SET @g = "
					+ columnDataType + "::STGeomFromText('" + targetGeometry.toText() + "'," + srId
					+ ");SELECT @g;";

			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlForBinaryValue);
			if (rs.next()) {
				return rs.getString(1);
			}
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					//
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					//
				}
		}
		return null;
	}

	@Override
	public GeometryProperty<?> convertToInstanceGeometry(Object geom, TypeDefinition columnType,
			SQLServerConnection connection, Supplier<CRSDefinition> crsProvider, SimpleLog log)
			throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		try {

			// We need Column Data type
			String columnDataType = columnType.getName().getLocalPart();

			String geomAsHex = BaseEncoding.base16().lowerCase().encode((byte[]) geom);
			String sqlGeom = "SELECT top 1 GeomConvert.geom.STSrid srid, GeomConvert.geom.STAsText() as geomAsText, GeomConvert.geom.STGeometryType() as geomType " //
					+ "FROM " //
					+ "(SELECT cast(cast(temp.wkb as varbinary(max)) as " + columnDataType
					+ ") as geom "//
					+ "FROM " //
					+ "( select " + "0x" + geomAsHex + " as wkb) as temp" //
					+ ") " //
					+ "as GeomConvert"; //

			stmt = connection.createStatement();

			rs = stmt.executeQuery(sqlGeom);
			Geometry jtsGeom = null;
			int srId = 0;

			if (rs.next()) {
				srId = rs.getInt(1);
				String geomAsText = rs.getString(2);
				String geomType = rs.getString(3);
				// WKTReader does not support CircularString, CurvePolygon,
				// CompoundCurve
				WKTReader wktReader = getSpecificWktReader(geomType);
				try {
					// conversion to JTS via WKT
					jtsGeom = wktReader.read(geomAsText);
				} catch (ParseException e) {
					log.error("Could not load geometry from database", e);
				}
			}

			CRSDefinition crsDef = null;

			String authName = SRSUtil.getAuthorityName(srId, connection);
			if (authName != null && authName.equals("EPSG")) {
				// For geography/geometry data type, SQL server assumes lon/lat
				// axis order, if we read using SQL function
				String epsgCode = authName + ":" + SRSUtil.getSRS(srId, connection);
				if (columnDataType.equals("geography"))
					crsDef = new CodeDefinition(epsgCode, true);
				else
					crsDef = new CodeDefinition(epsgCode, null);
			}
			else {
				String wkt = SRSUtil.getSRSText(srId, connection);
				if (wkt != null) {
					crsDef = new WKTDefinition(wkt, null);
				}
			}

			if (crsDef == null) {
				log.warn(
						"Could not find spatial reference system id " + srId + " in MS sql server");

				crsDef = crsProvider.get();

				if (crsDef == null) {
					log.warn("Could not retrieve default spatial reference for " + srId
							+ " in MS sql server");
				}

				// saving in cache
				if (crsDef != null) {

					String srsName = CRS.toSRS(crsDef.getCRS());
					if (srsName != null) {
						final int index = srsName.lastIndexOf(':');
						String authorityName = null;
						String authorizedId = null;
						if (index > 0) {
							authorityName = srsName.substring(0, index);
							authorizedId = srsName.substring(index + 1).trim();
						}
						// we don't need wkt.
						SRSUtil.addSRSinCache(srId, authorityName, authorizedId, null);
					}
				}
			}
			return new DefaultGeometryProperty<Geometry>(crsDef, jtsGeom);
		} finally

		{
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					//
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					//
				}
		}
	}

	@Override
	public boolean isFixedType(ColumnDataType columnType) {
		return false;
	}

	@Override
	public Class<? extends Geometry> configureGeometryColumnType(SQLServerConnection connection,
			BaseColumn<?> column, DefaultTypeDefinition type, SimpleLog log) {
		type.setConstraint(new GeometryMetadata());
		return Geometry.class;
	}

	private WKTReader getSpecificWktReader(String geometryType) {
		switch (geometryType) {
		case "CurvePolygon":
		case "CircularString":
		case "CompoundCurve":
			return new WKTReader2(new CurvedGeometryFactory(Double.MAX_VALUE));
		default:
			return new WKTReader(new GeometryFactory());
		}
	}

}
