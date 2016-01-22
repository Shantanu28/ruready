/*****************************************************************************************
 * Source File: TestPrintCatalog.java
 ****************************************************************************************/
package test.ruready.business.content.catalog;

import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.MainItem;
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
 * A simple test that prints a catalog tree.
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
 * @version Jul 25, 2007
 */
public class TestPrintCatalog extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPrintCatalog.class);

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
		// Clean up - delete all catalogs
		// ---------------------------------------
		logger.info("Deleting catalogs ...");
		AbstractEditItemBD<MainItem> bdItem = new StandAloneEditItemBD<MainItem>(
				MainItem.class, environment.getContext(), systemUser);
		MainItem catalog = bdItem.findByUniqueProperty(MainItem.class, NodeDAO.NAME,
				TestingNames.CONTENT.BASE.CATALOG_NAME);
		if (catalog != null)
		{
			bdItem.deleteAll(catalog, false);
		}
	}

	@Test
	public void printCatalog()
	{
		// Obsolete
	}

}
