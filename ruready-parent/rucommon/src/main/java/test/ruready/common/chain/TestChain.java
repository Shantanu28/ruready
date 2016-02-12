/*****************************************************************************************
 * Source File: TestChain.java
 ****************************************************************************************/
package test.ruready.common.chain;

import net.ruready.common.chain.ChainRequest;
import net.ruready.common.chain.RequestHandler;
import net.ruready.common.chain.RequestHandlerChain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;
import test.ruready.common.rl.TestingNames;

/**
 * An example of how the chain of responsibility pattern works.
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
 * @version Sep 26, 2007
 */
public class TestChain extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestChain.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * 
	 */
	@Test
	public void testPurchaseChain() // throws IOException
	{
		PurchasePower manager = new Manager("Manager", 1000);
		PurchasePower director = new Manager("Director", 2000);
		PurchasePower vp = new Manager("Vice President", 4000);
		PurchasePower president = new Manager("President", 8000);

		// PurchasePower noApproval = new ApprovalDenied();
		// RequestHandler[] handlers = { manager, director, vp, president,
		// noApproval };
		RequestHandler[] handlers =
		{ manager, director, vp, president };
		RequestHandler chain = new RequestHandlerChain(handlers, null);

		double[] amounts =
		{ 500, 1000, 2000, 4000, 7999, 8000 };
		String[] expectedApproved =
		{ "Manager", "Director", "Vice President", "President", "President", null };

		for (int i = 0; i < amounts.length; i++)
		{
			double amount = amounts[i];
			ChainRequest request = new PurchaseRequest(amount);
			boolean handled = chain.run(request);
			String approved = (String) request
					.getAttribute(TestingNames.CHAIN.REQUEST.ATTRIBUTE.APPROVED);
			logger.info("amount " + amount + " approved " + approved
					+ " expectedApproved " + expectedApproved[i] + " handled " + handled);
			Assert.assertEquals(expectedApproved[i], approved);
			amount *= 2;
		}
	}
}
