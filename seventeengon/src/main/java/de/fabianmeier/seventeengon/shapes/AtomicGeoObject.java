package de.fabianmeier.seventeengon.shapes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class AtomicGeoObject implements GeoObject
{

	/**
	 * 
	 * @param x
	 *            a double value
	 * @return a formatted double value
	 */
	public static String showValue(double x)
	{
		DecimalFormat df = new DecimalFormat("#.####",
				DecimalFormatSymbols.getInstance(Locale.ENGLISH));
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

	@Override
	public boolean containsPoint(XYpoint point)
	{
		return (!this.intersectWith(point).isEmpty());

	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#normalize()
	 */
	@Override
	public GeoObject normalize()
	{
		return this;
	}

}
