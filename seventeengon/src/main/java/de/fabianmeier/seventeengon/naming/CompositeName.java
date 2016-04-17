package de.fabianmeier.seventeengon.naming;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompositeName
{

	private List<GeoName> geoNames = new ArrayList<>();

	private String compName;

	public CompositeName(String compName)
	{

		this.compName = compName;

		int readIndex = 0;

		while (readIndex < compName.length())
		{
			String investigateString = compName.substring(readIndex);

			Pattern p3 = Pattern.compile("^[A-Za-z]");
			Matcher m3 = p3.matcher(investigateString);

			if (!m3.find())
			{
				readIndex++;
				continue;
			}

			BasicName basicName = null;

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

			if (investigateString.length() <= startPart.length()
					|| !(investigateString.charAt(startPart.length()) == '_'))
			{
				geoNames.add(basicName);
				readIndex += startPart.length();
			} else
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

				geoNames.add(new IndexedName(investigateString.substring(0,
						startPart.length() + 1 + indexLength)));

				readIndex += startPart.length() + 1 + indexLength;

			}

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
