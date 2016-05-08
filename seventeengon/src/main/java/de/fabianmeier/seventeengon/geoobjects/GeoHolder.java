package de.fabianmeier.seventeengon.geoobjects;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.processing.GeoGenerator;
import de.fabianmeier.seventeengon.processing.GeoGeneratorLookup;
import de.fabianmeier.seventeengon.shapes.GeoObject;
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
	private Map<CompName, GeoObject> geoMap = new HashMap<>();
	private Map<CompName, GeoVisible> visiMap = new HashMap<>();

	public void changeVisibility(CompName compName, GeoVisible geoVisible)
	{
		visiMap.put(compName, geoVisible);
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

	public GeoHolder()
	{

	}

	public boolean contains(GeoName geoName)
	{
		return contains(new CompName(geoName));
	}

	public boolean contains(CompName compName)
	{
		return geoMap.containsKey(compName);
	}

	public void add(GeoName geoName, GeoObject geoObject)
	{
		add(new CompName(geoName), geoObject);
	}

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
	 * Adds all elements with their respective names to the canvas.
	 * 
	 * @param canvas
	 *            A GeoCanvas
	 */
	public void draw(GeoCanvas canvas)
	{
		for (Entry<CompName, GeoObject> entry : geoMap.entrySet())
		{
			GeoVisible visi = visiMap.get(entry.getKey());
			if (visi == null)
				visi = GeoVisible.getStandard();
			entry.getValue().draw(canvas, entry.getKey().toString(), visi);
		}

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
	public void generateCompNames(Sentence sentence) throws IOException
	{
		List<CompName> compNameList = sentence.getCompositeNames();

		for (CompName compName : compNameList)
		{
			if (!this.contains(compName) && compName.getGeoNames().size() > 1)
			{
				GeoGenerator geoGen = GeoGeneratorLookup
						.get(new CompNamePattern(compName));

				geoGen.generateAndAdd(this, compName);

			}
		}
	}

}
