<%--
###################################################################################
editQuestionActions.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Handlers of submit buttons on the question editing page. Named as a JSP
file because it requires to JSP tags.
###################################################################################
--%>

<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--============================ Useful definitions ==============================--%>

<%--============================ JSP body begins here ============================--%>

<script type="text/javascript" language="javascript">
<!-- Hide script from old browsers

// TODO: translate all of the functions below to jQuery 

function handleQuestionTypeOnChange()
{
	var form = document.getElementById('editQuestionFullForm');
	form.action="<html:rewrite module='/content' page=''/>" +
		"/open/editItemFull/QUESTION.do?action_setup_updateQuestionType=true"
	form.submit();
}

/*
	Event handler for clicking the "Update" button at the top of the multiple choice section.
	Updates the number of multiple choices. 
*/
function handleUpdateNumberOfChoices()
{
	var form = document.getElementById('editQuestionFullForm');
	form.action="<html:rewrite module='/content' page=''/>" +
	"/open/editItemFull/QUESTION/updateNumberOfChoices.do?action_updateNumberOfChoices=true&validate=true&itemId=${itemId}&itemType=QUESTION";
	form.submit();
}

var newwindow;

function popup(url)
{
	newwindow=window.open(url,'name','height=400,width=400,left=400,top=400,resizable=yes,scrollbars=no,toolbar=no,status=no');
  
	if (window.focus) {newwindow.focus()}
}

// End hiding script from old browsers -->
</script>
