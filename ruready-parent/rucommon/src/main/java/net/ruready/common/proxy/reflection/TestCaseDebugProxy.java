/*****************************************************************************************
 * Source File: DebugProxy.java
 ****************************************************************************************/

package net.ruready.common.proxy.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A dynamic proxy that prints out a message before and after a JUnit test method
 * invocation on an object that implements an arbitrary list of interfaces.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 30, 2007
 * @see http://java.sun.com/j2se/1.5.0/docs/guide/reflection/proxy.html
 */
public class TestCaseDebugProxy implements InvocationHandler
//TODO: make this class generic
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestCaseDebugProxy.class);

	// ========================= FIELDS ==============================

	/**
	 * The object to be wrapped.
	 */
	private Object obj;

	// ========================= CONSTRUCTORS ========================

	/**
	 * @param obj
	 * @return
	 */
	public static Object newInstance(Object obj)
	{
		return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				obj.getClass().getInterfaces(), new TestCaseDebugProxy(obj));
	}

	/**
	 * Wrap an object.
	 * 
	 * @param obj
	 *            the object to be wrapped
	 */
	private TestCaseDebugProxy(Object obj)
	{
		this.obj = obj;
	}

	// ========================= IMPLEMENTATION: InvocationHandler ===

	/**
	 * Print out a message before and after a method invocation on an object that
	 * implements an arbitrary list of interfaces.
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable
	{
		Object result;
		boolean isTestMethod = m.getName().startsWith("test");
		try
		{
			logger.debug(m.getName() + "() obj "  + obj + " args " + args);
			if (isTestMethod && logger.isDebugEnabled())
			{
				logger.debug(m.getName() + "() begin");
			}
			result = m.invoke(obj, args);
		}
		catch (InvocationTargetException e)
		{
			throw e.getTargetException();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Unexpected invocation exception: "
					+ e.getMessage());
		}
		finally
		{
			if (isTestMethod && logger.isDebugEnabled())
			{
				logger.debug(m.getName() + "() end");
			}
		}
		return result;
	}
}
