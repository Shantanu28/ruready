/*******************************************************************************
 * Source File: PrettySequenceAssembler.java
 ******************************************************************************/
package net.ruready.common.parser.core.pretty.assembler;

import net.ruready.common.misc.Auxiliary;
import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.pretty.ComponentNode;
import net.ruready.common.parser.core.pretty.CompositeNode;

/**
 * Replace a given number of nodes on the stack with a new composite that holds
 * the popped nodes as its children.
 * 
 * @author Steven J. Metsker
 * @version 1.0
 */
class PrettySequenceAssembler extends Assembler implements Auxiliary
{
	private final String name;

	private final int numberNodes;

	/**
	 * Construct an assembler that will replace a given number of nodes on the
	 * stack with a new composite that holds the popped nodes as its children.
	 */
	public PrettySequenceAssembler(String name, Integer numberNodes)
	{
		this.name = name;
		this.numberNodes = numberNodes;
	}

	/**
	 * Replace a given number of nodes on the stack with a new composite that
	 * holds the popped nodes as its children.
	 * 
	 * @param Assembly
	 *            the assembly to work on
	 */
	@Override
	public void workOn(Assembly a)
	{
		CompositeNode newNode = new CompositeNode(name);
		for (int i = 0; i < numberNodes; i++) {
			ComponentNode node = (ComponentNode) a.pop();
			newNode.insert(node);
		}
		a.push(newNode);
	}
}
