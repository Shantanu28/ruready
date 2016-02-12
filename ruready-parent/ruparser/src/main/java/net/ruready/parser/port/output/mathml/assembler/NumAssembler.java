/*****************************************************************************************
 * Source File: NumAssembler.java
 ****************************************************************************************/
package net.ruready.parser.port.output.mathml.assembler;

import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.exception.InternationalizableErrorMessage;
import net.ruready.common.parser.core.tokens.Token;
import net.ruready.parser.arithmetic.entity.numericalvalue.NumericalValue;
import net.ruready.parser.math.entity.MathToken;
import net.ruready.parser.port.output.mathml.entity.XmlStringTarget;
import net.ruready.parser.rl.ParserNames;
import net.ruready.parser.tokenizer.entity.MathAssembly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An assembler assigned to "Num" components (numbers) of a logical expression.
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
 * @version Sep 8, 2007
 */
class NumAssembler extends Assembler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(NumAssembler.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	// ========================= METHODS ===================================

	/**
	 * Replace the top token in the stack with the token's Double value. Add the number to
	 * the target's lists of expression parts.
	 * 
	 * @param Assembly
	 *            the assembly whose stack to use
	 */
	@Override
	public void workOn(Assembly a)
	{
		XmlStringTarget target = (XmlStringTarget) a.getTarget();
		// logger.debug("start, a=" + a + ", target " + target);

		// Pop a token from the assembly, get the number
		double dvalue = ((Token) a.pop()).nval();

		// Make a math token representing this number
		// Convert all numbers encountered to the field of values specified by
		// the arithmetic mode
		NumericalValue value = null;
		try
		{
			value = target.createValue(dvalue);
		}
		catch (NumberFormatException e)
		{
			// May be caught when the arithmetic mode is changed by a control
			// sequence
			target.addSyntaxError(new InternationalizableErrorMessage(
					"Failed to create numerical value",
					ParserNames.KEY.MATH_EXCEPTION.CONSTANT, Double.toString(dvalue),
					target.getArithmeticMode().toString()));
		}

		// Save token in target
		MathToken t = new MathToken(((MathAssembly) a).elementsConsumedAll() - 1, value);
		// logger.debug("orig "+dvalue+" rounded "+value);

		// Push expression onto stack
		StringBuffer e = ConversionUtil.generateNumericalElement(t);
		a.push(e);
		// logger.debug("end , a=" + a + ", target " + target);
	}

}
