package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import de.fabianmeier.seventeengon.intersection.DMan;

public class DManTest
{

	@Test
	public void testSame()
	{
		double a = 1.0;
		double b = 1.00000001;
		double c = 2.3;

		assertTrue(DMan.same(a, b));
		assertFalse(DMan.same(a, c));
	}

	@Test
	public void testDoubleHash()
	{
		double a = 0.0;
		double b = 0.00000004;
		assertEquals(DMan.DoubleHash(a), DMan.DoubleHash(b));
	}

	@Test
	public void testSquareRoot()
	{
		Set<Double> back = DMan.squareRoot(4);

		for (Double d : back)
		{
			DMan.same(d * d, 4);
		}

		back = DMan.squareRoot(0.0000000002);
		assertTrue(back.size() == 1);

		back = DMan.squareRoot(-3);
		assertTrue(back.isEmpty());
	}

	@Test
	public void testLessOrEqual()
	{
		double a = 1.0;
		double b = 1.00000001;
		double c = 2.4;

		assertTrue(DMan.LessOrEqual(a, b));
		assertTrue(DMan.LessOrEqual(b, a));
		assertTrue(DMan.LessOrEqual(a, c));
		assertFalse(DMan.LessOrEqual(c, b));
	}

}
