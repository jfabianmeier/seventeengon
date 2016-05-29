package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.NumericAngle;

public class Arc extends AtomicGeoObject
{

	private final XYpoint centre;
	private final NumericAngle endAngle;

	private final double radius;
	private final NumericAngle startAngle;

	/**
	 * 
	 * @param centre
	 *            Centre of the Circle
	 * @param radius
	 *            Radius of the Circle
	 * @param startAngle
	 *            start Angle
	 * @param endAngle
	 *            End Angle
	 */
	public Arc(XYpoint centre, double radius, NumericAngle startAngle,
			NumericAngle endAngle)
	{
		this.centre = centre;
		this.radius = radius;
		this.startAngle = startAngle;
		this.endAngle = endAngle;

	}

	/**
	 * Complete arc (circle)
	 * 
	 * @param centre
	 *            Centre
	 * @param radius
	 *            Radius
	 */
	public Arc(XYpoint centre, double radius)
	{
		this(centre, radius, new NumericAngle(0),
				new NumericAngle(2 * Math.PI));
	}

	/**
	 * 
	 * @param angle
	 *            Angle
	 * @return if the angle is part of the arc.
	 */
	public boolean containsAngle(NumericAngle angle)
	{
		return angle.inBetween(startAngle, endAngle);

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
	// canvas.drawArc(getCentre(), getStartAngle(), getEndAngle(), radius,
	// visi);
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
		Arc other = (Arc) obj;
		if (centre == null)
		{
			if (other.centre != null)
				return false;
		}
		else if (!centre.equals(other.centre))
			return false;
		if (DMan.doubleHash(radius) != DMan.doubleHash(other.radius))
			return false;

		if (startAngle.equals(endAngle)
				&& other.getStartAngle().equals(other.getEndAngle()))
			return true;
		if (endAngle == null)
		{
			if (other.endAngle != null)
				return false;
		}
		else if (!endAngle.equals(other.endAngle))
			return false;

		if (startAngle == null)
		{
			if (other.startAngle != null)
				return false;
		}
		else if (!startAngle.equals(other.startAngle))
			return false;
		return true;
	}

	public NumericAngle getAngle(XYpoint point)
	{
		XYvector vector = new XYvector(centre, point);
		return vector.getAngle();
	}

	public XYpoint getAnglePoint(NumericAngle angle)
	{
		XYvector vector = new XYvector(radius, angle);
		return vector.shift(centre);
	}

	public XYpoint getCentre()
	{
		return centre;
	}

	@Override
	public int getDimension()
	{
		return 1;
	}

	public NumericAngle getEndAngle()
	{
		return endAngle;
	}

	public XYpoint getEndPoint()
	{
		return getAnglePoint(endAngle);
	}

	private XYpoint getPoint(double nextDouble)
	{
		NumericAngle angle = startAngle
				.addtoAngle(NumericAngle.angleDifference(startAngle, endAngle)
						* nextDouble);

		XYvector radVector = (new XYvector(angle.cos(), angle.sin()))
				.multiplyBy(radius);

		return radVector.shift(centre);
	}

	public double getRadius()
	{
		return radius;
	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + hashCode());
		return getPoint(rand.nextDouble());
	}

	public NumericAngle getStartAngle()
	{
		return startAngle;
	}

	public XYpoint getStartPoint()
	{
		return getAnglePoint(startAngle);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centre == null) ? 0 : centre.hashCode());
		result = prime * result + (int) DMan
				.doubleHash(NumericAngle.angleDifference(startAngle, endAngle));
		long temp;
		temp = DMan.doubleHash(radius);
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
	//
	// @Override
	// public void paint(Graphics2D g2d)
	// {
	// // setColourAndStroke(g2d);
	//
	// if (!startAngle.equals(endAngle))
	// {
	//
	// g2d.draw(new Arc2D.Double(centre.getX() - radius,
	// centre.getY() - radius, 2 * radius, 2 * radius,
	// -startAngle.asDouble() * 180 / Math.PI,
	// -NumericAngle.angleDifference(startAngle, endAngle) * 180
	// / Math.PI,
	// Arc2D.OPEN));
	// }
	// else
	// {
	//
	// g2d.draw(new Ellipse2D.Double(centre.getX() - radius,
	// centre.getY() - radius, 2 * radius, 2 * radius));
	// }
	//
	// }

	@Override
	public String toString()
	{
		String localLabel = "Arc";

		String back = localLabel + ": " + centre.toString() + " >> "
				+ showValue(radius);

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
		return new Circle(getCentre(), getRadius(), getStartAngle(),
				getEndAngle());
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
	public Arc affineMap(XYvector shiftVector, double scale)
	{
		XYpoint centreNew = centre.affineMap(shiftVector, scale);
		double radiusNew = radius * scale;

		return new Arc(centreNew, radiusNew, startAngle, endAngle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getNameDrawingAngles()
	 */
	@Override
	public List<Angle> getNameDrawingAngles()
	{
		double angleDifference = NumericAngle.angleDifference(startAngle,
				endAngle);

		double stepSize = angleDifference / 8;

		List<NumericAngle> drawNumAngles = new ArrayList<NumericAngle>();

		for (int i = 0; i < 8; i++)
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
			NumericAngle startAngle = drawNumAngles.get(i)
					.addtoAngle(-Math.PI / 2);
			NumericAngle endAngle = drawNumAngles.get(i)
					.addtoAngle(Math.PI / 2);

			back.add(new Angle(xy, startAngle, endAngle));
		}

		return back;

	}

}
