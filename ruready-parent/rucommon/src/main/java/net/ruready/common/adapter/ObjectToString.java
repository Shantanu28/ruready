/*****************************************************************************************
 * Source File: ObjectToString.java
 ****************************************************************************************/
package net.ruready.common.adapter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.util.DisplayUtil;
import net.ruready.common.util.ObjectUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities converting objects to strings.
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
 * @version Jul 16, 2007 $Revision: 1.1 $
 */
public class ObjectToString implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(ObjectToString.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ObjectToString()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * @param object
	 * @return
	 */
	public static String simpleToString(Object object)
	{
		if (object == null)
		{
			return "NULL";
		}

		StringBuffer sb = new StringBuffer();

		sb.append(DisplayUtil.niceClassName(object));

		sb.append(CommonNames.MISC.NEW_LINE_CHAR);

		Map<String, Object> attributes = new TreeMap<String, Object>(ObjectUtil
				.mapAttributes(object, ObjectUtil.stdIgnoreMap));
		Iterator<Map.Entry<String, Object>> iterator = attributes.entrySet().iterator();
		while (iterator.hasNext())
		{
			Map.Entry<String, Object> entry = iterator.next();
			sb.append("\t");

			sb.append(entry.getKey().toString().substring(3)); // strip off the
			// "get"
			sb.append(" = ");
			if (entry.getValue() != null)
			{

				if (entry.getValue() instanceof Collection)
				{
					sb.append("{");
					sb.append(DisplayUtil.niceClassName(entry.getValue()));
					sb.append(" / ");
					sb.append("size = ");
					sb.append(((Collection<?>) entry.getValue()).size());
					sb.append("}");

				}
				else
				{
					sb.append("{");
					sb.append(DisplayUtil.niceClassName(entry.getValue()));
					sb.append("}");
				}
				sb.append(CommonNames.MISC.NEW_LINE_CHAR);
			}
			else
			{
				sb.append("NULL");
			}
		}

		return sb.toString();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param object
	 *            DOCUMENT ME!
	 * @return DOCUMENT ME!
	 */
	public static String process(Object object)
	{

		try
		{
			if (object != null)
			{
				BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
				MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();

				MethodDescriptor methodDescriptor;
				Method method;
				Object objectResult = null;
				StringBuffer sb = new StringBuffer();

				sb
						.append("\n+--------------------------------------------------------------\n");
				sb.append("| Start: " + object.getClass().getName()
						+ CommonNames.MISC.NEW_LINE_CHAR);
				sb
						.append("+--------------------------------------------------------------\n");

				for (int i = 0; i < methodDescriptors.length; i++)
				{
					methodDescriptor = methodDescriptors[i];
					method = methodDescriptor.getMethod();

					if (method.getParameterTypes().length == 0
							&& (method.getName().startsWith("get") || method.getName()
									.startsWith("is")))
					{ // we
						// are
						// looking
						// at
						// a
						// getter
						// method

						try
						{
							objectResult = method.invoke(object, (Object[]) null);
						}
						catch (Throwable t)
						{
							logger.error(
									"An exception occurred while trying to run the following method ["
											+ method.getName() + "] on the class ["
											+ object.getClass().getName() + "]", t);
							objectResult = "error";
						}

						if (objectResult == null)
							objectResult = new String(CommonNames.MISC.NULL_TO_STRING);
						sb.append("|" + object.getClass().getName() + "."
								+ method.getName() + "() = [" + objectResult.toString()
								+ "]\n");

					}
				}

				sb
						.append("+--------------------------------------------------------------\n");
				sb.append("| End: " + object.getClass().getName()
						+ CommonNames.MISC.NEW_LINE_CHAR);
				sb
						.append("+--------------------------------------------------------------\n");

				return sb.toString();
			}
			else
			{
				return CommonNames.MISC.EMPTY_STRING;
			}
		}
		catch (Exception e)
		{

			logger.error("error has occurred in ObjectToString.process()");
			e.printStackTrace();
			return CommonNames.MISC.EMPTY_STRING;
		}
	}
}
