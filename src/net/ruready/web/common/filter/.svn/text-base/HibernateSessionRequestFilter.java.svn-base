/*****************************************************************************************
 * Source File: HibernateSessionRequestFilter.java
 ****************************************************************************************/

package net.ruready.web.common.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.web.common.imports.WebAppResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

/**
 * Filter that manages a Hibernate Session for a request.
 * <p>
 * This filter should be used if your <tt>hibernate.current_session_context_class</tt>
 * configuration is set to <tt>thread</tt> and you are not using JTA or CMT.
 * <p>
 * With JTA you'd replace transaction demarcation with calls to the
 * <tt>UserTransaction</tt> API. With CMT you would remove transaction demarcation code
 * from this filter.
 * <p>
 * An alternative, more flexible solution is <tt>SessionTransactionInterceptor</tt> that
 * can be applied to any pointcut with JBoss AOP.
 * <p>
 * Note that you should not use this interceptor out-of-the-box with enabled optimistic
 * concurrency control. Apply your own compensation logic for failed conversations, this
 * is totally dependent on your applications design.
 * <p>
 * Note (Oren): this implements the Open-Session-In-View pattern. This filter handles
 * Hibernate session opening/closing. Transactions are demarcated by plug-ins into the
 * Struts request processor. This allows the two-transaction variation of this pattern:
 * transaction #1 (business) and begin transaction #2 (view, read-only).
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @see Hibernate CaveatEmptor project - auction.persistence.SessionTransactionInterceptor
 * @author Christian Bauer
 * @version Oct 2, 2007
 */
@Deprecated
public class HibernateSessionRequestFilter implements Filter
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(HibernateSessionRequestFilter.class);

	// ========================= FIELDS ====================================

	// ========================= IMPLEMENTATION: Filter ====================

	/**
	 * @param filterConfig
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig)
	{
		logger.debug("Initializing ...");
		// logger.debug("Obtaining SessionFactory from HibernateUtil");
		// sf = HibernateUtil.getSessionFactory();
	}

	public void destroy()
	{

	}

	/**
	 * Mark the Hibernate session and transaction scope at the beginning and end of the
	 * request processing chain in the web layer, respectively.
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException
	{
		AbstractEISManager eisManager = WebAppResourceLocator.getInstance()
				.getDAOFactory();
		try
		{
			// ==================================
			// Main chain flow
			// ==================================
			logger.debug("Opening Hibernate session");
			eisManager.openSession();

			// Call the next filter (continue request processing)
			// This will begin and commit transaction #1 (business) and begin transaction
			// #2 (view, read-only)
			chain.doFilter(request, response);

			// Commit transaction #2 if it is open
			logger.debug("Commiting view transaction");
			eisManager.commitTransaction();
		}
		catch (Throwable ex)
		{
			// ==================================
			// Roll back transaction #2
			// ==================================
			ex.printStackTrace();
			try
			{
				logger.debug("Trying to rollback transaction after exception");
				eisManager.rollbackTransaction();
				logger.debug("Closing Hibernate session");
				eisManager.closeSession();
			}
			catch (Throwable rbEx)
			{
				logger.error("Could not rollback transaction after exception!", rbEx);
			}

			// Couldn't roll back transaction #2, let others handle it...
			throw new ServletException(ex);
		}
		finally
		{
			try
			{
				// No matter what happens, close the Session.
				logger.debug("Closing Hibernate session");
				eisManager.closeSession();
			}
			catch (Throwable ex)
			{
				// Couldn't close session, let others handle it...
				throw new ServletException(ex);
			}
		}
	}

}
