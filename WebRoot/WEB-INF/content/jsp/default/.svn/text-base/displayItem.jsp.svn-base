<%--
###################################################################################
displayItem.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Display a generic read-only item. This is not a full HTML page and included in
other edit pages inside an <html:form> tag and inside the proper nesting.
To be included in editItemFull.jsp.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://ruready.net/content"
	prefix="content"%>

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

<%-- Debugging printouts --%>
<%--
##################### item = ${item} ######################<br/>
--%>

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Submit read-only properties as hidden parameters
==================================================================
--%>
<nested:hidden property="comment" />
<nested:hidden property="name" />

<%--
==================================================================
Print Item properties
==================================================================
--%>

<table cellspacing="5">
<tbody>

	<%-- Display a textbox for the item's name --%>
	<tr>
		<td>
			<bean:message key="content.Item.name.label" />:
		</td>
		<td>
			<nested:write property="name" />
		</td>
	</tr>


	<%-- Display a textbox for the link --%>
	<tr>
		<td>
			<bean:message key="content.Item.comment.label" />:
		</td>
		<td>
			<nested:write property="comment" />
		</td>
	</tr>

</tbody>
</table>
