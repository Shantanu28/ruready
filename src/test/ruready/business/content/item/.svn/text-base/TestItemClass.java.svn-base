/*****************************************************************************************
 * Source File: TestItemClass.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.HashSet;
import java.util.Set;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test item's static <code>getThisClass()</code> method for different item
 * types.
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
 * @version Aug 4, 2007
 */
public class TestItemClass extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestItemClass.class);

	// ========================= FIELDS ====================================

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test item's static getThisClass() method for different item types.
	 */
	@Test
	public void testItemType()
	{
		Assert.assertEquals(ItemType.ITEM.getType().equals("ITEM"), true);
		Assert.assertEquals(ItemType.COURSE.getType().equals("COURSE"), true);
		Assert.assertEquals(ItemType.CATALOG.getType().equals("CATALOG"), true);
		Assert.assertEquals(ItemType.CATALOG.getType().equals("COURSE"), false);
	}

	/**
	 * Test item's equality by adding a few duplicates to a list.
	 */
	@Test
	public void testItemEquality()
	{
		Set<Item> items = new HashSet<Item>();
		Item item0 = new Course("My Course 2", null);
		items.add(item0);
		Item item1 = new Course("My Course", null);
		items.add(item1);
		items.add(item1);
		items.add(item1);
		logger.debug("items = " + items);
		Assert.assertEquals(2, items.size());
	}
}
