<%--
###################################################################################
demo.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Mathematical expression parser demo page with both text syntax and a WebEQ equation
editor applet that generates inputs for the parser in MathML content format.
###################################################################################
--%>

<%@ page language="java"%>
<%@ page import="net.ruready.web.common.rl.WebAppNames"%>
<%@ page import="net.ruready.parser.port.input.exports.ParserInputFormat"%>
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
	<%=""
					+ WebAppNames.REQUEST.ATTRIBUTE.TOKEN.PARSER_DEMO_RESULT%>
</c:set>

<%-- Javascript script includes --%>
<script type="text/javascript"
	src="<html:rewrite module='' page='/js/common/formUtil.js' />"></script>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Page title and demo instructions
==================================================================
--%>
<%-- <center><h1><bean:message key="parser.demo.title" /></h1></center> --%>
<bean:message key="parser.demo.instructions" />

<bean:message key="parser.demo.syntaxInstructions" />
<html:link module="/parser" page="/open/instructions.do">
	<bean:message key="parser.demo.syntaxInstructionsLink" />
</html:link>
.
<p />

<%--
==================================================================
Top table: submit form + options control
==================================================================
--%>
<html:form action="/open/demo">

<%--
##############################
Action parameters
##############################
Don't use html:hidden so that thess fields because they are not necessarily form bean properties.
Use the Javascript to dynamically add hidden fields.
--%>

	<%-- Display validation errors unless this is a fresh form --%>
	<logic:equal name="demoForm" property="empty" value="false">
		<span class="error">
			<html:errors />
		</span>
	</logic:equal>
	<p />

	<table width="100%" border="0" cellspacing="2">

	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Input type selection
	(text / EE)
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
	<tbody>
		<tr>
			<td colspan="2" id="format">
				<%--
				Display a radio button with input parser types.
				Upon an on-change event, the form is submitted with a request to update
				the input format (see the head file).
		 		--%>
				<logic:iterate name="demoForm" property="formatOptions"
					scope="request" id="format"
					type="net.ruready.web.select.exports.Option" indexId="ctr">
					<html:radio name="demoForm" property="format"
						value="${format.value}" styleId="format_${format.value}">
					${format.label}
					</html:radio>
				</logic:iterate>
			</td>

			<%--
		Vertical space
		--%>
			<td rowspan="3" valign="top" width="1%">
				&nbsp;
			</td>

			<%--
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		Parser Control Options
		%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		--%>
			<td rowspan="3" valign="top">
				<jsp:include page="parserOptions.jsp" flush="true" />
			</td>

		</tr>
	
	<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Parser input data
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
		<tr>
			<%-- Reference string label --%>
			<th align="left" class="section" width="15%">
				<bean:message key="parser.demo.form.referenceString.label" />
			</th>

			<%-- Reference string text area or EE applet --%>
			<td align="left" width="52%">
				<%-- Text area --%>
				<logic:equal name="demoForm" property="format"
					value="${ParserInputFormat_TEXT}">
					<html:textarea property="referenceString" rows="4"
						styleClass="bigtextarea">
						<bean:write name="demoForm" property="referenceString" />
					</html:textarea>
				</logic:equal>

				<%-- WebEQ EE applet --%>
				<logic:equal name="demoForm" property="format"
					value="${ParserInputFormat_EQUATION_EDITOR}">
					<html:hidden property="referenceString" />
					<parser:ee name="demoForm" property="referenceString"
						applet="referenceEE" />
				</logic:equal>
			</td>
		</tr>

		<tr>
			<%-- Response string label --%>
			<th align="left" class="section">
				<bean:message key="parser.demo.form.responseString.label" />
			</th>

			<%-- Response string text area or EE applet --%>
			<td align="left">
				<%-- Text area --%>
				<logic:equal name="demoForm" property="format"
					value="${ParserInputFormat_TEXT}">
					<html:textarea property="responseString" rows="4"
						styleClass="bigtextarea">
						<bean:write name="demoForm" property="responseString" />
					</html:textarea>
				</logic:equal>

				<%-- WebEQ EE applet --%>
				<logic:equal name="demoForm" property="format"
					value="${ParserInputFormat_EQUATION_EDITOR}">
					<html:hidden property="responseString" />
					<parser:ee name="demoForm" property="responseString"
						applet="responseEE" />
				</logic:equal>
			</td>
		</tr>

		<%--
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Submit buttons
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	--%>
		<tr>
			<td colspan="4">
				<%-- Text format: no conversion before submission --%>
				<logic:equal name="demoForm" property="format"
					value="${ParserInputFormat_TEXT}">
					<%-- Analyze button --%>
					<html:submit property="action_analyze" styleClass="done">
						<bean:message key="parser.demo.action.analyze" />
					</html:submit>

					<%-- Form reset button --%>
					<html:submit property="action_setup_reset"
						styleClass="cancel">
						<bean:message key="app.action.reset" />
					</html:submit>
				</logic:equal>

				<%-- EE format: use buttons that convert EE input to MathML content before submission --%>
				<logic:equal name="demoForm" property="format"
					value="${ParserInputFormat_EQUATION_EDITOR}">
					<%-- Analyze button --%>
					<html:button property="action_analyze" styleClass="done">
						<bean:message key="parser.demo.action.analyze" />
					</html:button>

					<%-- Form reset button --%>
					<html:submit property="action_setup_reset"
						styleClass="cancel">
						<bean:message key="app.action.reset" />
					</html:submit>
				</logic:equal>
			</td>
		</tr>
	</tbody>
	</table>
</html:form>

<%--
======================================================================
Results section
======================================================================
--%>
<logic:present scope="request"
	name="${WEBAPPNAMES_REQUEST_ATTRIBUTE_TOKEN_PARSER_DEMO_RESULT}">
	<jsp:include page="demoResult.jsp" flush="true" />
</logic:present>
