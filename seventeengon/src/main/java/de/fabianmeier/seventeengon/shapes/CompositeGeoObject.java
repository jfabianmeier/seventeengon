package de.fabianmeier.seventeengon.shapes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.util.GeoVisible;

/**
 * A GeoObjects which consists of a list of other GeoObjects. They may be
 * Composites as well.
 * 
 * @author JFM
 *
 */
public class CompositeGeoObject implements GeoObject
{
	int drawingVisibility = 1;

	private List<GeoObject> subObjectList = new ArrayList<>();

	public CompositeGeoObject(Collection<GeoObject> geoCollection)
	{
		subObjectList = new ArrayList<GeoObject>(geoCollection);

	}

	/**
	 * Constructs a composite geoObject from a collection of shapes
	 * 
	 * @param shapeCollection
	 *            set of Pshape
	 * @return a CompositeGeoObject with these shapes as subobjects
	 */
	public static CompositeGeoObject getCompositeGeoObject(
			Collection<Pshape> shapeCollection)
	{
		List<GeoObject> subObjectList = new ArrayList<GeoObject>();
		for (Pshape p : shapeCollection)
		{
			subObjectList.add(p);
		}
		return new CompositeGeoObject(subObjectList);
	}

	@Override
	public void draw(GeoCanvas canvas, String label, GeoVisible visi)
	{
		boolean written = false;
		for (GeoObject geo : subObjectList)
		{
			if (!written)
				geo.draw(canvas, label, visi);
			else
				geo.draw(canvas, null, visi);

			written = true;
		}

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

}
