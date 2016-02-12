/*****************************************************************************************
 * Source File: WhitespaceState.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;

import net.ruready.common.rl.CommonNames;

/**
 * A whitespace state ignores whitespace (such as blanks and tabs), and returns the
 * tokenizer's next token. By default, all characters from 0 to 32 are whitespace.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 * 
 * @author Steven J. Metsker
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112-9359<br>
 *         U.S.A.<br>
 *         Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 *         <br>
 *         Please contact these numbers immediately if you receive this file without
 *         permission from the authors. Thank you.<br>
 *         <br>
 *         (c) 2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 29, 2007
 */
public class WhitespaceState extends TokenizerState
{
	protected boolean whitespaceChar[] = new boolean[256];

	/**
	 * Constructs a whitespace state with a default idea of what characters are, in fact,
	 * whitespace.
	 * 
	 * @return a state for ignoring whitespace
	 */
	public WhitespaceState()
	{
		setWhitespaceChars(0, ' ', true);
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		return CommonNames.PARSER.CORE.TOKENS.TOKENIZER_STATE.WHITE_SPACE;
	}

	/**
	 * Ignore whitespace (such as blanks and tabs), and return the tokenizer's next token.
	 * 
	 * @param r
	 * @param aWhitespaceChar
	 * @param t
	 * @return the tokenizer's next token
	 * @throws IOException
	 * @see net.ruready.common.parser.core.tokens.TokenizerState#nextToken(java.io.PushbackReader,
	 *      int, net.ruready.common.parser.core.tokens.Tokenizer)
	 */
	@Override
	public Token nextToken(PushbackReader r, int aWhitespaceChar, Tokenizer t)
		throws IOException
	{

		int c;
		do
		{
			c = r.read();
		}
		while (c >= 0 && c < whitespaceChar.length && whitespaceChar[c]);

		if (c >= 0)
		{
			r.unread(c);
		}
		// return t.nextToken();
		return Token.NULL;
	}

	/**
	 * Establish the given characters as whitespace to ignore.
	 * 
	 * @param first
	 *            char
	 * @param second
	 *            char
	 * @param boolean
	 *            true, if this state should ignore characters in the given range
	 */
	public void setWhitespaceChars(int from, int to, boolean b)
	{
		for (int i = from; i <= to; i++)
		{
			if (i >= 0 && i < whitespaceChar.length)
			{
				whitespaceChar[i] = b;
			}
		}
	}
}
