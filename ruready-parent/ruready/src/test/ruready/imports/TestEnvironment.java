/*****************************************************************************************
 * Source File: TestEnvironment.java
 ****************************************************************************************/
package test.ruready.imports;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.misc.Singleton;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A common initialization of testing classes that use the minimal resource
 * locator. This is the same as the stand alone environment, without database
 * access.
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
public class TestEnvironment extends StandAloneEnvironment implements Singleton
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestEnvironment.class);

	/**
	 * Singleton instance of this class.
	 */
	protected static volatile TestEnvironment instance;

	// ========================= FIELDS ====================================

	/**
	 * A context for this environment. Stores some useful attributes.
	 */
	private final ApplicationContext context;

	/**
	 * Readiness state of this locator. It is set to true in
	 * {@link #setUp(String)} and back to false in {@link #tearDown()}.
	 */
	private boolean ready = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This is a singleton. Prevents instantiation.
	 */
	protected TestEnvironment()
	{
		this.context = new WebApplicationContext(resourceLocator.getDAOFactory());
	}

	/**
	 * Return the single instance of this type.
	 * 
	 * @return the single instance of this type
	 */
	public static TestEnvironment getInstance()
	{
		if ((instance == null) || !instance.isReady())
		{
			synchronized (TestEnvironment.class)
			{
				if ((instance == null) || !instance.isReady())
				{
					if (instance == null)
					{
						instance = new TestEnvironment();
					}
					// instance exists but is not ready yet, so set it up.
					instance.setUp();
				}
			}
		}
		return instance;
	}

	// ========================= IMPLEMENTATION: Environment ===============

	/**
	 * Set up: configure and initialize the Hibernate EIS layer (designed to
	 * bypass DAO factory and get sessions directly from the session factory).
	 * 
	 * @see net.ruready.common.rl.StandAloneResource#setUp()
	 */
	@Override
	synchronized public void setUp()
	{
		// Initialize the resource locator
		resourceLocator = TestResourceLocator.getInstance();
		ready = true;

		super.setUp();
	}

	/**
	 * Set up: configure and initialize the Hibernate EIS layer (designed to
	 * bypass DAO factory and get sessions directly from the session factory).
	 * 
	 * @see net.ruready.common.rl.DefaultEnvironment#tearDown()
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

		ready = false;

		// We suppress the RL tear down call and let the JVM clean after us so
		// that we can
		// maintain the test environment across multiple test cases.
		// super.tearDown();
	}

	// ========================= IMPLEMENTATION: StandAloneEnvironment =====

	/**
	 * A useful method to initialize test cases that use this environment.
	 * 
	 * @param initMethod
	 *            runs custom initializations of the test case
	 */
	@Override
	public void initialize(final StandAloneEnvironment.CallBack initMethod)
	{
		logger.info("----------------------");
		logger.info("initialize");
		logger.info("----------------------");

		// Begin unit-of-work of a test case method
		AbstractEISBounder bounder = getDAOFactory();
		bounder.openSession();
		bounder.beginTransaction();

		initMethod.run(this);
	}

	/**
	 * A useful method to clean up test cases that use this environment.
	 * 
	 * @param cleanUpMethod
	 *            custom clean up procedures of the test case
	 */
	@Override
	public void cleanUp(final StandAloneEnvironment.CallBack cleanUpMethod)
	{
		logger.info("----------------------");
		logger.info("cleanUp");
		logger.info("----------------------");
		AbstractEISBounder bounder = getDAOFactory();

		// End unit-of-work of a test case method
		if (bounder.isTransactionActive())
		{
			try
			{
				bounder.commitTransaction();
				bounder.rollbackTransaction();
			}
			catch (RuntimeException e)
			{
				logger.debug("Exception thrown during commit: " + e.toString());
				e.printStackTrace();
				bounder.rollbackTransaction();
			}
			finally
			{
				bounder.closeSession();
			}
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

	/**
	 * Clean up mock objects created in this environment.
	 * 
	 * @see net.ruready.common.rl.DefaultEnvironment#setUp()
	 */
	@Override
	synchronized public void initializeEnvironmentObjects()
	{

		// =======================================
		// Set up mock database objects
		// =======================================

		// ---------------------------------------
		// Initialize system users
		// ---------------------------------------

		AbstractUserBD userBD = new StandAloneUserBD(context);
		SystemUserFactory.initialize(resourceLocator.getEncryptor(), userBD);

		User user = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(context, user);

		// ---------------------------------------
		// Initialize root node
		// ---------------------------------------

		bdMainItem.createUnique(Root.class, CommonNames.MISC.INVALID_VALUE_INTEGER);
		// Prepare root node for processing, including initializing its children
		Item root = bdMainItem.readUnique(Root.class);

		// ---------------------------------------
		// Initialize trash can
		// ---------------------------------------
		bdMainItem.createUnique(DefaultTrash.class, root.getId());

		// ---------------------------------------
		// Initialize tag cabinet
		// ---------------------------------------
		// AbstractMainItemBD bdMainItem = new StrutsMainItemBD(user);
		// bdMainItem.createTagCabinet(root);
	}

	/**
	 * Clean up mock objects created in this environment.
	 * 
	 * @see net.ruready.common.rl.DefaultEnvironment#tearDown()
	 */
	@Override
	synchronized public void cleanUpEnvironmentObjects()
	{
		// =======================================
		// Tear down mock database objects
		// =======================================

		// Note: Root and Trash are read-only, but they are deleted because
		// we need clean up their audit messages.

		User user = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				context, user);
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(context, user);

		// Deleting the trash can if found
		Item trash = bdMainItem.findUnique(DefaultTrash.class);
		if (trash != null)
		{
			bdItem.deleteAll(trash, false);
		}

		// Deleting the root node if found
		Item root = bdMainItem.findUnique(Root.class);
		if (root != null)
		{
			bdItem.deleteAll(root, false);
			// logger.debug(root.getMessages());
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the ready
	 */
	public boolean isReady()
	{
		return ready;
	}

}
