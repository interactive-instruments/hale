/*
 * Copyright (c) 2012 Data Harmonisation Panel
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
 *     HUMBOLDT EU Integrated Project #030962
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.io.gml.geometry.handler.compositeGeometries;

import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import org.junit.Ignore;
import org.junit.Test;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import eu.esdihumboldt.hale.common.instance.geometry.InterpolationHelper;
import eu.esdihumboldt.hale.common.instance.model.Instance;
import eu.esdihumboldt.hale.common.instance.model.InstanceCollection;
import eu.esdihumboldt.hale.common.instance.model.ResourceIterator;
import eu.esdihumboldt.hale.io.gml.geometry.handler.internal.AbstractHandlerTest;
import eu.esdihumboldt.hale.io.gml.geometry.handler.internal.InterpolationConfigurations;
import eu.esdihumboldt.hale.io.gml.geometry.handler.internal.ReaderConfiguration;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

/**
 * Test for reading composite surface geometries
 * 
 * @author Patrick Lieb
 * @author Arun Varma
 * @author Simon Templer
 */
@Features("Geometries")
@Stories("GML")
public class CompositeSurfaceGeometryTest extends AbstractHandlerTest {

	private MultiPolygon reference;
	private final ReaderConfiguration gridConfig = InterpolationConfigurations.ALL_TO_GRID_DEFAULT;
	private Consumer<Geometry> checker;
	private Consumer<Geometry> gridChecker;

	@Override
	public void init() {
		super.init();

		LinearRing shell = geomFactory.createLinearRing(new Coordinate[] {
				new Coordinate(0.01, 3.2), new Coordinate(3.33, 3.33), new Coordinate(0.01, -3.2),
				new Coordinate(-3.33, -3.2), new Coordinate(0.01, 3.2) });

		LinearRing[] holes = new LinearRing[2];
		LinearRing hole1 = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(0, 1), new Coordinate(1, 1),
						new Coordinate(0, -1), new Coordinate(-1, -1), new Coordinate(0, 1) });
		LinearRing hole2 = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(0, 2), new Coordinate(2, 2),
						new Coordinate(0, -2), new Coordinate(-2, -2), new Coordinate(0, 2) });
		holes[0] = hole1;
		holes[1] = hole2;

		Polygon polygon1 = geomFactory.createPolygon(shell, holes);

		shell = geomFactory.createLinearRing(new Coordinate[] { new Coordinate(6.01, 9.2),
				new Coordinate(9.33, 9.33), new Coordinate(6.01, -9.2), new Coordinate(-9.33, -9.2),
				new Coordinate(6.01, 9.2) });

		holes = new LinearRing[2];
		hole1 = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(2, 3), new Coordinate(3, 3),
						new Coordinate(2, -3), new Coordinate(-3, -3), new Coordinate(2, 3) });
		hole2 = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(2, 4), new Coordinate(4, 4),
						new Coordinate(2, -4), new Coordinate(-4, -4), new Coordinate(2, 4) });
		holes[0] = hole1;
		holes[1] = hole2;

		Polygon polygon2 = geomFactory.createPolygon(shell, holes);

		Polygon[] polygons = new Polygon[] { polygon1, polygon2 };

		reference = geomFactory.createMultiPolygon(polygons);

		checker = referenceChecker(reference);

		gridChecker = combine(
				referenceChecker(reference, InterpolationHelper.DEFAULT_MAX_POSITION_ERROR),
				gridConfig.geometryChecker());
	}

	/**
	 * Test composite surface geometries read from a GML 3.2 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	@Ignore("Test case does not represent a valid composite surface")
	public void testCompositeSurfaceGml31() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml32.xsd").toURI(),
				getClass().getResource("/data/surface/sample-compositesurface-gml32.xml").toURI());

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// CompositeCurveProperty with surfaceMembers defined through
			// PolygonPatch and Polygon
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test composite surface geometries read from a GML 3.2 file Geometry
	 * coordinates will be moved to universal grid
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	@Ignore("Test case does not represent a valid composite surface")
	public void testCompositeSurfaceGml31_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml32.xsd").toURI(),
				getClass().getResource("/data/surface/sample-compositesurface-gml32.xml").toURI(),
				gridConfig);

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// CompositeCurveProperty with surfaceMembers defined through
			// PolygonPatch and Polygon
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

}
