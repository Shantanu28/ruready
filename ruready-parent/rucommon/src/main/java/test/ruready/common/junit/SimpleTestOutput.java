/*****************************************************************************************
 * Source File: AbsoluteCanonicalizationStepTestOutput.java
 ****************************************************************************************/
package test.ruready.common.junit;

import net.ruready.common.junit.entity.TestOutput;
import net.ruready.common.misc.Auxiliary;

/**
 * A simple base class for test outputs.
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
 * @version Sep 8, 2007
 */
class SimpleTestOutput implements TestOutput, Auxiliary
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	// Canonicalized tree's string representation
	private final String expression;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a test output (results) container from fields.
	 * 
	 * @param editDistance
	 * @param nodalMappingString
	 */
	public SimpleTestOutput(final String expression)
	{
		super();
		this.expression = expression;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((expression == null) ? 0 : expression.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SimpleTestOutput other = (SimpleTestOutput) obj;
		if (expression == null)
		{
			if (other.expression != null)
				return false;
		}
		else if (!expression.equals(other.expression))
			return false;
		return true;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the expression
	 */
	public String getExpression()
	{
		return expression;
	}

}
