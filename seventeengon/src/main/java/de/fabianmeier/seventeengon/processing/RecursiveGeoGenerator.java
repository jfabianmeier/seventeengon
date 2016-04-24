/**s
 * 
 */
package de.fabianmeier.seventeengon.processing;

import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.SentencePattern;
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

		generateCompNames(geoHolder, sourceSentence);

		GeoHolder localHolder = new GeoHolder();

		SequentialNameMapping nameMapping = new SequentialNameMapping(
				sourceSentence, sinkSentence);

		transferToLocal(geoHolder, localHolder, nameMapping);

		for (String rep : replacement)
		{
			GeoGenerator localGeo = GeoGeneratorLookup.get(rep);
			localGeo.generateAndAdd(localHolder, rep);
		}

		transferToGlobal(geoHolder, localHolder, nameMapping);

	}

	private void transferToGlobal(GeoHolder geoHolder, GeoHolder localHolder,
			SequentialNameMapping nameMapping)
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

	private void transferToLocal(GeoHolder geoHolder, GeoHolder localHolder,
			SequentialNameMapping nameMapping)
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

	private void generateCompNames(GeoHolder geoHolder, String sourceSentence)
	{
		List<CompName> compNameList = SentencePattern
				.getCompositeNames(sourceSentence);

		for (CompName compName : compNameList)
		{
			if (!geoHolder.contains(compName)
					&& compName.getGeoNames().size() > 1)
			{
				GeoGenerator geoGen = GeoGeneratorLookup
						.get(new CompNamePattern(compName));

				geoGen.generateAndAdd(geoHolder, compName.toString());

			}
		}
	}

}
