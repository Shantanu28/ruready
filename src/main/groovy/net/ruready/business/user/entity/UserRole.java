package net.ruready.business.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.eis.entity.PersistentEntity;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Object representing an individual user role.
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
 * @version Sep 20, 2007
 */
@Entity
@Table(name = "USER_ROLES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ROLE_TYPE", discriminatorType = DiscriminatorType.STRING, length = 15)
public abstract class UserRole implements Serializable, Comparable<UserRole>,
		PersistentEntity<Long>
{
	@Id
	@GeneratedValue
	@Column(name = "ROLE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", updatable = false)
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	@Version
	@Column(name = "OBJ_VERSION")
	private int version = 0;

	abstract public RoleType getRoleType();

	public UserRole()
	{
	}

	public String getName()
	{
		return getRoleType().toString();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public User getUser()
	{
		return user;
	}

	protected void setUser(final User user)
	{
		this.user = user;
	}

	public int getVersion()
	{
		return version;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreated()
	{
		return created;
	}

	public int compareTo(final UserRole userRole)
	{
		return new CompareToBuilder().append(getRoleType(), userRole.getRoleType())
				.toComparison();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 37).append(getRoleType()).append(getUser())
				.toHashCode();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof UserRole))
			return false;

		final UserRole that = (UserRole) o;

		if (this.getRoleType() != that.getRoleType())
			return false;

		// both users need to be null or equal
		if (this.getUser() != null ? !this.getUser().equals(that.getUser()) : that
				.getUser() != null)
			return false;

		return true;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id",
				getId()).append("version", getVersion()).append("userId",
				getUser() != null ? getUser().getId() : null).toString();
	}
}
