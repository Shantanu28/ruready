/*****************************************************************************************
 * Source File: FilterPackage.java
 ****************************************************************************************/
package net.ruready.web.chain.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.ruready.common.parser.xml.TagAttachment;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.stack.AbstractStack;
import net.ruready.common.stack.GenericStack;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A target object constructed by the {@link FilterPackageParser} . Contains a set of
 * filter definitions and a list of filter mappings.
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
 * @version Oct 5, 2007
 */
public class FilterPackage implements AbstractStack<TagAttachment>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(FilterPackage.class);

	// ========================= FIELDS ====================================

	/**
	 * Filter definitions.
	 */
	private final Set<FilterDefinition> filterDefinitions = new HashSet<FilterDefinition>();

	/**
	 * A list of filter mappings (filter-definition-to-URL-pattern).
	 */
	private final List<FilterMapping> filterMappings = new ArrayList<FilterMapping>();

	/**
	 * a placeholder that keeps track of consumption progress. The map entries' keys are
	 * XML tag names and their values are the corresponding bodies of the XML tags (read
	 * from the parsed XML file).
	 */
	private final AbstractStack<TagAttachment> stack = new GenericStack<TagAttachment>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty filter package.
	 */
	public FilterPackage()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append(filterDefinitions.toString());
		s.append(CommonNames.MISC.NEW_LINE_CHAR);
		s.append(filterMappings.toString());
		return s.toString();
	}

	// ========================= METHODS ===================================

	/**
	 * Print this object's embedded stack.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String printStack()
	{
		return stack.toString();
	}

	/**
	 * Return a filter definition by filter name.
	 * 
	 * @param filterName
	 * @return
	 */
	public FilterDefinition getFilterDefinitionByName(final String filterName)
	{
		FilterDefinition filterToFind = new FilterDefinition(filterName, null, null, null);
		for (FilterDefinition filterDefinition : filterDefinitions)
		{
			if (filterDefinition.equals(filterToFind))
			{
				return filterDefinition;
			}
		}
		return null;
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public boolean addFilterDefinition(FilterDefinition e)
	{
		return filterDefinitions.add(e);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addFilterMapping(FilterMapping e)
	{
		return filterMappings.add(e);
	}

	/**
	 * Return the list of filters that apply to a URL.
	 * 
	 * @param path
	 *            URL to apply filters to
	 * @return a list of filters that apply to the URL in their order of declaration. If
	 *         none applies, returns an empty list
	 */
	public List<FilterDefinition> getFiltersThatApply(final String path)
	{
		List<FilterDefinition> filtersThatApply = new ArrayList<FilterDefinition>();
		for (FilterMapping filterMapping : filterMappings)
		{
			if (filterMapping.isApplies(path))
			{
				filtersThatApply.add(filterMapping.getFilterDefinition());
			}
		}
		return filtersThatApply;
	}

	// ========================= DELEGATE METHODS ==========================

	/**
	 * @return
	 * @see net.ruready.common.parser.xml.AbstractStack#isEmpty()
	 */
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @param fence
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#elementsAbove(java.lang.Object)
	 */
	public List<TagAttachment> elementsAbove(TagAttachment fence)
	{
		return stack.elementsAbove(fence);
	}

	/**
	 * @param fence
	 * @param comparator
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#elementsAbove(java.lang.Object,
	 *      java.util.Comparator)
	 */
	public List<TagAttachment> elementsAbove(TagAttachment fence,
			Comparator<TagAttachment> comparator)
	{
		return stack.elementsAbove(fence, comparator);
	}

	/**
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#peek()
	 */
	public TagAttachment peek()
	{
		return stack.peek();
	}

	/**
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#pop()
	 */
	public TagAttachment pop()
	{
		return stack.pop();
	}

	/**
	 * @param item
	 * @return
	 * @see net.ruready.common.stack.AbstractStack#push(java.lang.Object)
	 */
	public TagAttachment push(TagAttachment item)
	{
		return stack.push(item);
	}

	/**
	 * @return the filterDefinitions
	 */
	public Set<FilterDefinition> getFilterDefinitions()
	{
		return filterDefinitions;
	}

	/**
	 * @return the filterMappings
	 */
	public List<FilterMapping> getFilterMappings()
	{
		return filterMappings;
	}
}
