/*******************************************************************************
 * Source File: AppendTokenAssembler.java
 ******************************************************************************/
package net.ruready.common.junit.assembler;

import net.ruready.common.junit.entity.LineID;
import net.ruready.common.junit.entity.LineReaderTarget;
import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.tokens.Token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Set a parameter in the target.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 *         <br>(c) 2006-07 Continuing Education , University of Utah . All
 *         copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 26, 2007
 */
class ParamSetAssembler extends Assembler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(ParamSetAssembler.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	// ========================= METHODS ===================================

	/**
	 * Replace the top token in the stack with the token's Double value. Add the
	 * token to the target's lists of expression parts.
	 * 
	 * @param Assembly
	 *            the assembly whose stack to use
	 */
	@Override
	public void workOn(Assembly a)
	{
		// Pop the name and parameter value
		String value = ((Token) a.pop()).sval();
		String name = ((Token) a.pop()).sval();

		// Get required info for math parsing from
		// ParamEvalParser's target
		LineReaderTarget target = (LineReaderTarget) a.getTarget();

		// Add string to target's evalString
		logger.debug("Setting parameter name " + name + " value " + value);
		target.setParameter(name, value);
		target.setLineID(LineID.PARAM_SET);
	}

}
