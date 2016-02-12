/*****************************************************************************************
 * Source File: PropertiesUtil.java
 ****************************************************************************************/
package net.ruready.common.util;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides some extra functionality for Java Properties objects.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class PropertiesUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PropertiesUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private PropertiesUtil()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * This is just a quick way to get some configuration properties to the resource
	 * locator. Sub-classes would use their own approach here.
	 * 
	 * @param configFileName
	 *            properties file name to be searched for in the class path
	 * @return the configuration parameters in effect
	 */
	public static Properties loadConfigFile(final String configFileName)
	{
		Properties configProps = new Properties();

		InputStream is = null;

		// Load the specified property resource
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			is = classLoader.getResourceAsStream(configFileName);
			if (is != null)
			{
				configProps.load(is);
				is.close();
			}
		}
		catch (Throwable t)
		{
			logger.error("load properties error: " + t.toString());
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (Throwable t)
				{
					logger.error("load properties error: " + t.toString());
				}
			}
		}

		return configProps;
	}

	/**
	 * Returns the sub-map of a properties object that contains only keys that start with
	 * a certain prefix. If the prefix is <code>p</code>, all keys that start with
	 * <code>p.</code> are matched.
	 * 
	 * @param props
	 *            Original properties
	 * @param prefix
	 *            Key prefix to match. A null prefix is treated like an empty prefix.
	 * @return the corresponding properties submap. Prefix is removed from keys.
	 */
	public static Properties subMap(final Properties props0, final String prefix0)
	{
		Properties props = props0;
		String prefix = prefix0;
		if (props == null)
		{
			return null;
		}
		if (prefix == null)
		{
			prefix = CommonNames.MISC.EMPTY_STRING;
		}
		prefix = prefix + ".";
		int prefixLength = prefix.length();
		Properties subProps = new Properties();
		for (Map.Entry<Object, Object> entry : props.entrySet())
		{
			String key = (String) entry.getKey();
			if (key.startsWith(prefix))
			{
				String newKey = key.substring(prefixLength);
				if (newKey.length() > 0)
				{
					subProps.put(newKey, entry.getValue());
				}
			}
		}
		return subProps;
	}
}
