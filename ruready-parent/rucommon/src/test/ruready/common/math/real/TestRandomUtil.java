/*****************************************************************************************
 * Source File: TestRationalUtil.java
 ****************************************************************************************/
package test.ruready.common.math.real;

import net.ruready.common.math.real.RandomUtil;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test procedures related to statistical and random number generation
 * functions.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class TestRandomUtil extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestRandomUtil.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * 
	 */
	@Test
	public void testConfidenceLevel()
	{
		logger.info("*** Confidence level ***");
		double confidence = 0.8;
		int k = 9;
		logger.info("conf " + confidence + " k " + k + " A "
				+ RandomUtil.tDistribution(confidence, k)); // A =
		// 0.883
		confidence = 0.9975;
		k = 100;
		logger.info("confidence " + confidence + " k " + k + " A "
				+ RandomUtil.tDistribution(confidence, k)); // A =
		// 3.581

		double mu = 0.5;
		double std = 0.2;
		confidence = 0.8;
		k = 3;
		logger.info("Confidence lower bound "
				+ RandomUtil.confidenceLowerBound(confidence, k, mu, std));
	}

	@Test
	public void testRandomSelectionBiased()
	{
		logger.info("*** Random Selection Results, biased ***");
		// Input relative probabilities
		double[] p =
		{ 0.5, 0.5, 1, 0.1, 0.01, 0.2, 0.8 };

		// Normalize probabilities to sum = 1
		int n = p.length;
		double sum = 0.0;
		for (int i = 0; i < n; i++)
		{
			sum += p[i];
		}
		sum = 1. / sum;
		for (int i = 0; i < n; i++)
		{
			p[i] *= sum;
		}

		// Generate random numbers and compute their histogram
		final long numSamples = 100000L;
		final long tick = 10000L;
		// final long tickNewLine = 70 * tick;
		// final long modulus = tick - 1;
		// final long modulusNewLine = tickNewLine - 1;
		logger.info("Running random selection tests ... (#=" + tick + " tests)");
		long[] hist = new long[n];
		for (int i = 0; i < n; i++)
		{
			hist[i] = 0;
		}
		for (long j = 0; j < numSamples; j++)
		{
			// if ((j % tick) == modulus)
			// logger.info("#");
			// if ((j % tickNewLine) == modulusNewLine)
			// logger.info(CommonNames.MISC.EMPTY_STRING);
			hist[RandomUtil.randomDiscrete(p)]++;
		}
		logger.info(" done.");

		// Print results
		logger.info("1/sqrt(numSamples) [%] = " + 100.0 / Math.sqrt(numSamples));
		logger.info(CommonNames.MISC.EMPTY_STRING);
		logger.info(" i  OrigProb   ActualProb   Rel.Err. [%]");
		logger.info("=======================================");
		for (int i = 0; i < n; i++)
		{
			double actualP = 1.0 * hist[i] / numSamples;
			logger.info(i + "     " + p[i] + "    " + actualP + "  " + 100.0
					* (actualP - p[i]) / p[i]);
		}
	}

	/**
	 * Test unbiased dice rolling. See if bias probabilities behave according to
	 * the central limit theorem.
	 */
	@Test
	public void testRandomSelectionUnbiased()
	{
		logger.info("*** Random Selection Results, unbiased ***");
		// Batch size, like #questions in a test
		final int numBatch = 32;
		final long numSamples = 100000L;
		final long tick = 10000L;
		// final long tickNewLine = 70 * tick;
		// final long modulus = tick - 1;
		// final long modulusNewLine = tickNewLine - 1;
		logger.info("Running random selection tests ... (#=" + tick + " tests)");
		long[] hist = new long[numBatch + 1];
		for (int i = 0; i <= numBatch; i++)
		{
			hist[i] = 0;
		}

		for (long j = 0; j < numSamples; j++)
		{
			// if ((j % tick) == modulus)
			// logger.info("#");
			// if ((j % tickNewLine) == modulusNewLine)
			// logger.info(CommonNames.MISC.EMPTY_STRING);
			int count = 0;
			for (int i = 0; i < numBatch; i++)
			{
				if (RandomUtil.randomBoolean())
					count++;
			}
			// logger.info(" " + count);
			hist[count]++;
		}
		logger.info(" done.");

		// Print results
		logger.info("1/sqrt(numBatch)   [%] = " + 100.0 / Math.sqrt(numBatch));
		logger.info("1/sqrt(numSamples) [%] = " + 100.0 / Math.sqrt(numSamples));
		logger.info(CommonNames.MISC.EMPTY_STRING);
		logger.info("  Bias   Probability[%]");
		logger.info("============================");
		for (int i = 0; i <= numBatch; i++)
		{
			double p = 1.0 * hist[i] / numSamples;
			logger.info(" " + (i - numBatch / 2) + "     " + 100.0 * p);
		}
	}

	/**
	 * 
	 */
	@Test
	public void testRandomBoolean()
	{
		logger.info("*** Random boolean test ****\n");
		int frequency = 100;
		int countTrue = 0;
		int numTrials = 10 * frequency;
		for (int i = 0; i < numTrials; i++)
		{
			if (RandomUtil.randomBoolean(frequency))
			{
				countTrue++;
			}
		}
		logger.info("#trials " + numTrials + " frequency " + frequency + " #true "
				+ countTrue);
	}

	// ========================= TESTING ====================================
}
