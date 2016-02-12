/*******************************************************************************
 * Source File: TestUtil.java
 ******************************************************************************/
package net.ruready.common.junit.entity;

import java.net.URL;

import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 * Utilities related to JUnit test cases.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 *         <br>(c) 2006-07 Continuing Education , University of Utah . All
 *         copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 26, 2007
 */
public class TestUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private TestUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Load a Log4J configuration from a file.
	 * 
	 * @param configFileName
	 *            Log4J configuration file name
	 */
	public static void loadLoggingConfiguration(final String configFileName)
	{
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				configFileName);
		PropertyConfigurator.configure(url);
		// logger.debug("Log test");
	}

}
