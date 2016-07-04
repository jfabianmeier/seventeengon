package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.PreservingMap;
import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * This class describes a point in the plane with x and y coordinate. Points
 * with large distance to zero are considered to lie on an infinite circle. They
 * are normed and marked as "far off". Equality is implemented accordingly.
 * 
 * @author JFM
 *
 */
public class XYpoint extends AtomicGeoObject
{

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
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * de.fabianmeier.seventeengon.geoobjects.GeoObject#draw(de.fabianmeier.
	// * seventeengon.geoobjects.GeoCanvas, java.lang.String)
	// */
	// @Override
	// public void draw(GeoCanvas canvas, GeoVisible visi)
	// {
	// canvas.drawPoint(this, visi);
	//
	// }

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

		int result = 1;
		long temp;
		temp = DMan.doubleHash(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = DMan.doubleHash(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public GeoObject intersectWith(GeoObject geoObject)
	{
		if (geoObject instanceof XYpoint)
		{
			return IntersectionManager.intersect(this, (XYpoint) geoObject);
		}
		if (geoObject instanceof Line)
		{
			return IntersectionManager.intersect(this, (Line) geoObject);
		}
		if (geoObject instanceof Arc)
		{
			return IntersectionManager.intersect(this, (Arc) geoObject);
		}
		if (geoObject instanceof Triangle)
		{
			return IntersectionManager.intersect(this, (Triangle) geoObject);
		}
		if (geoObject instanceof Circle)
		{
			return IntersectionManager.intersect(this, (Circle) geoObject);
		}

		return geoObject.intersectWith(this);

	}

	@Override
	public String toString()
	{
		String localLabel = "Point";

		return localLabel + "(" + showValue(x) + ", " + showValue(y) + ")";
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#getZeroDimensionalPart()
	 */
	@Override
	public Set<XYpoint> getZeroDimensionalPart()
	{
		Set<XYpoint> local = new HashSet<XYpoint>();

		local.add(this);

		return local;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#preservingMap(de.fabianmeier
	 * .seventeengon.geoobjects.PreservingMap)
	 */
	@Override
	public XYpoint preservingMap(PreservingMap preMap)
	{
		return preMap.mapPoint(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getNameDrawingAngles()
	 */
	@Override
	public List<Angle> getNameDrawingAngles()
	{
		List<Angle> back = new ArrayList<Angle>();
		back.add(new Angle(this, new NumericAngle(0),
				new NumericAngle(Math.PI * 0.5)));
		back.add(new Angle(this, new NumericAngle(Math.PI * 0.5),
				new NumericAngle(1 * Math.PI)));
		back.add(new Angle(this, new NumericAngle(1 * Math.PI),
				new NumericAngle(1.5 * Math.PI)));
		back.add(new Angle(this, new NumericAngle(1.5 * Math.PI),
				new NumericAngle(2.0 * Math.PI)));
		back.add(new Angle(this, new NumericAngle(Math.PI * 0.25),
				new NumericAngle(Math.PI * 0.75)));
		back.add(new Angle(this, new NumericAngle(Math.PI * 0.75),
				new NumericAngle(1.25 * Math.PI)));
		back.add(new Angle(this, new NumericAngle(1.25 * Math.PI),
				new NumericAngle(1.75 * Math.PI)));
		back.add(new Angle(this, new NumericAngle(1.75 * Math.PI),
				new NumericAngle(0.25 * Math.PI)));
		return back;
	}

}
