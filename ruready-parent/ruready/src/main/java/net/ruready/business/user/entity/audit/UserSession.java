/*****************************************************************************************
 * Source File: UserSession.java
 ****************************************************************************************/
package net.ruready.business.user.entity.audit;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import net.ruready.business.user.entity.User;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.exception.SystemException;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.eis.audit.TimeSpan;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * An audit message generated per user session. The start date indicates login
 * date, and the end date indicates logout time.
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
 * @version Aug 12, 2007
 */
@Entity
public class UserSession implements PersistentEntity<Long>, Comparable<UserSession>
{
	// ========================= CONSTANTS =================================
	private static final long serialVersionUID = -803757158144457336L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(UserSession.class);

	// ========================= FIELDS ====================================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	/**
	 * The user that last modified the item to generate this message
	 * Automatically saving user when a message is saved. Fetch type is "eager"
	 * by default, so loading a message will automatically load the user.
	 */
	@ManyToOne
	@Cascade(
	{ CascadeType.SAVE_UPDATE, CascadeType.PERSIST })
	private User user;

	/**
	 * Time span: [loginDate, logoutDate]
	 */
	@Embedded
	private TimeSpan timeSpan = new TimeSpan();

	/**
	 * Screen height (pixels).
	 */
	@Column
	private int screenHeight = CommonNames.MISC.INVALID_VALUE_INTEGER;

	/**
	 * Screen width (pixels).
	 */
	@Column
	private int screenWidth = CommonNames.MISC.INVALID_VALUE_INTEGER;

	// ========================= CONSTRUCTORS ===============================

	/**
	 * Construct an empty message. A no-argument constructor with at least
	 * protected scope visibility is required for Hibernate and portability to
	 * other EJB 3 containers. This constructor populates no properties inside
	 * this class.
	 */
	protected UserSession()
	{

	}

	/**
	 * Create a message from fields.
	 * 
	 * @param item
	 *            the message is about this item
	 * @param user
	 *            last modifier of this item
	 * @param comment
	 *            optional comment
	 * @param screenHeight
	 *            screen height (pixels) string
	 * @param screenWidth
	 *            screen width (pixels) string
	 */
	public UserSession(final User user, final String comment, final String screenHeight,
			final String screenWidth)
	{
		super();
		this.user = user;
		this.screenHeight = TextUtil.getStringAsInteger(screenHeight);
		this.screenWidth = TextUtil.getStringAsInteger(screenWidth);

		login();
		// logger.debug("created, item " + item.getName() + " V" +
		// version + " action " + action);
	}

	// ========================= METHODS ====================================

	/**
	 * Print this object.
	 * 
	 * @return data and properties of this node, represented as a string
	 */
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer("User ");
		s.append(user.getEmail());
		s.append(" ID ");
		s.append(user.getId());
		s.append(" ");
		s.append(timeSpan);
		s.append(" screen ");
		s.append(screenWidth);
		s.append(" x ");
		s.append(screenHeight);
		return s.toString();
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> ====

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	// ========================= IMPLEMENTATION: Comparable<UserSession> ===

	/**
	 * Compare tags by name (lexicographic ordering).
	 * 
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(UserSession o)
	{
		if (o == null)
		{
			return 1;
		}

		if (o.getClass() != this.getClass())
		{
			throw new SystemException("Cannot compare " + this.getClass() + " with "
					+ o.getClass());
		}

		// Compare by descending date
		int compareDate = -timeSpan.compareTo(o.timeSpan);
		return compareDate;
	}

	// ========================= GETTERS & SETTERS ==========================

	public void login()
	{
		setStartDate(new Date());
		getUser().setIsLoggedin(true);
		getUser().setLastLoggedInDate(getStartDate());
	}

	public void logout()
	{
		setEndDate(new Date());
		getUser().setIsLoggedin(false);
		getUser().setLastLoggedInDate(getEndDate());
	}

	/**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight()
	{
		return screenHeight;
	}

	/**
	 * @param screenHeight
	 *            the screenHeight to set
	 */
	public void setScreenHeight(int screenHeight)
	{
		this.screenHeight = screenHeight;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth()
	{
		return screenWidth;
	}

	/**
	 * @param screenWidth
	 *            the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth)
	{
		this.screenWidth = screenWidth;
	}

	// ========================= DELEGATE METHODS ===========================

	/**
	 * @return
	 * @see net.ruready.eis.audit.TimeSpan#getEndDate()
	 */
	public Timestamp getEndDate()
	{
		return timeSpan.getEndDate();
	}

	/**
	 * @return
	 * @see net.ruready.eis.audit.TimeSpan#getStartDate()
	 */
	public Timestamp getStartDate()
	{
		return timeSpan.getStartDate();
	}

	/**
	 * @param endDate
	 * @see net.ruready.eis.audit.TimeSpan#setEndDate(java.util.Date)
	 */
	private void setEndDate(final Date endDate)
	{
		timeSpan.setEndDate(endDate);
	}

	/**
	 * @param startDate
	 * @see net.ruready.eis.audit.TimeSpan#setStartDate(java.util.Date)
	 */
	private void setStartDate(final Date startDate)
	{
		timeSpan.setStartDate(startDate);
	}
}
