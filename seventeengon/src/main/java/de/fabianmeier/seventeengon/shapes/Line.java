package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.PreservingMap;
import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * Directed line through pointA and pointB. The lambdas indicate the length.
 * 
 * @author JFM
 *
 */
public class Line extends AtomicGeoObject
{
	private final double endLambda;
	private final XYpoint pointA;
	private final XYpoint pointB;

	private final XYvector normedDirection;

	private final double startLambda;

	/**
	 * Creates a line fulfilling the equation xFactor*x + yFactor*y = constant
	 * 
	 * @param xFactor
	 *            coefficient of x
	 * @param yFactor
	 *            coefficient of y
	 * @param constant
	 *            constant
	 */
	public Line(double xFactor, double yFactor, double constant)
	{
		XYpoint firstB = null;
		if (DMan.same(xFactor, 0))
		{
			pointA = new XYpoint(0, constant / yFactor);
			firstB = new XYpoint(1, constant / yFactor);
		}
		else
		{
			pointA = new XYpoint(constant / xFactor, 0);
			if (DMan.same(yFactor, 0))
				firstB = new XYpoint(constant / xFactor, 1);
			else
				firstB = new XYpoint(1, constant / yFactor - xFactor / yFactor);
		}

		normedDirection = new XYvector(pointA, firstB).normed();

		pointB = normedDirection.shift(pointA);

		this.startLambda = -10000;
		this.endLambda = 10000;

	}

	/**
	 * An infinite line through the points
	 * 
	 * @param pointA
	 *            first point
	 * @param pointB
	 *            second point
	 */
	// public Line(XYpoint pointA, XYpoint pointB)
	// {
	//
	// if (pointA.equals(pointB))
	// throw new IllegalArgumentException(
	// "Zwei gleiche Punkte " + pointA.toString());
	//
	// this.pointA = pointA;
	// this.pointB = pointB;
	// this.startLambda = -10000000;
	// this.endLambda = 10000000;
	// normedDirection = new XYvector(pointA, pointB).normed();
	//
	// }

	/**
	 * A finite line, parametrised by pointA and pointB which equal 0 and 1 on
	 * the lambda scale
	 * 
	 * @param pointA
	 *            point A
	 * @param pointB
	 *            point B
	 * @param startLambda
	 *            start Lambda (pointA=0, pointB=1)
	 * @param endLambda
	 *            end Lambda
	 */
	public Line(XYpoint pointA, XYpoint pointB, double startLambda,
			double endLambda)
	{

		if (startLambda > endLambda)
		{
			throw new IllegalArgumentException("startLambda " + startLambda
					+ " greater than endLambda " + endLambda);
		}

		if (pointA.equals(pointB))
			throw new IllegalArgumentException(
					"Zwei gleiche Punkte " + pointA.toString());

		this.pointA = pointA;
		this.pointB = pointB;
		this.startLambda = startLambda;
		this.endLambda = endLambda;
		normedDirection = new XYvector(pointA, pointB).normed();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoObject#draw(de.fabianmeier.
	 * seventeengon.geoobjects.GeoCanvas, java.lang.String)
	 */
	// @Override
	// public void draw(GeoCanvas canvas, GeoVisible visi)
	// {
	// canvas.drawLine(getStartPoint(), getEndPoint(), visi);
	//
	// }

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;

		if (!this.normedDirection.equals(other.normedDirection))
			return false;

		GeoObject intersect = this.intersectWith(other);

		if (intersect.isEmpty())
			return false;

		Line leftLine = new Line(1, 0, -10000);

		boolean leftLineMatch = leftLine.intersectWith(this)
				.equals(leftLine.intersectWith(other));

		if (!leftLineMatch)
			return false;

		Line rightLine = new Line(1, 0, 10000);

		boolean rightLineMatch = rightLine.intersectWith(this)
				.equals(rightLine.intersectWith(other));

		if (!rightLineMatch)
			return false;

		if (leftLineMatch && rightLineMatch
				&& !leftLine.intersectWith(this).isEmpty()
				&& !rightLine.intersectWith(this).isEmpty())
			return true;

		Line topLine = new Line(0, 1, 10000);

		boolean topLineMatch = topLine.intersectWith(this)
				.equals(topLine.intersectWith(other));

		if (!topLineMatch)
			return false;
		Line bottomLine = new Line(1, 0, -10000);

		boolean bottomLineMatch = bottomLine.intersectWith(this)
				.equals(bottomLine.intersectWith(other));
		if (!bottomLineMatch)
			return false;

		if (topLineMatch && bottomLineMatch
				&& !topLine.intersectWith(this).isEmpty()
				&& !bottomLine.intersectWith(this).isEmpty())
			return true;

		return (getStartPoint().equals(other.getStartPoint())
				&& getEndPoint().equals(other.getEndPoint()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getBoundary()
	 */
	@Override
	public GeoObject getBoundary()
	{
		return this;
	}

	@Override
	public int getDimension()
	{
		return 1;
	}

	public double getEndLambda()
	{
		return endLambda;
	}

	public XYpoint getEndPoint()
	{
		return getPointByLambda(endLambda);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getFilledObject()
	 */
	@Override
	public GeoObject getFilledObject()
	{
		return this;
	}

	/**
	 * 
	 * @param point
	 *            a point on the line
	 * @return The lambda value
	 */
	public double getLambda(XYpoint point)
	{
		// if ((new Line(pointA, pointB)).intersectWith(point).isEmpty())
		// throw new IllegalArgumentException(
		// "Punkt " + point + " nicht auf der Gerade " + this);

		if (DMan.same(pointA.getX(), pointB.getX()))
		{
			return (point.getY() - pointA.getY())
					/ (pointB.getY() - pointA.getY());
		}
		else
		{
			return (point.getX() - pointA.getX())
					/ (pointB.getX() - pointA.getX());
		}
	}

	private XYpoint getPoint(double nextDouble)
	{
		double lambda = startLambda + nextDouble * (endLambda - startLambda);
		return getPointByLambda(lambda);

	}

	public XYpoint getPointA()
	{
		return pointA;
	}

	public XYpoint getPointB()
	{
		return pointB;
	}

	public XYpoint getPointByLambda(double lambda)
	{
		XYvector vector = new XYvector(pointA, pointB);
		return vector.multiplyBy(lambda).shift(pointA);
	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + hashCode());
		return getPoint(rand.nextDouble());
	}

	public double getStartLambda()
	{
		return startLambda;
	}

	public XYpoint getStartPoint()
	{
		return getPointByLambda(startLambda);
	}

	@Override
	public int hashCode()
	{
		return normedDirection.hashCode();

	}

	@Override
	public GeoObject intersectWith(GeoObject geoObject)
	{
		if (geoObject instanceof XYpoint)
		{
			return IntersectionManager.intersect(this, (XYpoint) geoObject);
		}
		if (geoObject instanceof Line)
		{
			return IntersectionManager.intersect(this, (Line) geoObject);
		}
		if (geoObject instanceof Arc)
		{
			return IntersectionManager.intersect(this, (Arc) geoObject);
		}
		if (geoObject instanceof Triangle)
		{
			return IntersectionManager.intersect(this, (Triangle) geoObject);
		}
		if (geoObject instanceof Circle)
		{
			return IntersectionManager.intersect(this, (Circle) geoObject);
		}

		return geoObject.intersectWith(this);

	}

	// @Override
	// public void paint(Graphics2D g2d)
	// {
	// // setColourAndStroke(g2d);
	//
	// g2d.draw(new Line2D.Double(getStartPoint().getX(),
	// getStartPoint().getY(), getEndPoint().getX(),
	// getEndPoint().getY()));
	//
	// }

	/**
	 * Sub segment of the line
	 * 
	 * @param startPoint
	 *            point on the line
	 * @param endPoint
	 *            point on the line
	 * @return the intersection of the line from startPoint to endPoint and this
	 *         line.
	 */
	public Line subSegment(XYpoint startPoint, XYpoint endPoint)
	{
		Line otherLine = new Line(startPoint, endPoint, 0, 1);
		GeoObject geo = this.intersectWith(otherLine);

		if (geo instanceof Line)
			return (Line) geo;
		else
			throw new IllegalArgumentException("Points " + startPoint + " or "
					+ endPoint + " not on line " + this);
		// double startL = getLambda(startPoint);
		// double endL = getLambda(endPoint);
		//
		// startL = Math.max(startL, startLambda);
		// endL = Math.min(endL, endLambda);
		//
		// return new Line(pointA, pointB, startL, endL);

	}

	@Override
	public String toString()
	{
		return getStartPoint().toString() + " -> " + getEndPoint().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#getZeroDimensionalPart()
	 */
	@Override
	public Set<XYpoint> getZeroDimensionalPart()
	{
		return new HashSet<XYpoint>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#affineMap(de.fabianmeier.
	 * seventeengon.shapes.XYvector, double)
	 */
	@Override
	public Line preservingMap(PreservingMap preMap)
	{
		XYpoint aNew = pointA.preservingMap(preMap);
		XYpoint bNew = pointB.preservingMap(preMap);

		return new Line(aNew, bNew, startLambda, endLambda);

	}

	private XYpoint convexCombination(XYpoint a, XYpoint b, double lambda)
	{
		return new XYpoint(a.getX() * lambda + b.getX() * (1 - lambda),
				a.getY() * lambda + a.getY() * (1 - lambda));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getNameDrawingAngles()
	 */
	@Override
	public List<Angle> getNameDrawingAngles()
	{
		NumericAngle startAngle = (new XYvector(pointB, pointA)).getAngle();
		NumericAngle endAngle = (new XYvector(pointA, pointB)).getAngle();
		List<XYpoint> points = new ArrayList<XYpoint>();

		points.add(convexCombination(getStartPoint(), getEndPoint(), 0.5));
		points.add(convexCombination(getStartPoint(), getEndPoint(), 0.3));
		points.add(convexCombination(getStartPoint(), getEndPoint(), 0.7));
		points.add(convexCombination(getStartPoint(), getEndPoint(), 0.1));
		points.add(convexCombination(getStartPoint(), getEndPoint(), 0.9));

		List<Angle> back = new ArrayList<Angle>();

		for (XYpoint point : points)
		{
			back.add(new Angle(point, startAngle, endAngle));
		}

		return back;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// de.fabianmeier.seventeengon.shapes.GeoObject#rotate(de.fabianmeier.
	// * seventeengon.shapes.XYpoint, double)
	// */
	// @Override
	// public Line rotate(XYpoint around, double rotationAngle)
	// {
	// XYpoint aNew = pointA.rotate(around, rotationAngle);
	// XYpoint bNew = pointB.rotate(around, rotationAngle);
	//
	// return new Line(aNew, bNew, startLambda, endLambda);
	// }

}