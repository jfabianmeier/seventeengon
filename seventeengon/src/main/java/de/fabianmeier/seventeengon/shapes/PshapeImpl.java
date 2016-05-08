package de.fabianmeier.seventeengon.shapes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PshapeImpl implements Pshape
{

	// protected void setColourAndStroke(Graphics2D g2d)
	// {
	// if (visibility == 0)
	// g2d.setStroke(new BasicStroke(0.5f));
	// g2d.setPaint(Color.lightGray);
	//
	// if (visibility == 1)
	// g2d.setStroke(new BasicStroke(0.7f));
	// g2d.setPaint(Color.black);
	//
	// if (visibility == 2)
	// {
	// g2d.setStroke(new BasicStroke(1));
	// g2d.setPaint(Color.red);
	// }
	// }

	/**
	 * 
	 * @param x
	 *            a double value
	 * @return a formatted double value
	 */
	public static String showValue(double x)
	{
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(x);
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
		}
		else
		{
			Set<GeoObject> geoSet = new HashSet<GeoObject>();
			for (GeoObject geo : other.getSubObjects())
			{
				geoSet.add(this.intersectWith(geo));
			}

			return new CompositeGeoObject(geoSet);
		}

	}

	@Override
	public boolean containsPoint(XYpoint point)
	{
		return (!this.intersectWith(point).isEmpty());

	}

}
