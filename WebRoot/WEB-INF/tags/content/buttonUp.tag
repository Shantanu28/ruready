<%--
###################################################################################
buttonUp.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print an up-level button (link to the item's parent view page). Must be enclosed
in a <form>.
###################################################################################
--%>

<%@ tag display-name="Prints an up-level button"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- resource key (instance variable name) of the item --%>
<%@ attribute name="item" required="true"%>

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

<%-- ============ Tag code begins here ============ --%>

<%-- Find the item's immediate parent in the item's parent list --%>
<bean:size name="${item}" property="parents" scope="request" id="size" />

<%-- If this is the root item (parent.size = 1), have up-level go back to the root --%>
<logic:equal name="size" value="1">
	<bean:define id="parent" name="${item}" property="parents[0]" scope="request" />
	<c:set var="parentsTagItemId" scope="page">
		<bean:write name="parent" property="id" />
	</c:set>
	<c:set var="parentsTagItemType" scope="page">
		<bean:write name="parent"  property="type" />
	</c:set>

	<%-- Use &uarr;&nbsp; to add an arrow up. Doesn't look so nice on the screen though. --%>
	<input type="button"
	value="<bean:message key='content.itemToolbar.uplevel.label' />"
	onclick="window.location='<html:rewrite module='/content' page='/open/explore/viewFull.do?itemId=${parentsTagItemId}&itemType=${parentsTagItemType}'/>'"
	class="done" />
</logic:equal>

<%-- If item has a parent, i.e. its item
hierarchy consists of at least 2 items (itself & the parent), button goes to item.parent --%>
<logic:greaterEqual name="size" value="2">
	<%-- Parent object is item.parent[item.parent.size-2] --%>
	<bean:define id="parent" name="${item}" property="parents[${size-2}]" scope="request" />
	<c:set var="parentsTagItemId" scope="page">
		<bean:write name="parent"  property="id" />
	</c:set>
	<c:set var="parentsTagItemType" scope="page">
		<bean:write name="parent"  property="type" />
	</c:set>

	<%-- Use &uarr;&nbsp; to add an arrow up. Doesn't look so nice on the screen though. --%>
	<input type="button"
	value="<bean:message key='content.itemToolbar.uplevel.label' />"
	onclick="window.location='<html:rewrite module='/content' page='/open/explore/viewFull.do?itemId=${parentsTagItemId}&itemType=${parentsTagItemType}'/>'"
	class="done" />
</logic:greaterEqual>
