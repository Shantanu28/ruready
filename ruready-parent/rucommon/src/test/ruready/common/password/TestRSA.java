/*****************************************************************************************
 * Source File: TestRSA.java
 ****************************************************************************************/
package test.ruready.common.password;

import java.math.BigInteger;

import net.ruready.common.math.basic.NumberUtil;
import net.ruready.common.math.real.RandomUtil;
import net.ruready.common.password.BigRSA;
import net.ruready.common.password.Encryptor;
import net.ruready.common.password.RSA;
import net.ruready.common.password.RandPass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * An example of how RSA encryption works.
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
 * @version Sep 25, 2007
 */
public class TestRSA extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestRSA.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Manually go through the RSA algorithm steps.
	 */
	@Test
	public void testRSAManual()
	{
		logger.debug("=====================================================");
		logger.debug("testRSAManual");
		logger.debug("=====================================================");
		long x = RandomUtil.randomInInterval(25000, 30000); // 25000;
		long y = RandomUtil.randomInInterval(25000, 30000); // 30000;
		long message = 37373737; // 7017280452239032320l; //37373737;

		long p, q, n, nPrime, e, d;

		for (p = x; !NumberUtil.isPrime(p); p++)
		{
		}
		logger.info("p: " + p);
		for (q = y + 2; !NumberUtil.isPrime(q); q++)
		{
		}
		logger.info("q: " + q);

		n = p * q;
		logger.info("n: " + n);

		nPrime = (p - 1) * (q - 1);
		logger.info("nPrime: " + nPrime);

		for (e = nPrime / 10; NumberUtil.gcd(e, nPrime) != 1; e++)
		{
		}
		logger.info("e: " + e);

		d = NumberUtil.inverse(e, nPrime);
		logger.info("d: " + d);

		logger.info("Verify inverse: " + (e * d % (nPrime)));

		logger.info("message: " + message);
		long code = NumberUtil.power(message, e, n);
		long decode = NumberUtil.power(code, d, n);

		logger.info("Code: " + code);
		logger.info("Decode: " + decode);
		if (message != decode)
			logger.info("OOPS: ");
		else
			logger.info("Success!!!!");

		Assert.assertEquals(message, decode);
	}

	@Test
	public void testRSALong()
	{
		logger.debug("=====================================================");
		logger.debug("testRSALong");
		logger.debug("=====================================================");
		long message2 = 737373737; // 7017280452239032320l; //737373737;
		RSA rsa = new RSA();
		long code2 = rsa.encrypt(message2);
		long decode2 = rsa.decrypt(code2);

		if (message2 != decode2)
			logger.info("OOPS: " + message2 + " " + decode2);
		else
			logger.info("Success!!!!");

		Assert.assertEquals(message2, decode2);
	}

	/*
	 * @Test public void testRSAString() { // String encryption is broken in
	 * class RSA String message2 = "abcde"; Encryptor rsa = new RSA(); String
	 * code2 = rsa.encrypt( message2 ); String decode2 = rsa.decrypt( code2 );
	 * if( !message2.equals(decode2 )) logger.info( "OOPS: " + message2 + " " +
	 * decode2); else logger.info( "Success!!!!" ); logger.info( "String
	 * encoding: " + message2 + " " + code2 + " " + decode2);
	 * Assert.assertEquals(message2, decode2 ); }
	 */

	@Test
	public void testBigRSAString()
	{
		logger.debug("=====================================================");
		logger.debug("testBigRSAString");
		logger.debug("=====================================================");
		String message2 = "test2";

		// Public key (fixed). Using a common value in practice: 2^16 + 1.
		// Normally used with RSA encryption. Must be large enough to match
		// LENGTH_GENERATED, so that it can correctly encrypt and decrypt
		// all password up to that length.
		final BigInteger publicKey = new BigInteger("65537");
		// Key size has to be large enough for this to work.
		Encryptor rsa = new BigRSA(128, publicKey);

		String code2 = rsa.encrypt(message2);
		String decode2 = rsa.decrypt(code2);

		if (!message2.equals(decode2))
			logger.info("OOPS: " + message2 + " " + decode2);
		else
			logger.info("Success!!!!");

		logger.info("String encoding: " + message2 + " " + code2 + " " + decode2);

		Assert.assertEquals(message2, decode2);
	}

	@Test
	public void testBigRSAFixedKeys()
	{
		logger.debug("=====================================================");
		logger.debug("testBigRSAFixedKeys");
		logger.debug("=====================================================");
		// Public key (fixed). Using a common value in practice: 2^16 + 1.
		// Normally used with RSA encryption. Must be large enough to match
		// LENGTH_GENERATED, so that it can correctly encrypt and decrypt
		// all password up to that length.
		final BigInteger publicKey = new BigInteger("65537");

		// Manual p,q input; equivalent to N=128
		BigInteger p = new BigInteger("16321525218656177573");
		BigInteger q = new BigInteger("10428943764034699217");

		Encryptor key = new BigRSA(p, q, publicKey);

		logger.info(key);

		// create message
		String message2 = "1234abcd";
		String encrypt2 = key.encrypt(message2);
		String decrypt2 = key.decrypt(encrypt2);
		logger.info("message2   = " + message2);
		logger.info("encrypted2 = " + encrypt2);
		logger.info("decrypted2 = " + decrypt2);
		Assert.assertEquals(message2, decrypt2);
	}

	@Test
	public void testBigRSAHardWiredKeys()
	{
		logger.debug("=====================================================");
		logger.debug("testBigRSAHardWiredKeys");
		logger.debug("=====================================================");
		// -----------------------------------------------------------------------
		// Constants
		// -----------------------------------------------------------------------

		// Hard-wired primes for RSA private key generation . Match the
		// generated password length constant above. Equivalent to N=128.
		final BigInteger RSA_PRIME_P = new BigInteger("16321525218656177573");

		final BigInteger RSA_PRIME_Q = new BigInteger("10428943764034699217");

		// Public key (fixed). Using a common value in practice: 2^16 + 1.
		// Normally used with RSA encryption. Must be large enough to match
		// LENGTH_GENERATED, so that it can correctly encrypt and decrypt
		// all password up to that length.
		final BigInteger PUBLIC_KEY = new BigInteger("65537");

		// Manual p,q input; equivalent to N=128
		Encryptor key = new BigRSA(RSA_PRIME_P, RSA_PRIME_Q, PUBLIC_KEY);
		logger.info(key);

		// create message
		String message2 = "o3z90md3"; // "1234abcd";
		String encrypt2 = key.encrypt(message2);
		String decrypt2 = key.decrypt(encrypt2);
		logger.info("message2   = " + message2);
		logger.info("encrypted2 = " + encrypt2);
		logger.info("decrypted2 = " + decrypt2);
		Assert.assertEquals(message2, decrypt2);
	}

	/**
	 * Check the maximum length (30) of the cypher text postulated by the web
	 * application's resource files for an 8-character clear text input. The
	 * maximum length turns out to be 24 (experimental), so why not offer some
	 * slack (30).
	 */
	@Test
	public void testPasswordLength()
	{
		logger.debug("=====================================================");
		logger.debug("testPasswordLength");
		logger.debug("=====================================================");
		// -----------------------------------------------------------------------
		// Constants
		// -----------------------------------------------------------------------

		// Hard-wired primes for RSA private key generation . Match the
		// generated password length constant above.
		final BigInteger RSA_PRIME_P = new BigInteger("16321525218656177573");

		final BigInteger RSA_PRIME_Q = new BigInteger("10428943764034699217");

		// Public key (fixed). Using a common value in practice: 2^16 + 1.
		// Normally used with RSA encryption. Must be large enough to match
		// LENGTH_GENERATED, so that it can correctly encrypt and decrypt
		// all password up to that length.
		final BigInteger PUBLIC_KEY = new BigInteger("65537");

		// Manual p,q input; equivalent to N=128
		Encryptor key = new BigRSA(RSA_PRIME_P, RSA_PRIME_Q, PUBLIC_KEY);
		logger.info(key);

		// Create message
		final int clearTextLength = 8;
		final int expectedEncryptedLength = 30;
		int maxEncryptedLength = -1;
		for (int i = 0; i < 1000; i++)
		{
			// logger.info("Generating message");
			String message = new RandPass(RandPass.LOWERCASE_LETTERS_AND_NUMBERS_ALPHABET)
					.getPass(clearTextLength);// RandomUtil.randomLongPassword(clearTextLength);
			String encrypt = key.encrypt(message);
			String decrypt = key.decrypt(encrypt);
			// logger.info("message = " + message);
			// logger.info("encrypted = " + encrypt);
			// logger.info("decrypted = " + decrypt);
			Assert.assertEquals(message, decrypt);
			if (maxEncryptedLength < encrypt.length())
			{
				maxEncryptedLength = encrypt.length();
			}
		}
		logger.debug("Input     clearTextLength " + clearTextLength);
		logger.debug("Resulting maxEncryptedLength " + maxEncryptedLength);
		Assert.assertTrue(maxEncryptedLength <= expectedEncryptedLength);
	}

	// ========================= TESTING ====================================
}
