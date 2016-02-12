/*****************************************************************************************
 * Source File: RSA.java
 ****************************************************************************************/
package net.ruready.common.password;

import net.ruready.common.math.basic.Bytes;
import net.ruready.common.math.basic.NumberUtil;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Sample of how RSA encryption/decryption algorithm works. See
 * http://en.wikipedia.org/wiki/RSA . Limited to small messages and keys.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 25, 2007
 */
public class RSA implements Encryptor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RSA.class);

	// Generated once and used for all student certificate
	// ID encryption/decryption. Later may consider
	// using larger primes. We hold both private, public keys.
	private static final long OUR_P = 26029; // prime

	private static final long OUR_Q = 28807; // prime

	private static long OUR_N, OUR_D, OUR_E;

	// Generate keys from hard-coded primes
	static
	{
		long x = OUR_P;
		long y = OUR_Q;
		long p, q, n, nPrime, e, d;
		for (p = x; !NumberUtil.isPrime(p); p++)
		{
		}
		for (q = y + 2; !NumberUtil.isPrime(q); q++)
		{
		}
		n = p * q;
		nPrime = (p - 1) * (q - 1);
		for (e = nPrime / 10; NumberUtil.gcd(e, nPrime) != 1; e++)
		{
		}
		d = NumberUtil.inverse(e, nPrime);
		OUR_N = n;
		OUR_E = e;
		OUR_D = d;
	}

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Library; cannot be instantiated
	 */
	public RSA()
	{

	}

	// ========================= IMPLEMENTATION: Identifiable =================

	/**
	 * @see net.ruready.common.discrete.Identifier#getType()
	 */
	public String getType()
	{
		// return getIdentifier().getType();
		// This class is not part of the active Encryptor hierarchy.
		return null;
	}

	/**
	 * @see net.ruready.common.discrete.Identifiable#getIdentifier()
	 */
	public EncryptorID getIdentifier()
	{
		// return NodeMatchType.RSA;
		// This class is not part of the active Encryptor hierarchy.
		return null;
	}

	// ========================= IMPLEMENTATION: Encryptor =================

	/**
	 * Encrypt a string. Currently broken.
	 * 
	 * @param message
	 *            clear text (string)
	 * @return encrypted text (string, but really a number)
	 */
	@Deprecated
	public String encrypt(final String message)
	{
		StringBuffer encodedMessage = TextUtil.emptyStringBuffer();
		// Break string into 8-byte chunks an encoding each one separately
		for (int i = 0; i < message.length(); i += 8)
		{
			// Extract chunk
			StringBuffer chunk = new StringBuffer(message.substring(i));
			if (chunk.length() < 8)
			{
				for (int j = chunk.length(); j < 8; j++)
				{
					chunk.append('\000');
				}
			}
			// Convert chunk string to long value
			long chunkValue = Bytes.toLong(chunk.toString().getBytes());
			// Encode chunk value
			long encodedChunkValue = encrypt(chunkValue);
			// Convert encoded value to string
			String encodedChunk = new String(Bytes.toBytes(encodedChunkValue));
			// Append to cypher text
			encodedMessage.append(encodedChunk);
			logger.debug("encode(): chunk #" + i + ": " + chunk + " chunkValue "
					+ chunkValue + " encrypted value " + encodedChunkValue
					+ " encrypted text " + encodedChunk);
		}

		logger
				.debug("encode(): message " + message + " encodedMessage "
						+ encodedMessage);
		return encodedMessage.toString();
	}

	/**
	 * Decrypt a string. Currently broken
	 * 
	 * @param encrypted
	 *            encrypted text (string, but really a number)
	 * @return clear text (string). If failed to decode, returns <code>null</code>.
	 */
	@Deprecated
	public String decrypt(final String encrypted)
	{
		StringBuffer message = TextUtil.emptyStringBuffer();
		// Break string into 8-byte chunks an encoding each one separately
		for (int i = 0; i < encrypted.length(); i += 8)
		{
			// Extract chunk
			String chunk = encrypted.substring(i);
			// Convert chunk string to long value
			long chunkValue = Bytes.toLong(chunk.getBytes());
			// Decode chunk value
			long decodedChunkValue = decrypt(chunkValue);
			// Convert encoded value to string
			String decodedChunk = new String(Bytes.toBytes(decodedChunkValue));
			// Append to cypher text
			message.append(decodedChunk);
			logger.debug("encode(): chunk #" + i + ": " + chunk + " chunkValue "
					+ chunkValue + " decrypted value " + decodedChunkValue
					+ " decrypted text " + decodedChunk);
		}

		logger.debug("decode(): encrypted " + encrypted + " message " + message);
		return message.toString();
	}

	/**
	 * Does a trial message match a stored encrypted message. This is equivalent to
	 * <code>encryptedMessage.equals(encrypt(trialMessage))</code>.
	 * 
	 * @param trialMessage
	 *            trial message to be encrypted and compared with the encrypted message
	 *            parameter
	 * @param encryptedMessage
	 *            encrypted text (must be non-null)
	 * @return Does a trial message match a stored encrypted message.
	 */
	public boolean isMatch(final String trialMessage, final String encryptedMessage)
	{
		return (encryptedMessage == null) ? false : encryptedMessage
				.equals(encrypt(trialMessage));
	}

	// ========================= METHODS ===================================

	/**
	 * Encode a number.
	 * 
	 * @param message
	 *            clear text
	 * @return encrypted text
	 */
	public long encrypt(final long message)
	{
		return NumberUtil.power(message, OUR_E, OUR_N);
	}

	/**
	 * Decode a number.
	 * 
	 * @param encrypted
	 *            encrypted text
	 * @return clear text
	 */
	public long decrypt(final long encrypted)
	{
		return NumberUtil.power(encrypted, OUR_D, OUR_N);
	}
}
