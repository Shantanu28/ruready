/*****************************************************************************************
 * Source File: UserGroup.java
 ****************************************************************************************/
package net.ruready.business.user.entity;

import static org.apache.commons.lang.Validate.notEmpty;
import static org.apache.commons.lang.Validate.notNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.world.entity.School;
import net.ruready.common.eis.entity.PersistentEntity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents a group of users (a school class).
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
@Entity
@Table(uniqueConstraints =
{ @UniqueConstraint(columnNames =
{ UserGroup.NAME, UserGroup.COURSE_ID, UserGroup.SCHOOL_ID }) })
public class UserGroup implements PersistentEntity<Long>
{
	private static final long serialVersionUID = 1014222551670541872L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(UserGroup.class);

	// Uniquely identifying and other useful property references
	static final String NAME = "name";
	static final String COURSE_ID = "COURSE_ID";
	static final String SCHOOL_ID = "SCHOOL_ID";

	// ========================= FIELDS ====================================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	// ---------------- Participants ---------------------------------------

	/**
	 * The list's moderators (administrators). These are usually teachers and
	 * administrators. They have privileges to edit the group.
	 */

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private Set<UserGroupModerator> moderators = new HashSet<UserGroupModerator>();

	/**
	 * UserGroup groups ("classes") that the user belongs to and have
	 * personalized views.
	 */
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private Set<UserGroupMembership> members = new HashSet<UserGroupMembership>();

	@OneToOne
	@JoinColumn(name = COURSE_ID)
	private Course course;

	@OneToOne
	@JoinColumn(name = SCHOOL_ID)
	private School school;

	@OneToMany
	private Set<SubTopic> subtopics = new HashSet<SubTopic>();

	// ---------------- Data Fields ----------------------------------------

	// Group's name
	@Column(length = 80, name = NAME)
	private String name;

	// The status of the group--is it available for subscription?
	@Enumerated(value=EnumType.STRING)
	@Column(name="STATUS", nullable=false)
	private ActiveStatus activeStatus = ActiveStatus.ACTIVE;
	
	// ---------------- Auditing fields ------------------------------------

	// Date this user was created

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	@Version
	@Column(name = "OBJ_VERSION")
	private int version = 0;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Default constructor. Should not be called by application code.
	 */
	public UserGroup()
	{
	}

	public UserGroup(final Course course, final School school)
	{
		notNull(course, "course cannot be null.");
		createUserGroup(course.getName(), course, school);
	}

	public UserGroup(final String groupName, final Course course,
			final School school)
	{
		createUserGroup(groupName, course, school);
	}

	private void createUserGroup(final String groupName, final Course aCourse,
			final School aSchool)
	{
		notEmpty(groupName, "groupName cannot be empty.");
		notNull(aCourse, "course cannot be null.");
		notNull(aSchool, "school cannot be null.");

		setName(groupName);
		setCourse(aCourse);
		setSchool(aSchool);
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name)
	{
		this.name = name;
	}

	/**
	 * @return the course
	 */
	public Course getCourse()
	{
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(final Course course)
	{
		this.course = course;
	}

	public School getSchool()
	{
		return school;
	}

	public void setSchool(final School school)
	{
		this.school = school;
	}

	public Set<SubTopic> getSubTopics()
	{
		return this.subtopics;
	}

	public void addSubTopic(final SubTopic subTopic)
	{
		notNull(subTopic, "subTopic cannot be null");
		final Course aCourse = getCourseFromSubTopic(subTopic);

		notNull(
				aCourse,
				"subTopic must be defined under the structure course -> content knowledge -> topic.");
		if (!getCourse().equals(aCourse))
		{
			throw new BusinessRuleException("Cannot add SubTopic \""
					+ subTopic.getName()
					+ "\". It is not a valid SubTopic under course \""
					+ getCourse().getName() + "\".");
		}
		this.subtopics.add(subTopic);
	}

	public void removeSubTopic(final SubTopic subTopic)
	{
		notNull(subTopic, "subTopic cannot be null");
		this.subtopics.remove(subTopic);
	}
	
	/**
	 * Returns whether this user group is marked as deleted
	 * 
	 * @return true, if the user group is marked for deletion, otherwise false
	 */
	public Boolean isDeleted()
	{
		return (this.activeStatus == ActiveStatus.DISABLED);
	}
	
	/**
	 * Returns whether this user group is marked as deleted
	 * 
	 * This is a convenience method for accessing the property in JSP pages.
	 * 
	 * @return true, if the user group is marked for deletion, otherwise false
	 */
	public Boolean getIsDeleted()
	{
		return isDeleted();
	}
	
	public void delete()
	{
		this.activeStatus = ActiveStatus.DISABLED;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreated()
	{
		return created;
	}

	public int getVersion()
	{
		return version;
	}

	public Set<UserGroupMembership> getMembership()
	{
		return members;
	}

	public Boolean hasPrimaryModerator()
	{
		return (getPrimaryModerator() != null);
	}

	public UserGroupModerator getPrimaryModerator()
	{
		for (UserGroupModerator moderator : getModerators())
		{
			if (moderator.isOwner())
			{
				return moderator;
			}
		}
		return null;
	}

	public Set<UserGroupModerator> getModerators()
	{
		return moderators;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(-1578730987, 2142270667).append(getName())
				.append(getCourse()).append(getSchool()).toHashCode();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof UserGroup))
			return false;

		final UserGroup that = (UserGroup) o;
		return new EqualsBuilder().append(this.getName(), that.getName())
				.append(this.getCourse(), that.getCourse()).append(
						this.getSchool(), that.getSchool()).isEquals();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", getId()).append("name", getName()).append(
						"school", getSchool()).append("course", getCourse())
				.toString();
	}

	private Course getCourseFromSubTopic(final SubTopic subTopic)
	{
		Course courz;
		try
		{
			// subtopic -> topic -> content knowledge -> course
			courz = (Course) subTopic.getParent().getParent().getParent();
		}
		// should never happen unless a subtopic is not properly initialized
		catch (final NullPointerException npe)
		{
			logger.warn("SubTopic " + subTopic.getName()
					+ " is not under a course.");
			courz = null;
		}
		return courz;
	}
}
