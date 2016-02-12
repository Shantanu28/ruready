/*******************************************************************************
 * Source File: BinaryTree.java
 ******************************************************************************/
package net.ruready.common.tree.binary;

import java.io.Serializable;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A binary tree. Based on: Java Data Structures (2nd edition) (c) 1996-2001,
 * Particle (particle@theparticle.com
 * http://www.javacommerce.com/displaypage.jsp?name=javadata2.sql&id=18214#Trees
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 14, 2007
 */
public class BinaryTree<D extends Serializable & Comparable<? super D>>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(BinaryTree.class);

	// ========================= FIELDS ====================================

	private BinaryNode<D> root;

	// ========================= CONSTRUCTORS ==============================

	public BinaryTree()
	{
		setRoot(null);
	}

	public BinaryTree(D o)
	{
		setRoot(new BinaryNode<D>(o));
	}

	public BinaryTree(BinaryNode<D> n)
	{
		setRoot(n);
	}

	/**
	 * Return a copy of this object.
	 * 
	 * @return a copy of this object
	 */
	public BinaryTree(BinaryTree<D> other)
	{
		BinaryTree<D> copy = copySubTree(other.getRoot());
		setRoot(copy.getRoot());
	}

	// ========================= METHODS ===================================

	public boolean isEmpty()
	{
		return getRoot() == null;
	}

	public void insertLeft(BinaryNode<D> p, D o)
	{
		if ((p != null) && (p.getLeft() == null))
			p.setLeft(new BinaryNode<D>(o));
	}

	public void insertRight(BinaryNode<D> p, D o)
	{
		if ((p != null) && (p.getRight() == null))
			p.setRight(new BinaryNode<D>(o));
	}

	public void insertLeft(BinaryNode<D> p, BinaryNode<D> n)
	{
		if ((p != null) && (p.getLeft() == null))
			p.setLeft(n);
	}

	public void insertRight(BinaryNode<D> p, BinaryNode<D> n)
	{
		if ((p != null) && (p.getRight() == null))
			p.setRight(n);
	}

	protected BinaryTree<D> copySubTree(BinaryNode<D> p)
	{
		BinaryTree<D> t = new BinaryTree<D>();
		if (p == null)
		{
			return t;
		}
		t.setRoot(new BinaryNode<D>(p.getData()));
		t.getRoot().setLeft(copySubTree(p.getLeft()).getRoot());
		t.getRoot().setRight(copySubTree(p.getRight()).getRoot());
		return t;
	}

	public void print(int mode)
	{
		if (mode == 1)
			pretrav();
		else if (mode == 2)
			intrav();
		else if (mode == 3)
			postrav();
	}

	public void pretrav()
	{
		pretrav(getRoot());
	}

	protected void pretrav(BinaryNode<D> p)
	{
		if (p == null)
			return;
		logger.debug(p.getData() + " ");
		pretrav(p.getLeft());
		pretrav(p.getRight());
	}

	public void intrav()
	{
		intrav(getRoot());
	}

	protected void intrav(BinaryNode<D> p)
	{
		if (p == null)
			return;
		intrav(p.getLeft());
		logger.debug(p.getData() + " ");
		intrav(p.getRight());
	}

	public void postrav()
	{
		postrav(getRoot());
	}

	protected void postrav(BinaryNode<D> p)
	{
		if (p == null)
			return;
		postrav(p.getLeft());
		postrav(p.getRight());
		logger.debug(p.getData() + " ");
	}

	protected String pretravBuffer(BinaryNode<D> p)
	{
		if (p == null)
		{
			return CommonNames.MISC.EMPTY_STRING;
		}
		return "(" + p.getData() + " " + pretravBuffer(p.getLeft())
				+ pretravBuffer(p.getRight()) + " )";
	}

	@Override
	public String toString()
	{
		return pretravBuffer(getRoot());
	}

	// ========================= GETTERS & SETTERS =========================

	public BinaryNode<D> getRoot()
	{
		return root;
	}

	public void setRoot(BinaryNode<D> r)
	{
		root = r;
	}

	public D getData()
	{
		if (!isEmpty())
		{
			return getRoot().getData();
		}
		return null;
	}

	public BinaryNode<D> getLeft()
	{
		if (!isEmpty())
		{
			return getRoot().getLeft();
		}
		return null;
	}

	public BinaryNode<D> getRight()
	{
		if (!isEmpty())
		{
			return getRoot().getRight();
		}
		return null;
	}

	public void setData(D o)
	{
		if (!isEmpty())
		{
			getRoot().setData(o);
		}
	}

}
