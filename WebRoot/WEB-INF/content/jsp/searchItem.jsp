<%--
###################################################################################
searchItem.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E

2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displays an item search form and the search results, if available.
###################################################################################
--%>

<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="net.ruready.web.common.rl.WebAppNames"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<c:set var="WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_ITEM_RESULT">
	<%="" + WebAppNames.REQUEST.ATTRIBUTE.SEARCH_ITEM_RESULT%>
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Search Page Title
==================================================================
--%>
<h1>
	<bean:message key="content.searchItem.title" />
</h1>

<%--
==================================================================
Search form
==================================================================
--%>

<bean:message key="content.searchItem.instructions" />
<p />

<html:form action="/open/searchItem">
	<%-- Required for dispatch action and submit buttons to work well together --%>

	<logic:messagesPresent>
		<span class="error"> <html:errors /> <br /> </span>
	</logic:messagesPresent>
	<table border="0">
	<tbody>
		<tr>
			<td>
				<bean:message key="content.Item.name.label" />:
			</td>
			<td>
				<html:text name="searchItemForm" property="name" size="25" />
			</td>
		</tr>
	</tbody>
	</table>
	<br />

	<%-- Submit buttons --%>
	<html:submit property="action_search" styleClass="edit">
		<bean:message key="app.action.search" />
	</html:submit>

	<html:submit property="action_setup_reset" styleClass="reset">
		<bean:message key="app.action.reset" />
	</html:submit>

</html:form>

<%--
==================================================================
List search results
==================================================================
--%>
<br />
<br />

<%-- Display results only if there are no validation errors. --%>
<logic:present
	name="${WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_ITEM_RESULT}">
	<bean:size name="${WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_ITEM_RESULT}"
		scope="request" id="size" />

	<logic:equal value="0" name="size">
		<c:set var="messageArg" scope="page">
			<bean:write name="searchItemForm" property="name" scope="request" />
		</c:set>
		<bean:message key="app.search.notfound" />
	</logic:equal>

	<logic:greaterThan name="size" value="0">
		<table border="1" cellpadding="5" width="90%">

			<%-- Table header line --%>
			<thead>
				<tr>
					<th width="5%">
						#
					</th>
					<th width="20%" align="left">
						<bean:message key="content.Item.type.label" />
					</th>
					<th width="20%" align="left">
						<bean:message key="content.Item.name.label" />
					</th>
					<th width="55%">
						<bean:message key="content.Item.location.label" />
					</th>
				</tr>
			</thead>
			
			<tbody>
				<logic:iterate name="searchItemResult" scope="request" id="item"
					type="net.ruready.business.content.item.entity.Item" indexId="ctr">

					<c:set var="itemId" scope="page">
						<bean:write name="item" scope="page" property="id" />
					</c:set>
					<c:set var="itemType" scope="page">
						<bean:write name="item" property="type" />
					</c:set>

					<tr>
						<td>
							<c:out value="${ctr+1}" />
							.
						</td>

						<td>
							<bean:write name="item" property="type" />
						</td>

						<td>
							<html:link module="/content"
								page="/open/explore/viewFull.do?itemId=${itemId}&itemType=${itemType}">
								<bean:write name="item" property="name" scope="page" />
							</html:link>
						</td>

						<%--
					<td>
						<content:comment name="${item}" />				
		            </td>
		            --%>

						<td>
							<bean:define id="localItem" name="item" toScope="request" />
							<content:parents item="localItem" />
						</td>

					</tr>
				</logic:iterate>
			</tbody>
		</table>
	</logic:greaterThan>
</logic:present>
