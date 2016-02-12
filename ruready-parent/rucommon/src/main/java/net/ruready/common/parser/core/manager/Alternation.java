/*****************************************************************************************
 * Source File: Alternation.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.entity.Matches;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An {@link Alternation} object is a collection of parsers, any one of which can
 * successfully match against an assembly.
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
 * @version Original code: Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *          Steve Metsker makes no representations or warranties about the fitness of this
 *          software for any particular purpose, including the implied warranty of
 *          merchantability.
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class Alternation extends CollectionParser
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Alternation.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a nameless alternation.
	 */
	public Alternation()
	{

	}

	/**
	 * Constructs an alternation with the given name.
	 * 
	 * @param name
	 *            a name to be known by
	 */
	public Alternation(String name)
	{
		super(name);
	}

	// ========================= IMPLEMENTATION: Parser ====================

	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends ParserVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	/**
	 * Given a set of assemblies, this method matches this alternation against all of
	 * them, and returns a new set of the assemblies that result from the matches.
	 * 
	 * @return a List of assemblies that result from matching against a beginning set of
	 *         assemblies
	 * @param List
	 *            a vector of assemblies to match against
	 */
	@Override
	public Matches match(Matches in)
	{
		Matches out = new Matches();
		for (Parser p : subparsers)
		{
			Matches alternativeOut = p.matchAndAssemble(in);
			out.addAll(alternativeOut);
		}
		return out;
	}

	/**
	 * Create a random collection of elements that correspond to this alternation.
	 */
	@Override
	protected List<?> randomExpansion(int maxDepth, int depth)
	{
		if (depth >= maxDepth)
		{
			return randomSettle(maxDepth, depth);
		}
		int i = (int) (subparsers.size() * Math.random());
		Parser j = subparsers.get(i);
		return j.randomExpansion(maxDepth, depth + 1);
	}

	/**
	 * This method is similar to randomExpansion, but it will pick a terminal if one is
	 * available.
	 */
	protected List<?> randomSettle(int maxDepth, int depth)
	{
		// which alternatives are terminals?
		List<Parser> terms = new ArrayList<Parser>();
		for (Parser j : subparsers)
		{
			if (IsTerminalParserVisitor.isTerminal(j))
			{
				terms.add(j);
			}
		}

		// pick one of the terminals or, if there are no
		// terminals, pick any subparser

		List<?> which = terms;
		if (terms.isEmpty())
		{
			which = subparsers;
		}

		int i = (int) (which.size() * Math.random());
		Parser p = (Parser) which.get(i);
		return p.randomExpansion(maxDepth, depth + 1);
	}

	/**
	 * Returns the string to show between the parsers this parser is an alternation of.
	 */
	@Override
	protected String toStringSeparator()
	{
		return "|";
	}
}
