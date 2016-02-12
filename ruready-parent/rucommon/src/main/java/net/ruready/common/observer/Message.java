/*****************************************************************************************
 * Source File: Message.java
 ****************************************************************************************/
package net.ruready.common.observer;

/**
 * This is an original addition to the standard observer pattern. The data transfer object
 * sent from the subject to the observer must implement this interface. If an observer
 * registers with multiple subjects, the {@link #getSubject()} method can be used to find
 * which subject sent the message, and take appropriate action in the
 * {@link Observer#update(Message)} method.
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
public interface Message
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Return the subject that sent this message.
	 * 
	 * @return the subject that sent this message
	 */
	Subject getSubject();
}
