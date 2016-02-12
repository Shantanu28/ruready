/*****************************************************************************************
 * Source File: WordState.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;

import net.ruready.common.rl.CommonNames;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 */

/**
 * A wordState returns a word from a reader. Like other states, a tokenizer transfers the
 * job of reading to this state, depending on an initial character. Thus, the tokenizer
 * decides which characters may begin a word, and this state determines which characters
 * may appear as a second or later character in a word. These are typically different sets
 * of characters; in particular, it is typical for digits to appear as parts of a word,
 * but not as the initial character of a word.
 * <p>
 * By default, the following characters may appear in a word. The method
 * <code>setWordChars()</code> allows customizing this. <blockquote>
 * 
 * <pre>
 *      From    To
 *       'a', 'z'
 *       'A', 'Z'
 *       '0', '9'
 *      as well as: minus sign, underscore, and apostrophe.
 *  
 * </pre>
 * 
 * </blockquote>
 * 
 * @author Steven J. Metsker (c) 2006-07 Continuing Education , University of Utah . All
 *         copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 1.0
 */
public class WordState extends TokenizerState
{
	protected char charbuf[] = new char[16];

	protected boolean wordChar[] = new boolean[256];

	/**
	 * Constructs a word state with a default idea of what characters are admissible
	 * inside a word (as described in the class comment).
	 * 
	 * @return a state for recognizing a word
	 */
	public WordState()
	{
		setWordChars('a', 'z', true);
		setWordChars('A', 'Z', true);
		setWordChars('0', '9', true);
		setWordChars('-', '-', true);
		setWordChars('_', '_', true);
		setWordChars('\'', '\'', true);
		setWordChars(0xc0, 0xff, true);
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		return CommonNames.PARSER.CORE.TOKENS.TOKENIZER_STATE.WORD;
	}

	/*
	 * Fatten up charbuf as necessary.
	 */
	protected void checkBufLength(int i)
	{
		if (i >= charbuf.length)
		{
			char nb[] = new char[charbuf.length * 2];
			System.arraycopy(charbuf, 0, nb, 0, charbuf.length);
			charbuf = nb;
		}
	}

	/**
	 * Return a word token from a reader.
	 * 
	 * @param r
	 * @param c0
	 * @param t
	 * @return a word token from a reader
	 * @throws IOException
	 * @see net.ruready.common.parser.core.tokens.TokenizerState#nextToken(java.io.PushbackReader,
	 *      int, net.ruready.common.parser.core.tokens.Tokenizer)
	 */
	@Override
	public Token nextToken(final PushbackReader r, final int c0, final Tokenizer t)
		throws IOException
	{
		int c = c0;
		int i = 0;
		do
		{
			checkBufLength(i);
			charbuf[i++] = (char) c;
			c = r.read();
		}
		while (wordChar(c));

		if (c >= 0)
		{
			r.unread(c);
		}
		String sval = String.copyValueOf(charbuf, 0, i);
		return new Token(Token.TT_WORD, sval, 0);
	}

	/**
	 * Establish characters in the given range as valid characters for part of a word
	 * after the first character. Note that the tokenizer must determine which characters
	 * are valid as the beginning character of a word.
	 * 
	 * @param from
	 *            char
	 * @param to
	 *            char
	 * @param boolean
	 *            true, if this state should allow characters in the given range as part
	 *            of a word
	 */
	public void setWordChars(int from, int to, boolean b)
	{
		for (int i = from; i <= to; i++)
		{
			if (i >= 0 && i < wordChar.length)
			{
				wordChar[i] = b;
			}
		}
	}

	/*
	 * Just a test of the wordChar array.
	 */
	protected boolean wordChar(int c)
	{
		if (c >= 0 && c < wordChar.length)
		{
			return wordChar[c];
		}
		return false;
	}
}
