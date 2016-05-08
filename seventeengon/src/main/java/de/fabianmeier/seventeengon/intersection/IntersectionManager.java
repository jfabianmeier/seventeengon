package de.fabianmeier.seventeengon.intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.shapes.Arc;
import de.fabianmeier.seventeengon.shapes.Circle;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.Pshape;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.Angle;

public class IntersectionManager
{
	private static Set<Pshape> circlePieces(Arc circle,
			Set<XYpoint> pointsOnCircle, Pshape pshape1, Pshape pshape2)
	{
		Set<Pshape> pshapeSet = new HashSet<Pshape>();
		pshapeSet.add(pshape1);
		pshapeSet.add(pshape2);

		return circlePieces(circle, pointsOnCircle, pshapeSet);
	}

	// Checks for the pieces of circle separated by pointsOnCircle if they
	// belong to intersectionShapes, assuming that the whole segment is either
	// in or out.
	private static Set<Pshape> circlePieces(Arc circle,
			Set<XYpoint> pointsOnCircle, Set<Pshape> intersectionShapes)
	{

		List<Double> angleDoubleList = new ArrayList<Double>();

		Set<Pshape> back = new HashSet<Pshape>();

		for (XYpoint point : pointsOnCircle)
		{
			angleDoubleList.add(circle.getAngle(point).asDouble());
			if (containedInAll(intersectionShapes, point))
				back.add(point);
		}

		if (pointsOnCircle.isEmpty())
		{
			angleDoubleList.add(0.0);
		}

		Collections.sort(angleDoubleList);

		List<Double> betweenAngleDoubeList = new ArrayList<Double>();

		int n = angleDoubleList.size();

		for (int i = 0; i < n; i++)
		{
			betweenAngleDoubeList.add(angleDoubleList.get(i) / 2
					+ angleDoubleList.get((i + 1) % n) / 2
					+ ((i + 1) / n) * Math.PI);
		}

		for (int i = 0; i < n; i++)
		{
			XYpoint point = circle
					.getAnglePoint(new Angle(betweenAngleDoubeList.get(i)));

			if (containedInAll(intersectionShapes, point))
			{
				back.add(new Arc(circle.getCentre(), circle.getRadius(),
						new Angle(angleDoubleList.get(i)),
						new Angle(angleDoubleList.get((i + 1) % n))));
			}

		}

		return back;

	}

	private static boolean containedInAll(Set<Pshape> intersectionShapes,
			XYpoint point)
	{
		boolean contained = true;

		for (Pshape pshape : intersectionShapes)
		{
			if (pshape.intersectWith(point).isEmpty())
				contained = false;
		}
		return contained;
	}

	/**
	 * 
	 * @param arc1
	 *            Arc
	 * @param arc2
	 *            Arc
	 * @return if the arcs intersect
	 */
	public static Set<Pshape> intersect(Arc arc1, Arc arc2)
	{

		if (arc1.getCentre().equals(arc2.getCentre()))
		{
			Set<Pshape> back = new HashSet<Pshape>();

			if (DMan.same(arc1.getRadius(), arc2.getRadius()))
			{

				Set<XYpoint> pointSet = new HashSet<XYpoint>();

				pointSet.add(arc1.getStartPoint());
				pointSet.add(arc1.getEndPoint());
				pointSet.add(arc2.getStartPoint());
				pointSet.add(arc2.getEndPoint());

				back.addAll(circlePieces(arc1, pointSet, arc1, arc2));

				return back;

			}
			else
			{
				return back;
			}

		}
		else
		{
			double constant = square(arc1.getRadius())
					- square(arc2.getRadius()) + square(arc2.getCentre().getX())
					+ square(arc2.getCentre().getY())
					- square(arc1.getCentre().getX())
					- square(arc1.getCentre().getY());

			double xFactor = 2
					* (arc2.getCentre().getX() - arc1.getCentre().getX());
			double yFactor = 2
					* (arc2.getCentre().getY() - arc1.getCentre().getY());

			Line pseudoLine = new Line(xFactor, yFactor, constant);

			Set<Pshape> intersections = pseudoLine.intersectWith(arc1);

			Set<Pshape> back = new HashSet<Pshape>();

			for (Pshape pshape : intersections)
			{
				if (!arc2.intersectWith(pshape).isEmpty())
					back.add(pshape);
			}

			return back;

		}
	}

	/**
	 * 
	 * @param arc
	 *            Arc
	 * @param fcirc
	 *            Circle
	 * @return if the arc and the circle intersect
	 */
	public static Set<Pshape> intersect(Arc arc, Circle fcirc)
	{
		Arc circle2 = new Arc(fcirc.getCentre(), fcirc.getRadius(),
				fcirc.getStartAngle(), fcirc.getEndAngle());

		Set<Pshape> circleInter = arc.intersectWith(circle2);

		for (Pshape pseudoCircle : circleInter)
		{
			if (pseudoCircle instanceof Arc)
			{
				return circleInter;
			}
		}

		Set<Pshape> intersectionPieces = new HashSet<Pshape>();

		if (!fcirc.getStartPoint().equals(fcirc.getEndPoint()))
		{
			Line line = new Line(fcirc.getStartPoint(), fcirc.getEndPoint());
			intersectionPieces.addAll(arc.intersectWith(line));
		}

		intersectionPieces.addAll(arc.intersectWith(circle2));

		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		for (Pshape pshape : intersectionPieces)
			intersectionPoints.add((XYpoint) pshape);

		return circlePieces(arc, intersectionPoints, arc, fcirc);

	}

	/**
	 * 
	 * @param circle
	 *            Circle
	 * @param line
	 *            Line
	 * @return if the circle and the line intersect
	 */
	public static Set<Pshape> intersect(Arc circle, Line line)
	{
		return intersect(line, circle);
	}

	/**
	 * 
	 * @param circle
	 *            Circle
	 * @param triangle
	 *            Triangle
	 * @return if they intersect
	 */
	public static Set<Pshape> intersect(Arc circle, Triangle triangle)
	{
		Line lineAB = new Line(triangle.getPointA(), triangle.getPointB(), 0,
				1);
		Line lineBC = new Line(triangle.getPointB(), triangle.getPointC(), 0,
				1);
		Line lineCA = new Line(triangle.getPointC(), triangle.getPointA(), 0,
				1);

		Set<Pshape> intersectionPieces = new HashSet<Pshape>();

		intersectionPieces.addAll(circle.intersectWith(lineAB));
		intersectionPieces.addAll(circle.intersectWith(lineBC));
		intersectionPieces.addAll(circle.intersectWith(lineCA));

		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		for (Pshape pshape : intersectionPieces)
			intersectionPoints.add((XYpoint) pshape);

		return circlePieces(circle, intersectionPoints, circle, triangle);

	}

	/**
	 * 
	 * @param circle
	 *            Circle
	 * @param point
	 *            Point
	 * @return if the point lies inside or on the Circle
	 */
	public static Set<Pshape> intersect(Arc circle, XYpoint point)
	{
		return intersect(point, circle);

	}

	public static Set<Pshape> intersect(Circle fcirc, Arc circle)
	{
		return intersect(circle, fcirc);
	}

	/**
	 * 
	 * @param fcirc1
	 *            Circle
	 * @param fcirc2
	 *            Circle
	 * @return if the two circles intersect
	 */
	public static Set<Pshape> intersect(Circle fcirc1, Circle fcirc2)
	{
		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		Set<Pshape> intersectionShapes = new HashSet<Pshape>();

		Arc circle1 = new Arc(fcirc1.getCentre(), fcirc1.getRadius(),
				fcirc1.getStartAngle(), fcirc1.getEndAngle());

		Arc circle2 = new Arc(fcirc2.getCentre(), fcirc2.getRadius(),
				fcirc2.getStartAngle(), fcirc2.getEndAngle());

		if (!fcirc2.getStartPoint().equals(fcirc2.getEndPoint()))
		{
			Line line2 = new Line(fcirc2.getStartPoint(), fcirc2.getEndPoint());
			intersectionShapes.addAll(circle1.intersectWith(line2));
		}

		if (!fcirc1.getStartPoint().equals(fcirc1.getEndPoint()))
		{
			Line line1 = new Line(fcirc1.getStartPoint(), fcirc2.getEndPoint());
			if (!fcirc2.getStartPoint().equals(fcirc2.getEndPoint()))
			{
				Line line2 = new Line(fcirc2.getStartPoint(),
						fcirc2.getEndPoint());
				intersectionShapes.addAll(circle1.intersectWith(line2));

				intersectionShapes.addAll(line1.intersectWith(line2));
			}
			intersectionShapes.addAll(line1.intersectWith(circle2));
		}

		intersectionShapes.addAll(circle1.intersectWith(circle2));

		for (Pshape pshape : intersectionShapes)
		{
			if (pshape instanceof XYpoint)
			{
				intersectionPoints.add((XYpoint) pshape);
			}
		}

		if (!fcirc1.getStartPoint().intersectWith(fcirc2).isEmpty())
			intersectionPoints.add(fcirc1.getStartPoint());
		if (!fcirc1.getEndPoint().intersectWith(fcirc2).isEmpty())
			intersectionPoints.add(fcirc1.getEndPoint());

		if (!fcirc2.getStartPoint().intersectWith(fcirc1).isEmpty())
			intersectionPoints.add(fcirc2.getStartPoint());
		if (!fcirc2.getEndPoint().intersectWith(fcirc1).isEmpty())
			intersectionPoints.add(fcirc2.getEndPoint());

		Set<Pshape> back = new HashSet<Pshape>();

		back.addAll(triangulizeConvexSet(intersectionPoints));

		Set<XYpoint> onCircle = new HashSet<XYpoint>();

		for (XYpoint point : intersectionPoints)
		{
			if (!point.intersectWith(circle2).isEmpty())
				onCircle.add(point);
		}

		Set<Pshape> preCircles = circlePieces(circle2, onCircle, circle2,
				circle1);

		for (Pshape pshape : preCircles)
		{
			if (pshape instanceof Arc)
			{
				Arc preCircle = (Arc) pshape;
				back.add(new Circle(preCircle.getCentre(),
						preCircle.getRadius(), preCircle.getStartAngle(),
						preCircle.getEndAngle()));
			}
		}

		return back;

	}

	public static Set<Pshape> intersect(Circle fcirc, Line line)
	{
		return intersect(line, fcirc);
	}

	public static Set<Pshape> intersect(Circle fcirc, Triangle triangle)
	{
		return intersect(triangle, fcirc);
	}

	public static Set<Pshape> intersect(Circle fcirc, XYpoint point)
	{
		return intersect(point, fcirc);
	}

	/**
	 * 
	 * @param line
	 *            Line
	 * @param circle
	 *            Circle
	 * @return intersection of line and circle
	 */
	public static Set<Pshape> intersect(Line line, Arc circle)
	{
		XYvector lineVector = new XYvector(line.getPointA(), line.getPointB());

		XYvector circleVector = new XYvector(line.getPointA(),
				circle.getCentre());

		Set<Double> lambdaSet = EquationSolver.solveQuadraticEquation(
				lineVector.getLength() * lineVector.getLength(),
				-2 * lineVector.scalarProduct(circleVector),
				circleVector.getLength() * circleVector.getLength()
						- circle.getRadius() * circle.getRadius());

		Set<XYpoint> possiblePoints = new HashSet<XYpoint>();

		for (Double d : lambdaSet)
		{
			possiblePoints
					.add(lineVector.multiplyBy(d).shift(line.getPointA()));
		}

		Set<Pshape> back = new HashSet<Pshape>();

		for (XYpoint point : possiblePoints)
		{
			if (!line.intersectWith(point).isEmpty()
					&& !circle.intersectWith(point).isEmpty())
			{
				back.add(point);
			}
		}
		return back;

	}

	/**
	 * 
	 * @param line
	 *            Line
	 * @param fcirc
	 *            Circle
	 * @return if the line and the circle intersect
	 */
	public static Set<Pshape> intersect(Line line, Circle fcirc)
	{

		Arc circle = new Arc(fcirc.getCentre(), fcirc.getRadius());

		Set<Pshape> intersectionPieces = new HashSet<Pshape>();

		if (!fcirc.getStartPoint().equals(fcirc.getEndPoint()))
		{
			Line lineSE = new Line(fcirc.getStartPoint(), fcirc.getEndPoint(),
					0, 1);
			intersectionPieces.addAll(line.intersectWith(lineSE));
		}

		intersectionPieces.addAll(line.intersectWith(circle));
		Set<Pshape> back = new HashSet<Pshape>();

		if (intersectionPieces.size() == 0)
			return back;

		Set<XYpoint> pointSet = new HashSet<XYpoint>();

		for (Pshape pshape : intersectionPieces)
		{
			if (pshape instanceof XYpoint)
			{
				XYpoint neuPoint = (XYpoint) pshape;
				pointSet.add(neuPoint);
			}
		}

		if (pointSet.size() == 1)
		{
			back.addAll(pointSet);
			return back;
		}

		List<XYpoint> pointList = new ArrayList<XYpoint>(pointSet);

		back.add(line.subSegment(pointList.get(0), pointList.get(1)));

		return back;

	}

	/**
	 * 
	 * @param line1
	 *            Line
	 * @param line2
	 *            Line
	 * @return if both lines intersect
	 */
	public static Set<Pshape> intersect(Line line1, Line line2)
	{
		Set<Pshape> back = new HashSet<Pshape>();

		Line longLine = new Line(line1.getPointA(), line1.getPointB());

		if (!longLine.intersectWith(line2.getPointA()).isEmpty()
				&& !longLine.intersectWith(line2.getPointB()).isEmpty())
		{
			double startLambda2 = line1.getLambda(line2.getStartPoint());
			double endLambda2 = line1.getLambda(line2.getEndPoint());

			if (startLambda2 > endLambda2)
			{
				double temp = startLambda2;
				startLambda2 = endLambda2;
				endLambda2 = temp;
			}

			double startLambda = Math.max(line1.getStartLambda(), startLambda2);
			double endLambda = Math.min(line1.getEndLambda(), endLambda2);

			if (DMan.lessOrEqual(startLambda, endLambda))
			{
				if (DMan.same(startLambda, endLambda))
				{
					back.add(line1.getPointByLambda(startLambda));
				}
				else
				{
					back.add(new Line(line1.getPointA(), line1.getPointB(),
							startLambda, endLambda));
				}
			}

			return back;
		}

		XYvector vector1 = new XYvector(line1.getPointA(), line1.getPointB());
		XYvector vector2 = new XYvector(line2.getPointA(), line2.getPointB());

		vector1 = vector1.normed();
		vector2 = vector2.normed();

		if (vector1.equals(vector2) || vector1.equals(vector2.multiplyBy(-1)))
		{
			return back;
		}

		double[][] coefficients = new double[2][2];

		coefficients[0][0] = vector1.getxMove();
		coefficients[0][1] = -vector2.getxMove();
		coefficients[1][0] = vector1.getyMove();
		coefficients[1][1] = -vector2.getyMove();

		double[] rhs = new double[2];

		XYvector resultVector = new XYvector(line1.getPointA(),
				line2.getPointB());
		rhs[0] = resultVector.getxMove();
		rhs[1] = resultVector.getyMove();

		double[] lhs = EquationSolver.solveLinearSystem(coefficients, rhs);

		XYpoint point = vector1.multiplyBy(lhs[0]).shift(line1.getPointA());

		if (!line1.intersectWith(point).isEmpty()
				&& !line2.intersectWith(point).isEmpty())
			back.add(point);

		return back;

	}

	/**
	 * 
	 * @param line
	 *            Line
	 * @param triangle
	 *            Triangle
	 * @return if the line and the triangle intersect.
	 */
	public static Set<Pshape> intersect(Line line, Triangle triangle)
	{
		Line lineAB = new Line(triangle.getPointA(), triangle.getPointB(), 0,
				1);
		Line lineBC = new Line(triangle.getPointB(), triangle.getPointC(), 0,
				1);
		Line lineCA = new Line(triangle.getPointC(), triangle.getPointA(), 0,
				1);

		Set<Pshape> intersectionPieces = new HashSet<Pshape>();

		intersectionPieces.addAll(line.intersectWith(lineAB));
		intersectionPieces.addAll(line.intersectWith(lineBC));
		intersectionPieces.addAll(line.intersectWith(lineCA));

		Set<Pshape> back = new HashSet<Pshape>();

		if (intersectionPieces.size() == 0)
			return back;

		Set<XYpoint> pointSet = new HashSet<XYpoint>();

		for (Pshape pshape : intersectionPieces)
		{
			if (pshape instanceof XYpoint)
			{
				XYpoint neuPoint = (XYpoint) pshape;
				pointSet.add(neuPoint);
			}
		}

		if (pointSet.size() == 1)
		{
			back.addAll(pointSet);
			return back;
		}

		List<XYpoint> pointList = new ArrayList<XYpoint>(pointSet);

		back.add(line.subSegment(pointList.get(0), pointList.get(1)));

		return back;

	}

	/**
	 * 
	 * @param line
	 *            Line
	 * @param point
	 *            Point
	 * @return if the Point lies on the line
	 */
	public static Set<Pshape> intersect(Line line, XYpoint point)
	{
		return intersect(point, line);
	}

	public static Set<Pshape> intersect(Triangle triangle, Arc circle)
	{
		return intersect(circle, triangle);

	}

	/**
	 * 
	 * @param triangle
	 *            Triangle
	 * @param fcirc
	 *            Circle
	 * @return if the triangle and the circle intersect
	 */
	public static Set<Pshape> intersect(Triangle triangle, Circle fcirc)
	{
		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		Set<Pshape> intersectionShapes = new HashSet<Pshape>();

		Line lineAB = new Line(triangle.getPointA(), triangle.getPointB(), 0,
				1);
		Line lineBC = new Line(triangle.getPointB(), triangle.getPointC(), 0,
				1);
		Line lineCA = new Line(triangle.getPointC(), triangle.getPointA(), 0,
				1);
		if (!fcirc.getStartPoint().equals(fcirc.getEndPoint()))
		{
			Line line = new Line(fcirc.getStartPoint(), fcirc.getEndPoint(), 0,
					1);
			intersectionShapes.addAll(lineBC.intersectWith(line));

			intersectionShapes.addAll(lineCA.intersectWith(line));

			intersectionShapes.addAll(lineAB.intersectWith(line));
		}
		Arc circle = new Arc(fcirc.getCentre(), fcirc.getRadius(),
				fcirc.getStartAngle(), fcirc.getEndAngle());

		intersectionShapes.addAll(lineAB.intersectWith(circle));

		intersectionShapes.addAll(lineBC.intersectWith(circle));

		intersectionShapes.addAll(lineCA.intersectWith(circle));

		for (Pshape pshape : intersectionShapes)
		{
			if (pshape instanceof XYpoint)
			{
				intersectionPoints.add((XYpoint) pshape);
			}
		}

		if (!triangle.getPointA().intersectWith(fcirc).isEmpty())
			intersectionPoints.add(triangle.getPointA());
		if (!triangle.getPointB().intersectWith(fcirc).isEmpty())
			intersectionPoints.add(triangle.getPointB());
		if (!triangle.getPointC().intersectWith(fcirc).isEmpty())
			intersectionPoints.add(triangle.getPointC());

		if (!fcirc.getStartPoint().intersectWith(triangle).isEmpty())
			intersectionPoints.add(fcirc.getStartPoint());
		if (!fcirc.getEndPoint().intersectWith(triangle).isEmpty())
			intersectionPoints.add(fcirc.getEndPoint());

		Set<Pshape> back = new HashSet<Pshape>();

		back.addAll(triangulizeConvexSet(intersectionPoints));

		Set<XYpoint> onCircle = new HashSet<XYpoint>();

		for (XYpoint point : intersectionPoints)
		{
			if (!point.intersectWith(circle).isEmpty())
				onCircle.add(point);
		}

		Set<Pshape> preCircles = circlePieces(circle, onCircle, fcirc,
				triangle);

		for (Pshape pshape : preCircles)
		{
			if (pshape instanceof Arc)
			{
				Arc preCircle = (Arc) pshape;
				back.add(new Circle(preCircle.getCentre(),
						preCircle.getRadius(), preCircle.getStartAngle(),
						preCircle.getEndAngle()));
			}
		}

		return back;
	}

	public static Set<Pshape> intersect(Triangle triangle, Line line)
	{
		return intersect(line, triangle);

	}

	/**
	 * 
	 * @param triangle1
	 *            Triangle
	 * @param triangle2
	 *            Triangle
	 * @return The intersection of the triangles
	 */
	public static Set<Pshape> intersect(Triangle triangle1, Triangle triangle2)
	{
		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		Set<Pshape> intersectionShapes = new HashSet<Pshape>();

		Line line1AB = new Line(triangle1.getPointA(), triangle1.getPointB(), 0,
				1);
		Line line1BC = new Line(triangle1.getPointB(), triangle1.getPointC(), 0,
				1);
		Line line1CA = new Line(triangle1.getPointC(), triangle1.getPointA(), 0,
				1);
		Line line2AB = new Line(triangle2.getPointA(), triangle2.getPointB(), 0,
				1);
		Line line2BC = new Line(triangle2.getPointB(), triangle2.getPointC(), 0,
				1);
		Line line2CA = new Line(triangle2.getPointC(), triangle2.getPointA(), 0,
				1);

		intersectionShapes.addAll(line1AB.intersectWith(line2AB));
		intersectionShapes.addAll(line1AB.intersectWith(line2BC));
		intersectionShapes.addAll(line1AB.intersectWith(line2CA));

		intersectionShapes.addAll(line1BC.intersectWith(line2AB));
		intersectionShapes.addAll(line1BC.intersectWith(line2BC));
		intersectionShapes.addAll(line1BC.intersectWith(line2CA));

		intersectionShapes.addAll(line1CA.intersectWith(line2AB));
		intersectionShapes.addAll(line1CA.intersectWith(line2BC));
		intersectionShapes.addAll(line1CA.intersectWith(line2CA));

		for (Pshape pshape : intersectionShapes)
		{
			if (pshape instanceof XYpoint)
			{
				intersectionPoints.add((XYpoint) pshape);
			}
		}

		if (!triangle1.getPointA().intersectWith(triangle2).isEmpty())
			intersectionPoints.add(triangle1.getPointA());
		if (!triangle1.getPointB().intersectWith(triangle2).isEmpty())
			intersectionPoints.add(triangle1.getPointB());
		if (!triangle1.getPointC().intersectWith(triangle2).isEmpty())
			intersectionPoints.add(triangle1.getPointC());

		if (!triangle2.getPointA().intersectWith(triangle1).isEmpty())
			intersectionPoints.add(triangle2.getPointA());
		if (!triangle2.getPointB().intersectWith(triangle1).isEmpty())
			intersectionPoints.add(triangle2.getPointB());
		if (!triangle2.getPointC().intersectWith(triangle1).isEmpty())
			intersectionPoints.add(triangle2.getPointC());

		return triangulizeConvexSet(intersectionPoints);

	}

	public static Set<Pshape> intersect(Triangle triangle, XYpoint point)
	{
		return intersect(point, triangle);

	}

	/**
	 * 
	 * @param point
	 *            Point
	 * @param circle
	 *            Circle
	 * @return if both objects intersect
	 */
	public static Set<Pshape> intersect(XYpoint point, Arc circle)
	{
		XYvector shiftVector = new XYvector(circle.getCentre(), point);

		Set<Pshape> back = new HashSet<Pshape>();

		if (DMan.same(shiftVector.getLength(), circle.getRadius()))
		{
			Angle angle = shiftVector.getAngle();
			if (circle.containsAngle(angle))
				back.add(point);

		}

		return back;
	}

	/**
	 * 
	 * @param point
	 *            Point
	 * @param fcirc
	 *            Circle
	 * @return if the point lies inside or on the circle
	 */
	public static Set<Pshape> intersect(XYpoint point, Circle fcirc)
	{
		Set<Pshape> back = new HashSet<Pshape>();
		XYvector radiusVector = new XYvector(fcirc.getCentre(), point);

		if (!DMan.lessOrEqual(radiusVector.getLength(), fcirc.getRadius()))
		{
			return back;
		}
		else
		{
			if (fcirc.getStartPoint().equals(fcirc.getEndPoint()))
			{
				back.add(point);
			}
			else
			{
				XYvector vector1 = new XYvector(fcirc.getStartPoint(),
						fcirc.getEndPoint());
				Angle vectorAngle1 = vector1.getAngle();

				XYvector vector2 = new XYvector(fcirc.getEndPoint(),
						fcirc.getStartPoint());
				Angle vectorAngle2 = vector2.getAngle();

				XYvector pointVector = new XYvector(fcirc.getStartPoint(),
						point);
				Angle pointAngle = pointVector.getAngle();

				if (pointAngle.inBetween(vectorAngle2, vectorAngle1))
					back.add(point);
			}

		}

		return back;

	}

	/**
	 * 
	 * @param point
	 *            Point
	 * @param line
	 *            Line
	 * @return if both objects intersect
	 */
	public static Set<Pshape> intersect(XYpoint point, Line line)
	{

		Set<Pshape> back = new HashSet<Pshape>();

		XYvector BA = new XYvector(line.getPointA(), line.getPointB());
		XYvector BAorth = new XYvector(BA.getyMove(), -BA.getxMove());

		double[][] coefficients = new double[2][2];

		coefficients[0][0] = BA.getxMove();
		coefficients[0][1] = BAorth.getxMove();
		coefficients[1][0] = BA.getyMove();
		coefficients[1][1] = BAorth.getyMove();

		double[] rhs = new double[2];

		XYvector PA = new XYvector(line.getPointA(), point);
		rhs[0] = PA.getxMove();
		rhs[1] = PA.getyMove();

		double[] lambda = EquationSolver.solveLinearSystem(coefficients, rhs);

		if (DMan.same(lambda[1], 0))
		{
			if (DMan.lessOrEqual(line.getStartLambda(), lambda[0])

			&& DMan.lessOrEqual(lambda[0], line.getEndLambda()))
			{
				back.add(point);
			}

		}

		return back;
	}

	/**
	 * 
	 * @param point
	 *            Point
	 * @param triangle
	 *            Triangle
	 * @return if point lies inside or on the triangle
	 */
	public static Set<Pshape> intersect(XYpoint point, Triangle triangle)
	{
		Set<Pshape> back = new HashSet<Pshape>();

		double[][] coefficients = new double[2][2];

		coefficients[0][0] = triangle.getPointB().getX()
				- triangle.getPointA().getX();
		coefficients[0][1] = triangle.getPointC().getX()
				- triangle.getPointA().getX();
		coefficients[1][0] = triangle.getPointB().getY()
				- triangle.getPointA().getY();
		coefficients[1][1] = triangle.getPointC().getY()
				- triangle.getPointA().getY();

		double[] rhs = new double[2];
		rhs[0] = point.getX() - triangle.getPointA().getX();
		rhs[1] = point.getY() - triangle.getPointA().getY();

		double[] result = EquationSolver.solveLinearSystem(coefficients, rhs);

		if (DMan.lessOrEqual(0, result[0]) && DMan.lessOrEqual(0, result[1])
				&& DMan.lessOrEqual(result[0] + result[1], 1))
		{
			back.add(point);
		}

		return back;
	}

	/**
	 * 
	 * @param point1
	 *            Point
	 * @param point2
	 *            Point
	 * @return if objects intersect
	 */
	public static Set<Pshape> intersect(XYpoint point1, XYpoint point2)
	{
		Set<Pshape> back = new HashSet<Pshape>();

		if (point1.equals(point2))
			back.add(point1);

		return back;
	}

	private static double square(double x)
	{
		return x * x;
	}

	private static Set<Pshape> triangulizeConvexSet(
			Set<XYpoint> intersectionPoints)
	{
		Set<Pshape> back = new HashSet<Pshape>();

		if (intersectionPoints.size() == 0)
			return back;

		if (intersectionPoints.size() == 1)
		{
			back.addAll(intersectionPoints);
			return back;
		}

		if (intersectionPoints.size() == 2)
		{
			List<XYpoint> linePoints = new ArrayList<XYpoint>(
					intersectionPoints);

			back.add(new Line(linePoints.get(0), linePoints.get(1), 0, 1));

			return back;
		}

		XYpoint startPoint = intersectionPoints.iterator().next();

		List<XYvector> vectorList = new ArrayList<XYvector>();

		for (XYpoint point : intersectionPoints)
		{
			if (!startPoint.equals(point))
			{
				vectorList.add(new XYvector(startPoint, point));
			}
		}

		final XYvector firstVector = vectorList.get(0);

		Collections.sort(vectorList, new Comparator<XYvector>() {

			@Override
			public int compare(XYvector o1, XYvector o2)
			{
				if (firstVector.getAngleDifference(o1) < firstVector
						.getAngleDifference(o2))
					return -1;

				if (firstVector.getAngleDifference(o1) >= firstVector
						.getAngleDifference(o2))
					return 1;

				return 0;

			}

		});

		for (int i = 0; i < vectorList.size() - 1; i++)
		{
			// Wenn der Winkel kleiner als 180° ist, füge das Dreieck hinzu.

			back.add(new Triangle(startPoint,
					vectorList.get(i).shift(startPoint),
					vectorList.get(i + 1).shift(startPoint)));

		}

		return back;
	}

}
