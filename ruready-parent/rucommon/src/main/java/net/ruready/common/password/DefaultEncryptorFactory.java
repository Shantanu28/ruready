/*****************************************************************************************
 * Source File: DefaultEncryptorFactory.java
 ****************************************************************************************/
package net.ruready.common.password;

import net.ruready.common.exception.SystemException;
import net.ruready.common.rl.CommonNames;

/**
 * An simple factory that instantiates different encryptor service types within this JVM.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E University
 *         of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 2006-07
 *         Continuing Education , University of Utah . All copyrights reserved. U.S.
 *         Patent Pending DOCKET NO. 00846 25702.PROV
 * @version May 14, 2007
 */
public class DefaultEncryptorFactory implements EncryptorFactory
{
	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a parser service provider that instantiates different parser service types
	 * within this JVM.
	 */
	public DefaultEncryptorFactory()
	{
		super();
	}

	// ========================= IMPLEMENTATION: EncryptorFactory ==========

	/**
	 * @param identifier
	 * @param args
	 * @return
	 * @see net.ruready.common.discrete.EnumeratedFactory#createType(net.ruready.common.discrete.Identifier,
	 *      java.lang.Object[])
	 */
	public Encryptor createType(EncryptorID identifier, Object... args)
	{
		switch (identifier)
		{
			// No encryption (passwords are displayed in clear-text)
			case NONE:
			{
				return new NoEncryption();
			}

				// RSA encryption; uses common component constants for key generation
			case RSA:
			{
				return new BigRSA(CommonNames.ENRCYPTOR.BIG_RSA.RSA_PRIME_P,
						CommonNames.ENRCYPTOR.BIG_RSA.RSA_PRIME_Q,
						CommonNames.ENRCYPTOR.BIG_RSA.PUBLIC_KEY);
			}

			default:
			{
				throw new SystemException(
						"Unsupported parser service type " + identifier);
			}
		} // switch (identifier)
	}
	// ========================= FIELDS ====================================

}
