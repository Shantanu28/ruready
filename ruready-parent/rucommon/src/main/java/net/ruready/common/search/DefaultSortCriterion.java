package net.ruready.common.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ...
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
 * @version Nov 12, 2007
 */
public class DefaultSortCriterion implements SortCriterion
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleCriterion.class);

	// ========================= FIELDS ====================================

	/**
	 * property name
	 */
	private String propertyName;
	/**
	 * 
	 */
	private SortType sortType;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param propertyName
	 * @param sortType
	 */
	public DefaultSortCriterion(final String propertyName, final SortType sortType)
	{
		this.propertyName = propertyName;
		this.sortType = sortType;
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return
	 * @see net.ruready.common.search.SortCriterion#getFieldName()
	 */
	public String getFieldName()
	{
		return propertyName;
	}

	/**
	 * @return
	 * @see net.ruready.common.search.SortCriterion#getSortType()
	 */
	public SortType getSortType()
	{
		return sortType;
	}

}
