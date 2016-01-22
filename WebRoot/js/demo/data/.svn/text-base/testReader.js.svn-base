//################################################################
// An ExtJS example of using an JSON reader.
// Works only with an HTTP Proxy (myProxy2), not with a memory
// proxy that receives a string as its constructor argument
// (myProxy1). MemoryProxy expects an array, I guess.
//################################################################

function ReaderUI()
{
	//==============================
	// Global variables
	//==============================
	var data = "{'results': 2, 'rows': [{'name': 'test1'},{'name': 'test2'}]}";
	var ds;
	
	function setup() {
		var myProxy1 =  new Ext.data.MemoryProxy(data);
		var myProxy2 = new Ext.data.HttpProxy({
			url:'/ru2/js/demo/data/data.json'
		});
		var myReader = new Ext.data.JsonReader({
			totalProperty: "results",
			root: "rows"
			}, 
			[
				{name: 'name'}
			]);

		ds = new Ext.data.Store({
			proxy:  myProxy2,
			reader:  myReader
		});
		
		ds.load();
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
