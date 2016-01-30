/*****************************************************************************************
 * Source File: DefaultItemVisitor.java
 ****************************************************************************************/
package net.ruready.eis.factory.imports;

import java.util.List;

import net.ruready.common.exception.UnsupportedOpException;
import net.ruready.common.search.BinaryCriterion;
import net.ruready.common.search.CollectionExpression;
import net.ruready.common.search.DefaultSearchCriterionVisitor;
import net.ruready.common.search.IntervalExpression;
import net.ruready.common.search.JunctionCriterion;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.NoArgExpression;
import net.ruready.common.search.PropertyExpression;
import net.ruready.common.search.SQLCriterion;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterion;
import net.ruready.common.search.SimpleCriterion;
import net.ruready.common.search.SimpleExpression;
import net.ruready.common.search.SizeExpression;
import net.ruready.common.search.StringExpression;
import net.ruready.common.search.UnaryCriterion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * Converts {@link SearchCriterion} objects to Hibernate {@link Criterion}
 * objects.
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
class HibernateCriterionFactory extends DefaultSearchCriterionVisitor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateCriterionFactory.class);

	// ========================= FIELDS ====================================

	/**
	 * Hibernate criterion converted from the original search framework
	 * criterion.
	 */
	private Criterion result;

	/**
	 * Criterion constructor arguments required to build {@link #result}.
	 */
	private final List<Criterion> args;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a factory.
	 * 
	 * @param criterion
	 *            criterion to visit
	 * @param args
	 *            Criterion constructor arguments required to build
	 *            {@link #result}
	 */
	private HibernateCriterionFactory(final SearchCriterion criterion,
			final List<Criterion> args)
	{
		super(criterion);
		this.args = args;
		criterion.accept(this);
	}

	/**
	 * Convert a search criterion to a Hibernate Criterion.
	 * 
	 * @param criterion
	 *            criterion to visit
	 * @param args
	 *            Criterion constructor arguments required to build
	 *            {@link #result}
	 */
	public static Criterion createHibernateCriterion(final SearchCriterion criterion,
			final List<Criterion> args)
	{
		return new HibernateCriterionFactory(criterion, args).result;
	}

	// ========================= IMPLEMENTATION: SearchCriterionVisitor ====

	/**
	 * @param visitable
	 * @see net.ruready.common.visitor.Visitor#visit(net.ruready.common.visitor.Visitable)
	 */
	@Override
	public void visit(SearchCriterion visitable)
	{
		throw new UnsupportedOpException(
				"Factory must be invoked on a concrete SearchCriterion type");
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.BinaryCriterion)
	 */
	@Override
	public void visit(BinaryCriterion criterion)
	{
		switch (criterion.getSearchType())
		{
			case AND:
			{
				result = Restrictions.and(args.get(0), args.get(1));
				break;
			}

			case OR:
			{
				result = Restrictions.or(args.get(0), args.get(1));
				break;
			}

			default:
			{
				throw new IllegalStateException("BinaryCriterion cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.CollectionExpression)
	 */
	@Override
	public <E> void visit(CollectionExpression<E> criterion)
	{
		switch (criterion.getSearchType())
		{
			case IN:
			{
				result = Restrictions.in(criterion.getPropertyName(), criterion
						.getValue());
				break;
			}

			case SQL_RESTRICTION:
			{
				// No parametric replacement for now. Also, getPropertyName()
				// is regarded as the SQL condition.
				result = Restrictions.sqlRestriction(criterion.getPropertyName());
				break;
			}

			default:
			{
				throw new IllegalStateException("CollectionExpression cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.IntervalExpression)
	 */
	@Override
	public <E extends Comparable<? super E>> void visit(IntervalExpression<E> criterion)
	{
		result = Restrictions.between(criterion.getPropertyName(), criterion.getLow(),
				criterion.getHigh());
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.JunctionCriterion)
	 */
	@Override
	public void visit(JunctionCriterion criterion)
	{
		switch (criterion.getSearchType())
		{
			case CONJUNCTION:
			{
				result = Restrictions.conjunction();
				break;
			}

			case DISJUNCTION:
			{
				result = Restrictions.disjunction();
				break;
			}

			default:
			{
				throw new IllegalStateException("JunctionCriterion cannot have type "
						+ criterion.getSearchType());
			}
		}

		// Add junction arguments
		for (Criterion c : args)
		{
			((Junction) result).add(c);
		}
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.NoArgExpression)
	 */
	@Override
	public <E> void visit(NoArgExpression<E> criterion)
	{
		switch (criterion.getSearchType())
		{
			case IS_NULL:
			{
				result = Restrictions.isNull(criterion.getPropertyName());
				break;
			}

			case IS_NOT_NULL:
			{
				result = Restrictions.isNotNull(criterion.getPropertyName());
				break;
			}

			case IS_EMPTY:
			{
				result = Restrictions.isEmpty(criterion.getPropertyName());
				break;
			}

			case IS_NOT_EMPTY:
			{
				result = Restrictions.isNotEmpty(criterion.getPropertyName());
				break;
			}

			default:
			{
				throw new IllegalStateException("NoArgExpression cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.PropertyExpression)
	 */
	@Override
	public <E> void visit(PropertyExpression<E> criterion)
	{
		switch (criterion.getSearchType())
		{
			case EQ_PROPERTY:
			{
				result = Restrictions.eqProperty(criterion.getPropertyName(), criterion
						.getOtherPropertyName());
				break;
			}

			case NE_PROPERTY:
			{
				result = Restrictions.neProperty(criterion.getPropertyName(), criterion
						.getOtherPropertyName());
				break;
			}

			case GT_PROPERTY:
			{
				result = Restrictions.gtProperty(criterion.getPropertyName(), criterion
						.getOtherPropertyName());
				break;
			}

			case GE_PROPERTY:
			{
				result = Restrictions.geProperty(criterion.getPropertyName(), criterion
						.getOtherPropertyName());
				break;
			}

			case LT_PROPERTY:
			{
				result = Restrictions.ltProperty(criterion.getPropertyName(), criterion
						.getOtherPropertyName());
				break;
			}

			case LE_PROPERTY:
			{
				result = Restrictions.leProperty(criterion.getPropertyName(), criterion
						.getOtherPropertyName());
				break;
			}

			default:
			{
				throw new IllegalStateException("PropertyExpression cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SearchCriteria)
	 */
	@Override
	public void visit(SearchCriteria criterion)
	{
		criterion.getRootCriterion().accept(this);
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SimpleCriterion)
	 */
	@Override
	public void visit(SimpleCriterion criterion)
	{
		throw new UnsupportedOpException(
				"Factory must be invoked on a concrete SearchCriterion type");
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SimpleExpression)
	 */
	@Override
	public <E> void visit(SimpleExpression<E> criterion)
	{
		switch (criterion.getSearchType())
		{
			case EQ:
			{
				result = Restrictions.eq(criterion.getPropertyName(), criterion
						.getValue());
				break;
			}

			case NE:
			{
				result = Restrictions.ne(criterion.getPropertyName(), criterion
						.getValue());
				break;
			}

			case GT:
			{
				result = Restrictions.gt(criterion.getPropertyName(), criterion
						.getValue());
				break;
			}

			case GE:
			{
				result = Restrictions.ge(criterion.getPropertyName(), criterion
						.getValue());
				break;
			}

			case LT:
			{
				result = Restrictions.lt(criterion.getPropertyName(), criterion
						.getValue());
				break;
			}

			case LE:
			{
				result = Restrictions.le(criterion.getPropertyName(), criterion
						.getValue());
				break;
			}

			default:
			{
				throw new IllegalStateException("PropertyExpression cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param <E>
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SizeExpression)
	 */
	@Override
	public <E> void visit(SizeExpression<E> criterion)
	{
		switch (criterion.getSearchType())
		{
			case SIZE_EQ:
			{
				result = Restrictions.sizeEq(criterion.getPropertyName(), criterion
						.getSize());
				break;
			}

			case SIZE_NE:
			{
				result = Restrictions.sizeNe(criterion.getPropertyName(), criterion
						.getSize());
				break;
			}

			case SIZE_GT:
			{
				result = Restrictions.sizeGt(criterion.getPropertyName(), criterion
						.getSize());
				break;
			}

			case SIZE_GE:
			{
				result = Restrictions.sizeGe(criterion.getPropertyName(), criterion
						.getSize());
				break;
			}

			case SIZE_LT:
			{
				result = Restrictions.sizeLt(criterion.getPropertyName(), criterion
						.getSize());
				break;
			}

			case SIZE_LE:
			{
				result = Restrictions.sizeLe(criterion.getPropertyName(), criterion
						.getSize());
				break;
			}

			default:
			{
				throw new IllegalStateException("PropertyExpression cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.StringExpression)
	 */
	@Override
	public void visit(StringExpression criterion)
	{
		switch (criterion.getSearchType())
		{
			case LIKE:
			{
				result = Restrictions.like(criterion.getPropertyName(), criterion
						.getValue(), HibernateCriterionFactory
						.createHibernateMatchMode(criterion.getMatchType()));
				break;
			}

			case ILIKE:
			{
				result = Restrictions.ilike(criterion.getPropertyName(), criterion
						.getValue(), HibernateCriterionFactory
						.createHibernateMatchMode(criterion.getMatchType()));
				break;
			}

			default:
			{
				throw new IllegalStateException("StringExpression cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.UnaryCriterion)
	 */
	@Override
	public void visit(UnaryCriterion criterion)
	{
		switch (criterion.getSearchType())
		{
			case NOT:
			{
				result = Restrictions.not(args.get(0));
				break;
			}

			default:
			{
				throw new IllegalStateException("JunctionCriterion cannot have type "
						+ criterion.getSearchType());
			}
		}
	}

	/**
	 * @param criterion
	 * @see net.ruready.common.search.SearchCriterionVisitor#visit(net.ruready.common.search.SQLCriterion)
	 */
	@Override
	public <E> void visit(SQLCriterion criterion)
	{
		result = Restrictions.sqlRestriction(criterion.getSql());
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Convert match type to Hibernate match mode
	 * 
	 * @param matchType
	 *            match type
	 * @return corresponding Hibernate match mode
	 */
	private static MatchMode createHibernateMatchMode(final MatchType matchType)
	{
		switch (matchType)
		{
			case EXACT:
			{
				return MatchMode.EXACT;
			}

			case STARTS_WITH:
			{
				return MatchMode.START;
			}

			case ENDS_WITH:
			{
				return MatchMode.END;
			}

			case CONTAINS:
			{
				return MatchMode.ANYWHERE;
			}
		}

		return null;
	}
}
