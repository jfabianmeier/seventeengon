package de.fabianmeier.seventeengon.shapes;

import java.util.Set;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;

public class XYpoint extends PshapeImpl
{

	private final double x;
	private final double y;

	private boolean farOff;

	private double distanceToZero;

	public XYpoint(double x, double y, int visibility, String label)
	{
		super(visibility, label);
		this.x = x;
		this.y = y;

		distanceToZero = Math.sqrt(x * x + y * y);
		farOff = DMan.isInfinite(distanceToZero);
		if (farOff)
		{
			x = x / distanceToZero;
			y = y / distanceToZero;
		}

	}

	@Override
	public int hashCode()
	{

		int prime = 31;

		if (farOff)
			prime = 37;
		int result = 1;
		long temp;
		temp = DMan.DoubleHash(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = DMan.DoubleHash(y);
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
		XYpoint other = (XYpoint) obj;

		if (farOff != other.farOff)
			return false;

		if (DMan.DoubleHash(x) != DMan.DoubleHash(other.x))
			return false;
		if (DMan.DoubleHash(y) != DMan.DoubleHash(other.y))
			return false;
		return true;
	}

	public XYpoint(double x, double y, int visibility)
	{
		super(visibility, null);
		this.x = x;
		this.y = y;
	}

	public XYpoint(double x, double y)
	{
		super(0, null);
		this.x = x;
		this.y = y;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public int getDimension()
	{
		return 0;
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
		return this;
	}

	@Override
	public String toString()
	{
		String localLabel = getLabel();

		String farOffS = farOff ? "" : "INF";

		if (localLabel == null)
			localLabel = "P";
		return localLabel + "(" + showValue(x) + ", " + showValue(y) + ")"
				+ farOffS;
	}

	public int getPseudoHash()
	{
		return (int) Math.round(1000 * x + 10000 * y);
	}

}
