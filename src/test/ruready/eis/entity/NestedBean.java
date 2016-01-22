/*******************************************************
 * Source File: NestedBean.java
 *******************************************************/
package test.ruready.eis.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jan 30, 2007 Used in monkey tree testing of Struts nested tags.
 */
public class NestedBean
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	private List<String> itemList;

	private Set<String> itemSet;

	private Map<Integer, String> itemMap;

	private Map<Integer, NestedBean> children;

	// ========================= CONSTRUCTORS ==============================

	public NestedBean()
	{
		itemList = new ArrayList<String>();
		itemList.add("aaa");
		itemList.add("ccc");
		itemList.add("bbb");

		itemSet = new HashSet<String>();
		itemSet.add("aaa");
		itemSet.add("ccc");
		itemSet.add("bbb");

		itemMap = new HashMap<Integer, String>();
		itemMap.put(2, "aaa");
		itemMap.put(3, "bbb");
		itemMap.put(10, "ccc");
		itemMap.put(4, "dddd");

		// itemMapMap = new HashMap<Integer, Map<Integer, String>>();
		// itemMapMap.put(23, itemMap);
	}

	// ========================= METHODS ===================================

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the itemList
	 */
	public List<String> getItemList()
	{
		return itemList;
	}

	/**
	 * @return the itemMap
	 */
	public Map<Integer, String> getItemMap()
	{
		return itemMap;
	}

	/**
	 * @param itemList
	 *            the itemList to set
	 */
	public void setItemList(List<String> itemList)
	{
		this.itemList = itemList;
	}

	/**
	 * @param itemMap
	 *            the itemMap to set
	 */
	public void setItemMap(Map<Integer, String> itemMap)
	{
		this.itemMap = itemMap;
	}

	/**
	 * @return the itemSet
	 */
	public Set<String> getItemSet()
	{
		return itemSet;
	}

	/**
	 * @param itemSet
	 *            the itemSet to set
	 */
	public void setItemSet(Set<String> itemSet)
	{
		this.itemSet = itemSet;
	}

	/**
	 * @return the children
	 */
	public Map<Integer, NestedBean> getChildren()
	{
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(Map<Integer, NestedBean> children)
	{
		this.children = children;
	}

}
