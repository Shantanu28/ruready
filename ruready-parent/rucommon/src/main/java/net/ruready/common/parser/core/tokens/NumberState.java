/*******************************************************
 * Source File: NumberState.java
 *******************************************************/
package net.ruready.common.parser.core.tokens;

import java.io.IOException;
import java.io.PushbackReader;

import net.ruready.common.rl.CommonNames;

/**
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker
 * makes no representations or warranties about the fitness of this software for
 * any particular purpose, including the implied warranty of merchantability.
 * <p>
 * A NumberState object returns a real number from a reader. This state's idea
 * of a number allows an optional, initial minus sign, followed by one or more
 * digits. A decimal point and another string of digits may follow these digits.
 * <p>
 * Fixed by Oren Livne to prevent overflow of a temporary variable when the
 * number becomes large.
 */
public class NumberState extends TokenizerState
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	protected int c;

	protected boolean absorbedLeadingMinus;

	protected boolean gotAdigit;

	protected boolean absorbedDot;

	private double value;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a number tokenization state.
	 */
	public NumberState()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a triplet.
	 */
	@Override
	public String toString()
	{
		return CommonNames.PARSER.CORE.TOKENS.TOKENIZER_STATE.NUMBER;
	}

	// ========================= IMPLEMENTATION: TokenizerState ============

	/**
	 * Return a number token from a reader.
	 * 
	 * @return a number token from a reader
	 */
	@Override
	public Token nextToken(PushbackReader r, int cin, Tokenizer t)
			throws IOException
	{

		reset(cin);
		parseLeft(r);
		parseRight(r);
		r.unread(c);
		return value(r, t);
	}

	// ========================= METHODS ===================================

	/**
	 * Convert a stream of digits into a number, making this number a fraction
	 * if the boolean parameter is true.
	 * 
	 * @param r
	 *            reader
	 * @param fraction
	 *            if true will make this number a fraction
	 * @return double value
	 * @throws IOException
	 */
	private double absorbDigits(PushbackReader r, boolean fraction)
			throws IOException
	{
		// Original Metzker code
		// Has a bug when there are many digits after the decimal
		// point: "v" becomes so big before dividing by "divideBy"
		// that serious round-off kicks in. Corrected below.
		/*
		 * int divideBy = 1; double v = 0; while ('0' <= c && c <= '9') {
		 * gotAdigit = true; v = v * 10 + (c - '0'); c = r.read(); if (fraction) {
		 * divideBy *= 10; } } if (fraction) { v = v / divideBy; }
		 */
		// Corrected code begins here
		double multBy = 1.0;
		double v = 0;
		while ('0' <= c && c <= '9') {
			gotAdigit = true;
			if (fraction) {
				multBy *= 0.1;
				v = v + multBy * (c - '0');
			}
			else {
				v = v * 10 + (c - '0');
			}
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
	protected void parseLeft(PushbackReader r) throws IOException
	{

		if (c == '-') {
			c = r.read();
			absorbedLeadingMinus = true;
		}
		value = absorbDigits(r, false);
	}

	/**
	 * Parse from a decimal point to the end of the number.
	 * 
	 * @param r
	 *            reader
	 * @throws IOException
	 */
	protected void parseRight(PushbackReader r) throws IOException
	{

		if (c == '.') {
			c = r.read();
			absorbedDot = true;
			value += absorbDigits(r, true);
		}
	}

	/**
	 * Prepare to assemble a new number.
	 * 
	 * @param cin
	 *            input character
	 */
	protected void reset(int cin)
	{
		c = cin;
		value = 0;
		absorbedLeadingMinus = false;
		absorbedDot = false;
		gotAdigit = false;
	}

	/**
	 * Put together the pieces of a number.
	 * 
	 * @param r
	 *            reader
	 * @param t
	 *            tokenizer
	 * @return this state's output token representing a number
	 * @throws IOException
	 */
	protected Token value(PushbackReader r, Tokenizer t) throws IOException
	{

		if (!gotAdigit) {
			if (absorbedLeadingMinus && absorbedDot) {
				r.unread('.');
				return t.symbolState().nextToken(r, '-', t);
			}
			if (absorbedLeadingMinus) {
				return t.symbolState().nextToken(r, '-', t);
			}
			if (absorbedDot) {
				return t.symbolState().nextToken(r, '.', t);
			}
		}
		if (absorbedLeadingMinus) {
			value = -value;
		}
		return new Token(Token.TT_NUMBER, CommonNames.MISC.EMPTY_STRING, value);
	}

}
