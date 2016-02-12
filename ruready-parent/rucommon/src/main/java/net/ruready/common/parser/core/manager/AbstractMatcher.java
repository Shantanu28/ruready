/*******************************************************************************
 * Source File: AbstractMatcher.java
 ******************************************************************************/
package net.ruready.common.parser.core.manager;

/**
 * An object that matches a string using a parser and translates it into a
 * target.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Dec 23, 2006
 */
public interface AbstractMatcher
{
	// ========================= ABSTRACT METHODS ===========================

	/**
	 * Parse (match) a string. The results are stored in a target object that
	 * may be returned by a sub-class of this interface.
	 * 
	 * @param s
	 *            the string to parse.
	 */
	void match(String s);
}
