/*****************************************************************************************
 * Source File: DefaultWorldManager.java
 ****************************************************************************************/
package net.ruready.business.content.world.manager;

import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.content.item.ItemDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This service allows world-hierarchy-root-item manipulation.
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
public class DefaultWorldManager implements AbstractWorldManager
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultWorldManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs for {@link Item}s.
	 */
	protected final ResourceLocator resourceLocator;

	/**
	 * Retrieved from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects.
	 */
	protected final ApplicationContext context;

	/**
	 * The user requesting DAO operations.
	 */
	private final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to read/write
	 * items to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            application context
	 * @param user
	 *            user requesting the operations
	 */
	public DefaultWorldManager(final ResourceLocator resourceLocator,
			final ApplicationContext context, final User user)
	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
		this.user = user;
	}

	// ========================= IMPLEMENTATION: AbstractWorldManager =====

	// ========================= LOCATION-RELATED METHODS ==================

	/**
	 * @see net.ruready.business.content.world.manager.AbstractWorldManager#findSchoolsInWorld(java.lang.String)
	 */
	public List<School> findSchoolsInWorld(String worldUniqueName)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding all teacher links in world '" + worldUniqueName + "'");
		}
		// TODO: update the following code
		/*
		 * try { Item exampleInstance = itemType.createItem(null, null); DAO itemDAO =
		 * eisManager.getDAO(exampleInstance.getClass()); List<School> items =
		 * itemDAO.findByExample(exampleInstance); itemDAO.close(); return items; } catch
		 * (EISException e) { throw new ApplicationException(e.toString(), e); }
		 */
		return null;
	}

	/**
	 * Search for the teacher link that matches a unique ID identifier. If this teacher
	 * link is not found, this method will not throw an exception, but when we try to
	 * access the returned object, Hibernate will throw an
	 * <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            item ID to search by
	 * @return teacher link, if found
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public School findSchoolById(long id)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding teacher link by id " + id);
		}
		// ============================================================
		// Find teacher link
		// ============================================================
		return getSchoolDAO().read(id);
	}

	public List<School> findSchoolByPartialName(final String searchString)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding school by partial name: \"" + searchString + "\"");
		}
		return getSchoolDAO().findByLikeProperty(Node.NAME, searchString);
	}
	
	private ItemDAO<School> getSchoolDAO()
	{
		return (ItemDAO<School>) eisManager.getDAO(School.class, context);
	}

	// ========================= IMPLEMENTATION: AbstractMainItemManager ==

	// ========================= GETTERS & SETTERS ========================

	/**
	 * Returns the user property.
	 * 
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

}
