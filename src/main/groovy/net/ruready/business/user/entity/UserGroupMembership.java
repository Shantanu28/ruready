package net.ruready.business.user.entity;

import static org.apache.commons.lang.Validate.notNull;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.entity.PersistentEntity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Represents a user's membership in a group
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
* @version Sep 25, 2007
*/
@Entity
public class UserGroupMembership implements PersistentEntity<UserGroupMembership.Id>
{
	private static final long serialVersionUID = 3386042736907575289L;
	
	private static final String MEMBER_COLUMN = "MEMBER_ID";
	private static final String GROUP_COLUMN = "GROUP_ID";
	
	@Embeddable
    public static class Id implements Serializable {
	
		private static final long serialVersionUID = 73996805758281306L;

		@Column(name = MEMBER_COLUMN)
		private Long studentId;
		
		@Column(name = GROUP_COLUMN)
		private Long groupId;
        
        public Id() {}

        public Id(final Long studentId, final Long groupId) {
			this.studentId = studentId;
			this.groupId = groupId;
		}

        @Override
		public int hashCode() {
			return studentId.hashCode() + groupId.hashCode();
		}

        @Override
		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id)o;
				return new EqualsBuilder()
				.append(this.studentId, that.studentId)
				.append(this.groupId, that.groupId)
				.isEquals();
			} else {
				return false;
			}
		}
	}

	@EmbeddedId
	private Id id;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="STATUS", nullable=false)
	private ActiveStatus memberStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="STATUS_DATE", nullable = false)
	private Date statusDate = new Date();
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name=MEMBER_COLUMN, insertable = false, updatable = false)
	private StudentRole member;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name=GROUP_COLUMN, insertable = false, updatable = false)
	private UserGroup group;
	
	public UserGroupMembership() {}
	
	public UserGroupMembership(final User user, final UserGroup userGroup)
	{
		this(user,userGroup,ActiveStatus.PENDING);
	}
	
	public UserGroupMembership(final User user, final UserGroup userGroup, 
			final ActiveStatus memberStatus)
	{
		notNull(user, "user cannot be null.");
		assertUserIsAStudent(user);
		
		createUserGroupmembership(
				(StudentRole) user.getRole(RoleType.STUDENT), 
				userGroup, 
				memberStatus);
	}
	
	public UserGroupMembership(final StudentRole student, final UserGroup userGroup)
	{
		this(student,userGroup,ActiveStatus.PENDING);
	}
	
	public UserGroupMembership(final StudentRole student, final UserGroup userGroup, 
			final ActiveStatus memberStatus)
	{
		createUserGroupmembership(student, userGroup, memberStatus);
	}

	public Id getId()
	{
		return id;
	}

	public ActiveStatus getMemberStatus()
	{
		return memberStatus;
	}
	
	public void updateMemberStatus(final ActiveStatus aMemberStatus)
	{
		if (aMemberStatus == getMemberStatus()) {
			throw new BusinessRuleException("MemberStatus is already \"" + aMemberStatus + "\". Cannot update.");
		}
		setMemberStatus(aMemberStatus);
	}

	public Date getStatusDate()
	{
		return statusDate;
	}

	public StudentRole getMember()
	{
		return member;
	}

	public UserGroup getGroup()
	{
		return group;
	}
	
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}
	
	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
        if (!(o instanceof UserGroupMembership)) return false;
        
        final UserGroupMembership that = (UserGroupMembership) o;
        
        return new EqualsBuilder().append(this.getId(),that.getId()).isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("status", getMemberStatus())
		.append("statusDate", getStatusDate())
		.append("member", getMember())
		.append("group", getGroup())
		.toString();
	}

	private void createUserGroupmembership( final StudentRole student, 
			final UserGroup userGroup, final ActiveStatus aMemberStatus)
	{
		// Business rule checks
		notNull(student, "student cannot be null.");
		notNull(userGroup, "userGroup cannot be null.");
		assertNotAlreadyMemberOfGroup(student, userGroup);
		
		this.member = student;
		this.group = userGroup;
		setMemberStatus(aMemberStatus);
		
		// create the composite key for this record
		this.id = new Id(student.getId(),userGroup.getId());
		
		// tie the student and group to this object
		student.getGroups().add(this);
		userGroup.getMembership().add(this);
	}

	private void setMemberStatus(final ActiveStatus memberStatus)
	{
		this.memberStatus = memberStatus;
		this.statusDate = new Date();
	}
	
	private void assertUserIsAStudent(final User user)
	{
		if (!user.hasRole(RoleType.STUDENT)) {
			throw new BusinessRuleException("Cannot add user to group - user is not a student");
		}
	}
	
	private void assertNotAlreadyMemberOfGroup( final StudentRole student, 
			final UserGroup userGroup)
	{
		for (UserGroupMembership membership: student.getGroups()) {
			if (membership.getGroup().equals(userGroup)) {
				throw new BusinessRuleException("Cannot add student to group - student is already a member of the group.");
			}
		}
	}
}
