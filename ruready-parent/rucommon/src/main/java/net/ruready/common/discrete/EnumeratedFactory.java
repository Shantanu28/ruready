/*****************************************************************************************
 * Source File: EnumeratedFactory.java
 ****************************************************************************************/
package net.ruready.common.discrete;

/**
 * An factory that instantiates different types based on a unique enumerated
 * identifier type.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 16, 2007
 */
public interface EnumeratedFactory<ID extends Identifier, T extends Identifiable<ID>>
{

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Create a type corresponding to an enumerated type.
	 * 
	 * @param identifier
	 *            unique identifier of the parser service
	 * @param args
	 *            instantiation arguments. May differ for different assemblers.
	 *            See the documentation of each enumerated type.
	 * @return an instance of the type corresponding to this enumerated type
	 */
	T createType(ID identifier, Object... args);

}
