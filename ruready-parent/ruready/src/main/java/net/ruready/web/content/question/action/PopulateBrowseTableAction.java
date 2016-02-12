/*****************************************************************************************
 * Source File: PopulateMenuAction.java
 ****************************************************************************************/
package net.ruready.web.content.question.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.question.entity.QuestionCount;
import net.ruready.business.content.question.exports.AbstractEditQuestionBD;
import net.ruready.business.rl.WebApplicationContext;
import net.ruready.business.user.entity.User;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.XmlUtil;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.item.imports.StrutsEditItemBD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import test.ruready.imports.StandAloneEditQuestionBD;

/**
 * Handles AJAX events of populating a question browsing table with question
 * counts in a subject or a course item.
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
public class PopulateBrowseTableAction extends Action
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PopulateBrowseTableAction.class);

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * Populates a question browsing table with question counts in a subject or
	 * a course item. Returns the question count object in XML format.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		logger.debug("execute()");

		// ======================================================
		// Read context & parameters
		// ======================================================
		WebApplicationContext context = StrutsUtil.getWebApplicationContext(request);
		User user = HttpRequestUtil.findUser(request);
		// The value parameter may look like "23" or "parentMenuName_23". Get
		// the id.
		long value = HttpRequestUtil.getParameterAsLong(request, WebAppNames.XML.VALUE);
		if (!TextUtil.isValidId(value))
		{
			// valueParam is not numerical, assuming a parentMenuName_23
			// pattern. Here we could instead use a regexp matcher.
			try
			{
				String valueParam = request.getParameter(WebAppNames.XML.VALUE);
				String[] parts = valueParam.split(CommonNames.MISC.SEPARATOR);
				value = TextUtil.getStringAsLong(parts[1]);
			}
			catch (Exception e)
			{

			}
		}

		if (!TextUtil.isValidId(value))
		{
			logger.warn("Bad value parameter, ignoring");
			return null;
		}

		// ======================================================
		// Business logic
		// ======================================================

		// Read item
		AbstractEditItemBD<Item> bdItem = new StrutsEditItemBD<Item>(Item.class, context,
				user);
		Item parent = bdItem.read(Item.class, value);

		// Compute question counts
		AbstractEditQuestionBD bdQuestion = new StandAloneEditQuestionBD(context, user);
		QuestionCount questionCount = bdQuestion.generateQuestionCount(parent);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write counts to response in XML format
		HttpRequestUtil.prepareXMLResponse(response);
		response.getWriter().write(XmlUtil.openTag(WebAppNames.XML.RESPONSE).toString());
		response.getWriter().write(questionCount.toXml("question-count").toString());
		response.getWriter().write(XmlUtil.closeTag(WebAppNames.XML.RESPONSE).toString());
		return null;
	}
	// ========================= HOOKS =====================================

	// ========================= PRIVATE METHODS ===========================

}
