/*****************************************************************************************
 * Source File: SearchCriterion.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.util.Collection;
import java.util.List;

import net.ruready.common.misc.Immutable;
import net.ruready.common.visitor.Visitable;

/**
 * An object that encapsulates a single search criterion. uses the Composite
 * pattern.
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
 * @version Nov 9, 2007
 */
public interface SearchCriterion extends Immutable, Visitable<SearchCriterionVisitor>
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Add a criterion to the list of search criteria. If a criterion is
	 * <code>null</code>, it is not added to the list.
	 * 
	 * @param criterion
	 *            criterion to be added
	 */
	boolean add(final SearchCriterion criterion);

	/**
	 * Add a list of criteria to the list of search criteria.
	 * 
	 * @param criteria
	 *            criterion to be added
	 */
	boolean addAll(final Collection<? extends SearchCriterion> criteria);

	/**
	 * Return the list of criteria contained by this criterion, if this is a
	 * composite criterion. Returns an empty list for a leaf criterion (<code>SimpleCriterion</code>).
	 * 
	 * @return the list of criteria contained by this criterion, if this is a
	 *         composite criterion. Returns an empty list for a leaf criterion (<code>SimpleCriterion</code>).
	 */
	List<SearchCriterion> getCriteria();
}
