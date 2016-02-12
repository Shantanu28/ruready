/*****************************************************************************************
 * Source File: QuestionType.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.property;

import net.ruready.common.discrete.Identifier;

/**
 * Types of sets of questions for which we compute question totals.
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
 * @version Aug 12, 2007
 */
public enum QuestionCountType implements Identifier
{
	// ========================= ENUMERATED CONSTANTS ======================

	ACADEMIC, CREATIVE, PARAMETRIC, TOTAL;

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Identifier ================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped
	 * by Hibernate to return unexpected names. As a rule, the type string
	 * should not contain spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

}
