/*****************************************************************************************
 * Source File: ArraysOptionListSource.java
 ****************************************************************************************/
package net.ruready.web.select.entity;

import net.ruready.common.rl.CommonNames;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Returns the name/value pairs for the options related to an HTML "select" tag. The input
 * is two arrays (one for values and one for the corresponding labels).
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
 * @version Sep 16, 2007
 */
public class ArraysOptionListSource extends DefaultOptionListSource
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ArraysOptionListSource.class);

	// ========================= FIELDS ====================================

	// Class of the enumerated type to draw options from
	private Object[] labels;

	private Object[] values;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a new "ArraysOptionListSource" object using the parameters provided.
	 * 
	 * @param labels
	 *            list of labels
	 * @param values
	 *            list of corresponding values
	 */
	public ArraysOptionListSource(Object[] labels, Object[] values)
	{
		this.labels = labels;
		this.values = values;
	}

	// ========================= IMPLEMENTATION: OptionListSource ==========

	/**
	 * Loads the list of available options from the list of labels and the list of
	 * corresponding values.
	 */
	@Override
	protected void loadOptions()
	{
		OptionList list = new OptionList();

		if ((labels != null) && (values != null))
		{
			for (int i = 0; i < labels.length; i++)
			{
				Object labelObj = labels[i];
				Object valueObj = values[i];
				String name = (labelObj == null) ? CommonNames.MISC.EMPTY_STRING
						: labelObj.toString();
				String value = (valueObj == null) ? CommonNames.MISC.EMPTY_STRING
						: valueObj.toString();
				Option thisOption = new Option(name, value);
				list.add(thisOption);
			}
		}

		setOptions(list);
	}

}
