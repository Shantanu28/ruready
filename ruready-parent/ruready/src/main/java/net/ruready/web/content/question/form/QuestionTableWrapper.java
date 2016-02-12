/*****************************************************************************************
 * Source File: QuestionTableWrapper.java
 ****************************************************************************************/
package net.ruready.web.content.question.form;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.ruready.business.content.question.entity.Question;
import net.ruready.common.util.XmlUtil;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.TableDecorator;

/**
 * A concrete table decorator for the question result set table. Future
 * decorators should be implemented at the column level for reuse (e.g. for date
 * columns, etc).
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Greg Felice <code>&lt;greg.felice@gmail.com&gt;</code>
 * @version Aug 2, 2007
 */
@Deprecated
public class QuestionTableWrapper extends TableDecorator
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(QuestionTableWrapper.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * FastDateFormat used to format dates in getDate().
	 */
	private static final FastDateFormat dateFormat = FastDateFormat
			.getInstance("dd-MMM-yyyy HH:mm");

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct and initialize a question wrapper.
	 */
	public QuestionTableWrapper()
	{
		super();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the last modified date as a String in MM/dd/yy format.
	 * 
	 * @return formatted date
	 */
	public String getLastModified()
	{
		Date date = ((Question) this.getCurrentRowObject()).getLastModified();
		return QuestionTableWrapper.dateFormat.format(date != null ? date : new Date(
				System.currentTimeMillis()));
	}

	/**
	 * Return a string of dynamically rendered links that offer actions that can
	 * be executed based upon a question's state. If question is deleted
	 * undelete If question is disabled enable If question is not deleted or
	 * disabled clone, disable, delete, edit
	 * 
	 * @return String of links
	 */
	@Deprecated
	public String xxxgetFunctions()
	{
		Question question = (Question) this.getCurrentRowObject();
		boolean isDisabled = question.isDisabled();
		boolean isDeleted = question.isDeleted();

		StringBuffer functions = new StringBuffer();
		final String spacer = WebAppNames.HTML.SPACE;
		// Retrieve the URL prefix of actions called in the javascript handler
		// functions;
		// must be set by the calling JSP.
		final String prefixUrl = (String) getPageContext().getAttribute("prefixUrl");
		// Retrieve the form name from the page context; must be set by the
		// calling JSP.
		final String formName = (String) getPageContext().getAttribute("formName");
		// Page to come back to after the action is completed
		final String customForward = (String) getPageContext().getAttribute(
				"customForward");
		// Page we came from
		final String thisPageForward = (String) getPageContext().getAttribute(
				"thisPageForward");

		if (isDeleted)
		{
			StringBuffer undeleteHref = createQuestionActionLink(prefixUrl, formName,
					"Undelete", "action_undelete", question.getId(), customForward,
					thisPageForward);

			functions.append(undeleteHref);
		}
		else if (isDisabled)
		{
			StringBuffer enableHref = createQuestionActionLink(prefixUrl, formName,
					"Enable", "action_enable", question.getId(), customForward,
					thisPageForward);

			functions.append(enableHref);
		}
		else
		{
			StringBuffer editHref = createQuestionActionLink(prefixUrl, formName, "Edit",
					"action_edit", question.getId(), customForward, thisPageForward);

			functions.append(editHref);
			functions.append(spacer);

			StringBuffer cloneHref = createQuestionActionLink(prefixUrl, formName,
					"Clone", "action_clone", question.getId(), customForward,
					thisPageForward);

			functions.append(cloneHref);
			functions.append(spacer);

			StringBuffer disableHref = createQuestionActionLink(prefixUrl, formName,
					"Disable", "action_disable", question.getId(), customForward,
					thisPageForward);

			functions.append(disableHref);
			functions.append(spacer);

			StringBuffer deleteHref = createQuestionActionLink(prefixUrl, formName,
					"Delete", "action_delete", question.getId(), customForward,
					thisPageForward);

			functions.append(deleteHref);
		}

		// Generate the encompassing division tag and add style, alignment, etc.
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("align", "center");
		return XmlUtil.fullTag(WebAppNames.HTML.TAG_NAME_DIVISION, attributes, functions)
				.toString();
	}

	/**
	 * if question has parameters, return 'P', else return ''.
	 * 
	 * @return if question has parameters, returns 'P', else returns ''
	 */
	@Deprecated
	public String xxxgetParametric()
	{
		StringBuffer sb = new StringBuffer();

		// Could be replaced by WebAppNames.HTML tag and attribute names instead
		// of
		// hard-coding the strings here; see #xxxgetFunctions().
		sb.append("<div align='center'>");

		String questionParameters = ((Question) this.getCurrentRowObject())
				.getParameters();

		if (questionParameters != null)
		{

			if (questionParameters.trim().length() > 0)
				sb.append("P");
		}

		sb.append("</div>");

		return sb.toString();
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * A helper function that builds a link element string.
	 * 
	 * @param prefixUrl
	 *            URL prefix (Struts action URLs are relative to the prefix)
	 * @param formName
	 *            name of inscribing form.
	 * @param title
	 *            title of action (is displayed as the link's text)
	 * @param method
	 *            identifier of request parameter of the requested action.
	 * @param id
	 *            identifier of the object we are operating on.
	 * @param customForward
	 *            Struts forward that specifies the page to come back to after
	 *            the action
	 * @param thisPageForward
	 *            Struts forward that specifies the page we came from
	 * @return HTML link string
	 */
	@Deprecated
	private StringBuffer createQuestionActionLink(final String prefixUrl,
			final String formName, final String title, final String method,
			final Long id, final String customForward, final String thisPageForward)
	{
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("href", "javascript:handleQuestionOperation(" + prefixUrl
				+ WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR

				+ formName + WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR

				+ WebAppNames.JAVASCRIPT.ARGUMENT_STRING_DECORATOR + method
				+ WebAppNames.JAVASCRIPT.ARGUMENT_STRING_DECORATOR

				+ WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR + id

				+ WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR
				+ WebAppNames.JAVASCRIPT.ARGUMENT_STRING_DECORATOR + customForward
				+ WebAppNames.JAVASCRIPT.ARGUMENT_STRING_DECORATOR

				+ WebAppNames.JAVASCRIPT.ARGUMENT_SEPARATOR
				+ WebAppNames.JAVASCRIPT.ARGUMENT_STRING_DECORATOR + thisPageForward
				+ WebAppNames.JAVASCRIPT.ARGUMENT_STRING_DECORATOR

				+ ")");
		// Now implemented in a JSP, where we replace the title by a title key
		// into the
		// application resources, for i18n
		return XmlUtil.fullTag(WebAppNames.HTML.TAG_NAME_LINK, attributes,
				new StringBuffer(title));
	}
}
