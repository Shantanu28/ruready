package test.ruready.common.proxy.cglib;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.proxy.cglib.JUnitTrace;
import net.ruready.common.proxy.cglib.Trace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.proxy.reflection.JUnitTestCaseImpl;
import test.ruready.common.rl.TestBase;
import test.ruready.common.text.TestStrings;

/**
 * Test the {@link Trace} class that uses CGLIB to trace method signatures.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 31, 2007
 */
public class TestTrace extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestStrings.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test tracing {@link ArrayList} object methods.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testTraceList()
	{
		List<Object> list = (List<Object>) Trace.newInstance(ArrayList.class);
		Object value = "TEST";
		list.add(value);
		list.contains(value);
		try
		{
			list.set(2, "ArrayIndexOutOfBounds");
		}
		catch (ArrayIndexOutOfBoundsException ignore)
		{

		}
		catch (IndexOutOfBoundsException ignore)
		{

		}
		list.add(value + "1");
		list.add(value + "2");
		list.toString();
		list.equals(list);
		list.set(0, null);
		list.toString();
		list.add(list);
		list.get(1);
		list.toArray();
		list.remove(list);
		list.remove("");
		list.containsAll(list);
		list.lastIndexOf(value);
	}

	/**
	 * Test tracing a JUnit test case.
	 */
	@Test
	public void testJUnitTrace()
	{
		JUnitTestCaseImpl test = (JUnitTestCaseImpl) JUnitTrace
				.newInstance(JUnitTestCaseImpl.class);
		test.run();
	}

	// ========================= TESTING ====================================
}
