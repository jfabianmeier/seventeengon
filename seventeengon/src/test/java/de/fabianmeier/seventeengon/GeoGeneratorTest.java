/**
 * 
 */
package de.fabianmeier.seventeengon;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.SampleGenerator;
import de.fabianmeier.seventeengon.geoobjects.TextualCanvas;
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
	 */
	@Test
	public void testGetString()
	{
		GeoGenerator local = GeoGeneratorLookup.get("Sei A ein Punkt");
		TextualCanvas textCanvas = new TextualCanvas();

		GeoHolder geoHolder = new GeoHolder();

		SampleGenerator.reset();

		local.generateAndAdd(geoHolder, "Sei B ein Punkt");

		geoHolder.draw(textCanvas);

		System.out.println("Sei B ein Punkt:");
		System.out.println(textCanvas);
	}

	/**
	 * Test method for
	 * {@link de.fabianmeier.seventeengon.processing.GeoGeneratorLookup#add(java.lang.String, java.util.List)}
	 * .
	 */
	@Test
	public void testAddStringListOfString()
	{
		List<String> replacement = new ArrayList<String>();

		TextualCanvas textCanvas = new TextualCanvas();

		replacement.add("Sei A ein Punkt");
		replacement.add("Sei B ein Punkt");
		GeoGeneratorLookup.add("Seien A und B Punkte", replacement);

		String sentence = "Seien X und Y Punkte";
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
	 */
	@Test
	public void testAddStringListOfString2()
	{

		TextualCanvas textCanvas = new TextualCanvas();

		String sentence = "Sei EFX ein Dreieck";
		SampleGenerator.reset();
		GeoGenerator local = GeoGeneratorLookup.get(sentence);

		GeoHolder geoHolder = new GeoHolder();
		local.generateAndAdd(geoHolder, sentence);

		geoHolder.draw(textCanvas);

		System.out.println(sentence);
		System.out.println(textCanvas);

	}

}
