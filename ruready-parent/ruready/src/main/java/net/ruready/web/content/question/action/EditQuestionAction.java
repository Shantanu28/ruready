/*****************************************************************************************
 * Source File: EditQuestionAction.java
 ****************************************************************************************/
package net.ruready.web.content.question.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.action.EditItemFullAction;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.util.transfer.ItemValidationUtil;
import net.ruready.web.content.question.form.BrowseQuestionForm;
import net.ruready.web.content.question.form.EditQuestionForm;
import net.ruready.web.content.question.form.EditQuestionFullForm;
import net.ruready.web.content.question.form.SubTopicMenuGroupForm;
import net.ruready.web.content.question.util.EditQuestionUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * Action methods specific to the modify question activity. Extends
 * {@link EditItemFullAction}, where the item-generic save, done, cancel
 * activities are implemented.
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
public class EditQuestionAction extends EditItemFullAction
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditQuestionAction.class);

	// ========================= FIELDS ====================================

	// Never use instance variables! Actions must be thread-safe. Use local
	// variables and pooled resources only.
	// @see http://struts.apache.org/1.x/userGuide/building_controller.html
	// Section 4.4.1

	// ========================= IMPLEMENTATION: LoggedActionDispatc========
	//
	// /**
	// * @return the logger
	// */
	// protected static Log getLogger()
	// {
	// return logger;
	// }

	// ========================= ACTION METHODS ============================

	/**
	 * Update the number of multiple choices. We validate only the
	 * number-of-choices field.
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
	public ActionForward action_updateNumberOfChoices(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EditQuestionForm eqForm = EditQuestionUtil.getQuestionForm(form);

		eqForm.updateDTOs();

		// Set the anchor to jump to upon re-entry to the JSP
		request.setAttribute("hash", "#choices");

		return mapping.findForward("edit");
	}

	/**
	 * Add a new topic to the database. We validate only the new sub-topic name
	 * field.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @param itemType
	 *            type of new item to be added to database
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_addTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		EditQuestionForm eqForm = EditQuestionUtil.getQuestionForm(form);
		SubTopicMenuGroupForm subTopicMenuGroupForm = eqForm.getSubTopicMenuGroupForm();

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Add new topic
		ActionErrors errors = new ActionErrors();
		Item newTopic = this.action_addItem(mapping, request, response,
				subTopicMenuGroupForm.getCourseIdAsLong(), ItemType.TOPIC, eqForm
						.getAddTopic(), "error.question.cannotAddTopic",
				"error.question.cannotAddTopic.noParent", errors);

		// Clear/update the appropriate form fields after a successful item
		// addition
		if (errors.isEmpty())
		{
			// Set drop-down menus to new item's parents; clear all menus
			// dependent on the
			// item
			subTopicMenuGroupForm.setSubTopicId(null);

			subTopicMenuGroupForm.setTopicId(CommonNames.MISC.EMPTY_STRING
					+ newTopic.getId());

			int courseHeight = ItemTypeUtil
					.getPathLength(ItemType.COURSE, ItemType.TOPIC);
			subTopicMenuGroupForm.setCourseId(CommonNames.MISC.EMPTY_STRING
					+ newTopic.getSuperParentId(courseHeight));

			// Reset new item selection
			eqForm.setAddTopic(null);
		}

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		return mapping.findForward("edit");
	}

	/**
	 * Add a new sub-topic to the database. We validate only the new sub-topic
	 * name field.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @param itemType
	 *            type of new item to be added to database
	 * @return
	 * @throws Exception
	 */
	public ActionForward action_addSubTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		EditQuestionForm eqForm = EditQuestionUtil.getQuestionForm(form);
		SubTopicMenuGroupForm subTopicMenuGroupForm = eqForm.getSubTopicMenuGroupForm();

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Add new sub-topic
		ActionErrors errors = new ActionErrors();
		Item newSubTopic = this.action_addItem(mapping, request, response,
				subTopicMenuGroupForm.getTopicIdAsLong(), ItemType.SUB_TOPIC, eqForm
						.getAddSubTopic(), "error.question.cannotAddSubTopic",
				"error.question.cannotAddSubTopic.noParent", errors);

		// Clear form field after a successful addition
		if (errors.isEmpty())
		{
			// Set drop-down menus to new item's parents; clear all menus
			// dependent on the item
			subTopicMenuGroupForm.setSubTopicId(CommonNames.MISC.EMPTY_STRING
					+ newSubTopic.getId());

			int topicHeight = ItemTypeUtil.getPathLength(ItemType.TOPIC,
					ItemType.SUB_TOPIC);
			subTopicMenuGroupForm.setTopicId(CommonNames.MISC.EMPTY_STRING
					+ newSubTopic.getSuperParentId(topicHeight));

			int courseHeight = ItemTypeUtil.getPathLength(ItemType.COURSE,
					ItemType.SUB_TOPIC);
			subTopicMenuGroupForm.setCourseId(CommonNames.MISC.EMPTY_STRING
					+ newSubTopic.getSuperParentId(courseHeight));

			// Reset new item selection
			eqForm.setAddSubTopic(null);
		}

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		return mapping.findForward("edit");
	}

	/**
	 * A handler of onChange() events of the question type list-box.
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
	public ActionForward action_setup_updateQuestionType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EditQuestionForm eqForm = EditQuestionUtil.getQuestionForm(form);

		eqForm.updateDTOs();

		return mapping.findForward("edit");
	}

	/**
	 * Display multiple choice question read only screen
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
	public ActionForward action_setup_displayMultipleChoice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		return this.action_setup_displayFormat(mapping, form, request, response,
				"displayMultipleChoice");
	}

	/**
	 * Display open ended question read only screen
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
	public ActionForward action_setup_displayOpenEnded(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		return this.action_setup_displayFormat(mapping, form, request, response,
				"displayOpenEnded");
	}

	// ========================= IMPLEMENTATION: EditItemFullAction ========

	/**
	 * @see net.ruready.web.content.item.action.EditItemFullAction#getExitForward(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected ActionForward getExitForward(ActionMapping mapping, final ActionForm form,
			HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		// User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Read data from form
		// -------------------------------------------

		// Save display tag library state request parameters in the form

		// Retrieve custom forward token from session
		String customForward = HttpRequestUtil.getAndRemoveSessionToken(session,
				WebAppNames.SESSION.ATTRIBUTE.TOKEN.CUSTOM_FORWARD);
		boolean isSpecialForward = isSpecialForward(customForward);

		// ###########################################
		// Default forward
		// ###########################################
		if (!isSpecialForward)
		{
			return super.getExitForward(mapping, form, request);
		}

		// ###########################################
		// Custom forwards
		// ###########################################

		// This forward will change as necessary using method chaining below
		ActionForward forward = mapping.findForward(customForward);

		// -------------------------------------------
		// Add some request and session attributes
		// -------------------------------------------
		String attribute = getBrowseQuestionFormAttribute(customForward);
		// Retrieve the appropriate browse form from the session
		BrowseQuestionForm browseQuestionForm = (BrowseQuestionForm) session
				.getAttribute(attribute);

		// Append parent item reference from view's existing (session-scoped)
		// form
		if ("parentView".equals(customForward))
		{
			Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
			Item parent = item.getParent();
			forward = StrutsUtil.appendParameter(forward,
					WebAppNames.REQUEST.PARAM.ITEM_ID, CommonNames.MISC.EMPTY_STRING
							+ parent.getId());
			forward = StrutsUtil.appendParameter(forward,
					WebAppNames.REQUEST.PARAM.ITEM_TYPE, CommonNames.MISC.EMPTY_STRING
							+ parent.getIdentifier());
		}

		// -------------------------------------------
		// Finalize forward string and do the forward
		// -------------------------------------------
		// Append display tag parameters to forward from form bean properties
		if (isSpecialForward(customForward))
		{
			forward = EditQuestionUtil.appendDisplayTagParameters(forward,
					browseQuestionForm);
		}

		return forward;
	}

	/**
	 * Custom setup related activities.
	 */
	@Override
	protected void setupInit(HttpServletRequest request, ActionForm form)
	{
		super.setupInit(request, form);

		// Set parentId request attribute to be retrieved by later actions.
		// If a request parameter is found is the request, use it; otherwise,
		// use item.parent.id if an item is found in the request.
		Item item = (Item) request.getAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM);
		String parentIdStr = request
				.getParameter(WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID);
		if (parentIdStr != null)
		{
			HttpRequestUtil.setSessionToken(request,
					WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID);
		}
		else if (item != null)
		{
			HttpSession session = request.getSession(false);
			parentIdStr = CommonNames.MISC.EMPTY_STRING + item.getParentId();
			// Make sure to save a String-valued token!
			session.setAttribute(WebAppNames.REQUEST.PARAM.ITEM_PARENT_ID, parentIdStr);
			if (logger.isDebugEnabled())
			{
				logger.debug("Attaching item parent ID " + parentIdStr + " to session");
			}
		}
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * helper method to obtain questions from the db. Returns null is for a
	 * not-yet-saved question form.
	 * 
	 * @param request
	 *            client's request object
	 * @return
	 */
	private Question getQuestion(HttpServletRequest request)
	{
		User user = HttpRequestUtil.findUser(request);
		AbstractEditItemBD<Item> bd = new StrutsEditItemBD<Item>(Item.class, StrutsUtil
				.getWebApplicationContext(request), user);
		long id = HttpRequestUtil.getParameterAsLong(request,
				WebAppNames.REQUEST.PARAM.ITEM_ID);
		if (id == CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			ActionErrors errors = new ActionErrors();
			errors.add(WebAppNames.KEY.QUESTION_NOT_FOUND, new ActionMessage(
					WebAppNames.KEY.QUESTION_NOT_FOUND));
			this.saveErrors(request, errors);
			request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.TOKEN.INVALID,
					WebAppNames.ACTION.VALUE_TRUE);
			return null;
		}
		Question question = bd.read(Question.class, id);
		return question;
	}

	/**
	 * Add a new item to the database. We validate only the new sub-topic name
	 * field.
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @param superParentId
	 *            id of [super-]parent to contain this item
	 * @param itemType
	 *            type of new item to be added to database
	 * @param itemName
	 *            name of new item to be added to database
	 * @param errorMessageKey
	 *            error message key in the application message resources
	 * @return new item, or <code>null</code> if could not add item
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	private Item action_addItem(ActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, final long superParentId,
			final ItemType itemType, final String itemName, final String errorMessageKey,
			final String noParentMessageKey, ActionErrors errors) throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		User user = HttpRequestUtil.findUser(request);

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Find all courses under the currently selected course
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		// TODO: replace this ugly try-catch block by a smarter exception
		// handler/null
		// item case handler
		Item superParent = null;
		try
		{
			superParent = bdItem.read(Item.class, superParentId);
		}
		catch (Exception e)
		{
			errors.add("cannotAddItem", new ActionMessage(noParentMessageKey));
			saveErrors(request, errors);
			return null;
		}

		List<? extends Item> children = (superParent == null) ? null : bdItem
				.findChildren(superParent, itemType.getItemClass(), itemType);
		boolean directParent = (ItemTypeUtil.getPathLength(superParent.getIdentifier(),
				itemType) == 1);
		if (!directParent && ((children == null) || children.isEmpty()))
		{
			// If superParent is not the prospective child's direct parent, we
			// cannot add a new item because we don't know if there's even a
			// parent
			// to put the new item under on the path the super parent and the
			// item.
			errors.add("cannotAddItem", new ActionMessage(errorMessageKey));
			saveErrors(request, errors);
			return null;
		}

		// Find the parent of the first subTopic on the list
		Long parentId = (directParent ? superParent.getId() : children.get(0).getParent()
				.getId());
		Item parent = bdItem.read(Item.class, parentId);

		// Create new subTopic and add it under the same parent
		Item newChild = itemType.createItem(itemName, null);
		// Validate child name or rename it if necessary
		boolean success = ItemValidationUtil.validateChildCreation(parent, newChild,
				errors);
		if (!errors.isEmpty())
		{
			saveErrors(request, errors);
		}

		if (success)
		{
			bdItem.createUnder(parent.getId(), newChild);
		}
		return newChild;
	}

	/**
	 * Display multiple choice question read only screen
	 * 
	 * @param mapping
	 *            Struts forward mapping context object
	 * @param form
	 *            Struts form bean attached to this action
	 * @param request
	 *            client's request object
	 * @param response
	 *            server's response object
	 * @param forward
	 *            page to forward the request to
	 * @return
	 * @throws Exception
	 */
	private ActionForward action_setup_displayFormat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response,
			final String forward) throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		if (!this.validateQuestion(mapping, form, request, response))
		{
			return mapping.findForward(forward);
		}

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// // Automatically save the question before displaying it
		// ActionErrors errors = this.editItemFull(mapping, form, request,
		// response);
		// if (!this.validateRequest(mapping, form, request, response, errors))
		// {
		// return mapping.findForward(forward);
		// }

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		Question question = getQuestion(request);

		// Save question in the request for display
		request.setAttribute(WebAppNames.REQUEST.ATTRIBUTE.ITEM, question);

		return mapping.findForward(forward);
	}

	/**
	 * Validate question object; if it is invalid, add an invalidation token.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param forward
	 * @return is question object valid
	 * @throws Exception
	 */
	private boolean validateQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------
		EditQuestionFullForm editQuestionFullForm = (EditQuestionFullForm) form;

		// Check if the question is valid; if not, attach a token signaling its
		// invalidity
		ActionErrors errors = editQuestionFullForm.validate(mapping, request);
		return this.validateRequest(mapping, form, request, response, errors);
	}

	/**
	 * Validate current request status. If it is invalid (errors exist in the
	 * argument <code>errors</code>), add an invalidation token.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param forward
	 * @param errors
	 * @return is request valid
	 * @throws Exception
	 */
	private boolean validateRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, ActionErrors errors)
			throws Exception
	{
		// -------------------------------------------
		// Read data from form
		// -------------------------------------------

		// Check if the question is valid; if not, attach a token signaling its
		// invalidity
		if ((errors != null) && (!errors.isEmpty()))
		{
			this.saveErrors(request, errors);
			HttpRequestUtil.setRequestToken(request,
					WebAppNames.REQUEST.ATTRIBUTE.TOKEN.INVALID);
			return false;
		}
		return true;
	}

	// ========================= UTILITY METHODS ===========================

	/**
	 * Is the custom forward special and requires DT parameter appending or not.
	 * 
	 * @param customForward
	 *            custom forward string
	 * @return Is the custom forward special
	 */
	private static boolean isSpecialForward(final String customForward)
	{
		return (("search".equals(customForward)) || ("parentView".equals(customForward)));
	}

	/**
	 * Return the attribute name of the browsing form for the view page
	 * specified by a custom forward.
	 * 
	 * @param customForward
	 *            custom forward string
	 * @return attribute name of the browsing form on the corresponding view
	 *         page
	 */
	private static String getBrowseQuestionFormAttribute(final String customForward)
	{
		if ("search".equals(customForward))
		{
			return "searchQuestionForm";
		}
		if ("parentView".equals(customForward))
		{
			return "browseQuestionForm";
		}
		return CommonNames.MISC.EMPTY_STRING;
	}

}
