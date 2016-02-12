package net.ruready.web.user.webflow;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.web.user.beans.StudentRegistrationFormBean;

import org.springframework.webflow.execution.RequestContext;

public class StudentRegistrationFormAction extends UserRegistrationFormAction
{

	public StudentRegistrationFormAction()
	{
		setFormObjectClass(StudentRegistrationFormBean.class);
	}
	
	@Override
	protected void addSchool(final RequestContext context, final User user, final School school)
	{
		((StudentRole)user.getRole(RoleType.STUDENT)).addSchool(school);
		getWebFlowSupport().getUserBD(context).updateUser(user);
	}	
}
