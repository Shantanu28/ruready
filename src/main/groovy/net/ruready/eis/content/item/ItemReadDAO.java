/*****************************************************************************************
 * Source File: ItemDAO.java
 ****************************************************************************************/
package net.ruready.eis.content.item;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.common.eis.exception.RecordNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A custom Data Access Object (DAO) for a item that defines read-operations
 * only.
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
 * @see net.ruready.item.entity.DemoCatalogCreator
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 9, 2007
 */
public interface ItemReadDAO<T extends Item>
{
	// ========================= CONSTANTS =================================

	static final Log logger = LogFactory.getLog(ItemReadDAO.class);

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Read an entity from the database. Pre-populated fields are overridden if
	 * they are persistent, or unaffected if they are transient. The number of
	 * children of the item is also loaded. If children loading is specified (<code>cascadeType = CHILDREN</code>),
	 * we load the number of children of each item's child as well. Similarly,
	 * siblings are loaded if specified by the cascadeType parameter (<code>cascadeType = SIBLINGS</code>).
	 * If <code>cascadeType = ALL</code>, the entire item sub-tree hierarchy
	 * is loaded.
	 * 
	 * @param id
	 *            unique identifier to search for and load by
	 * @param entity
	 *            entity to be read
	 * @param removeFromAssociations
	 *            if <code>true</code>, the item tree's associations with
	 *            other objects will be removed (e.g. in preparation for
	 *            deleting the item tree)
	 * @return persisted entity after saving it
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found and
	 *             <code>removeFromAssociations = false</code>
	 * 
	 * if another DAO problem occurred
	 */
	T read(final long id, final boolean removeFromAssociations);

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
	 * @param constraintClause
	 *            if non-null, is appended to the query string
	 * @param constraintParams
	 *            a list of parameter values appearing in the constraint clause
	 * @return list of children under the parent; need not be its direct
	 *         children
	 */
	<C extends T> List<C> findChildren(final Item parent, final Class<C> persistentClass,
			final ItemType childItemType, final String constraintClause,
			final List<?> constraintParams);

	/**
	 * Find the list of children identifiers of a certain item type under a
	 * parent node.
	 * 
	 * @param <C>
	 *            type of child
	 * @return parent parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @param constraintClause
	 *            if non-null, is appended to the query string
	 * @param constraintParams
	 *            a list of parameter values appearing in the constraint clause
	 * @return list of children under the parent; need not be its direct
	 *         children
	 * 
	 */
	<C extends T> List<Long> findChildrenIds(final Item parent,
			final Class<C> persistentClass, final ItemType childItemType,
			final String constraintClause, final List<?> constraintParams);

	/**
	 * Find the number of children of a certain item type under a parent node
	 * and satisfy some constraints. We assume the constraint results in a
	 * scalar (unique) result set to be returned from this method.
	 * 
	 * @param <C>
	 *            type of child
	 * @return parent parent node
	 * @param persistentClass
	 *            child type class
	 * @param childItemType
	 *            type (identifier) of children to look for under the parent
	 * @param constraintClause
	 *            if non-null, is appended to the query string
	 * @param constraintParams
	 *            a list of parameter values appearing in the constraint clause
	 * @return number of children under the parent and satisfy the constraints
	 *         specified by <code>constraintClause</code>; need not be its
	 *         direct children
	 * 
	 */
	@SuppressWarnings("unchecked")
	<C extends T> long findChildrenCount(final Item parent,
			final Class<C> persistentClass, final ItemType childItemType,
			final String constraintClause, final List<?> constraintParams);

	/**
	 * List all items in the database that are not under the trash can. Note:
	 * assumes that the items are under the a tree-structure hierarchy (every
	 * child has one parent).
	 * 
	 * @param hierarchyRootId
	 *            root of hierarchy that the item type belongs to; must be a
	 *            tree structure and must be a {@link MainItem} whose level in
	 *            the item hierarchy equals the trash can's.
	 * @return a list of all non-deleted items
	 * 
	 * if there a DAO problem occurred
	 */
	@SuppressWarnings("unchecked")
	List<T> findAllNonDeleted(final ItemType hierarchyRootId);
}
