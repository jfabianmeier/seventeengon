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
	 * de.fabianmeier.seventeengon.geoobjects.GeoCanvas#drawAll(de.fabianmeier.
	 * seventeengon.geoobjects.GeoHolder, boolean)
	 */
	@Override
	public void drawAll(GeoHolder geoHolder, boolean turnAndFit)
	{
		GeoHolder fittedHolder = geoHolder;
		if (turnAndFit)
			fittedHolder = geoHolder.turnAndFitIntoCanvas();

		writings = new ArrayList<String>();
		for (CompName compName : fittedHolder.getCompNames())
		{
			GeoObject geo = fittedHolder.get(compName);
			GeoVisible visibility = fittedHolder.getVisibility(compName);

			writings.add(compName + " : " + geo.toString() + " Visibility: "
					+ visibility);

		}

	}

}
