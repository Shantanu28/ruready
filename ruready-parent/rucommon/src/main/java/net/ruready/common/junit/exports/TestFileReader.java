/*******************************************************************************
 * Source File: TestFileReader.java
 ******************************************************************************/
package net.ruready.common.junit.exports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.junit.entity.LineID;
import net.ruready.common.junit.entity.LineReaderTarget;
import net.ruready.common.junit.manager.LineReaderMatcher;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A template for reading and processing test strings from a text file.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jan 30, 2007
 */
public abstract class TestFileReader
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestFileReader.class);

	// ========================= FIELDS ====================================

	// File name to read tests from
	private String fileName;

	// Convenient local variables
	private BufferedReader reader;

	// Output variables
	private int numLinesRead;

	private int numErrors;

	// target object; holds parameters nad the evaluated string of each
	// processed line in the test data file
	private LineReaderTarget target = new LineReaderTarget();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a test file reader.
	 * 
	 * @param fileName
	 *            file name to read tests from
	 */
	public TestFileReader(String fileName) throws ApplicationException
	{
		String baseDir = System.getProperty(CommonNames.JUNIT.ENVIRONMENT.BASE_DIR);
		this.fileName = (baseDir != null) ? baseDir + File.separator + fileName
				: fileName;

		// Set parameter default values
		this.setStopOnFirstError(false);

		// Try to open the file
		try {
			reader = new BufferedReader(new FileReader(this.fileName));
			logger.info("Reading tests from " + this.fileName);
		}
		catch (Exception e) {
			logger.error("Test file '" + this.fileName + "' could not be opened");
			throw new ApplicationException("Test file '" + this.fileName
					+ "' could not be opened; working directory is "
					+ System.getProperty("user.dir") + "; baseDir is " + baseDir);
		}

	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Parse a string containing a single test's input and the test's expected
	 * output, and compare them.
	 * 
	 * @param inputStr
	 *            a string containing a single test's input
	 * @param outputStr
	 *            a string containing the test's expected output
	 * @return expected test result == actual test result
	 */
	abstract protected boolean assertEquals(String inputStr, String outputStr);

	/**
	 * A hook to set up a test case (just like {@link TestCase#setUp()}.
	 */
	protected void setUp()
	{

	}

	/**
	 * A hook to tear down a test case (just like {@link TestCase#tearDown()}.
	 */
	protected void tearDown()
	{

	}

	/**
	 * Process a comment.
	 * 
	 * @param line
	 *            comment line string
	 */
	protected void processComment(String line)
	{

	}

	// ========================= PUBLIC METHODS ============================

	/**
	 * A template method. Run the test: read a pair of lines from the test file
	 * (first line: test input; second line: expected output), and compare the
	 * results.
	 */
	public final void test()
	{
		// set up
		this.setUp();

		// reset counteres
		numLinesRead = 0;
		numErrors = 0;

		// Loop through the expressions in pairs
		logger.debug("Reading and comparing expressions...");
		while (true) {
			boolean hasError = false;
			String inputStr = null;
			String outputStr = null;
			try {
				// Read a pair of lines
				inputStr = getNextLine(); // returns null when end of file is
				// reached
				outputStr = getNextLine();

				// Either line may be null when the end of file is reached
				if ((inputStr == null) || (outputStr == null)) {
					// println("Reached end of file.");
					break;
				}

				// compare the results
				if (!assertEquals(inputStr, outputStr)) {
					hasError = true;
				}
			}
			catch (Exception e) {
				hasError = true;
				logger.error(e.getMessage());
				e.printStackTrace(System.err);
			}
			if (hasError) {
				numErrors++;
				logger.error("Line " + (numLinesRead - 2) + ": input " + inputStr
						+ " output " + outputStr);
				if (isStopOnFirstError()) {
					break;
				}
			}
		}

		// tear down
		this.tearDown();

		// Closing remarks
		if (numErrors == 0) {
			logger.info(numLinesRead + " line(s) processed. " + numErrors
					+ " error(s) found.");
		}
		else {
			logger.error(numLinesRead + " line(s) processed. " + numErrors
					+ " error(s) found.");

		}
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Parses a single line from the reader and trim it.
	 * 
	 * @return line string representation. Returns <code>null</code> when the
	 *         end of the file is reached.
	 * @throws Exception
	 *             when IOException occurs, parsing fails, or when evaluation
	 *             fails
	 */
	private String getNextLine() throws IOException
	{
		String line;

		// Cycle till a valid line is found
		boolean validLine = false;
		do {
			// Read and trim a line
			line = reader.readLine(); // returns null on end of file
			if (line == null) {
				return null;
			}
			line = line.trim();
			numLinesRead++;

			// Parse the line
			LineReaderMatcher matcher = new LineReaderMatcher(target);
			matcher.match(line);
			// Target might have been cloned so need to update the reference
			target = matcher.getTarget();
			// logger.debug("Parsed line " + numLinesRead + ": match? "
			// + matcher.isCompleteMatch() + " line type "
			// + target.getLineID() + " text " + line);
			if (matcher.isCompleteMatch()) {
				if (target.getLineID() == LineID.DATA) {
					// Data line, return it for processing
					validLine = true;
					// line = target.getEvalString().toString();
				}
			}
			else {
				logger.debug("Ignoring line " + numLinesRead + " - incomplete parsing: "
						+ line);
			}
		}
		while (!validLine);
		return line;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @return the numErrors
	 */
	public int getNumErrors()
	{
		return numErrors;
	}

	/**
	 * @return the numLinesRead
	 */
	public int getNumLinesRead()
	{
		return numLinesRead;
	}

	/**
	 * @return
	 * @see net.ruready.common.junit.entity.LineReaderTarget#isStopOnFirstError()
	 */
	public boolean isStopOnFirstError()
	{
		return target.isStopOnFirstError();
	}

	/**
	 * @param stopOnFirstError
	 * @see net.ruready.common.junit.entity.LineReaderTarget#setStopOnFirstError(boolean)
	 */
	public void setStopOnFirstError(boolean stopOnFirstError)
	{
		target.setStopOnFirstError(stopOnFirstError);
	}
}
