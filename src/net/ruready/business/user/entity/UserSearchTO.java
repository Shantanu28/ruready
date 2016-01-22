package net.ruready.business.user.entity;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import net.ruready.business.user.entity.property.UserStatus;

public class UserSearchTO extends UserTO
{
	private static final long serialVersionUID = 5361533242399535909L;

	private final DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM);

	@SuppressWarnings("hiding")
	public static final String ID = "userId";

	private final String ageGroupDescription;
	private final String ethnicityDescription;
	private final String genderDescription;
	private final String languageDescription;
	private final String userRoleDescription;
	private final String userStatusDescription;

	public UserSearchTO(final User user, final String ageGroupDescription,
			final String ethnicityDescription, final String genderDescription,
			final String languageDescription, final String userRoleDescription,
			final String userStatusDescription)
	{
		super(user);
		this.ageGroupDescription = ageGroupDescription;
		this.ethnicityDescription = ethnicityDescription;
		this.genderDescription = genderDescription;
		this.languageDescription = languageDescription;
		this.userRoleDescription = userRoleDescription;
		this.userStatusDescription = userStatusDescription;
	}

	public UserSearchTO(final User user, final String ageGroupDescription,
			final String ethnicityDescription, final String genderDescription,
			final String languageDescription, final String userRoleDescription,
			final String userStatusDescription, final Map<String, Long> linkParams)
	{
		super(user, linkParams);
		this.ageGroupDescription = ageGroupDescription;
		this.ethnicityDescription = ethnicityDescription;
		this.genderDescription = genderDescription;
		this.languageDescription = languageDescription;
		this.userRoleDescription = userRoleDescription;
		this.userStatusDescription = userStatusDescription;
	}

	public String getAgeGroup()
	{
		return this.ageGroupDescription;
	}

	public String getEthnicity()
	{
		return this.ethnicityDescription;
	}

	public String getGender()
	{
		return this.genderDescription;
	}

	public String getLanguage()
	{
		return this.languageDescription;
	}

	public String getUserStatus()
	{
		return this.userStatusDescription;
	}

	public String getUserRole()
	{
		return this.userRoleDescription;
	}

	public UserStatus getUserStatusEnum()
	{
		return getUser().getUserStatus();
	}

	public String getStudentIdentifier()
	{
		return getUser().getStudentIdentifier();
	}

	public String getRegistrationDate()
	{
		return formatDate(getUser().getCreatedDate());
	}

	public String getLastLoginDate()
	{
		return formatDate(getUser().getLastLoggedInDate());
	}

	public Boolean getIsLoggedIn()
	{
		return getUser().getIsLoggedin();
	}

	private String formatDate(final Date date)
	{
		if (date == null)
			return "";
		return formatter.format(date);
	}
}
