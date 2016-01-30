/*****************************************************************************************
 * Source File: DefaultGlobalPropertyManager.java
 ****************************************************************************************/
package net.ruready.business.user.manager;

import net.ruready.business.user.entity.Counter;
import net.ruready.business.user.entity.GlobalProperty;
import net.ruready.business.user.entity.audit.HitMessage;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This service allows counter manipulation: creating a new counter, updating an
 * existing counter, deleting an counter, and listing counters by certain
 * criteria.
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
 * @version Aug 11, 2007
 */
public class DefaultGlobalPropertyManager implements AbstractGlobalPropertyManager
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(DefaultGlobalPropertyManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs.
	 */
	protected final ResourceLocator resourceLocator;

	/**
	 * DAO factory.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects and other useful properties.
	 */
	protected final ApplicationContext context;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to
	 * read/write counters to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            web application context
	 */
	public DefaultGlobalPropertyManager(final ResourceLocator resourceLocator,
			final ApplicationContext context)

	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
	}

	// ========================= IMPLEMENTATION: AbstractGPManager =========

	/**
	 * @see net.ruready.business.user.manager.AbstractGlobalPropertyManager#load(java.lang.String)
	 */
	public Counter load(String name)
	{
		// if (logger.isDebugEnabled())
		// {
		// logger.debug("Loading counter '" + name + "'");
		// }
		DAO<Counter, Long> counterDAO = eisManager.getDAO(Counter.class, context);
		Counter counter = counterDAO.findByUniqueProperty(GlobalProperty.NAME, name);
		if (counter == null)
		{
			// Counter doesn't yet exist in the database, create a new one
			counter = new Counter(name);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Loaded counter '" + counter.getName() + "' = "
					+ counter.getValue());
		}
		return counter;
	}

	/**
	 * @see net.ruready.counter.manager.AbstractGlobalPropertyManager#decrement(java.lang.String)
	 */
	public Counter decrement(String name)
	{
		logger.debug("Decrementing counter " + name);
		DAO<Counter, Long> counterDAO = eisManager.getDAO(Counter.class, context);
		Counter counter = counterDAO.findByUniqueProperty(GlobalProperty.NAME, name);
		if (counter == null)
		{
			// Counter doesn't yet exist in the database, create a new one
			counter = new Counter(name);
		}
		else
		{
			counter.increment();
		}
		counterDAO.update(counter);
		logger.debug("After update: " + counter.getName() + " " + counter.getValue());
		return counter;
	}

	/**
	 * @see net.ruready.counter.manager.AbstractGlobalPropertyManager#increment(java.lang.String)
	 */
	public Counter increment(String name)
	{
		logger.debug("Incrementing counter " + name);
		DAO<Counter, Long> counterDAO = eisManager.getDAO(Counter.class, context);
		Counter counter = counterDAO.findByUniqueProperty(GlobalProperty.NAME, name);
		if (counter == null)
		{
			// Counter doesn't yet exist in the database, create a new one
			counter = new Counter(name);
		}
		else
		{
			counter.increment();
		}
		counterDAO.update(counter);
		logger.debug("After update: " + counter.getName() + " " + counter.getValue());
		return counter;
	}

	/**
	 * Save a new hit message to the database.
	 * 
	 * @param hitMessage
	 *            hit message to be saved
	 * 
	 */
	public void saveHitMessage(HitMessage hitMessage)
	{
		logger.debug("Saving " + hitMessage);
		DAO<HitMessage, Long> hitMessageDAO = eisManager
				.getDAO(HitMessage.class, context);
		hitMessageDAO.update(hitMessage);
	}
}
