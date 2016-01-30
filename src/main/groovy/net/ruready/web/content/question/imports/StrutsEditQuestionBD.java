/*****************************************************************************************
 * Source File: StrutsEditQuestionBD.java
 ****************************************************************************************/
package net.ruready.web.content.question.imports;

import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.exports.DefaultEditQuestionBD;
import net.ruready.business.content.question.manager.DefaultEditQuestionManager;
import net.ruready.business.user.entity.User;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An edit {@link Question} BD interface with a Struts resource locator. Methods also
 * allow a specific user to request the DAO operations on items.
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
 * @version Aug 26, 2007
 */
public class StrutsEditQuestionBD extends DefaultEditQuestionBD
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(StrutsEditQuestionBD.class);

	// ========================= FIELDS ====================================

	/**
	 * The user requesting the question operations.
	 */
	private final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a business delegate for this user.
	 * 
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 */
	public StrutsEditQuestionBD(final ApplicationContext context, final User user)
	{
		super(new DefaultEditQuestionManager(WebAppResourceLocator.getInstance(),
				context, user), WebAppResourceLocator.getInstance());
		this.user = user;
	}

	// ========================= IMPLEMENTATION: DefaultEditQuestionBD =========

	/**
	 * Throws an unsupported operation exception. Although the generic edit question
	 * manager supports deleting questions (physically), use instead
	 * <code>AbstractTrashManager.deleteQuestion()</code> for a soft delete that moves
	 * the deleted question under the trash.
	 * 
	 * @param question
	 * @param respectLocks
	 * @see net.ruready.business.content.question.exports.DefaultEditQuestionBD#delete(net.ruready.business.content.question.entity.Question,
	 *      boolean)
	 */
	@Override
	public void delete(final Question question, final boolean respectLocks)
	{
		throw new ApplicationException(
				"Unsupported operation. Deferred to AbstractTrashManager.");
	}

	// ========================= overridden METHODS: DefaultEditQuestionBD ======

	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @see net.ruready.business.content.catalog.exports.AbstractItemUtilManager#getUser()
	 */
	public User getUser()
	{
		return user;
	}
}
