/*******************************************************************************
 * Source File: PrettyRepetitionAssembler.java
 ******************************************************************************/
package net.ruready.common.parser.core.pretty.assembler;

import java.util.List;

import net.ruready.common.misc.Auxiliary;
import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.pretty.ComponentNode;
import net.ruready.common.parser.core.pretty.CompositeNode;

/**
 * Replace the nodes above a given "fence" object with a new composite that
 * holds the popped nodes as its children.
 * 
 * @author Steven J. Metsker
 * @version 1.0
 */
class PrettyRepetitionAssembler extends Assembler implements Auxiliary
{
	protected String name;

	protected Object fence;

	/**
	 * Construct an assembler that will replace the nodes above the supplied
	 * "fence" object with a new composite that will hold the popped nodes as
	 * its children.
	 */
	public PrettyRepetitionAssembler(String name, Object fence)
	{
		this.name = name;
		this.fence = fence;
	}

	/**
	 * Replace the nodes above a given "fence" object with a new composite that
	 * holds the popped nodes as its children.
	 * 
	 * @param Assembly
	 *            the assembly to work on
	 */
	@Override
	public void workOn(Assembly a)
	{
		CompositeNode newNode = new CompositeNode(name);
		List<?> v = Assembler.elementsAbove(a, fence);
		for (Object e : v) {
			newNode.add((ComponentNode) e);
		}
		a.push(newNode);
	}
}
