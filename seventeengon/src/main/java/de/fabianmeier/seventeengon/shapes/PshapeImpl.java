package de.fabianmeier.seventeengon.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.CompositeGeoObject;
import de.fabianmeier.seventeengon.geoobjects.GeoObject;

public abstract class PshapeImpl implements Pshape
{

	private int visibility;
	private String label;

	public PshapeImpl(int visibility, String label)
	{
		this.visibility = visibility;
		this.label = label;
	}

	public Set<Pshape> getPrimitiveShapes()
	{
		Set<Pshape> pShapeSet = new HashSet<Pshape>();
		pShapeSet.add(this);
		return pShapeSet;
	}

	@Override
	public int getVisibility()
	{
		return visibility;
	}

	protected void setColourAndStroke(Graphics2D g2d)
	{
		if (visibility == 0)
			g2d.setStroke(new BasicStroke(0.5f));
		g2d.setPaint(Color.lightGray);

		if (visibility == 1)
			g2d.setStroke(new BasicStroke(0.7f));
		g2d.setPaint(Color.black);

		if (visibility == 2)
		{
			g2d.setStroke(new BasicStroke(1));
			g2d.setPaint(Color.red);
		}
	}

	public String getLabel()
	{
		return label;
	}

	public static String showValue(double x)
	{
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(x);
	}

	@Override
	public String toString()
	{
		return label;
	}

	@Override
	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}

	@Override
	public void setLabel(String label)
	{
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoObject#getSubObjects()
	 */
	@Override
	public List<GeoObject> getSubObjects()
	{
		return new ArrayList<GeoObject>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoObject#intersectWith(de.
	 * fabianmeier.seventeengon.geoobjects.GeoObject)
	 */
	@Override
	public GeoObject intersectWith(GeoObject other)
	{
		if (other.getSubObjects().isEmpty())
		{
			Pshape pShape = (Pshape) other;

			Set<Pshape> shapeSet = this.intersectWith(pShape);
			Set<GeoObject> geoSet = new HashSet<GeoObject>();
			for (Pshape p : shapeSet)
			{
				geoSet.add(p);
			}

			return new CompositeGeoObject(geoSet);
		} else
		{
			Set<GeoObject> geoSet = new HashSet<GeoObject>();
			for (GeoObject geo : other.getSubObjects())
			{
				geoSet.add(this.intersectWith(geo));
			}

			return new CompositeGeoObject(geoSet);
		}

	}

}
