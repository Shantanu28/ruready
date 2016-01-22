/*****************************************************************************************
 * Source File: DataMessage.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity;

import net.ruready.business.content.question.entity.state.QuestionStateID;
import net.ruready.common.observer.DataMessage;

/**
 * An observer pattern message that signals a change in a question's state.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 31, 2007
 */
public class QuestionStateChangeMessage extends DataMessage
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * The sender of this message.
	 */
	private final Question question;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a message from a sender
	 * 
	 * @param subject
	 *            The sender of this message
	 */
	public QuestionStateChangeMessage(final Question subject)
	{
		super(subject);
		question = subject;
	}

	// ========================= IMPLEMENTATION: Message ===================

	// ========================= METHODS ===================================

	/**
	 * Return the new state of the observed question
	 * 
	 * @return the new state of the observed question
	 */
	public QuestionStateID getNewStateID()
	{
		return question.getStateID();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the question
	 */
	public Question getQuestion()
	{
		return question;
	}

}
