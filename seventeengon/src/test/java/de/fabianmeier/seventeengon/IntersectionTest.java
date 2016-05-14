package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fabianmeier.seventeengon.shapes.Arc;
import de.fabianmeier.seventeengon.shapes.Circle;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

public class IntersectionTest
{

	private static boolean pointAssert(GeoObject geo, double x, double y)
	{
		return (geo.containsPoint(new XYpoint(x, y)));
	}

	@Test
	public void testIntersectXYpointXYpoint()
	{
		XYpoint point1 = new XYpoint(10, 20);
		XYpoint point2 = new XYpoint(20, 30);
		XYpoint point3 = new XYpoint(10.000000001, 19.999999999);

		assertEquals(point1, point3);
		assertNotEquals(point1, point2);
	}

	@Test
	public void testIntersectXYpointLine()
	{
		XYpoint point1 = new XYpoint(10, 30);
		Line line = new Line(new XYpoint(0, 20), new XYpoint(20, 40));

		GeoObject back = line.intersectWith(point1);

		assertTrue(back.containsPoint(point1));

		XYpoint point2 = new XYpoint(0, 0);

		back = line.intersectWith(point2);

		assertTrue(back.isEmpty());
	}

	@Test
	public void testIntersectXYpointCircle()
	{
		XYpoint point = new XYpoint(10, 0);
		Arc circle = new Arc(new XYpoint(0, 0), 10);
		XYpoint point2 = new XYpoint(10, 10);

		assertFalse(circle.intersectWith(point).isEmpty());
		assertTrue(circle.intersectWith(point2).isEmpty());
	}

	@Test
	public void testIntersectXYpointTriangle()
	{
		XYpoint point = new XYpoint(10, 10);
		XYpoint point2 = new XYpoint(0, 10);
		XYpoint point3 = new XYpoint(5, 5);
		Triangle triangle = new Triangle(new XYpoint(0, 10), new XYpoint(0, 0),
				new XYpoint(10, 0));
		assertTrue(triangle.intersectWith(point).isEmpty());
		assertFalse(triangle.intersectWith(point2).isEmpty());
		assertFalse(triangle.intersectWith(point3).isEmpty());

	}

	@Test
	public void testIntersectXYpointFilledCircle()
	{
		Circle fcirc = new Circle(new XYpoint(0, 0), 10);

		XYpoint point1 = new XYpoint(1, 1);
		XYpoint point2 = new XYpoint(10, 0);
		XYpoint point3 = new XYpoint(10, 10);

		assertFalse(point1.intersectWith(fcirc).isEmpty());
		assertFalse(point2.intersectWith(fcirc).isEmpty());
		assertTrue(point3.intersectWith(fcirc).isEmpty());
	}

	@Test
	public void testIntersectLineXYpoint()
	{
		Line line = new Line(new XYpoint(0, 10), new XYpoint(0, 20));
		XYpoint point1 = new XYpoint(0, 40);
		XYpoint point2 = new XYpoint(10, 20);

		assertFalse(point1.intersectWith(line).isEmpty());
		assertTrue(point2.intersectWith(line).isEmpty());
	}

	@Test
	public void testIntersectLineLine()
	{
		Line line = new Line(new XYpoint(0, 10), new XYpoint(0, 30));
		Line line2 = new Line(new XYpoint(10, 0), new XYpoint(40, 0));

		GeoObject cut = line.intersectWith(line2);
		assertTrue(cut.containsPoint(new XYpoint(0, 0)));

		Line line3 = new Line(new XYpoint(0, 43), new XYpoint(0, 40));

		cut = line.intersectWith(line3);

		assertTrue(cut.containsPoint(new XYpoint(0, 40)));
		assertTrue(cut.containsPoint(new XYpoint(0, 42)));
		assertTrue(cut.containsPoint(new XYpoint(0, 43)));

	}

	@Test
	public void testIntersectLineCircle()
	{
		Line line = new Line(new XYpoint(10, 10), new XYpoint(10, 40));
		Arc circle = new Arc(new XYpoint(10, 10), 10);

		GeoObject cut = circle.intersectWith(line);

		assertTrue(cut.containsPoint(new XYpoint(10, 0)));
		assertTrue(cut.containsPoint(new XYpoint(10, 20)));
	}

	@Test
	public void testIntersectLineTriangle()
	{
		Line line = new Line(new XYpoint(0, 10), new XYpoint(0, 20));
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(0, 10),
				new XYpoint(10, 0));

		GeoObject cut = line.intersectWith(triangle);

		assertTrue(cut.containsPoint(new XYpoint(0, 5)));
		assertFalse(cut.containsPoint(new XYpoint(0, 15)));
	}

	@Test
	public void testIntersectLineFilledCircle()
	{
		Circle fcirc = new Circle(new XYpoint(0, 0), 10);

		Line line = new Line(new XYpoint(0, 0), new XYpoint(0, 10));

		GeoObject cut = line.intersectWith(fcirc);

		assertTrue(pointAssert(cut, 0, -10));
		assertTrue(pointAssert(cut, 0, 10));
		assertFalse(pointAssert(cut, 3, 3));

	}

	@Test
	public void testIntersectCircleXYpoint()
	{
		XYpoint point = new XYpoint(3, 4);
		Arc circle = new Arc(new XYpoint(0, 0), 5);

		assertFalse(point.intersectWith(circle).isEmpty());
	}

	@Test
	public void testIntersectCircleLine()
	{
		Arc circle = new Arc(new XYpoint(0, 0), 10);
		Line line = new Line(new XYpoint(10, 0), new XYpoint(10, 20));
		GeoObject cut = line.intersectWith(circle);

		assertTrue(pointAssert(cut, 10, 0));
	}

	@Test
	public void testIntersectCircleCircle()
	{
		Arc circle1 = new Arc(new XYpoint(0, 0), 5);
		Arc circle2 = new Arc(new XYpoint(0, 8), 5);

		GeoObject cut = circle1.intersectWith(circle2);

		assertTrue(pointAssert(cut, 3, 4));
		assertTrue(pointAssert(cut, -3, 4));
	}

	@Test
	public void testIntersectCircleTriangle()
	{
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(0, 10),
				new XYpoint(10, 0));
		Arc circle = new Arc(new XYpoint(0, 0), 10);

		GeoObject cut = triangle.intersectWith(circle);

		assertTrue(pointAssert(cut, 0, 10));
		assertTrue(pointAssert(cut, 10, 0));
	}

	@Test
	public void testIntersectCircleFilledCircle()
	{
		Arc arc = new Arc(new XYpoint(0, 0), 5);
		Circle fcirc = new Circle(new XYpoint(0, 5), 10);

		GeoObject cut = arc.intersectWith(fcirc);

		assertTrue(pointAssert(cut, 0, 5));
		assertTrue(pointAssert(cut, 5, 0));
	}

	@Test
	public void testIntersectCircleFilledCircle2()
	{
		Arc arc = new Arc(new XYpoint(0, 0), 5);
		Circle fcirc = new Circle(new XYpoint(0, 5), 100);

		GeoObject cut = arc.intersectWith(fcirc);

		assertTrue(pointAssert(cut, 0, 5));
		assertTrue(pointAssert(cut, 5, 0));
	}

	@Test
	public void testIntersectTriangleXYpoint()
	{
		Triangle triangle = new Triangle(new XYpoint(-1, -1),
				new XYpoint(-1, 10), new XYpoint(10, -1));
		XYpoint point = new XYpoint(0, 0);

		GeoObject cut = point.intersectWith(triangle);
		assertTrue(pointAssert(cut, 0, 0));
	}

	@Test
	public void testIntersectTriangleLine()
	{
		Line line = new Line(new XYpoint(0, 0), new XYpoint(10, 0));
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(10, 10),
				new XYpoint(0, 10));

		GeoObject cut = line.intersectWith(triangle);

		assertTrue(pointAssert(cut, 0, 0));
	}

	@Test
	public void testIntersectTriangleCircle()
	{
		Arc circle = new Arc(new XYpoint(0, 0), 5);
		Triangle triangle = new Triangle(new XYpoint(10, 10),
				new XYpoint(10, 20), new XYpoint(20, 20));

		GeoObject cut = triangle.intersectWith(circle);

		assertTrue(cut.isEmpty());
	}

	@Test
	public void testIntersectTriangleTriangle()
	{
		Triangle triangle1 = new Triangle(new XYpoint(0, 0), new XYpoint(10, 0),
				new XYpoint(0, 10));
		Triangle triangle2 = new Triangle(new XYpoint(0, 0),
				new XYpoint(-10, 0), new XYpoint(0, -10));

		GeoObject cut = triangle1.intersectWith(triangle2);

		assertTrue(pointAssert(cut, 0, 0));
	}

	@Test
	public void testIntersectTriangleFilledCircle()
	{
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(0, 10),
				new XYpoint(10, 0));
		Circle fcirc = new Circle(new XYpoint(0, 0), 5);

		GeoObject cut = triangle.intersectWith(fcirc);

		assertTrue(pointAssert(cut, 3, 3));

	}

	@Test
	public void testIntersectFilledCircleXYpoint()
	{
		Circle fcirc = new Circle(new XYpoint(10, 20), 3);
		XYpoint point = new XYpoint(11, 21);

		GeoObject cut = fcirc.intersectWith(point);

		assertFalse(cut.isEmpty());
	}

	@Test
	public void testIntersectFilledCircleLine()
	{
		Circle fcirc = new Circle(new XYpoint(0, 0), 5);

		Line line = new Line(new XYpoint(2, 10), new XYpoint(2, 0));

		GeoObject cut = line.intersectWith(fcirc);

		assertTrue(pointAssert(cut, 2, 1));
	}

	@Test
	public void testIntersectFilledCircleCircle()
	{
		Arc circle = new Arc(new XYpoint(1, 1), 5);
		Circle fcirc = new Circle(new XYpoint(1, 1), 5);

		GeoObject cut = circle.intersectWith(fcirc);

		assertTrue(pointAssert(cut, 6, 1));
	}

	@Test
	public void testIntersectFilledCircleTriangle()
	{
		Circle fcirc = new Circle(new XYpoint(0, 0), 10);
		Triangle triangle = new Triangle(new XYpoint(-1, -1),
				new XYpoint(1, -1), new XYpoint(1, 1));

		GeoObject cut = fcirc.intersectWith(triangle);

		assertTrue(pointAssert(cut, 0, 0));

	}

	@Test
	public void testIntersectFilledCircleFilledCircle()
	{
		Circle fcirc1 = new Circle(new XYpoint(0, 0), 10);
		Circle fcirc2 = new Circle(new XYpoint(5, 0), 5);

		GeoObject cut = fcirc1.intersectWith(fcirc2);

		assertTrue(pointAssert(cut, 6, 1));
	}

}
