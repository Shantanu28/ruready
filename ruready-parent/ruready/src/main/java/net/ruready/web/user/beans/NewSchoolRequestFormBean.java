package net.ruready.web.user.beans;

import java.io.Serializable;

import net.ruready.business.user.entity.User;

public class NewSchoolRequestFormBean implements Serializable
{
	private static final long serialVersionUID = -3848990066500736217L;
	
	private User user;
	private String requestContent;
	private Boolean contactMe;
	
	public User getUser()
	{
		return user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}

	public Boolean getContactMe()
	{
		return contactMe;
	}

	public void setContactMe(Boolean contactMe)
	{
		this.contactMe = contactMe;
	}

	public String getRequestContent()
	{
		return requestContent;
	}

	public void setRequestContent(String requestContent)
	{
		this.requestContent = requestContent;
	}
}
