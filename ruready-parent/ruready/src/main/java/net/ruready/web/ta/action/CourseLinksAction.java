package net.ruready.web.ta.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.user.entity.User;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.ta.beans.CourseLinksBean;
import net.ruready.web.user.support.StrutsSupport;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CourseLinksAction extends Action
{
	private static final String BEAN_NAME = "viewBean";
	
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
	public ActionForward execute(
			final ActionMapping mapping, 
			final ActionForm form, 
			final HttpServletRequest request, 
			final HttpServletResponse response) 
		throws Exception
	{
		final User user = HttpRequestUtil.findUser(request);
		final List<Course> courses = getStrutsSupport().getCourseBD(request).findAllNonDeleted(Course.class,
				ItemType.CATALOG);
		
		final List<StudentTranscript> transcripts = getStrutsSupport().getStudentTranscriptBD(request).findStudentTranscriptsForUser(user);
		final CourseLinksBean viewBean = new CourseLinksBean();
		viewBean.setAllCourses(courses);
		viewBean.setTranscripts(transcripts);
		
		request.setAttribute(BEAN_NAME, viewBean);
		return mapping.findForward("success");
	}
	
	private StrutsSupport getStrutsSupport()
	{
		return this.strutsSupport;
	}
}
