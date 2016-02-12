package net.ruready.common.io;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Executing a command and capturing its output on a windows platform.
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
public class CmdExecWindows extends CmdExec
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CmdExecWindows.class);

	// ========================= FIELDS ====================================

	/**
	 * Run a command line on windows.
	 * 
	 * @param cmdline
	 *            command line
	 * @param outFile
	 *            output to this file the command's output. If empty, dump to
	 *            the screen.
	 */
	public CmdExecWindows(String cmdline, String outFile)
	{
		try
		{
			String line;
			Process p = Runtime.getRuntime().exec(cmdline);
			// p.waitFor();
			BufferedReader input = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			if (outFile.trim().equals(""))
			{
				while ((line = input.readLine()) != null)
				{
					logger.debug(line);
				}
			}
			else
			{
				FileWriter writer = new FileWriter(outFile, false);
				while ((line = input.readLine()) != null)
				{
					writer.write(line + "\n");
				}
				writer.close();
			}
			input.close();
		}
		catch (Exception err)
		{
			err.printStackTrace();
		}
	}

	/* ================ TESTING ===================== */
	public static void main(String[] args)
	{
		new CmdExecWindows("mysqldump", "");
		// new CmdExecWindows("mysqldump -u user -pppp ruready >
		// ~/...../ruready_date.sql");
	}
}
