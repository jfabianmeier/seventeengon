package de.fabianmeier.seventeengon.geoobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompositeGeoObject implements GeoObject
{
	int drawingStrength = 1;

	private List<GeoObject> subObjectList = new ArrayList<>();

	public CompositeGeoObject(Collection<GeoObject> geoCollection)
	{
		subObjectList = new ArrayList<GeoObject>(geoCollection);

	}

	@Override
	public List<GeoObject> getSubObjects()
	{
		return subObjectList;
	}

	@Override
	public void draw(GeoCanvas canvas, String label)
	{
		boolean written = false;
		for (GeoObject geo : subObjectList)
		{
			if (!written)
				geo.draw(canvas, label);
			else
				geo.draw(canvas, null);
		}

	}

	@Override
	public void setDrawingStrength(int strength)
	{
		drawingStrength = strength;

	}

	@Override
	public int getDrawingStrength()
	{
		return drawingStrength;
	}

}
