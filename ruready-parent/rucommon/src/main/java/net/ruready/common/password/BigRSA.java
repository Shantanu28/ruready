/*****************************************************************************************
 * Source File: BigRSA.java
 ****************************************************************************************/
package net.ruready.common.password;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*****************************************************************************************
 * RSA encryption implementation based on BigIntegers. Retrieved from
 * http://www.cs.princeton.edu/introcs/79crypto/RSA.java.html and modified to encrypt
 * Strings as well.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Mar 31, 2007
 *          ******************************************************************************
 *          Compilation: javac RSA.java Execution: java RSA N Generate an N-bit public and
 *          private RSA key and use to encrypt and decrypt a random message. % java RSA 50
 *          public = 65537 private = 553699199426609 modulus = 825641896390631 message =
 *          48194775244950 encrpyted = 321340212160104 decrypted = 48194775244950 Known
 *          bugs (not addressed for simplicity) ----------------------------------------- -
 *          It could be the case that the message >= modulus. To avoid, use a do-while
 *          loop to generate key until modulus happen to be exactly N bits. - It's
 *          possible that gcd(phi, publicKey) != 1 in which case the key generation fails.
 *          This will only happen if phi is a multiple of 65537. To avoid, use a do-while
 *          loop to generate keys until the gcd is 1.
 ****************************************************************************************/
public class BigRSA implements Encryptor
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(BigRSA.class);

	private final static BigInteger ONE = new BigInteger("1");

	private final static SecureRandom random = new SecureRandom();

	private final static String TEST_MESSAGE = "test2";

	private final static int MAX_INITIALIZATION_TRIES = 10;

	// ========================= FIELDS ====================================

	private BigInteger privateKey;

	private BigInteger publicKey;

	private BigInteger modulus;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Generate an N-bit (roughly) public and private key.
	 * 
	 * @param N
	 *            key length
	 * @param publicKey
	 *            public key
	 */
	public BigRSA(final int N, final BigInteger publicKey)
	{
		logger.trace("Initializing, N " + N + " publicKey " + publicKey);
		boolean success = false;
		for (int i = 0; i < MAX_INITIALIZATION_TRIES; i++)
		{
			BigInteger p = BigInteger.probablePrime(N / 2, random);
			BigInteger q = BigInteger.probablePrime(N / 2, random);
			// logger.debug(p.isProbablePrime(15));
			// logger.debug(q.isProbablePrime(15));

			generateKeys(p, q, publicKey);
			logger.trace("i " + i + " p " + p + " q " + q);
			if (testEncryption())
			{
				// Test succeeded, stop trying
				success = true;
				break;
			}
		}
		if (!success)
		{
			logger.warn("Could not find good encryption keys");
		}
	}

	/**
	 * Initialize with prescribed primes.
	 * 
	 * @param p
	 *            prime #1
	 * @param q
	 *            prime #2
	 * @param publicKey
	 *            public key
	 */
	public BigRSA(final BigInteger p, final BigInteger q, final BigInteger publicKey)
	{
		generateKeys(p, q, publicKey);
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
		return EncryptorID.RSA;
	}

	// ========================= IMPLEMENTATION: Encryptor =================

	/**
	 * @see net.ruready.common.password.Encryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String message)
	{
		// ----------------------------------------------------------------------------------
		// WARNING: DIRECT GETBYTES() CONVERSIONS ARE NOT PORTABLE ACROSS PLATFORMS DUE TO
		// LITTLE/BIG-ENDIAN ENCODING OF GETBYTES() INTO A STRING!!!! Will work on Windows
		// (big endian, I guess).
		// ----------------------------------------------------------------------------------

		// Convert String to long using base-64 safe conversions
		Base64 base64 = new Base64();
		byte[] messageBytes = base64.encode(message.getBytes());
		BigInteger messageValue = new BigInteger(messageBytes);
		// Encrypt
		BigInteger encryptedValue = encrypt(messageValue);
		// Convert long to String using base-64 safe conversions
		byte[] encryptedBytes = encryptedValue.toByteArray();
		String encrypted = new String(base64.encode(encryptedBytes));

		// logger.trace("encrypt():");
		// logger.trace("message " + message);
		// logger.trace("messageBytes " + ArrayUtil.arrayToString(messageBytes));
		// logger.trace("messageValue " + messageValue);
		// logger.trace("encryptedValue " + encryptedValue);
		// logger.trace("encryptedBytes " + ArrayUtil.arrayToString(encryptedBytes));
		// logger.trace("encrypted " + encrypted);

		return encrypted;
	}

	/**
	 * @see net.ruready.common.password.Encryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String encrypted)
	{
		// ----------------------------------------------------------------------------------
		// WARNING: DIRECT GETBYTES() CONVERSIONS ARE NOT PORTABLE ACROSS PLATFORMS DUE TO
		// LITTLE/BIG-ENDIAN ENCODING OF GETBYTES() INTO A STRING!!!! Will work on Windows
		// (big endian, I guess).
		// ----------------------------------------------------------------------------------

		// Convert String to long using base-64 safe conversions
		// logger.trace("decrypt():");
		// logger.trace("encrypted " + encrypted);
		Base64 base64 = new Base64();
		byte[] encryptedBytes = base64.decode(encrypted.getBytes());
		// logger.trace("encryptedBytes " + ArrayUtil.arrayToString(encryptedBytes));
		BigInteger encryptedValue = new BigInteger(encryptedBytes);
		// logger.trace("encryptedValue " + encryptedValue);

		// Decrypt
		BigInteger messageValue = decrypt(encryptedValue);
		// logger.trace("messageValue " + messageValue);

		// Convert long to String using base-64 safe conversions
		byte[] messageBytes = messageValue.toByteArray();
		// logger.trace("messageBytes " + ArrayUtil.arrayToString(messageBytes));
		String message = new String(base64.decode(messageBytes));
		// logger.trace("message " + message);

		return message;
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
	 * Generated keys from primes.
	 * 
	 * @param p
	 *            prime #1
	 * @param q
	 *            prime #2
	 * @param publicKey
	 *            public key
	 */
	private void generateKeys(BigInteger p, BigInteger q, BigInteger pK)
	{
		// logger.trace("--- generate keys ---");
		// logger.trace("p " + p);
		// logger.debug("q " + q);
		BigInteger phi = (p.subtract(ONE)).multiply(q.subtract(ONE));
		// logger.trace("phi " + phi);
		this.modulus = p.multiply(q);
		// logger.trace("modulus " + modulus);
		this.publicKey = pK;
		// logger.trace("publicKey " + publicKey);
		this.privateKey = publicKey.modInverse(phi);
		// logger.trace("privateKey " + privateKey);
		// logger.debug("p " + p + " q " + q + " " + this);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "public " + publicKey + " private " + privateKey + " modulus " + modulus;
	}

	/**
	 * @param message
	 * @return
	 */
	private BigInteger encrypt(BigInteger message)
	{
		return message.modPow(publicKey, modulus);
	}

	/**
	 * @param encrypted
	 * @return
	 */
	private BigInteger decrypt(BigInteger encrypted)
	{
		return encrypted.modPow(privateKey, modulus);
	}

	/**
	 * Test encryption and description with the chosen keys.
	 * 
	 * @return success code
	 */
	private boolean testEncryption()
	{
		String message = TEST_MESSAGE;
		// Key size has to be large enough for this to work.
		String code = this.encrypt(message);
		String decode = this.decrypt(code);

		if (!message.equals(decode))
		{
			logger.trace("OOPS: " + message + " " + decode);
		}
		else
		{
			logger.trace("Success!!!!");
		}

		logger.info("String encoding: " + message + " " + code + " " + decode
				+ " test result " + message.equals(decode));

		return message.equals(decode);
	}

}
