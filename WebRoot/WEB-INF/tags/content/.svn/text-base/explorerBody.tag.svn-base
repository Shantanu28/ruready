<%--
###################################################################################
explorerBody.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Print an "explorer view" body for a catalog item. This item's children.
This is used in item transfer pages to print both halves of the screen
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
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Reference to (name of) item we are printing --%>
<%@ attribute name="item" required="true"%>
<%-- Type of item: Source or Destination --%>
<%@ attribute name="type" required="true"%>
<%-- A flag for printing item selection multiboxes. If set to true,
this tag must be enclosed by an <html:form> tag. --%>
<%@ attribute name="select" required="true"%>
<%-- Additional parameters to append to children links --%>
<%@ attribute name="linkParams" required="false"%>

<%-- ============ Tag output variables ============ --%>

<%-- ============ Tag code begins here ============ --%>

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

<%--
==================================================================
List the item's children (if applicable for this item type)
==================================================================
--%>

<%-- Indent the children section --%>
<div id="children">

<%-- This item may have children --%>
<logic:notEmpty name="${item}" property="childType">
	<c:set var="childType" scope="page">
		<bean:write name="${item}" property="childType" />
	</c:set>
	<c:set var="categories" scope="page">
		<bean:message key="content.${childType}.labelplural" />
	</c:set>

	<%-- Children title and useful links --%>
	<table border="0">
	<tbody>
	<tr>
	<%-- Children collection title --%>
	<td>
	<h2>${categories}</h2>
	</td>
	</tr>
	</tbody>
	</table>

	<%--
		################################################################
		Loop over children and display them in the trasnfer tab
		################################################################
		--%>
	<logic:empty name="${item}" property="children" scope="request">
		<bean:message key="content.editItemFull.children.notfound"
			arg0="${categories}" />
	</logic:empty>

	<logic:notEmpty name="${item}" property="children" scope="request">
		<table border="0">
		<tbody>
		<logic:iterate id="child" name="${item}" property="children"
			scope="request" type="net.ruready.business.content.item.entity.Item">

			<%-- Useful aliases --%>
			<c:set var="itemId" scope="page">
				<bean:write name="child" scope="page" property="id" />
			</c:set>
			<c:set var="itemType" scope="page">
				<bean:write name="child" property="type" />
			</c:set>

			<tr>
			<%-- Child item's icon --%>
			<td>
				<%-- Child item's icon --%>
				<common:message key="content.${itemType}.iconClass" 
				altKey="content.ITEM.iconClass" 
				id="iconClass"/>
				<div class="icon ${iconClass}" />
			</td>
			<td>
							&nbsp;
							<%-- Child is a node, display link --%>
			<logic:equal name="child" scope="page" property="leaf" value="false">
				<%--
								<html:link module="/content"
								page="/open/transferItem.do?item${type}Id=${itemId}${linkParams}">
									<bean:write name="child" scope="page" property="name" />
								</html:link>
								--%>
				<%--
								Using a post method for the link to preserve source item selection
								while navigating the destination.
								--%>
				<c:set var="childLink">
					<html:rewrite module='/content'
						page='/open/transferItem.do?item${type}Id=${itemId}${linkParams}' />
				</c:set>
				<html:link href=""
					onclick="return submitLink('transferItemForm', '${childLink}');">
					<bean:write name="child" scope="page" property="name" />
				</html:link>
			</logic:equal>

			<%-- Child is a leaf, don't display a link --%>
			<logic:equal name="child" scope="page" property="leaf" value="true">
				<bean:write name="child" scope="page" property="name" />
			</logic:equal>

			<%-- Display number of sub-categories of this child --%>
			<logic:greaterThan name="child" scope="page" property="numChildren"
				value="0">
			    (<bean:write name="child" scope="page" property="numChildren" />)
			</logic:greaterThan>
			</td>
			<c:if test="${select}">
				<td>
								&nbsp;
								<c:if test="${!child.readOnly && !child.unique}">
					<%-- Item is modifiable, display a selection check box --%>
					<html:multibox name="transferItemForm" property="selectedItems">
						<bean:write name="child" property="id" />
					</html:multibox>
				</c:if>
				</td>
			</c:if>
			</tr>
		</logic:iterate>
		</tbody>
		</table>
	</logic:notEmpty>
	<p />
</logic:notEmpty>
</div>
<%-- id="children" --%>
