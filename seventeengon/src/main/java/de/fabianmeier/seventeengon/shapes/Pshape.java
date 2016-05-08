package de.fabianmeier.seventeengon.shapes;

import java.awt.Graphics2D;
import java.util.Set;

public interface Pshape extends GeoObject
{

	Set<Pshape> intersectWith(Pshape pshape);

	int getPseudoHash();

	@Deprecated
	void paint(Graphics2D g2d);

}
