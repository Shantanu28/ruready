/*****************************************************************************************
 * Source File: DefaultEncryptorFactory.java
 ****************************************************************************************/
package net.ruready.business.user.entity.rsa;

import net.ruready.business.user.rl.UserNames;
import net.ruready.common.exception.SystemException;
import net.ruready.common.password.BigRSA;
import net.ruready.common.password.Encryptor;
import net.ruready.common.password.EncryptorFactory;
import net.ruready.common.password.EncryptorID;
import net.ruready.common.password.NoEncryption;

/**
 * An simple factory that instantiates different encryption services within this
 * JVM.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 25, 2007
 */
public class UserEncryptorFactory implements EncryptorFactory
{
	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a parser service provider that instantiates different parser
	 * service types within this JVM.
	 */
	public UserEncryptorFactory()
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
	public Encryptor createType(EncryptorID identifier, final Object... args)
	{
		switch (identifier)
		{
			// No encryption (passwords are displayed in clear-text)
			case NONE:
			{
				return new NoEncryption();
			}

				// RSA encryption; uses common component constants for key
				// generation
			case RSA:
			{
				return new BigRSA(UserNames.PASSWORD.RSA_PRIME_P,
						UserNames.PASSWORD.RSA_PRIME_Q, UserNames.PASSWORD.PUBLIC_KEY);
			}

			default:
			{
				throw new SystemException("Unsupported parser service type " + identifier);
			}
		} // switch (identifier)
	}
	// ========================= FIELDS ====================================

}
