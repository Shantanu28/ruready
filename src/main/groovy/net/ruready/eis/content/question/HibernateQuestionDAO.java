/*****************************************************************************************
 * Source File: HibernateQuestionDAO.java
 ****************************************************************************************/
package net.ruready.eis.content.question;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.question.entity.Question;
import net.ruready.common.eis.dao.DAO;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.eis.content.item.HibernateItemDAO;
import net.ruready.eis.content.item.ItemReadDAO;
import net.ruready.eis.factory.entity.AbstractHibernateSessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A custom DAO for {@link Question}s. Importantly, we aggressively load the question's
 * bastard object (= hierarchy of object external to the {@link Item} hierarchy).
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
 * @version Aug 12, 2007
 */
@Deprecated
public class HibernateQuestionDAO extends HibernateItemDAO<Question> implements
// QuestionDAO
		DAO<Question, Long>, ItemReadDAO<Question>
{
	// ========================= CONSTANTS =================================

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateQuestionDAO.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct a DAO from a session and class type.
	 * 
	 * @param persistentClass
	 *            entity's class type
	 * @param session
	 *            Hibernate session
	 * @param sessionFactory
	 *            a heavy-weight session factory that can provide new sessions after old
	 *            ones has closed or have been corrupted.
	 * @param associationManagerFactory
	 *            Produces entity association manager objects.
	 */
	public HibernateQuestionDAO(final AbstractHibernateSessionFactory sessionFactory,
			final ApplicationContext context)
	{
		super(Question.class, sessionFactory, context);
	}

	/**
	 * Read an entity from the database. Pre-populated fields are overridden if they are
	 * persistent, or unaffected if they are transient. The number of children of the item
	 * is also loaded. This implementation discards the cascadeType parameter. However, it
	 * also fetches all bastard objects under the question (these objects are external to
	 * the item hierarchy).
	 * 
	 * @param id
	 *            unique identifier to search for and load by
	 * @param entity
	 *            entity to be read
	 * @param removeFromAssociations
	 *            if <code>true</code>, the item tree's associations with other objects
	 *            will be removed (e.g. in preparation for deleting the item tree)
	 * @return persisted entity after saving it
	 * 
	 * @see net.ruready.eis.content.item.HibernateItemDAO#read(long,
	 *      net.ruready.business.content.item.entity.Item)
	 */
	@Override
	public Question read(final long id, final boolean removeFromAssociations)
	{
		return super.read(id, removeFromAssociations);
	}
}
