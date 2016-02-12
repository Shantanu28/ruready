/*****************************************************************************************
 * Source File: Complex.java
 ****************************************************************************************/
package net.ruready.common.math.complex;

import java.io.Serializable;
import java.text.ParseException;

import net.ruready.common.math.real.RealConstants;
import net.ruready.common.math.real.RealUtil;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * File: Complex.java -- A Java class for performing complex number arithmetic to double
 * precision. Copyright (c) 1997 - 2001, Alexander Anderson. This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA. p> (c) 2006-07 Continuing Education ,
 * University of Utah . All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846
 * 25702.PROV
 * 
 * @version <b>1.0.1</b> <br>
 *          <tt> Last change: ALM 23 Mar 2001 8:56 pm </tt>
 *          <p>
 *          A Java class for performing complex number arithmetic to <tt>double</tt>
 *          precision.
 *          <p>
 *          <center> <applet name="SeeComplex" archive="SeeComplex.jar"
 *          code="SeeComplex.class" codebase="imagery" width="85%" height="85%"
 *          align="Middle" alt="SeeComplex Applet" > Make yours a Java enabled browser and
 *          OS! </applet>
 *          <p>
 *          This applet has been adapted<br>
 *          from a <a href="http://www.pa.uky.edu/~phy211/VecArith/index.html">Vector
 *          Visualization applet</a> by <a href="mailto:Vladimir Sorokin
 *          <vsoro00@pop.uky.edu>">Vladimir Sorokin</a>. </center>
 *          <hr>
 *          <p>
 * @author <a href="mailto:Alexander Anderson <sandy@almide.demon.co.uk>">Sandy Anderson</a>
 * @author Priyantha Jayanetti
 *         <p>
 *         <font color="000080">
 * 
 * <pre>
 *                                &lt;b&gt;Copyright (c)
 *                                         1997 - 2001, Alexander Anderson.&lt;/b&gt; This program is free
 *                                         software; you can redistribute it and/or modify it under the terms
 *                                         of the &lt;a href=&quot;http://www.gnu.org/&quot;&gt;GNU&lt;/a&gt;
 *                                         General Public License as published by the Free Software Foundation;
 *                                         either version 2 of the License, or (at your option) any later
 *                                         version. This program is distributed in the hope that it will be
 *                                         useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *                                         of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *                                         General Public License for more details. You should have received a
 *                                         copy of the GNU General Public &lt;a
 *                                         href=&quot;GNU_GeneralPublicLicence.html&quot;&gt;License&lt;/a&gt;
 *                                         along with this program; if not, write to the Free Software
 *                                         Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 *                                         USA.
 * </pre>
 * 
 * </font>
 *         <p>
 *         The latest version of this <tt>Complex</tt> class is available from the <a
 *         href="http://www.netlib.org/">Netlib Repository</a>.
 *         <p>
 *         Here's an example of the style the class permits:<br>
 * 
 * <pre>
 *                                &lt;b&gt;import&lt;/b&gt;
 *                                         ORG.netlib.math.complex.Complex;
 * <br>
 *                                &lt;b&gt;public&lt;/b&gt;
 *                                         &lt;b&gt;class&lt;/b&gt; Test {
 * <br>
 *                                &lt;b&gt;public
 *                                         boolean&lt;/b&gt; isInMandelbrot (Complex c, &lt;b&gt;int&lt;/b&gt;
 *                                         maxIter) { Complex z= &lt;b&gt;new&lt;/b&gt; Complex(0, 0);
 * <br>
 *                                         &lt;b&gt;for&lt;/b&gt; (&lt;b&gt;int&lt;/b&gt; i= 0; i &lt; maxIter;
 *                                         i++) { z= z.times(z).plus(c); &lt;b&gt;if&lt;/b&gt; (z.abs() &gt; 2)
 *                                         &lt;b&gt;return false&lt;/b&gt;; }
 * <br>
 *                                &lt;b&gt;return
 *                                         true&lt;/b&gt;; }
 * <br>
 *                                }
 * </pre>
 * 
 * </dd>
 *         <p>
 *         <dd>This class was developed by <a href="http://www.almide.demon.co.uk">Sandy
 *         Anderson</a> at the School of Electronic Engineering, <a
 *         href="http://www.mdx.ac.uk/">Middlesex University</a>, UK, and Priyantha
 *         Jayanetti at The Power Systems Program, the <a
 *         href="http://www.eece.maine.edu/">University of Maine</a>, USA. </dd>
 *         <p>
 *         <dd>And many, many thanks to <a
 *         href="mailto:R.D.Hirsch@red-deer.demon.co.uk">Mr. Daniel Hirsch</a>, for his
 *         constant advice on the mathematics, his exasperating ability to uncover bugs
 *         blindfold, and for his persistent badgering over the exact wording of this
 *         documentation. </dd>
 *         <p>
 *         <dd>For instance, he starts to growl like a badger if you say "infinite set".</dd>
 *         <br>
 *         <dd>"Grrr...What's <i>that</i> mean? <i>Countably</i> infinite?"</dd>
 *         <br>
 *         <dd>You think for a while.</dd>
 *         <br>
 *         <dd>"Grrr..."</dd>
 *         <br>
 *         <dd>"Yes."</dd>
 *         <br>
 *         <dd>"Ah! Then you mean <i>infinitely many</i>."</dd>
 *         <br>
 *         <p>
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and Continuing
 *         Education (AOCE) 1901 East South Campus Dr., Room 2197-E University of Utah,
 *         Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E, University of
 *         Utah University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 *         <br>(c) 2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 25, 2007
 */
public class Complex implements Cloneable, Serializable
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Complex.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1;

	public static final String VERSION = "1.0.1";

	public static final String DATE = "Fri 23-Mar-2001 8:56 pm";

	public static final String AUTHOR = "sandy@almide.demon.co.uk";

	public static final String REMARK = "Class available from "
			+ "http://www.netlib.org/";

	// Symbol for unit imaginary number
	public static String UNIT_IM_SYMBOL = "i";

	// Complex constants
	public static final Complex ZERO = new Complex(0.0D, 0.0D);

	public static final Complex ONE = new Complex(1.0D, 0.0D);

	public static final Complex MINUS_ONE = new Complex(-1.0D, 0.0D);

	public static final Complex UNIT_IM = new Complex(0.0D, 1.0D);

	/**
	 * Switches on debugging information.
	 * <p>
	 */
	// protected static boolean debug = false;
	/**
	 * Whilst debugging: the nesting level when tracing method calls.
	 * <p>
	 */
	// private static int trace_nesting = 0;
	/**
	 * Twice <a href="http://cad.ucla.edu/repository/useful/PI.txt"><tt><b>PI</b></tt></a>
	 * radians is the same thing as 360 degrees.
	 * <p>
	 */
	private static final double TWO_PI = 2.0 * Math.PI;

	/**
	 * A constant representing <i><b>i</b></i>, the famous square root of <i>-1</i>.
	 * <p>
	 * The other square root of <i>-1</i> is - <i><b>i</b></i>.
	 * <p>
	 */
	public static final Complex i = new Complex(0.0, 1.0);

	// private static long objectCount; // !!!

	// ========================= FIELDS ====================================

	// Real part
	private double re;

	// Imaginary part
	private double im;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a <tt>Complex</tt> representing the number zero.
	 * <p>
	 */

	public Complex()
	{
		this(0.0, 0.0);
	}// end Complex()

	/**
	 * Constructs a <tt>Complex</tt> representing a real number.
	 * <p>
	 * 
	 * @param re
	 *            The real number
	 *            <p>
	 * @see Complex#real(double)
	 */

	public Complex(double re)
	{
		this(re, 0.0);
	}// end Complex(double)

	// public Complex(RealValue re)
	// {
	// this(re.getValue(), 0.0);
	// }// end Complex(double)

	/**
	 * Constructs a separate new <tt>Complex</tt> from an existing <tt>Complex</tt>.
	 * <p>
	 * 
	 * @param z
	 *            A <tt>Complex</tt> number
	 *            <p>
	 */

	public Complex(Complex z)
	{
		this(z.re, z.im);
	}// end Complex(Complex)

	/**
	 * Constructs a <tt>Complex</tt> from real and imaginary parts.
	 * <p>
	 * <i><b>Note:</b>
	 * <ul>
	 * <font color="000080">All methods in class <tt>Complex</tt> which deliver a
	 * <tt>Complex</tt> are written such that no intermediate <tt>Complex</tt> objects
	 * get generated. This means that you can easily anticipate the likely effects on
	 * garbage collection caused by your own coding.</font>
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @param re
	 *            Real part
	 * @param im
	 *            Imaginary part
	 *            <p>
	 * @see Complex#cart(double, double)
	 * @see Complex#polar(double, double)
	 */

	public Complex(double re, double im)
	{
		this.re = re;
		this.im = im;

		// if (debug) logger.info(indent(trace_nesting) + "new Complex,
		// #" + (++objectCount));// !!!
	}// end Complex(double,double)

	// ---------------------------------//
	// DEBUG //
	// ---------------------------------//

	/*
	 * // BETA Debugging methods... private static void entering (String what) {
	 * logger.info(indent(trace_nesting) + what); trace_nesting++; }//end entering(String)
	 * private static void enter (String what, double param1, double param2) {
	 * entering(what); logger.info("(" + param1 + ", " + param2 + ") "); }//end
	 * enter(String,double,double) private static void enter (String what, double param) {
	 * entering(what); logger.info("(" + param + ") "); }//end enter(String,double)
	 * private static void enter (String what, Complex z) { entering(what);
	 * logger.info("(" + z + ") "); }//end enter(String,Complex) private static void enter
	 * (String what, Complex z1, Complex z2) { entering(what); logger.info("(" + z1 + ", " +
	 * z2 + ") "); }//end enter(String,Complex,Complex) private static void enter (String
	 * what, Complex z, double x) { entering(what); logger.info("(" + z + ", " + x + ")
	 * "); }//end enter(String,Complex,double) private static void enter (String what,
	 * Complex z, double x, double y) { entering(what); logger.info("(" + z + ", " +
	 * cart(x, y) + ") "); }//end enter(String,Complex,double) private static void enter
	 * (String what, Complex z1, Complex z2, double x) { entering(what); logger.info("(" +
	 * z1 + ", " + z2 + ", " + x + ") "); }//end enter(String,Complex,Complex,double)
	 * private static void leaving (String what) { trace_nesting--;
	 * logger.info(indent(trace_nesting) + "is "); }//end leaving(String) private static
	 * void leave (String what, boolean result) { leaving(what); logger.info(result);
	 * }//end leave(String,boolean) private static void leave (String what, double result) {
	 * leaving(what); logger.info(result); }//end leave(String,double) private static void
	 * leave (String what, Complex result) { leaving(what); logger.info(result); }//end
	 * leave(String,Complex) private static String indent (int nesting) { StringBuffer
	 * indention = TextUtil.emptyStringBuffer(); for (int i = 0; i < nesting;
	 * i++) { indention.append(" "); }//endfor return indention.toString(); }//end
	 * indent(int)
	 */

	// ========================= STATIC METHODS ============================
	/**
	 * Returns a <tt>Complex</tt> representing a real number.
	 * <p>
	 * 
	 * @param real
	 *            The real number
	 *            <p>
	 * @return <tt>Complex</tt> representation of the real
	 *         <p>
	 * @see Complex#re()
	 * @see Complex#cart(double, double)
	 */

	public static Complex real(double real)
	{
		return new Complex(real, 0.0);
	}// end real(double)

	/**
	 * Returns a <tt>Complex</tt> from real and imaginary parts.
	 * <p>
	 * 
	 * @param re
	 *            Real part
	 * @param im
	 *            Imaginary part
	 *            <p>
	 * @return <tt>Complex</tt> from Cartesian coordinates
	 *         <p>
	 * @see Complex#re()
	 * @see Complex#im()
	 * @see Complex#polar(double, double)
	 * @see Complex#toString()
	 */

	public static Complex cart(double re, double im)
	{
		return new Complex(re, im);
	}// end cart(double,double)

	/**
	 * Returns a <tt>Complex</tt> from a size and direction.
	 * <p>
	 * 
	 * @param r
	 *            Size
	 * @param theta
	 *            Direction (in <i>radians</i>)
	 *            <p>
	 * @return <tt>Complex</tt> from Polar coordinates
	 *         <p>
	 * @see Complex#abs()
	 * @see Complex#arg()
	 * @see Complex#cart(double, double)
	 */

	public static Complex polar(final double r0, final double theta0)
	{
		double r = r0;
		double theta = theta0;
		if (r < 0.0)
		{
			theta += Math.PI;
			r = -r;
		}// endif

		theta = theta % TWO_PI;

		return cart(r * Math.cos(theta), r * Math.sin(theta));
	}// end polar(double,double)

	/**
	 * Returns the <tt>Complex</tt> base raised to the power of the exponent.
	 * <p>
	 * 
	 * @param base
	 *            The base "to raise"
	 * @param exponent
	 *            The exponent "by which to raise"
	 *            <p>
	 * @return base "raised to the power of" exponent
	 *         <p>
	 * @see Complex#pow(double, Complex)
	 */

	public static Complex pow(Complex base, double exponent)
	{
		// return base.log().scale(exponent).exp();

		// =======================================================
		// Oren - bug fix when base = 0. Prior to the
		// fix, 0^anything would produce a NaN + NaN*i result.
		double rBase = base.abs();
		double rExponent = Math.abs(exponent);
		if (rBase == 0.0 && rExponent != 0.0)
		{
			return new Complex(0.0);
		}
		// =======================================================

		double re = exponent * Math.log(rBase);
		double im = exponent * base.arg();

		double scalar = Math.exp(re);

		return cart(scalar * Math.cos(im), scalar * Math.sin(im));
	}// end pow(Complex,double)

	/**
	 * Returns the base raised to the power of the <tt>Complex</tt> exponent.
	 * <p>
	 * 
	 * @param base
	 *            The base "to raise"
	 * @param exponent
	 *            The exponent "by which to raise"
	 *            <p>
	 * @return base "raised to the power of" exponent
	 *         <p>
	 * @see Complex#pow(Complex, Complex)
	 * @see Complex#exp()
	 */

	public static Complex pow(double base, Complex exponent)
	{
		// return real(base).log().times(exponent).exp();

		// =======================================================
		// Oren - bug fix when base = 0. Prior to the
		// fix, 0^anything would produce a NaN + NaN*i result.
		double rBase = Math.abs(base);
		double rExponent = exponent.abs();
		if (rBase == 0.0 && rExponent != 0.0)
		{
			return new Complex(0.0);
		}
		// =======================================================

		double re = Math.log(rBase);
		double im = Math.atan2(0.0, base);

		double re2 = (re * exponent.re) - (im * exponent.im);
		double im2 = (re * exponent.im) + (im * exponent.re);

		double scalar = Math.exp(re2);

		return cart(scalar * Math.cos(im2), scalar * Math.sin(im2));
	}// end pow(double,Complex)

	/**
	 * Returns the <tt>Complex</tt> base raised to the power of the <tt>Complex</tt>
	 * exponent.
	 * <p>
	 * 
	 * @param base
	 *            The base "to raise"
	 * @param exponent
	 *            The exponent "by which to raise"
	 *            <p>
	 * @return base "raised to the power of" exponent
	 *         <p>
	 * @see Complex#pow(Complex, double)
	 * @see Complex#pow(Complex)
	 */

	public static Complex pow(Complex base, Complex exponent)
	{
		// return base.log().times(exponent).exp();

		// =======================================================
		// Oren - bug fix when base = 0. Prior to the
		// fix, 0^anything would produce a NaN + NaN*i result.
		double rBase = base.abs();
		double rExponent = exponent.abs();
		if (rBase == 0.0 && rExponent != 0.0)
		{
			return new Complex(0.0);
		}
		// =======================================================

		double re = Math.log(rBase);
		double im = base.arg();

		double re2 = (re * exponent.re) - (im * exponent.im);
		double im2 = (re * exponent.im) + (im * exponent.re);

		double scalar = Math.exp(re2);

		return cart(scalar * Math.cos(im2), scalar * Math.sin(im2));
	}// end pow(Complex,Complex)

	// ========================= PUBLIC METHODS ============================

	/**
	 * Returns <tt>true</tt> if either the real or imaginary component of this
	 * <tt>Complex</tt> is an infinite value.
	 * <p>
	 * 
	 * @return <tt>true</tt> if either component of the <tt>Complex</tt> object is
	 *         infinite; <tt>false</tt>, otherwise.
	 *         <p>
	 */

	public boolean isInfinite()
	{
		return (Double.isInfinite(re) || Double.isInfinite(im));
	}// end isInfinite()

	/**
	 * Returns <tt>true</tt> if either the real or imaginary component of this
	 * <tt>Complex</tt> is a Not-a-Number (<tt>NaN</tt>) value.
	 * <p>
	 * 
	 * @return <tt>true</tt> if either component of the <tt>Complex</tt> object is
	 *         <tt>NaN</tt>; <tt>false</tt>, otherwise.
	 *         <p>
	 */

	public boolean isNaN()
	{
		return (Double.isNaN(re) || Double.isNaN(im));
	}// end isNaN()

	/**
	 * Overrides the {@link java.lang.Cloneable <tt>Cloneable</tt>} interface.
	 * <p>
	 * Standard override; no change in semantics.
	 * <p>
	 * The following Java code example illustrates how to clone, or <i>copy</i>, a
	 * <tt>Complex</tt> number:
	 * <p>
	 * 
	 * <pre>
	 *                                              Complex z1 =  &lt;b&gt;new&lt;/b&gt; Complex(0, 1);
	 *                                              Complex z2 =  (Complex) z1.clone();
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return An <tt>Object</tt> that is a copy of this <tt>Complex</tt> object.
	 *         <p>
	 * @see java.lang.Cloneable
	 * @see java.lang.Object#clone()
	 */

	@Override
	public Complex clone()
	{
		try
		{
			return (Complex)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
	} // clone()

	/**
	 * Extracts the real part of a <tt>Complex</tt> as a <tt>double</tt>.
	 * <p>
	 * 
	 * <pre>
	 *                                              re(x + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y)  =  x
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return The real part
	 *         <p>
	 * @see Complex#im()
	 * @see Complex#cart(double, double)
	 * @see Complex#real(double)
	 */

	public double re()
	{
		return re;
	}// end re()

	/**
	 * Extracts the imaginary part of a <tt>Complex</tt> as a <tt>double</tt>.
	 * <p>
	 * 
	 * <pre>
	 *                                              im(x + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y)  =  y
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return The imaginary part
	 *         <p>
	 * @see Complex#re()
	 * @see Complex#cart(double, double)
	 */

	public double im()
	{
		return im;
	}// end im()

	/**
	 * Returns the square of the "length" of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              norm(x + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y)  =  x*x + y*y
	 * </pre>
	 * 
	 * <p>
	 * Always non-negative.
	 * <p>
	 * 
	 * @return The norm
	 *         <p>
	 * @see Complex#abs()
	 */

	public double norm()
	{
		return (re * re) + (im * im);
	}// end norm()

	/**
	 * Returns the magnitude of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * abs(z) = sqrt(norm(z))
	 * </pre>
	 * 
	 * <p>
	 * In other words, it's Pythagorean distance from the origin (<i>0 + 0<b>i</b></i>,
	 * or zero).
	 * <p>
	 * The magnitude is also referred to as the "modulus" or "length".
	 * <p>
	 * Always non-negative.
	 * <p>
	 * 
	 * @return The magnitude (or "length")
	 *         <p>
	 * @see Complex#arg()
	 * @see Complex#polar(double, double)
	 * @see Complex#norm()
	 */

	public double abs()
	{
		return abs(re, im);
	}// end abs()

	private static double abs(double x, double y)
	{
		// abs(z) = sqrt(norm(z))

		// Adapted from
		// "Numerical Recipes in Fortran 77: The Art of Scientific Computing"
		// (ISBN 0-521-43064-X)

		double absX = Math.abs(x);
		double absY = Math.abs(y);

		if (absX == 0.0 && absY == 0.0)
		{ // !!! Numerical Recipes, mmm?
			return 0.0;
		}
		else if (absX >= absY)
		{
			double d = y / x;
			return absX * Math.sqrt(1.0 + d * d);
		}
		else
		{
			double d = x / y;
			return absY * Math.sqrt(1.0 + d * d);
		}// endif
	}// end abs()

	/**
	 * Returns the <i>principal</i> angle of a <tt>Complex</tt> number, in radians,
	 * measured counter-clockwise from the real axis. (Think of the reals as the x-axis,
	 * and the imaginaries as the y-axis.)
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>A</b>
	 * is the principal solution of <i>arg(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              &lt;b&gt;A&lt;/b&gt; + 2*k*&lt;b&gt;PI&lt;/b&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * <tt>arg()</tt> always returns a <tt>double</tt> between -<tt><b>PI</b></tt>
	 * and +<tt><b>PI</b></tt>.
	 * <p>
	 * <i><b>Note:</b>
	 * <ul>
	 * 2*<tt><b>PI</b></tt> radians is the same as 360 degrees.
	 * </ul>
	 * </i>
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * There are no restrictions: the class defines arg(0) to be 0
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return Principal angle (in radians)
	 *         <p>
	 * @see Complex#abs()
	 * @see Complex#polar(double, double)
	 */

	public double arg()
	{
		return Math.atan2(im, re);
	}// end arg()

	/**
	 * Returns the "negative" of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              neg(a + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*b)  =  -a - &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*b
	 * </pre>
	 * 
	 * <p>
	 * The magnitude of the negative is the same, but the angle is flipped through
	 * <tt><b>PI</b></tt> (or 180 degrees).
	 * <p>
	 * 
	 * @return Negative of the <tt>Complex</tt>
	 *         <p>
	 * @see Complex#scale(double)
	 */

	public Complex neg()
	{
		return this.scale(-1.0);
	}// end neg()

	/**
	 * Returns the <tt>Complex</tt> "conjugate".
	 * <p>
	 * 
	 * <pre>
	 *                                              conj(x + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y)  =  x - &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y
	 * </pre>
	 * 
	 * <p>
	 * The conjugate appears "flipped" across the real axis.
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> conjugate
	 *         <p>
	 */

	public Complex conj()
	{
		return cart(re, -im);
	}// end conj()

	private static void inv(Complex z)
	{
		double zRe, zIm;
		double scalar;

		if (Math.abs(z.re) >= Math.abs(z.im))
		{
			scalar = 1.0 / (z.re + z.im * (z.im / z.re));

			zRe = scalar;
			zIm = scalar * (-z.im / z.re);
		}
		else
		{
			scalar = 1.0 / (z.re * (z.re / z.im) + z.im);

			zRe = scalar * (z.re / z.im);
			zIm = -scalar;
		}// endif

		z.re = zRe;
		z.im = zIm;
	}// end inv(Complex)

	/**
	 * Returns the <tt>Complex</tt> scaled by a real number.
	 * <p>
	 * 
	 * <pre>
	 *                                              scale((x + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y), s)  =  (x*s + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y*s)
	 * </pre>
	 * 
	 * <p>
	 * Scaling by the real number <i>2.0</i>, doubles the magnitude, but leaves the
	 * <tt>arg()</tt> unchanged. Scaling by <i>-1.0</i> keeps the magnitude the same,
	 * but flips the <tt>arg()</tt> by <tt><b>PI</b></tt> (180 degrees).
	 * <p>
	 * 
	 * @param scalar
	 *            A real number scale factor
	 *            <p>
	 * @return <tt>Complex</tt> scaled by a real number
	 *         <p>
	 * @see Complex#mul(Complex)
	 * @see Complex#over(Complex)
	 * @see Complex#neg()
	 */

	public Complex scale(double scalar)
	{
		return cart(scalar * re, scalar * im);
	}// end scale(double)

	/**
	 * To perform z1 + z2, you write <tt>z1.plus(z2)</tt> .
	 * <p>
	 * 
	 * <pre>
	 *                                              (a + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*b) + (c + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*d)  =  ((a+c) + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*(b+d))
	 * </pre>
	 * 
	 * <p>
	 */

	public Complex plus(Complex z)
	{
		return cart(re + z.re, im + z.im);
	}// end add(Complex)

	public Complex plus(double z)
	{
		return cart(re + z, im);
	}// end add(Complex)

	/**
	 * To perform z1 - z2, you write <tt>z1.minus(z2)</tt> .
	 * <p>
	 * 
	 * <pre>
	 *                                              (a + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*b) - (c + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*d)  =  ((a-c) + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*(b-d))
	 * </pre>
	 * 
	 * <p>
	 */

	public Complex minus(Complex z)
	{
		return cart(re - z.re, im - z.im);
	}// end sub(Complex)

	/**
	 * To perform z1 * z2, you write <tt>z1.times(z2)</tt> .
	 * <p>
	 * 
	 * <pre>
	 *                                              (a + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*b) * (c + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*d)  =  ( (a*c) - (b*d) + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*((a*d) + (b*c)) )
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @see Complex#scale(double)
	 */

	public Complex times(Complex z)
	{
		return cart((re * z.re) - (im * z.im), (re * z.im) + (im * z.re));
		// return cart( (re*z.re) - (im*z.im), (re + im)*(z.re + z.im) - re*z.re
		// - im*z.im);
	}// end mul(Complex)

	/**
	 * To perform z1 / z2, you write <tt>z1.over(z2)</tt> .
	 * <p>
	 * 
	 * <pre>
	 *                                              (a + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*b) / (c + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*d)  =  ( (a*c) + (b*d) + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*((b*c) - (a*d)) ) / norm(c + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*d)
	 * </pre>
	 * 
	 * <p>
	 * <i><b>Take care not to divide by zero!</b></i>
	 * <p>
	 * <i><b>Note:</b>
	 * <ul>
	 * <tt>Complex</tt> arithmetic in Java never causes exceptions. You have to
	 * deliberately check for overflow, division by zero, and so on, <u>for yourself</u>.
	 * </ul>
	 * </i>
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * z1/z2 is undefined if z2 = 0
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @see Complex#scale(double)
	 */

	public Complex over(Complex z)
	{
		Complex result = new Complex(this);
		over(result, z.re, z.im);
		return result;
	}// end over(Complex)

	private static void over(Complex z, double x, double y)
	{
		// Adapted from
		// "Numerical Recipes in Fortran 77: The Art of Scientific Computing"
		// (ISBN 0-521-43064-X)

		double zRe, zIm;
		double scalar;

		if (Math.abs(x) >= Math.abs(y))
		{
			scalar = 1.0 / (x + y * (y / x));

			zRe = scalar * (z.re + z.im * (y / x));
			zIm = scalar * (z.im - z.re * (y / x));

		}
		else
		{
			scalar = 1.0 / (x * (x / y) + y);

			zRe = scalar * (z.re * (x / y) + z.im);
			zIm = scalar * (z.im * (x / y) - z.re);
		}// endif

		z.re = zRe;
		z.im = zIm;
	}// end over(Complex,double,double)

	/**
	 * Returns a <tt>Complex</tt> representing one of the two square roots.
	 * <p>
	 * 
	 * <pre>
	 *                                              sqrt(z)  =  sqrt(abs(z)) * ( cos(arg(z)/2) + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt; * sin(arg(z)/2) )
	 * </pre>
	 * 
	 * <p>
	 * For any <i>complex</i> number <i>z</i>, <i>sqrt(z)</i> will return the
	 * <i>complex</i> root whose <i>arg</i> is <i>arg(z)/2</i>.
	 * <p>
	 * <i><b>Note:</b>
	 * <ul>
	 * There are always two square roots for each <tt>Complex</tt> number, except for 0 +
	 * 0<b>i</b>, or zero. The other root is the <tt>neg()</tt> of the first one. Just
	 * as the two roots of 4 are 2 and -2, the two roots of -1 are <b>i</b> and - <b>i</b>.
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return The square root whose <i>arg</i> is <i>arg(z)/2</i>.
	 *         <p>
	 * @see Complex#pow(Complex, double)
	 */

	public Complex sqrt()
	{
		Complex result = new Complex(this);
		sqrt(result);
		return result;
	}// end sqrt()

	private static void sqrt(Complex z)
	{
		// with thanks to Jim Shapiro <jnshapi@argo.ecte.uswc.uswest.com>
		// adapted from "Numerical Recipies in C" (ISBN 0-521-43108-5)
		// by William H. Press et al

		double mag = z.abs();

		if (mag > 0.0)
		{
			if (z.re > 0.0)
			{
				double temp = Math.sqrt(0.5 * (mag + z.re));

				z.re = temp;
				z.im = 0.5 * z.im / temp;
			}
			else
			{
				double temp = Math.sqrt(0.5 * (mag - z.re));

				if (z.im < 0.0)
				{
					temp = -temp;
				}// endif

				z.re = 0.5 * z.im / temp;
				z.im = temp;
			}// endif
		}
		else
		{
			z.re = 0.0;
			z.im = 0.0;
		}// endif
	}// end sqrt(Complex)

	/**
	 * Returns this <tt>Complex</tt> raised to the power of a <tt>Complex</tt>
	 * exponent.
	 * <p>
	 * 
	 * @param exponent
	 *            The exponent "by which to raise"
	 *            <p>
	 * @return this <tt>Complex</tt> "raised to the power of" the exponent
	 *         <p>
	 * @see Complex#pow(Complex, Complex)
	 */

	public Complex pow(Complex exponent)
	{
		return Complex.pow(this, exponent);
	}// end pow(Complex)

	/**
	 * Returns the number <i><b>e</b></i> "raised to" a <tt>Complex</tt> power.
	 * <p>
	 * 
	 * <pre>
	 *                                              exp(x + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*y)  =  exp(x) * ( cos(y) + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt; * sin(y) )
	 * </pre>
	 * 
	 * <p>
	 * <i><b>Note:</b>
	 * <ul>
	 * The value of <i><b>e</b></i>, a transcendental number, is roughly
	 * 2.71828182846...
	 * <p>
	 * Also, the following is quietly amazing:
	 * 
	 * <pre>
	 *                                              &lt;i&gt;&lt;b&gt;e&lt;/b&gt;&lt;/i&gt;&lt;sup&gt;&lt;font size=+0&gt;&lt;b&gt;PI&lt;/b&gt;*&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;&lt;/font&gt;&lt;/sup&gt;    =    - 1
	 * </pre>
	 * 
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return <i><b>e</b></i> "raised to the power of" this <tt>Complex</tt>
	 *         <p>
	 * @see Complex#log()
	 * @see Complex#pow(double, Complex)
	 */

	public Complex exp()
	{
		double scalar = Math.exp(re); // e^ix = cis x
		return cart(scalar * Math.cos(im), scalar * Math.sin(im));
	}// end exp()

	/**
	 * Returns the <i>principal</i> natural logarithm of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              log(z)  =  log(abs(z)) + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt; * arg(z)
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>L</b>
	 * is the principal solution of <i>log(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              &lt;b&gt;L&lt;/b&gt; + (2*k*&lt;b&gt;PI&lt;/b&gt;)*&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> natural logarithm
	 *         <p>
	 * @see Complex#exp()
	 */

	public Complex log()
	{
		return cart(Math.log(this.abs()), this.arg()); // principal value
	}// end log()

	/**
	 * Returns the <i>principal</i> logarithm (<i>base 10</i>) of a <tt>Complex</tt>
	 * number.
	 * <p>
	 * 
	 * <pre>
	 * log10(z) = log(z) / log(10)
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>L</b>
	 * is the principal solution of <i>log10(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              &lt;b&gt;L&lt;/b&gt; + (2*k*&lt;b&gt;PI&lt;/b&gt;)*&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> logarithm (base 10)
	 *         <p>
	 * @see Complex#exp()
	 * @see Complex#log()
	 */
	/*
	 * DEPRECATED !!! public Complex log10 () { Complex result; // if (debug)
	 * enter("log10", this); double scalar = 1.0/Math.log(10.0); // result =
	 * this.log().scale(scalar); result = cart( scalar * Math.log(this.abs()), scalar *
	 * this.arg() ); // if (debug) leave("log10", result); return result; }//end log10() /*
	 */

	/**
	 * Returns the sine of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              sin(z)  =  ( exp(&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*z) - exp(-&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*z) ) / (2*&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;)
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> sine
	 *         <p>
	 * @see Complex#asin()
	 * @see Complex#sinh()
	 * @see Complex#cosec()
	 * @see Complex#cos()
	 * @see Complex#tan()
	 */

	public Complex sin()
	{
		Complex result;
		// sin(z) = ( exp(i*z) - exp(-i*z) ) / (2*i)

		double scalar;
		double iz_re, iz_im;
		double _re1, _im1;
		double _re2, _im2;

		// iz: i.times(z) ...
		iz_re = -im;
		iz_im = re;

		// _1: iz.exp() ...
		scalar = Math.exp(iz_re);
		_re1 = scalar * Math.cos(iz_im);
		_im1 = scalar * Math.sin(iz_im);

		// _2: iz.neg().exp() ...
		scalar = Math.exp(-iz_re);
		_re2 = scalar * Math.cos(-iz_im);
		_im2 = scalar * Math.sin(-iz_im);

		// _1: _1.minus(_2) ...
		_re1 = _re1 - _re2; // !!!
		_im1 = _im1 - _im2; // !!!

		// result: _1.over(2*i) ...
		result = cart(0.5 * _im1, -0.5 * _re1);
		// ... result = cart(_re1, _im1);
		// over(result, 0.0, 2.0);
		return result;
	}// end sin()

	/**
	 * Returns the cosine of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              cos(z)  =  ( exp(&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*z) + exp(-&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*z) ) / 2
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> cosine
	 *         <p>
	 * @see Complex#acos()
	 * @see Complex#cosh()
	 * @see Complex#sec()
	 * @see Complex#sin()
	 * @see Complex#tan()
	 */

	public Complex cos()
	{
		Complex result;
		// cos(z) = ( exp(i*z) + exp(-i*z) ) / 2

		double scalar;
		double iz_re, iz_im;
		double _re1, _im1;
		double _re2, _im2;

		// iz: i.times(z) ...
		iz_re = -im;
		iz_im = re;

		// _1: iz.exp() ...
		scalar = Math.exp(iz_re);
		_re1 = scalar * Math.cos(iz_im);
		_im1 = scalar * Math.sin(iz_im);

		// _2: iz.neg().exp() ...
		scalar = Math.exp(-iz_re);
		_re2 = scalar * Math.cos(-iz_im);
		_im2 = scalar * Math.sin(-iz_im);

		// _1: _1.plus(_2) ...
		_re1 = _re1 + _re2; // !!!
		_im1 = _im1 + _im2; // !!!

		// result: _1.scale(0.5) ...
		result = cart(0.5 * _re1, 0.5 * _im1);
		return result;
	}// end cos()

	/**
	 * Returns the tangent of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * tan(z) = sin(z) / cos(z)
	 * </pre>
	 * 
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * tan(z) is undefined whenever z = (k + 1/2) * <tt><b>PI</b></tt><br>
	 * where k is any integer
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> tangent
	 *         <p>
	 * @see Complex#atan()
	 * @see Complex#tanh()
	 * @see Complex#cot()
	 * @see Complex#sin()
	 * @see Complex#cos()
	 */

	public Complex tan()
	{
		Complex result;
		// tan(z) = sin(z) / cos(z)

		double scalar;
		double iz_re, iz_im;
		double _re1, _im1;
		double _re2, _im2;
		double _re3, _im3;

		double cs_re, cs_im;

		// sin() ...

		// iz: i.times(z) ...
		iz_re = -im;
		iz_im = re;

		// _1: iz.exp() ...
		scalar = Math.exp(iz_re);
		_re1 = scalar * Math.cos(iz_im);
		_im1 = scalar * Math.sin(iz_im);

		// _2: iz.neg().exp() ...
		scalar = Math.exp(-iz_re);
		_re2 = scalar * Math.cos(-iz_im);
		_im2 = scalar * Math.sin(-iz_im);

		// _3: _1.minus(_2) ...
		_re3 = _re1 - _re2;
		_im3 = _im1 - _im2;

		// result: _3.over(2*i) ...
		result = cart(0.5 * _im3, -0.5 * _re3);
		// result = cart(_re3, _im3);
		// over(result, 0.0, 2.0);

		// cos() ...

		// _3: _1.plus(_2) ...
		_re3 = _re1 + _re2;
		_im3 = _im1 + _im2;

		// cs: _3.scale(0.5) ...
		cs_re = 0.5 * _re3;
		cs_im = 0.5 * _im3;

		// result: result.over(cs) ...
		over(result, cs_re, cs_im);
		return result;
	}// end tan()

	/**
	 * Returns the cosecant of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * cosec(z) = 1 / sin(z)
	 * </pre>
	 * 
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * cosec(z) is undefined whenever z = k * <tt><b>PI</b></tt><br>
	 * where k is any integer
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> cosecant
	 *         <p>
	 * @see Complex#sin()
	 * @see Complex#sec()
	 * @see Complex#cot()
	 */

	public Complex cosec()
	{
		Complex result;
		// cosec(z) = 1 / sin(z)

		double scalar;
		double iz_re, iz_im;
		double _re1, _im1;
		double _re2, _im2;

		// iz: i.times(z) ...
		iz_re = -im;
		iz_im = re;

		// _1: iz.exp() ...
		scalar = Math.exp(iz_re);
		_re1 = scalar * Math.cos(iz_im);
		_im1 = scalar * Math.sin(iz_im);

		// _2: iz.neg().exp() ...
		scalar = Math.exp(-iz_re);
		_re2 = scalar * Math.cos(-iz_im);
		_im2 = scalar * Math.sin(-iz_im);

		// _1: _1.minus(_2) ...
		_re1 = _re1 - _re2; // !!!
		_im1 = _im1 - _im2; // !!!

		// _result: _1.over(2*i) ...
		result = cart(0.5 * _im1, -0.5 * _re1);
		// result = cart(_re1, _im1);
		// over(result, 0.0, 2.0);

		// result: one.over(_result) ...
		inv(result);
		return result;
	}// end cosec()

	/**
	 * Returns the secant of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * sec(z) = 1 / cos(z)
	 * </pre>
	 * 
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * sec(z) is undefined whenever z = (k + 1/2) * <tt><b>PI</b></tt><br>
	 * where k is any integer
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> secant
	 *         <p>
	 * @see Complex#cos()
	 * @see Complex#cosec()
	 * @see Complex#cot()
	 */

	public Complex sec()
	{
		Complex result;
		// sec(z) = 1 / cos(z)

		double scalar;
		double iz_re, iz_im;
		double _re1, _im1;
		double _re2, _im2;

		// iz: i.times(z) ...
		iz_re = -im;
		iz_im = re;

		// _1: iz.exp() ...
		scalar = Math.exp(iz_re);
		_re1 = scalar * Math.cos(iz_im);
		_im1 = scalar * Math.sin(iz_im);

		// _2: iz.neg().exp() ...
		scalar = Math.exp(-iz_re);
		_re2 = scalar * Math.cos(-iz_im);
		_im2 = scalar * Math.sin(-iz_im);

		// _1: _1.plus(_2) ...
		_re1 = _re1 + _re2;
		_im1 = _im1 + _im2;

		// result: _1.scale(0.5) ...
		result = cart(0.5 * _re1, 0.5 * _im1);

		// result: one.over(result) ...
		inv(result);
		return result;
	}// end sec()

	/**
	 * Returns the cotangent of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * cot(z) = 1 / tan(z)
	 * </pre>
	 * 
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * cot(z) is undefined whenever z = k * <tt><b>PI</b></tt><br>
	 * where k is any integer
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> cotangent
	 *         <p>
	 * @see Complex#tan()
	 * @see Complex#cosec()
	 * @see Complex#sec()
	 */

	public Complex cot()
	{
		Complex result;
		// cot(z) = 1 / tan(z) = cos(z) / sin(z)

		double scalar;
		double iz_re, iz_im;
		double _re1, _im1;
		double _re2, _im2;
		double _re3, _im3;

		double sn_re, sn_im;

		// cos() ...

		// iz: i.times(z) ...
		iz_re = -im;
		iz_im = re;

		// _1: iz.exp() ...
		scalar = Math.exp(iz_re);
		_re1 = scalar * Math.cos(iz_im);
		_im1 = scalar * Math.sin(iz_im);

		// _2: iz.neg().exp() ...
		scalar = Math.exp(-iz_re);
		_re2 = scalar * Math.cos(-iz_im);
		_im2 = scalar * Math.sin(-iz_im);

		// _3: _1.plus(_2) ...
		_re3 = _re1 + _re2;
		_im3 = _im1 + _im2;

		// result: _3.scale(0.5) ...
		result = cart(0.5 * _re3, 0.5 * _im3);

		// sin() ...

		// _3: _1.minus(_2) ...
		_re3 = _re1 - _re2;
		_im3 = _im1 - _im2;

		// sn: _3.over(2*i) ...
		sn_re = 0.5 * _im3; // !!!
		sn_im = -0.5 * _re3; // !!!

		// result: result.over(sn) ...
		over(result, sn_re, sn_im);
		return result;
	}// end cot()

	/**
	 * Returns the hyperbolic sine of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * sinh(z) = (exp(z) - exp(-z)) / 2
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> hyperbolic sine
	 *         <p>
	 * @see Complex#sin()
	 * @see Complex#asinh()
	 */

	public Complex sinh()
	{
		Complex result;
		// sinh(z) = ( exp(z) - exp(-z) ) / 2

		double scalar;
		double _re1, _im1;
		double _re2, _im2;

		// _1: z.exp() ...
		scalar = Math.exp(re);
		_re1 = scalar * Math.cos(im);
		_im1 = scalar * Math.sin(im);

		// _2: z.neg().exp() ...
		scalar = Math.exp(-re);
		_re2 = scalar * Math.cos(-im);
		_im2 = scalar * Math.sin(-im);

		// _1: _1.minus(_2) ...
		_re1 = _re1 - _re2; // !!!
		_im1 = _im1 - _im2; // !!!

		// result: _1.scale(0.5) ...
		result = cart(0.5 * _re1, 0.5 * _im1);
		return result;
	}// end sinh()

	/**
	 * Returns the hyperbolic cosine of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * cosh(z) = (exp(z) + exp(-z)) / 2
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> hyperbolic cosine
	 *         <p>
	 * @see Complex#cos()
	 * @see Complex#acosh()
	 */

	public Complex cosh()
	{
		Complex result;
		// cosh(z) = ( exp(z) + exp(-z) ) / 2

		double scalar;
		double _re1, _im1;
		double _re2, _im2;

		// _1: z.exp() ...
		scalar = Math.exp(re);
		_re1 = scalar * Math.cos(im);
		_im1 = scalar * Math.sin(im);

		// _2: z.neg().exp() ...
		scalar = Math.exp(-re);
		_re2 = scalar * Math.cos(-im);
		_im2 = scalar * Math.sin(-im);

		// _1: _1.plus(_2) ...
		_re1 = _re1 + _re2; // !!!
		_im1 = _im1 + _im2; // !!!

		// result: _1.scale(0.5) ...
		result = cart(0.5 * _re1, 0.5 * _im1);
		return result;
	}// end cosh()

	/**
	 * Returns the hyperbolic tangent of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 * tanh(z) = sinh(z) / cosh(z)
	 * </pre>
	 * 
	 * <p>
	 * 
	 * @return The <tt>Complex</tt> hyperbolic tangent
	 *         <p>
	 * @see Complex#tan()
	 * @see Complex#atanh()
	 */

	public Complex tanh()
	{
		Complex result;
		// tanh(z) = sinh(z) / cosh(z)

		double scalar;
		double _re1, _im1;
		double _re2, _im2;
		double _re3, _im3;

		double ch_re, ch_im;

		// sinh() ...

		// _1: z.exp() ...
		scalar = Math.exp(re);
		_re1 = scalar * Math.cos(im);
		_im1 = scalar * Math.sin(im);

		// _2: z.neg().exp() ...
		scalar = Math.exp(-re);
		_re2 = scalar * Math.cos(-im);
		_im2 = scalar * Math.sin(-im);

		// _3: _1.minus(_2) ...
		_re3 = _re1 - _re2;
		_im3 = _im1 - _im2;

		// result: _3.scale(0.5) ...
		result = cart(0.5 * _re3, 0.5 * _im3);

		// cosh() ...

		// _3: _1.plus(_2) ...
		_re3 = _re1 + _re2;
		_im3 = _im1 + _im2;

		// ch: _3.scale(0.5) ...
		ch_re = 0.5 * _re3;
		ch_im = 0.5 * _im3;

		// result: result.over(ch) ...
		over(result, ch_re, ch_im);
		return result;
	}// end tanh()

	/**
	 * Returns the <i>principal</i> arc sine of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              asin(z)  =  -&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt; * log(&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;*z + sqrt(1 - z*z))
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>A</b>
	 * is the principal solution of <i>asin(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              k*&lt;b&gt;PI&lt;/b&gt; + (-1)&lt;sup&gt;&lt;font size=-1&gt;k&lt;/font&gt;&lt;/sup&gt;  * &lt;b&gt;A&lt;/b&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> arc sine
	 *         <p>
	 * @see Complex#sin()
	 * @see Complex#sinh()
	 */

	public Complex asin()
	{
		Complex result;
		// asin(z) = -i * log(i*z + sqrt(1 - z*z))

		double _re1, _im1;

		// _1: one.minus(z.times(z)) ...
		_re1 = 1.0 - ((re * re) - (im * im));
		_im1 = 0.0 - ((re * im) + (im * re));

		// result: _1.sqrt() ...
		result = cart(_re1, _im1);
		sqrt(result);

		// _1: z.times(i) ...
		_re1 = -im;
		_im1 = +re;

		// result: _1.plus(result) ...
		result.re = _re1 + result.re;
		result.im = _im1 + result.im;

		// _1: result.log() ...
		_re1 = Math.log(result.abs());
		_im1 = result.arg();

		// result: i.neg().times(_1) ...
		result.re = _im1;
		result.im = -_re1;
		return result;
	}// end asin()

	/**
	 * Returns the <i>principal</i> arc cosine of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              acos(z)  =  -&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt; * log( z + &lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt; * sqrt(1 - z*z) )
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>A</b>
	 * is the principal solution of <i>acos(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              2*k*&lt;b&gt;PI&lt;/b&gt; +/- &lt;b&gt;A&lt;/b&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> arc cosine
	 *         <p>
	 * @see Complex#cos()
	 * @see Complex#cosh()
	 */

	public Complex acos()
	{
		Complex result;
		// acos(z) = -i * log( z + i * sqrt(1 - z*z) )

		double _re1, _im1;

		// _1: one.minus(z.times(z)) ...
		_re1 = 1.0 - ((re * re) - (im * im));
		_im1 = 0.0 - ((re * im) + (im * re));

		// result: _1.sqrt() ...
		result = cart(_re1, _im1);
		sqrt(result);

		// _1: i.times(result) ...
		_re1 = -result.im;
		_im1 = +result.re;

		// result: z.plus(_1) ...
		result.re = re + _re1;
		result.im = im + _im1;

		// _1: result.log()
		_re1 = Math.log(result.abs());
		_im1 = result.arg();

		// result: i.neg().times(_1) ...
		result.re = _im1;
		result.im = -_re1;
		return result;
	}// end acos()

	/**
	 * Returns the <i>principal</i> arc tangent of a <tt>Complex</tt> number.
	 * <p>
	 * 
	 * <pre>
	 *                                              atan(z)  =  -&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;/2 * log( (&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;-z)/(&lt;i&gt;&lt;b&gt;i&lt;/b&gt;&lt;/i&gt;+z) )
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>A</b>
	 * is the principal solution of <i>atan(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              &lt;b&gt;A&lt;/b&gt; + k*&lt;b&gt;PI&lt;/b&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * atan(z) is undefined for z = + <b>i</b> or z = - <b>i</b>
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> arc tangent
	 *         <p>
	 * @see Complex#tan()
	 * @see Complex#tanh()
	 */

	public Complex atan()
	{
		Complex result;
		// atan(z) = -i/2 * log( (i-z)/(i+z) )

		double _re1, _im1;

		// result: i.minus(z) ...
		result = cart(-re, 1.0 - im);

		// _1: i.plus(z) ...
		_re1 = +re;
		_im1 = 1.0 + im;

		// result: result.over(_1) ...
		over(result, _re1, _im1);

		// _1: result.log() ...
		_re1 = Math.log(result.abs());
		_im1 = result.arg();

		// result: half_i.neg().times(_2) ...
		result.re = 0.5 * _im1;
		result.im = -0.5 * _re1;
		return result;
	}// end atan()

	/**
	 * Returns the <i>principal</i> inverse hyperbolic sine of a <tt>Complex</tt>
	 * number.
	 * <p>
	 * 
	 * <pre>
	 * asinh(z) = log(z + sqrt(z * z + 1))
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>A</b>
	 * is the principal solution of <i>asinh(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              k*&lt;b&gt;PI&lt;/b&gt;*&lt;b&gt;&lt;i&gt;i&lt;/i&gt;&lt;/b&gt; + (-1)&lt;sup&gt;&lt;font size=-1&gt;k&lt;/font&gt;&lt;/sup&gt;  * &lt;b&gt;A&lt;/b&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> inverse hyperbolic sine
	 *         <p>
	 * @see Complex#sinh()
	 */

	public Complex asinh()
	{
		Complex result;
		// asinh(z) = log(z + sqrt(z*z + 1))

		double _re1, _im1;

		// _1: z.times(z).plus(one) ...
		_re1 = ((re * re) - (im * im)) + 1.0;
		_im1 = ((re * im) + (im * re)) + 0.0;

		// result: _1.sqrt() ...
		result = cart(_re1, _im1);
		sqrt(result);

		// result: z.plus(result) ...
		result.re = re + result.re; // !
		result.im = im + result.im; // !

		// _1: result.log() ...
		_re1 = Math.log(result.abs());
		_im1 = result.arg();

		// result: _1 ...
		result.re = _re1;
		result.im = _im1;

		/*
		 * Many thanks to the mathematicians of aus.mathematics and sci.math, and to
		 * Zdislav V. Kovarik of the Department of Mathematics and Statistics, McMaster
		 * University and John McGowan <jmcgowan@inch.com> in particular, for their advice
		 * on the current naming conventions for "area/argumentus sinus hyperbolicus".
		 */

		return result;
	}// end asinh()

	/**
	 * Returns the <i>principal</i> inverse hyperbolic cosine of a <tt>Complex</tt>
	 * number.
	 * <p>
	 * 
	 * <pre>
	 * acosh(z) = log(z + sqrt(z * z - 1))
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>A</b>
	 * is the principal solution of <i>acosh(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              2*k*&lt;b&gt;PI&lt;/b&gt;*&lt;b&gt;&lt;i&gt;i&lt;/i&gt;&lt;/b&gt; +/- &lt;b&gt;A&lt;/b&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> inverse hyperbolic cosine
	 *         <p>
	 * @see Complex#cosh()
	 */

	public Complex acosh()
	{
		Complex result;
		// acosh(z) = log(z + sqrt(z*z - 1))

		double _re1, _im1;

		// _1: z.times(z).minus(one) ...
		_re1 = ((re * re) - (im * im)) - 1.0;
		_im1 = ((re * im) + (im * re)) - 0.0;

		// result: _1.sqrt() ...
		result = cart(_re1, _im1);
		sqrt(result);

		// result: z.plus(result) ...
		result.re = re + result.re; // !
		result.im = im + result.im; // !

		// _1: result.log() ...
		_re1 = Math.log(result.abs());
		_im1 = result.arg();

		// result: _1 ...
		result.re = _re1;
		result.im = _im1;
		return result;
	}// end acosh()

	/**
	 * Returns the <i>principal</i> inverse hyperbolic tangent of a <tt>Complex</tt>
	 * number.
	 * <p>
	 * 
	 * <pre>
	 * atanh(z) = 1 / 2 * log((1 + z) / (1 - z))
	 * </pre>
	 * 
	 * <p>
	 * There are infinitely many solutions, besides the principal solution. If <b>A</b>
	 * is the principal solution of <i>atanh(z)</i>, the others are of the form:
	 * <p>
	 * 
	 * <pre>
	 *                                              &lt;b&gt;A&lt;/b&gt; + k*&lt;b&gt;PI&lt;/b&gt;*&lt;b&gt;&lt;i&gt;i&lt;/i&gt;&lt;/b&gt;
	 * </pre>
	 * 
	 * <p>
	 * where k is any integer.
	 * <p>
	 * <i><b>Domain Restrictions:</b>
	 * <ul>
	 * atanh(z) is undefined for z = + 1 or z = - 1
	 * </ul>
	 * </i>
	 * <p>
	 * 
	 * @return Principal <tt>Complex</tt> inverse hyperbolic tangent
	 *         <p>
	 * @see Complex#tanh()
	 */

	public Complex atanh()
	{
		Complex result;
		// atanh(z) = 1/2 * log( (1+z)/(1-z) )

		double _re1, _im1;

		// result: one.plus(z) ...
		result = cart(1.0 + re, +im);

		// _1: one.minus(z) ...
		_re1 = 1.0 - re;
		_im1 = -im;

		// result: result.over(_1) ...
		over(result, _re1, _im1);

		// _1: result.log() ...
		_re1 = Math.log(result.abs());
		_im1 = result.arg();

		// result: _1.scale(0.5) ...
		result.re = 0.5 * _re1;
		result.im = 0.5 * _im1;
		return result;
	}// end atanh()

	// CONVERSIONS
	/**
	 * Converts a <tt>Complex</tt> into a {@link java.lang.String <tt>String</tt>} of
	 * the form <tt>(</tt><i>a</i><tt> + </tt><i>b</i><tt>i)</tt>.
	 * <p>
	 * This enables a <tt>Complex</tt> to be easily printed. For example, if <tt>z</tt>
	 * was <i>2 - 5<b>i</b></i>, then
	 * 
	 * <pre>
	 * logger.info(&quot;z = &quot; + z);
	 * </pre>
	 * 
	 * would print something like
	 * 
	 * <pre>
	 *                                              z = (2.0 - 5.0i)
	 * </pre>
	 * 
	 * <!-- <i><b>Note:</b>
	 * <ul>
	 * Concatenating {@link java.lang.String <tt>String</tt>}s, using a system overloaded
	 * meaning of the "<tt>+</tt>" operator, in fact causes the <tt>toString()</tt>
	 * method to be invoked on the object <tt>z</tt> at runtime.
	 * </ul>
	 * </i> -->
	 * <p>
	 * 
	 * @return {@link java.lang.String <tt>String</tt>} containing the cartesian
	 *         coordinate representation
	 *         <p>
	 * @see Complex#cart(double, double)
	 */
	// Format a complex number as a string, a + bj or a + bi[instance method]
	// < value of real > < + or - > < j or i> < value of imag >
	// Choice of j or i is set by Complex.seti() or Complex.setj()
	// j is the default option for j or i
	// Overides java.lang.String.toString()
	public String toString(final double tol)
	{
		// logger.debug("Complex.toString(" + this.re() + "," + this.im + ")");
		// Compute imaginary part sign
		char ch = (im < 0.0D) ? '-' : '+';

		Double AbsIm = Math.abs(im);
		String reStr = Complex.formatReal(re, tol);
		String imStr = Complex.formatReal(im, tol);
		String absImStr = Complex.formatReal(AbsIm, tol);
		if (RealUtil.doubleEquals(im, RealConstants.ONE, tol)
				|| RealUtil.doubleEquals(im, RealConstants.MINUS_ONE, tol))
		{
			imStr = CommonNames.MISC.EMPTY_STRING;
		}
		if (RealUtil.doubleEquals(im, RealConstants.ZERO, tol))
		{
			return CommonNames.MISC.EMPTY_STRING + reStr;
		}
		if (RealUtil.doubleEquals(re, RealConstants.ZERO, tol))
		{
			return imStr + UNIT_IM_SYMBOL;
		}
		// logger.debug("reStr " + reStr + CommonNames.MISC.EMPTY_STRING);
		// logger.debug("ch " + ch + CommonNames.MISC.EMPTY_STRING);
		// logger.debug("absImStr " + absImStr + CommonNames.MISC.EMPTY_STRING);
		return reStr + ch + absImStr + UNIT_IM_SYMBOL;
	}

	// Overides java.lang.String.toString()
	@Override
	public String toString()
	{
		return toString(RealUtil.MACHINE_DOUBLE_ERROR);
	}

	/**
	 * Used to a be a relative tolerance for printouts. Right now the tolerance does not
	 * have an effect.
	 * 
	 * @param d
	 *            real valued number
	 * @param tol
	 *            tolerance of rounding
	 * @param rationalRound
	 *            round to rational (tolerance=tol) or not.
	 * @return r printed to tol-precision
	 */
	private static String formatReal(double d, double tol)
	{
		// Make sure that r is printed as an integer
		// if it is [close to] one.
		long dInt = java.lang.Math.round(d);
		// Seems like "u" is too strict. So have 10*u
		// as the threshold.
		if (RealUtil.doubleEquals(1.0 * dInt, d, tol))
		{
			return CommonNames.MISC.EMPTY_STRING + dInt;
		}
		return Double.toString(d);
	}

	// Sets the representation of the square root of minus one to j in Strings
	public static void setj()
	{
		UNIT_IM_SYMBOL = "j";
	}

	// Sets the representation of the square root of minus one to i in Strings
	public static void seti()
	{
		UNIT_IM_SYMBOL = "i";
	}

	// Returns the representation of the square root of minus one (j or i) set
	// for Strings
	public static String getUNIT_IM_SYMBOL()
	{
		return UNIT_IM_SYMBOL;
	}

	/**
	 * Returns a new <code>Complex</code> initialized to the value represented by the
	 * specified <code>String</code>. Accepts two types of strings:<br> - Any
	 * <code>Double</code>-parsable string. - A full cartesian format "Re(c) + Im(c)i"
	 * or "Re(c) - Im(c)i".
	 * 
	 * @param str
	 *            the string to be parsed.
	 * @return the <code>Complex</code> value represented by the string argument.
	 * @exception NumberFormatException
	 *                if the string does not contain a parsable <code>double</code>.
	 * @see java.lang.Complex#valueOf(String)
	 */
	public static Complex parseComplex(String str)
	{
		// Try parsing a double value first
		try
		{
			Complex c = new Complex(Double.parseDouble(str));
			return c;
		}
		catch (NumberFormatException e)
		{

		}

		// Requires both re, im (e.g. 0-2i to represent -2i).
		ComplexFormat cf = new ComplexFormat();
		try
		{
			Complex c = cf.parse(str);
			return c;
		}
		catch (ParseException e)
		{
			throw new NumberFormatException(e.toString());
		}
	}

	/**
	 * Returns a <code>Complex</code> object holding the <code>complex</code> value
	 * represented by the argument string <code>s</code>.
	 * <p>
	 * If <code>s</code> is <code>null</code>, then a
	 * <code>NullPointerException</code> is thrown.
	 * 
	 * @param s
	 *            the string to be parsed.
	 * @return a <code>Double</code> object holding the value represented by the
	 *         <code>String</code> argument.
	 * @exception NumberFormatException
	 *                if the string does not contain a parsable number.
	 */
	// Overides java.lang.Object.valueOf()
	public static Complex valueOf(String ss) throws NumberFormatException
	{
		return Complex.parseComplex(ss);
	}

	/**
	 * Result of equality of two complex values. They are equal if and only if their
	 * <code>re,im</code> fields are equal up according to the {@link Double} class
	 * equals() implementation.
	 * 
	 * @param obj
	 *            The other <code>Complex</code> object.
	 * @return boolean The result of equality.
	 */
	@Override
	public boolean equals(Object obj)
	{
		// TODO: replace with Double equality
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
		Complex other = (Complex) obj;

		int compareRe = Double.compare(this.re(), other.re());
		int compareIm = Double.compare(this.im(), other.im());

		return (compareRe == 0) && (compareIm == 0);
	}

	// Return a HASH CODE for the Complex number
	// Overides java.lang.Object.hashCode()
	@Override
	public int hashCode()
	{
		long lre = Double.doubleToLongBits(this.re);
		long lim = Double.doubleToLongBits(this.im);
		int hre = (int) (lre ^ (lre >>> 32));
		int him = (int) (lim ^ (lim >>> 32));
		return 7 * (hre / 10) + 3 * (him / 10);
	}

	// Returns the reciprocal (1/a) of a Complex number (a) [instance method]
	public Complex inverse()
	{
		Complex b = new Complex(1.0D, 0.0D);
		return b.over(this);
	}

	// Negates a Complex number [instance method]
	public Complex negate()
	{
		Complex c = new Complex();
		c.re = -this.re;
		c.im = -this.im;
		return c;
	}

	/**
	 * Returns the base-<code>b</code> logarithm of a Complex number.
	 */
	public static final Complex log(Complex b, Complex x)
	{
		return x.log().over(b.log());
	}

	/**
	 * Returns the base-<code>b</code> logarithm of a Complex number.
	 */
	public Complex log10()
	{
		return log().over(new Complex(RealConstants.LOG10));
	}

	/**
	 * Returns the base-<code>b</code> logarithm of a Complex number.
	 */
	public static final Complex log(double b, Complex x)
	{
		return x.log().over(new Complex(Math.log(b)));
	}

	public Complex mod(Complex z)
	{
		if (RealUtil.doubleEquals(im, 0.0D) && RealUtil.doubleEquals(z.im, 0.0D))
		{
			return cart(re % z.re, 0.0D);
		}
		if (RealUtil.doubleEquals(re, 0.0D) && RealUtil.doubleEquals(z.re, 0.0D))
		{
			return cart(0.0D, im % z.im);
		}
		return cart(re % z.re, im % z.im);
	}// end mod(Complex)

	/**
	 * Returns the <code>b</code><i>th</i> root of a Complex value.
	 * 
	 * @param r
	 *            root order
	 * @param x
	 *            a number to take the root of
	 * @return the value x^(1/r)
	 */
	public Complex root(Complex z)
	{
		return z.pow(inverse());
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the im
	 */
	public double getIm()
	{
		return im;
	}

	/**
	 * @param im
	 *            the im to set
	 */
	protected void setIm(double im)
	{
		this.im = im;
	}

	/**
	 * @return the re
	 */
	public double getRe()
	{
		return re;
	}

	/**
	 * @param re
	 *            the re to set
	 */
	protected void setRe(double re)
	{
		this.re = re;
	}

	/**
	 * @param re
	 *            the re to set
	 * @param im
	 *            the im to set
	 */
	protected void setReIm(double re, double im)
	{
		this.re = re;
		this.im = im;
	}

	// ========================= TESTING ===================================

	/**
	 * Useful for checking up on the exact version. Debugging complex number printouts.
	 * <p>
	 */
	public static void main(String[] args)
	{
		logger.info(CommonNames.MISC.EMPTY_STRING);
		logger.info("Module : " + Complex.class.getName());
		logger.info("Version: " + Complex.VERSION);
		logger.info("Date   : " + Complex.DATE);
		logger.info("Author : " + Complex.AUTHOR);
		logger.info("Remark : " + Complex.REMARK);
		logger.info(CommonNames.MISC.EMPTY_STRING);
		logger.info("Hint:  use TestComplex to test the class.");
		logger.info(CommonNames.MISC.EMPTY_STRING);

		Complex a = new Complex(+0.6, +0.4);
		Complex b = new Complex(+0.6, -0.4);
		Complex c = new Complex(-0.6, +0.4);
		Complex d = new Complex(-0.6, -0.4);
		Complex e = new Complex(+0.6, 0.0);
		Complex f = new Complex(-0.6, 0.0);
		Complex g = new Complex(0.0, +0.4);
		Complex h = new Complex(0.0, -0.4);
		logger.info("a " + a);
		logger.info("b " + b);
		logger.info("c " + c);
		logger.info("d " + d);
		logger.info("e " + e);
		logger.info("f " + f);
		logger.info("g " + g);
		logger.info("h " + h);
	} // end main(String[])
}// end Complex
