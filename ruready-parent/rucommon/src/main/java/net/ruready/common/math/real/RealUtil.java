/*****************************************************************************************
 * Source File: TestUtil.java
 ****************************************************************************************/
package net.ruready.common.math.real;

import net.ruready.common.math.rational.RationalUtil;
import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Floating-point arithmetic utilities and constants. Some ideas for functions a library
 * by Dr. Michael Thomas Flanagan. Adapted for our purposes (especially: equality,
 * comparison of complex numbers). Arithmetic algorithms loosely related to a library by
 * Dr. Michael Thomas Flanagan. Adapted for our purposes (especially: equality. Also
 * provides extra functions not in java.lang.Math class. This class cannot be subclassed
 * or instantiated because all methods are static.
 * 
 * @version 1.2
 * @author Mark Hale
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 *         <br>(c) 2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 27, 2007
 */
public class RealUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RealUtil.class);

	// Floating-point arithmetic round-off error.
	// Mainly used for instance as a tolerance for comparing
	// mathematical expressions in the math parser.
	public static final double MACHINE_DOUBLE_ERROR;

	public static final float MACHINE_FLOAT_ERROR;

	// IEEE single-precision (32 bit) floating-point format parameters
	public final static int mantissaBits = 52;

	public final static int exponentBits = 11;

	public final static long mantMask = (1L << mantissaBits) - 1;

	public final static long expMask = (1L << exponentBits) - 1;

	// Compute machine round-off error
	static
	{
		float tmpFloat = 1f;
		while (1 + tmpFloat > 1)
		{
			tmpFloat /= 2;
		}
		// To be safe, take twice the smallest representable number as the
		// round-off error
		MACHINE_FLOAT_ERROR = 2 * tmpFloat;
		logger.info("Machine float  round off error is " + MACHINE_FLOAT_ERROR);

		double tmpDouble = 1d;
		while (1 + tmpDouble > 1)
		{
			tmpDouble /= 2;
		}
		// To be safe, take twice the smallest representable number as the
		// round-off error
		MACHINE_DOUBLE_ERROR = 2 * tmpDouble;
		logger.info("Machine double round off error is " + MACHINE_DOUBLE_ERROR);
	}

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private RealUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Return the relative/absolute distance between to two double-precision floating
	 * point numbers. Note that <code>relativeDistance(x,y) = relativeDistance(y,x)</code>.
	 * 
	 * @param x
	 *            left operand.
	 * @param y
	 *            right operand.
	 * @return |x-y| if min(|x|,|y|) <= 10*u, otherwise |x-y|/min(|x|,|y|), where u is
	 *         machine precision
	 */
	public static double relativeDistance(final double x, final double y)
	{
		double distance = Math.abs(x - y);
		double minAbs = Math.min(Math.abs(x), Math.abs(y));
		return (minAbs <= 10 * MACHINE_DOUBLE_ERROR) ? distance : (distance / minAbs);
	}

	/**
	 * Return the result of equality of two double-precision floating point numbers.
	 * 
	 * @param x
	 *            left operand.
	 * @param y
	 *            right operand.
	 * @param tol
	 *            relative tolerance for equality
	 * @return Result of equality (basically, abs(x-y) < eps).
	 */
	public static boolean doubleEquals(final double x, final double y, final double tol)
	{
		// logger.debug("x " + x + " y " + y + " distance " +
		// relativeDistance(x, y)
		// + " tol " + tol);
		return relativeDistance(x, y) <= tol;
	}

	/**
	 * Return the result of equality of two double-precision floating point numbers.
	 * 
	 * @param x
	 *            left operand.
	 * @param y
	 *            right operand.
	 * @return Result of equality (basically, abs(x-y) < eps).
	 */
	public static boolean doubleEquals(final double x, final double y)
	{
		return doubleEquals(x, y, MACHINE_DOUBLE_ERROR);
		/*
		 * if (!equals) { logger.debug(" x="+x+ " y="+y+ " |x-y|="+Math.abs(x-y)+ "
		 * eps*|x|="+ MACHINE_DOUBLE_ERROR * Math.abs(x)); }
		 */
	}

	/**
	 * Round a double to a presecribed tolerance.
	 * 
	 * @param value
	 *            original real value
	 * @param tol
	 *            round-off tolerance. For <code>n</code> significant digits, use
	 *            <code>tol=10^{-n}</code>.
	 */
	@SuppressWarnings("deprecation")
	@Deprecated
	public static double roundUsingRationalApproximation(final double value,
			final double tol)
	{
		// OK... round/floor don't work well in
		// same cases, so we used rational approximations for a while. But they
		// have their issues as well. use the round(value,int) method below
		// instead.
		if (tol <= RealUtil.MACHINE_DOUBLE_ERROR)
		{
			// Don't round, just return the value
			return value;
		}
		// logger.debug("value " + value
		// + " tol " + tol
		// + " rounded value "+RationalUtil.round(value, tol));
		return RationalUtil.roundUsingRationalApproximation(value, tol);
	}

	/**
	 * Rounds a number to so many significant figures.
	 * 
	 * @param x
	 *            a number to be rounded.
	 * @param tol
	 *            round-off tolerance. For <code>n</code> significant digits, use
	 *            <code>tol=10^{-n}</code>.
	 */
	public static double round(final double x, final double tol)
	{
		// Round/floor problems were previously detected (see the comment in
		// #roundUsingRationalApproximation). However, it seems ok for now so
		// use it until problems arise.
		if (x == 0.0)
		{
			return x;
		}
		else if (tol <= RealUtil.MACHINE_DOUBLE_ERROR)
		{
			// Don't round, just return the value
			return x;
		}
		int significant = (int) Math.rint(log10(Math.abs(tol)));
		if (significant == 0)
		{
			return 0.0;
		}
		final double signedExp = log10(Math.abs(x)) - significant;
		if (signedExp < 0.0)
		{
			// keep the exponent positive so factor is representable
			return Math.round(x * tol) / tol;
		}
		return Math.round(x / tol) * tol;
	}

	/**
	 * Rounds a number to so many significant figures.
	 * 
	 * @param x
	 *            a number to be rounded.
	 * @param significant
	 *            number of significant figures to round to.
	 */
	public static double round(final double x, final int significant)
	{
		if (x == 0.0)
		{
			return x;
		}
		else if (significant == 0)
		{
			return 0.0;
		}
		final double signedExp = log10(Math.abs(x)) - significant;
		if (signedExp < 0.0)
		{
			// keep the exponent positive so factor is representable
			final double factor = Math.pow(10.0, Math.floor(-signedExp));
			return Math.round(x * factor) / factor;
		}
		final double factor = Math.pow(10.0, Math.ceil(signedExp));
		return Math.round(x / factor) * factor;
	}

	/**
	 * Returns a random number within a specified range.
	 */
	public static double random(final double min, final double max)
	{
		return (max - min) * Math.random() + min;
	}

	/**
	 * Returns the sign of a number.
	 * 
	 * @return 1 if x>0.0, -1 if x<0.0, else 0.
	 */
	public static int sign(double x)
	{
		if (x > 0.0)
			return 1;
		else if (x < 0.0)
			return -1;
		else
			return 0;
	}

	/**
	 * Returns sqrt(x<sup>2</sup>+y<sup>2</sup>).
	 */
	public static double hypot(final double x, final double y)
	{
		final double xAbs = Math.abs(x);
		final double yAbs = Math.abs(y);
		if (xAbs == 0.0 && yAbs == 0.0)
			return 0.0;
		else if (xAbs < yAbs)
			return yAbs * Math.sqrt(1.0 + (x / y) * (x / y));
		else
			return xAbs * Math.sqrt(1.0 + (y / x) * (y / x));
	}

	/**
	 * Returns x<sup>b</sup>.
	 * 
	 * @param x
	 *            an integer.
	 * @param b
	 *            a positive integer.
	 */
	public static int pow(final int x, final int b)
	{
		int a = x;
		if (b < 0)
		{
			throw new IllegalArgumentException(b + " must be a positive integer.");
		}
		else if (b == 0)
		{
			return 1;
		}
		else
		{
			if (a == 0)
			{
				return 0;
			}
			else if (a == 1)
			{
				return 1;
			}
			else if (a == 2)
			{
				return 1 << b;
			}
			else
			{
				for (int i = 1; i < b; i++)
					a *= a;
				return a;
			}
		}
	}

	/**
	 * Returns 2<sup>a</sup>.
	 * 
	 * @param a
	 *            a positive integer.
	 */
	public static int pow2(int a)
	{
		return 1 << a;
	}

	/**
	 * Returns the factorial. (Wrapper for the gamma function).
	 * 
	 * @see SpecialFunctions#gamma
	 * @param x
	 *            a double.
	 */
	public static double factorial(final double x)
	{
		return SpecialFunctions.gamma(x + 1.0);
	}

	/**
	 * Returns the natural logarithm of the factorial. (Wrapper for the log gamma
	 * function).
	 * 
	 * @see SpecialFunctions#logGamma
	 * @param x
	 *            a double.
	 */
	public static double logFactorial(final double x)
	{
		return SpecialFunctions.logGamma(x + 1.0);
	}

	/**
	 * Returns the binomial coefficient (n k). Uses Pascal's recursion formula.
	 * 
	 * @planetmath PascalsRule
	 * @param n
	 *            an integer.
	 * @param k
	 *            an integer.
	 */
	public static int binomial(final int n, final int k)
	{
		if (k == n || k == 0)
			return 1;
		else if (n == 0)
			return 1;
		else
			return binomial(n - 1, k - 1) + binomial(n - 1, k);
	}

	/**
	 * Returns the binomial coefficient (n k). Uses gamma functions.
	 * 
	 * @planetmath BinomialCoefficient
	 * @param n
	 *            a double.
	 * @param k
	 *            a double.
	 */
	public static double binomial(final double n, final double k)
	{
		return Math.exp(SpecialFunctions.logGamma(n + 1.0)
				- SpecialFunctions.logGamma(k + 1.0)
				- SpecialFunctions.logGamma(n - k + 1.0));
	}

	/**
	 * Returns the base 10 logarithm of a double.
	 * 
	 * @param x
	 *            a double.
	 */
	public static double log10(final double x)
	{
		return Math.log(x) / RealConstants.LOG10;
	}

	/**
	 * Returns the hyperbolic sine of a double.
	 * 
	 * @param x
	 *            a double.
	 */
	public static double sinh(final double x)
	{
		return (Math.exp(x) - Math.exp(-x)) / 2.0;
	}

	/**
	 * Returns the hyperbolic cosine of a double.
	 * 
	 * @param x
	 *            a double.
	 */
	public static double cosh(final double x)
	{
		return (Math.exp(x) + Math.exp(-x)) / 2.0;
	}

	/**
	 * Returns the hyperbolic tangent of a double.
	 * 
	 * @param x
	 *            a double.
	 */
	public static double tanh(final double x)
	{
		return sinh(x) / cosh(x);
	}

	/**
	 * Returns the arc hyperbolic sine of a double, in the range of -<img border="0"
	 * alt="infinity" src="doc-files/infinity.gif"> through <img border="0" alt="infinity"
	 * src="doc-files/infinity.gif">.
	 * 
	 * @param x
	 *            a double.
	 */
	public static double asinh(final double x)
	{
		return Math.log(x + Math.sqrt(x * x + 1.0));
	}

	/**
	 * Returns the arc hyperbolic cosine of a double, in the range of 0.0 through <img
	 * border="0" alt="infinity" src="doc-files/infinity.gif">.
	 * 
	 * @param x
	 *            a double.
	 */
	public static double acosh(final double x)
	{
		return Math.log(x + Math.sqrt(x * x - 1.0));
	}

	/**
	 * Returns the arc hyperbolic tangent of a double, in the range of -<img border="0"
	 * alt="infinity" src="doc-files/infinity.gif"> through <img border="0" alt="infinity"
	 * src="doc-files/infinity.gif">.
	 * 
	 * @param x
	 *            a double.
	 */
	public static double atanh(final double x)
	{
		return Math.log((1.0 + x) / (1.0 - x)) / 2.0;
	}
}
