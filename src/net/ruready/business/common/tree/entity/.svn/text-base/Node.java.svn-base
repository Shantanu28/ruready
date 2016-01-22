/*****************************************************************************************
 * Source File: Node.java
 ****************************************************************************************/
package net.ruready.business.common.tree.entity;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.ruready.business.common.tree.comparator.ChildComparator;
import net.ruready.business.common.tree.comparator.TreeNodeComparator;
import net.ruready.business.common.tree.comparator.TreeNodeComparatorID;
import net.ruready.business.common.tree.util.NodeUtil;
import net.ruready.common.audit.Versioned;
import net.ruready.common.eis.entity.PersistentEntity;
import net.ruready.common.exception.UnsupportedOpException;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.tree.AbstractListTreeNode;
import net.ruready.common.tree.AbstractSetTreeNode;
import net.ruready.common.tree.Printer;
import net.ruready.common.tree.TraversalOrder;
import net.ruready.common.tree.TreeNodeCounter;
import net.ruready.common.tree.TreeNodeTraverser;
import net.ruready.common.util.HashCodeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * A tree node implementation that is a persistent entity of a database (EIS)
 * layer. Supports a general number of children. This is a generic class
 * parameterized by the children's type. The first parameter for the generic
 * AbstractListTreeNode interface refers to the name property. We tried to use a
 * composite, embedded data, but that requires vastly changing the content
 * component, so we gave it up.
 * <p>
 * Important note: do not use the annotation
 * <code>at-symbol-Inheritance(strategy = InheritanceType.JOINED)</code>
 * because we are expecting a large number of sub-classes and there is a limit
 * of 61 tables on a MySQL 5 join. We actually reached this limit on August, 29
 * 2007 when looking for a node's parent and super-parent. A more efficient
 * approach is:
 * <code>at-symbol-Inheritance(strategy = InheritanceType.SINGLE_TABLE)</code>
 * and NO NON-NULLABLE columns because all sub-classes share the same table.
 * Make the web-layer responsible for validations.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 31, 2007
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "itemType")
public class Node implements AbstractSetTreeNode<String, Node>, PersistentEntity<Long>,
		Versioned<Integer>, Commentable
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Node.class);

	/**
	 * Seed for version numbers.
	 */
	public static final int VERSION_SEED = -1;

	/**
	 * Children's serial #s are 1-based.
	 */
	public static final int SERIAL_NO_BASE = 1;

	/**
	 * Identifiers of useful tree node properties: identifier.
	 */
	public static final String ID = "id";

	/**
	 * Identifiers of useful tree node properties: node name.
	 */
	public static final String NAME = "name";

	// ========================= PERSISTENT FIELDS =========================

	/**
	 * The unique identifier of this entity.
	 */
	@Id
	@GeneratedValue
	protected Long id;

	/**
	 * Short name of this node.
	 */
	@Column(length = 50)
	private String name;

	/**
	 * An optional comment.
	 */
	@Column(length = 100)
	private String comment;

	/**
	 * Depth of this node in a super-tree.
	 */
	@Column
	private int depth = 0;

	/**
	 * Allows custom children ordering. This is the number of this node in its
	 * parent's children list if this type of custom ordering is enabled.
	 */
	@Cascade(CascadeType.ALL)
	private Integer serialNo;

	/**
	 * The parent node of this node.
	 */
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Node parent;

	/**
	 * Type of comparator according to which children {@link Node}s are sorted.
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private TreeNodeComparatorID comparatorType;

	/**
	 * List of children {@link Node}s. We don't want to save the entire tree
	 * every time this node is saved, so cascading is only specified on removing
	 * the node.
	 * <p>
	 * NOTE: DON'T USE LazyCollection(value = LazyCollectionOption.EXTRA)
	 * ANNOTATION BECAUSE IT DOES NOT WORK WELL.
	 */
	@OneToMany(mappedBy = "parent")
	@Cascade(CascadeType.REMOVE)
	@Sort(type = SortType.COMPARATOR, comparator = ChildComparator.class)
	private SortedSet<Node> children = new TreeSet<Node>();

	/**
	 * Version Control: Current database version number of this object.
	 */
	@Version
	private Integer version = VERSION_SEED;

	/**
	 * Version Control: Current version number of the local copy of the database
	 * entity.
	 */
	@Transient
	private Integer localVersion;

	// ========================= TRANSIENT FIELDS ==========================

	/**
	 * Comparator according to which children {@link Node}s are sorted. Helps
	 * cache the comparator instance instead of instantiating it every time the
	 * {@link Comparable#compareTo} method is called on a pair of nodes.
	 */
	@Transient
	private TreeNodeComparator comparator;

	/**
	 * Printer/formatter.
	 */
	@Transient
	private Printer<Node> printer;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct an empty tree node. A no-argument constructor with at least
	 * protected scope visibility is required for Hibernate and portability to
	 * other EJB 3 containers. This constructor populates no properties inside
	 * this class.
	 */
	protected Node()
	{
		setUp();
	}

	/**
	 * Construct a node with a name and no comment.
	 * 
	 * @param name
	 *            node's name
	 */
	public Node(String name)
	{
		setName(name);
		setUp();
	}

	/**
	 * Construct a node from basic data.
	 * 
	 * @param name
	 *            node's name
	 * @param comment
	 *            an optional cmoment descibing the node
	 */
	public Node(String name, String comment)
	{
		setName(name);
		setComment(comment);
		setUp();
	}

	// ========================= IMPLEMENTATION: AbstractListTreeNode ==========

	/**
	 * Print the data and other properties of this node.
	 * 
	 * @return data and properties of this node, represented as a string
	 */
	public String printData()
	{
		return "[" + getClass().getSimpleName() + " ID " + id + "]" + " " + name
				+ ((serialNo != null) ? (" #" + serialNo) : "");
	}

	/**
	 * @return parent node of this node
	 */
	public Node getParent()
	{
		return parent;
	}

	/**
	 * Returns the [grand-...-grand-] parent node of this node.
	 * 
	 * @param height
	 *            number of tree levels to go up.
	 *            {@link AbstractListTreeNode#getParent()} means
	 *            <code>height = 1</code> (the direct parent), and so on. A
	 *            non-positive <code>height</code> will return this node.
	 * @return [grand-...-grand-] parent node of this node
	 * @see net.ruready.common.tree.AbstractListTreeNode#getSuperParent(int)
	 */
	public Node getSuperParent(final int height)
	{
		Node node = this;
		for (int i = 1; i <= height; i++)
		{
			if (node == null)
			{
				return null;
			}
			node = node.parent;
		}
		return node;
	}

	/**
	 * Return the list of siblings of this node. This will return
	 * <code>null</code> if the this node's parent field is <code>null</code>.
	 * 
	 * @return list of siblings of this node
	 * @see net.ruready.common.tree.AbstractListTreeNode#getSiblings()
	 */
	public SortedSet<Node> getSiblings()
	{
		return (parent == null) ? null : parent.getChildren();
	}

	/**
	 * @return the printer
	 */
	public Printer<Node> getPrinter()
	{
		return printer;
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#setPrinter(net.ruready.common.tree.Printer)
	 */
	public void setPrinter(Printer<Node> printer)
	{
		this.printer = printer;
	}

	/**
	 * @return the children
	 */
	public SortedSet<Node> getChildren()
	{
		return children;
	}

	/**
	 * Add a child to the children list.
	 * 
	 * @param child
	 *            The child to be added.
	 */
	public void addChild(Node child)
	{
		child.setParent(this);
		getChildren().add(child);
		refresh();
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#getData()
	 */
	public String getData()
	{
		return getName();
	}

	/**
	 * @param oldChild
	 * @param newChild
	 * @see net.ruready.common.tree.MutableTreeNode#replaceChild(net.ruready.common.tree.MutableTreeNode,
	 *      net.ruready.common.tree.MutableTreeNode)
	 */
	public void replaceChild(Node oldChild, Node newChild)
	{
		throw new UnsupportedOpException("replaceChild(): Not yet implemented");
	}

	/**
	 * Remove a child node under this node; all grandchildren (children of this
	 * child) are added under this node, at position <code>indexOf(child)</code>.
	 * 
	 * @param child
	 *            the child to remove
	 * @see net.ruready.common.tree.AbstractListTreeNode#removeChildTree(net.ruready.common.tree.AbstractListTreeNode)
	 */
	public void removeChildTree(Node child)
	{
		throw new UnsupportedOpException(
				"Only list tree nodes currently support this operation");
	}

	/**
	 * @see net.ruready.common.tree.AbstractListTreeNode#setData(java.lang.Comparable)
	 */
	public void setData(String data)
	{
		setName(data);
	}

	/**
	 * Set the parent of this node to <code>null</code>.
	 * <p>
	 * WARNING: use this method only if you know that there is indeed no parent.
	 * Otherwise, a parent still might have a child reference to this node
	 * whereas this node doesn't point to it any longer.
	 */
	public void removeParentReference()
	{
		parent = null;
	}

	/**
	 * Remove a child from the children list.
	 * 
	 * @param child
	 *            The child to be removed.
	 */
	public void removeChild(Node child)
	{
		// int i = 0;
		// if (logger.isDebugEnabled())
		// {
		// logger.debug("Child to remove: hashCode " + child.hashCode());
		// }
		// for (Node c : getChildren())
		// {
		// if (logger.isDebugEnabled())
		// {
		// logger.debug("Child #" + i + " hashCode " + c.hashCode()
		// + " equals child ? " + child.equals(c) + " " + c.equals(child)
		// + " compare? " + new ChildComparator().compare(child, c));
		// }
		// i++;
		// }
		// child.setParent(null);
		getChildren().remove(child);
		child.parent = null;
		refresh();
	}

	/**
	 * Add all children in a list to this tree. This operation is delegated to
	 * the specific implementation of <code>children</code>.
	 * 
	 * @param newChildren
	 *            collection to be added
	 */
	public void addChilds(Collection<Node> newChildren)
	{
		getChildren().addAll(newChildren);
		for (Node child : newChildren)
		{
			child.setParent(this);
		}
		refresh();
	}

	/**
	 * Remove all children from this tree. This operation is delegated to the
	 * specific implementation of <code>children</code>.
	 */
	public void removeAllChilds()
	{
		for (Node child : getChildren())
		{
			// child.setParent(null);
			child.parent = null;
		}
		children = new TreeSet<Node>();
	}

	/**
	 * Returns the size of the tree, which is the total number of nodes in the
	 * tree.
	 * 
	 * @return the total number of nodes in the tree
	 */
	public int getSize()
	{
		return TreeNodeCounter.countNodes(this);
	}

	/**
	 * Returns the number of children (branches of this tree node).
	 * 
	 * @return Returns the number of children.
	 */
	public int getNumChildren()
	{
		return children.size();
	}

	/**
	 * Returns <code>true</code> if and only if the number of children is
	 * positive.
	 * 
	 * @return <code>true</code> if and only if this node has children
	 */
	public boolean hasChildren()
	{
		return children.size() > 0;
	}

	/**
	 * Remove this node from its parent's cghildren list. This should be done
	 * before deleting a node from the database.
	 * 
	 * @param comparator
	 *            the comparator to set
	 */
	public void removeFromParent()
	{
		// Touch the parent
		getParent();
		if (parent != null)
		{
			parent.removeChild(this);
		}
	}

	/**
	 * Return the list of nodes in the syntax tree.
	 * 
	 * @param traversalOrder
	 *            node traversal order (e.g. pre/post)
	 * @return list of tree nodes
	 */
	public List<Node> toNodeList(final TraversalOrder traversalOrder)
	{
		return new TreeNodeTraverser<String, Node>(traversalOrder).traverse(this);
	}

	// ========================= IMPLEMENTATION: ShallowCloneable ==========

	/**
	 * A shallow copy of this object, as opposed to {@link #clone()}, which is
	 * a deep copy.
	 * 
	 * @return a shallow copy of the receiving object
	 */
	public Node shallowClone()
	{
		try
		{
			Node copy = (Node) super.clone();

			// Custom field setting
			copy.setUp();
			copy.removeIdentity();
			copy.getParent();
			copy.parent = null;
			copy.setSerialNo(null);

			return copy;
		}
		catch (CloneNotSupportedException e)
		{
			// This shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
	}

	/**
	 * Does not copy all fields over - only data fields of this node. Children
	 * are not copied. Identity is not copied.
	 * 
	 * @param destination
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	public void mergeInto(final ShallowCloneable destination)
	{
		Node dest = (Node) destination;
		dest.name = name;
		dest.comment = comment;
		dest.depth = depth;
		// dest.serialNo = serialNo; // Will have messed dest.parent children
		// ordering
		dest.setComparatorType(comparatorType);
		// dest.setPrinter(printer); // Need a printer factory to do this right
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to
	 * serve as a target (or part of a target) of an assembly.
	 * 
	 * @return a deep copy of this object.
	 */
	@Override
	public final Node clone()
	{
		// Copy root node data
		Node copy = this.shallowClone();

		// Copy child trees (recursive calls)
		for (Node child : children)
		{
			copy.addChild(child.clone());
		}

		return copy;
	}

	// ========================= METHODS ===================================

	/**
	 * Move a child to a new index in the item's children array. If the children
	 * list index corresponding to <code>moveFromSN</code> is outside the
	 * array, this method does nothing. If the index corresponding to
	 * <code>moveToSN</code> is negative, it is regarded as <code>0</code>.
	 * If it is greater than <code>children.size-1</code>, it is treated like
	 * <code>children.size-1</code>.
	 * 
	 * @param moveFromSN
	 *            original serial number of child.
	 *            <code>Node.SERIAL_NO_BASE</code>-based.
	 * @param moveToSN
	 *            new serial number of child. <code>Node.SERIAL_NO_BASE</code>-based.
	 */
	public void move(final int moveFromSN, final int moveToSN)
	{
		// Convert to list indices
		int moveFrom = moveFromSN - Node.SERIAL_NO_BASE;
		int moveTo = moveToSN - Node.SERIAL_NO_BASE;

		if ((moveFrom < 0) || (moveFrom >= children.size()))
		{
			// Invalid index, ignore
			return;
		}

		// Bound the moveTo parameter
		int size = children.size();
		if (moveTo < 0)
		{
			moveTo = 0;
		}
		if (moveTo >= size)
		{
			moveTo = size - 1;
		}

		// Original and new indices are the same, do nothing
		if (moveTo == moveFrom)
		{
			return;
		}

		// =======================
		// Move the child
		// =======================
		// Find the child
		Node childToMove = null;
		for (Node child : children)
		{
			if (child.serialNo == moveFromSN)
			{
				childToMove = child;
				break;
			}
		}
		if (childToMove == null)
		{
			logger.warn("Didn't find child to move");
			return;
		}

		// Move the child from old to new position (update serial # only)
		childToMove.setSerialNo(moveToSN);

		// Update serial #s of all children except for the moved child so
		// that they are consecutive
		int increment = 0;
		int i = 0;
		for (@SuppressWarnings("unused")
		Node child : children)
		{
			if (child == childToMove)
			{
				continue;
			}
			if (i == moveTo)
			{
				increment++;
			}
			child.serialNo = i + Node.SERIAL_NO_BASE + increment;
			i++;
		}

		// Re-sort children
		refresh();
	}

	/**
	 * Refresh this tree node. This re-sorts children.
	 */
	private void refresh()
	{
		if (comparator != null)
		{
			// Sort children
			SortedSet<Node> newChildren = new TreeSet<Node>(comparator);
			// I've had some problems with addAll(). Instead, add the children
			// one-by-one.
			// newChildren.addAll(children);
			for (Node child : children)
			{
				newChildren.add(child);
			}
			this.children = newChildren;

			// Sorting post-processing
			comparator.refresh(this);
		}
	}

	/**
	 * Refresh all nodes in this tree. This re-sorts children and synchronizes
	 * their depth fields with the root node.
	 */
	@Deprecated
	public void refreshAll()
	{
		ApplyRefresh.refresh(this);
	}

	/**
	 * Set a new comparator for all nodes in this tree to define a new children
	 * ordering and refresh the node. Setting the comparator to
	 * <code>null</code> will keep the current children ordering.
	 * 
	 * @param comparatorType
	 *            the comparator type to set
	 */
	public void setComparatorAll(final TreeNodeComparatorID comparatorType)
	{
		ApplyComparator.applyComparator(this, comparatorType);
	}

	/**
	 * Remove unique identifier and parent identification. This is useful on a
	 * persistent entity so that we can copy it over under a different parent.
	 * This method sets <code>id = null</code> and
	 * <code>version = VERSION_SEED</code>.
	 * 
	 * @param child
	 *            The child to be removed.
	 */
	public void removeIdentity()
	{
		setId(null);
		version = VERSION_SEED;
	}

	/**
	 * Return the child of this item with a specified ID.
	 * 
	 * @param childId
	 *            unique identifier of a child
	 * @return the corresponding child if found, or null if not found
	 */
	public Node findChildById(final long childId)
	{
		for (Node child : getChildren())
		{
			if (child.getId() == childId)
			{
				return child;
			}
		}
		return null;
	}

	/**
	 * Return the child of this item using the comparator.
	 * 
	 * @param example
	 *            example child to look for. If a child and example have a
	 *            comparison result of 0 using the node's children comparator,
	 *            this child is returned
	 * @return the corresponding child if found, or <code>null</code> if not
	 *         found
	 */
	public Node findChild(final Node example)
	{
		return NodeUtil.findElementByExample(children, example);
	}

	/**
	 * Find an item within a this node's children list. If an item with this
	 * name already exists among the children, return that child. Otherwise, add
	 * the item to the children list and return it.
	 * 
	 * @param example
	 *            child example to look for
	 * @return existing child or this item
	 */
	@SuppressWarnings("unchecked")
	public <T extends Node> T findOrAddItem(final T example)
	{
		T child = (T) this.findChild(example);
		if (child == null)
		{
			this.addChild(example);
			return example;
		}
		else
		{
			return child;
		}
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print a tree in pre-traversal order.
	 * 
	 * @return a string with this tree in pre-traversal order.
	 */
	@Override
	public String toString()
	{
		return printer.toString();
	}

	/**
	 * Objects are equal if they are non-null and have equal hash codes.
	 * 
	 * @param obj
	 *            other object
	 * @return result of equality:
	 *         <code>(obj == null) ? false : (hashCode() == obj.hashCode())</code>
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		return (obj == null) ? false : (hashCode() == obj.hashCode());
	}

	/**
	 * The hash code is computed based on the entity's {@link #id} field only.
	 * 
	 * @return hash code of the {@link #id} field
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, id);
		return result;
	}

	// ========================= IMPLEMENTATION: PersistentEntity<Long> =====

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	// /**
	// * Create and return an association manager for this entity type.
	// *
	// * @return an association manager for this entity type.
	// * @see
	// net.ruready.common.eis.entity.PersistentEntity#createAssociationManager()
	// */
	// public AssociationManager createAssociationManager()
	// {
	// return null;
	// }

	// ========================= IMPLEMENTATION: Versioned ==================

	/**
	 * @return the database version
	 * @see net.ruready.common.audit.Versioned#getVersion()
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * @return the local version
	 * @see net.ruready.common.audit.Versioned#getLocalVersion()
	 */
	public Integer getLocalVersion()
	{
		return localVersion;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * Sets a new id property value.
	 * 
	 * @param id
	 *            the id to set
	 */
	private void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the depth
	 */
	public int getDepth()
	{
		return depth;
	}

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment)
	{
		// Trim spaces
		this.comment = (comment == null) ? null : comment.trim();
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		// Trim spaces
		this.name = (name == null) ? null : name.trim();
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(int depth)
	{
		this.depth = depth;
	}

	/**
	 * Set the parent node of this node. The depth of this node is set to the
	 * parent's depth plus one. Depth updates are not cascaded to the children
	 * of this node; use refreshAll() to achieve that.<br>
	 * To keep the integrity of parent-child bi-directional references, child
	 * cannot stay under both the old parent (this.parent) and new parent
	 * (parent). Clone it and put the clone under the old parent, and move it
	 * under the new parent.
	 * 
	 * @param parent
	 *            parent node of this node to set
	 */
	private void setParent(final Node newParent)
	{
		if ((parent != null) && (parent != newParent))
		{
			// To keep the integrity of parent-child bi-directional references,
			// a child cannot stay under both the old parent (parent) and new
			// parent
			// (newParent). Clone this node and put the clone under the old
			// parent, then
			// move this node under the new parent.
			Node copy = this.clone();
			copy.parent = parent;
			// this.removeFromParent();
			parent.getChildren().remove(this);
			parent.getChildren().add(copy);
		}

		parent = newParent;
		// Set the depth of this node only.
		depth = ((parent == null) ? 0 : (parent.getDepth() + 1));
	}

	/**
	 * Returns the comparator type.
	 * 
	 * @return the comparator type
	 */
	public TreeNodeComparatorID getComparatorType()
	{
		return comparatorType;
	}

	/**
	 * Set a new comparator to define a new children ordering and refresh the
	 * node. Setting the comparator to <code>null</code> will keep the current
	 * children ordering.
	 * 
	 * @param comparatorType
	 *            the comparator type to set
	 * @param doRefresh
	 *            refresh tree if and only if this flag is <code>true</code>
	 */
	public void setComparatorType(final TreeNodeComparatorID comparatorType)
	{
		this.comparatorType = comparatorType;
		this.comparator = comparatorType.createComparator();
		refresh();
	}

	/**
	 * Returns the cached comparator instance.
	 * 
	 * @return the comparator instance
	 */
	public TreeNodeComparator getComparator()
	{
		return comparator;
	}

	/**
	 * @return the serialNo
	 */
	public Integer getSerialNo()
	{
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(final Integer serialNo)
	{
		this.serialNo = serialNo;
	}

	/**
	 * Sets a new localVersion property value. Note that we do not provide
	 * similar access to the stored version number.
	 * 
	 * @param localVersion
	 *            the localVersion to set
	 */
	public void setLocalVersion(final Integer localVersion)
	{
		// logger.debug("ID " + id + ": local version set to " + localVersion);
		this.localVersion = localVersion;
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Perform initializations at the end of each constructor: initialize the
	 * data structure holding the collection of children, set a default printer,
	 * etc. This is a template method.
	 */
	protected void setUp()
	{
		// Initialize children
		children = new TreeSet<Node>();

		// Default printout style
		setPrinter(new NodeTraversalPrinter(this));

		// Default children ordering
		setComparatorType(TreeNodeComparatorID.NAME);
	}

}
