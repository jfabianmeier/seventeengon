package de.fabianmeier.seventeengon.shapes;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.GeoVisible;
import de.fabianmeier.seventeengon.util.NumericAngle;

public class Circle extends AtomicGeoObject
{

	private final XYpoint centre;
	private final NumericAngle endAngle;

	private final double radius;
	private final NumericAngle startAngle;

	/**
	 * Creates a full circle part (convex hull of the respective arc)
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
	public Circle(XYpoint centre, double radius, NumericAngle startAngle,
			NumericAngle endAngle)
	{
		this.centre = centre;
		this.radius = radius;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}

	/**
	 * Creates a full circle
	 * 
	 * @param centre
	 *            Centre
	 * @param radius
	 *            Radius
	 * 
	 */
	public Circle(XYpoint centre, double radius)
	{
		this(centre, radius, new NumericAngle(0),
				new NumericAngle(2 * Math.PI));
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
		canvas.drawArc(getCentre(), getStartAngle(), getEndAngle(), label,
				visi);

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
		return 2;
	}

	public NumericAngle getEndAngle()
	{
		return endAngle;
	}

	public XYpoint getEndPoint()
	{
		return getAnglePoint(endAngle);
	}

	private XYpoint getPoint(Random rand)
	{
		while (true)
		{
			NumericAngle angle = startAngle.addtoAngle(
					NumericAngle.angleDifference(startAngle, endAngle)
							* rand.nextDouble());

			XYvector radVector = (new XYvector(angle.cos(), angle.sin()))
					.multiplyBy(radius * rand.nextDouble());

			XYpoint candidate = radVector.shift(centre);
			if (!this.intersectWith(candidate).isEmpty())
				return candidate;
		}

	}

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

	public NumericAngle getStartAngle()
	{
		return startAngle;
	}

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
	// startAngle.asDouble() * 180 / Math.PI,
	// NumericAngle.angleDifference(startAngle, endAngle) * 180 / Math.PI,
	// Arc2D.CHORD));
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
		String localLabel = "Fcirc";

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

}