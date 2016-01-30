package net.ruready.business.ta.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.question.entity.Answer;
import net.ruready.business.content.question.entity.Choice;
import net.ruready.business.content.question.entity.Hint;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;
import net.ruready.business.content.question.entity.property.QuestionType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
/**
 * Represents a content knowledge question test item.
* <p>
* -------------------------------------------------------------------------<br>
* (c) 2006-2007 Continuing Education, University of Utah<br>
* All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
* <p>
* This file is part of the RUReady Program software.<br>
* Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
* Academic Outreach and Continuing Education (AOCE)<br>
* 1901 East South Campus Dr., Room 2197-E<br>
* University of Utah, Salt Lake City, UT 84112-9359<br>
* U.S.A.<br>
* Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
* <br>
* Please contact these numbers immediately if you receive this file without permission
* from the authors. Thank you.<br>
* -------------------------------------------------------------------------
* 
* @author Jeremy Lund
* @version Nov 15, 2007
*/
@Entity
@DiscriminatorValue("KNOWLEDGE")
public class KnowledgeAssessmentItem extends AssessmentItem
{
	private static final long serialVersionUID = 7339513781063506176L;

	@Enumerated(value = EnumType.STRING)
	@Column(updatable=false)
	private QuestionFormat questionFormat;
	
	@Enumerated(value = EnumType.STRING)
	@Column(updatable=false)
	private QuestionType questionType;

	@Column
	private int level;

	@Column(length = 150, updatable=false)
	private String variables;

	@Column(length = 150, updatable=false)
	private String parameters;

	@Column(updatable=false)
	private int questionPrecision;

	@Column(updatable=false)
	private int numberOfChoices;

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name = "index_choice")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<AssessmentChoice> choices = new ArrayList<AssessmentChoice>();

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name = "index_answer")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<AssessmentAnswer> answers = new ArrayList<AssessmentAnswer>();

	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name = "index_hint")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<AssessmentHint> hints = new ArrayList<AssessmentHint>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@IndexColumn(name = "index_responses")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<AssessmentItemResponse> responses = new ArrayList<AssessmentItemResponse>();
	
	protected KnowledgeAssessmentItem() {}
	
	public KnowledgeAssessmentItem(final Question question, final QuestionFormat questionFormat)
	{
		super(question.getName(),question.getId(),question.getFormulation());
		this.questionFormat = questionFormat;
		this.questionType = question.getQuestionType();
		this.level = question.getLevel();
		this.variables = question.getVariables();
		this.parameters = question.getParameters();
		this.questionPrecision = question.getQuestionPrecision();
		this.numberOfChoices = question.getNumberOfChoices();
		for (Choice choice : question.getChoices())
		{
			this.choices.add(new AssessmentChoice(choice));
		}
		for (Answer answer : question.getAnswers())
		{
			this.answers.add(new AssessmentAnswer(answer));
		}
		for (Hint hint : question.getHints())
		{
			this.hints.add(new AssessmentHint(hint));
		}
	}
	
	public QuestionFormat getQuestionFormat()
	{
		return this.questionFormat;
	}
	
	public QuestionType getQuestionType()
	{
		return this.questionType;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public String getVariables()
	{
		return variables;
	}
	
	public String getParameters()
	{
		return parameters;
	}
	
	public int getQuestionPrecision()
	{
		return this.questionPrecision;
	}
	
	public int getNumberOfChoices()
	{
		return this.numberOfChoices;
	}
	
	public List<AssessmentChoice> getChoices()
	{
		return this.choices;
	}
	
	public List<AssessmentAnswer> getAnswers()
	{
		return this.answers;
	}
	
	public List<AssessmentHint> getHints()
	{
		return this.hints;
	}
	
	/**
	 * Returns the number of hints that were requested
	 * 
	 * @return the number of hint requests
	 */
	public Integer getHintRequestCount()
	{
		Integer hintRequests = 0;
		for (AssessmentItemResponse response : getResponses())
		{
			if (response.getResponseType() == AssessmentItemResponseType.HINT)
			{
				hintRequests++;
			}
		}
		return hintRequests;
	}
	
	public List<AssessmentItemResponse> getResponses()
	{
		return this.responses;
	}
	
	public void addResponse(final AssessmentItemResponse response)
	{
		assertNoFinalAnswer();
		if (response.getResponseType() == AssessmentItemResponseType.HINT)
		{
			assertAnotherHintRequestAvailable();
		}
		getResponses().add(response);
	}
	
	public AssessmentItemResponse getLastResponse()
	{
		if (getResponses().isEmpty())
		{
			return null;
		}
		return getResponses().get(getResponses().size() - 1);
	}
	
	/**
	 * Asserts that the student has not already answered the question
	 */
	private void assertNoFinalAnswer()
	{
		if (!this.responses.isEmpty())
		{
			if (this.responses.get(this.responses.size() - 1).getResponseType() == AssessmentItemResponseType.ANSWER)
			{
				throw new BusinessRuleException("You cannot add another response to this question. The question has already been answered.");
			}
		}
	}
	
	/**
	 * Asserts that there is a hint available to request
	 */
	private void assertAnotherHintRequestAvailable()
	{
		if (getHintRequestCount() >= getHints().size())
		{
			throw new BusinessRuleException("You cannot request another hint. All hints have already been requested.");
		}
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.appendSuper(super.toString())
		.append("question type" , getQuestionType())
		.append("level", getLevel())
		.append("variables", getVariables())
		.append("parameters", getParameters())
		.append("question precision", getQuestionPrecision())
		.append("number of choices", getNumberOfChoices())
		.append("choices", getChoices())
		.append("answers", getAnswers())
		.append("hints", getHints())
		.append("responses", getResponses())
		.toString();
	}	
}
