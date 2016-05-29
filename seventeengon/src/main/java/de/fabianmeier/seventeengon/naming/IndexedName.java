package de.fabianmeier.seventeengon.naming;

public class IndexedName implements GeoName
{
	private BasicName basicName;
	private BasicName index;
	private int numericIndex;

	/**
	 * 
	 * @param name
	 *            A name containing a _
	 */
	public IndexedName(String name)
	{

		if (name.contains("_"))
		{
			String[] split = name.split("_");
			basicName = new BasicName(split[0]);
			if (BasicName.isBasicName(split[1]))
				index = new BasicName(split[1]);
			else
				numericIndex = Integer.parseInt(split[1]);
		}
		else
		{
			throw new IllegalArgumentException("Name does not contain _");
		}

	}

	@Override
	public String toString()
	{
		String back = basicName.toString();

		if (index != null)
		{
			back += index.toString();
		}
		else
		{
			back += numericIndex;
		}
		return back;
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
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
		return this.toString().equals(obj.toString());
	}

}
