/*****************************************************************************************
 * Source File: HibernateUserDAO.java
 ****************************************************************************************/
package net.ruready.eis.user;

import static org.apache.commons.lang.Validate.notEmpty;

import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.TeacherSchoolMembership;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Data access object (DAO) Hibernate implementation for domain model class {@link User}.
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
 * @see net.ruready.item.entity.Use
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
public class HibernateUserDAO extends HibernateDAO<User, Long> implements UserDAO
{
	// ========================= CONSTANTS =================================

	static final Log logger = LogFactory.getLog(HibernateUserDAO.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a DAO from a session and class type.
	 * 
	 * @param persistentClass
	 *            entity's class type
	 * @param session
	 *            Hibernate session
	 * @param sessionFactory
	 *            a heavy-weight session factory that can provide new sessions after old
	 *            ones has closed or have been corrupted.
	 * @param associationManagerFactory
	 *            Produces entity association manager objects.
	 */
	public HibernateUserDAO(final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(User.class, sessionFactory, context);
	}

	// ========================= IMPLEMENTATION: HibernateDAO =======
	
	/**
	 * @see net.ruready.eis.user.UserDAO#authenticate(java.lang.String, java.lang.String)
	 */
	public User authenticate(String email, String password)
	{
		notEmpty(email, "email cannot be empty.");
		notEmpty(password, "password cannot be empty.");

		return (User) getSession().createCriteria(getPersistentClass()).add(
				Restrictions.eq(User.EMAIL, email)).add(
				Restrictions.eq(User.PASSWORD, password)).uniqueResult();
	}

	public void deleteRoles(final List<UserRole> rolesToRemove)
	{
		logger.debug("Removing roles");
		final Session session = getSession();
		for (UserRole role : rolesToRemove)
		{
			session.delete(role);
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> getStudentSchoolMembershipsBySchool(final School school)
	{
		final String queryString = "select sr from StudentRole sr join sr.schools s where s.id = :id order by sr.user.email";
		return getSession()
			.createQuery(queryString)
			.setLockMode("sr", lockMode)
			.setParameter("id", school.getId())
			.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeacherSchoolMembership> getTeacherSchoolMembershipsByStatus(
			final ActiveStatus activeStatus)
	{
		final String queryString = "select t from TeacherSchoolMembership t where t.memberStatus = :activeStatus";
		return getSession().createQuery(queryString).setLockMode("t", lockMode)
				.setParameter("activeStatus", activeStatus).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<School> getTeacherSchoolMembershipsForUser(final User user, final ActiveStatus activeStatus)
	{
		final String queryString = "select s from School s join s.teachers tsm where tsm.member.id = :id and tsm.memberStatus = :activeStatus";
		return getSession()
			.createQuery(queryString)
			.setLockMode("s", lockMode)
			.setParameter("id", user.getRole(RoleType.TEACHER).getId())
			.setParameter("activeStatus", activeStatus)
			.list();
	}

	@Override
	protected User getUniqueResult(final Criteria crit)
	{
		return (User) crit.uniqueResult();
	}

}
