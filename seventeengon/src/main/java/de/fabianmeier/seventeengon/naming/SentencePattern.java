package de.fabianmeier.seventeengon.naming;

import java.util.ArrayList;
import java.util.List;

public class SentencePattern
{
	private List<String> words = new ArrayList<String>();

	// private List<CompositeName> compositeNames = new ArrayList<>();

	public SentencePattern(String sentence)
	{

		String[] parts = sentence.split("\\s+");

		for (String part : parts)
		{
			if (!BasicName.isBasicName(part) && part.matches("[A-Z]?[a-z]+"))
			{
				words.add(part);
			} else
			{
				words.add(null);
				// compositeNames.add(new CompositeName(part));
			}
		}

	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		for (String word : words)
		{
			if (word != null)
			{
				builder.append(word + " ");
			} else
			{
				builder.append("X" + " ");
			}
		}

		return builder.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((words == null) ? 0 : words.hashCode());
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
		SentencePattern other = (SentencePattern) obj;
		if (words == null)
		{
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}

}
