<%--
###################################################################################
displayMultipleChoice.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Multiple-choice preview page of a question.
###################################################################################
--%>

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page buffer="none"%>
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

<c:choose>

<%-- Invalid question, display error message --%>
<c:when test="${invalid == true}">
	<span class="error"><bean:message key="question.displayFormat.invalid"/></span>
</c:when>

<%-- Valid question, display question fields --%>
<c:otherwise>
	<c:set var="itemId" scope="request">
		<bean:write name="item" scope="request" property="id" />
	</c:set>
	
	<c:set var="itemType" scope="page">
		<bean:write name="item" property="type" />
	</c:set>

	<table cellspacing="5" cellpadding="5" border="0" id="Master Table">
	<thead>
		<tr>
			<td align="center">
				<h2>Question ID <bean:write name="item" property="id" /></h2>
			</td>
		</tr>
	</thead>

	<tbody>
		<tr>
			<td>
				<bean:write name="item" property="formulation" />
			</td>
		</tr>

		<tr>
			<td>
				<!-- spacer -->
			</td>
		</tr>

		<tr>
			<td>
				<h2>Choice</h2>
			</td>
		</tr>

		<tr>
			<td>
				<table border="1">
				<tbody>
					<tr>
						<td>
							<logic:iterate id="choice" name="item" property="choices" indexId="ctr" scope="request">
								<tr>
									<td width="150">
										<c:if test="${choice.correct eq true}">
											(Correct)			
											<c:set var="correctChoiceNum" value="${ctr + 1}" />
											<c:set var="correctChoice" value="${choice.choiceText}" />
										</c:if>
										<c:if test="${choice.correct eq false}">

										</c:if>
									</td>
									<td width="15" />
										${ctr + 1}
									</td>
									<td width="300">
										<bean:write name="choice" property="choiceText" />
									</td>
								</tr>

							</logic:iterate>

						</td>
					</tr>
				</tbody>
				</table>
			</td>
		</tr>

		<tr>
			<td>
				<!-- spacer -->
			</td>
		</tr>

		<tr>
			<td>
				<h2>Correct Answer</h2>
			</td>
		</tr>

		<tr>
			<td>
				<table border="1">
				<tbody>
					<tr>
						<td width="30">
							${correctChoiceNum}
						</td>
						<td>
							${correctChoice}
						</td>
					</tr>
				</tbody>
				</table>
			</td>
		</tr>
	</tbody>
	</table>
</c:otherwise>

</c:choose>
