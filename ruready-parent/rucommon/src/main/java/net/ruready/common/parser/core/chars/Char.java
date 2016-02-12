/*****************************************************************************************
 * Source File: Char.java
 ****************************************************************************************/
package net.ruready.common.parser.core.chars;

import java.util.List;

import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.Terminal;

/**
 * Original code: Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker
 * makes no representations or warranties about the fitness of this software for any
 * particular purpose, including the implied warranty of merchantability.
 * 
 * @author Steven J. Metsker (c) 2006-07 Continuing Education , University of Utah . All
 *         copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 1.0
 * @author Nava L. Livne <I>&lt;nlivne@aoce.utah.edu&gt;</I> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @author Oren E. Livne <I>&lt;olivne@aoce.utah.edu&gt;</I> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112 (c) 2006-07 Continuing Education , University
 *         of Utah . All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846
 *         25702.PROV
 * @version Jul 8, 2006 A Char matches a character from a character assembly.
 */
public class Char extends Terminal
{
	/**
	 * Returns true every time, since this class assumes it is working against a
	 * CharacterAssembly.
	 * 
	 * @param o
	 *            ignored
	 * @return true, every time, since this class assumes it is working against a
	 *         CharacterAssembly
	 */
	@Override
	public boolean qualifies(Object o)
	{
		return true;
	}

	/**
	 * Returns a textual description of this parser.
	 * 
	 * @param visited
	 *            a list of parsers already printed in this description
	 * @return a textual description of this parser
	 * @see Parser#toString()
	 */
	@Override
	public String unvisitedString(List<Parser> visited)
	{
		return "C";
	}
}
