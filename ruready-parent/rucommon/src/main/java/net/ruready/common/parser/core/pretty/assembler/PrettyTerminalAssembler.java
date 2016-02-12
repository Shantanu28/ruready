/*****************************************************************************************
 * Source File: PrettyTerminalAssembler.java
 ****************************************************************************************/
package net.ruready.common.parser.core.pretty.assembler;

import net.ruready.common.misc.Auxiliary;
import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.pretty.TerminalNode;
import net.ruready.common.parser.core.tokens.Token;

/**
 * Replace a {@link Token} object on the stack with a {@link TerminalNode} that holds the
 * token's value.
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
 * @author Steven J. Metsker
 * @version 1.0
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
class PrettyTerminalAssembler extends Assembler implements Auxiliary
{
	/**
	 * Replace a <code>Token</code> object on the stack with a <code>TerminalNode</code>
	 * that holds the token's value.
	 * 
	 * @param Assembly
	 *            the assembly to work on
	 */
	@Override
	public void workOn(Assembly a)
	{
		Token t = (Token) a.pop();
		a.push(new TerminalNode(t.value()));
	}
}
