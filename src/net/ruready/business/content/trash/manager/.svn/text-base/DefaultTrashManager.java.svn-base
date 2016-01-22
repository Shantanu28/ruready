/*****************************************************************************************
 * Source File: DefaultTrashManager.java
 ****************************************************************************************/
package net.ruready.business.content.trash.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import net.ruready.business.common.tree.comparator.ChildComparator;
import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.audit.AuditAction;
import net.ruready.business.content.item.entity.audit.AuditMessage;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.exception.RecordAccessException;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.content.item.ItemDAO;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This service provides an implementation of the trash can management. This
 * includes deleting and undeleting nodes, cleaning the trash, etc.
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
 * @version Aug 9, 2007
 */
public class DefaultTrashManager implements AbstractTrashManager
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultTrashManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs for TreeNodes.
	 */
	private final ResourceLocator resourceLocator;

	/**
	 * DAO factory, obtained from the resource locator.
	 */
	private final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects and other useful properties.
	 */
	protected final ApplicationContext context;

	/**
	 * The user requesting DAO operations.
	 */
	private final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to
	 * read/write items to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 */
	public DefaultTrashManager(final ResourceLocator resourceLocator,
			final ApplicationContext context, final User user)

	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
		this.user = user;
	}

	// ========================= IMPLEMENTATION: AbstractTrashManager ======

	// ========================= ITEM MANIPULATION METHODS =================

	/**
	 * Delete an existing node from the catalog tree. This moves it under the
	 * trash instead of physically removing it. If the node doesn't exist, this
	 * method has no effect.
	 * 
	 * @param trash
	 *            trash can node
	 * @param item
	 *            node to be deleted
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 * @see net.ruready.business.content.trash.manager.AbstractTrashManager#hardDelete(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public void hardDelete(Item trash, Item item)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Hard-deleting node '" + item.getName() + "'");
		}
		if (item.isReadOnly() || item.isUnique())
		{
			throw new RecordAccessException("hardDelete(): You may not delete '"
					+ item.getName() + "'" + " because it is read-only or unique.", item);
		}

		// Remove node from any associations before attempting to delete it
		// from the database, because the deleted object would be re-saved by
		// cascade and throw an org.hibernate.ObjectDeletedException.

		// This is also where we would remove item from all other known
		// associations (e.g.
		// with User or Group entities) before attempting to delete it. To this
		// end, there
		// are no such associations except item-parent and item-children treated
		// below.

		item.removeFromParent();

		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		Item itemTree = itemDAO.read(item.getId(), true);
		// Keep session open so that itemTree is still attached to it
		// Because of cascading on REMOVE, this will delete the entire tree
		// and then close the session.
		itemDAO.delete(itemTree, user.getId());
	}

	/**
	 * Delete an existing node from the catalog tree. This moves it under the
	 * trash instead of physically removing it. If the node doesn't exist, this
	 * method has no effect.
	 * 
	 * @param trash
	 *            trash can node
	 * @param node
	 *            node to be deleted
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void delete(final Item trash, final Item item)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Deleting node '" + item.getName() + "'");
		}
		if (item.isReadOnly() || item.isUnique())
		{
			throw new RecordAccessException("delete(): You may not delete '"
					+ item.getName() + "'" + " because it is read-only or unique.", item);
		}

		// Remove node from any associations before attempting to delete it
		// from the database, because the deleted object would be re-saved by
		// cascade and throw an org.hibernate.ObjectDeletedException.
		item.removeFromParent();

		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);

		// Add the node under the trash node
		trash.addChild(item);
		// Add an audit message
		AuditMessage auditMessage = new AuditMessage(item, user, AuditAction.DELETED,
				null, item.getVersion() + 1);
		item.addMessage(auditMessage);

		// Save changes to the database
		itemDAO.updateWithChildren(trash, user.getId(), false);
		// itemDAO.update(trash);
		// itemDAO.update(item);
	}

	/**
	 * Restore an existing node from the catalog tree. This moves it from the
	 * trash to being under a new parent node. If the node doesn't exist, this
	 * method has no effect.
	 * 
	 * @param trash
	 *            trash can node
	 * @param node
	 *            node to be restored
	 * @param newParent
	 *            new parent of the node
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void restore(Item trash, Item node, Item newParent)
	{
		if (logger.isInfoEnabled())
		{
			logger.debug("Restoring node '" + node.getName() + "'");
		}
		throw new ApplicationException("restore(): unsupported operation");
	}

	/**
	 * List all sub-nodes of the trash can.
	 * 
	 * @return a list of all items under the trash can
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public Collection<Item> findAll(Item trash)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Find all root sub-nodes except trash");
		}
		Collection<Node> nodes = trash.getChildren();
		// Copy the nodes to a new list, because if we remove from elements from
		// nodes during iterating over it, a ConcurrentModificationException
		// may be thrown.
		Collection<Item> newNodes = new TreeSet<Item>(new ChildComparator());
		for (Node node : nodes)
		{
			newNodes.add((Item) node);
		}
		return newNodes;
	}

	/**
	 * Search for a deleted node in the trash that matches a unique ID
	 * identifier. If this node is not found, this method will not throw an
	 * exception, but when we try to access the returned object, Hibernate will
	 * throw an <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            node ID to search by
	 * @return node, if found
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public Item findById(long id)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding node by id " + id);
		}
		DAO<Item, Long> itemDAO = eisManager.getDAO(Item.class, context);
		Item item = itemDAO.read(id);
		return item;
	}

	/**
	 * Search for a deleted node in the trash that matches a name.
	 * 
	 * @param name
	 *            name to search for
	 * @return list of nodes matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public List<Item> findByName(String name)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing node by name '" + name + "' ...");
		}
		DAO<Item, Long> itemDAO = eisManager.getDAO(Item.class, context);
		List<Item> items = itemDAO.findByProperty(Node.NAME, name);
		return items;
	}

	// ========================= CATALOG AND CASCADED METHODS ==============

	/**
	 * Find the unique trash can using its standard naming convention.
	 */
	public DefaultTrash findTrash()
	{
		DefaultTrash item = findTrashByUniqueName(ContentNames.UNIQUE_NAME.TRASH);
		if (item == null)
		{
			throw new ApplicationException("findTrash(): item not found");
		}

		// ============================================================
		// Load item from database
		// ============================================================
		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		// Will read item's children (it's normally lazy-initialized)
		item = (DefaultTrash) itemDAO.read(item.getId(), false);

		// ============================================================
		// Post processing and population of extra useful properties
		// ============================================================
		// Order children
		// item.refreshAll();

		return item;
	}

	/**
	 * Create or update the trash can.
	 * 
	 * @param item
	 *            item to be updated and all items below this item in the tree
	 *            hierarchy
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void updateTrash()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Saving/Updating trash ");
		}
		DefaultTrash trash = findTrash();
		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		itemDAO.update(trash);
	}

	// ========================= IMPLEMENTATION: AbstractTrash =============

	/**
	 * Manually empty the entire trash.
	 */
	public void clear()
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Clearing trash");
		}
		// Find all items in the trash
		DefaultTrash trash = findTrash();
		Collection<Item> items = findAll(trash);

		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		for (Item entity : items)
		{
			Item item = itemDAO.read(entity.getId(), true);
			// Remove item from associations
			item.removeFromParent();
			// Because of cascading on REMOVE, this will delete the entire
			// tree of items below "item" as well as attached objects (e.g.
			// audit
			// messages).
			itemDAO.delete(item);
		}
	}

	/**
	 * An automatic cleaning of the trash. Should called at regular time
	 * intervals. Deletes old items from the trash.
	 */
	public void expunge()
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Expunging trash");
		}
		long recentEnoughTime = new Date().getTime()
				- resourceLocator
						.getPropertyAsLong(WebAppNames.RESOURCE_LOCATOR.PROPERTY.TRASH_EXPUNGE_TIME)
				* CommonNames.TIME.MINS_TO_MS;

		// Find all items in the trash
		DefaultTrash trash = findTrash();
		Collection<Item> items = findAll(trash);

		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		for (Item entity : items)
		{
			Item item = itemDAO.read(entity.getId(), true);
			// If item's latest message exists, is a delete message
			// and indicated that the item has been recently deleted, skip
			// it
			AuditMessage auditMessage = item.getLatestMessage();
			if ((auditMessage != null)
					&& ((auditMessage.getDate().getTime() >= recentEnoughTime) || (auditMessage
							.getAction() != AuditAction.DELETED)))
			{
				logger.info("Skipping item " + item.getName());
				continue;
			}
			logger.info("Deleting item " + item.getName());
			// Remove item from associations
			item.removeFromParent();
			// Because of cascading on REMOVE, this will delete the entire
			// tree of items below "item".
			itemDAO.delete(item);
		}
	}

	// ========================= UTILITY AND TESTING METHODS ===============

	/**
	 * Search for a unique trash can that matches a name.
	 * 
	 * @param name
	 *            name to search for
	 * @return trash can matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public DefaultTrash findTrashByUniqueName(String name)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing unique trash by name '" + name + "' ...");
		}
		DAO<DefaultTrash, Long> trashDAO = eisManager.getDAO(DefaultTrash.class, context);
		DefaultTrash trash = trashDAO.findByUniqueProperty(Node.NAME, name);
		return trash;
	}

}
