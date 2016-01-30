/*****************************************************************************************
 * Source File: WorldDataParser.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.util.Hashtable;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.world.entity.property.InstitutionType;
import net.ruready.business.content.world.entity.property.Sector;
import net.ruready.common.parser.xml.XmlParser;
import net.ruready.common.parser.xml.helper.EnumElementHelper;
import net.ruready.common.parser.xml.helper.Helper;
import net.ruready.common.parser.xml.helper.IntegerElementHelper;
import net.ruready.common.parser.xml.helper.StringElementHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A converter of an XML file with school data to an {@link Item} tree.
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
public class WorldDataParser extends XmlParser
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(WorldDataParser.class);

	// ========================= FIELDS ====================================

	// -------------------------------------------------
	// Required input
	// -------------------------------------------------

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize a school data file parser.
	 */
	public WorldDataParser()
	{
		super();
		this.target = new WorldDataTarget();
	}

	// ========================= IMPLEMENTATION: XmlParser =================

	/**
	 * @see net.ruready.parser.core.xml.XmlParser#helpers()
	 */
	@Override
	protected Hashtable<String, Helper> helpers()
	{
		Hashtable<String, Helper> helpers = new Hashtable<String, Helper>();

		// Complex (composite) XML elements
		helpers.put("world", new WorldHelper("world"));
		helpers.put("country", new CountryHelper("country"));
		helpers.put("state", new StateHelper("state"));
		helpers.put("city", new CityHelper("city"));
		helpers.put("school", new SchoolHelper("school"));

		// Simple XML elements/types
		helpers.put("abbreviation", new StringElementHelper("abbreviation"));
		helpers.put("address", new StringElementHelper("address"));
		helpers.put("name", new StringElementHelper("name"));
		helpers.put("comment", new StringElementHelper("comment"));
		helpers.put("county", new StringElementHelper("county"));
		helpers.put("county-string", new StringElementHelper("county-string"));
		helpers.put("institution-type", new EnumElementHelper<InstitutionType>(
				"institution-type", InstitutionType.class));
		helpers.put("fax", new StringElementHelper("fax"));
		helpers.put("district", new StringElementHelper("district"));
		helpers.put("phone", new StringElementHelper("phone"));
		helpers.put("phone-code", new IntegerElementHelper("phone-code"));
		helpers.put("sector", new EnumElementHelper<Sector>("sector", Sector.class));
		helpers.put("type", new EnumElementHelper<ItemType>("type", ItemType.class));
		helpers.put("url", new StringElementHelper("url"));
		helpers.put("zip-code", new StringElementHelper("zip-code"));

		// Debugging: wrap all helpers with decorators
		// if (logger.isDebugEnabled())
		// {
		// for (Map.Entry<String, Helper> entry : helpers.entrySet())
		// {
		// helpers.put(entry.getKey(), new VerboseHelper(entry.getValue()));
		// }
		// }

		return helpers;
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the target
	 */
	@Override
	public final WorldDataTarget getTarget()
	{
		return (WorldDataTarget) target;
	}

}
