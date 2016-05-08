/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import java.util.ArrayList;
import java.util.List;

import de.fabianmeier.seventeengon.shapes.XYpoint;
import de.fabianmeier.seventeengon.util.Angle;
import de.fabianmeier.seventeengon.util.GeoVisible;

/**
 * @author JFM
 *
 */
public class TextualCanvas implements GeoCanvas
{

	List<String> writings = new ArrayList<String>();

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		for (String w : writings)
		{
			builder.append(w + "\n");
		}

		return builder.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawLine(de.fabianmeier.
	 * seventeengon.shapes.XYpoint, de.fabianmeier.seventeengon.shapes.XYpoint,
	 * int, java.lang.String)
	 */
	@Override
	public void drawLine(XYpoint start, XYpoint end, String label,
			GeoVisible visi)
	{
		writings.add(label + ": " + "Line from " + start.toString() + " to "
				+ end.toString() + " with visibility: " + visi);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawArc(de.fabianmeier.
	 * seventeengon.shapes.XYpoint, de.fabianmeier.seventeengon.util.Angle,
	 * de.fabianmeier.seventeengon.util.Angle, int, java.lang.String)
	 */
	@Override
	public void drawArc(XYpoint centre, Angle startAngle, Angle endAngle,
			String label, GeoVisible visi)
	{
		writings.add(label + ": " + "Arc from " + startAngle.toString() + " to "
				+ endAngle.toString() + " around " + centre
				+ " with visibility: " + visi);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawPoint(de.fabianmeier
	 * .seventeengon.shapes.XYpoint, int, java.lang.String)
	 */
	@Override
	public void drawPoint(XYpoint point, String label, GeoVisible visi)
	{
		writings.add(
				label + ": " + "Point " + point + " with visibility " + visi);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoCanvas#fillTriangle(de.
	 * fabianmeier.seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYpoint, int, java.lang.String)
	 */
	@Override
	public void fillTriangle(XYpoint a, XYpoint b, XYpoint c, String label,
			GeoVisible visi)
	{
		writings.add(label + ": " + "Triangle with " + a.toString() + ", "
				+ b.toString() + " and " + c.toString() + " with visibility: "
				+ visi);

	}

}
