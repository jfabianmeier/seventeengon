package de.fabianmeier.seventeengon.shapes;

import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.Angle;

public class Circle extends PshapeImpl
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

	public Circle(XYpoint centre, double radius, int visibility, String label)
	{
		super(visibility, label);
		this.centre = centre;
		this.radius = radius;
		this.startAngle = new Angle(0);
		this.endAngle = new Angle(2 * Math.PI);
	}

	public Circle(XYpoint centre, double radius, double startAngle,
			double endAngle, int visibility, String label)
	{
		super(visibility, label);
		this.centre = centre;
		this.radius = radius;
		this.startAngle = new Angle(startAngle);
		this.endAngle = new Angle(endAngle);

	}

	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + getPseudoHash());
		return getPoint(rand.nextDouble());
	}

	private XYpoint getPoint(double nextDouble)
	{
		Angle angle = startAngle.addtoAngle(
				Angle.AngleDifference(startAngle, endAngle) * nextDouble);

		XYvector radVector = (new XYvector(angle.cos(), angle.sin()))
				.multiplyBy(radius);

		return radVector.shift(centre);
	}

	public int getDimension()
	{
		return 1;
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

	public int getPseudoHash()
	{
		double didu = 1000 * startAngle.asDouble() + 100 * endAngle.asDouble()
				+ 234 * radius;

		int diduInt = (int) Math.round(didu);

		return centre.getPseudoHash() + diduInt;
	}

	public boolean containsAngle(Angle angle)
	{
		return angle.inBetween(startAngle, endAngle);

	}

	public Angle getAngle(XYpoint point)
	{
		XYvector vector = new XYvector(centre, point);
		return vector.getAngle();
	}

	@Override
	public String toString()
	{
		String localLabel = getLabel();
		if (localLabel == null)
			localLabel = "Circle";

		return localLabel + ": " + centre.toString() + " >> "
				+ showValue(radius);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centre == null) ? 0 : centre.hashCode());
		result = prime * result + (int) DMan
				.DoubleHash(Angle.AngleDifference(startAngle, endAngle));
		long temp;
		temp = DMan.DoubleHash(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));

		return result;
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
		Circle other = (Circle) obj;
		if (centre == null)
		{
			if (other.centre != null)
				return false;
		} else if (!centre.equals(other.centre))
			return false;
		if (DMan.DoubleHash(radius) != DMan.DoubleHash(other.radius))
			return false;

		if (startAngle.equals(endAngle)
				&& other.getStartAngle().equals(other.getEndAngle()))
			return true;
		if (endAngle == null)
		{
			if (other.endAngle != null)
				return false;
		} else if (!endAngle.equals(other.endAngle))
			return false;

		if (startAngle == null)
		{
			if (other.startAngle != null)
				return false;
		} else if (!startAngle.equals(other.startAngle))
			return false;
		return true;
	}

}