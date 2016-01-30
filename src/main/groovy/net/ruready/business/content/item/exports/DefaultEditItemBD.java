/*****************************************************************************************
 * Source File: DefaultEditItemBD.java
 ****************************************************************************************/
package net.ruready.business.content.item.exports;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.manager.AbstractEditItemManager;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.common.search.SearchCriteria;

/**
 * An edit item BD that implements the item manager interface. It relies on a
 * static hook to instantiate a specific manager implementation and a resource
 * locator. This type cannot be instantiated.
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
 * @version Jul 31, 2007
 */
public abstract class DefaultEditItemBD<B extends Item> implements AbstractEditItemBD<B>
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractEditItemManager<B> manager;

	/**
	 * Use this specific resource locator.
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
	protected DefaultEditItemBD(final AbstractEditItemManager<B> manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractEditItemBD ====

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#createUnder(long,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public void createUnder(long parentId, B item)
	{
		manager.createUnder(parentId, item);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#update(net.ruready.business.user.entity.DemoCatalogCreator,
	 *      boolean)
	 */
	public void update(B item, final boolean respectLocks)
	{
		manager.update(item, respectLocks);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#merge(net.ruready.business.user.entity.DemoCatalogCreator,
	 *      boolean)
	 */
	public void merge(B item, final boolean respectLocks)
	{
		manager.merge(item, respectLocks);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#read(java.lang.Class,
	 *      long)
	 */
	public <T extends B> T read(Class<T> persistentClass, long id)
	{
		return manager.read(persistentClass, id);
	}

	/**
	 * @param item
	 * @param respectLocks
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#delete(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	public void delete(B item, final boolean respectLocks)
	{
		manager.delete(item, respectLocks);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findById(long)
	 */
	public <T extends B> T findById(Class<T> persistentClass, long id)
	{
		return manager.findById(persistentClass, id);
	}

	/**
	 * @param <T>
	 * @param <S>
	 * @param newParent
	 * @param item
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#copyUnder(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public <T extends B, S extends B> S copyUnder(T newParent, S item)
	{
		return manager.copyUnder(newParent, item);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#moveUnder(net.ruready.common.tree.DemoCatalogCreator,
	 *      net.ruready.common.tree.DemoCatalogCreator)
	 */
	public void moveUnder(B newParent, B item)
	{
		manager.moveUnder(newParent, item);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#moveChildLocation(net.ruready.business.content.item.entity.Item,
	 *      int, int)
	 */
	public void moveChildLocation(B item, int moveFromSN, int moveToSN)
	{
		manager.moveChildLocation(item, moveFromSN, moveToSN);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByName(java.lang.String)
	 */
	public <T extends B> List<T> findByName(Class<T> persistentClass, String name)
	{
		return manager.findByName(persistentClass, name);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByLikeName(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T extends B> List<T> findByLikeName(Class<T> persistentClass, String name)
	{
		return manager.findByLikeName(persistentClass, name);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByExampleObject(org.hibernate.criterion.Example)
	 */
	public <T extends B> List<T> findByExampleObject(Class<T> persistentClass,
			Object example)
	{
		return manager.findByExampleObject(persistentClass, example);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByExample(net.ruready.business.user.entity.DemoCatalogCreator)
	 */
	public <T extends B> List<T> findByExample(Class<T> persistentClass, T item)
	{
		return manager.findByExample(persistentClass, item);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findAll()
	 */
	public <T extends B> List<T> findAll(Class<T> persistentClass)
	{
		return manager.findAll(persistentClass);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findAllNonDeleted(java.lang.Class,
	 *      net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends B> List<T> findAllNonDeleted(Class<T> persistentClass,
			ItemType hierarchyRootId)
	{
		return manager.findAllNonDeleted(persistentClass, hierarchyRootId);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#updateAll(net.ruready.business.user.entity.DemoCatalogCreator)
	 */
	public void updateAll(B item)
	{
		manager.updateAll(item);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#mergeAll(net.ruready.business.user.entity.DemoCatalogCreator)
	 */
	public void mergeAll(B item)
	{
		manager.mergeAll(item);
	}

	/**
	 * @param item
	 * @param respectLocks
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#deleteAll(net.ruready.business.content.item.entity.Item,
	 *      boolean)
	 */
	public void deleteAll(B item, final boolean respectLocks)
	{
		manager.deleteAll(item, respectLocks);
	}

	/**
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findByUniqueProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public <T extends B> T findByUniqueProperty(Class<T> persistentClass,
			String propertyName, Object uniqueValue)
	{
		return manager.findByUniqueProperty(persistentClass, propertyName, uniqueValue);
	}

	/**
	 * @param <T>
	 * @param parent
	 * @param persistentClass
	 * @param childItemType
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildren(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends B> List<T> findChildren(final Item parent,
			Class<T> persistentClass, final ItemType childItemType)
	{
		return manager.findChildren(parent, persistentClass, childItemType);
	}

	/**
	 * @param <T>
	 * @param parent
	 * @param persistentClass
	 * @param childItemType
	 * @return
	 * 
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildrenIds(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends B> List<Long> findChildrenIds(Item parent,
			Class<T> persistentClass, ItemType childItemType)
	{
		return manager.findChildrenIds(parent, persistentClass, childItemType);
	}

	/**
	 * @param <T>
	 * @param parent
	 * @param persistentClass
	 * @param childItemType
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#findChildrenCount(net.ruready.business.content.item.entity.Item,
	 *      java.lang.Class, net.ruready.business.content.item.entity.ItemType)
	 */
	public <T extends B> long findChildrenCount(Item parent, Class<T> persistentClass,
			ItemType childItemType)
	{
		return manager.findChildrenCount(parent, persistentClass, childItemType);
	}

	/**
	 * Basic method delegating search down to the manager layer.
	 * 
	 * @param searchCriteria
	 * @return List of Items that meet the search criteria.
	 */
	public List<B> findByCriteria(SearchCriteria searchCriteria)
	{
		return manager.findByCriteria(searchCriteria);
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param item
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#childrenToList(java.lang.Class,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public <T extends B> List<T> childrenToList(Class<T> persistentClass, Item item)
	{
		return manager.childrenToList(persistentClass, item);
	}

	/**
	 * @param <T>
	 * @param persistentClass
	 * @param jsonData
	 * @return
	 * @see net.ruready.business.content.item.manager.AbstractEditItemManager#fromJSON(java.lang.Class,
	 *      java.lang.String)
	 */
	public <T extends B> List<T> fromJSON(Class<T> persistentClass, String jsonData)
	{
		return manager.fromJSON(persistentClass, jsonData);
	}

	// ========================= GETTERS & SETTERS =========================
}
