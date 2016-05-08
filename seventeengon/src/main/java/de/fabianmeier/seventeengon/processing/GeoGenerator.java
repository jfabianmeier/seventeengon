package de.fabianmeier.seventeengon.processing;

import java.io.IOException;

import de.fabianmeier.seventeengon.geoobjects.GeoHolder;
import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.Sentence;

public interface GeoGenerator
{
	boolean generateAndAdd(GeoHolder geoHolder, Sentence sentence)
			throws IOException;
	boolean generateAndAdd(GeoHolder geoHolder, CompName sentence)
			throws IOException;

}
