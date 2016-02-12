package net.ruready.business.ta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.ruready.business.content.question.entity.Answer;
import net.ruready.common.eis.entity.PersistentEntity;
/**
 * The solution to a test item.
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
@Entity(name="ASSESSMENT_ITEM_ANSWER")
public class AssessmentAnswer implements PersistentEntity<Long>
{
	private static final long serialVersionUID = -2376742864603961306L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 255, updatable=false)
	private String answerText;
	
	protected AssessmentAnswer() {}
	
	public AssessmentAnswer(final Answer answer)
	{
		this(answer.getAnswerText());
	}
	
	private AssessmentAnswer(final String answerText)
	{
		this.answerText = answerText;
	}

	public Long getId()
	{
		return id;
	}

	public String getAnswerText()
	{
		return answerText;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("id", getId())
		.append("answer text", getAnswerText())
		.toString();
	}	
}
