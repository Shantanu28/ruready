package net.ruready.web.user.beans;

import java.io.Serializable;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.RoleType;

public class UserManagementFormBean implements UserAccountFormBean, Serializable
{
	private static final long serialVersionUID = 2783954155753858053L;

	private UserDemographicOptions options;

	private User user;

	private RoleType userRole;

	private String originalEmailAddress;

	public UserManagementFormBean()
	{
		this.user = new User();
	}

	public UserManagementFormBean(final User user)
	{
		this.user = user;
	}

	public User getUser()
	{
		return this.user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}

	public RoleType getRole()
	{
		return this.userRole;
	}

	public void setRole(final RoleType roleType)
	{
		this.userRole = roleType;
	}

	public String getOriginalEmailAddress()
	{
		return originalEmailAddress;
	}

	public void setOriginalEmailAddress(final String originalEmailAddress)
	{
		this.originalEmailAddress = originalEmailAddress;
	}

	public UserDemographicOptions getOptions()
	{
		return this.options;
	}

	public void setOptions(final UserDemographicOptions options)
	{
		this.options = options;
	}

}
