package net.ruready.business.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.property.RoleType;

/**
 * The student role
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
@DiscriminatorValue("STUDENT")
public class StudentRole extends UserRole
{
	private static final long serialVersionUID = 8228356797909638916L;
	
	@ManyToMany
	@JoinTable(
			name="STUDENTROLE_SCHOOL",
			joinColumns = {@JoinColumn(name = "ROLE_ID")},
			inverseJoinColumns = {@JoinColumn(name = "ITEM_ID")}
	)
	private Set<School> schools = new HashSet<School>();
	
	@OneToMany(mappedBy="member", cascade=CascadeType.ALL)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private Set<UserGroupMembership> groups = new HashSet<UserGroupMembership>();
	
	public StudentRole()
	{
		super();
	}
	
	public Set<School> getSchools()
	{
		return schools;
	}
	
	public void addSchool(final School school)
	{
		if (schools.contains(school)) {
			throw new BusinessRuleException("Cannot add student to school - student is already a member of this school.");
		}
		schools.add(school);
	}
	
	public void removeSchool(final School school) {
		if (!schools.contains(school)) {
			throw new BusinessRuleException("Cannot remove student from school - student is not a member of this school.");
		}
		schools.remove(school);
	}

	public Set<UserGroupMembership> getGroups()
	{
		return groups;
	}
	
	@Override
	public RoleType getRoleType()
	{
		return RoleType.STUDENT;
	}
}
