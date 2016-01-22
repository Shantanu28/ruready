/*****************************************************************************************
 * Source File: TagGridAction.java
 ****************************************************************************************/
package net.ruready.web.content.tag.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.tag.entity.MainTagItem;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.business.content.tag.entity.property.TagField;
import net.ruready.business.content.util.util.ItemTypeUtil;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.exception.RecordAccessException;
import net.ruready.common.eis.exception.RecordExistsException;
import net.ruready.common.search.DefaultSearchCriteria;
import net.ruready.common.search.MatchType;
import net.ruready.common.search.SearchCriteria;
import net.ruready.common.search.SearchCriterion;
import net.ruready.common.search.SearchCriterionFactory;
import net.ruready.common.search.SearchType;
import net.ruready.common.search.SortCriterion;
import net.ruready.common.search.SortType;
import net.ruready.common.search.StringExpression;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.EnumUtil;
import net.ruready.port.json.List2JsonWrapper;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.ParameterMap;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Handle AJAX events of an ExtJS grid of tags of a certain CMS item.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 7, 2007
 */
public class TagGridAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	//@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TagGridAction.class);

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * Pre-populate the grid with the tag list of a certain tag typeof the
	 * parent item.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);

		// This is a comma-delimited list of tag IDs that comes from the form
		// bean of the view page
		String tagIds = request.getParameter(WebAppNames.REQUEST.PARAM.ITEM_ID);

		// ======================================================
		// Business logic
		// ======================================================

		// Parse IDs
		String[] parts = tagIds.split(WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR);

		// Read tags
		List<TagItem> tags = new ArrayList<TagItem>();
		for (String part : parts)
		{
			tags.add(bdItem.read(TagItem.class, TextUtil.getStringAsLong(part)));
		}

		logger.debug("tags " + tags);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format
		String encoded = List2JsonWrapper.toJSONString(TagItem.class, tags);
		logger.debug("encoded " + encoded);
		response.getWriter().write(encoded.toString());
		return null;
	}

	/**
	 * Prepare data for an auto-complete feature.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward action_complete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		HttpSession session = request.getSession(false);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);

		long subjectId = TextUtil.getStringAsLong((String) session
				.getAttribute(WebAppNames.SESSION.ATTRIBUTE.SUBJECT_ID));
		ItemType tagType = EnumUtil.createFromString(ItemType.class, request
				.getParameter(WebAppNames.REQUEST.PARAM.TAG_TYPE));
		String query = request.getParameter(WebAppNames.REQUEST.PARAM.QUERY);

		// ======================================================
		// Business logic
		// ======================================================
		Subject subject = bdItem.read(Subject.class, subjectId);
		MainTagItem tagCollection = subject.getTagCollection(tagType);

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Search for all tags under the interest collections whose
		// name starts with "query"
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		SearchCriterion rootCriterion = SearchCriterionFactory
				.createJunctionCriterion(SearchType.CONJUNCTION);
		SearchCriteria searchCriteria = new DefaultSearchCriteria(rootCriterion);

		// Criterion: tag.parentId = interestCollection.id
		{
			ItemTypeUtil.addSuperParentAlias(searchCriteria, tagCollection
					.getIdentifier(), tagType);
			SearchCriterion criterion = SearchCriterionFactory
					.<Long> createSimpleExpression(SearchType.EQ, Long.class,
							TagField.SUPER_PARENT_ID.getName(), tagCollection.getId());
			searchCriteria.add(criterion);
		}

		// Criterion: tag.name starts with query
		if (!TextUtil.isEmptyTrimmedString(query))
		{
			TagField field = TagField.NAME;
			StringExpression criterion = SearchCriterionFactory.createStringExpression(
					SearchType.ILIKE, field.getName(), query, MatchType.STARTS_WITH);
			searchCriteria.add(criterion);
		}

		// Sort by ascending name
		SortCriterion sortOrder = SearchCriterionFactory.createSortCriterion(
				TagField.NAME.getName(), SortType.ASCENDING);

		searchCriteria.addSortCriterion(sortOrder);

		// Execute search
		if (logger.isDebugEnabled())
		{
			logger.debug("Search Criteria = " + searchCriteria);
		}
		AbstractEditItemBD<TagItem> bdTagItem = new StrutsEditItemBD<TagItem>(
				TagItem.class, context, user);
		List<TagItem> tags = bdTagItem.findByCriteria(searchCriteria);
		if (logger.isDebugEnabled())
		{
			logger.debug("#tags = " + tags.size());
		}

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format
		String encoded = List2JsonWrapper.toJSONString(TagItem.class, tags);
		logger.debug("encoded " + encoded);
		response.getWriter().write(encoded.toString());
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward action_new(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		// Immediately flag this action as an AJAX action
		context.put(WebAppNames.CONTEXT.AJAX, true);
		User user = HttpRequestUtil.findUser(request);
		HttpSession session = request.getSession(false);
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);

		long subjectId = TextUtil.getStringAsLong((String) session
				.getAttribute(WebAppNames.SESSION.ATTRIBUTE.SUBJECT_ID));
		ItemType tagType = EnumUtil.createFromString(ItemType.class, request
				.getParameter(WebAppNames.REQUEST.PARAM.TAG_TYPE));
		String name = request.getParameter(WebAppNames.REQUEST.PARAM.NAME);

		// ======================================================
		// Business logic
		// ======================================================
		Item newTag = tagType.createItem(name, null);
		String status = this.addNewTag(bdItem, subjectId, tagType, name, newTag);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in XML format. Use our own toJSONString()
		// method that does not quote the property names (map keys in this
		// case). This is due to problems of parsing quoted property names on
		// the client's side.
		ParameterMap map = new ParameterMap();
		map.put("status", status);
		map.put("name", newTag.getName());
		String encoded = map.toJSONString();
		logger.debug("encoded " + encoded);
		response.getWriter().write(encoded);
		return null;
	}

	// ========================= HOOKS =====================================

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Validate tag name.
	 * 
	 * @param name
	 *            prospective tag name
	 * @return is name valid
	 */
	private boolean validateName(final String name)
	{
		if (TextUtil.isEmptyString(name))
			return false;

		if (name.length() < 3)
			return false;

		if (name.length() > 40)
			return false;

		final String mask = "[a-zA-Z0-9\\s\\-\\:\\#\\/\\[\\]]*";
		final Pattern p = Pattern.compile(mask);
		final Matcher m = p.matcher(name);
		if (!m.matches())
			return false;

		return true;
	}

	/**
	 * Add a new tag to a parent item.
	 * 
	 * @param bdItem
	 * @param subjectId
	 * @param tagType
	 * @param name
	 * @param newTag
	 *            filled with the new tag upon returning from this function
	 * @return status code (a string)
	 * @throws IOException
	 */
	private String addNewTag(final AbstractEditItemBD<Item> bdItem, final long subjectId,
			final ItemType tagType, final String name, Item newTag)
	{
		if (!validateName(name))
		{
			return "invalid";
		}

		// Find the corresponding tag collection to this item + tag type
		Subject subject = bdItem.read(Subject.class, subjectId);
		MainTagItem tagCollection = subject.getTagCollection(tagType);
		try
		{
			bdItem.createUnder(tagCollection.getId(), newTag);
		}
		catch (RecordExistsException e)
		{
			return "exists";
		}
		catch (RecordAccessException e)
		{
			return "accessDenied";
		}
		catch (Exception e)
		{
			return "failure";
		}

		return "success";
	}
}
