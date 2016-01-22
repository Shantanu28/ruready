//################################################################
// An ExtJS editable grid used to tag CMS items with TagItems.
//
// Required parameters for remote data source action:
// (assuming actionUrl = actionStr + '?' + theParametersBelow)
// itemId - parent item's identifier
// tagType - ItemType of the tags
// query - for auto-complete, a prefix of tag names to match 
// Action name conventions:
// [unspecified] - pre-population
// action_complete - auto-complete of tag names
//################################################################

function TagGridUI(options)
{
	//==============================
	// Global variables
	//==============================
	var ds; 						// Main data store that holds our data
	var dsName;						// Data store for name auto-completion
	var grid; 						// Grid component
	var columnModel; 				// Column definitions and rendering
	
	//==============================
	// Utility functions
	//==============================
	/**
	 * Encode the data in a data store in JSON format.
	 * @param ds name of data store
	 * @return json string with the records in the table. If the store is empty,
	 * returns an empty string
	 */
	function encode_grid_data(ds)
	{
		var num_records = ds.getCount();
		if (num_records === 0) {
			return "";
		}
		var json_data = "[";
		for (i = 0; i < num_records; i=i+1) {
			record = ds.getAt(i);
			json_data += Ext.util.JSON.encode(record.data) + ",";
		}
		json_data = json_data.substring(0,json_data.length-1) + "]";
		return json_data;
	}
	
	/**
	 * Set a form element's value attribute to the grid data, encoded
	 * in JSON format.
	 * @param ds name of data store
	 * @param element id of target form element
	 */
	function save_grid_data_in_element(ds, element)
	{
		var json_data = encode_grid_data(ds);
		var element = Ext.get(element);
		element.set({value:json_data});
	}
	
	//==============================
	// Data sources (stores)
	//==============================
	function setupDataSource() {
		// On-load data source
		ds = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: options.url}),
			reader:  new Ext.data.JsonReader({
				root: 'data',
				id: 'id'
				}, 
				[
					{name: 'id', type: 'string'}, // Note: it's not a long or integer -- a string is more flexible
					{name: 'name'}
				])
			}
		);
		
		// Combo-box on-change event data source
		dsName = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: options.url + '&action_complete=true'}),
			reader:  new Ext.data.JsonReader(
				{root: 'data', id: 'id'},
				[
					{name: 'id', type: 'string'}, // Note: it's not a long or integer -- a string is more flexible
					{name: 'name'}
				])
			}
		);		
		
		ds.load();
		dsName.load(); // Note: will cause -all- tags to be loaded from db unless auto-complete action knows to be disabled for empty queries
	}

	
	//==============================
	// Column definitions
	//==============================
	function getColumnModel() {
		if(!columnModel) {
			columnModel = new Ext.grid.ColumnModel(
				[
					//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					// ID column: read-only
					//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					{
				        id: 'id',
						dataIndex: 'id',
						header: 'ID',
						width: 50,
						sortable: true
					},

					//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					// Name column
					// Drop-down menu tied to a remote data source
					//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					{
				        id: 'name',
						dataIndex: 'name',
						header: 'Name',
						width: 230,
						sortable: true,
						editor: new Ext.grid.GridEditor(
							new Ext.form.ComboBox({
								loadingText: options.messages.loadingText,
								typeAhead:true,
								minChars:2,
								triggerAction:'all',
								lazyRender:true,
								store: dsName,
								displayField: 'name',
								valueField: 'id'
							})
						),
						renderer:
							function(data) {
								record = dsName.getById(data);
								if(record) {
									return record.data.name;
								} else {
									return data;
								}
							}
						// TODO: add on-change handler
					}
				]
			);
		}
		return columnModel;
	}
	
	//==============================
	// Grid component UI definitions
	//==============================
	function buildGrid() {	
		/* Ext 2.0 RC1 (beta) code */
		/*
		grid = new Ext.grid.EditorGrid(
			options.grid_element,
			{
				ds: ds,
				cm: getColumnModel(),
				autoSizeColumns: true,
				selModel: new Ext.grid.RowSelectionModel({singleSelect:true})
			}
		);
		*/
		
		/* Ext 2.0 code */
		grid = new Ext.grid.EditorGridPanel({
			store: ds,
			cm: getColumnModel(),
	        renderTo: options.grid_element,
	        width: options.width,
	        height: options.height,
			//autoSizeColumns: true,
	        //autoExpandColumn: 'name',
	        title: options.messages.title,
	        frame: true,
	        clicksToEdit:1,
			sm: new Ext.grid.RowSelectionModel({singleSelect:true}),

			//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Header panel with a button toolbar
			//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			tbar: [
				//################################################
				// Add an existing item button (no server call)
				//################################################
				{
					text: options.messages.buttons.add.label,
		            tooltip: options.messages.buttons.add.tooltip,
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/add.gif',
					handler: function() {
						var newRecord = new Ext.data.Record(
							{
								name: '', /*'...',*/
								status: 'NEW'
							}
						);
						grid.stopEditing();
						ds.insert(0, newRecord);
						grid.startEditing(0, 0);
					}
				},
				
				'-',
				
				//################################################
				// Delete an item button (no server call)
				//################################################
				{
					text: options.messages.buttons.remove.label,
		            tooltip: options.messages.buttons.remove.tooltip,
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/cross.gif',
					handler: function() {
						selectedRow = grid.getSelectionModel().getSelected();
						if (selectedRow) {
							ds.remove(selectedRow);
							save_grid_data_in_element(ds, options.form_data_element);
						}
					}
				},
				
				'-',
				
				//################################################
				// Add-new-item-to-database button (no server call)
				//################################################
				{
					text: options.messages.buttons.newItem.label,
		            tooltip: options.messages.buttons.newItem.tooltip,
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/wand.gif',
					handler: function(e)
					{
				        Ext.MessageBox.prompt(options.messages.buttons.newItem.label, options.messages.buttons.newItem.prompt,
				        function(btn, text)
				        {
							//Ext.Msg.alert('Button Click', 'You clicked the ' + btn + ' button and entered the text "' + text + '".');
							if (btn == 'ok')
							{
						        // Send the new item's name to the server using *true* AJAX
						        // (not ausing a dummy form element submit as in the editor grid screencast)
								var conn = new Ext.data.Connection();
					            // grid.getGridEl().mask(options.messages.waitingText);
					            conn.request({
									url: options.url,
					                method: 'POST',
									params: {action_new:'true', name:text},
					                callback: function(param, success, responseObject) 
					                {
					                    //alert(responseObject.responseText);
										// Decode the JSON response text to an object
										var decoded = Ext.util.JSON.decode(responseObject.responseText);
										var newName = decoded.name;
										var statusMessage;
										if (decoded.status == "success") {
											statusMessage = options.messages.buttons.newItem.success;
										} else if (decoded.status == "invalid") {
											statusMessage = options.messages.buttons.newItem.invalid;
										} else if (decoded.status == "accessDenied") {
											statusMessage = options.messages.buttons.newItem.accessDenied;
										} else if (decoded.status == "exists") {
											statusMessage = options.messages.buttons.newItem.exists;
										} else { // generic failure
											statusMessage = options.messages.buttons.newItem.failure;
										}
										// Carry out a parametric replacement of the new tag's name in the status message 
										statusMessage = statusMessage.replace(/\[0\]/g, "'" + decoded.name + "'");
										Ext.Msg.alert('Status', statusMessage);
					                }
					            });
						    }
						    //else if (btn == 'cancel')
						    //{
						    //	// User cancelled, do nothing (might show a cancellation message later on)
						    //}
					    });
					}
				},
				
				'-'
			]
		});
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Row styling (data-dependent)
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		/*
		===== TESTING ===== 
		grid.getView().getRowClass = function(record, index) {
			if(record.data.id > 5) {
				return "greenrow";
			}
		}
		===== TESTING =====
		*/
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Event handlers
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		
		// Save cell editing changes in form element. This keeps the
		// form element value up to date at all times.
		grid.on('afteredit',
		function(e) {
			// Update the record's status unless it's a new one (in which case
			// this event handler is also trigerred). 
			if (e.record.data.status != 'NEW') {
				e.record.data.status = 'UPDATED';
			}
			save_grid_data_in_element(ds, options.form_data_element);
    	});
    	
		grid.render();
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Header panel with a button toolbar
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		/* Ext 2.0 RC1 (beta) code */
		/*
		var gridHeaderPanel = grid.getView().getHeaderPanel(true);
		var tb = new Ext.Toolbar(
			gridHeaderPanel,
			[
				//################################################
				// Add an existing item button (no server call)
				//################################################
				{
					text: options.messages.buttons.add.label,
		            tooltip: options.messages.buttons.add.tooltip,
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/add.gif',
					handler: function() {
						ds.add(
							new Ext.data.Record(
								{
									name: '', //'...'
									status: 'NEW'
								}
							)
						)
					}
				},
				
				'-',
				
				//################################################
				// Delete an item button (no server call)
				//################################################
				{
					text: options.messages.buttons.remove.label,
		            tooltip: options.messages.buttons.remove.tooltip,
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/cross.gif',
					handler: function() {
						selectedRow = grid.getSelectionModel().getSelected();
						if (selectedRow) {
							ds.remove(selectedRow);
							save_grid_data_in_element(ds, options.form_data_element);
						}
					}
				},
				
				'-',
				
				//################################################
				// Add-new-item-to-database button (no server call)
				//################################################
				{
					text: options.messages.buttons.newItem.label,
		            tooltip: options.messages.buttons.newItem.tooltip,
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/wand.gif',
					handler: function(e)
					{
				        Ext.MessageBox.prompt(options.messages.buttons.newItem.label, options.messages.buttons.newItem.prompt,
				        function(btn, text)
				        {
							//Ext.Msg.alert('Button Click', 'You clicked the ' + btn + ' button and entered the text "' + text + '".');
							if (btn == 'ok')
							{
						        // Send the new item's name to the server using *true* AJAX
						        // (not ausing a dummy form element submit as in the editor grid screencast)
								var conn = new Ext.data.Connection();
					            // grid.getGridEl().mask(options.messages.waitingText);
					            conn.request({
									url: options.url,
					                method: 'POST',
									params: {action_new:'true', name:text},
					                callback: function(param, success, responseObject) 
					                {
					                    //alert(responseObject.responseText);
										// Decode the JSON response text to an object
										var decoded = Ext.util.JSON.decode(responseObject.responseText);
										var newName = decoded.name;
										var statusMessage;
										if (decoded.status == "success") {
											statusMessage = options.messages.buttons.newItem.success;
										} else if (decoded.status == "invalid") {
											statusMessage = options.messages.buttons.newItem.invalid;
										} else if (decoded.status == "accessDenied") {
											statusMessage = options.messages.buttons.newItem.accessDenied;
										} else if (decoded.status == "exists") {
											statusMessage = options.messages.buttons.newItem.exists;
										} else { // generic failure
											statusMessage = options.messages.buttons.newItem.failure;
										}
										// Carry out a parametric replacement of the new tag's name in the status message 
										statusMessage = statusMessage.replace(/\[0\]/g, "'" + decoded.name + "'");
										Ext.Msg.alert('Status', statusMessage);
					                }
					            });
						    }
						    //else if (btn == 'cancel')
						    //{
						    //	// User cancelled, do nothing (might show a cancellation message later on)
						    //}
					    });
					}
				}
			]
		);
		*/
	}
	
	//==============================
	// Global functions
	//==============================
	return {
		init : function() {
			setupDataSource();
			buildGrid();
		},
		
		getDataSource: function() {
			return ds;
		},

		getTestProxy: function() {
			return testProxy;
		}
	}
}
