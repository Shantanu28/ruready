/*******************************************************
 * Source File: TreeNamedRootNode.java
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
 * An implementation of a tree root node with a name property.
 */
public class TreeNamedRootNode extends TreeRootNode implements NamedRootNode
{
	private String name;

	public TreeNamedRootNode(String name)
	{
		super();
		this.name = name;
	}

	/**
	 * @see net.ruready.common.tree.hierarchy.RootNode#getName()
	 */
	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return "Root Node '" + name + "'";
	}
}
