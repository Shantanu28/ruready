/*****************************************************************************************
 * Source File: PopulateSubTopicMenuGroupAction.java
 ****************************************************************************************/
package net.ruready.web.content.question.action;

import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.content.question.form.SubTopicMenuGroup;
import net.ruready.web.menugroup.action.PopulateNestedMenuGroupAction;
import net.ruready.web.menugroup.entity.NestedMenuGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Handle AJAX events of populating and updating the course-topic-sub-topic menu
 * group in the question editing page.
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
 * @version Nov 7, 2007
 */
public class PopulateSubTopicMenuGroupAction extends
		PopulateNestedMenuGroupAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(PopulateSubTopicMenuGroupAction.class);

	// ========================= IMPLEMENTATION: PopulateMenuAction ========

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * Return the menu group managed by this action.
	 * 
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 * @return the menu group
	 */
	@Override
	protected NestedMenuGroup getNestedMenuGroup(
			final ApplicationContext context, final User user)
	{
		return new SubTopicMenuGroup(context, user);
	}

	// ========================= PRIVATE METHODS ===========================

}
