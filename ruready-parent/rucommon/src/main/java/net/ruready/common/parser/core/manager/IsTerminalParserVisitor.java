/*****************************************************************************************
 * Source File: IsTerminalParserVisitor.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.List;

/**
 * A parser visitor that determines whether a parser is a terminal parser or not.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Apr 26, 2007
 */
public class IsTerminalParserVisitor implements ParserVisitor
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	// Value (true iff parser is terminal)
	private boolean value;

	// ========================= CONSTRUCTORS ==============================

	private IsTerminalParserVisitor(Parser origin)
	{
		super();
		origin.accept(this);
	}

	public static boolean isTerminal(Parser origin)
	{
		return new IsTerminalParserVisitor(origin).isValue();
	}

	// ========================= IMPLEMENTATION: ParserVisitor ================

	/**
	 * @param visited
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#setVisited(java.util.List)
	 */
	public void setVisited(List<Parser> visited)
	{

	}

	/**
	 * @param visitable
	 * @see net.ruready.common.visitor.Visitor#visit(net.ruready.common.visitor.Visitable)
	 */
	public void visit(Parser visitable)
	{
		value = false;
	}

	/**
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Alternation,
	 *      java.lang.Object[])
	 */
	public void visit(Alternation p)
	{
		this.visit((Parser) p);
	}

	/**
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Empty,
	 *      java.lang.Object[])
	 */
	public void visit(Empty e)
	{
		this.visit((Parser) e);
	}

	/**
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Repetition,
	 *      java.lang.Object[])
	 */
	public void visit(Repetition r)
	{
		this.visit((Parser) r);
	}

	/**
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Sequence,
	 *      java.lang.Object[])
	 */
	public void visit(Sequence s)
	{
		this.visit((Parser) s);
	}

	/**
	 * @param t
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Terminal)
	 */
	public void visit(Terminal t)
	{
		value = true;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the value
	 */
	private boolean isValue()
	{
		return value;
	}

}
