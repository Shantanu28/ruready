<%--
###################################################################################
itemTitle.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Item & children editing form's default title section.

See also: /*/editItem.jsp in this directory.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<c:set var="itemName">
	<bean:write name="item" property="name" scope="request" />
</c:set>

<c:set var="childType" scope="page">
	<bean:write name="item" property="childType" />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Item's Title & parent hierarchy
==================================================================
--%>

<content:itemTitle item="item" view="edit" scope="request" />
<hr align="left" class="itemseparator" noshade="noshade" />
<p />

<%--
==================================================================
Item latest revision information
==================================================================
--%>
<p>
	<content:auditMessage name="${item.latestMessage}" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<%-- Link to revision history --%>
	<html:link module="/content"
		page="/open/viewRevisions.do?itemId=${item.id}&itemType=${item.type}">
		<bean:message key="content.viewRevisions.link.title" />
	</html:link>
</p>
