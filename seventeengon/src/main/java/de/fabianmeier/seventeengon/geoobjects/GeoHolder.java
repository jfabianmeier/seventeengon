package de.fabianmeier.seventeengon.geoobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.fabianmeier.seventeengon.generator.GeoGenerator;
import de.fabianmeier.seventeengon.generator.GeoGeneratorLookup;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.shapes.CompositeGeoObject;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.util.GeoVisible;

/**
 * Holds geoObjects under given names. We consider these objects to be immutable
 * in the sense that a name once defined cannot be used for a different object
 * later.
 * 
 * @author JFM
 *
 */
public class GeoHolder
{
	private final Map<CompName, GeoObject> geoMap = new HashMap<>();
	private final double height;
	private int samplingValue;
	private final Map<CompName, GeoVisible> visiMap = new HashMap<>();
	private final double width;

	/**
	 * 
	 * @param width
	 *            Width of the drawing area
	 * @param height
	 *            Height of the drawing area
	 */
	public GeoHolder(double width, double height, int seed)
	{
		this.width = width;
		this.height = height;
		samplingValue = seed;

	}

	/**
	 * Copy Constructor
	 * 
	 * @param holder
	 *            OriginalHolder
	 */
	public GeoHolder(GeoHolder holder)
	{
		this.height = holder.height;
		this.width = holder.width;
		this.samplingValue = holder.samplingValue;

		this.geoMap.putAll(holder.geoMap);
		this.visiMap.putAll(holder.visiMap);

	}

	private GeoHolder(GeoHolder geoHolder, PreservingMap preMap)
	{
		this.width = geoHolder.width;
		this.height = geoHolder.height;
		samplingValue = geoHolder.samplingValue;

		for (CompName compName : geoHolder.geoMap.keySet())
		{
			GeoObject affineGeo = geoHolder.get(compName).preservingMap(preMap);

			affineGeo = affineGeo.intersectWith(getCanvasArea());

			geoMap.put(compName, affineGeo);
			visiMap.put(compName, geoHolder.getVisibility(compName));
		}

	}

	// private GeoHolder(GeoHolder geoHolder, XYpoint around, double
	// rotationAngle)
	// {
	// // How to set width and height
	// this.width = geoHolder.width;
	// this.height = geoHolder.height;
	// samplingValue = geoHolder.samplingValue;
	//
	// for (CompName compName : geoHolder.geoMap.keySet())
	// {
	// GeoObject affineGeo = geoHolder.get(compName).rotate(around,
	// rotationAngle);
	//
	// affineGeo = affineGeo.intersectWith(getCanvasArea());
	//
	// geoMap.put(compName, affineGeo);
	// visiMap.put(compName, geoHolder.getVisibility(compName));
	// }
	//
	// }

	/**
	 * Adds the geoObject under the name compName
	 * 
	 * @param compName
	 *            CompName
	 * @param geoObject
	 *            GeoObject
	 */
	public void add(CompName compName, GeoObject geoObject)
	{
		if (!geoMap.containsKey(compName))
			geoMap.put(compName, geoObject);
		else
			throw new IllegalArgumentException(
					"Object " + compName + " already in " + this);
	}

	public void add(GeoName geoName, GeoObject geoObject)
	{
		add(new CompName(geoName), geoObject);
	}

	/**
	 * change the visibility
	 * 
	 * @param compName
	 *            a compName
	 * @param geoVisible
	 *            an object defining visibility
	 */
	public void changeVisibility(CompName compName, GeoVisible geoVisible)
	{
		if (geoVisible == null)
			throw new IllegalArgumentException("GeoVisible may not be null.");
		visiMap.put(compName, geoVisible);
	}

	public boolean contains(CompName compName)
	{
		return geoMap.containsKey(compName);
	}

	public boolean contains(GeoName geoName)
	{
		return contains(new CompName(geoName));
	}

	/**
	 * Adds all elements with their respective names to the canvas.
	 * 
	 * @param canvas
	 *            A GeoCanvas
	 */
	public void draw(GeoCanvas canvas)
	{
		canvas.drawAll(this);

		// for (Entry<CompName, GeoObject> entry : geoMap.entrySet())
		// {
		// GeoVisible visi = visiMap.get(entry.getKey());
		// if (visi == null)
		// visi = GeoVisible.getStandard();
		// entry.getValue().draw(canvas, entry.getKey().toString(), visi);
		// }

	}

	/**
	 * Generates the objects for the contained compName objects
	 * 
	 * @param sentence
	 *            Sentence
	 * @throws IOException
	 *             If the compName objects cannot be generated or are not well
	 *             formed.
	 */
	public boolean generateCompNames(Sentence sentence) throws IOException
	{
		List<CompName> compNameList = sentence.getCompositeNames();

		for (CompName compName : compNameList)
		{
			if (!this.contains(compName) && compName.getGeoNames().size() > 1)
			{
				CompNamePattern compPattern = new CompNamePattern(compName);

				GeoGenerator geoGen = GeoGeneratorLookup.get(compPattern);

				if (!geoGen.generateAndAdd(this, compName))
					return false;

			}
		}

		return true;
	}

	/**
	 * 
	 * @param compName
	 *            compName
	 * @return geoObject
	 */
	public GeoObject get(CompName compName)
	{
		if (geoMap.containsKey(compName))
			return geoMap.get(compName);
		else
			throw new IllegalArgumentException(
					"Object " + compName + " does not exist.");
	}

	/**
	 * 
	 * @param geoName
	 *            geoName
	 * @return The geoObject
	 */
	public GeoObject get(GeoName geoName)
	{
		return get(new CompName(geoName));
	}

	/**
	 * 
	 * @return The area of the canvas as GeoObject
	 */
	public GeoObject getCanvasArea()
	{
		Triangle area1 = new Triangle(new XYpoint(0, 0),
				new XYpoint(0, getHeight()), new XYpoint(getWidth(), 0));

		Triangle area2 = new Triangle(new XYpoint(getWidth(), getHeight()),
				new XYpoint(0, getHeight()), new XYpoint(getWidth(), 0));

		List<GeoObject> geoList = new ArrayList<GeoObject>();
		geoList.add(area1);
		geoList.add(area2);

		return new CompositeGeoObject(geoList);

	}

	/**
	 * 
	 * @return A small area in the middle of the canvas to sample points from.
	 */
	public GeoObject getSamplingArea()
	{
		return new Triangle(new XYpoint(getWidth() * 0.4, getHeight() * 0.4),
				new XYpoint(getWidth() * 0.6, getHeight() * 0.4),
				new XYpoint(getWidth() * 0.5, getHeight() * 0.6));

	}

	/**
	 * 
	 * @return A clipped version of the GeoHolder scaled back to the same size
	 *         which contains all visible points
	 */
	public GeoHolder turnAndFitIntoCanvas()
	{

		Set<XYpoint> shapingPoints = getShapingPoints();

		PreservingMap preMap = RectangleFit.fitTo(width, height, shapingPoints);

		// double leftBorder = width;
		// double rightBorder = 0;
		// double bottomBorder = height;
		// double topBorder = 0;
		//
		// for (CompName comp : geoMap.keySet())
		// {
		// if (geoMap.get(comp) instanceof XYpoint
		// && !getVisibility(comp).isInvisible())
		// {
		// XYpoint localPoint = (XYpoint) geoMap.get(comp);
		// if (localPoint.getX() < leftBorder)
		// leftBorder = localPoint.getX() * 0.99;
		// if (localPoint.getX() > rightBorder)
		// rightBorder = localPoint.getX() * 1.01;
		//
		// if (localPoint.getY() < bottomBorder)
		// bottomBorder = localPoint.getY() * 0.99;
		// if (localPoint.getY() > topBorder)
		// topBorder = localPoint.getY() * 1.01;
		// }
		//
		// }
		//
		// if (leftBorder > rightBorder)
		// return this;
		//
		// XYvector shiftVector = new XYvector(-leftBorder * 0.8,
		// -bottomBorder * 0.8);
		//
		// double pseudoWidth = rightBorder - leftBorder;
		// double pseudoHeight = topBorder - bottomBorder;
		//
		// double scale = 0;
		//
		// double ratio = width / height;
		//
		// if (pseudoWidth / pseudoHeight > ratio)
		// {
		// scale = width / pseudoWidth * 0.8;
		// }
		// else
		// {
		// scale = height / pseudoHeight * 0.8;
		// }

		return new GeoHolder(this, preMap);

	}

	/**
	 * @return set of visible points
	 */
	private Set<XYpoint> getShapingPoints()
	{
		Set<XYpoint> back = new HashSet<XYpoint>();

		for (CompName comp : geoMap.keySet())
		{
			if (geoMap.get(comp) instanceof XYpoint
					&& !getVisibility(comp).isInvisible())
			{
				XYpoint localPoint = (XYpoint) geoMap.get(comp);
				back.add(localPoint);
			}

		}

		return back;
	}

	/**
	 * 
	 * @return the set of used compNames
	 */
	public Collection<CompName> getCompNames()
	{
		return geoMap.keySet();
	}

	/**
	 * @return the height
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * Returns a point under the name or an IOException if the element is not
	 * present or not a point
	 * 
	 * @param compName
	 *            The name of a possible XYpoint
	 * @return The XYpoint, if not exception is thrown
	 * @throws IOException
	 *             if the object does not exist or is not instanceof XYpoint
	 */
	public XYpoint getPointOrIO(CompName compName) throws IOException
	{
		if (!contains(compName))
			throw new IOException("Object " + compName + " does not exist.");

		GeoObject geo = get(compName);

		if (!(geo instanceof XYpoint))
			throw new IOException("Object " + geo + " is not a XYpoint.");

		return (XYpoint) geo;
	}

	/**
	 * Returns a point under the name or an IOException if the element is not
	 * present or not a point
	 * 
	 * @param geoName
	 *            The name of a possible XYpoint
	 * @return The XYpoint, if not exception is thrown
	 * @throws IOException
	 *             if the object does not exist or is not instanceof XYpoint
	 */
	public XYpoint getPointOrIO(GeoName geoName) throws IOException
	{
		return getPointOrIO(new CompName(geoName));
	}

	/**
	 * 
	 * @param compName
	 *            A registered compName
	 * @return the visibility status of compName
	 */
	public GeoVisible getVisibility(CompName compName)
	{
		if (visiMap.containsKey(compName))
			return visiMap.get(compName);

		if (geoMap.containsKey(compName))
			return GeoVisible.getStandard();

		throw new IllegalArgumentException("Not registered name: " + compName);

	}

	/**
	 * @return the width
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * Increases the stored sampling value by one and returns it
	 * 
	 * @return As said above.
	 */
	public int nextSampling()
	{
		samplingValue++;
		return samplingValue;

	}

	private Set<GeoObject> blockedAreaSet = new HashSet<GeoObject>();

	/**
	 * @param geo
	 *            A blocked area
	 */
	public void addBlockedArea(GeoObject geo)
	{
		blockedAreaSet.add(geo);

	}

	/**
	 * @return The set of blocked areas
	 */
	public Set<GeoObject> getBlockedAreas()
	{
		return blockedAreaSet;
	}

}
