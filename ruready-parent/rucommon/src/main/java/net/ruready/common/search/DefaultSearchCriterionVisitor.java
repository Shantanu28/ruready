/*****************************************************************************************
 * Source File: DefaultItemVisitor.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.util.Collection;

/**
 * A stub implementation of a search criterion visitor. All <code>visit()</code>
 * methods do nothing. Includes all search criteria types from all
 * sub-components. This is basically a nested <i>stub</i> -- each method's
 * implementation calls the method on the parent type, and the root type's
 * method is empty.
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
 * @version Jul 31, 2007
 */
public class DefaultSearchCriterionVisitor implements SearchCriterionVisitor
{
	// ========================= FIELDS ====================================

	/**
	 * A criterion to be visited by this object.
	 */
	protected final SearchCriterion crit;

	// ========================= CONSTRUCTOR ===============================

	/**
	 * Construct a visitor.
	 * 
	 * @param crit
	 *            criterion to visit
	 */
	public DefaultSearchCriterionVisitor(final SearchCriterion crit)
	{
		this.crit = crit;
	}

	// ========================= IMPLEMENTATION: SearchCriterionVisitor ====

	/**
	 * @param visitable
	 * @see net.ruready.common.visitor.Visitor#visit(net.ruready.common.visitor.Visitable)
	 */
	public void visit(SearchCriterion visitable)
	{

	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.BinaryCriterion)
	 */
	public void visit(BinaryCriterion criterion)
	{
		visit((SearchCriterion) criterion);
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.CollectionExpression)
	 */
	public <E> void visit(CollectionExpression<E> criterion)
	{
		visit((NoArgExpression<E>) criterion);
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.IntervalExpression)
	 */
	public <E extends Comparable<? super E>> void visit(IntervalExpression<E> criterion)
	{
		visit((NoArgExpression<E>) criterion);
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.JunctionCriterion)
	 */
	public void visit(JunctionCriterion criterion)
	{
		visit((SearchCriterion) criterion);
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.NoArgExpression)
	 */
	public <E> void visit(NoArgExpression<E> criterion)
	{
		visit((SearchCriterion) criterion);
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.PropertyExpression)
	 */
	public <E> void visit(PropertyExpression<E> criterion)
	{
		visit((NoArgExpression<E>) criterion);
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SearchCriteria)
	 */
	public void visit(SearchCriteria criterion)
	{
		visit((SearchCriterion) criterion);
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SimpleCriterion)
	 */
	public void visit(SimpleCriterion criterion)
	{
		visit((SearchCriterion) criterion);
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SimpleExpression)
	 */
	public <E> void visit(SimpleExpression<E> criterion)
	{
		visit((NoArgExpression<E>) criterion);
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SizeExpression)
	 */
	public <E> void visit(SizeExpression<E> criterion)
	{
		visit((NoArgExpression<Collection<? extends E>>) criterion);
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.StringExpression)
	 */
	public void visit(StringExpression criterion)
	{
		visit((NoArgExpression<String>) criterion);
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.UnaryCriterion)
	 */
	public void visit(UnaryCriterion criterion)
	{
		visit((SearchCriterion) criterion);
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SQLCriterion)
	 */
	public <E> void visit(SQLCriterion criterion)
	{
		visit((SimpleCriterion) criterion);
	}

}
