/*****************************************************************************************
 * Source File: BrowseQuestionAction.java
 ****************************************************************************************/
package net.ruready.web.content.question.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionAction;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.action.SearchItemAction;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.question.form.BrowseQuestionForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manages {@link Question} search queries. This is similar to
 * {@link SearchItemAction}, but has a custom behavior as a result of the
 * {@link Question} entity's special structure.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Greg Felice <code>&lt;greg.felice@gmail.com&gt;</code>
 * @version Jul 29, 2007
 */
public class BrowseQuestionAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(BrowseQuestionAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= ACTION METHODS ============================

	/**
	 * The default action taken when the method parameter is not present in the
	 * request. Performs question search setup.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return ActionForward forward action to forward the request to
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		if ((browseQuestionForm != null) && (!browseQuestionForm.isBlankForm()))
		{
			// If a a previous form exists in the session and is non-null and
			// not blank, run the search.
			doSearch(browseQuestionForm, request);
		}
		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 */
	public ActionForward action_setup_reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		browseQuestionForm.reset();

		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * A custom cancel action handler. We have to use the forward mapping, so we
	 * cannot override {@link #isCancelled(HttpServletRequest)}.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 */
	public ActionForward action_setup_cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * Search for questions in the database by criteria specified in the form.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		doSearch(browseQuestionForm, request);

		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		updateQuestionState(QuestionAction.DELETE, request);

		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		doSearch(browseQuestionForm, request);

		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_undelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		updateQuestionState(QuestionAction.UNDELETE, request);

		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		doSearch(browseQuestionForm, request);

		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_disable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		updateQuestionState(QuestionAction.DISABLE, request);

		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		doSearch(browseQuestionForm, request);

		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_enable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		updateQuestionState(QuestionAction.ENABLE, request);

		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		doSearch(browseQuestionForm, request);

		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * Reset display tag fields (sorting, pagination).
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_setup_resetDT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;

		browseQuestionForm.resetDisplayTagFields();
		doSearch(browseQuestionForm, request);

		return getSuccessForward(mapping, browseQuestionForm);
	}

	/**
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_clone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		Question question = getQuestion(request);
		User user = HttpRequestUtil.findUser(request);

		// Clone the question
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);
		Question clone = bdItem.copyUnder(item, question);

		// Update the status flags on the question
		clone.updateState(QuestionAction.ADD);

		// Save the question in the database
		getBD(request).update(clone, true);

		// Rerun the search to correctly refresh the screen
		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) form;
		doSearch(browseQuestionForm, request);

		return getSuccessForward(mapping, browseQuestionForm);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Modify the questions status. Supports all updateStatus operations in enum
	 * QuestionStatusOperation.
	 * 
	 * @param request
	 *            client's request object
	 * @see QuestionStateOperation
	 * @return
	 */
	private void updateQuestionState(final QuestionAction operation,
			HttpServletRequest request)
	{
		Question question = null;
		question = getQuestion(request);
		question.updateState(operation);
		getBD(request).update(question, true);
	}

	/**
	 * Helper method to obtain the BD.
	 * 
	 * @param request
	 *            client's request object
	 * @return
	 */
	private AbstractEditItemBD<Item> getBD(HttpServletRequest request)
	{
		User user = HttpRequestUtil.findUser(request);
		AbstractEditItemBD<Item> bd = new StrutsEditItemBD<Item>(Item.class, StrutsUtil
				.getWebApplicationContext(request), user);
		return bd;
	}

	/**
	 * Helper method to obtain a question from the database.
	 * 
	 * @param request
	 *            client's request object
	 * @return
	 */
	private Question getQuestion(HttpServletRequest request)
	{
		AbstractEditItemBD<Item> bd = getBD(request);
		Long id = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);
		Question question = bd.read(Question.class, id);
		return question;
	}

	/**
	 * Carry out the actual search for a question result set according to the
	 * criteria specified by a search form.
	 * 
	 * @param browseQuestionForm
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void doSearch(final BrowseQuestionForm browseQuestionForm,
			final HttpServletRequest request)
	{
		User user = HttpRequestUtil.findUser(request);
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		item = bdItem.read(Item.class, item.getId());
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, item);
		List<Question> resultSet = bdItem.childrenToList(Question.class, item);

		// -------------------------------------------
		// Attach results to request
		// -------------------------------------------
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.SEARCH_QUESTION_RESULT,
				resultSet);

	} // doSearch()

	/**
	 * @param mapping
	 * @param browseQuestionForm
	 * @return
	 */
	private ActionForward getSuccessForward(final ActionMapping mapping,
			final BrowseQuestionForm browseQuestionForm)
	{
		ActionForward forward = mapping.findForward("success");
		forward = StrutsUtil.appendParameter(forward, WebAppNames.REQUEST.PARAM.ITEM_ID,
				browseQuestionForm.getParentId());
		forward = StrutsUtil.appendParameter(forward,
				WebAppNames.REQUEST.PARAM.ITEM_TYPE, browseQuestionForm.getParentType());
		return forward;
	}
}
