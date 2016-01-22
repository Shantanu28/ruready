<%--
###################################################################################
links.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Useful links on the common header of pages.
###################################################################################
--%>

<%--========================== Tag Library Declarations ==========================--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<%-- Refresh page after 60 seconds so that the date & time are updated --%>
<%-- <meta http-equiv="refresh" content="60" /> --%>

<%-- Date & time printout format --%>
<c:set var="formatshortdatetime" scope="page">
	<bean:message key='app.format.shortdatetime' />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%-- Application front page --%>
<html:link module="" page="">
	<bean:message key="app.layout.links.home" />
</html:link>

&nbsp;&#9830;&nbsp;

<%-- About us information page --%>
<html:link module="/user" page="/open/FRONT/about.do">
	<bean:message key="app.layout.links.about" />
</html:link>

&nbsp;&#9830;&nbsp;

<c:set var="helpemail">
	<bean:message key="app.layout.links.emailhelp" />
</c:set>
<html:link href="mailto:${helpemail}">
	<bean:message key="app.layout.links.contactus" />
</html:link>

&nbsp;&#9830;&nbsp;

<%-- TODO: add a feedback pop-up window like in R1 instead --%>
<html:link href="#">
	<bean:message key="app.layout.links.feedback" />
</html:link>

&nbsp;&#9830;&nbsp;

<%-- Link to parser demo --%>
<html:link module="/parser" page="/open/demo.do">
	<bean:message key="app.layout.links.parserDemo" />
</html:link>
