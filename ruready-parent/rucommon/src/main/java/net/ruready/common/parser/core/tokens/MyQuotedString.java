/*****************************************************************************************
 * Source File: MyQuotedString.java
 ****************************************************************************************/
package net.ruready.common.parser.core.tokens;

import java.util.List;

import net.ruready.common.parser.core.manager.Parser;

/**
 * Added for the parametric parser, to distinguish certain strings within some
 * symbols/quotes from quoted strings.
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
 * @version Jul 29, 2007
 */
public class MyQuotedString extends QuotedString
{
	/**
	 * Returns true if an assembly's next element is a quoted string.
	 * 
	 * @param object
	 *            an element from a assembly
	 * @return true, if a assembly's next element is a quoted string, like "chubby
	 *         cherubim".
	 */

	@Override
	protected boolean qualifies(Object o)
	{
		Token t = (Token) o;
		return t.isQuotedString();
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
		return "MyQuotedString";
	}
}
