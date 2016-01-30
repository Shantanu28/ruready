/*****************************************************************************************
 * Source File: QuestionStateID.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.state;

import net.ruready.common.state.StateID;

/**
 * An identifier and enumerated factory of question states.
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
 * @version Aug 4, 2007
 */
public enum QuestionStateID implements StateID
{
	// ========================= ENUMERATED TYPES ==========================

	NEW
	{

		@Override
		public AbstractQuestionHandler createQuestionHandler()
		{
			return new QuestionHandlerNew();
		}

	},

	ACTIVE
	{

		@Override
		public AbstractQuestionHandler createQuestionHandler()
		{
			return new QuestionHandlerActive();
		}

	},

	UPDATED
	{

		@Override
		public AbstractQuestionHandler createQuestionHandler()
		{
			return new QuestionHandlerUpdated();
		}

	},

	DISABLED
	{

		@Override
		public AbstractQuestionHandler createQuestionHandler()
		{
			return new QuestionHandlerDisabled();
		}

	},

	DELETED
	{

		@Override
		public AbstractQuestionHandler createQuestionHandler()
		{
			return new QuestionHandlerDeleted();
		}

	},

	EXPIRED
	{

		@Override
		public AbstractQuestionHandler createQuestionHandler()
		{
			return new QuestionHandlerExpired();
		}

	};

	// ========================= FIELDS ====================================

	// ========================= STATIC METHODS ============================

	// ========================= IMPLEMENTATION: Identifier ===================

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

	// ========================= IMPLEMENTATION: ImmutableTreeNode =========

	// ========================= METHODS ===================================
	/**
	 * A factory method that creates a question state object corresponding to
	 * this type.
	 * 
	 * @return question state object
	 */
	abstract public AbstractQuestionHandler createQuestionHandler();

	// ========================= GETTERS & SETTERS =========================
}
