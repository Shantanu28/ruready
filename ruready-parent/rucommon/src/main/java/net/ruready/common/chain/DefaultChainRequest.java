/*****************************************************************************************
 * Source File: DefaultChainRequest.java
 ****************************************************************************************/
package net.ruready.common.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default implementation of a chain request that stores attributes in a map.
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
public class DefaultChainRequest implements ChainRequest
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultChainRequest.class);

	// ========================= FIELDS ====================================

	/**
	 * Request attributes placed/removed by handlers.
	 */
	private Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * Handler message list
	 */
	private List<HandlerMessage> messages = new ArrayList<HandlerMessage>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty request.
	 */
	public DefaultChainRequest()
	{

	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((attributes == null) ? 0 : attributes.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DefaultChainRequest other = (DefaultChainRequest) obj;
		if (attributes == null)
		{
			if (other.attributes != null)
				return false;
		}
		else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	/**
	 * Print a mapping element.
	 */
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer("--- Parser Request ---\n");
		for (String attributeName : this.getAttributeNames())
		{
			s.append(attributeName);
			s.append(CommonNames.TREE.SEPARATOR);
			s.append(this.getAttribute(attributeName));
			s.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		return s.toString();
	}

	// ========================= IMPLEMENTATION: ChainRequest ==============

	/**
	 * @see net.ruready.common.chain.ChainRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name)
	{
		return attributes.get(name);
	}

	/**
	 * @see net.ruready.common.chain.ChainRequest#getAttributeNames()
	 */
	public Set<String> getAttributeNames()
	{
		return attributes.keySet();
	}

	/**
	 * @see net.ruready.common.chain.ChainRequest#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String key)
	{
		attributes.remove(key);
	}

	/**
	 * @see net.ruready.common.chain.ChainRequest#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String key, Object value)
	{
		attributes.put(key, value);
	}

	/**
	 * Retrieves all messages from the request.
	 * 
	 * @return handler message list
	 * @see net.ruready.common.chain.ChainRequest#getMessages()
	 */
	public List<HandlerMessage> getMessages()
	{
		return messages;
	}

	/**
	 * Removes all messages from the request.
	 * 
	 * @see net.ruready.common.chain.ChainRequest#clearMessages()
	 */
	public void clearMessages()
	{
		messages.clear();
	}

	/**
	 * Retrieves a message from the request.
	 * 
	 * @param index
	 *            message number
	 * @return handler message no. <code>index</code>
	 * @see net.ruready.common.chain.ChainRequest#getMessage(int)
	 */
	public HandlerMessage getMessage(int index)
	{
		return messages.get(index);
	}

	/**
	 * Stores a handler message in this request.
	 * 
	 * @param message
	 *            message to add
	 * @see net.ruready.common.chain.ChainRequest#addMessage(net.ruready.common.chain.HandlerMessage)
	 */
	public void addMessage(HandlerMessage message)
	{
		messages.add(message);
	}
}
