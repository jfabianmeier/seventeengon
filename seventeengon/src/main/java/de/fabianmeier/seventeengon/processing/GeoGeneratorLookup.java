package de.fabianmeier.seventeengon.processing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.SentencePattern;

public class GeoGeneratorLookup
{
	private static Map<SentencePattern, GeoGenerator> lookUp = new HashMap<>();
	private static Map<CompNamePattern, GeoGenerator> compLookUp = new HashMap<>();

	static
	{
		add("Sei P ein Punkt", new PointGenerator());
		add("Sei ABC ein Dreieck", new TriangleGenerator());
	}

	public static GeoGenerator get(SentencePattern pattern)
	{
		if (!lookUp.containsKey(pattern))
			throw new IllegalArgumentException(
					"SentencePattern " + pattern + " not defined.");

		return lookUp.get(pattern);
	}

	public static GeoGenerator get(String pattern)
	{
		return get(new SentencePattern(pattern));
	}

	public static GeoGenerator get(CompNamePattern compPattern)
	{
		if (!compLookUp.containsKey(compPattern))
			throw new IllegalArgumentException(
					"SentencePattern " + compPattern + " not defined.");

		return compLookUp.get(compPattern);
	}

	public static void add(String sentence, GeoGenerator geoGen)
	{
		lookUp.put(new SentencePattern(sentence), geoGen);
	}

	public static void add(String sentence, List<String> replacement)
	{
		GeoGenerator localGeo = new RecursiveGeoGenerator(sentence,
				replacement);

		add(sentence, localGeo);

	}

	public static void add(CompName compName, GeoGenerator geoGen)
	{
		compLookUp.put(new CompNamePattern(compName), geoGen);
	}

}
