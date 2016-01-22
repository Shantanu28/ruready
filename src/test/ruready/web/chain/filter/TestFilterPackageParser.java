/*****************************************************************************************
 * Source File: TestUrlPattern.java
 ****************************************************************************************/
package test.ruready.web.chain.filter;

import java.io.File;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.chain.filter.FilterPackageParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests parsing an XML ini file of the Struts filter Framework.
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
 * @version Aug 13, 2007
 */
public class TestFilterPackageParser extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestFilterPackageParser.class);

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test parsing an example file #1.
	 */
	@Test
	public void testParseFile1() throws Exception
	{
		FilterPackageParser parser = new FilterPackageParser();
		String baseDir = System.getProperty(CommonNames.JUNIT.ENVIRONMENT.BASE_DIR);
		String fileName = "data/test/web/filter/struts-filter1.xml";
		String absoluteFileName = (baseDir != null) ? baseDir + File.separator + fileName
				: fileName;
		parser.parse(absoluteFileName);

		logger.debug(parser.getTarget());
	}

	/**
	 * Test parsing an example file #2.
	 */
	@Test
	public void testParseFile2() throws Exception
	{
		FilterPackageParser parser = new FilterPackageParser();
		String baseDir = System.getProperty(CommonNames.JUNIT.ENVIRONMENT.BASE_DIR);
		String fileName = "data/test/web/filter/struts-filter2.xml";
		String absoluteFileName = (baseDir != null) ? baseDir + File.separator + fileName
				: fileName;
		parser.parse(absoluteFileName);

		logger.debug(parser.getTarget());
	}

	/**
	 * Test parsing an example file #3.
	 */
	@Test
	public void testParseFile3() throws Exception
	{
		FilterPackageParser parser = new FilterPackageParser();
		String baseDir = System.getProperty(CommonNames.JUNIT.ENVIRONMENT.BASE_DIR);
		String fileName = "data/test/web/filter/struts-filter3.xml";
		String absoluteFileName = (baseDir != null) ? baseDir + File.separator + fileName
				: fileName;
		parser.parse(absoluteFileName);

		logger.debug(parser.getTarget());
	}

	/**
	 * Test parsing an example file #4.
	 */
	@Test
	public void testParseFile4() throws Exception
	{
		FilterPackageParser parser = new FilterPackageParser();
		String baseDir = System.getProperty(CommonNames.JUNIT.ENVIRONMENT.BASE_DIR);
		String fileName = "data/test/web/filter/struts-filter4.xml";
		String absoluteFileName = (baseDir != null) ? baseDir + File.separator + fileName
				: fileName;
		parser.parse(absoluteFileName);

		logger.debug(parser.getTarget());
	}
}
