package de.fabianmeier.seventeengon.shapes;

import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.intersection.IntersectionManager;

public class Triangle extends PshapeImpl
{

	private final XYpoint pointA;
	private final XYpoint pointB;
	private final XYpoint pointC;

	public XYpoint getPointA()
	{
		return pointA;
	}

	public XYpoint getPointB()
	{
		return pointB;
	}

	public int getPseudoHash()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pointA == null) ? 0 : pointA.getPseudoHash());
		result = prime * result
				+ ((pointB == null) ? 0 : pointB.getPseudoHash());
		result = prime * result
				+ ((pointC == null) ? 0 : pointC.getPseudoHash());
		return result;
	}

	public XYpoint getPointC()
	{
		return pointC;
	}

	public Triangle(XYpoint a, XYpoint b, XYpoint c, int visibility,
			String label)
	{
		super(visibility, label);
		pointA = a;
		pointB = b;
		pointC = c;
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
		return getPoint(rand.nextDouble(), rand.nextDouble());
	}

	private XYpoint getPoint(double factor1, double factor2)
	{
		XYvector vectorAB = new XYvector(pointA, pointB);
		XYvector vectorAC = new XYvector(pointA, pointC);

		if (factor1 + factor2 > 1)
		{
			factor1 = 1 - factor1;
			factor2 = 1 - factor2;
		}

		vectorAB = vectorAB.multiplyBy(factor1);
		vectorAC = vectorAC.multiplyBy(factor2);

		return vectorAC.shift(vectorAB.shift(pointA));

	}

	@Override
	public String toString()
	{
		String localLabel = getLabel();
		if (localLabel == null)
			localLabel = "ABC";

		return localLabel + "(" + pointA.toString() + "; " + pointB.toString()
				+ "; " + pointC.toString() + ")";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pointA == null) ? 0 : pointA.hashCode());
		result = prime * result + ((pointB == null) ? 0 : pointB.hashCode());
		result = prime * result + ((pointC == null) ? 0 : pointC.hashCode());
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
		Triangle other = (Triangle) obj;
		if (pointA == null)
		{
			if (other.pointA != null)
				return false;
		} else if (!pointA.equals(other.pointA))
			return false;
		if (pointB == null)
		{
			if (other.pointB != null)
				return false;
		} else if (!pointB.equals(other.pointB))
			return false;
		if (pointC == null)
		{
			if (other.pointC != null)
				return false;
		} else if (!pointC.equals(other.pointC))
			return false;
		return true;
	}

}