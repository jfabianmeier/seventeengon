package de.fabianmeier.seventeengon.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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

	protected void setColourAndStroke(Graphics2D g2d)
	{
		if (visibility == 0)
			g2d.setStroke(new BasicStroke(0.5f));
		g2d.setPaint(Color.lightGray);

		if (visibility == 1)
			g2d.setStroke(new BasicStroke(0.7f));
		g2d.setPaint(Color.black);

		if (visibility == 2)
		{
			g2d.setStroke(new BasicStroke(1));
			g2d.setPaint(Color.red);
		}
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

	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

}
