package net.ruready.web.ta.beans;

import java.io.Serializable;

import net.ruready.business.ta.entity.KnowledgeAssessment;
import net.ruready.business.ta.entity.KnowledgeAssessmentItem;
import net.ruready.business.ta.entity.StudentTranscript;

public class KnowledgeAssessmentBean implements Serializable
{
	private static final long serialVersionUID = -2612329378076390351L;
	
	private StudentTranscript transcript;	
	private KnowledgeAssessment assessment;
	private Integer currentIndex = 0;
	
	private String answer;

	public StudentTranscript getTranscript()
	{
		return transcript;
	}

	public void setTranscript(final StudentTranscript transcript)
	{
		this.transcript = transcript;
	}

	public KnowledgeAssessment getAssessment()
	{
		return assessment;
	}

	public void setAssessment(final KnowledgeAssessment assessment)
	{
		this.assessment = assessment;
	}
	
	public void beginTest()
	{
		getAssessment().beginTest();
		this.currentIndex = 0;
	}
	
	public void completeTest()
	{
		getAssessment().completeTest();
	}
	
	public void stopTest()
	{
		getAssessment().stopTest();
	}
	
	public Integer getCurrentQuestionNumber()
	{
		return (this.currentIndex + 1);
	}
	
	public KnowledgeAssessmentItem getCurrentQuestion()
	{
		return getAssessment().getTestItems().get(this.currentIndex);
	}
	
	public KnowledgeAssessmentItem getNextQuestion()
	{
		incrementIndex();
		// clear out the previous answer (if any)
		setAnswer(null);
		// is the student finished with the test?
		if (this.currentIndex >= getAssessment().getTestItems().size())
		{
			return null;
		}
		return getCurrentQuestion();
	}
	
	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer(final String answer)
	{
		this.answer = answer;
	}
	
	protected final void incrementIndex()
	{
		this.currentIndex++;
	}
}
