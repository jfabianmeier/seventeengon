/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class TriangleGenerator implements GeoGenerator
{

	public static final SentencePattern TRIANGLE = new SentencePattern(
			"Sei ABC ein Dreieck");
	public static final CompNamePattern ABC = new CompNamePattern(
			new CompName("ABC"));

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.generator.GeoGenerator#generateAndAdd(de.
	 * fabianmeier.seventeengon.geoobjects.GeoHolder, java.lang.String)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence input)
			throws IOException
	{
		List<CompName> points = new ArrayList<CompName>();

		CompName compName;

		if (TRIANGLE.equals(new SentencePattern(input)))
		{
			compName = input.getCompositeNames().get(0);
		}
		else
		{
			throw new IOException("Wrong sentence pattern: " + input);
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
				try
				{
					GeoGeneratorLookup.generateAndAdd(geoHolder, new Sentence(
							"Sei " + point.toString() + " ein Punkt"));
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		XYpoint a = (XYpoint) geoHolder.get(points.get(0));
		XYpoint b = (XYpoint) geoHolder.get(points.get(1));
		XYpoint c = (XYpoint) geoHolder.get(points.get(2));

		geoHolder.add(compName, new Triangle(a, b, c));

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.generator.GeoGenerator#generateAndAdd(de.
	 * fabianmeier.seventeengon.geoobjects.GeoHolder,
	 * de.fabianmeier.seventeengon.naming.CompName)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, CompName sentence)
			throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

}
