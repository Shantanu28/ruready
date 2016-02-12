/*****************************************************************************************
 * Source File: DAO.java
 ****************************************************************************************/
package net.ruready.common.eis.entity;

import java.io.Serializable;


/**
 * An abstraction of an types is parameterized by an entity type (T) and its identifier
 * type (ID).
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
 * @version Aug 17, 2007
 */
public interface EntityDependent<T extends PersistentEntity<ID>, ID extends Serializable>
{

}
