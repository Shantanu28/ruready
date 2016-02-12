/*****************************************************************************************
 * Source File: ComplexBenchmark.java
 ****************************************************************************************/
/*
 */

package test.ruready.common.math.complex;

import java.text.DecimalFormat;
import java.util.Date;

import net.ruready.common.math.complex.Complex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * File: ComplexBenchmark.java -- Benchmarker for Complex class.
 * <p>
 * Original Copyright (c) 1997 - 2001, Alexander Anderson. This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version. This program
 * is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * Last change: ALM 23 Mar 2001 9:58 pm
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 14, 2007
 */
public class ComplexBenchmark
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ComplexBenchmark.class);

	// ========================= FIELDS ====================================

	static final String MODULE = ComplexBenchmark.class.getName();

	static final String VERSION = "1.0.4";

	static final String DATE = "Fri 23-Mar-2001 9:58 pm";

	static final String AUTHOR = "sandy@almide.demon.co.uk";

	static final String REMARK = "Benchmarker for Complex class";

	static final char MARK_CHAR = '-';

	static final DecimalFormat stopwatch = new DecimalFormat("###0.00");

	static int iterations = 1000000;

	public static void main(String[] args)
	{
		Date start, finish;
		double dummy = 0;
		boolean bool = false;

		if (args.length > 0)
		{
			try
			{
				iterations = Integer.valueOf(args[0]).intValue();

				if (iterations < 0)
				{
					throw new NumberFormatException();
				}// endif
			}
			catch (NumberFormatException e)
			{
				logger.debug("");
				versionInfo();
				logger.debug("");
				logger.debug("usage:  ");
				logger.debug("java " + MODULE + " [loop iterations]");

				System.exit(1);
			}// endtry
		}// endif

		logger.debug("");
		System.out
				.println("    This tests on your system, the execution speed of the Complex");
		logger.debug("    functions by repeatedly calling each function " + iterations
				+ " times.");
		logger.debug("");
		System.out
				.println("                      Alexander Anderson <sandy@almide.demon.co.uk>");
		logger.debug("");

		Complex z = new Complex(0, 0);
		Complex c = new Complex(0.1234, 0.5678);

		System.getProperties().list(System.out);
		logger.debug("");
		versionInfo();
		logger.debug("");
		logger.debug("Benchmark " + iterations + " started! ...");
		logger.debug("");
		logger.debug("(Please be patient.  Each '" + MARK_CHAR + "' is one second.)");
		logger.debug("");

		logger.debug("Empty Loop   ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			// skip
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("Constructor  ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = new Complex(0.1234, 5.6789);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("polar()      ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = Complex.polar(0.1234, 5.6789);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("pow(r, r)    ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			dummy = Math.pow(0.1234, 5.6789);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("pow(r, C)    ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = Complex.pow(0.1234, c);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("pow(C, r)    ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = Complex.pow(c, 0.1234);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("pow(C, C)    ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = Complex.pow(c, c);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));
		/*
		 * logger.debug("equals() "); start = new Date(); for (int i = 1; i <=
		 * iterations; i++) { bool = c.equals(c); }//endfor finish = new Date();
		 * logger.debug(stars(start, finish));
		 */

		logger.debug("clone()      ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.clone();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("re()         ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			dummy = c.re();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("norm()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			dummy = c.norm();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("abs()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			dummy = c.abs();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("arg()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			dummy = c.arg();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("neg()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.neg();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("conj()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.conj();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("scale()      ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.scale(1.234);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("add()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.plus(c);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("sub()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.minus(c);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("mul()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.times(c);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("div()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.over(c);
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("sqrt()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.sqrt();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("exp()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.exp();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("log()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.log();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));
		/*
		 * logger.debug("log10() "); start = new Date(); for (int i = 1; i <=
		 * iterations; i++) { z = c.log10(); }//endfor finish = new Date();
		 * logger.debug(stars(start, finish));
		 */
		logger.debug("sin()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.sin();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("cos()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.cos();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("tan()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.tan();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("cosec()      ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.cosec();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("sec()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.sec();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("cot()        ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.cot();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("sinh()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.sinh();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("cosh()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.cosh();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("tanh()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.tanh();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("asin()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.asin();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("acos()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.acos();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("atan()       ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.atan();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("asinh()      ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.asinh();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("acosh()      ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.acosh();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("atanh()      ");
		start = new Date();
		for (int i = 1; i <= iterations; i++)
		{
			z = c.atanh();
		}// endfor
		finish = new Date();
		logger.debug(stars(start, finish));

		logger.debug("");
		logger.debug("... Benchmark " + iterations + " finished.");

		// Keep compiler happy
		if (z.abs() == 0)
		{
		}
		if (dummy == 0)
		{
		}
		if (bool)
		{
		}
	}// end main(String[])

	static String stars(Date start, Date finish)
	{
		double starSeconds = (finish.getTime() - start.getTime()) / 1000.0;
		StringBuffer starString = new StringBuffer();

		for (int i = 1; i <= Math.round(starSeconds); i++)
		{
			starString = starString.append(MARK_CHAR);
		}// endfor

		starString.append(" ").append(stopwatch.format(starSeconds));

		return starString.toString();
	}// end stars(Date,Date)

	static void versionInfo()
	{
		logger.debug("Benchmarker...");
		logger.debug("    Module : " + MODULE);
		logger.debug("    Version: " + VERSION);
		logger.debug("    Date   : " + DATE);
		logger.debug("    Author : " + AUTHOR);
		logger.debug("    Remark : " + REMARK);
		logger.debug("Benchmarking...");
		logger.debug("    Module : " + Complex.class.getName());
		logger.debug("    Version: " + Complex.VERSION);
		logger.debug("    Date   : " + Complex.DATE);
		logger.debug("    Author : " + Complex.AUTHOR);
		logger.debug("    Remark : " + Complex.REMARK);
	}// end versionInfo()

}// end ComplexBenchmark

/*
 * C A U T I O N E X P L O S I V E B O L T S -- REMOVE BEFORE ENGAGING REPLY // //
 * Kelly and Sandy Anderson <kelsan@explosive-alma-services-bolts.co.uk> //
 * (alternatively kelsan_odoodle at ya who period, see oh em) // Alexander
 * (Sandy) 1B5A DF3D A3D9 B932 39EB 3F1B 981F 4110 27E1 64A4 // Kelly 673F 6751
 * 6DBA 196F E8A8 6D87 4AEC F35E E9AD 099B // Homepages
 * http://www.explosive-alma-services-bolts.co.uk/
 */
