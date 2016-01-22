<%--
###################################################################################
transferButtonUp.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print an up-level button (link to the item's parent view page) for the item
transfer page. Must be enclosed in a <form>.

Note: the page calling this tag must include the javascript script formUtil.js.
###################################################################################
--%>

<%@ tag display-name="Prints an up-level button"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<%-- Find the item's immediate parent in the item's parent list --%>
<bean:size name="${item}" property="parents" scope="${scope}" id="size" />

<%-- If this is the root item (parent.size = 1), have up-level go back to the root --%>
<logic:equal name="size" value="1">
	<bean:define id="parent" name="${item}" property="parents[0]" scope="${scope}" />
	<c:set var="parentsTagItemId" scope="page">
		<bean:write name="parent"  property="id" />
	</c:set>
	
	<%--
	<input type="button"
	value="<bean:message key='content.itemToolbar.uplevel.label' />"
	onclick="window.location='<html:rewrite module='/content' page='/open/transferItem.do?item${type}Id=${parentsTagItemId}${linkParams}'/>';"
	class="done" />
	--%>
	
	<c:set var="upLevelLink">
		<html:rewrite module='/content' page='/open/transferItem.do?item${type}Id=${parentsTagItemId}${linkParams}' />
	</c:set>
	<input type="button"
	value="<bean:message key='content.itemToolbar.uplevel.label' />"
	onclick="return submitLink('transferItemForm', '${upLevelLink}');"
	class="done" />
	
</logic:equal>

<%-- If item has a parent, i.e. its item
hierarchy consists of at least 2 items (itself & the parent), button goes to item.parent --%>
<logic:greaterEqual name="size" value="2">
	<%-- Parent object is item.parent[item.parent.size-2] --%>
	<bean:define id="parent" name="${item}" property="parents[${size-2}]" scope="${scope}" />
	<c:set var="parentsTagItemId" scope="page">
		<bean:write name="parent"  property="id" />
	</c:set>
	
	<%--
	<input type="button"
	value="<bean:message key='content.itemToolbar.uplevel.label' />"
	onclick="window.location='<html:rewrite module='/content' page='/open/transferItem.do?item${type}Id=${parentsTagItemId}${linkParams}'/>';"
	class="done" />
	--%>

	<c:set var="upLevelLink">
		<html:rewrite module='/content' page='/open/transferItem.do?item${type}Id=${parentsTagItemId}${linkParams}' />
	</c:set>
	<input type="button"
	value="<bean:message key='content.itemToolbar.uplevel.label' />"
	onclick="return submitLink('transferItemForm', '${upLevelLink}');"
	class="done" />
	
</logic:greaterEqual>
