//################################################################
// ExtJS Example: two tree widgets.
//################################################################

function TreeTest() {
    // shorthand
    var Tree = Ext.tree;
    
    return {
        init : function() {
    		
    		// Define tree loader    
    		var loader = new Tree.TreeLoader({dataUrl:'/ru2/content/ajax/explore/tree.do'});
    		loader.on("beforeload", function(treeLoader, node) {
    			if (node.root) {
        			treeLoader.baseParams.category = 'root';
        		}
    		}, this);
    
            // yui-ext tree
            var tree = new Tree.TreePanel({
                el:'tree',
                animate:true, 
                autoScroll:true,
                loader: loader,
                enableDD: false,
                containerScroll: true,
                dropConfig: {}
            });
            
            // add a tree sorter in folder mode
            //new Tree.TreeSorter(tree, {folderSort:true});
            
            // set the root node
            var root = new Tree.AsyncTreeNode({
            	allowDrag: false,
            	allowDrop: false,
                text: 'Root', 
                draggable: false, // disable root node dragging
                id:'1'
            });
            tree.setRootNode(root);
            
    		tree.on("click", function(node, e) {
				Ext.Msg.alert('Node Clicked', 'Node clicked: ' + node.attributes.id);
				//Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?');
    		}, this);
            
            // render the tree
            tree.render();
            
            root.expand(false, /*no anim*/ false);
        }
    };
}

Ext.onReady(function() {
	var test = TreeTest();
	test.init();
});
