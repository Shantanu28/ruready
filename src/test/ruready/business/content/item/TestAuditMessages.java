/*****************************************************************************************
 * Source File: InactiveTestCreateDemoCatalog.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.business.content.catalog.TestPersistenceCatalog;
import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.imports.StandAloneMainItemBD;
import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Test creating and deleting the root node, and seeing whether their messages
 * are cleared or not.
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
public class TestAuditMessages extends StandAloneEnvTestBase
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
	private static User systemUser;

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);

		// Initialize root node
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		bdMainItem.createUnique(Root.class, CommonNames.MISC.INVALID_VALUE_INTEGER);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// Delete all tree items
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		// Deleting the root node and its descendants
		Item root = getRoot();
		bdItem.deleteAll(root, false);
	}

	// ========================= TESTING METHODS ===========================

	/**
	 * @param args
	 */
	@Test
	public void testCreateItem()
	{
		// Do some update on the root node
		AbstractEISBounder bounder = environment.getDAOFactory();
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);

		// First user creates an item
		bounder.openSession();
		bounder.beginTransaction();
		Item item = getRoot();
		bdItem.update(item, false);
		bounder.commitTransaction();
		bounder.closeSession();
	}

	// ========================= PRIVATE METHODS ===========================

	private Item getRoot()
	{
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		Item root = bdMainItem.readUnique(Root.class);
		return root;
	}
}
