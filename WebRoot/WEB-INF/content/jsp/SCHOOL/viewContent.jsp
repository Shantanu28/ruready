<%--
###################################################################################
viewContent.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Default view of a catalog item's contents. This can be any item in the catalog's
hierarchy.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

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

<c:set var="itemId" scope="request">
	<bean:write name="item" scope="request" property="id" />
</c:set>

<c:set var="itemType" scope="page">
	<bean:write name="item" property="type" />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Item's Title & parent hierarchy
==================================================================
--%>

<content:itemTitle item="item" view="browse" scope="request" />
<hr align="left" class="itemseparator" noshade />
<p/>

<%--
==================================================================
Useful buttons (a "functional toolbar")
==================================================================
--%>
<%--
Check if there is a custom edit item JSP for this item type. If yes,
include it, otherwise include the default edit item JSP.
--%>
<%-- The relative path to the display JSP (for read-only items) in the current directory --%>
<content:customType prefix="WEB-INF/content/jsp" type="${itemType}" postfix="itemToolbar.jsp"
	id="customDisplayItemTypePath" />
<%-- Note: home dir for the following URL is the parent dir (..) --%>
<jsp:include page="..${customDisplayItemTypePath}/itemToolbar.jsp" />
<p />

<%--
==================================================================
Item properties
==================================================================
--%>

<table cellspacing="5">
<tbody>

	<%-- Display a textbox for the item's name --%>
	<tr valign="top">
		<th>
			<bean:message key="content.Item.name.label" />
		</th>
		<td>
			<c:set var="institutionTypeKey">
				net.ruready.business.content.world.entity.property.InstitutionType.${item.institutionType}.label
			</c:set>
			<c:set var="sectorKey">
				net.ruready.business.content.world.entity.property.Sector.${item.sector}.label
			</c:set>
			
			<b><bean:write name="item" property="name" /></b>
			&nbsp;&nbsp;&nbsp;
			<c:if test="${!empty item.institutionType}">
				<bean:message key="${institutionTypeKey}"/>
			</c:if>
			<c:if test="${!empty item.sector}">
			,&nbsp;&nbsp;
				<bean:message key="${sectorKey}"/>
				<bean:message key="content.SCHOOL.school.text"/>
			</c:if>
		</td>
	</tr>

	<%-- Display a textbox for the item's comment --%>
	<tr valign="top">
		<th>
			<bean:message key="content.Item.comment.label" />
		</th>
		<td>
			<bean:write name="item" property="comment" />
		</td>
	</tr>

	<%-- Display a textbox for the school's address --%>
	<tr valign="top">
		<th>
			<bean:message key="content.SCHOOL.address.label" />
		</th>
		<td>
			<bean:write name="item" property="address1" />
			<br/>
			<bean:write name="item" property="address2" />
		</td>
	</tr>

	<%-- Display a textbox for the school's zip code --%>
	<tr valign="top">
		<th>
			<bean:message key="content.SCHOOL.zipCode.label" />
		</th>
		<td>
			<bean:write name="item" property="zipCode" />
		</td>
	</tr>

	<%-- Display a textbox for the school's primary phone number --%>
	<tr valign="top">
		<th>
			<bean:message key="content.SCHOOL.phone1.label" />
		</th>
		<td>
			<bean:write name="item" property="phone1" />
		</td>
	</tr>

	<%-- Display a textbox for the school's secondary phone number --%>
	<tr valign="top">
		<th>
			<bean:message key="content.SCHOOL.phone2.label" />
		</th>
		<td>
			<bean:write name="item" property="phone2" />
		</td>
	</tr>

	<%-- Display a textbox for the school's fax number --%>
	<tr valign="top">
		<th>
			<bean:message key="content.SCHOOL.fax.label" />
		</th>
		<td>
			<bean:write name="item" property="fax" />
		</td>
	</tr>

	<%-- Display a textbox for the school's county --%>
	<tr valign="top">
		<th>
			<bean:message key="content.CITY.county.label" />
		</th>
		<td>
			<bean:write name="item" property="county" />
		</td>
	</tr>

	<%-- Display a textbox for the school's district --%>
	<tr valign="top">
		<th>
			<bean:message key="content.SCHOOL.district.label" />
		</th>
		<td>
			<bean:write name="item" property="district" />
		</td>
	</tr>

	<%-- Display a link for the school's web site --%>
	<tr valign="top">
		<th>
			<bean:message key="content.SCHOOL.url.label" />
		</th>
		<td>
			<c:set var="url"><bean:write name='item' property='url' /></c:set>
			<html:link href="${url}">${url}</html:link>
		</td>
	</tr>

</tbody>
</table>
