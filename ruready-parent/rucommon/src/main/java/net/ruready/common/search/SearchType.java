/*******************************************************
 * Source File: TreeNodeCascadeType.java
 *******************************************************/
package net.ruready.common.search;

/**
 * Type of search (exact match / like match / etc.). Some of the search types
 * (e.g. <code>EQUALS</code>) support a general object as the value of the
 * search field. Other types are well defined ONLY for <i>string</i>-valued
 * search fields, or for collections (e.g. <code>IN_SET</code>).
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
 * @version Nov 12, 2007
 */
public enum SearchType
{
	// ========================= CONSTANTS =================================

	// ========================= NO-ARGUMENT EXPRESSIONS ===================

	/**
	 * Apply an "is null" constraint to the named property.
	 * 
	 * @return Criterion
	 */
	IS_NULL,

	/**
	 * Apply an "is not null" constraint to the named property.
	 * 
	 * @return Criterion
	 */
	IS_NOT_NULL,

	/**
	 * Constrain a collection-valued property to be empty.
	 */
	IS_EMPTY,

	/**
	 * Constrain a collection-valued property to be non-empty.
	 */
	IS_NOT_EMPTY,

	// ========================= SIMPLE EXPRESSIONS ========================

	/**
	 * Apply an "equal" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	EQ,

	/**
	 * Apply a "not equal" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	NE,

	/**
	 * Apply a "greater than" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	GT,

	/**
	 * Apply a "less than" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	LT,

	/**
	 * Apply a "less than or equal" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	LE,

	/**
	 * Apply a "greater than or equal" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	GE,

	// ========================= INTERVAL EXPRESSIONS ======================

	/**
	 * Apply a "between" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param lo
	 *            value
	 * @param hi
	 *            value
	 * @return Criterion
	 */
	BETWEEN,

	// ========================= STRING EXPRESSIONS ========================

	/**
	 * Apply a "like" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	LIKE,

	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt> operator.
	 * 
	 * @param propertyName
	 * @param value
	 * @return Criterion
	 */
	ILIKE,

	// ========================= PROPERTY EXPRESSIONS ======================

	/**
	 * Apply an "equal" constraint to two properties.
	 */
	EQ_PROPERTY,

	/**
	 * Apply a "not equal" constraint to two properties.
	 */
	NE_PROPERTY,

	/**
	 * Apply a "less than" constraint to two properties.
	 */
	LT_PROPERTY,

	/**
	 * Apply a "less than or equal" constraint to two properties
	 */
	LE_PROPERTY,

	/**
	 * Apply a "greater than" constraint to two properties
	 */
	GT_PROPERTY,

	/**
	 * Apply a "greater than or equal" constraint to two properties
	 */
	GE_PROPERTY,

	// ========================= SIZE EXPRESSIONS ==========================

	/**
	 * Constrain a collection valued property by size.
	 */
	SIZE_EQ,

	/**
	 * Constrain a collection valued property by size.
	 */
	SIZE_NE,

	/**
	 * Constrain a collection valued property by size.
	 */
	SIZE_GT,

	/**
	 * Constrain a collection valued property by size.
	 */
	SIZE_LT,

	/**
	 * Constrain a collection valued property by size.
	 */
	SIZE_GE,

	/**
	 * Constrain a collection valued property by size.
	 */
	SIZE_LE,

	// ========================= COLLECTION EXPRESSIOSN ====================

	/**
	 * Apply an "in" constraint to the named property.
	 * 
	 * @param propertyName
	 * @param values
	 * @return Criterion
	 */
	IN,

	/**
	 * Apply an "equals" constraint to each property in the key set of a
	 * <tt>Map</tt>.
	 * 
	 * @param propertyNameValues
	 *            a map from property names to values
	 * @return Criterion
	 */
	// ALL_EQ, // Obsolete, see Hibernate's allEq() before enabling this
	//
	// ========================= UNARY LOGICAL EXPRESSIONS =================
	/**
	 * Return the negation of an expression.
	 * 
	 * @param expression
	 * @return Criterion
	 */
	NOT,

	// ========================= BINARY LOGICAL EXPRESSIONS ================

	/**
	 * Return the conjunction of two expressions.
	 * 
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
	AND,

	/**
	 * Return the disjunction of two expressions.
	 * 
	 * @param lhs
	 * @param rhs
	 * @return Criterion
	 */
	OR,

	// ========================= MULTINARY (COMPOSITE) EXPRESSIONS =========

	/**
	 * Group expressions together in a single conjunction (A and B and C...).
	 * 
	 * @return Conjunction
	 */
	CONJUNCTION,

	/**
	 * Group expressions together in a single disjunction (A or B or C...).
	 * 
	 * @return Disjunction
	 */
	DISJUNCTION,

	// ========================= OTHER =====================================

	/**
	 * Apply a constraint expressed in SQL, with the given JDBC parameters. Any
	 * occurrences of <tt>{alias}</tt> will be replaced by the table alias.
	 * This is like Hibernate's <code>SQLRestriction</code> type, but does not
	 * allow parametric replacements. Also, getPropertyName() of the
	 * corresponding criterion object is regarded as the SQL condition.
	 */
	SQL_RESTRICTION;

	// ========================= FIELDS ====================================

	// ========================= METHODS ===================================

}
