package net.ruready.web.user.validator;

import org.springframework.validation.Errors;

import net.ruready.web.user.beans.TeacherSchoolMembershipFormBean;
import net.ruready.web.user.support.AbstractWebFlowValidator;

public class TeacherPendingSchoolMembershipsFormValidator extends AbstractWebFlowValidator
{
	private static final String LABEL_KEY_TEMPLATE = "user.pendingTeacherSchoolMemberships.review.confirmForm.%s.label";
	
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz)
	{
		return TeacherSchoolMembershipFormBean.class.isAssignableFrom(clazz);
	}
	
	public void validate(final Object target, final Errors errors)
	{
		validateReviewForm((TeacherSchoolMembershipFormBean) target, errors);
	}

	public void validateReviewForm(final TeacherSchoolMembershipFormBean form, final Errors errors)
	{
		validateEnum(errors, "approvalStatus", toLabel("approve"), TeacherSchoolMembershipFormBean.ApprovalStatus.class);
		getRules().validateRequiredEntry(form.getReason(), errors, "reason", "reason");
	}
	
	private String toLabel(final String shortLabelKey)
	{
		return getLabelFromTemplate(shortLabelKey, LABEL_KEY_TEMPLATE);
	}
}
