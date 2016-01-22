/*******************************************************************************
 * Source File: GenericTreeNode.java
 ******************************************************************************/
package test.ruready.eis.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         Protected by U.S. Provisional Patent U-4003, February 2006
 * @version Jan 30, 2007 A generic tree node test.
 */
public class GenericTreeNode<D extends Serializable & Comparable<? super D>>
		implements Comparable<GenericTreeNode<D>>
{
	/* usual member variables */
	private Collection<GenericTreeNode<D>> children;

	D data;

	/* constructor for the node */
	public GenericTreeNode()
	{
		children = new ArrayList<GenericTreeNode<D>>();
		// children = new TreeSet<Node<D>>();
	}

	/* constructor for the node */
	public GenericTreeNode(D data)
	{
		this.data = data;
		children = new ArrayList<GenericTreeNode<D>>();
		// children = new TreeSet<Node<D>>();
	}

	/* return the collection of children */
	public Collection<GenericTreeNode<D>> getChildren()
	{
		return children;
		// return new ArrayList<Node<D>>(children);
	}

	/* add child method */
	public void addChild(GenericTreeNode<D> newChild)
	{
		children.add(newChild);
	}

	public int compareTo(GenericTreeNode<D> o)
	{
		return getData().compareTo(o.getData());
	}

	/**
	 * @return the data
	 */
	public D getData()
	{
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(D data)
	{
		this.data = data;
	}

}
