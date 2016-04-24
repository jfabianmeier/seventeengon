/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import de.fabianmeier.seventeengon.shapes.Triangle;
import de.fabianmeier.seventeengon.shapes.XYpoint;

/**
 * @author JFM
 *
 */
public class GeoPoint extends GeoDrawingObject
{

	XYpoint xy;

	public XYpoint getXY()
	{
		return xy;
	}

	public GeoPoint()
	{
		Triangle area = new Triangle(new XYpoint(0, 0),
				new XYpoint(0, SampleGenerator.getHeight()),
				new XYpoint(SampleGenerator.getWidth(), 0), 0, null);

		xy = area.getSamplePoint(SampleGenerator.nextSampling());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoObject#draw(de.fabianmeier.
	 * seventeengon.geoobjects.GeoCanvas, java.lang.String)
	 */
	@Override
	public void draw(GeoCanvas canvas, String label)
	{

		canvas.drawPoint(xy, getDrawingStrength(), label);

	}

}
