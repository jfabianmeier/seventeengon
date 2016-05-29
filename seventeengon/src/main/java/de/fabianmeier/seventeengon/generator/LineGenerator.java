/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.CompNamePattern;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * @author JFM
 *
 */
public class LineGenerator implements GeoGenerator
{

	public static final SentencePattern LINE = new SentencePattern(
			"Sei AB eine Gerade");
	public static final CompNamePattern AB = new CompNamePattern(
			new CompName("AB"));
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

		if ((new SentencePattern(sentence)).equals(LINE))
		{
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
			{
				GeoGeneratorLookup.generateAndAdd(geoHolder,
						new Sentence("Sei " + nameA + " ein Punkt"));

			}

			if (!geoHolder.contains(nameB))
			{
				GeoGeneratorLookup.generateAndAdd(geoHolder,
						new Sentence("Sei " + nameB + " ein Punkt"));

			}

			XYpoint pointA = geoHolder.getPointOrIO(nameA);
			XYpoint pointB = geoHolder.getPointOrIO(nameB);

			XYpoint startPoint = getStartPoint(pointA, pointB,
					geoHolder.getWidth(), geoHolder.getHeight());

			XYpoint endPoint = getEndPoint(pointA, pointB, geoHolder.getWidth(),
					geoHolder.getHeight());

			Line line = new Line(startPoint, endPoint, 0, 1);

			geoHolder.add(compName, line);

			return true;
		}

		throw new IllegalArgumentException(
				compName + " is not defined operation.");
	}
	/**
	 * @param pointA
	 *            point on the line
	 * @param pointB
	 *            second point on the line
	 * @param width
	 *            width of the drawing area
	 * @param height
	 *            height of the drawing area
	 * @return a point some distance outside the drawing area to end the line
	 */
	private XYpoint getEndPoint(XYpoint pointA, XYpoint pointB, double width,
			double height)
	{
		return getStartPoint(pointB, pointA, width, height);
	}
	/**
	 * @param pointA
	 *            point on the line
	 * @param pointB
	 *            second point on the line
	 * @param width
	 *            width of the drawing area
	 * @param height
	 *            height of the drawing area
	 * @return a point some distance outside the drawing area to start the line
	 */
	private XYpoint getStartPoint(XYpoint pointA, XYpoint pointB, double width,
			double height)
	{
		XYvector directionVector = new XYvector(pointA, pointB).normed();

		NumericAngle numericAngle = directionVector.getAngle();

		Line startBorder = null;

		NumericAngle firstQuarter = new NumericAngle(0.25 * Math.PI);
		NumericAngle secondQuarter = new NumericAngle(0.75 * Math.PI);
		NumericAngle thirdQuarter = new NumericAngle(1.25 * Math.PI);
		NumericAngle fourthQuarter = new NumericAngle(1.75 * Math.PI);

		XYpoint leftBottom = new XYpoint(-width, -height);
		XYpoint rightBottom = new XYpoint(2 * width, -height);
		XYpoint rightTop = new XYpoint(2 * width, 2 * height);
		XYpoint leftTop = new XYpoint(-width, 2 * height);

		if (numericAngle.inBetween(firstQuarter, secondQuarter))
		{
			startBorder = new Line(leftBottom, rightBottom, -10, 10);
		}
		if (numericAngle.inBetween(secondQuarter, thirdQuarter))
		{
			startBorder = new Line(rightBottom, rightTop, -10, 10);
		}
		if (numericAngle.inBetween(thirdQuarter, fourthQuarter))
		{
			startBorder = new Line(rightTop, leftTop, -10, 10);
		}
		if (numericAngle.inBetween(fourthQuarter, firstQuarter))
		{
			startBorder = new Line(leftTop, leftBottom, -10, 10);
		}

		double hitLength = 3 * width + 3 * height;

		XYpoint pseudoB = directionVector.shift(pointA);
		Line pseudoLine = new Line(pointA, pseudoB, -hitLength, hitLength);

		GeoObject intersectionObject = startBorder.intersectWith(pseudoLine);

		Set<XYpoint> allIntersectionPoints = intersectionObject
				.getZeroDimensionalPart();

		if (!(allIntersectionPoints.size() == 1))
			throw new IllegalStateException("Set " + allIntersectionPoints
					+ " does not contain exactly one point. IntersectionObject "
					+ intersectionObject + " wrong.");

		return allIntersectionPoints.iterator().next();

	}

}
