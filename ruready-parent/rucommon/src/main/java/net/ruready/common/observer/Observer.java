/*****************************************************************************************
 * Source File: Observer.java
 ****************************************************************************************/
package net.ruready.common.observer;

/**
 * An observer in an observer pattern. The observable subject uses the call-back method
 * {@link #update(Message)} to push its notification on its change of state into each
 * observer.
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
public interface Observer
{
	/**
	 * Update the internal state of the observer as a result of a message sent by the
	 * subject.
	 * 
	 * @param message
	 *            message sent by the subject about changes in the subject's internal
	 *            state
	 */
	void update(Message message);
}
