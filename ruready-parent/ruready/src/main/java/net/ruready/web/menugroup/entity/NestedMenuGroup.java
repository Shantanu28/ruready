/*****************************************************************************************
 * Source File: NestedMenuGroup.java
 ****************************************************************************************/
package net.ruready.web.menugroup.entity;

import net.ruready.web.select.exports.OptionList;

/**
 * A group of drop-down (select) menus that depend on each other in a
 * hierarchical way (there's a top level menu, which populates the next menu
 * with a children selection, which in turn populates the grand-children menu,
 * and so on).
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
public interface NestedMenuGroup extends MenuGroup
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Return a menu name.
	 * 
	 * @param level
	 *            menu's level on the menu list
	 * @return menu name
	 */
	String getMenuName(final int level);

	/**
	 * Return the level of a menu.
	 * 
	 * @param menuName
	 *            menu name
	 * @return level of menu in the menu list (<code>0</code> corresponds to
	 *         the top-level menu)
	 */
	int indexOf(final String menuName);

	/**
	 * Return the number of menus in this group.
	 * 
	 * @return the number of menus in this group
	 * @see java.util.List#size()
	 */
	int size();

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
	 *            equals <code>CommonNames.MISC.INVALID_VALUE_INTEGER</code>.
	 * @return children selection of this node
	 */
	OptionList getChildrenSelection(final int level, final long id, final long selectedId);

	/**
	 * Return the parent node identifier of this node.
	 * 
	 * @param level
	 *            this node's menu level on the menu list
	 * @param id
	 *            this node's identifier
	 * @return parent node identifier
	 */
	long getParentId(final int level, final long id);

	/**
	 * Return an i18nalization key for the no-selection option of a menu.
	 * 
	 * @param level
	 *            menu's level on the menu list
	 * @return i18nalization key for the no-selection option of the menu
	 */
	String getNoSelectionKey(final int level);
}
