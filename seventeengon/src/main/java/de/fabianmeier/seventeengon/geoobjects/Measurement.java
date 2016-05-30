/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;

/**
 * @author JFM
 *
 */
public class Measurement
{
	/**
	 * Determines if the point lies above the (infinite) line that is described
	 * by line
	 * 
	 * @param point
	 *            a point
	 * @param line
	 *            a segment or line, proglonged to a line
	 * @return if the point lies above the line
	 */
	public static boolean aboveLine(XYpoint point, Line line)
	{
		return orientedDistance(point, line) >= 0;

	}

	/**
	 * Measures the orthogonal distance of the point to the line
	 * 
	 * @param point
	 *            point
	 * @param line
	 *            line (which is virtually prolonged to infinity)
	 * @return the distance
	 */
	public static double distance(XYpoint point, Line line)
	{
		return Math.abs(orientedDistance(point, line));
	}

	/**
	 * 
	 */
	public static double distance(XYpoint point1, XYpoint point2)
	{
		return (new XYvector(point1, point2)).getLength();
	}

	/**
	 * Determines the distance with positive sign if above and negative if below
	 * the line
	 * 
	 * @param point
	 *            point
	 * @param line
	 *            line (prolonged to the unknown territory that people call
	 *            infinity)
	 * @return a distance with sign
	 */
	public static double orientedDistance(XYpoint point, Line line)
	{
		XYvector lineVector = new XYvector(line.getStartPoint(),
				line.getEndPoint());
		XYvector pointVector = new XYvector(line.getStartPoint(), point);

		double angle = lineVector.getAngleDifference(pointVector);

		return Math.sin(angle) * pointVector.getLength();

	}

}
