/**
 * 
 */
package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fabianmeier.seventeengon.geoobjects.PreservingMap;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class PreservingTestMap
{

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.geoobjects.PreservingMap#mapPoint(de.fabianmeier.seventeengon.shapes.XYpoint)}
	 * .
	 */
	@Test
	public void testMapPoint()
	{
		XYpoint xy = new XYpoint(0, 2);

		PreservingMap pre = new PreservingMap(new XYpoint(1, 1),
				new XYpoint(1, 3), new XYpoint(0, 0), new XYpoint(0, 1));

		XYpoint mapPoint = pre.mapPoint(new XYpoint(1, 3));

		assertEquals(new XYpoint(0, 1), mapPoint);
		assertEquals(new XYpoint(-0.5, 0.5), pre.mapPoint(xy));
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.geoobjects.PreservingMap#scaleFrom(de.fabianmeier.seventeengon.shapes.XYpoint, double, de.fabianmeier.seventeengon.shapes.XYpoint)}
	 * .
	 */
	@Test
	public void testScaleFrom()
	{
		XYpoint xy = new XYpoint(0, 1);
		XYpoint xy2 = new XYpoint(0, 3);
		XYpoint scaleFrom = PreservingMap.scaleFrom(xy, 0.5, xy2);

		assertEquals(new XYpoint(0, 2), scaleFrom);

	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.geoobjects.PreservingMap#turnAround(de.fabianmeier.seventeengon.shapes.XYpoint, double, de.fabianmeier.seventeengon.shapes.XYpoint)}
	 * .
	 */
	@Test
	public void testTurnAround()
	{

		XYpoint xy = new XYpoint(0, 1);
		XYpoint centre = new XYpoint(0, 0);

		double angle = Math.PI / 2;

		assertEquals(new XYpoint(-1, 0),
				PreservingMap.turnAround(centre, angle, xy));

	}

}
