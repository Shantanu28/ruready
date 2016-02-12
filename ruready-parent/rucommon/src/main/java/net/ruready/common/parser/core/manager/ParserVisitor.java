/*****************************************************************************************
 * Source File: ParserVisitor.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.List;

import net.ruready.common.visitor.Visitor;

/**
 * This class provides a "visitor" hierarchy in support of the Auditable pattern -- see
 * the book, "Design Patterns" for an explanation of this pattern. Changed to adhere to
 * the generic visitor pattern.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public interface ParserVisitor extends Visitor<Parser>
{
	/**
	 * Set an instance variable in the visitor class holding the collection of previously
	 * visited parsers. Must be called before <code>visit()</code>.
	 * 
	 * @param visited
	 *            a collection of previously visited parsers
	 */
	void setVisited(List<Parser> visited);

	/**
	 * Visit an alternation.
	 * 
	 * @param p
	 *            the parser to visit
	 */
	void visit(Alternation p);

	/**
	 * Visit an empty parser.
	 * 
	 * @param p
	 *            the parser to visit
	 */
	void visit(Empty e);

	/**
	 * Visit a repetition.
	 * 
	 * @param p
	 *            the parser to visit
	 */
	void visit(Repetition r);

	/**
	 * Visit a sequence.
	 * 
	 * @param p
	 *            the parser to visit
	 */
	void visit(Sequence s);

	/**
	 * Visit a terminal.
	 * 
	 * @param p
	 *            the parser to visit
	 */
	void visit(Terminal t);
}
