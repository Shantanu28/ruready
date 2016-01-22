/*****************************************************************************************
 * Source File: ApplyFuncHelper.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import java.util.List;

import net.ruready.common.parser.xml.TagAttachment;
import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.parser.xml.helper.GriddyHelper;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.stack.AbstractStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * A helper that constructs a filter mapping from an XML element whose DTD syntax is
 * 
 * <pre>
 * &lt;!ELEMENT filter-mapping (filter-name, url-pattern)&gt;
 * </pre>
 * 
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
 * @version Jul 19, 2007
 */
class FilterDefinitionHelper extends GriddyHelper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FilterDefinitionHelper.class);

	/**
	 * Fence for this XML block.
	 */
	private static final TagAttachment FENCE = new XmlBodyElement(
			CommonNames.PARSER.FENCE.PREFIX
			+ FilterDefinitionHelper.class.toString(), null);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that applies a function or operation to its operands.
	 * 
	 * @param tagName
	 *            final String tagName
	 */
	public FilterDefinitionHelper(final String tagName)
	{
		super(tagName);
	}

	// ========================= IMPLEMENTATION: Helper ====================

	/**
	 * Stack a fence signifying that a function application block is starting. This fence
	 * is retrieved by {@link #endElement(Object)}. *
	 * 
	 * @param atts
	 *            the attributes attached to the element
	 * @param target
	 * @see net.ruready.parser.core.xml.Helper#startElement(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void processStartElement(final Attributes atts, final Object target)
	{
		// Cast to a friendlier version
		AbstractStack<Object> myTarget = (AbstractStack<Object>) target;

		// Stack the fence
		myTarget.push(FilterDefinitionHelper.FENCE);
	}

	/**
	 * Create a math element to wrap the most recently pushed element on the target's
	 * stack.
	 * 
	 * @see net.ruready.parser.core.xml.Helper#endElement(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void processEndElement(Object target)
	{
		// Cast to a friendlier version
		// logger.debug("------ characters(), tagName " + tagName + " ------");
		FilterPackage myTarget = (FilterPackage) target;

		// Pop all terms above the fence
		List<TagAttachment> elements = myTarget
				.elementsAbove(FilterDefinitionHelper.FENCE);

		// Loop over elements and set properties accordingly
		FilterDefinition filterDefinition = new FilterDefinition();
		for (TagAttachment element : elements)
		{
			String elementTagName = element.getTagName();
			if ("filter-name".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				filterDefinition.setFilterName((String) bodyElement.getValue());
			}
			else if ("description".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				filterDefinition.setDescription((String) bodyElement.getValue());
			}
			else if ("filter-class".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				filterDefinition
						.setFilterClass((Class<? extends FilterAction>) bodyElement
								.getValue());
			}
			else if ("error-page".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				filterDefinition.setErrorPage((String) bodyElement.getValue());
			}
			else if ("init-param".equals(elementTagName))
			{
				// If multiple parameters have the same name, the last one overrides
				// all previous ones in the init param map.
				InitParameter initParam = (InitParameter) element;
				filterDefinition.addInitParameter(initParam);
			}
		}

		// logger.debug("filterDefinition " + filterDefinition);

		// Add the filter definition element to the target; no need to push it onto the
		// stack.
		myTarget.addFilterDefinition(filterDefinition);
	}
}
