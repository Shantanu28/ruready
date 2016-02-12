/*****************************************************************************************
 * Source File: ColorWrapper.java
 ****************************************************************************************/
package net.ruready.common.draw;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.io.Serializable;

/**
 * A wrapper for AWT Color that returns the RGB model for JSP printouts. Immutable.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 16, 2007
 */
public class ColorWrapper extends Color implements Serializable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1;

	// ========================= FIELDS ====================================

	// RGB code
	protected String rgb;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a color from fields.
	 * 
	 * @param cspace
	 * @param components
	 * @param alpha
	 */
	public ColorWrapper(ColorSpace cspace, float[] components, float alpha)
	{
		super(cspace, components, alpha);
	}

	/**
	 * Create a color from floating point RGB + alpha values.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 * @param a
	 *            alpha
	 */
	public ColorWrapper(float r, float g, float b, float a)
	{
		super(r, g, b, a);
	}

	/**
	 * Create a color from RGB values.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 */
	public ColorWrapper(float r, float g, float b)
	{
		super(r, g, b);
	}

	/**
	 * Create a color from RGB hashcode.
	 * 
	 * @param rgba
	 *            RGB hashcode (includes alpha)
	 * @param hasalpha
	 *            uses alpha
	 */
	public ColorWrapper(int rgba, boolean hasalpha)
	{
		super(rgba, hasalpha);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public ColorWrapper(int r, int g, int b, int a)
	{
		super(r, g, b, a);
	}

	/**
	 * Create a color from integer-valued RGB + alpha values.
	 * 
	 * @param r
	 *            red
	 * @param g
	 *            green
	 * @param b
	 *            blue
	 * @param a
	 *            alpha
	 */
	public ColorWrapper(int r, int g, int b)
	{
		super(r, g, b);
	}

	/**
	 * Create a color from RGB hashcode.
	 * 
	 * @param rgba
	 *            RGB hashcode (doesn't include alpha)
	 */
	public ColorWrapper(int rgb)
	{
		super(rgb);
	}

	/**
	 * Wrap a color object.
	 * 
	 * @param r
	 *            color object to wrap
	 */
	public ColorWrapper(Color r)
	{
		super(r.getRGB());
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the RGB string representation of this color.
	 * 
	 * @return Returns the string representation of this color.
	 */
	public String getRgb()
	{
		int c = 0x10000 * getRed() + 0x100 * getGreen() + getBlue();
		String zero = "000000";
		int temp = c;
		while (temp > 0)
		{
			temp /= 0x10;
			zero = zero.substring(0, zero.length() - 1);
		}
		return zero + Integer.toHexString(c);
	}
}
