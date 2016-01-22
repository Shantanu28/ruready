var GridUI = function() {

	var ds; //hold our data
	var grid; //component
	var columnModel; // definition of the columns
	
	
	var gridData = [
		['Create about section','context','A','9/1/2006 3:30pm', true],
		['Create about section1','context','A','9/1/2006 3:30pm', false],
		['Create about section2','context','A','9/1/2006 3:30pm', false],
		['Create about section3','context','B','9/1/2006 3:30pm', true],
		['Create about section4','context','B','9/1/2006 3:30pm', true],
		['Create about section5','context','A','9/1/2006 3:30pm', false],
		['Create about section6','context','C','9/1/2006 3:30pm', true],
		['Create about section7','context','A','9/1/2006 3:30pm', false],
		['Create about section8','context','C','9/1/2006 3:30pm', true],
		['Create about section9','context','A','9/1/2006 3:30pm', true],
		['Create about section10','context','B','9/1/2006 3:30pm', true],
		['Create about section11','context','A','9/1/2006 3:30pm', true],
		['Create about section12','context','A','9/1/2006 3:30pm', true]
	];	
	
	function setupDataSource() {
		ds = new Ext.data.Store({
			proxy: new Ext.data.MemoryProxy(gridData),
			reader: new Ext.data.ArrayReader(
				{id: 0}, 
				[
					{name: 'description'},
					{name: 'context'},
					{name: 'priority', align: 'center'},
					{name: 'dueDate', type: 'date', dateFormat: 'n/j/Y h:ia'},
					{name: 'completed', type: 'boolean'}
				]
			)
		});
		
		ds.load();
	}

	
	function getColumnModel() {
		if(!columnModel) {
			columnModel = new Ext.grid.ColumnModel(
				[
					{
						header: 'Description',
						width: 250,
						sortable: true,
						dataIndex: 'description'
					},
				
					{
						header: 'Context',
						width:100,
						sortable: true,
						dataIndex: 'context'
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
							
						}
					},
					{
						header: 'Date Due',
						width:100,
						sortable: true,
						dataIndex: 'duedate',
						renderer:  Ext.util.Format.dateRenderer('m/d/Y')
					},
					{
						header: 'Is Complete?',
						width:100,
						sortable:true,
						dataIndex:'completed',
						align:'center',
						renderer: function(data) {
							return "<input type='checkbox' " + (data ? "checked='checked'" : "") + " onclick='return false;'/>"
						}
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