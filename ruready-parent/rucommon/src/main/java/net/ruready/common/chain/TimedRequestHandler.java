/*****************************************************************************************
 * Source File: TimedRequestHandler.java
 ****************************************************************************************/
package net.ruready.common.chain;

import net.ruready.common.time.SimpleTimer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.commsen.stopwatch.Report;
import com.commsen.stopwatch.Stopwatch;

/**
 * A deocrator of a {@link RequestHandler} that times the request processing time.
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
public class TimedRequestHandler extends RequestHandler
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TimedRequestHandler.class);

	// ========================= FIELDS ====================================

	// The object to wrap
	private final RequestHandler rh;

	// Records processing time
	private SimpleTimer timer = new SimpleTimer();

	private double elapsedTime;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a timer decorator of a request handler
	 * 
	 * @param rh
	 *            The object to wrap
	 */
	public TimedRequestHandler(final RequestHandler rh)
	{
		super(rh.getNextNode());
		this.rh = rh;
	}

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Process the request. Called by <code>run()</code>.
	 * 
	 * @param request
	 *            the request object
	 * @return a boolean indicates whether this node handled the request. If it is
	 *         <code>true</code>, the request processing chain will terminate
	 */
	@Override
	protected final boolean handle(ChainRequest request)
	{
		return rh.handle(request);
	}

	/**
	 * Returns the name of this handler.
	 * 
	 * @return the name of this handler.
	 */
	@Override
	public String getName()
	{
		return /* ParserNames.HANDLER.DECORATOR.NAME + CommonNames.MISC.TAB_CHAR + */rh
				.getName();
	}

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
	@Override
	public final boolean run(ChainRequest request)
	{
		this.timer.start();
		// TODO: replace start() parameters below by something more intelligent
		// and less hard-coded
		long watchId = Stopwatch.start("my group", "my label");

		boolean handledByThisNode = this.handle(request);

		Stopwatch.stop(watchId);
		Report report = Stopwatch.getSingleReport("my group", "my label");

		// Returns the average elapsed time of all calls to the handle() code
		// since the application comes up
		// this.elapsedTime = report.getAverageTime(); // report.getTotalTime();
		logger.debug("Average analysis time (Stopwatch): " + report.getAverageTime());

		this.timer.stop();
		this.elapsedTime = this.timer.getElapsedTime();

		if (this.getNextNode() != null && !handledByThisNode)
		{
			return this.getNextNode().run(request);
		}
		else
		{
			return handledByThisNode;
		}
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the elapsedTime
	 */
	public double getElapsedTime()
	{
		return elapsedTime;
	}

}
