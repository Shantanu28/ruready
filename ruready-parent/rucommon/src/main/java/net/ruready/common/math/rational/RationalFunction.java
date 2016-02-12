/*****************************************************************************************
 * Source File: RationalFunction.java
 ****************************************************************************************/
package net.ruready.common.math.rational;

import net.ruready.common.misc.Utility;

public class RationalFunction implements Utility
{
	// ========================= CONSTANTS =================================

	public static final long ZERO = 0;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private RationalFunction()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Compute the greatest Common Denominator.
	 * 
	 * @param value1
	 * @param value2
	 * @return greatest Common Denominator
	 */
	public static int gcd(final int value1, final int value2)
	{
		int retVal = 1;

		int aValue1 = StrictMath.abs(value1);
		int aValue2 = StrictMath.abs(value2);

		int tmpMax = StrictMath.max(aValue1, aValue2);
		int tmpMin = StrictMath.min(aValue1, aValue2);

		while (tmpMin != ZERO)
		{
			retVal = tmpMin;
			tmpMin = tmpMax % tmpMin;
			tmpMax = retVal;
		}

		return retVal;
	}

	/**
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static long gcd(final long value1, final long value2)
	{
		long retVal = 1;

		long aValue1 = StrictMath.abs(value1);
		long aValue2 = StrictMath.abs(value2);

		long tmpMax = StrictMath.max(aValue1, aValue2);
		long tmpMin = StrictMath.min(aValue1, aValue2);

		while (tmpMin != ZERO)
		{
			retVal = tmpMin;
			tmpMin = tmpMax % tmpMin;
			tmpMax = retVal;
		}

		return retVal;
	}

	/**
	 * @param aFirst
	 * @param aSize
	 * @return
	 */
	public static int[] getRange(final int aFirst, final int aSize)
	{
		int[] retVal = new int[aSize];

		for (int i = 0; i < retVal.length; i++)
		{
			retVal[i] = aFirst + i;
		}

		return retVal;
	}

	/**
	 * @param anIntA
	 * @param anIntB
	 * @param anIntC
	 * @return
	 */
	public static int max(final int anIntA, final int anIntB, final int anIntC)
	{
		return StrictMath.max(anIntA, StrictMath.max(anIntB, anIntC));
	}

	/**
	 * @param anIntA
	 * @param anIntB
	 * @param anIntC
	 * @return
	 */
	public static int min(final int anIntA, final int anIntB, final int anIntC)
	{
		return StrictMath.min(anIntA, StrictMath.min(anIntB, anIntC));
	}

	/**
	 * @param exp
	 * @return
	 */
	public static long pow10int(final int exp)
	{
		int anExponent = exp;
		long retVal = 1;

		while (anExponent > 0)
		{
			retVal *= 10;
			anExponent--;
		}

		return retVal;
	}

	/**
	 * @return An integer: 0 <= ? < aLimit
	 */
	public static int randomInteger(final int aLimit)
	{
		return (int) StrictMath.floor(aLimit * StrictMath.random());
	}

	/**
	 * @return An integer: aLower <= ? < aHigher
	 */
	public static int randomInteger(final int aLower, final int aHigher)
	{
		return aLower + RationalFunction.randomInteger(aHigher - aLower);
	}

}
