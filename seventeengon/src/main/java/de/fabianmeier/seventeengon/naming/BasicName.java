/**
 * 
 */
package de.fabianmeier.seventeengon.naming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author JFM
 *
 */
public class BasicName implements GeoName
{
	private static String[] greekLetters = new String[]{"alpha", "beta",
			"gamma", "delta", "epsilon", "zeta", "eta", "theta", "iota",
			"kappa", "lambda", "my", "ny", "xi", "omikron", "pi", "rho",
			"sigma", "tau", "ypsilon", "phi", "psi", "omega"};

	private static Set<String> greekLetterSet = new HashSet<String>(
			Arrays.asList(greekLetters));

	/**
	 * 
	 * @param name
	 *            A possible basic name
	 * @return if name is a basic name.
	 */
	public static boolean isBasicName(String name)
	{
		if (name.length() == 1)
		{
			return Pattern.matches("[a-zA-Z]", name);
		}
		else
		{
			return greekLetterSet.contains(name);
		}
	}

	private String name;

	/**
	 * 
	 * @param name
	 *            A basic name (otherwise, an IllegalArgumentException is
	 *            thrown).
	 */
	public BasicName(String name)
	{
		if (isBasicName(name))
			this.name = name;
		else
			throw new IllegalArgumentException("No basic name.");
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		BasicName other = (BasicName) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

}
