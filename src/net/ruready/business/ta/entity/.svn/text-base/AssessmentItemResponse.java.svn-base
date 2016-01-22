package net.ruready.business.ta.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.ruready.common.eis.entity.PersistentEntity;
/**
 * Represents a student's single response to a question. This response can be a hint request,
 * a trial answer, or their final answer.
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
* @version Nov 30, 2007
*/
@Entity(name="ASSESSMENT_ITEM_RESPONSE")
public class AssessmentItemResponse implements PersistentEntity<Long>
{
	private static final long serialVersionUID = 2972823411992825959L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(value = EnumType.STRING)
	@Column(updatable=false)
	private AssessmentItemResponseType responseType;
	
	@Column(name="ANSWER", length=1000)
	private String answer;
	
	@Column(name="SCORE", precision=4, scale=3)
	private BigDecimal score = BigDecimal.ZERO;

	protected AssessmentItemResponse() { }
	
	public static AssessmentItemResponse createHintResponse()
	{
		final AssessmentItemResponse event = new AssessmentItemResponse();
		event.setResponseType(AssessmentItemResponseType.HINT);
		return event;
	}
	
	public static AssessmentItemResponse createTryAnswerResponse(final String answer)
	{
		final AssessmentItemResponse event = new AssessmentItemResponse();
		event.setResponseType(AssessmentItemResponseType.TRY);
		event.setAnswer(answer);
		return event;
	}
	
	public static AssessmentItemResponse createSubmitAnswerResponse(final String answer)
	{
		final AssessmentItemResponse event = new AssessmentItemResponse();
		event.setResponseType(AssessmentItemResponseType.ANSWER);
		event.setAnswer(answer);
		return event;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public AssessmentItemResponseType getResponseType()
	{
		return responseType;
	}

	public void setResponseType(final AssessmentItemResponseType eventType)
	{
		this.responseType = eventType;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer(final String answer)
	{
		this.answer = answer;
	}

	public BigDecimal getScore()
	{
		return score;
	}

	public void setScore(final Double score)
	{
		setScore(BigDecimal.valueOf(score));
	}
	
	private void setScore(final BigDecimal score)
	{
		this.score = score;
	}
}
