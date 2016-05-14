/**
 * 
 */
package de.fabianmeier.seventeengon.shapes;

/**
 * @author JFM
 *
 */
public class DifferenceObject
{
	// TODO: sollte wahrscheinlich nicht GeoObject implementieren, weil viele
	// Teile sinnlos.

	private GeoObject area;
	private GeoObject cutOut;

	/**
	 * Forms a difference object
	 * 
	 * @param area
	 *            outer object
	 * @param cutOut
	 *            to be cut out
	 */
	public DifferenceObject(GeoObject area, GeoObject cutOut)
	{
		this.area = area;
		this.cutOut = cutOut;

	}

	/**
	 * 
	 * @param sampleNumber
	 *            some integer
	 * @return a sampled point from the difference area.
	 */
	public XYpoint getSamplePoint(int sampleNumber)
	{
		int localSampleNumber = sampleNumber;

		for (int i = 0; i < 1000; i++)
		{
			XYpoint samplePoint = area.getSamplePoint(localSampleNumber + i);
			if (samplePoint != null && !cutOut.containsPoint(samplePoint))
			{
				return samplePoint;
			}

		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#intersectWith(de.fabianmeier
	 * .seventeengon.shapes.GeoObject)
	 */
	public DifferenceObject intersectWith(GeoObject other)
	{
		return new DifferenceObject(area.intersectWith(other), cutOut);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fabianmeier.seventeengon.shapes.GeoObject#getDimension()
	 */
	public int getDimension()
	{
		return area.getDimension();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fabianmeier.seventeengon.shapes.GeoObject#containsPoint(de.fabianmeier
	 * .seventeengon.shapes.XYpoint)
	 */
	public boolean containsPoint(XYpoint point)
	{
		return (area.containsPoint(point) && !cutOut.containsPoint(point));
	}

}
