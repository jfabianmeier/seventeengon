package de.fabianmeier.seventeengon.geoobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.GeoName;

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

	public void add(CompName compName, GeoObject geoObject)
	{
		if (!geoMap.containsKey(compName))
			geoMap.put(compName, geoObject);
		else
			throw new IllegalArgumentException(
					"Object " + compName + " already in " + this);
	}

	public GeoObject get(GeoName geoName)
	{
		return get(new CompName(geoName));
	}

	public GeoObject get(CompName compName)
	{
		if (geoMap.containsKey(compName))
			return geoMap.get(compName);
		else
			throw new IllegalArgumentException(
					"Object " + compName + " does not exist.");
	}

	public void draw(GeoCanvas canvas)
	{
		for (Entry<CompName, GeoObject> entry : geoMap.entrySet())
		{
			entry.getValue().draw(canvas, entry.getKey().toString());
		}

	}

}
