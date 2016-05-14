/**
 * 
 */
package de.fabianmeier.seventeengon.processing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.TextualCanvas;

/**
 * @author JFM
 *
 */
public class ConsoleAccess
{

	private static final Logger LOG = LogManager.getLogger(ConsoleAccess.class);

	/**
	 * @param args
	 *            Sentences separated by .
	 */
	public static void main(String[] args)
	{
		List<String> rawSentences = constructSentences(args);

		try
		{
			GeoHolder holder = ListProcessing.sentenceProcessing(rawSentences);
			GeoCanvas textCanvas = new TextualCanvas();

			holder.draw(textCanvas);

			LOG.info(textCanvas.toString());
		}
		catch (IOException e)
		{
			LOG.error("Processing failed for reason: "
					+ ExceptionUtils.getStackTrace(e));
		}

	}

	private static List<String> constructSentences(String[] args)
	{
		List<String> rawSentences = new ArrayList<String>();

		String runningSentence = "";
		for (String arg : args)
		{
			if (arg.endsWith("."))
			{
				arg = arg.substring(0, arg.length() - 1);
				runningSentence += arg;
				rawSentences.add(runningSentence);
				runningSentence = "";
			}
			else
			{
				runningSentence += arg + " ";
			}

		}
		return rawSentences;
	}

}
