package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * A GeoObjects which consists of a list of other GeoObjects. They may be
 * Composites as well.
 * 
 * @author JFM
 *
 */
public class CompositeGeoObject implements GeoObject
{
	// int drawingVisibility = 1;

	private static GeoObject emptyObject = new CompositeGeoObject(
			new ArrayList<GeoObject>());
	private List<GeoObject> subObjectList = new ArrayList<>();

	public static GeoObject getEmptyObject()
	{
		return emptyObject;
	}

	public CompositeGeoObject(GeoObject... geoArray)
	{
		this(Arrays.asList(geoArray));
	}

	public CompositeGeoObject(Collection<? extends GeoObject> geoCollection)
	{
		subObjectList = new ArrayList<GeoObject>(geoCollection);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#containsPoint(de.fabianmeier
	 * .seventeengon.shapes.XYpoint)
	 */
	@Override
	public boolean containsPoint(XYpoint point)
	{
		for (GeoObject geo : subObjectList)
		{
			if (geo.containsPoint(point))
				return true;
		}
		return false;
	}

	// @Override
	// public void draw(GeoCanvas canvas, GeoVisible visi)
	// {
	// boolean written = false;
	// for (GeoObject geo : subObjectList)
	// {
	// if (!written)
	// geo.draw(canvas, visi);
	// else
	// geo.draw(canvas, visi.hideName());
	//
	// written = true;
	// }
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getBoundary()
	 */
	@Override
	public GeoObject getBoundary()
	{
		List<GeoObject> boundaries = new ArrayList<GeoObject>();

		for (GeoObject geo : subObjectList)
		{
			boundaries.add(geo.getBoundary());
		}

		return new CompositeGeoObject(boundaries);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoObject#getDimension()
	 */
	@Override
	public int getDimension()
	{
		int maxDim = -1;

		for (GeoObject geo : this.getSubObjects())
		{
			if (geo.getDimension() > maxDim)
				maxDim = geo.getDimension();
		}
		return maxDim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getFilledObject()
	 */
	@Override
	public GeoObject getFilledObject()
	{
		List<GeoObject> filled = new ArrayList<GeoObject>();

		for (GeoObject geo : subObjectList)
		{
			filled.add(geo.getFilledObject());
		}

		return new CompositeGeoObject(filled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoObject#getSamplePoint(int)
	 */
	@Override
	public XYpoint getSamplePoint(int sampleNumber)
	{
		if (getDimension() == -1)
			return null;

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

	@Override
	public List<GeoObject> getSubObjects()
	{
		return subObjectList;
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
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return getDimension() == -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#getZeroDimensionalPart()
	 */
	@Override
	public Set<XYpoint> getZeroDimensionalPart()
	{
		Set<XYpoint> back = new HashSet<XYpoint>();

		for (GeoObject geoObject : getSubObjects())
		{
			back.addAll(geoObject.getZeroDimensionalPart());
		}

		return back;
	}

	@Override
	public String toString()
	{
		return "\\" + StringUtils.join(subObjectList, ",") + "/";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#affineMap(de.fabianmeier.
	 * seventeengon.shapes.XYvector, double)
	 */
	@Override
	public CompositeGeoObject affineMap(XYvector shiftVector, double scale)
	{
		List<GeoObject> shiftedObjects = new ArrayList<GeoObject>();

		for (GeoObject geo : subObjectList)
		{
			shiftedObjects.add(geo.affineMap(shiftVector, scale));
		}

		return new CompositeGeoObject(shiftedObjects);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getNameDrawingAngles()
	 */
	@Override
	public List<Angle> getNameDrawingAngles()
	{
		List<Angle> back = new ArrayList<Angle>();

		for (GeoObject geo : subObjectList)
		{
			back.addAll(geo.getNameDrawingAngles());
		}

		return back;

	}

}
