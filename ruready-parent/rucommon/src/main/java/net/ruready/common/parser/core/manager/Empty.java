/*****************************************************************************************
 * Source File: Empty.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.entity.Matches;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 */

/**
 * An <code>Empty</code> parser matches any assembly once, and applies its assembler
 * that one time.
 * <p>
 * Language elements often contain empty parts. For example, a language may at some point
 * allow a list of parameters in parentheses, and may allow an empty list. An empty parser
 * makes it easy to match, within the parenthesis, either a list of parameters or "empty".
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 */
public class Empty extends Parser
{
	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends ParserVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	/**
	 * Given a set of assemblies, this method returns the set as a successful match.
	 * 
	 * @return the input set of states
	 * @param List
	 *            a vector of assemblies to match against
	 */
	@Override
	public Matches match(Matches in)
	{
		return in.clone();
	}

	/**
	 * There really is no way to expand an empty parser, so return an empty vector.
	 * 
	 * @param maxDepth
	 * @param depth
	 * @return
	 * @see net.ruready.common.parser.core.manager.Parser#randomExpansion(int, int)
	 */
	@Override
	protected List<?> randomExpansion(int maxDepth, int depth)
	{
		return new ArrayList<Object>();
	}

	/**
	 * Returns a textual description of this parser.
	 * 
	 * @param visited
	 *            a list of parsers already printed in this description
	 * @return a textual description of this parser
	 * @see Parser#toString()
	 */
	@Override
	protected String unvisitedString(List<Parser> visited)
	{
		return " empty ";
	}
}
