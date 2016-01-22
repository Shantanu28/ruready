<%--
###################################################################################
instructions.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Parser syntax instructions.
###################################################################################
--%>

<%@ page language="java"%>
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

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Instructions index
==================================================================
--%>

<a name="Index"></a>

<center><h1>
	<bean:message key="parser.instructions.quickstart.title" />
</h1></center>
<center>
	<span class="important">
		<bean:message key="parser.instructions.quickstart.print" />
	</span>
</center>
<p />

<%--
==================================================================
Quick start table
==================================================================
--%>
<bean:message key="parser.instructions.quickstart.text" />
<p/>
<table border="1" cellpadding="5" class="regtext" align="center">
<thead>
	<tr>
		<th class="options">
			<bean:message key="parser.instructions.quickstart.paper.title" />
		</th>
		<th class="options">
			<bean:message key="parser.instructions.quickstart.syntax.title" />
		</th>
		<th class="options">
			<bean:message key="parser.instructions.quickstart.tips.title" />
		</th>
	</tr>
</thead>

<tbody>
	<%-- Example 1 --%>
	<tr>
		<td>
			<html:img module="" page="parser.instructions.example1.paper"
				altKey="app.image.notFound" align="absmiddle" />
		</td>
		<td>
			<code><bean:message key="parser.instructions.example1.syntax" /></code>
		</td>

		<td>
			<bean:message key="parser.instructions.example1.tips" />
		</td>
	</tr>

	<%-- Example 2 --%>
	<tr>
		<td>
			<html:img module="" pageKey="parser.instructions.example2.paper"
				altKey="app.image.notFound" align="absmiddle" />
		</td>
		<td>
			<code><bean:message key="parser.instructions.example2.syntax" /></code>
		</td>

		<td>
			<bean:message key="parser.instructions.example2.tips" />
		</td>
	</tr>

	<%-- Example 3 --%>
	<tr>
		<td>
			<html:img module="" pageKey="parser.instructions.example3.paper"
				altKey="app.image.notFound" align="absmiddle" />
		</td>
		<td>
			<code><bean:message key="parser.instructions.example3.syntax" /></code>
		</td>

		<td>
			<bean:message key="parser.instructions.example3.tips" />
		</td>
	</tr>

	<%-- Example 4 --%>
	<tr>
		<td>
			<html:img module="" pageKey="parser.instructions.example4.paper"
				altKey="app.image.notFound" align="absmiddle" />
		</td>
		<td>
			<code><bean:message key="parser.instructions.example4.syntax" /></code>
		</td>

		<td>
			<bean:message key="parser.instructions.example4.tips" />
		</td>
	</tr>

	<%-- Example 5 --%>
	<tr>
		<td>
			<html:img module="" pageKey="parser.instructions.example5.paper"
				altKey="app.image.notFound" align="absmiddle" />
		</td>
		<td>
			<code><bean:message key="parser.instructions.example5.syntax" /></code>
		</td>

		<td>
			<bean:message key="parser.instructions.example5.tips" />
		</td>
	</tr>

	<%-- Example 6 --%>
	<tr>
		<td>
			<html:img module="" pageKey="parser.instructions.example6.paper"
				altKey="app.image.notFound" align="absmiddle" />
		</td>
		<td>
			<code><bean:message key="parser.instructions.example6.syntax" /></code>
		</td>

		<td>
			<bean:message key="parser.instructions.example6.tips" />
		</td>
	</tr>

	<%-- Example 7 --%>
	<tr>
		<td>
			<html:img module="" pageKey="parser.instructions.example7.paper"
				altKey="app.image.notFound" align="absmiddle" />
		</td>
		<td>
			<code><bean:message key="parser.instructions.example7.syntax" /></code>
		</td>

		<td>
			<bean:message key="parser.instructions.example7.tips" />
		</td>
	</tr>
</tbody>
</table>

<%--
==================================================================
Detailed instructions - index
==================================================================
--%>
<center>
	<h1>
		<bean:message key="parser.instructions.index.title" />
	</h1>
</center>

<ul style="margin-left:450">
	<li>
		<a href="#ArithmeticExpression"><bean:message key="parser.instructions.arithmetic.title" /></a>
	</li>
	<li>
		<a href="#Relations"><bean:message key="parser.instructions.logical.title" /></a>
	</li>
	<li>
		<a href="#MultipleRelations"><bean:message key="parser.instructions.multiple.title" /></a>
	</li>
	<li>
		<a href="#HowParserWorks"><bean:message key="parser.instructions.howParserWorks.title" /></a>
	</li>
	<li>
		<a href="#ParserDemoControl"><bean:message key="parser.instructions.demoControl.title" /></a>
	</li>
	<li>
		<a href="#Constants"><bean:message key="parser.instructions.constants.title" /></a>
	</li>
	<li>
		<a href="#Functions"><bean:message key="parser.instructions.functions.title" /></a>
	</li>
</ul>

<%--
#######################################
Arithmetic expressions section
#######################################
--%>

<%-- TODO: encapsulate table with a hand icon and title in a custom tag --%>
<table width="100%" border="0">
<tbody>
	<tr>
		<td align="left">
			<a name="ArithmeticExpression"></a>
			<h2>
				<bean:message key="parser.instructions.arithmetic.title" />
			</h2>
		</td>
		<td align="right" width="20%">
			<a href="#top" class="med">
				<bean:message key="parser.instructions.index.backToIndex"/><html:img module="" pageKey="app.index.upImage"
				altKey="app.image.notFound" width="50" height="50" border="0" /></a>
		</td>
	</tr>
</tbody>
</table>

<bean:message key="parser.instructions.arithmetic.text" />

<%--
#######################################
Logical expressions section
#######################################
--%>

<table width="100%" border="0">
<tbody>
	<tr>
		<td align="left">
			<a name="Relations"></a>
			<h2>
				<bean:message key="parser.instructions.logical.title" />
			</h2>
		</td>
		<td align="right" width="20%">
			<a href="#top" class="med"><bean:message key="parser.instructions.index.backToIndex"/><html:img module="" pageKey="app.index.upImage"
				altKey="app.image.notFound" width="50" height="50" border="0" /></a>
		</td>
	</tr>
</tbody>
</table>

<p>
	<bean:message key="parser.instructions.logical.text0" />
</p>

<h3>
	<bean:message key="parser.instructions.logical.section1" />
</h3>
<p>
	<bean:message key="parser.instructions.logical.text1" />
</p>

<h3>
	<bean:message key="parser.instructions.logical.section2" />
</h3>
<p>
	<bean:message key="parser.instructions.logical.text2.paragraph1" />
</p>
<p>
	<bean:message key="parser.instructions.logical.text2.paragraph2" />
</p>

<%--
#######################################
Multiple statement section
#######################################
--%>

<table width="100%" border="0">
<tbody>
	<tr>
		<td align="left">
			<a name="MultipleRelations"></a>
			<h2>
				<bean:message key="parser.instructions.multiple.title" />
			</h2>
		</td>
		<td align="right" width="20%">
			<a href="#top" class="med"><bean:message key="parser.instructions.index.backToIndex"/><html:img module="" pageKey="app.index.upImage"
				altKey="app.image.notFound" width="50" height="50" border="0" /></a>
		</td>
	</tr>
</tbody>
</table>

<bean:message key="parser.instructions.multiple.text" />

<%--
#######################################
Parser explanation section
#######################################
--%>

<table width="100%" border="0">
<tbody>
	<tr>
		<td align="left">
			<a name="HowParserWorks"></a>
			<h2>
				<bean:message key="parser.instructions.howParserWorks.title" />
			</h2>
		</td>
		<td align="right" width="20%">
			<a href="#top" class="med"><bean:message key="parser.instructions.index.backToIndex"/><html:img module="" pageKey="app.index.upImage"
				altKey="app.image.notFound" width="50" height="50" border="0" /></a>
		</td>
	</tr>
</tbody>
</table>

<p>
	<bean:message key="parser.instructions.howParserWorks.text.paragraph1" />
</p>

<p>
	<bean:message key="parser.instructions.howParserWorks.text.paragraph2" />
</p>

<p>
	<bean:message key="parser.instructions.howParserWorks.text.paragraph3" />
</p>

<%--
#######################################
Parser demo flags section
#######################################
--%>

<table width="100%" border="0">
<tbody>
	<tr>
		<td align="left">
			<a name="ParserDemoControl"></a>
			<h2>
				<bean:message key="parser.instructions.demoControl.title" />
			</h2>
		</td>
		<td align="right" width="20%">
			<a href="#top" class="med"><bean:message key="parser.instructions.index.backToIndex"/><html:img module="" pageKey="app.index.upImage"
				altKey="app.image.notFound" width="50" height="50" border="0" /></a>
		</td>
	</tr>
</tbody>
</table>

<p>
	<bean:message key="parser.instructions.demoControl.text1" />
</p>
<p>
	<bean:message key="parser.instructions.demoControl.text2" />
</p>
<ul>
	<li>
		<i><bean:message key="parser.instructions.demoControl.implicitMultiplication.title" />:</i>
		<bean:message key="parser.instructions.demoControl.implicitMultiplication.text" />
	</li>

	<%--
	<li>
		<i><bean:message key="parser.instructions.demoControl.commutativeEquality.title" />:</i>
		<bean:message key="parser.instructions.demoControl.commutativeEquality.text" />
	</li>
	--%>
	
	<li>
		<i><bean:message key="parser.instructions.demoControl.precision.title" />:</i>
		<bean:message key="parser.instructions.demoControl.precision.text" />
	</li>

	<li>
		<i><bean:message key="parser.instructions.demoControl.arithmeticMode.title" />:</i>
		<bean:message key="parser.instructions.demoControl.arithmeticMode.text" />
	</li>

	<li>
		<i><bean:message key="parser.instructions.demoControl.vars.title" />:</i>
		<bean:message key="parser.instructions.demoControl.vars.text" />
	</li>
</ul>

<%--
#######################################
Table of mathematical constants
#######################################
--%>

<table width="100%" border="0">
<tbody>
	<tr>
		<td align="left">
			<a name="Constants"></a>
			<h2>
				<bean:message key="parser.instructions.constants.title" />
			</h2>
		</td>
		<td align="right" width="20%">
			<a href="#top" class="med"><bean:message key="parser.instructions.index.backToIndex"/><html:img module="" pageKey="app.index.upImage"
				altKey="app.image.notFound" width="50" height="50" border="0" /></a>
		</td>
	</tr>
</tbody>
</table>

<%
	String[] constants = {"e", "pi"};
	request.setAttribute("constants", constants);
%>
<table summary="" border="1" cellpadding="5" cellspacing="0"
	width="100%" class="regtext">
<tbody>
	<logic:iterate id="constant" indexId="ctr" name="constants"
	scope="request" type="java.lang.String">
		<tr class="TableRowColor" bgcolor="white">
			<td align="right" valign="top" width="1%">
				<code>
					<bean:message key="parser.instructions.constants.${constant}.symbol" />
				</code>
			</td>
			<td>
				<bean:message key="parser.instructions.constants.${constant}.text" />
			</td>
		</tr>
	</logic:iterate>
</tbody>
</table>

<%--
#######################################
Table of mathematical functions
#######################################
--%>

<table width="100%" border="0">
<tbody>
	<tr>
		<td align="left">
			<a name="Functions"></a>
			<h2>
				<bean:message key="parser.instructions.functions.title" />
			</h2>
		</td>
		<td align="right" width="20%">
			<a href="#top" class="med"><bean:message key="parser.instructions.index.backToIndex"/><html:img module="" pageKey="app.index.upImage"
				altKey="app.image.notFound" width="50" height="50" border="0" /></a>
		</td>
	</tr>
</tbody>
</table>

<%------- Unary Functions -------%>
<p />
<h3>
	<bean:message key="parser.instructions.functions.unary.title" />
</h3>

<%
	String[] unaryOps = {"abs", "acos", "acosh", "asin", "asinh", "atan", "atanh",
	"cbrt", "ceil", "cos", "cosh", "cot", "csc", "exp", "fact", "floor", "ln",
	"log10", "round", "sgn", "sec", "sin", "sinh", "sqrt", "tan", "tanh"};	
	request.setAttribute("unaryOps", unaryOps);
%>
<table summary="" border="1" cellpadding="5" cellspacing="0"
	width="100%" class="regtext">
<tbody>
	<logic:iterate id="unaryOp" indexId="ctr" name="unaryOps"
	scope="request" type="java.lang.String">
		<tr class="TableRowColor" bgcolor="white">
			<td align="right" valign="top" width="1%">
				<code>
					<bean:message key="parser.instructions.functions.unary.${unaryOp}.symbol" />
				</code>
			</td>
			<td>
				<bean:message key="parser.instructions.functions.unary.${unaryOp}.text" />
			</td>
		</tr>
	</logic:iterate>
</tbody>
</table>

<%------- Binary Functions -------%>
<p />
<h3>
	Functions of Two Variables
</h3>

<a name="functions2"></a>

<%
	String[] binaryFuncs = {"log", "mod", "root"};	
	request.setAttribute("binaryFuncs", binaryFuncs);
%>
<table summary="" border="1" cellpadding="5" cellspacing="0"
	width="100%" class="regtext">
<tbody>
	<logic:iterate id="binaryFunc" indexId="ctr" name="binaryFuncs"
	scope="request" type="java.lang.String">
		<tr class="TableRowColor" bgcolor="white">
			<td align="right" valign="top" width="1%">
				<code>
					<bean:message key="parser.instructions.functions.binary.${binaryFunc}.symbol" />
				</code>
			</td>
			<td>
				<bean:message key="parser.instructions.functions.binary.${binaryFunc}.text" />
			</td>
		</tr>
	</logic:iterate>
</tbody>
</table>
