/*****************************************************************************************
 * Source File: TestHashCodeUtil.java
 ****************************************************************************************/
package test.ruready.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.ruready.common.draw.Triplet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test procedures related to the arithmetic parser (tokenizer, arithmetic
 * compiler + matcher) on strings.
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
public class TestHashCodeUtil extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestHashCodeUtil.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test hashing an <code>ApartmentBuilding</code> object.
	 */
	@Test
	public void testApartmentBuildingHashCode()
	{
		List<Object> options = new ArrayList<Object>();
		options.add("pool");
		Date[] maintenanceDates = new Date[1];
		maintenanceDates[0] = new Date();
		byte numFloors = 8;

		ApartmentBuilding building1 = new ApartmentBuilding(false, 'B', 12, 396L, 5.2,
				6.3f, numFloors, "Palisades", options, maintenanceDates);

		ApartmentBuilding building2 = new ApartmentBuilding(false, 'B', 12, 396L, 5.2,
				6.3f, numFloors, "Palisades", options, maintenanceDates);

		if (building2.equals(building1))
		{
			// Equal objects must have equal hash codes.
			// Note: hash codes will be different upon every run of the program,
			// because they depend on the current date. That's OK.
			int hash1 = building1.hashCode();
			int hash2 = building2.hashCode();
			logger.info("hashCode values: " + hash1 + " " + hash2);
			logger.info("Equal objects, comparing hash codes");
			Assert.assertEquals(hash2, hash1);
		}
	}

	/**
	 * Test hashing a <code>Triplet</code> object.
	 */
	@Test
	public void testTripletHashCode()
	{
		Triplet t1 = new Triplet(1, 2, 3);
		Triplet t2 = new Triplet(1, 2, 3);

		if (t2.equals(t1))
		{
			// Equal objects must have equal hash codes.
			int hash1 = t1.hashCode();
			int hash2 = t2.hashCode();
			logger.info("hashCode values: " + hash1 + " " + hash2);
			logger.info("Equal objects, comparing hash codes");
			Assert.assertEquals(hash2, hash1);
		}
	}

	// ========================= TESTING ====================================
}
