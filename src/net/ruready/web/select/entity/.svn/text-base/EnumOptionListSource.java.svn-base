/*****************************************************************************************
 * Source File: EnumOptionListSource.java
 ****************************************************************************************/
package net.ruready.web.select.entity;

import net.ruready.common.exception.ApplicationException;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Returns the name/value pairs for the options related to an HTML "select" tag. Source
 * data for this implementation is obtained directly from the option list string provided.
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
public class EnumOptionListSource extends DefaultOptionListSource
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EnumOptionListSource.class);

	// ========================= FIELDS ====================================

	/**
	 * Class of the enumerated type to draw options from.
	 */
	private final Class<?> enumClass;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a new "EnumOptionListSource" object using the parameters provided.
	 * 
	 * @param enumClassName
	 *            name of enumerated type to draw options from
	 */
	public EnumOptionListSource(final String enumClassName)
	{
		Class<?> myEnumClass = null;
		try
		{
			myEnumClass = Class.forName(enumClassName);
			if (!myEnumClass.isEnum())
			{
				throw new ApplicationException("Class " + enumClassName
						+ " is not an enumerated type");
			}
		}
		catch (Throwable e)
		{
			logger.error("Could not load class " + enumClassName + ": " + e.toString());
		}
		this.enumClass = myEnumClass;
	}

	// ========================= IMPLEMENTATION: OptionListSource ==========

	/**
	 * Loads the list of available options from the types of the enumerated types. Each
	 * label is the enumerated type's logical name, and corresponding value is its string
	 * representation.
	 */
	@Override
	protected void loadOptions()
	{
		OptionList list = new OptionList();
		Object[] constants = enumClass.getEnumConstants();

		for (int i = 0; i < constants.length; i++)
		{
			Enum<?> e = (Enum<?>) constants[i];
			Option thisOption = new Option(e.name(), e.toString());
			list.add(thisOption);
		}

		setOptions(list);
	}

}
