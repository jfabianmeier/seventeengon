/**
 * 
 */
package de.fabianmeier.seventeengon;

import java.util.List;

import org.junit.Test;

import de.fabianmeier.seventeengon.naming.CompositeName;
import de.fabianmeier.seventeengon.naming.GeoName;
import de.fabianmeier.seventeengon.naming.SentencePattern;

/**
 * @author JFM
 *
 */
public class CompositeNameTest
{

	@Test
	public void test()
	{
		CompositeName compName = new CompositeName("<A_1alpha||BC_a");

		List<GeoName> geoNames = compName.getGeoNames();

		System.out.println(geoNames.get(0).toString());
		System.out.println(geoNames.get(1).toString());
		System.out.println(geoNames.get(2).toString());
		System.out.println(geoNames.get(3).toString());
	}

	@Test
	public void test2()
	{
		SentencePattern sentPat = new SentencePattern(
				"Ein Kreis k sei definiert durch den Mittelpunkt M_0 und den Radius r");

		System.out.println(sentPat.toString());
	}

}
