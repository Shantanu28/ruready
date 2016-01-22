package net.ruready.web.user.beans;

import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;

public class StudentRegistrationFormBean extends AccountFormBean
{
	private static final long serialVersionUID = 7377904532481283605L;

	public StudentRegistrationFormBean()
	{
		super();
		getUser().addRole(new StudentRole());
	}
	
	public StudentRegistrationFormBean(final User user)
	{
		super(user);
		getUser().addRole(new StudentRole());
	}
}
