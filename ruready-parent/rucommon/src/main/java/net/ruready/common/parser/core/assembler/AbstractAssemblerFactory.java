/*****************************************************************************************
 * Source File: AbstractAssemblerFactory.java
 ****************************************************************************************/
package net.ruready.common.parser.core.assembler;

/**
 * An abstract factory to instantiate assembler types for the arithmetic parser.
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
 * @see {link ArithmeticAsssemblerID}
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public interface AbstractAssemblerFactory
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Create an assembler corresponding to a certain assembler enumerated type.
	 * 
	 * @param id
	 *            assembler ID
	 * @param args
	 *            instantiation arguments. May differ for different assemblers. See the
	 *            documentation of each enumerated type.
	 * @return an assembler instance of the type corresponding to this enumerated type
	 */
	public abstract Assembler createAssembler(AssemblerIdentifier id, Object... args);

}
