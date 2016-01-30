/*****************************************************************************************
 * Source File: StrutsUserBD.java
 ****************************************************************************************/
package test.ruready.imports;

import net.ruready.business.user.exports.DefaultUserGroupBD;
import net.ruready.business.user.manager.DefaultUserGroupManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.ruready.rl.StandAloneApplicationContext;

/**
 * A user BD interface with a stand-alone resource locator.
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
public class StandAloneUserGroupBD extends DefaultUserGroupBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StandAloneUserGroupBD.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a user group utility business delegate for a stand alone environment.
	 * 
	 * @param context
	 *            stand-alone application context
	 */
	public StandAloneUserGroupBD(final StandAloneApplicationContext context)
	{
		super(new DefaultUserGroupManager(TestResourceLocator.getInstance(), context),
				TestResourceLocator.getInstance());
	}
}
