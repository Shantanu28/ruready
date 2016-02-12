/*****************************************************************************************
 * Source File: EnumUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import java.lang.reflect.Type;

import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities related to reflection.
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
public class ReflectionUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ReflectionUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ReflectionUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Does a class implement an interface.
	 * 
	 * @param clazz
	 *            class to check
	 * @param interfaceClass
	 *            interface class
	 * @return <code>true</code> iff clazz implements interfaceClass
	 */
	public static boolean isImplements(final Class<?> clazz, final Class<?> interfaceClass)
	{
		Type[] interfaces = clazz.getGenericInterfaces();
		for (Type i : interfaces)
		{
			if (i.getClass().equals(interfaceClass))
			{
				return true;
			}
		}
		return false;
	}
}
