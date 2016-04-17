package de.fabianmeier.seventeengon.intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.shapes.Circle;
import de.fabianmeier.seventeengon.shapes.FilledCircle;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.Pshape;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.Angle;

public class IntersectionManager
{
	public static Set<Pshape> intersect(XYpoint point1, XYpoint point2)
	{
		Set<Pshape> back = new HashSet<Pshape>();

		if (point1.equals(point2))
			back.add(point1);

		return back;
	}

	public static Set<Pshape> intersect(XYpoint point, Line line)
	{
		// // TODO: Check if x- or y- coordinates coincide.
		// double lambdaX = (point.getX() - line.getPointA().getX())
		// / (point.getX() - line.getPointB().getX());
		//
		// double lambdaY = (point.getY() - line.getPointA().getY())
		// / (point.getY() - line.getPointB().getY());

		Set<Pshape> back = new HashSet<Pshape>();

		XYvector BA = new XYvector(line.getPointA(), line.getPointB());
		XYvector BAorth = new XYvector(BA.getyMove(), -BA.getxMove());

		XYvector PA = new XYvector(line.getPointA(), point);

		double[][] coefficients = new double[2][2];

		coefficients[0][0] = BA.getxMove();
		coefficients[0][1] = BAorth.getxMove();
		coefficients[1][0] = BA.getyMove();
		coefficients[1][1] = BAorth.getyMove();

		double[] rhs = new double[2];

		rhs[0] = PA.getxMove();
		rhs[1] = PA.getyMove();

		double[] lambda = EquationSolver.solveLinearSystem(coefficients, rhs);

		if (DMan.same(lambda[1], 0))
		{
			if (DMan.LessOrEqual(line.getStartLambda(), lambda[0])

			&& DMan.LessOrEqual(lambda[0], line.getEndLambda()))
			{
				back.add(point);
			}

		}

		// if (DMan.same(lambdaX, lambdaY))
		// if (DMan.LessOrEqual(line.getStartLambda(), lambdaX)
		// && DMan.LessOrEqual(lambdaX, line.getEndLambda()))
		// {
		// back.add(point);
		// }

		return back;

	}

	public static Set<Pshape> intersect(XYpoint point, Circle circle)
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

		if (DMan.LessOrEqual(0, result[0]) && DMan.LessOrEqual(0, result[1])
				&& DMan.LessOrEqual(result[0] + result[1], 1))
		{
			back.add(point);
		}

		return back;
	}

	public static Set<Pshape> intersect(XYpoint point, FilledCircle fcirc)
	{
		Set<Pshape> back = new HashSet<Pshape>();
		XYvector radiusVector = new XYvector(fcirc.getCentre(), point);

		if (!DMan.LessOrEqual(radiusVector.getLength(), fcirc.getRadius()))
		{
			return back;
		} else
		{
			if (fcirc.getStartPoint().equals(fcirc.getEndPoint()))
			{
				back.add(point);
			} else
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

	public static Set<Pshape> intersect(Line line, XYpoint point)
	{
		return intersect(point, line);
	}

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

			if (DMan.LessOrEqual(startLambda, endLambda))
			{
				if (DMan.same(startLambda, endLambda))
				{
					back.add(line1.getPointByLambda(startLambda));
				} else
				{
					back.add(new Line(line1.getPointA(), line1.getPointB(),
							startLambda, endLambda, 0, null));
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

		XYvector resultVector = new XYvector(line1.getPointA(),
				line2.getPointB());

		double[][] coefficients = new double[2][2];
		double[] rhs = new double[2];

		coefficients[0][0] = vector1.getxMove();
		coefficients[0][1] = -vector2.getxMove();
		coefficients[1][0] = vector1.getyMove();
		coefficients[1][1] = -vector2.getyMove();
		rhs[0] = resultVector.getxMove();
		rhs[1] = resultVector.getyMove();

		double[] lhs = EquationSolver.solveLinearSystem(coefficients, rhs);

		XYpoint point = vector1.multiplyBy(lhs[0]).shift(line1.getPointA());

		if (!line1.intersectWith(point).isEmpty()
				&& !line2.intersectWith(point).isEmpty())
			back.add(point);

		return back;

	}

	public static Set<Pshape> intersect(Line line, Circle circle)
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

	public static Set<Pshape> intersect(Line line, Triangle triangle)
	{
		Line lineAB = new Line(triangle.getPointA(), triangle.getPointB(), 0, 1,
				0, null);
		Line lineBC = new Line(triangle.getPointB(), triangle.getPointC(), 0, 1,
				0, null);
		Line lineCA = new Line(triangle.getPointC(), triangle.getPointA(), 0, 1,
				0, null);

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

	public static Set<Pshape> intersect(Line line, FilledCircle fcirc)
	{

		Circle circle = new Circle(fcirc.getCentre(), fcirc.getRadius(), 0,
				null);

		Set<Pshape> intersectionPieces = new HashSet<Pshape>();

		if (!fcirc.getStartPoint().equals(fcirc.getEndPoint()))
		{
			Line lineSE = new Line(fcirc.getStartPoint(), fcirc.getEndPoint(),
					0, 1, 0, null);
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

	public static Set<Pshape> intersect(Circle circle, XYpoint point)
	{
		return intersect(point, circle);

	}

	public static Set<Pshape> intersect(Circle circle, Line line)
	{
		return intersect(line, circle);
	}

	private static Set<Pshape> circlePieces(Circle circle,
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
	private static Set<Pshape> circlePieces(Circle circle,
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
				back.add(new Circle(circle.getCentre(), circle.getRadius(),
						angleDoubleList.get(i),
						angleDoubleList.get((i + 1) % n), 0, null));
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

	public static Set<Pshape> intersect(Circle circle1, Circle circle2)
	{

		if (circle1.getCentre().equals(circle2.getCentre()))
		{
			Set<Pshape> back = new HashSet<Pshape>();

			if (DMan.same(circle1.getRadius(), circle2.getRadius()))
			{

				Set<XYpoint> pointSet = new HashSet<XYpoint>();

				pointSet.add(circle1.getStartPoint());
				pointSet.add(circle1.getEndPoint());
				pointSet.add(circle2.getStartPoint());
				pointSet.add(circle2.getEndPoint());

				back.addAll(circlePieces(circle1, pointSet, circle1, circle2));

				return back;

			} else
			{
				return back;
			}

		} else
		{
			double constant = square(circle1.getRadius())
					- square(circle2.getRadius())
					+ square(circle2.getCentre().getX())
					+ square(circle2.getCentre().getY())
					- square(circle1.getCentre().getX())
					- square(circle1.getCentre().getY());

			double xFactor = 2
					* (circle2.getCentre().getX() - circle1.getCentre().getX());
			double yFactor = 2
					* (circle2.getCentre().getY() - circle1.getCentre().getY());

			Line pseudoLine = new Line(xFactor, yFactor, constant);

			Set<Pshape> intersections = pseudoLine.intersectWith(circle1);

			Set<Pshape> back = new HashSet<Pshape>();

			for (Pshape pshape : intersections)
			{
				if (!circle2.intersectWith(pshape).isEmpty())
					back.add(pshape);
			}

			return back;

			// XYvector centreVector = new XYvector(circle1.getCentre(),
			// circle2.getCentre());
			//
			// double k = centreVector.getLength();
			//
			// double k1 = (-square(circle2.getRadius())
			// + square(circle1.getRadius()) + square(k)) / 2 / k;
			//
			// Set<Double> hSet = DMan
			// .squareRoot(square(circle1.getRadius()) - square(k1));
			//
			// Set<Pshape> back = new HashSet<Pshape>();
			//
			// XYvector orthVector = new XYvector(centreVector.getyMove(),
			// -centreVector.getxMove()).normed();
			//
			// for (Double h : hSet)
			// {
			// XYpoint point = centreVector.multiplyBy(k1 / k)
			// .addTo(orthVector.multiplyBy(h))
			// .shift(circle1.getCentre());
			//
			// if (!circle1.intersectWith(point).isEmpty()
			// && !circle2.intersectWith(point).isEmpty())
			// back.add(point);
			// }
			//
			// return back;
		}
	}

	public static Set<Pshape> intersect(Circle circle, Triangle triangle)
	{
		Line lineAB = new Line(triangle.getPointA(), triangle.getPointB(), 0, 1,
				0, null);
		Line lineBC = new Line(triangle.getPointB(), triangle.getPointC(), 0, 1,
				0, null);
		Line lineCA = new Line(triangle.getPointC(), triangle.getPointA(), 0, 1,
				0, null);

		Set<Pshape> intersectionPieces = new HashSet<Pshape>();

		intersectionPieces.addAll(circle.intersectWith(lineAB));
		intersectionPieces.addAll(circle.intersectWith(lineBC));
		intersectionPieces.addAll(circle.intersectWith(lineCA));

		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		for (Pshape pshape : intersectionPieces)
			intersectionPoints.add((XYpoint) pshape);

		return circlePieces(circle, intersectionPoints, circle, triangle);

	}

	public static Set<Pshape> intersect(Circle circle, FilledCircle fcirc)
	{
		Circle circle2 = new Circle(fcirc.getCentre(), fcirc.getRadius(),
				fcirc.getStartAngle().asDouble(),
				fcirc.getEndAngle().asDouble(), 0, null);

		Set<Pshape> circleInter = circle.intersectWith(circle2);

		for (Pshape pseudoCircle : circleInter)
		{
			if (pseudoCircle instanceof Circle)
			{
				return circleInter;
			}
		}

		Set<Pshape> intersectionPieces = new HashSet<Pshape>();

		if (!fcirc.getStartPoint().equals(fcirc.getEndPoint()))
		{
			Line line = new Line(fcirc.getStartPoint(), fcirc.getEndPoint());
			intersectionPieces.addAll(circle.intersectWith(line));
		}

		intersectionPieces.addAll(circle.intersectWith(circle2));

		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		for (Pshape pshape : intersectionPieces)
			intersectionPoints.add((XYpoint) pshape);

		return circlePieces(circle, intersectionPoints, circle, fcirc);

	}

	public static Set<Pshape> intersect(Triangle triangle, XYpoint point)
	{
		return intersect(point, triangle);

	}

	public static Set<Pshape> intersect(Triangle triangle, Line line)
	{
		return intersect(line, triangle);

	}

	public static Set<Pshape> intersect(Triangle triangle, Circle circle)
	{
		return intersect(circle, triangle);

	}

	public static Set<Pshape> intersect(Triangle triangle1, Triangle triangle2)
	{
		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		Set<Pshape> intersectionShapes = new HashSet<Pshape>();

		Line line1AB = new Line(triangle1.getPointA(), triangle1.getPointB(), 0,
				1, 0, null);
		Line line1BC = new Line(triangle1.getPointB(), triangle1.getPointC(), 0,
				1, 0, null);
		Line line1CA = new Line(triangle1.getPointC(), triangle1.getPointA(), 0,
				1, 0, null);
		Line line2AB = new Line(triangle2.getPointA(), triangle2.getPointB(), 0,
				1, 0, null);
		Line line2BC = new Line(triangle2.getPointB(), triangle2.getPointC(), 0,
				1, 0, null);
		Line line2CA = new Line(triangle2.getPointC(), triangle2.getPointA(), 0,
				1, 0, null);

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

			back.add(new Line(linePoints.get(0), linePoints.get(1), 0, 1, 0,
					null));

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
					vectorList.get(i + 1).shift(startPoint), 0, null));

		}

		return back;
	}

	public static Set<Pshape> intersect(Triangle triangle, FilledCircle fcirc)
	{
		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		Set<Pshape> intersectionShapes = new HashSet<Pshape>();

		Line lineAB = new Line(triangle.getPointA(), triangle.getPointB(), 0, 1,
				0, null);
		Line lineBC = new Line(triangle.getPointB(), triangle.getPointC(), 0, 1,
				0, null);
		Line lineCA = new Line(triangle.getPointC(), triangle.getPointA(), 0, 1,
				0, null);
		if (!fcirc.getStartPoint().equals(fcirc.getEndPoint()))
		{
			Line line = new Line(fcirc.getStartPoint(), fcirc.getEndPoint(), 0,
					1, 0, null);
			intersectionShapes.addAll(lineBC.intersectWith(line));

			intersectionShapes.addAll(lineCA.intersectWith(line));

			intersectionShapes.addAll(lineAB.intersectWith(line));
		}
		Circle circle = new Circle(fcirc.getCentre(), fcirc.getRadius(),
				fcirc.getStartAngle().asDouble(),
				fcirc.getEndAngle().asDouble(), 0, null);

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
			if (pshape instanceof Circle)
			{
				Circle preCircle = (Circle) pshape;
				back.add(new FilledCircle(preCircle.getCentre(),
						preCircle.getRadius(),
						preCircle.getStartAngle().asDouble(),
						preCircle.getEndAngle().asDouble(), 0, null));
			}
		}

		return back;
	}

	public static Set<Pshape> intersect(FilledCircle fcirc, XYpoint point)
	{
		return intersect(point, fcirc);
	}

	public static Set<Pshape> intersect(FilledCircle fcirc, Line line)
	{
		return intersect(line, fcirc);
	}

	public static Set<Pshape> intersect(FilledCircle fcirc, Circle circle)
	{
		return intersect(circle, fcirc);
	}

	public static Set<Pshape> intersect(FilledCircle fcirc, Triangle triangle)
	{
		return intersect(triangle, fcirc);
	}

	public static Set<Pshape> intersect(FilledCircle fcirc1,
			FilledCircle fcirc2)
	{
		Set<XYpoint> intersectionPoints = new HashSet<XYpoint>();

		Set<Pshape> intersectionShapes = new HashSet<Pshape>();

		Circle circle1 = new Circle(fcirc1.getCentre(), fcirc1.getRadius(),
				fcirc1.getStartAngle().asDouble(),
				fcirc1.getEndAngle().asDouble(), 0, null);

		Circle circle2 = new Circle(fcirc2.getCentre(), fcirc2.getRadius(),
				fcirc2.getStartAngle().asDouble(),
				fcirc2.getEndAngle().asDouble(), 0, null);

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
			if (pshape instanceof Circle)
			{
				Circle preCircle = (Circle) pshape;
				back.add(new FilledCircle(preCircle.getCentre(),
						preCircle.getRadius(),
						preCircle.getStartAngle().asDouble(),
						preCircle.getEndAngle().asDouble(), 0, null));
			}
		}

		return back;

	}

	private static double square(double x)
	{
		return x * x;
	}

}
