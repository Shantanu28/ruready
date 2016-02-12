/*****************************************************************************************
 * Source File: Helper.java
 ****************************************************************************************/

package net.ruready.common.parser.xml.helper;

import net.ruready.common.parser.xml.TagAttachment;

import org.xml.sax.Attributes;

/**
 * An abstraction of filter package parser helpers.
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
 * @version Oct 11, 2007
 */
public interface Helper extends TagAttachment
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * @param atts
	 *            the attributes attached to the element
	 * @param target
	 * @see net.ruready.common.parser.xml.helper.Helper#startElement(java.lang.Object)
	 */
	void startElement(Attributes atts, Object target);

	/**
	 * @param s
	 * @param target
	 * @see net.ruready.common.parser.xml.helper.Helper#characters(java.lang.String,
	 *      java.lang.Object)
	 */
	void characters(String s, Object target);

	/**
	 * @param target
	 * @see net.ruready.common.parser.xml.helper.Helper#endElement(java.lang.Object)
	 */
	void endElement(Object target);
}
