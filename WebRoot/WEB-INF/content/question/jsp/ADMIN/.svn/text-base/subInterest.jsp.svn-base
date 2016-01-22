<%--
###################################################################################
subInterest.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Display sub-interests of an interest category.
###################################################################################
--%>

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/util" prefix="util"%>

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

<%--============================ JSP body begins here ============================--%>

<h1><bean:message key="question.viewInterest.head" /><br/>${item.name}</h1>
<bean:size name="item" property="children" id="numSubInterests" />
<c:set var="itemsPerRow" value="2" />
<c:set var="tr_open" value="<tr>" />
<c:set var="tr_close" value="</tr>" />

<table cellspacing="2" border="0" width="100%">
<tbody>
	<nested:iterate name="item" property="children" indexId="ctr" id="subInterest">
		<c:if test="${ctr % itemsPerRow == 0}">
			${tr_open}
		</c:if>
		<td>
		 	&bull; ${subInterest.name}
		</td>
		<c:if test="${ctr % itemsPerRow == (itemsPerRow-1)}">
			${tr_close}
		</c:if>
	</nested:iterate>
</tbody>
</table>
