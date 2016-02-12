/*****************************************************************************************
 * Source File: ApprovalDenied.java
 ****************************************************************************************/
package test.ruready.common.chain;

import net.ruready.common.chain.ChainRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Chain of responsibility pattern example - represents a handler of request that are
 * denied approval.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 12, 2007
 */
class ApprovalDenied extends PurchasePower
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ApprovalDenied.class);

	// ========================= CONSTRCUTORS ==============================

	public ApprovalDenied()
	{
		super();
	}

	// ========================= IMPLEMENTATION: RequestHandler ============

	/**
	 * @param request
	 * @return
	 * @see net.ruready.common.chain.RequestHandler#handle(net.ruready.common.chain.ChainRequest)
	 */
	@Override
	protected boolean handle(ChainRequest request)
	{
		logger.info("Approval denied");
		return true;
	}

	/**
	 * Returns the name of this handler.
	 * 
	 * @return the name of this handler.
	 */
	@Override
	public String getName()
	{
		return "Approval denied";
	}
}
