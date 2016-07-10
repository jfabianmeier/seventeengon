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
	/**
	 * the whole area from which the cutOut is excluded.
	 */
	private GeoObject area;
	/**
	 * the inner part that is excluded.
	 */
	private GeoObject cutOut;

	/**
	 * Forms a difference object.
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

	/**
	 * 
	 * @param other
	 *            a geoObject
	 * @return the intersection of both as DifferenceObject
	 */
	public DifferenceObject intersectWith(GeoObject other)
	{
		return new DifferenceObject(area.intersectWith(other), cutOut);
	}

	/**
	 * 
	 * @return the dimension of the defining area
	 */
	public int getDimension()
	{
		return area.getDimension();
	}

	/**
	 * 
	 * @param point
	 *            a point
	 * @return if this point is contained in the difference object
	 */
	public boolean containsPoint(XYpoint point)
	{
		return (area.containsPoint(point) && !cutOut.containsPoint(point));
	}

}
