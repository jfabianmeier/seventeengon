/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.Angle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class AngleGenerator implements GeoGenerator
{

	public static final SentencePattern ANGLE = new SentencePattern(
			"Sei alpha der Winkel <BAC");
	public static final CompNamePattern BAC = new CompNamePattern(
			new CompName("<BAC"));

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.generator.GeoGenerator#generateAndAdd(de.
	 * fabianmeier.seventeengon.geoobjects.GeoHolder, java.lang.String)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence)
			throws IOException
	{
		if (!geoHolder.generateCompNames(sentence))
			return false;

		if ((new SentencePattern(sentence)).equals(ANGLE))
		{
			List<CompName> compNames = sentence.getCompositeNames();
			CompName alpha = compNames.get(0);
			CompName combined = compNames.get(1);
			geoHolder.add(alpha, geoHolder.get(combined));
			return true;
		}
		throw new IllegalStateException();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seea
	 * de.fabianmeier.seventeengon.generator.GeoGenerator#generateAndAdd(de.
	 * fabianmeier.seventeengon.geoobjects.GeoHolder,
	 * de.fabianmeier.seventeengon.naming.CompName)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, CompName compName)
			throws IOException
	{
		if ((new CompNamePattern(compName)).equals(BAC))
		{
			GeoName nameB = compName.getGeoNames().get(0);
			GeoName nameA = compName.getGeoNames().get(1);
			GeoName nameC = compName.getGeoNames().get(2);

			XYpoint pointB = geoHolder.getPointOrIO(nameB);
			XYpoint pointA = geoHolder.getPointOrIO(nameA);
			XYpoint pointC = geoHolder.getPointOrIO(nameC);

			Angle angle = new Angle(pointB, pointA, pointC);

			geoHolder.add(compName, angle);

			return true;
		}

		throw new IllegalStateException();

	}

}
