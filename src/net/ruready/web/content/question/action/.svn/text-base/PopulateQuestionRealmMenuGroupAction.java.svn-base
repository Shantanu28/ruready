/*****************************************************************************************
 * Source File: PopulateSubTopicMenuGroupAction.java
 ****************************************************************************************/
package net.ruready.web.content.question.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.content.question.form.QuestionRealmMenuGroup;
import net.ruready.web.menugroup.action.PopulateNestedMenuGroupAction;
import net.ruready.web.menugroup.entity.NestedMenuGroup;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

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
public class PopulateQuestionRealmMenuGroupAction extends PopulateNestedMenuGroupAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(PopulateQuestionRealmMenuGroupAction.class);

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
	protected NestedMenuGroup getNestedMenuGroup(final ApplicationContext context,
			final User user)
	{
		return new QuestionRealmMenuGroup(context, user);
	}

	/**
	 * Extra action processing to invoke at the end of
	 * {@link #execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
	 * (right before returning the <code>null</code> forward).
	 * <p>
	 * In this case: store top menu's selected ID in the session so that
	 * question edit pages can refer to it.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param group
	 *            menu group, which is already populated in the body of
	 *            {@link #execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
	 * @param selection
	 *            menu selection, which is already populated in the body of
	 *            {@link #execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
	 */
	@Override
	protected void extraProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			final NestedMenuGroup group, final Map<String, OptionList> selection)
	{
		// Store top menu's selected ID in the session so that question edit
		// pages can refer to it.
		String subjectMenuName = group.getMenuName(0);
		OptionList subjectMenu = selection.get(subjectMenuName);
		if (subjectMenu != null)
		{
			String subjectId = subjectMenu.getSelectedValue();
			HttpSession session = request.getSession(false);
			session.setAttribute(WebAppNames.SESSION.ATTRIBUTE.SUBJECT_ID, subjectId);
			if (logger.isDebugEnabled())
			{
				logger.debug("Attaching subject ID " + subjectId + " to session");
			}
		}
	}
	// ========================= PRIVATE METHODS ===========================

}
