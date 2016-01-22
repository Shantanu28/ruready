//################################################################
// ExtJS Example: an editable grid that populates hidden form
// fields. It is pre-populated using a remote data source.
//################################################################

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
 * @param target_element id of target form element
 */
function save_grid_data_in_element(ds, target_element)
{
	var json_data = encode_grid_data(ds);
	var element = Ext.get(target_element);
	element.set({value:json_data});
}


function GridUI(options) {
	//==============================
	// Global variables
	//==============================
	var ds; 						// Hold our data
	var grid; 						// Grid component
	var columnModel; 				// Column definitions and rendering
	var dsContext;
	var actionUrl = options.url;	// Server action that executes grid editing operations
	var target_element = options.target_element;
	
	//==============================
	// Data sources (stores)
	//==============================
	function setupDataSource() {
		// On-load data source
		ds = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: actionUrl}),
			reader:  new Ext.data.JsonReader({
				root: 'data',
				id: 'id'
				}, 
				[
					{name: 'id', type: 'string'}, // Note: it's not a long or integer -- a string is more flexible
					{name: 'context'}
				])
			}
		);
		
		// Combo-box on-change event data source
		dsContext = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: actionUrl + '?action_complete=true'}),
			reader:  new Ext.data.JsonReader(
				{root: 'data', id: 'id'},
				[
					{name: 'id', type: 'string'}, // Note: it's not a long or integer -- a string is more flexible
					{name: 'context'}
				])
			}
		);		
		
		ds.load();
		dsContext.load();
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
						header: 'ID',
						width:100,
						sortable: true,
						dataIndex: 'id'
					},

					//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					// Context column
					// Drop-down menu tied to a remote data source
					//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					{
						header: 'Context',
						width:100,
						sortable: true,
						dataIndex: 'context',
						editor: new Ext.grid.GridEditor(
							new Ext.form.ComboBox({
								typeAhead:true,
								minChars:2,
								triggerAction:'all',
								lazyRender:true,
								store: dsContext,
								displayField: 'context',
								valueField:'id'
							})
						),
						renderer:
							function(data) {
								record = dsContext.getById(data);
								if(record) {
									return record.data.context;
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
		grid = new Ext.grid.EditorGrid(
			'mygrid',
			{
				ds: ds,
				cm: getColumnModel(),
				autoSizeColumns: true,
				selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
			}
		);
		
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
			save_grid_data_in_element(ds, target_element);
    	});
    	
		grid.render();
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Header panel with a button toolbar
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		var gridHeaderPanel = grid.getView().getHeaderPanel(true);
		var tb = new Ext.Toolbar(
			gridHeaderPanel,
			[
				//#######################################
				// Add a new row button (no server call)
				//#######################################
				new Ext.Toolbar.Button({
					text: 'Add Row',
		            tooltip:'Add a new row',
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/add.gif',
					handler: function() {
						ds.add(
							new Ext.data.Record(
								{
									context: '...',
									status: 'NEW'
								}
							)
						)
					}
				}),
				
				'-',
				
				//#######################################
				// Delete a row button (server call)
				//#######################################
				{
					text: 'Delete Row',
		            tooltip:'Delete a row and save changes',
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/fam/cross.gif',
					handler: function() {
						selectedRow = grid.getSelectionModel().getSelected();
						if (selectedRow) {
							ds.remove(selectedRow);
							save_grid_data_in_element(ds, target_element);
						}
					}
				}
			]
		)
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
		}
	}
}

//============================================================
// Main function; called upon document on-load
//============================================================
Ext.onReady(function() {
	// Enable quick tips
	Ext.QuickTips.init();
	
	var grid1 = GridUI({
		// Pre-population action URL
		url:'/ru2/demo/open/grid/simpleDataGrid.do',
		
		// ID of form input element to be populated and updated with this grid's contents 
		target_element: 'updategrid'
	});
	
	// Render Grid UI component
	grid1.init();
});
