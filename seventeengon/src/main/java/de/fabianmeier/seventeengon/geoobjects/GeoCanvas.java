package de.fabianmeier.seventeengon.geoobjects;

/**
 * Represents a way of drawing the abstract geoObjects to a concrete canvas.
 * 
 * @author jfabi
 *
 */
public interface GeoCanvas
{

	/**
	 * @param geoHolder
	 *            A geoHolder
	 * @param turnAndFit
	 *            If the result should be fitted into the rectangle.
	 * @return the quality of the drawing (high = good)
	 */
	double drawAll(GeoHolder geoHolder, boolean turnAndFit);

}
