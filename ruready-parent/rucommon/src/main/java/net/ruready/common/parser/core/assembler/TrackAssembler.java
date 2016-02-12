/*****************************************************************************************
 * Source File: TrackAssembler.java
 ****************************************************************************************/
package net.ruready.common.parser.core.assembler;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.exception.SystemException;
import net.ruready.common.parser.core.entity.Assembly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parsers that have an Assembler ask it to work on an assembly after a successful match.
 * <p>
 * By default, terminals push their matches on a assembly's stack after a successful
 * match.
 * <p>
 * Parsers recognize text, and assemblers provide any sort of work that should occur after
 * this recognition. This work usually has to do with the state of the assembly, which is
 * why assemblies have a stack and a target. Essentially, parsers trade advancement on a
 * assembly for work on the assembly's stack or target.
 * <p>
 * Oren 22-APR-07: Added generics support and converted {@link List} to {@link List} and
 * {@link ArrayList}.
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
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0 Original code: Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 *          Steve Metsker makes no representations or warranties about the fitness of this
 *          software for any particular purpose, including the implied warranty of
 *          merchantability.
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public abstract class TrackAssembler extends Assembler
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TrackAssembler.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a <code>MathParserTrackException</code> with the specified reasons for
	 * the exception.
	 */
	public TrackAssembler()
	{

	}

	// ========================= IMPLEMENTATION: Assembler =================

	/**
	 * @param a
	 * @see net.ruready.common.parser.core.assembler.Assembler#workOn(net.ruready.common.parser.core.entity.Assembly)
	 */
	@Override
	public void workOn(Assembly a)
	{
		throw new SystemException(
				"Call workOn() with error parts args instead");
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Handles a sequence failure and gives specified reasons for the failure.
	 * 
	 * @param a
	 *            the assembly to work on
	 * @param after
	 *            an indication of what text was parsed
	 * @param expected
	 *            an indication of what kind of thing was expected, such as a ')' token
	 * @param found
	 *            the text the thrower actually found
	 */
	protected abstract void workOn(Assembly a, String after, String expected, String found);
}
