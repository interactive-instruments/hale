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

import org.junit.Test;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import eu.esdihumboldt.hale.common.instance.model.Instance;
import eu.esdihumboldt.hale.common.instance.model.InstanceCollection;
import eu.esdihumboldt.hale.common.instance.model.ResourceIterator;
import eu.esdihumboldt.hale.io.gml.geometry.handler.internal.AbstractHandlerTest;
import eu.esdihumboldt.hale.io.gml.geometry.handler.internal.InterpolationConfigurations;
import eu.esdihumboldt.hale.io.gml.geometry.handler.internal.ReaderConfiguration;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

/**
 * Test for reading multi-surface geometries
 * 
 * @author Patrick Lieb
 * @author Arun Varma
 * @author Simon Templer
 */
@Features("Geometries")
@Stories("GML")
public class MultiSurfaceGeometryTest extends AbstractHandlerTest {

	private MultiPolygon reference;
	/*
	 * Max positional error must be smaller than the default as we have
	 * geographic coordinates.
	 * 
	 * With a larger value we also end up with double coordinates (which makes
	 * them harder to compare to the reference).
	 */
	private static final double maxPositionalError = 0.002;
	private final ReaderConfiguration gridConfig = InterpolationConfigurations
			.grid(maxPositionalError, true);
	private Consumer<Geometry> checker;
	private Consumer<Geometry> gridChecker;

	@Override
	public void init() {
		super.init();

		LinearRing shell = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(-122.44, 37.80),
						new Coordinate(-122.45, 37.80), new Coordinate(-122.45, 37.78),
						new Coordinate(-122.44, 37.78), new Coordinate(-122.44, 37.80) });

		LinearRing[] holes = new LinearRing[1];
		LinearRing hole1 = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(-122.24, 37.60),
						new Coordinate(-122.25, 37.60), new Coordinate(-122.25, 37.58),
						new Coordinate(-122.24, 37.58), new Coordinate(-122.24, 37.60) });
		holes[0] = hole1;

		Polygon polygon2 = geomFactory.createPolygon(shell, holes);

		shell = geomFactory.createLinearRing(new Coordinate[] { new Coordinate(0.01, 3.2),
				new Coordinate(3.33, 3.33), new Coordinate(0.01, -3.2), new Coordinate(-3.33, -3.2),
				new Coordinate(0.01, 3.2) });

		holes = new LinearRing[2];
		hole1 = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(0, 1), new Coordinate(1, 1),
						new Coordinate(0, -1), new Coordinate(-1, -1), new Coordinate(0, 1) });
		LinearRing hole2 = geomFactory
				.createLinearRing(new Coordinate[] { new Coordinate(0, 2), new Coordinate(2, 2),
						new Coordinate(0, -2), new Coordinate(-2, -2), new Coordinate(0, 2) });
		holes[0] = hole1;
		holes[1] = hole2;

		Polygon polygon1 = geomFactory.createPolygon(shell, holes);

		Polygon[] polygons = new Polygon[] { polygon1, polygon2 };

		reference = geomFactory.createMultiPolygon(polygons);

		checker = referenceChecker(reference);

		gridChecker = combine(referenceChecker(reference, maxPositionalError),
				gridConfig.geometryChecker());

	}

	/**
	 * Test multi-surface geometries read from a GML 3 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testMultiSurfaceGml3() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml3.xsd").toURI(),
				getClass().getResource("/data/surface/sample-multisurface-gml3.xml").toURI());

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// SurfaceMember with LinearRings defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test multi-surface geometries read from a GML 3.1 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testMultiSurfaceGml31() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml31.xsd").toURI(),
				getClass().getResource("/data/surface/sample-multisurface-gml31.xml").toURI());

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// SurfaceMember with LinearRings defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test multi-surface geometries read from a GML 3.2 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testMultiSurfaceGml32() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml32.xsd").toURI(),
				getClass().getResource("/data/surface/sample-multisurface-gml32.xml").toURI());

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// SurfaceMember with LinearRings defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test multi-surface geometries read from a GML 3 file. Geometry
	 * coordinates will be moved to the universal grid
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testMultiSurfaceGml3_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml3.xsd").toURI(),
				getClass().getResource("/data/surface/sample-multisurface-gml3.xml").toURI(),
				gridConfig);

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// SurfaceMember with LinearRings defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test multi-surface geometries read from a GML 3.1 file. Geometry
	 * coordinates will be moved to the universal grid
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testMultiSurfaceGml31_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml31.xsd").toURI(),
				getClass().getResource("/data/surface/sample-multisurface-gml31.xml").toURI(),
				gridConfig);

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// SurfaceMember with LinearRings defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test multi-surface geometries read from a GML 3.2 file. Geometry
	 * coordinates will be moved to the universal grid
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testMultiSurfaceGml32_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml32.xsd").toURI(),
				getClass().getResource("/data/surface/sample-multisurface-gml32.xml").toURI(),
				gridConfig);

		// one instance expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// SurfaceMember with LinearRings defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

}
