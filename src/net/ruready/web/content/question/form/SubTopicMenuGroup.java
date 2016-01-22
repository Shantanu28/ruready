/*****************************************************************************************
 * Source File: CatalogNestedMenuGroup.java
 ****************************************************************************************/
package net.ruready.web.content.question.form;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.content.item.form.ItemMenuGroup;
import net.ruready.web.menugroup.entity.MenuProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A three-level nested menu group that represents a catalog. The three levels
 * are course, topic and sub-topic. This is in effect a data transfer object
 * from the business layer to an Struts AJAX action. It is used on the edit
 * question page.
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
public class SubTopicMenuGroup extends ItemMenuGroup
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SubTopicMenuGroup.class);

	/**
	 * Course menu properties.
	 */
	private static final MenuProperties<ItemType, Item> courseMenu = new MenuProperties<ItemType, Item>(
			ItemType.COURSE, false, WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL,
			Course.class, ItemType.CATALOG);

	/**
	 * Topic menu properties.
	 */
	private static final MenuProperties<ItemType, Item> topicMenu = new MenuProperties<ItemType, Item>(
			ItemType.TOPIC, false, WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL);

	/**
	 * Sub-topic menu properties.
	 */
	private static final MenuProperties<ItemType, Item> subTopicMenu = new MenuProperties<ItemType, Item>(
			ItemType.SUB_TOPIC, false, WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize a catalog menu group.
	 * 
	 * @param context
	 *            web application context
	 */
	@SuppressWarnings("unchecked")
	public SubTopicMenuGroup(final ApplicationContext context, final User user)
	{
		super(context, user, courseMenu, topicMenu, subTopicMenu);
	}
}