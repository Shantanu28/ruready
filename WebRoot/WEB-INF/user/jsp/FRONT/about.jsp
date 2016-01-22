<%--
###################################################################################
about.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

"About us" page that explains the goals and target audience of our program.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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

<%--============================ JSP body begins here ============================--%>

<%--
======================================================================
Print main text describing the RUReady program
======================================================================
--%>

<h1>
	<bean:message key="user.about.title" />
</h1>

<html:img module="" pageKey="app.front.girlInLab"
	altKey="app.front.girlInLab.alt" styleClass="girlinlab"
	align="right" />

<p>
	<bean:message key="user.about.section1.body" />
</p>

<h2>
	<bean:message key="user.about.section2.title" />
</h2>
<p>
	<bean:message key="user.about.section2.body" />
</p>

<h2>
	<bean:message key="user.about.section3.title" />
</h2>
<ul>
	<li>
		<span class="title"><bean:message
				key="user.about.section3.bullet1.title" /></span>
		<bean:message key="user.about.section3.bullet1.body" />
	</li>
	<li>
		<span class="title"><bean:message
				key="user.about.section3.bullet2.title" /></span>
		<bean:message key="user.about.section3.bullet2.body" />
	</li>
	<li>
		<span class="title"><bean:message
				key="user.about.section3.bullet3.title" /></span>
		<bean:message key="user.about.section3.bullet3.body" />
	</li>
	<li>
		<span class="title"><bean:message
				key="user.about.section3.bullet4.title" /></span>
		<bean:message key="user.about.section3.bullet4.body" />
	</li>
	<li>
		<span class="title">
			<bean:message key="user.about.section3.bullet5.title" /></span>
		<bean:message key="user.about.section3.bullet5.body" />
	</li>
</ul>
<p />
<h1>
	<bean:message key="user.about.section4.title" />
</h1>
<ul>
	<li>
		<bean:message key="user.about.section4.bullet1.body" />
	</li>
	<li>
		<bean:message key="user.about.section4.bullet2.body" />
	</li>
	<li>
		<bean:message key="user.about.section4.bullet3.body" />
	</li>
	<li>
		<bean:message key="user.about.section4.bullet4.body" />
	</li>
	<li>
		<bean:message key="user.about.section4.bullet5.body" />
	</li>
</ul>

<h1>
	<bean:message key="user.about.doc.title" />
</h1>
<p>
	<bean:message key="user.about.doc.text1" />
</p>
<ul>
	<li>
		<a href="/ru2doc">
			<bean:message key="user.about.doc.javadoc" />
		</a>
	</li>
</ul>

<%--
======================================================================
Useful links
======================================================================
--%>
<p />
<table width="100%" border="0" cellpadding="5">
<tbody>
	<tr>
		<td class="h3" align="center">
			<bean:message key="user.home.newstudent"/>
			<html:link action="/secure/FRONT/enroll/student" styleClass="biglink">
				<bean:message key="user.home.createstudent"/>
			</html:link>
			<br />
			<bean:message key="user.home.newteacher"/>
			<html:link action="/secure/FRONT/enroll/teacher" styleClass="biglink">
				<bean:message key="user.home.createteacher"/>
			</html:link>
		</td>
	</tr>
	<tr>
		<td class="h3" align="center">
			<bean:message key="user.home.returntohome" />
			<html:link module="" page="">
				<bean:message key="user.home.link" />
			</html:link>
		</td>
	</tr>
</tbody>
</table>
