/*****************************************************************************************
 * Source File: SlashState.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This state will either delegate to a comment-handling state, or return a token with
 * just a slash in it.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 * 
 * @author Steven J. Metsker (c) 2006-07 Continuing Education , University of Utah . All
 *         copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 1.0
 */
public class SlashState extends TokenizerState
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SlashState.class);

	// ========================= FIELDS ====================================

	protected SlashStarState slashStarState = new SlashStarState();

	protected SlashSlashState slashSlashState = new SlashSlashState();

	// ========================= CONSTRUCTORS ==============================

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		return CommonNames.PARSER.CORE.TOKENS.TOKENIZER_STATE.SLASH;
	}

	/**
	 * Either delegate to a comment-handling state, or return a token with just a slash in
	 * it.
	 * 
	 * @param r
	 * @param theSlash
	 * @param t
	 * @return either just a slash token, or the results of delegating to a
	 *         comment-handling state
	 * @throws IOException
	 * @see net.ruready.common.parser.core.tokens.TokenizerState#nextToken(java.io.PushbackReader,
	 *      int, net.ruready.common.parser.core.tokens.Tokenizer)
	 */
	@Override
	public Token nextToken(PushbackReader r, int theSlash, Tokenizer t)
		throws IOException
	{
		int c = r.read();
		if (c == '*')
		{
			return slashStarState.nextToken(r, '*', t);
		}
		if (c == '/')
		{
			return slashSlashState.nextToken(r, '/', t);
		}
		if (c >= 0)
		{
			r.unread(c);
		}
		return new Token(Token.TT_SYMBOL, "/", 0);
	}
}
