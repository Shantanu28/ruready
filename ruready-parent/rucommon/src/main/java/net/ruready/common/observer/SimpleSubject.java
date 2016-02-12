/*****************************************************************************************
 * Source File: SimpleSubject.java
 ****************************************************************************************/
package net.ruready.common.observer;

import java.util.List;
import java.util.ArrayList;

/**
 * A standard implementation of the {@link Subject} using an {@link ArrayList} of
 * observers. The {@link #notifyObservers()} method is a template method that relies on
 * the abstract {@link #prepareMessage()} to send messages to observers. This object can
 * be composed with the &quot;real&quot; subject. Hence we call it a
 * <code>Transmitter</code>, whose only function is to communicate a change in state of
 * the subject.
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
public abstract class SimpleSubject implements Subject
{
	// ========================= FIELDS ====================================

	/**
	 * List of observers.
	 */
	private List<Observer> observers;

	/**
	 * Instead of implementing Subject, Subject can be a field in a higher level object,
	 * state, whose state is monitored by observers.
	 */
	private Subject subject;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a subject.
	 * 
	 * @param subject
	 */
	public SimpleSubject(Subject subject)
	{
		this.subject = subject;
		observers = new ArrayList<Observer>();
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Prepare a message based on the change in state of <code>state</code>. Because
	 * this method is abstract, sub-classes can customize their messages. It is protected,
	 * although we really wanted the entire <code>notifyObservers</code> to be as well.
	 */
	abstract protected Message prepareMessage();

	// ========================= IMPLEMENTATION: Subject ===================

	/**
	 * @return the subject
	 */
	public Subject getSubject()
	{
		return subject;
	}

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
	 * <code>Observer.update()</code> method will be called in all observers from this
	 * method, with an optional message.
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

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(Subject subject)
	{
		this.subject = subject;
	}

}
