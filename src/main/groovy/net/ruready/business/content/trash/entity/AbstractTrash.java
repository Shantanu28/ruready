/*****************************************************************************************
 * Source File: AbstractTrash.java
 ****************************************************************************************/
package net.ruready.business.content.trash.entity;

import net.ruready.business.content.main.entity.UniqueItem;

/**
 * Describes a trash / recycle bin. It should know to clean itself (= empty the trash).
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 25, 2007
 */
public interface AbstractTrash extends UniqueItem
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Manually empty the entire recycle bin.
	 */
	void clear();

	/**
	 * An automatic cleaning of the recycle bin. Normally called at fixed time intervals.
	 * May clean only old items, all items, or no items, for instance.
	 */
	void expunge();
}
