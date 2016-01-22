/*****************************************************************************************
 * Source File: TestItemDemoUtil.java
 ****************************************************************************************/
package test.ruready.business.content.demo;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.world.entity.World;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;
import test.ruready.rl.TestingNames;

/**
 * Tests creating mock objects of the item hierarchy.
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
 * @version Aug 5, 2007
 */
public class TestItemDemoUtil extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestItemDemoUtil.class);

	// ========================= FIELDS ====================================

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test creating base objects of various types.
	 */
	@Test
	public void testCreateBase()
	{
		TagCabinet tagCabinet = ItemDemoUtil.createBase(TagCabinet.class,
				ContentNames.UNIQUE_NAME.TAG_CABINET);
		logger.debug("Created tag cabinet " + tagCabinet.getName());

		Catalog catalog = ItemDemoUtil.createBase(Catalog.class,
				TestingNames.CONTENT.BASE.CATALOG_NAME, tagCabinet);
		logger.debug("Created catalog " + catalog.getName());

		World world = ItemDemoUtil
				.createBase(World.class, ContentNames.UNIQUE_NAME.WORLD);
		logger.debug("Created world " + world.getName());
	}
}
