/*****************************************************************************************
 * Source File: Subject.java
 ****************************************************************************************/
package net.ruready.common.observer;

/**
 * A subject / observable in an observer pattern. It is responsible for managing and
 * notifying observers of change of state in the subject.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 30, 2007
 */
public interface Subject
{
	// ========================= ABSTRACT METHODS ==========================
	/**
	 * Register an observer with this subject.
	 * 
	 * @param o
	 *            observer to add
	 */
	void addObserver(Observer o);

	/**
	 * Unsubscribe an observer with this subject.
	 * 
	 * @param o
	 *            observer to remove
	 */
	void removeObserver(Observer o);

	/**
	 * Notify all observers of a change in the subject's internal state. The
	 * <code>Observer.update()</code> method will be called in all observers from this
	 * method, with an optional message.
	 */
	void notifyObservers();
}
