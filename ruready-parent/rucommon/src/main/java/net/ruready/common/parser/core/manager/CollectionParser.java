/*******************************************************
 * Source File: CollectionParser.java
 *******************************************************/
package net.ruready.common.parser.core.manager;

import java.util.ArrayList;
import java.util.List;


/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker
 * makes no representations or warranties about the fitness of this software for
 * any particular purpose, including the implied warranty of merchantability.
 */

/**
 * This class abstracts the behavior common to parsers that consist of a series
 * of other parsers.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003,
 *         February 2006
 * @version 1.0
 */
public abstract class CollectionParser extends Parser
{
	/**
	 * the parsers this parser is a collection of
	 */
	protected List<Parser> subparsers = new ArrayList<Parser>();

	/**
	 * Supports subclass constructors with no arguments.
	 */
	public CollectionParser()
	{
		
	}

	/**
	 * Supports subclass constructors with a name argument
	 * 
	 * @param string
	 *            the name of this parser
	 */
	public CollectionParser(String name)
	{
		super(name);
	}

	/**
	 * Adds a parser to the collection.
	 * 
	 * @param Parser
	 *            the parser to add
	 * @return this
	 */
	public CollectionParser add(Parser e)
	{
		subparsers.add(e);
		return this;
	}

	/**
	 * Return this parser's subparsers.
	 * 
	 * @return List this parser's subparsers
	 */
	public List<Parser> getSubparsers()
	{
		return subparsers;
	}

	/**
	 * Helps to textually describe this CollectionParser.
	 * 
	 * @returns the string to place between parsers in the collection
	 */
	protected abstract String toStringSeparator();

	/*
	 * Returns a textual description of this parser.
	 */
	@Override
	protected String unvisitedString(List<Parser> visited)
	{
		StringBuffer buf = new StringBuffer("<");
		boolean needSeparator = false;
		for (Parser next : subparsers) {
			if (needSeparator) {
				buf.append(toStringSeparator());
			}
			buf.append(next.toString(visited));
			needSeparator = true;
		}
		buf.append(">");
		return buf.toString();
	}
}
