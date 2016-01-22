package net.ruready.web.user.webflow;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.User;
import net.ruready.web.user.beans.TeacherRegistrationFormBean;

import org.springframework.webflow.execution.RequestContext;

public class TeacherRegistrationFormAction extends UserRegistrationFormAction
{

	public TeacherRegistrationFormAction()
	{
		setFormObjectClass(TeacherRegistrationFormBean.class);
	}

	@Override
	protected void addSchool(RequestContext context, User user, School school)
	{
		getWebFlowSupport().getUserBD(context).addTeacherSchoolMembership(school, user);
	}

	
}
