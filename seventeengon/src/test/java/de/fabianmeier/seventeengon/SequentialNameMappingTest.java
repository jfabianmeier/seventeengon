package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.fabianmeier.seventeengon.naming.CompName;
import de.fabianmeier.seventeengon.naming.Sentence;
import de.fabianmeier.seventeengon.naming.SentencePattern;
import de.fabianmeier.seventeengon.naming.SequentialNameMapping;

/**
 * Test sequential mapping
 * 
 * @author jfabi
 *
 */
public class SequentialNameMappingTest
{
	private SequentialNameMapping mapping;

	private Sentence sourceSentence = new Sentence("Sei A der Punkt B");
	private Sentence sinkSentence = new Sentence("Sei C der Punkt C");
	private Sentence wrongSink = new Sentence("Sei D der Punkt, der hier liegt");

	/**
	 * 
	 */
	@Before
	public void setup()
	{
		mapping = new SequentialNameMapping(sourceSentence, sinkSentence);
	}

	/**
	 * 
	 */
	@Test
	public void testGetPattern()
	{
		assertEquals(mapping.getPattern(), new SentencePattern("Sei X der Punkt X"));
	}

	/**
	 * 
	 */
	@Test
	public void testException()
	{
		try
		{
			new SequentialNameMapping(sourceSentence, wrongSink);
			fail();
		} catch (IllegalArgumentException e)
		{
			// do nothing
		}

	}

	/**
	 * 
	 */
	@Test
	public void testGetSinkForSource()
	{
		assertEquals(mapping.getSinkForSource(new CompName("A")), new CompName("C"));
	}

	/**
	 * 
	 */
	@Test
	public void testGetSourceForSink()
	{
		assertEquals(mapping.getSourceForSink(new CompName("C")), new CompName("B"));
	}

}
