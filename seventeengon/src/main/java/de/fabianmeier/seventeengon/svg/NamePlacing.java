/**
 * 
 */
package de.fabianmeier.seventeengon.svg;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * @author JFM
 *
 */
public class NamePlacing
{
	/**
	 * 
	 * @param direction1
	 *            first vector of angle
	 * @param direction2
	 *            second vector of angle
	 * @param boundingWidth
	 *            width of the rectangle
	 * @param boundingHeight
	 *            height of the rectangle
	 * @return the midpoint of the rectangle, fitted into the angle.
	 */
	public static XYvector getMidVector(XYvector direction1, XYvector direction2, double boundingWidth,
			double boundingHeight)
	{
		int quadrant1 = getQuadrant(direction1);
		int quadrant2 = getQuadrant(direction2);

		if (quadrant2 < quadrant1)
			quadrant2 += 4;

		if (quadrant1 == quadrant2)
		{
			if (NumericAngle.angleDifference(direction1.getAngle(), direction2.getAngle()) > Math.PI
					|| direction1.getAngle().equals(direction2.getAngle()))
			{
				quadrant2 += 4;
			}
		}

		if (quadrant2 - quadrant1 >= 2)
		{
			return getQuadrantVector(quadrant1 + 1, boundingWidth / 2, boundingHeight / 2);
		} else if (quadrant2 - quadrant1 == 1)
		{
			return neighbouringQuadrant(direction1, direction2, boundingWidth, boundingHeight, quadrant1, quadrant2);

		} else
		{

			return sameQuadrant(direction1, direction2, boundingWidth, boundingHeight, quadrant1);

		}

	}

	/**
	 * @param direction1
	 *            first direction
	 * @param direction2
	 *            second direction
	 * @param boundingWidth
	 *            width of the rectangle
	 * @param boundingHeight
	 *            height of the rectangle
	 * @param quadrant
	 *            the quadrant to work in
	 * @return the vector given the midpoint of the rectangle to place
	 */
	private static XYvector sameQuadrant(XYvector direction1, XYvector direction2, double boundingWidth,
			double boundingHeight, int quadrant)
	{
		Line diagonalLine = null;

		if (quadrant == 1)
		{
			diagonalLine = new Line(new XYpoint(0, boundingHeight), new XYpoint(boundingWidth, 0), 0, 1);
		}
		if (quadrant == 2)
		{
			diagonalLine = new Line(new XYpoint(0, boundingHeight), new XYpoint(-boundingWidth, 0), 0, 1);
		}
		if (quadrant == 3)
		{
			diagonalLine = new Line(new XYpoint(0, -boundingHeight), new XYpoint(-boundingWidth, 0), 0, 1);
		}
		if (quadrant == 4)
		{
			diagonalLine = new Line(new XYpoint(0, -boundingHeight), new XYpoint(boundingWidth, 0), 0, 1);
		}

		double diagonalLength = Math.sqrt(boundingWidth * boundingWidth + boundingHeight * boundingHeight);

		XYpoint origin = new XYpoint(0, 0);

		Line direction1Line = new Line(origin, direction1.multiplyBy(diagonalLength).shift(origin), 0, 1);

		Line direction2Line = new Line(origin, direction2.multiplyBy(diagonalLength).shift(origin), 0, 1);

		XYpoint dirPoint1 = (XYpoint) diagonalLine.intersectWith(direction1Line).normalize();
		XYpoint dirPoint2 = (XYpoint) diagonalLine.intersectWith(direction2Line).normalize();

		double length12 = new Line(dirPoint1, dirPoint2, 0, 1).getLength();

		double factor = diagonalLength / length12;

		XYpoint midPoint = new XYpoint(dirPoint1.getX() / 2 + dirPoint2.getX() / 2,
				dirPoint1.getY() / 2 + dirPoint2.getY() / 2);

		return new XYvector(origin, midPoint).multiplyBy(factor);
	}

	/**
	 * @param direction1
	 *            first direction
	 * @param direction2
	 *            second direction
	 * @param boundingWidth
	 *            width of the rectangle
	 * @param boundingHeight
	 *            height of the rectangle
	 * @param quadrant1
	 *            first quadrant
	 * @param quadrant2
	 *            second quadrant
	 * @return the midpoint of the rectangle that should be placed
	 */
	private static XYvector neighbouringQuadrant(XYvector direction1, XYvector direction2, double boundingWidth,
			double boundingHeight, int quadrant1, int quadrant2)
	{
		if (quadrant1 == 1)
		{
			if (DMan.same(direction1.getyMove(), 0))
				return getQuadrantVector(quadrant1, boundingWidth / 2, boundingHeight / 2);
			if (DMan.same(direction2.getyMove(), 0))
				return getQuadrantVector(quadrant2, boundingWidth / 2, boundingHeight / 2);

			double wide = direction1.getxMove() / direction1.getyMove() - direction2.getxMove() / direction2.getyMove();

			double factor = boundingWidth / wide;

			return new XYvector(factor * (direction1.getxMove() / direction1.getyMove()) - boundingWidth / 2,
					factor + boundingHeight / 2);
		}
		if (quadrant1 == 2)
		{
			if (DMan.same(direction1.getxMove(), 0))
				return getQuadrantVector(quadrant1, boundingWidth / 2, boundingHeight / 2);
			if (DMan.same(direction2.getxMove(), 0))
				return getQuadrantVector(quadrant2, boundingWidth / 2, boundingHeight / 2);

			double tall = -direction1.getyMove() / direction1.getxMove()
					+ direction2.getyMove() / direction2.getxMove();

			double factor = boundingHeight / tall;

			return new XYvector(-factor - boundingWidth / 2,
					-direction1.getyMove() / direction1.getxMove() * factor - boundingHeight / 2);
		}
		if (quadrant1 == 3)
		{
			if (DMan.same(direction1.getyMove(), 0))
				return getQuadrantVector(quadrant1, boundingWidth / 2, boundingHeight / 2);
			if (DMan.same(direction2.getyMove(), 0))
				return getQuadrantVector(quadrant2, boundingWidth / 2, boundingHeight / 2);

			double wide = +direction1.getxMove() / direction1.getyMove()
					- direction2.getxMove() / direction2.getyMove();

			double factor = boundingWidth / wide;

			return new XYvector(factor * (direction1.getxMove() / direction1.getyMove()) - boundingWidth / 2,
					-factor - boundingHeight / 2);
		}
		if (quadrant1 == 4)
		{
			if (DMan.same(direction1.getxMove(), 0))
				return getQuadrantVector(quadrant1, boundingWidth / 2, boundingHeight / 2);
			if (DMan.same(direction2.getxMove(), 0))
				return getQuadrantVector(quadrant2, boundingWidth / 2, boundingHeight / 2);

			double tall = -direction1.getyMove() / direction1.getxMove()
					+ direction2.getyMove() / direction2.getxMove();

			double factor = boundingHeight / tall;

			return new XYvector(factor + boundingWidth / 2,
					direction1.getyMove() / direction1.getxMove() * factor + boundingHeight / 2);
		}
		throw new IllegalStateException("Should not be reached");
	}

	/**
	 * @param direction
	 *            vector
	 * @return the quadrant
	 */
	private static int getQuadrant(XYvector direction)
	{
		NumericAngle num = direction.getAngle();

		NumericAngle zero = new NumericAngle(0);
		NumericAngle ninety = new NumericAngle(Math.PI / 2);
		NumericAngle onehundredeighty = new NumericAngle(Math.PI);
		NumericAngle twohundredseventy = new NumericAngle(3 * Math.PI / 2);

		if (num.inBetween(zero, ninety))
			return 1;

		if (num.inBetween(ninety, onehundredeighty))
			return 2;

		if (num.inBetween(onehundredeighty, twohundredseventy))
			return 3;

		if (num.inBetween(twohundredseventy, zero))
			return 4;

		throw new IllegalStateException("Angle error.");

	}

	/**
	 * @param quadrant
	 *            the quadrant
	 * @param xOffset
	 *            horizontal vector offset
	 * @param yOffset
	 *            vertical vector offset
	 * @return the vector with the offset in the right quadrant
	 */
	private static XYvector getQuadrantVector(int quadrant, double xOffset, double yOffset)
	{
		quadrant = quadrant % 4;
		if (quadrant == 0)
			quadrant = 4;

		switch (quadrant)
		{
		case 1:
			return new XYvector(xOffset, yOffset);
		case 2:
			return new XYvector(-xOffset, yOffset);
		case 3:
			return new XYvector(-xOffset, -yOffset);
		case 4:
			return new XYvector(xOffset, -yOffset);
		default:
			throw new IllegalStateException("No quadrant");
		}

	}
}
