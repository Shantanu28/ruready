package net.ruready.business.user.entity;

import static org.apache.commons.lang.Validate.notNull;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * Represents the relationship from moderator to group.
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
* @version Sep 26, 2007
*/
@Entity
public class UserGroupModerator implements PersistentEntity<UserGroupModerator.Id>
{
	private static final long serialVersionUID = 8102420140540075141L;

	private static final String MODERATOR_COLUMN = "MODERATOR_ID";
	private static final String GROUP_COLUMN = "GROUP_ID";
	
	@Embeddable
    public static class Id implements Serializable {
		
		private static final long serialVersionUID = 3015306396836395207L;

		@Column(name = MODERATOR_COLUMN)
		private Long moderatorId;
		
		@Column(name = GROUP_COLUMN)
		private Long groupId;
        
        public Id() {}

        public Id(final Long moderatorId, final Long groupId) {
			this.moderatorId = moderatorId;
			this.groupId = groupId;
		}

        @Override
		public int hashCode() {
			return moderatorId.hashCode() + groupId.hashCode();
		}

        @Override
		public boolean equals(final Object o) {
			if (o instanceof Id) {
				Id that = (Id)o;
				return new EqualsBuilder()
				.append(this.moderatorId, that.moderatorId)
				.append(this.groupId, that.groupId)
				.isEquals();
			} else {
				return false;
			}
		}
	}

	@EmbeddedId
	private Id id;

	@Column(name="OWNER", nullable=false)
	private Boolean isOwner;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
	private Date created = new Date();
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name=MODERATOR_COLUMN, insertable = false, updatable = false)
	private TeacherRole moderator;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name=GROUP_COLUMN, insertable = false, updatable = false)
	private UserGroup group;
	
	/**
	 * Default Constructor
	 */
	public UserGroupModerator() {}
	
	public UserGroupModerator(final User user, final UserGroup userGroup)
	{
		this(user,userGroup,false);
	}
	
	public UserGroupModerator(final User user, final UserGroup userGroup,
			final Boolean isOwner)
	{
		notNull(user, "user cannot be null.");
		assertUserIsATeacher(user);
		
		createUserGroupModerator((TeacherRole) user.getRole(RoleType.TEACHER), userGroup, isOwner);
	}
	
	public UserGroupModerator(final TeacherRole teacher, final UserGroup userGroup)
	{
		this(teacher,userGroup,false);
	}
	
	public UserGroupModerator(final TeacherRole teacher, final UserGroup userGroup, 
			final Boolean isOwner)
	{
		createUserGroupModerator(teacher, userGroup, isOwner);
	}

	public Id getId()
	{
		return id;
	}

	/**
	 * Returns whether this moderator relationship is a primary moderator relationship
	 * 
	 * Added for compatibility with JSP EL
	 * 
	 * @return true if this is a primary moderator, false otherwise
	 */
	public Boolean getIsOwner()
	{
		return isOwner();
	}
	
	/**
	 * Returns whether this moderator relationship is a primary moderator relationship
	 * @return true if this is a primary moderator, false otherwise
	 */
	public Boolean isOwner()
	{
		return isOwner;
	}
	
	public void setOwner(final Boolean isOwner)
	{
		notNull(isOwner, "isOwner cannot be null.");
		this.isOwner = isOwner;
	}

	public Date getCreated()
	{
		return created;
	}

	public TeacherRole getModerator()
	{
		return moderator;
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
        if (!(o instanceof UserGroupModerator)) return false;
        
        final UserGroupModerator that = (UserGroupModerator) o;
        
        return new EqualsBuilder().append(this.getId(),that.getId()).isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("moderator", getModerator())
		.append("group", getGroup())
		.toString();
	}

	private void createUserGroupModerator(final TeacherRole teacher,
			final UserGroup userGroup, final Boolean isOwnerArg)
	{
		notNull(teacher, "teacher cannot be null.");
		notNull(userGroup, "userGroup cannot be null.");
		assertNotAlreadyModeratorOfGroup(teacher, userGroup);
		if (isOwnerArg)
		{
			assertNoPrimaryModeratorOnGroup(userGroup);
		}
		
		this.moderator = teacher;
		this.group = userGroup;
		this.isOwner = isOwnerArg;
		
		// create the composite key for this record
		this.id = new Id(teacher.getId(),userGroup.getId());
		
		// tie the teacher and group to this object
		teacher.getGroups().add(this);
		userGroup.getModerators().add(this);
	}
	
	private void assertUserIsATeacher(final User user)
	{
		if (!user.hasRole(RoleType.TEACHER)) {
			throw new BusinessRuleException("Cannot add user to group as a moderator - user is not a teacher");
		}
	}
	
	private void assertNotAlreadyModeratorOfGroup(final TeacherRole teacher, 
			final UserGroup userGroup)
	{
		for (UserGroupModerator aModerator: teacher.getGroups()) {
			if (aModerator.getGroup().equals(userGroup)) {
				throw new BusinessRuleException("Cannot add teacher to group - teacher is already a moderator for the group.");
			}
		}
	}
	
	private void assertNoPrimaryModeratorOnGroup(final UserGroup userGroup)
	{
		if (userGroup.hasPrimaryModerator()) {
			throw new BusinessRuleException("Cannot add teacher to group as a primary moderator - this group already has a primary moderator.");
		}
	}
}
