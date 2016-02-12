/*****************************************************************************************
 * Source File: DataTransmitter.java
 ****************************************************************************************/
package net.ruready.common.observer;

/**
 * A data transmitter that prepares concrete messages for a subject.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 31, 2007
 */
public class DataTransmitter extends SimpleSubject
{

	/**
	 * Create a transmitter for a subject.
	 * 
	 * @param subject
	 *            this object is usually composed with a transmitter
	 */
	public DataTransmitter(Subject subject)
	{
		super(subject);
	}

	/**
	 * Prepare a concrete message. This should use the subject's getters methods.
	 * 
	 * @return the message
	 * @see net.ruready.common.observer.SimpleSubject#prepareMessage()
	 */
	@Override
	protected Message prepareMessage()
	{
		Message message = new DataMessage(getSubject());
		return message;
	}

}
