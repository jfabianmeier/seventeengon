/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

import java.util.ArrayList;
import java.util.List;

import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.shapes.GeoObject;
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
	// @Override
	// public void drawLine(XYpoint start, XYpoint end, String label,
	// GeoVisible visi)
	// {
	// writings.add(label + ": " + "Line from " + start.toString() + " to "
	// + end.toString() + " with visibility: " + visi);
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawArc(de.fabianmeier.
	 * seventeengon.shapes.XYpoint, de.fabianmeier.seventeengon.util.Angle,
	 * de.fabianmeier.seventeengon.util.Angle, int, java.lang.String)
	 */
	// @Override
	// public void drawArc(XYpoint centre, NumericAngle startAngle,
	// NumericAngle endAngle, double radius, String label, GeoVisible visi)
	// {
	// writings.add(label + ": " + "Arc from " + startAngle.toString() + " to "
	// + endAngle.toString() + " with radius " + radius + " around "
	// + centre + " with visibility: " + visi);
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawPoint(de.fabianmeier
	 * .seventeengon.shapes.XYpoint, int, java.lang.String) //
	 */
	// @Override
	// public void drawPoint(XYpoint point, String label, GeoVisible visi)
	// {
	// writings.add(
	// label + ": " + "Point " + point + " with visibility " + visi);
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.geoobjects.GeoCanvas#fillTriangle(de.
	 * fabianmeier.seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYpoint, int, java.lang.String)
	 */
	// @Override
	// public void fillTriangle(XYpoint a, XYpoint b, XYpoint c, String label,
	// GeoVisible visi)
	// {
	// writings.add(label + ": " + "Triangle with " + a.toString() + ", "
	// + b.toString() + " and " + c.toString() + " with visibility: "
	// + visi);
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawAngle(de.fabianmeier
	 * .seventeengon.shapes.XYpoint,
	 * de.fabianmeier.seventeengon.shapes.XYvector,
	 * de.fabianmeier.seventeengon.shapes.XYvector, java.lang.String,
	 * de.fabianmeier.seventeengon.util.GeoVisible)
	 */
	// @Override
	// public void drawAngle(XYpoint vertex, XYvector direction1,
	// XYvector direction2, String label, GeoVisible visi)
	// {
	// writings.add(label + ": " + "Angle with vertex " + vertex.toString()
	// + " and rays: " + direction1 + ", " + direction2
	// + " with visibility: " + visi);
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#draw(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder)
	 */
	@Override
	public void drawAll(GeoHolder geoHolder)
	{
		writings = new ArrayList<String>();
		for (CompName compName : geoHolder.getCompNames())
		{
			GeoObject geo = geoHolder.get(compName);
			GeoVisible visibility = geoHolder.getVisibility(compName);

			writings.add(compName + " : " + geo.toString() + " Visibility: "
					+ visibility);

		}

	}

}
