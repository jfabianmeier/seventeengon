package de.fabianmeier.seventeengon.intersection;

import java.util.HashSet;
import java.util.Set;

/**
 * Defines a concept of equality by giving rounding hashes. Two values are
 * considered equal if they round to the same number. This concept is an
 * equivalence relation, but of course not continuous.
 * 
 * @author jfabi
 *
 */
public class DMan
{

	/**
	 * if a-b has the double hash value of 0
	 * 
	 * @param a
	 *            double
	 * @param b
	 *            double
	 * @return if they have the same doubleHash
	 */
	public static boolean same(double a, double b)
	{
		return doubleHash(a - b) == doubleHash(0);
	}

	/**
	 * 
	 * @param a
	 *            double value
	 * @return a long value that is the rounded value of a multiplied with a
	 *         million
	 */
	public static long doubleHash(double a)
	{

		return Math.round(a * 100000);
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

	/**
	 * 
	 * @param a
	 *            value
	 * @param b
	 *            value
	 * @return if a<=b in comparing the hashes
	 */
	public static boolean lessOrEqual(double a, double b)
	{
		return (doubleHash(b) >= doubleHash(a));
	}

}
