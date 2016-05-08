package de.fabianmeier.seventeengon.naming;

import java.util.ArrayList;
import java.util.List;

public class SentencePattern
{
	private final List<String> words;

	public List<String> getWords()
	{
		return words;
	}

	public SentencePattern(String sentence)
	{
		this(new Sentence(sentence));
	}

	/**
	 * Creates a pattern from a sentence
	 * 
	 * @param sentence
	 *            Sentence
	 */
	public SentencePattern(Sentence sentence)
	{
		words = new ArrayList<String>();

		for (Object part : sentence.getParts())
		{
			if (part instanceof String)
				words.add((String) part);
			else
				words.add(null);
		}
	}

	// private static boolean isSentenceWord(String part)
	// {
	// return !BasicName.isBasicName(part) && part.matches("[A-Z]?[a-z]+,?");
	// }

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		for (String word : words)
		{
			if (word != null)
			{
				builder.append(word + " ");
			}
			else
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
		}
		else if (!words.equals(other.words))
			return false;
		return true;
	}

}
