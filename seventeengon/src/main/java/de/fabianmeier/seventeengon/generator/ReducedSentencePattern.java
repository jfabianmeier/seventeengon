/**
 * 
 */
package de.fabianmeier.seventeengon.generator;

import java.util.HashSet;
import java.util.Set;

import de.fabianmeier.seventeengon.naming.SentencePattern;

/**
 * @author JFM
 *
 */
public class ReducedSentencePattern
{
	private final Set<String> reducedNameSet = new HashSet<>();
	private final int compCount;

	/**
	 * Creates a reduced sentence pattern (for better matching)
	 * 
	 * @param sentence
	 *            Sentence
	 */
	public ReducedSentencePattern(SentencePattern sentencePattern)
	{
		compCount = sentencePattern.getCompCount();

		for (String word : sentencePattern.getWords())
		{
			if (word != null)
				reducedNameSet.add(word.substring(0, 2));
		}
	}

	/**
	 * Provides an even wider equivalence relation for two
	 * ReducedSentencePatterns
	 * 
	 * @param rsp
	 *            rsp
	 * @return if they are compatible
	 */
	public boolean compatibleWith(ReducedSentencePattern rsp)
	{
		if (compCount != rsp.compCount)
		{
			return false;
		}

		Set<String> local = new HashSet<String>(reducedNameSet);
		local.retainAll(rsp.reducedNameSet);

		if (local.size() >= 3)
			return true;
		else
			return false;

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + compCount;
		result = prime * result
				+ ((reducedNameSet == null) ? 0 : reducedNameSet.hashCode());
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
		ReducedSentencePattern other = (ReducedSentencePattern) obj;
		if (compCount != other.compCount)
			return false;
		if (reducedNameSet == null)
		{
			if (other.reducedNameSet != null)
				return false;
		}
		else if (!reducedNameSet.equals(other.reducedNameSet))
			return false;
		return true;
	}

}
