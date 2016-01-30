package net.ruready.eis.ta;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.common.eis.dao.DAO;

public interface StudentTranscriptDAO extends DAO<StudentTranscript, Long>
{	
	public void reattach(final StudentTranscript transcript);
	public List<StudentTranscript> findByStudent(final StudentRole student);
	public List<StudentCourseTranscript> findCourseTranscriptsByStudent(final StudentRole student);
	public List<StudentGroupTranscript> findGroupTranscriptsByStudent(final StudentRole student);
	
	public List<StudentGroupTranscript> findByGroup(final UserGroup group);
	
	public StudentCourseTranscript findCourseTranscript(final StudentRole student, final Course course);
	public StudentGroupTranscript findGroupTranscript(final StudentRole student, final UserGroup group);
}
