/*****************************************************************************************
 * Source File: SearchCriterionFactory.java
 ****************************************************************************************/
package net.ruready.common.search;

import java.util.Collection;

import net.ruready.common.exception.UnsupportedOpException;
import net.ruready.common.misc.Utility;

/**
 * The <tt>criterion</tt> package may be used by applications as a framework
 * for building new kinds of <tt>Criterion</tt>. However, it is intended that
 * most applications will simply use the built-in criterion types via the static
 * factory methods of this class.
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
 * @version Nov 12, 2007
 */
public class SearchCriterionFactory implements Utility
{
	// ========================= STATIC METHODS =============================

	/**
	 * Construct a no-argument search criterion.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param name
	 *            Search field name
	 */
	public static <E> NoArgExpression<E> createNoArgExpression(
			final SearchType searchType, final Class<? extends E> type,
			final String propertyName)
	{
		switch (searchType)
		{
			case IS_NULL:
			case IS_NOT_NULL:
			case IS_EMPTY:
			case IS_NOT_EMPTY:
			{
				return new NoArgExpression<E>(searchType, type, propertyName);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a no-argument search type");
			}

		}
	}

	/**
	 * Construct a simple expression criterion.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param name
	 *            Search field name
	 * @param value
	 *            Field value to search for
	 */
	public static <E> SimpleExpression<E> createSimpleExpression(
			final SearchType searchType, final Class<? extends E> type,
			final String propertyName, final E value)
	{
		switch (searchType)
		{
			case EQ:
			case NE:
			case GT:
			case GE:
			case LT:
			case LE:
			{
				return new SimpleExpression<E>(searchType, type, propertyName, value);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a simple expression search type");
			}

		}
	}

	/**
	 * Construct a string expression criterion. Currently escapeChar and
	 * ignoreCase properties of {@link StringExpression} are ignored upon
	 * conversion to a Hibernate criterion.
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
	 */
	public static StringExpression createStringExpression(final SearchType searchType,
			final String propertyName, final String value, final MatchType matchType)
	{
		// Currently escapeChar and ignoreCase properties of StringExpression
		// are ignored upon conversion to a Hibernate criterion.
		switch (searchType)
		{
			case LIKE:
			case ILIKE:
			{
				return new StringExpression(searchType, propertyName, value, matchType,
						null, false);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a string expression search type");
			}

		}
	}

	/**
	 * Construct a property expression criterion.
	 * 
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param propertyName
	 *            Search field name
	 * @param size
	 *            Size value to search for
	 */
	public static <E> PropertyExpression<E> createPropertyExpression(
			final SearchType searchType, final Class<? extends E> type,
			final String propertyName, final String otherPropertyName)
	{
		switch (searchType)
		{
			case EQ_PROPERTY:
			case NE_PROPERTY:
			case GT_PROPERTY:
			case GE_PROPERTY:
			case LT_PROPERTY:
			case LE_PROPERTY:
			{
				return new PropertyExpression<E>(searchType, type, propertyName,
						otherPropertyName);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a property expression search type");
			}

		}
	}

	/**
	 * Construct a property expression criterion.
	 * 
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param propertyName
	 *            Search field name (a collection-type property)
	 * @param size
	 *            size value
	 */
	public static <E> SizeExpression<E> createSizeExpression(final SearchType searchType,
			final Class<? extends Collection<? extends E>> type,
			final String propertyName, final int size)
	{
		switch (searchType)
		{
			case EQ_PROPERTY:
			case NE_PROPERTY:
			case GT_PROPERTY:
			case GE_PROPERTY:
			case LT_PROPERTY:
			case LE_PROPERTY:
			{
				return new SizeExpression<E>(searchType, type, propertyName, size);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a size expression search type");
			}

		}
	}

	/**
	 * Construct a property expression criterion.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param fieldType
	 *            Field type
	 * @param propertyName
	 *            Search field name (a collection-type property)
	 * @param size
	 *            size value
	 */
	public static <E> CollectionExpression<E> createCollectionExpression(
			final SearchType searchType, final Class<? extends E> type,
			final String propertyName, final Collection<? extends E> value)
	{
		switch (searchType)
		{
			case IN:
			{
				return new CollectionExpression<E>(searchType, type, propertyName, value);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a collection expression search type");
			}

		}
	}

	/**
	 * Construct a unary criterion expression.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param criterion
	 *            criterion to operator on
	 */
	public static UnaryCriterion createUnaryCriterion(final SearchType searchType,
			final SearchCriterion criterion)
	{
		switch (searchType)
		{
			case NOT:
			{
				return new UnaryCriterion(searchType, criterion);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a unary criterion search type");
			}

		}
	}

	/**
	 * Construct a binary criterion expression.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 * @param lhsCriterion
	 *            left-hand-side criterion to operator on
	 * @param rhsCriterion
	 *            right-hand-side criterion to operator on
	 */
	public static BinaryCriterion createBinaryCriterion(final SearchType searchType,
			final SearchCriterion lhsCriterion, final SearchCriterion rhsCriterion)
	{
		switch (searchType)
		{
			case AND:
			case OR:
			{
				return new BinaryCriterion(searchType, lhsCriterion, rhsCriterion);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a binary criterion search type");
			}

		}
	}

	/**
	 * Construct a junction criterion expression.
	 * 
	 * @param searchType
	 *            type of search (equals/less than/...)
	 */
	public static JunctionCriterion createJunctionCriterion(final SearchType searchType)
	{
		switch (searchType)
		{
			case CONJUNCTION:
			case DISJUNCTION:
			{
				return new JunctionCriterion(searchType);
			}

			default:
			{
				throw new UnsupportedOpException(searchType
						+ " is not a junction criterion search type");
			}

		}
	}

	/**
	 * Construct an SQL restriction criterion. (In Hibernate, any occurrences of
	 * {alias} will be replaced by the table alias.)
	 * 
	 * @param sql
	 *            SQL query string
	 */
	public static SQLCriterion createSQLCriterion(final String sql)
	{
		return new SQLCriterion(sql);
	}

	/**
	 * Create a sort criterion.
	 * 
	 * @param field
	 *            the name of the field to sort on
	 * @param sortType
	 *            the order to sort in
	 * @return a search criterion
	 */
	public static SortCriterion createSortCriterion(final String fieldName,
			final SortType sortType)
	{
		return new DefaultSortCriterion(fieldName, sortType);
	}
}
