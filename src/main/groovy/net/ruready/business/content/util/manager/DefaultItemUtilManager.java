/*****************************************************************************************
 * Source File: DefaultItemUtilManager.java
 ****************************************************************************************/
package net.ruready.business.content.util.manager;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.manager.AbstractEditItemManager;
import net.ruready.business.content.main.exception.InvalidItemTypeException;
import net.ruready.business.content.main.manager.AbstractMainItemManager;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This service consists of miscellaneous item utility.
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
 * @version Aug 11, 2007
 */
public class DefaultItemUtilManager implements AbstractItemUtilManager
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultItemUtilManager.class);

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs for {@link Item} classes.
	 */
	protected final ResourceLocator resourceLocator;

	/**
	 * Retrieved from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects and other useful properties.
	 */
	protected final ApplicationContext context;

	/**
	 * The user requesting DAO operations.
	 */
	protected final User user;

	/**
	 * An item manipulation manager to use.
	 */
	protected final AbstractEditItemManager<Item> bdItem;

	/**
	 * A main item manager to use.
	 */
	protected final AbstractMainItemManager bdMainItem;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to read/write
	 * items to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 * @param bdItem
	 *            An item manipulation manager to use
	 * @param bdMainItem
	 *            A main item manager to use.
	 */
	public DefaultItemUtilManager(final ResourceLocator resourceLocator,
			final ApplicationContext context, final User user,
			final AbstractEditItemManager<Item> bdItem,
			final AbstractMainItemManager bdMainItem)

	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
		this.user = user;
		this.bdItem = bdItem;
		this.bdMainItem = bdMainItem;
	}

	// ========================= IMPLEMENTATION: AbstractItemUtilManager ===

	/**
	 * Returns a database item by ID. If not found, returns <code>null</code>. There
	 * are three cases:
	 * <p>
	 * 1. The ID is non-negative. We search for the item in the database and load it via
	 * the DAO corresponding to its type.<br>
	 * 2. The ID is not found in the request, or equals
	 * <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>. We try to find the unique
	 * item of the type parameter (e.g. the root node or the trash can) in the database,
	 * if this is a unique type.<br>
	 * 3. The ID is not found in the request, or equals
	 * <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>, and 2 failed. Throw an
	 * exception.<br>
	 * 
	 * @param itemType
	 *            item enumerated type
	 * @param id
	 *            item's unique id. If negative, its value should be the corresponding
	 *            unique item type's value.
	 * @param parentId
	 *            parent ID to be used in case of a new item
	 * @return a database item by ID. If not found, returns <code>null</code>
	 */
	public Item findItem(final long itemId, final ItemType itemType, final long parentId)
	{
		if (itemId == CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			// ID not found. Instantiate a unique item by type
			return findItemByType(itemType);
		}
		else
		{
			// Load by ID; item object is assumed to exist in the database
			return findItemById(itemId, itemType);
		}
	}

	/**
	 * Returns a database item by id. If not found, returns <code>null</code>.
	 * 
	 * @param itemId
	 *            item's unique id in the database
	 * @param itemType
	 *            item enumerated type
	 * @param cascadeType
	 *            specifies optional cascading (loading children/siblings/entire sub-tree
	 *            as well).
	 * @return a database item by type. If not found, returns <code>null</code>
	 */
	public Item findItemById(final long itemId, final ItemType itemType)
	{
		// Look for the item to edit by the provided itemId. if the item type
		// is also available, pass in a dummy empty item of the type corresponding to
		// itemType, so that the correct item type is instantiated within
		// the manager method.
		Class<? extends Item> itemClass = (itemType == null) ? Item.class : itemType
				.createItem(null, null).getItemClass();
		Item item = bdItem.read(itemClass, itemId);
		if ((item != null) && (item.getParent() != null))
		{
			item.setParentId(item.getParent().getId());
		}
		return item;
	}

	/**
	 * Returns a database item by type. If not found, returns <code>null</code>.
	 * 
	 * @param itemType
	 *            item enumerated type
	 * @param itemType
	 *            item enumerated type
	 * @param user
	 *            user requesting the item
	 * @return a database item by type. If not found, returns <code>null</code>
	 */
	public Item findItemByType(final ItemType itemType)
	{
		ItemType type = itemType;
		if (itemType == null)
		{
			type = ItemType.ROOT;
			logger.debug("Item ID and item type not found, using a default itemType "
					+ itemType);
		}

		return bdMainItem.readUniqueByType(type);
	}

	/**
	 * Create a new item by type. If not found or if its parent is not found, returns
	 * <code>null</code>.
	 * 
	 * @param itemType
	 *            item enumerated type
	 * @param parentId
	 *            parent ID to place item under in the item hierarchy
	 * @param errors
	 *            we add errors to this list if we encounter problems finding the items
	 * @return a new item by type. If a problem is encountered, returns <code>null</code>
	 */
	public Item createNewItem(final ItemType itemType, final long parentId)
	{
		if (itemType == null)
		{
			throw new InvalidItemTypeException("No item type supplied", itemType);
		}

		// Create a blank item
		Item item = itemType.createItem(CommonNames.MISC.EMPTY_STRING,
				CommonNames.MISC.EMPTY_STRING);
		item.setNewItem(true);

		if (parentId != CommonNames.MISC.INVALID_VALUE_INTEGER)
		{
			// Found item's prospective parent identifier, set in the item entity
			item.setParentId(parentId);
		}

		return item;
	}

	/**
	 * Returns a database item by ID. If not found, returns <code>null</code>. There
	 * are three cases:
	 * <p>
	 * 1. The ID is non-negative. We search for the item in the database and load it via
	 * the DAO corresponding to its type.<br>
	 * 2. The ID is not found in the request, or equals
	 * <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>. We try to find the unique
	 * item of the type parameter (e.g. the root node or the trash can) in the database,
	 * if this is a unique type.<br>
	 * 3. The ID is not found in the request, or equals
	 * <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>, and 2 failed. We instantiate
	 * an a new (blank) item of this type. This is case used by actions that add new items
	 * to the database.<br>
	 * 
	 * @param itemType
	 *            item enumerated type
	 * @param id
	 *            item's unique id. If negative, its value should be the corresponding
	 *            unique item type's value.
	 * @param parentId
	 *            parent ID to be used in case of a new item
	 * @return a database item by ID. If not found, returns <code>null</code>
	 */
	public Item findOrCreateItem(final long itemId, final ItemType itemType,
			final long parentId)
	{
		try
		{
			return findItemById(itemId, itemType);
		}
		catch (RecordNotFoundException e)
		{
			// ID not found and not a unique item type. Create a new item.
			return createNewItem(itemType, parentId);
		}
	}
}
