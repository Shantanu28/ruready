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

Custom view of the trash can. Lists the contents of the trash can
(list of deleted items) in a table.
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

<%-- Date & time printout format --%>
<c:set var="formatdatetime" scope="page">
	<bean:message key='app.format.datetime' />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Trash Title and custom actions. parent hierarchy is not displayed.
==================================================================
--%>

<html:form action="/open/deleteItem">
	<input type="hidden" name="itemId" value="${item.id}" />
	<input type="hidden" name="itemType" value="${item.type}" />

<table border="0" width="100%" class="noborder" cellspacing="1">
<tbody>
	<%--
	Don't show parents
	--%>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>

	<tr>
		<td class="cellnoborder" width="90%">
			<table border="0" class="noborder">
			<tbody>
				<tr>
					<td align="left">
						<%-- Item's image (large icon) --%>
						<common:message key="content.${item.type}.imageClass" 
						altKey="content.ITEM.imageClass" 
						id="imageClass"/>
						<div class="image ${imageClass}"></div>
						&nbsp;
					</td>
				<td>
					<%--
					Item's title
					--%>
					<h1>
						<bean:write name="item" property="name" scope="request" />
					</h1>
				</td>
				
				<%-- Useful links --%>
				<td>
					&nbsp;&nbsp;&nbsp;
					<input type="button"
						value="<bean:message key='content.viewItem.DEFAULT_TRASH.action.restore' />"
						onclick="window.location='<html:rewrite module='/content' page='/open/transferItem.do?action_setup_restore=true'/>'"
						class="edit" />
					<%--
					<html:submit property="submitRestore" value="${submitRestoreLabel}" styleClass="edit" />
					--%>
				</td>
				
				<td>
					&nbsp;
					<c:set var="submitDeleteLabel">
						<bean:message key='content.viewItem.DEFAULT_TRASH.action.delete' />
					</c:set>
					<html:submit value="${submitDeleteLabel}" styleClass="cancel" />
				</td>
		
			</tr>
		</tbody>
		</table>
		</td>
	</tr>	
</tbody>
</table>
<hr align="left" class="itemseparator" noshade="noshade" />

<%--
==================================================================
List deleted items
==================================================================
--%>

<p/>		
<span class="error" />
	<html:errors/>
</span>
	<logic:empty name="item" property="children" scope="request">
		<c:set var="messageArg" scope="page">
			<bean:write name="item" property="name" scope="request" />
		</c:set>
		<br/>
		<bean:message key="content.viewItem.DEFAULT_TRASH.children.empty" arg0="${messageArg}" />
		<p/>
  	</logic:empty>
  	
	<logic:notEmpty name="item" property="children" scope="request">
		<table border="1" cellpadding="5">			
			
			<%-- Table header line --%>
			<thead>
			<tr>
				<th width="5%">#</th>
				<th width="5%">ID</th>
				<th width="15%" align="left"><bean:message key="content.Item.type.label" /></th>
				<th align="left"><bean:message key="content.Item.name.label" /></th>
				<th align="left"><bean:message key="content.Item.comment.label" /></th>
				<th><bean:message key="content.Item.deletedDate.label" /></th>
				<th width="5%"><bean:message key="app.action.delete"/>?</th>
			</tr>
			</thead>
			
			<tbody>
			<logic:iterate name="item" scope="request" property="children"
			id="child" type="net.ruready.business.content.item.entity.Item" indexId="ctr">
				<c:set var="childType" scope="page">
					<bean:write name="child" property="type" />
				</c:set>
				
		    	<tr>
					<td>
						<c:out value="${ctr+1}" />.
					</td>
					<td align="center">
						<bean:write name="child" property="id" />
					</td>
					<td>
						<bean:message key="content.${childType}.label" />
					</td>
					<td>
						<bean:write name="child" property="name" />
		            </td>
					<td>
						<content:comment name="${child}" />				
		            </td>
		            
		            <td>
						<bean:write name="child" property="latestMessage.date"
						format="${formatdatetime}" />				
		            </td>
		            
					<%-- Item is modifiable, display a delete-selection check box --%>
					<td align="center">
						<c:if test="${!child.readOnly && !child.unique}" >
							<html:multibox name="transferItemForm" property="selectedItems">
								<bean:write name="child" property="id" />
							</html:multibox>
						</c:if>
					</td>

		        </tr>
		    </logic:iterate>
			</tbody>
			
		</table>
		<p />
	</logic:notEmpty>
	
</html:form>
