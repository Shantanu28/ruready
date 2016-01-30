/*****************************************************************************************
 * Source File: AbstractTrashManager.java
 ****************************************************************************************/
package net.ruready.business.content.trash.manager;

import java.util.Collection;
import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.trash.entity.AbstractTrash;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.BusinessManager;

/**
 * This service manages the trash can. This includes deleting and undeleting nodes,
 * cleaning the trash, etc.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 25, 2007
 */
public interface AbstractTrashManager extends BusinessManager, AbstractTrash
{
	// ========================= ABSTRACT METHODS ==========================

	// ========================= ITEM MANIPULATION METHODS =================

	/**
	 * Physically delete an existing node and all its descendants from the catalog tree.
	 * The node is removed from all associations first, and then deleted. If the node
	 * doesn't exist, this method has no effect.
	 * 
	 * @param trash
	 *            trash can node
	 * @param node
	 *            node to be deleted
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void hardDelete(Item trash, Item node);

	/**
	 * Delete an existing node from the catalog tree. This moves it under the trash
	 * instead of physically removing it. If the node doesn't exist, this method has no
	 * effect.
	 * 
	 * @param trash
	 *            trash can node
	 * @param node
	 *            node to be deleted
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void delete(Item trash, Item node);

	/**
	 * Restore an existing node from the catalog tree. This moves it from the trash to
	 * being under a new parent node. If the node doesn't exist, this method has no
	 * effect.
	 * 
	 * @param trash
	 *            trash can node
	 * @param node
	 *            node to be restored
	 * @param newParent
	 *            new parent of the node
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	void restore(Item trash, Item node, Item newParent);

	/**
	 * List all nodes in the trash can.
	 * 
	 * @param trash
	 *            trash can node
	 * @param trash
	 *            trash can node
	 * @return a list of all nodes
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	Collection<Item> findAll(Item trash);

	/**
	 * Search for a deleted node in the trash that matches a unique ID identifier. If this
	 * node is not found, this method will not throw an exception, but when we try to
	 * access the returned object, Hibernate will throw an
	 * <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            node ID to search by
	 * @return node, if found
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	Item findById(long id);

	/**
	 * Search for a deleted node in the trash that matches a name.
	 * 
	 * @param name
	 *            name to search for
	 * @return list of nodes matching the name
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	List<Item> findByName(String name);

	// ========================= CATALOG AND CASCADED METHODS ==============

	// ========================= UTILITY AND TESTING METHODS ===============
}
