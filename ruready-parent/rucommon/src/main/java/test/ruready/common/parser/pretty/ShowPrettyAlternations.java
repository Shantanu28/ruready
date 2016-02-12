/*******************************************************************************
 * Source File: ShowPrettyAlternations.java
 ******************************************************************************/
package test.ruready.common.parser.pretty;

import java.util.List;

import net.ruready.common.parser.core.manager.Alternation;
import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.pretty.PrettyParser;
import net.ruready.common.parser.core.tokens.Literal;
import net.ruready.common.parser.core.tokens.TokenAssembly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Show how the pretty printer displays a deep match of alternations. The
 * grammar this class shows is: <blockquote>
 * 
 * <pre>
 * reptile = crocodilian | squamata;
 * crocodilian = crocodile | alligator;
 * squamata = snake | lizard;
 * crocodile = &quot;nileCroc&quot; | &quot;cubanCroc&quot;;
 * alligator = &quot;chineseGator&quot; | &quot;americanGator&quot;;
 * snake = &quot;cobra&quot; | &quot;python&quot;;
 * lizard = &quot;gecko&quot; | &quot;iguana&quot;;
 * </pre>
 * 
 * </blockquote>
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
public class ShowPrettyAlternations
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ShowPrettyAlternations.class);

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Returns a parser that recognizes some alligators.
	 */
	private static Parser alligator()
	{
		Alternation a = new Alternation("<alligator>");
		a.add(new Literal("chineseGator"));
		a.add(new Literal("americanGator"));
		return a;
	}

	/**
	 * Returns a parser that recognizes some crocs.
	 */
	private static Parser crocodile()
	{
		Alternation a = new Alternation("<crocodile>");
		a.add(new Literal("nileCroc"));
		a.add(new Literal("cubanCroc"));
		return a;
	}

	/**
	 * Returns a parser that recognizes members of the crocodilian order.
	 */
	private static Parser crocodilian()
	{
		Alternation a = new Alternation("<crocodilian>");
		a.add(crocodile());
		a.add(alligator());
		return a;
	}

	/**
	 * Returns a parser that recognizes some lizards.
	 */
	private static Parser lizard()
	{
		Alternation a = new Alternation("<lizard>");
		a.add(new Literal("gecko"));
		a.add(new Literal("iguana"));
		return a;
	}

	/**
	 * Returns a parser that recognizes some reptiles.
	 */
	private static Parser reptile()
	{
		Alternation a = new Alternation("<reptile>");
		a.add(crocodilian());
		a.add(squamata());
		return a;
	}

	/**
	 * Returns a parser that recognizes some snakes.
	 */
	private static Parser snake()
	{
		Alternation a = new Alternation("<snake>");
		a.add(new Literal("cobra"));
		a.add(new Literal("python"));
		return a;
	}

	/**
	 * Returns a parser that recognizes some members of the squamata order.
	 */
	private static Parser squamata()
	{
		Alternation a = new Alternation("<squamata>");
		a.add(snake());
		a.add(lizard());
		return a;
	}

	// ========================= DEMO METHODS ==============================

	/**
	 * Show how a series of alternations appear when pretty- printed.
	 */
	public static void main(String[] args)
	{
		PrettyParser p = new PrettyParser(reptile());
		p.setShowLabels(true);
		TokenAssembly ta = new TokenAssembly("gecko");
		List<String> out = p.parseTrees(ta);
		for (String s : out)
		{
			logger.debug("The input parses as:");
			logger.debug("---------------------------");
			logger.debug(s);
		}
	}
}
