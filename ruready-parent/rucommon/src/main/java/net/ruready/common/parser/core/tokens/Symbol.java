/*****************************************************************************************
 * Source File: Symbol.java
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
 * A Symbol matches a specific sequence, such as <code><</code>, or <code><=</code>
 * that a tokenizer returns as a symbol.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 */

public class Symbol extends Terminal
{
	/**
	 * the literal to match
	 */
	protected Token symbol;

	/**
	 * Constructs a symbol that will match the specified char.
	 * 
	 * @param char
	 *            the character to match. The char must be one that the tokenizer will
	 *            return as a symbol token. This typically includes most characters except
	 *            letters and digits.
	 * @return a symbol that will match the specified char
	 */
	public Symbol(char c)
	{
		this(String.valueOf(c));
	}

	/**
	 * Constructs a symbol that will match the specified sequence of characters.
	 * 
	 * @param String
	 *            the characters to match. The characters must be a sequence that the
	 *            tokenizer will return as a symbol token, such as <code><=</code>.
	 * @return a Symbol that will match the specified sequence of characters
	 */
	public Symbol(String s)
	{
		symbol = new Token(Token.TT_SYMBOL, s, 0);
	}

	/**
	 * Returns true if the symbol this object represents equals an assembly's next
	 * element.
	 * 
	 * @param object
	 *            an element from an assembly
	 * @return true, if the specified symbol equals the next token from an assembly
	 */
	@Override
	protected boolean qualifies(Object o)
	{
		return symbol.equals(o);
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
		return symbol.toString();
	}
}
