<%--
###################################################################################
ajaxUpdateUser.jsp   -Deprecated- 

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

AJAX functions used to populate a set of dependent drop-down menus in the user
detail update form. This is included there as a JSP, so it is possible to use
Struts tags in this file.

Attribute name conventions:
@request-attribute userAjaxForm - name of user editing form
###################################################################################
--%>

<%@ page language="java"%>
<%@ page import="net.ruready.business.content.item.entity.ItemType" %>
<%@ page import="net.ruready.web.common.rl.WebAppNames" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%-- <%@ taglib prefix="ajax" uri="javawebparts/ajaxparts/taglib" %> --%>

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

<%-- Aliases for Struts actions used in the included AJAX code --%>
<c:set var="populateActionUrl">
	<html:rewrite module="/user" page="/ajax/populateChildrenMenu.do?action_populate=true"/>
</c:set>
<c:set var="prePopulateActionUrl">
	<html:rewrite module="/user" page="/ajax/populateChildrenMenu.do?itemId=${userAjaxForm.schoolId}"/>
</c:set>
<c:set var="tokenPrefix">
	<%=WebAppNames.REQUEST.ATTRIBUTE.TOKEN.FOUND_ITEM_PREFIX%>
</c:set>
<c:set var="selectChildPrefix">
	<%--<%=WebAppNames.XML.ATTRIBUTE.SELECT_CHILD_PREFIX%>--%>
</c:set>

<c:set var="countryType">
	<%=ItemType.COUNTRY.getType()%>
</c:set>
<c:set var="federationType">
	<%=ItemType.FEDERATION.getType()%>
</c:set>
<c:set var="stateType">
	<%=ItemType.STATE.getType()%>
</c:set>
<c:set var="cityType">
	<%=ItemType.CITY.getType()%>
</c:set>
<c:set var="schoolType">
	<%=ItemType.SCHOOL.getType()%>
</c:set>

<c:set var="message_na">
	<bean:message key="app.na.label" />
</c:set>

<%--============================ JSP body begins here ============================--%>

<script type="text/javascript" language="javascript">
<!-- Hide script from old browsers

/* Global variables */
var globalSelectedValueStates;
var globalSelectedValueCitys;
var globalSelectedValueSchools;

/**
 * Initialization/setup/pre-processing run upon body load.
 */
function initMenus()
{
	<c:if test="${userAjaxForm.schoolId > 0}">
//		alert('${prePopulateActionUrl}');	
		retrieveURL('${prePopulateActionUrl}', 'editUserForm', populateChildrenPreProcessor, prePopulateHandler);
	</c:if>
}

/**
 * Pre-processor for children drop-down menu AJAX population request.
 *
 * nameOfFormToPost - name of form whose parameters are posted to "url" if this function
 * returns true
 * returns: true
 */
function populateChildrenPreProcessor(nameOfFormToPost)
{
	// Find form
	var form = document.forms[nameOfFormToPost].elements;
		
	/*
	if (parentId.length > 0) {
		return false;
	}
	*/
	return true;
}

/**
 * An XMLHttpRequest handler that constructs state drop-down menu from
 * the XML-formatted list of states, which is parsed from the XML response.
 *
 * cartXML - the XMLHttpRequest's XML cart content
 */
function populateStatesHandler(cartXML)
{
	// Find out if the selected country is a Federation or a Country. If yes, this will
	// be manifested by a different id for the <select> group in cartXML.
	// In this case, populate an empty states menu and populate the citys
	// menu with the data received.	
	
	// Find the first select group that has an id corresponding to a country/federation
	// TODO: instead of the "OR" statement, simply have another attribute for the
	// <select> element that indicates the specific type of parent (COUNTRY/FEDERATION),
	// where the id is still generic.
	// Add all country types here
	var groupIds = new Array(2);
	groupIds[0] = "${selectChildPrefix}${countryType}";
	groupIds[1] = "${selectChildPrefix}${federationType}";
	//alert("populateStatesHandler(): groupIds = " + groupIds + " length = " + groupIds.length);
	var itemGroup = findSelectElementByGroupIds(cartXML, groupIds);

	// All menus except direct child menu have to be cleared, because country has been changed
	//alert("populating elements with text...");
	populateElementWithText(cartXML, "citys", "${message_na}");
	populateElementWithText(cartXML, "schools", "${message_na}");
	//alert("after populating elements with text...");

	// Decide what to do next with the states and/or city menus, depending on country type
	if (itemGroup.getElementsByTagName("option").length <= 1) {
		// Empty country selection (only empty choice is on the options list) ==> empty
		// all menus below it
		//alert("No country selection");
		// Empty states menu
		populateElementWithText(cartXML, "states", "${message_na}");
	} else if (itemGroup.hasAttribute("id") && (itemGroup.getAttribute("id") == "${selectChildPrefix}${federationType}")) {
		// Federation, populate states
		//alert("Country is of federation type");
		populateDropDownMenu(cartXML, "${selectChildPrefix}${federationType}", "stateId", "states", 
		"retrieveURL('${populateActionUrl}&itemType=${stateType}&parentId=' + this.value, 'editUserForm', populateChildrenPreProcessor, populateCitysHandler);",
		"world_menu",
		globalSelectedValueStates);
	} else {
		//alert("Country is of country type");
		// Empty states menu
		populateElementWithText(cartXML, "states", "${message_na}");
		// Populate citys instead. Change the id of the data so that it looks like it came from a State
		// parent selection.
		itemGroup.setAttribute("id", "${selectChildPrefix}${stateType}");
		populateDropDownMenu(cartXML, "${selectChildPrefix}${stateType}", "cityId", "citys", 
		"retrieveURL('${populateActionUrl}&itemType=${cityType}&parentId=' + this.value, 'editUserForm', populateChildrenPreProcessor, populateSchoolsHandler);",
		"world_menu",
		globalSelectedValueCitys);
	}
}

/**
 * An XMLHttpRequest handler that constructs a city drop-down menu from
 * the XML-formatted list of citys, which is parsed from the XML response.
 *
 * cartXML - the XMLHttpRequest's XML cart content
 */
function populateCitysHandler(cartXML)
{
	var itemGroup = findSelectElementByGroupId(cartXML, "${selectChildPrefix}${stateType}");

	// All menus except direct child menu have to be cleared, because country has been changed
	populateElementWithText(cartXML, "schools", "${message_na}");

	// Decide what to do next	
	if (itemGroup.getElementsByTagName("option").length <= 1) {
		// Empty state selection (only empty choice is on the options list) ==> empty
		// all menus below it
		populateElementWithText(cartXML, "citys", "${message_na}");
	} else {
		populateDropDownMenu(cartXML, "${selectChildPrefix}${stateType}", "cityId", "citys", 
		"retrieveURL('${populateActionUrl}&itemType=${cityType}&parentId=' + this.value, 'editUserForm', populateChildrenPreProcessor, populateSchoolsHandler);",
		"world_menu",
		globalSelectedValueCitys);
	}
}

/**
 * An XMLHttpRequest handler that constructs a school drop-down menu from
 * the XML-formatted list of schools, which is parsed from the XML response.
 *
 * cartXML - the XMLHttpRequest's XML cart content
 */
function populateSchoolsHandler(cartXML)
{
	var itemGroup = findSelectElementByGroupId(cartXML, "${selectChildPrefix}${cityType}");

	// All menus except direct child menu have to be cleared, because country has been changed

	// Decide what to do next	
	if (itemGroup.getElementsByTagName("option").length <= 1) 
	{
		// Empty city selection (only empty choice is on the options list) ==> empty
		// all menus below it
		populateElementWithText(cartXML, "schools", "${message_na}");
	} 
	else 
	{
		populateDropDownMenu(cartXML, "${selectChildPrefix}${cityType}", "schoolId", "schools",
		null,
		"world_menu",
		globalSelectedValueSchools);
	}
}

/**
 * An XMLHttpRequest handler that constructs all drop-down menus from
 * menu data parsed from the XML response.
 *
 * cartXML - the XMLHttpRequest's XML cart content
 */
function prePopulateHandler(cartXML)
{
	// Populate country menu	
	// N/A for now
	
	// Populate state menu	
	globalSelectedValueStates = '${userAjaxForm.stateId}'; // '${user.school.parent.parent.id}';
	populateStatesHandler(cartXML);

	// Populate city menu	
	globalSelectedValueCitys = '${userAjaxForm.cityId}'; // '${user.school.parent.id}';
	populateCitysHandler(cartXML);
	
	// Populate school menu	
	globalSelectedValueSchools = '${userAjaxForm.schoolId}'; // '${user.school.id}';
	populateSchoolsHandler(cartXML);
}

/**
 * A utility function that populates the contents of a division on the page
 * with some text.
 *
 * cartXML - the XMLHttpRequest's XML cart content
 * targetDivId - target page division ID whose contents is filled
 * text - text to fill this division with
 */
function populateElementWithText(cartXML, targetDivId, text)
{
	document.getElementById(targetDivId).innerHTML = text;
}

// End hiding script from old browsers -->
</script>
