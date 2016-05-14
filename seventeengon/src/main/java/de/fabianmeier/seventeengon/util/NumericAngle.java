package de.fabianmeier.seventeengon.util;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.shapes.AtomicGeoObject;

public class NumericAngle
{
	private double normalizedAngle;

	/**
	 * 
	 * @param start
	 *            Start angle
	 * @param end
	 *            End angle
	 * @return positive difference between these angles (as value between 0 and
	 *         2pi)
	 */
	public static double angleDifference(NumericAngle start, NumericAngle end)
	{
		double endAngle = end.normalizedAngle;
		while (endAngle < start.normalizedAngle)
			endAngle += 2 * Math.PI;

		return endAngle - start.normalizedAngle;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(normalizedAngle);
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
		NumericAngle other = (NumericAngle) obj;
		if (DMan.doubleHash(normalizedAngle) != DMan
				.doubleHash(other.normalizedAngle))
			return false;
		return true;
	}

	public NumericAngle addtoAngle(double shift)
	{
		return new NumericAngle(normalizedAngle + shift);
	}

	/**
	 * 
	 * @param angle
	 *            Double value of an angle (arbitrary positive or negative
	 *            number).
	 */
	public NumericAngle(double angle)
	{
		while (angle >= 2 * Math.PI)
			angle -= 2 * Math.PI;

		while (angle < 0)
			angle += 2 * Math.PI;

		normalizedAngle = angle;
	}

	// /**
	// * The angle of the vector to the x-axis
	// *
	// * @param vector
	// * A non-zero vector
	// */
	// public Angle(XYvector vector)
	// {
	// if (vector.equals(XYvector.nullVector()))
	// throw new IllegalArgumentException(
	// "Null vector does not describe an angle.");
	//
	// double xNormed = vector.getxMove() / vector.getLength();
	// double yNormed = vector.getyMove() / vector.getLength();
	//
	// normalizedAngle = Math.acos(xNormed);
	// if (yNormed < 0)
	// normalizedAngle = 2 * Math.PI - normalizedAngle;
	//
	// }

	/**
	 * 
	 * @param start
	 *            start Angle
	 * @param end
	 *            end Angle
	 * @return If this angle lies in the angle range described by start and end.
	 */
	public boolean inBetween(NumericAngle start, NumericAngle end)
	{
		double shiftedAngle = normalizedAngle;

		while (shiftedAngle < start.normalizedAngle)
		{
			shiftedAngle += 2 * Math.PI;
		}

		double shiftedEndAngle = end.normalizedAngle;
		while (DMan.lessOrEqual(shiftedEndAngle, start.normalizedAngle))
			shiftedEndAngle += 2 * Math.PI;

		return DMan.lessOrEqual(shiftedAngle, shiftedEndAngle);
	}

	public double asDouble()
	{
		return normalizedAngle;
	}

	public double cos()
	{
		return Math.cos(normalizedAngle);
	}

	public double sin()
	{
		return Math.sin(normalizedAngle);
	}

	@Override
	public String toString()
	{
		return AtomicGeoObject.showValue(normalizedAngle / Math.PI) + "pi";
	}

}
