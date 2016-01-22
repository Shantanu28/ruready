package net.ruready.web.user.beans;

import java.io.Serializable;

import net.ruready.business.user.entity.TeacherSchoolMembership;

public class TeacherSchoolMembershipFormBean implements Serializable
{
	private static final long serialVersionUID = -1352960761067822060L;

	public enum ApprovalStatus { APPROVE, DENY }

	private TeacherSchoolMembership membership;
	private ApprovalStatus approvalStatus;
	private String reason;
	
	public TeacherSchoolMembership getMembership()
	{
		return membership;
	}
	
	public void setMembership(final TeacherSchoolMembership membership)
	{
		this.membership = membership;
	}
	
	public ApprovalStatus getApprovalStatus()
	{
		return approvalStatus;
	}
	
	public void setApprovalStatus(final ApprovalStatus approvalStatus)
	{
		this.approvalStatus = approvalStatus;
	}
	
	public String getReason()
	{
		return reason;
	}
	
	public void setReason(final String reason)
	{
		this.reason = reason;
	}
}
