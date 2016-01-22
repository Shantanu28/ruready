<%--
###################################################################################
explorerTitle.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print an "explorer view" title for a tab.
This is used in item transfer pages to print both halves of the screen
(source and destination).

This is not a complete HTML page, just a snippet.
###################################################################################
--%>

<%@ tag display-name="Prints an explorer view of a catalog item"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/content"	prefix="content"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Reference to (name of) item we are printing --%>
<%@ attribute name="item" required="true"%>
<%-- Type of item: Source or Destination --%>
<%@ attribute name="type" required="true"%>
<%-- Scope of item bean --%>
<%@ attribute name="scope" required="false" %>
<%-- Additional parameters to append to children links --%>
<%@ attribute name="linkParams" required="false"%>

<%-- ============ Tag output variables ============ --%>

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

<%-- Set default values --%>
<c:if test="${empty scope}">
	<c:set var="scope" scope="page" value="page" />
</c:if>

<c:set var="itemType" scope="page">
	<bean:write name="${item}" property="type" scope="${scope}" />
</c:set>

<%-- ============ Tag code begins here ============ --%>

<table>
<tbody>
	<tr>
		<td align="left" valign="middle">
			<%-- Item's icon --%>
			<common:message key="content.${itemType}.iconClass" 
			altKey="content.ITEM.iconClass" 
			id="iconClass"/>
			<div class="icon ${iconClass}" />
			&nbsp;
		</td>
		
		<%-- Item's title --%>
		<td align="left" valign="middle">					
			<h2>
				<bean:message key="content.transferItem.${type}.label" />:
				<bean:write name="${item}" property="name" scope="${scope}" />
			</h2>
		</td>
	</tr>
</tbody>
</table>
