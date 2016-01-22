package test.ruready.business.ta;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 	TestExpectationAssessment.class, 
	TestExpectationAssessmentItem.class,
	TestKnowledgeAssessment.class, 
	TestKnowledgeAssessmentItem.class,
	TestStudentCourseTranscript.class, 
	TestStudentGroupTranscript.class,
	TestDefaultKnowledgeAssessmentFactory.class,
	TestIntegrationExpectationAssessment.class,
	TestIntegrationKnowledgeAssessment.class,
	TestIntegrationStudentCourseTranscript.class,
	TestIntegrationStudentGroupTranscript.class,
	TestIntegrationStudentTranscript.class })
public class SuiteTA
{
}
