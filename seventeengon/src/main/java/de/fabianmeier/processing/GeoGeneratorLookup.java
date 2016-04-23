package de.fabianmeier.processing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fabianmeier.seventeengon.naming.SentencePattern;

public class GeoGeneratorLookup
{
	private static Map<SentencePattern, GeoGenerator> lookUp = new HashMap<>();

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

	public static void add(String sentence, List<String> replacement)
	{
		GeoGenerator localGeo = new RecursiveGeoGenerator(sentence,
				replacement);

		lookUp.put(new SentencePattern(sentence), localGeo);
	}

}
