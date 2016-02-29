package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import de.fabianmeier.seventeengon.shapes.Circle;
import de.fabianmeier.seventeengon.shapes.FilledCircle;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.Pshape;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

public class IntersectionTest1
{

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

		Set<Pshape> back = line.intersectWith(point1);

		assertTrue(back.contains(point1));

		XYpoint point2 = new XYpoint(0, 0);

		back = line.intersectWith(point2);

		assertTrue(back.isEmpty());
	}

	@Test
	public void testIntersectXYpointCircle()
	{
		XYpoint point = new XYpoint(10, 0);
		Circle circle = new Circle(new XYpoint(0, 0), 10, 0, null);
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
				new XYpoint(10, 0), 0, null);
		assertTrue(triangle.intersectWith(point).isEmpty());
		assertFalse(triangle.intersectWith(point2).isEmpty());
		assertFalse(triangle.intersectWith(point3).isEmpty());

	}

	@Test
	public void testIntersectXYpointFilledCircle()
	{
		FilledCircle fcirc = new FilledCircle(new XYpoint(0, 0), 10, 0, null);

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

		Set<Pshape> cut = line.intersectWith(line2);
		assertTrue(cut.contains(new XYpoint(0, 0)));

		Line line3 = new Line(new XYpoint(0, 43), new XYpoint(0, 40));

		cut = line.intersectWith(line3);

		assertTrue(cut.contains(line3));
	}

	@Test
	public void testIntersectLineCircle()
	{
		Line line = new Line(new XYpoint(10, 10), new XYpoint(10, 40));
		Circle circle = new Circle(new XYpoint(10, 10), 10, 0, null);

		Set<Pshape> cut = circle.intersectWith(line);

		assertTrue(cut.contains(new XYpoint(10, 0)));
		assertTrue(cut.contains(new XYpoint(10, 20)));
	}

	@Test
	public void testIntersectLineTriangle()
	{
		Line line = new Line(new XYpoint(0, 10), new XYpoint(0, 20));
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(0, 10),
				new XYpoint(10, 0), 0, null);

		Set<Pshape> cut = line.intersectWith(triangle);

		Line line2 = (Line) cut.iterator().next();

		assertFalse(line2.intersectWith(new XYpoint(0, 5)).isEmpty());
		assertTrue(line2.intersectWith(new XYpoint(0, 15)).isEmpty());
	}

	@Test
	public void testIntersectLineFilledCircle()
	{
		FilledCircle fcirc = new FilledCircle(new XYpoint(0, 0), 10, 0, null);

		Line line = new Line(new XYpoint(0, 0), new XYpoint(0, 10));

		Set<Pshape> cut = line.intersectWith(fcirc);

		assertTrue(cut.size() == 1);

		assertTrue(cut.iterator().next() instanceof Line);

	}

	@Test
	public void testIntersectCircleXYpoint()
	{
		XYpoint point = new XYpoint(3, 4);
		Circle circle = new Circle(new XYpoint(0, 0), 5, 0, null);

		assertFalse(point.intersectWith(circle).isEmpty());
	}

	@Test
	public void testIntersectCircleLine()
	{
		Circle circle = new Circle(new XYpoint(0, 0), 10, 0, null);
		Line line = new Line(new XYpoint(10, 0), new XYpoint(10, 20));
		Set<Pshape> cut = line.intersectWith(circle);

		assertTrue(cut.contains(new XYpoint(10, 0)));
	}

	@Test
	public void testIntersectCircleCircle()
	{
		Circle circle1 = new Circle(new XYpoint(0, 0), 5, 0, null);
		Circle circle2 = new Circle(new XYpoint(0, 8), 5, 0, null);

		Set<Pshape> cut = circle1.intersectWith(circle2);

		assertTrue(cut.contains(new XYpoint(3, 4)));
		assertTrue(cut.contains(new XYpoint(-3, 4)));
	}

	@Test
	public void testIntersectCircleTriangle()
	{
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(0, 10),
				new XYpoint(10, 0), 0, null);
		Circle circle = new Circle(new XYpoint(0, 0), 10, 0, null);

		Set<Pshape> cut = triangle.intersectWith(circle);

		assertTrue(cut.contains(new XYpoint(0, 10)));
		assertTrue(cut.contains(new XYpoint(10, 0)));
	}

	@Test
	public void testIntersectCircleFilledCircle()
	{
		Circle circle = new Circle(new XYpoint(0, 0), 5, 0, null);
		FilledCircle fcirc = new FilledCircle(new XYpoint(0, 5), 10, 0, null);

		Set<Pshape> cut = circle.intersectWith(fcirc);

		assertTrue(cut.contains(circle));
	}

	@Test
	public void testIntersectCircleFilledCircle2()
	{
		Circle circle = new Circle(new XYpoint(0, 0), 5, 0, null);
		FilledCircle fcirc = new FilledCircle(new XYpoint(0, 5), 100, 0, null);

		Set<Pshape> cut = circle.intersectWith(fcirc);

		assertTrue(cut.contains(circle));
	}

	@Test
	public void testIntersectTriangleXYpoint()
	{
		Triangle triangle = new Triangle(new XYpoint(-1, -1),
				new XYpoint(-1, 10), new XYpoint(10, -1), 0, null);
		XYpoint point = new XYpoint(0, 0);

		Set<Pshape> cut = point.intersectWith(triangle);
		assertTrue(cut.contains(point));
	}

	@Test
	public void testIntersectTriangleLine()
	{
		Line line = new Line(new XYpoint(0, 0), new XYpoint(10, 0));
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(10, 10),
				new XYpoint(0, 10), 0, null);

		Set<Pshape> cut = line.intersectWith(triangle);

		assertTrue(cut.contains(new XYpoint(0, 0)));
	}

	@Test
	public void testIntersectTriangleCircle()
	{
		Circle circle = new Circle(new XYpoint(0, 0), 5, 0, null);
		Triangle triangle = new Triangle(new XYpoint(10, 10),
				new XYpoint(10, 20), new XYpoint(20, 20), 0, null);

		Set<Pshape> cut = triangle.intersectWith(circle);

		assertTrue(cut.isEmpty());
	}

	@Test
	public void testIntersectTriangleTriangle()
	{
		Triangle triangle1 = new Triangle(new XYpoint(0, 0), new XYpoint(10, 0),
				new XYpoint(0, 10), 0, null);
		Triangle triangle2 = new Triangle(new XYpoint(0, 0),
				new XYpoint(-10, 0), new XYpoint(0, -10), 0, null);

		Set<Pshape> cut = triangle1.intersectWith(triangle2);

		assertTrue(cut.contains(new XYpoint(0, 0)));
	}

	@Test
	public void testIntersectTriangleFilledCircle()
	{
		Triangle triangle = new Triangle(new XYpoint(0, 0), new XYpoint(0, 10),
				new XYpoint(10, 0), 0, null);
		FilledCircle fcirc = new FilledCircle(new XYpoint(0, 0), 5, 0, null);

		Set<Pshape> cut = triangle.intersectWith(fcirc);

		for (Pshape pshape : cut)
		{
			if (pshape instanceof FilledCircle)
			{
				FilledCircle fcirc2 = fcirc;
				assertFalse(fcirc.intersectWith(new XYpoint(3, 3)).isEmpty());
			}
		}

	}

	@Test
	public void testIntersectFilledCircleXYpoint()
	{
		FilledCircle fcirc = new FilledCircle(new XYpoint(10, 20), 3, 0, null);
		XYpoint point = new XYpoint(11, 21);

		Set<Pshape> cut = fcirc.intersectWith(point);

		assertFalse(cut.isEmpty());
	}

	@Test
	public void testIntersectFilledCircleLine()
	{
		FilledCircle fcirc = new FilledCircle(new XYpoint(0, 0), 5, 0, null);

		Line line = new Line(new XYpoint(2, 10), new XYpoint(2, 0));

		Set<Pshape> cut = line.intersectWith(fcirc);

		for (Pshape pshape : cut)
		{
			if (pshape instanceof Line)
			{
				Line line2 = (Line) pshape;
				assertFalse(line2.intersectWith(new XYpoint(2, 1)).isEmpty());
			}
		}
	}

	@Test
	public void testIntersectFilledCircleCircle()
	{
		Circle circle = new Circle(new XYpoint(1, 1), 5, 0, null);
		FilledCircle fcirc = new FilledCircle(new XYpoint(1, 1), 5, 0, null);

		Set<Pshape> cut = circle.intersectWith(fcirc);

		for (Pshape pshape : cut)
		{
			if (pshape instanceof Circle)
			{
				Circle circle2 = (Circle) pshape;
				assertFalse(circle2.intersectWith(new XYpoint(6, 1)).isEmpty());
			}
		}
	}

	@Test
	public void testIntersectFilledCircleTriangle()
	{
		FilledCircle fcirc = new FilledCircle(new XYpoint(0, 0), 10, 0, null);
		Triangle triangle = new Triangle(new XYpoint(-1, -1),
				new XYpoint(1, -1), new XYpoint(1, 1), 0, null);

		Set<Pshape> cut = fcirc.intersectWith(triangle);

		for (Pshape pshape : cut)
		{
			if (pshape instanceof Triangle)
			{
				Triangle triangle2 = (Triangle) pshape;
				assertFalse(
						triangle2.intersectWith(new XYpoint(0, 0)).isEmpty());
			}
		}

	}

	@Test
	public void testIntersectFilledCircleFilledCircle()
	{
		FilledCircle fcirc1 = new FilledCircle(new XYpoint(0, 0), 10, 0, null);
		FilledCircle fcirc2 = new FilledCircle(new XYpoint(5, 0), 5, 0, null);

		Set<Pshape> cut = fcirc1.intersectWith(fcirc2);

		for (Pshape pshape : cut)
		{
			if (pshape instanceof FilledCircle)
			{
				assertFalse(pshape.intersectWith(new XYpoint(6, 1)).isEmpty());

			}
		}
	}

}
