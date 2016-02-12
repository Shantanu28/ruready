/*****************************************************************************************
 * Source File: EqCriterion.java
 ****************************************************************************************/
package net.ruready.common.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * String-related search criterion, e.g. SQL LIKE command.
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
public class StringExpression extends SimpleExpression<String>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StringExpression.class);

	// ========================= FIELDS ====================================

	/**
	 * Match type (starts with/exact/...).
	 */
	protected final MatchType matchType;

	/**
	 * Escape character for value.
	 */
	protected final Character escapeChar;

	/**
	 * Is the search case insensitive.
	 */
	protected final boolean ignoreCase;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a text field search criterion.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param propertyName
	 *            Search field name
	 * @param value
	 *            Field value to search for
	 * @param matchType
	 *            Match type (starts with/exact/...)
	 * @param escapeChar
	 *            Escape character for value
	 * @param ignoreCase
	 *            Is the search case insensitive
	 */
	public StringExpression(final SearchType searchType, final String propertyName,
			final String value, final MatchType matchType, final Character escapeChar,
			final boolean ignoreCase)
	{
		super(searchType, String.class, propertyName, value);
		this.matchType = matchType;
		this.escapeChar = escapeChar;
		this.ignoreCase = ignoreCase;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[" + searchType + "]" + " " + propertyName + " " + value + " escapeChar "
				+ escapeChar + " ignoreCase " + ignoreCase;
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

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the matchType property.
	 * 
	 * @return the matchType
	 */
	public MatchType getMatchType()
	{
		return matchType;
	}

	/**
	 * Returns the escapeChar property.
	 * 
	 * @return the escapeChar
	 */
	public Character getEscapeChar()
	{
		return escapeChar;
	}

	/**
	 * Returns the ignoreCase property.
	 * 
	 * @return the ignoreCase
	 */
	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}
}
