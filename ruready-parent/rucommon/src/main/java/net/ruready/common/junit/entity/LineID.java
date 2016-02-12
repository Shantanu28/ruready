/*******************************************************************************
 * Source File: LineID.java
 ******************************************************************************/
package net.ruready.common.junit.entity;

import net.ruready.common.parser.core.assembler.AssemblerIdentifier;

/**
 * Types of lines read from a test data file.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jan 31, 2007
 */
public enum LineID implements AssemblerIdentifier
{
	// ========================= ENUMERATED TYPES ==========================

	/**
	 * An empty (possibly commented out) line.
	 */
	EMPTY
	{

	},

	/**
	 * A data line.
	 */
	DATA
	{

	},

	/**
	 * A line containing a command to set a parameter value in the target.
	 */
	PARAM_SET
	{

	};

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped
	 * by Hibernate to return unexpected names. As a rule, the type string
	 * should not contain spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= METHODS ===================================

}
