/*****************************************************************************************
 * Source File: Identifiable.java
 ****************************************************************************************/
package net.ruready.common.discrete;

/**
 * An identifiable object. parameterized by the identifier type. Note that an identifiable
 * object can also be regarded as a unique identifier (of itself), hence it extends the
 * {@link Identifier} interface.
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
 * @version Jul 16, 2007
 */
public interface Identifiable<ID extends Identifier> extends Identifier
{

	/**
	 * Returns the identifier of this object.
	 * 
	 * @return the type of this item
	 */
	ID getIdentifier();

}
