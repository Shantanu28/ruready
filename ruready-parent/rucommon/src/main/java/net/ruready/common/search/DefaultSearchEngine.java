/*****************************************************************************************
 * Source File: DefaultSearchEngine.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.io.Serializable;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.rl.ResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default implementation of a database search engine by criteria.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 12, 2007
 */
public abstract class DefaultSearchEngine<E extends PersistentEntity<ID>, ID extends Serializable>
		implements SearchEngine<E>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultSearchEngine.class);

	// ========================= FIELDS ===== ==============================

	/**
	 * Locator of the DAO factory that creates DAOs for Items.
	 */
	protected final ResourceLocator resourceLocator;

	/**
	 * Retrieved from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a search engine that will use a resource locator and a DAO factory
	 * to search items in the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 */
	public DefaultSearchEngine(final ResourceLocator resourceLocator)

	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
	}

	// ========================= IMPLEMENTATION: SearchCriteria ============

	// ========================= ABSTRACT METHODS ==========================
}
