/**
 * 
 */
package de.fabianmeier.seventeengon.svg;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.AttributedString;
import java.util.List;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.shapes.Angle;
import de.fabianmeier.seventeengon.shapes.Arc;
import de.fabianmeier.seventeengon.shapes.Circle;
import de.fabianmeier.seventeengon.shapes.CompositeGeoObject;
import de.fabianmeier.seventeengon.shapes.GeoObject;
import de.fabianmeier.seventeengon.shapes.Line;
import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.GeoVisible;
import de.fabianmeier.seventeengon.util.NumericAngle;

/**
 * @author JFM
 *
 */
public class SVGcanvas implements GeoCanvas
{
	private static final Logger LOG = LogManager.getLogger(SVGcanvas.class);

	/**
	 * @param graphics
	 *            A graphics object
	 * @param visible
	 *            A visibility description
	 */
	private static void setColourAndStroke(Graphics2D graphics,
			GeoVisible visible)
	{
		final float dash1[] = {10.0f};
		final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

		if (visible.isDotted())
			graphics.setStroke(dashed);

		final BasicStroke filled = new BasicStroke(3);

		if (visible.isMarked())
			graphics.setStroke(filled);

	}
	private final double height;
	private final SVGGraphics2D svgGenerator;

	private final double width;

	/**
	 * Generates a canvas for svg generation
	 * 
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	public SVGcanvas(double width, double height)
	{
		this.width = width;
		this.height = height;

		DOMImplementation domImpl = GenericDOMImplementation
				.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);

		// Create an instance of the SVG Generator.
		svgGenerator = new SVGGraphics2D(document);

		svgGenerator.setSVGCanvasSize(new Dimension((int) width, (int) height));

		svgGenerator.setClip(0, 0, (int) width, (int) height);

	}

	private AttributedString convertToAttributedString(GeoName geoName)
	{
		String unicodeString = geoName.toUnicodeString();
		AttributedString attString = new AttributedString(unicodeString);

		if (unicodeString.length() > 1)
		{
			attString.addAttribute(TextAttribute.SUPERSCRIPT,
					TextAttribute.SUPERSCRIPT_SUB, 1, unicodeString.length());
		}
		return attString;
	}

	/**
	 * The drawing is empty
	 * 
	 * @param angle
	 *            An angle
	 */
	private void draw(Angle angle)
	{

	}

	/**
	 * 
	 * @param arc
	 *            An arc
	 */
	public void draw(Arc arc)
	{
		NumericAngle startAngle = arc.getStartAngle();
		NumericAngle endAngle = arc.getEndAngle();
		XYpoint centre = arc.getCentre();
		double radius = arc.getRadius();

		if (!startAngle.equals(endAngle))
		{

			svgGenerator.draw(new Arc2D.Double(centre.getX() - radius,
					height - centre.getY() - radius, 2 * radius, 2 * radius,
					-startAngle.asDouble() * 180 / Math.PI,
					-NumericAngle.angleDifference(startAngle, endAngle) * 180
							/ Math.PI,
					Arc2D.OPEN));
		}
		else
		{

			svgGenerator.draw(new Ellipse2D.Double(centre.getX() - radius,
					height - centre.getY() - radius, 2 * radius, 2 * radius));
		}

	}

	/**
	 * 
	 * @param circle
	 *            A circle
	 */
	private void draw(Circle circle)
	{
		GeoObject boundary = circle.getBoundary();
		draw(boundary);

	}

	private void draw(CompositeGeoObject geoObject)
	{
		for (GeoObject geo : geoObject.getSubObjects())
		{
			draw(geo);
		}

	}

	/**
	 * @param geo
	 *            A geoObject
	 */
	private void draw(GeoObject geo)
	{
		if (geo instanceof Angle)
			draw((Angle) geo);
		if (geo instanceof Arc)
			draw((Arc) geo);
		if (geo instanceof Circle)
			draw((Circle) geo);
		if (geo instanceof CompositeGeoObject)
			draw((CompositeGeoObject) geo);
		if (geo instanceof Line)
			draw((Line) geo);
		if (geo instanceof Triangle)
			draw((Triangle) geo);
		if (geo instanceof XYpoint)
			draw((XYpoint) geo);

	}

	/**
	 * 
	 * @param line
	 *            A Line
	 */
	public void draw(Line line)
	{

		svgGenerator.draw(new Line2D.Double(line.getStartPoint().getX(),
				height - line.getStartPoint().getY(), line.getEndPoint().getX(),
				height - line.getEndPoint().getY()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawAngle(de.fabianmeier
	 * .seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYvector,
	 * de.fabianmeier.seventeengon.shapes.XYvector, java.lang.String,
	 * de.fabianmeier.seventeengon.util.GeoVisible)
	 */
	// @Override
	// public void drawAngle(XYpoint vertex, XYvector direction1,
	// XYvector direction2, GeoVisible visible)
	// {
	//
	// setColourAndStroke(svgGenerator, visible);
	//
	// NumericAngle startAngle = direction1.getAngle();
	// NumericAngle endAngle = direction2.getAngle();
	//
	// double radius = 40;
	//
	// if (!visible.isInvisible())
	// {
	// if (!startAngle.equals(endAngle))
	// {
	//
	// svgGenerator.draw(new Arc2D.Double(vertex.getX() - radius,
	// height - vertex.getY() - radius, 2 * radius, 2 * radius,
	// -startAngle.asDouble() * 180 / Math.PI,
	// -NumericAngle.angleDifference(startAngle, endAngle)
	// * 180 / Math.PI,
	// Arc2D.OPEN));
	// }
	// else
	// {
	//
	// svgGenerator.draw(new Ellipse2D.Double(vertex.getX() - radius,
	// height - vertex.getY() - radius, 2 * radius,
	// 2 * radius));
	// }

	// NumericAngle middleAngle = startAngle.addtoAngle(
	// NumericAngle.angleDifference(startAngle, endAngle) / 2);
	//
	// XYvector angleVector = new XYvector(radius, middleAngle);
	//
	// XYpoint drawPoint = angleVector.shift(vertex);
	//
	// if (!visible.isNameHidden())
	// drawName(label, drawPoint, startAngle, endAngle);

	// }
	//
	// }

	private void draw(Triangle triangle)
	{
		draw(new Line(triangle.getPointA(), triangle.getPointB(), 0, 1));
		draw(new Line(triangle.getPointB(), triangle.getPointC(), 0, 1));
		draw(new Line(triangle.getPointC(), triangle.getPointA(), 0, 1));
	}

	/**
	 * 
	 * @param point
	 *            A Point
	 */
	public void draw(XYpoint point)
	{

		svgGenerator.fill(new Ellipse2D.Double(point.getX() - width / 200,
				height - point.getY() - width / 200, width / 100, width / 100));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#draw(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder)
	 */
	@Override
	public void drawAll(GeoHolder geoHolder)
	{
		GeoHolder fittedHolder = geoHolder.turnAndFitIntoCanvas();

		for (CompName compName : fittedHolder.getCompNames())
		{
			GeoObject geo = geoHolder.get(compName);
			GeoVisible visibility = geoHolder.getVisibility(compName);

			if (!visibility.isInvisible())
			{
				setColourAndStroke(svgGenerator, visibility);
				draw(geo);
			}
		}

		for (CompName compName : fittedHolder.getCompNames())
		{
			GeoObject geo = geoHolder.get(compName);
			GeoVisible visibility = geoHolder.getVisibility(compName);

			if (compName.getGeoNames().size() > 1)
				continue;

			GeoName geoName = compName.getGeoNames().get(0);

			if (!visibility.isNameHidden())
			{
				List<Angle> nameDrawingAngles = geo.getNameDrawingAngles();

				boolean success = false;
				double factor = 1;

				while (!success)
				{

					for (Angle angle : nameDrawingAngles)
					{
						boolean localSuccess = nameTry(fittedHolder, geoName,
								angle, factor);

						if (localSuccess)
						{
							success = true;
							break;
						}

					}

					factor *= 1.1;

					if (factor > 4)
						break;

				}

			}

		}

	}

	/**
	 * @param label
	 *            Label to draw
	 * @param drawPoint
	 *            point to which the label should be near
	 * @param startAngle
	 *            startAngle for the angle in which the label should be
	 * @param endAngle
	 *            endAngle for the angle in which the label should be
	 */
	@SuppressWarnings("unused")
	private void drawName(GeoName label, XYpoint drawPoint,
			NumericAngle startAngle, NumericAngle endAngle)
	{
		NumericAngle middleAngle = startAngle.addtoAngle(
				NumericAngle.angleDifference(startAngle, endAngle) / 2);

		XYvector boundingBox = getBoundingBox(label);

		XYvector angleVector = new XYvector(boundingBox.getxMove(),
				middleAngle);

		XYpoint targetPoint = angleVector.shift(drawPoint);

		svgGenerator.drawString(convertToAttributedString(label).getIterator(),
				(float) targetPoint.getX(),
				(float) height - (float) targetPoint.getY());

	}

	/**
	 * 
	 * @param s
	 *            Ein String, der dort angezeigt werden sollen
	 * @return Die bounding box des Strings im dem aktuellen Graphics2D
	 */
	private XYvector getBoundingBox(GeoName geoName)
	{

		AttributedString attString = convertToAttributedString(geoName);

		TextLayout textLayout = new TextLayout(attString.getIterator(),
				svgGenerator.getFontRenderContext());
		Rectangle2D.Float textBounds = (Rectangle2D.Float) textLayout
				.getBounds();

		return new XYvector(textBounds.getWidth(), textBounds.getHeight());

		// FontMetrics fontMetrics = svgGenerator.getFontMetrics();

		// Rectangle2D stringBounds = fontMetrics.getStringBounds(s,
		// svgGenerator);

		//
		// AttributedString as = new AttributedString("I love you 104
		// gazillion");
		// as.addAttribute(TextAttribute.SUPERSCRIPT,
		// TextAttribute.SUPERSCRIPT_SUPER, 13, 14);
		// as.addAttribute(TextAttribute.FOREGROUND, Color.RED, 2, 6);
		// g.drawString(as.getIterator(), 20, 20);

		// TextLayout textLayout = new TextLayout(
		// text.getIterator(),
		// g.getFontRenderContext()
		// );
		// Rectangle2D.Float textBounds = ( Rectangle2D.Float )
		// textLayout.getBounds();
		//
		// g.drawString( text.getIterator(), 50, 50 );
		// // lets draw a bounding rect exactly around our text
		// // to be sure we calculated it properly
		// g.draw( new Rectangle2D.Float(
		// 50 + textBounds.x, 50 + textBounds.y,
		// textBounds.width, textBounds.height
		// ) );

		// return new XYvector(stringBounds.getWidth(),
		// stringBounds.getHeight());
	}

	private XYvector getMidVector(XYvector direction1, XYvector direction2,
			double boundingWidth, double boundingHeight)
	{
		int quadrant1 = getQuadrant(direction1);
		int quadrant2 = getQuadrant(direction2);

		if (quadrant2 < quadrant1)
			quadrant2 += 4;

		if (quadrant1 == quadrant2)
		{
			if (NumericAngle.angleDifference(direction1.getAngle(),
					direction2.getAngle()) > Math.PI
					|| direction1.getAngle().equals(direction2.getAngle()))
			{
				quadrant2 += 4;
			}
		}

		if (quadrant2 - quadrant1 >= 2)
		{
			return getQuadrantVector(quadrant1 + 1, boundingWidth / 2,
					boundingHeight / 2);
		}
		else if (quadrant2 - quadrant1 == 1)
		{
			if (quadrant1 == 1)
			{
				if (DMan.same(direction1.getyMove(), 0))
					return getQuadrantVector(quadrant1, boundingWidth / 2,
							boundingHeight / 2);
				if (DMan.same(direction2.getyMove(), 0))
					return getQuadrantVector(quadrant2, boundingWidth / 2,
							boundingHeight / 2);

				double wide = direction1.getxMove() / direction1.getyMove()
						- direction2.getxMove() / direction2.getyMove();

				double factor = boundingWidth / wide;

				return new XYvector(
						factor * (direction1.getxMove() / direction1.getyMove())
								- boundingWidth / 2,
						factor + boundingHeight / 2);
			}
			if (quadrant1 == 2)
			{
				if (DMan.same(direction1.getxMove(), 0))
					return getQuadrantVector(quadrant1, boundingWidth / 2,
							boundingHeight / 2);
				if (DMan.same(direction2.getxMove(), 0))
					return getQuadrantVector(quadrant2, boundingWidth / 2,
							boundingHeight / 2);

				double tall = -direction1.getyMove() / direction1.getxMove()
						+ direction2.getyMove() / direction2.getxMove();

				double factor = boundingHeight / tall;

				return new XYvector(-factor - boundingWidth / 2,
						direction1.getyMove() / direction1.getxMove() * factor
								+ boundingHeight / 2);
			}
			if (quadrant1 == 3)
			{
				if (DMan.same(direction1.getyMove(), 0))
					return getQuadrantVector(quadrant1, boundingWidth / 2,
							boundingHeight / 2);
				if (DMan.same(direction2.getyMove(), 0))
					return getQuadrantVector(quadrant2, boundingWidth / 2,
							boundingHeight / 2);

				double wide = +direction1.getxMove() / direction1.getyMove()
						- direction2.getxMove() / direction2.getyMove();

				double factor = boundingWidth / wide;

				return new XYvector(
						factor * (direction1.getxMove() / direction1.getyMove())
								- boundingWidth / 2,
						-factor - boundingHeight / 2);
			}
			if (quadrant1 == 4)
			{
				if (DMan.same(direction1.getxMove(), 0))
					return getQuadrantVector(quadrant1, boundingWidth / 2,
							boundingHeight / 2);
				if (DMan.same(direction2.getxMove(), 0))
					return getQuadrantVector(quadrant2, boundingWidth / 2,
							boundingHeight / 2);

				double tall = -direction1.getyMove() / direction1.getxMove()
						+ direction2.getyMove() / direction2.getxMove();

				double factor = boundingHeight / tall;

				return new XYvector(factor + boundingWidth / 2,
						direction1.getyMove() / direction1.getxMove() * factor
								- boundingHeight / 2);
			}

		}
		else
		{
			throw new IllegalStateException("Not implemented.");

		}

		throw new IllegalStateException("Should not be reached");

	}

	/**
	 * @param direction
	 *            vector
	 * @return the quadrant
	 */
	private int getQuadrant(XYvector direction)
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
	private XYvector getQuadrantVector(int quadrant, double xOffset,
			double yOffset)
	{
		quadrant = quadrant % 4;
		if (quadrant == 0)
			quadrant = 4;

		switch (quadrant)
		{
			case 1 :
				return new XYvector(xOffset, yOffset);
			case 2 :
				return new XYvector(-xOffset, yOffset);
			case 3 :
				return new XYvector(-xOffset, -yOffset);
			case 4 :
				return new XYvector(xOffset, -yOffset);
			default :
				throw new IllegalStateException("No quadrant");
		}

	}

	private GeoObject getRectangle(double left, double bottom, double width,
			double height)
	{
		double right = left + width;
		double top = bottom + height;
		Triangle triangle1 = new Triangle(new XYpoint(left, bottom),
				new XYpoint(right, bottom), new XYpoint(right, top));
		Triangle triangle2 = new Triangle(new XYpoint(right, top),
				new XYpoint(left, top), new XYpoint(left, bottom));

		return new CompositeGeoObject(triangle1, triangle2);

	}

	private boolean intersectsWithVisibleObject(GeoHolder geoHolder,
			GeoObject other)
	{
		for (CompName compName : geoHolder.getCompNames())
		{
			GeoObject geo = geoHolder.get(compName);
			GeoVisible visibility = geoHolder.getVisibility(compName);

			if (!visibility.isInvisible())
			{
				if (!other.intersectWith(geo).isEmpty())
					return true;

			}

		}

		for (GeoObject geo : geoHolder.getBlockedAreas())
		{
			if (!other.intersectWith(geo).isEmpty())
				return true;
		}

		return false;

	}

	/**
	 * @param fittedHolder
	 *            holder where the name should be drawn into
	 * @param geoName
	 *            a geoName
	 * @param angle
	 *            the angle to draw in
	 * @param offset
	 *            an offset to the angle boundary
	 * @return if the addition of the geoName succeeded
	 */
	private boolean nameTry(GeoHolder fittedHolder, GeoName geoName,
			Angle angle, double factor)
	{
		XYvector boundingBox = getBoundingBox(geoName);

		XYvector direction1 = angle.getDirection1();
		XYvector direction2 = angle.getDirection2();

		double boundingWidth = boundingBox.getxMove();
		double boundingHeight = boundingBox.getyMove();

		XYvector midVector = getMidVector(direction1, direction2, boundingWidth,
				boundingHeight);

		midVector = midVector.multiplyBy(factor);

		double startX = angle.getVertex().getX() + midVector.getxMove()
				- boundingBox.getxMove() / 2;
		double startY = angle.getVertex().getY() + midVector.getyMove()
				- boundingBox.getyMove() / 2;

		GeoObject rectangle = getRectangle(
				startX - fittedHolder.getWidth() / 100,
				startY - fittedHolder.getWidth() / 100,
				boundingBox.getxMove() + fittedHolder.getWidth() / 100,
				boundingBox.getyMove() + fittedHolder.getWidth() / 100);

		if (intersectsWithVisibleObject(fittedHolder, rectangle))
			return false;

		fittedHolder.addBlockedArea(rectangle);

		svgGenerator.drawString(
				convertToAttributedString(geoName).getIterator(),
				(float) startX, (float) height - (float) startY);

		return true;

	}

	/**
	 * Writes the output to a file
	 * 
	 * @param svgFile
	 *            The file
	 */
	public void writeToFile(File svgFile)
	{
		boolean useCSS = true; // we want to use CSS style attributes

		try
		{
			Writer out = new OutputStreamWriter(new FileOutputStream(svgFile),
					"UTF-8");
			svgGenerator.stream(out, useCSS);
		}
		catch (UnsupportedEncodingException | FileNotFoundException
				| SVGGraphics2DIOException e)
		{
			LOG.error("Writing on " + svgFile + " failed.", e);
		}

	}

}
