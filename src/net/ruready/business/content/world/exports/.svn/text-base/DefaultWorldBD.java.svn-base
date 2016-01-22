/*****************************************************************************************
 * Source File: DefaultWorldBD.java
 ****************************************************************************************/
package net.ruready.business.content.world.exports;

import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.manager.AbstractWorldManager;
import net.ruready.common.rl.ResourceLocator;

/**
 * A world hierarchy BD that implements the item manager interface. It relies on a static
 * hook to instantiate a specific manager implementation and a resource locator. This type
 * cannot and should not be instantiated.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 3, 2007
 */
public abstract class DefaultWorldBD implements AbstractWorldBD
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractWorldManager manager;

	/**
	 * Use this specific resource locator.
	 */
	protected final ResourceLocator resourceLocator;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize this BD from fields.
	 * 
	 * @param manager
	 *            wrapped manager
	 * @param resourceLocator
	 *            using this specific resource locator
	 */
	protected DefaultWorldBD(final AbstractWorldManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractParserBD =====

	/**
	 * @see net.ruready.business.content.world.manager.AbstractWorldManager#findSchoolsInWorld(java.lang.String)
	 */
	public List<School> findSchoolsInWorld(String worldUniqueName)
	{
		return manager.findSchoolsInWorld(worldUniqueName);
	}

	/**
	 * @see net.ruready.business.content.world.manager.AbstractWorldManager#findSchoolById(long)
	 */
	public School findSchoolById(long id)
	{
		return manager.findSchoolById(id);
	}

	public List<School> findSchoolByPartialName(final String searchString)
	{
		return manager.findSchoolByPartialName(searchString);
	}
	
}
