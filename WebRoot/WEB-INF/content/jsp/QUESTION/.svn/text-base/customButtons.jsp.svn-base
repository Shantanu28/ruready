<%--
###################################################################################
customButtons.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Custom submit buttons to appear on the question editing page. These are preview
actions (open-ended preview, multiple-choice preview).
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<script language="javascript" type="text/javascript">
<!--

function handleDisplayOperation(method)
{
	//alert("handleDisplayOperation() - start");
	
	url = "<html:rewrite module='/content' page=''/>" 
	+ "/open/editItemFull/QUESTION.do?" 
	+ method + "=true"
	+ "&itemType=QUESTION"
	+ "&customForward=search"
	+ "&itemId=" + "${itemId}";

	// alert("handleDisplayOperation - url: " + url);

	newwindow=window.open(url,'name','height=800,width=800,left=200,top=200,resizable=yes,scrollbars=no,toolbar=no,status=no');
  
	if (window.focus) {newwindow.focus()}
}

-->
</script>

<c:if test="${itemId ne null}">
&nbsp;
&nbsp;
&nbsp;
<html:submit value="Display Multiple Choice" styleClass="move"
		onclick="javascript:handleDisplayOperation('action_setup_displayMultipleChoice'); return false;" />
&nbsp;
<html:submit value="Display Open Ended" styleClass="move"
		onclick="javascript:handleDisplayOperation('action_setup_displayOpenEnded'); return false;" />
</c:if>
