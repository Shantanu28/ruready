<%--
###################################################################################
main.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Application main internal page. Pops right after logging in at the front page.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/user" prefix="user"%>

<%--============================ Useful definitions ==============================--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Title
==================================================================
--%>
<table border="0" width="100%">
	<tbody>
		<tr>
			<td align="center">
				<h1>
					<bean:message key="user.teacherMain.title" />
				</h1>
			</td>
			<td align="right" width="25%">
				<user:perspectives module="/user" page="/open/ALL/main.do" />
			</td>
		</tr>
	</tbody>
</table>

<%--
==================================================================
Main Table with tabs
==================================================================
--%>
<table border="0" width="80%" cellspacing="10">
	<thead>
		<tr>
			<th colspan="2">
				<common:messages prefix="" showInstructions="false" showRequiredHint="false"/>
			</th>
		</tr>
		<tr>
			<th class="tab" width="50%" align="left">
				Test Links (placeholder)
			</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				<ul>
					<li>
						<html:link action="/open/TEACHER/groups/view">
							<bean:message key="user.teacherMain.myAccount.manageGroups"/>
						</html:link>
					</li>
					<li>
						<html:link action="/open/TEACHER/schools/view">
							<bean:message key="user.teacherMain.myAccount.manageSchools"/>
						</html:link>
					</li>
				</ul>
			</td>
			<td></td>
		</tr>
	</tbody>
</table>

