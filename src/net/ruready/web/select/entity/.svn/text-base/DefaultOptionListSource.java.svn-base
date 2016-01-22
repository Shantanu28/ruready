/*****************************************************************************************
 * Source File: DefaultOptionListSource.java
 ****************************************************************************************/
package net.ruready.web.select.entity;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;
import net.ruready.web.select.exports.OptionListSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This abstract class implements the required methods for an "Option List"
 * source, which returns the name/value pairs for the options related to an HTML
 * "select" tag. This class must be extended by a concrete subclass which must
 * implement the specified abstract method loadOptions() to load the list of
 * available options from the implementation-specific data source.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 3, 2007
 */
public abstract class DefaultOptionListSource implements OptionListSource
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultOptionListSource.class);

	// ========================= FIELDS ====================================

	private OptionList options = null;

	private OptionList optionsPlusBlank = null;

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Loads the list of available options from the implementation-specific data
	 * source.
	 */
	protected abstract void loadOptions();

	// ========================= IMPLEMENTATION: OptionListSource ==========

	/**
	 * Returns a filtered array of Option objects defining the available
	 * options. This method will return the same results as using the method
	 * getOptions(filter, true).
	 * 
	 * @param filter
	 *            an implementation-defined filter parameter further identifying
	 *            or limiting the available options
	 * @return an List of Option objects defining the available options
	 */
	public OptionList getOptions(Object filter)
	{
		return getOptions(filter, true);
	}

	/**
	 * Returns a filtered array of Option objects defining the available
	 * options. The default implementation of this method ignores the filter and
	 * invokes the unfiltered getOptions() method. Implementations that require
	 * filtered options should override this method with the appropriate
	 * implementation- specific code.
	 * 
	 * @param filter
	 *            an implementation-defined filter parameter further identifying
	 *            or limiting the available options
	 * @param required
	 *            a boolean indicating whether or not input is required for this
	 *            field. Setting this indicator to false will cause the
	 *            generation of an additional option for no selection.
	 * @return an List of Option objects defining the available options
	 */
	public OptionList getOptions(Object filter, boolean required)
	{
		return getOptions(required);
	}

	/**
	 * Returns an List of Option objects defining the available options. This
	 * method will return the same results as using the method getOptions(true).
	 * 
	 * @return an List of Option objects defining the available options
	 */
	public OptionList getOptions()
	{
		return getOptions(true);
	}

	/**
	 * Returns an List of Option objects defining the available options.
	 * 
	 * @param required
	 *            a boolean indicating whether or not input is required for this
	 *            field. Setting this indicator to false will cause the
	 *            generation of an additional option for no selection.
	 * @return an List of Option objects defining the available options
	 */
	public OptionList getOptions(final boolean required)
	{
		return this.getOptions(required, CommonNames.MISC.EMPTY_STRING);
	}

	/**
	 * Returns an List of Option objects defining the available options.
	 * 
	 * @param required
	 *            a boolean indicating whether or not input is required for this
	 *            field. Setting this indicator to false will cause the
	 *            generation of an additional option for no selection.
	 * @param emptySelectionValue
	 *            value of empty selection
	 * @return an List of Option objects defining the available options
	 */
	public OptionList getOptions(final boolean required, final String emptySelectionValue)
	{
		if (options == null)
		{
			loadOptionsArrays(emptySelectionValue);
		}

		return required ? options : optionsPlusBlank;
	}

	// ========================= METHODS ===================================

	/**
	 * Obtains the available options from the implementation-specific load
	 * method.
	 * 
	 * @param emptySelectionValue
	 *            value of empty selection
	 */
	private void loadOptionsArrays(final String emptySelectionValue)
	{
		loadOptions();

		if (options == null)
		{
			options = new OptionList();
			logger.info(this.getClass().getName() + ".loadOptions() produced no result.");
		}

		optionsPlusBlank = new OptionList();
		Option blankOption = new Option(WebAppNames.OLS.LABEL_NO_SELECTION,
				emptySelectionValue, true);
		optionsPlusBlank.add(blankOption);
		optionsPlusBlank.addAll(options);
	}

	/**
	 * Sets the List of Option objects defining the available options.
	 * 
	 * @param options
	 *            the List of Option objects defining the available options
	 */
	protected void setOptions(OptionList options)
	{
		this.options = options;
	}
}
