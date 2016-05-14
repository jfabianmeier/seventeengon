/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;

/**
 * @author JFM
 *
 */
public class AngleGenerator implements GeoGenerator
{

	public static final SentencePattern ANGLE = new SentencePattern(
			"Sei alpha der Winkel <BAC");
	public static final CompNamePattern BAC = new CompNamePattern(
			new CompName("<BAC"));

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
		if ((new SentencePattern(input)).equals(ANGLE))
		{
			List<CompName> compNames = input.getCompositeNames();

		}

		// TODO Auto-generated method stub
		throw new IllegalStateException();

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
		// TODO Auto-generated method stub
		throw new IllegalStateException();
	}

}
