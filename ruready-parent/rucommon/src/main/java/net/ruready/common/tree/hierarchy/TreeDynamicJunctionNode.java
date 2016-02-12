/*******************************************************
 * Source File: TreeDynamicJunctionNode.java
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
 * A concrete implementation of a dynamic junction node.
 */
public class TreeDynamicJunctionNode extends TreeDynamicNode implements
		DynamicJunctionNode
{
	private JunctionNode node;

	public TreeDynamicJunctionNode(JunctionNode node)
	{
		// This is a must for non-overridden base class methods to work
		// with this child class' node field. Had we used super() instead,
		// super.node would be null and getChildren() would throw a null
		// pointer exception trying to use it. Hence, we set both to the same
		// reference so that all child methods use the -same- node type.
		super(node);
		this.node = node;
	}

	/**
	 * @see net.ruready.common.tree.hierarchy.JunctionNode#getParent()
	 */
	public AbstractNode getParent()
	{
		return node.getParent();
	}

}
