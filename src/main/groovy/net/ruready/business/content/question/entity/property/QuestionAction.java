/*****************************************************************************************
 * Source File: QuestionType.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.property;

import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.state.QuestionStateID;
import net.ruready.common.state.StateMachineAction;

/**
 * Permissible {@link Question} actions from any given question state: enable, disable,
 * delete, undelete, etc. Uses call-backs to the {@link Question} object, so can be
 * thought of as a proxy that allows only certain status change operations to occur
 * through a uniform interface, instead of directly calling the question's methods.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 12, 2007
 */
public enum QuestionAction implements
		StateMachineAction<QuestionStateID, QuestionAction, Question>
{
	// ========================= ENUMERATED CONSTANTS ======================

	/**
	 * Edit a new question.
	 */
	ADD
	{
		/**
		 * @param question
		 * @see net.ruready.business.content.question.entity.property.QuestionAction#updateState(net.ruready.business.content.question.entity.Question)
		 */
		@Override
		public void updateState(Question question)
		{
			// Do nothing -- question constructor takes care of setting the default state
		}

		/**
		 * Does this action refer to a new question object or not.
		 * 
		 * @return true if and only if this action refers to a new question object or not
		 * @see net.ruready.business.content.question.entity.property.QuestionAction#isNewObject()
		 */
		@Override
		public boolean isNewObject()
		{
			return true;
		}

		/**
		 * Is this action an editing operation on a question object or not.
		 * 
		 * @return true if and only if this action involves an editing operation on a
		 *         question object
		 * @see net.ruready.business.content.question.entity.property.QuestionAction#isEdit()
		 */
		@Override
		public boolean isEdit()
		{
			return true;
		}
	},

	/**
	 * Edit/update an existing question.
	 */
	EDIT
	{
		/**
		 * @see net.ruready.common.state.StateMachineAction#updateState(net.ruready.common.state.StateMachine)
		 */
		@Override
		public void updateState(Question question)
		{
			question.update();
		}

		/**
		 * Is this action an editing operation on a question object or not.
		 * 
		 * @return true if and only if this action involves an editing operation on a
		 *         question object
		 * @see net.ruready.business.content.question.entity.property.QuestionAction#isEdit()
		 */
		@Override
		public boolean isEdit()
		{
			return true;
		}
	},

	/**
	 * Make a clone (deep-copy) of an existing question.
	 */
	CLONE
	{
		/**
		 * @param question
		 * @see net.ruready.common.state.StateMachineAction#updateState(net.ruready.common.state.StateMachine)
		 */
		@Override
		public void updateState(Question question)
		{
			// Nothing to do - no change in the original question's state
		}
	},

	/**
	 * Mark the question as deleted. Currently, deletes are soft-deletes and are
	 * completely reversible.
	 */
	DELETE
	{
		/**
		 * @see net.ruready.common.state.StateMachineAction#updateState(net.ruready.common.state.StateMachine)
		 */
		@Override
		public void updateState(Question question)
		{
			question.delete();
		}
	},

	/**
	 * Remove the deletion mark from this question.
	 */
	UNDELETE
	{
		/**
		 * @see net.ruready.common.state.StateMachineAction#updateState(net.ruready.common.state.StateMachine)
		 */
		@Override
		public void updateState(Question question)
		{
			question.undelete();
		}
	},

	/**
	 * Mark the question as disabled. Disables are always soft-disables and are completely
	 * reversible.
	 */
	DISABLE
	{
		/**
		 * @see net.ruready.common.state.StateMachineAction#updateState(net.ruready.common.state.StateMachine)
		 */
		@Override
		public void updateState(Question question)
		{
			question.disable();
		}
	},

	/**
	 * Remove the disabling mark from this question.
	 */
	ENABLE
	{
		/**
		 * @see net.ruready.common.state.StateMachineAction#updateState(net.ruready.common.state.StateMachine)
		 */
		@Override
		public void updateState(Question question)
		{
			question.enable();
		}
	};

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped by
	 * Hibernate to return unexpected names. As a rule, the type string should not contain
	 * spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= METHODS ===================================

	/**
	 * Web-layer dispatch action method name that corresponds to this action. This does
	 * not include the common method name prefix.
	 */
	public String getName()
	{
		return name().toLowerCase();
	}

	/**
	 * Does this action refer to a new question object or not.
	 * 
	 * @return true if and only if this action refers to a new question object
	 */
	public boolean isNewObject()
	{
		return false;
	}

	/**
	 * Is this action an editing operation on a question object or not.
	 * 
	 * @return true if and only if this action involves an editing operation on a question
	 *         object
	 */
	public boolean isEdit()
	{
		return false;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Perform a state machine transitional operation. Is responsible for updating the
	 * machine's state. This method has to be specifically declared in the enumerated-type
	 * class even though it is already formally declared in the {@link StateMachineAction}
	 * interface, because of the peculiarities of Java enums.
	 * 
	 * @param question
	 *            question to update
	 */
	public abstract void updateState(Question stateMachine);

}
