<%--
###################################################################################
editItem.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education, University of Utah.
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Country-type Item custom editing form. This is not a full HTML page and included in
other edit pages inside an <html:form> tag and a <nested:nest> whose scoping object
is an EditItemForm.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
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

<%--============================ JSP body begins here ============================--%>

<%--
==================================================================
Item properties
==================================================================
--%>

<table cellspacing="5">
<tbody>

	<%-- Display a textbox for the item's name --%>
	<tr valign="top">
		<td>
			<bean:message key="content.Item.name.label" />
		</td>
		<td>
			<nested:text property="name" size="25" />
			<content:conflict localEntity="${item.name}" storedEntity="${storedItem.name}" />
		</td>
	</tr>

	<%-- Display a textbox for the item's comment --%>
	<tr valign="top">
		<td>
			<bean:message key="content.Item.comment.label" />
		</td>
		<td>
			<nested:text property="comment" size="60" />
			<content:conflict localEntity="${item.comment}" storedEntity="${storedItem.comment}" />
		</td>
	</tr>

	<%-- Display a textbox for the city's county --%>
	<tr valign="top">
		<td>
			<bean:message key="content.CITY.county.label" />
		</td>
		<td>
			<nested:text property="county" size="20" />
			<content:conflict localEntity="${item.county}" storedEntity="${storedItem.county}" />
		</td>
	</tr>

</tbody>
</table>
