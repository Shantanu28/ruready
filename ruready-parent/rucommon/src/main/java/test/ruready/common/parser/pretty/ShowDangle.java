/*******************************************************
 * Source File: ShowDangle.java
 *******************************************************/
package test.ruready.common.parser.pretty;

import java.util.List;

import net.ruready.common.parser.core.pretty.PrettyParser;
import net.ruready.common.parser.core.tokens.TokenAssembly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Show that the <code>Dangle.statement()</code> parser is ambiguous.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Steven J. Metsker
 * @version 1.0
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 14, 2007
 */
public class ShowDangle
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ShowDangle.class);

	// ========================= FIELDS ====================================

	// ========================= DEMO METHODS ==============================

	/**
	 * Show that the <code>Dangle.statement()</code> parser is ambiguous.
	 */
	public static void main(String[] args)
	{
		String s;
		s = "if (overdueDays > 90)    \n";
		s += "    if (balance > 1000) \n"; // Was originally >= but our symbol
		// state and tokenizer are a little
		// different here
		s += "        callCustomer();  \n";
		s += "else sendBill();";

		TokenAssembly ta = new TokenAssembly(s);
		logger.debug("ta " + ta);

		PrettyParser p = new PrettyParser(Dangle.statement());

		List<String> out = p.parseTrees(ta);
		for (String a : out)
		{
			logger.debug("The input parses as:");
			logger.debug("---------------------------");
			logger.debug(a);
		}
	}
}
