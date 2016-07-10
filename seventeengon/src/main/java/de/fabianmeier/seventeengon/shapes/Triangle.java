package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.PreservingMap;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;

public class Triangle extends AtomicGeoObject
{

	private final XYpoint pointA;
	private final XYpoint pointB;
	private final XYpoint pointC;

	/**
	 * Generates a triangle
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * @param c
	 *            third point
	 */
	public Triangle(XYpoint a, XYpoint b, XYpoint c)
	{
		pointA = a;
		pointB = b;
		pointC = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoObject#draw(de.fabianmeier.
	 * seventeengon.geoobjects.GeoCanvas, java.lang.String)
	 */
	// @Override
	// public void draw(GeoCanvas canvas, GeoVisible visi)
	// {
	// canvas.drawLine(pointA, pointB, visi);
	// canvas.drawLine(pointB, pointC, visi.hideName());
	// canvas.drawLine(pointC, pointA, visi.hideName());
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
		Triangle other = (Triangle) obj;
		if (pointA == null)
		{
			if (other.pointA != null)
				return false;
		}
		else if (!pointA.equals(other.pointA))
			return false;
		if (pointB == null)
		{
			if (other.pointB != null)
				return false;
		}
		else if (!pointB.equals(other.pointB))
			return false;
		if (pointC == null)
		{
			if (other.pointC != null)
				return false;
		}
		else if (!pointC.equals(other.pointC))
			return false;
		return true;
	}

	@Override
	public int getDimension()
	{
		return 2;
	}

	private XYpoint getPoint(double factor1, double factor2)
	{
	
		XYvector vectorAB = new XYvector(pointA, pointB);
		XYvector vectorAC = new XYvector(pointA, pointC);

		if (factor1 + factor2 > 1)
		{
			factor1 = 1 - factor1;
			factor2 = 1 - factor2;
		}

		vectorAB = vectorAB.multiplyBy(factor1);
		vectorAC = vectorAC.multiplyBy(factor2);

		return vectorAC.shift(vectorAB.shift(pointA));

	}

	public XYpoint getPointA()
	{
		return pointA;
	}

	public XYpoint getPointB()
	{
		return pointB;
	}

	public XYpoint getPointC()
	{
		return pointC;
	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + hashCode());
		return getPoint(rand.nextDouble(), rand.nextDouble());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = ((pointA == null) ? 0 : pointA.hashCode());
		result = prime * result + ((pointB == null) ? 0 : pointB.hashCode());
		result = prime * result + ((pointC == null) ? 0 : pointC.hashCode());
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
		String localLabel = "Triangle";

		return localLabel + "(" + pointA.toString() + "; " + pointB.toString()
				+ "; " + pointC.toString() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getBoundary()
	 */
	@Override
	public GeoObject getBoundary()
	{
		List<GeoObject> lines = new ArrayList<GeoObject>();
		lines.add(new Line(getPointA(), getPointB(), 0, 1));
		lines.add(new Line(getPointB(), getPointC(), 0, 1));
		lines.add(new Line(getPointC(), getPointA(), 0, 1));

		return new CompositeGeoObject(lines);

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
		return new HashSet<XYpoint>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#affineMap(de.fabianmeier.
	 * seventeengon.shapes.XYvector, double)
	 */
	@Override
	public Triangle preservingMap(PreservingMap preMap)
	{
		XYpoint aNew = pointA.preservingMap(preMap);
		XYpoint bNew = pointB.preservingMap(preMap);
		XYpoint cNew = pointC.preservingMap(preMap);

		return new Triangle(aNew, bNew, cNew);

	}

	// @Override
	// public Triangle rotate(XYpoint around, double rotationAngle)
	// {
	// XYpoint aNew = pointA.rotate(around, rotationAngle);
	// XYpoint bNew = pointB.rotate(around, rotationAngle);
	// XYpoint cNew = pointC.rotate(around, rotationAngle);
	//
	// return new Triangle(aNew, bNew, cNew);
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getNameDrawingAngles()
	 */
	@Override
	public List<Angle> getNameDrawingAngles()
	{
		List<Angle> back = new ArrayList<Angle>();
		back.addAll((new Line(pointA, pointB, 0, 1).getNameDrawingAngles()));
		back.addAll((new Line(pointB, pointC, 0, 1).getNameDrawingAngles()));
		back.addAll((new Line(pointC, pointA, 0, 1).getNameDrawingAngles()));

		return back;
	}

}