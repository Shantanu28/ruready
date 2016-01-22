//################################################################
// An ExtJS example of using an XML reader. Broken.
//################################################################

function ReaderUI()
{
	//==============================
	// Global variables
	//==============================
	var data = '<?xml?><dataset><row><name>test</name></row></dataset>';
	var ds;
	
	function setup()
	{
		var RecordDef = Ext.data.Record.create([
			{name: 'name', mapping: 'name'}
		]);
										
		var myReader = new Ext.data.XmlReader({
			record: 'row'
		}, RecordDef);
												
		// On-load data source
		ds = new Ext.data.Store({
			proxy:  new Ext.data.MemoryProxy(data),
//			proxy: new Ext.data.HttpProxy({url:'/ru2/js/demo/data/data.xml'}),
			reader: myReader
		});
		
		ds.load();
		
		alert(ds.getTotalCount());
	}
	
	//==============================
	// Global functions
	//==============================
	return {
		init : function() {
			setup();
		},

		getDs: function() {
			return ds;
		}
	}
}

//============================================================
// Main function; called upon document on-load
//============================================================
Ext.onReady(function() {
	var reader = ReaderUI();
	
	// Render Grid UI component
	Ext.onReady(reader.init, reader, true);
});
