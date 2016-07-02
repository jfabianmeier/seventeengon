/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;

/**
 * @author JFM
 *
 */
public class RectangleFit
{
	/**
	 * Returns a map which maps the points to a rectangle of given width and
	 * height, with a good fit determined by heuristic method.
	 * 
	 * @param width
	 *            width of rectangle
	 * @param height
	 *            height of rectangle
	 * @param points
	 *            the points which should be fitted
	 * @return the Map to maps the points into the rectangle
	 */
	public static PreservingMap fitTo(double width, double height,
			Set<XYpoint> points)
	{

		if (points.size() == 0)
			return new PreservingMap(new XYpoint(0, 0), new XYpoint(0, 1),
					new XYpoint(0, 0), new XYpoint(0, 1));

		if (points.size() == 1)
		{
			return new PreservingMap(new XYpoint(0, 0),
					points.iterator().next(), new XYpoint(0, 0),
					new XYpoint(width / 2, height / 2));

		}

		PreservingMap bestPreMap = getBestPreMap(width, height, points);

		//
		// XYvector shiftVector = new XYvector(width / 10 - left,
		// height / 10 - bottom);

		double scaleRatio = getScaleRatio(width, height, points, bestPreMap);

		XYpoint initPoint = points.iterator().next();

		XYpoint initMapPoint = PreservingMap.scaleFrom(new XYpoint(0, 0),
				scaleRatio, bestPreMap.mapPoint(initPoint));

		double left = initMapPoint.getX();
		double bottom = initMapPoint.getY();
		double top = initMapPoint.getY();
		double right = initMapPoint.getX();

		for (XYpoint point : points)
		{
			XYpoint mapPoint = PreservingMap.scaleFrom(new XYpoint(0, 0),
					scaleRatio, bestPreMap.mapPoint(point));

			if (mapPoint.getX() < left)
				left = mapPoint.getX();
			if (mapPoint.getY() < bottom)
				bottom = mapPoint.getY();
			if (mapPoint.getX() > right)
				right = mapPoint.getX();
			if (mapPoint.getY() > top)
				top = mapPoint.getY();
		}

		XYpoint leftBottomCorner = new XYpoint(left, bottom);
		XYpoint rightTopCorner = new XYpoint(right, top);

		List<XYpoint> pointList = new ArrayList<XYpoint>(points);

		XYpoint firstPoint = pointList.get(0);
		XYpoint scaledShiftedFirstImage = getShiftedAndScaledPoint(bestPreMap,
				leftBottomCorner, scaleRatio, rightTopCorner, firstPoint, width,
				height);

		XYpoint secondPoint = pointList.get(1);
		XYpoint scaledShiftedSecondImage = getShiftedAndScaledPoint(bestPreMap,
				leftBottomCorner, scaleRatio, rightTopCorner, secondPoint,
				width, height);

		PreservingMap backPre = new PreservingMap(firstPoint, secondPoint,
				scaledShiftedFirstImage, scaledShiftedSecondImage);

		// XYpoint zero = new XYpoint(0, 0);
		// XYpoint zeroImage = bestPreMap.mapPoint(zero);
		// XYpoint widthZero = new XYpoint(width, 0);
		// XYpoint widthImage = bestPreMap.mapPoint(widthZero);
		//
		// XYvector zeroVector = new XYvector(corner, zeroImage);
		//
		// XYvector zeroWidthVector = new XYvector(zeroImage, widthImage);
		//
		// zeroWidthVector = zeroWidthVector.multiplyBy(scaleRatio);
		//
		// XYvector shift = new XYvector(0.1 * width + left,
		// 0.1 * height + bottom);
		//
		// XYpoint neuZeroImage = shift.shift(zeroImage);
		//
		// XYpoint neuWidthImage =
		// zeroWidthVector.addTo(shift).shift(zeroImage);
		//
		// PreservingMap backPre = new PreservingMap(zero, widthZero,
		// neuZeroImage,
		// neuWidthImage);

		return backPre;

	}

	private static XYpoint getShiftedAndScaledPoint(PreservingMap bestPreMap,
			XYpoint leftBottom, double scaleRatio, XYpoint rightTop,
			XYpoint firstPoint, double width, double height)
	{
		XYpoint firstImage = bestPreMap.mapPoint(firstPoint);

		XYpoint scaledFirstImage = PreservingMap.scaleFrom(new XYpoint(0, 0),
				scaleRatio, firstImage);

		XYpoint newCorner = new XYpoint(
				0.5 * (width - rightTop.getX() + leftBottom.getX()),
				0.5 * (height - rightTop.getY() + leftBottom.getY()));

		XYvector shiftVector = new XYvector(leftBottom, newCorner);

		XYpoint shiftedScaledFirstImage = shiftVector.shift(scaledFirstImage);

		return shiftedScaledFirstImage;
	}

	private static double getScaleRatio(double width, double height,
			Set<XYpoint> points, PreservingMap bestPreMap)
	{
		Set<XYpoint> unscaledPoints = new HashSet<XYpoint>(points);

		for (XYpoint point : points)
		{
			XYpoint neuPoint = bestPreMap.mapPoint(point);
			unscaledPoints.add(neuPoint);
		}

		double unscaledWidth = getWidth(unscaledPoints);
		double unscaledHeight = getHeight(unscaledPoints);

		double scaleRatio = 1;

		if (width / unscaledWidth < height / unscaledHeight)
		{
			scaleRatio = width / unscaledWidth;
		}
		else
		{
			scaleRatio = height / unscaledHeight;
		}

		scaleRatio = 0.8 * scaleRatio;
		return scaleRatio;
	}

	private static PreservingMap getBestPreMap(double width, double height,
			Set<XYpoint> points)
	{
		double bestFit = Math
				.abs(width / height - getWidthToHeightRatio(points));

		PreservingMap bestPreMap = new PreservingMap(new XYpoint(0, 0),
				new XYpoint(0, 1), new XYpoint(0, 0), new XYpoint(0, 1));

		XYpoint standardA = new XYpoint(0, 0);

		List<XYpoint> standardBList = new ArrayList<XYpoint>();

		standardBList.add(new XYpoint(0, height));
		standardBList.add(new XYpoint(width, height));
		standardBList.add(new XYpoint(width, 0));

		for (XYpoint standardB : standardBList)
		{

			for (XYpoint neuA : points)
				for (XYpoint neuB : points)
					if (neuA != neuB)
					{
						PreservingMap preMap = new PreservingMap(neuA, neuB,
								standardA, standardB);
						Set<XYpoint> unscaledPoints = new HashSet<XYpoint>(
								points);

						for (XYpoint point : points)
						{
							XYpoint neuPoint = preMap.mapPoint(point);
							unscaledPoints.add(neuPoint);
						}

						double ratio = getWidthToHeightRatio(unscaledPoints);

						double neuFit = Math.abs(width / height - ratio);

						if (neuFit < bestFit)
						{
							bestFit = neuFit;
							bestPreMap = preMap;
						}

					}
		}
		return bestPreMap;
	}

	/**
	 * 
	 * @param points
	 *            Set of points
	 * @return the maximal width
	 */
	public static double getWidth(Set<XYpoint> points)
	{
		XYpoint firstPoint = points.iterator().next();

		double left = firstPoint.getX();
		double right = firstPoint.getX();

		for (XYpoint point : points)
		{
			if (point.getX() < left)
				left = point.getX();

			if (point.getX() > right)
				right = point.getX();

		}

		return right - left;
	}

	/**
	 * 
	 * @param points
	 *            Set of points
	 * @return the maximal height
	 */
	public static double getHeight(Set<XYpoint> points)
	{
		XYpoint firstPoint = points.iterator().next();

		double top = firstPoint.getY();
		double bottom = firstPoint.getY();

		for (XYpoint point : points)
		{

			if (point.getY() < bottom)
				bottom = point.getY();

			if (point.getY() > top)
				top = point.getY();
		}

		return top - bottom;
	}

	/**
	 * 
	 * @param points
	 *            one or more points
	 * @return the ratio of maximal width to maximal height
	 */
	public static double getWidthToHeightRatio(Set<XYpoint> points)
	{
		if (points.size() <= 1)
			return 1;

		else
			return getWidth(points) / getHeight(points);
	}

}
