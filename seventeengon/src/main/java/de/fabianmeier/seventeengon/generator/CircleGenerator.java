/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.Arc;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;

/**
 * @author JFM
 *
 */
public class CircleGenerator implements GeoGenerator
{

	public static final SentencePattern CIRCLE = new SentencePattern(
			"Sei k der Kreis um P durch Q");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.generator.GeoGenerator#generateAndAdd(de.
	 * fabianmeier.seventeengon.geoobjects.GeoHolder,
	 * de.fabianmeier.seventeengon.naming.Sentence)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence)
			throws IOException
	{
		if (!geoHolder.generateCompNames(sentence))
			return false;

		if ((new SentencePattern(sentence)).equals(CIRCLE))
		{
			List<CompName> compNames = sentence.getCompositeNames();
			CompName k = compNames.get(0);
			CompName P = compNames.get(1);
			CompName Q = compNames.get(2);

			XYpoint pointP = geoHolder.getPointOrIO(P);
			XYpoint pointQ = geoHolder.getPointOrIO(Q);

			Arc circle = new Arc(pointP,
					(new XYvector(pointP, pointQ)).getLength());

			geoHolder.add(k, circle);

			return true;

		}
		throw new IllegalArgumentException(
				sentence + " is not defined operation.");
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
		throw new IllegalArgumentException(
				compName + " is not defined operation.");
	}

}
