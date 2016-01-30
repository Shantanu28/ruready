package net.ruready.business.ta.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.rl.CommonNames;
import net.ruready.eis.audit.TimeSpan;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import static org.apache.commons.lang.Validate.notNull;
/**
 * Represents an individual item that a student is being tested/questioned on.
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
@Table(name="ASSESSMENT_ITEM")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="ITEM_TYPE",
		discriminatorType=DiscriminatorType.STRING,
		length=15)
public abstract class AssessmentItem implements PersistentEntity<Long>
{
	@Id	@GeneratedValue
	private Long id;
	
	@Column(name="NAME", length = 50, nullable=false, updatable=false)
	private String name;
	
	@Column(name="REFERENCE_ID", updatable=false)
	private Long referenceId;
	
	@Column(name="FORMULATION", length = 1000, updatable=false)
	private String formulation = CommonNames.MISC.EMPTY_STRING;
	
	@Column(name="SCORE", precision=4, scale=3)
	private BigDecimal score = BigDecimal.ZERO;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="STATUS", nullable=false)
	private AssessmentItemStatus status = AssessmentItemStatus.NOT_ANSWERED;

	@Embedded
	private TimeSpan timeSpan = new TimeSpan();
		
	protected AssessmentItem() {} 
	
	protected AssessmentItem(final String name, final Long referenceId, final String formulation)
	{
		notNull(name, "name cannot be null.");
		this.name = name;
		this.referenceId = referenceId;
		this.formulation = formulation;
	}
	
	protected AssessmentItem(final AssessmentItem item)
	{
		notNull(item, "item cannot be null.");
		this.name = item.getName();
		this.referenceId = item.getReferenceId();
		this.formulation = item.getFormulation();
	}
	
	public Long getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}

	public Long getReferenceId()
	{
		return this.referenceId;
	}
	
	public String getFormulation()
	{
		return this.formulation;
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

	public AssessmentItemStatus getStatus()
	{
		return this.status;
	}

	protected final void setStatus(final AssessmentItemStatus status)
	{
		this.status = status;
	}
	
	public Boolean isAnswered()
	{
		return (getStatus() == AssessmentItemStatus.ANSWERED);
	}
	
	public void beginTestItem()
	{
		setStatus(AssessmentItemStatus.NOT_ANSWERED);
		setScore(BigDecimal.ZERO);
		getTimeSpan().setStartDate(new Date());
	}
	
	public void answerTestItem()
	{
		setStatus(AssessmentItemStatus.ANSWERED);
		getTimeSpan().setEndDate(new Date());
	}
	
	public void stopTestItem()
	{
		setStatus(AssessmentItemStatus.NOT_ANSWERED);
		setScore(BigDecimal.ZERO);
		getTimeSpan().setEndDate(new Date());
	}
	
	public Timestamp getStartTime()
	{
		return getTimeSpan().getStartDate();
	}
	
	public Timestamp getEndTime()
	{
		return getTimeSpan().getEndDate();
	}
	
	private TimeSpan getTimeSpan()
	{
		if (this.timeSpan == null)
		{
			this.timeSpan = new TimeSpan();
		}
		return this.timeSpan;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(292621745, -1692731659)
		.append(this.getName())
		.append(this.getReferenceId())
		.toHashCode();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (o == this) return true;
		if (!(o instanceof AssessmentItem))	return false;

		final AssessmentItem that = (AssessmentItem) o;
		return new EqualsBuilder()
		.append(this.getName(), that.getName())
		.append(this.getReferenceId(), that.getReferenceId())
		.isEquals();
	}
	
	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("id", getId())
		.append("name", getName())
		.append("referenceId", getReferenceId())
		.append("formulation", getFormulation())
		.append("score", getScore())
		.append("status", getStatus())
		.append("startTime", getStartTime())
		.append("endTime", getEndTime())
		.toString();
	}
}
