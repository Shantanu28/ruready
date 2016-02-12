/*****************************************************************************************
 * Source File: Item.java
 ****************************************************************************************/
package net.ruready.business.content.demo.manager;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.business.content.world.entity.property.InstitutionType;
import net.ruready.business.content.world.entity.property.Sector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A factory of a mock world created for testing and upon site initialization. The world
 * is unique, so this factory must be called upon site initialization.
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
 * @version Aug 1, 2007
 */
public class DemoWorldCreator implements DemoItemCreator<World>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DemoWorldCreator.class);

	public static final String SCHOOL_UTAH = "University of Utah";

	public static final String SCHOOL_UTAHSTATE = "Utah State University";

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * In the future: we might hide constructor in this utility class and use a facade
	 * instead.
	 * </p>
	 */
	public DemoWorldCreator()
	{

	}

	// ========================= IMPLEMENTATION: DemoItemCreator ==============

	/**
	 * Initialize a base catalog using a hard-coded tree of nodes. This is useful for
	 * testing and site initialization defaulting.
	 * 
	 * @param uniqueName
	 *            catalog's uniqueName identifier
	 * @see net.ruready.business.content.demo.manager.DemoItemCreator#createItem(java.lang.String)
	 */
	public World createItem(String uniqueName)
	{
		// Create some items. Because we only add items,
		// the tree automatically refreshes and appears sorted.
		logger.info("Creating base World");
		World world = null;
		Item parent = null;
		Item child = null;

		world = new World(uniqueName, "States, cities, schools, ...");
		child = world;

		// A country with states
		parent = child;
		child = new Federation("United States", null, 1);
		parent.addChild(child);

		parent = child;
		child = new State("Utah", null);
		parent.addChild(child);

		parent = child;
		child = new City("Salt Lake City", null);
		((City)child).setCounty("Salt Lake");
		parent.addChild(child);

		parent = child;
		child = new School("Olympus High", null);
		parent.addChild(child);

		child = new School("West High", null);
		parent.addChild(child);

		child = new School(SCHOOL_UTAH, null);
		parent.addChild(child);

		child = new School(SCHOOL_UTAHSTATE, null);
		parent.addChild(child);

		parent = parent.getParent();

		child = new City("Cottonwood Heights", null);
		parent.addChild(child);

		parent = child;
		child = new School("Ridgecrest", null);
		parent.addChild(child);

		// A country without states
		parent = world;
		child = new Country("Israel", null, 972);
		parent.addChild(child);

		parent = child;
		child = new City("Ariel", null);
		parent.addChild(child);

		parent = child;
		School school = new School("College of Judea and Smaria", null);
		school.setInstitutionType(InstitutionType.HIGHER_EDUCATION);
		school.setSector(Sector.PUBLIC);
		school.setPhone1("0-000-0000");
		parent.addChild(school);

		return world;
	}

}
