var GridUI = function() {

	var ds; //hold our data
	var grid; //component
	var columnModel; // definition of the columns
	var dsContext;
	var gridForm;
	
	
	function setupDataSource() {
		ds = new Ext.data.Store({
			proxy:  new Ext.data.ScriptTagProxy({url:'http://0.0.0.0:3000/admin/json'}),
			reader:  new Ext.data.JsonReader(
				{root: 'data', id: 'description'},
				[
					{name: 'description'},
					{name: 'context'}, 
					{name: 'priority'},
					{name: 'duedate', type:'date', dateFormat:'n/j/Y h:i a'},
					{name: 'completed', type:'boolean'}
				]
			)
		}
		);
		
		dsContext = new Ext.data.Store({
			proxy:  new Ext.data.ScriptTagProxy({url:'http://0.0.0.0:3000/admin/contexts'}),
			reader:  new Ext.data.JsonReader(
				{root: 'data', id: 'id'},
				[
					{name: 'description'},
					{name: 'id'}
				]
			)
		}
		);		
		
		ds.load();
		dsContext.load();
	}

	
	function getColumnModel() {
		if(!columnModel) {
			columnModel = new Ext.grid.ColumnModel(
				[
					{
						header: 'Description',
						width: 250,
						sortable: true,
						dataIndex: 'description',
						editor: new Ext.grid.GridEditor(
							new Ext.form.TextField(
								{
									allowBlank:false
								}
							)
						)
					},
				
					{
						header: 'Context',
						width:100,
						sortable: true,
						dataIndex: 'context',
						editor: new Ext.grid.GridEditor(
							new Ext.form.ComboBox(
								{
									typeAhead:false,
									triggerAction:'all',
									lazyRender:true,
									store:  dsContext,
									displayField: 'description',
									valueField:'id'
								}
							)
						),
						renderer:
							function(data) {
								record = dsContext.getById(data);
								if(record) {
									return record.data.description;
								} else {
									return data;
								}
							}
					},
					{
						header: 'Priority',
						width:100,
						sortable:true,
						dataIndex: 'priority',
						renderer:  function(data, cell, record, rowIndex, columnIndex, store) {
							switch(data) {
								case "A":
									cell.css = "redcell";
									return "High";
								case "B":
									return "Medium";
								case "C":
									return "Low";
							}							
						},
						editor: new Ext.grid.GridEditor(
							new Ext.form.ComboBox(
								{
									typeAhead:true,
									triggerAction:'all',
									lazyRender:true,
									transform:'priorities'
								}
							)
						)
					},
					{
						header: 'Date Due',
						width:100,
						sortable: true,
						dataIndex: 'duedate',
						renderer:  Ext.util.Format.dateRenderer('m/d/Y'),
						editor: new Ext.grid.GridEditor(
							new Ext.form.DateField(
								{
									format:'m/d/Y'
								}
							)
						)
					},
					{
						header: 'Is Complete?',
						width:100,
						sortable:true,
						dataIndex:'completed',
						align:'center',
						renderer: function(data) {
							return "<input type='checkbox' " + (data ? "checked='checked'" : "") + " onclick='return false;'/>"
						},
						editor: new Ext.grid.GridEditor(
							new Ext.form.Checkbox(
								{
									
								}
							)
						)
					}
				]
			);
		}
		return columnModel;
	}
	
	function buildGrid() {	
		gridForm = new Ext.BasicForm(
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
				selModel: new Ext.grid.RowSelectionModel({singleSelect:true})
			}
		);
		
		grid.getView().getRowClass = function(record, index) {
			if(record.data.completed) {
				return "greenrow";
			}
		}
		
		/*
		grid.on("rowdblclick", function(grid) {
			alert(grid.getSelectionModel().getSelected().data.description);
		});
		*/
		
		grid.render();
		
		var gridHeaderPanel = grid.getView().getHeaderPanel(true);
		var tb = new Ext.Toolbar(
			gridHeaderPanel,
			[
				{
					text: 'Add Row',
					handler: function() {
						ds.add(
							new Ext.data.Record(
								{
									description:'new task',
									completed: false,
									duedate: new Date(),
									priority:'B',
									context:'2',
									newRecord:true
								}
							)
						)
					}
				},
				{
					text: 'Delete Row',
					handler: function() {
						selectedRow = grid.getSelectionModel().getSelected();
						if(selectedRow) {
							gridForm.submit(
								{
									waitMsg: 'Deleting row, please wait...',
									url:'http://0.0.0.0:3000/admin/deleterow',
									params:{rowid:selectedRow.data.description},
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
				}	,
				{
					text: 'Save Changes',
					handler: function() {
						jsonData = "[";
						
						for(i=0;i<ds.getCount();i=i+1) {
							record = ds.getAt(i);
							if(record.data.newRecord || record.dirty) {
								jsonData += Ext.util.JSON.encode(record.data) + ",";
							}
						}
						
						jsonData = jsonData.substring(0,jsonData.length-1) + "]";
						
						console.info(jsonData);
						
						gridForm.submit(
							{
								waitMsg: 'Saving changes, please wait...',
								url:'http://0.0.0.0:3000/admin/updategrid',
								params:{data:jsonData},
								success:function(form, action) {
									alert('Congrats!  Your changes were saved!!!!');
								},
								failure: function(form, action) {
									alert('Oops the delete did not work out too well!');
								}
							}
						);						
					}
				}	
			]
		)
	}
	
	return {
		init : function() {
			setupDataSource();
			buildGrid();
		},
		
		getDataSource: function() {
			return ds;
		}
	}
}();

var GridUI2 = function() {

	var ds; //hold our data
	var grid; //component
	var columnModel; // definition of the columns
	var dsContext;
	var gridForm;
	
	
	function setupDataSource() {
		ds = new Ext.data.Store({
			proxy:  new Ext.data.ScriptTagProxy({url:'http://0.0.0.0:3000/admin/json'}),
			reader:  new Ext.data.JsonReader(
				{root: 'data', id: 'description'},
				[
					{name: 'description'},
					{name: 'context'}, 
					{name: 'priority'},
					{name: 'duedate', type:'date', dateFormat:'n/j/Y h:i a'},
					{name: 'completed', type:'boolean'}
				]
			)
		}
		);
		
		dsContext = new Ext.data.Store({
			proxy:  new Ext.data.ScriptTagProxy({url:'http://0.0.0.0:3000/admin/contexts'}),
			reader:  new Ext.data.JsonReader(
				{root: 'data', id: 'id'},
				[
					{name: 'description'},
					{name: 'id'}
				]
			)
		}
		);		
		
		ds.load();
		dsContext.load();
	}

	
	function getColumnModel() {
		if(!columnModel) {
			columnModel = new Ext.grid.ColumnModel(
				[
					{
						header: 'Description',
						width: 250,
						sortable: true,
						dataIndex: 'description',
						editor: new Ext.grid.GridEditor(
							new Ext.form.TextField(
								{
									allowBlank:false
								}
							)
						)
					},
				
					{
						header: 'Context',
						width:100,
						sortable: true,
						dataIndex: 'context',
						editor: new Ext.grid.GridEditor(
							new Ext.form.ComboBox(
								{
									typeAhead:false,
									triggerAction:'all',
									lazyRender:true,
									store:  dsContext,
									displayField: 'description',
									valueField:'id'
								}
							)
						),
						renderer:
							function(data) {
								record = dsContext.getById(data);
								if(record) {
									return record.data.description;
								} else {
									return data;
								}
							}
					},
					{
						header: 'Priority',
						width:100,
						sortable:true,
						dataIndex: 'priority',
						renderer:  function(data, cell, record, rowIndex, columnIndex, store) {
							switch(data) {
								case "A":
									cell.css = "redcell";
									return "High";
								case "B":
									return "Medium";
								case "C":
									return "Low";
							}							
						},
						editor: new Ext.grid.GridEditor(
							new Ext.form.ComboBox(
								{
									typeAhead:true,
									triggerAction:'all',
									lazyRender:true,
									transform:'priorities2'
								}
							)
						)
					},
					{
						header: 'Date Due',
						width:100,
						sortable: true,
						dataIndex: 'duedate',
						renderer:  Ext.util.Format.dateRenderer('m/d/Y'),
						editor: new Ext.grid.GridEditor(
							new Ext.form.DateField(
								{
									format:'m/d/Y'
								}
							)
						)
					},
					{
						header: 'Is Complete?',
						width:100,
						sortable:true,
						dataIndex:'completed',
						align:'center',
						renderer: function(data) {
							return "<input type='checkbox' " + (data ? "checked='checked'" : "") + " onclick='return false;'/>"
						},
						editor: new Ext.grid.GridEditor(
							new Ext.form.Checkbox(
								{
									
								}
							)
						)
					}
				]
			);
		}
		return columnModel;
	}
	
	function buildGrid() {	
		gridForm = new Ext.BasicForm(
			Ext.get("updategrid2"),
			{
				
			}
		);
			
		grid = new Ext.grid.EditorGrid(
			'mygrid2',
			{
				ds: ds,
				cm: getColumnModel(),
				autoSizeColumns: true,
				selModel: new Ext.grid.RowSelectionModel({singleSelect:true})
			}
		);
		
		grid.getView().getRowClass = function(record, index) {
			if(record.data.completed) {
				return "greenrow";
			}
		}
		
		/*
		grid.on("rowdblclick", function(grid) {
			alert(grid.getSelectionModel().getSelected().data.description);
		});
		*/
		
		grid.render();
		
		var gridHeaderPanel = grid.getView().getHeaderPanel(true);
		var tb = new Ext.Toolbar(
			gridHeaderPanel,
			[
				{
					text: 'Add Row',
					handler: function() {
						ds.add(
							new Ext.data.Record(
								{
									description:'old task',
									completed: false,
									duedate: new Date(),
									priority:'A',
									context:'3',
									newRecord:true
								}
							)
						)
					}
				},
				{
					text: 'Delete Row',
					handler: function() {
						selectedRow = grid.getSelectionModel().getSelected();
						if(selectedRow) {
							gridForm.submit(
								{
									waitMsg: 'Deleting row, please wait...',
									url:'http://0.0.0.0:3000/admin/deleterow',
									params:{rowid:selectedRow.data.description},
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
				}	,
				{
					text: 'Save Changes',
					handler: function() {
						jsonData = "[";
						
						for(i=0;i<ds.getCount();i=i+1) {
							record = ds.getAt(i);
							if(record.data.newRecord || record.dirty) {
								jsonData += Ext.util.JSON.encode(record.data) + ",";
							}
						}
						
						jsonData = jsonData.substring(0,jsonData.length-1) + "]";
						
						console.info(jsonData);
						
						gridForm.submit(
							{
								waitMsg: 'Saving changes, please wait...',
								url:'http://0.0.0.0:3000/admin/updategrid',
								params:{data:jsonData},
								success:function(form, action) {
									alert('Congrats!  Your changes were saved!!!!');
								},
								failure: function(form, action) {
									alert('Oops the delete did not work out too well!');
								}
							}
						);						
					}
				}	
			]
		)
	}
	
	return {
		init : function() {
			setupDataSource();
			buildGrid();
		},
		
		getDataSource: function() {
			return ds;
		}
	}
}();

Ext.onReady(function() {
	Ext.onReady(GridUI.init, GridUI, true);
	Ext.onReady(GridUI2.init, GridUI2, true);
});
