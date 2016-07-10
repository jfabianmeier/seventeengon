/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import de.fabianmeier.seventeengon.shapes.Angle;
import de.fabianmeier.seventeengon.shapes.Arc;
import de.fabianmeier.seventeengon.shapes.Circle;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class GeoDistance
{
	/**
	 * 
	 * @param first
	 *            first geoObject
	 * @param second
	 *            second geoObject
	 * @return the distance (minimal distance of points)
	 */
	public static double distance(GeoObject first, GeoObject second)
	{
		if (!first.intersectWith(second).isEmpty())
			return 0;

		if (first.getSubObjects().size() > 0)
		{
			double minDist = Double.MAX_VALUE;

			for (GeoObject geo : first.getSubObjects())
			{
				double localDist = distance(geo, second);
				if (localDist < minDist)
					minDist = localDist;
			}

			return minDist;
		}

		if (second.getSubObjects().size() > 0)
		{
			double minDist = Double.MAX_VALUE;

			for (GeoObject geo : second.getSubObjects())
			{
				double localDist = distance(first, geo);
				if (localDist < minDist)
					minDist = localDist;
			}

			return minDist;
		}

		if (first instanceof Triangle)
		{
			Triangle triangle = (Triangle) first;

			Line AB = new Line(triangle.getPointA(), triangle.getPointB(), 0, 1);
			Line BC = new Line(triangle.getPointB(), triangle.getPointC(), 0, 1);
			Line CA = new Line(triangle.getPointC(), triangle.getPointA(), 0, 1);

			return Math.min(Math.min(distance(AB, second), distance(BC, second)), distance(CA, second));

		}

		if (second instanceof Triangle)
		{
			Triangle triangle = (Triangle) second;

			Line AB = new Line(triangle.getPointA(), triangle.getPointB(), 0, 1);
			Line BC = new Line(triangle.getPointB(), triangle.getPointC(), 0, 1);
			Line CA = new Line(triangle.getPointC(), triangle.getPointA(), 0, 1);

			return Math.min(Math.min(distance(AB, first), distance(BC, first)), distance(CA, first));
		}

		if (first instanceof Circle)
		{
			Circle circle = (Circle) first;

			Arc arc = new Arc(circle.getCentre(), circle.getRadius(), circle.getStartAngle(), circle.getEndAngle());

			return distance(arc, second);

		}

		if (second instanceof Circle)
		{
			Circle circle = (Circle) second;

			Arc arc = new Arc(circle.getCentre(), circle.getRadius(), circle.getStartAngle(), circle.getEndAngle());

			return distance(arc, first);

		}

		if (first instanceof Angle)
		{
			return distance(((Angle) first).getVertex(), second);
		}

		if (second instanceof Angle)
		{
			return distance(((Angle) first).getVertex(), first);
		}

		if (first instanceof XYpoint && second instanceof XYpoint)
		{
			return specialDistance((XYpoint) first, (XYpoint) second);
		}

		if (first instanceof XYpoint && second instanceof Line)
		{
			return specialDistance((XYpoint) first, (Line) second);
		}

		if (first instanceof XYpoint && second instanceof Arc)
		{
			return specialDistance((XYpoint) first, (Arc) second);
		}

		if (first instanceof Line && second instanceof XYpoint)
		{
			return specialDistance((XYpoint) second, (Line) first);
		}

		if (first instanceof Line && second instanceof Line)
		{
			return specialDistance((Line) first, (Line) second);
		}

		if (first instanceof Line && second instanceof Arc)
		{
			return specialDistance((Line) first, (Arc) second);
		}

		if (first instanceof Arc && second instanceof XYpoint)
		{
			return specialDistance((XYpoint) second, (Arc) first);
		}

		if (first instanceof Arc && second instanceof Line)
		{
			return specialDistance((Line) second, (Arc) first);
		}

		if (first instanceof Arc && second instanceof Arc)
		{
			return specialDistance((Arc) first, (Arc) second);
		}

		throw new IllegalStateException("This should never happen.");

	}

	/**
	 * @param first
	 *            an arc
	 * @param second
	 *            an arc
	 * @return the distance of both
	 */
	private static double specialDistance(Arc first, Arc second)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param first
	 *            a line
	 * @param second
	 *            an arc
	 * @return the distance of both
	 */
	private static double specialDistance(Line first, Arc second)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param first
	 *            a line
	 * @param second
	 *            a line
	 * @return the distance of both
	 */
	private static double specialDistance(Line first, Line second)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param first
	 *            a point
	 * @param second
	 *            an arc
	 * @return the distance of both
	 */
	private static double specialDistance(XYpoint first, Arc second)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param first
	 *            a point
	 * @param second
	 *            a line
	 * @return the distance of both
	 */
	private static double specialDistance(XYpoint first, Line second)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param first
	 *            a point
	 * @param second
	 *            apoint
	 * @return the distance of both
	 */
	private static double specialDistance(XYpoint first, XYpoint second)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
