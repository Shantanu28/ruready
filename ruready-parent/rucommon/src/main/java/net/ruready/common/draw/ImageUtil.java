/*****************************************************************************************
 * Source File: ImageUtil.java
 ****************************************************************************************/
package net.ruready.common.draw;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import net.ruready.common.exception.SystemException;
import net.ruready.common.misc.Utility;

/**
 * JFrame/Image export, save-to-file in various formats utilities.
 * <p>
 * Note: this library depends on a Sun proprietary API. Replace in the future by an
 * open-source library like ImageJ (http://rsb.info.nih.gov/ij/).
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
@Deprecated
public class ImageUtil implements Utility
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	// private static JPEGImageEncoder encoder = null;

	// private static FileOutputStream fileStream = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ImageUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Create a component image.
	 * 
	 * @param component
	 *            component
	 * @return component image
	 */
	public static BufferedImage createComponentImage(Component component)
	{
		throw new SystemException(
				"Need to find an open-source image library");
		// BufferedImage image = (BufferedImage)
		// component.createImage(component.getWidth(),
		// component.getHeight());
		// Graphics graphics = image.getGraphics();
		// if (graphics != null)
		// {
		// component.paintAll(graphics);
		// }
		// return image;
	}

	/**
	 * Encode image into a file.
	 * 
	 * @param image
	 *            image to write to a file
	 * @param file
	 *            file stream to write to
	 * @throws IOException
	 */
	public static void encodeImage(BufferedImage image, File file)
	// throws IOException
	{
		throw new SystemException(
				"Need to find an open-source image library");
		// fileStream = new FileOutputStream(file);
		// JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(image);
		// encoder = JPEGCodec.createJPEGEncoder(fileStream);
		// encoder.encode(image, encodeParam);
	}

	/**
	 * Encode image into a stream.
	 * 
	 * @param image
	 *            image to write to the stream
	 * @param out
	 *            output stream
	 * @throws IOException
	 */
	public static void encodeImage(BufferedImage image, OutputStream out)
	// throws IOException
	{
		throw new SystemException(
				"Need to find an open-source image library");
		// JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(image);
		// encoder = JPEGCodec.createJPEGEncoder(out);
		// encoder.encode(image, encodeParam);
	}

	/**
	 * Save screen frame to a PNG stream.
	 * 
	 * @param f
	 *            frame to save
	 * @param out
	 *            output stream
	 * @throws IOException
	 * @throws AWTException
	 */
	public static void saveScreenToPNG(JFrame f, OutputStream out)
		throws IOException, AWTException
	{
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(f.getBounds());
		ImageIO.write(image, "png", out);
	}

	/**
	 * Save JFrame to an output stream in JPEG encoding. Based on
	 * http://www.jguru.com/faq/view.jsp?EID=703938
	 * <p>
	 * Note 1: doesn't seem to work. yields a black image.<br>
	 * Note 2: depeneds on a proprietary API.
	 * 
	 * @param f
	 *            frame to save
	 * @param out
	 *            output stream
	 */
	// public static void saveFrameToJPG(JFrame f, OutputStream out)
	// {
	// BufferedImage awtImage = new BufferedImage(f.getWidth(), f.getHeight(),
	// BufferedImage.TYPE_INT_RGB);
	// // Graphics g = awtImage.getGraphics();
	// // f.printAll(g);
	// try {
	// // ByteArrayOutputStream out = new ByteArrayOutputStream();
	// JPEGImageEncoderImpl j = new JPEGImageEncoderImpl(out);
	// j.encode(awtImage);
	// out.close();
	// }
	// catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
