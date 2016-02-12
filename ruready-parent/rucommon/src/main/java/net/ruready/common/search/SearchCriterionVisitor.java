/*****************************************************************************************
 * Source File: ItemVisitor.java
 ****************************************************************************************/
package net.ruready.common.search;

import net.ruready.common.tree.TreeVisitor;
import net.ruready.common.visitor.Visitor;

/**
 * An item's {@link TreeVisitor} Visitor Pattern (from "Gang of Four"). Allows
 * overcoming polymporhism and instance-of anti-patterns and problems for the
 * item hierarchy. Includes all item types from all sub-components.
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
public interface SearchCriterionVisitor extends Visitor<SearchCriterion>
{
	// List all sub-classes of SearchCriterion here.

	void visit(JunctionCriterion criterion);

	void visit(SimpleCriterion criterion);

	void visit(BinaryCriterion criterion);

	<E> void visit(NoArgExpression<E> criterion);

	<E> void visit(CollectionExpression<E> criterion);

	<E extends Comparable<? super E>> void visit(IntervalExpression<E> criterion);

	<E> void visit(PropertyExpression<E> criterion);

	<E> void visit(SimpleExpression<E> criterion);

	void visit(StringExpression criterion);

	<E> void visit(SizeExpression<E> criterion);

	void visit(UnaryCriterion criterion);

	<E> void visit(SQLCriterion criterion);

	void visit(SearchCriteria criterion);
}
