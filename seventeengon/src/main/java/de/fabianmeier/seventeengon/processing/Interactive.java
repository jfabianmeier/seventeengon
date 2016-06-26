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
public class Interactive
{

	private static final Logger LOG = LogManager.getLogger(ConsoleAccess.class);

	/**
	 * @param args
	 *            no arguments
	 */
	public static void main(String[] args)
	{

		List<String> rawSentences = new ArrayList<String>();

		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));

		File drawfile = null;

		while (true)
		{
			System.out.println("Next Sentence: ");

			try
			{
				String nextSentence = br.readLine();

				if (nextSentence.equals("exit"))
					break;

				if (nextSentence.startsWith("svg:"))
				{
					drawfile = new File(nextSentence.substring(4));
				}
				else
					rawSentences.add(nextSentence);

				GeoHolder holder = ListProcessing
						.sentenceProcessing(rawSentences);
				GeoCanvas textCanvas = new TextualCanvas();

				holder.draw(textCanvas);

				SVGcanvas svgCanvas = new SVGcanvas(holder.getWidth(),
						holder.getHeight());

				holder.draw(svgCanvas);

				GeoHolder holderShift = holder.turnAndFitIntoCanvas();
				GeoCanvas textCanvasShift = new TextualCanvas();

				holderShift.draw(textCanvasShift);

				SVGcanvas svgCanvasShift = new SVGcanvas(holder.getWidth(),
						holder.getHeight());

				holderShift.draw(svgCanvasShift);

				if (drawfile != null)
					svgCanvas.writeToFile(drawfile);

				if (drawfile != null)
					svgCanvasShift.writeToFile(new File(
							drawfile.toString().replace(".svg", "-shift.svg")));

				LOG.info(textCanvas.toString());
			}
			catch (IOException e)
			{
				LOG.error("Processing failed for reason: "
						+ ExceptionUtils.getStackTrace(e));
				break;
			}
		}

	}

}
