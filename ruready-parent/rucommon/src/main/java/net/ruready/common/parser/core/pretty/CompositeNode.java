/*****************************************************************************************
 * Source File: CompositeNode.java
 ****************************************************************************************/
package net.ruready.common.parser.core.pretty;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.rl.CommonNames;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 */

/**
 * This class provides a composite node that can contain other nodes.
 * 
 * @author Steven J. Metsker
 * @version 1.0
 */
public class CompositeNode extends ComponentNode
{
	protected List<ComponentNode> children = new ArrayList<ComponentNode>();

	/**
	 * Create a node that can contain other nodes, and that holds the given value.
	 */
	public CompositeNode(Object v)
	{
		this.value = v;
	}

	/**
	 * Add a node after the currently held nodes.
	 * 
	 * @param ComponentNode
	 *            another node, either a composite or a terminal node
	 */
	public void add(ComponentNode node)
	{
		children.add(node);
	}

	/**
	 * Add a node before the currently held nodes.
	 * 
	 * @param ComponentNode
	 *            another node, either a composite or a terminal node
	 */
	public void insert(ComponentNode n)
	{
		children.add(0, n);
	}

	/**
	 * Return a textual description of this node. We take care to print a node only once,
	 * since a composite may contain cycles. We may or may not want to print the object
	 * this composite contains -- the identation indicates the presence of the composite
	 * and can obviate the need for printing the composite's value. ShowDangle gives an
	 * example of not needing to see the composite's value.
	 * 
	 * @param depth
	 * @param label
	 * @param visited
	 * @return
	 * @see net.ruready.common.parser.core.pretty.ComponentNode#toString(int, boolean,
	 *      java.util.List)
	 */
	@Override
	protected String toString(int depth, boolean label, List<ComponentNode> visited)
	{

		if (visited.contains(this))
		{
			return "...";
		}
		visited.add(this);
		StringBuffer buf = new StringBuffer();
		if (label)
		{
			buf.append(indent(depth));
			buf.append(value);
			buf.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		for (ComponentNode child : children)
		{
			buf.append(child.toString(depth + 1, label, visited));
		}
		return buf.toString();
	}
}
