/*****************************************************************************************
 * Source File: ObjectUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * General class for handling object operations.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 10, 2007
 */
public class ObjectUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ObjectUtil.class);

	public static IgnoreMap stdIgnoreMap2 = new IgnoreMap();

	public static Map<String, Object> stdIgnoreMap = new HashMap<String, Object>();

	public static Map<String, Object> stdMethodPrefixes = new HashMap<String, Object>();

	public static Map<String, Object> stdIgnoreClassesMap = new HashMap<String, Object>();

	static
	{
		// list of methods to ignore
		stdIgnoreMap.put("getId", null);

		stdIgnoreMap2.addWildcard("getClass");
		stdIgnoreMap2.addWildcard("getOLock");
		stdIgnoreMap2.addWildcard("isDirty");
		stdIgnoreMap2.addWildcard("isEmpty");
		stdIgnoreMap2.addWildcard("isNew");
		stdIgnoreMap2.addWildcard("getNanos");
		stdIgnoreMap2.addWildcard("getTime");
		stdIgnoreMap2.addWildcard("getBaseObjectsCreated");
		stdIgnoreMap2.addWildcard("getAsEmployeeGroup");
		stdIgnoreMap2.addWildcard("getIgnoreMap");
		stdIgnoreMap2.addWildcard("getBytes");
		stdIgnoreMap2.addWildcard("getGuid");
	}

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ObjectUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Returns a list of attributes for the object
	 * 
	 * @param theObject
	 *            the object to examine
	 * @param ignoreMap
	 *            any attributes to ignore
	 * @return a list of the attribute values for a particular object
	 */
	public static List<Object> findAttributes(Object theObject,
			Map<String, Object> ignoreMap)
	{
		return findAttributes(theObject, ignoreMap, null);
	}

	/**
	 * Returns a list of attributes for the object
	 * 
	 * @param theObject
	 *            the object to examine
	 * @param ignoreMap
	 *            any attributes to ignore
	 * @param edgeClass
	 *            the edge class to stop at - methods that don't return a sub-class of
	 *            this class will not be examined
	 * @return a list of the attribute values for a particular object
	 */
	public static List<Object> findAttributes(Object theObject,
			Map<String, Object> ignoreMap, Class<?> edgeClass)
	{
		return findAttributes(theObject, ignoreMap, edgeClass,
				new HashMap<String, Object>());
	}

	/**
	 * Returns a list of attributes for the object
	 * 
	 * @param theObject
	 *            the object to examine
	 * @param ignoreMap
	 *            any attributes to ignore
	 * @param edgeClass
	 *            the edge class to stop at - methods that don't return a sub-class of
	 *            this class will not be examined
	 * @param ignoreClasses
	 *            methods that return classes in the ignoreClasses Map will not be
	 *            examined (i.e. Address, Phone, etc...) - a null map is treated as an
	 *            empty map
	 * @return a list of the attribute values for a particular object
	 */
	public static List<Object> findAttributes(Object theObject,
			Map<String, Object> ignoreMap, Class<?> edgeClass,
			Map<String, Object> ignoreClasses)
	{
		return new ArrayList<Object>(mapAttributes(theObject, ignoreMap, edgeClass,
				ignoreClasses).values());
	}

	// Map attributes....
	/**
	 * Returns a Map of attributes for the object
	 * 
	 * @param theObject
	 *            the object to examine
	 * @param ignoreMap
	 *            any attributes to ignore
	 * @return a list of the attribute values for a particular object
	 */
	public static Map<String, Object> mapAttributes(Object theObject,
			Map<String, Object> ignoreMap)
	{
		return mapAttributes(theObject, ignoreMap, null);
	}

	/**
	 * Returns a Map of attributes for the object
	 * 
	 * @param theObject
	 *            the object to examine
	 * @param ignoreMap
	 *            any attributes to ignore
	 * @param edgeClass
	 *            the edge class to stop at - methods that don't return a sub-class of
	 *            this class will not be examined
	 * @return a list of the attribute values for a particular object
	 */
	public static Map<String, Object> mapAttributes(Object theObject,
			Map<String, Object> ignoreMap, Class<?> edgeClass)
	{
		return mapAttributes(theObject, ignoreMap, edgeClass,
				new HashMap<String, Object>());
	}

	/**
	 * Returns a map of attributes for the object. The key of the Map is the getter name
	 * ("getFoo") and the value in the return value of the method (if any)
	 * 
	 * @param theObject
	 *            the object to examine
	 * @param ignoreMap
	 *            any attributes to ignore
	 * @param edgeClass
	 *            the edge class to stop at - methods that don't return a sub-class of
	 *            this class will not be examined
	 * @param ignoreClasses
	 *            methods that return classes in the ignoreClasses Map will not be
	 *            examined (i.e. Address, Phone, etc...) - a null map is treated as an
	 *            empty map
	 * @return a list of the attribute values for a particular object
	 */
	public static Map<String, Object> mapAttributes(final Object theObject,
			final Map<String, Object> ignoreMap, final Class<?> edgeClass0,
			final Map<String, Object> ignoreClasses0)
	{
		// if there isn't an edge class passed in, assume edge class to be
		// Object.class
		Class<?> edgeClass = edgeClass0;
		Map<String, Object> ignoreClasses = ignoreClasses0;
		if (edgeClass == null)
		{
			edgeClass = Object.class;
		}

		// if the ignoreClasses map is null, make an empty HashMap();
		if (ignoreClasses == null)
		{
			ignoreClasses = new HashMap<String, Object>();
		}

		// list for the results
		Map<String, Object> results = new HashMap<String, Object>();

		// discover the methods
		Method[] methods = theObject.getClass().getMethods();

		// look through each method and invoke. If it matched our criteria, add
		// to results
		for (int i = 0; i < methods.length; i++)
		{
			Method method = methods[i];
			if ((method.getName().startsWith("get"))
					&& (method.getParameterTypes().length == 0)
					&& (stdIgnoreMap.containsKey(method.getName()) == false))
			{ // stdIgnoreMap2.isIgnore(theObject,
				// method)

				Class<?> returnClass = method.getReturnType();

				// check that the return class isn't on the list of classes to
				// ignore
				if (!ignoreClasses.containsKey(returnClass))
				{

					// check that the method will return what we want first or
					// that we are looking at a cllection
					if (edgeClass.isAssignableFrom(returnClass)
							|| Collection.class.isAssignableFrom(returnClass))
					{
						Object result = invoke(method, theObject);
						// If we have a valid result add the results
						if (result != null)
						{
							results.put(method.getName(), result);
						}
					}
				}
			}
		}

		return results;
	}

	private static Object invoke(Method method, Object object)
	{
		try
		{
			// log.debug("Invoking Method: " + method.getName());
			Object result = method.invoke(object, (Object[]) null);
			return result;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static class IgnoreMap
	{
		HashMap<String, Object> ignores = new HashMap<String, Object>();

		public void add(Class<?> clazz, String method)
		{
			ignores.put(clazz.getName() + "::" + method, null);
		}

		public void addWildcard(String method)
		{
			ignores.put("WILDCARD" + "::" + method, null);
		}

		public boolean isIgnore(Object object, Method method)
		{
			return isIgnore(object.getClass(), method);
		}

		public boolean isIgnore(Class<?> clazz, Method method)
		{
			if (ignores.containsKey("WILDCARD" + "::" + method.getName()))
				return true;

			if (ignores.containsKey(clazz.getName() + "::" + method.getName()))
				return true;

			return false;
		}
	}
}
