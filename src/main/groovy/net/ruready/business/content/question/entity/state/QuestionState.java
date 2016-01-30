/*****************************************************************************************
 * Source File: QuestionState.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity.state;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.ruready.business.content.item.entity.audit.AuditMessage;
import net.ruready.business.content.question.entity.property.QuestionAction;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.observer.DataMessage;
import net.ruready.common.observer.Message;
import net.ruready.common.observer.Observer;
import net.ruready.common.visitor.Visitor;

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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 31, 2007
 */
// @Embeddable
@Entity
public class QuestionState implements AbstractQuestionState, PersistentEntity<Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(QuestionState.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * All possible states that can be assigned to this object.
	 */
	@Transient
	private static Map<QuestionStateID, AbstractQuestionHandler> possibleStates = new HashMap<QuestionStateID, AbstractQuestionHandler>();

	static
	{
		/**
		 * Initialization procedures of state handlers.
		 */
		possibleStates = new HashMap<QuestionStateID, AbstractQuestionHandler>();
		for (QuestionStateID questionStateId : QuestionStateID.values())
		{
			possibleStates.put(questionStateId, questionStateId.createQuestionHandler());
		}

	}

	// ========================= FIELDS ====================================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	/**
	 * Unique identifier of the current state.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	protected QuestionStateID stateID = QuestionStateID.NEW;

	/**
	 * This seems redundant - the latest {@link AuditMessage} already implements
	 * this field. Having said that, this redundancy makes it a little easier to
	 * retrieve the last modification date, which is more commonly used than
	 * earlier audit message information. We can't use our UTC time-stamp user
	 * type here because this is not an entity.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastModified = new Date();

	/**
	 * List of observers. Needed for the observer pattern.
	 */
	@Transient
	private List<Observer> observers = new ArrayList<Observer>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor with at least protected scope visibility is
	 * required for Hibernate and portability to other EJB 3 containers. This
	 * constructor populates no properties inside this class.
	 */
	public QuestionState()
	{
		super();
	}

	/**
	 * Initialize a state with a specified state ID.
	 * 
	 * @param stateID
	 *            state ID
	 */
	public QuestionState(final QuestionStateID stateID)
	{
		super();
		this.stateID = stateID;
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
		return stateID.getType();
	}

	/**
	 * @see net.ruready.common.discrete.Identifiable#getIdentifier()
	 */
	public QuestionStateID getIdentifier()
	{
		return stateID;
	}

	// ========================= IMPLEMENTATION:
	// Visitable<Vistitor<AbstractQState>> =====

	/**
	 * Let a visitor process this item. Part of the TreeVisitor pattern. This
	 * calls back the visitor's
	 * {@link Visitor#visit(net.ruready.common.visitor.Visitable)} method with
	 * this item type. Must be overridden by every item sub-class.
	 * 
	 * @param visitor
	 *            item visitor whose
	 *            {@link Visitor#visit(net.ruready.common.visitor.Visitable)}
	 *            method is invoked
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends Visitor<AbstractQuestionState>> void accept(B visitor)
	{
		visitor.visit(this);
	}

	// ========================= IMPLEMENTATION: AbstractQuestionHandler ===

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isActive()
	 */
	public boolean isActive()
	{
		return possibleStates.get(stateID).isActive();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isDeleted()
	 */
	public boolean isDeleted()
	{
		return possibleStates.get(stateID).isDeleted();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isDisabled()
	 */
	public boolean isDisabled()
	{
		return possibleStates.get(stateID).isDisabled();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isExpired()
	 */
	public boolean isExpired()
	{
		return possibleStates.get(stateID).isExpired();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isNew()
	 */
	public boolean isNew()
	{
		return possibleStates.get(stateID).isNew();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isUpdated()
	 */
	public boolean isUpdated()
	{
		return possibleStates.get(stateID).isUpdated();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#getActions()
	 */
	public Set<QuestionAction> getActions()
	{
		return possibleStates.get(stateID).getActions();
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionState#delete()
	 */
	public void delete()
	{
		possibleStates.get(stateID).delete(this);
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionState#disable()
	 */
	public void disable()
	{
		possibleStates.get(stateID).disable(this);
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionState#enable()
	 */
	public void enable()
	{
		possibleStates.get(stateID).enable(this);
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionState#timeout(long)
	 */
	public void timeout(final long cutoffTime)
	{
		possibleStates.get(stateID).timeout(this, cutoffTime);
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionState#undelete()
	 */
	public void undelete()
	{
		possibleStates.get(stateID).undelete(this);
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionState#update()
	 */
	public void update()
	{
		possibleStates.get(stateID).update(this);
	}

	// ========================= IMPLEMENTATION: Subject ===================

	/**
	 * @see net.ruready.common.observer.Subject#addObserver(net.ruready.common.observer.Observer)
	 */
	public void addObserver(Observer o)
	{
		observers.add(o);
	}

	/**
	 * Unsubscribe an observer with this subject.
	 * 
	 * @param o
	 *            observer to remove
	 * @see net.ruready.common.observer.Subject#removeObserver(net.ruready.common.observer.Observer)
	 */
	public void removeObserver(Observer o)
	{
		observers.remove(o);
	}

	/**
	 * Notify all observers of a change in the subject's internal state. The
	 * <code>Observer.update()</code> method will be called in all observers
	 * from this method, with an optional message.
	 * 
	 * @see net.ruready.common.observer.Subject#notifyObservers()
	 */
	public void notifyObservers()
	{
		Message message = prepareMessage();
		for (Observer observer : observers)
		{
			observer.update(message);
		}
	}

	/**
	 * Prepare a concrete message. This should use the subject's getters
	 * methods.
	 * 
	 * @return the message
	 * @see net.ruready.common.observer.SimpleSubject#prepareMessage()
	 */
	protected Message prepareMessage()
	{
		Message message = new DataMessage(this);
		return message;
	}

	// ========================= METHODS ===================================

	/**
	 * Set a new ID in this state object. Also updates the lastModified member
	 * to the current date.
	 * 
	 * @param stateID
	 *            the new state id to set
	 */
	protected void setStateID(final QuestionStateID stateID)
	{
		this.stateID = stateID;
		lastModified = new Date();
		notifyObservers();
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> =====

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the stateID
	 */
	public QuestionStateID getStateID()
	{
		return stateID;
	}

	/**
	 * @return
	 */
	public Date getLastModified()
	{
		return lastModified;
	}

}
