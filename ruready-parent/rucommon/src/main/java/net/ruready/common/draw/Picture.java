/*****************************************************************************************
 * Source File: Picture.java
 ****************************************************************************************/
package net.ruready.common.draw;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Data type for manipulating individual pixels of an image. The original image can be
 * read from a file in JPEG, GIF, or PNG format, or the user can create a blank image of a
 * given size. Includes methods for displaying the image in a window on the screen or
 * saving to a file. <br>
 * <code>
 *         % java Picture image.jpg
 *       
 *         Remarks
 *         -------
 *          - pixel (0, 0) is upper left hand corner
 *            should we change this?
 *       
 *          - if JPEG read in is in grayscale, then you can only set the
 *            color to a graycale value
 *            should we change this?
 * </code>
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
public class Picture implements ActionListener
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	private static final Log logger = LogFactory.getLog(Picture.class);

	// ========================= FIELDS ====================================

	private BufferedImage image; // the rasterized image

	private JFrame frame; // on-screen view

	// ========================= CONSTRUCTORS ==============================

	// create a blank w-by-h image
	public Picture(int w, int h)
	{
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		// set to TYPE_INT_ARGB to support transparency
	}

	// create an image by reading in the PNG, GIF, or JPEG from a filename
	public Picture(String filename)
	{
		try
		{
			image = ImageIO.read(new File(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Could not open file: " + filename);
		}
		if (image == null)
			throw new RuntimeException("Invalid image file: " + filename);
	}

	// create an image by reading in the PNG, GIF, or JPEG from a file
	public Picture(File file)
	{
		try
		{
			image = ImageIO.read(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Could not open file: " + file);
		}
		if (image == null)
			throw new RuntimeException("Invalid image file: " + file);
	}

	// ========================= METHODS ===================================

	// to embed in a JPanel, JFrame or other GUI widget
	public JLabel getJLabel()
	{
		if (image == null)
			return null; // no image available
		ImageIcon icon = new ImageIcon(image);
		return new JLabel(icon);
	}

	// view on-screen, creating new frame if necessary
	public void show()
	{

		// create the GUI for viewing the image if needed
		if (frame == null)
		{
			frame = new JFrame();

			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menuBar.add(menu);
			JMenuItem menuItem1 = new JMenuItem("Save...");
			// menuItem1.addActionListener(new SaveListener());
			menuItem1.addActionListener(this);
			menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit
					.getDefaultToolkit().getMenuShortcutKeyMask()));
			menu.add(menuItem1);
			frame.setJMenuBar(menuBar);

			frame.setContentPane(getJLabel());
			// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.setTitle("Picture Frame");
			frame.setResizable(false);
			frame.pack();
			frame.setVisible(true);
		}

		// draw
		frame.repaint();
	}

	// accessor methods
	public int height()
	{
		return image.getHeight(null);
	}

	public int width()
	{
		return image.getWidth(null);
	}

	// return Color of pixel (i, j)
	public Color get(int i, int j)
	{
		return new Color(image.getRGB(i, j));
	}

	// return grayscale equivalent of pixel (i, j)
	public int getGray(int i, int j)
	{
		Color color = get(i, j);
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int luminance = (int) (0.299 * r + 0.587 * g + 0.114 * b); // NTSC
		// formula
		return luminance;
	}

	// set pixel (i, j) to given grayscale value
	public void setGray(int i, int j, int c)
	{
		Color color = new Color(c, c, c);
		set(i, j, color);
	}

	// change color of pixel (i, j) to c
	public void set(int i, int j, Color c)
	{
		image.setRGB(i, j, c.getRGB());
	}

	/**
	 * Save to picture given filename - suffix must be png, jpg, or gif/
	 * 
	 * @param filename
	 *            file name
	 */
	public void save(String filename)
	{
		save(new File(filename));
	}

	/**
	 * Save picture to given file - suffix must be png, jpg, or gif/
	 * 
	 * @param file
	 *            file object
	 */
	public void save(File file)
	{
		String filename = file.getName();
		String suffix = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
		if (suffix.equals("jpg") || suffix.equals("png"))
		{
			try
			{
				ImageIO.write(image, suffix, file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			logger.error("filename must end with .jpg or .png");
		}
	}

	/**
	 * Open a save dialog when the user selects "Save As" from the menu.
	 * 
	 * @param e
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		FileDialog chooser = new FileDialog(frame, "Use a .png or .jpg extension",
				FileDialog.SAVE);
		chooser.setVisible(true);
		String filename = chooser.getFile();
		if (filename != null)
		{
			save(chooser.getDirectory() + File.separator + chooser.getFile());
		}
	}

	// test client: read in input file and display
	public static void main(String args[])
	{
		Picture pic = new Picture(args[0]);
		pic.show();
	}

}
