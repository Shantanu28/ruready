/*****************************************************************************************
 * Source File: TerminalNode.java
 ****************************************************************************************/
package net.ruready.common.parser.core.pretty;

import java.util.List;

import net.ruready.common.rl.CommonNames;

/**
 * A terminal node in the node hierarchy.
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
public class TerminalNode extends ComponentNode
{
	/**
	 * Create a node that holds the given value.
	 */
	public TerminalNode(Object value)
	{
		this.value = value;
	}

	/**
	 * Return a textual description of this node, indenting the string based on the depth
	 * of the node.
	 * 
	 * @param depth
	 * @param label
	 * @param ignored
	 * @return
	 * @see net.ruready.common.parser.core.pretty.ComponentNode#toString(int, boolean,
	 *      java.util.List)
	 */
	@Override
	protected String toString(int depth, boolean label, List<ComponentNode> ignored)
	{

		return indent(depth) + value + CommonNames.MISC.NEW_LINE_CHAR;
	}
}
