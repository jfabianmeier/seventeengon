/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * @author JFM
 *
 */
public class PreservingMap
{
	private final XYvector move;
	private final double turnAngle;
	private final double scale;

	/**
	 * 
	 * @return scale
	 */
	public double getScale()
	{
		return scale;
	}

	private final XYpoint turningCentre;

	private String to;

	/**
	 * Defines a map that preserves angles and ratios of lengths. It is defined
	 * by two points and their respective images.
	 * 
	 * @param pointA
	 *            first point
	 * @param pointB
	 *            second point
	 * @param imageA
	 *            image of first point
	 * @param imageB
	 *            image of second point
	 */
	public PreservingMap(XYpoint pointA, XYpoint pointB, XYpoint imageA, XYpoint imageB)
	{
		this.move = new XYvector(pointA, imageA);
		this.turningCentre = imageA;

		XYpoint B1 = move.shift(pointB);

		XYvector aToB1 = new XYvector(imageA, B1);
		XYvector imageAtoImageB = new XYvector(imageA, imageB);
		turnAngle = NumericAngle.angleDifference(aToB1.getAngle(), imageAtoImageB.getAngle());

		scale = imageAtoImageB.getLength() / aToB1.getLength();

		to = pointA + " -> " + imageA + ", " + pointB + " -> " + imageB + " move: " + move + " turningCentre: "
				+ turningCentre + " turningAngle " + turnAngle;

	}

	@Override
	public String toString()
	{
		return to;
	}

	/**
	 * 
	 * @param point
	 *            point
	 * @return image under the map
	 */
	public XYpoint mapPoint(XYpoint point)
	{
		point = move.shift(point);
		point = turnAround(turningCentre, turnAngle, point);
		point = scaleFrom(turningCentre, scale, point);

		return point;

	}

	/**
	 * @param scalingCentre
	 *            centre for scaling
	 * @param scale
	 *            the scaling factor
	 * @param point
	 *            the point to be moved
	 * @return The mapped point
	 */
	public static XYpoint scaleFrom(XYpoint scalingCentre, double scale, XYpoint point)
	{
		XYvector shiftVector = new XYvector(scalingCentre, point);

		shiftVector = shiftVector.multiplyBy(scale);

		return shiftVector.shift(scalingCentre);

	}

	/**
	 * 
	 * @param turningCentre
	 *            centre for turning
	 * @param turnAngle
	 *            the angle of turning
	 * @param point
	 *            the point to be mapped
	 * @return the mapped point
	 */
	public static XYpoint turnAround(XYpoint turningCentre, double turnAngle, XYpoint point)
	{
		if (turningCentre.equals(point))
			return point;
		XYvector shiftVector = new XYvector(turningCentre, point);

		NumericAngle ang = shiftVector.getAngle().addtoAngle(turnAngle);

		XYvector neu = new XYvector(shiftVector.getLength(), ang);

		return neu.shift(turningCentre);

	}

}
