/*******************************************************
 * Source File: TreeJunctionNode.java
 *******************************************************/
package net.ruready.common.tree.hierarchy;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
 *          Academic Outreach and Continuing Education (AOCE)
 *          1901 East South Campus Dr., Room 2197-E
 *          University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
 *          AOCE, Room 2197-E, University of Utah
 *            
 *          University of Utah, Salt Lake City, UT 84112
 * (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * 
 * @version Feb 12, 2007
 * 
 * A concrete implementation of a tree node.
 */
public class TreeJunctionNode extends TreeNode implements JunctionNode
{
	private AbstractNode parent;

	public TreeJunctionNode(AbstractNode parent)
	{
		this.parent = parent;

	}

	@Override
	public String getChildren()
	{
		return "TreeJunctionNode::getChildren()";
	}

	public AbstractNode getParent()
	{
		return parent;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "TreeJunctionNode";
	}

}
