//################################################################
// An ExtJS example of decoding a string into a JSON object.
//################################################################

//============================================================
// Main function; called upon document on-load
//============================================================
Ext.onReady(function() {
	var data = "{'results': 2, 'rows': [{'name': 'test1'},{'name': 'test2'}]}";
	var decoded = Ext.util.JSON.decode(data);
	
	var dh = Ext.DomHelper;
	for (var i = 0; i < decoded.rows.length; i++) {
		var list = dh.append('my-div', {tag: 'p', html: decoded.rows[i].name});
	}
	
	Ext.select("p").addClass("red");
});
