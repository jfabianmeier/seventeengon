package de.fabianmeier.seventeengon.naming;

/**
 * A name of a GeoObject (with or without index)
 * 
 * @author jfabi
 *
 */
public interface GeoName
{
	/**
	 * 
	 * @return gives the objects name in unicode (relevant mostly for greek
	 *         characters)
	 */
	String toUnicodeString();
}
