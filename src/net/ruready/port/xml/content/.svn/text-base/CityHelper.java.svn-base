/*****************************************************************************************
 * Source File: StateHelper.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.util.List;

import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.School;
import net.ruready.common.parser.xml.TagAttachment;
import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.parser.xml.helper.GriddyHelper;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.stack.AbstractStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * A helper that constructs a {@link City} object from a corresponding XML
 * element.
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
 * @version Jul 19, 2007
 */
class CityHelper extends GriddyHelper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CityHelper.class);

	/**
	 * Fence for this XML block.
	 */
	private static final TagAttachment FENCE = new XmlBodyElement(
			CommonNames.PARSER.FENCE.PREFIX + CityHelper.class.toString(), null);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that applies a function or operation to its operands.
	 * 
	 * @param tagName
	 *            final String tagName
	 */
	public CityHelper(final String tagName)
	{
		super(tagName);
	}

	// ========================= IMPLEMENTATION: Helper ====================

	/**
	 * Stack a fence signifying that a function application block is starting.
	 * This fence is retrieved by {@link #endElement(Object)}.
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
		myTarget.push(CityHelper.FENCE);
	}

	/**
	 * Create a city and add all schools under it.
	 * 
	 * @see net.ruready.parser.core.xml.Helper#endElement(java.lang.Object)
	 */
	@Override
	public void processEndElement(Object target)
	{
		// Cast to a friendlier version
		WorldDataTarget myTarget = (WorldDataTarget) target;

		// Pop all child elements above the fence
		List<TagAttachment> elements = myTarget.elementsAbove(CityHelper.FENCE);

		// Create a new city
		City city = new City(null, null);
		/* XmlBodyElement nameElement = */WorldDataTarget.findElementByTagName(elements,
				"name");
		// logger.debug("Found city '" + (String) nameElement.getValue() + "'");

		// Loop over child elements and add them to the city
		for (TagAttachment element : elements)
		{
			String elementTagName = element.getTagName();
			if ("name".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				city.setName((String) bodyElement.getValue());
			}
			else if ("comment".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				city.setComment((String) bodyElement.getValue());
			}
			else if ("county".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				city.setCounty((String) bodyElement.getValue());
			}
			else if ("school".equals(elementTagName))
			{
				XmlBodyElement childElement = (XmlBodyElement) element;
				School school = (School) childElement.getValue();
				// logger.debug("Added school '" + school.getName() + "'");
				School existing = (School) city.findChild(school);
				if (existing != null)
				{
					logger.info("Found duplicate school: " + school.getName());
				}
				city.findOrAddItem(school);
			}
		}

		// logger.debug("filterMapping " + filterMapping);

		// Push the city onto the stack
		myTarget.push(new XmlBodyElement(tagName, city));
	}
}
