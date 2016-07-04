package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.PreservingMap;
import de.fabianmeier.seventeengon.util.NumericAngle;

public class Angle extends AtomicGeoObject
{

	private final XYvector direction1;
	private final XYpoint vertex;
	private final XYvector direction2;

	// private final Triangle reprTriangle;

	/**
	 * Generates a triangle
	 * 
	 * @param onRay1
	 *            first point
	 * @param vertex
	 *            second point
	 * @param onRay2
	 *            third point
	 */
	public Angle(XYpoint onRay1, XYpoint vertex, XYpoint onRay2)
	{
		this.vertex = vertex;

		direction1 = new XYvector(vertex, onRay1).normed();
		direction2 = new XYvector(vertex, onRay2).normed();

		// reprTriangle = new
		// Triangle(direction1.multiplyBy(10000).shift(vertex),
		// vertex, direction2.multiplyBy(10000).shift(vertex));
	}

	/**
	 * Generates a triangle
	 * 
	 * @param vertex
	 *            second point
	 * @param firstAngle
	 *            first Angle
	 * @param secondAngle
	 *            second Angle
	 */
	public Angle(XYpoint vertex, NumericAngle firstAngle,
			NumericAngle secondAngle)
	{
		this.vertex = vertex;

		direction1 = new XYvector(1, firstAngle);
		direction2 = new XYvector(1, secondAngle);

		// reprTriangle = new
		// Triangle(direction1.multiplyBy(10000).shift(vertex),
		// vertex, direction2.multiplyBy(10000).shift(vertex));
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
		Angle other = (Angle) obj;
		if (direction1 == null)
		{
			if (other.direction1 != null)
				return false;
		}
		else if (!direction1.equals(other.direction1))
			return false;
		if (direction2 == null)
		{
			if (other.direction2 != null)
				return false;
		}
		else if (!direction2.equals(other.direction2))
			return false;
		if (vertex == null)
		{
			if (other.vertex != null)
				return false;
		}
		else if (!vertex.equals(other.vertex))
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

		XYvector vector1 = direction1.multiplyBy(factor1);
		XYvector vector2 = direction2.multiplyBy(factor2);

		return vector1.shift(vector2.shift(vertex));

	}

	public XYvector getDirection1()
	{
		return direction1;
	}

	public XYpoint getVertex()
	{
		return vertex;
	}

	public XYvector getDirection2()
	{
		return direction2;
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
		result = prime * result
				+ ((direction1 == null) ? 0 : direction1.hashCode());
		result = prime * result
				+ ((direction2 == null) ? 0 : direction2.hashCode());
		result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
		return result;
	}

	@Override
	public GeoObject intersectWith(GeoObject geoObject)
	{

		if (geoObject.containsPoint(vertex))
			return this;
		else
			return CompositeGeoObject.getEmptyObject();

		// if (geoObject instanceof XYpoint)
		// {
		// return IntersectionManager.intersect(reprTriangle,
		// (XYpoint) geoObject);
		// }
		// if (geoObject instanceof Line)
		// {
		// return IntersectionManager.intersect(reprTriangle,
		// (Line) geoObject);
		// }
		// if (geoObject instanceof Arc)
		// {
		// return IntersectionManager.intersect(reprTriangle, (Arc) geoObject);
		// }
		// if (geoObject instanceof Triangle)
		// {
		// return IntersectionManager.intersect(reprTriangle,
		// (Triangle) geoObject);
		// }
		// if (geoObject instanceof Circle)
		// {
		// return IntersectionManager.intersect(reprTriangle,
		// (Circle) geoObject);
		// }
		//
		// return geoObject.intersectWith(this);

	}

	@Override
	public String toString()
	{
		return "<" + direction1.multiplyBy(100).shift(vertex) + vertex
				+ direction2.multiplyBy(100).shift(vertex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getBoundary()
	 */
	@Override
	public GeoObject getBoundary()
	{
		return vertex;

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
	public Angle preservingMap(PreservingMap preMap)
	{
		XYpoint vertexNew = vertex.preservingMap(preMap);
		XYpoint dir1Point = direction1.shift(vertex).preservingMap(preMap);
		XYpoint dir2Point = direction2.shift(vertex).preservingMap(preMap);

		return new Angle(dir1Point, vertexNew, dir2Point);
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

		back.add(this);

		return back;
	}

}