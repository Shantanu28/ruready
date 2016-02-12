/*******************************************************
 * Source File: FloatParts.java
 *******************************************************/
package net.ruready.common.math.real;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.ruready.common.rl.CommonNames;

/**
 * This separates the sign, exponent, and mantissa from a 32-bit floating point
 * number. Based on fancyFloat class by Lynn Tetreault, 1998,
 * http://pages.cpsc.ucalgary.ca/~rokne/CONVEX/src/convexHull/fancyFloat.java
 * 
 * It also contains the function ldexp which Java does not appear to have.
 * 
 * 
 * IEEE 754 floating-point "single format" bit layout: (32 bit) seeeeeee
 * emmmmmmm mmmmmmmm mmmmmmmm
 * 
 * s = sign bit e = exponent bits m = mantissa bits
 * 
 * ================================================================ To extract
 * the sign (bit 31) seeeeeee emmmmmmm mmmmmmmm mmmmmmmm >>31 00000000 00000000
 * 00000000 0000000s ----------------------------------- 00000000 00000000
 * 00000000 0000000s
 * ================================================================ To extract
 * the exponent (bits 30-23) seeeeeee emmmmmmm mmmmmmmm mmmmmmmm >>23 00000000
 * 00000000 0000000s eeeeeeee & 00000000 00000000 00000000 11111111 (0x000000FF)
 * ----------------------------------- 00000000 00000000 00000000 eeeeeeee
 * 
 * ================================================================ To extract
 * the mantissa (bits 22-0) seeeeeee emmmmmmm mmmmmmmm mmmmmmmm & 00000000
 * 01111111 11111111 11111111 (0x007FFFFF) -----------------------------------
 * 00000000 0mmmmmmm mmmmmmmm mmmmmmmm
 * ================================================================
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
public class FloatParts
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FloatParts.class);

	// IEEE single-precision (32 bit) floating-point
	// format parameters
	public final static int mantissaBits = 23;
	public final static int exponentBits = 8;
	public final static int mantissaMask = (1 << mantissaBits) - 1;
	public final static int expMask = (1 << exponentBits) - 1;
	public final static int leadingOne = (1 << mantissaBits);
	public final static int expOffset = (1 << (exponentBits - 1)) - 1;

	// ################## FIELDS #######################
	protected int sign = 0;
	protected int exponent = 0;
	protected int mantissa = 0;
	protected float fnum = 0.0f;
	protected int inum = 0;

	// ################## CONSTRUCTORS #######################

	/**
	 * @param value
	 *            floating-point number.
	 */
	public FloatParts(float value)
	{
		fnum = value;
		inum = Float.floatToIntBits(fnum);

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
	public int getExponent()
	{
		return exponent;
	}

	/**
	 * @return
	 */
	int getMantissa()
	{
		return mantissa;
	}

	/**
	 * @param value
	 * @return
	 */
	static int getSign(float value)
	{
		FloatParts temp = new FloatParts(value);
		return temp.getSign();
	}

	/**
	 * @param value
	 * @return
	 */
	static int getExponent(float value)
	{
		FloatParts temp = new FloatParts(value);
		return temp.getExponent();
	}

	/**
	 * @param value
	 * @return
	 */
	static int getMantissa(float value)
	{
		FloatParts temp = new FloatParts(value);
		return temp.getMantissa();
	}

	/**
	 * @param value
	 * @return
	 */
	static float getMantissaFloat(float value)
	{
		FloatParts temp = new FloatParts(value);
		Double exp = new Double(Math.pow(2, temp.getExponent()));
		return value / exp.floatValue();
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
	 *            Floating point value representing mantissa
	 * 
	 * @param exp
	 *            Integer exponent
	 * 
	 * @return Floating-point value equal to <code>x * 2^exp</code>
	 */
	public static float ldexp(float value, int exp)
	{
		Double temp = new Double(Math.pow(2, exp));
		return value * temp.floatValue();
	}

	/**
	 * @param args
	 */
	public static void main(String args[])
	{
		float a = 6.5f;
		logger.debug("expOffset = " + FloatParts.expOffset);
		logger.debug(CommonNames.MISC.EMPTY_STRING);

		logger.debug("a = " + a);
		logger.debug("  bits     = 0x" + Integer.toHexString(Float.floatToIntBits(a)));
		logger.debug("  sign     = " + FloatParts.getSign(a));
		logger.debug("  mantissa = 0x" + Integer.toHexString(FloatParts.getMantissa(a))
				+ " " + FloatParts.getMantissa(a) + " " + FloatParts.getMantissaFloat(a));
		logger.debug("  exponent = " + FloatParts.getExponent(a));
		logger.debug(CommonNames.MISC.EMPTY_STRING);

		logger.debug("ldexp(1.625, 2) = " + FloatParts.ldexp(1.625f, 2));
		logger.debug(CommonNames.MISC.EMPTY_STRING);
	}
}
