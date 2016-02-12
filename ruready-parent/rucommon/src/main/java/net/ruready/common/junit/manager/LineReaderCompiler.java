/*****************************************************************************************
 * Source File: LineReaderCompiler.java
 ****************************************************************************************/
package net.ruready.common.junit.manager;

import net.ruready.common.junit.assembler.LineReaderAssemblerFactory;
import net.ruready.common.junit.assembler.LineReaderAssemblerID;
import net.ruready.common.junit.entity.LineID;
import net.ruready.common.parser.core.assembler.AbstractAssemblerFactory;
import net.ruready.common.parser.core.manager.AbstractCompiler;
import net.ruready.common.parser.core.manager.Alternation;
import net.ruready.common.parser.core.manager.Empty;
import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.Repetition;
import net.ruready.common.parser.core.manager.Sequence;
import net.ruready.common.parser.core.tokens.Symbol;
import net.ruready.common.parser.core.tokens.Tokenizer;
import net.ruready.common.parser.core.tokens.Word;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class provides a parser that parses a single trimmed line from a test data file.<br>
 * <code>
 *    line           = Empty | paramSetLine | dataLine;
 *    dataLine       = nonReserved token*;
 *    paramSetLine   = '$' (' ')* Word (' ')* '=' (' ')* Word;
 *    token          = Word | Num | Symbol;
 * </code>
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
 * @version Jul 27, 2007
 */
class LineReaderCompiler implements AbstractCompiler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LineReaderCompiler.class);

	// ========================= FIELDS ====================================

	// -------------------------------------------------
	// Required input
	// -------------------------------------------------

	// Assembler factory; makes the same syntax usable for multiple purposes
	private final AbstractAssemblerFactory factory = new LineReaderAssemblerFactory();

	// Uncomment to enable assembler debugging printouts
	// private final AbstractAssemblerFactory factory = new
	// VerboseLineReaderAssemblerFactory();

	// -------------------------------------------------
	// Temporary variables to alleviate cyclic
	// grammar dependencies. Must be lazy-initialized.
	// -------------------------------------------------

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize an arithmetic parser from options.
	 * 
	 * @param options
	 *            control options object
	 * @param factory
	 *            assembler factory to use
	 */
	public LineReaderCompiler()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Compiler ==================

	/**
	 * Main parser call. Returns a parser that will recognize a single test data file
	 * trimmed line. The gramma rule is
	 * <p>
	 * line = Empty | paramSetLine | dataLine;
	 * 
	 * @return a parser that will recognize a single test data file trimmed line.
	 */
	public Parser parser()
	{
		// line = Empty | paramSetLine | dataLine
		Alternation line = new Alternation("line");
		line.add(new Empty().setAssembler(factory.createAssembler(
				LineReaderAssemblerID.SET_LINE_ID, LineID.EMPTY)));
		line.add(dataLine().setAssembler(
				factory.createAssembler(LineReaderAssemblerID.SET_LINE_ID, LineID.DATA)));
		line.add(paramSetLine().setAssembler(
				factory.createAssembler(LineReaderAssemblerID.PARAM_SET)));
		return line;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Returns a parser that will recognize the gramma rule for a data line:
	 * <p>
	 * dataLine = nonReserved token*;
	 * 
	 * @return a parser that will recognize parameter setting lines
	 */
	private Parser dataLine()
	{
		// rest = token*;
		Repetition rest = new Repetition(token().setAssembler(
				factory.createAssembler(LineReaderAssemblerID.APPEND_TOKEN)));

		// dataLine = nonReserved rest;
		Sequence dataLine = new Sequence("dataLine");
		dataLine.add(nonReserved().setAssembler(
				factory.createAssembler(LineReaderAssemblerID.APPEND_TOKEN)));
		dataLine.add(rest);
		return dataLine;
	}

	/**
	 * Returns a parser that will recognize the gramma rule for setting a parameter:
	 * <p>
	 * paramSetLine = '$' (' ')* Word (' ')* '=' (' ')* Word;
	 * 
	 * @return a parser that will recognize parameter setting lines
	 */
	private Parser paramSetLine()
	{
		// whiteSpace = (' ')*;
		Repetition whiteSpace = new Repetition(new Symbol(' ').discard());

		// paramSet = $' whiteSpace Word whiteSpace '=' whiteSpace Word;
		Sequence paramSetLine = new Sequence("paramSetLine");
		paramSetLine.add(new Symbol('$').discard());
		paramSetLine.add(whiteSpace);
		paramSetLine.add(new Word());
		paramSetLine.add(whiteSpace);
		paramSetLine.add(new Symbol('=').discard());
		paramSetLine.add(whiteSpace);
		paramSetLine.add(new Word());
		return paramSetLine;
	}

	/**
	 * Returns a parser that will recognize a token. The gramma rule is:
	 * <p>
	 * token = nonReserved | reservedSymbol;
	 * 
	 * @return a parser that will recognize parameter setting lines
	 */
	private Parser token()
	{
		// token = nonReserved | reservedSymbol;
		Alternation token = new Alternation("token");
		token.add(nonReserved());
		token.add(reservedSymbol());
		return token;
	}

	/**
	 * Returns a parser that will recognize a non-reserved token. The gramma rule is:
	 * <p>
	 * nonReserved = Word();
	 * 
	 * @return a parser that will recognize non-reserved tokens
	 */
	private Parser nonReserved()
	{
		// Word | '/' | '*'
		Alternation nonReserved = new Alternation("nonReserved");
		nonReserved.add(new Word());
		nonReserved.add(new Symbol('/'));
		nonReserved.add(new Symbol('*'));
		return nonReserved;
	}

	/**
	 * Returns a parser that will recognize a reserved symbol. The gramma rule is:
	 * <p>
	 * reservedSymbol = ' ' | '$' | '=' | '*';
	 * 
	 * @return a parser that will recognize reserved symbols
	 */
	private Parser reservedSymbol()
	{
		// Word | reservedSymbol
		Alternation reservedSymbol = new Alternation("reservedSymbol");
		reservedSymbol.add(new Symbol(' '));
		reservedSymbol.add(new Symbol('$'));
		reservedSymbol.add(new Symbol('='));
		// reservedSymbol.add(new Symbol('*'));
		return reservedSymbol;
	}

	// ========================= METHODS ===================================

	/**
	 * Returns a tokenizer that allows tokenizes based on a single delimiter, the
	 * character '#'. "~" is recognized as a symbol.
	 * 
	 * @return a tokenizer that breaks a string into tokens based on the delimiter
	 */
	public static Tokenizer tokenizer()
	{
		Tokenizer t = new Tokenizer();

		// No numbers are allowed. Their parts are treated as parts of words.
		t.setCharacterState('0', '9', t.wordState());
		t.setCharacterState('-', '-', t.wordState());
		t.setCharacterState('.', '.', t.wordState());

		// Words can contain any character except for '/','*','$'.
		t.wordState().setWordChars(0, 255, true);
		t.wordState().setWordChars('/', '/', false);
		t.wordState().setWordChars('*', '*', false);
		t.wordState().setWordChars('$', '$', false);
		t.wordState().setWordChars(' ', ' ', false);
		t.wordState().setWordChars('=', '=', false);

		// Words can start with anything...
		t.setCharacterState(0, 255, t.wordState());

		// ... except the reserved symbols
		t.setCharacterState('/', '/', t.slashState());
		t.setCharacterState('$', '$', t.symbolState());
		t.setCharacterState(' ', ' ', t.symbolState());
		t.setCharacterState('=', '=', t.symbolState());

		return t;
	}

	// ========================= PARSER CONTROL COMPILATION ================

	// ========================= PARAMETRIC PARSER COMPILATION =============

	// ========================= UTILITY OBJECT COMPILATION ================

	// ========================= GETTERS & SETTERS =========================

}
