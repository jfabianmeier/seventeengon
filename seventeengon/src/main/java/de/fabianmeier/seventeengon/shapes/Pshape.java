package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.util.Set;

import de.fabianmeier.seventeengon.geoobjects.GeoObject;

public interface Pshape extends GeoObject
{

	Set<Pshape> intersectWith(Pshape pshape);

	int getPseudoHash();

	@Override
	void setVisibility(int visibility);

	void setLabel(String label);

	void paint(Graphics2D g2d);

}
