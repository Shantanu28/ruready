/*****************************************************************************************
 * Source File: OperandHelper.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.parser.xml.helper.GriddyHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A helper that processes a simple XML tag like <code><tag>body</tag></code>.
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
class FilterClassHelper extends GriddyHelper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FilterClassHelper.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that processes an XML tag.
	 * 
	 * @param tagName
	 *            XML tag name
	 */
	public FilterClassHelper(final String tagName)
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
		String input = charactersBuffer.toString().trim();

		// Cast to a friendlier version
		FilterPackage myTarget = (FilterPackage) target;
		// logger.debug("stack " + myTarget.printStack());

		// Construct the filter class
		Class<? extends FilterAction> filterClass = null;
		try
		{
			// Check if filter class is an instance of FilterAction
			Class<?> c = Class.forName(input);
			if (!FilterAction.class.isAssignableFrom(c))
			{
				throw new IllegalArgumentException("Bad filter class name " + input
						+ ": does not extend FilterAction");
			}

			// Try to instantiate the constructor FilterClass(FilterDefinition)
			filterClass = (Class<? extends FilterAction>) c;
			filterClass.getConstructor(FilterDefinition.class);
		}
		catch (ClassNotFoundException e)
		{
			throw new IllegalArgumentException("Bad filter class name " + input
					+ ": class not found");
		}
		catch (NoSuchMethodException e)
		{
			throw new IllegalArgumentException("Bad filter class name " + input
					+ ": necessary constructor not found");
		}
		catch (Throwable e)
		{
			throw new IllegalStateException(e);
		}

		// Create a new element for this tag's body
		XmlBodyElement element = new XmlBodyElement(tagName, filterClass);

		// Push the new element onto the stack
		myTarget.push(element);
	}
}
