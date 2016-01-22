<%--
###################################################################################
searchQuestion.filter.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112-9359

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Display result set filter flags.
###################################################################################
--%>

<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<%--============================ JSP body begins here ============================--%>

<%--
############################ 
Javascript script includes
############################ 
--%>

<%--
############################ 
Result set filter info
############################ 
--%>
<table border="0" cellpadding="2">
	<tbody>
		<tr>
			<td>
				<bean:message key="question.resultsPerPage.label" />:
				<html:text property="resultsPerPage" styleId="resultsPerPage" size="3" />
			</td>
			
			<td class="cellnew">
				<html:checkbox property="showNew" />
				<bean:message key="question.state.new" />
			</td>

			<td class="cellactive">
				<html:checkbox property="showActive" />
				<bean:message key="question.state.active" />
			</td>

			<td class="cellupdated">
				<html:checkbox property="showUpdated" />
				<bean:message key="question.state.updated" />
			</td>
													
			<td class="celldisabled">
				<html:checkbox property="showDisabled" />
				<bean:message key="question.state.disabled" />
			</td>

			<td class="celldeleted">
				<html:checkbox property="showDeleted" />
				<bean:message key="question.state.deleted" />
			</td>

			<td>
				<html:checkbox property="parametric" />
				<bean:message key="net.ruready.business.content.question.entity.property.QuestionCountType.PARAMETRIC.label" />
			</td>
														
			<td>
				<html:submit property="action_search" styleClass="done">
					<bean:message key="question.searchQuestion.result.update" />
				</html:submit>
			</td>
		</tr>
	</tbody>
</table>
