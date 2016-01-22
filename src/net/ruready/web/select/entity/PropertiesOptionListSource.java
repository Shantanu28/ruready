/*****************************************************************************************
 * Source File: PropertiesOptionListSource.java
 ****************************************************************************************/
package net.ruready.web.select.entity;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Returns the name/value pairs for the options related to an HTML "select" tag. Source
 * data for this implementation is obtained from a Java <code>Properties</code> object.
 * You can instantiate this class by providing the Properties object directly, or by
 * providing the fully qualified name of a text resource from which to load a new
 * Properties object.
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
 * @version Aug 3, 2007
 */
public class PropertiesOptionListSource extends DefaultOptionListSource
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PropertiesOptionListSource.class);

	// ========================= FIELDS ====================================

	private Properties props = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a new "PropertiesOptionListSource" object using the parameters provided.
	 * 
	 * @param fileName
	 *            a String containing the fully qualified file name of the text resource
	 *            from which to load a new Properties object
	 */
	public PropertiesOptionListSource(String fileName)
	{
		this(getProperties(fileName));
	}

	/**
	 * Constructs a new "PropertiesOptionListSource" object using the parameters provided.
	 * 
	 * @param props
	 *            the Java <code>Properties</code> object from which we will obtain our
	 *            labels and values
	 */
	public PropertiesOptionListSource(Properties props)
	{
		this.props = props;
	}

	// ========================= IMPLEMENTATION: OptionListSource ==========

	/**
	 * Loads the list of available options from the <code>Properties</code> object.
	 */
	@Override
	protected void loadOptions()
	{
		OptionList list = new OptionList();

		Enumeration<?> e = props.propertyNames();
		while (e.hasMoreElements())
		{
			String value = e.nextElement().toString();
			String label = props.getProperty(value);
			Option thisOption = new Option(label, value);
			list.add(thisOption);
		}

		setOptions(list);
	}

	// ========================= METHODS ===================================

	/**
	 * Creates a new <code>Properties</code> object and load it from the named resource.
	 * 
	 * @param fileName
	 *            a String containing the fully qualified file name of the text file from
	 *            which to load the Properties object
	 * @return the loaded <code>Properties</code> object
	 */
	private static Properties getProperties(String fileName)
	{
		Properties props = new Properties();
		InputStream is = null;

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			is = classLoader.getResourceAsStream(fileName);
			// is = new FileInputStream(fileName);
			props.load(is);
			is.close();
			is = null;
		}
		catch (Throwable t)
		{
			logger.error(" load Properties error: " + t.toString());
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
					is = null;
				}
				catch (Throwable t)
				{
					logger.error(" load Properties error: " + t.toString());
				}
			}
		}

		return props;
	}

}
