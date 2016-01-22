<%--
###################################################################################
toolbar.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah .  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

A common header for pages. Contains a title, logo and useful links. 
This is not a complete HTML page, just a snippet.
###################################################################################
--%>

<%--========================== Tag Library Declarations ==========================--%>

<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ruready.net/common" prefix="common"%>

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

<%-- Refresh page after 60 seconds so that the date & time are updated --%>
<%-- <meta http-equiv="refresh" content="60" /> --%>

<%-- Date & time printout format --%>
<c:set var="formatshortdatetime" scope="page">
	<bean:message key='app.format.shortdatetime' />
</c:set>

<%--============================ JSP body begins here ============================--%>

<%-- List of useful links --%>
<table width="100%" cellpadding="2" border="0">
<tbody>
	<tr>
		<%-- Useful links: on the left-hand-side of the bar. --%>
		<td align="left" valign="middle">

    	  	<html:link module="/content" page="/open/home.do">
	    		<bean:message key="content.root.label" />
	    	</html:link>
	    							
	    	&nbsp;&#9830;&nbsp;

			<%-- Trash link --%>
			<html:link module="/content" page="/open/explore/viewFull.do?itemType=DEFAULT_TRASH">
				<bean:message key="content.trash.label" />
	    	</html:link>
	    	
	    	<%--
	    	&nbsp;&#9830;&nbsp;
	    	
	    	<html:link module="/user" page="/open/FRONT/about.do">
	    		<bean:message key="app.layout.links.about" />
	    	</html:link>
			--%>

	    	&nbsp;&#9830;&nbsp;

			<%-- Search item view --%>
			<html:link module="/content" page="/open/searchItem.do">
	    		<bean:message key="content.searchItem.title" />
	    	</html:link>
	    	
	    	&nbsp;&#9830;&nbsp;

			<%-- Search question view --%>
			<html:link module="/content" page="/open/searchQuestion.do">
				<bean:message key="question.search.title" />
			</html:link>
						
	    	&nbsp;&#9830;&nbsp;
		
			<%-- Transfer item view --%>
			<html:link module="/content" page="/open/transferItem.do">
	    		<bean:message key="content.transferItem.title" />
	    	</html:link>
		</td>		
		
		<%-- Display item search form --%>
		<%--
		<td align="left">
			<!-- TODO: auto on-focus the search text field -->
			<html:form action="/open/searchItem">
				<input type="text" name="name" size="10" height="4" />
				<!-- Stylish submit button -->	
				<c:set var="submitSearchLabel">
					<bean:message key="content.searchItem.title" />
				</c:set>
				<html:submit property="submitSearch" value="${submitSearchLabel}" styleClass="done" />
			</html:form>
		</td>
		--%>
		
		<%-- Trash link: on the right-hand-side of the bar. --%>
		<%--
		<td align="right">
			<!--
			Create two links so that there is no underline between the
			image and the word.
			-->
			<table border="0" class="linksbar">
			<tbody>
				<tr>
					<td>
						<html:link module="/content" page="/open/explore/viewFull.do?itemType=DEFAULT_TRASH">
							<div class="image image_item_${trashType}" />
				    	</html:link>
				    	&nbsp;
			    	</td>
			    	<td>
						<html:link module="/content" page="/open/explore/viewFull.do?itemType=DEFAULT_TRASH">
							<bean:message key="content.trash.label" />
				    	</html:link>
			    	</td>
		    	</tr>
			</tbody>
	    	</table>
	    </td>
	    --%>
	    
		<%-- Display the current date and time --%>
		<td align="right">
			<jsp:useBean id="currentDate" class="java.util.Date" scope="page" />
			<% if (currentDate.toString() == null) {} /* Keep JSP validator happy */ %>
			<bean:write name="currentDate" scope="page"
					format="${formatshortdatetime}" />
		</td>
	</tr>
</tbody>
</table>
