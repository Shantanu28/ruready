<%--
###################################################################################
explorerHeader.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print an "explorer view" header for a catalog item. This depicts the item and its
parent hierarchy. This is used in item transfer pages to print both halves of the screen
(source and destination).

This is not a complete HTML page, just a snippet.

Note: the page calling this tag must include the javascript script formUtil.js.
###################################################################################
--%>

<%@ tag display-name="Prints an explorer view of a catalog item"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Reference to (name of) item we are printing --%>
<%@ attribute name="item" required="true"%>
<%-- Type of item: Source or Destination --%>
<%@ attribute name="type" required="true"%>
<%-- Scope of item bean --%>
<%@ attribute name="scope" required="false"%>
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

<%--
==================================================================
Item's Title & parent hierarchy
==================================================================
--%>

<%--
Tab Title: N/A for now
--%>

<%--
List item's parents so that we can go up levels by clicking them
--%>
<span class="smalltext">
<%-- Link to editing children if the item is not read-only --%>
<%--
List item's parents so that we can go up levels by clicking them
--%>
<bean:size name="${item}" property="parents" scope="${scope}" id="size" />
<logic:iterate id="parent" name="${item}" property="parents"
	scope="${scope}" indexId="index"
	type="net.ruready.business.content.item.entity.Item">
	<c:set var="parentId" scope="page">
		<bean:write name="parent" property="id" scope="page" />
	</c:set>
	<c:set var="parentItemType" scope="page">
		<bean:write name="parent" property="type" />
	</c:set>
	<logic:notEqual value="${size-1}" name="index" scope="page">
		<%--
		In the parents tag, these links refer to ${STRUTS_MODULE_URL}/viewItemFull.do. Here
		we use the transferItem's setup action instead, to stay in the item transfer view. 
		TODO: add /${parentItemType} to view action for custom explorer views per
		(parent) item type.
		--%>
		<%--
		<html:link module="/content"
			page="/open/transferItem.do?item${type}Id=${parentId}${linkParams}">
			<bean:write name="parent" property="name" scope="page" />
		</html:link>
		--%>
		<%--
		Using a post method for the link to preserve source item selection
		while navigating the destination.
		--%>
		<c:set var="parentLink">
			<html:rewrite module='/content'	page='/open/transferItem.do?item${type}Id=${parentId}${linkParams}' />
		</c:set>
		<html:link href=""
			onclick="return submitLink('transferItemForm', '${parentLink}');">
			<bean:write name="parent" property="name" scope="page" />
		</html:link>
		&gt;
	</logic:notEqual>
	<logic:equal value="${size-1}" name="index" scope="page">
		<%--       	<bean:write name="parent" property="name" scope="page" /> --%>
		<%--
		<html:link module="/content"
			page="/open/transferItem.do?item${type}Id=${parentId}${linkParams}">
			<bean:write name="parent" property="name" scope="page" />
		</html:link>
		--%>
		<c:set var="parentLink">
			<html:rewrite module='/content'	page='/open/transferItem.do?item${type}Id=${parentId}${linkParams}' />
		</c:set>
		<html:link href=""
			onclick="return submitLink('transferItemForm', '${parentLink}');">
			<bean:write name="parent" property="name" scope="page" />
		</html:link>
	</logic:equal>
</logic:iterate>
</span>
