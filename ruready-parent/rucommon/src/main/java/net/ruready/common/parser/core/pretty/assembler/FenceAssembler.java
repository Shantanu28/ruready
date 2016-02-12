/*******************************************************************************
 * Source File: FenceAssembler.java
 ******************************************************************************/
package net.ruready.common.parser.core.pretty.assembler;

import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.entity.Assembly;

/**
 * Places a given "fence" or marker object on an assembly's stack.
 * 
 * @author Steven J. Metsker
 * @version 1.0
 */
class FenceAssembler extends Assembler
{
	private Object fence;

	/**
	 * Construct an assembler that will place the given object on an assembly's
	 * stack.
	 */
	public FenceAssembler(Object fence)
	{
		this.fence = fence;
	}

	/**
	 * Place the fence object on the assembly's stack.
	 * 
	 * @param Assembly
	 *            the assembly to work on
	 */
	@Override
	public void workOn(Assembly a)
	{
		a.push(fence);
	}
}
