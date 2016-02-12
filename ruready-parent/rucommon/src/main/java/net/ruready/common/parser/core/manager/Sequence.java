/*****************************************************************************************
 * Source File: Sequence.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.entity.Matches;
import net.ruready.common.rl.CommonNames;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 */

/**
 * A <code>Sequence</code> object is a collection of parsers, all of which must in turn
 * match against an assembly for this parser to successfully match.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 */

public class Sequence extends CollectionParser
{

	/**
	 * Constructs a nameless sequence.
	 */
	public Sequence()
	{
	}

	/**
	 * Constructs a sequence with the given name.
	 * 
	 * @param name
	 *            a name to be known by
	 */
	public Sequence(String name)
	{
		super(name);
	}

	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends ParserVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	/**
	 * Given a set of assemblies, this method matches this sequence against all of them,
	 * and returns a new set of the assemblies that result from the matches.
	 * 
	 * @return a List of assemblies that result from matching against a beginning set of
	 *         assemblies
	 * @param List
	 *            a vector of assemblies to match against
	 */
	@Override
	public Matches match(Matches in)
	{
		Matches out = in;
		for (Parser p : subparsers)
		{
			out = p.matchAndAssemble(out);
			if (out.isEmpty())
			{
				return out;
			}
		}
		return out;
	}

	/**
	 * Create a random expansion for each parser in this sequence and return a collection
	 * of all these expansions.
	 */
	@Override
	protected List<?> randomExpansion(final int maxDepth, final int depth)
	{
		int currentDepth = depth;
		List<Object> v = new ArrayList<Object>();
		for (Parser p : subparsers)
		{
			List<?> w = p.randomExpansion(maxDepth, currentDepth++);
			for (Object element : w)
			{
				v.add(element);
			}
		}
		return v;
	}

	/**
	 * Returns the string to show between the parsers this parser is a sequence of. This
	 * is an empty string, since convention indicates sequence quietly. For example, note
	 * that in the regular expression <code>(a|b)c</code>, the lack of a delimiter
	 * between the expression in parentheses and the 'c' indicates a sequence of these
	 * expressions.
	 */
	@Override
	protected String toStringSeparator()
	{
		return CommonNames.MISC.EMPTY_STRING;
	}
}
