/*******************************************************************************
 * Source File: SetFenceAssembler.java
 ******************************************************************************/
package net.ruready.common.parser.core.assembler;

import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.tokens.Token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This assembler sets a fence in (pushes a fence token onto) the assembly's
 * stack. This is highly reusable, so it is part of the core parser package.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Apr 22, 2007
 */
public class SetFenceAssembler extends Assembler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SetFenceAssembler.class);

	// ========================= FIELDS ====================================

	// Fence token
	private Token fence;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a fence assembler.
	 * 
	 * @param fence
	 *            fence token
	 */
	public SetFenceAssembler(Token fence)
	{
		super();
		this.fence = fence;
	}

	// ========================= IMPLEMENTATION: Assembler =================

	/**
	 * Push the fence token onto the assembly's stack.
	 * 
	 * @param Assembly
	 *            the assembly to process
	 */
	@Override
	public void workOn(Assembly a)
	{
		a.push(fence);
	}
}
