package de.fabianmeier.seventeengon.naming;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes the pattern of a sentence (the words and where compObjects are
 * placed).
 * 
 * @author jfabi
 *
 */
public class SentencePattern
{
	private final List<String> words;

	/**
	 * 
	 * @return the list of words
	 */
	public List<String> getWords()
	{
		return words;
	}

	/**
	 * 
	 * @param sentence
	 *            a sentence as a string
	 */
	public SentencePattern(String sentence)
	{
		this(new Sentence(sentence));
	}

	/**
	 * Creates a pattern from a sentence.
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

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		int objectCount = 1;

		for (String word : words)
		{
			if (word != null)
			{
				builder.append(word + " ");
			} else
			{
				builder.append("[Object " + objectCount + "]" + " ");
				objectCount++;
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

	/**
	 * @return Number of CompName places in the pattern
	 */
	public int getCompCount()
	{
		int count = 0;
		for (String word : words)
		{
			if (word == null)
				count++;

		}
		return count;
	}

}
