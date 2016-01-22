var GridUI = function() {

	var ds; //hold our data
	var grid; //component
	var columnModel; // definition of the columns
	var dsContext;
	var gridForm;
	
	
	function setupDataSource() {
		ds = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({
				url: '/ru2/demo/open/grid/simpleDataGrid.do'
			}),
			reader:  new Ext.data.JsonReader({
				root: "data",
				id: "id"
				}, 
				[
					{name: 'id'},
					{name: 'context'}
				])
		});
		
		dsContext = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({
				url: '/ru2/demo/open/grid/simpleDataGrid.do'
			}),
			reader:  new Ext.data.JsonReader({
				root: "data",
				id: "id"
				}, 
				[
					{name: 'id'},
					{name: 'context'}
				])
		});
		
		ds.load();
		dsContext.load();
	}

	
	function getColumnModel() {
		if(!columnModel) {
			columnModel = new Ext.grid.ColumnModel(
				[
					{
						header: 'ID',
						width:100,
						sortable: true,
						dataIndex: 'id'
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
			if(record.data.id > 5) {
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
									context:'New Context',
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
									url:'/ru2/demo/open/grid/simpleDataGrid.do',
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
						
						for(i=0;i<ds.getCount();i++) {
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
								url:'/ru2/demo/open/grid/simpleDataGrid.do',
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

Ext.onReady(GridUI.init, GridUI, true);
