/*****************************************************************************************
 * Source File: AbstractUser.java
 ****************************************************************************************/
package test.ruready.eis;

import net.ruready.common.eis.entity.PersistentEntity;

/**
 * An interface for the composite type (user in this case). Because it's an interface,
 * <i>do not annotate it</i>. We can still extend {@link PersistentEntity}, though.
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
 * @version Aug 12, 2007
 */
public interface AbstractUser extends PersistentEntity<Long>
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Return the phone component of this user.
	 * 
	 * @return the phone component of this user
	 */
	AbstractPhone getPhone();

	/**
	 * Set the phone component of this user.
	 * 
	 * @param phone
	 *            a phone object
	 */
	void setPhone(AbstractPhone phone);
}
