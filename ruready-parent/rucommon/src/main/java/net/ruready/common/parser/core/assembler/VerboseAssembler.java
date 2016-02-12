/*******************************************************************************
 * Source File: VerboseAssembler.java
 ******************************************************************************/
package net.ruready.common.parser.core.assembler;

import net.ruready.common.parser.core.entity.Assembly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A decorator that prints some debugging printouts at the beginning and end of
 * the assembler's <code>workOn()</code> method.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 05/12/2005
 */
public class VerboseAssembler extends Assembler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(VerboseAssembler.class);

	// If false, will not even compile the debugging printout lines in this
	// class
	// private static final boolean debug = false;
	private static final boolean debug = true;

	// ========================= FIELDS ====================================

	// The object to decorate
	private Assembler assembler;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a decorator of an assembler
	 * 
	 * @param assembler
	 *            The object to decorate
	 */
	public VerboseAssembler(Assembler assembler)
	{
		super();
		this.assembler = assembler;
	}

	// ========================= METHODS ===================================

	/**
	 * Decorate the <code>assembler</code>'s <code>workOn()</code> method
	 * with pre and post debugging printouts.
	 * 
	 * @param a
	 *            the assembly whose stack is used
	 */
	@Override
	public void workOn(Assembly a)
	{
		// Pre-assembly printouts
		if (VerboseAssembler.debug) {
			String format = "%16s %s";
			Object[] temp = new Object[2];
			temp[0] = assembler.getName();

			temp[1] = "start assembly " + a;
			logger.debug(String.format(format, temp));

			temp[1] = "start target " + a.getTarget();
			logger.debug(String.format(format, temp));
		}

		// Do the actual assembly work
		assembler.workOn(a);

		// Post-assembly printouts
		if (VerboseAssembler.debug) {
			String format = "%16s %s";
			Object[] temp = new Object[2];
			temp[0] = assembler.getName();

			temp[1] = "end   assembly " + a;
			logger.debug(String.format(format, temp));

			temp[1] = "end   target " + a.getTarget();
			logger.debug(String.format(format, temp));

			logger.debug("----------------------------------------------");
		}
	}
}
