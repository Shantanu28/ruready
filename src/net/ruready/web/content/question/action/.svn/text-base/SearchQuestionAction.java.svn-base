/*****************************************************************************************
 * Source File: SearchQuestionAction.java
 ****************************************************************************************/
package net.ruready.web.content.question.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionAction;
import net.ruready.business.content.question.entity.property.QuestionField;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.question.entity.state.QuestionStateID;
import net.ruready.business.content.question.exports.AbstractEditQuestionBD;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterion;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.common.search.StringExpression;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.EnumUtil;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.action.SearchItemAction;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.question.form.SearchQuestionForm;
import net.ruready.web.content.question.imports.StrutsEditQuestionBD;
import net.ruready.web.content.question.util.EditQuestionUtil;

import org.apache.commons.beanutils.BeanUtils;
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
public class SearchQuestionAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SearchQuestionAction.class);

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
		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		if ((searchQuestionForm != null) && (!searchQuestionForm.isBlankForm()))
		{
			// If a a previous form exists in the session and is non-null and
			// not blank, run the search.
			doSearch(searchQuestionForm, request);
		}
		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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
		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		searchQuestionForm.reset();
		searchQuestionForm.reset(mapping, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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
		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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
		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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

		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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

		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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

		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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

		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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
		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		searchQuestionForm.resetDisplayTagFields();
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
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
		// ---------------------------------------
		// Read data from the request
		// ---------------------------------------
		User user = HttpRequestUtil.findUser(request);
		// Existing question to be cloned
		Question question = getQuestion(request);

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Clone the question
		Question clone = (Question) question.clone();

		// Update the status flags of the clone
		clone.updateState(QuestionAction.ADD);

		// Save the cloned question to the database
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		if (question.getParent() == null)
		{
			// No known parent
			getBD(request).update(clone, true);
		}
		else
		{
			// Add under the new parent
			bdItem.createUnder(question.getParent().getId(), clone);
		}

		// ---------------------------------------
		// Attach results to response
		// ---------------------------------------

		// Redo the search to refresh the screen
		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
	}

	/**
	 * Search for questions in the database by criteria specified by the browse
	 * table links. This method uses a different form bean name to avoid
	 * clearing filter field check-boxes.
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
	public ActionForward action_browse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// Leave only relevant fields to browse-type search
		SearchQuestionForm searchQuestionForm = (SearchQuestionForm) form;
		searchQuestionForm.resetExceptBrowseFields();

		// Carry out search normally
		doSearch(searchQuestionForm, request);

		return EditQuestionUtil.appendDisplayTagParameters(
				mapping.findForward("success"), searchQuestionForm);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Modify the question's state. Supports all updateStatus operations defined
	 * in the the enumerated type {@link QuestionStateOperation}.
	 * 
	 * @param operation
	 *            question state change operation
	 * @param request
	 *            client's request object
	 */
	private void updateQuestionState(final QuestionAction operation,
			HttpServletRequest request)
	{
		Question question = getQuestion(request);
		question.updateState(operation);
		getBD(request).update(question, true);
	}

	/**
	 * Helper method to obtain the appropriate item editing BD for this action.
	 * 
	 * @param request
	 *            client's request object
	 * @return item editing business delegate
	 */
	private AbstractEditItemBD<Item> getBD(final HttpServletRequest request)
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
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void doSearch(SearchQuestionForm searchQuestionForm,
			HttpServletRequest request) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException
	{
		// -------------------------------------------
		// Read data from request
		// -------------------------------------------
		final WebApplicationContext context = StrutsUtil
				.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		// Once a search is requested, we save the result set, i.e., the form is
		// no longer
		// considered blank.
		searchQuestionForm.setBlankForm(false);
		logger.info("searchQuestionForm = " + BeanUtils.describe(searchQuestionForm));

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Set up vars for the search framework
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Root criterion = (searchCriterion AND filterCriterion)
		SearchCriterion rootCriterion = SearchCriterionFactory
				.createJunctionCriterion(SearchType.CONJUNCTION);
		SearchCriteria searchCriteria = new DefaultSearchCriteria(rootCriterion);
		// TODO: add result set bounds after we migrate from DisplayTag to
		// ExtJs. Something like (if page is 1-based):
		// .setFirstResult((page-1)*results_per_page).setMaxResults(results_per_page);

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Search parameters
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		SearchCriterion searchCriterion = searchQuestionForm.getSearchModeAsLogic()
				.createCriterion();

		Long subjectId = searchQuestionForm.getSubTopicMenuGroupForm()
				.getSubjectIdAsLong();
		Long courseId = searchQuestionForm.getSubTopicMenuGroupForm().getCourseIdAsLong();
		Long topicId = searchQuestionForm.getSubTopicMenuGroupForm().getTopicIdAsLong();
		Long subTopicId = searchQuestionForm.getSubTopicMenuGroupForm()
				.getSubTopicIdAsLong();

		// ===============================
		// Sub-Topic ID search parameter
		// ===============================
		if (subTopicId != CommonNames.MISC.INVALID_VALUE_LONG)
		{
			ItemTypeUtil.addSuperParentAlias(searchCriteria, ItemType.SUB_TOPIC,
					ItemType.QUESTION);
			SearchCriterion criterion = SearchCriterionFactory
					.<Long> createSimpleExpression(SearchType.EQ, Long.class,
							QuestionField.SUPER_PARENT_ID.getName(), subTopicId);
			searchCriterion.add(criterion);
		}

		// ===============================
		// Topic ID search parameter
		// ===============================
		else if (topicId != CommonNames.MISC.INVALID_VALUE_LONG)
		{
			ItemTypeUtil.addSuperParentAlias(searchCriteria, ItemType.TOPIC,
					ItemType.QUESTION);
			SearchCriterion criterion = SearchCriterionFactory
					.<Long> createSimpleExpression(SearchType.EQ, Long.class,
							QuestionField.SUPER_PARENT_ID.getName(), topicId);
			searchCriterion.add(criterion);
		}

		// ===============================
		// Course ID search parameter
		// ===============================
		else if (courseId != CommonNames.MISC.INVALID_VALUE_LONG)
		{
			ItemTypeUtil.addSuperParentAlias(searchCriteria, ItemType.COURSE,
					ItemType.QUESTION);
			SearchCriterion criterion = SearchCriterionFactory
					.<Long> createSimpleExpression(SearchType.EQ, Long.class,
							QuestionField.SUPER_PARENT_ID.getName(), courseId);
			searchCriterion.add(criterion);
		}

		// ===============================
		// Subject ID search parameter
		// (fall-back from courseID)
		// ===============================
		// Always active, so it's a fall-back if a course is not specified
		else
		{
			AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
					context, user);
			Subject subject = bdItem.read(Subject.class, subjectId);
			ItemTypeUtil.addSuperParentAlias(searchCriteria, ItemType.SUBJECT,
					ItemType.QUESTION);
			SearchCriterion subjectIdCriterion = SearchCriterionFactory
					.<Long> createSimpleExpression(SearchType.EQ, Long.class,
							QuestionField.SUPER_PARENT_ID.getName(), subject.getId());
			searchCriterion.add(subjectIdCriterion);
		}

		// ===============================
		// id search parameter
		// ===============================
		long id = searchQuestionForm.getIdAsLong();
		if (id != CommonNames.MISC.INVALID_VALUE_LONG)
		{
			QuestionField field = QuestionField.ID;
			SearchCriterion criterion = SearchCriterionFactory
					.<Long> createSimpleExpression(SearchType.EQ, Long.class, field
							.getName(), id);
			searchCriterion.add(criterion);
		}

		// ===============================
		// level search parameter
		// ===============================
		int level = searchQuestionForm.getLevelAsInteger();
		if (level != CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			QuestionField field = QuestionField.LEVEL;
			SearchCriterion criterion = SearchCriterionFactory
					.<Integer> createSimpleExpression(SearchType.EQ, Integer.class, field
							.getName(), level);
			searchCriterion.add(criterion);
		}

		// ===============================
		// Question type search parameter
		// ===============================
		QuestionType questionType = EnumUtil.valueOf(QuestionType.class,
				searchQuestionForm.getType());
		if (questionType != null)
		{
			QuestionField field = QuestionField.QUESTION_TYPE;
			SearchCriterion criterion = SearchCriterionFactory
					.<QuestionType> createSimpleExpression(SearchType.EQ,
							QuestionType.class, field.getName(), questionType);
			searchCriterion.add(criterion);
		}

		// ===============================
		// formulation search parameter
		// ===============================
		String formulation = searchQuestionForm.getFormulation();
		if (!TextUtil.isEmptyTrimmedString(formulation))
		{
			QuestionField field = QuestionField.FORMULATION;
			StringExpression criterion = SearchCriterionFactory.createStringExpression(
					SearchType.LIKE, field.getName(), formulation, MatchType.CONTAINS);
			searchCriterion.add(criterion);
		}

		rootCriterion.add(searchCriterion);

		// ===============================
		// Parametric search parameter
		// ===============================
		// Activated only if check-box field is set in the form, otherwise
		// includes both parametric and non-parametric questions.
		if (searchQuestionForm.isParametric())
		{
			QuestionField field = QuestionField.PARAMETRIC;
			// SearchCriterion criterion = SearchCriterionFactory
			// .<String> createNoArgExpression(SearchType.IS_NOT_NULL,
			// String.class, field.getName());
			SearchCriterion criterion = SearchCriterionFactory
					.<String> createSimpleExpression(SearchType.NE, String.class, field
							.getName(), CommonNames.MISC.EMPTY_STRING);
			searchCriterion.add(criterion);
		}

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// Filter parameters
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		// filterCriterion = AND(filter1,...,filterN)
		SearchCriterion filterCriterion = SearchCriterionFactory
				.createJunctionCriterion(SearchType.CONJUNCTION);
		// Required alias to search for state
		searchCriteria.addAlias("question_state", "state");

		// ===============================
		// New-state filter parameter
		// ===============================
		if (!searchQuestionForm.isShowNew())
		{
			QuestionField field = QuestionField.QUESTION_STATE_ID;
			SearchCriterion criterion = SearchCriterionFactory
					.<QuestionStateID> createSimpleExpression(SearchType.NE,
							QuestionStateID.class, field.getName(), QuestionStateID.NEW);
			filterCriterion.add(criterion);
		}

		// ===============================
		// Active-state filter parameter
		// ===============================
		if (!searchQuestionForm.isShowActive())
		{
			QuestionField field = QuestionField.QUESTION_STATE_ID;
			SearchCriterion criterion = SearchCriterionFactory
					.<QuestionStateID> createSimpleExpression(SearchType.NE,
							QuestionStateID.class, field.getName(), QuestionStateID.ACTIVE);
			filterCriterion.add(criterion);
		}

		// ===============================
		// Updated-state filter parameter
		// ===============================
		if (!searchQuestionForm.isShowUpdated())
		{
			QuestionField field = QuestionField.QUESTION_STATE_ID;
			SearchCriterion criterion = SearchCriterionFactory
					.<QuestionStateID> createSimpleExpression(SearchType.NE,
							QuestionStateID.class, field.getName(),
							QuestionStateID.UPDATED);
			filterCriterion.add(criterion);
		}

		// ===============================
		// Deleted-state filter parameter
		// ===============================
		if (!searchQuestionForm.isShowDeleted())
		{
			QuestionField field = QuestionField.QUESTION_STATE_ID;
			SearchCriterion criterion = SearchCriterionFactory
					.<QuestionStateID> createSimpleExpression(SearchType.NE,
							QuestionStateID.class, field.getName(),
							QuestionStateID.DELETED);
			filterCriterion.add(criterion);
		}

		// ===============================
		// Disabled-state filter parameter
		// ===============================
		if (!searchQuestionForm.isShowDisabled())
		{
			QuestionField field = QuestionField.QUESTION_STATE_ID;
			SearchCriterion criterion = SearchCriterionFactory
					.<QuestionStateID> createSimpleExpression(SearchType.NE,
							QuestionStateID.class, field.getName(),
							QuestionStateID.DISABLED);
			filterCriterion.add(criterion);
		}

		rootCriterion.add(filterCriterion);

		// ===============================
		// Execute search
		// ===============================
		if (logger.isDebugEnabled())
		{
			logger.debug("Search Criteria = " + searchCriteria);
		}
		AbstractEditQuestionBD bd = new StrutsEditQuestionBD(StrutsUtil
				.getWebApplicationContext(request), user);
		List<Question> resultSet = bd.findByCriteria(searchCriteria);
		if (logger.isDebugEnabled())
		{
			logger.debug("#results = " + resultSet.size());
		}

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.SEARCH_QUESTION_RESULT,
				resultSet);

	} // doSearch()
}
