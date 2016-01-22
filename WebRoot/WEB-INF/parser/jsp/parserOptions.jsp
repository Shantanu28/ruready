<%--
###################################################################################
parserOptions.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Prints an <html:form> part that refers to the parser's control options. Must be
inscribed within an <html:form> tag.
###################################################################################
--%>

<%@ page language="java"%>
<%@ page import="net.ruready.web.common.rl.WebAppNames"%>
<%@ page
	import="net.ruready.parser.port.input.exports.ParserInputFormat"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://ruready.net/parser" prefix="parser"%>

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

<c:set var="ParserInputFormat_EQUATION_EDITOR">
	<%="" + ParserInputFormat.EQUATION_EDITOR%>
</c:set>

<c:set var="ParserInputFormat_TEXT">
	<%="" + ParserInputFormat.TEXT%>
</c:set>

<c:set var="WEBAPPNAMES_REQUEST_ATTRIBUTE_TOKEN_PARSER_DEMO_RESULT">
	<%="" + WebAppNames.REQUEST.ATTRIBUTE.TOKEN.PARSER_DEMO_RESULT%>
</c:set>

<%--============================ JSP body begins here ============================--%>

<table width="100%" border="0">
	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Main Options' Section
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
	<%--
	<tr><td>
	<table width="100%" border="0">
	<tbody>
	--%>
	
	<%--
	==================================================================
	Options' Title
	==================================================================
	--%>
	<thead>
	<tr>
		<td colspan="2" align="center" class="options">
			<bean:message key="parser.demo.options.title" />
		</td>
	</tr>
	</thead>
	
	<tbody>
	
	<%--
	==================================================================
	Implicit multiplication flag
	==================================================================
	--%>
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message key="parser.demo.options.implicitMultiplication.label" />
		</td>
		<td align="left">
			<html:checkbox name="demoForm" property="implicitMultiplication" />
		</td>
	</tr>

	<%--
	==================================================================
	Precision of numerical comparison (#digits)
	==================================================================
	--%>
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message key="parser.demo.options.digits.label" />
		</td>
		<td align="left">
			<html:text name="demoForm" property="digits" size="1" />
		</td>
	</tr>

	<%--
	==================================================================
	Arithmetic mode drop-down menu
	==================================================================
	--%>
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message key="parser.demo.options.arithmeticMode.label" />
		</td>
		<td align="left">
			<html:select name="demoForm" property="arithmeticModeStr">
				<html:optionsCollection property="arithmeticModeOptions" />
			</html:select>
		</td>
	</tr>
	
	<%--
	==================================================================
	Analysis type drop-down menu
	==================================================================
	--%>
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message key="parser.demo.options.analysisID.label" />
		</td>
		<td align="left">
			<%--
			<bean:write name="demoForm" property="analysisIDStr" />
			--%>
			<html:select name="demoForm" property="analysisIDStr">
				<html:optionsCollection property="analysisIDOptions" />
			</html:select>			
		</td>
	</tr>
	
	<%--
	</tbody>
	</table>
	</td>
	</tr>
	--%>
	
	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Edit Distance params ' section
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>

	<%--
	<tr><td>
	<table width="100%" border="0">
	<tbody>
	--%>
		
	<%--
	==================================================================
	Edit distance cost function parameter map
	==================================================================
	--%>
	<%-- Title --%>
	<tr>
		<td colspan="2" align="center" class="options">
			<bean:message key="parser.demo.options.costMap.title" />
		</td>
	</tr>

	<!-- Cost of inserting a node or deleting a node -->
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message key="parser.demo.options.costMap.INSERT_DELETE.label" />
		</td>
		<td align="left" class="smalltext">
			<%--
			Prevent editing this field. All other fields are relative to its size.
			Do pass a hidden parameter so that this cost map entry is populated into the
			form as well.
			--%>
			<%-- <html:text name="demoForm" property="costMap(INSERT_DELETE)" size="2" /> --%>
			<bean:write name="demoForm" property="costMap(INSERT_DELETE)" />
			<html:hidden name="demoForm" property="costMap(INSERT_DELETE)" />
		</td>
	</tr>

	<!-- Cost of relabeling a fictitious node into a non-fictitious node -->
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message key="parser.demo.options.costMap.UNEQUAL_FICTITIOUS.label" />
		</td>
		<td align="left">
			<html:text name="demoForm" property="costMap(UNEQUAL_FICTITIOUS)" size="2" />
		</td>
	</tr>

	<!-- Cost of deleting a node -->
	<%--
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message key="parser.demo.options.costMap.DELETE.label" />
		</td>
		<td align="left">
			<html:text name="demoForm" property="costMap(DELETE)" size="2" />
		</td>
	</tr>
	--%>
	
	<!-- Cost of a wrong value -->
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message
				key="parser.demo.options.costMap.UNEQUAL_SAME_TYPE.label" />
		</td>
		<td align="left">
			<html:text name="demoForm" property="costMap(UNEQUAL_SAME_TYPE)"
				size="2" />
		</td>
	</tr>

	<!-- Cost of a wrong operation -->
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message
				key="parser.demo.options.costMap.UNEQUAL_SAME_TYPE_OPERATION.label" />
		</td>
		<td align="left">
			<html:text name="demoForm"
				property="costMap(UNEQUAL_SAME_TYPE_OPERATION)" size="2" />
		</td>
	</tr>

	<!-- Cost of an unrecognized node -->
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message
				key="parser.demo.options.costMap.UNEQUAL_DIFFERENT_TYPE.label" />
		</td>
		<td align="left">
			<html:text name="demoForm"
				property="costMap(UNEQUAL_DIFFERENT_TYPE)" size="2" />
		</td>
	</tr>

	<!-- Cost of an unrecognized operation -->
	<tr>
		<td align="left" valign="middle" class="regtext">
			<bean:message
				key="parser.demo.options.costMap.UNEQUAL_DIFFERENT_TYPE_OPERATION.label" />
		</td>
		<td align="left">
			<br />			
		</td>
	</tr>
	
	</tbody>
	
	<%--
	</tbody>
	</table>
	</td>
	</tr>
	--%>
	
</table>
