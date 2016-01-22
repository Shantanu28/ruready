<%--
###################################################################################
createUser.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displays a form for creating a new user account (registration).
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

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

<c:set var="pagePrefix" value="${flowKeyPrefix}.entryForm"/>
<c:set var="fieldPrefix" value="net.ruready.business.user.entity.property.UserField"/>
<%--============================ JSP body begins here ============================--%>

<center><h1><bean:message key="${pagePrefix}.title" /></h1></center>

<%--
==================================================================
Registration form
==================================================================
--%>
<html:form method="post">
	<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">
	
	<div id="entryForm">
		<common:messages prefix="${pagePrefix}"/>
		<%-- Email field --%>
		<div>
			<label for="email"><bean:message key="${fieldPrefix}.EMAIL.label"/><common:required/>:</label>
			<span>
				<html:text property="user.email" styleId="email" size="45" maxlength="45"/>
			</span>
		</div>
		<%-- First Name --%>
		<div>
			<label for="firstName"><bean:message key="${fieldPrefix}.FIRST_NAME.label"/>:</label>
			<span>
				<html:text property="user.firstName" styleId="firstName" size="45" maxlength="40"/>
			</span>
		</div>
		
		<%-- Middle Initial --%>
		<div>
			<label for="middleInitial"><bean:message key="${fieldPrefix}.MIDDLE_INITIAL.label"/>:</label>
			<span>
				<html:text property="user.middleInitial" styleId="middleInitial" size="1" maxlength="1"/>
			</span>
		</div>
		
		<%-- Last Name --%>
		<div>
			<label for="lastName"><bean:message key="${fieldPrefix}.LAST_NAME.label"/>:</label>
			<span>
				<html:text property="user.lastName" styleId="lastName" size="45" maxlength="40"/>
			</span>
		</div>
		
		<%-- User Role field --%>
		<div>
			<label for="userRole"><bean:message key="${fieldPrefix}.USER_ROLE.label"/><common:required/>:</label>
			<span>
				<html:select styleId="userRole" property="role">
					<html:optionsCollection property="options.roleTypeOptions"/>
				</html:select>
			</span>
		</div>
		
		<%-- Gender field --%>
		<div>
			<label for="gender"><bean:message key="${fieldPrefix}.GENDER.label"/><common:required/>:</label>
			<span>
				<html:select styleId="gender" property="user.gender">
					<html:optionsCollection property="options.genderOptions"/>
				</html:select>
			</span>
		</div>
		
		<%-- Ethnicity field --%>
		<div>
			<label for="ethnicity"><bean:message key="${fieldPrefix}.ETHNICITY.label"/><common:required/>:</label>
			<span>
				<html:select styleId="ethnicity" property="user.ethnicity">
					<html:optionsCollection property="options.ethnicityOptions"/>
				</html:select>
			</span>
		</div>
		
		<%-- Age group field --%>
		<div>
			<label for="ageGroup"><bean:message key="${fieldPrefix}.AGE_GROUP.label"/><common:required/>:</label>
			<span>
				<html:select styleId="ageGroup" property="user.ageGroup">
					<html:optionsCollection property="options.ageGroupOptions"/>
				</html:select>
			</span>
		</div>
		
		<%-- Language field --%>
		<div>
			<label for="language"><bean:message key="${fieldPrefix}.LANGUAGE.label"/><common:required/>:</label>
			<span>
				<html:select styleId="language" property="user.language">
					<html:optionsCollection property="options.languageOptions"/>
				</html:select>
			</span>
		</div>
		
		<div class="formControls">
			<html:submit property="_eventId_submit" styleClass="done default">
				<bean:message key="${pagePrefix}.button.submit" />
			</html:submit>
			<html:submit property="_eventId_cancel" styleClass="cancel">
				<bean:message key="app.action.cancel" />
			</html:submit>
		</div>
	</div>
</html:form>
