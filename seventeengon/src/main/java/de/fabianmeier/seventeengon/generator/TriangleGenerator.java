/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;

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
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence)
			throws IOException

	{
		if (!geoHolder.generateCompNames(sentence))
			return false;

		if ((new SentencePattern(sentence)).equals(TRIANGLE))
		{
			return true;
		}
		throw new IllegalArgumentException(
				sentence + " is not defined operation.");
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
	public boolean generateAndAdd(GeoHolder geoHolder, CompName compName)
			throws IOException
	{
		if ((new CompNamePattern(compName)).equals(ABC))
		{
			GeoName nameA = compName.getGeoNames().get(0);
			GeoName nameB = compName.getGeoNames().get(1);
			GeoName nameC = compName.getGeoNames().get(2);

			if (!geoHolder.contains(nameA))
			{
				GeoGeneratorLookup.generateAndAdd(geoHolder,
						new Sentence("Sei " + nameA + " ein Punkt"));

			}

			if (!geoHolder.contains(nameB))
			{
				GeoGeneratorLookup.generateAndAdd(geoHolder,
						new Sentence("Sei " + nameB + " ein Punkt"));

			}

			if (!geoHolder.contains(nameC))
			{
				GeoGeneratorLookup.generateAndAdd(geoHolder, new Sentence(
						"Sei " + nameC + " ein Punkt über " + nameA + nameB));

			}

			XYpoint pointA = geoHolder.getPointOrIO(nameA);
			XYpoint pointB = geoHolder.getPointOrIO(nameB);
			XYpoint pointC = geoHolder.getPointOrIO(nameC);

			Triangle triangle = new Triangle(pointA, pointB, pointC);
			geoHolder.add(compName, triangle);

			return true;
		}

		throw new IllegalArgumentException(
				compName + " is not defined operation.");
	}

}
