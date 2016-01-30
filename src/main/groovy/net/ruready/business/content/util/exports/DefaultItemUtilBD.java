/*****************************************************************************************
 * Source File: DefaultItemUtilBD.java
 ****************************************************************************************/
package net.ruready.business.content.util.exports;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.util.manager.AbstractItemUtilManager;
import net.ruready.common.misc.Singleton;
import net.ruready.common.rl.ResourceLocator;

/**
 * A singleton that implements the item utility BD interface, and relies on a static hook
 * to instantiate a specific manager implementation and a resource locator.
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
 * @version Jul 21, 2007
 */
public abstract class DefaultItemUtilBD implements AbstractItemUtilBD, Singleton
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractItemUtilManager manager;

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
	protected DefaultItemUtilBD(final AbstractItemUtilManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractItemUtilBD =====

	/**
	 * @param itemId
	 * @param itemType
	 * @param parentId
	 * @return
	 * @see net.ruready.business.content.util.manager.AbstractItemUtilManager#findItem(long,
	 *      net.ruready.business.content.item.entity.ItemType, long)
	 */
	public Item findItem(long itemId, ItemType itemType, long parentId)
	{
		return manager.findItem(itemId, itemType, parentId);
	}

	/**
	 * @param itemId
	 * @param itemType
	 * @return
	 * @see net.ruready.business.content.util.manager.AbstractItemUtilManager#findItemById(long,
	 *      net.ruready.business.content.item.entity.ItemType)
	 */
	public Item findItemById(long itemId, ItemType itemType)
	{
		return manager.findItemById(itemId, itemType);
	}

	/**
	 * @param itemType
	 * @return
	 * @see net.ruready.business.content.util.manager.AbstractItemUtilManager#findItemByType(net.ruready.business.content.item.entity.ItemType)
	 */
	public Item findItemByType(ItemType itemType)
	{
		return manager.findItemByType(itemType);
	}

	/**
	 * @param itemType
	 * @param parentId
	 * @return
	 * @see net.ruready.business.content.util.manager.AbstractItemUtilManager#createNewItem(net.ruready.business.content.item.entity.ItemType,
	 *      long)
	 */
	public Item createNewItem(ItemType itemType, long parentId)
	{
		return manager.createNewItem(itemType, parentId);
	}

	/**
	 * @param itemId
	 * @param itemType
	 * @param parentId
	 * @return
	 * @see net.ruready.business.content.util.manager.AbstractItemUtilManager#findOrCreateItem(long, net.ruready.business.content.item.entity.ItemType, long)
	 */
	public Item findOrCreateItem(long itemId, ItemType itemType, long parentId)
	{
		return manager.findOrCreateItem(itemId, itemType, parentId);
	}

}
