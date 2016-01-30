/*****************************************************************************************
 * Source File: DefaultGlobalPropertyBD.java
 ****************************************************************************************/
package net.ruready.business.user.exports;

import net.ruready.business.user.entity.Counter;
import net.ruready.business.user.entity.audit.HitMessage;
import net.ruready.business.user.manager.AbstractGlobalPropertyManager;
import net.ruready.common.rl.ResourceLocator;

/**
 * A singleton that implements the user BD interface, and relies on a static hook to
 * instantiate a specific manager implementation and a resource locator.
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
 * @version Jul 21, 2007
 */
public abstract class DefaultGlobalPropertyBD implements AbstractGlobalPropertyBD
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractGlobalPropertyManager manager;

	/**
	 * Use this specific resource locator.
	 */
	protected final ResourceLocator resourceLocator;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize this BD from fields.
	 * 
	 * @param manager
	 *            wrapped manager
	 * @param resourceLocator
	 *            using this specific resource locator
	 */
	protected DefaultGlobalPropertyBD(final AbstractGlobalPropertyManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractGlobalPropertyBD =====

	/**
	 * @see net.ruready.business.user.manager.AbstractGlobalPropertyManager#load(java.lang.String)
	 */
	public Counter load(String name)
	{
		return manager.load(name);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractGlobalPropertyManager#decrement(java.lang.String)
	 */
	public Counter decrement(String name)
	{
		return manager.decrement(name);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractGlobalPropertyManager#increment(java.lang.String)
	 */
	public Counter increment(String name)
	{
		return manager.increment(name);
	}

	/**
	 * @see net.ruready.business.user.manager.AbstractGlobalPropertyManager#saveHitMessage(net.ruready.business.user.entity.audit.HitMessage)
	 */
	public void saveHitMessage(HitMessage hitMessage)
	{
		manager.saveHitMessage(hitMessage);
	}

	// ========================= GETTERS & SETTERS =========================
}
