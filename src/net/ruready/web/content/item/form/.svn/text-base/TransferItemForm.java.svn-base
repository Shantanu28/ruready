/*****************************************************************************************
 * Source File: TransferItemForm.java
 ****************************************************************************************/
package net.ruready.web.content.item.form;

import javax.servlet.http.HttpServletRequest;

import net.ruready.common.text.TextUtil;
import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Contains a list of selected items for actions like copy/move in a
 * "Norton-Commander"-like views.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @struts.form name="TransferItemForm"
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
public class TransferItemForm extends ActionForm // ValidatorForm
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
	private static final Log logger = LogFactory.getLog(TransferItemForm.class);

	// ========================= FIELDS ====================================

	// Source item form
	private String[] selectedItems;

	// ========================= CONSTRUCTORS ==============================

	public TransferItemForm()
	{

	}

	// ========================= METHODS ===================================

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
		// ==================================================================
		// Carry out all the Validator framework validations
		// ==================================================================
		ActionErrors errors = super.validate(mapping, request);

		// Custom validations should be added below
		logger.debug("validate");
		if (errors == null)
		{
			errors = new ActionErrors();
		}

		return errors;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		// Carry out all the Validator framework resets
		super.reset(mapping, request);

		// Custom reset operations should be added here
		logger.debug("reset");
		selectedItems = null;
	}

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

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the selectedItems
	 */
	public String[] getSelectedItems()
	{
		return selectedItems;
	}

	/**
	 * @param selectedItems
	 *            the selectedItems to set
	 */
	public void setSelectedItems(String[] selectedItems)
	{
		this.selectedItems = selectedItems;
	}

	/**
	 * Convert an element of the selected item string array to integer.
	 * 
	 * @param index
	 *            element index in <code>selectedItems</code>
	 * @return <code>selectedItems[i].longValue()</code> or <code>0</code> if the
	 *         conversion failed
	 */
	public long getSelectedItemId(final int index)
	{
		return TextUtil.getStringAsLong(selectedItems[index]);
	}
}
