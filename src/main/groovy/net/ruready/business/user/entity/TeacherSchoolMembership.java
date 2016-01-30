package net.ruready.business.user.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

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
import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.entity.PersistentEntity;

import static org.apache.commons.lang.Validate.notNull;
import static org.apache.commons.lang.Validate.notEmpty;

/**
 * Represents the relationship from teacher to school.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Jeremy Lund
 * @version Sep 27, 2007
 */
@Entity
public class TeacherSchoolMembership implements
		PersistentEntity<TeacherSchoolMembership.Id>
{
	private static final long serialVersionUID = -5344356808255138094L;
	private static final String MEMBER_COLUMN = "MEMBER_ID";
	private static final String SCHOOL_COLUMN = "SCHOOL_ID";

	@Embeddable
	public static class Id implements Serializable
	{
		private static final long serialVersionUID = 6328166232275887990L;

		@Column(name = MEMBER_COLUMN)
		private Long memberId;

		@Column(name = SCHOOL_COLUMN)
		private Long schoolId;

		public Id()
		{
		}

		public Id(final Long memberId, final Long schoolId)
		{
			this.memberId = memberId;
			this.schoolId = schoolId;
		}

		@Override
		public int hashCode()
		{
			return memberId.hashCode() + schoolId.hashCode();
		}

		@Override
		public boolean equals(final Object o)
		{
			if (o instanceof Id)
			{
				Id that = (Id) o;
				return new EqualsBuilder().append(this.memberId, that.memberId).append(
						this.schoolId, that.schoolId).isEquals();
			}
			else
			{
				return false;
			}
		}
	}

	@EmbeddedId
	private Id id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private ActiveStatus memberStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STATUS_DATE", nullable = false)
	private Date statusDate = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = MEMBER_COLUMN, insertable = false, updatable = false)
	private TeacherRole member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = SCHOOL_COLUMN, insertable = false, updatable = false)
	private School school;

	@Column(name = "STATUS_REASON")
	private String statusReason;

	public TeacherSchoolMembership()
	{
	}

	public TeacherSchoolMembership(final User user, final School school)
	{
		this(user, school, ActiveStatus.PENDING);
	}

	public TeacherSchoolMembership(final User user, final School school,
			final ActiveStatus memberStatus)
	{
		notNull(user, "user cannot be null.");
		assertUserIsATeacher(user);

		createTeacherSchoolMembership((TeacherRole) user.getRole(RoleType.TEACHER),
				school, memberStatus);
	}

	public TeacherSchoolMembership(final TeacherRole teacher, final School school)
	{
		this(teacher, school, ActiveStatus.PENDING);
	}

	public TeacherSchoolMembership(final TeacherRole teacher, final School school,
			final ActiveStatus memberStatus)
	{
		createTeacherSchoolMembership(teacher, school, memberStatus);
	}

	private void createTeacherSchoolMembership(final TeacherRole teacher,
			final School aSchool, final ActiveStatus aMemberStatus)
	{
		notNull(teacher, "teacher cannot be null.");
		notNull(aSchool, "school cannot be null.");
		assertTeacherDoesNotAlreadyBelongToSchool(teacher, aSchool);

		this.member = teacher;
		this.school = aSchool;
		setMemberStatus(aMemberStatus);

		// create the composite key for this record
		this.id = new Id(teacher.getId(), aSchool.getId());

		// tie the teacher and school to this object
		teacher.getSchools().add(this);
		aSchool.getTeachers().add(this);
	}

	public Id getId()
	{
		return id;
	}

	public ActiveStatus getMemberStatus()
	{
		return memberStatus;
	}

	public void updateMemberStatus(final ActiveStatus aMemberStatus, final String reason)
	{
		notEmpty(reason, "reason cannot be empty");
		if (aMemberStatus == getMemberStatus())
		{
			throw new BusinessRuleException("MemberStatus is already \"" + memberStatus
					+ "\". Cannot update.");
		}
		setMemberStatus(aMemberStatus);
		setMemberStatusReason(reason);
	}

	public String getMemberStatusReason()
	{
		return this.statusReason;
	}

	private void setMemberStatusReason(final String statusReason)
	{
		this.statusReason = statusReason;
	}

	public Date getStatusDate()
	{
		return statusDate;
	}

	public TeacherRole getMember()
	{
		return member;
	}

	public School getSchool()
	{
		return school;
	}

	private void setMemberStatus(final ActiveStatus memberStatus)
	{
		this.memberStatus = memberStatus;
		this.statusDate = new Date();
	}

	private void assertUserIsATeacher(final User user)
	{
		if (!user.hasRole(RoleType.TEACHER))
		{
			throw new BusinessRuleException(
					"Cannot add this saved school relationship - user is not a teacher.");
		}
	}

	private void assertTeacherDoesNotAlreadyBelongToSchool(final TeacherRole teacher,
			final School aSchool)
	{
		for (TeacherSchoolMembership membership : teacher.getSchools())
		{
			if (membership.getSchool().equals(aSchool))
			{
				throw new BusinessRuleException(
						"Cannot add this saved school relationship - teacher already belongs to this school.");
			}
		}
	}
}
