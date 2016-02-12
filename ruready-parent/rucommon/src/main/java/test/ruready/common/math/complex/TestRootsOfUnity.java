/*****************************************************************************************
 * Source File: TestRootsOfUnity.java
 ****************************************************************************************/
package test.ruready.common.math.complex;

import net.ruready.common.math.complex.Complex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Compute the N Nth roots of unity and check them by direct multiplication.
 * <p>
 * Example output:<br>
 * <blockquote>
 * 
 * <pre>
 *   java RootsOfUnity
 * <br>
 *    4 1.0 + 0.0i error = 0.0
 * <br>
 *    6.123233995736766E-17 + 1.0i error = 2.4492935982947064E-16
 * <br>
 *    -1.0 + 1.2246467991473532E-16i error = 4.898587196589413E-16
 * <br>
 *    -1.8369701987210297E-16 + -1.0i error = 7.347880794884119E-16
 * <br>
 * </pre>
 * 
 * </blockquote>
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
 * @version Sep 8, 2007
 */
public class TestRootsOfUnity extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestRootsOfUnity.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * 
	 */
	@Test
	public void testRootsOfUnity()
	{
		for (int N = 1; N < 7; N++)
		{
			logger.debug("N = " + N);
			Complex one = new Complex(1, 0);

			for (int i = 0; i < N; i++)
			{
				// compute root of unity
				double x = Math.cos(2.0 * Math.PI * i / N);
				double y = Math.sin(2.0 * Math.PI * i / N);
				Complex t = new Complex(x, y);
				logger.info("root #" + i + " = " + t);

				// test mult by computing t^N and comparing to 1 + 0i
				Complex z = one;
				for (int j = 0; j < N; j++)
					z = z.times(t);

				// compute error
				Complex error = z.minus(one);
				// logger.info("error = " + error.abs());
				Assert.assertEquals(0.0, error.abs(), 1e-14);
			}
		}
	}

	// ========================= TESTING ====================================
}
