/*****************************************************************************************
 * Source File: User.java
 ****************************************************************************************/
package net.ruready.business.user.entity;

import static org.apache.commons.lang.Validate.isTrue;
import static org.apache.commons.lang.Validate.notNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.user.entity.audit.UserSession;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserStatus;
import net.ruready.business.user.rl.UserNames;
import net.ruready.common.discrete.Gender;
import net.ruready.common.eis.entity.PersistentEntity;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

import test.ruready.common.password.TestRSA;

/**
 * A user entity. A user represents a person logging into the site and
 * performing some business operations. Uniquely identified by an email address.
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
 * @author Jeremy Lund
 * @version September 19, 2007
 */
@Entity
public class User implements PersistentEntity<Long>
{
	// ========================= CONSTANTS =================================
	private static final long serialVersionUID = -1921100472256739909L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(User.class);

	/**
	 * Uniquely identifying and other useful property references: email
	 * property.
	 */
	public static final String EMAIL = "email";

	public static final String PASSWORD = "password";

	/**
	 * Uniquely identifying and other useful property references: date on which
	 * this user account was created.
	 */
	public static final String CREATED_DATE = "createdDate";

	// ========================= FIELDS ====================================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	/**
	 * E-mail address. A unique identifier of the user.
	 */
	@Column(name = EMAIL, length = 255, unique = true)
	private String email;

	// ---------------- Administrative information (required) --------------

	/**
	 * Password. Maximum length is determined by parameters in {@link UserNames}.
	 * See also {@link TestRSA#testPasswordLength()}.
	 */
	@Column(name = PASSWORD, length = 30)
	private String password;

	/**
	 * List of administrative roles for page access restriction Cannot be
	 * eagerly fetched because this entity has multiple one-to-many or
	 * many-to-many associations.
	 * 
	 * @see http://www.jroller.com/page/eyallupu?entry=hibernate_exception_simultaneously_fetch_multiple
	 */
	@OneToMany(cascade =
	{ CascadeType.ALL }, mappedBy = "user", fetch = FetchType.EAGER)
	@Sort(type = SortType.NATURAL)
	private SortedSet<UserRole> roles = new TreeSet<UserRole>();

	@Enumerated(value = EnumType.STRING)
	@Column
	private RoleType highestRole;

	// ---------------- Biographical information (required) ----------------

	/**
	 * Gender.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private Gender gender;

	/**
	 * Age group.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private AgeGroup ageGroup;

	/**
	 * Ethnicity/race.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private Ethnicity ethnicity;

	/**
	 * Native language.
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private Language language;

	/**
	 * Person's name fields (optional).
	 */
	@Embedded
	private Name name = new Name();

	// ---------------- Geographical location (optional) ----------------

	/**
	 * Student identification number. May contain numbers, letters and spaces.
	 */
	@Column(length = 40)
	private String studentIdentifier;

	// ---------------- Login-related parameters ----------------

	/**
	 * Date this user was created. Originally we used
	 * 
	 * <pre>
	 * at-Generated(GenerationTime.INSERT) at-Column(name = &quot;created&quot;,
	 * insertable = false, updatable = false) 
	 * </pre>
	 * 
	 * but for some strange reason, the annotations commented above do not
	 * insert the column upon creating a user row. So we do it manually instead.
	 * <p>
	 * 12-NOV-07: converted this property to utcTimestamp type, for uniformity
	 * and to avoid MySQL truncation of date fields. See
	 * {@link #lastLoggedInDate}.
	 */
	@Column(updatable = false)
	@Type(type = "utcTimestamp")
	private Timestamp createdDate = new Timestamp(new Date().getTime());

	@Version
	@Column(name = "OBJ_VERSION")
	private int version = 0;

	@Column(nullable = false)
	private Boolean isLoggedin = false;

	@Column
	@Type(type = "utcTimestamp")
	private Timestamp lastLoggedInDate;

	/**
	 * Latest audit message. Also appears in the {@link #messages} set, but is
	 * easily accessed by this direct reference.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private UserSession latestUserSession = null;

	/**
	 * A set of user session audit messages logging the actions performed on
	 * this item by users. Could be large, which is why we use a bi-directional
	 * association with SAVE cascades on both types. Cascading now includes
	 * removing.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(value = LazyCollectionOption.EXTRA)
	@IndexColumn(name = "index_user_session")
	private List<UserSession> userSessions = new ArrayList<UserSession>();

	/**
	 * User's default perspective (if he/she has multiple roles).
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private RoleType defaultPerspective;

	@Enumerated(value = EnumType.STRING)
	@Column
	private UserStatus userStatus = UserStatus.ACTIVE;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty item whose items are held as a sorted tree with toggling.
	 * Must be public because {@link User} is a JavaBean.
	 */
	public User()
	{
		super();
	}

	/**
	 * Construct a user from required fields.
	 * 
	 * @param email
	 * @param gender
	 * @param ageGroup
	 * @param ethnicity
	 * @param language
	 */
	public User(String email, Gender gender, AgeGroup ageGroup, Ethnicity ethnicity,
			Language language)
	{
		super();
		this.email = email;
		this.gender = gender;
		this.ageGroup = ageGroup;
		this.ethnicity = ethnicity;
		this.language = language;
	}

	// ========================= METHODS ===================================

	public UserRole getRole(final RoleType userRole)
	{
		for (UserRole role : getRoles())
		{
			if (role.getRoleType() == userRole)
			{
				return role;
			}
		}
		return null;
	}

	public List<UserRole> addRole(final RoleType roleType)
	{
		notNull(roleType, "roleType cannot be null.");
		return addRole(roleType.newInstance());
	}

	public List<UserRole> addRole(final UserRole userRole)
	{
		notNull(userRole, "userRole cannot be null.");

		if (hasRole(userRole.getRoleType()))
		{
			throw new BusinessRuleException("User already has the role: "
					+ userRole.getRoleType());
		}
		final List<UserRole> addedRoles = addIntermediateRoles(userRole);

		setHighestRole();
		setDefaultPerspective(getHighestRole());
		return addedRoles;
	}

	public List<UserRole> removeRole(final UserRole userRole)
	{
		notNull(userRole, "userRole cannot be null.");
		return removeRole(userRole.getRoleType());
	}

	public List<UserRole> removeRole(final RoleType roleType)
	{
		notNull(roleType, "roleType cannot be null.");

		final List<UserRole> removedRoles;
		if (hasRole(roleType))
		{
			removedRoles = removeIntermediateRoles(roleType);
		}
		else
		{
			removedRoles = Collections.emptyList();
		}
		setHighestRole();
		setDefaultPerspective(getHighestRole());
		return removedRoles;
	}

	/**
	 * @param role
	 *            the enumerated type for the UserRole
	 * @return true if user has the specified role, otherwise false
	 */
	public boolean hasRole(final RoleType role)
	{
		return getRolesAsRoleTypes().contains(role);
	}

	public boolean hasAnyRole(final Set<RoleType> testRoles)
	{
		notNull(testRoles, "testRoles cannot be null.");
		return CollectionUtils.containsAny(getRolesAsRoleTypes(), testRoles);
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> =====

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return getName().getFirstName();
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		getName().setFirstName(firstName);
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return getName().getLastName();
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName)
	{
		getName().setLastName(lastName);
	}

	/**
	 * @return the middleInitial
	 */
	public String getMiddleInitial()
	{
		return getName().getMiddleInitial();
	}

	/**
	 * @param middleInitial
	 *            the middleInitial to set
	 */
	public void setMiddleInitial(String middleInitial)
	{
		getName().setMiddleInitial(middleInitial);
	}

	/**
	 * @return the name
	 */
	public Name getName()
	{
		if (this.name == null)
		{
			this.name = new Name();
		}
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(Name name)
	{
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the ageGroup
	 */
	public AgeGroup getAgeGroup()
	{
		return ageGroup;
	}

	/**
	 * @param ageGroup
	 *            the ageGroup to set
	 */
	public void setAgeGroup(AgeGroup ageGroup)
	{
		this.ageGroup = ageGroup;
	}

	/**
	 * @return the ethnicity
	 */
	public Ethnicity getEthnicity()
	{
		return ethnicity;
	}

	/**
	 * @param ethnicity
	 *            the ethnicity to set
	 */
	public void setEthnicity(Ethnicity ethnicity)
	{
		this.ethnicity = ethnicity;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender()
	{
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	/**
	 * @return the language
	 */
	public Language getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(Language language)
	{
		this.language = language;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate()
	{
		return createdDate;
	}

	public int getVersion()
	{
		return version;
	}

	public RoleType getHighestRole()
	{
		return this.highestRole;
	}

	/**
	 * @return the roles
	 */
	public SortedSet<UserRole> getRoles()
	{
		return roles;
	}

	/**
	 * @return the studentIdentifier
	 */
	public String getStudentIdentifier()
	{
		return studentIdentifier;
	}

	/**
	 * @param studentIdentifier
	 *            the studentIdentifier to set
	 */
	public void setStudentIdentifier(final String studentIdentifier)
	{
		this.studentIdentifier = studentIdentifier;
	}

	public RoleType getDefaultPerspective()
	{
		return this.defaultPerspective;
	}

	public void setDefaultPerspective(final RoleType perspective)
	{
		if (perspective == null)
		{
			isTrue(getRoles().isEmpty(),
					"Cannot set a null default perspective when user has at least one role");
			this.defaultPerspective = null;
		}
		else
		{
			isTrue(!getRoles().isEmpty(),
					"Cannot set a default perspective on a user with no roles");
			isTrue(hasRole(perspective),
					"Cannot set the default perspective to a role that the user does not have");
			this.defaultPerspective = perspective;
		}
	}

	public UserStatus getUserStatus()
	{
		return userStatus;
	}

	public void setUserStatus(final UserStatus userStatus)
	{
		this.userStatus = userStatus;
	}

	/**
	 * @return the latestUserSession
	 */
	public UserSession getLatestUserSession()
	{
		return latestUserSession;
	}

	/**
	 * Add a user session and set it to be the latest user session associated
	 * with this object.
	 * 
	 * @param latestUserSession
	 *            the user session to add
	 * @see net.ruready.common.audit.Auditable#addMessage(net.ruready.common.audit.Message)
	 */
	public void addUserSession(final UserSession session)
	{
		// latestMessage = message.clone();
		latestUserSession = session;
		userSessions.add(session);
		// getMessages().add(message);
	}

	/**
	 * @return the userSessions
	 */
	public List<UserSession> getUserSessions()
	{
		return userSessions;
	}

	/**
	 * @param userSessions
	 *            the userSessions to set
	 */
	public void setUserSessions(final List<UserSession> userSessions)
	{
		this.userSessions = userSessions;
	}

	public Boolean getIsLoggedin()
	{
		return isLoggedin;
	}

	public void setIsLoggedin(Boolean isLoggedin)
	{
		this.isLoggedin = isLoggedin;
	}

	public Timestamp getLastLoggedInDate()
	{
		return lastLoggedInDate;
	}

	public void setLastLoggedInDate(Timestamp lastLoggedInDate)
	{
		this.lastLoggedInDate = lastLoggedInDate;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 37).append(this.getEmail()).toHashCode();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;

		final User that = (User) o;

		return new EqualsBuilder().append(this.getEmail(), that.getEmail()).isEquals();
	}

	/**
	 * Print this form bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id",
				getId()).append("email", getEmail()).append("password", getPassword())
				.append("gender", getGender()).append("ageGroup", getAgeGroup()).append(
						"ethnicity", getEthnicity()).append("language", getLanguage())
				.append("studentID", getStudentIdentifier()).append("roles", getRoles())
				.toString();
	}

	private void setHighestRole()
	{
		if (getRoles().isEmpty())
		{
			this.highestRole = null;
		}
		else
		{
			this.highestRole = getRoles().last().getRoleType();
		}
	}

	private EnumSet<RoleType> getRolesAsRoleTypes()
	{
		if (roles.isEmpty())
			return EnumSet.noneOf(RoleType.class);
		return EnumSet.range(roles.first().getRoleType(), roles.last().getRoleType());
	}

	private List<UserRole> addIntermediateRoles(final UserRole userRole)
	{
		final List<UserRole> addedRoles = new ArrayList<UserRole>();
		for (RoleType type : EnumSet.range(getHighestRoleTypeEndpoint(), userRole
				.getRoleType()))
		{
			if (type != userRole.getRoleType())
			{
				addedRoles.add(doAddRole(type.newInstance()));
			}
			else
			{
				addedRoles.add(doAddRole(userRole));
			}
		}
		return addedRoles;
	}

	private UserRole doAddRole(final UserRole userRole)
	{
		roles.add(userRole);
		userRole.setUser(this);
		return userRole;
	}

	private UserRole doRemoveRole(final UserRole userRole)
	{
		roles.remove(userRole);
		userRole.setUser(null);
		return userRole;
	}

	private RoleType getHighestRoleTypeEndpoint()
	{
		final RoleType theHighestRole = getHighestRole();
		if (theHighestRole == null)
			return RoleType.values()[0];
		return theHighestRole;
	}

	private List<UserRole> removeIntermediateRoles(final RoleType roleType)
	{
		final List<UserRole> removedRoles = new ArrayList<UserRole>();
		for (RoleType type : EnumSet.range(roleType, getHighestRoleTypeEndpoint()))
		{
			removedRoles.add(doRemoveRole(getRole(type)));
		}
		return removedRoles;
	}
}
