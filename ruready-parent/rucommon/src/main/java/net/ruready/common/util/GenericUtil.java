/*****************************************************************************************
 * Source File: EnumUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities related to generic types.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 1, 2007
 */
public class GenericUtil<T> implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(GenericUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	public GenericUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Returns the type of the first generic parameter of this class.
	 * 
	 * @param <T>
	 * @return
	 */
	// public Class<T> getParameterClass()
	// {
	// // warning: [unchecked] unchecked cast
	// return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
	// .getActualTypeArguments()[0];
	// }
	/**
	 * Returns the type of the first generic parameter of this class.
	 * 
	 * @param <T>
	 * @return
	 */

	// public Class<T> returnedClass()
	// {
	// // warning: [unchecked] unchecked cast
	// ParameterizedType parameterizedType = (ParameterizedType) getClass()
	// .getGenericSuperclass();
	// return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	// }
	/**
	 * @param args
	 */
}
