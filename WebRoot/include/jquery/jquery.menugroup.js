//==================================================================
// 						-jQuery MenuGroup Plugin-
//
// @author Oren E. Livne <olivne@aoce.utah.edu>
// @date   November 6, 2007
//
// This plugin groups several drop-down menus. It plugins AJAX
// action URLs for menu pre-population and on-change event response.
//
// Arguments: (default values appear in square brackets)
// * actions - an associative array of actions (event listener structs)
//   that are triggered upon a menu's onchange. Action structs can be
//   set for each menu name (serving as a key).
//   Each action struct may contain the following elements:
//   o populate_url - URL to a server action that populates the child
//     menu of this menu upon an on-change event [null].
//   o onchange - a function reference that accepts a single argument
//     (the new selected value) and can be used to trigger other events on
//     the page [null].   
// Options: (default values appear in square brackets)
// * onload_key - key of pre-population action in the actions array ['onload'].
// * name_prefix - prefix of "name" elements of generated <select> tags.
//   If a div tag's id is x, the select's name will be name_prefix + x ['menugroup'].
//==================================================================
// Example of usage:
/*
	// Your server actions might be organized in one object, e.g.
	// a Struts dispatch action class.
	var base_action = "/jquery/populateMenu.do?";
	
	// Pre-population action URL on the server
	var onload_populate_url = base_action + "action=initial&menu=course";

	// Menu on-change event actions
	courseOnChange = function(value)
	{ 
		alert('New course celection: value = ' + value); 
	};
		
	var actions = new Array();
	actions['onload']          = { populate_url: onload_populate_url };
	actions['${subjectMenu}']  = { populate_url: base_action + "?menu=${subjectMenu}" };
	actions['${courseMenu}']   = { populate_url: base_action + "?menu=${courseMenu}"  , onchange: courseOnChange};
	actions['${topicMenu}']    = { populate_url: base_action + "?menu=${topicMenu}"   };
	actions['${subTopicMenu}'] = { }; 
	
	// Activate the menu group
	options = { name_prefix: '${prefix}' };
	$(this).menugroup(actions, options);
*/

jQuery.fn.menugroup = function(actions, options)
{
	//==================================================================
	// menu_group_handler()
	// A handler that populates a drop-down menu from XML data.
	//
	// @param xml - XML output of the action
	// @param actions - list of actions for select on-change events
	// @param options - options struct
	//==================================================================
	function menu_group_handler(xml, actions, options) 
	{
		// alert('menu_group_handler() BEGIN');

		// Loop over select groups in the XML document and prepare a
		// menu (HTML select element) within the corresponding <div> tag
		$(xml).find('select').each(function()
		{
			// Convenient variables	and aliases	
			var div_id = $(this).attr('id');
			var div = '#' + div_id;
			var select_id = div_id + '_select';
			var select = '#'+select_id;
			// alert('populating <div> ' + div);
	
			// Clear this division's default text
			$(div).html('');

			// Create and format a new select element
			var div_class = $(div).attr('class');
	        $('<select></select>')
			.attr('id',select_id)
			.attr('name',options.name_prefix + div_id) //	.addClass(div_class + "_" + div_id)
			.appendTo(div);

			// alert('Created <select> element');
			
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				// Add all attributes found in the XML section corresponding to
				// this div to the select element. E.g., a disabled attribute.
				// I don't know how to loop over attributes of an XML element,
				// so we use nested <attribute> elements instead, with <name>
				// and <value> elements within each. 
				// 
				// In the future this can be replaced by looping over DOM attributes:
				// @see http://www.howtocreate.co.uk/tutorials/javascript/dombasics
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				$(xml).find('#' + div_id + ' > attribute').each(function()
				{
					var name_text = $(this).find('name').text();
					var value_text = $(this).find('value').text();
					$(select).attr(name_text, value_text);
				}); // each(
	
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				// Find options in the XML text and append them to the select menu.
				// Find out which one is selected and save it in the selected_value
				// variable.
				//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				var selected_value;
				$(xml).find('#' + div_id + ' > option').each(function()
				{
					var label_text = $(this).find('label').text();
					var value_text = $(this).find('value').text();
					var selected_text = $(this).find('selected').text();
					var isSelected = $(this).is(":contains(selected)");
					if (isSelected) 
					{
						selected_value = value_text;
					}
					// alert(label_text + " " + value_text + " " + selected_text);
					$('<option></option>')
						.attr('value', value_text)
                 		.html(label_text)
						.appendTo(select);
				}); // each(
				
				// Set the menu selection
				$(select).val(selected_value);
	
				// Trigger child menu population and other event listeners (in the
				// listener function hook) upon changing the selection in the parent
				// menu.
				var populate_url = actions[div_id].populate_url;
				var onchange_listener = actions[div_id].onchange;
				if ((populate_url != null) || (onchange_listener != null)) 
				{
 				 	// alert('added menu selection event: ' + select + ' populate_url='+populate_url);
	    			$(select).change(function ()
					{
		          		var value = $(select + " option:selected").eq(0).attr('value');
						if (populate_url != null)
						{
							populate_menu_group(div_id, actions, options, value);
						}
						if (onchange_listener != null)
						{
							onchange_listener(value);
						}
        			});
				}

			}); // each of find('select')

		//alert('menu_group_handler() END');

	}; // menu_group_handler()

	//==================================================================
	// populate_menu_group()
	// Hook an AJAX action to pre-populate drop-down menu. Its handler
	// will set on-change event actions on created menus.
	//
	// @param key - key into the actions array whose populate_url
	// action is invoked here using AJAX. 
	// @param actions - list of actions for select on-change events
	// @param options - options struct
	// @param value - newly selected value of the last drop-down menu
	// that was changed (if null, does not apply)
	//==================================================================
	function populate_menu_group(key, actions, options, value) 
	{
		var populate_url = actions[key].populate_url;
		var value_query = ((value == null) || (value.indexOf('_') >= 0)) ? '' : ('&value=' + value + '&change=true');
		if (options.debug)
		{
			alert('Invoking populate URL ' + populate_url + value_query);
		}
 		$.ajax({
	    	type: "POST",
			url: populate_url + value_query,
			dataType: "xml",
			success: function(xml) 
			{
				menu_group_handler(xml, actions, options);
			} // success: function
		}); // $.ajax(
	}; // populate_menu_group()


	//==================================================================
	// Main code
	//==================================================================
	//	alert('menugroup()');
	// instead of selecting a static container with $("#rating"), we now 
	// use the jQuery context
	var me = this;
 	
 	// Use this code to set default options and override them with the arguments
 	// passed to this function
   	var settings = jQuery.extend({
   	onload_key: 'onload',
	name_prefix: 'menugroup',
	debug: false   	
   	}, options);
	
	populate_menu_group(settings.onload_key, actions, settings, null);
	
	// if possible, return "this" to not break the chain
	return this;
};
