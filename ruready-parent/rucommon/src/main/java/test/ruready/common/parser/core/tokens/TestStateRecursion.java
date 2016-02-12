/*****************************************************************************************
 * Source File: TestParametricEvaluation.java
 ****************************************************************************************/
package test.ruready.common.parser.core.tokens;

import java.io.IOException;

import net.ruready.common.parser.core.tokens.Token;
import net.ruready.common.parser.core.tokens.Tokenizer;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test what happens when a lot of comments appear in a string fed to the
 * tokenizer.
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
public class TestStateRecursion extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestStateRecursion.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * @throws IOException
	 */
	@Test
	public void testSlashSlashState() throws IOException
	{
		StringBuffer buffer = TextUtil.emptyStringBuffer();
		final int n = 100000;
		logger.debug("Testing a string with " + n + " comments ");
		for (int i = 0; i < n; i++)
		{
			// Comment
			buffer.append("// >=give 2receive #" + i + "\n");
			// Real text
			buffer.append(">=give 2receive #" + i + "\n");
		}
		String s = buffer.toString();
		Tokenizer t = new Tokenizer(s);

		// logger.debug("Original string:\n" + s);

		// Check tokens
		for (int i = 0; i < n; i++)
		{
			// logger.debug("i " + i);
			Token expectedTokens[] = new Token[]
			{ new Token(Token.TT_SYMBOL, ">=", 0), new Token(Token.TT_WORD, "give", 0),
					new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 2.0),
					new Token(Token.TT_WORD, "receive", 0),
					new Token(Token.TT_SYMBOL, "#", 0),
					new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, 1.0 * i) };
			for (int j = 0; j < expectedTokens.length; j++)
			{
				Token token = t.nextToken();
				// logger.debug("token " + token + " expected " +
				// expectedTokens[j]);
				Assert.assertEquals(token.equals(expectedTokens[j]), true);
			}
		}
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void testSlashStarState() throws IOException
	{
		String s = "hi /* hi";
		Tokenizer t = new Tokenizer(s);

		// logger.debug("Original string:\n" + s);

		// Check tokens
		Token expectedTokens[] = new Token[]
		{ new Token(Token.TT_WORD, "hi", 0), };
		for (int j = 0; j < expectedTokens.length; j++)
		{
			Token token = t.nextToken();
			// logger.debug("token " + token + " expected " +
			// expectedTokens[j]);
			Assert.assertEquals(token.equals(expectedTokens[j]), true);
		}

	}

	// ========================= TESTING ====================================
}
