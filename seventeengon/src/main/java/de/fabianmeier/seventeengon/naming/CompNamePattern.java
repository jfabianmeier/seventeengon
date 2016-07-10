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
	private final List<String> separation = new ArrayList<String>();

	/**
	 * 
	 * @return the separation values as list of strings which can be null
	 */
	public List<String> getSeparation()
	{
		return separation;
	}

	/**
	 * Creates a pattern (i.e. a list of paddings) for a compName
	 * 
	 * @param compName
	 *            CompName
	 */
	public CompNamePattern(CompName compName)
	{
		List<String> pieces = CompName.getCompNamePieces(compName.toString());

		if (pieces.size() % 2 != 1)
			throw new IllegalStateException("Size of list is " + pieces.size());

		for (int i = 0; i < pieces.size() / 2 + 1; i++)
		{
			separation.add(pieces.get(2 * i));
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((separation == null) ? 0 : separation.hashCode());
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
