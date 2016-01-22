package net.ruready.web.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.world.entity.School;
import net.ruready.web.user.support.StrutsSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SchoolLookupAction extends Action
{	
	private final StrutsSupport strutsSupport = new StrutsSupport();
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		final String schoolName = request.getParameter("q");
		if (StringUtils.isEmpty(schoolName)) {
			return null;
		}
		response.setContentType("text/plain");
		response.getWriter().print(buildPlainTextSchoolList(getMatchingSchools(request, schoolName)));
		return null;
	}

	private List<School> getMatchingSchools(final HttpServletRequest request,
			final String schoolName)
	{		
		return getStrutsSupport().getWorldBD(request).findSchoolByPartialName(schoolName);
	}
	
	private String buildPlainTextSchoolList(final List<School> matches)
	{
		final StringBuilder results = new StringBuilder();
		for (School match : matches)
		{
			results.append(match.getDescription())
			.append("|")
			.append(match.getId())
			.append("\n");
		}
		return results.toString();
	}
	
	private StrutsSupport getStrutsSupport()
	{
		return this.strutsSupport;
	}
}
