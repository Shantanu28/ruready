/* http://snippets.scottwalter.com/posts/show/253 */
  var PagesUI = function() {  
        var rootContextMenu = new Ext.menu.Menu('rootContext');
        var mainContextMenu = new Ext.menu.Menu('mainContext');

        mainContextMenu.add(
            new Ext.menu.Item({text: 'Edit Page...', handler:  editPage}),
            new Ext.menu.Item({text: 'Delete Page', handler:  deletePage}),
            new Ext.menu.Separator(),
            new Ext.menu.Item({text: 'Add New Page...', handler:  addPage})
        );

        rootContextMenu.add(
            new Ext.menu.Item({text: 'Add New Page...', handler:  addPage})
        );

        function addPage() {
            alert('add page');
        }


        function editPage() {
            alert('Edit page');
        }


        function deletePage() {
            alert('delete page');
        }


        function buildTree(config) {
            if (!config) return null;
			var c = config.children;
			config.leaf = c ? false : true;
			var child, node = new Ext.tree.TreeNode(config);
                        
			if (node && c && c.length) {
			    for (var i = 0; i < c.length; i++) {
			    	child = buildTree(c[i]);
			        if (child) node.appendChild(child);
			    }
			}
			return node;
        }

        return {
            init : function(){  
                    var tree = new Ext.tree.TreePanel('pages-tree-div', {
		        animate:false, enableDD:true, containerScroll:true, rootVisible:true,
		        lines:true
		    });
		    var root = buildTree({
                        id : "0", 
                        text: 'Pages',
                        cls:'folder',
                        children: [
                            {id:1, cls:'file', text: 'Home Page'},
                            {id:2, cls:'folder', text: 'About Us',
                            children: [
                                {id:"3", cls:'file', text: 'General Info'},
                                {id:"4", cls:'file', text: 'History'},
                                {id:"5", cls:'file', text: 'Locations'}
                                //{id:6, cls:'file', text: 'General Info', href:'dummy.html'}
                            ]},
                            {id:10, cls:'folder', text: 'Products',
                            children: [
                                {id:11, cls:'file', text: 'P1'},
                                {id:12, cls:'file', text: 'P1'},
                                {id:13, cls:'file', text: 'P1'},
                                {id:14, cls:'file', text: 'P1'},
                                {id:15, cls:'file', text: 'P1'},
                                {id:16, cls:'file', text: 'P1'},
                                {id:17, cls:'file', text: 'P1'},
                                {id:18, cls:'file', text: 'P1'},
                                {id:19, cls:'file', text: 'P1'},
                                {id:20, cls:'file', text: 'P1'},
                                {id:21, cls:'file', text: 'P1'},
                                {id:22, cls:'file', text: 'P1'},
                                {id:23, cls:'file', text: 'P1'}
                            ]}
                        ]
                    });

		    tree.setRootNode(root);
		    tree.render();
                    tree.expandPath("/0");
                    tree.on('contextmenu', PagesUI.menuShow, this);                    

                    tree.on("click", function(node, e) {
                    });

                    tree.on("dblclick", function(node, e) {
                        if(node.childNodes.length==0) {
                            editPage();
                        }
                    });

                    tree.on("nodedrop", function(e) {
                        sourceNode = e.dropNode;
                        destinationNode = e.target; 

                       SWUtils.logDebug(sourceNode.text + " to " + destinationNode.text);       
                    });

                    var tree2 = new Ext.tree.TreePanel('pages2-tree-div', {
                        animate:true, 
                        loader: new Ext.tree.TreeLoader({dataUrl:'<%=url_for :controller => 'pages', :action => 'pages'%>'}),
                        enableDD:true,
                        containerScroll: true
                    });

                    // set the root node
                    var root = new Ext.tree.AsyncTreeNode({
                        text: 'yui-ext',
                        draggable:false,
                        id:'source'
                    });
                    tree2.setRootNode(root);
                    tree2.render();
                            }, 
            menuShow : function( node ){
                if(node.isRoot) {
                     rootContextMenu.show(node.ui.getAnchor());
                } else {
                    mainContextMenu.show(node.ui.getAnchor());
                }
            }
        }
    }();

    Ext.onReady(PagesUI.init, PagesUI, true);