/*****************************************************************************************
 * Source File: DefaultEncryptorFactory.java
 ****************************************************************************************/
package net.ruready.business.content.world.entity;

import net.ruready.business.content.item.entity.ItemType;
import net.ruready.common.discrete.EnumeratedFactory;
import net.ruready.common.exception.SystemException;

/**
 * An simple factory that instantiates different country types. service types within this
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 25, 2007
 */
public class CountryFactory implements EnumeratedFactory<ItemType, Country>
{
	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a parser service provider that instantiates different parser service types
	 * within this JVM.
	 */
	public CountryFactory()
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
	public Country createType(ItemType identifier, final Object... args)
	{
		switch (identifier)
		{
			case COUNTRY:
			{
				return new Country((String) args[0], (String) args[1],
						((Integer) args[2]).intValue());
			}
			case FEDERATION:
			{
				return new Federation((String) args[0], (String) args[1],
						((Integer) args[2]).intValue());
			}

			default:
			{
				throw new SystemException("Unsupported country type " + identifier);
			}
		} // switch (identifier)
	}
	
	// ========================= FIELDS ====================================

}
