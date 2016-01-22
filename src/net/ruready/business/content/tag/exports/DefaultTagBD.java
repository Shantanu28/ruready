/*****************************************************************************************
 * Source File: DefaultTagBD.java
 ****************************************************************************************/
package net.ruready.business.content.tag.exports;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.content.tag.manager.AbstractTagManager;
import net.ruready.common.rl.ResourceLocator;

/**
 * A interestCollection hierarchy BD that implements the item manager interface. It relies
 * on a static hook to instantiate a specific manager implementation and a resource
 * locator. This type cannot and should not be instantiated.
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
public abstract class DefaultTagBD implements AbstractTagBD
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractTagManager manager;

	/**
	 * Using this specific resource locator.
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
	protected DefaultTagBD(final AbstractTagManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}
	
	// ========================= IMPLEMENTATION: AbstractTagBD =============

	/**
	 * @param <T>
	 * @param course
	 * @param tagClass
	 * @return
	 * @see net.ruready.business.content.tag.manager.AbstractTagManager#findTagsUnderCourse(net.ruready.business.content.catalog.entity.Course,
	 *      java.lang.Class)
	 */
	public <T extends TagItem> List<T> findTagsUnderCourse(Course course,
			Class<T> tagClass)
	{
		return manager.findTagsUnderCourse(course, tagClass);
	}

	// ========================= GETTERS & SETTERS =========================
}
