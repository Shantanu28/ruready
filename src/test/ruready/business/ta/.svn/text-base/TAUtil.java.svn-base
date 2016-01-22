package test.ruready.business.ta;

import net.ruready.business.imports.StandAloneEnvironment;
import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.exports.AbstractAssessmentBD;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import test.ruready.imports.StandAloneAssessmentBD;
import test.ruready.imports.StandAloneStudentTranscriptBD;
import test.ruready.rl.StandAloneApplicationContext;

public class TAUtil
{
	// ========================= FIELDS ====================================

	private final StandAloneApplicationContext context;
	
	private final AbstractAssessmentBD assessmentBD;
	private final AbstractStudentTranscriptBD transcriptBD;
	
	public TAUtil(final StandAloneEnvironment environment)
	{
		this.context = environment.getContext();
		this.assessmentBD = new StandAloneAssessmentBD(this.context);
		this.transcriptBD = new StandAloneStudentTranscriptBD(this.context);
	}
	
	public final void deleteAssessment(final ExpectationAssessment assessment)
	{
		if (assessment != null && assessment.getId() != null)
		{
			getAssessmentBD().deleteExpectationAssessment(assessment);
		}
	}
	
	public final void deleteAssessment(final KnowledgeAssessment assessment)
	{
		if (assessment != null && assessment.getId() != null)
		{
			getAssessmentBD().deleteKnowledgeAssessment(assessment);
		}
	}
	
	public final void deleteAllAssessments()
	{
		deleteAllExpectationAssessments();
		deleteAllKnowledgeAssessments();
	}
	
	public final void deleteAllExpectationAssessments()
	{
		final AbstractAssessmentBD anAssessmentBD = getAssessmentBD();
		for (ExpectationAssessment assessment : anAssessmentBD.findAllExpectationAssessments())
		{
			anAssessmentBD.deleteExpectationAssessment(assessment);
		}
	}
	
	public final void deleteAllKnowledgeAssessments()
	{
		final AbstractAssessmentBD anAssessmentBD = getAssessmentBD();
		for (KnowledgeAssessment assessment : anAssessmentBD.findAllKnowledgeAssessments())
		{
			anAssessmentBD.deleteKnowledgeAssessment(assessment);
		}
	}
	
	public final void deleteAllTranscripts()
	{
		for (StudentTranscript transcript : getTranscriptBD().findAllTranscripts())
		{
			getTranscriptBD().deleteTranscript(transcript);
		}
	}
	
	public final void deleteTranscript(final StudentTranscript transcript)
	{
		if (transcript != null && transcript.getId() != null)
		{
			getTranscriptBD().deleteTranscript(transcript);
		}
	}
	
	public final AbstractAssessmentBD getAssessmentBD()
	{
		return this.assessmentBD;
	}
	
	public final AbstractStudentTranscriptBD getTranscriptBD()
	{
		return this.transcriptBD;
	}
}
