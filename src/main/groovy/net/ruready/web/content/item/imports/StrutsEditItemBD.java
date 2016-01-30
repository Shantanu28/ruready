/*****************************************************************************************
 * Source File: StrutsEditItemBD.java
 ****************************************************************************************/
package net.ruready.web.content.item.imports;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.DefaultEditItemBD;
import net.ruready.business.content.item.manager.DefaultEditItemManager;
import net.ruready.business.user.entity.User;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An edit item BD interface with a Struts resource locator. Methods also allow a specific
 * user to request the DAO operations on items.
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
 * @version Aug 8, 2007
 */
public class StrutsEditItemBD<B extends Item> extends DefaultEditItemBD<B>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsEditItemBD.class);

	// ========================= FIELDS ====================================

	/**
	 * The user requesting the item operations.
	 */
	private final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a business delegate for this user.
	 * 
	 * @param user
	 *            user requesting the operations
	 * @param context
	 *            web application context
	 */
	public StrutsEditItemBD(final Class<B> baseClass, final ApplicationContext context,
			final User user)
	{
		super(new DefaultEditItemManager<B>(baseClass, WebAppResourceLocator
				.getInstance(), context, user), WebAppResourceLocator.getInstance());
		this.user = user;
	}

	// ========================= IMPLEMENTATION: DefaultEditItemBD =========

	/**
	 * Throws an unsupported operation exception. Although the generic edit item manager
	 * supports deleting items (physically), use instead
	 * <code>AbstractTrashManager.delete()</code> for a soft delete that moves the
	 * deleted item under the trash.
	 * 
	 * @param item
	 * @param respectLocks
	 * @throws ApplicationException
	 *             (always)
	 * @see net.ruready.business.content.item.exports.DefaultEditItemBD#delete(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	@Override
	public void delete(final Item item, final boolean respectLocks)
	{
		throw new ApplicationException(
				"Unsupported operation. Deferred to AbstractTrashManager.");
	}

	// ========================= overridden METHODS: DefaultEditItemBD ======

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @see net.ruready.business.content.catalog.exports.AbstractItemUtilManager#getUser()
	 */
	public User getUser()
	{
		return user;
	}
}
