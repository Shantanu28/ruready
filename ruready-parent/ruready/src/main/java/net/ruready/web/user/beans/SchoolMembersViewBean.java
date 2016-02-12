package net.ruready.web.user.beans;

import java.io.Serializable;

import net.ruready.business.content.world.entity.School;

public class SchoolMembersViewBean extends SimpleUserRoleListViewBean implements Serializable
{
	private static final long serialVersionUID = 686688652924964443L;
	
	private School school;

	public School getSchool()
	{
		return school;
	}

	public void setSchool(final School school)
	{
		this.school = school;
	}
}
