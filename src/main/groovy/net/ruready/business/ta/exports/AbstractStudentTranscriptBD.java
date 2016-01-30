package net.ruready.business.ta.exports;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.manager.AbstractStudentTranscriptManager;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.common.rl.BusinessDelegate;

public interface AbstractStudentTranscriptBD extends AbstractStudentTranscriptManager, BusinessDelegate
{
	public StudentCourseTranscript createCourseTranscript(final User user, final Course course);
	public StudentCourseTranscript createCourseTranscriptIfNotExists(final User user, final Course course);
	public StudentGroupTranscript createGroupTranscript(final User user, final UserGroup group);
	public StudentGroupTranscript createGroupTranscriptIfNotExists(final User user, final UserGroup group);
	
	public List<StudentTranscript> findStudentTranscriptsForUser(final User user);
	public List<StudentCourseTranscript> findStudentCourseTranscriptsForUser(final User user);
	public List<StudentGroupTranscript> findStudentGroupTranscriptsForUser(final User user);
	
	public StudentCourseTranscript findCourseTranscript(final User user, final Course course);
	public StudentGroupTranscript findGroupTranscript(final User user, final UserGroup group);
}
