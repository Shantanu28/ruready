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
    
(c) 2006-07 Continuing Education , University of Utah.  
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Question editing page.
###################################################################################
--%>

<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="net.ruready.business.content.item.entity.ItemType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://ruready.net/content" prefix="content"%>
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

<util:preventCaching />

<%--
<c:set var="typeChangeAction">
	<html:rewrite module="/content" page="/editQuestion.do?action_setup_updateQuestionType=true" />
</c:set>
--%>

<%--
=================================
AJAX declarations and includes
=================================
--%>

<c:set var="courseMenu"><%=ItemType.COURSE.getCamelCaseName()%>Id</c:set>
<c:set var="topicMenu"><%=ItemType.TOPIC.getCamelCaseName()%>Id</c:set>
<c:set var="subTopicMenu"><%=ItemType.SUB_TOPIC.getCamelCaseName()%>Id</c:set>

<%--============================ JSP body begins here ============================--%>

<%--
############################ 
Javascript script includes
############################ 
--%>

<%-- Handlers of events during editing of a question form --%>
<%@ include file="editQuestionActions.jsp" %>

<%--
==================================================================
Hidden fields
==================================================================
--%>
<nested:hidden property="id" />
<nested:hidden property="newItem" />
<nested:hidden property="newParent" />
<nested:hidden property="parentId" />

<%--
Comply with FindItemFilter parameter name conventions:
If this is an add question session, save the parent ID request
attribute in the form 
--%>
<logic:present name="parentId" scope="request">
	<input type="hidden" name="parentId" value="${requestScope.parentId}" />
</logic:present>

<p>
<b>
<bean:message key="question.editQuestion.noteOriginal"/>
</b>
</p>

<%-- TESTING --%>
<%--
<hr/>
editQuestionFullForm = ${editQuestionFullForm}<br/>
<hr/>
--%>

<c:set var="isCreative" value="${editQuestionFullForm.itemForm.questionTypeAsString eq 'CREATIVE'}" />

<%--
==================================================================
Top table: reference information
==================================================================
--%>
<table border="0" id="Reference Information Table" cellpadding="3">
<tbody>

	<%-- Top Row: section headers --%>
	<tr id="Top Section">
		<td valign="top">
			<h2>
				<bean:message key="question.editQuestion.generalInformation.title" />
			</h2>
		</td>
		<td valign="top">
			<%--
			<h2><bean:message key="question.editQuestion.reference.title" /></h2>
			--%>
			&nbsp;
		</td>
		<td valign="top">
		</td>
	</tr>

	<%-- Bottom Row: reference information fields --%>
	<tr id="Reference Data Fields">

		<%--
		##################################
		General Info Section
		##################################
		--%>
		<td valign="top" id="General Info">
			<table cellspacing="0" border="0" id="General Info">
			<tbody>
				<c:if test="${itemForm.id ne null && itemForm.id ne 0}">
					<tr>
						<td class="h3"> 
							<bean:message key="question.id.label" />
						</td>
						<td>
							<nested:write property="id" />
						</td>
					</tr>
				</c:if>

				<tr>
					<td class="h3">
						<bean:message key="content.Item.name.label" />
					</td>
					<td>
						<nested:text maxlength="150" property="name" size="25" />
						<content:conflict localEntity="${item.name}" storedEntity="${storedItem.name}" />
					</td>
				</tr>


				<tr>
					<td class="h3">
						<bean:message key="content.Item.comment.label" />
					</td>
					<td>
						<nested:text maxlength="150" property="comment" size="25" />
						<content:conflict localEntity="${item.comment}" storedEntity="${storedItem.comment}" />
					</td>
				</tr>

				<%-- Question type field --%>
				<tr>
					<td class="h3">
						<bean:message key="question.typeString.label" />
					</td>
					<td>
						<nested:select property="questionTypeAsString" onchange="handleQuestionTypeOnChange();">
							<nested:optionsCollection property="questionTypeOptions" />
							<%--<nested:optionsCollection property="questionTypes" value="header" label="shortDescription" />--%>
						</nested:select>
						<%-- The following conflict resolution code is broken for a new question --%>
						<%--
							// TODO: move this code to some EditQuestionForm getters
							EditQuestionFullForm form = (EditQuestionFullForm)request.getAttribute("editQuestionFullForm");
							Question item = (Question)request.getAttribute("item");
							pageContext.setAttribute("itemQuestionTypeIndex", EditQuestionUtil.getQuestionForm(form).getQuestionTypeOptions().indexOf(new Option(null, item.getQuestionType().toString())));
							Question storedItem = (Question)request.getAttribute("storedItem");
							if (storedItem != null) {
								pageContext.setAttribute("storedItemQuestionTypeIndex", EditQuestionUtil.getQuestionForm(form).getQuestionTypeOptions().indexOf(new Option(null, storedItem.getQuestionType().toString())));
							}
						 %>
						<%--
						<content:conflict localEntity="${editQuestionFullForm.itemForm.questionTypeOptions[itemQuestionTypeIndex].label}"
						storedEntity="${editQuestionFullForm.itemForm.questionTypeOptions[storedItemQuestionTypeIndex].label}" />
						--%>
					</td>
				</tr>

				<%-- Level of difficulty field --%>
				<tr>
					<td class="h3">
						<bean:message key="question.level.label" />&nbsp;
					</td>
					<td>
						<nested:text property="level" size="1" />
					</td>
				</tr>

			</tbody>
			</table>
		</td>

		<%-- Horizontal separator --%>
		<td>
			&nbsp;&nbsp;
		</td>
		
		<%--
		##################################
		Reference Info Section
		##################################
		--%>
		<td valign="top">
			<%--
			This table demarcates the boundaries of the sub-topic menu group using
			an id attribute.
			--%>
			<table cellspacing="2" border="0" id="item_group">
			<tbody>
			
				<%--
				Course drop-down menu.
				AJAX onchange events subsequently update dependent menus.
				--%>
				<tr>
					<td class="h3">
						<bean:message key="content.COURSE.label" />
					</td>	
					<td>
						<div id="${courseMenu}">
							<bean:message key="app.na.label" />
						</div>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>

				<%--
				@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				Topic drop down menu. To be populated by AJAX. Hence cannot use Struts tags here.
				@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				--%>
				<tr>
					<td class="h3">
						<bean:message key="content.TOPIC.label" />
					</td>
					<td>
						<div id="${topicMenu}">
							<bean:message key="app.na.label" />
						</div>
					</td>

					<%--
					Optional: add a new topic. Visible only when a course is selected.
					--%>
					<td>
						&nbsp;&nbsp;
						<%--<bean:message key="question.editQuestion.addTopic" />--%>
					</td>
					<td>						
						<nested:text property="addTopic" size="15" />
						<c:set var="addTopicLabel">
							<bean:message key='question.editQuestion.action.addTopic'/>
						</c:set>
						<html:submit value="${addTopicLabel}" styleId="addTopic"
						styleClass="move" />
						<span id="addTopicMessage"></span>
					</td>
					
				</tr>

				<%--
				@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				Sub-Topic drop down menu. To be populated by AJAX. Hence cannot use Struts tags here.
				@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				--%>
				<tr>
					<td class="h3">
						<bean:message key="content.SUB_TOPIC.label" />
					</td>
					<td>
						<div id="${subTopicMenu}">
							<bean:message key="app.na.label" />
						</div>
					</td>
				
					<%--
					Optional: add a new sub-topic. Visible only when a topic is selected.
					--%>
					<td>				
						&nbsp;&nbsp;
						<%--<bean:message key="question.editQuestion.addSubTopic" />--%>
					</td>
					<td>						
						<nested:text property="addSubTopic" size="15" />
						<c:set var="addSubTopicLabel">
							<bean:message key='question.editQuestion.action.addSubTopic'/>
						</c:set>
						<html:submit value="${addSubTopicLabel}" styleId="addSubTopic"
						styleClass="move" />
						<span id="addSubTopicMessage"></span>
					</td>

				</tr>

				<%-- 
				@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				Textbook information suppressed because we now rely on 
				completely original content for our questions. 
				@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				--%>
				<%--
				<tr>
					<td class="h3">
						Textbook
					</td>
					<td>
						<nested:text maxlength="150" property="textbook" />
						<content:conflict localEntity="${item}" storedEntity="${storedItem}" property="textbook"/>

					</td>
				</tr>

				<tr>
					<td class="h3">
						Chapter
					</td>
					<td>
						<nested:text maxlength="150" property="chapter" />
						<content:conflict localEntity="${item}" storedEntity="${storedItem}" property="chapter"/>

					</td>
				</tr>

				<tr>
					<td class="h3">
						Page
					</td>
					<td>
						<nested:text maxlength="150" property="questionPage" />
						<content:conflict localEntity="${item}" storedEntity="${storedItem}" property="questionPage"/>

					</td>
				</tr>

				<tr>
					<td class="h3">
						Number
					</td>
					<td>
						<nested:text maxlength="150" property="number" />
						<content:conflict localEntity="${item}" storedEntity="${storedItem}" property="number"/>

					</td>
				</tr>
				--%>
			</tbody>
			</table>
		</td>
	</tr>
</tbody>
</table>

<%--
==================================================================
List of Student Interests
==================================================================
--%>
<p>
<h2><bean:message key="question.interests.label" /></h2>

<bean:size name="editQuestionFullForm" property="itemForm.subject.interestCollection.children" id="numInterests" />
<c:set var="itemsPerRow" value="3" />
<c:set var="tr_open" value="<tr>" />
<c:set var="tr_close" value="</tr>" />

<table cellspacing="2" border="0" width="100%">
<tbody>
	<nested:iterate property="subject.interestCollection.children" indexId="ctr" id="interest">
		<c:if test="${ctr % itemsPerRow == 0}">
			${tr_open}
		</c:if>
		<td>
			<html:multibox property="itemForm.selectedInterests" value="${interest.id}" />
			${interest.name}

			<%-- Interest informational pop-up link --%>
			<font size="-3">
				<a href="javascript:popup('<html:rewrite module='/content'
				page='/open/viewInterest.do?itemId=${interest.id}&itemType=${interest.type}'/>')"
				>?</a>
			</font>
			<%--
			<br />
			<content:conflict localEntity="${item}" storedEntity="${storedItem}" property="interests"/>
			--%>
		</td>
		<c:if test="${ctr % itemsPerRow == (itemsPerRow-1)}">
			${tr_close}
		</c:if>
	</nested:iterate>
</tbody>
</table>
</p>

<%--
==================================================================
Problem Formulation
==================================================================
--%>
<p>
<h2><bean:message key="question.formulation.description" /></h2>
<table border="0" width="100%" cellpadding="2" cellspacing="0">
<tbody>
	<tr>
		<%--
		<td width="2%">
			&nbsp;
		</td>
		--%>
		<td>
			<nested:textarea styleClass="textarea100" rows="5" property="formulation" />
			<content:conflict localEntity="${item.formulation}" storedEntity="${storedItem.formulation}" />
		</td>
	</tr>
</tbody>
</table>
</p>

<%--
==================================================================
Variable and other numerical parameters - sub-section
==================================================================
--%>
<p>
<table border="0" cellspacing="5">
<tbody>
<tr valign="top">
	<td>
	<h2>
		<bean:message key="question.editQuestion.options.title" />
	</h2>
	<table border="0" cellspacing="2">
	<tbody>
		<tr id="Variables list">
			<td class="h3">
				<bean:message key="question.variables.label" />
			</td>
			<td>
				<nested:text maxlength="150" property="variables" />
				<content:conflict localEntity="${item.variables}" storedEntity="${storedItem.variables}" />
	
			</td>
		</tr>
		<tr>
			<td class="h3">
				<bean:message key="question.parameters.label" />
			</td>
			<td>
				<nested:text maxlength="150" property="parameters" />
				<content:conflict localEntity="${item.parameters}" storedEntity="${storedItem.parameters}" />
			</td>
		</tr>
		<tr>
			<td class="h3">
				<bean:message key="question.questionPrecision.label" />
			</td>
			<td>
				<nested:text maxlength="150" property="questionPrecision" />
				<content:conflict localEntity="${item.questionPrecision}" storedEntity="${storedItem.questionPrecision}" />
			</td>
		</tr>
		<%--
		-----OBSOLETE-----
		<tr>
			<td class="h3">
				<bean:message key="question.commutativeEquality.label" />
			</td>
			<td>
				<nested:checkbox property="commutativeEquality" />
				<content:conflict localEntity="${item}" storedEntity="${storedItem}" property="commutativeEquality"/>
	
			</td>
		</tr>
		--%>
	</tbody>
	</table>
	</td>
	
	<%--
	==================================================================
	List of concepts: ExtJS grid widget
	==================================================================
	--%>
	<td>
		<h2>
			<bean:message key="content.CONCEPT.labelplural" />
		</h2>
		<nested:hidden property="conceptData" styleId="conceptData"/>
		<table border="0" width="100%" cellpadding="2" cellspacing="0">
			<tbody>
				<tr>
					<td>
						<div id="conceptGrid" class="taggrid"></div>
					</td>
				</tr>
			</tbody>
		</table>
	</td>

	<%--
	==================================================================
	List of skills: ExtJS grid widget
	==================================================================
	--%>
	<td>
		<h2>
			<bean:message key="content.SKILL.labelplural" />
		</h2>
		<nested:hidden property="skillData" styleId="skillData"/>
		<table border="0" width="100%" cellpadding="2" cellspacing="0">
			<tbody>
				<tr>
					<td>
						<div id="skillGrid" class="taggrid"></div>
					</td>
				</tr>
			</tbody>
		</table>
	</td>

</tr>
</tbody>
</table>
</p>

<%--
==================================================================
List of multiple choices (MC format)
==================================================================
--%>
<p />
<h2><a name="choices"><bean:message key="question.choices.description" /></a></h2>
<table border="0" cellpadding="2">
<tbody>

	<tr id="Multiple Choices">
		<td colspan="4">
			<bean:message key="question.numberOfChoices.label" />
			<html:text maxlength="2" property="itemForm.numberOfChoices" size="2" />
			<c:set var="updateNumberOfChoicesLabel">
				<bean:message key='question.editQuestion.action.updateNumberOfChoices'/>
			</c:set>
			<html:submit value="${updateNumberOfChoicesLabel}"
			styleClass="move" onclick="handleUpdateNumberOfChoices();" />
		</td>
	</tr>

	<tr>
		<td>
			<table width="100%" id="Choice Table" "border="0" width="100%">
			<tbody>
				<nested:iterate property="choiceDTOs" indexId="ctr" scope="request" length="${itemForm.numberOfChoices}">
					<tr id="Choices">
						<td width="30">
							<nested:hidden property="id" />
							<c:out value="${ctr + 1}" />
						</td>

						<td width="20">
							<html:radio property="itemForm.correctChoiceCtr" value="${ctr}" />
						</td>

						<td>
							<nested:text maxlength="150" property="choiceText" size="50" />
							<content:conflict localEntity="${itemForm.choiceDTOs[ctr].choiceText}" storedEntity="${storedItem.choices[ctr].choiceText}" />
						</td>
					</tr>
				</nested:iterate>
			</tbody>
			</table>
		</td>
	</tr>
</tbody>
</table>

<%--
==================================================================
Correct answers (either 1 or 2 depending on the question type)
==================================================================
--%>
<p />
<c:set var="solutionsTitleAcademic" value="Solution" />
<c:set var="solutionsTitleCreative" value="Solutions" />
<h2>${isCreative ? solutionsTitleCreative : solutionsTitleAcademic}</h2>
<table border="0" width="100%" cellpadding="2" cellspacing="0">
<tbody>
	<tr id="Answers">
		<td width="2%">
			&nbsp;
		</td>
		<%-- Loop over solutions --%>
		<nested:iterate id="answerDTOs" property="answerDTOs" indexId="ctr">
			<td width="${isCreative ? 49 : 98}%" class="h3">
				<c:if test="${isCreative}">
					<bean:message key="question.choice.answer.label"/> #${ctr + 1}
					<c:if test="${ctr == 0}">(<bean:message key="net.ruready.business.content.question.entity.property.QuestionType.ACADEMIC.label"/>)</c:if>
					<c:if test="${ctr == 1}">(<bean:message key="net.ruready.business.content.question.entity.property.QuestionType.CREATIVE.label"/>)</c:if>
					<br />
				</c:if>
				<nested:hidden property="id" />
				<nested:textarea rows="5" styleClass="textarea100" property="answerText" />
				<content:conflict localEntity="${item.answers[ctr].answerText}" storedEntity="${storedItem.answers[ctr].answerText}" />
			</td>
		</nested:iterate>
	</tr>
</tbody>
</table>

<%--
==================================================================
Solution stages - big table
==================================================================
--%>
<p />
<h2><bean:message key="question.stages.description" /></h2>
<table width="100%" cellspacing="2" border="0" id="Hint Table" width="100%">
<tbody>
	<tr>
		<td width="2%">
			&nbsp;
		</td>
		<td width="${isCreative ? 24 : 49}%" class="h3">
			<bean:message key="question.choice.hint.label" /> 1
		</td>
		<td width="${isCreative ? 24 : 49}%" class="h3">
			<bean:message key="question.choice.keyword.label" /> 1
		</td>
		<c:if test="${isCreative}">
			<td width="24%" class="h3">
				<bean:message key="question.choice.hint.label" /> 2
			</td>
		</c:if>
		<c:if test="${isCreative}">
			<td width="25%" class="h3">
				<bean:message key="question.choice.keyword.label" /> 2
			</td>
		</c:if>
	</tr>
	<nested:iterate id="hintDTOs" property="hintDTOs" indexId="ctr">
		<tr id="Solution Stages">
			<td>
				${ctr + 1}
			</td>
			<td>
				<nested:hidden property="id" />
				<nested:textarea rows="5" styleClass="textarea100" property="hint1Text" />
				<content:conflict localEntity="${item.hints[ctr].hint1Text}" storedEntity="${storedItem.hints[ctr].hint1Text}" />

			</td>
			<td>
				<nested:textarea rows="5" styleClass="textarea100" property="keyword1Text" />
				<content:conflict localEntity="${item.hints[ctr].keyword1Text}" storedEntity="${storedItem.hints[ctr].keyword1Text}" />
			</td>
			<c:if test="${isCreative}">
				<td>
					<nested:textarea rows="5" styleClass="textarea100" property="hint2Text" />
					<content:conflict localEntity="${item.hints[ctr].hint2Text}" storedEntity="${storedItem.hints[ctr].hint2Text}" />
				</td>
			</c:if>
			<c:if test="${isCreative}">
				<td>
					<nested:textarea rows="5" styleClass="textarea100" property="keyword2Text" />
					<content:conflict localEntity="${item.hints[ctr].keyword2Text}" storedEntity="${storedItem.hints[ctr].keyword2Text}" />
				</td>
			</c:if>
		</tr>
	</nested:iterate>
</tbody>
</table>
