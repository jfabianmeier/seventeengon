/**
 * 
 */
package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.PreservingMap;
import de.fabianmeier.seventeengon.geoobjects.RectangleFit;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class RectangleFitTest
{

	@Test
	public void test()
	{
		Set<XYpoint> points = new HashSet<XYpoint>();

		points.add(new XYpoint(0, 0));
		points.add(new XYpoint(1, 0));
		points.add(new XYpoint(0, 2));
		points.add(new XYpoint(1, 2));

		PreservingMap fitTo = RectangleFit.fitTo(2000, 1000, points);

		Set<XYpoint> resultPoints = new HashSet<XYpoint>();

		for (XYpoint point : points)
		{
			resultPoints.add(fitTo.mapPoint(point));
		}

		System.out.println(resultPoints);

		assertTrue(resultPoints.contains(new XYpoint(200, 100)));
		assertTrue(resultPoints.contains(new XYpoint(1800, 100)));
		assertTrue(resultPoints.contains(new XYpoint(1800, 900)));
		assertTrue(resultPoints.contains(new XYpoint(200, 900)));

	}

	@Test
	public void test2()
	{
		Set<XYpoint> points = new HashSet<XYpoint>();

		points.add(new XYpoint(1, 1));
		points.add(new XYpoint(2, 1));
		points.add(new XYpoint(1, 2));
		points.add(new XYpoint(2, 2));

		PreservingMap fitTo = RectangleFit.fitTo(2000, 1000, points);

		Set<XYpoint> resultPoints = new HashSet<XYpoint>();

		for (XYpoint point : points)
		{
			resultPoints.add(fitTo.mapPoint(point));
		}

		System.out.println(resultPoints);

		assertTrue(resultPoints.contains(new XYpoint(600, 100)));
		assertTrue(resultPoints.contains(new XYpoint(1400, 100)));
		assertTrue(resultPoints.contains(new XYpoint(600, 900)));
		assertTrue(resultPoints.contains(new XYpoint(1400, 900)));

	}

	@Test
	public void testCutRectangle()
	{
		Line AB = new Line(new XYpoint(-100, -100), new XYpoint(100, 100), 0,
				1);

		GeoObject geoObject = GeoHolder.cutToRectangle(AB, 50, 100);

		assertEquals(new Line(new XYpoint(0, 0), new XYpoint(50, 50), 0, 1),
				geoObject);

	}

}
