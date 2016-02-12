/*****************************************************************************************
 * Source File: SlashSlashState.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;

import net.ruready.common.rl.CommonNames;

/**
 * A slashSlash state ignores everything up to an end-of-line and returns the tokenizer's
 * next token.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
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
public class SlashSlashState extends TokenizerState
{
	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		return CommonNames.PARSER.CORE.TOKENS.TOKENIZER_STATE.SLASH_SLASH;
	}

	/**
	 * Ignore everything up to an end-of-line and return the tokenizer's next token.
	 * 
	 * @param r
	 * @param theSlash
	 * @param t
	 * @return the tokenizer's next token
	 * @throws IOException
	 * @see net.ruready.common.parser.core.tokens.TokenizerState#nextToken(java.io.PushbackReader,
	 *      int, net.ruready.common.parser.core.tokens.Tokenizer)
	 */
	@Override
	public Token nextToken(PushbackReader r, int theSlash, Tokenizer t)
		throws IOException
	{

		int c;
		while ((c = r.read()) != CommonNames.MISC.NEW_LINE_CHAR && c != '\r' && c >= 0)
		{

		}
		// return t.nextToken();
		return Token.NULL;
	}
}
