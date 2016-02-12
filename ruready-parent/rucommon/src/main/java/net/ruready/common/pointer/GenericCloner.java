/*****************************************************************************************
 * Source File: GenericCloner.java
 ****************************************************************************************/
/**
 * File: GenericCloner.java
 */
package net.ruready.common.pointer;

import net.ruready.common.exception.SystemException;
import net.ruready.common.misc.Utility;
import net.ruready.common.tree.AbstractListTreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A factory that clones tree data types. Because {@link AbstractListTreeNode} is
 * parameterized by the data type <code>E</code>, we need to know how to clone an
 * <code>E</code> instance inside the tree's <code>clone</code> method. Unfortunately,
 * this uses a lot of <code>instanceof()</code> calls, but on the other hand provides an
 * abstraction of primitive immutable types like {@link String} as well as
 * {@link PubliclyCloneable} data types.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 31, 2007
 */
public class GenericCloner implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(GenericCloner.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private GenericCloner()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Clone an object.
	 * 
	 * @param obj
	 *            object to clone
	 * @return a clone of this object
	 * @throws SystemException
	 *             if the object is not primitive or not <code>PubliclyCloneable</code>
	 * @see Object#clone()
	 */
	public static Object clone(final Object obj)
	{
		if (obj == null)
		{
			// The clone of null is null
			return null;
		}

		// look if obj is a primitive type
		if (obj instanceof String)
		{
			return new String((String) obj);
		}
		else if (obj instanceof Integer)
		{
			return new Integer((Integer) obj);
		}
		else if (obj instanceof Double)
		{
			return new Double((Double) obj);
		}
		else if (obj instanceof Boolean)
		{
			return new Boolean((Boolean) obj);
		}
		else if (obj instanceof Long)
		{
			return new Long((Long) obj);
		}
		else if (obj instanceof Byte)
		{
			return new Byte((Byte) obj);
		}
		else if (obj instanceof Character)
		{
			return new Character((Character) obj);
		}
		else if (obj instanceof Short)
		{
			return new Short((Short) obj);
		}
		else if (obj instanceof Float)
		{
			return new Float((Float) obj);
		}
		else if (obj instanceof PubliclyCloneable)
		{
			// Use the clone method
			return ((PubliclyCloneable) obj).clone();
		}
		else
		{
			throw new SystemException("Cannot clone object of type "
					+ obj.getClass().getCanonicalName());
		}
	}

	/**
	 * Merge an object into another object.
	 * 
	 * @param source
	 *            source object
	 * @param destination
	 *            destination object that the source object is merged into. If source is a
	 *            primitive or immutable type, use <code>destination = null</code>.
	 * @return the destination object
	 * @throws SystemException
	 *             if the object is not primitive or not <code>ShallowCloneable</code>
	 * @see ShallowCloneable#mergeInto()
	 */
	public static Object mergeInto(final Object source, final Object destination)
	{
		if (source == null)
		{
			// The clone of null is null
			return null;
		}

		// look if obj is a primitive type
		if (source instanceof String)
		{
			return new String((String) source);
		}
		else if (source instanceof Integer)
		{
			return new Integer((Integer) source);
		}
		else if (source instanceof Double)
		{
			return new Double((Double) source);
		}
		else if (source instanceof Boolean)
		{
			return new Boolean((Boolean) source);
		}
		else if (source instanceof Long)
		{
			return new Long((Long) source);
		}
		else if (source instanceof Byte)
		{
			return new Byte((Byte) source);
		}
		else if (source instanceof Character)
		{
			return new Character((Character) source);
		}
		else if (source instanceof Short)
		{
			return new Short((Short) source);
		}
		else if (source instanceof Float)
		{
			return new Float((Float) source);
		}
		else if ((source instanceof ShallowCloneable)
				&& (destination instanceof ShallowCloneable))
		{
			((ShallowCloneable) source).mergeInto((ShallowCloneable) destination);
			return destination;
		}
		else
		{
			throw new SystemException("Cannot clone object of type "
					+ source.getClass().getCanonicalName());
		}
	}
}
