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
 * The state of a question that has been recently updated (edited).
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
class QuestionHandlerUpdated extends QuestionHandlerStub
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(QuestionHandlerUpdated.class);

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
	protected QuestionHandlerUpdated()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Identifiable =================

	/**
	 * @see net.ruready.common.discrete.Identifiable#getIdentifier()
	 * @return the type of this item
	 */
	public QuestionStateID getIdentifier()
	{
		return QuestionStateID.UPDATED;
	}

	// ========================= IMPLEMENTATION: QuestionState =============

	/**
	 * Is the question updated.
	 * 
	 * @return true if and only if the question updated
	 */
	@Override
	public boolean isUpdated()
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
		actions.add(QuestionAction.EDIT);
		actions.add(QuestionAction.CLONE);
		actions.add(QuestionAction.DISABLE);
		actions.add(QuestionAction.DELETE);
		return actions;
	}

	/**
	 * Mark a question for deletion.
	 * 
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#delete(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	@Override
	public void delete(final AbstractQuestionState state)
	{
		updateState(state, QuestionStateID.DELETED);
	}

	/**
	 * Disable a question.
	 * 
	 * @see net.ruready.business.content.question.entity.state.QuestionHandlerStub#disable(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	@Override
	public void disable(final AbstractQuestionState state)
	{
		updateState(state, QuestionStateID.DISABLED);
	}

	/**
	 * Update a question.
	 * 
	 * @see net.ruready.business.content.question.entity.state.QuestionHandlerStub#update(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	@Override
	public void update(final AbstractQuestionState state)
	{
		updateState(state, QuestionStateID.UPDATED);
	}

	/**
	 * Time out an updated question: remove the updated mark and make it active.
	 * 
	 * @param cutoffTime
	 *            if question was modified on or before this date, expire it; otherwise,
	 *            do nothing
	 * @see net.ruready.business.content.question.entity.state.QuestionHandlerStub#timeout(net.ruready.business.content.question.entity.state.AbstractQuestionState,
	 *      long)
	 */
	@Override
	public void timeout(final AbstractQuestionState state, final long cutoffTime)
	{
		if (state.getLastModified().getTime() <= cutoffTime)
		{
			updateState(state, QuestionStateID.ACTIVE);
		}
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

}
