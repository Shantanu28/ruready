/*****************************************************************************************
 * Source File: AbstractEditItemManager.java
 ****************************************************************************************/
package net.ruready.business.content.item.manager;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.BusinessManager;
import net.ruready.common.search.SearchCriteria;

/**
 * This service allows item manipulation: creating a new item, updating an
 * existing item, deleting an item, and listing items by certain criteria. It
 * depends on two parameters:<br>
 * <B> base item type (e.g. Item); parameterizes the entire class <T> specific
 * item type for some methods (e.g. {@link AbstractEditItemManager#findById}).
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
 * @version Jul 30, 2007
 */
public interface AbstractEditItemManager<B extends Item> extends BusinessManager
{
	// ========================= CRUD OPERATIONS ===========================

	/**
	 * Add an item under another tree item and save the changes to the database.
	 * 
	 * @param parentId
	 *            id of catalog node with this id. If the parent ID is not
	 *            found, an <code>ApplicationException</code> is thrown
	 * @param item
	 *            new item to be added under the specified parent node. The
	 *            constructed node for item will be by default a toggleable
	 *            sorted tree.
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void createUnder(final long parentId, final B item);

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
	void update(final B item, final boolean respectLocks);

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
	void merge(final B item, final boolean respectLocks);

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
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	<T extends B> T read(final Class<T> persistentClass, final long id);

	/**
	 * Delete an existing item from the the database. If the item doesn't exist,
	 * this method has no effect.
	 * 
	 * @param item
	 *            item to be deleted
	 * @param respectLocks
	 *            if <code>false</code>, will delete even read-only and
	 *            unique items
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void delete(final B item, final boolean respectLocks);

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
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	<T extends B, S extends B> S copyUnder(final T newParent, final S item);

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
	void moveUnder(final B newParent, final B item);

	/**
	 * Move a child to a new index in the item's children array.
	 * 
	 * @param item
	 *            item to be moved
	 * @param moveFromSN
	 *            original serial # of child
	 * @param moveToSN
	 *            new serial # of child
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void moveChildLocation(final B item, final int moveFromSN, final int moveToSN);

	// ========================= FINDER METHODS (CUSTOM READS) =============

	/**
	 * List all items in the database of type <T>.
	 * 
	 * @return a list of all items
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	<T extends B> List<T> findAll(final Class<T> persistentClass);

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
	 */
	<T extends B> List<T> findAllNonDeleted(final Class<T> persistentClass,
			final ItemType hierarchyRootId);

	/**
	 * Search for the item that matches a unique ID identifier. If this item is
	 * not found, this method will not throw an exception, but when we try to
	 * access the returned object, Hibernate will throw an
	 * <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            item ID to search by
	 * @return item, if found
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	<T extends B> T findById(Class<T> persistentClass, long id);

	/**
	 * Search for an item that matches a name.
	 * 
	 * @param name
	 *            name to search for
	 * @return list of items matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	<T extends B> List<T> findByName(final Class<T> persistentClass, final String name);

	/**
	 * Search for an item that begins with name.
	 * 
	 * @param <T>
	 * @param persistentClass
	 * @param name
	 *            the name to look for
	 * @return a list of items that begin with name.
	 */
	<T extends B> List<T> findByLikeName(final Class<T> persistentClass, final String name);

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
	<T extends B> T findByUniqueProperty(final Class<T> persistentClass,
			final String propertyName, final Object uniqueValue);

	/**
	 * Search for an item that matches an example.
	 * 
	 * @param example
	 *            example criterion
	 * @return list of items matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	<T extends B> List<T> findByExampleObject(final Class<T> persistentClass,
			final Object example);

	/**
	 * Search for an item that matches an example.
	 * 
	 * @param item
	 *            example item instance to match
	 * @return list of items matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	<T extends B> List<T> findByExample(final Class<T> persistentClass, T item);

	/**
	 * Find the list of children of a certain item type under a parent node.
	 * 
	 * @param <T>
	 *            type of child
	 * @param parent
	 *            parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @return list of children under the parent; need not be its direct
	 *         children
	 * 
	 */
	<T extends B> List<T> findChildren(final Item parent, final Class<T> persistentClass,
			final ItemType childItemType);

	/**
	 * Find the list of children identifiers of a certain item type under a
	 * parent node.
	 * 
	 * @param <T>
	 *            type of child
	 * @param parent
	 *            parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @return list of children IDs under the parent; need not be its direct
	 *         children
	 * 
	 */
	<T extends B> List<Long> findChildrenIds(final Item parent,
			final Class<T> persistentClass, final ItemType childItemType);

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
	<T extends B> long findChildrenCount(final Item parent,
			final Class<T> persistentClass, final ItemType childItemType);

	/**
	 * Find questions by criteria using the search framework.
	 * 
	 * @param searchCriteria
	 *            search criteria
	 * @return list of Items that match the criteria
	 */
	List<B> findByCriteria(SearchCriteria searchCriteria);

	// ========================= CASCADED METHODS ==========================

	/**
	 * Create or update an entire catalog tree in the database.
	 * 
	 * @param item
	 *            item to be updated and all items below this item in the tree
	 *            hierarchy
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void updateAll(final B item);

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
	void mergeAll(final B item);

	/**
	 * Delete an entire catalog tree in the database.
	 * 
	 * @param item
	 *            item to be delete and all items below this item in the tree
	 *            hierarchy
	 * @param respectLocks
	 *            if <code>false</code>, will delete even read-only and
	 *            unique items
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void deleteAll(final B item, final boolean respectLocks);

	// ========================= UTILITY AND TESTING METHODS ===============

	/**
	 * Convert an item's children set to a list of its child item type.
	 * 
	 * @param <T>
	 *            type of child
	 * @param persistentClass
	 *            desired generic type of list items
	 * @param item
	 *            item's whose children are converted
	 * @return list of item's children, cast to List<persistentClass>
	 */
	<T extends B> List<T> childrenToList(final Class<T> persistentClass, final Item item);

	/**
	 * Convert an input JSON string to a list of items. Each item is loaded from
	 * the database according to the corresponding JSON element's id property.
	 * If the id property is not found or is null or invalid, the item is looked
	 * up by name.
	 * 
	 * @param <T>
	 *            type of item
	 * @param persistentClass
	 *            desired generic type of list items
	 * @param jsonData
	 *            input JSON string
	 * @return list of item
	 */
	<T extends B> List<T> fromJSON(final Class<T> persistentClass, final String jsonData);
}
