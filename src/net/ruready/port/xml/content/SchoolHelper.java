/*****************************************************************************************
 * Source File: SchoolHelper.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.property.InstitutionType;
import net.ruready.business.content.world.entity.property.Sector;
import net.ruready.common.parser.xml.TagAttachment;
import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.parser.xml.helper.GriddyHelper;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.stack.AbstractStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * A helper that constructs a {@link School} object from a school tag scope.
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
class SchoolHelper extends GriddyHelper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SchoolHelper.class);

	/**
	 * Fence for this XML block.
	 */
	private static final TagAttachment FENCE = new XmlBodyElement(
			CommonNames.PARSER.FENCE.PREFIX + SchoolHelper.class.toString(), null);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that applies a function or operation to its operands.
	 * 
	 * @param tagName
	 *            final String tagName
	 */
	public SchoolHelper(final String tagName)
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
		myTarget.push(SchoolHelper.FENCE);
	}

	/**
	 * Create a math element to wrap the most recently pushed element on the
	 * target's stack.
	 * 
	 * @see net.ruready.parser.core.xml.Helper#endElement(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void processEndElement(Object target)
	{
		// Cast to a friendlier version
		WorldDataTarget myTarget = (WorldDataTarget) target;

		// Pop all child elements above the fence
		List<TagAttachment> elements = myTarget.elementsAbove(SchoolHelper.FENCE);

		// Create a new school object from required nested tags
		School school = new School(null, null);
		/* XmlBodyElement nameElement = */WorldDataTarget.findElementByTagName(elements,
				"name");
		// logger.debug("Found school '" + (String) nameElement.getValue() +
		// "'");

		// =========================================================
		// Loop over nested elements and add them to the school
		// =========================================================
		// keep track of multiple elements with the same tag name
		Set<String> multipleElementNames = new HashSet<String>();
		// Add multiple element names here
		multipleElementNames.add("address");
		multipleElementNames.add("phone");
		Map<String, Integer> elementCount = new HashMap<String, Integer>();
		for (String name : multipleElementNames)
		{
			elementCount.put(name, 0);
		}
		// Loop over elements in reverse so that multiple elements are easy
		// to populate into School fields
		for (int i = elements.size() - 1; i >= 0; i--)
		{
			TagAttachment element = elements.get(i);
			String elementTagName = element.getTagName();
			if ("name".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setName((String) bodyElement.getValue());
			}
			else if ("comment".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setComment((String) bodyElement.getValue());
			}
			if ("institution-type".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setInstitutionType((InstitutionType) bodyElement.getValue());
			}
			if ("sector".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setSector((Sector) bodyElement.getValue());
			}
			if ("url".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setUrl((String) bodyElement.getValue());
			}
			else if ("address".equals(elementTagName))
			{
				// Address lines
				int count = elementCount.get("address") + 1;
				elementCount.put("address", count);
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				if (count == 1)
				{
					school.setAddress1((String) bodyElement.getValue());
				}
				else if (count == 2)
				{
					school.setAddress2((String) bodyElement.getValue());
				}
				else
				{
					// Append address line 3,4,... to the address2 field
					String address = (String) bodyElement.getValue();
					school.setAddress2(school.getAddress2()
							+ CommonNames.MISC.NEW_LINE_CHAR + address);
				}
			}
			else if ("district".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setDistrict((String) bodyElement.getValue());
			}
			else if ("zip-code".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setZipCode((String) bodyElement.getValue());
			}
			else if ("phone".equals(elementTagName))
			{
				// Phone numbers
				int count = elementCount.get("phone") + 1;
				elementCount.put("phone", count);
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				if (count == 1)
				{
					school.setPhone1((String) bodyElement.getValue());
				}
				else if (count == 2)
				{
					school.setPhone2((String) bodyElement.getValue());
				}
				else
				{
					// Ignore extra phone numbers
				}
			}
			else if ("fax".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				school.setFax((String) bodyElement.getValue());
			}
		}

		// logger.debug("Added school '" + school.getName() + "'");

		// Push the school onto the stack
		myTarget.push(new XmlBodyElement(tagName, school));
	}
}
