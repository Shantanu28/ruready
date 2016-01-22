<%--
###################################################################################
printFormProps.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
(c) 2006-07 Continuing Education , University of Utah. 
All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV

Used for debugging purposes. Print display tag search question form properties. 
###################################################################################
--%>

<style type="text/css">

div#props div {
	border: 0px solid black;
	padding: 2px 2px 2px 2px;
}

div#props div#props_displayTag {
	position: absolute;
	left: 0;
	width: 200px;
}

div#props div#props_menus {
	position: absolute;
	width: 200px;
	left: 220px;
}

div#props table {
	font-weight: bold;
	border-collapse: collapse;
	border: 1px solid black;
	margin: auto;
}

div#props td {
	background-color: yellow;
	color: black;
	border: 1px solid black;
	padding: 2px 2px 2px 2px;
}
</style>

<div id="props">
	<div id="props_displayTag">
		<center>
			Display Tag Props
		</center>
		<table>
			<tr>
				<td>
					displayTagPage
				</td>
				<td>
					${searchQuestionForm.displayTagPage}
				</td>
			</tr>
			<tr>
				<td>
					displayTagSortColumn
				</td>
				<td>
					${searchQuestionForm.displayTagSortColumn}
				</td>
			</tr>
			<tr>
				<td>
					displayTagOrder
				</td>
				<td>
					${searchQuestionForm.displayTagOrder}
				</td>
			</tr>
		</table>
	</div>
	
	<div id="props_menus">
		<center>
			Menu Props
		</center>
		<table>
			<tr>
				<td>
					subjectId
				</td>
				<td>
					${searchQuestionForm.subTopicMenuGroupForm.subjectId}
				</td>
			</tr>
			<tr>
				<td>
					courseId
				</td>
				<td>
					${searchQuestionForm.subTopicMenuGroupForm.courseId}
				</td>
			</tr>
			<tr>
				<td>
					topicId
				</td>
				<td>
					${searchQuestionForm.subTopicMenuGroupForm.topicId}
				</td>
			</tr>
			<tr>
				<td>
					subTopicId
				</td>
				<td>
					${searchQuestionForm.subTopicMenuGroupForm.subTopicId}
				</td>
			</tr>
		</table>
	</div>
</div>
