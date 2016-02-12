package net.ruready.web.ta.beans;

import java.io.Serializable;

import net.ruready.business.ta.entity.ExpectationAssessment;
import net.ruready.business.ta.entity.StudentTranscript;

public class ExpectationAssessmentBean implements Serializable
{
	private static final long serialVersionUID = -7124518077818286493L;
	
	private StudentTranscript transcript;
	private ExpectationAssessment assessment;

	public StudentTranscript getTranscript()
	{
		return transcript;
	}

	public void setTranscript(final StudentTranscript transcript)
	{
		this.transcript = transcript;
	}
	
	public ExpectationAssessment getAssessment()
	{
		return assessment;
	}

	public void setAssessment(final ExpectationAssessment assessment)
	{
		this.assessment = assessment;
	}
	
	public void beginTest()
	{
		getAssessment().beginTest();
	}
	
	public void completeTest()
	{
		getAssessment().completeTest();
	}
}
