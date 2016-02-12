/*****************************************************************************************
 * Source File: Num.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.Terminal;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 */

/**
 * A Num matches a number from a token assembly.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 */

public class Num extends Terminal
{

	/**
	 * Returns true if an assembly's next element is a number.
	 * 
	 * @param object
	 *            an element from an assembly
	 * @return true, if an assembly's next element is a number as recognized the tokenizer
	 */
	@Override
	protected boolean qualifies(Object o)
	{
		Token t = (Token) o;
		return t.isNumber();
	}

	/**
	 * Create a set with one random number (between 0 and 100).
	 * 
	 * @param maxDepth
	 * @param depth
	 * @return
	 * @see net.ruready.common.parser.core.manager.Terminal#randomExpansion(int, int)
	 */
	@Override
	public List<?> randomExpansion(int maxDepth, int depth)
	{
		double d = Math.floor(1000.0 * Math.random()) / 10;
		List<String> v = new ArrayList<String>();
		v.add(Double.toString(d));
		return v;
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
		return "Num";
	}
}
