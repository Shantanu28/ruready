/*******************************************************************************
 * Source File: TestingNames.java
 ******************************************************************************/
package test.ruready.common.rl;

import java.io.File;

/**
 * This interface centralizes constants, labels and names used throughout the
 * testing component.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jan 31, 2007
 */
public interface TestingNames
{
	// ========================= CONSTANTS =================================

	// If no trailing slash is attached, file names are relative to the
	// "classes" directory of the application.

	// -----------------------------------------------------------------------
	// Default values for resources
	// -----------------------------------------------------------------------
	public interface DEFAULT_RESOURCE_LOCATOR
	{
		// For all tests that don't use a resource locator but use a logger
		static final String PROPERTY_LOG4J_CONFIG_FILE = "./log4j.properties";
	}

	// -----------------------------------------------------------------------
	// Chain pattern
	// -----------------------------------------------------------------------
	public interface CHAIN
	{

		// -----------------------------------------------------------------------
		// Chain request name conventions
		// -----------------------------------------------------------------------
		public interface REQUEST
		{

			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Request-scope attribute name conventions
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			public interface ATTRIBUTE
			{

				// Purchasing amount in the company CoR test
				static final String AMOUNT = "amount";

				// Who approved the purchasing amount
				static final String APPROVED = "approved";
			}

			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Error message attribute names
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			public interface ERROR
			{
				// TODO: change these examples to attributes used in the parser
				// code
			}
		}
	}

	// -----------------------------------------------------------------------
	// Test data file names
	// -----------------------------------------------------------------------
	public interface FILE
	{
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Directory names
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// test home directory; relative to project directory
		public interface DIR
		{
			static final String TEST = "data" + File.separator + "test";

			// ============================
			// Parser Component
			// ============================
			// Parser test suite directory structure
			public interface COMMON
			{
				static final String COMMON = TEST + File.separator + "common";

				// Directories of each parser module tests; relative to project
				// directory

				// RT test data file parser
				static final String JUNIT = COMMON + File.separator + "junit";
			} // interface DIR.COMMON

		} // interface DIR

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Test data file names
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		// ============================
		// Common Component
		// ============================
		public interface COMMON
		{
			// RT test data file parser
			public interface JUNIT
			{
				// Arithmetic matching data file name
				static final String LINE_READER = DIR.COMMON.JUNIT
						+ File.separator + "LineReader.dat";
			}
		} // interface FILE.COMMON

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Test file reader parameter name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface PARAMS
		{
			// Stop on first error found in the file or not; boolean
			static final String STOP_ON_FIRST_ERROR = "stopOnFirstError";
		}
	} // interface FILE

}
