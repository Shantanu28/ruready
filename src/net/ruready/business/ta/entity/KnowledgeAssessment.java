package net.ruready.business.ta.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.property.QuestionFormat;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Represents a content knowledge assessment/test.
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
public class KnowledgeAssessment extends AbstractAssessment implements Assessment<KnowledgeAssessmentItem>
{
	private static final long serialVersionUID = -1710942326644704047L;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="KNOWLEDGE_ASSESSMENT_ITEM",
			joinColumns = {@JoinColumn(name="ASSESSMENT_ID")},
			inverseJoinColumns = {@JoinColumn(name="ITEM_ID")})
	@IndexColumn(name="ASSESSMENT_INDEX")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<KnowledgeAssessmentItem> testItems = new ArrayList<KnowledgeAssessmentItem>();
	
	public List<KnowledgeAssessmentItem> getTestItems()
	{
		return this.testItems;
	} 
	
	public KnowledgeAssessmentItem getTestItem(final int i)
	{
		return getTestItems().get(i);
	}
	
	public void addTestItem(final Question question, final QuestionFormat questionFormat)
	{
		addTestItem(new KnowledgeAssessmentItem(question, questionFormat));
	}
	
	public void addTestItem(final KnowledgeAssessmentItem testItem)
	{
		if (getTestItems().contains(testItem))
		{
			throw new BusinessRuleException("This collection already contains the specified test item.");
		}
		getTestItems().add(testItem);
	}
}
