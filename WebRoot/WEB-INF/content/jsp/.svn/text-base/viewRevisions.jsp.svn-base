<%--
###################################################################################
viewRevisions.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displays a table with the revisions history of a catalog item.
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

<c:set var="itemType" scope="page">
	<bean:write name="item" property="type" />
</c:set>

<%-- Date & time printout format --%>
<c:set var="formatdatetime" scope="page">
	<bean:message key='app.format.datetime' />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Revision History Page Title
==================================================================
--%>

<form>
	<table border="0">
	<tbody>
		<tr>
			<td>
				<%-- Item's image (large icon) --%>
				<common:message key="imageClass" 
				altKey="content.ITEM.imageClass" 
				id="imageClass"/>
				<div class="image ${imageClass}"></div>
			</td>

			<td>
				<%-- Item's title --%>
				<h1>
					<bean:write name="item" property="name" scope="request" />:
					<bean:message key="content.viewRevisions.title" />
				</h1>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:set var="itemId" scope="request">
					<bean:write name="item" scope="request" property="id" />
				</c:set>

				<%-- The relative path to the editItem JSP in the current directory --%>
				<content:customType prefix="WEB-INF/content/jsp" type="${itemType}"
					postfix="editItem.jsp" id="customPath" />

				<input type="button"
					value="<bean:message key='app.action.done' />"
					onclick="window.location='<html:rewrite module='/content' page='/open/editItemFull${customPath}.do?itemId=${itemId}&itemType=${itemType}'/>'"
					class="done" />
			</td>
		</tr>
	</tbody>
	</table>
</form>

<%--
==================================================================
List revisions in a table
==================================================================
--%>
<p />
<table border="1" cellpadding="5" width="90%">

	<%-- Table header line --%>
	<thead>
		<tr>
			<th width="5%">
				<bean:message key="content.AuditMessage.version.label" />
			</th>
			<th width="20%">
				<bean:message key="content.AuditMessage.user.label" />
			</th>
			<th width="50%" align="left">
				<bean:message key="content.AuditMessage.date.label" />
			</th>
			<th width="25%">
				<bean:message key="content.AuditMessage.action.label" />
			</th>
		</tr>
	</thead>
	
	<tbody>	
		<logic:iterate name="viewRevisionsResult" scope="request" id="auditMessage"
			type="net.ruready.business.content.item.entity.audit.AuditMessage" indexId="ctr">
			<tr>
				<td>
					<bean:write name="auditMessage" property="version" />
				</td>
	
				<td>
					<bean:write name="auditMessage" property="email" />
				</td>
	
				<td>
					<bean:write name="auditMessage" property="date" format="${formatdatetime}" />
				</td>
	
				<td>
					<bean:write name="auditMessage" property="action" />
				</td>
			</tr>
		</logic:iterate>
	</tbody>	
</table>
