<%--
###################################################################################
demoResult.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah.
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Parser demo results section.
###################################################################################
--%>

<%@ page language="java"%>
<jsp:directive.page import="net.ruready.common.rl.CommonNames"/>
<%@ page import="java.util.Date" %>
<%@ page import="net.ruready.common.parser.core.tokens.Token" %>
<%@ page import="net.ruready.parser.math.entity.MathTokenStatus" %>
<%@ page import="net.ruready.parser.rl.ParserNames" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/test" prefix="test"%>

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

<%-- Number of element types in the element count table --%>
<c:set var="numTypes">
	<%="" + MathTokenStatus.numNonNegativeValues()%>
</c:set>

<c:choose>
	<c:when test="${parserDemoEquivalent}">
		<c:set var="answer_type">correct</c:set>
		<c:set var="correctness_score">100.0</c:set>
	</c:when>
	<c:otherwise>
		<%-- Incorrect answer --%>
		<c:set var="answer_type">incorrect</c:set>
		<c:set var="correctness_score">0.0</c:set>
	</c:otherwise>
</c:choose>		

<c:set var="elementTypes">
	<%="" + MathTokenStatus.numNonNegativeValues()%>
</c:set>

<c:set var="PARSERNAMES_OPTIONS_DEFAULT_ERROR_WEIGHT">
	<%= ParserNames.OPTIONS.DEFAULT_ERROR_WEIGHT %>
</c:set>

<%-- Identifiers of element types in maps --%>
<c:set var="ELEMENT_CORRECT">
	<%= "C" %>
</c:set>
<c:set var="ELEMENT_WRONG">
	<%= "W" %>
</c:set>
<c:set var="ELEMENT_MISSING">
	<%= "M" %>
</c:set>
<c:set var="ELEMENT_UNRECOGNIZED">
	<%= "U" %>
</c:set>
<c:set var="ELEMENT_REDUNDANT">
	<%= "R" %>
</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Page title and demo instructions
==================================================================
--%>
<center><h1><bean:message key="parser.demo.result.title" /></h1></center>

<table border="0" cellspacing="5" class="bigtext" width="100%">

<thead>

<%--
==================================================================
Print highlighted string 
==================================================================
--%>
<tr>
	<td class="h2" width="22%"><bean:message key="parser.demo.highlight.title" />:</td>
	<td>${parserDemoHTMLResponseString}</td>
</tr>

<%--
==================================================================
Print global evaluation
==================================================================
--%>
<tr>
	<td class="h2"><bean:message key="parser.demo.evaluation.title" />:</td>
	<td>
		<%-- Print icon, summary and description of the equivalence status (correct/incorrect) --%>
		<div style="icon icon_parser_${answer_type}"></div>
		<span class="answer_${answer_type}"><bean:message key="parser.demo.evaluation.${answer_type}.title" /></span>
		<br />
		<span class="regtext"><bean:message key="parser.demo.evaluation.${answer_type}.description" /></span>
	</td>
</tr>

</thead>

<%--
==================================================================
Element analysis
==================================================================
--%>
<tbody>
<tr>
<td class="h2"><bean:message key="parser.demo.analysis.title" />:</td>

<%-- Element count + score breakdown table --%>
<td>
<table border="1" cellpadding="3" cellspacing="0" width="100%"
class="regtext">

<%-- Table Header (Notation) --%>
<thead>
<tr>
	<th align="center" colspan="5">
		<bean:message key="parser.demo.analysis.elements.label" />
	</th>
	<th align="center" colspan="3">
		<bean:message key="parser.demo.analysis.score.label" />
	</th>
</tr>
	
<tr class="regtext">
	<th align="center" class="mts_correct" width="14%">
		<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.CORRECT.label" />
	</th>
	<th align="center" class="mts_wrong" width="14%">
		<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.WRONG.label" />
	</th>
	<th align="center" class="mts_unrecognized" width="14%">
		<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.UNRECOGNIZED.hyphen" />
	</th>
	<th align="center" class="mts_missing" width="14%">
		<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.MISSING.label" />
	</th>	
	<th class="mts_redundant" width="14%">
		<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.REDUNDANT.hyphen" />
	</th>	
	<th align="center" width="10%">
		<bean:message key="parser.demo.analysis.score.elements.label" />
	</th>
	<th align="center" width="10%">
		<bean:message key="parser.demo.analysis.score.correctness.hyphen" />
	</th>
	<th align="center" width="10%" class="score_fail">
		<bean:message key="parser.demo.analysis.score.total.label" />
	</th>	
</tr>
</thead>

<%-- Data row (element counts and scores) --%>
<tbody>
<tr>
	<%-- Element counts --%>
	<td align="center" class="mts_correct"     >${parserDemoElementMap[ELEMENT_CORRECT]}</td>	
	<td align="center" class="mts_wrong"       >${parserDemoElementMap[ELEMENT_WRONG]}</td>
	<td align="center" class="mts_unrecognized">${parserDemoElementMap[ELEMENT_UNRECOGNIZED]}</td>
	<td align="center" class="mts_missing"     >${parserDemoElementMap[ELEMENT_MISSING]}</td>
	<td align="center" class="mts_redundant"   >${parserDemoElementMap[ELEMENT_REDUNDANT]}</td>

	<%-- Sub-total and total scores --%>
	<c:set var="score_subtotal_correctness">
		<fmt:formatNumber value="${PARSERNAMES_OPTIONS_DEFAULT_ERROR_WEIGHT * parserDemoAnalysis.score}" />
	</c:set>
	<td align="center" class="score_subtotal"  >
		<fmt:formatNumber value="${score_subtotal_correctness}" pattern="###"/>
	</td>

	<c:set var="score_subtotal_elements">
		<fmt:formatNumber value="${(1-PARSERNAMES_OPTIONS_DEFAULT_ERROR_WEIGHT) * correctness_score}" />
	</c:set>
	<td align="center" class="score_subtotal"  >
		<fmt:formatNumber value="${score_subtotal_elements}" pattern="###"/>
	</td>
	
	<%-- Make sure score always looks like a failed score. Beacuse it's a nicer format, that's all. --%>
	<test:tdScore score="${score_subtotal_correctness + score_subtotal_elements}" passScore="101" format="%3.0f" align="center" />
</tr>
</tbody>

</table> <%-- Elements + score table --%>
</td>

</tr>
</tbody>
</table> <%-- Response analysis results --%>

<%--
==================================================================
Print syntax trees
==================================================================
--%>
<h2><bean:message key="parser.demo.tree.title" />:</h2>
<br/>

<table border="0" cellspacing="0" width="100%">
	<thead>
	<tr>
		<td width="45%" align="center" valign="middle">
			<span class="section">
				<bean:message key="parser.demo.form.referenceString.label" />:
			</span>
			${parserDemoHTMLReferenceString}
		</td>
		<td width="45%" align="center" valign="middle">
			<span class="section">
				<bean:message key="parser.demo.form.responseString.label" />:
			</span>
			${parserDemoHTMLResponseString}
		</td>
		<td width="10%" align="center" valign="bottom"
		class="smalltext">
			<u><b><bean:message key="parser.demo.tree.notation.label" /></b></u>
		</td>
	</tr>
	</thead>
	
	<tbody>
	<tr>
		<%-- Compute plot box size --%>
		<td align="center" valign="top">
			<%-- Plot the reference tree --%>
			<%
				// Pass the necessary information for the tree plot action from the request
				// to the session (temporarily; will be removed at the end of the plot action)
				{
					Object data = request.getAttribute("parserDemoTreeImageReference");
					// Not using session directly because MyEclipse complains about null pointer
					request.getSession().setAttribute("parserDemoTreeImageReference", data);
				}
			 %>
			<c:set var="timestamp"><%= new Date().getTime() %></c:set>
			<img src="<html:rewrite module='/parser' page='/open/plotTree.do?name=parserDemoTreeImageReference&ts=${timestamp}' />"
			border="0"
			width="${requestScope.parserDemoTreeImageSizeReference.left}"
			height="${requestScope.parserDemoTreeImageSizeReference.right}"
			alt="<bean:message key='parser.demo.form.referenceString.alt' />"/>
		</td>
		
		<td align="center" valign="top">
			<%
				// Pass the necessary information for the tree plot action from the request
				// to the session (temporarily; will be removed at the end of the plot action)
				{
					Object data = request.getAttribute("parserDemoTreeImageResponse");
					// Not using session directly because MyEclipse complains about null pointer
					request.getSession().setAttribute("parserDemoTreeImageResponse", data);
				}
			 %>
			<c:set var="timestamp"><%= new Date().getTime() %></c:set>
			<img src="<html:rewrite module='/parser' page='/open/plotTree.do?name=parserDemoTreeImageResponse&ts=${timestamp}' />"
			border="0"
			width="${requestScope.parserDemoTreeImageSizeResponse.left}"
			height="${requestScope.parserDemoTreeImageSizeResponse.right}"
			alt="<bean:message key='parser.demo.form.responseString.alt' />"/>
		</td>
	
		<%--
		##########################################
		Table with Notation of tree charts
		##########################################
		--%>
		<td width="20" align="left" valign="top">
		
		<table border="1" cellpadding="5" cellspacing="0" class="smalltext">
		<tbody>
			<tr>
				<td class="mts_correct">
				<%=
					new Token("4")
				%>
				</td>
				<td>
					<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.CORRECT.label" />
				</td>
			</tr>
			
			<tr>
				<td class="mts_wrong">
				<%=
					new Token("x")
				%>
				</td>
				<td>
					<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.WRONG.label" />
				</td>
			</tr>
			
			<tr>
				<td class="mts_unrecognized">
				<%=
					new Token("=")
				%>
				</td>
				<td>
					<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.UNRECOGNIZED.label" />
				</td>
			</tr>
			
			<tr>
				<td class="mts_missing">
				<%=
					new Token("+")
				%>
				</td>
				<td>
					<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.MISSING.label" />
				</td>
			</tr>
			
			<tr>
				<td class="mts_redundant">
				<%=
					CommonNames.TREE.PARENTHESIS
				%>
				</td>
				<td>
					<bean:message key="net.ruready.parser.arithmetic.entity.MathTokenStatus.REDUNDANT.label" />
					<sup>*</sup>
				</td>
			</tr>
			
			<tr>
				<td class="mts_response">
				<%=
					new Token("Res.")
				%>
				</td>
				<td>
					<bean:message key="parser.demo.tree.notation.other.label" />
					<sup>**</sup>
				</td>
			</tr>
		</tbody>
		</table>
		</td>	
	</tr>
	</tbody>
</table>

<%--
==================================================================
Global statistics
==================================================================
--%>

<table border="0" class="bigtext">
<tbody>
	<tr>
		<td valign="top">
			<bean:message key="parser.demo.stats.editDistance.label" />:
		</td>
		<td>
			<bean:write name="parserDemoAnalysis" property="editDistance" format="##.##" />
		</td>
	</tr>
	<tr>
		<td valign="top">
			<bean:message key="parser.demo.stats.time.label" />:
		</td>
		<td>
			${parserDemoAnalysis.elapsedTime} <bean:message key="parser.demo.stats.time.units.label" />
		</td>
	</tr>
</tbody>
</table>

<%--
==================================================================
Footnotes
==================================================================
--%>
<p/>
<table border="0" class="smalltext">
<tbody>
	<tr>
		<td valign="top">
			<sup>*</sup>
		</td>
		<td>
			<bean:message key="parser.demo.tree.notation.REDUNDANT.description" />
		</td>
	</tr>
	<tr>
		<td valign="top">
			<sup>**</sup>
		</td>
		<td>
			<bean:message key="parser.demo.tree.notation.other.description" />
		</td>
	</tr>
</tbody>
</table>
