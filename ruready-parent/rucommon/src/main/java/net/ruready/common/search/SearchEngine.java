/*******************************************************
 * Source File: SearchEngine.java
 *******************************************************/
package net.ruready.common.search;

import java.util.List;

/**
 * An object that carries out a search on objects of a certain type.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 12, 2007
 */
public interface SearchEngine<E>
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Search for objects based on the criteria set by the other methods of the
	 * engine, and return a result set.
	 * 
	 * @param criteria
	 *            search criteria object
	 * @return result set of searchable objects
	 */
	List<E> search(final SearchCriteria criteria);
}
