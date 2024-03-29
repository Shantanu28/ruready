/*****************************************************************************************
 * Source File: StrutsTrashBD.java
 ****************************************************************************************/
package net.ruready.web.content.item.imports;

import net.ruready.business.content.trash.exports.DefaultTrashBD;
import net.ruready.business.content.trash.manager.DefaultTrashManager;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An trash manager BD with a stand-alone resource locator. Methods also allow a specific
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
public class StrutsTrashBD extends DefaultTrashBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsTrashBD.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a business delegate for this user.
	 * 
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 */
	public StrutsTrashBD(final ApplicationContext context, final User user)
	{
		// Initialize references with concrete types
		super(
				new DefaultTrashManager(WebAppResourceLocator.getInstance(), context,
						user), WebAppResourceLocator.getInstance());
	}

	// ========================= overridden METHODS: DefaultTrashBD =========

	// ========================= GETTERS & SETTERS ==========================
}
