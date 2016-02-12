/*****************************************************************************************
 * Source File: StandAloneEnvironment.java
 ****************************************************************************************/
package net.ruready.business.imports;

import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.DefaultEnvironment;
import net.ruready.eis.factory.imports.HibernateEISManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import test.ruready.imports.StandAloneUserBD;
import test.ruready.rl.StandAloneApplicationContext;

/**
 * A common initialization of testing classes that use the stand-alone resource
 * locator.
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
public class StandAloneEnvironment extends DefaultEnvironment
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StandAloneEnvironment.class);

	// ========================= NESTED TYPES ==============================

	/**
	 * A function pointer for init() and cleanUp() methods.
	 */
	public interface CallBack
	{
		void run(final StandAloneEnvironment env);
	}

	// ========================= FIELDS ====================================

	/**
	 * A context for this environment. Stores some useful attributes.
	 */
	private final StandAloneApplicationContext context;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a stand-alone setup object. Resource locator is not yet
	 * initialized. Call <code>setUp()</code> to initialize it.
	 */
	public StandAloneEnvironment()
	{
		// Initialize the resource locator
		resourceLocator = StandAloneResourceLocator.getInstance();
		this.context = new StandAloneApplicationContext(resourceLocator.getDAOFactory());
	}

	// ========================= IMPLEMENTATION: Environment ===============

	/**
	 * Set up: configure and initialize the Hibernate EIS layer (designed to
	 * bypass DAO factory and get sessions directly from the session factory).
	 */
	synchronized public void setUp()
	{
		// Initialization has its own unit-of-work
		AbstractEISBounder bounder = getDAOFactory();
		bounder.openSession();
		bounder.beginTransaction();

		initializeEnvironmentObjects();

		bounder.commitTransaction();
		bounder.closeSession();
	}

	// ========================= IMPLEMENTATION: ResourceLocator ===========

	/**
	 * Set up: configure and initialize the Hibernate EIS layer (designed to
	 * bypass DAO factory and get sessions directly from the session factory).
	 */
	@Override
	synchronized public void tearDown()
	{
		cleanUp(new StandAloneEnvironment.CallBack()
		{
			public void run(StandAloneEnvironment env)
			{
				env.cleanUpEnvironmentObjects();
			}
		});

		// We suppress the RL tear down call and let the JVM clean after us so
		// that we can maintain the test RL across multiple test cases.
		// super.tearDown();
	}

	// ========================= METHODS ===================================

	/**
	 * Set up: configure and initialize the Hibernate EIS layer (designed to
	 * bypass DAO factory and get sessions directly from the session factory).
	 */
	public Session getSession()
	{
		return ((HibernateEISManager) resourceLocator.getDAOFactory()).getSession();
	}

	/**
	 * A useful method to initialize test cases that use this environment.
	 * 
	 * @param initMethod
	 *            runs custom initializations of the test case
	 */
	public void initialize(final StandAloneEnvironment.CallBack initMethod)
	{
		logger.info("----------------------");
		logger.info("initialize");
		logger.info("----------------------");

		// Initialization has its own unit-of-work
		AbstractEISBounder bounder = getDAOFactory();
		bounder.openSession();
		bounder.beginTransaction();

		initMethod.run(this);

		bounder.commitTransaction();
		bounder.closeSession();
	}

	/**
	 * A useful method to clean up test cases that use this environment.
	 * 
	 * @param cleanUpMethod
	 *            custom clean up procedures of the test case
	 */
	public void cleanUp(final StandAloneEnvironment.CallBack cleanUpMethod)
	{
		logger.info("----------------------");
		logger.info("cleanUp");
		logger.info("----------------------");
		AbstractEISBounder bounder = getDAOFactory();

		// Clean up a previous session if it is open
		try
		{
			bounder.rollbackTransaction();
			bounder.closeSession();
		}
		catch (Exception e)
		{
			logger.debug("Exception thrown during roll-back: " + e.toString());
			e.printStackTrace();
		}

		// ===================
		// Clean-up
		// ===================
		// Has its own unit-of-work
		try
		{
			bounder.openSession();
			bounder.beginTransaction();

			cleanUpMethod.run(this);

			bounder.commitTransaction();
		}
		catch (RuntimeException e)
		{
			logger.debug("Exception thrown during cleanUp: " + e.toString());
			e.printStackTrace();
			bounder.rollbackTransaction();
		}
		finally
		{
			bounder.closeSession();
		}
	}

	// ========================= STUBS =====================================

	/**
	 * Clean up mock objects created in this environment.
	 * 
	 * @see net.ruready.common.rl.DefaultEnvironment#setUp()
	 */
	synchronized public void initializeEnvironmentObjects()
	{
		// Initialize system users
		AbstractUserBD userBD = new StandAloneUserBD(context);
		SystemUserFactory.initialize(resourceLocator.getEncryptor(), userBD);
	}

	/**
	 * Clean up mock objects created in this environment.
	 * 
	 * @see net.ruready.common.rl.DefaultEnvironment#tearDown()
	 */
	synchronized public void cleanUpEnvironmentObjects()
	{

	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the context
	 */
	public StandAloneApplicationContext getContext()
	{
		return context;
	}

}
