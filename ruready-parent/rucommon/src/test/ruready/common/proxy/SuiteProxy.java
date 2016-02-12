/*****************************************************************************************
 * Source File: SuiteText.java
 ****************************************************************************************/
package test.ruready.common.proxy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.ruready.common.proxy.cglib.SuiteCGLib;
import test.ruready.common.proxy.reflection.SuiteReflection;

/**
 * A test suite that includes all tests in the tree testing package.
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
 * @version Oct 31, 2007
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ SuiteCGLib.class, SuiteReflection.class })
public class SuiteProxy
{
}
