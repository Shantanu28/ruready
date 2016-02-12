/*******************************************************
 * Source File: TreeDynamicNode.java
 *******************************************************/
package net.ruready.common.tree.hierarchy;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A concrete implementation of a dynamic tree node.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Feb 12, 2007
 */
public class TreeDynamicNode implements DynamicNode
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TreeDynamicNode.class);

	// ========================= METHODS ===================================

	private AbstractNode node;

	private String structure;

	protected TreeDynamicNode()
	{

	}

	public TreeDynamicNode(AbstractNode node)
	{
		super();
		this.node = node;
	}

	/**
	 * @return the structure
	 */
	public String getStructure()
	{
		return structure;
	}

	/**
	 * @param structure
	 *            the structure to set
	 */
	public void setStructure(String structure)
	{
		this.structure = structure;
	}

	/**
	 * @see net.ruready.common.tree.hierarchy.TreeNode#getChildren()
	 */
	public String getChildren()
	{
		logger.info("TreeDynamicNode()::getChildren()");
		logger.info("Class reflection: " + ReflectionToStringBuilder.toString(this));
		logger.info("node type is " + node.getClass().getCanonicalName());
		return node.getChildren();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return node + " Structure " + structure;
	}
}
