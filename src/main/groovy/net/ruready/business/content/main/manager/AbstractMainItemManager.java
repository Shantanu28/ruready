/*****************************************************************************************
 * Source File: AbstractCatalogManager.java
 ****************************************************************************************/
package net.ruready.business.content.main.manager;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.rl.BusinessManager;

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
 * @version Aug 11, 2007
 */
public interface AbstractMainItemManager extends BusinessManager
{
	// ========================= CRUD METHODS ==============================

	/**
	 * Create an object of this <i>unique</i> main item type in the database if
	 * not found. The item's unique name is taken from a map of hard-coded
	 * names.
	 * 
	 * @param <T>
	 *            type of unique main item
	 * @param class
	 *            of main item, must match <code>T</code>
	 * @param parentId
	 *            object's parent (root node's id or <code>null</code> if we
	 *            are creating the root)
	 * @param uniqueName
	 *            object unique name
	 * @return the unique main item
	 * 
	 * if a DAO problem has occurred
	 */
	<T extends MainItem> T createUnique(final Class<T> itemClass, final long parentId);

	/**
	 * Find the unique object of this type the database.
	 * 
	 * @param <T>
	 *            type of unique main item
	 * @param class
	 *            of main item, must match <code>T</code>
	 * @return the unique object of this type
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 * if a DAO problem has occurred
	 */
	<T extends MainItem> T readUnique(final Class<T> itemClass);

	/**
	 * Find the unique object of this type the database.
	 * 
	 * @param <T>
	 *            type of unique main item
	 * @param itemType
	 *            type of item
	 * @return the unique object of this type
	 * @throws RecordNotFoundException
	 *             if <T> does not extend MainItem, or if a record with this id
	 *             was not found
	 * 
	 * if a DAO problem has occurred
	 */
	Item readUniqueByType(final ItemType itemType);

	// ========================= FINDER METHODS ============================

	/**
	 * Find the unique object of this type the database.
	 * 
	 * @param <T>
	 *            type of unique main item
	 * @param class
	 *            of main item, must match <code>T</code>
	 * @return the unique object of this type
	 * 
	 * if a DAO problem has occurred
	 */
	<T extends MainItem> T findUnique(final Class<T> itemClass);

	// ========================= UTILITY AND TESTING METHODS ===============

	/**
	 * Create a base object of this main item type in the database if not found.
	 * The base hierarchy consists of some example items for testing purposes.
	 * 
	 * @param <T>
	 *            type of main item
	 * @param class
	 *            of main item, must match <code>T</code>
	 * @param parentId
	 *            base hierarchy's parent (root) node's id
	 * @param uniqueName
	 *            base hierarchy's name
	 * @param fromFile
	 *            if true, loads base from file. Otherwise, uses hard-coded data
	 * @param depenencies
	 *            main items that this main item depend on. See the constructors
	 *            of the individual demo creator classes in this package.
	 * @return the base main item
	 */
	<T extends MainItem> T createBase(final Class<T> itemClass, final long parentId,
			final String uniqueName, final boolean fromFile,
			final MainItem... depenencies);
}
