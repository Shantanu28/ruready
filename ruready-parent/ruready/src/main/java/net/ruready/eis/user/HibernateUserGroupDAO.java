package net.ruready.eis.user;

import static org.apache.commons.lang.Validate.notNull;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.ruready.business.user.entity.ActiveStatus;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.UserRole;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateDAO;

public class HibernateUserGroupDAO extends HibernateDAO<UserGroup, Long> implements UserGroupDAO
{	
	public HibernateUserGroupDAO(final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(UserGroup.class, sessionFactory, context);
	}

	public List<UserGroup> getMembershipsByUser(final User user)
	{
		notNull(user, "user cannot be null");
		return getMembershipsByStudent((StudentRole) user.getRole(RoleType.STUDENT));
	}

	public List<UserGroup> findActive()
	{
		return findByCriteria(
				createCriteria().add(Restrictions.eq("activeStatus", ActiveStatus.ACTIVE)));
	}

	public List<UserGroup> findActiveByPartialName(String searchString)
	{
		return findByPartialName(
				searchString, 
				createCriteria().add(Restrictions.eq("activeStatus", ActiveStatus.ACTIVE)));
	}

	public List<UserGroup> findByPartialName(final String searchString)
	{
		return findByPartialName(searchString, createCriteria());
	}
	
	private List<UserGroup> findByPartialName(final String searchString, final Criteria criteria)
	{
		return findByCriteria(
				criteria
				.add(Restrictions.ilike("name", searchString + "%"))
				.addOrder(Order.asc("name")));
	}
	
	public List<UserGroup> findByMatchingProperties(final UserGroup userGroup)
	{
		final Criteria criteria =
			createCriteria()
			.add(Restrictions.eq("name", userGroup.getName()))
			.add(Restrictions.eq("course", userGroup.getCourse()))
			.add(Restrictions.eq("school", userGroup.getSchool()));
		return findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup> getMembershipsByStudent(final StudentRole student)
	{
		notNull(student, "student cannot be null");
		final String queryString = "select ug from UserGroup ug join ug.members ugm where ugm.member.id = :id";
		return performQuery(queryString, "ug", student.getId());
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> getMembershipsByUserGroup(UserGroup userGroup)
	{
		notNull(userGroup, "userGroup cannot be null.");
		final String queryString = "select s from StudentRole s join s.groups ugm where ugm.group.id = :id";
		return performQuery(queryString, "s", userGroup.getId());
	}

	public List<UserGroup> getModeratedGroupsByUser(final User user)
	{
		notNull(user, "user cannot be null");
		return getModeratedGroupByTeacher((TeacherRole) user.getRole(RoleType.TEACHER));
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup> getModeratedGroupByTeacher(final TeacherRole teacher)
	{
		notNull(teacher, "teacher cannot be null");
		final String queryString = "select ug from UserGroup ug join ug.moderators ugm where ugm.moderator.id = :id";
		return performQuery(queryString, "ug", teacher.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<UserRole> getModeratorsByUserGroup(final UserGroup userGroup)
	{
		notNull(userGroup, "userGroup cannot be null.");
		final String queryString = "select t from TeacherRole t join t.groups ugm where ugm.group.id = :id";
		return performQuery(queryString, "t", userGroup.getId());
	}
	
	@SuppressWarnings("unchecked")
	private List performQuery(final String queryString, final String tableAlias, final Long id)
	{
		return getSession().createQuery(queryString)
		.setLockMode(tableAlias, lockMode)
		.setLong("id", id)
		.list();
	}
}
