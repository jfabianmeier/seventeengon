package de.fabianmeier.seventeengon.geoobjects;

import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.util.Angle;
import de.fabianmeier.seventeengon.util.GeoVisible;

public interface GeoCanvas
{
	void drawLine(XYpoint start, XYpoint end, String label, GeoVisible visible);
	void drawArc(XYpoint centre, Angle startAngle, Angle endAngle, String label,
			GeoVisible visible);
	void drawPoint(XYpoint point, String label, GeoVisible visible);
	void fillTriangle(XYpoint a, XYpoint b, XYpoint c, String label,
			GeoVisible visible);

}
