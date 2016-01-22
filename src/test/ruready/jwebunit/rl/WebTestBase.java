/*****************************************************************************************
 * Source File: WebTestLogin.java
 ****************************************************************************************/

package test.ruready.jwebunit.rl;

import net.ruready.common.junit.entity.TestUtil;
import net.ruready.common.rl.Environment;
import net.ruready.web.common.rl.WebAppNames;
import net.sourceforge.jwebunit.junit.WebTestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.ruready.common.rl.TestingNames;

/**
 * A base class for web test cases.
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
 * @version Oct 13, 2007
 */
public abstract class WebTestBase extends WebTestCase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(WebTestBase.class);

	/**
	 * Simulated Firefox browser user agent.
	 */
	public static final String FIREFOX_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.7) Gecko/20070914 Firefox/2.0.0.7";

	// ========================= FIELDS ====================================

	/**
	 * An environment with a resource locator that initializes and manages the
	 * testing database properly.
	 */
	protected final Environment environment;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * 
	 */
	public WebTestBase()
	{
		super();
		initForConstructors();
		environment = setEnvironment();
	}

	/**
	 * @param name
	 */
	public WebTestBase(String name)
	{
		super(name);
		initForConstructors();
		environment = setEnvironment();
	}

	// ========================= IMPLEMENTATION: TestCase ==================

	/**
	 * @throws Exception
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected final void setUp() throws Exception
	{
		// Initialize the resource locator
		environment.setUp();

		// Set base URL
		String projectName = environment.getProperty(
				WebAppNames.RESOURCE_LOCATOR.PROPERTY.PROJECT_NAME,
				WebAppNames.DEFAULT_PROJECT_NAME);
		String baseUrl = "http://localhost:8080/" + projectName;
		logger.debug("Base URL: " + baseUrl);
		getTestContext().setBaseUrl(baseUrl);

		// Initialize i18n resources
		getTestContext().setResourceBundleName("ApplicationResources");
		String userAgent = FIREFOX_USER_AGENT;
		getTestContext().setUserAgent(userAgent);

		// Custom initializations
		initialize();
	}

	/**
	 * @throws Exception
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected final void tearDown() throws Exception
	{
		// Custom clean-ups
		cleanUp();

		environment.tearDown();
	}

	// ========================= HOOKS =====================================

	/**
	 * Set and initialize the testing environment. Called in all constructors.
	 * Must return a non-<code>null</code> object.
	 */
	abstract protected Environment setEnvironment();

	/**
	 * Custom initializations of the test case. Called before every test method.
	 */
	protected void initialize()
	{
		// This is a stub.
	}

	/**
	 * Custom clean-ups of the test case. Called after every test method.
	 */
	protected void cleanUp()
	{
		// This is a stub.
	}

	// ========================= METHODS ===================================

	/**
	 * Return the default log4j configuration file name.
	 * 
	 * @return the default log4j configuration file name
	 */
	protected String getDefaultLoggingConfigFileName()
	{
		return TestingNames.DEFAULT_RESOURCE_LOCATOR.PROPERTY_LOG4J_CONFIG_FILE;
	}

	/**
	 * Common initialization procedure for constructors.
	 */
	private void initForConstructors()
	{
		// Load the default log4j configuration. May be overridden by
		// sub-classes in constructors / setUp methods.
		TestUtil.loadLoggingConfiguration(getDefaultLoggingConfigFileName());
		// setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
	}

}
