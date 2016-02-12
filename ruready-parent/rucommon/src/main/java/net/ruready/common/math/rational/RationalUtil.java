/*******************************************************************************
 * Source File: RationalUtil.java
 ******************************************************************************/
package net.ruready.common.math.rational;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.math.real.RealUtil;
import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 25/02/2006 <b>Rational</b> representation of a double precision
 *          floating point number. For printouts only; doesn't do any
 *          arithmetic.
 */
public final class RationalUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RationalUtil.class);

	// Upper bound for numerator, denominator
	// created in approximating a float by a rational.
	public static final int MAX_INT_VALUE = Integer.MAX_VALUE;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private RationalUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Create a rational approximation to a floating-point number <code>x</code>.
	 * <p>
	 * We use the Hardy-Wright algorithm, based on continued fraction partial
	 * convegents. The resulting rational <code>p/q</code> satisfies
	 * <code>|x-p/q| <= 1/(k*q^2)</code> where <code>k</code> is the
	 * <code>i+1</code><i>th</i> component in the continued fraction of
	 * <code>x</code>, if the algorithm terminates after <code>i</code>
	 * partial convegents. We make sure that <code>i</code> is sufficiently
	 * large so that <code>|x-p/q| <= tol</code>. The only exception is that
	 * the numerator and denominator are not to exceed
	 * <code>MAX_INT_VALUE</code>, so the tolerance may not always be reached
	 * if it is too small.
	 * 
	 * @param x
	 *            floating point number
	 * @param tol
	 *            approximation tolerance
	 */
	public static Rational roundDoubleToRational(final double x, final double tol)
	{
		int num;
		int den;
		double y = x;
		double z = x;
		final int MAX_ITER = 100;
		int p1 = 0, q1 = 1, p2 = 1, q2 = 0, i = 0, iter = 0;
		while ((p2 <= MAX_INT_VALUE)
				&& (q2 <= MAX_INT_VALUE)
				&& !RealUtil.doubleEquals(y, p2 / (q2 + RealUtil.MACHINE_DOUBLE_ERROR),
						tol) &&
				// (Math.abs(y-p2/(q2+TestUtil.MACHINE_DOUBLE_ERROR)) > tol) &&
				(iter < MAX_ITER)) {
			iter++;
			// Update old values: use the
			// recurrence relationship between
			// continued-fraction partial convergents
			int p0 = p1;
			int q0 = q1;
			p1 = p2;
			q1 = q2;
			// i = IntegerPart(z)
			i = (int) Math.floor(z);
			if (z < 0)
				i = (int) Math.ceil(z);
			p2 = p0 + i * p1;
			q2 = q0 + i * q1;

			if (RealUtil.doubleEquals(z, i)) {
				// z happens to be an exact rational
				// logger.debug("iter "+iter+" "+p2+"/"+q2+" err
				// "+Math.abs(y-p2/(q2+1e-16)));
				break;
			}
			// Get the next convergent
			z = 1.0 / (z - i);
			// logger.debug("iter "+iter+" "+p2+"/"+q2+" err
			// "+Math.abs(y-p2/(q2+1e-16)));
		}
		// Result: p2/q2
		num = p2;
		den = q2;
		if (q2 < 0) {
			num = -num;
			den = -den;
		}
		return new Rational(num, den);
	}

	/**
	 * Rounded & rationalized value of a double.
	 * 
	 * @param x
	 *            number to be printed
	 * @param tol
	 *            round-off tolerance. For <code>n</code> significant digits,
	 *            use <code>tol=10^{-n}</code>.
	 * @return rounded value
	 */
	@Deprecated
	public static double roundUsingRationalApproximation(final double x, final double tol)
	{
		return RationalUtil.roundDoubleToRational(x, tol).doubleValue();
	}

	/**
	 * Positive remainder. Like the <code>%</code> operator, but the result is
	 * always in <code>[0..n-1]</code>.
	 * 
	 * @param x
	 *            any integer number
	 * @param n
	 *            modulus base
	 * @return <code>x % n</code>, but if <code>x</code> is negative, adds
	 *         a multiple of <code>n</code> so that the result is still in
	 *         <code>[0..n-1]</code>.
	 */
	public static int positiveMod(final int x, final int n)
	{
		return (x >= 0) ? (x % n) : (((x + 1) % n) + n - 1);
	}

	/**
	 * Get base-<code>b</code> digits of a number. If <code>x</code> is
	 * negative, return the digits of <code>-x</code>. If <code>x=0</code>,
	 * return an array with a single entry (<code>0</code>).
	 * 
	 * @param x
	 *            a <i>positive</i> number to be analyzed
	 * @param base
	 *            base to compute digits in
	 * @return Array of digits. The first entry is the least significant digit;
	 *         the last one is the most significant digit.
	 */
	public static List<Integer> digits(final int x, final int base)
	{
		int y = x;
		List<Integer> digits = new ArrayList<Integer>();
		if (y == 0) {
			digits.add(0);
		}
		else {
			if (y < 0) {
				y = -y;
			}
			while (y != 0) {
				digits.add(y % base);
				y = y / base;
			}
		}
		return digits;
	}
}
