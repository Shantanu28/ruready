/*****************************************************************************************
 * Source File: InitParamHelper.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.parser.xml.helper.GriddyHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A helper that processes an init parameter tag.
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
class InitParameterHelper extends GriddyHelper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(InitParameterHelper.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that processes an XML tag.
	 * 
	 * @param tagName
	 *            XML tag name
	 */
	public InitParameterHelper(final String tagName)
	{
		super(tagName);
	}

	// ========================= IMPLEMENTATION: Helper ====================

	/**
	 * Pop the parameter name and value from the stack, and push the init parameter object
	 * back onto the stack.
	 * 
	 * @see net.ruready.parser.core.xml.Helper#characters(java.lang.String,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void processEndElement(Object target)
	{
		// Cast to a friendlier version
		// logger.debug("------ endElement(), tagName " + tagName + " ------");
		FilterPackage myTarget = (FilterPackage) target;
		// logger.debug("stack " + myTarget.printStack());

		// Pop the parameter name and value
		XmlBodyElement paramValue = (XmlBodyElement) myTarget.pop();
		XmlBodyElement paramName = (XmlBodyElement) myTarget.pop();

		// Construct an init parameter from both tags' bodies push it onto the stack
		InitParameter initParam = new InitParameter(tagName, (String) paramName
				.getValue(), (String) paramValue.getValue());

		// Push the new element and onto the stack
		myTarget.push(initParam);
	}

}
