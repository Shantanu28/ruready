/*****************************************************************************************
 * Source File: DefaultTagManager.java
 ****************************************************************************************/
package net.ruready.business.content.tag.manager;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.content.item.ItemDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This service allows interest {@link TagItem}-type item hierarchy manipulation.
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
 * @version Jul 25, 2007
 */
public class DefaultTagManager implements AbstractTagManager
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultTagManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs for TreeNodes.
	 */
	protected final ResourceLocator resourceLocator;

	/**
	 * DAO factory, obtained from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects and other useful properties.
	 */
	protected final ApplicationContext context;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to read/write
	 * items to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 */
	public DefaultTagManager(final ResourceLocator resourceLocator,
			final ApplicationContext context)

	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
	}

	// ========================= IMPLEMENTATION: AbstractTagManager ========

	/**
	 * List all tags of a certain type in the database of all descendants of a course.
	 * 
	 * @param <T>
	 *            tag type
	 * @param course
	 *            course to search in
	 * @param tagClass
	 *            tag class
	 * @return a list of tags
	 * 
	 *             if there a DAO problem occurred
	 */
	public <T extends TagItem> List<T> findTagsUnderCourse(final Course course,
			Class<T> tagClass)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding all tags under '" + course.getName() + "'");
		}
		Item tag = null;
		try
		{
			tag = tagClass.getConstructor(String.class, String.class).newInstance(null,
					null);
		}
		catch (Exception e)
		{
			throw new IllegalStateException(
					"Cannot create an tag instance with the standard item constructor!");
		}
		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		List<T> tags = itemDAO.findChildren(course, tagClass, tag.getIdentifier(), null, null);
		return tags;
	}
	// ========================= UTILITY AND TESTING METHODS ===============

}
