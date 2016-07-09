package de.fabianmeier.seventeengon.geoobjects;

// TODO: Should think about moving labels from string to GeoName. CompNames are
// no good labels (who labels a line from A to B with AB?)

public interface GeoCanvas
{

	/**
	 * @param geoHolder
	 *            A geoHolder
	 * @param turnAndFit
	 *            If the result should be fitted into the rectangle.
	 */
	double drawAll(GeoHolder geoHolder, boolean turnAndFit);

}
