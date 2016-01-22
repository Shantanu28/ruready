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

This is a JSP snippet, not a full page.
Displays a browsing table for the questions in a subject or a course.
###################################################################################
--%>

<%@ page language="java"%>
<%@page import="net.ruready.business.content.rl.ContentNames"%>
<%@page import="net.ruready.business.content.question.entity.property.QuestionCountType"%>
<%@page import="net.ruready.business.content.question.entity.QuestionCount"%>
<%@page import="net.ruready.business.content.item.entity.ItemType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<c:set var="HIGHEST_LEVEL">
	<%= ""+ ContentNames.QUESTION.HIGHEST_LEVEL %>
</c:set>

<% pageContext.setAttribute("COUNT_TYPES", QuestionCountType.values()); %>
<% pageContext.setAttribute("COUNT_TYPE_PARAMETRIC", QuestionCountType.PARAMETRIC); %>
<% pageContext.setAttribute("COUNT_TYPE_TOTAL", QuestionCountType.TOTAL); %>
<% pageContext.setAttribute("LEVEL_TOTAL", QuestionCount.LEVEL_TOTAL); %>

<%-- Save realm parameter if found in search form --%>
<c:set var="ajaxForm" value="${searchQuestionForm.subTopicMenuGroupForm}" />
<c:set var="prefix" value="subTopicMenuGroupForm." />
<c:set var="subjectMenu"><%=ItemType.SUBJECT.getCamelCaseName()%>Id</c:set>
<c:set var="courseMenu"><%=ItemType.COURSE.getCamelCaseName()%>Id</c:set>
<c:set var="topicMenu"><%=ItemType.TOPIC.getCamelCaseName()%>Id</c:set>
<c:set var="subTopicMenu"><%=ItemType.SUB_TOPIC.getCamelCaseName()%>Id</c:set>

<%--
jQuery code:
Find out the dynamically (AJAX) generated values of the realm drop-down
menus, and append them to the base action's URL.
--%>
<script type="text/JavaScript">
	$(function() 
	{
		// A helper method to return the selected value in a select group
		function selected_value(selectGroup)
		{
			var value = $(selectGroup + ' option:selected').val();
			return (value == null) ? '' : value; 
		}
		
		// Is a string numeric or not.
		function isNumeric(sText)
		{
			if (sText.length == 0) {
				return false;
			}
		   	var ValidChars = "0123456789.";
		   	var IsNumber=true;
		   	var Char;
		   	for (i = 0; i < sText.length && IsNumber == true; i++) 
		    { 
		      Char = sText.charAt(i); 
		      if (ValidChars.indexOf(Char) == -1) 
		         {
		         	IsNumber = false;
		         }
		      }
	   		  return IsNumber;
		 }

		//==================================================================
		// Add realm parameter to all browse table links upon clicking them 
		//==================================================================
		$("#browse_table a").click(function()
		{
			// Get the current URL of the browse link
			var url = $(this).attr('href');
			
			// Determine which realmQueryString parameter to append
			var subTopicMenuValue 	= selected_value("#subTopicId > select");
			var topicMenuValue 		= selected_value("#topicId > select");
			var courseMenuValue 	= selected_value("#courseId > select");
			var subjectMenuValue 	= selected_value("#subjectId > select");

			var realmQueryString;
			if (isNumeric(subTopicMenuValue)) 
			{
				realmQueryString = '${subTopicMenu}=' + subTopicMenuValue;
			} 
			else if (isNumeric(topicMenuValue)) 
			{
				realmQueryString = '${topicMenu}=' + topicMenuValue;
			}
			else if (isNumeric(courseMenuValue)) 
			{
				realmQueryString = '${courseMenu}=' + courseMenuValue;
			}
			else if (isNumeric(subjectMenuValue)) 
			{
				realmQueryString = '${subjectMenu}=' + subjectMenuValue;
			}
			
			// Append parameter to the link's URL
			var newUrl = url + '&${prefix}' + realmQueryString;
	        $(this).attr('href', newUrl);
			
			// Add filter parameters to link
			var filterString = '';
         	$('#searchQuestionForm').find("input[@type=checkbox]:checked").each(function()
         	{
         		filterString = filterString + '&' + $(this).attr('name') + '=on';
         	});
			var url = $(this).attr('href');
	        $(this).attr('href', url + filterString);
			
			// Proceed with clicking as usual
			return true;
    	});

	}); // document.ready()
</script>

<%-- Base browse action URL --%>
<c:set var="browseUrl" value="/open/searchQuestion.do?action_browse=true&itemType=QUESTION${realmQueryString}" />

<%--
=================================
AJAX declarations and includes
=================================
--%>

<%--============================ JSP body begins here ============================--%>
		
<table cellspacing="0" id="browse_table">
	<%--
	############################################
	Headers
	############################################
	--%>
	<thead>
		<tr>
			<th width="40%"><bean:message key="question.level.label" /></th>
			<c:forEach items="${COUNT_TYPES}" var="type">
				<th>
					<bean:message key="net.ruready.business.content.question.entity.property.QuestionCountType.${type}.label"/>
				</th>
			</c:forEach>
		</tr>
	</thead>
	
	<%--
	############################################
	Question counts per type + level
	############################################
	--%>
	<tbody>
		<c:forEach var="level" begin="1" end="${HIGHEST_LEVEL}">
			<tr>
				<td class="left_column">
					<html:link module="/content" page="${browseUrl}&level=${level}">
						${level} - <bean:message key="question.level${level}.label"/>
					</html:link>
				</td>
				<c:forEach items="${COUNT_TYPES}" var="type">
					<td>
						<html:link module="/content" page="${browseUrl}&type=${type}&level=${level}">
							<span id="browse_${type}_${level}"></span>
						</html:link>
					</td>
				</c:forEach>
   			</tr>
	 	</c:forEach>
	</tbody>
	
	<%--
	############################################
	Grand-totals and statistics
	############################################
	--%>
	<tfoot>	
		<tr>
			<td class="left_column">
				<bean:message key="net.ruready.business.content.question.entity.property.QuestionCountType.${COUNT_TYPE_TOTAL}.label"/>
				<span id="browse_mean_std"></span>
			</td>
			<c:forEach items="${COUNT_TYPES}" var="type">
				<c:choose>
					<c:when test="${type == COUNT_TYPE_TOTAL}">
						<c:set var="styleClass" value="total_total"/>
						<c:set var="typeQueryString" value=""/>
					</c:when>
					<c:when test="${type == COUNT_TYPE_PARAMETRIC}">
						<c:set var="styleClass" value="total_parametric"/>
						<c:set var="typeQueryString" value="&parametric=true"/>
					</c:when>
					<c:otherwise>
						<c:set var="styleClass" value="total"/>
						<c:set var="typeQueryString" value="&type=${type}"/>
					</c:otherwise>
				</c:choose>
				<td class="${styleClass}">
					<html:link module="/content" page="${browseUrl}${typeQueryString}">
						<span id="browse_${type}_${LEVEL_TOTAL}"></span>
					</html:link>
				</td>
			</c:forEach>
		</tr>
	</tfoot>
</table>
