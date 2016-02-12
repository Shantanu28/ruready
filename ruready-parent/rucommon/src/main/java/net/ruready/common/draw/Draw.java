/*****************************************************************************************
 * Source File: Draw.java
 ****************************************************************************************/
package net.ruready.common.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.ruready.common.rl.CommonNames;

/**
 * A simple graphics library. Based on http://www.cs.princeton.edu/introcs/24inout but
 * changed quite a bit.
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
@SuppressWarnings("hiding")
public class Draw
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	private final BufferedImage offscreenImage; // double buffered image

	private final BufferedImage onscreenImage; // double buffered image

	private final Graphics2D offscreen;

	private final Graphics2D onscreen;

	private final int width, height; // size of drawing area in pixels

	private JFrame frame; // the frame for drawing to the screen

	private Insets insets; // frame border

	private double xmin, xmax, ymin, ymax; // boundary of (x, y) coordinates

	private double x = 0.0, y = 0.0; // turtle is at coordinate (x, y)

	// facing this many degrees counterclockwise
	private double orientation = 0.0;

	private Color background = Color.WHITE; // background color

	private Color foreground = Color.BLACK; // foreground color

	private boolean fill = true; // fill in circles and rectangles?

	private String title = CommonNames.MISC.EMPTY_STRING; // title of the frame in the menubar

	private Font font = new Font("Serif", Font.PLAIN, 16);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a new drawing region of given dimensions.
	 * 
	 * @param width
	 * @param height
	 */
	public Draw(int width, int height)
	{
		this.width = width;
		this.height = height;
		if (width <= 0 || height <= 0)
			throw new RuntimeException("Illegal dimension");
		offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		offscreen = (Graphics2D) offscreenImage.getGraphics();
		onscreen = (Graphics2D) onscreenImage.getGraphics();
		setScale(0.0, 0.0, 1.0, 1.0);
		clear();
	}

	// ========================= METHODS ====================================

	// #########################################
	// Simple state-changing methods
	// #########################################

	public void setTitle(String s)
	{
		title = s;
	}

	public void fillOn()
	{
		fill = true;
	}

	public void fillOff()
	{
		fill = false;
	}

	public void xorOn()
	{
		offscreen.setXORMode(background);
	}

	public void xorOff()
	{
		offscreen.setPaintMode();
	}

	// rotate counterclockwise in degrees
	public void rotate(double angle)
	{
		orientation += angle;
	}

	// #########################################
	// Affine transform
	// #########################################
	/**
	 * Change the user coordinate system. Update (x, y) so that they stay at same screen
	 * position. Update orientation so that it stays the same relative to screen
	 * coorindates There may be some bugs when using scaling with images.
	 * 
	 * @param xmin
	 *            new minimum <code>x</code> position.
	 * @param ymin
	 *            new maximum <code>x</code> position.
	 * @param xmax
	 *            new minimum <code>y</code> position.
	 * @param ymax
	 *            new maximum <code>y</code> position.
	 */
	public void setScale(double xmin, double ymin, double xmax, double ymax)
	{
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}

	// scale from user coordinates to screen coordinates
	public double scaleX(double x)
	{
		return width * (x - xmin) / (xmax - xmin);
	}

	public double scaleY(double y)
	{
		return height * (ymax - y) / (ymax - ymin);
	}

	public double factorX(double w)
	{
		return w * width / Math.abs(xmax - xmin);
	}

	public double factorY(double h)
	{
		return h * height / Math.abs(ymax - ymin);
	}

	// scale from screen coordinates to user coordinates
	public double toUserX(double x)
	{
		return (xmax - xmin) * (x - insets.left) / width + xmin;
	}

	public double toUserY(double y)
	{
		return (ymax - ymin) * (height - y + insets.top) / height + ymin;
	}

	// #########################################
	// Background and foreground colors
	// #########################################
	/**
	 * Clear the background.
	 */
	public void clear()
	{
		offscreen.setColor(background);
		offscreen.fillRect(0, 0, width, height);
		offscreen.setColor(foreground);
	}

	/**
	 * Clear the background with a new color.
	 * 
	 * @param color
	 *            new background color
	 */
	public void clear(Color color)
	{
		background = color;
		clear();
	}

	/**
	 * Set the pen size.
	 * 
	 * @param size
	 *            new pen size
	 */
	public void setPenSize(double size)
	{
		BasicStroke stroke = new BasicStroke((float) size);
		offscreen.setStroke(stroke);
	}

	/**
	 * Set the foreground color.
	 * 
	 * @param color
	 *            new foreground color
	 */
	public void setColor(Color color)
	{
		foreground = color;
		offscreen.setColor(foreground);
	}

	/**
	 * Set the foreground color using red-green-blue (inputs between 0 and 255).
	 * 
	 * @param r
	 *            red value
	 * @param g
	 *            green value
	 * @param b
	 *            blue value
	 */
	public void setColorRGB(int r, int g, int b)
	{
		setColor(new Color(r, g, b));
	}

	/**
	 * Set the foreground color using hue-saturation-brightness (inputs between 0 and
	 * 255).
	 * 
	 * @param h
	 *            hue value
	 * @param s
	 *            saturation value
	 * @param b
	 *            brightness value
	 */
	public void setColorHSB(int h, int s, int b)
	{
		setColor(Color.getHSBColor(1.0f * h / 255, 1.0f * s / 255, 1.0f * b / 255));
	}

	/**
	 * Set the foreground color using hue-saturation-brightness (inputs between 0.0 and
	 * 1.0 and 255).
	 * 
	 * @param h
	 *            hue value
	 * @param s
	 *            saturation value
	 * @param b
	 *            brightness value
	 */
	public void setColorHSB(double h, double s, double b)
	{
		setColor(Color.getHSBColor((float) h, (float) s, (float) b));
	}

	// set the foreground color to a random color
	public void setColorRandom()
	{
		setColorHSB((int) (Math.random() * 256), 255, 255);
	}

	// #########################################
	// Cursor (pen) movements
	// #########################################
	// go to (x, y) with pen up
	public void moveTo(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	// go to (x, y) with the pen down
	public void lineTo(double x, double y)
	{
		offscreen.draw(new Line2D.Double(scaleX(this.x), scaleY(this.y), scaleX(x),
				scaleY(y)));
		this.x = x;
		this.y = y;
	}

	// walk forward with the pen down
	public void forward(double d)
	{
		double oldx = x;
		double oldy = y;
		x += d * Math.cos(Math.toRadians(orientation));
		y += d * Math.sin(Math.toRadians(orientation));
		offscreen
				.draw(new Line2D.Double(scaleX(x), scaleY(y), scaleX(oldx), scaleY(oldy)));
	}

	// #########################################
	// Draw spots
	// #########################################

	/**
	 * Draw pixel at current location.
	 */
	public void spot()
	{
		spot(0.0);
	}

	/**
	 * Draw circle of diameter d, centered at current location; degenerate to pixel if
	 * small.
	 * 
	 * @param d
	 *            diameter
	 */
	public void spot(double d)
	{
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(d);
		double hs = factorY(d);
		if (ws <= 1 && hs <= 1)
			offscreen.fillRect((int) Math.round(xs), (int) Math.round(ys), 1, 1);
		else if (fill)
			offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
		else if (!fill)
			offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
	}

	/**
	 * draw w-by-h rectangle, centered at current location; degenerate to single pixel if
	 * too small.
	 * 
	 * @param w
	 *            rectangle width
	 * @param h
	 *            rectangle height
	 */
	public void spot(double w, double h)
	{
		// screen coordinates
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(w);
		double hs = factorY(h);
		offscreen.rotate(Math.toRadians(orientation), xs, ys);
		if (ws <= 1 && hs <= 1)
			offscreen.fillRect((int) Math.round(xs), (int) Math.round(ys), 1, 1);
		else if (fill)
			offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
		else if (!fill)
			offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
		offscreen.rotate(Math.toRadians(-orientation), xs, ys);
	}

	// #########################################
	// Get an image from the given filename
	// #########################################
	private Image getImage(String filename)
	{

		// try to read from file
		ImageIcon icon = new ImageIcon(filename);

		// in case file is inside a .jar
		if (icon.getImageLoadStatus() != MediaTracker.COMPLETE)
		{
			URL url = Draw.class.getResource(filename);
			if (url == null)
				throw new RuntimeException("image " + filename + " not found");
			icon = new ImageIcon(url);
		}

		return icon.getImage();
	}

	public void drawPolygon(double[] x, double[] y)
	{
		int N = x.length;
		GeneralPath path = new GeneralPath();
		path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
		for (int i = 0; i < N; i++)
			path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
		path.closePath();

		// offscreen.rotate(Math.toRadians(orientation), xs, ys);
		if (fill)
			offscreen.fill(path);
		else
			offscreen.draw(path);
		// offscreen.rotate(Math.toRadians(-orientation), xs, ys);
	}

	/**
	 * Draw spot using gif - fix to be centered at (x, y).
	 * 
	 * @param s
	 */
	public void spot(String s)
	{
		Image image = getImage(s);
		double xs = scaleX(x);
		double ys = scaleY(y);
		int ws = image.getWidth(null);
		int hs = image.getHeight(null);

		// center of rotation is (xs, ys)
		offscreen.rotate(Math.toRadians(orientation), xs, ys);
		offscreen.drawImage(image, (int) (xs - ws / 2.0), (int) (ys - hs / 2.0), null);
		offscreen.rotate(Math.toRadians(-orientation), xs, ys);
	}

	/**
	 * Draw spot using gif, centered on (x, y), scaled of size w-by-h center vs. !center.
	 * 
	 * @param s
	 * @param w
	 *            width
	 * @param h
	 *            height
	 */
	public void spot(String s, double w, double h)
	{
		Image image = getImage(s);
		double xs = scaleX(x);
		double ys = scaleY(y);
		double ws = factorX(w);
		double hs = factorY(h);
		if (ws <= 1 && hs <= 1)
			offscreen.fillRect((int) Math.round(xs), (int) Math.round(ys), 1, 1);

		else
		{
			// center of rotation is (xs, ys)
			offscreen.rotate(Math.toRadians(orientation), xs, ys);
			offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math
					.round(ys - hs / 2.0), (int) Math.round(ws), (int) Math.round(hs),
					null);
			offscreen.rotate(Math.toRadians(-orientation), xs, ys);
		}
	}

	// #########################################
	// Writing text
	// #########################################

	/**
	 * Write the given string in the current font.
	 * 
	 * @param font
	 *            font to use
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * write the given string in the current font, center on the current location.
	 * 
	 * @param s
	 *            string to write
	 */
	public void write(String s)
	{
		offscreen.setFont(font);
		FontMetrics metrics = offscreen.getFontMetrics();
		double xs = scaleX(x);
		double ys = scaleY(y);
		int ws = metrics.stringWidth(s);
		int hs = metrics.getDescent();
		offscreen.rotate(Math.toRadians(orientation), xs, ys);
		offscreen.drawString(s, (float) (xs - ws / 2.0), (float) (ys + hs));
		offscreen.rotate(Math.toRadians(-orientation), xs, ys);
	}

	/*************************************************************************************
	 * Display the image on screen or save to file
	 ************************************************************************************/
	// wait for a short while
	public void pause(int delay)
	{
		show();
		try
		{
			Thread.sleep(delay);
		}
		catch (InterruptedException e)
		{
		}
	}

	// view on-screen, creating new frame if necessary
	public void show()
	{
		// create the GUI for viewing the image if needed
		if (frame == null)
		{
			frame = new JFrame();
			ImageIcon icon = new ImageIcon(onscreenImage);
			frame.setContentPane(new JLabel(icon));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes
			// all
			// windows
			// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //
			// closes only current window
			frame.setTitle(title);
			frame.setResizable(false);
			frame.pack();
			frame.setVisible(true);
			insets = frame.getInsets(); // must be after frame is rendered
		}

		// draw
		onscreen.drawImage(offscreenImage, 0, 0, null);
		frame.setTitle(title);
		frame.repaint();
	}

	// save to file - suffix can be png, jpg, or gif
	public void save(String filename)
	{
		File file = new File(filename);
		String suffix = filename.substring(filename.lastIndexOf('.') + 1);
		try
		{
			ImageIO.write(offscreenImage, suffix, file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// for emebedding into a JPanel
	public JLabel getJLabel()
	{
		if (offscreenImage == null)
			return null; // no image available
		ImageIcon icon = new ImageIcon(offscreenImage);
		JLabel jlabel = new JLabel(icon);
		jlabel.setAlignmentX(0.5f);
		return jlabel;
	}

	// ========================= GETTERS & SETTERS ==========================

	public double x()
	{
		return x;
	}

	public double y()
	{
		return y;
	}

	public double orientation()
	{
		return orientation;
	}

	public int width()
	{
		return width;
	}

	public int height()
	{
		return height;
	}

	public boolean isFillOn()
	{
		return fill;
	}
}
