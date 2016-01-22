package test.ruready.business.ta;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.ta.entity.StudentCourseTranscript;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.discrete.Gender;

import org.junit.Before;
import org.junit.Test;

public class TestStudentCourseTranscript
{

	private Course course;

	// user with no roles
	private User nobody;

	// student
	private User student;

	// teacher
	private User teacher;

	@Before
	public void setUp()
	{
		course = new Course("Testing for Dummies", "");

		nobody = getUser("nobody");

		student = getUser("student");
		student.addRole(RoleType.STUDENT.newInstance());

		teacher = getUser("teacher");
		teacher.addRole(RoleType.TEACHER.newInstance());
	}

	@Test
	public void testCreateAsStudent()
	{
		new StudentCourseTranscript((StudentRole) student.getRole(RoleType.STUDENT),
				course);
	}

	@Test
	public void testCreateAsStudentUser()
	{
		new StudentCourseTranscript(student, course);
	}

	@Test
	public void testCreateAsTeacherUser()
	{
		new StudentCourseTranscript(teacher, course);
	}

	@Test(expected = BusinessRuleException.class)
	public void testCreateNoStudentRole()
	{
		new StudentCourseTranscript(nobody, course);
	}

	private String getUserEmail(final String username)
	{
		return username + "@" + getClass().getSimpleName();
	}

	private User getUser(final String username)
	{
		return new User(getUserEmail(username), Gender.MALE, AgeGroup.GT_25,
				Ethnicity.UNSPECIFIED, Language.OTHER);
	}
}
