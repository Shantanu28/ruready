/*******************************************************************************
 * Source File: BinarySearchTree.java
 ******************************************************************************/
package net.ruready.common.tree.binary;

import java.io.Serializable;

/**
 * A binary search tree: a binary tree where every node's left subtree has keys
 * less than the node's key, and every right subtree has keys greater than the
 * node's key. The basic operations on a binary search tree take time
 * proportional to the height of the tree. (The height of the Binary Search Tree
 * equals the number of links from the root node to the deepest node.) For a
 * complete binary tree with node n, such operations runs in O(ln n) worst-case
 * time. If the tree is a linear chain of n nodes, however, the same operations
 * takes O(n) worst-case time. To print out the sorted list, use the
 * <code>print()</code> method. More methods may later be implemented using
 * the reference
 * http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/binarySearchTree.htm
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 08/01/2006
 */

public class BinarySearchTree<D extends Serializable & Comparable<? super D>>
		extends BinaryTree<D>
{
	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	public BinarySearchTree()
	{
		super();
	}

	public BinarySearchTree(D o)
	{
		super(o);
	}

	// ========================= METHODS ===================================

	public void print()
	{
		print(2);
	}

	public void insert(D o)
	{
		BinaryNode<D> t, q;
		for (q = null, t = getRoot(); t != null
				&& o.compareTo(t.getData()) != 0; q = t, t =
				o.compareTo(t.getData()) < 0 ? t.getLeft() : t.getRight()) {
		}
		if (t != null) {
			return;
		}
		else if (q == null) {
			setRoot(new BinaryNode<D>(o));
		}
		else if (o.compareTo(q.getData()) < 0) {
			insertLeft(q, o);
		}
		else {
			insertRight(q, o);
		}
	}

	// ========================= TESTING ===================================

	public static void main(String[] args)
	{
	}
}
