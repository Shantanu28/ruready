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
    
(c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Application main internal page. Pops right after logging in at the front page.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>
<%@ taglib uri="http://ruready.net/user" prefix="user"%>

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
==================================================================
Title
==================================================================
--%>
<table border="0" width="100%">
<tbody>
	<tr>
		<td align="center">
			<h1>
				<bean:message key="user.adminMain.title" />
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
	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Tab header row
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
	<thead>
	<tr>
		<th colspan="3">
			<common:messages prefix="" showInstructions="false" showRequiredHint="false"/>
		</th>
	</tr>
	<tr>
		<th class="tab" width="33%" align="left">
			<bean:message key="user.studentMain.myAccount.title" />
		</th>
		<th class="tab" width="33%" align="left">
			<bean:message key="user.adminMain.tools.title" />
		</th>
		<th class="tab" width="33%" align="left">
			<bean:message key="user.adminMain.demos.title" />
		</th>
	</tr>
	</thead>
	
	<tbody>
	<tr>
		<%--
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		My Account Tab Content
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
		<td class="regtext" valign="top">
			<ul>
				<li>
					<html:link module="/user"
						page="/secure/STUDENT/updateUser.do">
						<bean:message key="user.studentMain.myAccount.updateDetails" />
					</html:link>
				</li>
			</ul>
		</td>

		<%--
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		Tools Content
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
		<td class="regtext" valign="top">
			<ul>
				<%-- Content management component --%>
				<li>
					<html:link module="/content" page="/open/home.do" styleId="cms">
						<bean:message key="content.title.short" />
					</html:link>
				</li>

				<%-- Edit questions sub-component --%>
				<li>
					<html:link module="/content" page="/open/searchQuestion.do">
						<bean:message key="question.search.title" />
					</html:link>
				</li>
				<li>
					<html:link action="/open/ADMIN/searchUser">
						<bean:message key="user.adminSearchUser.title" />
					</html:link>
				</li>
				<li>
					<html:link action="/open/ADMIN/createUser">
						<bean:message key="user.adminCreateUser.title" />
					</html:link>
				</li>
				<li>
					<html:link action="/open/ADMIN/schools/pending/view">
						<bean:message key="user.adminMain.tools.viewPendingTeacherSchools"/>
					</html:link>
				</li>
				<li>
					<html:link action="/open/ADMIN/group/search">
						<bean:message key="user.adminMain.tools.manageGroups"/>
					</html:link>
				</li>
				<li>
					<html:link action="/open/ADMIN/school/search">
						<bean:message key="user.adminMain.tools.manageSchools"/>
					</html:link>
				</li>
			</ul>
		</td>

		<%--
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		Demos Content
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
		<td class="regtext" valign="top">
			<ul>
				<%-- Math parser demo --%>
				<li>
					<html:link module="/parser" page="/open/demo.do">
						<bean:message key="user.adminMain.demo.parserDemo.label" />
					</html:link>
				</li>
				
				<%-- Item tree demo (stand-alone) --%>
				<li>
					<html:link module="" page="/js/demo/tree/item-tree.jsp">
						<bean:message key="user.adminMain.demo.itemTree.label" />
					</html:link>
				</li>
			</ul>
		</td>

	</tr>
</tbody>
</table>

<table border="0" width="80%" cellspacing="10">
	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Second line of tabs
	TODO: replace with more flexible divs later on
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
	<thead>
	<tr>
		<th width="33%" align="left">
			&nbsp;
		</th>
		<th width="33%" align="left">
			&nbsp;
		</th>
		<th class="tab" width="33%" align="left">
			<bean:message key="user.adminMain.tests.title" />
		</th>
	</tr>
	</thead>
	
	
	<tbody>
	<tr>
		<td>
			&nbsp;
		</td>
		<td>
			&nbsp;
		</td>
		<%--
		
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		Test Content
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
		<td class="regtext" valign="top">
			<ul>
				<%-- Generating XHTML with Struts --%>
				<li>
					<html:link module="/demo" page="/open/strutsXhtml.do">
						<bean:message key="user.adminMain.tests.strutsXhtml.label" />
					</html:link>
				</li>
				
				<%-- Manipulating forms using DOM --%>
				<li>
					<html:link module="/demo" page="/open/addField.do">
						<bean:message key="user.adminMain.tests.addField.label" />
					</html:link>
				</li>
				<li>
					<html:link action="/open/ADMIN/randomUsers">
						<bean:message key="user.adminMain.tests.dummyUsers.label" />
					</html:link>
				</li>
			</ul>
		</td>
	</tr>

	</tbody>
</table>
