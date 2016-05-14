/**
 * 
 */
package de.fabianmeier.seventeengon.svg;

import java.io.File;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
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
	private final double width;
	private final double height;

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
		SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawLine(de.fabianmeier.
	 * seventeengon.shapes.XYpoint, de.fabianmeier.seventeengon.shapes.XYpoint,
	 * java.lang.String, de.fabianmeier.seventeengon.util.GeoVisible)
	 */
	@Override
	public void drawLine(XYpoint start, XYpoint end, String label,
			GeoVisible visible)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawArc(de.fabianmeier.
	 * seventeengon.shapes.XYpoint, de.fabianmeier.seventeengon.util.Angle,
	 * de.fabianmeier.seventeengon.util.Angle, java.lang.String,
	 * de.fabianmeier.seventeengon.util.GeoVisible)
	 */
	@Override
	public void drawArc(XYpoint centre, NumericAngle startAngle,
			NumericAngle endAngle, String label, GeoVisible visible)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawPoint(de.fabianmeier
	 * .seventeengon.shapes.XYpoint, java.lang.String,
	 * de.fabianmeier.seventeengon.util.GeoVisible)
	 */
	@Override
	public void drawPoint(XYpoint point, String label, GeoVisible visible)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoCanvas#fillTriangle(de.
	 * fabianmeier.seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYpoint, java.lang.String,
	 * de.fabianmeier.seventeengon.util.GeoVisible)
	 */
	@Override
	public void fillTriangle(XYpoint a, XYpoint b, XYpoint c, String label,
			GeoVisible visible)
	{
		// TODO Auto-generated method stub

	}

	public void writeToFile(File svgFile)
	{

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
	@Override
	public void drawAngle(XYpoint vertex, XYvector direction1,
			XYvector direction2, String label, GeoVisible visi)
	{
		// TODO Auto-generated method stub

	}

}
