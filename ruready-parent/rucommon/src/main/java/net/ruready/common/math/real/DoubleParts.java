/*****************************************************************************************
 * Source File: DoubleParts.java
 ****************************************************************************************/
package net.ruready.common.math.real;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This separates the sign, exponent, and mantissa from a 64-bit floating point
 * number. Based on fancyFloat class by Lynn Tetreault, 1998,
 * http://pages.cpsc.ucalgary.ca/~rokne/CONVEX/src/convexHull/fancyDouble.java
 * It also contains the function ldexp which Java does not appear to have. See
 * also {@link FloatParts}.
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
 * @version Oct 26, 2007
 */
public class DoubleParts
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DoubleParts.class);

	// IEEE single-precision (32 bit) floating-point
	// format parameters
	public final static int mantissaBits = 52;

	public final static int exponentBits = 11;

	public final static long mantissaMask = (1L << mantissaBits) - 1;

	public final static long expMask = (1L << exponentBits) - 1;

	public final static long leadingOne = (1L << mantissaBits);

	public final static long expOffset = (1l << (exponentBits - 1)) - 1;

	// ========================= FIELDS ====================================

	protected long sign = 0;

	protected long exponent = 0;

	protected long mantissa = 0;

	protected double fnum = 0.0f;

	protected long inum = 0;

	// ################## CONSTRUCTORS #######################

	/**
	 * @param value
	 *            floating-point number.
	 */
	public DoubleParts(double value)
	{
		fnum = value;
		inum = Double.doubleToLongBits(fnum);

		sign = (mantissaBits + exponentBits >> 31);

		exponent = (inum >> mantissaBits) & expMask;
		exponent -= expOffset; // bias the exponent.

		mantissa = inum & mantissaMask;
		// mantissa |= leadingOne; // add the implicit leading 1 bit
	}

	/**
	 * Return the sign of a real number.
	 * 
	 * @return Sign of the real number: -1 if x < 0, 0 if x=0, otherwise 1.
	 */
	public int getSign()
	{
		if (fnum == 0)
			return 0;
		return (sign == 1) ? 1 : -1;
	}

	/**
	 * Return the exponent of a real number.
	 * 
	 * @return Exponent of a real number (8-bit).
	 */
	public long getExponent()
	{
		return exponent;
	}

	long getMantissa()
	{
		return mantissa;
	}

	/**
	 * @param value
	 * @return
	 */
	static int getSign(double value)
	{
		DoubleParts temp = new DoubleParts(value);
		return temp.getSign();
	}

	/**
	 * @param value
	 * @return
	 */
	static long getExponent(double value)
	{
		DoubleParts temp = new DoubleParts(value);
		return temp.getExponent();
	}

	/**
	 * @param value
	 * @return
	 */
	static long getMantissa(double value)
	{
		DoubleParts temp = new DoubleParts(value);
		return temp.getMantissa();
	}

	/**
	 * @param value
	 * @return
	 */
	static double getMantissaDouble(double value)
	{
		DoubleParts temp = new DoubleParts(value);
		Double exp = new Double(Math.pow(2, temp.getExponent()));
		return value / exp.doubleValue();
	}

	/**
	 * Get floating-point value from mantissa and exponent. Calculates the
	 * floating point value corresponding to the given mantissa and exponent,
	 * such that:<br>
	 * <code>x * 2^exp</code><br>
	 * where <code>x</code> represents the mantissa and <code>exp</code>
	 * represents the exponent.
	 * 
	 * @param x
	 *            Doubleing point value representing mantissa
	 * @param exp
	 *            Integer exponent
	 * @return Doubleing-point value equal to <code>x * 2^exp</code>
	 */
	public static double ldexp(double value, int exp)
	{
		Double temp = new Double(Math.pow(2, exp));
		return value * temp.doubleValue();
	}

	/**
	 * Test double parts.
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		double a = 6.5;
		logger.debug("expOffset = " + DoubleParts.expOffset);
		logger.debug(CommonNames.MISC.EMPTY_STRING);

		logger.debug("a = " + a);
		logger.debug("  bits     = 0x" + Long.toHexString(Double.doubleToLongBits(a)));
		logger.debug("  sign     = " + DoubleParts.getSign(a));
		logger.debug("  mantissa = 0x" + Long.toHexString(DoubleParts.getMantissa(a))
				+ " " + DoubleParts.getMantissa(a) + " "
				+ DoubleParts.getMantissaDouble(a));
		logger.debug("  exponent = " + DoubleParts.getExponent(a));
		logger.debug(CommonNames.MISC.EMPTY_STRING);

		logger.debug("ldexp(1.625, 2) = " + DoubleParts.ldexp(1.625f, 2));
		logger.debug(CommonNames.MISC.EMPTY_STRING);
	}

}
