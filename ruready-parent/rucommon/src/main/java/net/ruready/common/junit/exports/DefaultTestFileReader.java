/*****************************************************************************************
 * Source File: DefaultTestFileReader.java
 ****************************************************************************************/
package net.ruready.common.junit.exports;

import net.ruready.common.exception.ApplicationException;
import net.ruready.common.junit.entity.TestInput;
import net.ruready.common.junit.entity.TestOutput;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default implementation of a test suite loaded from a data file.
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
 * @version Sep 8, 2007
 */
public abstract class DefaultTestFileReader extends TestFileReader
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultTestFileReader.class);

	// ========================= FIELDS ====================================

	// Holds the test's inputs; constructed during parsing an input string from
	// the data file. Set in the abstract hooks.
	protected TestInput testInput;

	// Variables holding the actual and expected test results.
	// Set in the abstract hooks.
	protected TestOutput actualTestOutput;

	protected TestOutput expectedTestOutput;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a test file reader.
	 * 
	 * @param fileName
	 *            file name to read tests from
	 */
	public DefaultTestFileReader(String fileName) throws ApplicationException
	{
		super(fileName);
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Parse input string into test inputs, which are stored in instance variables.
	 * 
	 * @param inputStr
	 *            a single test's input string from data file
	 */
	protected abstract void parseInputString(String inputStr);

	/**
	 * Parse output string into expected results, which are stored in instance variables.
	 * 
	 * @param outputStr
	 *            a single test's output string from data file
	 */
	protected abstract void parseOutputString(String outputStr);

	/**
	 * Print a test result in the format that parseOutput reads from the data file.
	 * 
	 * @param result
	 *            test result object
	 * @return string representation in the data file's format
	 */
	protected abstract String encodeTestOutput(TestOutput testOutput);

	/**
	 * Run a single test after the test input instance field has been set.
	 * 
	 * @param aTestInput
	 *            test input data struct
	 */
	protected abstract void runTest(TestInput aTestInput);

	// ========================= IMPLEMENTATION: TestFileReader ============

	/**
	 * A template method for processing a test case. Parses the input and output strings,
	 * runs the test, and compares the expected and actual test results.
	 * 
	 * @see net.ruready.common.test.SimpleDDLFileFixer#Assert.assertEquals(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	final protected boolean assertEquals(String inputStr, String outputStr)
	{
		logger.debug("############## Processing: inputStr " + inputStr + " output "
				+ outputStr + " ##############");

		// Parse input string
		this.parseInputString(inputStr);

		// Parse output string
		this.parseOutputString(outputStr);

		// Run test and get actual results
		this.runTest(testInput);

		// Compare results
		return this.compareExpectedActual();
	}

	// ========================= PUBLIC METHODS ============================

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Compare expected and actual test results.
	 * 
	 * @return expected results == actual results
	 */
	private boolean compareExpectedActual()
	{
		// Useful to insert new tests
		logger.debug("line " + this.getNumLinesRead() + " result:"
				+ CommonNames.MISC.NEW_LINE_CHAR + "expected "
				+ this.encodeTestOutput(expectedTestOutput)
				+ CommonNames.MISC.NEW_LINE_CHAR + "actual   "
				+ this.encodeTestOutput(actualTestOutput));

		// Negative checks
		if (!expectedTestOutput.equals(actualTestOutput))
		{
			logger.error("line " + this.getNumLinesRead() + " failure; result:"
					+ CommonNames.MISC.NEW_LINE_CHAR + "expected "
					+ this.encodeTestOutput(expectedTestOutput)
					+ CommonNames.MISC.NEW_LINE_CHAR + "actual   "
					+ this.encodeTestOutput(actualTestOutput));
			return false;
		}
		return true;
	}

	// ========================= GETTERS & SETTERS =========================

}
