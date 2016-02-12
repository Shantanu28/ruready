/*******************************************************************************
 * Source File: Tokenizer.java
 ******************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A tokenizer divides a string into tokens. This class is highly customizable
 * with regard to exactly how this division occurs, but it also has defaults
 * that are suitable for many languages. This class assumes that the character
 * values read from the string lie in the range 0-255. For example, the Unicode
 * value of a capital A is 65, so <code> logger.debug((char)65); </code> prints
 * out a capital A.
 * <p>
 * The behavior of a tokenizer depends on its character state table. This table
 * is an array of 256 <code>TokenizerState
 * </code> states. The state table
 * decides which state to enter upon reading a character from the input string.
 * <p>
 * For example, by default, upon reading an 'A', a tokenizer will enter a "word"
 * state. This means the tokenizer will ask a <code>WordState</code> object to
 * consume the 'A', along with the characters after the 'A' that form a word.
 * The state's responsibility is to consume characters and return a complete
 * token.
 * <p>
 * The default table sets a SymbolState for every character from 0 to 255, and
 * then overrides this with: <blockquote>
 * 
 * <pre>
 *                                    From    To     State
 *                                      0     ' '    whitespaceState
 *                                     'a'    'z'    wordState
 *                                     'A'    'Z'    wordState
 *                                    160     255    wordState
 *                                     '0'    '9'    numberState
 *                                     '-'    '-'    numberState
 *                                     '.'    '.'    numberState
 *                                     '&quot;'    '&quot;'    quoteState
 *                                    '\''   '\''    quoteState
 *                                     '/'    '/'    slashState
 * </pre>
 * 
 * </blockquote> In addition to allowing modification of the state table, this
 * class makes each of the states above available. Some of these states are
 * customizable. For example, wordState allows customization of what characters
 * can be part of a word, after the first character.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker
 * makes no representations or warranties about the fitness of this software for
 * any particular purpose, including the implied warranty of merchantability.
 * 
 * @author Steven J. Metsker (c) 2006-07 Continuing Education , University of
 *         Utah . All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846
 *         25702.PROV
 * @version 1.0
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach
 *         and Continuing Education (AOCE) 1901 East South Campus Dr., Room
 *         2197-E University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112-9359<br>
 *         U.S.A.<br>
 *         Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 *         <br>
 *         Please contact these numbers immediately if you receive this file
 *         without permission from the authors. Thank you.<br>
 *         <br>
 *         (c) 2006-07 Continuing Education , University of Utah . All
 *         copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 29, 2007
 */
public class Tokenizer
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Tokenizer.class);

	/**
	 * The number of characters that might be in a symbol
	 */
	private static final int DEFAULT_SYMBOL_MAX = 4;

	// ========================= FIELDS ====================================

	/**
	 * The reader to read characters from
	 */
	private PushbackReader reader;

	/**
	 * The state lookup table
	 */
	private final TokenizerState[] characterState = new TokenizerState[256];

	/**
	 * The default states that actually consume text and produce a token. They
	 * are declared final so that the initialization in the no-argument
	 * constructor keeps the correct references to states in the character state
	 * array. States can still be customized by calling the state getter methods
	 * of this object and then calling the obtained states' methods. This
	 * violates PLK but that's OK -- we are getting the benefits of the State
	 * Pattern in return.
	 */
	protected final NumberState numberState = new NumberState();

	protected final QuoteState quoteState = new QuoteState();

	protected final SlashState slashState = new SlashState();

	protected final SymbolState symbolState = new SymbolState();

	protected final WhitespaceState whitespaceState = new WhitespaceState();

	protected final WordState wordState = new WordState();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a tokenizer with a default state table (as described in the
	 * class comment).
	 * 
	 * @return a tokenizer
	 */
	public Tokenizer()
	{
		setCharacterState(0, 255, symbolState()); // the default

		setCharacterState(0, ' ', whitespaceState());
		setCharacterState('a', 'z', wordState());
		setCharacterState('A', 'Z', wordState());
		setCharacterState(0xc0, 0xff, wordState());
		setCharacterState('0', '9', numberState());
		setCharacterState('-', '-', numberState());
		setCharacterState('.', '.', numberState());
		setCharacterState('"', '"', quoteState());
		setCharacterState('\'', '\'', quoteState());
		setCharacterState('/', '/', slashState());
	}

	/**
	 * Constructs a tokenizer to read from the supplied string.
	 * 
	 * @param String
	 *            the string to read from
	 */
	public Tokenizer(String s)
	{
		this();
		setString(s);
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append("characterState:" + CommonNames.MISC.NEW_LINE_CHAR);
		for (int i = 0; i < 256; i++)
		{
			s.append(String.format("%3c: %2s", i, characterState[i]));
			if (i % 8 == 7)
			{
				s.append(CommonNames.MISC.NEW_LINE_CHAR);
			}
			else
			{
				s.append(CommonNames.MISC.TAB_CHAR);
			}
		}
		return s.toString();
	}

	// ========================= PUBLIC METHODS ============================

	/**
	 * Return the reader this tokenizer will read from.
	 * 
	 * @return the reader this tokenizer will read from
	 */
	public PushbackReader getReader()
	{
		return reader;
	}

	/**
	 * Original Metsker implementation. Causes a stack overflow when white
	 * space/slash tokenizer states are invoked many times in a string. To use
	 * it, make sure that these tokenizer states return t.nextToken() instead of
	 * Token.NULL. This method Returns the next token.
	 * 
	 * @return the next token.
	 * @exception IOException
	 *                if there is any problem reading
	 */
	@Deprecated
	public final Token nextTokenMetskerImplementation() throws IOException
	{
		int c = reader.read();
		// There was a defect here, that resulted from the fact that
		// unreading a -1 results in the next read having a value of
		// (int)(char)-1, which is 65535. This may be a defect in
		// PushbackReader.
		if (c >= 0 && c < characterState.length)
		{
			return characterState[c].nextToken(reader, c, this);
		}
		return Token.EOF;
	}

	/**
	 * Return the next token. Works around a possible stack overflow in
	 * {@link #nextTokenMetskerImplementation()}.
	 * 
	 * @return the next token.
	 * @exception IOException
	 *                if there is any problem reading
	 */
	public final Token nextToken() throws IOException
	{
		boolean done = false;
		while (!done)
		{
			int c = reader.read();
			// There was a defect here, that resulted from the fact that
			// unreading a -1 results in the next read having a value of
			// (int)(char)-1, which is 65535. This may be a defect in
			// PushbackReader.
			if (c >= 0 && c < characterState.length)
			{
				Token token = characterState[c].nextToken(reader, c, this);
				if (token == Token.NULL)
				{
					// State didn't return a character ==> read another
					// character
					continue;
				}
				return token;
			}
			else
			{
				done = true;
			}
		}
		return Token.EOF;
	}

	/**
	 * Return the state this tokenizer uses to build numbers.
	 * 
	 * @return the state this tokenizer uses to build numbers
	 */
	public final NumberState numberState()
	{
		return numberState;
	}

	/**
	 * Return the state this tokenizer uses to build quoted strings.
	 * 
	 * @return the state this tokenizer uses to build quoted strings
	 */
	public QuoteState quoteState()
	{
		return quoteState;
	}

	/**
	 * Change the state the tokenizer will enter upon reading any character
	 * between "from" and "to".
	 * 
	 * @param from
	 *            the "from" character
	 * @param to
	 *            the "to" character
	 * @param TokenizerState
	 *            the state to enter upon reading a character between "from" and
	 *            "to"
	 */
	public final void setCharacterState(int from, int to, TokenizerState state)
	{

		for (int i = from; i <= to; i++)
		{
			if (i >= 0 && i < characterState.length)
			{
				characterState[i] = state;
			}
		}
	}

	/**
	 * Set the reader to read from.
	 * 
	 * @param PushbackReader
	 *            the reader to read from
	 */
	public final void setReader(PushbackReader r)
	{
		this.reader = r;
	}

	/**
	 * Set the string to read from.
	 * 
	 * @param String
	 *            the string to read from
	 */
	public final void setString(String s)
	{
		setString(s, DEFAULT_SYMBOL_MAX);
	}

	/**
	 * Set the string to read from.
	 * 
	 * @param String
	 *            the string to read from
	 * @param int
	 *            the maximum length of a symbol, which establishes the size of
	 *            pushback buffer we need
	 */
	public final void setString(String s, int symbolMax)
	{
		setReader(new PushbackReader(new StringReader(s), symbolMax));
	}

	/**
	 * Return the state this tokenizer uses to recognize (and ignore) comments.
	 * 
	 * @return the state this tokenizer uses to recognize (and ignore) comments
	 */
	public final SlashState slashState()
	{
		return slashState;
	}

	/**
	 * Return the state this tokenizer uses to recognize symbols.
	 * 
	 * @return the state this tokenizer uses to recognize symbols
	 */
	public final SymbolState symbolState()
	{
		return symbolState;
	}

	/**
	 * Return the state this tokenizer uses to recognize (and ignore)
	 * whitespace.
	 * 
	 * @return the state this tokenizer uses to recognize whitespace
	 */
	public final WhitespaceState whitespaceState()
	{
		return whitespaceState;
	}

	/**
	 * Return the state this tokenizer uses to build words.
	 * 
	 * @return the state this tokenizer uses to build words
	 */
	public final WordState wordState()
	{
		return wordState;
	}
}
