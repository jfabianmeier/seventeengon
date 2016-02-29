package de.fabianmeier.seventeengon.intersection;

import java.util.HashSet;
import java.util.Set;

public class DMan
{
	// private static final double epsilon = 0.00001;

	public static boolean same(double a, double b)
	{
		return DoubleHash(a) == DoubleHash(b);
	}

	public static long DoubleHash(double a)
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

	// public static boolean same(XYpoint point1, XYpoint point2)
	// {
	// Set<Pshape> back = IntersectionManager.intersect(point1, point2);
	//
	// return (!back.isEmpty());
	// }

	public static Set<Double> squareRoot(double a)
	{
		Set<Double> back = new HashSet<Double>();
		if (DoubleHash(a) < 0)
			return back;

		if (DoubleHash(a) == 0)
		{
			back.add(0.0);
			return back;
		}

		back.add(Math.sqrt(a));
		back.add(-Math.sqrt(a));
		return back;
	}

	public static boolean LessOrEqual(double a, double b)
	{
		return (DoubleHash(b) >= DoubleHash(a));
	}

	// public static boolean same(XYvector vector1, XYvector vector2)
	// {
	// return (same(vector1.getxMove(), vector2.getxMove())
	// && same(vector1.getyMove(), vector2.getyMove()));
	// }

}
