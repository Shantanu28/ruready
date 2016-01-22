/*****************************************************************************************
 * Source File: ItemTypeUtil.java
 ****************************************************************************************/
package net.ruready.business.content.util.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.main.exception.InvalidItemTypeException;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.text.TextUtil;
import net.ruready.common.tree.TreeInspector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Centralizes utilities related to item types ({@link ItemType}).
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
 * @version Jul 29, 2007
 */
public class ItemTypeUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ItemTypeUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private ItemTypeUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Return the unique path length from a source (parent) item type to a
	 * destination (child) item type. Right now this method supports tree
	 * structures only, so the path length is unique.
	 * 
	 * @param source
	 *            beginning of path; must be a tree
	 * @param destination
	 *            end of path
	 * @return unique path length from <code>source</code> to
	 *         <code>destination</code>. May be empty. Returns
	 *         <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code> if no path
	 *         is found.
	 * @throws ApplicationException
	 *             if and only if <code>source</code> is not a tree
	 */
	public static int getPathLength(final ItemType source, final ItemType destination)
	{
		TreeInspector<ItemType, ItemType> inspector = new TreeInspector<ItemType, ItemType>();
		if (!inspector.isTree(source))
		{
			throw new ApplicationException(
					"Path length list computation is not supported for non-tree structures:"
							+ " source " + source + " destination " + destination);
		}

		// If source = destination, there's one path of length 0
		if (source == destination)
		{
			return 0;
		}

		// Otherwise, compute the path lengths from source's children to
		// destination,
		// and add 1 to the only positive number found
		for (ItemType child : source.getChildren())
		{
			int childLength = ItemTypeUtil.getPathLength(child, destination);
			if (childLength != CommonNames.MISC.INVALID_VALUE_INTEGER)
			{
				// Found path that includes this child
				return childLength + 1;
			}
		}

		// Path not found
		return CommonNames.MISC.INVALID_VALUE_INTEGER;
	}

	/**
	 * Return a list of lengths of possible paths from a source (parent) item
	 * type to a destination (child) item type. Right now this method supports
	 * tree structures only, so the path length is unique. In the future, this
	 * may change.
	 * 
	 * @param source
	 *            beginning of path
	 * @param destination
	 *            end of path
	 * @return list of path lengths from <code>source</code> to
	 *         <code>destination</code>. May be empty. It is
	 *         <code>null</code> if and only if <code>source</code> is not a
	 *         tree
	 */
	public static Set<Integer> getPathLengthList(final ItemType source,
			final ItemType destination)
	{
		TreeInspector<ItemType, ItemType> inspector = new TreeInspector<ItemType, ItemType>();
		if (!inspector.isTree(source))
		{
			// throw new ApplicationException(
			// "Path length list computation is not supported for non-tree
			// structures");
			return null;
		}

		Set<Integer> pathLengthSet = new HashSet<Integer>();
		// If source = destination, there's one path of length 0
		if (source == destination)
		{
			pathLengthSet.add(0);
			return pathLengthSet;
		}

		// Otherwise, compute the path lengths from source's children to
		// destination,
		// and add 1
		for (ItemType child : source.getChildren())
		{
			Set<Integer> childSet = ItemTypeUtil.getPathLengthList(child, destination);
			for (Integer pathLength : childSet)
			{
				pathLengthSet.add(pathLength + 1);
			}
		}

		return pathLengthSet;
	}

	/**
	 * A factory method that creates an item corresponding to a type.
	 * 
	 * @param itemType
	 *            requested item type
	 * @param name
	 *            name of this item
	 * @param comment
	 *            comment on this item
	 * @return an item instance of the request type with the specified name and
	 *         comment
	 */
	public static Item createItem(final ItemType itemType, final String name,
			final String comment)
	{
		if (itemType == null)
		{
			throw new InvalidItemTypeException("Item type was not specified", itemType);
		}
		return itemType.createItem(name, comment);
	}

	/**
	 * Is a child type valid for this item. The children of an item are
	 * <code>Item</code>s. This might change to a more concrete type at a
	 * later stage.
	 * 
	 * @param childId
	 *            type of child comment on this item
	 * @return is a child type valid for this item
	 */
	public static boolean isValidChildType(final ItemType childId,
			final List<ItemType> allowedTypes)
	{
		if ((childId == null) || (allowedTypes == null))
		{
			return false;
		}
		if (allowedTypes.contains(childId))
		{
			// Found an exact match for child id in the allowed list
			return true;
		}

		// Attempt to see whether one of item's super-classes is in the allowed
		// list
		Item child = childId.createItem(null, null);
		if (child == null)
		{
			return false;
		}
		for (ItemType allowed : allowedTypes)
		{
			Item example = allowed.createItem(null, null);
			if ((example != null)
					&& (example.getClass().isAssignableFrom(child.getClass())))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Determine how many ".parent" need to be appended to a query string
	 * according to the location of <code>parentID</code> and
	 * <code>childID</code> in the Item tree, and return this field name
	 * string.
	 * 
	 * @param parentID
	 *            parent item type; must be a tree
	 * @param childID
	 *            child item type
	 * @return query string (e.g. "parent.parent" if <code>parentID</code> is
	 *         the grand-parent of <code>childID</code> in the item tree)
	 * @throws ApplicationException
	 *             if <code>parentID</code> is not a tree
	 */
	public static String getSuperParentFieldName(final ItemType parentID,
			final ItemType childID)
	{
		// Determine how many ".parent" need to be
		// appended to the query string according to the location of parent and
		// child in the Item tree).
		final int pathLength = ItemTypeUtil.getPathLength(parentID, childID);
		StringBuffer parentSnippet = TextUtil.emptyStringBuffer();
		for (int i = 0; i < pathLength; i++)
		{
			if (i > 0)
			{
				parentSnippet.append(".");
			}
			parentSnippet.append("parent");
		}
		return parentSnippet.toString();
	}

	/**
	 * Adds the necessary aliases required to define a super-parent of an item.
	 * If the distance between the item and the super-parent is d, this method
	 * adds the aliases superParent1,superParent2,...,superParent(d-1) and
	 * superParent (which would have been superParentd in this sequence, yet
	 * with a simpler, fixed alias name). If the distance is non-positive, this
	 * method has no effect.
	 * 
	 * @param criteria
	 *            search criteria to add aliases to
	 * @param superParentType
	 *            super parent item type
	 * @param thisType
	 *            this item's type (the main searchable entity)
	 */
	public static void addSuperParentAlias(final SearchCriteria criteria,
			final ItemType superParentType, final ItemType thisType)
	{
		final int height = ItemTypeUtil.getPathLength(superParentType, thisType);
		for (int i = 1; i <= height; i++)
		{
			criteria.addAlias("superParent" + ((i == height) ? "" : i),
					((i == 1) ? "this.parent" : "superParent" + (i - 1) + ".parent"));
		}
	}
}
