package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.Angle;

public class FilledCircle extends PshapeImpl
{

	private final XYpoint centre;
	private final double radius;

	private final Angle startAngle;
	private final Angle endAngle;

	public XYpoint getCentre()
	{
		return centre;
	}

	public double getRadius()
	{
		return radius;
	}

	public Angle getStartAngle()
	{
		return startAngle;
	}

	public Angle getEndAngle()
	{
		return endAngle;
	}

	public XYpoint getAnglePoint(Angle angle)
	{
		XYvector vector = new XYvector(radius, angle);
		return vector.shift(centre);
	}

	public XYpoint getStartPoint()
	{
		return getAnglePoint(startAngle);
	}

	public XYpoint getEndPoint()
	{
		return getAnglePoint(endAngle);
	}

	public FilledCircle(XYpoint centre, double radius, int visibility,
			String label)
	{
		super(visibility, label);
		this.centre = centre;
		this.radius = radius;
		this.startAngle = new Angle(0);
		this.endAngle = new Angle(2 * Math.PI);
	}

	public FilledCircle(XYpoint centre, double radius, double startAngle,
			double endAngle, int visibility, String label)
	{
		super(visibility, label);
		this.centre = centre;
		this.radius = radius;
		this.startAngle = new Angle(startAngle);
		this.endAngle = new Angle(endAngle);
	}

	@Override
	public int getDimension()
	{
		return 2;
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
		if (pshape instanceof Circle)
		{
			return IntersectionManager.intersect(this, (Circle) pshape);
		}
		if (pshape instanceof Triangle)
		{
			return IntersectionManager.intersect(this, (Triangle) pshape);
		}
		if (pshape instanceof FilledCircle)
		{
			return IntersectionManager.intersect(this, (FilledCircle) pshape);
		}

		return null;

	}

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + getPseudoHash());
		return getPoint(rand);
	}

	private XYpoint getPoint(Random rand)
	{
		while (true)
		{
			Angle angle = startAngle
					.addtoAngle(Angle.AngleDifference(startAngle, endAngle)
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

	@Override
	public String toString()
	{
		String localLabel = getLabel();
		if (localLabel == null)
			localLabel = "Fcirc";

		String back = localLabel + ": " + centre.toString() + " >> "
				+ showValue(radius);

		if (!startAngle.equals(endAngle))
		{
			back += " from " + startAngle + " to " + endAngle;
		}

		return back;

	}

	@Override
	public void paint(Graphics2D g2d)
	{
		setColourAndStroke(g2d);

		if (!startAngle.equals(endAngle))
		{

			g2d.draw(new Arc2D.Double(centre.getX() - radius,
					centre.getY() - radius, 2 * radius, 2 * radius,
					startAngle.asDouble() * 180 / Math.PI,
					Angle.AngleDifference(startAngle, endAngle) * 180 / Math.PI,
					Arc2D.CHORD));
		} else
		{

			g2d.draw(new Ellipse2D.Double(centre.getX() - radius,
					centre.getY() - radius, 2 * radius, 2 * radius));
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
	public void draw(GeoCanvas canvas, String label)
	{
		canvas.drawArc(getCentre(), getStartAngle(), getEndAngle(),
				getVisibility(), label);

	}

}