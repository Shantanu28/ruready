/*****************************************************************************************
 * Source File: SearchQuestionForm.java
 ****************************************************************************************/
package net.ruready.web.content.question.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.web.common.form.PostPopulatableForm;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HtmlUtil;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Question browse form.
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
 * @version Aug 24, 2007
 */
public class BrowseQuestionForm extends ValidatorActionForm implements Resettable,
		PostPopulatableForm
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
	private static final Log logger = LogFactory.getLog(BrowseQuestionForm.class);

	// ========================= FIELDS ====================================

	// --------------------------------------
	// Web layer's flow references
	// --------------------------------------

	/**
	 * ID of item whose content is browsed.
	 */
	private String parentId;

	/**
	 * Type of item whose content is browsed.
	 */
	private String parentType;

	/**
	 * Identifier of a Struts forward action to forward the request to at the
	 * end of the current action.
	 */
	private String customForward;

	// --------------------------------------
	// Filters and options
	// --------------------------------------

	/**
	 * Number of results to show per page.
	 */
	private String resultsPerPage = CommonNames.MISC.EMPTY_STRING;

	/**
	 * Show new questions flag. Immutable in this implementation.
	 */
	protected boolean showNew = true;

	/**
	 * Show active questions flag. Immutable in this implementation.
	 */
	protected boolean showActive = true;

	/**
	 * Show updated questions flag. Immutable in this implementation.
	 */
	protected boolean showUpdated = true;

	/**
	 * Show deleted questions flag. Immutable in this implementation.
	 */
	protected boolean showDeleted = true;

	/**
	 * Show disabled questions flag. Immutable in this implementation.
	 */
	protected boolean showDisabled = true;

	/**
	 * Display tag preference parameter: page number.
	 */
	private String displayTagPage;

	/**
	 * Display tag preference parameter: column to sort by.
	 */
	private String displayTagSortColumn;

	/**
	 * Display tag preference parameter: sorting order.
	 */
	private String displayTagOrder;

	private boolean blankForm = true;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor required for a JavaBean.
	 */
	public BrowseQuestionForm()
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
		// If a setup method has been requested, skip validation
		if (!StrutsUtil.isMethodValidated(request))
		{
			return null;
		}

		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		logger.debug("errors " + errors);

		// Custom validations should be added here
		logger.debug("validate");
		return errors;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1)
	{
		logger.info("reset");
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form fields. This is not the same as the Struts ActionForm
	 * reset method.
	 * 
	 * @see net.ruready.common.eis.Resettable#reset()
	 */
	public void reset()
	{
		logger.info("Resetting form");

		this.blankForm = true;

		this.resultsPerPage = CommonNames.MISC.EMPTY_STRING + getDefaultResultsPerPage();
		this.showNew = true;
		this.showActive = true;
		this.showUpdated = true;
		this.showDeleted = true;
		this.showDisabled = true;

		this.resetDisplayTagFields();
	}

	// ========================= IMPLEMENTATION: PostPopulatableForm =======

	/**
	 * Custom post-population following standard form population by
	 * {@link BeanUtils}. This is where to read the display tag library's
	 * request parameters to our form bean properties.
	 * 
	 * @param request
	 *            client's request object
	 * @see net.ruready.web.common.form.PostPopulatableForm#postPopulate()
	 */
	public void postPopulate(final HttpServletRequest request)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("postPopulate()");
		}
		setDisplayTagFields(request);
	}

	// ========================= METHODS ===================================

	/**
	 * Reset display tag library state request parameters in the form (sorting,
	 * pagination).
	 */
	public final void resetDisplayTagFields()
	{
		logger.info("Resetting display tag fields");

		this.displayTagPage = null;
		this.displayTagSortColumn = null;
		this.displayTagOrder = null;
	}

	/**
	 * Set display tag library state request parameters in the form (sorting,
	 * pagination) from request parameters.
	 * 
	 * @param request
	 *            client's request object
	 */
	public final void setDisplayTagFields(final HttpServletRequest request)
	{
		// Save display tag library state request parameters in the form
		// (sorting,
		// pagination) in this form
		{
			String param = request
					.getParameter(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_PAGE);
			if (!TextUtil.isEmptyTrimmedString(param))
			{
				setDisplayTagPage(param);
			}
		}

		{
			String param = request
					.getParameter(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_SORT_COLUMN);
			if (!TextUtil.isEmptyTrimmedString(param))
			{
				setDisplayTagSortColumn(param);
			}
		}

		{
			String param = request
					.getParameter(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_SORT_ORDER);
			if (!TextUtil.isEmptyTrimmedString(param))
			{
				setDisplayTagOrder(param);
			}
		}
	}

	/**
	 * Append a query string to a struts path (URL) with the corresponding
	 * parameter values to the display tag fields of this form. The original URL
	 * should of course not contain these parameters to avoid duplication. If
	 * old display tag parameter values are found, they are replaced.
	 * 
	 * @param url
	 *            original URL
	 * @return modified query string with new display tag values
	 */
	public final String appendDisplayTagFieldsToUrl(final String url)
	{
		// Add new display tag parameter values to map;
		// override old values of parameters if found in the original URL
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_PAGE, displayTagPage);
		parameters.put(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_SORT_COLUMN,
				displayTagSortColumn);
		parameters.put(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_SORT_ORDER, displayTagOrder);

		// Build query string from new parameters
		return HtmlUtil.buildUrl(url, parameters);
	}

	/**
	 * Append a query string to a URL query string with the corresponding
	 * parameter values to the display tag fields of this form. The original URL
	 * should of course not contain these parameters to avoid duplication. If
	 * old display tag parameter values are found, they are replaced.
	 * 
	 * @param originalQueryString
	 *            original query string
	 * @return modified parameter map with new display tag values
	 */
	public final Map<String, String> appendDisplayTagFieldsToParameters(
			final String originalQueryString)
	{
		// Retrieve all parameters from originalQueryString by tokenization.
		// Map<String, String> parameters = Collections
		// .synchronizedMap(new TreeMap<String, String>());
		Map<String, String> parameters = HtmlUtil.getQueryMap(originalQueryString);

		// Add new display tag parameter values to map;
		// override old values of parameters if found in the original URL
		parameters.put(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_PAGE, displayTagPage);
		parameters.put(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_SORT_COLUMN,
				displayTagSortColumn);
		parameters.put(WebAppNames.REQUEST.PARAM.DISPLAY_TAG_SORT_ORDER, displayTagOrder);

		return parameters;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Return the default number of results per page.
	 * 
	 * @return the default number of results per page
	 */
	protected int getDefaultResultsPerPage()
	{
		return 10;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the parentId property.
	 * 
	 * @return the parentId
	 */
	public String getParentId()
	{
		return parentId;
	}

	/**
	 * Sets a new parentId property value.
	 * 
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(final String parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * Returns the parentType property.
	 * 
	 * @return the parentType
	 */
	public String getParentType()
	{
		return parentType;
	}

	/**
	 * Sets a new parentType property value.
	 * 
	 * @param parentType
	 *            the parentType to set
	 */
	public void setParentType(String parentType)
	{
		this.parentType = parentType;
	}

	/**
	 * @return
	 */
	public String getResultsPerPage()
	{
		return resultsPerPage;
	}

	/**
	 * Programmatically enforces some validation rules.
	 * 
	 * @param resultsPerPage
	 */
	public void setResultsPerPage(String resultsPerPage)
	{
		// Parse input string value into an integer
		// Programmatically enforce some validation rules.
		int value = TextUtil.getStringAsInteger(resultsPerPage);
		if (value == CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			// Default value
			value = getDefaultResultsPerPage();
		}
		if (value < 5)
		{
			value = 5;
		}
		if (value > 1000)
		{
			value = 1000;
		}

		// Convert back to a string
		this.resultsPerPage = CommonNames.MISC.EMPTY_STRING + value;
	}

	/**
	 * Return the showNew property.
	 * 
	 * @return the showNew
	 */
	public boolean isShowNew()
	{
		return showNew;
	}

	/**
	 * Return the showUpdated property.
	 * 
	 * @return the showUpdated
	 */
	public boolean isShowUpdated()
	{
		return showUpdated;
	}

	/**
	 * Returns the showActive property.
	 *
	 * @return the showActive
	 */
	public boolean isShowActive()
	{
		return showActive;
	}

	/**
	 * @return
	 */
	public boolean isShowDeleted()
	{
		return showDeleted;
	}

	/**
	 * @return
	 */
	public boolean isShowDisabled()
	{
		return showDisabled;
	}

	/**
	 * @return the displayTagPage
	 */
	public String getDisplayTagPage()
	{
		return displayTagPage;
	}

	/**
	 * @param displayTagPage
	 *            the displayTagPage to set
	 */
	protected void setDisplayTagPage(String displayTagPage)
	{
		this.displayTagPage = displayTagPage;
	}

	/**
	 * @return the displayTagSortColumn
	 */
	public String getDisplayTagSortColumn()
	{
		return displayTagSortColumn;
	}

	/**
	 * @param displayTagSortColumn
	 *            the displayTagSortColumn to set
	 */
	protected void setDisplayTagSortColumn(String displayTagSortColumn)
	{
		this.displayTagSortColumn = displayTagSortColumn;
	}

	/**
	 * @return the displayTagOrder
	 */
	public String getDisplayTagOrder()
	{
		return displayTagOrder;
	}

	/**
	 * @param displayTagOrder
	 *            the displayTagOrder to set
	 */
	protected void setDisplayTagOrder(String displayTagOrder)
	{
		this.displayTagOrder = displayTagOrder;
	}

	/**
	 * @return the blankForm
	 */
	public boolean isBlankForm()
	{
		return blankForm;
	}

	/**
	 * @param blankForm
	 *            the blankForm to set
	 */
	public void setBlankForm(boolean blankForm)
	{
		this.blankForm = blankForm;
	}

	/**
	 * Returns the customForward property.
	 * 
	 * @return the customForward
	 */
	public String getCustomForward()
	{
		return customForward;
	}

	/**
	 * Sets a new customForward property value.
	 * 
	 * @param customForward
	 *            the customForward to set
	 */
	public void setCustomForward(String customForward)
	{
		this.customForward = customForward;
	}
}
