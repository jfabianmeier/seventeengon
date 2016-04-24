/**
 * 
 */
package de.fabianmeier.seventeengon.processing;

import java.util.ArrayList;
import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class TriangleGenerator implements GeoGenerator
{

	SentencePattern sentencePattern = new SentencePattern(
			"Sei ABC ein Dreieck");
	CompNamePattern compPattern = new CompNamePattern(new CompName("ABC"));

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.processing.GeoGenerator#generateAndAdd(de.
	 * fabianmeier.seventeengon.geoobjects.GeoHolder, java.lang.String)
	 */
	@Override
	public void generateAndAdd(GeoHolder geoHolder, String input)
	{
		List<CompName> points = new ArrayList<CompName>();

		CompName compName;

		if (sentencePattern.equals(new SentencePattern(input)))
		{
			compName = SentencePattern.getCompositeNames(input).get(0);
		} else
		{
			compName = new CompName(input);
		}
		List<GeoName> geoPoints = compName.getGeoNames();
		for (GeoName geoName : geoPoints)
		{
			points.add(new CompName(geoName));
		}

		if (points.size() != 3)
			throw new IllegalArgumentException(
					"Input " + input + " not suitable for triangle");

		for (CompName point : points)
			if (!geoHolder.contains(point))
			{
				GeoGeneratorLookup.generateAndAdd(geoHolder,
						"Sei " + point.toString() + " ein Punkt");
			}

		XYpoint a = (XYpoint) geoHolder.get(points.get(0));
		XYpoint b = (XYpoint) geoHolder.get(points.get(1));
		XYpoint c = (XYpoint) geoHolder.get(points.get(2));

		geoHolder.add(compName, new Triangle(a, b, c));

	}

}
