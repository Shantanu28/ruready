/*****************************************************************************************
 * Source File: SymbolState.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The idea of a symbol is a character that stands on its own, such as an ampersand or a
 * parenthesis. For example, when tokenizing the expression <code>(isReady)&
 * (isWilling) </code>,
 * a typical tokenizer would return 7 tokens, including one for each parenthesis and one
 * for the ampersand. Thus a series of symbols such as <code>)&( </code> becomes three
 * tokens, while a series of letters such as <code>isReady</code> becomes a single word
 * token.
 * <p>
 * Multi-character symbols are an exception to the rule that a symbol is a standalone
 * character. For example, a tokenizer may want less-than-or-equals to tokenize as a
 * single token. This class provides a method for establishing which multi-character
 * symbols an object of this class should treat as single symbols. This allows, for
 * example, <code>"cat <= dog"</code> to tokenize as three tokens, rather than splitting
 * the less-than and equals symbols into separate tokens.
 * <p>
 * By default, this state recognizes the following multi- character symbols:
 * <code>!=, :-, <=, >=</code>.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 * <p>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * (c) 2006-07 Continuing Education , University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah
 * @version Jul 10, 2007
 */
public class SymbolState extends TokenizerState
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SymbolState.class);

	// ========================= FIELDS ====================================

	/**
	 * The list of multi-character symbols recognized by this state.
	 */
	private SymbolRootNode symbols = new SymbolRootNode();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a symbol state with a default idea of what multi-character symbols to
	 * accept (as described in the class comment).
	 * 
	 * @return a state for recognizing a symbol
	 */
	public SymbolState()
	{
		add("!=");
		add(":-");
		add("<=");
		add(">=");
	}

	/**
	 * A dummy constructor that does nothing.
	 * 
	 * @param x
	 *            dummy value
	 */
	public SymbolState(Double x)
	{

	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		return CommonNames.PARSER.CORE.TOKENS.TOKENIZER_STATE.SYMBOL;
	}

	// ========================= IMPLEMENTATION: TokenizerState ============

	/**
	 * Return a symbol token from a reader.
	 * 
	 * @param r
	 * @param first
	 * @param t
	 * @return a symbol token from a reader
	 * @throws IOException
	 * @see net.ruready.common.parser.core.tokens.TokenizerState#nextToken(java.io.PushbackReader,
	 *      int, net.ruready.common.parser.core.tokens.Tokenizer)
	 */
	@Override
	public Token nextToken(PushbackReader r, int first, Tokenizer t) throws IOException
	{
		String s = symbols.nextSymbol(r, first);
		return new Token(Token.TT_SYMBOL, s, 0);
	}

	// ========================= METHODS ===================================

	/**
	 * Add a multi-character symbol.
	 * 
	 * @param String
	 *            the symbol to add, such as <code>=:="</code>
	 */
	public void add(String s)
	{
		symbols.add(s);
	}

	/**
	 * Clear the list of multi-character symbols recognized by this state.
	 */
	public void clear()
	{
		symbols = new SymbolRootNode();
	}

}
