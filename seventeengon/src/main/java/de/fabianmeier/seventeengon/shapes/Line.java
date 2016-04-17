package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;

public class Line extends PshapeImpl
{
	private final XYpoint pointA;
	private final XYpoint pointB;

	private final double startLambda;
	private final double endLambda;

	public Line(XYpoint pointA, XYpoint pointB, double startLambda,
			double endLambda, int visibility, String label)
	{
		super(visibility, label);

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

	public Line(XYpoint pointA, XYpoint pointB)
	{
		super(0, null);

		if (pointA.equals(pointB))
			throw new IllegalArgumentException(
					"Zwei gleiche Punkte " + pointA.toString());

		this.pointA = pointA;
		this.pointB = pointB;
		this.startLambda = -10000000;
		this.endLambda = 10000000;
	}

	public Line(double xFactor, double yFactor, double constant)
	{
		super(0, null);
		if (DMan.same(xFactor, 0))
		{
			pointA = new XYpoint(0, constant / yFactor);
			pointB = new XYpoint(1, constant / yFactor);
		} else
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

	public XYpoint getStartPoint()
	{
		return getPointByLambda(startLambda);
	}

	public XYpoint getEndPoint()
	{
		return getPointByLambda(endLambda);
	}

	public double getLambda(XYpoint point)
	{
		if ((new Line(pointA, pointB)).intersectWith(point).isEmpty())
			throw new IllegalArgumentException(
					"Punkt " + point + " nicht auf der Gerade " + this);

		if (DMan.same(pointA.getX(), pointB.getX()))
		{
			return (point.getY() - pointA.getY())
					/ (pointB.getY() - pointA.getY());
		} else
		{
			return (point.getX() - pointA.getX())
					/ (pointB.getX() - pointA.getX());
		}
		//
		// return (point.getX() - getPointA().getX())
		// / (point.getX() - getPointB().getX());
	}

	public double getStartLambda()
	{
		return startLambda;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getStartPoint() == null) ? 0 : getStartPoint().hashCode())
				+ ((getEndPoint() == null) ? 0 : getEndPoint().hashCode());
		return result;
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

		if (getStartPoint().equals(other.getEndPoint())
				&& getEndPoint().equals(other.getStartPoint()))
			return true;

		return false;
	}

	public double getEndLambda()
	{
		return endLambda;
	}

	public int getDimension()
	{
		return 1;
	}

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
		if (pshape instanceof Circle)
		{
			return IntersectionManager.intersect(this, (Circle) pshape);
		}
		if (pshape instanceof Triangle)
		{
			return IntersectionManager.intersect(this, (Triangle) pshape);
		}
		if (pshape instanceof FilledCircle)
		{
			return IntersectionManager.intersect(this, (FilledCircle) pshape);
		}

		return null;

	}

	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + getPseudoHash());
		return getPoint(rand.nextDouble());
	}

	private XYpoint getPoint(double nextDouble)
	{
		double lambda = startLambda + nextDouble * (endLambda - startLambda);
		return getPointByLambda(lambda);

	}

	public Line subSegment(XYpoint startPoint, XYpoint endPoint)
	{
		double startL = getLambda(startPoint);
		double endL = getLambda(endPoint);

		startL = Math.max(startL, startLambda);
		endL = Math.min(endL, endLambda);

		return new Line(pointA, pointB, startL, endL, getVisibility(), null);

	}

	@Override
	public String toString()
	{
		if (getLabel() == null)
		{
			return getStartPoint().toString() + " -> "
					+ getEndPoint().toString();
		} else
		{
			return getLabel() + ": " + getStartPoint().toString() + " -> "
					+ getEndPoint().toString();
		}
	}

	public void paint(Graphics2D g2d)
	{
		setColourAndStroke(g2d);

		g2d.draw(new Line2D.Double(getStartPoint().getX(),
				getStartPoint().getY(), getEndPoint().getX(),
				getEndPoint().getY()));

	}

}