/**
 * 
 */
package de.fabianmeier.seventeengon.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.TextualCanvas;
import de.fabianmeier.seventeengon.svg.SVGcanvas;

/**
 * @author JFM
 *
 */
public class ConsoleAccess
{

	private static final Logger LOG = LogManager.getLogger(ConsoleAccess.class);

	private static File OUTPUT = new File("C:\\Dropbox\\testSVG");

	/**
	 * @param args
	 *            Sentences separated by .
	 */
	public static void main(String[] args)
	{
		List<String> rawSentences = new ArrayList<String>();

		if (args.length == 0)
		{
			LOG.info("Eingabesätze: \n");
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));

			try
			{
				String result = br.readLine();
				rawSentences = constructSentences(result.split("\\s"));
			}
			catch (IOException e)
			{
				LOG.info("Einlesen gescheitert", e);
			}

		}
		else
		{

			rawSentences = constructSentences(args);
		}

		try
		{

			String fileName = "test.svg";

			String last = rawSentences.get(rawSentences.size() - 1);

			if (last.matches("Schreibe nach (.*)"))
			{
				fileName = last.substring("Schreibe nach ".length());
				rawSentences.remove(rawSentences.size() - 1);
			}

			LOG.info("Schreibe nach " + fileName);

			GeoHolder holder = ListProcessing.sentenceProcessing(rawSentences);

			SVGcanvas firstSvgCanvas = new SVGcanvas(holder.getWidth(),
					holder.getHeight());

			firstSvgCanvas.drawAll(holder, false);

			firstSvgCanvas.writeToFile(new File(OUTPUT, "raw" + fileName));

			GeoCanvas textCanvas = new TextualCanvas();

			textCanvas.drawAll(holder, true);

			LOG.info(textCanvas.toString());

			SVGcanvas svgCanvas = new SVGcanvas(holder.getWidth(),
					holder.getHeight());

			svgCanvas.drawAll(holder, true);

			svgCanvas.writeToFile(new File(OUTPUT, fileName));

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
				rawSentences.add(runningSentence.trim());
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
