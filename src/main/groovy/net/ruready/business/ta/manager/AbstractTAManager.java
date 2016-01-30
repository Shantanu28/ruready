package net.ruready.business.ta.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;
/**
 * Root manager for all test administration managers to extend from.
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
* @author Jeremy Lund
* @version Nov 15, 2007
*/
public abstract class AbstractTAManager
{
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Locator of the DAO factory that creates DAOs for TreeNodes.
	 */
	private final ResourceLocator resourceLocator;

	/**
	 * DAO factory, obtained from the resource locator.
	 */
	private final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects.
	 */
	private final ApplicationContext context;
	
	protected AbstractTAManager(
			final ResourceLocator resourceLocator,
			final ApplicationContext context)
	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
	}

	protected final ResourceLocator getResourceLocator()
	{
		return resourceLocator;
	}

	protected final AbstractEISManager getEisManager()
	{
		return eisManager;
	}

	protected final ApplicationContext getContext()
	{
		return context;
	}
}
