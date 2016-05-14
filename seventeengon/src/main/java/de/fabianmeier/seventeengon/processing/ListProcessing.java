/**
 * #
 * 
 */
package de.fabianmeier.seventeengon.processing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.fabianmeier.seventeengon.generator.GeoGeneratorLookup;
import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.SampleGenerator;
import de.fabianmeier.seventeengon.naming.Sentence;

/**
 * @author JFM
 *
 */
public class ListProcessing
{
	/**
	 * 
	 * @param rawSentenceList
	 *            A list of input sentences from IO
	 * @return A holder object with the sentences.
	 * @throws IOException
	 *             If the construction fails.
	 */
	public static GeoHolder sentenceProcessing(List<String> rawSentenceList)
			throws IOException
	{
		List<Sentence> sentenceList = new ArrayList<Sentence>();

		for (String rawSentence : rawSentenceList)
		{
			String trimmedSentence = rawSentence.trim();
			if (!Sentence.isSentence(trimmedSentence))
			{
				throw new IOException("The expression '" + trimmedSentence
						+ "' is no valid sentence");
			}
			sentenceList.add(new Sentence(trimmedSentence));
		}

		GeoHolder holder = new GeoHolder();

		SampleGenerator.reset();

		for (int versuch = 0; versuch < 100; versuch++)
		{
			boolean runSuccess = true;
			for (Sentence sentence : sentenceList)
			{
				if (!GeoGeneratorLookup.generateAndAdd(holder, sentence))
				{
					runSuccess = false;
					break;
				}

			}

			if (runSuccess)
			{
				return holder;
			}
			else
			{
				holder = new GeoHolder();
			}
		}

		throw new IOException("Construction of the geometric object failed.");

	}

}
