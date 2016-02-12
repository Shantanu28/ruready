/*******************************************************
 * Source File: CaselessLiteral.java
 *******************************************************/
package net.ruready.common.parser.core.tokens;

/**
 * A CaselessLiteral matches a specified String from an assembly, disregarding
 * case.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker
 * makes no representations or warranties about the fitness of this software for
 * any particular purpose, including the implied warranty of merchantability. *
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003,
 *         February 2006
 * @version 1.0
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 11, 2007
 */
public class CaselessLiteral extends Literal
{
	/**
	 * Constructs a literal that will match the specified string, given
	 * mellowness about case.
	 * 
	 * @param string
	 *            the string to match as a token
	 * @return a literal that will match the specified string, disregarding case
	 */
	public CaselessLiteral(String literal)
	{
		super(literal);
	}

	/**
	 * Returns true if the literal this object equals an assembly's next
	 * element, disregarding case.
	 * 
	 * @param object
	 *            an element from an assembly
	 * @return true, if the specified literal equals the next token from an
	 *         assembly, disregarding case
	 */
	@Override
	protected boolean qualifies(Object o)
	{
		return literal.equalsIgnoreCase(o);
	}
}
