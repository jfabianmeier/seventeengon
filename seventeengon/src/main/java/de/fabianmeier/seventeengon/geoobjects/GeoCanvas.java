package de.fabianmeier.seventeengon.geoobjects;

import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.util.Angle;

public interface GeoCanvas
{
	void drawLine(XYpoint start, XYpoint end, int strength, String label);
	void drawArc(XYpoint centre, Angle startAngle, Angle endAngle, int strength,
			String label);
	void drawPoint(XYpoint point, int strength, String label);
	void fillTriangle(XYpoint a, XYpoint b, XYpoint c, int strength,
			String label);

}
