/*
 * Copyright (c) 2017 wetransform GmbH
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

package eu.esdihumboldt.util.geometry.interpolation.split;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import eu.esdihumboldt.util.geometry.interpolation.AbstractArcTest;
import eu.esdihumboldt.util.geometry.interpolation.grid.GridInterpolation;
import eu.esdihumboldt.util.geometry.interpolation.model.Angle;
import eu.esdihumboldt.util.geometry.interpolation.model.Arc;
import eu.esdihumboldt.util.geometry.interpolation.model.ArcByCenterPoint;
import eu.esdihumboldt.util.geometry.interpolation.model.ArcByPoints;
import eu.esdihumboldt.util.geometry.interpolation.model.ArcString;
import eu.esdihumboldt.util.geometry.interpolation.model.impl.ArcByCenterPointImpl;
import eu.esdihumboldt.util.geometry.interpolation.model.impl.ArcByPointsImpl;
import eu.esdihumboldt.util.geometry.interpolation.model.impl.ArcStringImpl;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

/**
 * Tests for interpolation with {@link GridInterpolation}.
 * 
 * @author Simon Templer
 */
@Features("Geometries")
@Stories("Arcs")
@SuppressWarnings("javadoc")
public class SplitInterpolationTest extends AbstractArcTest {

	@Test
	public void testHalfCircle() throws IOException {
		ArcByCenterPoint arc = new ArcByCenterPointImpl(new Coordinate(0, 0), 1.0,
				Angle.fromDegrees(0), Angle.fromDegrees(180), true);

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void testOffsetBig() throws IOException {
		ArcByCenterPoint arc = new ArcByCenterPointImpl(new Coordinate(2, 2), 15.0,
				Angle.fromDegrees(45), Angle.fromDegrees(135), true);

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void testLarge() throws IOException {
		ArcByCenterPoint arc = new ArcByCenterPointImpl(new Coordinate(0, 0), 50.0,
				Angle.fromDegrees(45), Angle.fromDegrees(135), true);

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void testOffsetCircle() throws IOException {
		ArcByCenterPoint arc = new ArcByCenterPointImpl(new Coordinate(2, 2), 5.0,
				Angle.fromDegrees(0), Angle.fromDegrees(0), true);

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void test90Deegrees() throws IOException {
		ArcByCenterPoint arc = new ArcByCenterPointImpl(new Coordinate(0, 0), Math.sqrt(2.0),
				Angle.fromDegrees(45), Angle.fromDegrees(135), false);

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void testByPoints1() throws IOException {
		Arc arc = new ArcByPointsImpl(new Coordinate(-3, 2), new Coordinate(-2, 4),
				new Coordinate(0, 4));

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void testByPoints2() throws IOException {
		Arc arc = new ArcByPointsImpl(new Coordinate(0, 4), new Coordinate(2, 3),
				new Coordinate(4, 4));

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void testByPoints3() throws IOException {
		Arc arc = new ArcByPointsImpl(new Coordinate(4, 4), new Coordinate(4, 6),
				new Coordinate(2, 6));

		splitInterpolationTest(arc, 0.1);
	}

	@Test
	public void testArcString() throws IOException {
		List<Arc> arcs = new ArrayList<>();

		arcs.add(new ArcByPointsImpl(new Coordinate(-3, 2), new Coordinate(-2, 4),
				new Coordinate(0, 4)));
		arcs.add(new ArcByPointsImpl(new Coordinate(0, 4), new Coordinate(2, 3),
				new Coordinate(4, 4)));
		arcs.add(new ArcByPointsImpl(new Coordinate(4, 4), new Coordinate(4, 6),
				new Coordinate(2, 6)));

		splitInterpolationTest(new ArcStringImpl(arcs), 0.1);
	}

	// utility methods

	private LineString splitInterpolationTest(ArcString arcs, double maxPositionalError)
			throws IOException {
		SplitInterpolation interpol = new SplitInterpolation();
		Map<String, String> properties = new HashMap<>();
		interpol.configure(new GeometryFactory(), maxPositionalError, properties);

		LineString result = interpol.interpolateArcString(arcs);

		drawInterpolatedArcString(arcs, result);

		// test interpolated geometry
		Coordinate[] coords = result.getCoordinates();
		for (int i = 0; i < coords.length; i++) {
			Coordinate c = coords[i];

			// check if two coordinates are not the same
			if (i < coords.length - 1) {
				Coordinate c2 = coords[i + 1];

				assertNotEquals(MessageFormat
						.format("Subsequent coordinates are equal ({0} and {1})", c, c2), c, c2);
			}
		}

		return result;
	}

	private LineString splitInterpolationTest(Arc arc, double maxPositionalError)
			throws IOException {
		SplitInterpolation interpol = new SplitInterpolation();
		Map<String, String> properties = new HashMap<>();
		interpol.configure(new GeometryFactory(), maxPositionalError, properties);

		LineString result = interpol.interpolateArc(arc);

		drawInterpolatedArc(arc, result);

		// test interpolated geometry
		Coordinate[] coords = result.getCoordinates();

		if (coords.length > 1 && arc instanceof ArcByPoints) {
			// test start and end point
			assertEquals(arc.toArcByPoints().getStartPoint(), coords[0]);
			assertEquals(arc.toArcByPoints().getEndPoint(), coords[coords.length - 1]);
		}

		for (int i = 0; i < coords.length; i++) {
			Coordinate c = coords[i];

			// check if two coordinates are not the same
			if (i < coords.length - 1) {
				Coordinate c2 = coords[i + 1];

				assertNotEquals("Subsequent coordinates are equal", c, c2);
			}

			// check distance from center
			double distance = arc.toArcByCenterPoint().getCenterPoint().distance(c);
			double delta = Math.abs(distance - arc.toArcByCenterPoint().getRadius());
			assertTrue(delta <= maxPositionalError);
		}

		return result;
	}

}
