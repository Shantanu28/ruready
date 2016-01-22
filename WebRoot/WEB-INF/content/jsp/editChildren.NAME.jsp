<%--
###################################################################################
editChildren.NAME.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Children editing form for an item whose children comparator is of type NAME
(NameComparator).

See also: editItemFull.jsp
###################################################################################
--%>

<%@ page language="java"%>
<jsp:directive.page import="net.ruready.common.rl.CommonNames"/>
<%@page import="net.ruready.business.content.item.entity.Item"%>
<%@page import="java.util.Iterator"%>
<%@page import="net.ruready.business.common.tree.entity.Node"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

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

<c:set var="itemName">
	<bean:write name="item" property="name" scope="request" />
</c:set>

<c:set var="childType" scope="page">
	<bean:write name="item" property="childType" />
</c:set>
<c:set var="childrenplural" scope="page">
	<bean:message key="content.${childType}.labelplural" />
</c:set>
<c:set var="childsingle" scope="page">
	<bean:message key="content.${childType}.label" />
</c:set>

<c:set var="Names_MISC_INVALID_VALUE_INTEGER">
	<%="" + CommonNames.MISC.INVALID_VALUE_INTEGER%>
</c:set>

<%--============================ JSP body begins here ============================--%>

<logic:empty name="editItemFullForm" property="childrenForms"
	scope="request">
	<c:set var="messageArg" scope="page">
			${fn:toLowerCase(childrenplural)}
	</c:set>
	<br />
	<bean:message key="content.editItemFull.children.notfound"
		arg0="${messageArg}" />
	<br />
</logic:empty>

<table border="0" cellspacing="5">

	<%-- Table header line --%>
	<thead>
	<tr>
		<th align="left">
			#
		</th>
		<th align="left">
			<bean:message key="content.Item.type.label" />
		</th>
		<th>
			<bean:message key="content.Item.name.label" />
		</th>
		<th>
			<bean:message key="content.Item.comment.label" />
		</th>
		<th>
			<bean:message key="content.editItemFull.delete.label" />
		</th>
	</tr>
	</thead>
	
	<tbody>
	<%
		Item storedItem = (Item)request.getAttribute("storedItem");
		Iterator<Node> iterator = null;
		Node storedChild = null;
		if (storedItem != null) 
		{
			iterator = storedItem.getChildren().iterator();
		}
	%>
	<logic:iterate name="editItemFullForm" scope="request"
		property="childrenForms" id="childrenForms"
		type="net.ruready.web.content.item.form.EditItemForm" indexId="ctr">
		
		<%--
		 If the stored entity exists, iterate over it while iterating
		 over the form list.
		--%>
		<%
		if (iterator != null) 
		{ 
			storedChild = iterator.next();
			pageContext.setAttribute("storedChild", storedChild);
		} 
		%>
		
		<%-- properties that must be re-submitted with the form --%>
		<html:hidden name="childrenForms" indexed="true" property="id" />
		<html:hidden name="childrenForms" indexed="true" property="type" />
		<html:hidden name="childrenForms" indexed="true" property="localVersion" />
		<html:hidden name="childrenForms" indexed="true" property="readOnly" />
		<html:hidden name="childrenForms" indexed="true" property="serialNo" />
		
		<c:set var="thisChildType" scope="page">
			<bean:write name="childrenForms" property="type" />
		</c:set>

		<tr valign="top">
			<logic:equal name="childrenForms" property="readOnly" value="true">
				<%-- Item is read-only, display non-editable information --%>

				<%-- these properties must be now re-submitted with the form instead of using text boxes --%>
				<html:hidden name="childrenForms" indexed="true" property="name" />
				<html:hidden name="childrenForms" indexed="true" property="comment" />

				<td align="left">
					<c:out value="${ctr+1}" />.
				</td>
				<td>
					<bean:message key="content.${thisChildType}.label" />
				</td>
				<td>
					<bean:write name="childrenForms" property="name" />
				</td>
				<td>
					<content:comment name="${childrenForms}" />
				</td>
				<td align="center">
					<bean:message key="app.na.label" />
				</td>
			</logic:equal>

			<logic:notEqual name="childrenForms" property="readOnly" value="true">
				<%-- Item is modifiable, display editable fields --%>

				<td>
					<c:out value="${ctr+1}" />.
				</td>
				<td>
					<bean:message key="content.${thisChildType}.label" />
				</td>
				<td>
					<html:text name="childrenForms" indexed="true" property="name"
						size="20" />
					<content:conflict
					localEntity="${childrenForms.name}" storedEntity="${storedChild.name}" />
				</td>
				<td>
					<html:text name="childrenForms" indexed="true" property="comment"
						size="40" />
					<content:conflict
					localEntity="${childrenForms.comment}" storedEntity="${storedChild.comment}" />
				</td>
				<td align="center">
					<html:checkbox name="childrenForms" indexed="true"
						property="delete" />
				</td>
			</logic:notEqual>
		</tr>
	</logic:iterate>
	</tbody>
	
	<%-- Optional child addition --%>
	<tfoot>
	<tr>
		<td>
			<bean:message key="content.editItemFull.add.label" />
		</td>
		
		<%-- Child type select menu --%>
		<td align="center">
			<html:select name="editItemFullForm" property="childType">
				<html:optionsCollection name="childTypes" />
			</html:select>			
		</td>		
		
		<td>
			<html:text name="editItemFullForm" property="newChildForm.name"
				size="20" />
		</td>
		
		<td>
			<html:text name="editItemFullForm" property="newChildForm.comment"
				size="40" />
		</td>

		<%-- Delete checkbox is inapplicable here --%>
		<td align="center">
			<bean:message key="app.na.label" />
		</td>
	</tr>

	<%-- Optional child movement not applicable here --%>
	<%--
	<tr>
		<td>
			<bean:message key="content.editItemFull.move.label" />
		</td>
		<td colspan="3">
			${fn:toLowerCase(childsingle)} #
			<logic:equal name="editItemFullForm" property="moveFrom" value="${Names_MISC_INVALID_VALUE_INTEGER}">
				<html:text name="editItemFullForm" property="moveFrom" size="1" value="" />
			</logic:equal>
			<logic:notEqual name="editItemFullForm" property="moveFrom" value="${Names_MISC_INVALID_VALUE_INTEGER}">
				<html:text name="editItemFullForm" property="moveFrom" size="1" />
			</logic:notEqual>

			<bean:message key="content.editItemFull.to.label" />

			<logic:equal name="editItemFullForm" property="moveTo" value="${Names_MISC_INVALID_VALUE_INTEGER}">
				<html:text name="editItemFullForm" property="moveTo" size="1" value="" />
			</logic:equal>
			<logic:notEqual name="editItemFullForm" property="moveTo" value="${Names_MISC_INVALID_VALUE_INTEGER}">
				<html:text name="editItemFullForm" property="moveTo" size="1" />
			</logic:notEqual>
			.
		</td>

		// Delete checkbox is inapplicable here
		<td>
			&nbsp;
		</td>
	</tr>
	--%>
	</tfoot>
	
</table>
