/*******************************************************
 * Source File: ShowPrettyRepetitions.java
 *******************************************************/
package test.ruready.common.parser.pretty;

import java.util.List;

import net.ruready.common.parser.core.manager.Repetition;
import net.ruready.common.parser.core.manager.Sequence;
import net.ruready.common.parser.core.pretty.PrettyParser;
import net.ruready.common.parser.core.tokens.TokenAssembly;
import net.ruready.common.parser.core.tokens.Word;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Show that the pretty printer will find all the parses that result from
 * applying the parser <code>Word* Word*</code> against a string with four
 * words.
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
public class ShowPrettyRepetitions
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ShowPrettyRepetitions.class);

	// ========================= DEMO METHODS ==============================

	/**
	 * Show that the pretty printer will find all the parses that result from
	 * applying the parser <code>Word* Word*</code> against a string with four
	 * words.
	 */
	public static void main(String[] args)
	{
		PrettyParser p = new PrettyParser(seq());
		p.setShowLabels(true);
		TokenAssembly ta = new TokenAssembly("belfast cork dublin limerick");
		List<String> out = p.parseTrees(ta);
		for (String s : out)
		{
			logger.debug("The input parses as:");
			logger.debug("---------------------------");
			logger.debug(s);
		}
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * The parser to try: <blockquote>
	 * 
	 * <pre>
	 *  
	 *      seq  = rep1 rep2;
	 *      rep1 = Word*;
	 *      rep2 = Word*;
	 * </pre>
	 * 
	 * </blockquote>
	 */
	private static Sequence seq()
	{
		Sequence seq = new Sequence("<seq>");
		seq.add(new Repetition(new Word(), "<rep1>"));
		seq.add(new Repetition(new Word(), "<rep2>"));
		return seq;
	}
}
