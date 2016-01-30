/*****************************************************************************************
 * Source File: Mail.java
 ****************************************************************************************/
package net.ruready.port.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An e-mail sender. Uses the JavaMail API.
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
public class Mail
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Mail.class);

	// ========================= METHODS ===================================

	/**
	 * Send a test mail message.
	 */
	public static boolean mailTest(Session session)
	{
		String helpEmail = session.getProperty("mail.net.ruready.helpemail");
		if (TextUtil.isEmptyString(helpEmail))
		{
			logger.info("Skipping email test");
			return true;
		}
		String[] recipients =
		{
			helpEmail
		};
		String attachedFileNames[] = {};
		String subject = "Test Message";
		String content = "If you see this, it means that the resource locator is properly configured to send e-mails.";
		logger.info("Sending test e-mail");
		try
		{
			Mail.send(recipients, subject, content, attachedFileNames, session);
			return true;
		}
		catch (MessagingException e)
		{
			logger.warn("Mail Test Failed, exception: " + e.toString());
			return false;
		}
	}

	/**
	 * Send an email message to a list of email addresses.
	 * 
	 * @param hostName
	 *            SMTP host name
	 * @param fromAddress
	 *            Address of email sender.
	 * @param recipients
	 *            List of recipient email addresses.
	 * @param subject
	 *            Subject of email message.
	 * @param message
	 *            Body of email message.
	 * @param debug
	 *            Debug flag for mail sending
	 */
	public static void send(String recipients[], String subject, String content,
			String attachedFileName[], Session session)
		throws AddressException, MessagingException
	{

		// Set custom session flags
		boolean block = Boolean.parseBoolean(session
				.getProperty("mail.net.ruready.block"));
		if (block)
		{
			logger.debug("Recipient was <" + recipients[0] + ">, but mail is blocked.");
			return;
		}

		logger.debug("Sending, recipient to <" + recipients[0] + ">");

		// Useful aliases for session properties
		String fromAddress = session.getProperty("mail.from");

		// Construct the message
		Message msg = (attachedFileName == null || attachedFileName.length == 0) ? constructMessage(
				fromAddress, recipients, subject, content, session.getDebug(), session)
				: constructMessage(fromAddress, recipients, subject, content,
						attachedFileName, session.getDebug(), session);

		// Send the message
		Transport transport = session.getTransport();
		transport.connect(session.getProperty("mail.host"), session
				.getProperty("mail.user"), session.getProperty("mail.password"));
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();

		logger.debug("Sending email completed");
	}

	/**
	 * Construct an email message.
	 * 
	 * @param fromAddress
	 *            Address of email sender.
	 * @param recipients
	 *            List of recipient email addresses.
	 * @param subject
	 *            Subject of email message.
	 * @param content
	 *            Body of email message.
	 * @param debug
	 *            Debug flag for mail sending
	 * @return email message object
	 */
	protected static Message constructMessage(String fromAddress, String recipients[],
			String subject, String content, boolean debug, Session ses)
		throws AddressException, MessagingException
	{
		// Construct the message
		Message msg = new MimeMessage(ses);
		msg.setFrom(new InternetAddress(fromAddress));
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++)
		{
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		/*
		 * if (cc != null) { msg.setRecipients(AbstractMessage.RecipientType.CC
		 * ,InternetAddress.parse(cc, false)); } if (bcc != null) {
		 * msg.setRecipients(AbstractMessage.RecipientType.BCC ,InternetAddress.parse(bcc,
		 * false)); } if (htmlFormat) { msg.setContent(body, "text/html"); } else {
		 * msg.setContent(body, "text/plain"); }
		 */

		msg.setContent(content, "text/plain");
		msg.setSubject(subject);

		// Optional : You can also set your custom headers in the Email if you
		// Want
		// msg.addHeader("MyHeaderName", "myHeaderValue");

		msg.saveChanges();
		return msg;
	}

	/**
	 * Construct an email message with an attachement.
	 * 
	 * @param fromAddress
	 *            Address of email sender.
	 * @param recipients
	 *            List of recipient email addresses.
	 * @param subject
	 *            Subject of email message.
	 * @param content
	 *            Body of email message.
	 * @param debug
	 *            Debug flag for mail sending
	 * @return email message object
	 */
	protected static Message constructMessage(String fromAddress, String recipients[],
			String subject, String content, String attachedFileName[], boolean debug,
			Session ses) throws AddressException, MessagingException
	{
		// Construct the message
		MimeMessage msg = new MimeMessage(ses);
		msg.setFrom(new InternetAddress(fromAddress));
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++)
		{
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setContent(content, "text/plain");
		msg.setSubject(subject);
		Multipart multipart = new MimeMultipart();

		// Part 1: text content
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(content);
		multipart.addBodyPart(messageBodyPart);

		// Part 2-(n+1): n attached files
		for (int i = 0; i < attachedFileName.length; i++)
		{
			String fileAttachment = attachedFileName[i];
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(fileAttachment);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileAttachment);
			multipart.addBodyPart(messageBodyPart);
		}

		// Put parts in message
		msg.setContent(multipart);
		msg.saveChanges();
		return msg;
	}

	class MyPasswordAuthenticator extends Authenticator
	{
		String user;

		String pw;

		public MyPasswordAuthenticator(String userName, String password)
		{
			super();
			this.user = userName;
			this.pw = password;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(user, pw);
		}
	}

	/**
	 * Validate the form of an email address.
	 * 
	 * @return <code>true</code> if and only if the email address is valid according to
	 *         Internet RFC 822.
	 */
	public static boolean isValidEmailAddress(String aEmailAddress)
	{
		if (aEmailAddress == null)
			return false;
		boolean result = true;
		try
		{
			InternetAddress emailAddr = new InternetAddress(aEmailAddress);
			emailAddr.validate();
			result = true;
		}
		catch (AddressException ex)
		{
			result = false;
		}
		return result;
	}
}
