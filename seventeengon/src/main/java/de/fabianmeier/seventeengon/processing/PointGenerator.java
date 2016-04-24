/**
 * 
 */
package de.fabianmeier.seventeengon.processing;

import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.GeoPoint;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.SentencePattern;

/**
 * @author JFM
 *
 */
public class PointGenerator implements GeoGenerator
{

	private final SentencePattern pattern = new SentencePattern(
			"Sei P ein Punkt");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.processing.GeoGenerator#generateAndAdd(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder, java.lang.String)
	 */
	@Override
	public void generateAndAdd(GeoHolder geoHolder, String sentence)
	{
		if (!pattern.equals(new SentencePattern(sentence)))
			throw new IllegalArgumentException("Wrong pattern, expected "
					+ pattern + " but got " + sentence);

		List<CompName> compNames = SentencePattern.getCompositeNames(sentence);

		CompName point = compNames.get(0);

		geoHolder.add(point, new GeoPoint());

	}

}
