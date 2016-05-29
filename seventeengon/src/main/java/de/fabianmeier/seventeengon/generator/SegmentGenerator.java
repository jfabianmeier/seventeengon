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
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class SegmentGenerator implements GeoGenerator
{
	public static final SentencePattern SEGMENT = new SentencePattern(
			"Sei |AB| eine Strecke");
	public static final CompNamePattern AB = new CompNamePattern(
			new CompName("|AB|"));
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.generator.GeoGenerator#generateAndAdd(de.
	 * fabianmeier.seventeengon.geoobjects.GeoHolder,
	 * de.fabianmeier.seventeengon.naming.Sentence)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence)
			throws IOException
	{
		if (!geoHolder.generateCompNames(sentence))
			return false;

		if ((new SentencePattern(sentence)).equals(SEGMENT))
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
		if ((new CompNamePattern(compName)).equals(AB))
		{
			GeoName nameA = compName.getGeoNames().get(0);
			GeoName nameB = compName.getGeoNames().get(1);

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

			XYpoint pointA = geoHolder.getPointOrIO(nameA);
			XYpoint pointB = geoHolder.getPointOrIO(nameB);

			Line line = new Line(pointA, pointB, 0, 1);

			geoHolder.add(compName, line);

			return true;
		}

		throw new IllegalArgumentException(
				compName + " is not defined operation.");
	}

}
