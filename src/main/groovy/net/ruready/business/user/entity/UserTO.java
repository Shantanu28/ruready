package net.ruready.business.user.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserTO implements Serializable, Comparable<UserTO>
{
	private static final long serialVersionUID = -2476710370506745337L;

	public static final String ID = "userId";
	
	private final User user;
	private final Map<String, Long> linkParams;
	
	public UserTO(final User user)
	{
		this.user = user;
		
		this.linkParams = new HashMap<String, Long>();
		this.linkParams.put(ID, this.user.getId());
	}
	
	public UserTO(final User user, final Map<String, Long> linkParams)
	{
		this.user = user;
		
		this.linkParams = new HashMap<String, Long>();
		if (linkParams != null)
		{
			this.linkParams.putAll(linkParams);
		}
		else
		{
			this.linkParams.put(ID, this.user.getId());
		}	
	}
	
	public User getUser()
	{
		return this.user;
	}
	
	public Long getId()
	{
		return getUser().getId();
	}
	
	public String getEmail()
	{
		return getUser().getEmail();
	}
	
	public String getFirstName()
	{
		return (getNameInternal() == null) ? "" : getNameInternal().getFirstName();
	}
	
	public String getMiddleInitial()
	{
		return (getNameInternal() == null) ? "" : getNameInternal().getMiddleInitial();
	}
	
	public String getLastName()
	{
		return (getNameInternal() == null) ? "" : getNameInternal().getLastName();
	}
	
	public String getName()
	{
		return formatName(getNameInternal());
	}

	public Map<String, Long> getLinkParams()
	{
		return linkParams;
	}

	public int compareTo(final UserTO o)
	{
		return new CompareToBuilder()
			.append(this.getEmail(), o.getEmail())
			.toComparison();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17,37)
			.append(getEmail())
			.toHashCode();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o) return true;
		if (!(o instanceof UserTO)) return false;

		final UserTO that = (UserTO) o;

		return new EqualsBuilder()
			.append(this.getEmail(), that.getEmail())
			.isEquals();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", getId())
			.append("email", getEmail())
			.append("name", getName())
			.toString();
	}
	
	private Name getNameInternal()
	{
		return getUser().getName();
	}
	
	private String formatName(final Name name)
	{
		if (name == null) return "";
		final StringBuilder nameBuilder = new StringBuilder();
		if (StringUtils.isNotBlank(name.getLastName()))
		{
			nameBuilder.append(name.getLastName()).append(", ");
		}
		nameBuilder.append(name.getFirstName());
		if (StringUtils.isNotBlank(name.getMiddleInitial()))
		{
			nameBuilder.append(" ").append(name.getMiddleInitial()).append(".");
		}
		return nameBuilder.toString();
	}
}
