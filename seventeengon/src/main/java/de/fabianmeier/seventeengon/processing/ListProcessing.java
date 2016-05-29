/**
 * #
 * 
 */
package de.fabianmeier.seventeengon.processing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fabianmeier.seventeengon.generator.GeoGeneratorLookup;
import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.Sentence;

/**
 * @author JFM
 *
 */
public class ListProcessing
{

	private static final Logger LOG = LogManager
			.getLogger(ListProcessing.class);

	private static double width = 600;
	private static double height = 400;
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

		for (int versuch = 0; versuch < 100; versuch++)
		{
			GeoHolder holder = new GeoHolder(width, height, versuch);
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

			LOG.debug("Try " + versuch + " in writing.");

		}

		throw new IOException("Construction of the geometric object failed.");

	}

}
