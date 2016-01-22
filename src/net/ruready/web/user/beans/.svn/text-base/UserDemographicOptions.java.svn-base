package net.ruready.web.user.beans;

import java.io.Serializable;

import org.apache.struts.util.MessageResources;

import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserStatus;
import net.ruready.common.discrete.Gender;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.select.exports.OptionList;

public class UserDemographicOptions implements Serializable
{
	private static final long serialVersionUID = 3081231931327082862L;
	
	private final OptionList genderOptions;
	private final OptionList ethnicityOptions;
	private final OptionList ageGroupOptions;
	private final OptionList languageOptions;
	private final OptionList roleTypeOptions;
	private final OptionList userStatusOptions;	
	
	public UserDemographicOptions(final MessageResources messageResources)
	{
		this (messageResources, false);
	}
	
	public UserDemographicOptions(final MessageResources messageResources, final Boolean required)
	{
		this.genderOptions = getOptionList(messageResources, Gender.class, required);
		this.ethnicityOptions = getOptionList(messageResources, Ethnicity.class, required);
		this.ageGroupOptions = getOptionList(messageResources, AgeGroup.class, required);
		this.languageOptions = getOptionList(messageResources, Language.class, required);
		// role type is always required
		this.roleTypeOptions = getOptionList(messageResources, RoleType.class, true);
		// user status is always required
		this.userStatusOptions = getOptionList(messageResources, UserStatus.class, true);
	}
	
	public OptionList getGenderOptions()
	{
		return genderOptions;
	}
	public OptionList getEthnicityOptions()
	{
		return ethnicityOptions;
	}
	public OptionList getAgeGroupOptions()
	{
		return ageGroupOptions;
	}
	public OptionList getLanguageOptions()
	{
		return languageOptions;
	}
	
	public OptionList getRoleTypeOptions()
	{
		return roleTypeOptions;
	}

	public OptionList getUserStatusOptions()
	{
		return userStatusOptions;
	}

	private OptionList getOptionList(final MessageResources messageResources, final Class<?> type, final Boolean required)
	{
		return StrutsUtil.i18NSelection(
				type, 
				required, 
				WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX, 
				WebAppNames.KEY.MESSAGE.SEPARATOR, 
				messageResources);
	}
}
