/*****************************************************************************************
 * Source File: EditItemUtil.java
 ****************************************************************************************/
package net.ruready.web.content.question.util;

import net.ruready.common.misc.Utility;
import net.ruready.web.content.question.form.BrowseQuestionForm;
import net.ruready.web.content.question.form.EditQuestionForm;
import net.ruready.web.content.question.form.EditQuestionFullForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

/**
 * Centralizes utilities related to custom question editing in the content
 * management component.
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
 * @version Jul 29, 2007
 */
public class EditQuestionUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditQuestionUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private EditQuestionUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Extract the question form's property from the action form attached to
	 * this action.
	 * 
	 * @param form
	 *            action form
	 * @return form.itemForm, cast to a {@link EditQuestionForm}
	 */
	public static EditQuestionForm getQuestionForm(final ActionForm form)
	{
		EditQuestionFullForm editQuestionFullForm = (EditQuestionFullForm) form;
		EditQuestionForm eqForm = (EditQuestionForm) editQuestionFullForm.getItemForm();
		return eqForm;
	}

	/**
	 * Append display tag parameters to a Struts forward using form bean
	 * properties.
	 * 
	 * @param forward
	 *            action forward object (will be cloned upon returning from this
	 *            method)
	 * @param browseQuestionForm
	 *            browse form containing DT parameters
	 * @return cloned, modified forward (also good for method chaining)
	 */
	public static ActionForward appendDisplayTagParameters(final ActionForward forward,
			final BrowseQuestionForm browseQuestionForm)
	{
		// Make a copy of the forward action
		ActionForward newForward = new ActionForward(forward);

		if (browseQuestionForm != null)
		{
			// Add parameters to the forward action
			String newPath = browseQuestionForm.appendDisplayTagFieldsToUrl(newForward
					.getPath());
			newForward.setPath(newPath);
		}

		return newForward;
	}
}
