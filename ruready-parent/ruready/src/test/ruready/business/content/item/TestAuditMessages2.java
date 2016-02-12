/*****************************************************************************************
 * Source File: InactiveTestCreateDemoCatalog.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.audit.AuditMessage;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.imports.StandAloneMainItemBD;
import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Test creating and deleting root and trash nodes, and seeing whether their
 * messages are cleared or not.
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
 * @version Aug 3, 2007
 */
public class TestAuditMessages2 extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestAuditMessages2.class);

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private static User systemUser;

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
		// We expect some audit messages might not be deleted by this test case,
		// so
		// manually delete all remaining ones here.
		Query query = environment.getSession().createQuery("delete AuditMessage am");
		query.executeUpdate();
	}

	// ========================= TESTING METHODS ===========================

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateRootAndTrash()
	{
		AbstractEISBounder bounder = environment.getDAOFactory();
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		// ######################## UNIT OF WORK 1 BEGIN ##################
		logger.debug("------------- Creating --------------");
		bounder.openSession();
		bounder.beginTransaction();

		// Initialize root node
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		bdMainItem.createUnique(Root.class, CommonNames.MISC.INVALID_VALUE_INTEGER);

		// Initialize trash node under the root
		bdMainItem.createUnique(DefaultTrash.class, getRoot().getId());

		bounder.commitTransaction();
		bounder.closeSession();
		// ######################## UNIT OF WORK 1 END ####################

		// ######################## UNIT OF WORK 2 BEGIN ##################
		logger.debug("------------- Updating root ---------");
		// Do some update on the root node

		// First user creates an item
		bounder.openSession();
		bounder.beginTransaction();
		Item item = getRoot();
		// Update #1
		logger.debug("############# Update #1 #############");
		logger.debug("Root messages before = " + item.getMessages());
		bdItem.update(item, false);
		logger.debug("Root messages after  0= " + item.getMessages());

		// Update #2
		logger.debug("############# Update #2 #############");
		logger.debug("Root messages before = " + item.getMessages());
		bdItem.update(item, false);
		logger.debug("Root messages after = " + item.getMessages());

		bounder.commitTransaction();
		bounder.closeSession();
		// ######################## UNIT OF WORK 2 END ####################

		// ######################## UNIT OF WORK 3 BEGIN ##################
		logger.debug("------------- Deleting --------------");

		// Delete all tree items
		bounder.openSession();
		bounder.beginTransaction();

		// Deleting the trash can if found
		Item trash = getTrash();
		if (trash != null)
		{
			bdItem.deleteAll(trash, false);
		}

		// Deleting the root node and its descendants
		Item root = getRoot();
		logger.debug("Root messages = " + item.getMessages());
		bdItem.deleteAll(root, false);

		bounder.commitTransaction();
		bounder.closeSession();
		// ######################## UNIT OF WORK 3 END ####################

		// ######################## UNIT OF WORK 3 BEGIN ##################
		// Delete all tree items
		bounder.openSession();
		bounder.beginTransaction();
		Session session = environment.getSession();
		Query query = session.createQuery("from AuditMessage am");
		List<AuditMessage> messages = query.list();
		logger.debug("Messages left = " + messages);
		Assert.assertEquals(0, messages.size());
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * @return root node
	 */
	private Item getRoot()
	{
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		return bdMainItem.readUnique(Root.class);
	}

	/**
	 * @return trash node
	 */
	private Item getTrash()
	{
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		return bdMainItem.readUnique(DefaultTrash.class);
	}
}
