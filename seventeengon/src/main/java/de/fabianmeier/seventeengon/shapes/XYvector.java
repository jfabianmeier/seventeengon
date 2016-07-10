package de.fabianmeier.seventeengon.shapes;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * A two-dimensional vector
 * 
 * @author jfabi
 *
 */
public class XYvector
{

	/**
	 * 
	 * @return Gives the vector (0,0)
	 */
	public static XYvector nullVector()
	{
		return new XYvector(new XYpoint(0, 0), new XYpoint(0, 0));
	}

	private final double xMove;
	private final double yMove;

	/**
	 * 
	 * @return the x part
	 */
	public double getxMove()
	{
		return xMove;
	}

	/**
	 * 
	 * @return the y part
	 */
	public double getyMove()
	{
		return yMove;
	}

	/**
	 * a vector from start to end.
	 * 
	 * @param start
	 *            start
	 * @param end
	 *            end
	 */
	public XYvector(XYpoint start, XYpoint end)
	{
		xMove = end.getX() - start.getX();
		yMove = end.getY() - start.getY();
	}

	/**
	 * a vector of given radius and angle
	 * 
	 * @param radius
	 *            radius
	 * @param angle
	 *            angle
	 */
	public XYvector(double radius, NumericAngle angle)
	{
		xMove = radius * angle.cos();
		yMove = radius * angle.sin();
	}

	/**
	 * A vector of given coordinates
	 * 
	 * @param xMove
	 *            x
	 * @param yMove
	 *            y
	 */
	public XYvector(double xMove, double yMove)
	{
		this.xMove = xMove;
		this.yMove = yMove;
	}

	/**
	 * 
	 * @param other
	 *            another vector
	 * @return the scalar product
	 */
	public double scalarProduct(XYvector other)
	{
		return xMove * other.xMove + yMove * other.yMove;
	}

	/**
	 * 
	 * @param a
	 *            a point
	 * @return the shifted point (by this vector)
	 */
	public XYpoint shift(XYpoint a)
	{
		return new XYpoint(a.getX() + xMove, a.getY() + yMove);
	}

	/**
	 * 
	 * @param factor
	 *            a scalar factor
	 * @return the stretched vector
	 */
	public XYvector multiplyBy(double factor)
	{
		return new XYvector(factor * xMove, factor * yMove);
	}

	/**
	 * 
	 * @param vector
	 *            vector
	 * @return the sum of this and the other vector
	 */
	public XYvector addTo(XYvector vector)
	{
		return new XYvector(xMove + vector.xMove, yMove + vector.yMove);
	}

	/**
	 * 
	 * @return the length of the vector
	 */
	public double getLength()
	{
		return Math.sqrt(xMove * xMove + yMove * yMove);
	}

	/**
	 * 
	 * @return the angle of the vector to the positive x-axis.
	 */
	public NumericAngle getAngle()
	{
		if (DMan.same(0, getLength()))
			throw new UnsupportedOperationException("No angle for null vector");
		double angle = Math.acos(xMove / getLength());

		if (yMove > 0)
			return new NumericAngle(angle);
		else
			return new NumericAngle(2 * Math.PI - angle);

	}

	/**
	 * 
	 * @return the normed vector (length 1)
	 */
	public XYvector normed()
	{
		return this.multiplyBy(1 / getLength());
	}

	/**
	 * Gives the difference as value between -pi and pi.
	 * 
	 * @param otherVector
	 *            vector of which the angle will be used
	 * @return difference of vector angle to this angle
	 */
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
		temp = DMan.doubleHash(xMove);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = DMan.doubleHash(yMove);
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
		if (DMan.doubleHash(xMove) != DMan.doubleHash(other.xMove))
			return false;
		if (DMan.doubleHash(yMove) != DMan.doubleHash(other.yMove))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "<" + xMove + ", " + yMove + ">";
	}

}
