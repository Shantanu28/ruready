package net.ruready.business.ta.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.ruready.business.content.catalog.entity.Expectation;
/**
 * Represents an expectation test item.
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
@DiscriminatorValue("EXPECTATION")
public class ExpectationAssessmentItem extends AssessmentItem
{
	private static final long serialVersionUID = -2886870554531610650L;

	@Column(updatable=false)
	private Boolean negative = false;
	
	@Column(name="EXPECTATION_VALUE", precision=4, scale=3)
	private BigDecimal value;
	
	@Column(name="EXPECTATION_BASELINE", precision=4, scale=3)
	private BigDecimal baseline;
	
	protected ExpectationAssessmentItem() { }
	
	public ExpectationAssessmentItem(final Expectation expectation)
	{
		super(expectation.getName(),expectation.getId(),expectation.getFormulation());
		this.negative = expectation.isNegative();
		setBaseline(0d);
	}
	
	public ExpectationAssessmentItem(final ExpectationAssessmentItem item)
	{
		super(item);
		this.negative = item.isNegative();
		setBaseline(item.getBaseline());
	}

	public Double getValue()
	{
		if (value == null) return null;
		return value.doubleValue();
	}

	public void setValue(final Double value)
	{
		this.value = BigDecimal.valueOf(value);
	}

	public Double getBaseline()
	{
		return baseline.doubleValue();
	}

	public void setBaseline(final Double baseline)
	{
		this.baseline = BigDecimal.valueOf(baseline);
	}

	public boolean isNegative()
	{
		return negative;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.appendSuper(super.toString())
		.append("isNegative", isNegative())
		.append("value", getValue())
		.toString();
	}
}
