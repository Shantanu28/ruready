/*****************************************************************************************
 * Source File: Parser.java
 ****************************************************************************************/
package net.ruready.common.parser.core.manager;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.entity.Match;
import net.ruready.common.parser.core.entity.Matches;
import net.ruready.common.visitor.Visitable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker makes no
 * representations or warranties about the fitness of this software for any particular
 * purpose, including the implied warranty of merchantability.
 */

/**
 * A <code>Parser</code> is an object that recognizes the elements of a language.
 * <p>
 * Each <code>Parser</code> object is either a <code>
 * Terminal</code> or a composition
 * of other parsers. The <code>Terminal</code> class is a subclass of <code>
 * Parser</code>,
 * and is itself a hierarchy of parsers that recognize specific patterns of text. For
 * example, a <code>Word</code> recognizes any word, and a <code>Literal</code>
 * matches a specific string.
 * <p>
 * In addition to <code>Terminal</code>, other subclasses of <code>Parser</code>
 * provide composite parsers, describing sequences, alternations, and repetitions of other
 * parsers. For example, the following <code>
 * Parser</code> objects culminate in a
 * <code>good
 * </code> parser that recognizes a description of good coffee. <blockquote>
 * 
 * <pre>
 * Alternation adjective = new Alternation();
 * adjective.add(new Literal(&quot;steaming&quot;));
 * adjective.add(new Literal(&quot;hot&quot;));
 * Sequence good = new Sequence();
 * good.add(new Repetition(adjective));
 * good.add(new Literal(&quot;coffee&quot;));
 * String s = &quot;hot hot steaming hot coffee&quot;;
 * Assembly a = new TokenAssembly(s);
 * logger.debug(good.bestMatch(a));
 * </pre>
 * 
 * </blockquote> This prints out: <blockquote>
 * 
 * <pre>
 *                [hot, hot, steaming, hot, coffee]
 *                hot/hot/steaming/hot/coffee&circ;
 * </pre>
 * 
 * </blockquote> The parser does not match directly against a string, it matches against
 * an <code>Assembly</code>. The resulting assembly shows its stack, with four words on
 * it, along with its sequence of tokens, and the index at the end of these. In practice,
 * parsers will do some work on an assembly, based on the text they recognize.
 * <p>
 * 26-APR-2007 Adhering to generic visitor pattern
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003, February 2006
 * @version 1.0
 */
public abstract class Parser implements Visitable<ParserVisitor>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Parser.class);

	/**
	 * A flag for debugging printouts and procedures.<br>
	 * When declared as final and set to false, all if (debug) calls are not even compiled
	 * into the executable code! So finalization serves as an optimization strategy here.
	 */
	private static final boolean debug = false;

	// ========================= FIELDS ====================================

	// a name to identify this parser
	private String name;

	// An object that will work on an assembly whenever this parser successfully
	// matches against the assembly
	private Assembler assembler;

	// ========================= CONSTRCUTORS ==============================

	/**
	 * Constructs a nameless parser.
	 */
	public Parser()
	{

	}

	/**
	 * Constructs a parser with the given name.
	 * 
	 * @param String
	 *            A name to be known by. For parsers that are deep composites, a simple
	 *            name identifying its purpose is useful.
	 */
	public Parser(String name)
	{
		this.name = name;
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Given a set (well, a <code>List</code>, really) of assemblies, this method
	 * matches this parser against all of them, and returns a new set (also really a
	 * <code>List</code>) of the assemblies that result from the matches.
	 * <p>
	 * For example, consider matching the regular expression <code>a*</code> against the
	 * string <code>"aaab"</code>. The initial set of states is <code>{^aaab}</code>,
	 * where the ^ indicates how far along the assembly is. When <code>a*</code> matches
	 * against this initial state, it creates a new set <code>{^aaab, a^aab, aa^ab, 
	 * aaa^b}</code>.
	 * 
	 * @return a List of assemblies that result from matching against a beginning set of
	 *         assemblies
	 * @param List
	 *            a vector of assemblies to match against
	 */
	public abstract Matches match(Matches in);

	/**
	 * Create a random expansion for this parser, where a concatenation of the returned
	 * collection will be a language element.
	 */
	protected abstract List<?> randomExpansion(int maxDepth, int depth);

	/**
	 * Returns a textual description of this string.
	 * 
	 * @param visited
	 *            a list of objects already printed
	 * @return
	 */
	protected abstract String unvisitedString(List<Parser> visited);

	// ========================= IMPLEMENTATION: Visitable<ParserVisitor> ==

	// Abstract

	// ========================= METHODS ===================================

	/**
	 * Returns the most-matched assembly in a collection.
	 * 
	 * @return the most-matched assembly in a collection.
	 * @param List
	 *            the collection to look through
	 */
	protected Match best(Matches v)
	{
		Assembly best = null;
		for (Assembly a : v.getAssemblies())
		{
			if (!a.hasMoreElements())
			{
				best = a;
				break;
			}
			if (best == null)
			{
				best = a;
			}
			else if (a.elementsConsumed() > best.elementsConsumed())
			{
				best = a;
			}
		}
		return new Match(best, v.getSyntaxErrors());
	}

	/**
	 * Returns an assembly with the greatest possible number of elements consumed by
	 * matches of this parser.
	 * 
	 * @return an assembly with the greatest possible number of elements consumed by this
	 *         parser
	 * @param Assembly
	 *            an assembly to match against
	 */
	public Match bestMatch(Assembly a)
	{
		Matches in = new Matches();
		in.addAssembly(a);
		Matches out = this.matchAndAssemble(in);
		return this.best(out);
	}

	/**
	 * Returns either null, or a completely matched version of the supplied assembly.
	 * 
	 * @return either null, or a completely matched version of the supplied assembly
	 * @param Assembly
	 *            an assembly to match against
	 */
	public Match completeMatch(Assembly a)
	{
		Match best = this.bestMatch(a);
		if (best != null && best.getAssembly() != null && !best.hasMoreElements())
		{
			// Clear all syntax errors because we completely matched
			best.clearSyntaxErrors();
			return best;
		}
		return null;
		// return best;
	}

	/**
	 * Returns the name of this parser.
	 * 
	 * @return the name of this parser
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Match this parser against an input state, and then apply this parser's assembler
	 * against the resulting state.
	 * 
	 * @return a List of assemblies that result from matching against a beginning set of
	 *         assemblies
	 * @param List
	 *            a vector of assemblies to match against
	 */
	public Matches matchAndAssemble(Matches in)
	{
		if (debug)
		{
			logger.trace("matchAndAssemble(): " + this + " in " + in);
		}
		Matches out = this.match(in);
		if (debug)
		{
			logger.trace("Finished matching: " + this + " in " + in + " out " + out);
			if (out.isEmpty())
			{
				// logger.trace("No match: " + this + " in " + in + " out " + out);
			}
			else
			{
				logger.trace("Found match: " + this + " in " + in + " out " + out);
			}
		}
		if (assembler != null)
		{
			for (Assembly a : out.getAssemblies())
			{
				// logger.trace(this + " running assembler on assembly " + a);
				assembler.workOn(a);
			}
		}
		if (debug)
		{
			logger.trace("end matchAndAssemble(): " + this + " out " + out);
		}
		return out;
	}

	/**
	 * Return a random element of this parser's language.
	 * 
	 * @return a random element of this parser's language
	 */
	public String randomInput(int maxDepth, String separator)
	{
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for (Object object : randomExpansion(maxDepth, 0))
		{
			if (!first)
			{
				buf.append(separator);
			}
			buf.append(object);
			first = false;
		}
		return buf.toString();
	}

	/**
	 * Sets the object that will work on an assembly whenever this parser successfully
	 * matches against the assembly.
	 * 
	 * @param Assembler
	 *            the assembler to apply
	 * @return Parser this
	 */
	public Parser setAssembler(Assembler assembler)
	{
		this.assembler = assembler;
		return this;
	}

	/**
	 * Returns a textual description of this parser.
	 * 
	 * @return String a textual description of this parser, taking care to avoid infinite
	 *         recursion
	 */
	@Override
	public String toString()
	{
		return toString(new ArrayList<Parser>());
	}

	/**
	 * Returns a textual description of this parser. Parsers can be recursive, so when
	 * building a descriptive string, it is important to avoid infinite recursion by
	 * keeping track of the objects already described. This method keeps an object from
	 * printing twice, and uses <code>unvisitedString</code> which subclasses must
	 * implement.
	 * 
	 * @param visited
	 *            a list of objects already printed
	 * @return a textual version of this parser, avoiding recursion
	 */
	protected String toString(List<Parser> visited)
	{
		if (name != null)
		{
			return name;
		}
		else if (visited.contains(this))
		{
			return "...";
		}
		else
		{
			visited.add(this);
			return unvisitedString(visited);
		}
	}
}
