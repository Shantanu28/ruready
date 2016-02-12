package test.ruready.business.ta;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.StudentGroupTranscript;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.entity.TranscriptActiveStatus;
import net.ruready.business.ta.entity.TranscriptProgressStatus;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.common.discrete.Gender;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestStudentGroupTranscript
{

	private UserGroup userGroup;

	// user with no roles
	private User nobody;

	// student
	private User student;

	// teacher
	private User teacher;

	@Before
	public void setUp()
	{
		userGroup = new UserGroup(new Course("Testing for Dummies", ""), new School(
				"Testing School", ""));

		nobody = getUser("nobody");

		student = getUser("student");
		student.addRole(RoleType.STUDENT.newInstance());

		teacher = getUser("teacher");
		teacher.addRole(RoleType.TEACHER.newInstance());
	}

	@Test
	public void testCreateAsStudent() throws Exception
	{
		new StudentGroupTranscript((StudentRole) student.getRole(RoleType.STUDENT),
				userGroup);
	}

	@Test
	public void testCreateAsStudentUser() throws Exception
	{
		new StudentGroupTranscript(student, userGroup);
	}

	@Test
	public void testCreateAsTeacherUser() throws Exception
	{
		new StudentGroupTranscript(teacher, userGroup);
	}

	@Test(expected = BusinessRuleException.class)
	public void testCreateNoStudentRole() throws Exception
	{
		new StudentGroupTranscript(nobody, userGroup);
	}

	@Test
	public void testTranscriptStatusAtCreation() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		Assert.assertEquals(TranscriptActiveStatus.OPEN, transcript.getActiveStatus());
	}

	@Test
	public void testTranscriptStatusClosed() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.closeTranscript();
		Assert.assertEquals(TranscriptActiveStatus.CLOSED, transcript.getActiveStatus());
	}

	@Test
	public void testTranscriptReOpen() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.closeTranscript();
		transcript.openTranscript();
		Assert.assertEquals(TranscriptActiveStatus.OPEN, transcript.getActiveStatus());
	}

	@Test(expected = BusinessRuleException.class)
	public void testTranscriptChangeProgressStatusWhileClosedInvalid() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.closeTranscript();
		transcript.startCourse();
	}

	@Test(expected = BusinessRuleException.class)
	public void testTranscriptAddAssessmentWhileClosedInvalid() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.closeTranscript();
		transcript.addExpectationAssessment(new ExpectationAssessment());
	}

	@Test
	public void testTranscriptProgressStatusAtCreation() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		Assert.assertEquals(TranscriptProgressStatus.NOT_STARTED, transcript
				.getProgressStatus());
	}

	@Test
	public void testTranscriptProgressStatusInProcess() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.startCourse();
		Assert.assertEquals(TranscriptProgressStatus.IN_PROCESS, transcript
				.getProgressStatus());
	}

	@Test
	public void testTranscriptProgressStatusPassed() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.startCourse();
		transcript.passCourse();
		Assert.assertEquals(TranscriptProgressStatus.PASSED, transcript
				.getProgressStatus());
	}

	@Test(expected = BusinessRuleException.class)
	public void testTranscriptProgressNotStartedToPassedInvalid() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.passCourse();
	}

	@Test(expected = BusinessRuleException.class)
	public void testTranscriptProgressPassedToInProcessInvalid() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.startCourse();
		transcript.passCourse();
		transcript.startCourse();
	}

	@Test
	public void testHasExpectationAssessment() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		Assert.assertFalse(transcript.hasExpectationAssessment());
		transcript.addExpectationAssessment(new ExpectationAssessment());
		Assert.assertTrue(transcript.hasExpectationAssessment());
	}

	@Test
	public void testHasKnowledgeAssessment() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		Assert.assertFalse(transcript.hasKnowledgeAssessment());
		transcript.addKnowledgeAssessment(new KnowledgeAssessment());
		Assert.assertTrue(transcript.hasKnowledgeAssessment());
	}

	@Test(expected = BusinessRuleException.class)
	public void testAddSecondExpectationAssessmentNotCompleted() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.addExpectationAssessment(new ExpectationAssessment());
		transcript.addExpectationAssessment(new ExpectationAssessment());
	}

	@Test(expected = BusinessRuleException.class)
	public void testAddSecondKnowledgeAssessmentNotCompleted() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		transcript.addKnowledgeAssessment(new KnowledgeAssessment());
		transcript.addKnowledgeAssessment(new KnowledgeAssessment());
	}

	@Test
	public void testExpectationAssessmentOrder() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		final ExpectationAssessment assessment1 = new ExpectationAssessment();
		assessment1.completeTest();
		final ExpectationAssessment assessment2 = new ExpectationAssessment();

		transcript.addExpectationAssessment(assessment1);
		transcript.addExpectationAssessment(assessment2);
		Assert.assertEquals(2, transcript.getExpectationAssessments().size());
		Assert.assertSame(assessment1, transcript.getExpectationAssessment(0));
		Assert.assertSame(assessment2, transcript.getExpectationAssessment(1));
		Assert.assertNotSame(assessment1, transcript.getExpectationAssessment(1));
		Assert.assertSame(assessment2, transcript.getCurrentExpectationAssessment());
	}

	@Test
	public void testKnowledgeAssessmentOrder() throws Exception
	{
		final StudentTranscript transcript = new StudentGroupTranscript(student,
				userGroup);
		final KnowledgeAssessment assessment1 = new KnowledgeAssessment();
		assessment1.completeTest();
		final KnowledgeAssessment assessment2 = new KnowledgeAssessment();

		transcript.addKnowledgeAssessment(assessment1);
		transcript.addKnowledgeAssessment(assessment2);
		Assert.assertEquals(2, transcript.getKnowledgeAssessments().size());
		Assert.assertSame(assessment1, transcript.getKnowledgeAssessment(0));
		Assert.assertSame(assessment2, transcript.getKnowledgeAssessment(1));
		Assert.assertNotSame(assessment1, transcript.getKnowledgeAssessment(1));
		Assert.assertSame(assessment2, transcript.getCurrentKnowledgeAssessment());
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
