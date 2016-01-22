/*****************************************************************************************
 * Source File: Counter.java
 ****************************************************************************************/
package net.ruready.business.user.entity;

import javax.persistence.Entity;

import net.ruready.common.rl.CommonNames;

/**
 * A hit/user counter. Can be initialized to a specific value, incremented, and
 * cleared.
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
 * @version Aug 11, 2007
 */
@Entity
public class Counter extends GlobalProperty
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * The counter.
	 */
	private long value = 0;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a counter whose value is zero. Must be public because
	 * <code>Counter</code> is a JavaBean.
	 */
	protected Counter()
	{

	}

	/**
	 * Construct a counter with zero initial value.
	 * 
	 * @param name
	 *            counter unique identifier
	 */

	public Counter(String name)
	{
		super(name);
	}

	/**
	 * Construct a counter with given initial value.
	 * 
	 * @param name
	 *            counter unique identifier
	 * @param value
	 *            is the initial value of the counter
	 */

	public Counter(String name, long value)
	{
		super(name);
		this.value = value;
	}

	// ========================= METHODS ===================================

	/**
	 * Zeros the counter so getValue() == 0.
	 */
	public void clear()
	{
		value = 0;
	}

	/**
	 * Increase the value of the counter by one.
	 */
	public void increment()
	{
		value++;
	}

	/**
	 * Decrease the value of the counter by one.
	 */
	public void decrement()
	{
		value--;
	}

	/**
	 * Return a string representing the value of this counter.
	 * 
	 * @return a string representation of the value
	 */
	@Override
	public String toString()
	{
		return CommonNames.MISC.EMPTY_STRING + value;
	}

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * Returns the value of the counter.
	 * 
	 * @return the value of the counter
	 */
	public long getValue()
	{
		return value;
	}
}
