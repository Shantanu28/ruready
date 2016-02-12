/*****************************************************************************************
 * Source File: DefaultEditItemBD.java
 ****************************************************************************************/
package net.ruready.common.eis.exports;

import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.rl.ResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An edit item BD that implements the EIS general manager interface. It relies on a
 * static hook to instantiate a specific manager implementation and a resource locator.
 * This type cannot be instantiated.
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
 * @version Jul 31, 2007
 */
public abstract class DefaultEISBD implements AbstractEISBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultEISBD.class);

	// ========================= FIELDS ====================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractEISManager manager;

	/**
	 * Using this specific resource locator.
	 */
	protected final ResourceLocator resourceLocator;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize this BD. This is a template-style constructor, because the manager
	 * instance depends on the resourceLocator instance.
	 * 
	 * @param managerClass
	 *            wrapped manager type
	 * @param resourceLocator
	 *            using this specific resource locator
	 */
	protected DefaultEISBD(final AbstractEISManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.resourceLocator = resourceLocator;
		this.manager = manager;
	}

	// ========================= IMPLEMENTATION: AbstractEISManager ====

	/**
	 * @param proxy
	 * @return
	 * @see net.ruready.common.eis.manager.AbstractEISManager#isInitialized(java.lang.Object)
	 */
	public boolean isInitialized(Object proxy)
	{
		return manager.isInitialized(proxy);
	}

	// ========================= PRIVATE METHODS ===========================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the manager
	 */
	public AbstractEISManager getManager()
	{
		return manager;
	}

}
