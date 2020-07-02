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

package eu.esdihumboldt.hale.io.gml.geometry.handler;

import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import org.junit.Test;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;

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
 * Test for reading line string geometries
 * 
 * @author Patrick Lieb
 * @author Arun Varma
 * @author Simon Templer
 */
@Features("Geometries")
@Stories("GML")
public class LineStringHandlerTest extends AbstractHandlerTest {

	private LineString reference;
	private final ReaderConfiguration gridConfig = InterpolationConfigurations.ALL_TO_GRID_DEFAULT;
	private Consumer<Geometry> checker;
	private Consumer<Geometry> gridChecker;

	@Override
	public void init() {
		super.init();

		Coordinate[] coordinates = new Coordinate[] {
				new Coordinate(-39799.68820381, 273207.53980172),
				new Coordinate(-39841.185, 273182.863), new Coordinate(-39882.89, 273153.86) };
		reference = geomFactory.createLineString(coordinates);

		checker = combine(referenceChecker(reference), noCoordinatePairs());

		gridChecker = combine(
				referenceChecker(reference, InterpolationHelper.DEFAULT_MAX_POSITION_ERROR),
				gridConfig.geometryChecker(), noCoordinatePairs());
	}

	/**
	 * Test linestring geometries read from a GML 2 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml2() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml2.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml2.xml").toURI());

		// three instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);

			// 2. LineStringProperty with LineString defined through coord
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 3. LineStringProperty with LineString defined through coord
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test linestring geometries read from a GML 3 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml3() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml3.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml3.xml").toURI());

		// five instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);

			// 2. LineStringProperty with LineString defined through coord
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 3. GeometryProperty with LineString defined through coord
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 4. LineStringProperty with LineString defined through pos
			assertTrue("Fourth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 5. LineStringProperty with LineString defined through pointRep
			assertTrue("Fifth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test linestring geometries read from a GML 3.1 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml31() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml31.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml31.xml").toURI());

		// five instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);

			// 2. LineStringProperty with LineString defined through coord
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 3. LineStringProperty with LineString defined through pos
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 4. LineStringProperty with LineString defined through pointRep
			assertTrue("Fourth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 5. LineStringProperty with LineString defined through
			// pointProperty
			assertTrue("Fifth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 6. LineStringProperty with LineString defined through posList
			assertTrue("Sixth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test linestring geometries read from a GML 3.2 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml32() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml32.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml32.xml").toURI());

		// five instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, checker);

			// 2. LineStringProperty with LineString defined through pos
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 3. LineStringProperty with LineString defined through pointRep
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 4. LineStringProperty with LineString defined through
			// pointProperty
			assertTrue("Fourth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);

			// 5. LineStringProperty with LineString defined through posList
			assertTrue("Fifth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, checker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test linestring geometries read from a GML 2 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml2_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml2.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml2.xml").toURI(),
				gridConfig);

		// three instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 2. LineStringProperty with LineString defined through coord
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 3. LineStringProperty with LineString defined through coord
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test linestring geometries read from a GML 3 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml3_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml3.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml3.xml").toURI(),
				gridConfig);

		// five instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 2. LineStringProperty with LineString defined through coord
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 3. GeometryProperty with LineString defined through coord
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 4. LineStringProperty with LineString defined through pos
			assertTrue("Fourth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 5. LineStringProperty with LineString defined through pointRep
			assertTrue("Fifth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test linestring geometries read from a GML 3.1 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml31_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml31.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml31.xml").toURI(),
				gridConfig);

		// five instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 2. LineStringProperty with LineString defined through coord
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 3. LineStringProperty with LineString defined through pos
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 4. LineStringProperty with LineString defined through pointRep
			assertTrue("Fourth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 5. LineStringProperty with LineString defined through
			// pointProperty
			assertTrue("Fifth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 6. LineStringProperty with LineString defined through posList
			assertTrue("Sixth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

	/**
	 * Test linestring geometries read from a GML 3.2 file
	 * 
	 * @throws Exception if an error occurs
	 */
	@Test
	public void testLineStringGml32_Grid() throws Exception {
		InstanceCollection instances = AbstractHandlerTest.loadXMLInstances(
				getClass().getResource("/data/gml/geom-gml32.xsd").toURI(),
				getClass().getResource("/data/linestring/sample-linestring-gml32.xml").toURI(),
				gridConfig);

		// five instances expected
		ResourceIterator<Instance> it = instances.iterator();
		try {
			// 1. LineStringProperty with LineString defined through coordinates
			assertTrue("First sample feature missing", it.hasNext());
			Instance instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 2. LineStringProperty with LineString defined through pos
			assertTrue("Second sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 3. LineStringProperty with LineString defined through pointRep
			assertTrue("Third sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 4. LineStringProperty with LineString defined through
			// pointProperty
			assertTrue("Fourth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);

			// 5. LineStringProperty with LineString defined through posList
			assertTrue("Fifth sample feature missing", it.hasNext());
			instance = it.next();
			checkSingleGeometry(instance, gridChecker);
		} finally {
			it.close();
		}
	}

}
