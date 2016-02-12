/*******************************************************
 * Source File: NamedRootNode.java
 *******************************************************/
package net.ruready.common.tree.hierarchy;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah
 * 
 * University of Utah, Salt Lake City, UT 84112 Protected by U.S. Provisional
 * Patent U-4003, February 2006
 * 
 * @version Jan 14, 2007
 * 
 * A root node with a name.
 */
public interface NamedRootNode extends RootNode
{
	String getName();
}
