/*****************************************************************************************
 * Source File: Rational.java
 ****************************************************************************************/
package net.ruready.common.math.rational;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.ruready.common.math.highprec.BigRealConstant;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.util.HashCodeUtil;

/**
 * A rational number with standard arithmetic operations. This class is mutable,
 * comparable, cloneable, extensible and experimental.
 * <p>
 * Copyright (c) 2005 Optimatika (www.optimatika.se) Permission is hereby granted, free of
 * charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions: The above copyright notice and
 * this permission notice shall be included in all copies or substantial portions of the
 * Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. <br>
 * Note: Comparable is implemented but not declared so that RationalValue can implement
 * it.
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
 * @version Jul 27, 2007
 */
public class Rational extends Number implements // Comparable<Rational>,
		Cloneable, PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1;

	public static final BigDecimal NANO = new BigDecimal("0.000000001");

	private static final NumericTypeContext CONTEXT = new NumericTypeContext(9, 9,
			RoundingMode.HALF_EVEN);

	// Division sign in printing a rational
	private static final String DIV = "/";

	private static final int ONE = 1;

	private static final int TEN = 10;

	private static final int ZERO = 0;

	// ========================= FIELDS ====================================

	private long myDenominator;

	private long myNumerator;

	// ========================= CONSTRUCTORS ==============================

	protected Rational()
	{
		super();

		myNumerator = ZERO;
		myDenominator = ONE;
	}

	public Rational(BigDecimal aNmbr)
	{
		super();
		this.init(aNmbr);
	}

	public Rational(double aNmbr)
	{
		// this(new BigDecimal(aNmbr, CONTEXT.getMathContext()));
		super();
		BigDecimal bd = null;
		try
		{
			bd = new BigDecimal(aNmbr, CONTEXT.getMathContext());
			this.init(bd);
		}
		catch (NumberFormatException e)
		{
			// double value might have been null or infinity, set this rational
			// object to NaN
			myNumerator = ZERO;
			myDenominator = ZERO;
		}
	}

	public Rational(long aNumerator, long aDenominator)
	{
		super();

		myNumerator = aNumerator;
		myDenominator = aDenominator;

		this.simplify();
	}

	public Rational(Number aNmbr)
	{
		this(aNmbr.toString());
	}

	public Rational(Rational aNmbr)
	{

		super();

		myNumerator = aNmbr.getNumerator();
		myDenominator = aNmbr.getDenominator();
	}

	public Rational(String aNmbr)
	{
		// this(new BigDecimal(aNmbr, CONTEXT.getMathContext()));
		super();
		BigDecimal bd = null;
		try
		{
			bd = new BigDecimal(aNmbr, CONTEXT.getMathContext());
			this.init(bd);
		}
		catch (NumberFormatException e)
		{
			// double value might have been null or infinity, set this rational
			// object to NaN
			myNumerator = ZERO;
			myDenominator = ZERO;
		}
	}

	/**
	 * Factored out of the BigDecimal constructor to catch number format exceptions in
	 * other constructors that use the BigDecimal constructor.
	 * 
	 * @param aNmbr
	 */
	private void init(final BigDecimal aNmbr0)
	{
		BigDecimal aNmbr = aNmbr0;
		boolean tmpInv = Rational.isTooSmall(aNmbr);

		if (tmpInv)
		{
			aNmbr = BigRealConstant.ONE.divide(aNmbr, CONTEXT.getMathContext());
		}

		myNumerator = aNmbr.unscaledValue().longValue();

		if (aNmbr.scale() < 0)
		{

			myNumerator *= RationalFunction.pow10int(-aNmbr.scale());
			myDenominator = ONE;

		}
		else if (myNumerator == ZERO)
		{

			myDenominator = ONE;

		}
		else
		{

			myDenominator = RationalFunction.pow10int(aNmbr.scale());

			this.simplify();
		}

		if (tmpInv)
		{
			this.invert();
		}

	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		if (myDenominator < ZERO)
		{
			myNumerator = -myNumerator;
			myDenominator = -myDenominator;
		}

		if (myDenominator == 1)
		{
			// Integer
			return CommonNames.MISC.EMPTY_STRING + myNumerator;
		}
		return myNumerator + DIV + myDenominator;
	}

	/**
	 * Must be overridden when <code>equals()</code> is.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// Dubious original implementation
		// return (int) (this.getClass().hashCode() * myNumerator *
		// myDenominator);
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, myNumerator);
		result = HashCodeUtil.hash(result, myDenominator);
		return result;
	}

	// ========================= IMPLEMENTATION: Number ====================

	@Override
	public int intValue()
	{
		return (int) this.doubleValue();
	}

	@Override
	public long longValue()
	{
		return (long) this.doubleValue();
	}

	@Override
	public float floatValue()
	{
		return (float) this.doubleValue();
	}

	@Override
	public double doubleValue()
	{
		return ((double) myNumerator) / ((double) myDenominator);
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	@Override
	public Rational clone()
	{
		try
		{
			return (Rational) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
	}

	// ========================= IMPLEMENTATION: Comparable ================

	/**
	 * @param aNmbr
	 * @return
	 */
	public int compareTo(Rational aNmbr)
	{
		double tmpThis = this.doubleValue();
		double tmpArg = aNmbr.doubleValue();

		return Double.compare(tmpThis, tmpArg);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		// Standard checks to ensure the adherence to the general contract of
		// the equals() method
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// Cast to friendlier version
		Rational other = (Rational) obj;

		return (myNumerator == other.getNumerator())
				&& (myDenominator == other.getDenominator());
	}

	// ========================= PUBLIC METHODS ============================

	/**
	 * 
	 */
	public void abs()
	{
		if (myNumerator < ZERO)
		{
			myNumerator = -myNumerator;
		}

		if (myDenominator < ZERO)
		{
			myDenominator = -myDenominator;
		}
	}

	/**
	 * Return the absolute value of this number
	 * 
	 * @return the absolute value of this number
	 */
	public double d_abs()
	{
		return Math.abs(this.doubleValue());
	}

	/**
	 * @param aNmbr
	 * @return
	 */
	public Rational add(Rational aNmbr)
	{
		Rational retVal = this.clone();

		retVal.doRawAdd(aNmbr);

		retVal.simplify();

		return retVal;
	}

	/**
	 * @param aNmbr
	 * @return
	 */
	public Rational divide(Rational aNmbr)
	{
		Rational retVal = this.clone();

		retVal.doRawDivide(aNmbr);

		retVal.simplify();

		return retVal;
	}

	public void doRawAdd(Rational aNmbr)
	{
		myNumerator = (myNumerator * aNmbr.getDenominator())
				+ (aNmbr.getNumerator() * myDenominator);
		myDenominator *= aNmbr.getDenominator();
	}

	public void doRawDivide(Rational aNmbr)
	{
		myNumerator *= aNmbr.getDenominator();
		myDenominator *= aNmbr.getNumerator();
	}

	public void doRawMultiply(Rational aNmbr)
	{
		myNumerator *= aNmbr.getNumerator();
		myDenominator *= aNmbr.getDenominator();
	}

	public void doRawSubtract(Rational aNmbr)
	{
		myNumerator = (myNumerator * aNmbr.getDenominator())
				- (aNmbr.getNumerator() * myDenominator);
		myDenominator *= aNmbr.getDenominator();
	}

	public void invert()
	{

		long tmpval = myNumerator;

		myNumerator = myDenominator;
		myDenominator = tmpval;
	}

	public boolean isInfinity()
	{
		return (myNumerator != ZERO && myDenominator == ZERO);
	}

	public boolean isNaN()
	{
		return (myNumerator == ZERO && myDenominator == ZERO);
	}

	public boolean isZero()
	{
		return (myNumerator == ZERO && myDenominator != ZERO);
	}

	public void max(Rational aNmbr)
	{
		if (this.compareTo(aNmbr) >= ONE)
		{

			myNumerator = aNmbr.getNumerator();
			myDenominator = aNmbr.getDenominator();
		}
	}

	public void min(Rational aNmbr)
	{
		if (this.compareTo(aNmbr) <= -ONE)
		{

			myNumerator = aNmbr.getNumerator();
			myDenominator = aNmbr.getDenominator();
		}
	}

	/**
	 * @param shift
	 */
	public void movePointLeft(final int shift)
	{
		int aShift = shift;
		while (aShift > 0)
		{
			myDenominator *= TEN;
			--aShift;
		}

		this.simplify();
	}

	/**
	 * @param shift
	 */
	public void movePointRight(final int shift)
	{
		int aShift = shift;
		while (aShift > 0)
		{
			myNumerator *= TEN;
			--aShift;
		}

		this.simplify();
	}

	/**
	 * @param aNmbr
	 * @return
	 */
	public Rational multiply(final Rational aNmbr)
	{

		Rational retVal = this.clone();

		retVal.doRawMultiply(aNmbr);

		retVal.simplify();

		return retVal;
	}

	/**
	 * 
	 */
	public void negate()
	{
		myNumerator = -myNumerator;
	}

	/**
	 * Raise a rational to an integer power.
	 * 
	 * @param exp
	 *            exponent
	 */
	public void pow(final int exp)
	{
		int anExp = exp;
		if (anExp < 0)
		{
			this.invert();
			anExp = -anExp;
		}

		for (int i = 0; i < anExp; i++)
		{
			this.multiply(this);
			--anExp;
		}
	}

	/**
	 * Makes certain the numerator and/or denominator are not too large.
	 */
	public void scale()
	{
		myNumerator = new BigDecimal(myNumerator, CONTEXT.getMathContext()).longValue();
		myDenominator = new BigDecimal(myDenominator, CONTEXT.getMathContext())
				.longValue();

		this.simplify();
	}

	/**
	 * Sign of a rational number.
	 * 
	 * @return the sign of a rational number
	 */
	public int signum()
	{
		return Double.compare(this.doubleValue(), ZERO);
	}

	public void simplify()
	{
		long tmpGCD = RationalFunction.gcd(myNumerator, myDenominator);

		myNumerator /= tmpGCD;
		myDenominator /= tmpGCD;
	}

	public Rational subtract(Rational aNmbr)
	{
		Rational retVal = this.clone();

		retVal.doRawSubtract(aNmbr);

		retVal.simplify();

		return retVal;
	}

	public BigDecimal toBigDecimal()
	{
		return new BigDecimal(this.toNumberFormatString());
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Is number too small (compared with <code>NANO</code>).
	 * 
	 * @param aNmbr
	 *            big decimal number
	 * @return is number too small (compared with <code>NANO</code>).
	 */
	private static boolean isTooSmall(final BigDecimal aNmbr0)
	{
		boolean retVal = false;

		BigDecimal aNmbr = aNmbr0.abs();

		retVal = aNmbr.signum() != 0 && aNmbr.compareTo(NANO) <= -ONE;

		return retVal;
	}

	/**
	 * @return
	 */
	private String toNumberFormatString()
	{
		return Double.toString(this.doubleValue());
	}

	/**
	 * @return
	 */
	public Rational recip()
	{
		return new Rational(myDenominator, myNumerator);
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 */
	public long getDenominator()
	{
		return myDenominator;
	}

	/**
	 * @return
	 */
	public long getNumerator()
	{
		return myNumerator;
	}
}
