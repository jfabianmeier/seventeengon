package de.fabianmeier.seventeengon.shapes;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.util.Angle;

public class XYvector
{
	private final double xMove;
	private final double yMove;

	public double getxMove()
	{
		return xMove;
	}

	public double getyMove()
	{
		return yMove;
	}

	public XYvector(XYpoint start, XYpoint end)
	{
		xMove = end.getX() - start.getX();
		yMove = end.getY() - start.getY();
	}

	public XYvector(double radius, Angle angle)
	{
		xMove = radius * angle.cos();
		yMove = radius * angle.sin();
	}

	public XYvector(double xMove, double yMove)
	{
		this.xMove = xMove;
		this.yMove = yMove;
	}

	public double scalarProduct(XYvector other)
	{
		return xMove * other.xMove + yMove * other.yMove;
	}

	public XYpoint shift(XYpoint a)
	{
		return new XYpoint(a.getX() + xMove, a.getY() + yMove,
				a.getVisibility(), a.getLabel());
	}

	public XYvector multiplyBy(double factor)
	{
		return new XYvector(factor * xMove, factor * yMove);
	}

	public XYvector addTo(XYvector vector)
	{
		return new XYvector(xMove + vector.xMove, yMove + vector.yMove);
	}

	public double getLength()
	{
		return Math.sqrt(xMove * xMove + yMove * yMove);
	}

	public Angle getAngle()
	{
		double angle = Math.acos(xMove / getLength());

		if (yMove > 0)
			return new Angle(angle);
		else
			return new Angle(2 * Math.PI - angle);

	}

	public XYvector normed()
	{
		return this.multiplyBy(1 / getLength());
	}

	// Gives the difference as value between -pi and pi.
	public double getAngleDifference(XYvector otherVector)
	{
		double thisAngle = getAngle().asDouble();
		double otherAngle = otherVector.getAngle().asDouble();

		double result = otherAngle - thisAngle;

		while (result < -Math.PI)
			result += 2 * Math.PI;

		while (result > Math.PI)
			result -= 2 * Math.PI;

		return result;

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = DMan.DoubleHash(xMove);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = DMan.DoubleHash(yMove);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		XYvector other = (XYvector) obj;
		if (DMan.DoubleHash(xMove) != DMan.DoubleHash(other.xMove))
			return false;
		if (DMan.DoubleHash(yMove) != DMan.DoubleHash(other.yMove))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "<" + xMove + ", " + yMove + ">";
	}

}
