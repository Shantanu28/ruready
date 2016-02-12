/*****************************************************************************************
 * Source File: Equalizer.java
 ****************************************************************************************/
package net.ruready.web.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Object equality utilities.
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
 * @version Oct 8, 2007
 */
public class EqualityUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(EqualityUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private EqualityUtil()
	{

	}

	/**
	 * Provides the ability to specify an ignore list, which consists of keys which
	 * correspond to the name of the method to ignore. The value of the key is not used,
	 * and should just be null. For example: key="getOLock" value=null.
	 * 
	 * @param objA
	 * @param objB
	 * @param ignoreMap
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean check(Object objA, Object objB, Map<?, ?> ignoreMap)
		throws Exception
	{

		boolean result = false;

		try
		{

			if (objA == null)
				throw new Exception("objA is null");
			if (objB == null)
				throw new Exception("objB is null");

			if (objA.getClass().getName().equalsIgnoreCase(objB.getClass().getName()))
			{

				// First gather information about the objects in question. We
				// are assuming that they are bean-like in nature.
				// BeanInfo beanInfo = Introspector.getBeanInfo(objA.getClass(),
				// BaseBusinessObject.class);
				BeanInfo beanInfo = Introspector.getBeanInfo(objA.getClass());

				MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();

				MethodDescriptor methodDescriptor;
				Method method;
				Object objAResult;
				Object objBResult;

				for (int i = 0; i < methodDescriptors.length; i++)
				{
					methodDescriptor = methodDescriptors[i];
					method = methodDescriptor.getMethod();

					// if method is a get/is method, and takes no parameters,
					// process the method
					if (((method.getName().startsWith("get")) || (method.getName()
							.startsWith("is")))
							&& (method.getParameterTypes().length == 0))
					{

						// return true if the method is in the ignore list
						if ((ignoreMap != null)
								&& ignoreMap.containsKey(method.getName()))
						{
							continue;
						}

						// get method results
						objAResult = method.invoke(objA, (Object[]) null);
						objBResult = method.invoke(objB, (Object[]) null);

						// null conditions
						if ((objAResult == null) && (objBResult == null))
						{
							result = true;
						}
						else if ((objAResult != null) && (objBResult == null))
						{
							result = false;
							return result;
						}
						else if ((objAResult == null) && (objBResult != null))
						{
							result = false;
							return result;
						}
						else if ((objAResult != null) && (objBResult != null))
						{

							if ((objAResult instanceof Collection)
									&& (objBResult instanceof Collection))
							{

								/*
								 * note: Collection runtime type must be a list!
								 */

								/*
								 * if (objAResult instanceof AbstractBoCollection) {
								 * ((AbstractBoCollection)objAResult).load();
								 * ((AbstractBoCollection)objBResult).load(); }
								 */

								Collections.sort((List) objAResult);
								Collections.sort((List) objBResult);

								Iterator iterA = ((Collection) objAResult).iterator();
								Iterator iterB = ((Collection) objBResult).iterator();
								Object a, b;

								if (((Collection) objAResult).isEmpty()
										&& ((Collection) objBResult).isEmpty())
								{
									result = true;
								}

								while (iterA.hasNext())
								{
									a = iterA.next();

									// both iterators have a next
									b = null;
									if (iterB.hasNext())
									{
										b = iterB.next();

										// if object a != object b
										if (a.equals(b))
										{
											result = true;
										}
										else
										{
											result = false;
											return result;
										}
										// if iterator b does not have a next
										// object
									}
									else
									{
										result = false;
										return false;
									}
								}

							}
							else
							{
								result = objAResult.equals(objBResult);
								if (result == false)
								{
									return result;
								}
							}
						}
					}
				}

			}
			else
			{
				// Generating way to many messages. Commenting them out for now.
				// log.error("Object A class does not equal Object B class:");
				// log.error("Object A class: " + objA.getClass().getName());
				// log.error("Object B class: " + objB.getClass().getName());
				result = false;
			}

		}
		catch (Exception e)
		{
			log.error("exception in Equalizer.check(): objA: "
					+ objA.getClass().getName() + " objB: " + objB.getClass().getName(),
					e);
			throw new Exception(e);
		}

		return result;
	}

	public static String examineObject(Object objA, Object objB) throws Exception
	{
		return examineObject(null, objA, objB, null, 0);
	}

	public static String examineObject(Object objA, Object objB, Map<?, ?> ignoreMap)
		throws Exception
	{
		return examineObject(null, objA, objB, ignoreMap, 0);
	}

	/**
	 * Provides the ability to specify an ignore list, which consists of keys which
	 * correspond to the name of the method to ignore. The value of the key is not used,
	 * and should just be null. For example: key="getOLock" value=null
	 * 
	 * @param objA
	 * @param objB
	 * @param ignoreMap
	 * @return boolean
	 * @throws Exception
	 */
	protected static String examineObject(final Object parent, final Object objA,
			final Object objB, final Map<?, ?> ignoreMap, final int lvl0)
		throws Exception
	{
		int lvl = lvl0;
		if (objA == null)
			throw new Exception("objA is null");
		if (objB == null)
			throw new Exception("objB is null");

		String tmp = null;

		if (objA.getClass().getName().equalsIgnoreCase(objB.getClass().getName()))
		{

			// First gather information about the objects in question. We are
			// assuming that they are bean-like in nature.
			// BeanInfo beanInfo = Introspector.getBeanInfo(objA.getClass(),
			// BaseBusinessObject.class);
			BeanInfo beanInfo = Introspector.getBeanInfo(objA.getClass());

			MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();

			MethodDescriptor methodDescriptor;
			Method method;
			Object objAResult;
			Object objBResult;
			Boolean result;
			StringBuffer sb = new StringBuffer();

			String title = CommonNames.MISC.EMPTY_STRING;
			if (parent != null)
			{
				title = getStem(parent.getClass()) + "." + getStem(objA.getClass())
						+ CommonNames.TREE.SEPARATOR;

			}
			else
			{
				title = getStem(objA.getClass()) + CommonNames.TREE.SEPARATOR;
			}
			sb.append(CommonNames.MISC.NEW_LINE_CHAR + indent(lvl) + title
					+ CommonNames.MISC.NEW_LINE_CHAR);
			// sb.append(indent(lvl) + getDashes(title) +
			// CommonNames.MISC.NEW_LINE_CHAR);

			// lvl++;
			if (parent != null)
			{
				lvl = lvl + getStem(parent.getClass()).length() + 1;
			}

			for (int i = 0; i < methodDescriptors.length; i++)
			{
				methodDescriptor = methodDescriptors[i];
				method = methodDescriptor.getMethod();

				// if method is a get/is method, and takes no parameters,
				// process the method
				if (((method.getName().startsWith("get")) || (method.getName()
						.startsWith("is")))
						&& (method.getParameterTypes().length == 0)
						&& (method.getName().equalsIgnoreCase("getClass") != true))
				{

					// if theres no ignore list, OR if there is an ignore list
					// and im not on the ignore list, process the method
					if ((ignoreMap == null) || !ignoreMap.containsKey(method.getName()))
					{

						objAResult = method.invoke(objA, (Object[]) null);
						objBResult = method.invoke(objB, (Object[]) null);

						sb.append("- " + method.getName() + "() ->");

						result = null;

						if ((objAResult == null) && (objBResult == null))
						{ // check
							// for
							// both
							// equal
							// Null
							result = new Boolean(true);
							tmp = indent(lvl)
									+ format(objA, method, result, objAResult, objBResult);
							log.debug(tmp);
							sb.append(tmp);

						}
						else if ((objAResult != null) && (objBResult == null))
						{ // check
							// A ==
							// null
							result = new Boolean(false);
							tmp = indent(lvl)
									+ format(objA, method, result, objAResult, objBResult);
							log.debug(tmp);
							sb.append(tmp);

						}
						else if ((objAResult == null) && (objBResult != null))
						{ // check
							// B ==
							// null
							result = new Boolean(false);
							tmp = indent(lvl)
									+ format(objA, method, result, objAResult, objBResult);
							log.debug(tmp);
							sb.append(tmp);

						}
						else if (
						// look if these are primitive type
						(objAResult instanceof String) || (objAResult instanceof Integer)
								|| (objAResult instanceof Double)
								|| (objAResult instanceof Boolean)
								|| (objAResult instanceof Long)
								|| (objAResult instanceof Byte)
								|| (objAResult instanceof Character)
								|| (objAResult instanceof Short)
								|| (objAResult instanceof Float)
								|| (objAResult instanceof Class))
						{
							// check condition b/c we are looking at a primitive

							result = new Boolean(objAResult.equals(objBResult));
							tmp = indent(lvl)
									+ format(objA, method, result, objAResult, objBResult);
							log.debug(tmp);
							sb.append(tmp);

						}
						else if ((objAResult instanceof Collection) && objBResult != null)
						{
							// handle looping into the resultant collection

							Iterator<?> iterA = ((Collection<?>) objAResult).iterator();
							Iterator<?> iterB = ((Collection<?>) objBResult).iterator();
							while (iterA.hasNext())
							{
								objAResult = iterA.next();

								if (iterB.hasNext())
								{
									objBResult = iterB.next();
									sb.append(EqualityUtil.examineObject(objA, objAResult,
											objBResult, ignoreMap, lvl));
								}
								else
								{
									log.debug("iterB.hasNext == false");
								}
							}

						}
						else
						{ // if not primitives & both are non-null,
							// recurse into equalizer again
							sb.append(EqualityUtil.examineObject(objA, objAResult,
									objBResult, ignoreMap, lvl));
						}
					}
				}
			}

			return sb.toString();
			// return CommonNames.MISC.EMPTY_STRING;

		}
		else
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
	}

	private static String format(Object parent, Method method, Boolean result,
			Object objA, Object objB)
	{

		if (result.booleanValue())
		{
			return getStem(parent.getClass()) + "." + formatMethodName(method) + " - "
					+ "EQUALS\n";
		}
		else
		{
			return getStem(parent.getClass()) + "." + formatMethodName(method) + " - "
					+ "** NOT EQUAL ** " + " (" + objA + "==" + objB + ")\n";
		}
	}

	private static String indent(int lvl)
	{
		String out = CommonNames.MISC.EMPTY_STRING;
		for (int i = 0; i < lvl; i++)
		{
			out += " ";
		}
		return out;
	}

	private static String formatMethodName(Method method)
	{
		String methodName = method.getName();
		if (methodName.startsWith("is"))
		{
			methodName = methodName.substring(2);
		}
		else if (methodName.startsWith("get"))
		{
			methodName = methodName.substring(3);
		}
		return methodName;
	}

	private static String getStem(Class<?> aClass)
	{
		String output = aClass.getName();
		int lastDot = output.lastIndexOf(".");
		output = output.substring(lastDot + 1);
		return output;
	}

	@SuppressWarnings("unused")
	private static String getDashes(String display)
	{
		int cnt = display.length();
		String out = CommonNames.MISC.EMPTY_STRING;
		for (int i = 0; i < cnt; i++)
		{
			out += "-";
		}
		return out;
	}

	/**
	 * No ignore list specified.
	 */
	public static boolean check(Object objA, Object objB) throws Exception
	{

		return check(objA, objB, null);
	}
}

/**
 * ...
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
 * @version Oct 8, 2007
 */
class Result
{
	public final String key; // the key

	public final Boolean result; // result

	public final Object lhs; // left hand side

	public final Object rhs; // right hand side

	public Result(String key, Boolean result, Object lhs, Object rhs)
	{
		this.key = key;
		this.result = result;
		this.lhs = lhs;
		this.rhs = rhs;
	}
}
