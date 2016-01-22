package net.ruready.web.user.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import net.ruready.business.user.entity.UserGroupModerator;

import org.apache.commons.lang.builder.CompareToBuilder;

public class ModeratedGroupsViewBean implements Serializable
{
	private static final long serialVersionUID = 7750654255313791503L;
	
	private Boolean canManage;
	private Collection<UserGroupModerator> userGroups;
	
	public Boolean getCanManage()
	{
		return canManage;
	}
	
	public void setCanManage(final Boolean canManage)
	{
		this.canManage = canManage;
	}
	
	public Collection<UserGroupModerator> getUserGroups()
	{
		return userGroups;
	}
	
	public void setUserGroups(final Collection<UserGroupModerator> groups)
	{
		this.userGroups = new TreeSet<UserGroupModerator>(getComparator());
		this.userGroups.addAll(groups);
	}
	
	private Comparator<UserGroupModerator> getComparator()
	{
		return new Comparator<UserGroupModerator>() {
			public int compare(final UserGroupModerator o1,  final UserGroupModerator o2)
			{				
				return new CompareToBuilder()
					.append(o1.getGroup().getName(), o2.getGroup().getName())
					.append(o1.getGroup().getCourse().getDescription(), o2.getGroup().getCourse().getDescription())
					.append(o1.getGroup().getSchool().getDescription(), o2.getGroup().getSchool().getDescription())
					.toComparison();
			}
		};
	}
}
