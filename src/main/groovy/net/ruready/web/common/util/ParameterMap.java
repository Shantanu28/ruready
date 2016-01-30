/*****************************************************************************************
 * Source File: LineReaderTarget.java
 ****************************************************************************************/
package net.ruready.web.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.ruready.common.text.TextUtil;
import net.sf.json.JSONString;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A request parameter map. Contains useful utilitie sfor JSON conversions.
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
 * @version Sep 8, 2007
 */
public class ParameterMap implements Map<String, String>, JSONString
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ParameterMap.class);

	// ========================= FIELDS ====================================

	/**
	 * We decorate this map object.
	 */
	private final Map<String, String> parameters = new HashMap<String, String>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an initial test file line target.
	 */
	public ParameterMap()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#String()
	 */
	@Override
	public String toString()
	{
		return parameters.toString();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		return parameters.equals(o);
	}

	/**
	 * @return
	 * @see java.util.Map#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return parameters.hashCode();
	}

	// ========================= IMPLEMENTATION: JSONString ================

	/**
	 * @return
	 * @see net.sf.json.JSONString#toJSONString()
	 */
	public String toJSONString()
	{
		StringBuffer s = TextUtil.emptyStringBuffer().append("{");
		Iterator<String> iterator = parameters.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			String value = parameters.get(key);
			s.append("\"").append(key).append("\"").append(":").append("\"")
					.append(value).append("\"");
			if (iterator.hasNext())
			{
				s.append(", ");
			}
		}
		s.append("}");
		return s.toString();
	}

	// ========================= IMPLEMENTATION: Map =======================

	/**
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear()
	{
		parameters.clear();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key)
	{
		return parameters.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value)
	{
		return parameters.containsValue(value);
	}

	/**
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<String, String>> entrySet()
	{
		return parameters.entrySet();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public String get(Object key)
	{
		return parameters.get(key);
	}

	/**
	 * @return
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty()
	{
		return parameters.isEmpty();
	}

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	public Set<String> keySet()
	{
		return parameters.keySet();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public String put(String key, String value)
	{
		return parameters.put(key, value);
	}

	/**
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends String, ? extends String> m)
	{
		parameters.putAll(m);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public String remove(Object key)
	{
		return parameters.remove(key);
	}

	/**
	 * @return
	 * @see java.util.Map#size()
	 */
	public int size()
	{
		return parameters.size();
	}

	/**
	 * @return
	 * @see java.util.Map#values()
	 */
	public Collection<String> values()
	{
		return parameters.values();
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

}
