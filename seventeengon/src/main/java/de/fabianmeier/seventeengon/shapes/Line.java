package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.GeoVisible;

/**
 * Directed line through pointA and pointB. The lambdas indicate the length. If
 * endpoints are infinite, it is infinite.
 * 
 * @author JFM
 *
 */
public class Line extends PshapeImpl
{
	private final double startLambda;
	private final double endLambda;
	private final XYpoint pointA;

	private final XYpoint pointB;

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
		if (DMan.same(xFactor, 0))
		{
			pointA = new XYpoint(0, constant / yFactor);
			pointB = new XYpoint(1, constant / yFactor);
		}
		else
		{
			pointA = new XYpoint(constant / xFactor, 0);
			if (DMan.same(yFactor, 0))
				pointB = new XYpoint(constant / xFactor, 1);
			else
				pointB = new XYpoint(1, constant / yFactor - xFactor / yFactor);
		}

		this.startLambda = -Double.MAX_VALUE;
		this.endLambda = Double.MAX_VALUE;

	}

	/**
	 * An infinite line through the points
	 * 
	 * @param pointA
	 *            first point
	 * @param pointB
	 *            second point
	 */
	public Line(XYpoint pointA, XYpoint pointB)
	{

		if (pointA.equals(pointB))
			throw new IllegalArgumentException(
					"Zwei gleiche Punkte " + pointA.toString());

		this.pointA = pointA;
		this.pointB = pointB;
		this.startLambda = -10000000;
		this.endLambda = 10000000;
	}

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
			double temp = startLambda;
			startLambda = endLambda;
			endLambda = temp;
		}

		if (pointA.equals(pointB))
			throw new IllegalArgumentException(
					"Zwei gleiche Punkte " + pointA.toString());

		this.pointA = pointA;
		this.pointB = pointB;
		this.startLambda = startLambda;
		this.endLambda = endLambda;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoObject#draw(de.fabianmeier.
	 * seventeengon.geoobjects.GeoCanvas, java.lang.String)
	 */
	@Override
	public void draw(GeoCanvas canvas, String label, GeoVisible visi)
	{
		canvas.drawLine(getStartPoint(), getEndPoint(), label, visi);

	}

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

		if (getStartPoint().equals(other.getStartPoint())
				&& getEndPoint().equals(other.getEndPoint()))
			return true;

		return false;
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

	/**
	 * 
	 * @param point
	 *            a point on the line
	 * @return The lambda value
	 */
	public double getLambda(XYpoint point)
	{
		if ((new Line(pointA, pointB)).intersectWith(point).isEmpty())
			throw new IllegalArgumentException(
					"Punkt " + point + " nicht auf der Gerade " + this);

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
		//
		// return (point.getX() - getPointA().getX())
		// / (point.getX() - getPointB().getX());
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
	public int getPseudoHash()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(endLambda);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((pointA == null) ? 0 : pointA.getPseudoHash());
		result = prime * result
				+ ((pointB == null) ? 0 : pointB.getPseudoHash());
		temp = Double.doubleToLongBits(startLambda);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + getPseudoHash());
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
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getStartPoint() == null) ? 0 : getStartPoint().hashCode())
				+ ((getEndPoint() == null) ? 0 : getEndPoint().hashCode()) * 5;
		return result;
	}

	@Override
	public Set<Pshape> intersectWith(Pshape pshape)
	{
		if (pshape instanceof XYpoint)
		{
			return IntersectionManager.intersect(this, (XYpoint) pshape);
		}
		if (pshape instanceof Line)
		{
			return IntersectionManager.intersect(this, (Line) pshape);
		}
		if (pshape instanceof Arc)
		{
			return IntersectionManager.intersect(this, (Arc) pshape);
		}
		if (pshape instanceof Triangle)
		{
			return IntersectionManager.intersect(this, (Triangle) pshape);
		}
		if (pshape instanceof Circle)
		{
			return IntersectionManager.intersect(this, (Circle) pshape);
		}

		return null;

	}

	@Override
	public void paint(Graphics2D g2d)
	{
		// setColourAndStroke(g2d);

		g2d.draw(new Line2D.Double(getStartPoint().getX(),
				getStartPoint().getY(), getEndPoint().getX(),
				getEndPoint().getY()));

	}

	/**
	 * Sub segment of the line
	 * 
	 * @param startPoint
	 *            point on the line
	 * @param endPoint
	 *            point on the line
	 * @return a new line from one point to the other.
	 */
	public Line subSegment(XYpoint startPoint, XYpoint endPoint)
	{
		double startL = getLambda(startPoint);
		double endL = getLambda(endPoint);

		startL = Math.max(startL, startLambda);
		endL = Math.min(endL, endLambda);

		return new Line(pointA, pointB, startL, endL);

	}

	@Override
	public String toString()
	{
		return getStartPoint().toString() + " -> " + getEndPoint().toString();
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

}