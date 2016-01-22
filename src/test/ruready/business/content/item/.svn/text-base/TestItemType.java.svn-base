/*****************************************************************************************
 * Source File: TestItemList.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.Set;

import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.tree.TreeInspector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests related to instantiation and manipulation of {@link ItemType} objects.
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
 * @see http://today.java.net/pub/a/today/2006/11/07/nuances-of-java-5-for-each-loop.html
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 4, 2007
 */
public class TestItemType extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestItemType.class);

	// ========================= TESTING METHODS ===========================

	/**
	 * Test whether the item type connections specify a tree.
	 */
	@Test
	public void testTreeIntegrity()
	{
		TreeInspector<ItemType, ItemType> inspector = new TreeInspector<ItemType, ItemType>();
		// - World has a child with multiple parents (District can descend off
		// Country or State).
		// - Cabinet has a loop (Folder can descend off Folder).
		// - Hence the root node is not a tree either.
		// The rest are trees.
		Assert.assertEquals(false, inspector.isTree(ItemType.ROOT));
		Assert.assertEquals(true, inspector.isTree(ItemType.CATALOG));
		Assert.assertEquals(false, inspector.isTree(ItemType.WORLD));
		Assert.assertEquals(false, inspector.isTree(ItemType.DOCUMENT_CABINET));
		Assert.assertEquals(true, inspector.isTree(ItemType.TAG_CABINET));
	}

	/**
	 * Test item-to-item paths.
	 */
	@Test
	public void testPathSets()
	{
		for (ItemType source : ItemType.values())
		{
			for (ItemType destination : ItemType.values())
			{
				// Assert.assertEquals(itemType,
				// ItemType.create(itemType.getValue()));
				Set<Integer> pathSet = ItemTypeUtil
						.getPathLengthList(source, destination);
				logger.debug("Paths(" + source + ", " + destination + ") = " + pathSet);
			}
		}
	}

	/**
	 * Test item-to-item unique path in the catalog tree.
	 */
	@Test
	public void testUniquePaths()
	{
		Assert.assertEquals(4, ItemTypeUtil.getPathLength(ItemType.COURSE,
				ItemType.QUESTION));
		Assert.assertEquals(3, ItemTypeUtil.getPathLength(ItemType.COURSE,
				ItemType.EXPECTATION));
		Assert.assertEquals(CommonNames.MISC.INVALID_VALUE_INTEGER, ItemTypeUtil
				.getPathLength(ItemType.QUESTION, ItemType.EXPECTATION));
	}

	/**
	 * Test camel case formatting.
	 */
	@Test
	public void testCamelCase()
	{
		Assert.assertEquals("item", ItemType.ITEM.getCamelCaseName());
		Assert.assertEquals("root", ItemType.ROOT.getCamelCaseName());
		Assert.assertEquals("mainItem", ItemType.MAIN_ITEM.getCamelCaseName());
		Assert.assertEquals("subTopic", ItemType.SUB_TOPIC.getCamelCaseName());
	}
}
