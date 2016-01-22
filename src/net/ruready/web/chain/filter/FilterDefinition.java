/*****************************************************************************************
 * Source File: FilterPackage.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A definition of a filter. Corresponds to a <code><filter></code> element in the XML
 * declaration file. Corresponds to the DTD syntax
 * 
 * <pre>
 *  &lt;!ELEMENT filter (filter-name, description?, filter-class, init-param*)&gt;
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
public class FilterDefinition
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FilterDefinition.class);

	// ========================= FIELDS ====================================

	/**
	 * Name of the filter.
	 */
	private String filterName;

	/**
	 * Option filter description.
	 */
	private String description;

	/**
	 * Class of filter
	 */
	private Class<? extends FilterAction> filterClass;

	/**
	 * An error page to forward the request to in case this filter's processing fails.
	 */
	private String errorPage;

	/**
	 * Initialization parameters for the filter class.
	 */
	private Set<InitParameter> initParameters = new HashSet<InitParameter>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty filter definition.
	 * 
	 * @param filterName
	 * @param description
	 * @param filterClass
	 * @param errorPage
	 */
	public FilterDefinition()
	{
		super();
	}

	/**
	 * Create a filter definition.
	 * 
	 * @param filterName
	 * @param description
	 * @param filterClass
	 * @param errorPage
	 */
	public FilterDefinition(final String filterName, final String description,
			final Class<? extends FilterAction> filterClass, final String errorPage)
	{
		super();
		this.filterName = filterName;
		this.description = description;
		this.filterClass = filterClass;
		this.errorPage = errorPage;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer(filterName);
		// if (description != null)
		// {
		// s.append(CommonNames.MISC.SPACE_CHAR).append(description);
		// }
		// s.append(CommonNames.MISC.SPACE_CHAR).append(filterClass);
		// if (errorPage != null)
		// {
		// s.append(" errorPage ").append(errorPage);
		// }
		if (!initParameters.isEmpty())
		{
			s.append(initParameters);
		}
		return s.toString();
	}

	/**
	 * t
	 * 
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
		final FilterDefinition other = (FilterDefinition) obj;
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
	 * @param e
	 * @return
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public boolean addInitParameter(InitParameter e)
	{
		return initParameters.add(e);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	public boolean removeInitParameter(Object o)
	{
		return initParameters.remove(o);
	}

	/**
	 * Returns <tt>true</tt> if the init parameter set contains the specified parameter
	 * name.
	 * 
	 * @param paramName
	 *            parameter name
	 * @return
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	public boolean containsInitParameter(final String paramName)
	{
		return initParameters.contains(new InitParameter(null, paramName, null));
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
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the filterClass
	 */
	public Class<? extends FilterAction> getFilterClass()
	{
		return filterClass;
	}

	/**
	 * @param filterClass
	 *            the filterClass to set
	 */
	public void setFilterClass(Class<? extends FilterAction> filterClass)
	{
		this.filterClass = filterClass;
	}

	/**
	 * @return the errorPage
	 */
	public String getErrorPage()
	{
		return errorPage;
	}

	/**
	 * @param errorPage
	 *            the errorPage to set
	 */
	public void setErrorPage(String errorPage)
	{
		this.errorPage = errorPage;
	}

	/**
	 * @return the initParameters
	 */
	public Set<InitParameter> getInitParameters()
	{
		return initParameters;
	}

}
