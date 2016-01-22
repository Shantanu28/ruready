/*******************************************************
 * Source File: SimpleMailTest.java
 *******************************************************/
package net.ruready.port.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.ruready.common.misc.Utility;
import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A simple mail test that can be run to determine if the JavaMail configuration
 * works properly. Sends an email from the help desk to a specified recipient.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Apr 18, 2007
 */
/**
 * Long description ...
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
public class SimpleMailTest implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleMailTest.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private SimpleMailTest()
	{

	}

	// ========================= METHODS ===================================

	/**
	 * Stand-alone mail test.
	 * 
	 * @param smtpHost
	 * @param smtpPort
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendStandAlone(String smtpHost, int smtpPort, String from,
			String to, String subject, String content) throws AddressException,
			MessagingException
	{
		// Create a mail session
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", CommonNames.MISC.EMPTY_STRING + smtpPort);
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);

		// Construct the message
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		msg.setText(content);
		msg.saveChanges();

		// Send the message
		Transport transport = session.getTransport("smtp");
		transport.connect(smtpHost, "rureadyhelp", "Ready4What");
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();

		// Transport.send(msg);
	}

	/**
	 * Sendmail test without SSL.
	 * 
	 * @param smtpHost
	 * @param smtpPort
	 * @param user
	 * @param password
	 * @param from
	 * @param recipients
	 * @param subject
	 * @param content
	 * @param attachedFileNames
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendUsingMailNoSSL(String smtpHost, int smtpPort, String user,
			String password, String from, String recipients[], String subject,
			String content, String[] attachedFileNames) throws AddressException,
			MessagingException
	{
		// Create a mail session
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.from", from);
		props.put("mail.user", user);
		props.put("mail.password", password);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.allow8bitmime", "true");
		// props.put("mail.smtp.ehlo", "true");
		// props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "false");
		// props.put("mail.net.ruready.block", "true");

		Session session = Session.getDefaultInstance(props, null);

		Mail.send(recipients, subject, content, attachedFileNames, session);
	}
}
