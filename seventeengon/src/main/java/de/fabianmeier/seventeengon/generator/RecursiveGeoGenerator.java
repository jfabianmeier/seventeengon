/**
 * s
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SequentialNameMapping;

/**
 * @author JFM
 *
 */
public class RecursiveGeoGenerator implements GeoGenerator
{

	private List<Sentence> replacement;
	private Sentence sinkSentence;

	/**
	 * 
	 * @param sentence
	 *            a sentence
	 * @param replacement
	 *            the list of sentences that defines the above sentence
	 */
	public RecursiveGeoGenerator(Sentence sentence, List<Sentence> replacement)
	{
		this.sinkSentence = sentence;
		this.replacement = replacement;
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
	public boolean generateAndAdd(GeoHolder geoHolder, CompName compName) throws IOException
	{
		throw new IllegalArgumentException(compName + " is not a valid pattern for " + this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.processing.GeoGenerator#generateAndAdd(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence sourceSentence) throws IOException
	{

		geoHolder.generateCompNames(sourceSentence);

		GeoHolder localHolder = new GeoHolder(geoHolder.getWidth(), geoHolder.getHeight(), geoHolder.nextSampling());
		SequentialNameMapping nameMapping = new SequentialNameMapping(sourceSentence, sinkSentence);

		transferToLocal(geoHolder, localHolder, nameMapping);

		for (Sentence rep : replacement)
		{
			boolean created = GeoGeneratorLookup.generateAndAdd(localHolder, rep);
			if (!created)
				return false;
		}

		transferToGlobal(geoHolder, localHolder, nameMapping);
		return true;

	}

	/**
	 * transferring the local objects to the global ones
	 * 
	 * @param geoHolder
	 *            the global geoHolder
	 * @param localHolder
	 *            the local geoHolder
	 * @param nameMapping
	 *            mapping of names
	 */
	private void transferToGlobal(GeoHolder geoHolder, GeoHolder localHolder, SequentialNameMapping nameMapping)
	{
		for (CompName sinkName : nameMapping.getSinkNames())
		{
			CompName sourceName = nameMapping.getSourceForSink(sinkName);
			if (!geoHolder.contains(sourceName))
			{
				geoHolder.add(sourceName, localHolder.get(sinkName));
			}
		}
	}

	/**
	 * 
	 * @param geoHolder
	 *            the global geoHolder
	 * @param localHolder
	 *            the local geoHolder
	 * @param nameMapping
	 *            mapping of names
	 */
	private void transferToLocal(GeoHolder geoHolder, GeoHolder localHolder, SequentialNameMapping nameMapping)
	{
		for (CompName sourceName : nameMapping.getSourceNames())
		{
			CompName sinkName = nameMapping.getSinkForSource(sourceName);
			if (geoHolder.contains(sourceName))
			{
				localHolder.add(sinkName, geoHolder.get(sourceName));
			}
		}
	}

}
