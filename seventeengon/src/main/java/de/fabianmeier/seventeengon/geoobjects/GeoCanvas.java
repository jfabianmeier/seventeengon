package de.fabianmeier.seventeengon.geoobjects;

import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.shapes.XYvector;
import de.fabianmeier.seventeengon.util.GeoVisible;
import de.fabianmeier.seventeengon.util.NumericAngle;

public interface GeoCanvas
{
	/**
	 * Draws a line from start to end
	 * 
	 * @param start
	 *            start point of line
	 * @param end
	 *            end point of line
	 * @param label
	 *            label
	 * @param visible
	 *            visible
	 */
	void drawLine(XYpoint start, XYpoint end, String label, GeoVisible visible);

	/**
	 * Draws a circle arc
	 * 
	 * @param centre
	 *            centre
	 * @param startAngle
	 *            startAngle (from x-axis)
	 * @param endAngle
	 *            endAngle (from x-axis)
	 * @param label
	 *            label
	 * @param visible
	 *            visible
	 */
	void drawArc(XYpoint centre, NumericAngle startAngle, NumericAngle endAngle,
			String label, GeoVisible visible);

	/**
	 * Draws a point
	 * 
	 * @param point
	 *            point
	 * @param label
	 *            label
	 * @param visible
	 *            visible
	 */
	void drawPoint(XYpoint point, String label, GeoVisible visible);

	/**
	 * Draws a filled triangle
	 * 
	 * @param a
	 *            point a
	 * @param b
	 *            point b
	 * @param c
	 *            point c
	 * @param label
	 *            label
	 * @param visible
	 *            visible
	 */
	void fillTriangle(XYpoint a, XYpoint b, XYpoint c, String label,
			GeoVisible visible);
	/**
	 * Draws an angle
	 * 
	 * @param vertex
	 *            vertex
	 * @param direction1
	 *            first direction vector
	 * @param direction2
	 *            second direction vector
	 * @param label
	 *            label
	 * @param visi
	 *            visible
	 */
	void drawAngle(XYpoint vertex, XYvector direction1, XYvector direction2,
			String label, GeoVisible visi);

}
