package de.fabianmeier.seventeengon.shapes;

import java.util.Set;

public interface Gshape {
	Set<Pshape> getPrimitiveShapes();
	
	XYpoint getSamplePoint(int sampleNumber);
	
	int getVisibility();
	
	String getLabel();
	

}
