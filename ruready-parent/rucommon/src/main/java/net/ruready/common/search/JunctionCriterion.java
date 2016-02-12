/*****************************************************************************************
 * Source File: EqCriterion.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A sequence of a logical expressions combined by some associative logical
 * operator.
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
public class JunctionCriterion implements SearchCriterion
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(JunctionCriterion.class);

	// ========================= FIELDS ====================================

	/**
	 * Type of search (equals/less than/....
	 */
	protected final SearchType searchType;

	/**
	 * Criteria to operator on.
	 */
	protected final List<SearchCriterion> criteria = new ArrayList<SearchCriterion>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a text field search criterion.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param lhsCriterion
	 *            Left-hand-side criterion of the operation.
	 * @param rhsCriterion
	 *            Right-hand-side criterion of the operation.
	 */
	public JunctionCriterion(final SearchType searchType)
	{
		this.searchType = searchType;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[" + searchType + "]" + " " + criteria;
	}

	// ========================= IMPLEMENTATION: SearchCriterion ===========

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(SearchCriterion e)
	{
		return criteria.add(e);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends SearchCriterion> c)
	{
		return criteria.addAll(c);
	}

	/**
	 * @return
	 * @see net.ruready.common.search.SearchCriterion#getCriteria()
	 */
	public List<SearchCriterion> getCriteria()
	{
		return criteria;
	}

	// ========================= IMPLEMENTATION: Visitable =================

	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends SearchCriterionVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	// ========================= GETTERS & SETTERS =========================

	// ========================= METHODS ===================================

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
