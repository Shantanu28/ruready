/*****************************************************************************************
 * Source File: OptionListSource.java
 ****************************************************************************************/
package net.ruready.web.select.exports;

import net.ruready.web.select.exports.OptionList;

/**
 * This interface specifies the required methods for an "Option List" source,
 * which returns the name/value pairs for the options related to an HTML
 * "select" tag.
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
 * @see http://www.webspherepower.com/issues/issue200311/00001130001.html
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 16, 2007
 */
public interface OptionListSource
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Returns a filtered array of Option objects defining the available
	 * options. This method should return the same results as using the method
	 * getOptions(filter, true).
	 * 
	 * @param filter
	 *            an implementation-defined filter parameter further identifying
	 *            or limiting the available options
	 * @return an List of Option objects defining the available options
	 */
	OptionList getOptions(final Object filter);

	/**
	 * Returns a filtered array of Option objects defining the available
	 * options.
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
	OptionList getOptions(final Object filter, final boolean required);

	/**
	 * Returns an List of Option objects defining the available options.
	 * This method should return the same results as using the method
	 * getOptions(true).
	 * 
	 * @return an List of Option objects defining the available options
	 */
	OptionList getOptions();

	/**
	 * Returns an List of Option objects defining the available options.
	 * 
	 * @param required
	 *            a boolean indicating whether or not input is required for this
	 *            field. Setting this indicator to false will cause the
	 *            generation of an additional option for no selection.
	 * @return an List of Option objects defining the available options
	 */
	OptionList getOptions(final boolean required);

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
	OptionList getOptions(final boolean required, final String emptySelectionValue);
}
