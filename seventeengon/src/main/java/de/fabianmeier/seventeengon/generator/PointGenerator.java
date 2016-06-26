/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.geoobjects.Measurement;
import de.fabianmeier.seventeengon.intersection.IntersectionManager;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.shapes.DifferenceObject;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;

/**
 * @author JFM
 *
 */
public class PointGenerator implements GeoGenerator
{

	public static final SentencePattern CUT = new SentencePattern(
			"Sei P im Schnitt von X und Y");

	public static final SentencePattern INX = new SentencePattern(
			"Sei P ein Punkt in X");

	public static final SentencePattern ONX = new SentencePattern(
			"Sei P ein Punkt auf X");

	public static final SentencePattern OUTSIDE = new SentencePattern(
			"Sei P ein Punkt außerhalb von X");

	public static final SentencePattern OVER = new SentencePattern(
			"Sei P ein Punkt über AB");

	public static final SentencePattern OVERINSIDE = new SentencePattern(
			"Sei P ein Punkt im Schnitt von X und Y über AB");

	public static final SentencePattern SIMPLE = new SentencePattern(
			"Sei P ein Punkt");

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.processing.GeoGenerator#generateAndAdd(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder, java.lang.String)
	 */
	@Override
	public boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence)
			throws IOException
	{
		if (!geoHolder.generateCompNames(sentence))
			return false;

		if ((new SentencePattern(sentence)).equals(SIMPLE))
		{
			List<CompName> compNames = sentence.getCompositeNames();

			CompName point = compNames.get(0);

			return samplePointFromArea(geoHolder, point,
					geoHolder.getCanvasArea());
		}

		if ((new SentencePattern(sentence)).equals(ONX))
		{
			List<CompName> compNames = sentence.getCompositeNames();

			CompName point = compNames.get(0);
			CompName objName = compNames.get(1);

			if (!geoHolder.contains(objName))
				throw new IOException(
						"The name " + objName + " does not exist.");

			GeoObject area = geoHolder.get(objName).getBoundary();

			return samplePointFromArea(geoHolder, point, area);
		}

		if ((new SentencePattern(sentence)).equals(INX))
		{
			List<CompName> compNames = sentence.getCompositeNames();

			CompName point = compNames.get(0);
			CompName objName = compNames.get(1);

			if (!geoHolder.contains(objName))
				throw new IOException(
						"The name " + objName + " does not exist.");

			GeoObject area = geoHolder.get(objName).getFilledObject();

			return samplePointFromArea(geoHolder, point, area);
		}

		if ((new SentencePattern(sentence)).equals(CUT))
		{
			List<CompName> compNames = sentence.getCompositeNames();

			CompName objName1 = compNames.get(1);
			CompName objName2 = compNames.get(2);

			if (!geoHolder.contains(objName1))
				throw new IOException(
						"The name " + objName1 + " does not exist.");

			if (!geoHolder.contains(objName2))
				throw new IOException(
						"The name " + objName2 + " does not exist.");

			CompName point = compNames.get(0);

			GeoObject area = geoHolder.get(objName1);
			area = area.intersectWith(geoHolder.get(objName2));

			return samplePointFromArea(geoHolder, point, area);
		}

		if ((new SentencePattern(sentence)).equals(OVER))
		{
			List<CompName> compNames = sentence.getCompositeNames();

			CompName objName1 = compNames.get(1);

			if (!geoHolder.contains(objName1))
				throw new IOException(
						"The name " + objName1 + " does not exist.");

			CompName point = compNames.get(0);

			GeoObject geoLine = geoHolder.get(objName1);

			if (!(geoLine instanceof Line))
				throw new IOException(objName1 + " does not represent Line");

			Line line = (Line) geoLine;

			GeoObject combinedArea = getAreaOverLine(line, geoHolder);

			return samplePointFromArea(geoHolder, point, combinedArea);
		}

		if ((new SentencePattern(sentence)).equals(OVERINSIDE))
		{
			List<CompName> compNames = sentence.getCompositeNames();

			CompName X = compNames.get(1);

			if (!geoHolder.contains(X))
				throw new IOException("The name " + X + " does not exist.");

			CompName Y = compNames.get(2);

			if (!geoHolder.contains(Y))
				throw new IOException("The name " + Y + " does not exist.");

			CompName AB = compNames.get(3);

			if (!geoHolder.contains(Y))
				throw new IOException("The name " + AB + " does not exist.");

			GeoObject geoLine = geoHolder.get(AB);

			if (!(geoLine instanceof Line))
				throw new IOException(AB + " does not represent Line");

			Line line = (Line) geoLine;

			GeoObject combinedArea = getAreaOverLine(line, geoHolder);

			combinedArea = combinedArea.intersectWith(geoHolder.get(X));
			combinedArea = combinedArea.intersectWith(geoHolder.get(Y));

			CompName point = compNames.get(0);

			return samplePointFromArea(geoHolder, point, combinedArea);
		}

		if ((new SentencePattern(sentence)).equals(OUTSIDE))
		{
			List<CompName> compNames = sentence.getCompositeNames();

			CompName objName1 = compNames.get(1);

			if (!geoHolder.contains(objName1))
				throw new IOException(
						"The name " + objName1 + " does not exist.");

			CompName point = compNames.get(0);

			DifferenceObject diff = new DifferenceObject(
					geoHolder.getCanvasArea(), geoHolder.get(objName1));

			XYpoint xy = diff.getSamplePoint(geoHolder.nextSampling());

			if (xy != null)
			{
				geoHolder.add(point, xy);
				return true;
			}
			else
				return false;
		}

		throw new IllegalArgumentException(
				sentence + " is not a valid pattern for " + this);

	}

	/**
	 * 
	 * 
	 * @param line
	 *            A line
	 * @param geoHolder
	 *            geoHolder
	 * @return the area inside the geoHolder drawing area that lies above the
	 *         (virtually prolonged Line given by line).
	 */
	private GeoObject getAreaOverLine(Line line, GeoHolder geoHolder)
	{
		XYpoint leftBottom = new XYpoint(0, 0);
		XYpoint rightBottom = new XYpoint(0, geoHolder.getWidth());
		XYpoint leftTop = new XYpoint(geoHolder.getHeight(), 0);
		XYpoint rightTop = new XYpoint(geoHolder.getWidth(),
				geoHolder.getHeight());

		double longDistance = 3 * geoHolder.getWidth()
				+ 3 * geoHolder.getHeight();

		XYvector direction = new XYvector(line.getStartPoint(),
				line.getEndPoint()).normed();

		Line prolongedLine = new Line(line.getStartPoint(),
				direction.shift(line.getStartPoint()), -longDistance,
				longDistance);

		Line leftLine = new Line(leftBottom, leftTop, 0, 1);
		Line rightLine = new Line(rightBottom, rightTop, 0, 1);
		Line bottomLine = new Line(leftBottom, rightBottom, 0, 1);
		Line topLine = new Line(leftTop, rightTop, 0, 1);

		Set<XYpoint> cornerPoints = new HashSet<XYpoint>();

		cornerPoints.addAll(
				prolongedLine.intersectWith(leftLine).getZeroDimensionalPart());
		cornerPoints.addAll(prolongedLine.intersectWith(rightLine)
				.getZeroDimensionalPart());
		cornerPoints.addAll(
				prolongedLine.intersectWith(topLine).getZeroDimensionalPart());
		cornerPoints.addAll(prolongedLine.intersectWith(bottomLine)
				.getZeroDimensionalPart());

		if (Measurement.aboveLine(leftBottom, prolongedLine))
			cornerPoints.add(leftBottom);

		if (Measurement.aboveLine(rightBottom, prolongedLine))
			cornerPoints.add(rightBottom);

		if (Measurement.aboveLine(rightTop, prolongedLine))
			cornerPoints.add(rightTop);

		if (Measurement.aboveLine(leftTop, prolongedLine))
			cornerPoints.add(leftTop);

		return IntersectionManager.triangulizeConvexSet(cornerPoints);

		// XYpoint startPoint = line.getStartPoint();
		// XYpoint endPoint = line.getEndPoint();
		// XYvector direction = (new XYvector(startPoint, endPoint)).normed();
		// XYvector orthogonalDirection = new XYvector(-direction.getyMove(),
		// direction.getxMove());
		//
		// XYpoint overStart = orthogonalDirection.multiplyBy(10000)
		// .shift(startPoint);
		// XYpoint overEnd =
		// orthogonalDirection.multiplyBy(10000).shift(endPoint);
		//
		// Triangle firstTriangle = new Triangle(startPoint, endPoint,
		// overStart);
		// Triangle secondTriangle = new Triangle(startPoint, endPoint,
		// overEnd);
		//
		// GeoObject combinedArea = new CompositeGeoObject(firstTriangle,
		// secondTriangle);
		// return combinedArea;
	}

	private boolean samplePointFromArea(GeoHolder geoHolder, CompName point,
			GeoObject area)
	{
		area = area.intersectWith(geoHolder.getSamplingArea());

		XYpoint xy = area.getSamplePoint(geoHolder.nextSampling());

		if (xy != null)
		{
			geoHolder.add(point, xy);
			return true;
		}
		else
			return false;
	}

}
