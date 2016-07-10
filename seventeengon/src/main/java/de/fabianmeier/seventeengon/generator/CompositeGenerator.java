/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.CompositeGeoObject;
import de.fabianmeier.seventeengon.shapes.GeoObject;

/**
 * @author JFM
 *
 */
public class CompositeGenerator implements GeoGenerator
{

	public static final SentencePattern ELEMENTS = new SentencePattern(
			"Sei a die Menge A,B");
	public static final CompNamePattern AB = new CompNamePattern(
			new CompName("A,B"));
	public static final CompNamePattern ABC = new CompNamePattern(
			new CompName("A,B,C"));
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

		if ((new SentencePattern(sentence)).equals(ELEMENTS))
		{
			List<CompName> compNames = sentence.getCompositeNames();
			CompName a = compNames.get(0);
			CompName compAB = compNames.get(1);

			geoHolder.add(a, geoHolder.get(compAB));
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
		if ((new CompNamePattern(compName)).equals(AB))
		{
			GeoName nameA = compName.getGeoNames().get(0);
			GeoName nameB = compName.getGeoNames().get(1);

			if (!geoHolder.contains(nameA))
				throw new IOException("Object " + nameA + " does not exist.");

			if (!geoHolder.contains(nameB))
				throw new IOException("Object " + nameB + " does not exist.");

			Set<GeoObject> geoObjects = new HashSet<GeoObject>();

			geoObjects.add(geoHolder.get(nameA));
			geoObjects.add(geoHolder.get(nameB));

			geoHolder.add(compName, new CompositeGeoObject(geoObjects));

			return true;
		}

		if ((new CompNamePattern(compName)).equals(ABC))
		{
			GeoName nameA = compName.getGeoNames().get(0);
			GeoName nameB = compName.getGeoNames().get(1);
			GeoName nameC = compName.getGeoNames().get(2);

			if (!geoHolder.contains(nameA))
				throw new IOException("Object " + nameA + " does not exist.");

			if (!geoHolder.contains(nameB))
				throw new IOException("Object " + nameB + " does not exist.");

			if (!geoHolder.contains(nameC))
				throw new IOException("Object " + nameB + " does not exist.");

			Set<GeoObject> geoObjects = new HashSet<GeoObject>();

			geoObjects.add(geoHolder.get(nameA));
			geoObjects.add(geoHolder.get(nameB));
			geoObjects.add(geoHolder.get(nameC));

			geoHolder.add(compName, new CompositeGeoObject(geoObjects));

			return true;
		}

		throw new IllegalArgumentException(
				compName + " is not defined operation.");
	}

}
