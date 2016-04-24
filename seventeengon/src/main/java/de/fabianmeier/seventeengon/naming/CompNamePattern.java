/**
 * 
 */
package de.fabianmeier.seventeengon.naming;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JFM
 *
 */
public class CompNamePattern
{
	List<String> separation = new ArrayList<String>();

	public CompNamePattern(CompName compName)
	{
		String stringRep = compName.toString();

		List<GeoName> geoNameList = compName.getGeoNames();

		String runningString = stringRep;
		for (int i = 0; i < geoNameList.size() - 1; i++)
		{
			GeoName geoName = geoNameList.get(i);
			GeoName nextGeoName = geoNameList.get(i + 1);
			runningString = runningString
					.substring(geoName.toString().length());
			int index = runningString.indexOf(nextGeoName.toString());
			separation.add(runningString.substring(0, index));
		}

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((separation == null) ? 0 : separation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompNamePattern other = (CompNamePattern) obj;
		if (separation == null)
		{
			if (other.separation != null)
				return false;
		} else if (!separation.equals(other.separation))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("X");

		for (String s : separation)
		{
			builder.append(s);
			builder.append("X");
		}

		return builder.toString();

	}
}
