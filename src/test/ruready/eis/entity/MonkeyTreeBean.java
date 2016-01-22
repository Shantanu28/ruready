/*******************************************************
 * Source File: MonkeyTreeBean.java
 *******************************************************/
package test.ruready.eis.entity;

import org.apache.struts.action.ActionForm;

/**
 * Based on the "Monkey" website Struts nested tag examples.
 * 
 * @see http://www.keyboardmonkey.com/pilotlight/monkey-tree/monkey-tree-01.jsp?content=yesplease
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 15, 2007
 */
public class MonkeyTreeBean extends ActionForm
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/* usual member variables */
	private String beanName;

	private GenericTreeNode<String> monkeyTree;

	/* empty constructor for the bean */
	public MonkeyTreeBean()
	{
		this.beanName = "MonkeyTreeBean";
		this.monkeyTree = new GenericTreeNode<String>("Monkey Tree");

		GenericTreeNode<String> nodeOne = new GenericTreeNode<String>("monkey one");
		GenericTreeNode<String> nodeTwo = new GenericTreeNode<String>("monkey two");
		GenericTreeNode<String> nodeThree = new GenericTreeNode<String>("monkey three");
		GenericTreeNode<String> nodeFour = new GenericTreeNode<String>("monkey four");

		this.monkeyTree.addChild(nodeThree);
		nodeThree.addChild(nodeFour);

		this.monkeyTree.addChild(nodeOne);
		nodeOne.addChild(nodeTwo);
	}

	/* getter method for the "beanName" property */
	public String getBeanName()
	{
		return this.beanName;
	}

	/**
	 * @return the monkeyTree
	 */
	public GenericTreeNode<String> getMonkeyTree()
	{
		return monkeyTree;
	}

}
