/*****************************************************************************************
 * Source File: ParserTester.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.entity.Matches;
import net.ruready.common.pointer.PubliclyCloneable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class generates random language elements for a parser and tests that the
 * parser can accept them.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003,
 *         February 2006 Copyright (c) 2000 Steven J. Metsker. All Rights
 *         Reserved. Steve Metsker makes no representations or warranties about
 *         the fitness of this software for any particular purpose, including
 *         the implied warranty of merchantability.
 * @version 1.0
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public abstract class ParserTester
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ParserTester.class);

	// ========================= FIELDS ====================================

	private Parser p;

	private boolean logTestStrings = true;

	/**
	 * Constructs a tester for the given parser.
	 */
	public ParserTester(Parser p)
	{
		this.p = p;
	}

	/**
	 * Subclasses must override this, to produce an assembly from the given
	 * (random) string.
	 */
	protected abstract Assembly assembly(String s);

	/**
	 * Generate a random language element, and return true if the parser cannot
	 * unambiguously parse it.
	 */
	protected boolean canGenerateProblem(int depth)
	{
		String s = p.randomInput(depth, separator());
		logTestString(s);
		Assembly a = assembly(s);
		a.setTarget(freshTarget());
		Matches in = new Matches();
		in.addAssembly(a);
		Matches out = ParserTester.completeMatches(p.match(in));
		if (out.numAssemblies() != 1)
		{
			logProblemFound(s, out.numAssemblies());
			return true;
		}
		return false;
	}

	/**
	 * Return a subset of the supplied vector of assemblies, filtering for
	 * assemblies that have been completely matched.
	 * 
	 * @param List
	 *            a collection of partially or completely matched assemblies
	 * @return a collection of completely matched assemblies
	 */
	public static Matches completeMatches(Matches in)
	{
		Matches out = new Matches();
		for (Assembly a : in.getAssemblies())
		{
			if (!a.hasMoreElements())
			{
				out.addAssembly(a);
			}
		}
		return out;
	}

	/*
	 * Give subclasses a chance to provide fresh target at the beginning of a
	 * parse.
	 */
	protected PubliclyCloneable freshTarget()
	{
		return null;
	}

	/*
	 * This method is broken out to allow subclasses to create less verbose
	 * tester, or to direct logging to somewhere other than System.out.
	 */
	protected void logDepthChange(int depth)
	{
		logger.debug("Testing depth " + depth + "...");
	}

	/*
	 * This method is broken out to allow subclasses to create less verbose
	 * tester, or to direct logging to somewhere other than System.out.
	 */
	protected void logPassed()
	{
		logger.debug("No problems found.");
	}

	/*
	 * This method is broken out to allow subclasses to create less verbose
	 * tester, or to direct logging to somewhere other than System.out.
	 */
	protected void logProblemFound(String s, int matchSize)
	{
		logger.debug("Problem found for string:");
		logger.debug(s);
		if (matchSize == 0)
		{
			logger.debug("Parser cannot match this apparently " + "valid string.");
		}
		else
		{
			logger.debug("The parser found " + matchSize + " ways to parse this string.");
		}
	}

	/*
	 * This method is broken out to allow subclasses to create less verbose
	 * tester, or to direct logging to somewhere other than System.out.
	 */
	protected void logTestString(String s)
	{
		if (logTestStrings)
		{
			logger.debug("    Testing string " + s);
		}
	}

	/*
	 * By default, place a blank between randomly generated "words" of a
	 * language.
	 */
	protected String separator()
	{
		return " ";
	}

	/**
	 * Set the boolean which determines if this class displays every test
	 * string.
	 * 
	 * @param boolean
	 *            true, if the user wants to see every test string
	 */
	public void setLogTestStrings(boolean logTestStrings)
	{
		this.logTestStrings = logTestStrings;
	}

	/**
	 * Create a series of random language elements, and test that the parser can
	 * unambiguously parse each one.
	 */
	public void test()
	{
		for (int depth = 2; depth < 8; depth++)
		{
			logDepthChange(depth);
			for (int k = 0; k < 100; k++)
			{
				if (canGenerateProblem(depth))
				{
					return;
				}
			}
		}
		logPassed();
	}
}
