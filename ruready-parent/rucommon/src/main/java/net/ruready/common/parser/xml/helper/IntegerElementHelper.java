/*****************************************************************************************
 * Source File: IntegerElementHelper.java
 ****************************************************************************************/
package net.ruready.common.parser.xml.helper;

import net.ruready.common.parser.xml.TagAttachment;
import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.stack.AbstractStack;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A helper that processes a simple XML tag's body. The tag's body is assumed to contain
 * an integer value.
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
public class IntegerElementHelper extends GriddyHelper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(IntegerElementHelper.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that processes an operand in a mathematical expression.
	 * 
	 * @param tagName
	 *            final String tagName
	 */
	public IntegerElementHelper(final String tagName)
	{
		super(tagName);
	}

	// ========================= IMPLEMENTATION: Helper ====================

	/**
	 * Push a body element object onto the target's stack.
	 * 
	 * @see net.ruready.parser.core.xml.Helper#characters(java.lang.String,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void processEndElement(Object target)
	{
		String s = charactersBuffer.toString();
		// Cast to a friendlier version
		if (TextUtil.isEmptyTrimmedString(s))
		{
			// Do nothing
			return;
		}
		String input = s.trim();

		AbstractStack<TagAttachment> myTarget = (AbstractStack<TagAttachment>) target;
		// logger.debug("stack " + myTarget.printStack());

		// Create a new element for this tag's body
		Integer inputInteger = TextUtil.getStringAsInteger(input);
		XmlBodyElement element = new XmlBodyElement(tagName, inputInteger);

		// Push the new element onto the stack
		myTarget.push(element);
	}
}
