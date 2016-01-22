/*****************************************************************************************
 * Source File: EditQuestionForm.java
 ****************************************************************************************/
package net.ruready.web.content.question.form;

import net.ruready.web.select.exports.OptionList;

/**
 * An abstraction for form beans that contain question property menus with static data.
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
 * @author Greg Felice <code>&lt;greg.felice@gmail.com&gt;</code>
 * @version Aug 1, 2007
 */
public interface QuestionStaticMenuForm
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * @return the courseOptions
	 */
	OptionList getQuestionTypeOptions();

	/**
	 * @param courseOptions
	 *            the courseOptions to set
	 */
	void setQuestionTypeOptions(OptionList questionTypeOptions);
}