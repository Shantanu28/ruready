/*****************************************************************************************
 * Source File: SuiteQBE.java
 ****************************************************************************************/
package test.ruready.eis.qbe;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Hibernate qyert-by-example tests.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 31, 2007
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ TestHibernateQBE.class, TestHibernateQBEInheritance.class })
public class SuiteSAQBE
{
}
