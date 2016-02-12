/*******************************************************
 * Source File: Encryptor.java
 *******************************************************/
package net.ruready.common.password;

import net.ruready.common.discrete.Identifiable;

/**
 * An encryption service. Mostly used to protected user passwords.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112 Protected by U.S.
 *         Provisional Patent U-4003, February 2006
 * @version Jun 19, 2006
 */
public interface Encryptor extends Identifiable<EncryptorID>
{
	// ========================= CONSTANTS =================================

	// ========================= METGODS ===================================

	/**
	 * Encrypt a string.
	 * 
	 * @param message
	 *            clear text (string)
	 * @return encrypted text (string, but really a number)
	 */
	String encrypt(final String message);

	/**
	 * Decrypt a string.
	 * 
	 * @param encrypted
	 *            encrypted text (string, but really a number)
	 * @return clear text (string). If failed to decode, returns
	 *         <code>null</code>.
	 */
	String decrypt(final String encrypted);

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
	boolean isMatch(final String trialMessage, final String encryptedMessage);
}
