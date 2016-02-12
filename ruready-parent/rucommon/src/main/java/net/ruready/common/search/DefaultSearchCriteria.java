/*****************************************************************************************
 * Source File: DefaultSearchCriteria.java
 ****************************************************************************************/
/**
 * File: DefaultSearchCriteria.java
 */
package net.ruready.common.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default implementation of a set of search searchCriteria.
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
public class DefaultSearchCriteria implements SearchCriteria
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultSearchCriteria.class);

	// ========================= FIELDS ====================================

	/**
	 * A criterion to be decorated by this object.
	 */
	private final SearchCriterion criterion;

	/**
	 * List of sort criteria.
	 */
	private final List<SortCriterion> sortCriteria = new ArrayList<SortCriterion>();

	/**
	 * A map of alias definitions.
	 */
	private final Map<String, String> aliases = new HashMap<String, String>();

	/**
	 * The first result to be retrieved.
	 */
	private Integer firstResult;

	/**
	 * A limit upon the number of objects to be retrieved.
	 */
	private Integer maxResults;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a search criteria object.
	 * 
	 * @param criterion
	 *            root node of the criteria hierarchy
	 */
	public DefaultSearchCriteria(final SearchCriterion criterion)
	{
		this.criterion = criterion;
	}

	/**
	 * @param <B>
	 * @param visitor
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends SearchCriterionVisitor> void accept(B visitor)
	{
		criterion.accept(visitor);
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Criteria: " + criterion.toString() + " Aliases: " + aliases;
	}

	// ========================= IMPLEMENTATION: SearchCriterion ===========

	/**
	 * @param crit
	 * @return
	 * @see net.ruready.common.search.SearchCriterion#add(net.ruready.common.search.SearchCriterion)
	 */
	public boolean add(SearchCriterion crit)
	{
		return criterion.add(crit);
	}

	/**
	 * @param criteria
	 * @return
	 * @see net.ruready.common.search.SearchCriterion#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends SearchCriterion> criteria)
	{
		return criterion.addAll(criteria);
	}

	/**
	 * @return
	 * @see net.ruready.common.search.SearchCriterion#getCriteria()
	 */
	public List<SearchCriterion> getCriteria()
	{
		return criterion.getCriteria();
	}

	/**
	 * Return the list of specified sort criteria.
	 * 
	 * @return the list of criteria.
	 */
	public List<SortCriterion> getSortCriteria()
	{
		return sortCriteria;
	}

	// ========================= IMPLEMENTATION: SearchCriteria ============

	/**
	 * Add a sortOrder to the list of sort orders. If a sortOrder is
	 * <code>null</code> it is not added to the list.
	 * 
	 * @param sortOrder
	 *            sortOrder to add
	 * @return this (for method chaining)
	 * @see java.util.List#add(java.lang.Object)
	 * @see net.ruready.common.search.SearchCriteria#addSortCriterion(net.ruready.common.search.SortCriterion)
	 */
	public SearchCriteria addSortCriterion(SortCriterion e)
	{
		sortCriteria.add(e);
		return this;
	}

	/**
	 * Return the root criterion of this criteria tree.
	 * 
	 * @return the root criterion of this criteria tree
	 * @see net.ruready.common.search.SearchCriterion#getCriteria()
	 */
	public SearchCriterion getRootCriterion()
	{
		return criterion;
	}

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
	public SearchCriteria addAlias(final String alias, final String associationPath)
	{
		aliases.put(alias, associationPath);
		return this;
	}

	/**
	 * Remove an alias.
	 * 
	 * @param alias
	 *            The alias to assign to the joined association (for later
	 *            reference).
	 * @return this (for method chaining)
	 * @see net.ruready.common.search.SearchCriteria#removeAlias(java.lang.String)
	 */
	public SearchCriteria removeAlias(final String alias)
	{
		aliases.remove(alias);
		return this;
	}

	/**
	 * Return a map of the defined aliases. The keys are aliases and the
	 * associated values are the corresponding association paths.
	 * 
	 * @return a map of the defined aliases
	 * @see #createAlias(String, String)
	 */
	public Map<String, String> getAliases()
	{
		return aliases;
	}

	/**
	 * Return the first result number in the result set. Returns
	 * <code>null</code> if the result set is not set a lower bound.
	 * 
	 * @return the first result number in the result set, if set
	 */
	public Integer getFirstResult()
	{
		return firstResult;
	}

	/**
	 * Set the first result to be retrieved.
	 * 
	 * @param firstResult
	 *            the first result to retrieve, numbered from <tt>0</tt>
	 * @return this (for method chaining)
	 */
	public SearchCriteria setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
		return this;
	}

	/**
	 * Return the limit upon the number of objects to be retrieved. Returns
	 * <code>null</code> if the result set is not set a size limit.
	 * 
	 * @return the limit upon the number of objects to be retrieved
	 */
	public Integer getMaxResults()
	{
		return maxResults;
	}

	/**
	 * Set a limit upon the number of objects to be retrieved.
	 * 
	 * @param maxResults
	 *            the maximum number of results
	 * @return this (for method chaining)
	 */
	public SearchCriteria setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
		return this;
	}
}
