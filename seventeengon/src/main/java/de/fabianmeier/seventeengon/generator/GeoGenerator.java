package de.fabianmeier.seventeengon.generator;

import java.io.IOException;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.Sentence;

/**
 * Generates GeoObjects for sentences of given pattern
 * 
 * @author jfabi
 *
 */
public interface GeoGenerator
{
	/** 
	 *  
	 * @param geoHolder
	 *            a geoHolder
	 * @param sentence
	 *            a sentence fitting to the pattern of the object
	 * @return if the object was added to the geoHolder
	 * @throws IOException
	 *             if the sentence is not valid
	 */
	boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence) throws IOException;

	/**
	 * 
	 * @param geoHolder
	 *            a geoHolder
	 * @param compName
	 *            a composite Name
	 * @return if the object described by the composite name was added to the
	 *         geoHolder
	 * @throws IOException
	 *             if the composite name does not fulfil a known pattern.
	 */
	boolean generateAndAdd(GeoHolder geoHolder, CompName compName) throws IOException;

}
