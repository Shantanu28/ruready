/*****************************************************************************************
 * Source File: DefaultMainItemBD.java
 ****************************************************************************************/
package net.ruready.business.content.main.exports;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.manager.AbstractMainItemManager;
import net.ruready.common.misc.Singleton;
import net.ruready.common.rl.ResourceLocator;

/**
 * A singleton that implements the mainItem BD interface, and relies on a static hook to
 * instantiate a specific manager implementation and a resource locator.
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
public abstract class DefaultMainItemBD implements AbstractMainItemBD, Singleton
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractMainItemManager manager;

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
	protected DefaultMainItemBD(final AbstractMainItemManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractMainItemBD =====

	/**
	 * @param <T>
	 * @param itemClass
	 * @param parentId
	 * @return
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#createUnique(java.lang.Class,
	 *      long)
	 */
	public <T extends MainItem> T createUnique(Class<T> itemClass,
			long parentId)
	{
		return manager.createUnique(itemClass, parentId);
	}

	/**
	 * @param <T>
	 * @param itemClass
	 * @return
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#readUnique(java.lang.Class)
	 */
	public <T extends MainItem> T readUnique(Class<T> itemClass)
	{
		return manager.readUnique(itemClass);
	}

	/**
	 * @param <T>
	 * @param itemClass
	 * @return
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#findUnique(java.lang.Class)
	 */
	public <T extends MainItem> T findUnique(Class<T> itemClass)
	{
		return manager.findUnique(itemClass);
	}

	/**
	 * @param itemType
	 * @return
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#readUniqueByType(net.ruready.business.content.item.entity.ItemType)
	 */
	public Item readUniqueByType(ItemType itemType)
	{
		return manager.readUniqueByType(itemType);
	}

	/**
	 * @param <T>
	 * @param itemClass
	 * @param parentId
	 * @param uniqueName
	 * @param fromFile
	 * @param depenencies
	 * @return
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#createBase(java.lang.Class, long, java.lang.String, boolean, net.ruready.business.content.main.entity.MainItem[])
	 */
	public <T extends MainItem> T createBase(Class<T> itemClass, long parentId,
			String uniqueName, boolean fromFile, MainItem... depenencies)
	{
		return manager.createBase(itemClass, parentId, uniqueName, fromFile, depenencies);
	}

}
