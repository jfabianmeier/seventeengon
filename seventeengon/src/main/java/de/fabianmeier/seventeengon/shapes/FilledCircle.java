package de.fabianmeier.seventeengon.shapes;

import java.util.Random;
import java.util.Set;

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

	public int getDimension()
	{
		return 2;
	}

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

		return localLabel + ": " + centre.toString() + " >> "
				+ showValue(radius);
	}

}