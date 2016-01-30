package net.ruready.business.ta.entity;

import static org.apache.commons.lang.Validate.notNull;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.user.entity.StudentRole;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserGroup;
/**
 * Represents a student's transcript for the specified group.
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
* @version Nov 19, 2007
*/
@Entity
@DiscriminatorValue("GROUP")
public class StudentGroupTranscript extends StudentTranscript
{
	private static final long serialVersionUID = -7576896562960523498L;
	
	@OneToOne
	@JoinColumn(name="GROUP_ID", updatable = false)
	private UserGroup group;
	
	protected StudentGroupTranscript() {}
	
	public StudentGroupTranscript(final User user, final UserGroup group)
	{
		super(user);
		notNull(group, "group cannot be null.");
		this.group = group;
	}
	
	public StudentGroupTranscript(final StudentRole student, final UserGroup group)
	{
		super(student);
		notNull(group, "group cannot be null.");
		this.group = group;
	}

	@Override
	public String getTranscriptType()
	{
		return "GROUP";
	}

	public UserGroup getGroup()
	{
		return group;
	}
	
	@Override
	public Course getCourse()
	{
		return getGroup().getCourse();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("student", getStudent())
		.append("group", getGroup())
		.toString();
	}
}
