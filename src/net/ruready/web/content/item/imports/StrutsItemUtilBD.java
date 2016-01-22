/*****************************************************************************************
 * Source File: StrutsItemUtilBD.java
 ****************************************************************************************/
package net.ruready.web.content.item.imports;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.manager.DefaultEditItemManager;
import net.ruready.business.content.main.manager.DefaultMainItemManager;
import net.ruready.business.content.util.exports.DefaultItemUtilBD;
import net.ruready.business.content.util.manager.DefaultItemUtilManager;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A mainItem BD interface with a Struts resource locator. Methods also allow a specific
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
 * @version Aug 11, 2007
 */
public class StrutsItemUtilBD extends DefaultItemUtilBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsItemUtilBD.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a business delegate for this user.
	 * 
	 * @param user
	 *            user requesting the operations
	 * @param context
	 *            web application context
	 */
	public StrutsItemUtilBD(final ApplicationContext context, final User user)
	{
		super(new DefaultItemUtilManager(WebAppResourceLocator.getInstance(), context,
				user, new DefaultEditItemManager<Item>(Item.class, WebAppResourceLocator
						.getInstance(), context, user), new DefaultMainItemManager(
						WebAppResourceLocator.getInstance(), context, user)),
				WebAppResourceLocator.getInstance());

	}

	// ========================= IMPLEMENTATION: DefaultItemUtilBD =======

	// ========================= GETTERS & SETTERS ==========================
}
