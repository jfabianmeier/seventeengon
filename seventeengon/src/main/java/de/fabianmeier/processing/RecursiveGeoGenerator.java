/**s
 * 
 */
package de.fabianmeier.processing;

import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.SequentialNameMapping;

/**
 * @author JFM
 *
 */
public class RecursiveGeoGenerator implements GeoGenerator
{

	private String sinkSentence;
	private List<String> replacement;

	public RecursiveGeoGenerator(String sentence, List<String> replacement)
	{
		this.sinkSentence = sentence;
		this.replacement = replacement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.processing.GeoGenerator#generateAndAdd(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder)
	 */
	@Override
	public void generateAndAdd(GeoHolder geoHolder, String sourceSentence)
	{
		GeoHolder localHolder = new GeoHolder();

		SequentialNameMapping nameMapping = new SequentialNameMapping(
				sourceSentence, sinkSentence);

		for (CompName sourceName : nameMapping.getSourceNames())
		{
			CompName sinkName = nameMapping.getSinkForSource(sourceName);
			if (geoHolder.contains(sourceName))
			{
				localHolder.add(sinkName, geoHolder.get(sourceName));
			}
		}

		for (String rep : replacement)
		{
			GeoGenerator localGeo = GeoGeneratorLookup.get(rep);
			localGeo.generateAndAdd(localHolder, rep);
		}

		for (CompName sinkName : nameMapping.getSinkNames())
		{
			CompName sourceName = nameMapping.getSourceForSink(sinkName);
			if (!geoHolder.contains(sourceName))
			{
				geoHolder.add(sourceName, localHolder.get(sinkName));
			}
		}

	}

}
