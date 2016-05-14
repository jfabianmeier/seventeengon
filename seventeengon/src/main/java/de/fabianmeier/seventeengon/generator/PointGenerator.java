/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.SampleGenerator;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.CompositeGeoObject;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class PointGenerator implements GeoGenerator
{

	public static final SentencePattern SIMPLE = new SentencePattern(
			"Sei P ein Punkt");

	public static final SentencePattern ONX = new SentencePattern(
			"Sei P ein Punkt auf X");

	public static final SentencePattern INX = new SentencePattern(
			"Sei P ein Punkt in X");

	public static final SentencePattern CUT = new SentencePattern(
			"Sei P im Schnitt von X und Y");

	public static final SentencePattern OVER = new SentencePattern(
			"Sei P ein Punkt über AB");

	public static final SentencePattern INSIDE = new SentencePattern(
			"Sei P ein Punkt in k");

	public static final SentencePattern OVERINSIDE = new SentencePattern(
			"Sei P ein Punkt im Schnitt von X und Y über AB");

	public static final SentencePattern OUTSIDE = new SentencePattern(
			"Sei P ein Punkt außerhalb von X");

	private GeoObject getCanvasArea()
	{
		Triangle area1 = new Triangle(new XYpoint(0, 0),
				new XYpoint(0, SampleGenerator.getHeight()),
				new XYpoint(SampleGenerator.getWidth(), 0));

		Triangle area2 = new Triangle(
				new XYpoint(SampleGenerator.getWidth(),
						SampleGenerator.getHeight()),
				new XYpoint(0, SampleGenerator.getHeight()),
				new XYpoint(SampleGenerator.getWidth(), 0));

		List<GeoObject> geoList = new ArrayList<GeoObject>();
		geoList.add(area1);
		geoList.add(area2);

		return new CompositeGeoObject(geoList);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.processing.GeoGenerator#generateAndAdd(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder, java.lang.String)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence)
	{
		// if (!pattern.equals(new SentencePattern(sentence)))
		// throw new IllegalArgumentException("Wrong pattern, expected "
		// + pattern + " but got " + sentence);

		List<CompName> compNames = sentence.getCompositeNames();

		CompName point = compNames.get(0);

		XYpoint xy = getCanvasArea()
				.getSamplePoint(SampleGenerator.nextSampling());

		if (xy != null)
		{
			geoHolder.add(point, xy);
			return true;
		}
		else
			return false;

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
	public boolean generateAndAdd(GeoHolder geoHolder, CompName compName)
			throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

}
