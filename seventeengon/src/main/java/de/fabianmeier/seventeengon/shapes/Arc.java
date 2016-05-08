package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.Angle;
import de.fabianmeier.seventeengon.util.GeoVisible;

public class Arc extends PshapeImpl
{

	private final XYpoint centre;
	private final Angle endAngle;

	private final double radius;
	private final Angle startAngle;

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
	public Arc(XYpoint centre, double radius, Angle startAngle, Angle endAngle)
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
		this(centre, radius, new Angle(0), new Angle(2 * Math.PI));
	}

	/**
	 * 
	 * @param angle
	 *            Angle
	 * @return if the angle is part of the arc.
	 */
	public boolean containsAngle(Angle angle)
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
	@Override
	public void draw(GeoCanvas canvas, String label, GeoVisible visi)
	{
		canvas.drawArc(getCentre(), getStartAngle(), getEndAngle(), label,
				visi);

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

	public Angle getAngle(XYpoint point)
	{
		XYvector vector = new XYvector(centre, point);
		return vector.getAngle();
	}

	public XYpoint getAnglePoint(Angle angle)
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

	public Angle getEndAngle()
	{
		return endAngle;
	}

	public XYpoint getEndPoint()
	{
		return getAnglePoint(endAngle);
	}

	private XYpoint getPoint(double nextDouble)
	{
		Angle angle = startAngle.addtoAngle(
				Angle.angleDifference(startAngle, endAngle) * nextDouble);

		XYvector radVector = (new XYvector(angle.cos(), angle.sin()))
				.multiplyBy(radius);

		return radVector.shift(centre);
	}

	@Override
	public int getPseudoHash()
	{
		double didu = 1000 * startAngle.asDouble() + 100 * endAngle.asDouble()
				+ 234 * radius;

		int diduInt = (int) Math.round(didu);

		return centre.getPseudoHash() + diduInt;
	}

	public double getRadius()
	{
		return radius;
	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + getPseudoHash());
		return getPoint(rand.nextDouble());
	}

	public Angle getStartAngle()
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
				.doubleHash(Angle.angleDifference(startAngle, endAngle));
		long temp;
		temp = DMan.doubleHash(radius);
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

		if (!startAngle.equals(endAngle))
		{

			g2d.draw(new Arc2D.Double(centre.getX() - radius,
					centre.getY() - radius, 2 * radius, 2 * radius,
					-startAngle.asDouble() * 180 / Math.PI,
					-Angle.angleDifference(startAngle, endAngle) * 180
							/ Math.PI,
					Arc2D.OPEN));
		}
		else
		{

			g2d.draw(new Ellipse2D.Double(centre.getX() - radius,
					centre.getY() - radius, 2 * radius, 2 * radius));
		}

	}

	@Override
	public String toString()
	{
		String localLabel = "Circle";

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

}
