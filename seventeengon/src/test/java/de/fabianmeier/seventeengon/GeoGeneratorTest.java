/**
 * 
 */
package de.fabianmeier.seventeengon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.SampleGenerator;
import de.fabianmeier.seventeengon.geoobjects.TextualCanvas;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.processing.GeoGenerator;
import de.fabianmeier.seventeengon.processing.GeoGeneratorLookup;

/**
 * @author JFM
 *
 */
public class GeoGeneratorTest
{

	@Before
	public void init()
	{

	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.processing.GeoGeneratorLookup#get(java.lang.String)}
	 * .
	 * 
	 * @throws IOException
	 *             Exception
	 */
	@Test
	public void testGetString() throws IOException
	{
		GeoGenerator local = GeoGeneratorLookup
				.get(new Sentence("Sei A ein Punkt"));
		TextualCanvas textCanvas = new TextualCanvas();

		GeoHolder geoHolder = new GeoHolder();

		SampleGenerator.reset();

		local.generateAndAdd(geoHolder, new Sentence("Sei B ein Punkt"));

		geoHolder.draw(textCanvas);

		System.out.println("Sei B ein Punkt:");
		System.out.println(textCanvas);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.processing.GeoGeneratorLookup#add(java.lang.String, java.util.List)}
	 * .
	 * 
	 * @throws IOException
	 *             Exception
	 */
	@Test
	public void testAddStringListOfString() throws IOException
	{
		List<Sentence> replacement = new ArrayList<Sentence>();

		replacement.add(new Sentence("Sei A ein Punkt"));
		replacement.add(new Sentence("Sei B ein Punkt"));
		GeoGeneratorLookup.add(new Sentence("Seien A und B Punkte"),
				replacement);

		Sentence sentence = new Sentence("Seien X und Y Punkte");
		SampleGenerator.reset();
		GeoGenerator local = GeoGeneratorLookup.get(sentence);

		GeoHolder geoHolder = new GeoHolder();
		local.generateAndAdd(geoHolder, sentence);

		TextualCanvas textCanvas = new TextualCanvas();
		geoHolder.draw(textCanvas);

		System.out.println(sentence);
		System.out.println(textCanvas);

	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.processing.GeoGeneratorLookup#add(java.lang.String, java.util.List)}
	 * .
	 * 
	 * @throws IOException
	 *             Exception
	 */
	@Test
	public void testAddStringListOfString2() throws IOException
	{

		TextualCanvas textCanvas = new TextualCanvas();

		Sentence sentence = new Sentence("Sei EFX ein Dreieck");
		SampleGenerator.reset();
		GeoGenerator local = GeoGeneratorLookup.get(sentence);

		GeoHolder geoHolder = new GeoHolder();
		local.generateAndAdd(geoHolder, sentence);

		geoHolder.draw(textCanvas);

		System.out.println(sentence);
		System.out.println(textCanvas);

	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.processing.GeoGeneratorLookup#add(java.lang.String, java.util.List)}
	 * .
	 * 
	 * @throws IOException
	 *             Exception
	 */
	@Test
	public void testAddStringListOfString3() throws IOException
	{

		TextualCanvas textCanvas = new TextualCanvas();

		Sentence sentence = new Sentence("Sei HDI eine Dreieck");
		SampleGenerator.reset();

		GeoHolder geoHolder = new GeoHolder();
		GeoGeneratorLookup.generateAndAdd(geoHolder, sentence);

		geoHolder.draw(textCanvas);

		System.out.println(sentence);
		System.out.println(textCanvas);

	}

}
