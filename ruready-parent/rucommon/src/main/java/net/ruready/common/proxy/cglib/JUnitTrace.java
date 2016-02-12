package net.ruready.common.proxy.cglib;

import junit.framework.TestCase;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A dynamic proxy that traces method signatures when they are called on a
 * wrapped Junit {@link TestCase} object.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author baliuka
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 31, 2007
 */
public class JUnitTrace implements MethodInterceptor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(JUnitTrace.class);

	// ========================= FIELDS ==============================

	// ========================= FIELDS ==============================

	private static JUnitTrace callback = new JUnitTrace();

	// ========================= CONSTRUCTORS ========================

	/**
	 * Creates a new instance of a tracer.
	 */
	private JUnitTrace()
	{

	}

	/**
	 * Wrap an object with a tracer.
	 * 
	 * @param clazz
	 *            object type
	 * @return dynamic proxy that traces the type's methods
	 */
	public static TestCase newInstance(final Class<? extends TestCase> clazz)
	{
		try
		{
			Enhancer e = new Enhancer();
			e.setSuperclass(clazz);
			e.setCallback(callback);
			return (TestCase) e.create();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			throw new Error(e.getMessage());
		}

	}

	/**
	 * @param obj
	 * @param method
	 * @param args
	 * @param proxy
	 * @return
	 * @throws Throwable
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[],
	 *      net.sf.cglib.proxy.MethodProxy)
	 */
	public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
			MethodProxy proxy) throws Throwable
	{
		boolean isTestMethod = method.getName().startsWith("test");
		if (logger.isDebugEnabled())
		{
			if (isTestMethod)
			{
				logger.debug("=====================================================");
				logger.debug(method.getName());
				logger.debug("=====================================================");
			}
			else if (!"finalize".equals(method.getName()))
			{
				// Ignore finalize() in printouts
				logger.debug(method.getName() + "()");
			}
		}

		Object retValFromSuper = null;
		try
		{
			retValFromSuper = proxy.invokeSuper(obj, args);
		}
		catch (Throwable t)
		{
			throw t.fillInStackTrace();
		}

		return retValFromSuper;
	}
}
