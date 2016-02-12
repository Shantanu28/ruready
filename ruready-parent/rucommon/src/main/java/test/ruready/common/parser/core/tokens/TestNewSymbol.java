package test.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.ruready.common.parser.core.tokens.Token;
import net.ruready.common.parser.core.tokens.Tokenizer;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * This class shows how to add a new multi-character symbol.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker
 * makes no representations or warranties about the fitness of this software for
 * any particular purpose, including the implied warranty of merchantability.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class TestNewSymbol extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestNewSymbol.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Demonstrate how to add a multi-character symbol.
	 */
	@Test
	public void testSymbol1() throws IOException
	{
		Tokenizer t = new Tokenizer("42.001 =~= x 42");

		t.symbolState().add("=~=");
		// This has no effect, because a word may start with "x"
		t.symbolState().add("x");

		// Check tokens
		Token[] expectedTokens = new Token[]
		{ new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 42.001),
				new Token(Token.TT_SYMBOL, "=~=", 0), new Token(Token.TT_WORD, "x", 0),
				new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 42.0),
				new Token(Token.TT_WORD, "x", 0) };

		checkTokens(t, expectedTokens);

	}

	/**
	 * Demonstrate how to add a multi-character symbol using a customized
	 * {@link Tokenizer} object.
	 */
	@Test
	public void testSymbol2() throws IOException
	{
		Tokenizer t = new Tokenizer();

		t.symbolState().add("=~=");
		// This has no effect, because a word may start with "x"
		t.symbolState().add("x");

		t.setString("42.001 =~= x 42");

		// Check tokens
		Token[] expectedTokens = new Token[]
		{ new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 42.001),
				new Token(Token.TT_SYMBOL, "=~=", 0), new Token(Token.TT_WORD, "x", 0),
				new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 42.0),
				new Token(Token.TT_WORD, "x", 0) };

		checkTokens(t, expectedTokens);

	}

	/**
	 * Demonstrate how to add a multi-character symbol using a customized
	 * {@link Tokenizer} class.
	 */
	@Test
	public void testSymbol3() throws IOException
	{
		class MyTokenizer extends Tokenizer
		{
			public MyTokenizer()
			{
				super();
				symbolState.add("=~=");
				// This has no effect, because a word may start with "x"
				symbolState.add("x");
			}

			public MyTokenizer(String s)
			{
				this();
				setString(s);
			}

		}

		Tokenizer t = new MyTokenizer();
		t.setString("42.001 =~= x 42");

		// Check tokens
		Token[] expectedTokens = new Token[]
		{ new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 42.001),
				new Token(Token.TT_SYMBOL, "=~=", 0), new Token(Token.TT_WORD, "x", 0),
				new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 42.0),
				new Token(Token.TT_WORD, "x", 0) };

		checkTokens(t, expectedTokens);

		Tokenizer t2 = new MyTokenizer("42.001 =~= x 42");
		checkTokens(t2, expectedTokens);
	}

	/**
	 * Demonstrate how to add a multi-character symbol using a customized
	 * {@link Tokenizer} class. Using values that are close to the math parser
	 * case.
	 */
	@Test
	public void testSymbol4() throws IOException
	{
		Set<String> variables = new HashSet<String>();
		variables.add("x");

		Tokenizer t = new EmulatedMathTokenizer(variables);
		t.setString("x+5*yy-3 :=");

		// Check tokens
		Token[] expectedTokens = new Token[]
		{ new Token(Token.TT_WORD, "x", 0), new Token(Token.TT_SYMBOL, "+", 0),
				new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 5),
				new Token(Token.TT_SYMBOL, "*", 0), new Token(Token.TT_WORD, "yy", 0),
				new Token(Token.TT_SYMBOL, "-", 0),
				new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 3.0),
				new Token(Token.TT_SYMBOL, ":=", 0) };

		checkTokens(t, expectedTokens);
	}

	// ========================= PRIVATE METHODS ============================

	/**
	 * @param t
	 * @param expectedTokens
	 * @throws IOException
	 */
	private static void checkTokens(Tokenizer t, Token[] expectedTokens)
			throws IOException
	{
		for (int j = 0; j < expectedTokens.length; j++)
		{
			Token token = t.nextToken();
			Token expectedToken = expectedTokens[j];
			if (token.equals(Token.EOF))
			{
				break;
			}
			logger.debug("token " + token + " type " + token.ttype() + " expected "
					+ expectedToken + " type " + expectedToken.ttype());
			Assert.assertEquals(token.equals(expectedToken), true);
		}
	}

	// ========================= TESTING ====================================
}
