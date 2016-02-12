/*****************************************************************************************
 * Source File: EnumListOptionListSource.java
 ****************************************************************************************/
package net.ruready.web.select.entity;

import java.util.List;

import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Returns the name/value pairs for the options related to an HTML "select" tag, from an
 * input list of enumerated types.
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
public class EnumListOptionListSource extends DefaultOptionListSource
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EnumListOptionListSource.class);

	// ========================= FIELDS ====================================

	// Class of the enumerated type to draw options from
	private List<Enum<?>> enumList;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Constructs a new "EnumListOptionListSource" object using the parameters provided.
	 * 
	 * @param list
	 *            input list of enumerated types to use
	 */
	public EnumListOptionListSource(List<Enum<?>> enumList)
	{
		this.enumList = enumList;
	}

	// ========================= IMPLEMENTATION: OptionListSource ==========

	/**
	 * Loads the list of available options from the list of the enumerated types. Each
	 * label is the enumerated type's logical name, and corresponding value is its
	 * <code>toString()</code> value.
	 */
	@Override
	protected void loadOptions()
	{
		OptionList list = new OptionList();
		if (enumList != null)
		{
			for (Enum<?> e : enumList)
			{
				String name = e.name();
				String value = e.toString();
				Option thisOption = new Option(name, value);
				list.add(thisOption);
			}
			setOptions(list);
		}
	}

}
