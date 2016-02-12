/*****************************************************************************************
 * Source File: EmailSMS.java
 ****************************************************************************************/
package test.ruready.port.sms;

// Package Imports
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test sending an Email/SMS from the web appication to a cell phone.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class TestEmailSMS extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestEmailSMS.class);

	// ========================= FIELDS ====================================

	// Global Variables

	/**
	 * Recipient address.
	 */
	private String TO;

	/**
	 * Sender's address.
	 */
	private String FROM;

	/**
	 * Subject of the message.
	 */
	private String SUBJECT;

	/**
	 * Message content.
	 */
	private String TEXT;

	/**
	 * Name of mail host.
	 */
	private String MAILHOST;

	/**
	 * Last error reported by mailer.
	 */
	private String LASTERROR = "No method called.";

	// ========================= TESTING METHODS ===========================

	/**
	 * Just a dummy method, otherwise JUnit complains that it doesn't find any
	 * tests when testSMS is disabled below.
	 */
	@Test
	public void testDummy()
	{

	}

	/**
	 * Remove ignore annotation to testSMS to activate. This will send an SMS
	 * message to Oren's cell phone.
	 */
	@Ignore
	public void testSMS()
	{
		setMailHost("smtpsp1.vtext.com");
		setTo("8016313885@vtext.com");
		setFrom("help@ruready.utah.edu");
		setSubject("Hi from Web Application");
		setText("Hello World!");
		boolean ret = send();

		if (ret)
		{
			logger.info("SMS was sent!");
		}
		else
		{
			logger.info("SMS was not sent - " + getLastError());
		}
	}

	// ========================= GETTERS & SETTERS =========================

	public void setTo(String to)
	{
		TO = to;
	}

	public String getTo()
	{
		return TO;
	}

	public void setFrom(String from)
	{
		FROM = from;
	}

	public String getFrom()
	{
		return FROM;
	}

	public void setSubject(String subject)
	{
		SUBJECT = subject;
	}

	public String getSubject()
	{
		return SUBJECT;
	}

	public void setText(String text)
	{
		TEXT = text;
	}

	public String getText()
	{
		return TEXT;
	}

	public void setMailHost(String host)
	{
		MAILHOST = host;
	}

	public String getMailHost()
	{
		return MAILHOST;
	}

	public String getLastError()
	{
		return LASTERROR;
	}

	/**
	 * Will attempt to send the Email SMS and return a boolean meaning it either
	 * failed or succeeded.
	 * 
	 * @return
	 */
	public boolean send()
	{

		// Variables to check message length.
		int maxLength;
		int msgLength;

		// Check to make sure that the parameters are correct
		if (TO.indexOf("mobile.att.net") > 0)
		{
			maxLength = 140;
		}
		else if (TO.indexOf("messaging.nextel.com") > 0)
		{
			maxLength = 280;
		}
		else if (TO.indexOf("messaging.sprintpcs.com") > 0)
		{
			maxLength = 100;
		}
		else
		{
			maxLength = 160;
		}

		// Calculate message length
		msgLength = FROM.length() + 1 + SUBJECT.length() + 1 + TEXT.length();

		// Typically, there are at least two characters of delimiter
		// between the from, subject, and text. This is here to make
		// sure the message isn't longer than the device supports.
		if (msgLength > maxLength)
		{
			LASTERROR = "SMS length too long.";
			return false;
		}

		// Set Email Properties
		Properties props = System.getProperties();

		if (MAILHOST != null)
		{
			props.put("mail.smtp.host", MAILHOST);
		}

		// Get a Session object
		Session session = Session.getDefaultInstance(props, null);

		try
		{

			// Construct the email
			Message msg = new MimeMessage(session);

			// Set From
			if (FROM != null)
			{
				msg.setFrom(new InternetAddress(FROM));
			}
			else
			{
				msg.setFrom();
			}

			// Set Subject
			msg.setSubject(SUBJECT);

			// Set Text
			msg.setText(TEXT);

			// Add Recipient
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO, false));

			// Sent Date
			msg.setSentDate(new Date());

			// Send Email SMS
			Transport.send(msg);

			LASTERROR = "Success.";
			return true;
		}
		catch (MessagingException mex)
		{
			LASTERROR = mex.getMessage();
			return false;
		}
	}
}
