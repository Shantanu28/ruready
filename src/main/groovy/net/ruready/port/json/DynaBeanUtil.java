/*****************************************************************************************
 * Source File: DynaBeanUtil.java
 ****************************************************************************************/
package net.ruready.port.json;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.EnumUtil;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Centralizes useful methods and conversions related to ezmorph dynamic beans.
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
 * @version Jul 29, 2007
 */
public class DynaBeanUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DynaBeanUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private DynaBeanUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Get a dynamic bean property as a long value.
	 * 
	 * @param bean
	 *            dynamic bean
	 * @param propertyName
	 *            property name in the bean
	 * @param defaultValue
	 *            default value to use if bean property is not found or fails to
	 *            convert to a long
	 * @return dynamic bean property as a long value
	 */
	public static long getPropertyAsLong(final DynaBean bean, final String propertyName,
			final long defaultValue)
	{
		long value = defaultValue;
		try
		{
			value = TextUtil.getStringAsLong(CommonNames.MISC.EMPTY_STRING
					+ bean.get(propertyName));
		}
		catch (Exception e)
		{

		}
		return value;
	}

	/**
	 * Get a dynamic bean property as a boolean value.
	 * 
	 * @param bean
	 *            dynamic bean
	 * @param propertyName
	 *            property name in the bean
	 * @param defaultValue
	 *            default value to use if bean property is not found or fails to
	 *            convert to a boolean
	 * @return dynamic bean property as a boolean value
	 */
	public static boolean getPropertyAsBoolean(final DynaBean bean,
			final String propertyName, final boolean defaultValue)
	{
		boolean value = defaultValue;
		try
		{
			value = TextUtil.getStringAsBoolean(CommonNames.MISC.EMPTY_STRING
					+ bean.get(propertyName));
		}
		catch (Exception e)
		{

		}
		return value;
	}

	/**
	 * Get a dynamic bean property as a string value.
	 * 
	 * @param bean
	 *            dynamic bean
	 * @param propertyName
	 *            property name in the bean
	 * @param defaultValue
	 *            default value to use if bean property is not found or fails to
	 *            convert to a string
	 * @return dynamic bean property as a string value
	 */
	public static String getPropertyAsString(final DynaBean bean,
			final String propertyName, final String defaultValue)
	{
		String value = defaultValue;
		try
		{
			value = (String) bean.get(propertyName);
		}
		catch (Exception e)
		{

		}
		return value;
	}

	/**
	 * Get a dynamic bean property as a enumerated type value.
	 * 
	 * @param enumType
	 *            the Class object of the enumerated type
	 * @param bean
	 *            dynamic bean
	 * @param propertyName
	 *            property name in the bean
	 * @param defaultValue
	 *            default value to use if bean property is not found or fails to
	 *            convert to the enumerated type
	 * @return dynamic bean property as a enumerated type
	 */
	public static <T extends Enum<T>> T getPropertyAsEnum(final Class<T> enumType,
			final DynaBean bean, final String propertyName, final T defaultValue)
	{
		// Note: we could use defaultValue.getClass() instead of passing in
		// enumType, but it would have to be non-null.
		T value = defaultValue;
		try
		{
			value = EnumUtil.createFromString(enumType, (String) bean.get(propertyName));
		}
		catch (Exception e)
		{

		}
		return value;
	}
}
