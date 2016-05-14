/**
 * 
 */
package de.fabianmeier.seventeengon.util;

/**
 * @author JFM
 *
 */
public class GeoVisible
{

	public static GeoVisible getStandard()
	{
		return new GeoVisible(false, false, false, false);
	}

	private GeoVisible(boolean invisible, boolean marked, boolean dotted,
			boolean nameHidden)
	{
		this.invisible = invisible;
		this.marked = marked;
		this.dotted = dotted;
		this.nameHidden = nameHidden;
	}

	private final boolean invisible;
	private final boolean marked;
	private final boolean dotted;
	private final boolean nameHidden;

	public boolean isInvisible()
	{
		return invisible;
	}
	public boolean isMarked()
	{
		return marked;
	}
	public boolean isDotted()
	{
		return dotted;
	}
	public boolean isNameHidden()
	{
		return nameHidden;
	}

	public GeoVisible makeVisible()
	{
		return new GeoVisible(false, this.marked, this.dotted, this.nameHidden);
	}

	public GeoVisible makeInvisible()
	{
		return new GeoVisible(true, this.marked, this.dotted, this.nameHidden);
	}

	public GeoVisible mark()
	{
		return new GeoVisible(this.invisible, true, this.dotted,
				this.nameHidden);
	}

	public GeoVisible unmark()
	{
		return new GeoVisible(this.invisible, false, this.dotted,
				this.nameHidden);
	}

	public GeoVisible makeDotted()
	{
		return new GeoVisible(this.invisible, this.marked, true,
				this.nameHidden);
	}

	public GeoVisible makeUnDotted()
	{
		return new GeoVisible(this.invisible, this.marked, false,
				this.nameHidden);
	}

	public GeoVisible hideName()
	{
		return new GeoVisible(this.invisible, this.marked, this.dotted, true);
	}

	public GeoVisible showName()
	{
		return new GeoVisible(this.invisible, this.marked, this.dotted, false);
	}

	@Override
	public String toString()
	{
		String back = "";
		if (invisible)
			back += "Invisible ";
		else
			back += "Visible ";
		if (marked)
			back += "marked ";
		if (dotted)
			back += "dotted ";
		if (nameHidden)
			back += "with hidden name";

		return back;
	}

}
