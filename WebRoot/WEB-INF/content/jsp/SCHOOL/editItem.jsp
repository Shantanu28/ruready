<%--
###################################################################################
editItem.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education, University of Utah.
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Country-type Item custom editing form. This is not a full HTML page and included in
other edit pages inside an <html:form> tag and a <nested:nest> whose scoping object
is an EditItemForm.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content"
	prefix="content"%>

<%--============================ Useful definitions ==============================--%>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Item properties
==================================================================
--%>

<table cellspacing="5">
<tbody>

	<%-- Display a textbox for the item's name --%>
	<tr valign="top">
		<td>
			<bean:message key="content.Item.name.label" />
		</td>
		<td>
			<nested:text property="name" size="25" />
			<content:conflict localEntity="${item.name}" storedEntity="${storedItem.name}" />
		</td>
	</tr>

	<%-- Display a textbox for the item's comment --%>
	<tr valign="top">
		<td>
			<bean:message key="content.Item.comment.label" />
		</td>
		<td>
			<nested:text property="comment" size="60" />
			<content:conflict localEntity="${item.comment}" storedEntity="${storedItem.comment}" />
		</td>
	</tr>

	<%-- Institution type field --%>
  	<tr>
    	<td>
    		<bean:message key="content.SCHOOL.institutionType.label"/>
    	</td>
    	<td>
			<nested:select property="institutionType" styleClass="school_menu">
				<nested:optionsCollection property="institutionTypeOptions" />
			</nested:select>			
    	</td>
    </tr>

	<%-- Sector field --%>
  	<tr>
    	<td>
    		<bean:message key="content.SCHOOL.sector.label"/>
    	</td>
    	<td>
			<nested:select property="sector" styleClass="school_menu">
				<nested:optionsCollection property="sectorOptions" />
			</nested:select>			
    	</td>
    </tr>

	<%-- Display a textbox for the school's address (Line 1) --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.address1.label" />
		</td>
		<td>
			<nested:text property="address1" size="100" />
			<content:conflict localEntity="${item.address1}" storedEntity="${storedItem.address1}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's address (Line 2) --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.address2.label" />
		</td>
		<td>
			<nested:text property="address2" size="100" />
			<content:conflict localEntity="${item.address2}" storedEntity="${storedItem.address2}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's zip code --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.zipCode.label" />
		</td>
		<td>
			<nested:text property="zipCode" size="10" />
			<content:conflict localEntity="${item.zipCode}" storedEntity="${storedItem.zipCode}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's primary phone number --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.phone1.label" />
		</td>
		<td>
			<nested:text property="phone1" size="20" />
			<content:conflict localEntity="${item.phone1}" storedEntity="${storedItem.phone1}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's secondary phone number --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.phone2.label" />
		</td>
		<td>
			<nested:text property="phone2" size="20" />
			<content:conflict localEntity="${item.phone2}" storedEntity="${storedItem.phone2}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's fax number --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.fax.label" />
		</td>
		<td>
			<nested:text property="fax" size="20" />
			<content:conflict localEntity="${item.fax}" storedEntity="${storedItem.fax}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's county --%>
	<tr valign="top">
		<td>
			<bean:message key="content.CITY.county.label" />
		</td>
		<td>
			<nested:text property="county" size="20" />
			<content:conflict localEntity="${item.county}" storedEntity="${storedItem.county}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's district --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.district.label" />
		</td>
		<td>
			<nested:text property="district" size="20" />
			<content:conflict localEntity="${item.district}" storedEntity="${storedItem.district}" />
		</td>
	</tr>

	<%-- Display a textbox for the school's web site --%>
	<tr valign="top">
		<td>
			<bean:message key="content.SCHOOL.url.label" />
		</td>
		<td>
			<nested:text property="url" size="60" />
			<content:conflict localEntity="${item.url}" storedEntity="${storedItem.url}" />
		</td>
	</tr>

</tbody>
</table>
