/*****************************************************************************************
 * Source File: AbstractTagBD.java
 ****************************************************************************************/
package net.ruready.business.content.tag.exports;

import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.content.tag.manager.AbstractTagManager;
import net.ruready.common.rl.BusinessDelegate;

/**
 * Defines the business delegate of operations relating to the {@link TagItem} hierarchy.
 * This interface should be implemented by every view (e.g. a web tier). It has all the
 * capabilities of the manager, and uses a specific resource locator.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 3, 2007
 */
public interface AbstractTagBD extends BusinessDelegate, AbstractTagManager
{

}
