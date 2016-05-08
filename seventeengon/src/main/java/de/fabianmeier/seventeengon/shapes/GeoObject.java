package de.fabianmeier.seventeengon.shapes;

import java.util.List;

import de.fabianmeier.seventeengon.geoobjects.GeoCanvas;
import de.fabianmeier.seventeengon.util.GeoVisible;

public interface GeoObject
{
	List<GeoObject> getSubObjects();
	void draw(GeoCanvas canvas, String label, GeoVisible visi);

	// void setVisibility(int visibility);
	// int getVisibility();

	XYpoint getSamplePoint(int sampleNumber);

	GeoObject intersectWith(GeoObject other);

	int getDimension();

	/**
	 * 
	 * @return The object under deletion of all inner parts, i.e. it leaves the
	 *         boundaries of the circles and triangles and all other objects.
	 */
	GeoObject getBoundary();
	GeoObject getFilledObject();

	boolean containsPoint(XYpoint point);
}
