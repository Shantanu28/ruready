/*****************************************************************************************
 * Source File: TestCommonsCollections.java
 ****************************************************************************************/
package test.ruready.web.util;

import java.util.HashSet;
import java.util.Set;

import net.ruready.business.content.trash.manager.TrashCleaner;

import org.apache.commons.collections.SetUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.quartz.JobDetail;

import test.ruready.common.rl.TestBase;

/**
 * Tests related to using the Apache commons-collections library.
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
 * @version Sep 26, 2007
 */
public class TestCommonsCollections extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestCommonsCollections.class);

	// ========================= TESTING METHODS ===========================

	/**
	 * Test {@link SetUtils} calls that cause the Quartz 1.6.0 scheduler to
	 * crash while initializing.
	 */
	@Test
	public void testSetUtils()
	{
		// The following call appears in JobDetail.java. For this class to even
		// compile, checkstyle-all.jar must come after the
		// commons-collections-3.1+.jar and quartz-1.6.0+.jar in the webapp
		// project's classpath.
		// Use eclipse's build path -> order and export list to specify the
		// order of jars.
		//
		// This class also serves a warning sign: if it doesn't compile,
		// we know there will be problems with the Quartz scheduler because
		// it won't find the following method at run time.
		Set<?> jobListeners = SetUtils.orderedSet(new HashSet<Object>());
		logger.info("jobListeners = " + jobListeners);

		// The actual Quartz call
		JobDetail job = new JobDetail("job1", "group1", TrashCleaner.class);
		logger.info("job = " + job);
	}
}
