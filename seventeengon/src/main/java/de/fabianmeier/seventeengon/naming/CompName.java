package de.fabianmeier.seventeengon.naming;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompName
{

	private final List<GeoName> geoNames = new ArrayList<>();

	private final String compName;

	public CompName(GeoName geoName)
	{
		this(geoName.toString());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compName == null) ? 0 : compName.hashCode());
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
		CompName other = (CompName) obj;
		if (compName == null)
		{
			if (other.compName != null)
				return false;
		}
		else if (!compName.equals(other.compName))
			return false;
		return true;
	}

	/**
	 * Returns a String list of separated pieces of the form padding, geoname,
	 * padding, ... ,padding
	 * 
	 * @param compName
	 *            Possible CompName
	 * @return A list of strings of the pieces or null if the string is not
	 *         well-formed.
	 */
	public static List<String> getCompNamePieces(String compName)
	{
		if (compName == null)
			throw new IllegalArgumentException("CompName cannot be null.");

		List<String> back = new ArrayList<String>();
		int runningIndex = 0;

		while (runningIndex < compName.length())
		{
			String padding = getNonGeoName(compName, runningIndex);
			back.add(padding);

			runningIndex += padding.length();

			if (runningIndex >= compName.length())
				return back;

			GeoName geoName = getGeoName(compName, runningIndex);
			if (geoName == null)
				return null;

			back.add(geoName.toString());

			runningIndex += geoName.toString().length();
		}

		back.add("");

		return back;
	}

	/**
	 * 
	 * @param pattern
	 *            Pattern
	 * @param name
	 *            Name
	 * @return if the name fits the CompNamePattern
	 */
	public static boolean matchesPattern(CompNamePattern pattern, String name)
	{
		List<String> pieces = getCompNamePieces(name);
		if (pieces == null)
			return false;

		List<String> patternPieces = pattern.getSeparation();

		for (int i = 0; i < patternPieces.size(); i++)
		{
			if (!patternPieces.get(i).equals(pieces.get(2 * i)))
				return false;
		}

		return true;
	}

	private static String getNonGeoName(String name, int runningIndex)
	{
		String internalName = name.substring(runningIndex);
		Pattern p = Pattern.compile("^\\W*");
		Matcher m = p.matcher(internalName);
		if (!m.find())
			throw new IllegalStateException(
					internalName + " does not begin anywhere!?!");
		return m.group(0);
	}

	private static GeoName getGeoName(String geoName)
	{
		return getGeoName(geoName, 0);
	}

	private static GeoName getGeoName(String input, int startIndex)
	{
		BasicName basicName = null;

		String investigateString = input.substring(startIndex);

		String startPart = investigateString.substring(0, 1);

		if (!startPart.matches("[A-Z]"))
		{

			Pattern p = Pattern.compile("^[a-z]+");
			Matcher m = p.matcher(investigateString);

			if (m.find())
			{
				startPart = m.group(0);
			}

		}
		if (BasicName.isBasicName(startPart))
		{
			basicName = new BasicName(startPart);
		}
		else
			return null;

		if (investigateString.length() <= startPart.length()
				|| !(investigateString.charAt(startPart.length()) == '_'))
		{
			return basicName;
		}
		else
		{
			String secondPart = investigateString
					.substring(startPart.length() + 1);

			int indexLength = 1;

			Pattern p = Pattern.compile("^\\d+");
			Matcher m = p.matcher(secondPart);

			if (m.find())
			{
				indexLength = m.group(0).length();
			}

			Pattern p2 = Pattern.compile("^[a-z]+");
			Matcher m2 = p2.matcher(secondPart);

			if (m2.find())
			{
				indexLength = m2.group(0).length();
			}

			return new IndexedName(investigateString.substring(0,
					startPart.length() + 1 + indexLength));
		}

	}

	/**
	 * Constructs a composite name from a string
	 * 
	 * @param compName
	 *            a valid composite name
	 */
	public CompName(String compName)
	{
		this.compName = compName;

		List<String> pieces = getCompNamePieces(compName);

		if (pieces == null)
			throw new IllegalArgumentException(
					compName + " is no valid CompName");

		for (int i = 0; i < pieces.size() / 2; i++)
		{
			geoNames.add(getGeoName(pieces.get(2 * i + 1)));
		}

	}

	public List<GeoName> getGeoNames()
	{
		return geoNames;
	}

	@Override
	public String toString()
	{
		return compName;
	}

}
