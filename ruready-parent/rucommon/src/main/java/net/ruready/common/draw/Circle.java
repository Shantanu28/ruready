/*****************************************************************************************
 * Source File: Circle.java
 ****************************************************************************************/
package net.ruready.common.draw;

import java.awt.Color;
import java.awt.Graphics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * By Scot Drysdale on 4/5/00 to show simple circle objects. Based on a C++ demo by THC.
 * See http://www.cs.dartmouth.edu/~farid/teaching/cs15/cs5/lectures/0407/Circle.java
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
public class Circle
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Circle.class);

	// ========================= FIELDS ====================================

	// x and y coordinates of circle center
	private int myX, myY;

	// Circle radius
	private int myRadius;

	// Circle color
	private Color myColor;

	// Circle background color
	private Color bgColor;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructor for Circle. Called to create a circle object with center (x, y), radius
	 * r, color circleColor, and background color bgColor.
	 * 
	 * @param x
	 * @param y
	 * @param r
	 * @param circleColor
	 * @param bgColor
	 */
	public Circle(int x, int y, int r, Color circleColor, Color bgColor)
	{
		this.myX = x;
		this.myY = y;
		this.myRadius = r;
		this.myColor = circleColor;
		this.bgColor = bgColor;
	}

	// ========================= METHODS ===================================

	/**
	 * Have the Circle object draw itself on the page passed as a parameter.
	 */
	public void draw(Graphics page)
	{
		Color origColor = page.getColor();

		// Set the background color, draw filled circle
		page.setColor(bgColor);
		page.fillOval(myX - myRadius, myY - myRadius, 2 * myRadius, 2 * myRadius);

		// Set the background color, draw filled circle
		page.setColor(myColor);
		page.drawOval(myX - myRadius, myY - myRadius, 2 * myRadius, 2 * myRadius);

		// Restore original page color
		page.setColor(origColor);
	}

	/**
	 * Have the Circle object move itself by deltaX, deltaY.
	 */
	public void move(int deltaX, int deltaY)
	{
		myX += deltaX;
		myY += deltaY;
	}

	/**
	 * Have the Circle change its radius by the scaling factor <code>factor</code>.
	 */
	public void scale(double factor)
	{
		myRadius = (int) (factor * myRadius);
	}

	/**
	 * Have the Circle object return its area.
	 */
	public double areaOf()
	{
		return Math.PI * myRadius * myRadius;
	}
}
