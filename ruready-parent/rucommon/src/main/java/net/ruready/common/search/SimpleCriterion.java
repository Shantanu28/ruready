/*****************************************************************************************
 * Source File: EqCriterion.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ruready.common.exception.UnsupportedOpException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A search criterion that does not contain other search criteria.
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
 * @version Jul 29, 2007
 */
public abstract class SimpleCriterion implements SearchCriterion
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleCriterion.class);

	// ========================= FIELDS ====================================

	/**
	 * Type of search (equals/less than/....
	 */
	protected final SearchType searchType;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a text field search criterion.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 */
	public SimpleCriterion(final SearchType searchType)
	{
		this.searchType = searchType;
	}

	// ========================= IMPLEMENTATION: SearchCriterion ===========

	/**
	 * @param criterion
	 * @return
	 * @see net.ruready.common.search.SearchCriterion#add(net.ruready.common.search.SearchCriterion)
	 */
	public final boolean add(SearchCriterion criterion)
	{
		throw new UnsupportedOpException(
				"Cannot add a criterion to a leaf criterion type");
	}

	/**
	 * @param criteria
	 * @return
	 * @see net.ruready.common.search.SearchCriterion#addAll(java.util.Collection)
	 */
	public final boolean addAll(Collection<? extends SearchCriterion> criteria)
	{
		throw new UnsupportedOpException("Cannot add criteria to a leaf criterion type");
	}

	/**
	 * @return
	 * @see net.ruready.common.search.SearchCriterion#getCriteria()
	 */
	public final List<SearchCriterion> getCriteria()
	{
		return new ArrayList<SearchCriterion>();
	}

	// ========================= IMPLEMENTATION: Visitable =================

	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends SearchCriterionVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the searchType property.
	 * 
	 * @return the searchType
	 */
	public SearchType getSearchType()
	{
		return searchType;
	}
}
