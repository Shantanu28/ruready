/*****************************************************************************************
 * Source File: ChainRequest.java
 ****************************************************************************************/
package net.ruready.common.chain;

import java.util.List;
import java.util.Set;

import net.ruready.common.rl.CommonNames;

/**
 * A request object passed to a chain of handlers. Like <code>ServletRequest</code>,
 * holds a map of input/output attributes that may be manipulated by handlers. In
 * addition, contains a map of error messages similar to the <i>Struts</i> framework.
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
 * @version Jul 16, 2007
 */
public interface ChainRequest
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Returns the value of the named attribute as an <code>Object</code>. This method
	 * allows chain handlers to give input/output information in the request. This method
	 * returns <code>null</code> if no attribute of the given name exists.
	 * <p>
	 * Attribute names should follow the naming conventions of {@link CommonNames}.
	 * 
	 * @param name
	 *            a <code>String</code> specifying the name of the attribute
	 * @return an <code>Object</code> containing the value of the attribute, or
	 *         <code>null</code> if the attribute does not exist
	 */
	Object getAttribute(String name);

	/**
	 * Returns an <code>Set</code> containing the names of the attributes available to
	 * this request. This method returns an empty <code>Set</code> if the request has no
	 * attributes available to it.
	 * 
	 * @return an <code>Enumeration</code> of strings containing the names of the
	 *         request's attributes
	 */
	Set<String> getAttributeNames();

	/**
	 * Removes an attribute from the context of this request. If the attribute is not
	 * found, this method has no effect.
	 * <p>
	 * Attribute names should follow the naming conventions of {@link CommonNames}.
	 * 
	 * @param key
	 *            a <code>String</code> specifying the name of the attribute
	 */
	void removeAttribute(String key);

	/**
	 * Stores an attribute in the context of this request.
	 * <p>
	 * Attribute names should follow the naming conventions of {@link CommonNames}.
	 * 
	 * @param key
	 *            a <code>String</code> specifying the name of the attribute
	 * @param value
	 *            an <code>Object</code> containing the context of the request
	 */
	void setAttribute(String key, Object value);

	/**
	 * Retrieves all messages from the request.
	 * 
	 * @return handler message list
	 */
	List<HandlerMessage> getMessages();

	/**
	 * Removes all messages from the request.
	 */
	void clearMessages();

	/**
	 * Retrieves a message from the request.
	 * 
	 * @param index
	 *            message number
	 * @return handler message no. <code>index</code>
	 */
	HandlerMessage getMessage(int index);

	/**
	 * Stores a handler message in this request.
	 * 
	 * @param message
	 *            message to add
	 */
	void addMessage(HandlerMessage message);
}
