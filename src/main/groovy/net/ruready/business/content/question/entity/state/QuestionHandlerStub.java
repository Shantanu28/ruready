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
 * Provides stubs (default implementations) for a question state.
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
abstract class QuestionHandlerStub implements AbstractQuestionHandler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(QuestionHandlerStub.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * Passed from the {@link #updateState(QuestionState, QuestionStateID)} to the
	 * {@link #visit(AbstractQuestionState)} method.
	 */
	private QuestionStateID newStateID;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is required for
	 * Hibernate and portability to other EJB 3 containers. This constructor populates no
	 * properties inside this class.
	 */
	protected QuestionHandlerStub()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getType();
	}

	// ========================= IMPLEMENTATION: Identifiable =================

	/**
	 * Return the item type. Delegates to QuestionStateID.
	 * 
	 * @see net.ruready.common.discrete.Identifier#getType()
	 */
	public final String getType()
	{
		return getIdentifier().getType();
	}

	// ========================= IMPLEMENTATION: QuestionState =============

	/**
	 * Is the question newly created.
	 * 
	 * @return true if and only if the question is newly created.
	 */
	public boolean isNew()
	{
		return false;
	}

	/**
	 * Is the question not newly created and active.
	 * 
	 * @return true if and only if the question is not newly created and active.
	 */
	public boolean isActive()
	{
		return false;
	}

	/**
	 * Is the question updated.
	 * 
	 * @return true if and only if the question updated
	 */
	public boolean isUpdated()
	{
		return false;
	}

	/**
	 * Is the question marked as disabled.
	 * 
	 * @return true if and only if the question is marked as disabled.
	 */
	public boolean isDisabled()
	{
		return false;
	}

	/**
	 * Is the question marked as deleted.
	 * 
	 * @return true if and only if the question is marked as deleted.
	 */
	public boolean isDeleted()
	{
		return false;
	}

	/**
	 * Is the question marked as expired, i.e. will be deleted from the database on the
	 * next round of deletions.
	 * 
	 * @return true if and only if the question is marked as expired, i.e. will be deleted
	 *         from the database on the next round of deletions
	 */
	public boolean isExpired()
	{
		return false;
	}

	/**
	 * Return a sorted set of permissible actions based upon a question's state. If the
	 * question is deleted, undelete. If the question is disabled, enable. If question is
	 * not deleted or disabled, clone, disable, delete, edit.
	 * 
	 * @return sorted set of actions
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#getActions()
	 */
	public Set<QuestionAction> getActions()
	{
		Set<QuestionAction> actions = Collections
				.synchronizedSortedSet(new TreeSet<QuestionAction>());
		return actions;
	}

	/**
	 * @param state
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#delete(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	public void delete(AbstractQuestionState state)
	{

	}

	/**
	 * @param state
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#disable(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	public void disable(AbstractQuestionState state)
	{

	}

	/**
	 * @param state
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#enable(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	public void enable(AbstractQuestionState state)
	{

	}

	/**
	 * @param state
	 * @param cutoffTime
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#timeout(net.ruready.business.content.question.entity.state.AbstractQuestionState,
	 *      long)
	 */
	public void timeout(AbstractQuestionState state, long cutoffTime)
	{

	}

	/**
	 * @param state
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#undelete(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	public void undelete(AbstractQuestionState state)
	{

	}

	/**
	 * @param state
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#update(net.ruready.business.content.question.entity.state.AbstractQuestionState)
	 */
	public void update(AbstractQuestionState state)
	{

	}

	// ========================= IMPLEMENTATION: Visitor<AbstractQState> ===

	/**
	 * Set the state of a question. Sets the instance variable <code>newStateID</code>
	 * to be the new state.
	 * 
	 * @see net.ruready.common.visitor.Visitor#visit(net.ruready.common.visitor.Visitable)
	 */
	public void visit(AbstractQuestionState visitable)
	{
		((QuestionState) visitable).setStateID(newStateID);
	}

	// ========================= METHODS ===================================

	/**
	 * Set the state of a question.
	 * 
	 * @param question
	 *            question whose state is managed here
	 * @param stateID
	 *            new state ID to set
	 */
	protected void updateState(final AbstractQuestionState state,
			final QuestionStateID stateID)
	{
		newStateID = stateID;
		state.accept(this);
	}

	// ========================= GETTERS & SETTERS =========================
}
