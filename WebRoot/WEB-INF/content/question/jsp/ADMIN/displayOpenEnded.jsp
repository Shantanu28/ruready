<%--
###################################################################################
displayOpenEnded.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Open-ended preview page of a question.
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
				<h2>Hints</h2>
			</td>
		</tr>

		<tr>
			<td>
				<table border="1">
				<thead>
					<tr>
						<td>
						</td>
						<td>
							<h3>Hint 1</h3>
						</td>
						<td>
							<h3>Hint 2</h3>
						</td>
						<td>
							<h3>Keyword 1</h3>
						</td>
						<td>
							<h3>Keyword 2</h3>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<logic:iterate id="hint" name="item" property="hints" indexId="ctr" scope="request">
								<tr>
									<td width="15" />
										${ctr + 1}
									</td>
									<td width="300">
										<bean:write name="hint" property="hint1Text" />
									</td>
									<td width="300">
										<bean:write name="hint" property="hint2Text" />
									</td>
									<td width="300">
										<bean:write name="hint" property="keyword1Text" />
									</td>
									<td width="300">
										<bean:write name="hint" property="keyword2Text" />
									</td>
								</tr>
							</logic:iterate>

						</td>
					</tr>
				</tbody>
				</table>
			</td>
		</tr>
	<tbody>
</table>
</c:otherwise>

</c:choose>
