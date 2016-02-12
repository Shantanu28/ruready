/*****************************************************************************************
 * Source File: ExceptionUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.ruready.common.misc.Utility;

/**
 * Provides flexible exception printouts to strings.
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
 * @version Aug 11, 2007
 */
public class ExceptionUtil implements Utility
{
	// ========================= CONSTANTS =================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private ExceptionUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Gets the exception stack trace as a string.
	 * 
	 * @param exception
	 * @return
	 */
	public static String getStackTraceAsString(Exception exception)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.print(" [ ");
		pw.print(exception.getClass().getName());
		pw.print(" ] ");
		pw.print(exception.getMessage());
		pw.print(" ");
		exception.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * Gets the exception stack trace as a string.
	 * 
	 * @param exception
	 * @return
	 */
	public static String getStackTraceAsString(Error error)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.print(" [ ");
		pw.print(error.getClass().getName());
		pw.print(" ] ");
		pw.print(error.getMessage());
		pw.print(" ");
		error.printStackTrace(pw);
		return sw.toString();
	}
}
