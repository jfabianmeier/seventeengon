package de.fabianmeier.seventeengon.geoobjects;

import java.util.List;

public interface GeoObject
{
	List<GeoObject> getSubObjects();
	void draw(GeoCanvas canvas, String label);

	void setDrawingStrength(int strength);
	int getDrawingStrength();
}
