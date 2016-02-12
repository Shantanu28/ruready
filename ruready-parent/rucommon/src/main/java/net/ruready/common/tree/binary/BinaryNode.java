/*******************************************************************************
 * Source File: BinaryNode.java
 ******************************************************************************/
package net.ruready.common.tree.binary;

import java.io.Serializable;

import net.ruready.common.rl.CommonNames;

/**
 * An optimized implementation of a binary tree node with two children (left and
 * right). Based on: Java Data Structures (2nd edition) � 1996-2001, Particle
 * (particle@theparticle.com
 * http://www.javacommerce.com/displaypage.jsp?name=javadata2.sql&id=18214#Trees
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
public class BinaryNode<D extends Serializable & Comparable<? super D>>
{
	// ========================= FIELDS ====================================

	private D data;

	private BinaryNode<D> left, right;

	// ========================= CONSTRUCTORS ==============================

	public BinaryNode()
	{
		data = null;
		left = right = null;
	}

	public BinaryNode(D d)
	{
		data = d;
		left = right = null;
	}

	public BinaryNode(D d, BinaryNode<D> l, BinaryNode<D> r)
	{
		data = d;
		setLeft(l);
		setRight(r);
	}

	// ========================= METHODS ===================================

	@Override
	public String toString()
	{
		return CommonNames.MISC.EMPTY_STRING + data;
	}

	// ========================= GETTERS & SETTERS =========================

	public void setLeft(BinaryNode<D> l)
	{
		left = l;
	}

	public void setRight(BinaryNode<D> r)
	{
		right = r;
	}

	public void setData(D d)
	{
		data = d;
	}

	public BinaryNode<D> getLeft()
	{
		return left;
	}

	public BinaryNode<D> getRight()
	{
		return right;
	}

	public D getData()
	{
		return data;
	}
}
