/*****************************************************************************************
 * Source File: TestItemList.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test whether a an item list can be iterated over and modified during the
 * iteration. This has to do with Java 5 iteration features. Expected results:
 * Java 4 syntax (looping over the list with an index) permits changing the list
 * during iteration. Java 5 throws {@link ConcurrentModificationException}.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @see http://today.java.net/pub/a/today/2006/11/07/nuances-of-java-5-for-each-loop.html
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 4, 2007
 */
public class TestItemList extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestItemList.class);

	// ========================= TESTING METHODS ===========================

	/**
	 * Java 5 style iteration test
	 */
	@Test
	public void testIteration5()
	{
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5); // Uncomment to see ConcurrentModificationException
		logger.info("List after the iteration:" + list);
		try
		{
			for (int item : list)
			{ // the iterator returned by
				// java.util.ArrayList
				// is fail-fast
				if (item == 3)
					list.remove(item); // after this, loop behavior is
				// indeterminate
				else
					logger.info(item);
			}
			logger.info("List after the iteration:" + list);
		}
		catch (ConcurrentModificationException e)
		{
			logger.info("ConcurrentModificationException thrown as expected");
			return;
		}

		// We shouldn't be here
		throw new RuntimeException(
				"ConcurrentModificationException was not thrown as expected");
	}

	/**
	 * Java 4 style iteration test
	 */
	@Test
	public void testIteration4()
	{
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5); // Uncomment to see ConcurrentModificationException
		logger.info("List after the iteration:" + list);
		for (int i = 0; i < list.size(); i++)
		{
			Integer item = list.get(i);
			if (i == 3)
			{
				list.remove(item);
			}
			else
				logger.info(item);

			// The following printout works even for i=5, but that's risky.
			// The list's size is already 4, but probably still allocated to
			// have at least 5 elements so this works.
			logger.debug("i ; item = " + list.get(i));
		}
		logger.info("List after the iteration:" + list);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TestItemList t = new TestItemList();
		t.testIteration4(); // OK
		t.testIteration5(); // throws java.util.ConcurrentModificationException
	}

}
