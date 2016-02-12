/*****************************************************************************************
 * Source File: Digit.java
 ****************************************************************************************/
package net.ruready.common.parser.core.chars;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.Terminal;

/**
 * A Digit matches a digit from a character assembly.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 */
public class Digit extends Terminal
{
	/**
	 * Returns true if an assembly's next element is a digit.
	 * 
	 * @param object
	 *            an element from an assembly
	 * @return true, if an assembly's next element is a digit
	 */
	@Override
	public boolean qualifies(Object o)
	{
		Character c = (Character) o;
		return Character.isDigit(c.charValue());
	}

	/**
	 * Create a set with one random digit.
	 * 
	 * @param maxDepth
	 * @param depth
	 * @return
	 * @see net.ruready.common.parser.core.manager.Terminal#randomExpansion(int, int)
	 */
	@Override
	public List<?> randomExpansion(int maxDepth, int depth)
	{
		char c = (char) (10 * Math.random() + '0');
		List<String> v = new ArrayList<String>();
		v.add(new String(new char[]
		{
			c
		}));
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
		return "D";
	}
}
