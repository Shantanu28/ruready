package net.ruready.business.ta.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.ruready.business.common.exception.BusinessRuleException;
import net.ruready.common.eis.entity.PersistentEntity;

@Entity
@Table(name="ASSESSMENT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="ASSESSMENT_TYPE",
		discriminatorType=DiscriminatorType.STRING,
		length=15)
public abstract class AbstractAssessment implements PersistentEntity<Long>
{
	@Id	@GeneratedValue
	@Column(name="ASSESSMENT_ID")
	private Long id;
	
	@Column(name="SCORE", precision=4, scale=3)
	private BigDecimal score = BigDecimal.ZERO;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="STATUS", nullable=false)
	private AssessmentStatus status = AssessmentStatus.INCOMPLETE;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED", nullable=false)
	private Date lastUpdated = new Date();
	
	public void beginTest()
	{
		assertStatusIs(AssessmentStatus.INCOMPLETE);
		setStatus(AssessmentStatus.INCOMPLETE);
		setScore(BigDecimal.ZERO);
		updateLastUpdated();
	}
	
	public void completeTest()
	{
		assertStatusIs(AssessmentStatus.INCOMPLETE);
		setStatus(AssessmentStatus.COMPLETE);
		updateLastUpdated();
	}
	
	public void stopTest()
	{
		assertStatusIs(AssessmentStatus.INCOMPLETE);
		setStatus(AssessmentStatus.CLOSED);
		setScore(BigDecimal.ZERO);
		updateLastUpdated();
	}
	
	public Long getId()
	{
		return this.id;
	}
	
	public Double getScore()
	{
		return this.score.doubleValue();
	}

	public void setScore(final Double score)
	{
		setScore(BigDecimal.valueOf(score));
	}
	
	protected final void setScore(final BigDecimal score)
	{
		this.score = score;
	}
	
	public AssessmentStatus getStatus()
	{
		return this.status;
	}
	
	protected final void setStatus(final AssessmentStatus status)
	{
		this.status = status;
	}
	
	public Date getLastUpdated()
	{
		return lastUpdated;
	}

	private void updateLastUpdated()
	{
		this.lastUpdated = new Date();
	}
	
	private void assertStatusIs(final AssessmentStatus aStatus)
	{
		if (getStatus() != aStatus)
		{
			throw new BusinessRuleException(
					"Invalid progress transition: cannot change the progress status from "
							+ getStatus() + " to " + aStatus);
		}
	}
}
