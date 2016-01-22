/*****************************************************************************************
 * Source File: DefaultEditItemManager.java
 ****************************************************************************************/
package net.ruready.business.content.item.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.entity.audit.AuditAction;
import net.ruready.business.content.item.entity.audit.AuditMessage;
import net.ruready.business.content.item.util.ItemUtil;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.exception.InvalidItemTypeException;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.eis.exception.RecordAccessException;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchEngine;
import net.ruready.common.text.TextUtil;
import net.ruready.eis.content.item.ItemDAO;
import net.ruready.port.json.DynaBeanUtil;
import net.ruready.web.demo.ext.grid.entity.RecordStatus;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This service allows item manipulation: creating a new item, updating an
 * existing item, deleting an item, and listing items by certain criteria.
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
 * @version Jul 16, 2007
 */
public class DefaultEditItemManager<B extends Item> implements AbstractEditItemManager<B>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultEditItemManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Base class of the item [sub-]hierarchy we are managing.
	 */
	protected final Class<B> baseClass;

	/**
	 * Locator of the DAO factory that creates DAOs for Items.
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
	protected final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to
	 * read/write items to/from the database.
	 * 
	 * @param persistentClass
	 *            base class of the item [sub-]hierarchy we are managing
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 */
	public DefaultEditItemManager(final Class<B> baseClass,
			final ResourceLocator resourceLocator, final ApplicationContext context,
			final User user)

	{
		this.baseClass = baseClass;
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
		this.user = user;
	}

	// ========================= IMPLEMENTATION: AbstractEditItemManager ===

	// ========================= CRUD OPERATIONS ===========================

	/**
	 * Add an item under another tree item and save the changes to the database.
	 * 
	 * @param parentId
	 *            id of catalog node (item) with this id. If the parent ID is
	 *            not found, no action is taken.
	 * @param item
	 *            new item to be added under the specified parent node. The
	 *            constructed node for item will be by default a togglable
	 *            sorted tree.
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	@SuppressWarnings("unchecked")
	public void createUnder(long parentId, B item)
	{
		Item parentNode = read(baseClass, parentId);
		if (parentNode == null)
		{
			throw new ApplicationException("createUnder(): parent node not found");
		}
		if (logger.isInfoEnabled())
		{
			logger.info("Adding a new item '" + item.getName() + "' under parent '"
					+ parentNode.getName() + "' ID " + parentId);
		}

		// Add the new item
		renameItem(parentNode, item);
		parentNode.addChild(item);

		// Get the DAO factory and DAO through our resource locator
		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		// Save the parent node & all children
		itemDAO.updateWithChildren(parentNode, user.getId(), true);
		// itemDAO.update(parentNode, user);
		// itemDAO.update(item, user);
		if (logger.isDebugEnabled())
		{
			logger.debug("# Children after updating: " + parentNode.getNumChildren());
			for (Node child : parentNode.getChildren())
			{
				logger.debug(child);
			}
		}
	}

	/**
	 * Create or update an item instance to database. Transient fields will not
	 * be persisted.
	 * 
	 * @param item
	 *            item to be updated
	 * @param respectLocks
	 *            if <code>true</code>, will throw an exception if item is
	 *            read-only
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void update(final B item, final boolean respectLocks)
	{
		Node parent = item.getParent();
		if (logger.isDebugEnabled())
		{
			logger.debug("Saving/Updating item " + item.getName() + " parent "
					+ ((parent == null) ? "NULL" : parent.getName()));
		}

		// Do not update item if it is read-only; notify client of a "soft"
		// error
		if (item.isReadOnly() && respectLocks)
		{
			throw new RecordAccessException("update(): You may not update '"
					+ item.getName() + "'" + " because it is read-only.", item);
		}
		// Get the DAO factory and DAO through our resource locator
		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		itemDAO.update(item, user.getId());
	}

	/**
	 * Update an item instance to database. Transient fields will not be
	 * affected.
	 * 
	 * @param item
	 *            item to be updated
	 * @param respectLocks
	 *            if <code>true</code>, will throw an exception if item is
	 *            read-only
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void merge(final B item, final boolean respectLocks)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Merging item '" + item.getName() + "'");
		}

		// Get the DAO factory and DAO through our resource locator
		ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class, context);
		itemDAO.merge(item, user.getId());
	}

	/**
	 * Read an item instance using database values. Pre-populated fields are
	 * overridden if they are persistent, or unaffected if they are transient.
	 * If children loading is specified (<code>cascadeType = CHILDREN</code>),
	 * we load the number of children of each item's child as well. Similarly,
	 * siblings are loaded if specified by the cascadeType parameter (<code>cascadeType = SIBLINGS</code>).
	 * If <code>cascadeType = ALL</code>, the entire item sub-tree hierarchy
	 * is loaded.
	 * 
	 * @param id
	 *            unique item identifier to search for and load by
	 * @param item
	 *            item to be updated
	 * @param cascadeType
	 *            specifies optional cascading (loading children/siblings/entire
	 *            sub-tree as well).
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 * if there a DAO problem occurred
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#read(java.lang.Class,
	 *      long)
	 */
	public <T extends B> T read(final Class<T> persistentClass, final long id)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Reading item type " + persistentClass.getSimpleName() + " id "
					+ id);
		}
		ItemDAO<T> itemDAO = null;
		// ============================================================
		// Load item from database
		// ============================================================
		// ItemDAO<T> itemDAO = (ItemDAO<T>) eisManager.getDAO(persistentClass);

		itemDAO = (ItemDAO<T>) eisManager.getDAO(persistentClass, context);
		T item = itemDAO.read(id, false);

		// ============================================================
		// Post processing and population of extra useful properties
		// ============================================================
		// Order children
		// item.refreshAll();

		return item;
	}

	/**
	 * Delete an existing item from the the database. If the item doesn't exist,
	 * this method has no effect.
	 * 
	 * @param item
	 *            item to be deleted
	 * @param respectLocks
	 *            if <code>false</code>, will delete even read-only and
	 *            unique items
	 * 
	 * if there a DAO problem occurred
	 */
	@SuppressWarnings("unchecked")
	public void delete(final B item, final boolean respectLocks)
	{
		if (item == null)
		{
			return;
		}
		if (logger.isInfoEnabled())
		{
			logger.info("Deleting item '" + item.getName() + "', parent "
					+ item.getParent());
		}
		// Do not delete item if it is read-only or unique; only notify client
		// of a "soft"
		// error
		if (respectLocks && (item.isReadOnly() || item.isUnique()))
		{
			if (logger.isInfoEnabled())
			{
				logger.info("Ignoring delete() of item '" + item.getName() + "'"
						+ " because it is read-only or unique.");
			}
			return;
		}

		// Remove item from any associations before attempting to delete it
		// from the database, because the deleted object would be re-saved by
		// cascade and throw an org.hibernate.ObjectDeletedException.
		if (item.getParent() != null)
		{
			item.getParent().getChildren();
			item.removeFromParent();
		}
		ItemDAO<B> itemDAO = (ItemDAO<B>) eisManager.getDAO(item.getItemClass(), context);

		// ItemDAO<? extends Item> itemDAO = (ItemDAO<? extends Item>)
		// eisManager
		// .getDAO(item.getItemClass());

		itemDAO.delete(item, user.getId());
	}

	/**
	 * Deep-copy an item [from its current parent] under another tree item and
	 * save the changes to the database. The original item may be renamed
	 * according to the <i>{@link Item} renaming convention</i> if items with
	 * the same name already exist under the new parent. The entire hierarchy
	 * under the item is also copied over (a deep copy).
	 * 
	 * @param newParent
	 *            new parent of the item
	 * @param item
	 *            item to be deep-copied
	 * @return the item's copy
	 * 
	 * if there a DAO problem occurred
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#copyUnder(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	@SuppressWarnings("unchecked")
	public <T extends B, S extends B> S copyUnder(T newParent, S item)
	{
		// Clone the item tree, renaming it and add it under the parent
		S itemCopy = (S) item.clone();
		// Add the new item under the new parent
		renameItem(newParent, itemCopy);
		newParent.addChild(itemCopy);

		// ==========================================
		// Save changes to the database
		// ==========================================

		// Prepare an audit message for newItem indicating that it was
		// copied over, not just created
		AuditMessage auditMessage = new AuditMessage(itemCopy, user, AuditAction.COPIED,
				null, itemCopy.getVersion() + 1);
		itemCopy.addMessage(auditMessage);

		// Don't save the entire parent tree:
		// save only the parent and the new child.
		update(newParent, false);
		updateAll(itemCopy);
		return itemCopy;
	}

	/**
	 * Move an item [from its current parent] under another tree item and save
	 * the changes to the database.
	 * 
	 * @param newParent
	 *            new parent of the item
	 * @param item
	 *            item to be moved
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void moveUnder(B newParent, B item)
	{
		if (logger.isInfoEnabled())
		{
			logger
					.info("Moving node " + item.getName() + " under "
							+ newParent.getName());
		}

		// Remove node from any associations before attempting to delete it
		// from the database, because the deleted object would be re-saved by
		// cascade
		// and throw an org.hibernate.ObjectDeletedException.
		item.removeFromParent();

		// Get the DAO factory and DAO through our resource locator
		ItemDAO<B> itemDAO = (ItemDAO<B>) eisManager.getDAO(baseClass, context);

		// Add the node under the new parent
		newParent.addChild(item);

		// Add an audit message
		AuditMessage auditMessage = new AuditMessage(item, user, AuditAction.MOVED, null,
				item.getVersion() + 1);
		item.addMessage(auditMessage);

		// Save changes to the database
		itemDAO.updateWithChildren(newParent, user.getId(), false);
		// itemDAO.update(newParent, user);
		// itemDAO.update(item, user);
	}

	/**
	 * Move a child to a new index in the item's children array.
	 * 
	 * @param item
	 *            item to be moved
	 * @param moveFromSN
	 *            original serial # of child
	 * @param moveToSN
	 *            new serial # of child
	 * 
	 * if there a DAO problem occurred
	 */
	@SuppressWarnings("unchecked")
	public void moveChildLocation(final B item, final int moveFromSN, final int moveToSN)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Moving child S.N. " + moveFromSN + " to S.N. #" + moveToSN
					+ " in item " + item.getName());
		}

		// Do the move in the item children array
		item.move(moveFromSN, moveToSN);

		// Add an audit message
		AuditMessage auditMessage = new AuditMessage(item, user,
				AuditAction.CHILDREN_REORDERED, null, item.getVersion() + 1);
		item.addMessage(auditMessage);

		// Update item in the database
		ItemDAO<B> itemDAO = (ItemDAO<B>) eisManager.getDAO(item.getItemClass(), context);
		itemDAO.updateWithChildren(item, user.getId(), false);
	}

	// ========================= FINDER METHODS (CUSTOM READS) =============

	/**
	 * List all items in the database.
	 * 
	 * @return a list of all items
	 * 
	 * if there a DAO problem occurred
	 */
	public <T extends B> List<T> findAll(final Class<T> persistentClass)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding all items of type " + persistentClass.getSimpleName());
		}
		DAO<T, Long> itemDAO = eisManager.getDAO(persistentClass, context);
		List<T> items = itemDAO.findAll();
		return items;
	}

	/**
	 * List all items in the database that are not under the trash can. Note:
	 * assumes that the items are under the a tree-structure hierarchy (every
	 * child has one parent).
	 * 
	 * @param persistentClass
	 *            type of items to find
	 * @param hierarchyRootId
	 *            root of hierarchy that the item type belongs to; must be a
	 *            tree structure and must be a {@link MainItem} whose level in
	 *            the item hierarchy equals the trash can's.
	 * @return a list of all non-deleted items
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findAllNonDeleted(java.lang.Class,
	 *      net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends B> List<T> findAllNonDeleted(final Class<T> persistentClass,
			final ItemType hierarchyRootId)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding all items of type " + persistentClass.getSimpleName()
					+ " under node ID " + hierarchyRootId);
		}
		ItemDAO<T> itemDAO = (ItemDAO<T>) eisManager.getDAO(persistentClass, context);
		List<T> items = itemDAO.findAllNonDeleted(hierarchyRootId);
		return items;
	}

	/**
	 * Search for the item that matches a unique ID identifier. If this item is
	 * not found, this method will not throw an exception, but when we try to
	 * access the returned object, Hibernate will throw an
	 * <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            item ID to search by
	 * @return item, if found
	 * 
	 * if there a DAO problem occurred
	 */
	public <T extends B> T findById(final Class<T> persistentClass, final long id)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding item by id " + id);
		}
		DAO<T, Long> itemDAO = eisManager.getDAO(persistentClass, context);
		T item = itemDAO.read(id);
		return item;
	}

	/**
	 * Search for an item that matches a name.
	 * 
	 * @param name
	 *            name to search for
	 * @return list of items matching the name
	 * 
	 * if there a DAO problem occurred
	 */
	public <T extends B> List<T> findByName(final Class<T> persistentClass,
			final String name)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing item by name '" + name + "' ...");
		}
		DAO<T, Long> itemDAO = eisManager.getDAO(persistentClass, context);
		List<T> items = itemDAO.findByProperty(Node.NAME, name);
		return items;
	}

	/**
	 * Search for an item that begins with name.
	 * 
	 * @param <T>
	 * @param persistentClass
	 * @param name
	 *            the name to look for
	 * @return a list of items that begin with name.
	 */
	@SuppressWarnings("unchecked")
	public <T extends B> List<T> findByLikeName(Class<T> persistentClass, String name)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing item like name '" + name + "' ...");
		}
		ItemDAO<T> itemDAO = (ItemDAO<T>) eisManager.getDAO(persistentClass, context);
		return itemDAO.findByLikeProperty(Node.NAME, name);
	}

	/**
	 * Search for an item that matches an example.
	 * 
	 * @param example
	 *            example criterion
	 * @return list of items matching the name
	 * 
	 * if there a DAO problem occurred
	 */
	public <T extends B> List<T> findByExampleObject(final Class<T> persistentClass,
			final Object example)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing item by example '" + example + "' ...");
		}
		DAO<T, Long> itemDAO = eisManager.getDAO(persistentClass, context);
		List<T> items = itemDAO.findByExampleObject(example);
		return items;
	}

	/**
	 * Search for an item that matches an example.
	 * 
	 * @param example
	 *            example item to match
	 * @return list of items matching the name
	 * 
	 * if there a DAO problem occurred
	 */
	public <T extends B> List<T> findByExample(final Class<T> persistentClass,
			final T example)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding existing item by example '" + example.getName()
					+ "' ...");
		}
		DAO<T, Long> itemDAO = eisManager.getDAO(persistentClass, context);
		List<T> items = itemDAO.findByExample(example);
		return items;
	}

	/**
	 * Find the list of children of a certain item type under a parent node.
	 * 
	 * @param <C>
	 *            type of child
	 * @return parent parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @return list of children under the parent; need not be its direct
	 *         children
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildren(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	@SuppressWarnings("unchecked")
	public <T extends B> List<T> findChildren(final Item parent,
			final Class<T> persistentClass, final ItemType childItemType)
	{
		ItemDAO<T> itemDAO = (ItemDAO<T>) eisManager.getDAO(parent.getItemClass(),
				context);
		List<T> children = itemDAO.findChildren(parent, persistentClass, childItemType,
				null, null);
		return children;
	}

	/**
	 * Find the list of children identifiers of a certain item type under a
	 * parent node.
	 * 
	 * @param <T>
	 *            type of child
	 * @return parent parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @return list of children IDs under the parent; need not be its direct
	 *         children
	 * 
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildrenIds(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	@SuppressWarnings("unchecked")
	public <T extends B> List<Long> findChildrenIds(final Item parent,
			final Class<T> persistentClass, final ItemType childItemType)
	{
		ItemDAO<T> itemDAO = (ItemDAO<T>) eisManager.getDAO(parent.getItemClass(),
				context);
		List<Long> childrenIds = itemDAO.findChildrenIds(parent, persistentClass,
				childItemType, null, null);
		return childrenIds;
	}

	/**
	 * Find the number of question of a certain type and level under a certain
	 * node.
	 * 
	 * @param parent
	 *            parent node to look under
	 * @param questionType
	 *            question type (academic/creative)
	 * @param level
	 *            difficulty level
	 * @return number of questions of this type, level and under this parent
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findQuestionCount(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.question.entity.property.QuestionType,
	 *      int)
	 */
	@SuppressWarnings("unchecked")
	public <T extends B> long findChildrenCount(final Item parent,
			final Class<T> persistentClass, final ItemType childItemType)

	{
		ItemDAO<T> itemDAO = (ItemDAO<T>) eisManager.getDAO(parent.getItemClass(),
				context);
		final String constraintClause = null;// "(question.questionType = ?)
		// and (question.level = ?)";
		final List<?> constraintParams = null;// Arrays.asList(new Object[]
		// { questionType, level });
		return itemDAO.findChildrenCount(parent, persistentClass, childItemType,
				constraintClause, constraintParams);
	}

	/**
	 * Search for a user by criteria.
	 * 
	 * @param searchCriteria
	 *            an object holding a list of search criteria
	 * @return list of questions matching the criteria
	 * @throws ApplicationException
	 *             if a DAO problem occurred
	 */
	public List<B> findByCriteria(SearchCriteria searchCriteria)
	{
		SearchEngine<B> searchEngine = resourceLocator
				.getSearchEngine(baseClass, context);
		List<B> items = searchEngine.search(searchCriteria);
		return items;
	}

	// ========================= CASCADED METHODS ==========================

	/**
	 * Create or update an entire catalog tree in the database.
	 * 
	 * @param item
	 *            item to be updated and all items below this item in the tree
	 *            hierarchy
	 * 
	 * if there a DAO problem occurred
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#updateAll(net.ruready.business.content.item.entity.Item)
	 */
	public void updateAll(final B item)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Saving/Updating item tree " + item.getName());
		}
		ApplySaveOrUpdate.update(this, item);
	}

	/**
	 * Merge an entire catalog tree in the database. Catalog items that already
	 * exist are merged.
	 * 
	 * @param item
	 *            item to be updated and all items below this item in the tree
	 *            hierarchy
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public void mergeAll(final B item)
	{
		if (logger.isInfoEnabled())
		{
			logger.info("Saving/Updating item tree " + item.getName());
		}
		ApplyMerge.merge(this, item);
	}

	/**
	 * Delete an entire catalog tree in the database.
	 * 
	 * @param item
	 *            item to be delete and all items below this item in the tree
	 *            hierarchy. The item's id must be set. *
	 * @param respectLocks
	 *            if <code>false</code>, will delete even read-only and
	 *            unique items
	 * 
	 * if there a DAO problem occurred
	 */
	@SuppressWarnings("unchecked")
	public void deleteAll(final B item, final boolean respectLocks)
	{
		if (item == null)
		{
			return;
		}
		if (logger.isDebugEnabled())
		{
			logger.info("Deleting item hierarchy '" + item.getName() + "', parent "
					+ item.getParent());
		}
		// Do not delete item if it is read-only or unique; only notify client
		// of a "soft"
		// error. Must check here in addition to delete() because we will remove
		// the item tree from associations.
		if (respectLocks && (item.isReadOnly() || item.isUnique()))
		{
			if (logger.isInfoEnabled())
			{
				logger.info("Ignoring delete() of item '" + item.getName() + "'"
						+ " because it is read-only or unique.");
			}
			return;
		}

		// Load item tree and remove it from associations
		ItemDAO<B> itemDAO = (ItemDAO<B>) eisManager.getDAO(item.getItemClass(), context);
		B itemTree = itemDAO.read(item.getId(), true);

		// Keep session open so that itemTree is still attached to it
		// Because of cascading on REMOVE, this will delete the entire tree
		// and then close the session.
		delete(itemTree, respectLocks);
	}

	/**
	 * Find an item by unique property.
	 * 
	 * @param propertyName
	 *            property name
	 * @param uniqueValue
	 *            property value; must be unique per entity
	 * @return unique items that has this property value
	 * 
	 */
	public <T extends B> T findByUniqueProperty(final Class<T> persistentClass,
			final String propertyName, final Object uniqueValue)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding item by uniquely identifying property name '"
					+ propertyName + "' value '" + uniqueValue + "'...");
		}
		DAO<T, Long> itemDAO = eisManager.getDAO(persistentClass, context);
		T item = itemDAO.findByUniqueProperty(propertyName, uniqueValue);
		return item;
	}

	/**
	 * Convert an item's children set to a list of its child item type.
	 * 
	 * @param <T>
	 * @param persistentClass
	 *            desired generic type of list items
	 * @param item
	 *            item's whose children are converted
	 * @return list of item's children, cast to List<persistentClass>
	 */
	@SuppressWarnings("unchecked")
	public <T extends B> List<T> childrenToList(final Class<T> persistentClass,
			final Item item)
	{
		if (item == null)
		{
			throw new RecordNotFoundException(
					"Could not find item to convert children to a list", null);
		}

		// Item OK, create a result set. The original children list
		// is of type List<Node>; need to convert to List<T>.
		List<T> resultSet = new ArrayList<T>();
		for (Node child : item.getChildren())
		{
			try
			{
				resultSet.add((T) child);
			}
			catch (ClassCastException e)
			{
				throw new InvalidItemTypeException("Could not convert child item from "
						+ child.getClass() + " to " + persistentClass, e);

			}
		}
		return resultSet;
	}

	// ========================= UTILITY AND TESTING METHODS ===============

	/**
	 * Rename a prospective child of a parent item to avoid naming conflicts.
	 * 
	 * @param parentNode
	 *            parent node
	 * @param item
	 *            prospective child (renamed if necessary)
	 */
	@SuppressWarnings("unchecked")
	private void renameItem(final Item parentNode, final B item)
	{
		// Rename child if necessary
		Collection<? extends Node> nameSpace = item.isUniqueName() ? findAll((Class<? extends B>) item
				.getItemClass())
				: parentNode.getChildren();
		String newName = ItemUtil.generateNewChildName(item.getName(), nameSpace);
		if (!newName.equals(item.getName()))
		{
			item.setName(newName);
			if (logger.isInfoEnabled())
			{
				logger.info("Renamed item to '" + item.getName()
						+ "' to avoid name conflict");
			}
		}
	}

	/**
	 * Convert an input JSON string to a list of items. Each item is loaded from
	 * the database according to the corresponding JSON element's id property.
	 * If the id property is not found or is null or invalid, the item is looked
	 * up by name. {@link DynaBean} is used as the DTO type.
	 * 
	 * @param <T>
	 *            type of item
	 * @param persistentClass
	 *            desired generic type of list items
	 * @param jsonData
	 *            input JSON string
	 * @return list of item
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#fromJSON(java.lang.Class,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T extends B> List<T> fromJSON(final Class<T> persistentClass,
			final String jsonData)
	{
		// Convert JSON array data to POJOs
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
		List<DynaBean> updatedRecords = JSONArray.toList(jsonArray);

		List<T> items = new ArrayList<T>();
		if (TextUtil.isEmptyTrimmedString(jsonData))
		{
			return items;
		}

		for (DynaBean record : updatedRecords)
		{
			// Read parameters from dynamic bean
			long id = DynaBeanUtil.getPropertyAsLong(record, Node.ID,
					CommonNames.MISC.INVALID_VALUE_LONG);
			// A useful record status passed into the JSON string by the view.
			RecordStatus status = DynaBeanUtil.getPropertyAsEnum(RecordStatus.class,
					record, "status", RecordStatus.NOT_APPLICABLE);
			T item = null;

			switch (status)
			{
				case NEW:
				case UPDATED:
				{
					// New record or existing record that was modified: the
					// "name" property is the id here (weird, I know. An
					// artifact of my limited ExtJS ComboBox knowledge)
					id = DynaBeanUtil.getPropertyAsLong(record, Node.NAME,
							CommonNames.MISC.INVALID_VALUE_LONG);
					item = read(persistentClass, id);
					break;
				}

				case NOT_APPLICABLE:
				{
					// Existing record that was not modified, look up by id
					item = read(persistentClass, id);
					break;
				}

				case DELETED:
				{
					// We won't normally reach this code because deleted records
					// are not submitted with the form, but just in case
					if (logger.isDebugEnabled())
					{
						logger.debug(status + " record ID " + id);
					}
					break;
				}

				default:
				{
					// Unrecognized status
					if (logger.isErrorEnabled())
					{
						logger.error("Invalid record status " + status);
					}
					break;
				}

			}

			if (item != null)
			{
				items.add(item);
			}
		}

		return items;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @see net.ruready.business.content.catalog.exports.AbstractAuditManager#getUser()
	 */
	public User getUser()
	{
		return user;
	}
}
