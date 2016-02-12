/*****************************************************************************************
 * Source File: ValueObject.java
 ****************************************************************************************/
package net.ruready.common.pointer;

import java.io.Serializable;

/**
 * An abstract Value Object (a.k.a. VO, Data Transfer Object, DTO). This is only a marker
 * interface to standardize VOs across the persistent layer implementation. We anticipate
 * that VOs will be passed between different JVMs, so they implement
 * <code>Serializable</code>.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 31, 2007
 */
public interface ValueObject extends Serializable
{
	// ========================= ABSTRACT METHODS ==========================
}
