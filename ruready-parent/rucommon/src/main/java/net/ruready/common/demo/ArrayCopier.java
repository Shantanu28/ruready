/*****************************************************************************************
 * Source File: ArrayCopier.java
 ****************************************************************************************/
package net.ruready.common.demo;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.commsen.stopwatch.Report;
import com.commsen.stopwatch.Stopwatch;

/**
 * Array copying options demo. There are several ways to copy an array :
 * <p>
 * <ul>
 * <li> call its clone method, and do a cast - the simplest style, but only a shallow
 * clone is performed</li>
 * <li> use System.arraycopy - useful when copying parts of an array</li>
 * <li> use the various copyOf and copyOfRange methods of the Arrays class</li>
 * <li> use a for loop - more than one line, and needs a loop index</li>
 * </ul>
 * <p>
 * Example
 * <p>
 * This example class demonstrates
 * <ul>
 * <li> relative performance of the various methods</li>
 * <li> how clone is a shallow copy, and leads to independent storage only for primitive,
 * one dimensional arrays.</li>
 * </ul>
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
 * @version Jul 16, 2007
 */
public final class ArrayCopier
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ArrayCopier.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ArrayCopier()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Display the time it takes to copy an array in various ways.
	 */
	private static void demoPerformance(int aNumIterations)
	{
		// An environment with a resource locator that has reference to a parser
		// service provider
		// Environment environment = new MinimalStandAloneEnvironment();
		// environment.setUp();
		// ResourceLocator resourceLocator = environment.getResourceLocator();
		// Stopwatch.setActive(true);

		int[] numbers =
		{
				1, 2, 3, 4, 5, 6, 7, 8, 9, 10
		};

		{
			// TODO: replace start() parameters below by something more
			// intelligent
			// and less hard-coded
			long watchId = Stopwatch.start("my group", "my label");
			copyUsingClone(numbers, aNumIterations);
			Stopwatch.stop(watchId);
			Report[] reports = Stopwatch.getGroupReports("my group");
			logger.debug("Using clone: " + reports[0]);
		}

		{
			long watchId = Stopwatch.start("my group", "my label");
			copyUsingArraycopy(numbers, aNumIterations);
			Stopwatch.stop(watchId);
			Report[] reports = Stopwatch.getGroupReports("my group");
			logger.debug("Using System.arraycopy: " + reports[0]);
		}

		// stopwatch.start();
		// copyUsingArraysCopyOf(numbers, aNumIterations);
		// stopwatch.stop();
		// logger.debug("Using Arrays.copyOf: " + stopwatch);

		{
			long watchId = Stopwatch.start("my group", "my label");
			copyUsingForLoop(numbers, aNumIterations);
			Stopwatch.stop(watchId);
			Report[] reports = Stopwatch.getGroupReports("my group");
			logger.debug("Using for loop " + reports[0]);
		}

		// environment.tearDown();
	}

	private static void copyUsingClone(int[] aArray, int aNumIterations)
	{
		for (int idx = 0; idx < aNumIterations; ++idx)
		{

			@SuppressWarnings("unused")
			int[] copy = aArray.clone();

		}
	}

	private static void copyUsingArraycopy(int[] aArray, int aNumIterations)
	{
		for (int idx = 0; idx < aNumIterations; ++idx)
		{

			int[] copy = new int[aArray.length];
			System.arraycopy(aArray, 0, copy, 0, aArray.length);

		}
	}

	// @Deprecated
	// private static void copyUsingArraysCopyOf(int[] aArray, int
	// aNumIterations)
	// {
	// for (int idx = 0; idx < aNumIterations; ++idx) {
	//
	// int[] copy = Arrays.copyOf(aArray, aArray.length);
	//
	// }
	// }

	private static void copyUsingForLoop(int[] aArray, int aNumIterations)
	{
		for (int iterIdx = 0; iterIdx < aNumIterations; ++iterIdx)
		{

			int[] copy = new int[aArray.length];
			for (int idx = 0; idx < aArray.length; ++idx)
			{
				copy[idx] = aArray[idx];
			}

		}
	}

	/**
	 * (The for-loop and System.arraycopy styles clearly have independent storage, and are
	 * not exercised in this method.)
	 */
	private static void demoIndependanceOfStorage()
	{
		// a clone of a one-dimensional array has independent storage
		int[] numbers =
		{
				1, 1, 1, 1
		};
		int[] numbersClone = numbers.clone();
		// set 0th element to 0, and compare
		numbersClone[0] = 0;
		logger.debug("Altered clone has NOT affected original:");
		logger.debug("numbersClone[0]: " + numbersClone[0]);
		logger.debug("numbers[0]: " + numbers[0]);

		// the clone of a multi-dimensional array does *not* have
		// independant storage
		int[][] matrix =
		{
				{
						1, 1
				},
				{
						1, 1
				}
		};
		int[][] matrixClone = matrix.clone();
		// set 0-0th element to 0, and compare
		matrixClone[0][0] = 0;
		logger.debug("Altered clone has affected original:");
		logger.debug("matrixClone element 0-0:" + matrixClone[0][0]);
		logger.debug("matrix element 0-0: " + matrix[0][0]);

		// the clone of an array of objects as well is only shallow
		Date[] dates =
		{
			new Date()
		};
		logger.debug("Original date: " + dates[0]);
		Date[] datesClone = dates.clone();
		datesClone[0].setTime(0);
		logger.debug("Altered clone has affected original:");
		logger.debug("datesClone[0]:" + datesClone[0]);
		logger.debug("dates[0]: " + dates[0]);
	}

	public static void main(String... aArguments)
	{
		String action = aArguments[0];
		int numIterations = 0;
		if (aArguments.length == 2)
		{
			numIterations = Integer.parseInt(aArguments[1]);
		}

		if (action.equals("performance"))
		{
			demoPerformance(numIterations);
		}
		else if (action.equals("storage"))
		{
			demoIndependanceOfStorage();
		}
	}
}
