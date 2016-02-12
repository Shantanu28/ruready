/*****************************************************************************************
 * Source File: PrettyVisitor.java
 ****************************************************************************************/
package net.ruready.common.parser.core.pretty;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.exception.SystemException;
import net.ruready.common.parser.core.assembler.AbstractAssemblerFactory;
import net.ruready.common.parser.core.manager.Alternation;
import net.ruready.common.parser.core.manager.Empty;
import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.ParserVisitor;
import net.ruready.common.parser.core.manager.Repetition;
import net.ruready.common.parser.core.manager.Sequence;
import net.ruready.common.parser.core.manager.Terminal;
import net.ruready.common.parser.core.pretty.assembler.PrettyAssemblerFactory;
import net.ruready.common.parser.core.pretty.assembler.PrettyAssemblerID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An object of this class visits the parsers in a parser composite and sets each
 * subparser's assembler to be one of the "pretty" assemblers in this package. These
 * assemblers build a tree of nodes from the {@link ComponentNode} hierarchy that is also
 * in this package. The resulting tree effectively records the order in which the parse
 * proceeds. -------------------------------------------------------------------------<br>
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
 * @author Steven J. Metsker
 * @version 1.0 Copyright (c) 2000 Steven J. Metsker. All Rights Reserved. Steve Metsker
 *          makes no representations or warranties about the fitness of this software for
 *          any particular purpose, including the implied warranty of merchantability.
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class PrettyVisitor implements ParserVisitor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PrettyVisitor.class);

	// ========================= FIELDS ====================================

	/**
	 * Assembler factory, provides restricted access to private assembler classes.
	 */
	private final AbstractAssemblerFactory factory = new PrettyAssemblerFactory();

	/**
	 * Keeps track of the list of sub-parsers visited before this parser visit is
	 * completed.
	 */
	private List<Parser> visited = new ArrayList<Parser>();

	// ========================= CONSTRUCTORS ==============================

	// ========================= IMPLEMENTATION: ParserVisitor ================

	/**
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#setVisited(java.util.List)
	 */
	public void setVisited(List<Parser> visited)
	{
		this.visited = visited;
	}

	/**
	 * @param visitable
	 * @see net.ruready.common.visitor.Visitor#visit(net.ruready.common.visitor.Visitable)
	 */
	public void visit(Parser visitable)
	{
		throw new SystemException(
				"We don't visit generic parsers here");
	}

	/**
	 * Set an <code>Empty</code> parser's assembler to be a
	 * <code>PrettyEmptyAssembler</code> object.
	 */
	public void visit(Empty e)
	{
		e.setAssembler(factory.createAssembler(PrettyAssemblerID.EMPTY));
	}

	/**
	 * Set an {@link Alternation} parser's assembler to be a
	 * <code>PrettyAlternationAssembler</code> object and visit this parser's children.
	 * 
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Alternation,
	 *      java.lang.Object[])
	 */
	public void visit(Alternation a)
	{
		if (visited.contains(a))
		{
			return;
		}
		visited.add(a);
		a.setAssembler(factory
				.createAssembler(PrettyAssemblerID.ALTERNATION, a.getName()));

		for (Parser child : a.getSubparsers())
		{
			child.accept(this);
		}
	}

	/**
	 * Set a <code>Repetition</code> parser's pre-assembler to push a "fence", and set
	 * the parser's post-assembler to be a <code>PrettyRepetitionAssembler</code>
	 * object. The latter assembler will pop results down to the fence. Also visit the
	 * repetition parser's subparser.
	 * 
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Repetition,
	 *      java.lang.Object[])
	 */
	public void visit(Repetition r)
	{
		if (visited.contains(r))
		{
			return;
		}
		visited.add(r);
		Object fence = new Object();
		r.setPreAssembler(factory.createAssembler(PrettyAssemblerID.FENCE, fence));
		r.setAssembler(factory.createAssembler(PrettyAssemblerID.REPETITION, r.getName(),
				fence));
		r.getSubparser().accept(this);
	}

	/**
	 * Set a <code>Sequence</code> parser's assembler to be a
	 * <code>PrettySequenceAssembler</code> object and visit the parser's children.
	 * 
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Sequence,
	 *      java.lang.Object[])
	 */
	public void visit(Sequence s)
	{
		if (visited.contains(s))
		{
			return;
		}
		visited.add(s);
		s.setAssembler(factory.createAssembler(PrettyAssemblerID.SEQUENCE, s.getName(), s
				.getSubparsers().size()));
		for (Parser child : s.getSubparsers())
		{
			child.accept(this);
		}
	}

	/**
	 * Set a <code>Terminal</code> object's assembler to be a
	 * <code>PrettyTerminalAssembler</code> object.
	 * 
	 * @see net.ruready.common.parser.core.manager.ParserVisitor#visit(net.ruready.common.parser.core.manager.Terminal,
	 *      java.lang.Object[])
	 */
	public void visit(Terminal t)
	{
		t.setAssembler(factory.createAssembler(PrettyAssemblerID.TERMINAL));
	}
}
