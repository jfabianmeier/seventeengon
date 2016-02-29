package de.fabianmeier.seventeengon.util;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.shapes.PshapeImpl;
import de.fabianmeier.seventeengon.shapes.XYvector;

public class Angle
{
	private double normalizedAngle;

	public static double AngleDifference(Angle start, Angle end)
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
		Angle other = (Angle) obj;
		if (DMan.DoubleHash(normalizedAngle) != DMan
				.DoubleHash(other.normalizedAngle))
			return false;
		return true;
	}

	public Angle addtoAngle(double shift)
	{
		return new Angle(normalizedAngle + shift);
	}

	public Angle(double angle)
	{
		while (angle >= 2 * Math.PI)
			angle -= 2 * Math.PI;

		while (angle < 0)
			angle += 2 * Math.PI;

		normalizedAngle = angle;
	}

	public Angle(XYvector vector)
	{
		double xNormed = vector.getxMove() / vector.getLength();
		double yNormed = vector.getyMove() / vector.getLength();

		normalizedAngle = Math.acos(xNormed);
		if (yNormed < 0)
			normalizedAngle = 2 * Math.PI - normalizedAngle;

	}

	public boolean inBetween(Angle start, Angle end)
	{
		double shiftedAngle = normalizedAngle;

		while (shiftedAngle < start.normalizedAngle)
		{
			shiftedAngle += 2 * Math.PI;
		}

		double shiftedEndAngle = end.normalizedAngle;
		while (DMan.LessOrEqual(shiftedEndAngle, start.normalizedAngle))
			shiftedEndAngle += 2 * Math.PI;

		return DMan.LessOrEqual(shiftedAngle, shiftedEndAngle);
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
		return PshapeImpl.showValue(normalizedAngle / Math.PI) + " pi";
	}

}