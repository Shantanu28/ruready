/*******************************************************
 * Source File: HashCodeUtil.java
 *******************************************************/
package net.ruready.common.util;

import java.lang.reflect.Array;

import net.ruready.common.misc.Utility;

/**
 * Collected methods which allow easy implementation of <code>hashCode</code>.
 * Example use case:
 * 
 * <pre>
 * public int hashCode()
 * {
 * 	int result = HashCodeUtil.SEED;
 * 	//collect the contributions of various fields
 * 	result = HashCodeUtil.hash(result, fPrimitive);
 * 	result = HashCodeUtil.hash(result, fObject);
 * 	result = HashCodeUtil.hash(result, fArray);
 * 	return result;
 * }
 * </pre>
 * 
 * Retrieved from: {@link http://www.javapractices.com/Topic28.cjp}
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Apr 26, 2007
 */
public final class HashCodeUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * An initial value for a <code>hashCode</code>, to which is added
	 * contributions from fields. Using a non-zero value decreases collisons of
	 * <code>hashCode</code> values.
	 */
	public static final int SEED = 23;

	private static final int ODD_PRIME_NUMBER = 37;
	
	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private HashCodeUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Hash a boolean.
	 * 
	 * @param aSeed
	 * @param aBoolean
	 * @return
	 */
	public static int hash(int aSeed, boolean aBoolean)
	{
		return firstTerm(aSeed) + (aBoolean ? 1 : 0);
	}

	/**
	 * Hash a char.
	 * 
	 * @param aSeed
	 * @param aChar
	 * @return hash code
	 */
	public static int hash(int aSeed, char aChar)
	{
		return firstTerm(aSeed) + aChar;
	}

	/**
	 * Hash an integer.
	 * 
	 * @param aSeed
	 * @param aInt
	 * @return hash code
	 */
	public static int hash(int aSeed, int aInt)
	{
		// Note that byte and short are also handled by this
		// method, through implicit conversion.
		return firstTerm(aSeed) + aInt;
	}

	/**
	 * Hash a long.
	 * 
	 * @param aSeed
	 * @param aLong
	 * @return hash code
	 */
	public static int hash(int aSeed, long aLong)
	{
		return firstTerm(aSeed) + (int) (aLong ^ (aLong >>> 32));
	}

	/**
	 * Hash a float.
	 * 
	 * @param aSeed
	 * @param aFloat
	 * @return hash code
	 */
	public static int hash(int aSeed, float aFloat)
	{
		return hash(aSeed, Float.floatToIntBits(aFloat));
	}

	/**
	 * Hash a double.
	 * 
	 * @param aSeed
	 * @param aDouble
	 * @return hash code
	 */
	public static int hash(int aSeed, double aDouble)
	{
		return hash(aSeed, Double.doubleToLongBits(aDouble));
	}

	/**
	 * Hash an object.
	 * 
	 * @param aSeed
	 * @param aObject
	 *            a possibly-null object field, and possibly an array. If
	 *            <code>aObject</code> is an array, then each element may be a
	 *            primitive or a possibly-null object.
	 * @return hash code
	 */
	public static int hash(int aSeed, Object aObject)
	{
		int result = aSeed;
		if (aObject == null) {
			result = hash(result, 0);
		}
		else if (!isArray(aObject)) {
			result = hash(result, aObject.hashCode());
		}
		else {
			int length = Array.getLength(aObject);
			for (int idx = 0; idx < length; ++idx) {
				Object item = Array.get(aObject, idx);
				// recursive call!
				result = hash(result, item);
			}
		}
		return result;
	}

	// ========================= PRIVATE METHODS ===========================

	private static int firstTerm(int aSeed)
	{
		return ODD_PRIME_NUMBER * aSeed;
	}

	private static boolean isArray(Object aObject)
	{
		return aObject.getClass().isArray();
	}
}
