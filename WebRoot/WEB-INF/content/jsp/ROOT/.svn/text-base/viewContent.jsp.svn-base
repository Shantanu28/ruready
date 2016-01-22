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
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

CMS component home page. View the list of catalogs (main directory).
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<%--
Don't list the item's hierarchy & edit buttons because it's trivial, but leave room for it to
conform with other pages
--%>
<content:itemTitle item="item" view="browse" scope="request" showParents="false" />
<hr align="left" class="itemseparator" noshade="noshade" />
<p />

<%--
==================================================================
Useful buttons (a "functional toolbar")
==================================================================
--%>
<%-- We could instead use the itemToolbar.jsp overriding pattern. See default/viewContent.jsp. --%>
<content:itemToolbar item="item" scope="request" />
<p/>

<%--
==================================================================
List the available catalogs (root nodes of each catalog tree)
Provide a little bit more info than in other page.s
==================================================================
--%>

<%-- Indent the children section --%>
<div id="children">
	<bean:size name="item" property="children" scope="request" id="size" />

	<%--
	Print this if there are no catalogs in the database yet. Because the
	trash always exists, if the size of the collection <= 1, there are no catalogs.
	--%>
	<logic:lessEqual name="size" value="1">
		<c:set var="catalogNameKey" scope="page">
			<bean:message key="content.CATALOG.labelplural" />
		</c:set>
		<c:set var="messageArg" scope="page">
			${fn:toLowerCase(catalogNameKey)}
		</c:set>
		<tr>
			<td colspan="2">
				<bean:message key="content.viewFull.notfound" arg0="${messageArg}" />
			</td>
		</tr>
	</logic:lessEqual>

	<%-- Print the list of catalogs --%>
	<logic:greaterThan name="size" value="1">
		<%-- Children title --%>
		<h2>
			<bean:message key="content.root.childrendescription" />
		</h2>

		<table>
		<tbody>
		<logic:iterate id="child" indexId="ctr" name="item" property="children"
			scope="request" type="net.ruready.business.content.main.entity.MainItem">
			<tr>
				<c:set var="childId" scope="page">
					<bean:write name="child" scope="page" property="id" />
				</c:set>
				<c:set var="childType" scope="page">
					<bean:write name="child" scope="page" property="type" />
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
					<%-- Catalog name & link --%>
					<html:link module="/content"
						page="/open/explore/viewFull.do?itemId=${childId}&itemType=${childType}">
						<b><bean:write name="child" scope="page" property="name" /></b>
					</html:link>
		
					<logic:notEqual name="childType" value="DEFAULT_TRASH" scope="page" >
						<%-- Catalog description (comment) --%>
						&nbsp;-&nbsp;
						<content:comment name="${child}" />
					</logic:notEqual>
				</td>
			</tr>
		</logic:iterate>
		</tbody>
		</table>
	</logic:greaterThan>
</div>
