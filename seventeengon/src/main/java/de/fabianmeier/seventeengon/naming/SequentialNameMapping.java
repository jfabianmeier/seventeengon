package de.fabianmeier.seventeengon.naming;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapping of names for sub functions in the generation of the image.
 * 
 * @author jfabi
 *
 */
public class SequentialNameMapping
{
	private final SentencePattern pattern;

	/**
	 * 
	 * @return the sentence pattern
	 */
	public SentencePattern getPattern()
	{
		return pattern;
	}

	private Map<CompName, CompName> sourceToSink = new HashMap<>();
	private Map<CompName, CompName> sinkToSource = new HashMap<>();

	/**
	 * 
	 * @param name
	 *            a compName
	 * @return the sink compName corresponding
	 */
	public CompName getSinkForSource(CompName name)
	{
		return sourceToSink.get(name);
	}

	/**
	 * 
	 * @param name
	 *            a compName
	 * @return the source compName corresponding to it
	 */
	public CompName getSourceForSink(CompName name)
	{
		return sinkToSource.get(name);
	}

	/**
	 * 
	 * @return all source names
	 */
	public Collection<CompName> getSourceNames()
	{
		return sourceToSink.keySet();
	}

	/**
	 * 
	 * @return all sink names
	 */
	public Collection<CompName> getSinkNames()
	{
		return sinkToSource.keySet();
	}

	/**
	 * A back and forth mapping of the list elements of the component list. This
	 * is not necessarily a bijection.
	 * 
	 * @param source
	 *            The source sentence
	 * @param sink
	 *            The sink sentence
	 */
	public SequentialNameMapping(Sentence source, Sentence sink)
	{
		SentencePattern pattern1 = new SentencePattern(source);
		SentencePattern pattern2 = new SentencePattern(sink);

		if (!pattern1.equals(pattern2))
			throw new IllegalArgumentException("Different sentence Patterns: " + pattern1 + " vs. " + pattern2);

		pattern = pattern1;

		List<CompName> sourceCompList = source.getCompositeNames();
		List<CompName> sinkCompList = sink.getCompositeNames();

		for (int i = 0; i < sourceCompList.size(); i++)
		{
			CompName sourceElement = sourceCompList.get(i);
			CompName sinkElement = sinkCompList.get(i);
			sourceToSink.put(sourceElement, sinkElement);
			sinkToSource.put(sinkElement, sourceElement);

			if (sourceElement.getGeoNames().size() == sinkElement.getGeoNames().size())
			{
				for (int j = 0; j < sourceElement.getGeoNames().size(); j++)
				{
					sourceToSink.put(new CompName(sourceElement.getGeoNames().get(j)),
							new CompName(sinkElement.getGeoNames().get(j)));
					sinkToSource.put(new CompName(sinkElement.getGeoNames().get(j)),
							new CompName(sourceElement.getGeoNames().get(j)));
				}
			}

		}

	}

}
