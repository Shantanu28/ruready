/*****************************************************************************************
 * Source File: HibernateSessionFactory.java
 ****************************************************************************************/
package net.ruready.common.eis.exports;

import java.util.Properties;

import net.ruready.common.rl.Resource;

/**
 * An abstraction for managers of persistence layer session and transaction boundaries.
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
 * @version Jul 21, 2007
 */
public interface AbstractEISBounder extends Resource<Properties>
{
	// ========================= CONSTANTS =================================

	/**
	 * Open a <code>Session</code> using the embeddedHibernate session factory, but do
	 * not return it.
	 */
	void openSession();

	/**
	 * Close the single Hibernate session instance. @
	 */
	void closeSession();

	/**
	 * Start a new database transaction.
	 * <p>
	 * Is a no-op (with warning) if called in a CMT environment. Should be used in
	 * non-managed environments with resource local transactions, or with EJBs and
	 * bean-managed transactions. In both cases, it will either start a new transaction or
	 * join the existing ThreadLocal or JTA transaction.
	 */
	void beginTransaction();

	/**
	 * Commit the database transaction.
	 * <p>
	 * Is a no-op (with warning) if called in a CMT environment. Should be used in
	 * non-managed environments with resource local transactions, or with EJBs and
	 * bean-managed transactions. It will commit the ThreadLocal or BMT/JTA transaction.
	 */
	void commitTransaction();

	/**
	 * Rollback the database transaction.
	 * <p>
	 * Is a no-op (with warning) if called in a CMT environment. Should be used in
	 * non-managed environments with resource local transactions, or with EJBs and
	 * bean-managed transactions. It will rollback the resource local or BMT/JTA
	 * transaction.
	 */
	void rollbackTransaction();

	/**
	 * Is the current transaction open or not.
	 * 
	 * @return <code>true</code> if and only if the current transaction is open
	 * @see org.hibernate.Session#getTransaction()
	 */
	boolean isTransactionActive();

	/**
	 * Check if this transaction was successfully committed. This method could return
	 * false even after successful invocation of commit(). As an example, JTA based
	 * strategies no-op on commit() calls if they did not start the transaction; in that
	 * case, they also report wasCommitted() as false.
	 * 
	 * @return boolean True if the transaction was (unequivocally) committed via this
	 *         local transaction; false otherwise.
	 * @throws HibernateException
	 */
	boolean wasTransactionCommitted();

	/**
	 * Was this transaction rolled back or set to rollback only? This only accounts for
	 * actions initiated from this local transaction. If, for example, the underlying
	 * transaction is forced to rollback via some other means, this method still reports
	 * false because the rollback was not initiated from here.
	 * 
	 * @return boolean True if the transaction was rolled back via this local transaction;
	 *         false otherwise.
	 * @throws HibernateException
	 */
	boolean wasTransactionRolledBack();
}
