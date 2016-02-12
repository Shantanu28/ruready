package test.ruready.common.proxy.reflection;

import junit.framework.TestResult;
import junit.framework.TestSuite;
import net.ruready.common.audit.Versioned;
import net.ruready.common.proxy.reflection.DebugProxy;
import net.ruready.common.proxy.reflection.TestCaseDebugProxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;
import test.ruready.common.text.TestStrings;

/**
 * Test dynamic proxies using the Java reflection API.
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
public class TestProxy extends TestBase
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
	 * Test some proxies.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testProxies()
	{
		Versioned<Integer> foo = (Versioned<Integer>) DebugProxy
				.newInstance(new VersionedImpl());
		logger.debug(foo.getVersion());

		junit.framework.Test bar = (junit.framework.Test) DebugProxy
				.newInstance(new JUnitTestImpl());
		bar.run(new TestResult());

		junit.framework.Test bar2 = (junit.framework.Test) TestCaseDebugProxy
				.newInstance(new JUnitTestCaseImpl());
		bar2.run(new TestResult());

		TestSuite suite = new TestSuite("Some test case");
		junit.framework.Test proxy = (junit.framework.Test) TestCaseDebugProxy
				.newInstance(new JUnitTestCaseImpl());
		suite.addTest(proxy);
		logger.debug("suite " + suite);
		suite.run(new TestResult());
	}

	// ========================= TESTING ====================================
}
