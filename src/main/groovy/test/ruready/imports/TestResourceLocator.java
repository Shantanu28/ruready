/*****************************************************************************************
 * Source File: StandAloneResourceLocator.java
 ****************************************************************************************/
package test.ruready.imports;

import net.ruready.business.imports.StandAloneResourceLocator;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.web.common.rl.WebAppNames;

/**
 * A stand-alone resource locator for the testing package. Uses the default Hibernate
 * configuration.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 30, 2007
 */
public class TestResourceLocator extends StandAloneResourceLocator
{

	// ========================= CONSTANTS =================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This is a singleton. Prevents instantiation.
	 */
	protected TestResourceLocator()
	{

	}

	/**
	 * Return the single instance of this type.
	 * 
	 * @return the single instance of this type
	 */
	public static ResourceLocator getInstance()
	{
		if ((instance == null) || !instance.isReady())
		{
			synchronized (TestResourceLocator.class)
			{
				if ((instance == null) || !instance.isReady())
				{
					if (instance == null)
					{
						instance = new TestResourceLocator();
					}
					// instance exists but is not ready yet, so set it up.
					// Load from properties files whose name is hard-coded in
					// the Names class
					instance.setUp(WebAppNames.RESOURCE_LOCATOR.TEST_CONFIG_FILE);
				}
			}
		}
		return instance;
	}
}
