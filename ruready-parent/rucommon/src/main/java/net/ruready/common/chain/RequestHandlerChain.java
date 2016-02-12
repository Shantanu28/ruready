/*****************************************************************************************
 * Source File: RequestHandlerChain.java
 ****************************************************************************************/
package net.ruready.common.chain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A request handler chain - Classic CoR (Chain of Responsibility Pattern).
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
public class RequestHandlerChain extends RequestHandler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RequestHandlerChain.class);

	// ========================= FIELDS ====================================

	// List of handlers in their order of invokation
	protected RequestHandler[] handlerList;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a request handler chain from handlers.
	 * 
	 * @param handlerList
	 *            List of handlers in their order of invokation
	 * @param nextNode
	 *            The next node to invoke in a super-chain after this chain is completed.
	 *            If <code>null</code>, the request processing chain will terminate
	 *            after this handler handles the request.
	 */
	public RequestHandlerChain(RequestHandler[] handlerList, RequestHandler nextNode)
	{
		super(nextNode);

		if (handlerList != null)
		{
			this.handlerList = handlerList;
			// Initialize the chain node references
			for (int i = 0; i < handlerList.length - 1; i++)
			{
				handlerList[i].setNextNode(handlerList[i + 1]);
			}
		}
	}

	// ========================= IMPLEMENTATION: RequestHandler ============

	/**
	 * @see net.ruready.common.chain.RequestHandler#handle(net.ruready.common.chain.ChainRequest)
	 */
	@Override
	protected final boolean handle(ChainRequest request)
	{
		// Invoke this chain; the result indicates whether super-chain
		// processing should be terminated
		return handlerList[0].run(request);
	}

	/**
	 * Returns the name of this handler.
	 * 
	 * @return the name of this handler.
	 */
	@Override
	public String getName()
	{
		return "Chain";
	}

	// ========================= METHODS ===================================

	/**
	 * Initialize handler chain. Useful when the chain depends on instance parameters and
	 * cannot be determined in static block.
	 * 
	 * @param inputHandlerList
	 *            list of handlers in this chain
	 */
	protected void initialize(RequestHandler[] inputHandlerList)
	{
		this.handlerList = inputHandlerList;

		// Initialize the chain node references
		for (int i = 0; i < handlerList.length - 1; i++)
		{
			handlerList[i].setNextNode(handlerList[i + 1]);
		}

	}

}
