/*****************************************************************************************
 * Source File: EditItemForm.java
 ****************************************************************************************/
package net.ruready.web.content.item.form;

import javax.servlet.http.HttpServletRequest;

import net.ruready.business.common.tree.entity.Commentable;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.property.InstitutionType;
import net.ruready.business.content.world.entity.property.Sector;
import net.ruready.common.eis.entity.Resettable;
import net.ruready.common.eis.entity.ValueBean;
import net.ruready.common.pointer.PubliclyCloneable;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.util.EnumUtil;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.content.tag.form.TagCollectionMenuForm;
import net.ruready.web.select.exports.OptionList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Item edit form, containing its ID, data fields, and some useful transient
 * properties. This is a useful building block of nested Struts forms for item
 * <i>hierarchies</i>.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 1, 2007
 */
public class EditItemForm extends ValidatorActionForm implements ValueBean<Item>,
		PubliclyCloneable, Resettable, Commentable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = -3719108450498198575L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditItemForm.class);

	// ========================= FIELDS ====================================

	// -------------------------------------------------------------
	// List of input form fields for all possible item types
	// -------------------------------------------------------------

	/**
	 * ID of item whose contents are to be displayed in the view pages.
	 */
	private Long id;

	/**
	 * Type of item whose contents are to be displayed in the view pages.
	 */
	private String type;

	/**
	 * Version number of the client's local copy of the item.
	 */
	private Integer localVersion;

	/**
	 * Prospective parent item's ID.
	 */
	private Long parentId;

	/**
	 * If true, indicates that this is a yet-unsaved item. The {@link #parentId}
	 * field is then used to determine the parent to add this item under.This
	 * flag takes precedence to the {@link newParent} flag.
	 */
	private boolean newItem = false;

	/**
	 * If true, indicates that this is a saved item but its parent has changed.
	 * This flag is used to determine whether the item should be moved under the
	 * new parent, specified by the {@link #parentId} field.
	 */
	private boolean newParent = false;

	// ------------------------- Item Fields -------------------------------

	/**
	 * Item data fields - name.
	 */
	private String name;

	/**
	 * Item data fields - optional comment/description.
	 */
	private String comment;

	/**
	 * Item's serial number in a parent's children list.
	 */
	private String serialNo;

	/**
	 * Item access restriction flag.
	 */
	private boolean readOnly = false;

	// ------------------------- Course Fields -----------------------------

	/**
	 * University catalog number (e.g. "1210" for the "Calculus 1210" course).
	 */
	private String univCatalogNumber;

	/**
	 * University of Utah link to course syllabus / description / information.
	 */
	private String syllabusUrl;

	// ------------------------- Country Fields ----------------------------

	/**
	 * Phone extension for this country.
	 */
	private String phoneCode;

	// ------------------------- Document Fields ---------------------------

	/**
	 * Document content/text.
	 */
	private String content;

	// ------------------------- School Fields -----------------------------

	/**
	 * Institution type.
	 */
	private String institutionType;

	/**
	 * Sector (private/public/...).
	 */
	private String sector;

	/**
	 * School address (Line 1).
	 */
	private String address1;

	/**
	 * School address (Line 2).
	 */
	private String address2;

	/**
	 * School zip code.
	 */
	private String zipCode;

	/**
	 * County that the school resides in.
	 */
	private String county;

	/**
	 * School district of this school.
	 */
	private String district;

	/**
	 * School primary phone number.
	 */
	private String phone1;

	/**
	 * School secondary phone number.
	 */
	private String phone2;

	/**
	 * School fax number.
	 */
	private String fax;

	/**
	 * School's home page.
	 */
	private String url;

	// -------------------
	// Drop-down menu data
	// -------------------

	/**
	 * Data for drop-down menu to select the school institutionType property.
	 */
	private OptionList institutionTypeOptions;

	/**
	 * Data for drop-down menu to select the school sector property.
	 */
	private OptionList sectorOptions;

	// ------------------------- Subject Fields ----------------------------

	/**
	 * University catalog abbreviation (e.g. "MATH" for the subject
	 * "Mathematics"), or State abbreviation (e.g.: "UT" for the State of Utah).
	 */
	private String abbreviation = null;

	/**
	 * Contains drop-down menu fields of the question's item hierarchy, and
	 * related common operations that appear in search and edit question forms
	 * (course, topic, sub-topic menus).
	 */
	private TagCollectionMenuForm tagCollectionMenuForm = new TagCollectionMenuForm();

	// ------------------------- State Fields ------------------------------

	// ------------------------- Question Fields ---------------------------

	/**
	 * Question's level of difficulty.
	 */
	private String level;

	/**
	 * Precision [#digits] required in the answer
	 */
	private String precisionDigits = CommonNames.MISC.EMPTY_STRING
			+ ContentNames.QUESTION.DEFAULT_PRECISION_DIGITS;

	// ------------------------- Transient form fields ---------------------

	/**
	 * Marks item for deletion.
	 */
	private boolean delete = false;

	/**
	 * Selects item for a transfer operation.
	 */
	private boolean selected = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * A no-argument constructor required for a JavaBean.
	 */
	public EditItemForm()
	{
		super();
	}

	// ========================= IMPLEMENTATION: Object ====================

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

	// ========================= IMPLEMENTATION: ValueBean =================

	/**
	 * @param valueObject
	 * @param context
	 * @see net.ruready.common.eis.entity.ValueBean#copyFrom(net.ruready.common.pointer.ValueObject,
	 *      java.lang.Object[])
	 */
	public void copyFrom(final Item valueObject, final ApplicationContext context)
	{
		Item2FormCopier.copyProperties(this, valueObject);

		// Properties that do need type conversions
		switch (valueObject.getIdentifier())
		{
			case SCHOOL:
			{
				School school = (School) valueObject;
				if (school.getInstitutionType() != null)
				{
					setInstitutionType(school.getInstitutionType().name());
				}
				if (school.getSector() != null)
				{
					setSector(school.getSector().name());
				}
				break;
			}

			default:
			{

			}
		}

		// Tags in a nested form
		tagCollectionMenuForm.copyFrom(valueObject, context);
	}

	/**
	 * @param valueObject
	 * @param applicationContext
	 * @see net.ruready.common.eis.entity.ValueBean#copyTo(net.ruready.common.pointer.ValueObject,
	 *      net.ruready.common.rl.ApplicationContext)
	 */
	public void copyTo(Item valueObject, final ApplicationContext context)
	{
		Form2ItemCopier.copyProperties(valueObject, this);

		// Properties that do need type conversions
		switch (valueObject.getIdentifier())
		{
			case SCHOOL:
			{
				School school = (School) valueObject;

				if (institutionType != null)
				{
					school.setInstitutionType(EnumUtil.valueOf(InstitutionType.class,
							institutionType));
				}
				if (sector != null)
				{
					school.setSector(EnumUtil.valueOf(Sector.class, getSector()));
				}

				break;
			}

			default:
			{

			}
		}

		// Tags in a nested form
		tagCollectionMenuForm.copyTo(valueObject, context);
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EditItemForm clone()
	{
		EditItemForm dest = new EditItemForm();
		try
		{
			PropertyUtils.copyProperties(dest, this);

			dest.institutionTypeOptions = (institutionTypeOptions == null) ? institutionTypeOptions
					: institutionTypeOptions.clone();
			dest.sectorOptions = (sectorOptions == null) ? sectorOptions : sectorOptions
					.clone();

		}
		catch (Exception e)
		{
			logger.error("Could not clone form");
		}

		dest.setTagCollectionMenuForm(tagCollectionMenuForm.clone());

		return dest;
	}

	// ========================= IMPLEMENTATION: ValidatorActionForm =======

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
		// Carry out all the Validator framework validations
		ActionErrors errors = super.validate(mapping, request);

		// Custom validations should be added here
		if (logger.isDebugEnabled())
		{
			logger.debug("validate");
		}
		return errors;
	}

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
		if (logger.isDebugEnabled())
		{
			logger.debug("reset");
		}
		reset();
	}

	// ========================= IMPLEMENTATION: Resettable ================

	/**
	 * Reset the form fields. This is not the same as the Struts ActionForm
	 * reset method.
	 * 
	 * @see net.ruready.common.eis.Resettable#reset()
	 */
	public void reset()
	{
		if (tagCollectionMenuForm == null)
		{
			tagCollectionMenuForm = new TagCollectionMenuForm();
		}
		tagCollectionMenuForm.reset();

		// Make sure we satisfy the validation rules
		name = null;
		comment = null;
		serialNo = null;
		institutionType = null;
		sector = null;
		delete = false;
		selected = false;
	}

	// ========================= METHODS ===================================

	/**
	 * Copy transient form properties that are not item properties into another
	 * form.
	 * 
	 * @param dest
	 *            form to copy properties to
	 */
	public void copyTransientPropertiesTo(EditItemForm dest)
	{
		dest.setDelete(delete);
		dest.setSelected(selected);
	}

	/**
	 * Check if the form is empty or partially filled.
	 * 
	 * @return is the form empty or partially filled
	 */
	public boolean isEmpty()
	{
		return (TextUtil.isEmptyTrimmedString(name) && TextUtil
				.isEmptyTrimmedString(comment))
		/*
		 * && (serialNo == null)
		 */;
	}

	/**
	 * Set one of the menu ids.
	 * 
	 * @param itemType
	 *            tag item type corresponding to the menu
	 * @param itemId
	 *            id of selected item
	 */
	public void setMenuId(final ItemType itemType, final long itemId)
	{
		tagCollectionMenuForm.setMenuId(itemType, itemId);
	}

	/**
	 * Set one of the menu options.
	 * 
	 * @param itemType
	 *            tag item type corresponding to the menu
	 * @param options
	 *            menu option list
	 */
	public void setMenu(final ItemType itemType, final OptionList options)
	{
		tagCollectionMenuForm.setMenu(itemType, options);
	}

	// ========================= DELEGATE METHODS ==========================

	/**
	 * @return
	 * @see net.ruready.web.select.exports.OptionList#getSelectedIndex()
	 */
	public int getSelectedInstitutionTypeIndex()
	{
		institutionTypeOptions.setSelectedValue(institutionType);
		return institutionTypeOptions.getSelectedIndex();
	}

	/**
	 * @return
	 * @see net.ruready.web.select.exports.OptionList#getSelectedValue()
	 */
	public String getSelectedInstitutionType()
	{
		institutionTypeOptions.setSelectedValue(institutionType);
		return institutionTypeOptions.getSelectedValue();
	}

	/**
	 * @return
	 * @see net.ruready.web.select.exports.OptionList#getSelectedIndex()
	 */
	public int getSelectedSectorIndex()
	{
		sectorOptions.setSelectedValue(sector);
		return sectorOptions.getSelectedIndex();
	}

	/**
	 * @return
	 * @see net.ruready.web.select.exports.OptionList#getSelectedValue()
	 */
	public String getSelectedSector()
	{
		sectorOptions.setSelectedValue(sector);
		return sectorOptions.getSelectedValue();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	/**
	 * @return the level
	 */
	public String getLevel()
	{
		return level;
	}

	/**
	 * @return the level
	 */
	public int getLevelAsInteger()
	{
		return TextUtil.getStringAsInteger(level);
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level)
	{
		this.level = level;
	}

	/**
	 * @return the precisionDigits
	 */
	public String getPrecisionDigits()
	{
		return precisionDigits;
	}

	/**
	 * @param precisionDigits
	 *            the precisionDigits to set
	 */
	public void setPrecisionDigits(String precisionDigits)
	{
		this.precisionDigits = precisionDigits;
	}

	/**
	 * @return the syllabusUrl
	 */
	public String getSyllabusUrl()
	{
		return syllabusUrl;
	}

	/**
	 * @param syllabusUrl
	 *            the syllabusUrl to set
	 */
	public void setSyllabusUrl(String syllabusUrl)
	{
		this.syllabusUrl = syllabusUrl;
	}

	/**
	 * @return the univCatalogNumber
	 */
	public String getUnivCatalogNumber()
	{
		return univCatalogNumber;
	}

	/**
	 * @param univCatalogNumber
	 *            the univCatalogNumber to set
	 */
	public void setUnivCatalogNumber(String univCatalogNumber)
	{
		this.univCatalogNumber = univCatalogNumber;
	}

	/**
	 * @return the phoneCode
	 */
	public String getPhoneCode()
	{
		return phoneCode;
	}

	/**
	 * @return the phoneCode
	 */
	public int getPhoneCodeAsInteger()
	{
		return TextUtil.getStringAsInteger(phoneCode);
	}

	/**
	 * @param phoneCode
	 *            the phoneCode to set
	 */
	public void setPhoneCode(String phoneCode)
	{
		this.phoneCode = phoneCode;
	}

	/**
	 * Return the address1 property.
	 * 
	 * @return the address1
	 */
	public String getAddress1()
	{
		return address1;
	}

	/**
	 * Set a new value for the address1 property.
	 * 
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	/**
	 * Return the address2 property.
	 * 
	 * @return the address2
	 */
	public String getAddress2()
	{
		return address2;
	}

	/**
	 * Set a new value for the address2 property.
	 * 
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the abbreviation
	 */
	public String getAbbreviation()
	{
		return abbreviation;
	}

	/**
	 * @param abbreviation
	 *            the abbreviation to set
	 */
	public void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}

	/**
	 * @return the serialNo
	 */
	public String getSerialNo()
	{
		return serialNo;
	}

	/**
	 * @return the serialNo
	 */
	public int getSerialNoAsInteger()
	{
		return TextUtil.getStringAsInteger(serialNo);
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Returns the localVersion property.
	 * 
	 * @return the localVersion
	 */
	public Integer getLocalVersion()
	{
		return localVersion;
	}

	/**
	 * Sets a new localVersion property value.
	 * 
	 * @param localVersion
	 *            the localVersion to set
	 */
	public void setLocalVersion(Integer localVersion)
	{
		this.localVersion = localVersion;
	}

	/**
	 * @return the delete
	 */
	public boolean isDelete()
	{
		return delete;
	}

	/**
	 * @param delete
	 *            the delete to set
	 */
	public void setDelete(boolean delete)
	{
		this.delete = delete;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly()
	{
		return readOnly;
	}

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @return the newItem
	 */
	public boolean isNewItem()
	{
		return newItem;
	}

	/**
	 * @param newItem
	 *            the newItem to set
	 */
	public void setNewItem(boolean newItem)
	{
		this.newItem = newItem;
	}

	/**
	 * @return the newParent
	 */
	public boolean isNewParent()
	{
		return newParent;
	}

	/**
	 * @param newParent
	 *            the newParent to set
	 */
	public void setNewParent(boolean newParent)
	{
		this.newParent = newParent;
	}

	/**
	 * @return the tagCollectionMenuForm
	 */
	public TagCollectionMenuForm getTagCollectionMenuForm()
	{
		return tagCollectionMenuForm;
	}

	/**
	 * @param tagCollectionMenuForm
	 *            the tagCollectionMenuForm to set
	 */
	public void setTagCollectionMenuForm(TagCollectionMenuForm tagCollectionMenuForm)
	{
		this.tagCollectionMenuForm = tagCollectionMenuForm;
	}

	/**
	 * @return the institutionType
	 */
	public String getInstitutionType()
	{
		return institutionType;
	}

	/**
	 * @param institutionType
	 *            the institutionType to set
	 */
	public void setInstitutionType(String institutionType)
	{
		this.institutionType = institutionType;
	}

	/**
	 * @return the sector
	 */
	public String getSector()
	{
		return sector;
	}

	/**
	 * @param sector
	 *            the sector to set
	 */
	public void setSector(String sector)
	{
		this.sector = sector;
	}

	/**
	 * @return the county
	 */
	public String getCounty()
	{
		return county;
	}

	/**
	 * @param county
	 *            the county to set
	 */
	public void setCounty(String county)
	{
		this.county = county;
	}

	/**
	 * @return the district
	 */
	public String getDistrict()
	{
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(String district)
	{
		this.district = district;
	}

	/**
	 * Return the phone1 property.
	 * 
	 * @return the phone1
	 */
	public String getPhone1()
	{
		return phone1;
	}

	/**
	 * Set a new value for the phone1 property.
	 * 
	 * @param phone1
	 *            the phone1 to set
	 */
	public void setPhone1(String phone1)
	{
		this.phone1 = phone1;
	}

	/**
	 * Return the phone2 property.
	 * 
	 * @return the phone2
	 */
	public String getPhone2()
	{
		return phone2;
	}

	/**
	 * Set a new value for the phone2 property.
	 * 
	 * @param phone2
	 *            the phone2 to set
	 */
	public void setPhone2(String phone2)
	{
		this.phone2 = phone2;
	}

	/**
	 * Return the fax property.
	 * 
	 * @return the fax
	 */
	public String getFax()
	{
		return fax;
	}

	/**
	 * Set a new value for the fax property.
	 * 
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	/**
	 * @return the institutionTypeOptions
	 */
	public OptionList getInstitutionTypeOptions()
	{
		return institutionTypeOptions;
	}

	/**
	 * @param institutionTypeOptions
	 *            the institutionTypeOptions to set
	 */
	public void setInstitutionTypeOptions(OptionList institutionTypeOptions)
	{
		this.institutionTypeOptions = institutionTypeOptions;
	}

	/**
	 * @return the sectorOptions
	 */
	public OptionList getSectorOptions()
	{
		return sectorOptions;
	}

	/**
	 * @param sectorOptions
	 *            the sectorOptions to set
	 */
	public void setSectorOptions(OptionList sectorOptions)
	{
		this.sectorOptions = sectorOptions;
	}
}
