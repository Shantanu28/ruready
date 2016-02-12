/*****************************************************************************************
 * Source File: SearchCriteria.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.util.List;
import java.util.Map;

/**
 * An object holding a list of search criteria, their filtering logic and result
 * set controls.
 * 
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
 * @version Nov 11, 2007
 */
public interface SearchCriteria extends SearchCriterion
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Add a sortOrder to the list of sort orders. If a sortOrder is
	 * <code>null</code> it is not added to the list.
	 * 
	 * @param sortOrder
	 *            sortOrder to add
	 * @return this (for method chaining)
	 */
	SearchCriteria addSortCriterion(final SortCriterion sortOrder);

	/**
	 * Return the list of specified sort criteria.
	 * 
	 * @return the list of criteria.
	 */
	List<SortCriterion> getSortCriteria();

	/**
	 * Return the root criterion of this criteria tree.
	 * 
	 * @return the root criterion of this criteria tree
	 * @see net.ruready.common.search.SearchCriterion#getCriteria()
	 */
	SearchCriterion getRootCriterion();

	/**
	 * Join an association, assigning an alias to the joined association. <p/>
	 * Functionally equivalent to the Hibernate
	 * {@link #createAlias(String, String, int)} using {@link #INNER_JOIN} for
	 * the joinType, <i>with the first two arguments reversed</i>.
	 * 
	 * @param alias
	 *            The alias to assign to the joined association (for later
	 *            reference).
	 * @param associationPath
	 *            A dot-separated property path
	 * @return this (for method chaining)
	 */
	SearchCriteria addAlias(final String alias, final String associationPath);

	/**
	 * Remove an alias.
	 * 
	 * @param alias
	 *            The alias to assign to the joined association (for later
	 *            reference).
	 * @return this (for method chaining)
	 */
	SearchCriteria removeAlias(final String alias);

	/**
	 * Return a map of the defined aliases. The keys are aliases and the
	 * associated values are the corresponding association paths.
	 * 
	 * @return a map of the defined aliases
	 * @see #createAlias(String, String)
	 */
	Map<String, String> getAliases();

	/**
	 * Return the first result number in the result set. Returns
	 * <code>null</code> if the result set is not set a lower bound.
	 * 
	 * @return the first result number in the result set, if set
	 */
	Integer getFirstResult();

	/**
	 * Set the first result to be retrieved.
	 * 
	 * @param firstResult
	 *            the first result to retrieve, numbered from <tt>0</tt>
	 * @return this (for method chaining)
	 */
	SearchCriteria setFirstResult(int firstResult);

	/**
	 * Return the limit upon the number of objects to be retrieved. Returns
	 * <code>null</code> if the result set is not set a size limit.
	 * 
	 * @returnthe limit upon the number of objects to be retrieved
	 */
	Integer getMaxResults();

	/**
	 * Set a limit upon the number of objects to be retrieved.
	 * 
	 * @param maxResults
	 *            the maximum number of results
	 * @return this (for method chaining)
	 */
	SearchCriteria setMaxResults(int maxResults);
}
