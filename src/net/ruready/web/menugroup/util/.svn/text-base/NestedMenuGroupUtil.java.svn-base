/*****************************************************************************************
 * Source File: PopulateMenuAction.java
 ****************************************************************************************/
package net.ruready.web.menugroup.util;

import java.util.HashMap;
import java.util.Map;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.menugroup.entity.MenuEvent;
import net.ruready.web.menugroup.entity.NestedMenuGroup;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Handle AJAX events of populating and updating a menu group.
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
public class NestedMenuGroupUtil
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(NestedMenuGroupUtil.class);

	// ========================= PUBLIC METHODS ============================

	/**
	 * Generate menu selections following a menu change event.
	 * 
	 * @param group
	 *            menu group
	 * @param menuEvent
	 *            menu change event
	 * @return map of selections ({@link OptionList}s) to be transferred back
	 *         to the client
	 */
	public static Map<String, OptionList> generateMenuSelection(
			final NestedMenuGroup group, final MenuEvent menuEvent)
	{
		// Compute menu levels to populate
		long id = menuEvent.id;
		int bottomLevel = (menuEvent.menu == null) ? 0
				: ((menuEvent.id == CommonNames.MISC.INVALID_VALUE_LONG) ? group
						.indexOf(menuEvent.menu) : group.indexOf(menuEvent.menu) + 1);
		int topLevel = (menuEvent.id == CommonNames.MISC.INVALID_VALUE_LONG || menuEvent.change) ? bottomLevel
				: 0;
		if (logger.isDebugEnabled())
		{
			logger.debug("menu " + menuEvent.menu + " id " + id + " change "
					+ menuEvent.change);
			logger.debug("topLevel " + topLevel + " bottomLevel " + bottomLevel);
		}

		// Populate all topLevel <= menus <= bottomLevel with the inferred
		// selection from the node's hierarchy (node is specified by the
		// "value" parameter).
		Map<String, OptionList> selection = new HashMap<String, OptionList>();
		long childId = CommonNames.MISC.INVALID_VALUE_LONG;
		for (int i = bottomLevel; i >= topLevel; i--)
		{
			// If this is not a menu on-change event, we need to
			// populate the selection of all parent menus of the id
			// node. If it is an on-change event, we want to leave it up
			// to the user to select from the child menu (i = bottomLevel).
			long selectedId = ((!menuEvent.change) && (childId != CommonNames.MISC.INVALID_VALUE_LONG)) ? childId
					: CommonNames.MISC.INVALID_VALUE_LONG;

			OptionList options = group.getChildrenSelection(i - 1, id, selectedId);
			if (options != null)
			{
				selection.put(group.getMenuName(i), options);
				if (logger.isDebugEnabled())
				{
					logger.debug("Added menu selection '" + group.getMenuName(i) + "': "
							+ options + "; selectedId=" + selectedId);
				}
			}
			childId = id;
			if (i > topLevel)
			{
				id = group.getParentId(i - 1, id);
			}
		}

		// Disable all menus > bottomLevel
		for (int i = bottomLevel + 1; i < group.size(); i++)
		{
			OptionList options = new OptionList(false);
			options.selectEmptySelection();
			selection.put(group.getMenuName(i), options);
		}

		return selection;
	}
}
