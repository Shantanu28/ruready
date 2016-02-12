/*******************************************************************************
 * Source File: EmulatedMathTokenizer.java
 ******************************************************************************/
package test.ruready.common.parser.core.tokens;

import java.util.Set;

import net.ruready.common.parser.core.tokens.Tokenizer;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A tokenizer that's used in all the mathematical expression parser.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Mar 1, 2006
 */
public class EmulatedMathTokenizer extends Tokenizer
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EmulatedMathTokenizer.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Returns a tokenizer that does not allow a '-' sign to appear in a number.
	 * Words are not allowed to have '-' in them.
	 * 
	 * @param variables
	 *            set of permitted variable symbols. These are assigned to the
	 *            tokenizer's "symbol state".
	 * @param arithmeticMode
	 *            arithmetic mode. If it is integer or rational, numbers are not
	 *            allowed to contain decimal points. Expressions like "5.7" will
	 *            be broken into "5/./7" where the "." is a symbol. Otherwise,
	 *            they are allowed.
	 */
	public EmulatedMathTokenizer(Set<String> variables)
	{
		super();

		// ========================================
		// Customize the symbol state
		// ========================================

		// Remove the default symbols
		symbolState.clear();
		
		// Add all unary op symbols to the symbol state.
		symbolState.add("+");
		symbolState.add("-");
		symbolState.add("*");
		symbolState.add("/");

		// Add all relational operation symbols to the symbol state
		symbolState.add(":=");
		symbolState.add("=");
		symbolState.add("!=");
		symbolState.add("<");
		symbolState.add(">");
		symbolState.add("<=");
		symbolState.add(">=");

		// Any symbol on the variable's list is recognized as a symbol; if
		// variable names are words, this has no effect -- these variables
		// will be recognized as word tokens, not symbol tokens.
		for (String variable : variables) {
			symbolState.add(variable);
		}

		// Control sequence reserved symbols
		// Control sequence reserved symbols
		symbolState.add(CommonNames.MISC.EMPTY_STRING + '!');
		symbolState.add(CommonNames.MISC.EMPTY_STRING + '*');

		setCharacterState('!', '!', symbolState);
		setCharacterState('*', '*', symbolState);

		// Signed numbers are not allowed. This will break
		// "-1" into "-/1" and "+1" into "+/1".
		setCharacterState('-', '-', symbolState);
		setCharacterState('+', '+', symbolState);

		// ========================================
		// Customize word recognition
		// ========================================

		wordState.setWordChars('-', '-', false);
		wordState.setWordChars('!', '!', false);
		wordState.setWordChars('*', '*', false);
	}

	// ========================= METHODS ===================================
}
