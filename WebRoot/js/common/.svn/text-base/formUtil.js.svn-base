<!-- Hide script from old browsers
/*
###################################################################################
formUtil.js

Nava L. Livne <i><nlivne@aoce.utah.edu></i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i><olivne@aoce.utah.edu></i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-D
    
Protected by U.S. Provisional Patent U-4003, February 2006

Form field utilities. 
###################################################################################
*/

/*
	Dynamically adds a hidden form element to a form using DOM. 
	
	@param formName - name of inscribing form.
	@param elementName - desired name of hidden field.
	@param elementValue - desired value of hidden field.
*/
function addHiddenElement(formName, elementName, elementValue)
{
    var newInput  = document.createElement('input');
    newInput.setAttribute("Name", elementName);
    newInput.setAttribute("Type", "hidden");
    newInput.setAttribute("Value", elementValue);
    formName.appendChild(newInput);
    return true;
}

/*
	Dynamically sets a form element using DOM. 
	
	@param formName - name of inscribing form.
	@param elementName - name of field.
	@param elementValue - new value of hidden field.
*/
function setElement(formName, elementName, elementValue)
{
    //alert('removeElement(elementName = ' + elementName + ')');
    // Assuming there's only one field in the page with name = elementName!!!
    var element = document.getElementsByName(elementName)[0];
    element.setAttribute("Value", elementValue);
    return true;
}

/*
	Dynamically removes a form element using DOM. 
	
	@param formName - name of inscribing form.
	@param elementName - name of field.
*/
function removeElement(formName, elementName)
{
    //alert('removeElement(elementName = ' + elementName + ')');
    // Assuming there's only one field in the page with name = elementName!!!
    var element = document.getElementsByName(elementName)[0];
    //alert('element = ' + element);
    formName = formName.removeChild(element);
    return true;
}

/*
	Submit to a form and change the action URL to link.
	Useful for submit-links onclick events.
	 
	@param formId - id of inscribing form.
	@param link - desired action URL.
*/
function submitLink(formId, link)
{
	var form = document.getElementById(formId);
	form.action = link;
	form.submit();
	return false;
}
		
// End hiding script from old browsers -->
