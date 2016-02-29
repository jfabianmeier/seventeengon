package de.fabianmeier.seventeengon.shapes;

import java.util.Set;

public interface Pshape extends Gshape
{
	int getDimension();

	Set<Pshape> intersectWith(Pshape pshape);

	int getPseudoHash();

	void setVisibility(int visibility);

	void setLabel(String label);

}