/*******************************************************
 * Source File: NoEncryption.java
 *******************************************************/
package net.ruready.common.password;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * An implementation of a trivial encryptor whose cypher text equals the clear
 * text.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Mar 31, 2007
 */
public class NoEncryption implements Encryptor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(NoEncryption.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize the encryption service.
	 */
	public NoEncryption()
	{

	}

	// ========================= IMPLEMENTATION: Identifiable =================

	/**
	 * @see net.ruready.common.discrete.Identifier#getType()
	 */
	public String getType()
	{
		return getIdentifier().getType();
	}

	/**
	 * @see net.ruready.common.discrete.Identifiable#getIdentifier()
	 */
	public EncryptorID getIdentifier()
	{
		return EncryptorID.NONE;
	}

	// ========================= IMPLEMENTATION: Encryptor =================

	/**
	 * @see net.ruready.common.password.Encryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String message)
	{
		return message;
	}

	/**
	 * @see net.ruready.common.password.Encryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String encrypted)
	{
		return encrypted;
	}

	/**
	 * Does a trial message match a stored encrypted message. This is equivalent
	 * to <code>encryptedMessage.equals(encrypt(trialMessage))</code>.
	 * 
	 * @param trialMessage
	 *            trial message to be encrypted and compared with the encrypted
	 *            message parameter
	 * @param encryptedMessage
	 *            encrypted text (must be non-null)
	 * @return Does a trial message match a stored encrypted message.
	 */
	public boolean isMatch(final String trialMessage, final String encryptedMessage)
	{
		return (encryptedMessage == null) ? false : encryptedMessage.equals(encrypt(trialMessage));
	}

}
