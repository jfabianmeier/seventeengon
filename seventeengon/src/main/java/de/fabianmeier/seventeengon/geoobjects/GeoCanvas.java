package de.fabianmeier.seventeengon.geoobjects;

// TODO: Should think about moving labels from string to GeoName. CompNames are
// no good labels (who labels a line from A to B with AB?)

public interface GeoCanvas
{

	// /**
	// * Draws a line from start to end
	// *
	// * @param start
	// * start point of line
	// * @param end
	// * end point of line
	// * @param visible
	// * visible
	// */
	// void drawLine(XYpoint start, XYpoint end, GeoVisible visible);
	//
	// /**
	// * Draws a circle arc
	// *
	// * @param centre
	// * centre
	// * @param startAngle
	// * startAngle (from x-axis)
	// * @param endAngle
	// * endAngle (from x-axis)
	// * @param visible
	// * visible
	// */
	// void drawArc(XYpoint centre, NumericAngle startAngle, NumericAngle
	// endAngle,
	// double radius, GeoVisible visible);
	//
	// /**
	// * Draws a point
	// *
	// * @param point
	// * point
	// * @param visible
	// * visible
	// */
	// void drawPoint(XYpoint point, GeoVisible visible);
	//
	// /**
	// * Draws a filled triangle
	// *
	// * @param a
	// * point a
	// * @param b
	// * point b
	// * @param c
	// * point c
	// *
	// * @param visible
	// * visible
	// */
	// void fillTriangle(XYpoint a, XYpoint b, XYpoint c, GeoVisible visible);
	// /**
	// * Draws an angle
	// *
	// * @param vertex
	// * vertex
	// * @param direction1
	// * first direction vector
	// * @param direction2
	// * second direction vector
	// * @param label
	// * label
	// * @param visi
	// * visible
	// */
	// void drawAngle(XYpoint vertex, XYvector direction1, XYvector direction2,
	// GeoVisible visi);

	/**
	 * @param geoHolder
	 *            a geoHolder object
	 */
	void drawAll(GeoHolder geoHolder);

}
