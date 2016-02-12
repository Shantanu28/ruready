/*****************************************************************************************
 * Source File: TestItemFunctions.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.Comparator;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.document.entity.DocumentCabinet;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.world.entity.World;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests {@link MainItem} comparisons.
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
public class TestMainItemFunctions extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestMainItemFunctions.class);

	// ========================= FIELDS ====================================

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test comparing main items using the {@link Root} node comparator.
	 */
	@Test
	public void testCompareMainItems()
	{
		Item root = new Root("Root", null);
		Comparator<Node> comparator = root.getComparator();

		MainItem trash = new DefaultTrash("Trash", null);
		MainItem catalog = new Catalog("Catalog", null);
		MainItem world = new World("World", null);
		MainItem tagCabinet = new TagCabinet("Tag Cabinet", null);
		MainItem documentCabinet = new DocumentCabinet("Document Cabinet", null);

		root.addChild(trash);
		root.addChild(world);
		root.addChild(catalog);
		root.addChild(tagCabinet);
		root.addChild(documentCabinet);

		// Check basic comparisons
		Assert.assertEquals(-1, comparator.compare(catalog, world));
		Assert.assertEquals(-1, comparator.compare(world, tagCabinet));
		Assert.assertEquals(-1, comparator.compare(tagCabinet, documentCabinet));
		Assert.assertEquals(-1, comparator.compare(documentCabinet, trash));

		// Check if root children is well sorted

		// Check symmetry of the main item comparator
		for (Node node1 : root.getChildren())
		{
			for (Node node2 : root.getChildren())
			{
				Assert.assertEquals(-comparator.compare(node1, node2), comparator
						.compare(node2, node1));
			}
		}
	}

	// ========================= PRIVATE METHODS ===========================
}
