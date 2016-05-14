/**
 * 
 */
package de.fabianmeier.seventeengon.geoobjects;

/**
 * @author JFM
 *
 */
public class SampleGenerator
{

	// TODO: Should probably be integrated into GeoHolder because it goes along
	// with it.

	private static int samplingValue = 0;

	private static double width = 2000;
	private static double height = 1000;

	public static double getWidth()
	{
		return width;
	}

	public static void setWidth(double width)
	{
		SampleGenerator.width = width;
	}

	public static double getHeight()
	{
		return height;
	}

	public static void setHeight(double height)
	{
		SampleGenerator.height = height;
	}

	public static void reset()
	{
		samplingValue = 0;
	}

	/**
	 * Increases the stored sampling value by one and returns it
	 * 
	 * @return As said above.
	 */
	public static int nextSampling()
	{
		samplingValue++;
		return samplingValue;

	}

}
