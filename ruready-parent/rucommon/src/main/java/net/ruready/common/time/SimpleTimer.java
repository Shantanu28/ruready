/*******************************************************
 * Source File: SimpleTimer.java
 *******************************************************/
package net.ruready.common.time;

import java.util.Date;

import net.ruready.common.chain.RequestHandlerChain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Records time spent in various parts in the code.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 18/11/2005
 */
public class SimpleTimer
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RequestHandlerChain.class);

	// ========================= FIELDS ====================================

	private Date startTime;

	private Date stopTime;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * 
	 */
	public SimpleTimer()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Start the timer. Sets the start time to now.
	 */
	public void start()
	{
		startTime = new Date();
	}

	/**
	 * Stop the timer. Sets the stop time to now.
	 */
	public void stop()
	{
		stopTime = new Date();
	}

	/**
	 * Get the elapsed time between the last start and stop of the timer.
	 * 
	 * @return elapsed time between the last start and stop of the timer
	 *         [seconds]
	 */
	public double getElapsedTime()
	{
		return (stopTime.getTime() - startTime.getTime()) / 1000.0;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * @return the startTime
	 */
	public Date getStartTime()
	{
		return startTime;
	}

	/**
	 * @return the stopTime
	 */
	public Date getStopTime()
	{
		return stopTime;
	}

}
