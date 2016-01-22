/*****************************************************************************************
 * Source File: SimpleOptionListSource.java
 ****************************************************************************************/
package net.ruready.web.select.entity;

import java.util.StringTokenizer;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;

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
public class SimpleOptionListSource extends DefaultOptionListSource
{
	// ========================= FIELDS ====================================

	private String optionList = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a new "SimpleOptionListSource" object using the parameters provided.
	 * 
	 * @param optionList
	 *            a String containing the comma separated values for the option list
	 */
	public SimpleOptionListSource(String optionList)
	{
		this.optionList = optionList;
	}

	// ========================= IMPLEMENTATION: OptionListSource ==========

	/**
	 * Loads the list of available options from the optionList String passed to the
	 * constructor.
	 */
	@Override
	protected void loadOptions()
	{
		OptionList list = new OptionList();

		StringTokenizer tokens = new StringTokenizer(optionList, ",");
		while (tokens.hasMoreTokens())
		{
			String thisItem = tokens.nextToken();
			Option thisOption = new Option(thisItem, thisItem);

			// If there are multiple Names.TREE.SEPARATOR's, as in "foo:and:bar", they
			// will
			// be regarded as Option("foo", "and:bar").
			String[] parts = thisItem.split(CommonNames.TREE.SEPARATOR, 2);
			if (parts.length >= 2)
			{
				thisOption = new Option(parts[0], parts[1]);
			}

			/*
			 * // Original implementation int x = thisItem.indexOf(Names.TREE.SEPARATOR);
			 * if (x > -1) { String label = thisItem.substring(0, x); String value =
			 * thisItem.substring(x + 1); thisOption = new Option(label, value); }
			 */

			list.add(thisOption);
		}

		setOptions(list);
	}

}
