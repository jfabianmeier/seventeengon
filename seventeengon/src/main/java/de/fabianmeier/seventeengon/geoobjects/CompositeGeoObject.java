package de.fabianmeier.seventeengon.geoobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import de.fabianmeier.seventeengon.shapes.XYpoint;

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
	public void setVisibility(int visibility)
	{
		drawingStrength = visibility;

	}

	@Override
	public int getVisibility()
	{
		return drawingStrength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoObject#getSamplePoint(int)
	 */
	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		List<GeoObject> maxDimObjects = new ArrayList<GeoObject>();

		for (GeoObject geo : this.getSubObjects())
		{
			if (geo.getDimension() == getDimension())
			{
				maxDimObjects.add(geo);
			}
		}

		Random rand = new Random(
				sampleNumber + getDimension() + getSubObjects().size());

		GeoObject chosen = maxDimObjects
				.get(rand.nextInt(maxDimObjects.size()));
		return chosen.getSamplePoint(sampleNumber);

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
		List<GeoObject> intersectionList = new ArrayList<GeoObject>();

		for (GeoObject geo : this.getSubObjects())
		{
			intersectionList.add(geo.intersectWith(other));
		}

		return new CompositeGeoObject(intersectionList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoObject#getDimension()
	 */
	@Override
	public int getDimension()
	{
		int maxDim = 0;

		for (GeoObject geo : this.getSubObjects())
		{
			if (geo.getDimension() > maxDim)
				maxDim = geo.getDimension();
		}
		return maxDim;
	}

}
