/*****************************************************************************************
 * Source File: RequestHandlerLoop.java
 ****************************************************************************************/
package net.ruready.common.chain;

import net.ruready.common.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A request handler closed-loop chain. To terminate the chain, one of the chain pieces
 * must signal that the request has been handled. This is usually the task of a dedicated
 * termination criteria handler.
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
public class RequestHandlerLoop extends RequestHandlerChain
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RequestHandlerLoop.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a request handler loop from handlers.
	 * 
	 * @param start
	 *            start handler for the loop; initializes loop counters
	 * @param body
	 *            A non-null List of loop body handlers in their order of invokation
	 * @param end
	 *            end handler for the loop; specifies the termination criterion
	 * @param nextNode
	 *            The next node to invoke in a super-chain after this chain is completed.
	 *            If <code>null</code>, the request processing chain will terminate
	 *            after this handler handles the request.
	 */
	public RequestHandlerLoop(RequestHandler start, RequestHandler[] body,
			RequestHandler end, RequestHandler nextNode)
	{
		super(null, nextNode);

		if ((start == null) || (body == null) || (end == null))
		{
			// Skip initialization; uses initialize() instead in this case
			return;
		}

		handlerList = new RequestHandler[body.length + 2];

		// Initialize start handler references
		int count = 0;
		handlerList[count] = start;
		handlerList[count].setNextNode(body[0]);
		count++;

		// Initialize body handler references
		for (int i = 0; i < body.length - 1; i++)
		{
			handlerList[count] = body[i];
			handlerList[count].setNextNode(body[i + 1]);
			count++;
		}

		// Initialize end handler references; closes the loop. Note that
		// it goes back to the body, so the start handler is invoked only once
		// throughout the loop.
		handlerList[count] = end;
		handlerList[count].setNextNode(body[0]);
	}

	// ========================= IMPLEMENTATION: RequestHandler ============

	/**
	 * Returns the name of this handler.
	 * 
	 * @return the name of this handler.
	 */
	@Override
	public String getName()
	{
		return "Loop";
	}

	// ========================= METHODS ===================================

	/**
	 * Initialize handler chain. Useful when the chain depends on instance parameters and
	 * cannot be determined in static block.
	 * 
	 * @param inputHandlerList
	 *            list of handlers in this chain
	 */
	@Override
	protected void initialize(RequestHandler[] inputHandlerList)
	{
		throw new SystemException(
				"You must specify start and end handlers for a loop");
	}

	/**
	 * Initialize handler loop. Useful when the chain depends on instance parameters and
	 * cannot be determined in static block.
	 * 
	 * @param start
	 *            start handler for the loop; initializes loop counters
	 * @param body
	 *            A non-null List of loop body handlers in their order of invokation
	 * @param end
	 *            end handler for the loop; specifies the termination criterion
	 */
	final protected void initialize(RequestHandler start, RequestHandler[] body,
			RequestHandler end, RequestHandler nextNode)
	{

		handlerList = new RequestHandler[body.length + 2];

		// Initialize start handler references
		int count = 0;
		handlerList[count] = start;
		handlerList[count].setNextNode(body[0]);
		count++;

		// Initialize body handler references
		for (int i = 0; i <= body.length - 1; i++)
		{
			handlerList[count] = body[i];
			// The last body handler points to the end handler
			handlerList[count].setNextNode((i == body.length - 1) ? end : body[i + 1]);
			count++;
		}

		// Initialize end handler references; closes the loop. Note that
		// it goes back to the body, so the start handler is invoked only once
		// throughout the loop.
		handlerList[count] = end;
		handlerList[count].setNextNode(body[0]);

		this.setNextNode(nextNode);
	}

}
