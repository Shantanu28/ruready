/*****************************************************************************************
 * Source File: QuestionState.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.state;

import java.util.Date;
import java.util.Set;

import net.ruready.business.content.question.entity.property.QuestionAction;
import net.ruready.common.eis.entity.PersistentEmeddable;
import net.ruready.common.state.State;

/**
 * Manages the question entity's state (State pattern).
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
public interface AbstractQuestionState extends PersistentEmeddable,
		State<QuestionStateID, AbstractQuestionState>
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS: Get current state =======

	/**
	 * Is the question newly created.
	 * 
	 * @return true if and only if the question is newly created
	 */
	boolean isNew();

	/**
	 * Is the question not newly created and active.
	 * 
	 * @return true if and only if the question is not newly created and active
	 */
	boolean isActive();

	/**
	 * Is the question updated.
	 * 
	 * @return true if and only if the question updated
	 */
	boolean isUpdated();

	/**
	 * Is the question marked as disabled.
	 * 
	 * @return true if and only if the question is marked as disabled
	 */
	boolean isDisabled();

	/**
	 * Is the question marked as deleted.
	 * 
	 * @return true if and only if the question is marked as deleted
	 */
	boolean isDeleted();

	/**
	 * Is the question marked as expired, i.e. will be deleted from the database on the
	 * next round of deletions.
	 * 
	 * @return true if and only if the question is marked as expired, i.e. will be deleted
	 *         from the database on the next round of deletions
	 */
	boolean isExpired();

	/**
	 * Return a sorted set of permissible actions based upon a question's state. If the
	 * question is deleted, undelete. If the question is disabled, enable. If question is
	 * not deleted or disabled, clone, disable, delete, edit.
	 * 
	 * @return sorted set of actions
	 */
	Set<QuestionAction> getActions();

	// ========================= ABSTRACT METHODS: Actions =================

	/**
	 * Mark a question for deletion.
	 */
	void delete();

	/**
	 * Undelete a question.
	 */
	void undelete();

	/**
	 * Disable a question.
	 */
	void disable();

	/**
	 * Enable a question.
	 */
	void enable();

	/**
	 * Mark a question as updated.
	 */
	void update();

	/**
	 * Time out a deleted question (make it expired) or a new question (make it active).
	 * 
	 * @param cutoffTime
	 *            if question was modified on or before this date, expire it; otherwise,
	 *            do nothing
	 */
	void timeout(final long cutoffTime);

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the question state identifier.
	 * 
	 * @return the question state identifier
	 */
	QuestionStateID getStateID();

	/**
	 * Return the date at which the question was last modified.
	 * 
	 * @return the date at which the question was last modified.
	 */
	Date getLastModified();

}
