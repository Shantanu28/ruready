/*****************************************************************************************
 * Source File: Literal.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.util.List;

import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.Terminal;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 */

/**
 * A Literal matches a specific String from an assembly.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 */

public class Literal extends Terminal
{
	/**
	 * the literal to match
	 */
	protected Token literal;

	/**
	 * Constructs a literal that will match the specified string.
	 * 
	 * @param string
	 *            the string to match as a token
	 * @return a literal that will match the specified string
	 */
	public Literal(String s)
	{
		literal = new Token(s);
	}

	/**
	 * Returns true if the literal this object equals an assembly's next element.
	 * 
	 * @param object
	 *            an element from an assembly
	 * @return true, if the specified literal equals the next token from an assembly
	 */
	@Override
	protected boolean qualifies(Object o)
	{
		return literal.equals(o);
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
	public String unvisitedString(List<Parser> visited)
	{
		return literal.toString();
	}
}
