package de.fabianmeier.seventeengon.shapes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public abstract class PshapeImpl implements Pshape
{

	private int visibility;
	private String label;

	public PshapeImpl(int visibility, String label)
	{
		this.visibility = visibility;
		this.label = label;
	}

	public Set<Pshape> getPrimitiveShapes()
	{
		Set<Pshape> pShapeSet = new HashSet<Pshape>();
		pShapeSet.add(this);
		return pShapeSet;
	}

	public int getVisibility()
	{
		return visibility;
	}

	public String getLabel()
	{
		return label;
	}

	public static String showValue(double x)
	{
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(x);
	}

	@Override
	public String toString()
	{
		return label;
	}

}
