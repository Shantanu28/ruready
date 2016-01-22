package net.ruready.web.user.beans;

import java.io.Serializable;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.User;

public class SchoolFormBean implements Serializable
{
	private static final long serialVersionUID = 6506034290626518856L;

	public static final Integer MIN_SEARCH_STRING_LENGTH = 2;
	
	private String searchString = "";
	private ResultsPagingBean pagingBean = new ResultsPagingBean(0);
	private School matchingSchool;
	private User matchingUser;
	
	public Integer getMinimumSearchStringLength()
	{
		return MIN_SEARCH_STRING_LENGTH;
	}
	
	public String getSearchString()
	{
		return searchString;
	}
	
	public void setSearchString(final String searchString)
	{
		this.searchString = searchString;
	}

	public School getMatchingSchool()
	{
		return matchingSchool;
	}

	public void setMatchingSchool(final School matchingSchool)
	{
		this.matchingSchool = matchingSchool;
	}

	public User getMatchingUser()
	{
		return matchingUser;
	}

	public void setMatchingUser(final User matchingUser)
	{
		this.matchingUser = matchingUser;
	}

	public ResultsPagingBean getPagingBean()
	{
		return pagingBean;
	}

	public void setPagingBean(ResultsPagingBean pagingBean)
	{
		this.pagingBean = pagingBean;
	}
}
