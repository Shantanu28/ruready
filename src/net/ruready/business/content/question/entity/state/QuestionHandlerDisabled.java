/*****************************************************************************************
 * Source File: QuestionState.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.state;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import net.ruready.business.content.question.entity.property.QuestionAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The state of a disabled question.
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
 * @version Jul 31, 2007
 */
class QuestionHandlerDisabled extends QuestionHandlerStub
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(QuestionHandlerDisabled.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected QuestionHandlerDisabled()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Identifiable =================

	/**
	 * @see net.ruready.common.discrete.Identifiable#getIdentifier()
	 * @return the type of this item
	 */
	/**
	 * @see net.ruready.business.content.question.entity.state.QuestionHandlerStub#getIdentifier()
	 */
	public QuestionStateID getIdentifier()
	{
		return QuestionStateID.DISABLED;
	}

	// ========================= IMPLEMENTATION: QuestionState =============

	/**
	 * Is the question marked as disabled.
	 * 
	 * @return true if and only if the question is marked as disabled.
	 */
	@Override
	public boolean isDisabled()
	{
		return true;
	}

	/**
	 * Return a sorted set of permissible actions based upon a question's state. If the
	 * question is deleted, undelete. If the question is disabled, enable. If question is
	 * not deleted or disabled, clone, disable, delete, edit.
	 * 
	 * @return sorted set of actions
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#getActions()
	 */
	@Override
	public Set<QuestionAction> getActions()
	{
		Set<QuestionAction> actions = Collections
				.synchronizedSortedSet(new TreeSet<QuestionAction>());
		actions.add(QuestionAction.ENABLE);
		return actions;
	}

	/**
	 * Enable a question.
	 * 
	 * @see net.ruready.business.content.question.entity.state.QuestionHandlerStub#enable(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	@Override
	public void enable(final AbstractQuestionState state)
	{
		updateState(state, QuestionStateID.UPDATED);
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

}
