/*****************************************************************************************
 * Source File: TestMunkres.java
 ****************************************************************************************/
package net.ruready.common.demo;

import net.ruready.common.eis.ddl.SimpleDDLFileFixer;
import net.ruready.common.rl.Environment;
import net.ruready.common.rl.MinimalEnvironment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Runs a Hibernate tool DDL file format fix.
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
 * @version Jul 24, 2007
 */
public class RunSimpleDDLFixer
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RunSimpleDDLFixer.class);

	// ========================= FIELDS ====================================

	// ========================= PUBLIC METHODS ============================

	/**
	 * Runs a Hibernate tool DDL file format fix.
	 * 
	 * @param args
	 *            (inputFileName, outputFileName)
	 */
	public static void main(String[] args)
	{
		if (args.length != 3)
		{
			System.err
					.println("Usage: RunDDLFixer <inputFileName> <outputFileName> <databaseName>");
			return;
		}

		// An environment with a resource locator
		Environment environment = new MinimalEnvironment();
		environment.setUp();

		SimpleDDLFileFixer t = new SimpleDDLFileFixer(args[0], args[1], args[2]);
		t.run();

		environment.tearDown();
	}
}
