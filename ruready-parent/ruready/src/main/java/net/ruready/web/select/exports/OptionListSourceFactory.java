/*****************************************************************************************
 * Source File: OptionListSourceFactory.java
 ****************************************************************************************/
package net.ruready.web.select.exports;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.select.entity.ArraysOptionListSource;
import net.ruready.web.select.entity.EnumListOptionListSource;
import net.ruready.web.select.entity.EnumMapOptionListSource;
import net.ruready.web.select.entity.EnumOptionListSource;
import net.ruready.web.select.entity.ListsOptionListSource;
import net.ruready.web.select.entity.PropertiesOptionListSource;
import net.ruready.web.select.entity.SimpleOptionListSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A factory that produces OptionListSource objects.
 * 
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
 * @version Nov 8, 2007
 */
public class OptionListSourceFactory
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(OptionListSourceFactory.class);

	private static Properties config = getConfigProperties();

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	private OptionListSourceFactory()
	{

	}

	/**
	 * Returns a fully configured OptionListSource object based on the
	 * configuration parameters in effect.
	 * 
	 * @param name
	 *            the identifier of the specific OptionListSource
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	public static OptionListSource getOptionListSource(String name)
	{
		OptionListSourceFactory factory = new OptionListSourceFactory();
		return factory.getOptListSource(name);
	}

	/**
	 * Returns a fully configured OptionListSource object based a list of
	 * arguments.
	 * 
	 * @param type
	 *            type of OL source
	 * @param object
	 *            a variable argument list. Will be used differently depending
	 *            on <code>type</code>
	 * @return a fully configured OptionListSource object
	 */
	public static OptionListSource getOptionListSource(final String type,
			final Object... object)
	{
		OptionListSourceFactory factory = new OptionListSourceFactory();
		return factory.getOptListSource(type, object);
	}

	// ========================= METHODS ===================================

	/**
	 * This is just a quick way to get some configuration properties to the
	 * factory. Actual implementations of a factory would use their own approach
	 * here. The properties file name is hard-coded in the <code>Names</code>
	 * convention class.
	 * 
	 * @return the configuration parameters in effect
	 */
	private static Properties getConfigProperties()
	{
		Properties configProps = new Properties();

		String name = WebAppNames.OLS.FACTORY_CONFIG_FILE;
		InputStream is = null;

		// Load the specified property resource
		try
		{
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			is = classLoader.getResourceAsStream(name);
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
	 * Returns a fully configured OptionListSource object based on the
	 * configuration parameters in effect.
	 * 
	 * @param name
	 *            the identifier of the specific OptionListSource
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	public OptionListSource getOptListSource(String name)
	{
		OptionListSource factoryProduct = null;

		String typeProperty = name + WebAppNames.OLS.PROPERTY_PREFIX + ".type";
		String type = config.getProperty(typeProperty);

		if (type == null)
		{
			logger.warn("Requested OptionListSource ('" + name
					+ "') not configured.");
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_SIMPLE))
		{
			factoryProduct = getSimpleOptionListSource(name);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_PROPERTIES))
		{
			factoryProduct = getPropertiesOptionListSource(name);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_ENUM))
		{
			factoryProduct = getEnumOptionListSource(name);
		}
		else
		{
			logger.warn("Configured OptionListSource type ('" + type
					+ "') not supported.");
		}

		return factoryProduct;
	}

	/**
	 * Returns a SimpleOptionListSource object based on the configuration
	 * parameters in effect.
	 * 
	 * @param name
	 *            the identifier of the specific OptionListSource
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	private OptionListSource getSimpleOptionListSource(String name)
	{
		String stringProperty = name + WebAppNames.OLS.PROPERTY_PREFIX
				+ ".string";
		String configString = config.getProperty(stringProperty);

		return new SimpleOptionListSource(configString);
	}

	/**
	 * Returns a PropertiesOptionListSource object based on the configuration
	 * parameters in effect.
	 * 
	 * @param name
	 *            the identifier of the specific OptionListSource
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	private OptionListSource getPropertiesOptionListSource(String name)
	{
		String fileNameProperty = name + WebAppNames.OLS.PROPERTY_PREFIX
				+ ".filename";
		String fileName = config.getProperty(fileNameProperty);

		return new PropertiesOptionListSource(fileName);
	}

	/**
	 * Returns a EnumOptionListSource object based on the configuration
	 * parameters in effect.
	 * 
	 * @param name
	 *            the identifier of the specific OptionListSource
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	private OptionListSource getEnumOptionListSource(String name)
	{
		/*
		 * String typeProperty = name + WebAppNames.OLS.PROPERTY_PREFIX +
		 * ".class"; String typeString = config.getProperty(typeProperty);
		 * return new EnumOptionListSource(typeString);
		 */
		return new EnumOptionListSource(name);
	}

	/**
	 * Returns a EnumMapOptionListSource object based on the configuration
	 * parameters in effect.
	 * 
	 * @param name
	 *            the identifier of the specific OptionListSource
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	private OptionListSource getEnumMapOptionListSource(String className,
			Map<String, String> map)
	{
		return new EnumMapOptionListSource(className, map);
	}

	/**
	 * Returns a EnumListOptionListSource object based on the configuration
	 * parameters in effect.
	 * 
	 * @param enumList
	 *            input list of enumerated types
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	private OptionListSource getEnumListOptionListSource(List<Enum<?>> enumList)
	{
		return new EnumListOptionListSource(enumList);
	}

	/**
	 * Returns a ArraysOptionListSource object based on the configuration
	 * parameters in effect.
	 * 
	 * @param labels
	 *            list of labels
	 * @param values
	 *            list of corresponding values
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	private OptionListSource getArraysOptionListSource(Object[] labels,
			Object[] values)
	{
		return new ArraysOptionListSource(labels, values);
	}

	/**
	 * Returns a ListsOptionListSource object based on the configuration
	 * parameters in effect.
	 * 
	 * @param labels
	 *            list of labels
	 * @param values
	 *            list of corresponding values
	 * @return a fully configured OptionListSource object based on the
	 *         configuration parameters in effect
	 */
	private OptionListSource getListsOptionListSource(final List<?> labels,
			final List<?> values)
	{
		return new ListsOptionListSource(labels, values);
	}

	/**
	 * Returns a fully configured OptionListSource object based on based a list
	 * of arguments.
	 * 
	 * @param type
	 *            type of OL source
	 * @param object
	 *            a variable argument list. Will be used differently depending
	 *            on <code>type</code>
	 * @return a fully configured OptionListSource object
	 */
	@SuppressWarnings("unchecked")
	public OptionListSource getOptListSource(final String type,
			final Object... object)
	{
		OptionListSource factoryProduct = null;
		if (type == null)
		{
			logger.warn("Requested OptionListSource ('" + type
					+ "') not supported.");
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_SIMPLE))
		{
			factoryProduct = getSimpleOptionListSource((String) object[0]);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_PROPERTIES))
		{
			factoryProduct = getPropertiesOptionListSource((String) object[0]);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_ENUM))
		{
			factoryProduct = getEnumOptionListSource((String) object[0]);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_ENUM_MAP))
		{
			factoryProduct = getEnumMapOptionListSource((String) object[0],
					(Map<String, String>) object[1]);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_ENUM_LIST))
		{
			factoryProduct = getEnumListOptionListSource((List<Enum<?>>) object[0]);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_ARRAYS))
		{
			factoryProduct = getArraysOptionListSource((Object[]) object[0],
					(Object[]) object[1]);
		}
		else if (type.equalsIgnoreCase(WebAppNames.OLS.TYPE_LISTS))
		{
			factoryProduct = getListsOptionListSource((List<?>) object[0],
					(List<?>) object[1]);
		}
		else
		{
			logger.warn("Configured OptionListSource type ('" + type
					+ "') not supported.");
		}

		return factoryProduct;
	}
}
