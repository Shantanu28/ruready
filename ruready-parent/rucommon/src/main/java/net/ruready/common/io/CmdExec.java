package net.ruready.common.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Executing a system command (platform-independent) and capturing its output.
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
 * @version Nov 14, 2007
 */
public abstract class CmdExec
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CmdExec.class);

	// ========================= FIELDS ====================================

	// ========================= METHODS ===================================

	/**
	 * Run a command line and determine the platform (windows/unix) that we're
	 * running on.
	 * 
	 * @param cmdline
	 *            command line
	 * @param outFile
	 *            output to this file the command's output. If empty, dump to
	 *            the screen.
	 */
	public static CmdExec newType(String cmdline, String outFile)
	{
		CmdExec c = null;
		String os = System.getProperty("os.arch");
		logger.debug("Executing command line: " + cmdline);
		if (os.equals("x86"))
		{
			// Windows
			c = new CmdExecWindows(cmdline, outFile);
		}
		else
		{
			// Unix/linux
			c = new CmdExecUnix(cmdline, outFile);
		}
		return c;
	}

	/* ================ TESTING ===================== */
	public static void main(String[] args)
	{
		CmdExec
				.newType("mysqldump -h localhost -u ruready -pruready ruready",
						"c:\\cygwin\\home\\Nava Livne\\Projects\\Java\\workspace\\ru1\\WEB-INF\\data\\temp2");
		// CmdExec.newType("mysqldump -u ruready -pruready ruready");
		logger.debug("Done");
	}
}
