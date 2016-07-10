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
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.NumericAngle;

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
		double distByCentre1 = Math.abs(second.getRadius() - specialDistance(second.getCentre(), first));
		double distByCentre2 = Math.abs(first.getRadius() - specialDistance(first.getCentre(), second));
		double dist1 = specialDistance(first.getStartPoint(), second);
		double dist2 = specialDistance(first.getEndPoint(), second);
		double dist3 = specialDistance(second.getStartPoint(), first);
		double dist4 = specialDistance(second.getEndPoint(), first);

		return Math.min(Math.min(distByCentre1, distByCentre2),
				Math.min(Math.min(dist1, dist2), Math.min(dist3, dist4)));
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
		double distByCentre = Math.abs(second.getRadius() - specialDistance(second.getCentre(), first));
		double dist1 = specialDistance(first.getStartPoint(), second);
		double dist2 = specialDistance(first.getEndPoint(), second);
		double dist3 = specialDistance(second.getStartPoint(), first);
		double dist4 = specialDistance(second.getEndPoint(), first);

		return Math.min(distByCentre, Math.min(Math.min(dist1, dist2), Math.min(dist3, dist4)));

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
		double dist1 = specialDistance(first.getStartPoint(), second);
		double dist2 = specialDistance(first.getEndPoint(), second);
		double dist3 = specialDistance(second.getStartPoint(), first);
		double dist4 = specialDistance(second.getEndPoint(), first);

		return Math.min(Math.min(dist1, dist2), Math.min(dist3, dist4));

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
		XYvector centreVector = new XYvector(second.getCentre(), first);

		NumericAngle firstAngle = centreVector.getAngle();

		if (second.containsAngle(firstAngle))
		{
			return Math.abs(second.getRadius() - centreVector.getLength());
		}

		return Math.min(specialDistance(second.getStartPoint(), first), specialDistance(second.getEndPoint(), first));
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
		XYvector lineVector = new XYvector(second.getStartPoint(), second.getEndPoint());
		XYvector reverseLineVector = new XYvector(second.getEndPoint(), second.getStartPoint());

		XYvector startVector = new XYvector(second.getStartPoint(), first);
		XYvector endVector = new XYvector(second.getEndPoint(), first);

		double startAngle = lineVector.getAngleDifference(startVector);
		double endAngle = reverseLineVector.getAngleDifference(endVector);

		if (startAngle < -Math.PI / 2 || startAngle > Math.PI / 2)
			return specialDistance(second.getStartPoint(), first);

		if (endAngle < -Math.PI / 2 || endAngle > Math.PI / 2)
			return specialDistance(second.getEndPoint(), first);

		return Measurement.distance(first, second);
	}

	/**
	 * @param first
	 *            a point
	 * @param second
	 *            a point
	 * @return the distance of both
	 */
	private static double specialDistance(XYpoint first, XYpoint second)
	{
		return (new XYvector(first, second)).getLength();
	}

}
