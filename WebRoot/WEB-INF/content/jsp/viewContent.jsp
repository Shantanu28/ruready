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

A front-end for viewing a catalog item's contents. This includes an appropriate
JSP according to the item type.

See also: editItemFull.jsp.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<c:set var="itemId" scope="request">
	<bean:write name="item" scope="request" property="id" />
</c:set>

<c:set var="itemType" scope="page">
	<bean:write name="item" property="type" />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%-- This is the right column (content division) of the page --%>
<%-- Regular node item --%>
<logic:equal scope="request" name="item" property="editAccessible" value="true">
	<%-- The relative path to the editItem JSP in the current directory --%>
	<content:customType prefix="WEB-INF/content/jsp" type="${itemType}"
		postfix="viewContent.jsp" id="customPath" />
	<%-- Include the specific item view's JSP --%>
	<jsp:include page=".${customPath}/viewContent.jsp" />
</logic:equal>
	
<%-- Leaf item that is inaccessible to standard editing, redirect to parent view --%>
<logic:equal scope="request" name="item" property="editAccessible" value="false">
	<meta http-equiv="refresh" content="0;URL=<html:rewrite module='/content'
	page='/open/explore/viewFull.do?itemId=${item.parent.id}&itemType=${item.parent.type}' />">	
</logic:equal>
