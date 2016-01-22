<%--
###################################################################################
transferItem.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Item transfer page. Holds two views of the item tree: "source" and
"destination". Allows copying/moving/deleting items.

See also: viewContent.jsp, editItemFull.jsp
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content"
	prefix="content"%>

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

<%--
<c:set var="itemSourceId" scope="page">
	<bean:write name="itemSource" scope="request" property="id"/>
</c:set>

<c:set var="itemDestinationId" scope="page">
	<bean:write name="itemDestination" scope="request" property="id"/>
</c:set>
--%>

<%--============================ JSP body begins here ============================--%>
<%--
############################ 
Javascript script includes
############################ 
--%>
<script type="text/javascript"
	src="<html:rewrite module='' page='/js/common/formUtil.js' />"></script>

<%--
==================================================================
Transfer Page Title
==================================================================
--%>

<center>
<h1>
	<bean:message key="content.transferItem.title" />
</h1>
</center>

<%--
==================================================================
Transfer Section & Form: 
Left Tab: Source item
Middle Tab: Transfer buttons
Right Tab: Destination item
==================================================================
--%>

<html:form action="/open/transferItem">

	<%--
	item Ids so that we can find the source + destination items and re-attach
	it to the request.
	--%>
	<input type="hidden" name="itemSourceId" value="${itemSource.id}" />
	<input type="hidden" name="itemDestinationId"
		value="${itemDestination.id}" />

	<logic:messagesPresent>
		<span class="error" />
			<html:errors />
			<br/>
		</span>
	</logic:messagesPresent>

	<%-- Stylish submit buttons --%>
	<%--
	<p>
		<bean:message key="content.transferItem.instructions" />
		<br />

		<html:submit property="action_done" styleClass="done">
			<bean:message key="content.transferItem.action.done" />
		</html:submit>

		<html:submit property="action_move" styleClass="move">
			<bean:message key="content.transferItem.action.move" />
		</html:submit>

		<html:submit property="action_delete" styleClass="cancel">
			<bean:message key="content.transferItem.action.delete" />
		</html:submit>
	</p>
	--%>
	
	<%-- Height in percentage doesn't seem to work --%>
	<table width="95%" align="center" cellpadding="2" class="noborder">
		<%--
		==================================================================
		Headers (location in hierarchy)
		==================================================================
		--%>
		<thead>
		<tr>
			<%-- Source item --%>
			<td width="43%" valign="top">
				<content:explorerHeader item="itemSource" type="Source" scope="request"
					linkParams="&itemDestinationId=${itemDestination.id}" />
			</td>

			<td>&nbsp;</td>
			
			<%-- Destination item --%>
			<td width="43%" valign="top">
				<content:explorerHeader item="itemDestination" type="Destination" scope="request"
					linkParams="&itemSourceId=${itemSource.id}" />
			</td>
		</tr>
		
		<%--
		==================================================================
		Titles
		==================================================================
		--%>
		<tr>
			<%-- Source item --%>
			<td valign="top">
				<content:explorerTitle item="itemSource" type="Source" scope="request"
					linkParams="&itemDestinationId=${itemDestination.id}" />
			</td>

			<td>&nbsp;</td>
			
			<%-- Destination item --%>
			<td valign="top">
				<content:explorerTitle item="itemDestination" type="Destination" scope="request"
					linkParams="&itemSourceId=${itemSource.id}" />
			</td>
		</tr>
		</thead>
		
		<%--
		==================================================================
		Bodies
		==================================================================
		--%>
		<tbody>
		<tr>
			<%--
			@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			Destination window
			@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			--%>
			<td valign="top" class="cellthinborder">
				<%--
				==================================================================
				Useful buttons (a "functional toolbar")
				==================================================================
				--%>
				<table border="0" class="noborder" cellspacing="1">
				<tbody>
					<tr>
						<td>
							<content:transferButtonUp item="itemSource" type="Source" scope="request"
							linkParams="&itemDestinationId=${itemDestination.id}"/>
							&nbsp;
						</td>
	
						<%-- Don't show the delete button for the trash --%>
						<logic:equal name="itemSource" property="trash" value="false">
							<td>
							<html:submit property="action_delete" styleClass="cancel">
								<bean:message key="app.action.delete" />
							</html:submit>
							</td>
						</logic:equal>
								
					</tr>
				</tbody>
				</table>
				<p/>
				
				<%--
				==================================================================
				List of children
				==================================================================
				--%>
				<content:explorerBody item="itemSource" type="Source" select="true"
					linkParams="&itemDestinationId=${itemDestination.id}" />
			</td>
			
			<%--
			@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			Middle tab with functional buttons
			@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			--%>
			<td class="cellnoborder" valign="middle">
				<table align="center">
				<tbody>
				
				<tr>		
					<%-- Show copy button if the source item is not the trash can --%>
					<td align="center" valign="middle">
						<logic:equal name="itemSource" property="trash" value="false">
							<html:submit property="action_copy" styleClass="edit">
								<bean:message key="app.action.copy" />
							</html:submit>
						</logic:equal>
						<logic:equal name="itemSource" property="trash" value="true">
							&nbsp;
						</logic:equal>
					</td>
	
					<td align="center" valign="middle" class="bigtext">
						<logic:equal name="itemSource" property="trash" value="false">
							&rarr;
						</logic:equal>
						<logic:equal name="itemSource" property="trash" value="true">
							&nbsp;
						</logic:equal>
					</td>
				</tr>
				
				<tr>
					<td align="center" valign="middle">
						<html:submit property="action_move" styleClass="move">
							<bean:message key="app.action.move" />
						</html:submit>
						</td><td align="center" valign="middle" class="bigtext">
						&rarr;
					</td>
				</tr>
				
				</tbody>
				</table>
			</td>

			<%--
			@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			Destination window
			@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			--%>
			<td valign="top" class="cellthinborder">
				<%--
				==================================================================
				Useful buttons (a "functional toolbar")
				==================================================================
				--%>
				<table border="0" class="noborder" cellspacing="1">
				<tbody>
					<tr>
						<td>
							<content:transferButtonUp item="itemDestination" type="Destination" scope="request"
							linkParams="&itemSourceId=${itemSource.id}"/>
							&nbsp;
						</td>
					</tr>
				</tbody>
				</table>
				<p/>
				
				<%--
				==================================================================
				List of children
				==================================================================
				--%>
				<content:explorerBody item="itemDestination" type="Destination"
					select="false" linkParams="&itemSourceId=${itemSource.id}" />
			</td>
		</tr>
		
		</tbody>
	</table>

</html:form>
