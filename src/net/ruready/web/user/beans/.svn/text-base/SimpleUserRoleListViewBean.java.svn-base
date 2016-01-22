package net.ruready.web.user.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.ruready.business.user.entity.UserRole;

import org.apache.commons.lang.builder.CompareToBuilder;

public class SimpleUserRoleListViewBean implements Serializable
{
	private static final long serialVersionUID = -3750143613756779458L;
	
	private List<UserRole> users;

	public List<UserRole> getUsers()
	{
		return users;
	}

	public void setUsers(final List<UserRole> users)
	{
		this.users = users;
		Collections.sort(this.users, getComparator());
	}

	private Comparator<UserRole> getComparator()
	{
		return new Comparator<UserRole>() {
			public int compare(final UserRole o1,  final UserRole o2)
			{				
				return new CompareToBuilder()
					.append(o1.getUser().getEmail(), o2.getUser().getEmail())
					.toComparison();
			}
		};
	}

}