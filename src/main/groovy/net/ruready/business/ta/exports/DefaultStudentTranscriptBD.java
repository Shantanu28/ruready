package net.ruready.business.ta.exports;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.manager.AbstractStudentTranscriptManager;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.rl.ResourceLocator;

import static org.apache.commons.lang.Validate.notNull;

public abstract class DefaultStudentTranscriptBD implements AbstractStudentTranscriptBD
{

	protected final AbstractStudentTranscriptManager manager;
	protected final ResourceLocator resourceLocator;
	
	protected DefaultStudentTranscriptBD(
			final AbstractStudentTranscriptManager manager, 
			final ResourceLocator resourceLocator)
	{
		this.manager = manager;
		this.resourceLocator = resourceLocator;
	}
	public void reattachTranscript(final StudentTranscript transcript)
	{
		manager.reattachTranscript(transcript);
	}
	
	public List<StudentTranscript> findAllTranscripts()
	{
		return manager.findAllTranscripts();
	}

	public List<StudentCourseTranscript> findAllCourseTranscripts()
	{
		return manager.findAllCourseTranscripts();
	}

	public List<StudentGroupTranscript> findAllGroupTranscripts()
	{
		return manager.findAllGroupTranscripts();
	}

	public StudentTranscript findById(final Long id)
	{
		return manager.findById(id);
	}
	
	public void createTranscript(final StudentTranscript transcript)
	{
		manager.createTranscript(transcript);
	}

	public StudentCourseTranscript createCourseTranscript(final StudentRole student, final Course course)
	{
		return manager.createCourseTranscript(student, course);
	}

	public StudentCourseTranscript createCourseTranscript(final User user, final Course course)
	{
		return createCourseTranscript(getStudentFromUser(user), course);
	}

	public StudentGroupTranscript createGroupTranscript(final StudentRole student, final UserGroup group)
	{
		return manager.createGroupTranscript(student, group);
	}

	public StudentGroupTranscript createGroupTranscript(final User user, final UserGroup group)
	{
		return createGroupTranscript(getStudentFromUser(user), group);
	}
	
	public StudentCourseTranscript createCourseTranscriptIfNotExists(final User user, final Course course)
	{
		final StudentCourseTranscript transcript = findCourseTranscript(user, course);
		if (transcript != null)
			return transcript;
		return createCourseTranscript(user, course);
	}

	public StudentGroupTranscript createGroupTranscriptIfNotExists(final User user, final UserGroup group)
	{
		final StudentGroupTranscript transcript = findGroupTranscript(user, group);
		if (transcript != null)
			return transcript;
		return createGroupTranscript(user, group);
	}

	public StudentTranscript readTranscript(final Long id, final StudentTranscript transcript)
	{
		return manager.readTranscript(id, transcript);
	}

	public void updateTranscript(final StudentTranscript transcript)
	{
		manager.updateTranscript(transcript);
	}

	public void updateTranscriptMerge(final StudentTranscript transcript)
	{
		manager.updateTranscriptMerge(transcript);
	}

	public void deleteTranscript(final StudentTranscript transcript)
	{
		manager.deleteTranscript(transcript);
	}

	public List<StudentTranscript> findStudentTranscriptsForUser(final User user)
	{
		notNull(user, "user cannot be null.");
		return findTranscriptsForStudent(getStudentFromUser(user));
	}

	public List<StudentCourseTranscript> findStudentCourseTranscriptsForUser(final User user)
	{
		notNull(user, "user cannot be null.");
		return findCourseTranscriptsForStudent(getStudentFromUser(user));
	}

	public List<StudentGroupTranscript> findStudentGroupTranscriptsForUser(final User user)
	{
		notNull(user, "user cannot be null.");
		return findGroupTranscriptsForStudent(getStudentFromUser(user));
	}

	public List<StudentTranscript> findTranscriptsForStudent(final StudentRole student)
	{
		return manager.findTranscriptsForStudent(student);
	}

	public List<StudentCourseTranscript> findCourseTranscriptsForStudent(final StudentRole student)
	{
		return manager.findCourseTranscriptsForStudent(student);
	}

	public List<StudentGroupTranscript> findGroupTranscriptsForStudent(final StudentRole student)
	{
		return manager.findGroupTranscriptsForStudent(student);
	}
	
	public List<StudentGroupTranscript> findTranscriptsForGroup(final UserGroup group)
	{
		return manager.findTranscriptsForGroup(group);
	}

	public void openAllTranscriptsForGroup(final UserGroup group)
	{
		manager.openAllTranscriptsForGroup(group);
	}
	
	public void closeAllTranscriptsForGroup(final UserGroup group)
	{
		manager.closeAllTranscriptsForGroup(group);
	}

	public StudentCourseTranscript findCourseTranscript(final User user, final Course course)
	{
		return findCourseTranscript(getStudentFromUser(user), course);
	}

	public StudentCourseTranscript findCourseTranscript(final StudentRole student, final Course course)
	{
		return manager.findCourseTranscript(student, course);
	}

	public StudentGroupTranscript findGroupTranscript(final User user, final UserGroup group)
	{
		return findGroupTranscript(getStudentFromUser(user), group);
	}

	public StudentGroupTranscript findGroupTranscript(final StudentRole student, final UserGroup group)
	{
		return manager.findGroupTranscript(student, group);
	}
	
	private StudentRole getStudentFromUser(final User user)
	{
		return (StudentRole) user.getRole(RoleType.STUDENT);
	}
}
