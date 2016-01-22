var GridUI = function() {

	var ds; //hold our data
	var grid; //component
	var columnModel; // definition of the columns
	
	function setupDataSource() {
		var myProxy = new Ext.data.HttpProxy({
			url: '/ru2/demo/open/grid/simpleDataGrid.do'
		});
		var myReader = new Ext.data.JsonReader({
			root: "data",
			id: "id"
			}, 
			[
				{name: 'id'},
				{name: 'context'}
			]);

		ds = new Ext.data.Store({
			proxy:  myProxy,
			reader:  myReader
		});
		
		ds.load();
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
						dataIndex: 'context'
					}
				]
			);
		}
		return columnModel;
	}
	
	function buildGrid() {
		grid = new Ext.grid.Grid(
			'mygrid',
			{
				ds: ds,
				cm: getColumnModel(),
				autoSizeColumns: true
			}
		);
		
		grid.getView().getRowClass = function(record, index) {
			if(record.data.completed) {
				return "greenrow";
			}
		}
		
		grid.on("rowdblclick", function(grid) {
			alert(grid.getSelectionModel().getSelected().data.description);
		});
		
		grid.render();
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
