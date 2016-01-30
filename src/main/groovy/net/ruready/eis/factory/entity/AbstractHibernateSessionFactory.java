/*****************************************************************************************
 * Source File: HibernateSessionFactory.java
 ****************************************************************************************/
package net.ruready.eis.factory.entity;

import java.util.Properties;

import net.ruready.common.eis.exports.AbstractEISBounder;
import net.ruready.common.rl.Resource;

import org.hibernate.Session;

/**
 * An abstraction for Hibernate session factories.
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
public interface AbstractHibernateSessionFactory extends Resource<Properties>,
		AbstractEISBounder
{
	// ========================= CONSTANTS =================================

	/**
	 * Retrieves the current Session local to the thread. <p/> If no Session is open,
	 * opens a new Session for the running thread. If CMT is used, returns the Session
	 * bound to the current JTA container transaction. Most other operations on this class
	 * will then be no-ops or not supported, the container handles Session and Transaction
	 * boundaries, ThreadLocals are not used.
	 * 
	 * @return Session
	 */
	Session getSession();
}
