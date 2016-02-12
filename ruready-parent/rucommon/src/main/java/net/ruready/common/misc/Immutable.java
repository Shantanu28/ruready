/*****************************************************************************************
 * Source File: Immutable.java
 ****************************************************************************************/
package net.ruready.common.misc;

/**
 * A naming interface for immutable classes.<br>
 * Immutable objects are simply objects whose state (the object's data) cannot change
 * after construction.
 * <p>
 * Immutable objects greatly simplify your program, since they
 * <ul>
 * <li> are simple to construct, test, and use</li>
 * <li> are automatically <a href="Topic48.cjp">thread-safe</a> and have no
 * synchronization issues</li>
 * <li> do not need a <a href="Topic12.cjp">copy constructor</a></li>
 * <li> do not need an implementation of <tt><a href="Topic71.cjp">clone</a></tt></li>
 * <li> allow <tt><a href="Topic28.cjp">hashCode</a></tt> to use <a
 * href="Topic34.cjp">lazy initialization</a>, and to cache its return value</li>
 * <li> do not need to be <a href="Topic15.cjp">copied defensively</a> when used as a
 * field</li>
 * <li> make good <tt>Map</tt> keys and <tt>Set</tt> elements (these objects must not
 * change state while in the collection)</li>
 * <li> the <a href="Topic6.cjp">class invariant</a> is established once upon
 * construction, and never needs to be checked again</li>
 * </ul>
 * Make a class immutable by following these guidelines :
 * <ul>
 * <li> always construct an object completely, instead of using a no-argument constructor
 * combined with subsequent calls to <tt>setXXX</tt> methods (that is, <a
 * href="Topic84.cjp">avoid the Java Beans convention</a>)</li>
 * <li> do not provide any methods which can change the state of the object in any way -
 * not just <tt>setXXX</tt> methods, but any method which can change state</li>
 * <li> ensure no methods can be overridden - make the class <tt>final</tt>, or use
 * static factories and keep constructors private</li>
 * <li> make fields <tt>final</tt></li>
 * <li> if the state of a mutable object field is "owned" by the native class, and the
 * intention is to allow <i>direct</i> access to the field only from within that native
 * class, then, when the field "crosses the border" (as in a <tt>get</tt> or
 * <tt>set</tt> method, or in the constructor itself), then a <a
 * href="Topic15.cjp">defensive copy</a> <i>must</i> be made, in order to maintain
 * encapsulation.</li>
 * <li> if the state of a mutable object field is not "owned" by the native class, then a
 * defensive copy of the object field is not necessary</li>
 * </ul>
 * In <i><a
 * href="http://www.amazon.com/exec/obidos/ASIN/0201310058/ref=nosim/javapractices-20">Effective
 * Java</a></i>, Joshua Bloch makes this recommendation:
 * <p>
 * <b>"Classes should be immutable unless there's a very good reason to make them
 * mutable....If a class cannot be made immutable, you should still limit its mutability
 * as much as possible."</b>
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
 * @version Jul 21, 2007
 * @see http://www.javapractices.com/Topic29.cjp
 */
public interface Immutable
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================
}
