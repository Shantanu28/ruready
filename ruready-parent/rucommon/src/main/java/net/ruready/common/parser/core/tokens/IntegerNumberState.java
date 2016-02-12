/*****************************************************************************************
 * Source File: IntegerNumberState.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;

import net.ruready.common.rl.CommonNames;

/**
 * A IntegerNumberState object returns an integer number from a reader. This state's idea
 * of a number allows an optional, initial minus sign, followed by one or more digits. A
 * decimal point may not follow.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 29, 2007
 */
public class IntegerNumberState extends NumberState
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	private int intValue;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a number tokenization state.
	 */
	public IntegerNumberState()
	{
		super();
	}

	// ========================= IMPLEMENTATION: TokenizerState ============

	// ========================= METHODS ===================================

	/**
	 * Convert a stream of digits into an integer number.
	 * 
	 * @param r
	 *            reader
	 * @return integer value
	 * @throws IOException
	 */
	protected int absorbDigits(PushbackReader r) throws IOException
	{
		int v = 0;
		while ('0' <= c && c <= '9')
		{
			gotAdigit = true;
			v = v * 10 + (c - '0');
			c = r.read();
		}

		return v;
	}

	/**
	 * Parse up to a decimal point.
	 * 
	 * @param r
	 *            reader
	 * @throws IOException
	 */
	@Override
	protected void parseLeft(PushbackReader r) throws IOException
	{

		if (c == '-')
		{
			c = r.read();
			absorbedLeadingMinus = true;
		}
		intValue = absorbDigits(r);
	}

	/**
	 * Parse from a decimal point to the end of the number.
	 * 
	 * @param r
	 *            reader
	 * @throws IOException
	 */
	@Override
	protected void parseRight(PushbackReader r) // throws IOException
	{
		// Do nothing
	}

	/**
	 * Prepare to assemble a new number.
	 * 
	 * @param cin
	 *            input character
	 */
	@Override
	protected void reset(int cin)
	{
		super.reset(cin);
		intValue = 0;
	}

	/**
	 * Put together the pieces of an integer number.
	 * 
	 * @param r
	 *            reader
	 * @param t
	 *            tokenizer
	 * @return this state's output token representing an integer number
	 * @throws IOException
	 */
	@Override
	protected Token value(PushbackReader r, Tokenizer t) throws IOException
	{

		if (!gotAdigit)
		{
			if (absorbedLeadingMinus && absorbedDot)
			{
				r.unread('.');
				return t.symbolState().nextToken(r, '-', t);
			}
			if (absorbedLeadingMinus)
			{
				return t.symbolState().nextToken(r, '-', t);
			}
			if (absorbedDot)
			{
				return t.symbolState().nextToken(r, '.', t);
			}
		}
		if (absorbedLeadingMinus)
		{
			intValue = -intValue;
		}
		return new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, intValue);
	}
}
