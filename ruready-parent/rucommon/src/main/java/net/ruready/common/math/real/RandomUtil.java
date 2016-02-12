/*****************************************************************************************
 * Source File: RandomUtil.java
 ****************************************************************************************/
package net.ruready.common.math.real;

import java.util.Random;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities related to random number generation. Centralizes all methods related to
 * randomization, shuffling, etc.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 25, 2007
 */
public class RandomUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * For logging printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RandomUtil.class);

	/**
	 * For parameter randomization.
	 */
	private static Random rand = new Random();

	// ==========================================================
	// Confidence intervals / t-Distributions
	// ==========================================================
	/*
	 * Original table Reference: http://en.wikipedia.org/wiki/Student%27s_t-distribution r
	 * 75% 80% 85% 90% 95% 97.5% 99% 99.5% 99.75% 99.9% 99.95% 1 1.000 1.376 1.963 3.078
	 * 6.314 12.71 31.82 63.66 127.3 318.3 636.6 2 0.816 1.061 1.386 1.886 2.920 4.303
	 * 6.965 9.925 14.09 22.33 31.60 3 0.765 0.978 1.250 1.638 2.353 3.182 4.541 5.841
	 * 7.453 10.21 12.92 4 0.741 0.941 1.190 1.533 2.132 2.776 3.747 4.604 5.598 7.173
	 * 8.610 5 0.727 0.920 1.156 1.476 2.015 2.571 3.365 4.032 4.773 5.893 6.869 6 0.718
	 * 0.906 1.134 1.440 1.943 2.447 3.143 3.707 4.317 5.208 5.959 7 0.711 0.896 1.119
	 * 1.415 1.895 2.365 2.998 3.499 4.029 4.785 5.408 8 0.706 0.889 1.108 1.397 1.860
	 * 2.306 2.896 3.355 3.833 4.501 5.041 9 0.703 0.883 1.100 1.383 1.833 2.262 2.821
	 * 3.250 3.690 4.297 4.781 10 0.700 0.879 1.093 1.372 1.812 2.228 2.764 3.169 3.581
	 * 4.144 4.587
	 */
	private final static double[] conf =
	{
			.75, .80, .85, .90, .95, .975, .99, .995, .9975, .999, .9995
	};

	private final static int[] dof =
	{
			1, 2, 3, 4, 5, 6, 7, 8, 9, 10
	};

	private final static double[][] tDistrib =
	{
			{
					1.000, 1.376, 1.963, 3.078, 6.314, 12.71, 31.82, 63.66, 127.3, 318.3,
					636.6
			},
			{
					0.816, 1.061, 1.386, 1.886, 2.920, 4.303, 6.965, 9.925, 14.09, 22.33,
					31.60
			},
			{
					0.765, 0.978, 1.250, 1.638, 2.353, 3.182, 4.541, 5.841, 7.453, 10.21,
					12.92
			},
			{
					0.741, 0.941, 1.190, 1.533, 2.132, 2.776, 3.747, 4.604, 5.598, 7.173,
					8.610
			},
			{
					0.727, 0.920, 1.156, 1.476, 2.015, 2.571, 3.365, 4.032, 4.773, 5.893,
					6.869
			},
			{
					0.718, 0.906, 1.134, 1.440, 1.943, 2.447, 3.143, 3.707, 4.317, 5.208,
					5.959
			},
			{
					0.711, 0.896, 1.119, 1.415, 1.895, 2.365, 2.998, 3.499, 4.029, 4.785,
					5.408
			},
			{
					0.706, 0.889, 1.108, 1.397, 1.860, 2.306, 2.896, 3.355, 3.833, 4.501,
					5.041
			},
			{
					0.703, 0.883, 1.100, 1.383, 1.833, 2.262, 2.821, 3.250, 3.690, 4.297,
					4.781
			},
			{
					0.700, 0.879, 1.093, 1.372, 1.812, 2.228, 2.764, 3.169, 3.581, 4.144,
					4.587
			}
	};

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private RandomUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Generate a random integer in an interval.
	 * 
	 * @param low
	 *            lower bound (inclusive)
	 * @param high
	 *            upper bound (inclusive)
	 * @return random interger in [low:high]
	 */
	public static int randomInInterval(final int low, final int high)
	{
		// Generate a random integer in the range
		int n = high - low + 1;
		int i = rand.nextInt() % n;
		if (i < 0)
		{
			i = -i;
		}
		return low + i;
	}

	/**
	 * Generate a random boolean.
	 * 
	 * @param frequency
	 *            average (number of returning false + 1) per one true return
	 * @return random boolean; average (number of returning false + 1) per one true return
	 */
	public static boolean randomBoolean(final int frequency)
	{
		// Generate a random integer in the range
		return (RandomUtil.randomInInterval(1, frequency) == 1);
	}

	/**
	 * Generate a random boolean with equal probabilities for returning true and false.
	 * 
	 * @return a random boolean with equal probabilities for returning true and false
	 */
	public static boolean randomBoolean()
	{
		// Somewhat faster than calling RandomUtils.randomBoolean(2)
		return (Math.random() < 0.5);
	}

	/**
	 * Create a new random numerical password for a user. Consists of digits only between
	 * 0 and <code>maxPassword-1</code>.
	 * 
	 * @param maxPassword
	 *            maximum number allowed for the password
	 * @return password String
	 */
	public static String randomLongPassword(final int maxPassword)
	{
		if (maxPassword <= 0)
		{
			throw new IllegalArgumentException(
					"Cannot generate password with non-positive length " + maxPassword);
		}
		int num = 0;
		while (num == 0)
		{
			num = rand.nextInt(maxPassword);
		}
		String password = CommonNames.MISC.EMPTY_STRING;
		int pad = 10 * num;
		while (pad < maxPassword)
		{
			password = password + "0";
			pad *= 10;
		}
		password = password + num; // A bizzare way of converting long to String!
		return password;
	} // randomPassword()

	/**
	 * Generate a random element in a discrete set (list), where each element has a
	 * probability of being selected.
	 * 
	 * @param p
	 *            array of probabilities. p[i] is the probability of selecting i,
	 *            sum(p[i]) = 1, 0 <= p[i] <= 1.
	 * @param numSamples
	 *            #generated random indices
	 * @return random integer in [0..p.length-1]
	 */
	public static int randomDiscrete(final double[] p)
	{
		// x is uniformly random in [0,1]
		double x = rand.nextDouble();

		// If x is in the interval [q[i]..q[i+1]), choose i
		// where q[i] = sum(p[j], j=0..i-1), q[0] = 0
		double low, high = 0.0d;
		for (int i = 0; i < p.length; i++)
		{
			low = high;
			high += p[i];
			if ((x >= low) && (x < high))
			{
				return i;
			}
		}
		// Something went wrong
		return -1;
	}

	/**
	 * Return the constant A in the one-sided confidence interval lower bound on a
	 * Gaussian random variable X, which is (mu - A*std/sqrt(n-1)) where mu = average of n
	 * samples of X and std = their standard deviation. Here #dof = n-1.
	 * 
	 * @param confidence
	 *            desired confidence level (in [0,1])
	 * @param thisDof
	 *            degrees of freedom
	 * @return confidence interval constant A
	 */
	public static double tDistribution(final double confidence, final int thisDof)
	{
		if (thisDof <= 0)
			return Double.POSITIVE_INFINITY;

		int dofIndex = 0;
		for (int i = 0; i < dof.length; i++, dofIndex++)
		{
			if (thisDof <= dof[i])
			{
				break;
			}
		}
		if (dofIndex == dof.length)
			dofIndex--;

		int confIndex = 0;
		for (int i = 0; i < conf.length; i++, confIndex++)
		{
			if (confidence <= conf[i])
			{
				break;
			}
		}
		if (confIndex == conf.length)
			confIndex--;

		return tDistrib[dofIndex][confIndex];
	}

	/**
	 * One-sided confidence interval lower bound on a Gaussian random variable X, which is
	 * (mu - A*std/sqrt(n-1)) where mu = average of n samples of X and std = their
	 * standard deviation. Here #dof = n-1 and A is a function of the dof
	 * 
	 * @param mu
	 *            average of X measured over n samples
	 * @param std
	 *            standard deviation of X (continuous, or measured over the same n
	 *            samples)
	 * @param confidence
	 *            desired confidence level (in [0,1])
	 * @param n
	 *            number of samples
	 * @return One-sided confidence interval lower bound
	 */
	public static double confidenceLowerBound(final double confidence, final int n,
			final double mu, final double std)
	{
		int degreesOfFreedom = n - 1;
		if (degreesOfFreedom <= 0)
			return Double.NEGATIVE_INFINITY;
		double A = tDistribution(confidence, degreesOfFreedom);
		return mu - A * std / Math.sqrt(degreesOfFreedom);
	}

	/**
	 * Generate a random string.
	 * 
	 * @param low
	 *            minimum string length
	 * @param high
	 *            maximum string length
	 * @return
	 */
	public static String randomString(final int low, final int high)
	{
		int n = randomInInterval(low, high);
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
		{
			b[i] = (byte) randomInInterval('a', 'z');
		}
		return new String(b);
	}

}
