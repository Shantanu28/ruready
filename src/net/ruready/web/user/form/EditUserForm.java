/*****************************************************************************************
 * Source File: EditUserForm.java
 ****************************************************************************************/
package net.ruready.web.user.form;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.property.AgeGroup;
import net.ruready.business.user.entity.property.Ethnicity;
import net.ruready.business.user.entity.property.Language;
import net.ruready.common.discrete.Gender;
import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.util.EnumUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * User personal information form.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 3, 2007
 */
public class EditUserForm extends ValidatorActionForm implements ValueBean<User>,
		PubliclyCloneable, Resettable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditUserForm.class);

	// ========================= FIELDS ====================================

	// -------------------------------------------------------------
	// Type of edit operation
	// -------------------------------------------------------------
	// Create a new user or update an existing user
	// private boolean create = false;

	// -------------------------------------------------------------
	// List of input fields
	// -------------------------------------------------------------

	// User's email
	private String email;

	// User's password
	private String password;

	// ------------------------- User properties ---------------------------

	// Enumerated-type properties
	private String gender;

	private String ageGroup;

	private String ethnicity;

	private String language;

	// Person's name (optional)
	private String firstName;

	private String middleInitial;

	private String lastName;

	/**
	 * Student identification number.
	 */
	private String studentIdentifier;

	// -------------------------
	// Drop-down menu data: IDS
	// -------------------------

	// ---------------- Geographical location (optional) ----------------

	/**
	 * IDs of institutional hierarchy objects: country.
	 */
	private Long countryId;

	/**
	 * IDs of institutional hierarchy objects: state.
	 */
	private Long stateId;

	/**
	 * IDs of institutional hierarchy objects: city.
	 */
	private Long cityId;

	/**
	 * IDs of institutional hierarchy objects: school.
	 */
	private Long schoolId;

	// -------------------
	// Drop-down menu data
	// -------------------

	/**
	 * Data for drop-down menu to select user properties: gender property.
	 */
	private OptionList genderOptions;

	/**
	 * Data for drop-down menu to select user properties: ethnicity property.
	 */
	private OptionList ethnicityOptions;

	/**
	 * Data for drop-down menu to select user properties: age group property.
	 */
	private OptionList ageGroupOptions;

	/**
	 * Data for drop-down menu to select user properties: language property.
	 */
	private OptionList languageOptions;

	/**
	 * Drop down menu data with the list of catalogs.
	 */
	private OptionList catalogOptions;

	/**
	 * Drop down menu data with the list of worlds.
	 */
	private OptionList worldOptions;

	/**
	 * Drop down menu data with the list of countries in a world.
	 */
	private OptionList countryOptions;

	// ========================= CONSTRUCTORS ==============================

	public EditUserForm()
	{

	}

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyFrom(User valueObject, final ApplicationContext context)
	{
		logger.debug("copyFrom()");
		try
		{
			// Properties that don't need type conversions
			// PropertyUtils.copyProperties(this, user);
			setEmail(valueObject.getEmail());

			// Properties that don't need type conversions
			setFirstName(valueObject.getFirstName());
			setMiddleInitial(valueObject.getMiddleInitial());
			setLastName(valueObject.getLastName());
			setStudentIdentifier(valueObject.getStudentIdentifier());

			// Properties that do need type conversions
			setGender(valueObject.getGender().getType());
			setEthnicity(valueObject.getEthnicity().name());
			setAgeGroup(valueObject.getAgeGroup().name());
			setLanguage(valueObject.getLanguage().name());

			// ===========================================================
			// Convert from user teacher link property to drop-down menu
			// values (or by its ID)
			// ===========================================================
		}
		catch (Exception e)
		{
			logger.error("Could not copy properties VO -> EditUserForm, " + e.toString());
		}

	}

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(User valueObject, final ApplicationContext context)
	{
		logger.debug("copyTo()");
		try
		{
			// Properties that don't need type conversions
			// PropertyUtils.copyProperties(user, this);

			// Read-only properties are not copied
			// valueObject.setEmail(getEmail());

			// Properties that don't need type conversions
			valueObject.setFirstName(firstName);
			valueObject.setMiddleInitial(middleInitial);
			valueObject.setLastName(lastName);
			valueObject.setStudentIdentifier(getStudentIdentifier());

			// Properties that do need type conversions
			valueObject.setGender(EnumUtil.valueOf(Gender.class, getGender()));
			valueObject.setEthnicity(EnumUtil.valueOf(Ethnicity.class, getEthnicity()));
			valueObject.setAgeGroup(EnumUtil.valueOf(AgeGroup.class, getAgeGroup()));
			valueObject.setLanguage(EnumUtil.valueOf(Language.class, getLanguage()));

			// =================================================================
			// Teacher link conversion from drop-down menu value (or by its ID)
			// =================================================================

			// TODO remove this code once it has been referenced
			/*
			 * if ((getSchoolId() == null) || (getSchoolId() == 0)) {
			 * valueObject.setSchool(null); } else { // Retrieve teacher object from
			 * database AbstractWorldBD bdWorld = new StrutsWorldBD(valueObject, context);
			 * School school = bdWorld.findSchoolById(getSchoolId());
			 * valueObject.setSchool(school); }
			 */
		}
		catch (Exception e)
		{
			logger.error("Could not copy properties EditUserForm -> VO, " + e.toString());
		}
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EditUserForm clone()
	{
		EditUserForm dest = new EditUserForm();
		try
		{
			PropertyUtils.copyProperties(dest, this);
			dest.genderOptions = (genderOptions == null) ? genderOptions : genderOptions
					.clone();
			dest.ethnicityOptions = (ethnicityOptions == null) ? ethnicityOptions
					: ethnicityOptions.clone();
			dest.ageGroupOptions = (ageGroupOptions == null) ? ageGroupOptions
					: ageGroupOptions.clone();
			dest.languageOptions = (languageOptions == null) ? languageOptions
					: languageOptions.clone();
			dest.catalogOptions = (catalogOptions == null) ? catalogOptions
					: catalogOptions.clone();
			dest.worldOptions = (worldOptions == null) ? worldOptions : worldOptions
					.clone();
		}
		catch (Exception e)
		{
			logger.error("Could not clone form");
		}
		return dest;
	}

	// ========================= METHODS ===================================

	/**
	 * Print this form bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return StrutsUtil.formToString(this);
	}

	/**
	 * Check if the form is empty or partially filled.
	 * 
	 * @return is the form empty or partially filled
	 */
	public boolean isEmpty()
	{
		return false;
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		// If a setup method has been requested, skip validation
		if (!StrutsUtil.isMethodValidated(request))
		{
			return null;
		}

		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		// Custom validations should be added here
		logger.debug("validate");
		return errors;
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form values (this is NOT the Struts
	 * {@link #reset(ActionMapping, HttpServletRequest)} method). This method is called by
	 * the parent forms of this object.
	 */
	public void reset()
	{
		// Make sure we satisfy the validation rules
		email = null;
		password = null;
		gender = null;
		ethnicity = null;
		ageGroup = null;
		language = null;
		firstName = null;
		middleInitial = null;
		lastName = null;
		countryId = null;
		cityId = null;
		schoolId = null;
		studentIdentifier = null;
	}

	// ========================= IMPLEMENTATION: ValidatorActionForm =======

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		// Carry out all the Validator framework resets, if applicable
		super.reset(mapping, request);

		// Custom reset operations should be added here
		logger.debug("reset");
		reset();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
		// Trim spaces
		if (this.email != null)
		{
			this.email = this.email.trim();
		}
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the ageGroup
	 */
	public String getAgeGroup()
	{
		return ageGroup;
	}

	/**
	 * @param ageGroup
	 *            the ageGroup to set
	 */
	public void setAgeGroup(String ageGroup)
	{
		this.ageGroup = ageGroup;
	}

	/**
	 * @return the ethnicity
	 */
	public String getEthnicity()
	{
		return ethnicity;
	}

	/**
	 * @param ethnicity
	 *            the ethnicity to set
	 */
	public void setEthnicity(String ethnicity)
	{
		this.ethnicity = ethnicity;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
		if (this.firstName != null)
		{
			this.firstName = this.firstName.trim();
		}
	}

	/**
	 * @return the gender
	 */
	public String getGender()
	{
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender)
	{
		this.gender = gender;
	}

	/**
	 * @return the language
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
		if (this.lastName != null)
		{
			this.lastName = this.lastName.trim();
		}
	}

	/**
	 * @return the middleInitial
	 */
	public String getMiddleInitial()
	{
		return middleInitial;
	}

	/**
	 * @param middleInitial
	 *            the middleInitial to set
	 */
	public void setMiddleInitial(String middleInitial)
	{
		this.middleInitial = middleInitial;
		if (this.middleInitial != null)
		{
			this.middleInitial = this.middleInitial.trim();
		}
	}

	/**
	 * @return the schoolId
	 */
	public Long getSchoolId()
	{
		return schoolId;
	}

	/**
	 * @param schoolId
	 *            the schoolId to set
	 */
	public void setSchoolId(Long schoolId)
	{
		this.schoolId = schoolId;
	}

	/**
	 * @return the countryId
	 */
	public Long getCountryId()
	{
		return countryId;
	}

	/**
	 * @param countryId
	 *            the countryId to set
	 */
	public void setCountryId(Long countryId)
	{
		this.countryId = countryId;
	}

	/**
	 * Returns the cityId property.
	 * 
	 * @return the cityId
	 */
	public Long getCityId()
	{
		return cityId;
	}

	/**
	 * Sets a new cityId property value.
	 * 
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Long cityId)
	{
		this.cityId = cityId;
	}

	/**
	 * @return the stateId
	 */
	public Long getStateId()
	{
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(Long stateId)
	{
		this.stateId = stateId;
	}

	/**
	 * @return the studentIdentifier
	 */
	public String getStudentIdentifier()
	{
		return studentIdentifier;
	}

	/**
	 * @param studentIdentifier
	 *            the studentIdentifier to set
	 */
	public void setStudentIdentifier(String studentIdentifier)
	{
		this.studentIdentifier = studentIdentifier;
		if (this.studentIdentifier != null)
		{
			this.studentIdentifier = this.studentIdentifier.trim();
		}
	}

	/**
	 * @return the genderOptions
	 */
	public OptionList getGenderOptions()
	{
		return genderOptions;
	}

	/**
	 * @param genderOptions
	 *            the genderOptions to set
	 */
	public void setGenderOptions(OptionList genderOptions)
	{
		this.genderOptions = genderOptions;
	}

	/**
	 * @return the ethnicityOptions
	 */
	public OptionList getEthnicityOptions()
	{
		return ethnicityOptions;
	}

	/**
	 * @param ethnicityOptions
	 *            the ethnicityOptions to set
	 */
	public void setEthnicityOptions(OptionList ethnicityOptions)
	{
		this.ethnicityOptions = ethnicityOptions;
	}

	/**
	 * @return the ageGroupOptions
	 */
	public OptionList getAgeGroupOptions()
	{
		return ageGroupOptions;
	}

	/**
	 * @param ageGroupOptions
	 *            the ageGroupOptions to set
	 */
	public void setAgeGroupOptions(OptionList ageGroupOptions)
	{
		this.ageGroupOptions = ageGroupOptions;
	}

	/**
	 * @return the languageOptions
	 */
	public OptionList getLanguageOptions()
	{
		return languageOptions;
	}

	/**
	 * @param languageOptions
	 *            the languageOptions to set
	 */
	public void setLanguageOptions(OptionList languageOptions)
	{
		this.languageOptions = languageOptions;
	}

	/**
	 * @return the catalogOptions
	 */
	public OptionList getCatalogOptions()
	{
		return catalogOptions;
	}

	/**
	 * @param catalogOptions
	 *            the catalogOptions to set
	 */
	public void setCatalogOptions(OptionList catalogOptions)
	{
		this.catalogOptions = catalogOptions;
	}

	/**
	 * @return the worldOptions
	 */
	public OptionList getWorldOptions()
	{
		return worldOptions;
	}

	/**
	 * @param worldOptions
	 *            the worldOptions to set
	 */
	public void setWorldOptions(OptionList worldOptions)
	{
		this.worldOptions = worldOptions;
	}

	/**
	 * @return the countryOptions
	 */
	public OptionList getCountryOptions()
	{
		return countryOptions;
	}

	/**
	 * @param countryOptions
	 *            the countryOptions to set
	 */
	public void setCountryOptions(OptionList countryOptions)
	{
		this.countryOptions = countryOptions;
	}

}
