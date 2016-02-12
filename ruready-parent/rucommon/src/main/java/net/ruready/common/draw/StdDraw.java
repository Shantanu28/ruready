/*****************************************************************************************
 * Source File: StdDraw.java
 ****************************************************************************************/
package net.ruready.common.draw;

import java.awt.Color;
import java.awt.Font;

import net.ruready.common.misc.Utility;

/**
 * A small library of standard drawing methods.
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
public class StdDraw implements Utility
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	private static Draw draw;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Prevent construction. This is a library.
	 */
	private StdDraw()
	{

	}

	// ========================= METHODS ===================================

	// create a canvas with drawing area width-by-height
	public static void create(int width, int height)
	{

		// If we don't already have one, create a new one
		if (draw == null)
			draw = new Draw(width, height);
		else
			throw new RuntimeException("Attempted to call StdDraw.create twice");
	}

	public static void setScale(double xmin, double ymin, double xmax, double ymax)
	{
		draw.setScale(xmin, ymin, xmax, ymax);
	}

	public static int width()
	{
		return draw.width();
	}

	public static int height()
	{
		return draw.height();
	}

	public static double x()
	{
		return draw.x();
	}

	public static double y()
	{
		return draw.y();
	}

	public static double orientation()
	{
		return draw.orientation();
	}

	public static void fillOn()
	{
		draw.fillOn();
	}

	public static void fillOff()
	{
		draw.fillOff();
	}

	public static void clear()
	{
		draw.clear();
	}

	public static void clear(Color bg)
	{
		draw.clear(bg);
	}

	public static void pause(int delay)
	{
		draw.pause(delay);
	}

	public static void setColor(Color c)
	{
		draw.setColor(c);
	}

	public static void setColorRGB(int r, int g, int b)
	{
		draw.setColorRGB(r, g, b);
	}

	public static void setColorHSB(int h, int s, int b)
	{
		draw.setColorHSB(h, s, b);
	}

	public static void setColorHSB(double h, double s, double b)
	{
		draw.setColorHSB(h, s, b);
	}

	public static void setColorRandom()
	{
		draw.setColorRandom();
	}

	public static void setFont(Font font)
	{
		draw.setFont(font);
	}

	public static void spot()
	{
		draw.spot();
	}

	public static void spot(double w, double h)
	{
		draw.spot(w, h);
	}

	public static void spot(double d)
	{
		draw.spot(d);
	}

	public static void spot(String s)
	{
		draw.spot(s);
	}

	public static void spot(String s, double w, double h)
	{
		draw.spot(s, w, h);
	}

	public static void rotate(double angle)
	{
		draw.rotate(angle);
	}

	public static void write(String s)
	{
		draw.write(s);
	}

	public static void show()
	{
		draw.show();
	}

	public static void save(String s)
	{
		draw.save(s);
	}

	public static void moveTo(double x, double y)
	{
		draw.moveTo(x, y);
	}

	public static void lineTo(double x, double y)
	{
		draw.lineTo(x, y);
	}

}
