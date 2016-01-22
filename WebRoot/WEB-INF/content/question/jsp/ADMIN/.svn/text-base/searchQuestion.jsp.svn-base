<%--
###################################################################################
searchQuestion.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112-9359

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Displays a question search form and the search results, if available.
###################################################################################
--%>

<%@ page language="java"%>
<%@ page import="net.ruready.web.common.rl.WebAppNames"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

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

<c:set var="WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_QUESTION_RESULT">
	<%= ""+ WebAppNames.REQUEST.ATTRIBUTE.SEARCH_QUESTION_RESULT%>
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
################################################################
Page title
################################################################
--%>

<%--
Debugging: print the current form in a text area for inspection.
--%>
<%-- searchQuestionForm = ${searchQuestionForm}--%>

<%--
################################################################
Search question form
################################################################
--%>
<html:form action="/open/searchQuestion.do" method="post">

	<%-- 
	Adapt display tag library parameter names (that are not JavaBean-compliant because of the 
	dashwith some search question form fields that are persisted in that form's scope (normally the sessions). 

	VARS: ${pageContext.request.parameterNames}
	<br>
	Page: [${param['d-16544-p']}]
	<br>
	Sort Column: [${param['d-16544-s']}]
	<br>
	Order: [${param['d-16544-o']}]
	<br>
		
	Old way of doing things using request parameters:
	<input type="hidden" name="d-16544-p" value="${param['d-16544-p']}">
	<input type="hidden" name="d-16544-s" value="${param['d-16544-s']}">
	<input type="hidden" name="d-16544-o" value="${param['d-16544-o']}">
	--%>

	<input type="hidden" name="d-16544-p"
		value="${searchQuestionForm.displayTagPage}" />
	<input type="hidden" name="d-16544-s"
		value="${searchQuestionForm.displayTagSortColumn}" />
	<input type="hidden" name="d-16544-o"
		value="${searchQuestionForm.displayTagOrder}" />

	<%--
	############################################
	Display search and browse sections
	############################################
	--%>
	<div id="search">
		<div id="form">
			<div class="box">
				<h1>
					<bean:message key="question.searchQuestion.search.title" />
				</h1>
				<jsp:include page="searchQuestion.form.jsp" />
			</div>
		</div>
		<div id="browse">
			<h1>
				<span id="browse_item"></span>&nbsp;<bean:message key="content.QUESTION.labelplural"/>
			</h1>
			<jsp:include page="searchQuestion.browse.jsp" />
		</div>
	</div>
	<div id="filter">
		<jsp:include page="searchQuestion.filter.jsp" />
	</div>
	
	<%--
	############################################
	Display result set if exists
	############################################
	--%>
	<logic:present name="${WEBAPPNAMES_REQUEST_ATTRIBUTE_SEARCH_QUESTION_RESULT}">
		<p />
		<div id="result">
			<h1>
				<bean:message key="app.search.results.title" />
			</h1>
			<jsp:include page="searchQuestion.result.jsp" />
		</div>
	</logic:present>
</html:form>
