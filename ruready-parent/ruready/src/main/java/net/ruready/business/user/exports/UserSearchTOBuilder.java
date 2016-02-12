package net.ruready.business.user.exports;

import java.util.Map;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.UserSearchTO;
import net.ruready.business.user.entity.UserTO;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.business.user.entity.property.RoleType;
import net.ruready.business.user.entity.property.UserStatus;
import net.ruready.common.discrete.Gender;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
//import org.apache.struts.util.MessageResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class UserSearchTOBuilder extends UserTOBuilder {

    private final Map<AgeGroup, String> ageGroupValues;
    private final Map<Ethnicity, String> ethnicityValues;
    private final Map<Gender, String> genderValues;
    private final Map<Language, String> languageValues;
    private final Map<RoleType, String> userRoleValues;
    private final Map<UserStatus, String> userStatusValues;

    public UserSearchTOBuilder(final MessageSource messageResources) {
        ageGroupValues = getMap(AgeGroup.class, messageResources);
        ethnicityValues = getMap(Ethnicity.class, messageResources);
        genderValues = getMap(Gender.class, messageResources);
        languageValues = getMap(Language.class, messageResources);
        userRoleValues = getMap(RoleType.class, messageResources);
        userStatusValues = getMap(UserStatus.class, messageResources);
    }

    private <E extends Enum<E>> Map<E, String> getMap(final Class<E> clazz, final MessageSource messageResources) {
        return StrutsUtil.i18NMap(
                clazz,
                WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX,
                WebAppNames.KEY.MESSAGE.SEPARATOR,
                messageResources);
    }

    @Override
    protected UserTO doBuildUserTO(final User user, final Map<String, Long> linkParams) {
        return new UserSearchTO(
                user,
                getAgeGroupValue(user.getAgeGroup()),
                getEthnicityValue(user.getEthnicity()),
                getGenderValue(user.getGender()),
                getLanguageValue(user.getLanguage()),
                getUserRoleValue(user.getHighestRole()),
                getUserStatusValues(user.getUserStatus()),
                linkParams);
    }

    @Override
    protected UserTO doBuildUserTO(final User user) {
        return doBuildUserTO(user, null);
    }

    private String getAgeGroupValue(final AgeGroup ageGroup) {
        return ageGroupValues.get(ageGroup);
    }

    private String getEthnicityValue(final Ethnicity ethnicity) {
        return ethnicityValues.get(ethnicity);
    }

    private String getGenderValue(final Gender gender) {
        return genderValues.get(gender);
    }

    private String getLanguageValue(final Language language) {
        return languageValues.get(language);
    }

    private String getUserRoleValue(final RoleType roleType) {
        return userRoleValues.get(roleType);
    }

    private String getUserStatusValues(final UserStatus userStatus) {
        return userStatusValues.get(userStatus);
    }

}
