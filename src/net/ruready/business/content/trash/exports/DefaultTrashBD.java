/*****************************************************************************************
 * Source File: DefaultTrashBD.java
 ****************************************************************************************/
package net.ruready.business.content.trash.exports;

import java.util.Collection;
import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.trash.manager.AbstractTrashManager;
import net.ruready.common.misc.Singleton;
import net.ruready.common.rl.ResourceLocator;

/**
 * A singleton that implements the trash BD interface, and relies on a static hook to
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
public abstract class DefaultTrashBD implements AbstractTrashBD, Singleton
{
	// ========================= CONSTANTS =================================

	/**
	 * We construct a manager and delegate all methods to it.
	 */
	protected final AbstractTrashManager manager;

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
	protected DefaultTrashBD(final AbstractTrashManager manager,
			final ResourceLocator resourceLocator)
	{
		super();
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}

	// ========================= IMPLEMENTATION: AbstractTrashBD =====

	/**
	 * @see net.ruready.business.content.catalog.manager.AbstractTrashManager#delete(net.ruready.entity.catalog.DemoCatalogCreator,
	 *      net.ruready.entity.catalog.DemoCatalogCreator)
	 */
	public void delete(Item trash, Item node)
	{
		manager.delete(trash, node);
	}

	/**
	 * @param trash
	 * @param node
	 * @see net.ruready.business.content.trash.manager.AbstractTrashManager#hardDelete(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	public void hardDelete(Item trash, Item node)
	{
		manager.hardDelete(trash, node);
	}

	/**
	 * @see net.ruready.business.content.catalog.manager.AbstractTrashManager#findAll(net.ruready.entity.catalog.DemoCatalogCreator)
	 */
	public Collection<Item> findAll(Item trash)
	{
		return manager.findAll(trash);
	}

	/**
	 * @see net.ruready.business.content.catalog.manager.AbstractTrashManager#restore(net.ruready.entity.catalog.DemoCatalogCreator,
	 *      net.ruready.entity.catalog.DemoCatalogCreator,
	 *      net.ruready.entity.catalog.DemoCatalogCreator)
	 */
	public void restore(Item trash, Item node, Item newParent)
	{
		manager.restore(trash, node, newParent);
	}

	/**
	 * @see net.ruready.business.content.catalog.manager.AbstractTrashManager#findById(long)
	 */
	public Item findById(long id)
	{
		return manager.findById(id);
	}

	/**
	 * @see net.ruready.business.content.catalog.manager.AbstractTrashManager#findByName(java.lang.String)
	 */
	public List<Item> findByName(String name)
	{
		return manager.findByName(name);
	}

	/**
	 * @see net.ruready.business.content.trash.entity.AbstractTrash#clear()
	 */
	public void clear()
	{
		manager.clear();
	}

	/**
	 * @see net.ruready.business.content.trash.entity.AbstractTrash#expunge()
	 */
	public void expunge()
	{
		manager.expunge();
	}

	// ========================= GETTERS & SETTERS =========================
}
