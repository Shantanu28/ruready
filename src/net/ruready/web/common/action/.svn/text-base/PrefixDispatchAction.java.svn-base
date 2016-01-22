/*****************************************************************************************
 * Source File: MethodDispatchAction.java
 ****************************************************************************************/

package net.ruready.web.common.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * A version of {@link DispatchAction} that uses a hard-coded parameter name prefix to
 * dispatch to different methods. {@link DispatchAction} is great when you need more than
 * one Submit-Button in a Form. But the disadvantage is, that the name of the called
 * Action method must be the same what the User sees as Text on the Submit-Button.
 * Internationalization of Button-Texts is not usable and the Web-Designer is not as
 * flexible as you need. The Struts-Solution is the more complicated LookupDispatchAction.
 * But here you have to implement a Method called "getKeyMethodMap". A simpler, yet not
 * perfect solution, has been proposed in
 * {@link https://issues.apache.org/struts/browse/STR-825}.In the JSP you can have
 * something like this: <blockquote>
 * 
 * <pre>
 *  &lt;nested:form action=&quot;/list.do&quot;&gt;
 *  &lt;table&gt;
 *  &lt;nested:iterate property=&quot;list&quot;&gt;
 * <tr>
 * <td>
 * &lt;nested:submit property=&quot;method=delete&quot; value=&quot;Delete this&quot;/&gt;
 * </td>
 * <td>
 * &lt;nested:submit property=&quot;method=insert&quot; value=&quot;Empty here&quot;/&gt;
 * </td>
 * <td>
 * &lt;nested:text property=&quot;this/&quot; /&gt;
 * </td>
 * </tr>
 *  &lt;/nested:iterate&gt;
 *  &lt;/table&gt;
 *  &lt;html:submit property=&quot;method=save&quot;&gt;Save everything&lt;/html:submit&gt;
 *  &lt;html:submit property=&quot;method=append&quot;&gt;append empty to list&lt;/html:submit&gt;
 *  &lt;/nested:form&gt;
 * </pre>
 * 
 * </blockquote>
 * <p>
 * The alternative (implementing the getKeyMethodMap() method of LookupDispatchAction) is
 * also OK but a little cumbersome. Keep in mind that the original intent of the
 * "property" attributes of the html tags was to map an html control to a form bean
 * control. Specifying property="method=name" does not follow the intended usage and
 * further confuses the issue.
 * <p>
 * Note: override the {@link DispatchAction#unspecified} method for a default method
 * called when no parameter matching the prefix is found in the request (the default
 * action).
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
 * @see https://issues.apache.org/struts/browse/STR-825
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 30, 2007
 */
public class PrefixDispatchAction extends LoggedDispatchAction
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PrefixDispatchAction.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Default constructor, probably used by the action servlet to load this class
	 * whenever necessary.
	 */
	public PrefixDispatchAction()
	{
		super();
	}

	// ========================= STATIC METHODS ============================

	// ========================= IMPLEMENTATION: DispatchAction ============

	/**
	 * @see org.apache.struts.actions.DispatchAction#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (isCancelled(request))
		{
			// Treat html:cancel first
			return this.cancelled(mapping, form, request, response);
		}

		// Identify the method name to be dispatched to
		String name = "unspecified";
		Enumeration<?> parameters = request.getParameterNames();
		while (parameters.hasMoreElements())
		{
			String item = (String) parameters.nextElement();
			String value = request.getParameter(item);
			int i = item.indexOf(WebAppNames.ACTION.DISPATCH_PARAMETER_PREFIX);
			if ((i < 0) || !WebAppNames.ACTION.VALUE_TRUE.equals(value))
			{
				// Parameter must start with DISPATCH_PARAMETER_PREFIX and must equal to
				// VALUE_TRUE to active its dispatch method.
				continue;
			}
			name = item.substring(i + 7);
			if (i > 0)
			{
				// Was a nested:submit Button, identify the clicked object
				String prop = item.substring(0, i - 1);
				Object obj = PropertyUtils.getProperty(form, prop);
				request.setAttribute("clickedOBJ", obj); // store for later use
			}
			break;
		}

		return dispatchMethod(mapping, form, request, response, name);
	}

	// ========================= METHODS ===================================

	/**
	 * Return object we are dispatching to based on which value of the parameter METHOD
	 * was found in the request.
	 * 
* @param request client's request object
	 *            incoming HTTP request
	 * @return object to dispatch to
	 */
	public Object getClickedObject(HttpServletRequest request)
	{
		return request.getAttribute("clickedOBJ");
	}
}
