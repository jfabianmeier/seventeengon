/**
 * 
 */
package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.svg.NamePlacing;

/**
 * @author JFM
 *
 */
public class NamePlacingTest
{

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector1()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(1, 0),
				new XYvector(0, 1), 2, 1);
		assertEquals(new XYvector(1, 0.5), midVector);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector2()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(1, 0),
				new XYvector(-1, 0), 2, 1);
		assertEquals(new XYvector(1, 0.5), midVector);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector3()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(1, 0),
				new XYvector(1, 1), 2, 1);
		assertEquals(new XYvector(2, 0.5), midVector);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector4()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(1, 1),
				new XYvector(-1, 1), 2, 1);
		assertEquals(new XYvector(0, 1.5), midVector);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector5()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(-1, -1),
				new XYvector(1, -1), 2, 1);
		assertEquals(new XYvector(0, -1.5), midVector);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector6()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(-1, 1),
				new XYvector(-1, -1), 2, 1);
		assertEquals(new XYvector(-1.5, 0), midVector);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector7()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(1, -1),
				new XYvector(1, 1), 2, 1);
		assertEquals(new XYvector(1.5, 0), midVector);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.svg.NamePlacing#getMidVector(de.fabianmeier.seventeengon.shapes.XYvector, de.fabianmeier.seventeengon.shapes.XYvector, double, double)}
	 * .
	 */
	@Test
	public void testGetMidVector8()
	{
		XYvector midVector = NamePlacing.getMidVector(new XYvector(-1, 0),
				new XYvector(-1, -1), 2, 1);
		assertEquals(new XYvector(-2, -0.5), midVector);
	}

}
