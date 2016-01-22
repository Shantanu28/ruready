package net.ruready.web.user.beans;

import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;

public class TeacherRegistrationFormBean extends AccountFormBean
{
	private static final long serialVersionUID = 2190040894671358636L;

	public TeacherRegistrationFormBean()
	{
		super();
		getUser().addRole(new TeacherRole());
	}
	
	public TeacherRegistrationFormBean(final User user)
	{
		super(user);
		getUser().addRole(new TeacherRole());
	}
}
