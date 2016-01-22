/*****************************************************************************************
 * Source File: CatalogNestedMenuGroup.java
 ****************************************************************************************/
package net.ruready.web.content.item.form;

import java.util.ArrayList;
import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.menugroup.entity.MenuProperties;
import net.ruready.web.menugroup.entity.NestedMenuGroup;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A generic nested menu group that represents an item hierarchy in the CM. This
 * is in effect a data transfer object from the business layer to an Struts AJAX
 * action.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 7, 2007
 */
public abstract class ItemMenuGroup implements NestedMenuGroup
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ItemMenuGroup.class);

	/**
	 * Suffix added to all menu names on the <code>menus</code> list.
	 */
	private static final String MENU_IDENTIFIER_SUFFIX = "Id";

	// ========================= FIELDS ====================================

	/**
	 * Manager of item operations.
	 */
	private final AbstractEditItemBD<Item> bdItem;

	/**
	 * List of menu names in their order of dependence.
	 */
	private final List<MenuProperties<ItemType, Item>> menus = new ArrayList<MenuProperties<ItemType, Item>>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize a catalog menu group.
	 * 
	 * @param context
	 *            web application context
	 * @param topMenuItemClass
	 *            Top-level menu - item class type
	 * 
	 * @param topMenuParentItemType
	 *            Encompassing item type to look for children for the top-level
	 *            menu selection
	 * @param itemTypes
	 *            List of menu identifiers in their order of dependence.
	 * @param selectionRequired
	 *            corresponding flags to itemTypes that determine whether
	 *            selection is required from each menu
	 */
	public ItemMenuGroup(final ApplicationContext context, final User user,
			final MenuProperties<ItemType, Item>... menus)
	{
		bdItem = new StrutsEditItemBD<Item>(Item.class, context, user);
		for (MenuProperties<ItemType, Item> menu : menus)
		{
			addMenu(menu);
		}
	}

	// ========================= IMPLEMENTATION: NestedMenuGroup ===========

	/**
	 * Return the parent node identifier of this node.
	 * 
	 * @param level
	 *            this node's menu level on the menu list
	 * @param id
	 *            this node's identifier
	 * @return parent node identifier
	 * @see net.ruready.web.menugroup.entity.NestedMenuGroup#getParentId(int,
	 *      long)
	 */
	public long getParentId(final int level, final long id)
	{
		if (level == 0)
		{
			// Top level menu items have no parent
			return CommonNames.MISC.INVALID_VALUE_LONG;
		}

		// Read item with this id from the database
		Item item = bdItem.read(Item.class, id);
		Item parent = item.getSuperParent(menus.get(level - 1).identifier);
		return (parent == null) ? CommonNames.MISC.INVALID_VALUE_LONG : parent.getId();
	}

	/**
	 * Return a menu name.
	 * 
	 * @param level
	 *            menu's level on the menu list
	 * @return menu name
	 * @see java.util.List#get(int)
	 */
	public String getMenuName(int level)
	{
		return menus.get(level).name;
	}

	/**
	 * Return an i18nalization key for the no-selection option of a menu.
	 * 
	 * @param level
	 *            menu's level on the menu list
	 * @return i18nalization key for the no-selection option of the menu
	 * @see java.util.List#get(int)
	 */
	public String getNoSelectionKey(final int level)
	{
		return menus.get(level).noSelectionKey;
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(final String menuName)
	{
		MenuProperties<ItemType, Item> element = new MenuProperties<ItemType, Item>();
		element.name = menuName;
		return menus.indexOf(element);
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size()
	{
		return menus.size();
	}

	/**
	 * Return the children selection of this node.
	 * 
	 * @param level
	 *            menu's level on the menu list. If negative, this method
	 *            returns the top menu selection
	 * @param id
	 *            this node's identifier (selection value from menu number
	 *            <code>level</code>)
	 * @param selectedId
	 *            an id to set as selected in the generated menu. Ignore if
	 *            equals <code>CommonNames.MISC.INVALID_VALUE_LONG</code>.
	 * @return children selection of this node
	 * @see net.ruready.web.menugroup.entity.NestedMenuGroup#getChildrenSelection(int,
	 *      long, long)
	 */
	public OptionList getChildrenSelection(final int level, final long id,
			final long selectedId)
	{
		List<? extends Item> children;
		if (level < 0)
		{
			// Top-level menu
			MenuProperties<ItemType, Item> topMenu = menus.get(0);
			children = bdItem.findAllNonDeleted(topMenu.clazz, topMenu.parentIdentifier);
		}
		else if (level < menus.size() - 1)
		{
			// Some menu below the top menu
			if (id == CommonNames.MISC.INVALID_VALUE_LONG)
			{
				return null;
			}
			Item item = bdItem.read(Item.class, id);
			ItemType childType = menus.get(level + 1).identifier;
			children = (item == null) ? null : bdItem.findChildren(item, childType
					.getItemClass(), childType);
		}
		else
		{
			// Unsupported menu level
			return null;
		}

		// Convert to an option list and set selected value.
		// The empty option is set to "parentMenuName_id", if the parent's id is
		// non-null.
		String emptySelectionValue = (level < 0) ? CommonNames.MISC.EMPTY_STRING
				: (getMenuName(level) + CommonNames.MISC.SEPARATOR + id);
		return StrutsUtil.items2OLS(children, menus.get(level + 1).selectionRequired,
				emptySelectionValue, selectedId);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Add a menu by ID.
	 * 
	 * @param menuID
	 *            menu identifier
	 * @param required
	 *            is menu selection required or not
	 */
	private void addMenu(final MenuProperties<ItemType, Item> menu)
	{
		final MenuProperties<ItemType, Item> copy = menu.clone();
		copy.name = menu.identifier.getCamelCaseName() + MENU_IDENTIFIER_SUFFIX;
		menus.add(copy);
	}

	// ========================= GETTERS & SETTERS =========================
}