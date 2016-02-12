/*****************************************************************************************
 * Source File: ValueBean.java
 ****************************************************************************************/
package net.ruready.common.eis.entity;

import net.ruready.common.pointer.ValueObject;
import net.ruready.common.rl.ApplicationContext;

/**
 * A bean (e.g. in Struts) that is used to copy fields to and from a {@link ValueObject}.
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
 * @version Sep 3, 2007
 */
public interface ValueBean<V extends ValueObject> extends ValueObject
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Copy fields from a value object to this bean.
	 * 
	 * @param vo
	 *            value object to copy fields from
	 * @param context
	 *            contains optional input and output arguments. A commonly used argument
	 *            is action/validation errors used by the web layer.
	 */
	void copyFrom(V valueObject, final ApplicationContext context);

	/**
	 * Copy fields to a value object to this bean.
	 * 
	 * @param vo
	 *            value object to copy fields to
	 * @param context
	 *            contains optional input and output arguments. A commonly used argument
	 *            is action/validation errors used by the web layer.
	 */
	void copyTo(V valueObject, final ApplicationContext context);
}
