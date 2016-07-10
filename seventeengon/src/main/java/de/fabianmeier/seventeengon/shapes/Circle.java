package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.PreservingMap;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * A filled circle (or the convex hull of an arc)
 * 
 * @author jfabi
 *
 */
public class Circle extends AtomicGeoObject
{

	private final XYpoint centre;
	private final NumericAngle endAngle;

	private final double radius;
	private final NumericAngle startAngle;

	/**
	 * Creates a full circle part (convex hull of the respective arc).
	 * 
	 * @param centre
	 *            Centre
	 * @param radius
	 *            Radius
	 * @param startAngle
	 *            start Angle
	 * @param endAngle
	 *            end Angle
	 */
	public Circle(XYpoint centre, double radius, NumericAngle startAngle, NumericAngle endAngle)
	{
		this.centre = centre;
		this.radius = radius;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}

	/**
	 * Creates a full circle.
	 * 
	 * @param centre
	 *            Centre
	 * @param radius
	 *            Radius
	 * 
	 */
	public Circle(XYpoint centre, double radius)
	{
		this(centre, radius, new NumericAngle(0), new NumericAngle(2 * Math.PI));
	}

	/**
	 * 
	 * @param angle
	 *            an absolute angle
	 * @return the point on the circle corresponding to that angle
	 */
	public XYpoint getAnglePoint(NumericAngle angle)
	{
		XYvector vector = new XYvector(radius, angle);
		return vector.shift(centre);
	}

	/**
	 * 
	 * @return the centre of the circle
	 */
	public XYpoint getCentre()
	{
		return centre;
	}

	@Override
	public int getDimension()
	{
		return 2;
	}

	/**
	 * 
	 * @return the end angle of the arc (equals startAngle for a full circle)
	 */
	public NumericAngle getEndAngle()
	{
		return endAngle;
	}

	/**
	 * 
	 * @return the end point of the arc (for full circle, it equals the start
	 *         point)
	 */
	public XYpoint getEndPoint()
	{
		return getAnglePoint(endAngle);
	}

	/**
	 * 
	 * @param rand
	 *            Random object
	 * @return a random point from the circle
	 */
	private XYpoint getPoint(Random rand)
	{
		while (true)
		{
			NumericAngle angle = startAngle
					.addtoAngle(NumericAngle.angleDifference(startAngle, endAngle) * rand.nextDouble());

			XYvector radVector = (new XYvector(angle.cos(), angle.sin())).multiplyBy(radius * rand.nextDouble());

			XYpoint candidate = radVector.shift(centre);
			if (!this.intersectWith(candidate).isEmpty())
				return candidate;
		}

	}

	/**
	 * 
	 * @return the radius
	 */
	public double getRadius()
	{
		return radius;
	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + hashCode());
		return getPoint(rand);
	}

	/**
	 * 
	 * @return the absolute start angle (for full circle equal to end angle)
	 */
	public NumericAngle getStartAngle()
	{
		return startAngle;
	}

	/**
	 * 
	 * @return the start point (for full circle equal to end point)
	 */
	public XYpoint getStartPoint()
	{
		return getAnglePoint(startAngle);
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
		String localLabel = "Fcirc";

		String back = localLabel + ": " + centre.toString() + " >> " + showValue(radius);

		if (!startAngle.equals(endAngle))
		{
			back += " from " + startAngle + " to " + endAngle;
		}

		return back;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getBoundary()
	 */
	@Override
	public GeoObject getBoundary()
	{
		return new Arc(centre, radius, startAngle, endAngle);

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
	public Circle preservingMap(PreservingMap preMap)
	{
		XYpoint centreNew = centre.preservingMap(preMap);
		double radiusNew = radius * preMap.getScale();

		return new Circle(centreNew, radiusNew, startAngle, endAngle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getNameDrawingAngles()
	 */
	@Override
	public List<Angle> getNameDrawingAngles()
	{
		double angleDifference = NumericAngle.angleDifference(startAngle, endAngle);

		double stepSize = angleDifference / 8;

		List<NumericAngle> drawNumAngles = new ArrayList<NumericAngle>();

		List<Integer> intList = Arrays.asList(4, 3, 5, 2, 6, 1, 7);

		for (int i : intList)
		{
			drawNumAngles.add(startAngle.addtoAngle(i * stepSize));
		}

		List<XYpoint> namingPoints = new ArrayList<XYpoint>();

		for (NumericAngle num : drawNumAngles)
		{
			namingPoints.add((new XYvector(radius, num).shift(centre)));
		}

		List<Angle> back = new ArrayList<Angle>();

		for (int i = 0; i < 8; i++)
		{
			XYpoint xy = namingPoints.get(i);
			NumericAngle startAngle = drawNumAngles.get(i).addtoAngle(-Math.PI / 2);
			NumericAngle endAngle = drawNumAngles.get(i).addtoAngle(Math.PI / 2);

			back.add(new Angle(xy, startAngle, endAngle));
		}

		return back;

	}

}