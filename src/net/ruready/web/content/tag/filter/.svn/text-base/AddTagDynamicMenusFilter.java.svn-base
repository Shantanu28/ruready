/*****************************************************************************************
 * Source File: AddQuestionMenusFilter.java
 ****************************************************************************************/
package net.ruready.web.content.tag.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.concept.entity.ConceptCollection;
import net.ruready.business.content.interest.entity.InterestCollection;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.skill.entity.SkillCollection;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.chain.filter.FilterAction;
import net.ruready.web.chain.filter.FilterDefinition;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.form.EditItemForm;
import net.ruready.web.content.item.form.EditItemFullForm;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.tag.form.TagCollectionMenuForm;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.util.MessageResources;

/**
 * Adds data for a drop-down menus related to {@link TagItem} items that are
 * attributes of another {@link Item}. Specifically (so far at least),
 * interest, concept and skill collections of a {@link Subject}.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 7, 2007
 */
public class AddTagDynamicMenusFilter extends FilterAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AddTagDynamicMenusFilter.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize filter. Set init parameters using the JavaBean conventions.
	 * Sub-classes should provide setters for this injection to happen.
	 * 
	 * @param filterDefinition
	 *            filter definition, including init parameters
	 */
	public AddTagDynamicMenusFilter(FilterDefinition filterDefinition)
	{
		super(filterDefinition);
	}

	// ========================= IMPLEMENTATION: FilterAction ==============

	/**
	 * Add a child item type drop-down menu data to the request.
	 * 
	 * @param saContext
	 * @param action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @see net.ruready.web.chain.filter.FilterAction#doFilter(org.apache.struts.chain.contexts.ServletActionContext,
	 *      org.apache.struts.action.Action,
	 *      org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doFilter(final ServletActionContext saContext,
			final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	{
		// ---------------------------------------
		// Read data from request & parameters
		// ---------------------------------------
		// WebApplicationContext context =
		// StrutsUtil.getWebApplicationContext(request);
		EditItemFullForm editItemFullForm = (EditItemFullForm) request
				.getAttribute("editItemFullForm");

		// -------------------------------------------
		// Business logic
		// -------------------------------------------

		// Reset the form parameters to default values (this is not the same as
		// Struts' reset() method)
		if (editItemFullForm == null)
		{
			// Request-scope form, instantiate a new form and reset its fields
			editItemFullForm = new EditItemFullForm();
			request.setAttribute("editItemFullForm", editItemFullForm);
		}

		// Populate the menus
		MessageResources messageResources = this.getResources(request);
		User user = HttpRequestUtil.findUser(request);

		this.populateMenus(InterestCollection.class, ItemType.INTEREST_COLLECTION, request,
				editItemFullForm.getItemForm(), user, messageResources);
		this.populateMenus(ConceptCollection.class, ItemType.CONCEPT_COLLECTION, request,
				editItemFullForm.getItemForm(), user, messageResources);
		this.populateMenus(SkillCollection.class, ItemType.SKILL_COLLECTION, request,
				editItemFullForm.getItemForm(), user, messageResources);

		// -------------------------------------------
		// Attach results to response
		// -------------------------------------------

		return null;
	}

	/**
	 * @return
	 * @see net.ruready.web.chain.command.NamedCommand#getDescription()
	 */
	public String getDescription()
	{
		return "Add Tag drop-down menu data";
	}

	// ========================= METHODS ===================================

	/**
	 * A helper method that populates drop-down menus.
	 * 
	 * @param userForm
	 *            Struts form bean attached to this action/filter.
	 * @param user
	 *            user requesting this operation
	 * @param messageResources
	 *            request's Struts resource bundle
	 */
	private void populateMenus(final Class<? extends Item> tagClass,
			final ItemType tagType, final HttpServletRequest request,
			EditItemForm editQuestionForm, final User user,
			final MessageResources messageResources)
	{
		// ---------------------------------------
		// Read data from view, retrieve beans
		// ---------------------------------------

		// ---------------------------------------
		// Business logic
		// ---------------------------------------

		// Retrieve the list of non-deleted (active) interest collections from
		// the
		// database
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		List<? extends Item> tagList = bdItem.findAllNonDeleted(tagClass, tagType);

		// Apply i18n to labels
		OptionList tagOptions = StrutsUtil.items2OLS(tagList, false,
				CommonNames.MISC.INVALID_VALUE_INTEGER);
		tagOptions = StrutsUtil.i18NEmptySelectionOptions(tagOptions, messageResources,
				WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL);

		// Use the OLS framework to construct a Option option list for
		// each question property drop-down menu that does not require loading
		// from database
		//
		// String separator = WebAppNames.KEY.MESSAGE.SEPARATOR;
		// String suffix = WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX;

		// ---------------------------------------
		// Attach results to form
		// ---------------------------------------
		TagCollectionMenuForm tagCollectionMenuForm = editQuestionForm
				.getTagCollectionMenuForm();
		tagCollectionMenuForm.setMenu(tagType, tagOptions);

	} // populateCountries()

	// ========================= GETTERS & SETTERS =========================
}
