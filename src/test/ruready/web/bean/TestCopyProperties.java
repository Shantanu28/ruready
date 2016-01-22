/*****************************************************************************************
 * Source File: TestCopyProperties.java
 ****************************************************************************************/
package test.ruready.web.bean;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.world.entity.School;
import net.ruready.web.content.item.form.EditItemForm;
import net.ruready.web.content.item.form.Item2FormCopier;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Test Apache's {@link BeanUtils#copyProperties(Object, Object)} including type
 * conversions.
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
public class TestCopyProperties extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestCopyProperties.class);

	// ========================= TESTING METHODS ===========================

	/**
	 * Copy from one simple bean to another.
	 */
	@Test
	public void testCopy()
	{
		FormBean fb = new FormBean();
		fb.setLevel("10"); // vo.level will be 10 after copying
		// fb.setLevel("a"); // vo.level will be 0 after copying
		SomeValueObject vo = new SomeValueObject();

		logger.info("Before copying:");
		logger.info("fb = " + fb);
		logger.info("vo = " + vo);
		try
		{
			// This is supposed to do type conversions
			BeanUtils.copyProperties(vo, fb);
		}
		catch (Exception e)
		{
			logger.error("Cannot copy: " + e.toString());
		}

		logger.info("After copying:");
		logger.info("fb = " + fb);
		logger.info("vo = " + vo);
	}

	/**
	 * Test our custom copier for item entity <-> form bean.
	 */
	@Test
	public void testCopyToUnitLink()
	{
		FormBean fb = new FormBean();
		fb.setLevel("10"); // vo.level will be 10 after copying
		// fb.setLevel("a"); // vo.level will be 0 after copying
		SomeValueObject vo = new SomeValueObject();

		logger.info("Before copying:");
		logger.info("fb = " + fb);
		logger.info("vo = " + vo);

		try
		{
			// This is supposed to do type conversions
			BeanUtils.copyProperties(vo, fb);
		}
		catch (Exception e)
		{
			logger.error("Cannot copy: " + e.toString());
		}

		logger.info("After copying:");
		logger.info("fb = " + fb);
		logger.info("vo = " + vo);
	}

	/**
	 * Copy from a simple bean to a UnitLink.
	 */
	@Test
	public void testItemCopier()
	{
		EditItemForm fb = new EditItemForm();

		Item item = new Item(null, null);
		item.setName("Item Name");
		logger.info("item = " + item);
		logger.info("Before copying item -> fb:");
		logger.info("fb   = " + fb);
		Item2FormCopier.copyProperties(fb, item);
		logger.info("After copying:");
		logger.info("fb   = " + fb);

		Item catalog = new Catalog("Catalog Name", null);
		logger.info("catalog = " + catalog);
		logger.info("Before copying catalog -> fb:");
		logger.info("fb   = " + fb);
		Item2FormCopier.copyProperties(fb, catalog);
		logger.info("After copying:");
		logger.info("fb   = " + fb);

		School school = new School("School Name", null);
		school.setZipCode("84103");
		logger.info("school = " + catalog);
		logger.info("Before copying school -> fb:");
		logger.info("fb   = " + fb);
		Item2FormCopier.copyProperties(fb, school);
		logger.info("After copying:");
		logger.info("fb   = " + fb);

	}
}
