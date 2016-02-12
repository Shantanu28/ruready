/*****************************************************************************************
 * Source File: TestPersistenceCatalog.java
 ****************************************************************************************/
package test.ruready.business.content.catalog;

import java.util.List;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.ContentKnowledge;
import net.ruready.business.content.catalog.entity.ContentType;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.catalog.entity.Topic;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.eis.common.tree.NodeDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.rl.TestEnvTestBase;
import test.ruready.rl.TestingNames;

/**
 * Test the persistence functions of a catalog hierarchy: creating, saving,
 * adding/deleting/updating items, and printing the results after each catalog
 * change.
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
public class TestPersistenceCatalog extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPersistenceCatalog.class);

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private User systemUser;

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// ---------------------------------------
		// Delete all main items
		// ---------------------------------------
		logger.info("Deleting main items ...");
		AbstractEditItemBD<MainItem> bdItem = new StandAloneEditItemBD<MainItem>(
				MainItem.class, environment.getContext(), systemUser);
		for (MainItem item : bdItem.findAll(MainItem.class))
		{
			if (item.isTrash() || item.getIdentifier() == ItemType.ROOT)
			{
				// Skip critical items
				continue;
			}
			bdItem.deleteAll(item, false);
		}
	}

	// ========================= METHODS ===================================

	/**
	 * Create a test catalog, put some items in it, play with loading a little
	 * bit, and delete the test catalog.
	 */
	@Test
	public void testCatalog()
	{
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		AbstractEditItemBD<MainItem> bdMainItem = new StandAloneEditItemBD<MainItem>(
				MainItem.class, environment.getContext(), systemUser);

		// Deleting the test catalog if already found
		MainItem catalogOld = bdMainItem.findByUniqueProperty(MainItem.class,
				NodeDAO.NAME, TestingNames.CONTENT.BASE.CATALOG_NAME);
		if (catalogOld != null)
		{
			bdItem.deleteAll(catalogOld, true);
		}

		// Create a new test catalog
		logger.info("Creating a demo catalog ...");
		TagCabinet tagCabinet = ItemDemoUtil.createBase(TagCabinet.class,
				ContentNames.UNIQUE_NAME.TAG_CABINET);
		MainItem catalog = ItemDemoUtil.createBase(Catalog.class,
				TestingNames.CONTENT.BASE.CATALOG_NAME, tagCabinet);

		// Save catalog to database
		logger.info("Saving catalog to database ...");
		bdMainItem.updateAll(tagCabinet);
		bdMainItem.updateAll(catalog);
		/*
		 * bdItem.update(catalog); for (Node child : catalog.getChildren()) {
		 * bdItem.update((Item)child); }
		 */
		// Load catalog (only root node)
		MainItem catalog2 = bdMainItem.findByUniqueProperty(MainItem.class, NodeDAO.NAME,
				TestingNames.CONTENT.BASE.CATALOG_NAME);
		// logger.info("Catalog loaded from database (catalog2 root): " +
		// catalog2);
		// Load catalog (only root node + immediate children)
		catalog2 = bdItem.read(MainItem.class, catalog.getId());
		logger.info("Catalog loaded from database (catalog2 root + children): "
				+ catalog2);

		// Add and delete a new course
		Item subject = new Subject("Mathematics", "Mathematics");
		bdItem.createUnder(catalog.getId(), subject);

		Item course0 = new Course("MY COURSE", "9999");
		bdItem.createUnder(subject.getId(), course0);
		bdItem.delete(course0, true);

		// Add and delete a new course tree
		Item course1 = new Course("MY COURSE TREE", "8888");
		bdItem.createUnder(subject.getId(), course1);
		bdItem.deleteAll(course1, true);

		// Add some new items
		Item course = new Course("New Course", "0000");
		bdItem.createUnder(subject.getId(), course);

		ContentType contentType = new ContentKnowledge("New Content ContentKnowledge",
				null);
		bdItem.createUnder(course.getId(), contentType);

		Item topic = new Topic("New Topic", null);
		bdItem.createUnder(contentType.getId(), topic);

		Item subTopic = new SubTopic("New Sub-Topic", null);
		bdItem.createUnder(topic.getId(), subTopic);

		// Need to refresh course to see the newly added item
		Item course2 = bdItem.read(Item.class, course.getId());
		logger.info("Course children: " + course2.getChildren());

		logger.info("Catalog after adding new items: " + catalog);

		logger.info("List of parents of the new subTopic: ");
		for (Item parent : subTopic.getParents())
		{
			logger.info(parent);
		}

		logger.info("Catalog after reloading (full): "
				+ bdItem.read(Item.class, catalog2.getId()));

		logger.info("Course children: " + course.getChildren());

		logger.info("Catalog before deleting course: " + catalog);
		bdItem.deleteAll(course, true);
		logger.info("Catalog after deleting course: " + catalog);

		// Uncomment to test recovery from exceptions during a transaction
		// environment.getSession().load(Item.class, null);
	}

	/**
	 * Test finding non-deleted items of a certain type.
	 */
	@Test
	public void testFindAllNonDeleted()
	{
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		List<Course> courses = bdItem.findAll(Course.class);
		logger.debug("All courses = " + courses);

		List<Course> coursesNonDeleted = bdItem.findAllNonDeleted(Course.class,
				ItemType.CATALOG);
		logger.debug("Non-deleted courses = " + coursesNonDeleted);
	}
}
