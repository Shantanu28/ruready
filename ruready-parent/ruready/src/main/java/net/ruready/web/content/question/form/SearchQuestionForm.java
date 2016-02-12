/*****************************************************************************************
 * Source File: SearchQuestionForm.java
 ****************************************************************************************/
package net.ruready.web.content.question.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.search.Logic;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.action.LoggedActionDispatcher;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * Question search-and-browse form: an elaborate version of a question broswing
 * form with additional search parameters.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 13, 2007
 */
public class SearchQuestionForm extends BrowseQuestionForm implements
		QuestionStaticMenuForm
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
	private static final Log logger = LogFactory.getLog(SearchQuestionForm.class);

	// ========================= FIELDS ====================================

	/**
	 * Search Mode. Must be non-null.
	 */
	private String searchMode = Logic.AND.name();

	private String id;

	private String formulation;

	private String level;

	private String type;

	/**
	 * Is question parametric or not.
	 */
	protected boolean parametric = false;

	// -------------------
	// Drop-down menu data
	// -------------------

	/**
	 * Contains drop-down menu fields of the question's item hierarchy, and
	 * related common operations that appear in search and edit question forms
	 * (course, topic, sub-topic menus).
	 */
	private SubTopicMenuGroupForm subTopicMenuGroupForm = new SubTopicMenuGroupForm();

	/**
	 * Drop-down menu option list of question types (e.g. academic, creative).
	 */
	private OptionList questionTypeOptions;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor required for a JavaBean.
	 */
	public SearchQuestionForm()
	{
		logger.debug("Creating a new form");
		reset();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print this form bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return StrutsUtil.formToString(this);
	}

	// ========================= IMPLEMENTATION: ValidatorActionForm =======

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		// Debugging printouts
		HttpRequestUtil.printRequestParameters(request);

		// If a setup method has been requested, skip validation
		if (!StrutsUtil.isMethodValidated(request))
		{
			return null;
		}

		// Check if checkboxes should be cleared
		if (request.getParameter("showNew") == null)
		{
			this.setShowNew(false);
		}

		if (request.getParameter("showActive") == null)
		{
			this.setShowActive(false);
		}

		if (request.getParameter("showUpdated") == null)
		{
			this.setShowUpdated(false);
		}

		if (request.getParameter("showDisabled") == null)
		{
			this.setShowDisabled(false);
		}

		if (request.getParameter("showDeleted") == null)
		{
			this.setShowDeleted(false);
		}

		if (request.getParameter("parametric") == null)
		{
			this.setParametric(false);
		}

		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		logger.debug("errors " + errors);

		// Custom validations should be added here
		logger.debug("validate");
		return errors;
	}

	/**
	 * @param mapping
	 * @param request
	 * @see net.ruready.web.content.question.form.BrowseQuestionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		logger.info("reset");

		// Reset item menu values so that if new values have been received
		// only for some of the menus, old values for the other values do not
		// persist across multiple responses.
		subTopicMenuGroupForm.reset();

		// For browse actions: reset browse fields
		String actionBrowseParam = request.getParameter("action_browse");
		if (!LoggedActionDispatcher.isIgnoredString(actionBrowseParam))
		{
			resetBrowseFields();
		}

		// Populate subjectId so that the ajax action populating the
		// corresponding menu has the proper id parameter (value)
		User user = HttpRequestUtil.findUser(request);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class,
				StrutsUtil.getWebApplicationContext(request), user);
		List<? extends Item> children = bdItem.findAllNonDeleted(Subject.class,
				ItemType.CATALOG);
		if (children.size() >= 1)
		{
			subTopicMenuGroupForm.setSubjectId(CommonNames.MISC.EMPTY_STRING
					+ children.get(0).getId());
		}
	}

	// ========================= IMPLEMENTATION: QuestionStaticMenuForm ====

	/**
	 * @return the questionTypeOptions
	 */
	public OptionList getQuestionTypeOptions()
	{
		return questionTypeOptions;
	}

	/**
	 * @param questionTypeOptions
	 *            the questionTypeOptions to set
	 */
	public void setQuestionTypeOptions(OptionList questionTypeOptions)
	{
		this.questionTypeOptions = questionTypeOptions;
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form values (this is NOT the Struts
	 * {@link #reset(ActionMapping, HttpServletRequest)} method).
	 */
	@Override
	public void reset()
	{
		logger.info("Resetting form");
		super.reset();

		if (subTopicMenuGroupForm == null)
		{
			subTopicMenuGroupForm = new SubTopicMenuGroupForm();
		}
		subTopicMenuGroupForm.reset();

		this.formulation = null;
		this.id = null;
		this.level = null;
		this.type = null;

		this.showNew = true;
		this.showActive = true;
		this.showUpdated = true;
		this.showDeleted = false;
		this.showDisabled = true;
		this.parametric = false;
	}

	// ========================= METHODS ===================================

	/**
	 * Reset form values except for the fields relevant to a browse table
	 * search.
	 */
	public void resetExceptBrowseFields()
	{
		logger.info("Resetting form except browse fields");

		this.searchMode = Logic.AND.name();
		this.formulation = null;
		this.id = null;

		// this.showNew = true;
		// this.showActive = true;
		// this.showUpdated = true;
		// this.showDeleted = false;
		// this.showDisabled = true;
	}

	/**
	 * Reset form values relevant to a browse table search.
	 */
	public void resetBrowseFields()
	{
		this.level = null;
		this.type = null;
		this.parametric = false;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Return the default number of results per page.
	 * 
	 * @return the default number of results per page
	 */
	@Override
	protected int getDefaultResultsPerPage()
	{
		return 15;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 */
	public String getSearchMode()
	{
		return searchMode;
	}

	/**
	 * @param searchMode
	 */
	public void setSearchMode(String searchMode)
	{
		this.searchMode = searchMode;
	}

	/**
	 * @return
	 */
	public String getFormulation()
	{
		return formulation;
	}

	/**
	 * @param formulation
	 */
	public void setFormulation(String formulation)
	{
		this.formulation = formulation;
	}

	/**
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public long getIdAsLong()
	{
		return TextUtil.getStringAsLong(id);
	}

	/**
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Set a new value for the showNew property.
	 * 
	 * @param showNew
	 *            the showNew to set
	 */
	public void setShowNew(boolean showNew)
	{
		this.showNew = showNew;
	}

	/**
	 * Set a new value for the showActive property.
	 * 
	 * @param showActive
	 *            the showActive to set
	 */
	public void setShowActive(boolean showActive)
	{
		this.showActive = showActive;
	}

	/**
	 * Set a new value for the showUpdated property.
	 * 
	 * @param showUpdated
	 *            the showUpdated to set
	 */
	public void setShowUpdated(boolean showUpdated)
	{
		this.showUpdated = showUpdated;
	}

	/**
	 * @param showDeleted
	 */
	public void setShowDeleted(boolean showDeleted)
	{
		this.showDeleted = showDeleted;
	}

	/**
	 * @param showDisabled
	 */
	public void setShowDisabled(boolean showDisabled)
	{
		this.showDisabled = showDisabled;
	}

	/**
	 * @return the subTopicMenuGroupForm
	 */
	public SubTopicMenuGroupForm getSubTopicMenuGroupForm()
	{
		return subTopicMenuGroupForm;
	}

	/**
	 * @param subTopicMenuGroupForm
	 *            the subTopicMenuGroupForm to set
	 */
	public void setSubTopicMenuGroupForm(SubTopicMenuGroupForm subTopicMenuGroupForm)
	{
		this.subTopicMenuGroupForm = subTopicMenuGroupForm;
	}

	/**
	 * Returns the level property.
	 * 
	 * @return the level
	 */
	public String getLevel()
	{
		return level;
	}

	/**
	 * @return
	 */
	public int getLevelAsInteger()
	{
		return TextUtil.getStringAsInteger(level);
	}

	/**
	 * Sets a new level property value.
	 * 
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level)
	{
		this.level = level;
	}

	/**
	 * Convert the {@link #searchMode} property to logic.
	 * 
	 * @return logic (enumerated type of the search framework)
	 */
	public Logic getSearchModeAsLogic()
	{
		return Logic.valueOf(searchMode);
	}

	/**
	 * Returns the parametric property.
	 * 
	 * @return the parametric
	 */
	public boolean isParametric()
	{
		return parametric;
	}

	/**
	 * Sets a new parametric property value.
	 * 
	 * @param parametric
	 *            the parametric to set
	 */
	public void setParametric(boolean parametric)
	{
		this.parametric = parametric;
	}
}
