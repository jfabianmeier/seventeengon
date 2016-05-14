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
public class Sentence
{
	/**
	 * 
	 * @param sentence
	 *            Sentence as String
	 * @return if sentence is a valid Sentence
	 */
	public static boolean isSentence(String sentence)
	{
		String[] stringParts = sentence.split("\\s+");

		for (String part : stringParts)
		{
			if (!isSentenceWord(part))
			{
				List<String> compParts = CompName.getCompNamePieces(part);
				if (compParts == null)
					return false;
			}
		}

		return true;

	}

	private static boolean isSentenceWord(String part)
	{
		return !BasicName.isBasicName(part)
				&& part.matches("[A-ZÄÖÜ]?[a-zäöüß]+,?");
	}

	private final List<Object> parts = new ArrayList<Object>();
	private final List<String> words = new ArrayList<String>();
	private final List<CompName> compNames = new ArrayList<CompName>();

	private final String sentence;

	/**
	 * Creates a Sentence
	 * 
	 * @param sentence
	 *            a sentence (if invalid, an Exception is thrown)
	 */
	public Sentence(String sentence)
	{
		this.sentence = sentence;
		String[] stringParts = sentence.split("\\s+");

		for (String part : stringParts)
		{
			if (isSentenceWord(part))
			{
				parts.add(part);
				words.add(part);
			}
			else
			{
				List<String> compParts = CompName.getCompNamePieces(part);
				if (compParts == null)
					throw new IllegalArgumentException(
							part + " no valid CompName");
				CompName comp = new CompName(part);
				parts.add(comp);
				compNames.add(comp);
			}
		}
	}

	/**
	 * 
	 * @return All CompName in the sentence
	 */
	public List<CompName> getCompositeNames()
	{
		return compNames;
	}

	/**
	 * @return All words in the sentence
	 */
	public List<String> getWords()
	{
		return words;
	}

	public List<Object> getParts()
	{
		return parts;
	}

	@Override
	public String toString()
	{
		return sentence;
	}

}
