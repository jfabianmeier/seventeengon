package de.fabianmeier.seventeengon.shapes;

import java.util.HashSet;
import java.util.Set;

public class GshapeImpl implements Gshape
{

	private Set<Pshape> pshapeSet;
	private int visibility;
	private String label;

	public GshapeImpl(Set<Pshape> pshapeSet, int visibility, String label)
	{
		this.pshapeSet = new HashSet<Pshape>(pshapeSet);
		for (Pshape pshape : pshapeSet)
		{
			pshape.setLabel(label);
			pshape.setVisibility(visibility);
		}
		this.visibility = visibility;
		this.label = label;
	}

	public Set<Pshape> getPrimitiveShapes()
	{
		return new HashSet<Pshape>(pshapeSet);
	}

	public XYpoint getSamplePoint(int sampleNumber)
	{

		if (pshapeSet.isEmpty())
			return null;

		int counter = sampleNumber;

		int maxDim = 0;

		for (Pshape pshape : pshapeSet)
		{
			if (pshape.getDimension() > maxDim)
				maxDim = pshape.getDimension();
		}

		while (true)
		{
			for (Pshape pshape : pshapeSet)
			{
				if (pshape.getDimension() == maxDim
						&& counter % pshapeSet.size() == 0)
				{
					return pshape.getSamplePoint(counter);
				}
				counter++;

			}
		}

	}

	public int getVisibility()
	{
		return visibility;
	}

	public String getLabel()
	{
		return label;
	}

}
