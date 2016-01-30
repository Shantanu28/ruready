package net.ruready.business.ta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.ruready.business.content.question.entity.Hint;
import net.ruready.common.eis.entity.PersistentEntity;
/**
 * Encapsulates a hint for a test item.
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
@Entity(name="ASSESSMENT_ITEM_HINT")
public class AssessmentHint implements PersistentEntity<Long>
{
	private static final long serialVersionUID = 1564866473300315430L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 255, updatable=false)
	private String hint1Text;
	
	@Column(length = 255, updatable=false)
	private String hint2Text;
	
	@Column(length = 255, updatable=false)
	private String keyword1Text;
	
	@Column(length = 255, updatable=false)
	private String keyword2Text;
	
	protected AssessmentHint() {}
	
	public AssessmentHint(final Hint hint)
	{
		this(hint.getHint1Text(),
			hint.getHint2Text(),
			hint.getKeyword1Text(),
			hint.getKeyword2Text());
	}
	
	private AssessmentHint(final String hint1Text, final String hint2Text, final String keyword1Text, final String keyword2Text)
	{
		this.hint1Text = hint1Text;
		this.hint2Text = hint2Text;
		this.keyword1Text = keyword1Text;
		this.keyword2Text = keyword2Text;
	}

	public Long getId()
	{
		return id;
	}

	public String getHint1Text()
	{
		return hint1Text;
	}

	public String getHint2Text()
	{
		return hint2Text;
	}

	public String getKeyword1Text()
	{
		return keyword1Text;
	}

	public String getKeyword2Text()
	{
		return keyword2Text;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("id", getId())
		.append("hint 1 text", getHint1Text())
		.append("hint 2 text", getHint2Text())
		.append("keyword 1 text", getKeyword1Text())
		.append("keyword 2 text", getKeyword2Text())
		.toString();
	}	
}
