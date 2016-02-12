/*****************************************************************************************
 * Source File: NumericTypeContext.java
 ****************************************************************************************/
package net.ruready.common.math.rational;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Think of this as a MathContext that specifies both precision and scale. Numeric data
 * types (non-integers) in databases are specified using precision and scale. While doing
 * maths the precision is all that matters, but before sending a number to a database, or
 * printing/displaying it, rounding to a specified scale is desireable.
 * <p>
 * Copyright (c) 2006 Optimatika (www.optimatika.se) Permission is hereby granted, free of
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
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author apete (original version)
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 25, 2007
 */
public final class NumericTypeContext extends Object
{
	// ========================= CONSTANTS =================================

	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

	// ========================= FIELDS ====================================

	private final MathContext myMathContext;

	private final int myScale;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param aMathContext
	 * @param aScale
	 */
	public NumericTypeContext(MathContext aMathContext, int aScale)
	{

		super();

		myMathContext = aMathContext;
		myScale = aScale;
	}

	/**
	 * @param aPrecision
	 * @param aScale
	 */
	public NumericTypeContext(int aPrecision, int aScale)
	{
		this(aPrecision, aScale, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * @param aPrecision
	 * @param aScale
	 * @param aRoundingMode
	 */
	public NumericTypeContext(int aPrecision, int aScale, RoundingMode aRoundingMode)
	{
		this(new MathContext(aPrecision, aRoundingMode), aScale);
	}

	/**
	 * @param aMathContext
	 */
	public NumericTypeContext(MathContext aMathContext)
	{
		this(aMathContext, aMathContext.getPrecision() / 2);
	}

	// ========================= IMPLEMENTATION: Object ====================

	// ========================= PUBLIC METHODS ============================

	/**
	 * The enforce methods first enforce the precision and then set the scale. It is
	 * possible that this will create a number with trailing zeros and more digits than
	 * the precision allows. It is also possible to define a context with a scale that is
	 * larger than the precision. This is NOT how precision and scale functions with
	 * numeric types in databases.
	 */
	public static BigDecimal enforce(BigDecimal aNmbr, NumericTypeContext aCntxt)
	{
		return aNmbr.plus(aCntxt.getMathContext()).setScale(aCntxt.getScale(),
				aCntxt.getRoundingMode());
	}

	/*
	 * public static ComplexNumber enforce(ComplexNumber aNmbr, NumericTypeContext aCntxt) {
	 * double tmpRe = NumericTypeContext.enforce(aNmbr.getReal(), aCntxt); double tmpIm =
	 * NumericTypeContext.enforce(aNmbr.getImaginary(), aCntxt); return
	 * ComplexNumber.fromRectangularCoordinates(tmpRe, tmpIm); }
	 */
	/**
	 * @param aNmbr
	 * @param aCntxt
	 * @return
	 */
	public static double enforce(double aNmbr, NumericTypeContext aCntxt)
	{
		return NumericTypeContext.enforce(new BigDecimal(aNmbr), aCntxt).doubleValue();
	}

	/**
	 * @param aNmbr
	 * @param aCntxt
	 * @return
	 */
	public static Rational enforce(Rational aNmbr, NumericTypeContext aCntxt)
	{

		Rational retVal = aNmbr.clone();

		retVal.scale();

		return retVal;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 */
	public MathContext getMathContext()
	{
		return myMathContext;
	}

	/**
	 * @return
	 */
	public int getPrecision()
	{
		return myMathContext.getPrecision();
	}

	/**
	 * @return
	 */
	public RoundingMode getRoundingMode()
	{
		return myMathContext.getRoundingMode();
	}

	/**
	 * @return
	 */
	public int getScale()
	{
		return myScale;
	}

}
