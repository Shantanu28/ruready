/*****************************************************************************************
 * Source File: TestFileReader.java
 ****************************************************************************************/
package test.ruready.rl;

import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.common.junit.entity.TestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import test.ruready.common.rl.TestingNames;
import test.ruready.imports.TestEnvironment;

/**
 * Custom test case used throughout our applications.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 30, 2007
 */
public abstract class TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestEnvTestBase.class);

	// ========================= FIELDS ====================================

	/**
	 * An environment with a resource locator that initializes and manages the
	 * testing database properly.
	 */
	protected static StandAloneEnvironment environment;

	// ========================= CONSTRUCTORS ==============================

	// ========================= STATIC METHODS ============================

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception
	{
		// Load the default log4j configuration. May be overridden by
		// sub-classes in constructors / setUp methods.
		TestUtil
				.loadLoggingConfiguration(TestingNames.DEFAULT_RESOURCE_LOCATOR.PROPERTY_LOG4J_CONFIG_FILE);

		// Initialize the resource locator
		environment = TestEnvironment.getInstance();
		environment.setUp();
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDown() throws Exception
	{
		// Tear down the resource locator
		environment.tearDown();
	}

	// ========================= METHODS ===================================

	/**
	 * Set up: configure and initialize the Hibernate EIS layer (designed to
	 * bypass DAO factory and get sessions directly from the session factory).
	 */
	@Before
	synchronized public void initializeTest()
	{
		environment.initialize(new StandAloneEnvironment.CallBack()
		{
			public void run(@SuppressWarnings("unused")
			StandAloneEnvironment env)
			{
				initialize();
			}
		});
	}

	/**
	 * Set up: configure and initialize the Hibernate EIS layer (designed to
	 * bypass DAO factory and get sessions directly from the session factory).
	 */
	@After
	synchronized public void cleanUpTest()
	{
		environment.cleanUp(new StandAloneEnvironment.CallBack()
		{
			public void run(@SuppressWarnings("unused")
			StandAloneEnvironment env)
			{
				cleanUp();
			}
		});
	}

	// ========================= STUBS =====================================

	/**
	 * Initialize in preparation for a test method.
	 * 
	 * @see net.ruready.common.rl.DefaultEnvironment#setUp()
	 */
	synchronized protected void initialize()
	{

	}

	/**
	 * Clean up after a test method.
	 * 
	 * @see net.ruready.common.rl.DefaultEnvironment#tearDown()
	 */
	synchronized protected void cleanUp()
	{

	}
}
