<%--
###################################################################################
footer.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

A common footer for pages. Contains useful links, disclaimers, copyright
messages, etc.
This is not a complete HTML page, just a snippet.
###################################################################################
--%>

<%--========================== Tag Library Declarations ==========================--%>

<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

<%-- Copyright information --%>
<div id="copyright">
	&copy; <bean:message key="app.layout.footer.years" />
	<a href="http://www.continue.utah.edu"><bean:message key="app.layout.footer.aoce" /></a>,
	<a href="http://www.utah.edu"><bean:message key="app.layout.footer.uofu" /></a>.
	<span class="copyright">
		<bean:message key="app.layout.footer.copyright" />
		</span>
</div>

<%-- Contact information --%>
<div id="contactinfo">
	<c:set var="helpemail">
		<bean:message key="app.layout.links.emailhelp" />
	</c:set>
	<bean:message key="app.layout.footer.address" />
	<bean:message key="app.layout.footer.emailprefix" />
   	<html:link href="mailto:${helpemail}">
   		<bean:message key="app.layout.links.emailhelp" />
   	</html:link>
</div>
