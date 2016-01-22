//################################################################
// ExtJS Example: an editable grid tied to a remote data source
//################################################################

function GridUI(options) {
	//==============================
	// Global variables
	//==============================
	var ds; 						// Hold our data
	var grid; 						// Grid component
	var columnModel; 				// Column definitions and rendering
	var dsContext;
	var gridForm;
	var actionUrl = options.url;	// Server action that executes grid editing operations
	
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
							new Ext.form.ComboBox(
								{
									typeAhead:true,
									minChars:2,
									triggerAction:'all',
									lazyRender:true,
									store:  dsContext,
									displayField: 'context',
									valueField:'id'
								}
							)
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
		gridForm = new Ext.form.BasicForm(
			Ext.get("updategrid"),
			{
				
			}
		);
			
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
									context:'New Context',
									newRecord:true
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
						if(selectedRow) {
							gridForm.submit(
								{
									waitMsg: 'Deleting row, please wait...',
									url: actionUrl,
									params:{action_delete:'true', id:selectedRow.data.id},
									success:function(form, action) {
										ds.remove(selectedRow);
									},
									failure: function(form, action) {
										alert('Oops the delete did not work out too well!');
									}
								}
							);												
						}
					}
				},
				
				'-',

				//#######################################
				// Save new + updated rows (server call)
				//#######################################
				{
					text: 'Save Changes',
		            tooltip:'Save modified rows and newly added rows',
		            cls: 'x-btn-text-icon',
		            icon: '/ru2/images/icons/save.gif',
					handler: function() {
						var updateExists = false;
						jsonData = "[";
							
						for(i=0;i<ds.getCount();i++) {
							record = ds.getAt(i);
							if(record.data.newRecord || record.dirty) {
								jsonData += Ext.util.JSON.encode(record.data) + ",";
								if (!updateExists) {
									updateExists = true;
								}
							}
						}
						// Warning: string will look like "]" if there are no updates
						jsonData = jsonData.substring(0,jsonData.length-1) + "]";
							
						if (updateExists) {
							// Send changes to server
							console.info(jsonData);
							gridForm.submit(
								{
									waitMsg: 'Saving changes, please wait...',
									url: actionUrl,
									params:{action_update:'true', data:jsonData},
									success:function(form, action) {
										Ext.Msg.alert('Success', 'Your changes were saved successfully.');
									},
									failure: function(form, action) {
										Ext.Msg.alert('Failure', 'Failed to save changes!');
									}
								}
							);
						} else {
							Ext.Msg.show({
							   title: 'Warning',
							   msg: 'Nothing to save.',
							   buttons: Ext.MessageBox.OK,
							   icon: Ext.MessageBox.WARNING
							});
							// Ext.Msg.alert('Warning', 'Nothing to save.');
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
		url:'/ru2/demo/open/grid/simpleDataGrid.do'
	});
	
	// Render Grid UI component
	Ext.onReady(grid1.init, grid1, true);
});
