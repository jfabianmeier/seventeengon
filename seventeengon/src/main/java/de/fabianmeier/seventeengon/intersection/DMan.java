package de.fabianmeier.seventeengon.intersection;

import java.util.HashSet;
import java.util.Set;

public class DMan
{

	/**
	 * if both a and b have the same double hash value
	 * 
	 * @param a
	 *            double
	 * @param b
	 *            double
	 * @return if they have the same doubleHash
	 */
	public static boolean same(double a, double b)
	{
		return doubleHash(a) == doubleHash(b);
	}

	/**
	 * 
	 * @param a
	 *            double value
	 * @return a long value that is either the rounded value of a multiplied
	 *         with a million or a representative of positive or negative
	 *         infinity if the absolute value of a exceeds one million
	 */
	public static long doubleHash(double a)
	{
		if (a > 1000000)
			return 1000000 * 100000;

		if (a < -1000000)
			return -1000000 * 100000;

		return Math.round(a * 100000);
	}

	public static boolean isInfinite(double a)
	{
		return (a > 1000000 || a < -1000000);
	}

	/**
	 * 
	 * @param a
	 *            double
	 * @return A multi-valued square root
	 */
	public static Set<Double> squareRoot(double a)
	{
		Set<Double> back = new HashSet<Double>();
		if (doubleHash(a) < 0)
			return back;

		if (doubleHash(a) == 0)
		{
			back.add(0.0);
			return back;
		}

		back.add(Math.sqrt(a));
		back.add(-Math.sqrt(a));
		return back;
	}

	public static boolean lessOrEqual(double a, double b)
	{
		return (doubleHash(b) >= doubleHash(a));
	}

}
