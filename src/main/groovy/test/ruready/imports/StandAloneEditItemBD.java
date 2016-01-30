/*****************************************************************************************
 * Source File: StandAloneEditItemBD.java
 ****************************************************************************************/
package test.ruready.imports;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.DefaultEditItemBD;
import net.ruready.business.content.item.manager.DefaultEditItemManager;
import net.ruready.business.imports.StandAloneResourceLocator;
import net.ruready.business.user.entity.User;
import net.ruready.common.misc.Singleton;
import net.ruready.common.rl.ApplicationContext;

/**
 * An edit catalog BD interface with a stand-alone resource locator.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 21, 2007
 */
public class StandAloneEditItemBD<B extends Item> extends DefaultEditItemBD<B> implements
		Singleton
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This is a singleton. Put static initializations here.
	 * 
	 * @param persistentClass
	 *            the class type we're managing here
	 * @param context
	 *            application context
	 * @param user
	 *            user requesting the operations
	 */
	public StandAloneEditItemBD(final Class<B> baseClass,
			final ApplicationContext context, final User user)
	{
		super(new DefaultEditItemManager<B>(baseClass, StandAloneResourceLocator
				.getInstance(), context, user), StandAloneResourceLocator.getInstance());
	}
}
