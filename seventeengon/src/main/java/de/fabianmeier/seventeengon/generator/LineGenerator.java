/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;

/**
 * @author JFM
 *
 */
public class LineGenerator implements GeoGenerator
{

	public static final SentencePattern LINE = new SentencePattern(
			"Sei AB eine Gerade");
	public static final CompNamePattern AB = new CompNamePattern(
			new CompName("AB"));
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
	public boolean generateAndAdd(GeoHolder geoHolder, CompName sentence)
			throws IOException
	{
		// TODO Auto-generated method stub
		throw new IllegalStateException();
	}

}
