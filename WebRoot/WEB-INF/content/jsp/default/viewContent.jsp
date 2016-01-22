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
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
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
List the item's children (if applicable for this item type)
==================================================================
--%>

<%-- Indent the children section --%>
<div id="children">

	<%-- This item may have children --%>
	<logic:notEmpty name="item" property="childType">
		<c:set var="childType" scope="page">
			<bean:write name="item" property="childType" />
		</c:set>
		<c:set var="categories" scope="page">
			<bean:message key="content.${childType}.labelplural" />
		</c:set>

		<%-- Children title and useful links --%>
		<h2>${categories}</h2>

		<%-- Loop over children --%>
		<logic:empty name="item" property="children" scope="request">
			<br />
			<bean:message key="content.editItemFull.children.notfound"
				arg0="${categories}" />
		</logic:empty>

		<logic:notEmpty name="item" property="children" scope="request">
			<table>
			<tbody>
				<logic:iterate id="child" indexId="ctr" name="item" property="children"
					scope="request" type="net.ruready.business.content.item.entity.Item">
					<tr>
						<c:set var="childId" scope="page">
							<bean:write name="child" scope="page" property="id" />
						</c:set>
						<c:set var="childType" scope="page">
							<bean:write name="child" property="type" />
						</c:set>
						<td>
							<%-- Child item's icon --%>
							<common:message key="content.${childType}.iconClass" 
							altKey="content.ITEM.iconClass" 
							id="iconClass"/>
							<div class="icon ${iconClass}"></div>
						</td>
						<td>
							&nbsp;
							<html:link module="/content"
								page="/open/explore/viewFull.do?itemId=${childId}&itemType=${childType}">
								<bean:write name="child" scope="page" property="name" />
							</html:link>

							<%-- Display number of sub-categories of this child --%>
							<logic:greaterThan name="child" scope="page"
								property="numChildren" value="0">
			               	(<bean:write name="child" scope="page"
									property="numChildren" />)
			            </logic:greaterThan>
						</td>
						<td>
							&nbsp;&nbsp;
						</td>
						<td>
							<content:comment name="${child}" />
						</td>
					</tr>
				</logic:iterate>
			</tbody>
			</table>
		</logic:notEmpty>
		<p />
	</logic:notEmpty>
</div>
