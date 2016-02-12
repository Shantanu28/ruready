/*****************************************************************************************
 * Source File: AbstractGlobalPropertyManager.java
 ****************************************************************************************/
package net.ruready.business.user.manager;

import net.ruready.business.user.entity.Counter;
import net.ruready.business.user.entity.audit.HitMessage;
import net.ruready.common.rl.BusinessManager;

/**
 * GlobalProperty business service. Includes creating, deleting and updating user
 * operations.
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
 * @version Aug 11, 2007
 */
public interface AbstractGlobalPropertyManager extends BusinessManager
{
	// ========================= ABSTRACT METHODS ==========================

	// ========================= ITEM MANIPULATION METHODS =================

	/**
	 * Load counter from database.
	 * 
	 * @param name
	 *            counter unique name
	 * @return counter updated counter object
	 * 
	 */
	public Counter load(String name);

	/**
	 * Increment counter and persist changes to the database.
	 * 
	 * @param name
	 *            counter unique name
	 * @return counter updated counter object
	 * 
	 */
	public Counter increment(String name);

	/**
	 * Decrement counter and persist changes to the database.
	 * 
	 * @param name
	 *            counter unique name
	 * @return counter updated counter object
	 * 
	 */
	public Counter decrement(String name);

	/**
	 * Save a new hit message to the database.
	 * 
	 * @param hitMessage
	 *            hit message to be saved
	 * 
	 */
	public void saveHitMessage(HitMessage hitMessage);
}
