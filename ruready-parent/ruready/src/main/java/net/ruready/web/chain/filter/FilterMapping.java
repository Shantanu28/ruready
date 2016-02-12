/*****************************************************************************************
 * Source File: FilterPackage.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A definition of a filter mapping. Corresponds to a <code><filter-mapping></code>
 * element in the XML declaration file. Corresponds to the DTD syntax
 * 
 * <pre>
 * &lt;!ELEMENT filter-mapping (filter-name, url-pattern)&gt;
 * </pre>
 * 
 * <p>
 * ------------------------------------------------------------------------- <br>
 * (c) 2006-2007 Continuing Education, University of Utah <br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software. <br>
 * Contact: Nava L. Livne <code>
 * &lt;nlivne@aoce.utah.edu&gt;
 * </code><br>
 * Academic Outreach and Continuing Education (AOCE) <br>
 * 1901 East South Campus Dr., Room 2197-E <br>
 * University of Utah, Salt Lake City, UT 84112-9399 <br>
 * U.S.A. <br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414 <br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you. <br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>
 * &lt;olivne@aoce.utah.edu&gt;
 * </code>
 * @version Oct 5, 2007
 */
class FilterMapping
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FilterMapping.class);

	// ========================= FIELDS ====================================

	/**
	 * Filter unique identifier.
	 */
	private String filterName;

	/**
	 * Filter definition.
	 */
	private FilterDefinition filterDefinition;

	/**
	 * URL pattern that the filter is applied to.
	 */
	private String urlPattern;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty filter mapping.
	 * 
	 * @param filterDefinition
	 * @param urlPattern
	 */
	public FilterMapping()
	{
		super();
	}

	/**
	 * Create a filter mapping.
	 * 
	 * @param filterDefinition
	 * @param urlPattern
	 */
	public FilterMapping(FilterDefinition filterDefinition, String urlPattern)
	{
		super();
		this.filterDefinition = filterDefinition;
		this.filterName = filterDefinition.getFilterName();
		this.urlPattern = urlPattern;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer("Filter Mapping: name = ");
		s.append(filterName);
		s.append(" URL Pattern ").append(urlPattern);
		return s.toString();
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filterName == null) ? 0 : filterName.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FilterMapping other = (FilterMapping) obj;
		if (filterName == null)
		{
			if (other.filterName != null)
				return false;
		}
		else if (!filterName.equals(other.filterName))
			return false;
		return true;
	}

	// ========================= METHODS ===================================

	/**
	 * Does this filter mapping apply to a URL.
	 * 
	 * @param path
	 *            URL to apply filter to
	 * @return <code>true</code> iff the URL is included in this filter mapping's URL
	 *         patternF
	 */
	public boolean isApplies(final String path)
	{
		return StrutsUtil.urlMatches(path, urlPattern);
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the filterName
	 */
	public String getFilterName()
	{
		return filterName;
	}

	/**
	 * @param filterName
	 *            the filterName to set
	 */
	public void setFilterName(String filterName)
	{
		this.filterName = filterName;
	}

	/**
	 * @return the filterDefinition
	 */
	public FilterDefinition getFilterDefinition()
	{
		return filterDefinition;
	}

	/**
	 * @param filterDefinition
	 *            the filterDefinition to set
	 */
	public void setFilterDefinition(FilterDefinition filterDefinition)
	{
		this.filterDefinition = filterDefinition;
	}

	/**
	 * @return the urlPattern
	 */
	public String getUrlPattern()
	{
		return urlPattern;
	}

	/**
	 * @param urlPattern
	 *            the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern)
	{
		this.urlPattern = urlPattern;
	}
}
