package net.ruready.eis.ta;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;
import net.ruready.eis.factory.imports.HibernateDAO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class HibernateStudentTranscriptDAO extends HibernateDAO<StudentTranscript, Long> implements StudentTranscriptDAO
{
	
	public HibernateStudentTranscriptDAO(
			final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(StudentTranscript.class, sessionFactory, context);
	}

	public void reattach(final StudentTranscript transcript)
	{
		getSession().refresh(transcript);
	}
	
	@SuppressWarnings("unchecked")
	public List<StudentTranscript> findByStudent(final StudentRole student)
	{
		return findTranscriptsByStudent(StudentTranscript.class, student);
	}

	@SuppressWarnings("unchecked")
	public List<StudentCourseTranscript> findCourseTranscriptsByStudent(final StudentRole student)
	{
		return findTranscriptsByStudent(StudentCourseTranscript.class, student);
	}

	@SuppressWarnings("unchecked")
	public List<StudentGroupTranscript> findGroupTranscriptsByStudent(final StudentRole student)
	{
		return findTranscriptsByStudent(StudentGroupTranscript.class, student);
	}

	@SuppressWarnings("unchecked")
	public List<StudentGroupTranscript> findByGroup(UserGroup group)
	{
		return getSession()
		.createCriteria(StudentGroupTranscript.class)
		.add(Restrictions.eq("group.id", group.getId()))
		.list(); 
	}

	public StudentCourseTranscript findCourseTranscript(final StudentRole student, final Course course)
	{
		return (StudentCourseTranscript) getSession()
		.createCriteria(StudentCourseTranscript.class)
		.add(Restrictions.eq("student.id", student.getId()))
		.add(Restrictions.eq("course.id", course.getId()))
		.uniqueResult();
	}

	public StudentGroupTranscript findGroupTranscript(final StudentRole student, final UserGroup group)
	{
		return (StudentGroupTranscript) getSession()
		.createCriteria(StudentGroupTranscript.class)
		.add(Restrictions.eq("student.id", student.getId()))
		.add(Restrictions.eq("group.id", group.getId()))
		.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	private List findTranscriptsByStudent(final Class<? extends StudentTranscript> clazz, final StudentRole student)
	{
		return getSession()
		.createCriteria(clazz)
		.add(Restrictions.eq("student.id", student.getId()))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.list();
	}
}
