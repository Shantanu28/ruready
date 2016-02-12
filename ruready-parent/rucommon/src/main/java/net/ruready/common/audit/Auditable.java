/*****************************************************************************************
 * Source File: Auditable.java
 ****************************************************************************************/
package net.ruready.common.audit;

import java.util.List;

/**
 * An auditable object that has a one-to-many relationship to {@link Message}s.
 * <p>
 * Note: this is the standard visitor pattern. A slower version which however considerably
 * simplifies the code is the reflection-based visitor pattern. See
 * {@link http://surguy.net/articles/visitor-with-reflection.xml}
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
 * @version Sep 8, 2007
 */
public interface Auditable<V extends Comparable<? super V>, T extends Message> extends
		Versioned<V>
{
	// ========================= ABSTRACT METHODS ===========================

	/**
	 * Returns the latest audit message associated with this object.
	 * 
	 * @return the latestMessage
	 */
	T getLatestMessage();

	/**
	 * Add a message and set it to be the latest audit message associated with this
	 * object.
	 * 
	 * @param latestMessage
	 *            the message to add
	 */
	void addMessage(T message);

	/**
	 * Returns the list of messages associated with this object.
	 * 
	 * @return the messages
	 */
	List<T> getMessages();

	/**
	 * Sets the list of messages associated with this object.
	 * 
	 * @param messages
	 *            the messages to set
	 */
	void setMessages(List<T> messages);
}
