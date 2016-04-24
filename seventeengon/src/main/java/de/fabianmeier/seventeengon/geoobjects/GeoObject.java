package de.fabianmeier.seventeengon.geoobjects;

import java.util.List;

import de.fabianmeier.seventeengon.shapes.XYpoint;

public interface GeoObject
{
	List<GeoObject> getSubObjects();
	void draw(GeoCanvas canvas, String label);

	void setVisibility(int visibility);
	int getVisibility();

	XYpoint getSamplePoint(int sampleNumber);

	GeoObject intersectWith(GeoObject other);

	int getDimension();
}
