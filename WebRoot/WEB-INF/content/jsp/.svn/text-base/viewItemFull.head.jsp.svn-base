<%--
###################################################################################
viewItemFull.head.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Item & children editing form. Here one can edit the item,
add a new child, remove existing children, and update children's basic data.

See also: /*/viewContent.jsp in this directory.
###################################################################################
--%>

<%@ page language="java"%>
<%@page import="net.ruready.business.content.item.entity.ItemType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />

<c:choose>
	<c:when test="${empty param['itemType']}">
		<c:set var="itemType" scope="page"><%= ItemType.ROOT %></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="itemType" scope="page">${param['itemType']}</c:set>
	</c:otherwise>
</c:choose>
<%--
<c:set var="itemType" scope="page">
	<bean:write name="item" property="type" />
</c:set>
--%>

<%--============================ JSP body begins here ============================--%>

<%--
Check if there is a custom edit item head JSP for this item type. If yes,
include it, otherwise include the default edit item JSP.
--%>
<%-- The relative path to the display JSP (for read-only items) in the current directory --%>
<content:customType prefix="WEB-INF/content/jsp" type="${itemType}" postfix="viewItemFull.head.jsp"
	id="customPath" />
<%--
<span style="color:white;background:green">param = ${param}, itemType = ${itemType}, ITEM HEAD PATH = ${customPath}</span>
--%>
<jsp:include page=".${customPath}/viewItemFull.head.jsp" />
