/*****************************************************************************************
 * Source File: TokenAssembly.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import net.ruready.common.parser.core.entity.Assembly;

/**
 * A TokenAssembly is an Assembly whose elements are Tokens. Tokens are, roughly, the
 * chunks of text that a <code>
 * Tokenizer</code> returns.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 *          <p>
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 Protected by
 *         U.S. Provisional Patent U-4003, February 2006
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 27, 2007
 */
public class TokenAssembly extends Assembly
{
	/**
	 * the "string" of tokens this assembly will consume
	 */
	protected TokenString tokenString;

	/**
	 * Constructs a TokenAssembly on a TokenString constructed from the given String.
	 * 
	 * @param string
	 *            the string to consume
	 * @return a TokenAssembly that will consume a tokenized version of the supplied
	 *         String
	 */
	public TokenAssembly(String s)
	{
		this(new TokenString(s));
	}

	/**
	 * Constructs a TokenAssembly on a TokenString constructed from the given Tokenizer.
	 * 
	 * @param Tokenizer
	 *            the tokenizer to consume tokens from
	 * @return a TokenAssembly that will consume a tokenized version of the supplied
	 *         Tokenizer
	 */
	public TokenAssembly(Tokenizer t)
	{
		this(new TokenString(t));
	}

	/**
	 * Constructs a <code>TokenAssembly</code> from the given <code>TokenString</code>.
	 * 
	 * @param tokenString
	 *            the tokenString to consume
	 * @return a TokenAssembly that will consume the supplied TokenString
	 */
	public TokenAssembly(TokenString tokenString)
	{
		this.tokenString = tokenString;
	}

	/**
	 * Returns a textual representation of the amount of this token assembly that has been
	 * consumed.
	 * 
	 * @param delimiter
	 *            the mark to show between consumed elements
	 * @return a textual description of the amount of this assembly that has been consumed
	 */
	@Override
	public String consumed(String delimiter)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < elementsConsumed(); i++)
		{
			if (i > 0)
			{
				buf.append(delimiter);
			}
			buf.append(tokenString.tokenAt(i));
		}
		return buf.toString();
	}

	/**
	 * Returns the default string to show between elements consumed or remaining.
	 * 
	 * @return the default string to show between elements consumed or remaining
	 */
	@Override
	public String defaultDelimiter()
	{
		return "/";
	}

	/**
	 * Returns the number of elements in this assembly.
	 * 
	 * @return the number of elements in this assembly
	 */
	@Override
	public int length()
	{
		return tokenString.length();
	}

	/**
	 * Shows the next object in the assembly, without removing it
	 * 
	 * @return the next object
	 */
	@Override
	public Object peek()
	{
		if (index < length())
		{
			return tokenString.tokenAt(index);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Returns the next token.
	 * 
	 * @return the next token from the associated token string.
	 * @exception ArrayIndexOutOfBoundsException
	 *                if there are no more tokens in this tokenizer's string.
	 */
	public Token nextElement()
	{
		return tokenString.tokenAt(index++);
	}

	/**
	 * Returns a textual representation of the amount of this tokenAssembly that remains
	 * to be consumed.
	 * 
	 * @param delimiter
	 *            the mark to show between consumed elements
	 * @return a textual description of the amount of this assembly that remains to be
	 *         consumed
	 */
	@Override
	public String remainder(String delimiter)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = elementsConsumed(); i < tokenString.length(); i++)
		{

			if (i > elementsConsumed())
			{
				buf.append(delimiter);
			}
			buf.append(tokenString.tokenAt(i));
		}
		return buf.toString();
	}
}
