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

package eu.esdihumboldt.util.geometry.interpolation.grid;

import static eu.esdihumboldt.util.geometry.interpolation.grid.GridUtil.movePointToGrid;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import eu.esdihumboldt.util.geometry.interpolation.AbstractInterpolationAlgorithm;
import eu.esdihumboldt.util.geometry.interpolation.ArcSegment;
import eu.esdihumboldt.util.geometry.interpolation.InterpolationUtil;
import eu.esdihumboldt.util.geometry.interpolation.model.Arc;
import eu.esdihumboldt.util.geometry.interpolation.model.ArcByPoints;

/**
 * Grid based interpolation algorithm.
 * 
 * @author Simon Templer
 */
public class GridInterpolation extends AbstractInterpolationAlgorithm {

	/**
	 * Identifier of the algorithm in the extension point.
	 */
	public static final String EXTENSION_ID = "grid";

	/**
	 * Parameter name for the setting to move all geometries' coordinates to the
	 * grid.
	 */
	public static final String PARAMETER_MOVE_ALL_TO_GRID = "interpolation.grid.moveAllToGrid";

	private boolean moveAllToGrid = false;

	private double gridSize;

	@Override
	public void configure(GeometryFactory factory, double maxPositionalError,
			Map<String, String> properties) {
		super.configure(factory, maxPositionalError, properties);

		// determine grid size
		this.gridSize = GridUtil.getGridSize(maxPositionalError);

		// setting to move all geometries to the grid
		this.moveAllToGrid = Boolean
				.parseBoolean(properties.getOrDefault(PARAMETER_MOVE_ALL_TO_GRID, "false"));
	}

	/**
	 * relocate relocate geometry coordinate to the nearest universal grid point
	 * 
	 * @param coordinate the geometry coordinates
	 * @return relocates grid coordinate
	 */
	protected Coordinate pointToGrid(Coordinate coordinate) {
		return movePointToGrid(coordinate, gridSize);
	}

	@Override
	public LineString interpolateArc(Arc arc) {
		if (InterpolationUtil.isStraightLine(arc)) {
			// this happens when slopes are close to equal

			ArcByPoints byPoints = arc.toArcByPoints();
			if (moveAllToGrid) {
				// move to grid
				return createLineString(new Coordinate[] { pointToGrid(byPoints.getStartPoint()),
						pointToGrid(byPoints.getMiddlePoint()),
						pointToGrid(byPoints.getEndPoint()) }, arc);
			}
			else {
				// return points as-is
				return createLineString(new Coordinate[] { byPoints.getStartPoint(),
						byPoints.getMiddlePoint(), byPoints.getEndPoint() }, arc);
			}
		}

		return interpolateToLineString(arc);
	}

	private LineString interpolateToLineString(Arc arc) {
		// arc segments to process
		Deque<ArcSegment> toProcess = new LinkedList<>();
		// start with full arc as segment
		toProcess.addFirst(new ArcGridSegment(arc, moveAllToGrid, gridSize));
		// list to collect atomic parts
		List<ArcSegment> parts = new LinkedList<>();

		// for every segment to process...
		while (!toProcess.isEmpty()) {
			ArcSegment segment = toProcess.pop();

			if (segment.isAtomic()) {
				// segment cannot be split
				// -> use for result
				parts.add(segment);
			}
			else {
				// otherwise split the segment in two and handle the parts
				toProcess.addFirst(segment.getSecondPart());
				toProcess.addFirst(segment.getFirstPart());
			}
		}

		// combine the segments to a single LineString
		List<Coordinate> coords = new ArrayList<>();
		for (int i = 0; i < parts.size(); i++) {
			ArcSegment part = parts.get(i);
			if (i == 0) {
				coords.add(part.getStartPoint());
			}
			Coordinate middle = part.getMiddlePoint();
			if (middle != null) {
				// should actually not occur
				InterpolationUtil.addIfDifferent(coords, middle);
			}
			InterpolationUtil.addIfDifferent(coords, part.getEndPoint());
		}

		return createLineString(coords.toArray(new Coordinate[coords.size()]), arc);
	}

}
