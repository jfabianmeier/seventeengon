/**
 * 
 */
package de.fabianmeier.seventeengon.util;

/**
 * @author JFM
 *
 */
public final class GeoVisible
{

	/**
	 * 
	 * @return the standard visibility
	 */
	public static GeoVisible getStandard()
	{
		return new GeoVisible(false, false, false, false);
	}

	/**
	 * Private constructor to create a visibility object.
	 * 
	 * @param invisible
	 *            if the object should be invisible
	 * @param marked
	 *            if it should be marked (shown larger or red)
	 * @param dotted
	 *            if it should be drawn dotted
	 * @param nameHidden
	 *            if the name should be drawn or hidden.
	 */
	private GeoVisible(boolean invisible, boolean marked, boolean dotted, boolean nameHidden)
	{
		this.invisible = invisible;
		this.marked = marked;
		this.dotted = dotted;
		this.nameHidden = nameHidden;
	}

	/**
	 * Object is invisible.
	 */
	private final boolean invisible;

	/**
	 * Object is marked.
	 */
	private final boolean marked;

	/**
	 * Object is dotted.
	 */
	private final boolean dotted;

	/**
	 * Object's name is hidden.
	 */
	private final boolean nameHidden;

	/**
	 * 
	 * @return Whether the object is invisible
	 */
	public boolean isInvisible()
	{
		return invisible;
	}

	/**
	 * 
	 * @return whether the object is marked
	 */
	public boolean isMarked()
	{
		return marked;
	}

	/**
	 * 
	 * @return whether the object is dotted
	 */
	public boolean isDotted()
	{
		return dotted;
	}

	/**
	 * 
	 * @return if the name is hidden
	 */
	public boolean isNameHidden()
	{
		return nameHidden;
	}

	/**
	 * 
	 * @return returns a copy which is visible
	 */
	public GeoVisible makeVisible()
	{
		return new GeoVisible(false, this.marked, this.dotted, this.nameHidden);
	}

	/**
	 * 
	 * @return a copy that is invisible
	 */
	public GeoVisible makeInvisible()
	{
		return new GeoVisible(true, this.marked, this.dotted, this.nameHidden);
	}

	/**
	 * 
	 * @return a copy that is marked
	 */
	public GeoVisible mark()
	{
		return new GeoVisible(this.invisible, true, this.dotted, this.nameHidden);
	}

	/**
	 * 
	 * @return a copy that is not marked
	 */
	public GeoVisible unmark()
	{
		return new GeoVisible(this.invisible, false, this.dotted, this.nameHidden);
	}

	/**
	 * 
	 * @return a copy that is dotted
	 */
	public GeoVisible makeDotted()
	{
		return new GeoVisible(this.invisible, this.marked, true, this.nameHidden);
	}

	/**
	 * 
	 * @return a copy that is not dotted
	 */
	public GeoVisible makeUnDotted()
	{
		return new GeoVisible(this.invisible, this.marked, false, this.nameHidden);
	}

	/**
	 * 
	 * @return a copy with hidden name
	 */
	public GeoVisible hideName()
	{
		return new GeoVisible(this.invisible, this.marked, this.dotted, true);
	}

	/**
	 * 
	 * @return a copy where the name is not hidden
	 */
	public GeoVisible showName()
	{
		return new GeoVisible(this.invisible, this.marked, this.dotted, false);
	}

	@Override
	public String toString()
	{
		String back = "";
		if (invisible)
		{
			back += "Invisible ";
		} else
		{
			back += "Visible ";
		}
		if (marked)
		{
			back += "marked ";
		}
		if (dotted)
		{
			back += "dotted ";
		}
		if (nameHidden)
		{
			back += "with hidden name";
		}

		return back;
	}

}
