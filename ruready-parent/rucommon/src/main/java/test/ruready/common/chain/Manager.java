/*******************************************************
 * Source File: Manager.java
 *******************************************************/
package test.ruready.common.chain;

import net.ruready.common.chain.ChainRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.ruready.common.rl.TestingNames;

/**
 * A manager in a chain of responsibility in a company.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Apr 15, 2007
 */
class Manager extends PurchasePower
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Manager.class);

	// ========================= FIELDS ====================================

	private String title;

	private double allowable;

	// ========================= CONSTRCUTORS ==============================

	/**
	 * Construct a manager handler.
	 * 
	 * @param title
	 *            manager's title
	 * @param allowable
	 *            maximum allowed purchase amount that this manager can approve
	 */
	public Manager(String title, double allowable)
	{
		super();
		this.title = title;
		this.allowable = allowable;
	}

	// ========================= IMPLEMENTATION: RequestHandler ============

	/**
	 * @see net.ruready.common.chain.RequestHandler#handle(net.ruready.common.chain.ChainRequest)
	 */
	@Override
	protected boolean handle(ChainRequest request)
	{
		// Cast to a friendlier version
		PurchaseRequest purchaseRequest = (PurchaseRequest) request;
		double amount = purchaseRequest.getAmount();
		if (amount < allowable) {
			request.setAttribute(TestingNames.CHAIN.REQUEST.ATTRIBUTE.APPROVED, title);
			// logger.info(title + " will approve $" + amount);
			return true;
		}
		return false;
	}

	/**
	 * Returns the name of this handler.
	 * 
	 * @return the name of this handler.
	 */
	@Override
	public String getName()
	{
		return "Manager";
	}
}
