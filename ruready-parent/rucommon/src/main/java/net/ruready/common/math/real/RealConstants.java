/*******************************************************
 * Source File: RealConstants.java
 *******************************************************/
package net.ruready.common.math.real;

import net.ruready.common.math.highprec.BigRealConstant;
import net.ruready.common.misc.Auxiliary;
import net.ruready.common.misc.Utility;

/**
 * Contains useful references to real-valued constants.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 11, 2007
 */
public interface RealConstants extends Auxiliary, Utility
{
	// ========================= CONSTANTS =================================
	static final double HALF = BigRealConstant.HALF.doubleValue();

	static final double THIRD = BigRealConstant.THIRD.doubleValue();

	static final double ONE = BigRealConstant.ONE.doubleValue();

	static final double MINUS_ONE = BigRealConstant.MINUS_ONE.doubleValue();

	static final int[] PRIME = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37 };

	static final double TWO = BigRealConstant.TWO.doubleValue();

	static final double ZERO = BigRealConstant.ZERO.doubleValue();

	static final double REAL_GAMMA = BigRealConstant.BIG_GAMMA.doubleValue();

	static final double REAL_E = BigRealConstant.BIG_E.doubleValue();

	static final double REAL_PI = BigRealConstant.BIG_PI.doubleValue();

	/**
	 * Square root of 2.
	 */
	final static double SQRT2 = 1.4142135623730950488016887242096980785696718753769;

	/**
	 * Two times <img border="0" alt="pi" src="doc-files/pi.gif">.
	 * 
	 * @planetmath Pi
	 */
	final static double TWO_PI = 6.2831853071795864769252867665590057683943387987502;

	/**
	 * Square root of 2<img border="0" alt="pi" src="doc-files/pi.gif">.
	 */
	final static double SQRT2PI = 2.5066282746310005024157652848110452530069867406099;

	/**
	 * Natural logarithm of 10.
	 */
	final static double LOG10 = 2.30258509299404568401799145468436420760110148862877;

	/**
	 * Golden ratio.
	 * 
	 * @planetmath GoldenRatio
	 */
	final static double GOLDEN_RATIO = 1.6180339887498948482045868343656381177203091798058;
}
