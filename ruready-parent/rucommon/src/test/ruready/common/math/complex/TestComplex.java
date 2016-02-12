/*****************************************************************************************
 * Source File: TestComplex.java
 ****************************************************************************************/
package test.ruready.common.math.complex;

import net.ruready.common.math.complex.Complex;
import net.ruready.common.math.real.RealUtil;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tester for class <tt>Complex</tt>.
 * <p>
 * 
 * <pre>
 *                 
 *                     Last change:  ALM  23 Mar 2001    9:13 pm
 * </pre>
 * 
 * Tester for Complex class. Copyright (c) 1997 - 2001, Alexander Anderson. This
 * program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 * <p>
 * C A U T I O N E X P L O S I V E B O L T S -- REMOVE BEFORE ENGAGING REPLY // //
 * Kelly and Sandy Anderson <kelsan@explosive-alma-services-bolts.co.uk> //
 * (alternatively kelsan_odoodle at ya who period, see oh em) // Alexander
 * (Sandy) 1B5A DF3D A3D9 B932 39EB 3F1B 981F 4110 27E1 64A4 // Kelly 673F 6751
 * 6DBA 196F E8A8 6D87 4AEC F35E E9AD 099B // Homepages
 * http://www.explosive-alma-services-bolts.co.uk/
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class TestComplex extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestComplex.class);

	static final String MODULE = TestComplex.class.getName();

	static final String VERSION = "1.0.1";

	static final String DATE = "Fri 23-Mar-2001 9:13 pm";

	static final String AUTHOR = "sandy@almide.demon.co.uk";

	static final String REMARK = "Tester for Complex class";

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Used for testing class <tt>Complex</tt>.
	 * <p>
	 * <dl>
	 * <dt> <b>Usage:</b>
	 * <dd>
	 * 
	 * <pre>
	 *                 
	 *                    &lt;b&gt;java Complex&lt;/b&gt; &lt;i&gt;method&lt;/i&gt;  [&lt;i&gt;a&lt;/i&gt;] [&lt;i&gt;bi&lt;/i&gt;]  [&lt;i&gt;c&lt;/i&gt;] [&lt;i&gt;di&lt;/i&gt;]
	 * </pre>
	 * 
	 * </dl>
	 * <p>
	 * <hr>
	 * <dd><b>Help us <blink>BETA</blink> test class <tt>Complex</tt>...</b></dd>
	 * <p>
	 * <dd>But first, <b><i>enable your browser's Java Console</i></b>. On
	 * Netscape, it's the <b>Options</b> menu, then the <b>Show Java Console</b>
	 * item.
	 * <p>
	 * <applet code="ComplexTestApplet.class" width=300 height=24
	 * alt="ComplexTest Applet" align=texttop > Make yours a Java enabled
	 * browser and OS! </applet>
	 * </p>
	 * <dd>Feel free to test out <i>any</i> method in the <tt>Complex</tt>
	 * documentation. For instance, to test out <i>sqrt(-1)</i> you'd type
	 * <p>
	 * 
	 * <pre>
	 *                 
	 *                         sqrt -1 0
	 * </pre>
	 * 
	 * <p>
	 * and hit the <b>Test!</b> button. You'll see the results appearing on the
	 * Java Console. Please mail me, <a href="mailto:Alexander Anderson
	 * <sandy@almide.demon.co.uk>">Sandy Anderson</a> in England, and give me
	 * your observations. </dd>
	 * <hr>
	 * <p>
	 * Note: System.out is static but NOT final, so it is OK to do PrintStream
	 * redirect = new PrintStream(someDataOutputStream); System.out = redirect;
	 * Instead, however, I generally do something like PrintStream out; if
	 * (someCondition) out = new PrintStream(...); else out = System.out; Then I
	 * use out.println instead of logger.debug.
	 */
	@Test
	public void testComplexArithmetic()
	{
		// Default args
		String[] args =
		{ "exp", "0.0", "0.5", "999", "999" }; // !!!
		Complex expectedResult = new Complex(0.8775825618903728, 0.479425538604203);

		// debug = false; // !!!

		final String nanStr = "NaN";
		final String negInfStr = "-Infinity";
		final String posInfStr = "Infinity";
		final String traceFlagStr = "-trace";

		// boolean setDebug = false;
		int maxArg = 6;
		String method = "unknown";
		double[] doubleArgs =
		{ 0.0, 0.0, 0.0, 0.0, 0.5, 999, 999 }; // !!!
		// epsilon
		// woz
		// ere
		boolean noArgs = false;
		boolean inputError = false;
		// double scalar = 100.0;
		double tolerance;
		String complexClassName;
		Complex z1 = null;
		Complex z2;

		if (args.length == 0)
		{
			inputError = true;
			noArgs = true;
		}
		else
		{
			method = args[0];

			for (int i = 1; ((i < args.length)); i++)
			{ // good enough...
				try
				{
					if (args[i].equalsIgnoreCase(traceFlagStr))
					{
						// setDebug = true;
					}
					else
					{
						if (i >= maxArg)
							break;

						String argsI = args[i];

						if (argsI.equalsIgnoreCase(nanStr))
						{
							doubleArgs[i - 1] = Double.NaN;
						}
						else if (argsI.equalsIgnoreCase(posInfStr))
						{
							doubleArgs[i - 1] = Double.POSITIVE_INFINITY;
						}
						else if (argsI.equalsIgnoreCase(negInfStr))
						{
							doubleArgs[i - 1] = Double.NEGATIVE_INFINITY;
						}
						else
						{
							doubleArgs[i - 1] = Double.valueOf(args[i]).doubleValue();
						}// endif
					}// endif
				}
				catch (NumberFormatException e)
				{
					inputError = true;
				}// endtry
			}// endfor
		}// endif

		z1 = Complex.cart(doubleArgs[0], doubleArgs[1]);
		z2 = Complex.cart(doubleArgs[2], doubleArgs[3]);
		tolerance = doubleArgs[4];
		complexClassName = z1.getClass().getName();

		// Complex.objectCount = 0; // !!!

		logger.debug(CommonNames.MISC.EMPTY_STRING);

		// if (setDebug) debug = true; // !!!

		if (!noArgs)
		{
			logger.debug("z1         ==  " + z1);
			logger.debug("z2         ==  " + z2);
			logger.debug("tolerance  ==  " + tolerance);
			// logger.debug("trace == " + debug); // !!!
			logger.debug(CommonNames.MISC.EMPTY_STRING);

			if (method.equals("real"))
			{
				logger.debug(complexClassName + "." + method + "(" + z1.re() + ")  ==  "
						+ Complex.real(z1.re()));
			}
			else if (method.equals("cart"))
			{
				logger.debug(complexClassName + "." + method + z1 + "  ==  "
						+ Complex.cart(z1.re(), z1.im()));
			}
			else if (method.equals("polar"))
			{
				logger.debug(complexClassName + "." + method + z1 + "  ==  "
						+ Complex.polar(z1.re(), z1.im()));
			}
			else if (method.equals("pow"))
			{
				logger.debug(complexClassName + "." + method + "(" + z1.re() + ", " + z2
						+ ")  ==  " + Complex.pow(z1.re(), z2));
				logger.debug(complexClassName + "." + method + "(" + z1 + ", " + z2.re()
						+ ")  ==  " + Complex.pow(z1, z2.re()));
				logger.debug(complexClassName + "." + method + "(" + z1 + ", " + z2
						+ ")  ==  " + Complex.pow(z1, z2));

			}
			else if (method.equals("isInfinite"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.isInfinite());
				logger.debug(z1 + ".isNaN()  ==  " + z1.isNaN());
			}
			else if (method.equals("isNaN"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.isNaN());
				logger.debug(z1 + ".isInfinite()  ==  " + z1.isInfinite());

			}
			else if (method.equals("equals"))
			{
				logger.debug(z1 + "." + method + "(" + z2 + ", " + tolerance + ")  ==  "
						+ z1.equals(z2));
			}
			else if (method.equals("clone"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.clone());

			}
			else if (method.equals("re"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.re());
			}
			else if (method.equals("im"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.im());

			}
			else if (method.equals("norm"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.norm());
			}
			else if (method.equals("abs"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.abs());

			}
			else if (method.equals("arg"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.arg());

			}
			else if (method.equals("neg"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.neg());
			}
			else if (method.equals("conj"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.conj());
			}
			else if (method.equals("scale"))
			{
				logger.debug(z1 + "." + method + "(" + z2.re() + ")  ==  "
						+ z1.scale(z2.re()));

			}
			else if (method.equals("plus"))
			{
				logger.debug(z1 + "." + method + z2 + "  ==  " + z1.plus(z2));
			}
			else if (method.equals("minus"))
			{
				logger.debug(z1 + "." + method + z2 + "  ==  " + z1.minus(z2));
			}
			else if (method.equals("times"))
			{
				logger.debug(z1 + "." + method + z2 + "  ==  " + z1.times(z2));
			}
			else if (method.equals("over"))
			{
				logger.debug(z1 + "." + method + z2 + "  ==  " + z1.over(z2));

			}
			else if (method.equals("sqrt"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.sqrt());

			}
			else if (method.equals("exp"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.exp());
				Complex actualResult = z1.exp();
				Assert.assertEquals(expectedResult.re(), actualResult.re(),
						RealUtil.MACHINE_DOUBLE_ERROR);
				Assert.assertEquals(expectedResult.im(), actualResult.im(),
						RealUtil.MACHINE_DOUBLE_ERROR);
			}
			else if (method.equals("log"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.log());

				// } else if (method.equals("log10")) {
				// logger.debug(z1 + "." + method + "() == " +
				// z1.log10());

			}
			else if (method.equals("sin"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.sin());
			}
			else if (method.equals("cos"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.cos());
			}
			else if (method.equals("tan"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.tan());

			}
			else if (method.equals("cosec"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.cosec());
			}
			else if (method.equals("sec"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.sec());
			}
			else if (method.equals("cot"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.cot());

			}
			else if (method.equals("sinh"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.sinh());
			}
			else if (method.equals("cosh"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.cosh());
			}
			else if (method.equals("tanh"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.tanh());

			}
			else if (method.equals("asin"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.asin());
			}
			else if (method.equals("acos"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.acos());
			}
			else if (method.equals("atan"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.atan());

			}
			else if (method.equals("asinh"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.asinh());
			}
			else if (method.equals("acosh"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.acosh());
			}
			else if (method.equals("atanh"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.atanh());

			}
			else if (method.equals("toString"))
			{
				logger.debug(z1 + "." + method + "()  ==  " + z1.toString());
			}
			else
			{
				logger.debug("Does the method '" + method
						+ "' have the correct spelling?");
				logger.debug(CommonNames.MISC.EMPTY_STRING);
				System.out
						.println("(Run TestComplex with no args to get a list of method-names.)");
				inputError = true;
			}// endif
		}// endif

		if (inputError)
		{
			if (noArgs)
			{
				logger.debug("Tester...");
				logger.debug("    Module : " + MODULE);
				logger.debug("    Version: " + VERSION);
				logger.debug("    Date   : " + DATE);
				logger.debug("    Author : " + AUTHOR);
				logger.debug("    Remark : " + REMARK);
				logger.debug("Testing...");
				logger.debug("    Module : " + complexClassName);
				logger.debug("    Version: " + Complex.VERSION);
				logger.debug("    Date   : " + Complex.DATE);
				logger.debug("    Author : " + Complex.AUTHOR);
				logger.debug("    Remark : " + Complex.REMARK);
				logger.debug(CommonNames.MISC.EMPTY_STRING);
				manInfo();
			}// endif

			logger.debug(CommonNames.MISC.EMPTY_STRING);
			logger.debug("usage: ");
			logger.debug("java " + MODULE + " method-name [a] [bi] [c] [di]");
		}// endif

		// debug = false; // !!!

		// this caused more headaches than all of class Complex put together!
	}// end main(String[])

	@Test
	public void testComplexPrintout()
	{
		Complex z = new Complex(3.5, -4.5);
		logger.debug("z = " + z);
		Assert.assertEquals("3.5-4.5i", z.toString());

		z = new Complex(2, 0);
		logger.debug("z = " + z);
		Assert.assertEquals("2", z.toString());
	}

	/**
	 * Testing parsing strings into complex numbers.
	 */
	@Test
	public void testComplexParse()
	{
		testComplexParse(new Complex(-2.0, 0), "-2");
		testComplexParse(new Complex(0, 2.0), "0+2i");
		testComplexParse(new Complex(0, -2.0), "0-2i");
	}

	// ========================= PRIVATE METHODS ===========================

	private static void testComplexParse(final Complex x, final String str)
	{
		logger.debug("x = " + x + " str " + str);
		Complex y = Complex.parseComplex(str);
		Assert.assertEquals(y.re(), x.re(), RealUtil.MACHINE_DOUBLE_ERROR);
		Assert.assertEquals(y.im(), x.im(), RealUtil.MACHINE_DOUBLE_ERROR);
	}

	private static void manInfo()
	{
		logger.debug("The following is a list of Complex methods:");
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug("    method-name        arguments    -- remark");
		System.out
				.println("    -------------------------------------------------------------------");
		logger.debug("    plus minus times over    a b  c d");
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug("    arg                a b          -- angle (in radians)");
		logger.debug("    abs                a b          -- length, or magnitude");
		logger.debug("    norm               a b          -- square of magnitude");
		System.out
				.println("    scale              a b  scalar  -- scale complex by a real number");
		logger.debug("    neg                a b          -- scale by minus 1");
		logger.debug("    conj               a b          -- complex conjugate");
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug("    sqrt               a b          -- square root");
		System.out
				.println("    exp                a b          -- raise e to a complex power");
		logger.debug("    log                a b          -- natural logarithm");
		System.out
				.println("    pow                a b  c d     -- raise (a+bi) to the power (c+di)");
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug("    sin   cos   tan    a b          --");
		logger.debug("    cosec sec   cot    a b          --");
		logger.debug("    sinh  cosh  tanh   a b          --    trigonometry");
		logger.debug("    asin  acos  atan   a b          --");
		logger.debug("    asinh acosh atanh  a b          --");
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		System.out
				.println("    polar              r theta      -- convert polar to cartesian");
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug("    -- More method-names ...");
		logger.debug("    cart               a b");
		logger.debug("    clone              a b");
		logger.debug("    equals             a b  c d  tolerance");
		logger.debug("    isInfinite isNaN   a b");
		logger.debug("    re im              a b");
		logger.debug("    real               x");
		logger.debug("    toString           a b");
	}// end manInfo()

} // end TestComplex
