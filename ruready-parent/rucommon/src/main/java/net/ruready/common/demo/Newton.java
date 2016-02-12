/*****************************************************************************************
 * Source File: Newton.java
 ****************************************************************************************/
package net.ruready.common.demo;

import java.awt.Color;

import net.ruready.common.draw.Picture;
import net.ruready.common.math.complex.Complex;

/**
 * Plots an N-by-N grid of points showing which of the four roots that Newton's method
 * converges to in the 2-by-2 box centered at (0, 0).
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
public class Newton
{
	// ========================= METHODS ===================================

	/**
	 * Return the number of Mandelbrot iterations to check z = x + iy is in the the Newton
	 * convergence set
	 * 
	 * @param z
	 *            a complex number
	 * @return number of Mandelbrot iterations to check if z = x + iy is in the the Newton
	 *         convergence set
	 */
	private static Color newton(final Complex z0)
	{
		Complex z = z0;
		final double EPSILON = 0.00000001;
		Complex four = new Complex(4, 0);
		Complex one = new Complex(1, 0);

		Complex root1 = new Complex(1, 0);
		Complex root2 = new Complex(-1, 0);
		Complex root3 = new Complex(0, 1);
		Complex root4 = new Complex(0, -1);

		for (int i = 0; i < 100; i++)
		{
			Complex f = z.times(z).times(z).times(z).minus(one);
			Complex fp = four.times(z).times(z).times(z);
			z = z.minus(f.over(fp));
			if (z.minus(root1).abs() <= EPSILON)
				return Color.WHITE;
			if (z.minus(root2).abs() <= EPSILON)
				return Color.RED;
			if (z.minus(root3).abs() <= EPSILON)
				return Color.GREEN;
			if (z.minus(root4).abs() <= EPSILON)
				return Color.BLUE;
		}
		return Color.BLACK;
	}

	// ========================= DEMO METHODS ==============================

	public static void main(String args[])
	{
		int N = Integer.parseInt(args[0]);
		double xmin = -1.0;
		double ymin = -1.0;
		double width = 2.0;
		double height = 2.0;

		Picture pic = new Picture(N, N);

		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++)
			{
				double x = xmin + i * width / N;
				double y = ymin + j * height / N;
				Complex z = new Complex(x, y);
				Color color = newton(z);
				pic.set(i, j, color);
			}
		}
		pic.show();
	}

}
