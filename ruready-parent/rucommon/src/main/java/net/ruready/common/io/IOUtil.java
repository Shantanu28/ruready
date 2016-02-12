/*****************************************************************************************
 * Source File: IOUtil.java
 ****************************************************************************************/
package net.ruready.common.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * I/O and classpath-related utilities.
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
 * @version Aug 23, 2007
 */
public class IOUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(IOUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private IOUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Returns the list of files in a directory.
	 * 
	 * @param directoryName
	 *            directory name relative to the path where the JVM was started
	 * @return list of children files of the directory
	 */
	public static String[] directoryListing(String directoryName)
	{
		File dir = new File(".");
		return dir.list();
	}

	/**
	 * Deletes all files and sub-directories under dir. Returns true if all deletions were
	 * successful. If a deletion fails, the method stops attempting to delete and returns
	 * false.
	 */
	public static boolean deleteDir(File dir)
	{
		if (dir.isDirectory())
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	/**
	 * Return a list of jar files in the classpath of this JVM, by their order of
	 * appearance in the classpath.
	 * 
	 * @return list of jar files in the classpath of this JVM, by their order of
	 *         appearance in the classpath
	 */
	public static List<String> getTokenizedClassPath()
	{
		StringTokenizer tokenizer = new StringTokenizer(System
				.getProperty("java.class.path"), System.getProperty("path.separator"));
		List<String> jars = new ArrayList<String>();
		while (tokenizer.hasMoreTokens())
		{
			jars.add(tokenizer.nextToken());
		}

		tokenizer = new StringTokenizer(System.getProperty("java.library.path"), System
				.getProperty("path.separator"));
		while (tokenizer.hasMoreTokens())
		{
			jars.add(tokenizer.nextToken());
		}

		tokenizer = new StringTokenizer(System.getProperty("java.ext.dirs"), System
				.getProperty("path.separator"));
		while (tokenizer.hasMoreTokens())
		{
			jars.add(tokenizer.nextToken());
		}

		return jars;
	}

	/**
	 * Print a list of jar files in the classpath of this JVM, by their order of
	 * appearance in the classpath.
	 */
	public static void printTokenizedClassPath()
	{
		List<String> jars = IOUtil.getTokenizedClassPath();
		logger
				.debug("####################### CLASSPATH jars START #######################");
		for (String jar : jars)
		{
			logger.debug(jar);
		}
		logger
				.debug("####################### CLASSPATH jars END   #######################");
	}

	/**
	 * Return a string with the list of jar files in the classpath of this JVM, by their
	 * order of appearance in the classpath.
	 * 
	 * @return a string with the list of jar files in the classpath of this JVM, by their
	 *         order of appearance in the classpath.
	 */
	public static String toStringTokenizedClassPath()
	{
		List<String> jars = IOUtil.getTokenizedClassPath();
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append("####################### CLASSPATH jars START #######################")
				.append(CommonNames.MISC.NEW_LINE_CHAR);
		for (String jar : jars)
		{
			s.append(jar).append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		s.append("####################### CLASSPATH jars END   #######################")
				.append(CommonNames.MISC.NEW_LINE_CHAR);
		return s.toString();
	}
}
