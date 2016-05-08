package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.GeoVisible;

/**
 * This class describes a point in the plane with x and y coordinate. Points
 * with large distance to zero are considered to lie on an infinite circle. They
 * are normed and marked as "far off". Equality is implemented accordingly.
 * 
 * @author JFM
 *
 */
public class XYpoint extends PshapeImpl
{

	private double distanceToZero;
	private boolean farOff;

	private final double x;

	private final double y;

	/**
	 * A point (points far away will be considered as lying on the
	 * "infinite circle"
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public XYpoint(double x, double y)
	{

		this.x = x;
		this.y = y;

		distanceToZero = Math.sqrt(x * x + y * y);
		farOff = DMan.isInfinite(distanceToZero);
		if (farOff)
		{
			x = x / distanceToZero;
			y = y / distanceToZero;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoObject#draw(de.fabianmeier.
	 * seventeengon.geoobjects.GeoCanvas, java.lang.String)
	 */
	@Override
	public void draw(GeoCanvas canvas, String label, GeoVisible visi)
	{
		canvas.drawPoint(this, label, visi);

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
		XYpoint other = (XYpoint) obj;

		if (farOff != other.farOff)
			return false;

		if (DMan.doubleHash(x) != DMan.doubleHash(other.x))
			return false;
		if (DMan.doubleHash(y) != DMan.doubleHash(other.y))
			return false;
		return true;
	}

	@Override
	public int getDimension()
	{
		return 0;
	}

	@Override
	public int getPseudoHash()
	{
		return (int) Math.round(1000 * x + 10000 * y);
	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		return this;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	@Override
	public int hashCode()
	{

		int prime = 31;

		if (farOff)
			prime = 37;
		int result = 1;
		long temp;
		temp = DMan.doubleHash(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = DMan.doubleHash(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public Set<Pshape> intersectWith(Pshape pshape)
	{
		if (pshape instanceof XYpoint)
		{
			return IntersectionManager.intersect(this, (XYpoint) pshape);
		}
		if (pshape instanceof Line)
		{
			return IntersectionManager.intersect(this, (Line) pshape);
		}
		if (pshape instanceof Arc)
		{
			return IntersectionManager.intersect(this, (Arc) pshape);
		}
		if (pshape instanceof Triangle)
		{
			return IntersectionManager.intersect(this, (Triangle) pshape);
		}
		if (pshape instanceof Circle)
		{
			return IntersectionManager.intersect(this, (Circle) pshape);
		}

		return null;

	}

	@Override
	public void paint(Graphics2D g2d)
	{
		// setColourAndStroke(g2d);

		g2d.fill(new Ellipse2D.Double(x - 1, y - 1, 2, 2));
	}

	@Override
	public String toString()
	{
		String localLabel = "Point";

		String farOffS = farOff ? "INF" : "";

		return localLabel + "(" + showValue(x) + ", " + showValue(y) + ")"
				+ farOffS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getBoundary()
	 */
	@Override
	public GeoObject getBoundary()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getFilledObject()
	 */
	@Override
	public GeoObject getFilledObject()
	{
		return this;
	}

}
