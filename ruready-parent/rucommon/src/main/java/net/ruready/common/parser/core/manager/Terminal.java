/*****************************************************************************************
 * Source File: Terminal.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.entity.Matches;

/**
 * A <code>Terminal</code> is a parser that is not a composition of other parsers.
 * Terminals are "terminal" because they do not pass matching work on to other parsers.
 * The criterion that terminals use to check a match is something other than another
 * parser. Terminals are also the only parsers that advance an assembly.
 * <p>
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Oct 1, 2007
 */
public class Terminal extends Parser
{
	/*
	 * whether or not this terminal should push itself upon an assembly's stack after a
	 * successful match
	 */
	protected boolean discard = false;

	/**
	 * Constructs an unnamed terminal.
	 */
	public Terminal()
	{
	}

	/**
	 * Constructs a terminal with the given name.
	 * 
	 * @param String
	 *            A name to be known by.
	 */
	public Terminal(String name)
	{
		super(name);
	}

	/**
	 * @see net.ruready.common.visitor.Visitable#accept(net.ruready.common.visitor.Visitor)
	 */
	public <B extends ParserVisitor> void accept(B visitor)
	{
		visitor.visit(this);
	}

	/**
	 * A convenience method that sets discarding to be true.
	 * 
	 * @return this
	 */
	public Terminal discard()
	{
		return setDiscard(true);
	}

	/**
	 * Given a collection of assemblies, this method matches this terminal against all of
	 * them, and returns a new collection of the assemblies that result from the matches.
	 * 
	 * @return a List of assemblies that result from matching against a beginning set of
	 *         assemblies
	 * @param List
	 *            a vector of assemblies to match against
	 */
	@Override
	public Matches match(Matches in)
	{
		Matches out = new Matches();
		for (Assembly a : in.getAssemblies())
		{
			Assembly b = matchOneAssembly(a);
			if (b != null)
			{
				out.addAssembly(b);
			}
		}
		return out;
	}

	/**
	 * Returns an assembly equivalent to the supplied assembly, except that this terminal
	 * will have been removed from the front of the assembly. As with any parser, if the
	 * match succeeds, this terminal's assembler will work on the assembly. If the match
	 * fails, this method returns null.
	 * 
	 * @param Assembly
	 *            the assembly to match against
	 * @return a copy of the incoming assembly, advanced by this terminal
	 */
	protected Assembly matchOneAssembly(Assembly in)
	{
		if (!in.hasMoreElements())
		{
			return null;
		}
		if (qualifies(in.peek()))
		{
			Assembly out = in.clone();
			Object o = out.nextElement();
			if (!discard)
			{
				out.push(o);
			}
			return out;
		}
		return null;
	}

	/**
	 * The mechanics of matching are the same for many terminals, except for the check
	 * that the next element on the assembly qualifies as the type of terminal this
	 * terminal looks for. This method performs that check.
	 * 
	 * @param o
	 *            an element from a assembly
	 * @return true, if the object is the kind of terminal this parser seeks
	 */
	protected boolean qualifies(Object o)
	{
		return true;
	}

	/**
	 * By default, create a collection with this terminal's string representation of
	 * itself. (Most subclasses override this.)
	 * 
	 * @param maxDepth
	 * @param depth
	 * @return
	 * @see net.ruready.common.parser.core.manager.Parser#randomExpansion(int, int)
	 */
	@Override
	public List<?> randomExpansion(int maxDepth, int depth)
	{
		List<String> v = new ArrayList<String>();
		v.add(this.toString());
		return v;
	}

	/**
	 * By default, terminals push themselves upon a assembly's stack, after a successful
	 * match. This routine will turn off (or turn back on) that behavior.
	 * 
	 * @param boolean
	 *            true, if this terminal should push itself on a assembly's stack
	 * @return this
	 */
	public Terminal setDiscard(boolean discard)
	{
		this.discard = discard;
		return this;
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
	protected String unvisitedString(List<Parser> visited)
	{
		return "any";
	}
}
