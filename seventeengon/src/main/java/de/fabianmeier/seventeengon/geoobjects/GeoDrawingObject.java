/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JFM
 *
 */
public abstract class GeoDrawingObject implements GeoObject
{

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

	private int drawingStrength = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoObject#setDrawingStrength(int)
	 */
	@Override
	public void setVisibility(int strength)
	{
		drawingStrength = strength;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoObject#getDrawingStrength()
	 */
	@Override
	public int getVisibility()
	{
		return drawingStrength;
	}

}
