package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.util.GeoVisible;

public class Triangle extends PshapeImpl
{

	private final XYpoint pointA;
	private final XYpoint pointB;
	private final XYpoint pointC;

	/**
	 * Generates a triangle
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * @param c
	 *            third point
	 */
	public Triangle(XYpoint a, XYpoint b, XYpoint c)
	{
		pointA = a;
		pointB = b;
		pointC = c;
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
		canvas.drawLine(pointA, pointB, label, visi);
		canvas.drawLine(pointB, pointC, null, visi);
		canvas.drawLine(pointC, pointA, null, visi);

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
		}
		else if (!pointA.equals(other.pointA))
			return false;
		if (pointB == null)
		{
			if (other.pointB != null)
				return false;
		}
		else if (!pointB.equals(other.pointB))
			return false;
		if (pointC == null)
		{
			if (other.pointC != null)
				return false;
		}
		else if (!pointC.equals(other.pointC))
			return false;
		return true;
	}

	@Override
	public int getDimension()
	{
		return 2;
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

	public XYpoint getPointA()
	{
		return pointA;
	}

	public XYpoint getPointB()
	{
		return pointB;
	}

	public XYpoint getPointC()
	{
		return pointC;
	}

	@Override
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

	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		Random rand = new Random(sampleNumber + getPseudoHash());
		return getPoint(rand.nextDouble(), rand.nextDouble());
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

		Path2D path = new Path2D.Double();

		path.moveTo(pointA.getX(), pointA.getY());
		path.lineTo(pointB.getX(), pointB.getY());
		path.lineTo(pointC.getX(), pointC.getY());

		path.closePath();

		g2d.draw(path);
	}

	@Override
	public String toString()
	{
		String localLabel = "ABC";

		return localLabel + "(" + pointA.toString() + "; " + pointB.toString()
				+ "; " + pointC.toString() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getBoundary()
	 */
	@Override
	public GeoObject getBoundary()
	{
		List<Pshape> lines = new ArrayList<Pshape>();
		lines.add(new Line(getPointA(), getPointB(), 0, 1));
		lines.add(new Line(getPointB(), getPointC(), 0, 1));
		lines.add(new Line(getPointC(), getPointA(), 0, 1));

		return CompositeGeoObject.getCompositeGeoObject(lines);

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