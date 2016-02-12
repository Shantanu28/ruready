package net.ruready.common.demo;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import static java.lang.System.out;

/**
 * A method declaration includes the name, modifiers, parameters, return type, and list of
 * throwable exceptions. The java.lang.reflect.Method class provides a way to obtain this
 * information.
 * <p>
 * The {@link MethodSpy} example illustrates how to enumerate all of the declared methods
 * in a given class and retrieve the return, parameter, and exception types for all the
 * methods of the given name.
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
 * @version Jul 16, 2007
 */
public class MethodSpy
{
	private static final String fmt = "%24s: %s%n";

	/**
	 * For the morbidly curious...
	 * 
	 * @param <E>
	 * @throws E
	 */
	<E extends RuntimeException> void genericThrow() throws E
	{

	}

	public static void main(String... args)
	{
		try
		{
			Class<?> c = Class.forName(args[0]);
			Method[] allMethods = c.getDeclaredMethods();
			for (Method m : allMethods)
			{
				if (!m.getName().equals(args[1]))
				{
					continue;
				}
				out.format("%s%n", m.toGenericString());

				out.format(fmt, "ReturnType", m.getReturnType());
				out.format(fmt, "GenericReturnType", m.getGenericReturnType());

				Class<?>[] pType = m.getParameterTypes();
				Type[] gpType = m.getGenericParameterTypes();
				for (int i = 0; i < pType.length; i++)
				{
					out.format(fmt, "ParameterType", pType[i]);
					out.format(fmt, "GenericParameterType", gpType[i]);
				}

				Class<?>[] xType = m.getExceptionTypes();
				Type[] gxType = m.getGenericExceptionTypes();
				for (int i = 0; i < xType.length; i++)
				{
					out.format(fmt, "ExceptionType", xType[i]);
					out.format(fmt, "GenericExceptionType", gxType[i]);
				}
			}

			// production code should handle these exceptions more gracefully
		}
		catch (ClassNotFoundException x)
		{
			x.printStackTrace();
		}
	}
}
