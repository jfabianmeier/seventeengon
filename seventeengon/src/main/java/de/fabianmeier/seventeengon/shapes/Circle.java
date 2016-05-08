package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.Angle;
import de.fabianmeier.seventeengon.util.GeoVisible;

public class Circle extends PshapeImpl
{

	private final XYpoint centre;
	private final Angle endAngle;

	private final double radius;
	private final Angle startAngle;

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
	public Circle(XYpoint centre, double radius, Angle startAngle,
			Angle endAngle)
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
		this(centre, radius, new Angle(0), new Angle(2 * Math.PI));
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
		return 2;
	}

	public Angle getEndAngle()
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
			Angle angle = startAngle
					.addtoAngle(Angle.angleDifference(startAngle, endAngle)
							* rand.nextDouble());

			XYvector radVector = (new XYvector(angle.cos(), angle.sin()))
					.multiplyBy(radius * rand.nextDouble());

			XYpoint candidate = radVector.shift(centre);
			if (!this.intersectWith(candidate).isEmpty())
				return candidate;
		}

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
		return getPoint(rand);
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
					startAngle.asDouble() * 180 / Math.PI,
					Angle.angleDifference(startAngle, endAngle) * 180 / Math.PI,
					Arc2D.CHORD));
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

}