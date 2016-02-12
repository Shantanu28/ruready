/*****************************************************************************************
 * Source File: RequestHandler.java
 ****************************************************************************************/
package net.ruready.common.chain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A single handler in a Classic CoR (Chain of Responsibility Pattern), i.e., the request
 * is handled by only one of the handlers in the chain at a time, and it decides where to
 * forward the request next. This class cannot be instantiated and should be used in
 * conjunction with {@link RequestHandlerChain}.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 16, 2007
 */
public abstract class RequestHandler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RequestHandler.class);

	// ========================= FIELDS ====================================

	// The next node in the chain.
	private RequestHandler nextNode = null;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a request handler.
	 * 
	 * @param nextNode
	 *            The next node in the chain. If <code>null</code>, the request
	 *            processing chain will terminate after this handler handles the request.
	 */
	protected RequestHandler(RequestHandler nextNode)
	{
		this.nextNode = nextNode;
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Process the request. Called by <code>start()</code>.
	 * 
	 * @param request
	 *            the request object
	 * @return a boolean indicates whether this node handled the request. If it is
	 *         <code>true</code>, the request processing chain will terminate
	 */
	protected abstract boolean handle(ChainRequest request);

	/**
	 * Returns the name of this handler.
	 * 
	 * @return the name of this handler.
	 */
	public abstract String getName();

	// ========================= METHODS ===================================

	/**
	 * Starting point of the chain, called by client or pre-node. Call
	 * <code>handle()</code> on this node, and decide whether to continue the chain. If
	 * the next node is not null and this node did not handle the request, call
	 * <code>start()</code> on next node to handle request.
	 * 
	 * @param request
	 *            the request object
	 * @return a boolean indicates whether this handler fully handled the request
	 */
	public boolean run(ChainRequest request)
	{
		logger.debug("%%%%%%%%%%%%%%%%%%%%%%% Handling request: " + this.getName()
				+ " %%%%%%%%%%%%%%%%%%%%%%% ");
		// Add a message about our handling of the request
		request.addMessage(new HandlerMessage(this.getName(), "Begin handling"));
		boolean handledByThisNode = this.handle(request);
		if (nextNode != null && !handledByThisNode)
		{
			return nextNode.run(request);
		}
		else
		{
			return handledByThisNode;
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the nextNode
	 */
	public RequestHandler getNextNode()
	{
		return nextNode;
	}

	/**
	 * @param nextNode
	 *            the nextNode to set
	 */
	protected void setNextNode(RequestHandler nextNode)
	{
		this.nextNode = nextNode;
	}

}
