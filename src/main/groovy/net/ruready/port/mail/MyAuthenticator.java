/*****************************************************************************************
 * Source File: MyAuthenticator.java
 ****************************************************************************************/
package net.ruready.port.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import net.ruready.common.misc.Auxiliary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An authenticator required for secure e-mail sending using SSL protocols. Long
 * description ...
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
 * @version Aug 11, 2007
 */
class MyAuthenticator extends Authenticator implements Auxiliary
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(MyAuthenticator.class);

	// ========================= FIELDS ====================================

	// Username
	private String user;

	// Password
	private String password;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an authenticator
	 * 
	 * @param userName
	 *            username
	 * @param password
	 *            password
	 */
	public MyAuthenticator(String userName, String password)
	{
		super();
		this.user = userName;
		this.password = password;
	}

	// ========================= IMPLEMENTATION: Authenticator =============

	/**
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	@Override
	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(user, password);
	}
}
