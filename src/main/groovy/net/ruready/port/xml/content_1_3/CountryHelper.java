/*****************************************************************************************
 * Source File: CountryHelper.java
 ****************************************************************************************/
package net.ruready.port.xml.content_1_3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.CountryFactory;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.common.parser.xml.TagAttachment;
import net.ruready.common.parser.xml.XmlBodyElement;
import net.ruready.common.parser.xml.helper.GriddyHelper;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.stack.AbstractStack;
import net.ruready.port.xml.content.WorldDataTarget;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

/**
 * A helper that constructs a {@link Country} object from a corresponding XML
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
class CountryHelper extends GriddyHelper
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CountryHelper.class);

	/**
	 * Fence for this XML block.
	 */
	private static final TagAttachment FENCE = new XmlBodyElement(
			CommonNames.PARSER.FENCE.PREFIX + CountryHelper.class.toString(), null);

	/**
	 * Tag's attributes.
	 */
	private static final String ATTRIBUTES_NAME = "ATTRIBUTES"
			+ CommonNames.MISC.SEPARATOR + CountryHelper.class.toString();

	/**
	 * Convert a &lt;country&gt; tag xsi:type attribute to an item ID.
	 */
	private static final Map<String, ItemType> attribute2ItemType = new HashMap<String, ItemType>();

	static
	{
		attribute2ItemType.put("unified", ItemType.COUNTRY);
		attribute2ItemType.put("federation", ItemType.FEDERATION);
	}

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a helper that applies a function or operation to its operands.
	 * 
	 * @param tagName
	 *            final String tagName
	 */
	public CountryHelper(final String tagName)
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

		// Stack the country type attribute, to be used by processEndElement().
		ItemType type = attribute2ItemType.get(atts.getValue("xsi:type"));
		myTarget.push(new XmlBodyElement(CountryHelper.ATTRIBUTES_NAME, type));

		// Stack the fence
		myTarget.push(CountryHelper.FENCE);
	}

	/**
	 * Create a country and add all states under it.
	 * 
	 * @see net.ruready.parser.core.xml.Helper#endElement(java.lang.Object)
	 */
	@Override
	public void processEndElement(Object target)
	{
		// Cast to a friendlier version
		WorldDataTarget myTarget = (WorldDataTarget) target;

		// Pop all state elements above the fence
		List<TagAttachment> elements = myTarget.elementsAbove(CountryHelper.FENCE);

		// Pop the tag's attributes
		ItemType type = (ItemType) ((XmlBodyElement) myTarget.pop()).getValue();

		// Create a new country object from required nested tags. If a country
		// with this name already exists in the target, use it instead so that
		// we end up merging the XML data into it.

		// Country and <country> are polymorphic. Use the type to instantiate
		// the correct country type.
		XmlBodyElement nameElement = WorldDataTarget.findElementByTagName(elements,
				"name");
		String name = (String) nameElement.getValue();
		XmlBodyElement phoneCodeElement = WorldDataTarget.findElementByTagName(elements,
				"phone-code");
		int phoneCode = (Integer) phoneCodeElement.getValue();

		Country country = new CountryFactory().createType(type, name, null, phoneCode);
		logger.debug("Found country, type " + type + " phoneCode '" + phoneCode + "'");
		country = myTarget.getWorld().findOrAddItem(country);

		// Loop over nested elements and add them to the country
		for (TagAttachment element : elements)
		{
			String elementTagName = element.getTagName();
			if ("name".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				country.setName((String) bodyElement.getValue());
			}
			else if ("comment".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				country.setComment((String) bodyElement.getValue());
			}
			else if ("city-string".equals(elementTagName))
			{
				XmlBodyElement bodyElement = (XmlBodyElement) element;
				country.setCountyString((String) bodyElement.getValue());
			}
			else if ("state".equals(elementTagName))
			{
				// Country is a federation
				XmlBodyElement stateElement = (XmlBodyElement) element;
				State state = (State) stateElement.getValue();
				country.findOrAddItem(state);
			}
			else if ("school".equals(elementTagName))
			{
				// In version 1.3. the city is a property of School, not its
				// parent.
				// Therefore, read the school, read its city, and add the city
				// under the state object and the school under the city.
				XmlBodyElement childElement = (XmlBodyElement) element;
				School school = (School) childElement.getValue();
				String cityName = school.getCity();
				if (cityName == null)
				{
					myTarget.incrementNumErrors();
					logger.warn("Missing city for school '" + school.getName()
							+ "', using default city");
					cityName = ContentNames.BASE_NAME.CITY;
				}
				City city = new City(cityName, null);

				// Transfer county property from school to city. If an existing
				// city is
				// found with the same name and county, rename both cities to
				// include
				// the county name.
				String county = school.getCounty();
				city.setCounty(county);
				String suffix = (country.getCountyString() == null) ? CommonNames.MISC.EMPTY_STRING
						: country.getCountyString();
				if (county != null)
				{
					for (Node childRaw : country.getChildren())
					{
						City child = (City) childRaw;
						String childName = child.getName();
						String childCounty = child.getCounty();
						if (cityName.equals(childName) && (childCounty != null)
								&& county.equals(childCounty))
						{
							// We decided not to rename, just warn for now
							myTarget.incrementNumErrors();
							logger.warn("Found duplicate city: " + school.getName()
									+ " city '" + city.getName() + "' in counties "
									+ childCounty + ", " + county + " ==> renaming");
							child.setName(childName + ", " + county + suffix);
							city.setName(cityName + ", " + county + suffix);
						}
					}
				}

				// logger.debug("Added school '" + school.getName() + "'" + "
				// city '"
				// + city.getName() + "'");

				city = country.findOrAddItem(city);

				School existing = (School) country.findChild(school);
				if ((existing != null) && (existing.getParent() == city))
				{
					logger.warn("Found duplicate school: " + school.getName() + " city '"
							+ city.getName() + "'");
				}
				school = city.findOrAddItem(school);
			}
		}
	}
}
