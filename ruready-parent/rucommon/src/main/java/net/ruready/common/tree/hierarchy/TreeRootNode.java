/*******************************************************
 * Source File: TreeRootNode.java
 *******************************************************/
package net.ruready.common.tree.hierarchy;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah
 * 
 * University of Utah, Salt Lake City, UT 84112 Protected by U.S. Provisional
 * Patent U-4003, February 2006
 * 
 * @version Feb 12, 2007
 * 
 * A concrete root node of a tree.
 */
public class TreeRootNode extends TreeNode implements RootNode
{

	/**
	 * @see net.ruready.common.tree.hierarchy.TreeNode#getChildren()
	 */
	@Override
	public String getChildren()
	{
		return "TreeRoot::getChildren()";
	}

	@Override
	public String toString()
	{
		return "Root Node";
	}

}
