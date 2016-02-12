/*****************************************************************************************
 * Source File: Repetition.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.entity.Matches;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A <code>Repetition</code> matches its underlying parser repeatedly against a
 * assembly.
 * <p>
 * -------------------------------------------------------------------------<br>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 * <p>
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
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 17, 2007
 */
public class Repetition extends Parser
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Repetition.class);

	// ========================= FIELDS ====================================

	/*
	 * the parser this parser is a repetition of
	 */
	protected Parser subparser;

	/*
	 * the width of a random expansion
	 */
	protected static final int EXPWIDTH = 4;

	/*
	 * an assembler to apply at the beginning of a match
	 */
	protected Assembler preAssembler;

	/**
	 * Constructs a repetition of the given parser.
	 * 
	 * @param parser
	 *            the parser to repeat
	 * @return a repetiton that will match the given parser repeatedly in successive
	 *         matches
	 */
	public Repetition(Parser p)
	{
		this(p, null);
	}

	/**
	 * Constructs a repetition of the given parser with the given name.
	 * 
	 * @param Parser
	 *            the parser to repeat
	 * @param String
	 *            a name to be known by
	 * @return a repetiton that will match the given parser repeatedly in successive
	 *         matches
	 */
	public Repetition(Parser subparser, String name)
	{
		super(name);
		this.subparser = subparser;
	}

	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends ParserVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	/**
	 * Return this parser's subparser.
	 * 
	 * @return Parser this parser's subparser
	 */
	public Parser getSubparser()
	{
		return subparser;
	}

	/**
	 * Given a set of assemblies, this method applies a preassembler to all of them,
	 * matches its subparser repeatedly against each of them, applies its post-assembler
	 * against each, and returns a new set of the assemblies that result from the matches.
	 * <p>
	 * For example, matching the regular expression <code>a*
	 * </code> against
	 * <code>{^aaab}</code> results in <code>
	 * {^aaab, a^aab, aa^ab, aaa^b}</code>.
	 * 
	 * @return a List of assemblies that result from matching against a beginning set of
	 *         assemblies
	 * @param List
	 *            a vector of assemblies to match against
	 */
	@Override
	public Matches match(Matches in)
	{
		// ---------------------------------
		// Original Metsker code
		if (preAssembler != null)
		{
			// logger.debug("applying pre-assembler");
			for (Assembly a : in.getAssemblies())
			{
				preAssembler.workOn(a);
			}
		}
		Matches out = in.clone();
		Matches s = in; // a working state
		while (!s.isEmpty())
		{
			s = subparser.matchAndAssemble(s);
			out.addAll(s);
		}
		return out;
		// ---------------------------------

		// We run the pre-assembler AFTER cloning in to out
		// so that in is unaffected upon return from this method.
		// List<Assembly> out = elementClone(in);
		//
		// if (preAssembler != null) {
		// // logger.debug("applying pre-assembler");
		// for (Assembly a : out) {
		// preAssembler.workOn(a);
		// }
		// }
		//
		// List<Assembly> s = in; // a working state
		// while (!s.isEmpty()) {
		// s = subparser.matchAndAssemble(s);
		// out.addAll(s);
		// }
		// return out;
	}

	/**
	 * Create a collection of random elements that correspond to this repetition.
	 */
	@Override
	protected List<?> randomExpansion(final int maxDepth, final int depth)
	{
		int currentDepth = depth;
		List<Object> v = new ArrayList<Object>();
		if (depth >= maxDepth)
		{
			return v;
		}

		int n = (int) (EXPWIDTH * Math.random());
		for (int j = 0; j < n; j++)
		{
			List<?> w = subparser.randomExpansion(maxDepth, currentDepth++);
			for (Object element : w)
			{
				v.add(element);
			}
		}
		return v;
	}

	/**
	 * Returns a textual description of this parser.
	 * 
	 * @return a textual description of this parser
	 */
	@Override
	protected String unvisitedString(List<Parser> visited)
	{
		return subparser.toString(visited) + "*";
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Sets the object that will work on every assembly before matching against it.
	 * 
	 * @param Assembler
	 *            the assembler to apply
	 * @return Parser this
	 */
	public Parser setPreAssembler(Assembler preAssembler)
	{
		this.preAssembler = preAssembler;
		return this;
	}
}
