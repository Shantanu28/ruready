package net.ruready.web.user.beans;

import java.io.Serializable;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;

public class GroupSubscriptionFormBean implements Serializable
{
	private static final long serialVersionUID = -7907604130450280183L;
	
	private User matchingUser;
	private UserGroup matchingGroup;
	private UserGroup conflictingGroup;
	
	private SimpleUserSearchBean search = new SimpleUserSearchBean();
	
	public User getMatchingUser()
	{
		return matchingUser;
	}

	public void setMatchingUser(final User matchingUser)
	{
		this.matchingUser = matchingUser;
	}

	public UserGroup getMatchingGroup()
	{
		return matchingGroup;
	}

	public void setMatchingGroup(final UserGroup matchingGroup)
	{
		this.matchingGroup = matchingGroup;
	}

	public UserGroup getConflictingGroup()
	{
		return conflictingGroup;
	}

	public void setConflictingGroup(final UserGroup conflictingGroup)
	{
		this.conflictingGroup = conflictingGroup;
	}
	
	public SimpleUserSearchBean getSearch()
	{
		return search;
	}

	public void setSearch(final SimpleUserSearchBean userSearch)
	{
		this.search = userSearch;
	}
}
