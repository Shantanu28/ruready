/*****************************************************************************************
 * Source File: StrutsUserBD.java
 ****************************************************************************************/
package net.ruready.web.user.imports;

import net.ruready.business.user.exports.DefaultUserBD;
import net.ruready.business.user.manager.DefaultUserManager;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A user BD interface with a Struts resource locator.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 2, 2007
 */
public class StrutsUserBD extends DefaultUserBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsUserBD.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a user utility business delegate.
	 * 
	 * @param context
	 *            web application context
	 */
	public StrutsUserBD(final ApplicationContext context)
	{
		super(new DefaultUserManager(WebAppResourceLocator.getInstance(), context),
				WebAppResourceLocator.getInstance());
	}
}
