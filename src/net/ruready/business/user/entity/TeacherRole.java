package net.ruready.business.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.property.RoleType;

/**
 * The teacher role
* <p>
* -------------------------------------------------------------------------<br>
* (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
* -------------------------------------------------------------------------
* 
* @author Jeremy Lund
* @version Sep 20, 2007
*/
@Entity
@DiscriminatorValue("TEACHER")
public class TeacherRole extends UserRole
{
	private static final long serialVersionUID = 4166466085598161688L;

	@OneToMany(mappedBy="moderator", cascade=CascadeType.ALL)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private Set<UserGroupModerator> groups = new HashSet<UserGroupModerator>();	

	@OneToMany(mappedBy="member", cascade=CascadeType.ALL)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private Set<TeacherSchoolMembership> schools = new HashSet<TeacherSchoolMembership>();
	
	public TeacherRole() 
	{
		super();
	}
	
	public Set<UserGroupModerator> getGroups()
	{
		return groups;
	}
	
	public Boolean hasMembershipToGroup(final UserGroup userGroup)
	{
		for (UserGroupModerator moderator : getGroups())
		{
			if (moderator.getGroup().equals(userGroup))
			{
				return true;
			}
		}
		return false;
	}

	public Boolean hasActiveSchoolMemberships()
	{
		for (TeacherSchoolMembership membership : getSchools())
		{
			if (membership.getMemberStatus() == ActiveStatus.ACTIVE)
			{
				return true;
			}
		}
		return false;
	}
	
	public Boolean hasAnyMembershipToSchool(final School school)
	{
		for (TeacherSchoolMembership membership : getSchools())
		{
			if (membership.getSchool().equals(school))
			{
				return true;
			}
		}
		return false;
	}
	
	public Boolean hasActiveMembershipToSchool(final School school)
	{
		for (TeacherSchoolMembership membership : getSchools())
		{
			if (membership.getSchool().equals(school))
			{
				if (membership.getMemberStatus() == ActiveStatus.ACTIVE)
				{
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public Set<TeacherSchoolMembership> getSchools()
	{
		return schools;
	}

	@Override
	public RoleType getRoleType()
	{
		return RoleType.TEACHER;
	}
}
