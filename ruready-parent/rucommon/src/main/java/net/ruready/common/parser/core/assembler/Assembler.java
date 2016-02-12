/*******************************************************************************
 * Source File: Assembler.java
 ******************************************************************************/
package net.ruready.common.parser.core.assembler;

import java.util.ArrayList;
import java.util.List;

import net.ruready.common.parser.core.entity.Assembly;

/**
 * Original code: Copyright (c) 1999 Steven J. Metsker. All Rights Reserved.
 * Steve Metsker makes no representations or warranties about the fitness of
 * this software for any particular purpose, including the implied warranty of
 * merchantability. Parsers that have an Assembler ask it to work on an assembly
 * after a successful match.
 * <p>
 * By default, terminals push their matches on a assembly's stack after a
 * successful match.
 * <p>
 * Parsers recognize text, and assemblers provide any sort of work that should
 * occur after this recognition. This work usually has to do with the state of
 * the assembly, which is why assemblies have a stack and a target. Essentially,
 * parsers trade advancement on a assembly for work on the assembly's stack or
 * target.
 * <p>
 * Oren 22-APR-07: Added generics support and converted {@link List} to
 * {@link List} and {@link ArrayList}.
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003,
 *         February 2006
 * @version 1.0
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Apr 22, 2007
 */
public abstract class Assembler
{
	/**
	 * Returns a vector of the elements on an assembly's stack that appear
	 * before a specified fence.
	 * <p>
	 * Sometimes a parser will recognize a list from within a pair of
	 * parentheses or brackets. The parser can mark the beginning of the list
	 * with a fence, and then retrieve all the items that come after the fence
	 * with this method.
	 * 
	 * @param assembly
	 *            a assembly whose stack should contain some number of items
	 *            above a fence marker
	 * @param object
	 *            the fence, a marker of where to stop popping the stack
	 * @return the list the elements above the specified fence
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> elementsAbove(Assembly a, Object fence)
	{
		List<T> items = new ArrayList<T>();

		while (!a.stackIsEmpty()) {
			T top = (T) a.pop();
			if (top.equals(fence)) {
				break;
			}
			items.add(top);
		}

		return items;
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * This is the one method all subclasses must implement. It specifies what
	 * to do when a parser successfully matches against a assembly.
	 * <p>
	 * Note: the original signature of this class was public; it is now
	 * protected so that it is accessible by the package classes only. It is
	 * internal to the parser's flow and should not be accessed in other
	 * packages.
	 * 
	 * @param Assembly
	 *            the assembly to work on
	 */
	public abstract void workOn(Assembly a);

	// ========================= METHODS ===================================

	/**
	 * Return the name of this object. A hook that returns the assembler type by
	 * default.
	 * 
	 * @return name of this object
	 */
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
