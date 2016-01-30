/*****************************************************************************************
 * Source File: AbstractItemUtilManager.java
 ****************************************************************************************/
package net.ruready.business.content.util.manager;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.common.rl.BusinessManager;

/**
 * This service allows item manipulation: creating a new item, updating an existing item,
 * deleting an item, and listing items by certain criteria.
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
public interface AbstractItemUtilManager extends BusinessManager
{
	// ========================= ABSTRACT METHODS ==========================

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
	Item findItem(final long itemId, final ItemType itemType, final long parentId);

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
	Item findItemById(final long itemId, final ItemType itemType);

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
	Item findItemByType(final ItemType itemType);

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
	Item createNewItem(final ItemType itemType, final long parentId);

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
	Item findOrCreateItem(final long itemId, final ItemType itemType, final long parentId);
}
