/*****************************************************************************************
 * Source File: XmlUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import java.util.Map;

import net.ruready.common.misc.Utility;

/**
 * XML-related utils. Includes various XML tag generation methods. Used in
 * server actions processing AJAX requests.
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
public class XmlUtil implements Utility
{
	// ========================= CONSTANTS =================================

	// ------------------------------------------------
	// XML Element syntax symbol conventions
	// ------------------------------------------------

	// Start of an element syntax:
	// ELEMENT_START_OPEN + element_name + ELEMENT_START_CLOSE
	public static final String ELEMENT_START_OPEN = "<";

	public static final String ELEMENT_START_CLOSE = ">";

	// End of an element syntax:
	// ELEMENT_END_OPEN + element_name + ELEMENT_END_CLOSE

	public static final String ELEMENT_END_OPEN = "</";

	public static final String ELEMENT_END_CLOSE = ">";

	// Empty element syntax:
	// ELEMENT_EMPTY_OPEN + element_name + ELEMENT_EMPTY_CLOSE

	public static final String ELEMENT_EMPTY_OPEN = "<";

	public static final String ELEMENT_EMPTY_CLOSE = "/>";

	// Attribute syntax within an element start:
	// (ATTRIBUTE_SEPARATOR + attribute_name + ATTRIBUTE_EQUALS +
	// ATTRIBUTE_VALUE_ESCAPE_START + attribute_value +
	// ATTRIBUTE_VALUE_ESCAPE_END)*
	public static final String ATTRIBUTE_SEPARATOR = " ";

	public static final String ATTRIBUTE_EQUALS = "=";

	public static final String ATTRIBUTE_VALUE_ESCAPE_START = "\"";

	public static final String ATTRIBUTE_VALUE_ESCAPE_END = "\"";

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private XmlUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Print an XML opening tag with no attributes.
	 * 
	 * @param tag
	 *            tag name
	 * @return opening tag
	 */
	public static StringBuffer openTag(final String tag)
	{
		return new StringBuffer(XmlUtil.ELEMENT_START_OPEN + tag
				+ XmlUtil.ELEMENT_START_CLOSE);
	}

	/**
	 * Print an XML opening tag with attributes.
	 * 
	 * @param tag
	 *            tag name
	 * @param attributes
	 *            list of attributes
	 * @return opening tag
	 */
	public static StringBuffer openTag(final String tag,
			final Map<String, String> attributes)
	{
		StringBuffer s = new StringBuffer(XmlUtil.ELEMENT_START_OPEN)
				.append(tag);
		for (Map.Entry<String, String> attribute : attributes.entrySet())
		{
			s.append(XmlUtil.ATTRIBUTE_SEPARATOR);
			s.append(attribute.getKey());
			s.append(XmlUtil.ATTRIBUTE_EQUALS);
			s.append(XmlUtil.ATTRIBUTE_VALUE_ESCAPE_START);
			s.append(attribute.getValue());
			s.append(XmlUtil.ATTRIBUTE_VALUE_ESCAPE_END);
		}
		s.append(XmlUtil.ELEMENT_START_CLOSE);
		return s;
	}

	/**
	 * Print an XML closing tag.
	 * 
	 * @param tag
	 *            tag name
	 * @return closing tag
	 */
	public static StringBuffer closeTag(final String tag)
	{
		StringBuffer s = new StringBuffer(XmlUtil.ELEMENT_END_OPEN);
		s.append(tag);
		s.append(XmlUtil.ELEMENT_END_CLOSE);
		return s;
	}

	/**
	 * Print an empty XML tag with attributes.
	 * 
	 * @param tag
	 *            tag name
	 * @return opening tag
	 */
	public static StringBuffer emptyTag(String tag)
	{
		StringBuffer s = new StringBuffer(XmlUtil.ELEMENT_EMPTY_OPEN);
		s.append(tag);
		s.append(XmlUtil.ELEMENT_EMPTY_CLOSE);
		return s;
	}

	/**
	 * Print an empty XML tag.
	 * 
	 * @param tag
	 *            tag name
	 * @param attributes
	 *            list of attributes
	 * @return opening tag
	 */
	public static StringBuffer emptyTag(final String tag,
			final Map<String, String> attributes)
	{
		StringBuffer s = new StringBuffer(XmlUtil.ELEMENT_EMPTY_OPEN)
				.append(tag);
		for (Map.Entry<String, String> attribute : attributes.entrySet())
		{
			s.append(XmlUtil.ATTRIBUTE_SEPARATOR);
			s.append(attribute.getKey());
			s.append(XmlUtil.ATTRIBUTE_EQUALS);
			s.append(XmlUtil.ATTRIBUTE_VALUE_ESCAPE_START);
			s.append(attribute.getValue());
			s.append(XmlUtil.ATTRIBUTE_VALUE_ESCAPE_END);
		}
		s.append(XmlUtil.ELEMENT_EMPTY_CLOSE);
		return s;
	}

	/**
	 * Print a full XML tag. If the body is empty, uses <code>emptyTag</code>.
	 * 
	 * @param tag
	 *            tag name
	 * @param body
	 *            tag body to place within the opening and closing tag.
	 * @return full tag
	 */
	public static StringBuffer fullTag(final String tag, final StringBuffer body)
	{
		if ((body == null) || body.length() == 0)
		{
			return XmlUtil.emptyTag(tag);
		}
		StringBuffer s = XmlUtil.openTag(tag);
		s.append(body);
		s.append(XmlUtil.closeTag(tag));
		return s;
	}

	/**
	 * Print an XML opening tag with attributes.
	 * 
	 * @param tag
	 *            tag name
	 * @param attributes
	 *            list of attributes
	 * @param body
	 *            tag body to place within the opening and closing tag.
	 * @return full tag
	 */
	public static StringBuffer fullTag(final String tag,
			final Map<String, String> attributes, final StringBuffer body)
	{
		if ((body == null) || body.length() == 0)
		{
			return XmlUtil.emptyTag(tag, attributes);
		}
		StringBuffer s = XmlUtil.openTag(tag, attributes);
		s.append(body);
		s.append(XmlUtil.closeTag(tag));
		return s;
	}

	/**
	 * Print a full XML tag. If the body is empty, uses <code>emptyTag</code>.
	 * 
	 * @param tag
	 *            tag name
	 * @param body
	 *            tag body to place within the opening and closing tag.
	 * @return full tag
	 */
	public static StringBuffer fullTag(final String tag, final String body)
	{
		return XmlUtil.fullTag(tag, new StringBuffer(body));
	}

	/**
	 * Print an XML opening tag with attributes.
	 * 
	 * @param tag
	 *            tag name
	 * @param attributes
	 *            list of attributes
	 * @param body
	 *            tag body to place within the opening and closing tag.
	 * @return full tag
	 */
	public static StringBuffer fullTag(final String tag,
			final Map<String, String> attributes, final String body)
	{
		return XmlUtil.fullTag(tag, attributes, new StringBuffer(body));
	}
}
