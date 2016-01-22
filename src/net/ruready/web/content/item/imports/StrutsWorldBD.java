/*****************************************************************************************
 * Source File: StrutsWorldBD.java
 ****************************************************************************************/
package net.ruready.web.content.item.imports;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.exports.DefaultWorldBD;
import net.ruready.business.content.world.manager.DefaultWorldManager;
import net.ruready.business.user.entity.User;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A world BD interface with a Struts resource locator. Methods also allow a specific user
 * to request the DAO operations on items.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
public class StrutsWorldBD extends DefaultWorldBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsWorldBD.class);

	// ========================= FIELDS ====================================

	/**
	 * Produces entity association manager objects.
	 */
	private final ApplicationContext context;

	/**
	 * The user requesting the item operations.
	 */
	private final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a business delegate for this user.
	 * 
	 * @param user
	 *            user requesting the operations
	 * @context web application context
	 */
	public StrutsWorldBD(final User user, final ApplicationContext context)
	{
		super(
				new DefaultWorldManager(WebAppResourceLocator.getInstance(), context,
						user), WebAppResourceLocator.getInstance());
		this.user = user;
		this.context = context;
	}

	// ========================= IMPLEMENTATION: DefaultParserBD =========

	// ========================= overridden METHODS: DefaultParserBD ======

	/**
	 * Search for the teacher link that matches a unique ID identifier. If this teacher
	 * link is not found, this method will not throw an exception, but when we try to
	 * access the returned object, Hibernate will throw an
	 * <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            item ID to search by
	 * @return teacher link, if found
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	@Override
	public School findSchoolById(long id)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Finding teacher link by id " + id);
		}
		// ============================================================
		// Find teacher link
		// ============================================================
		DAO<School, Long> schoolDAO = resourceLocator.getDAOFactory().getDAO(
				School.class, context);
		School school = schoolDAO.read(id);

		// ============================================================
		// Post processing and population of extra useful properties
		// ============================================================
		// Order children
		// school.refreshAll();

		return school;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @see net.ruready.business.content.catalog.exports.AbstractParserManager#getUser()
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * @see net.ruready.business.content.catalog.exports.AbstractWorldManager#setUser(net.ruready.business.user.entity.User)
	 */
	/*
	 * public void setUser(User user) { this.user = user; ((DefaultParserManager)
	 * manager).setUser(user); }
	 */
}
