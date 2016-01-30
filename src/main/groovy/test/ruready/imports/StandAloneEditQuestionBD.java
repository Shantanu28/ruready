/*****************************************************************************************
 * Source File: StandAloneEditQuestionBD.java
 ****************************************************************************************/
package test.ruready.imports;

import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.exports.DefaultEditQuestionBD;
import net.ruready.business.content.question.manager.DefaultEditQuestionManager;
import net.ruready.business.imports.StandAloneResourceLocator;
import net.ruready.business.user.entity.User;
import net.ruready.common.misc.Singleton;
import test.ruready.rl.StandAloneApplicationContext;

/**
 * A custom item editing business delegate for {@link Question} types.
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
 * @author Greg Felice <code>&lt;greg.felice@gmail.com&gt;</code>
 * @version Jul 21, 2007
 */
public class StandAloneEditQuestionBD extends DefaultEditQuestionBD implements Singleton
{
	// ========================= CONSTANTS =================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a business delegate for this user.
	 * 
	 * @param user
	 *            user requesting the operations
	 * @param context
	 *            stand-alone application context
	 */
	public StandAloneEditQuestionBD(final StandAloneApplicationContext context,
			final User user)
	{
		super(new DefaultEditQuestionManager(StandAloneResourceLocator.getInstance(),
				context, user), StandAloneResourceLocator.getInstance());
	}
}
