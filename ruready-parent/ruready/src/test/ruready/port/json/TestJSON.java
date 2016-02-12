/*****************************************************************************************
 * Source File: TestJSON.java
 ****************************************************************************************/
package test.ruready.port.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruready.port.json.List2JsonWrapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test JSON-object conversions.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class TestJSON extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestJSON.class);

	// ========================= TESTING METHODS ===========================

	/**
	 * Test printing an array object in JSON format.
	 */
	@Test
	public void testConversion()
	{
		final List<SimpleRecord> myData = new ArrayList<SimpleRecord>();
		logger.debug("Initializing data");
		for (long i = 1; i <= 5; i++)
		{
			myData.add(new SimpleRecord(i, "Record " + i));
		}

		JSONArray encoded = JSONArray.fromObject(myData);
		logger.info("data " + myData);
		logger.info("encoded " + encoded);

		// Wrap the list in a root entity and convert to Json
		String json = List2JsonWrapper.toJSONString(SimpleRecord.class, myData);
		logger.info("encoded wrapper " + json);

		List<? extends SimpleRecord> backToList = List2JsonWrapper.toList(
				SimpleRecord.class, json);
		logger.info("backToList " + backToList);
		Assert.assertEquals(myData, backToList);
	}

	/**
	 * Test printing an array object in JSON format.
	 */
	@Test
	public void testMap()
	{
		final Map<String, String> params = new HashMap<String, String>();
		params.put("success", "true");
		JSONObject encoded = JSONObject.fromObject(params);
		Assert.assertEquals("{\"success\":\"true\"}", encoded.toString());
	}
}
