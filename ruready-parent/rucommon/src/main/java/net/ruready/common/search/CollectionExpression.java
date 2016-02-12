/*****************************************************************************************
 * Source File: EqCriterion.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A property search criterion with an interval value.
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
public class CollectionExpression<E> extends NoArgExpression<E>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CollectionExpression.class);

	// ========================= FIELDS ====================================

	/**
	 * Field value to search for.
	 */
	protected final Collection<? extends E> value;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a text field search criterion.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param name
	 *            Search field name
	 * @param value
	 *            collection-type value to search for
	 */
	public CollectionExpression(final SearchType searchType,
			final Class<? extends E> type, final String propertyName,
			final Collection<? extends E> value)
	{
		super(searchType, type, propertyName);
		this.value = value;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[" + searchType + "]" + " " + propertyName + " " + value;
	}

	// ========================= IMPLEMENTATION: SearchCriterion ===========

	// ========================= IMPLEMENTATION: Visitable =================

	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	@Override
	public <B extends SearchCriterionVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the value property.
	 * 
	 * @return the value
	 */
	public Collection<? extends E> getValue()
	{
		return value;
	}

	// ========================= METHODS ===================================

}