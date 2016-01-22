/*****************************************************************************************
 * Source File: TestUrlPattern.java
 ****************************************************************************************/
package test.ruready.port.xml.content;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.business.common.tree.util.NodeUtil;
import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.item.util.ItemCounter;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.business.content.world.entity.property.InstitutionType;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.port.xml.content.AsciiItemPrinter;
import net.ruready.port.xml.content.ContentPrinter;
import net.ruready.port.xml.content.WorldDataParser;
import net.ruready.port.xml.content.WorldDataTarget;
import net.ruready.port.xml.content.XmlContentPrinter;
import net.ruready.port.xml.content_1_3.WorldDataParser_1_3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.rl.TestEnvTestBase;

/**
 * Tests parsing an XML ini file of the Struts filter Framework.
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
 * @version Aug 13, 2007
 */
public class TestSchoolDataParser extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestSchoolDataParser.class);

	/**
	 * Test directory path where test files used by this test case are located.
	 */
	private final String BASE_TEST_DIR = "data" + File.separator + "test"
			+ File.separator + "port" + File.separator + "xml" + File.separator
			+ "content";

	/**
	 * A simple test file with several schools (1.3 format).
	 */
	private final String SCHOOL_TEST_FILE_1_3 = "schools_1_3.xml";

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private User systemUser;

	// ========================= CONSTRUCTORS ==============================

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		// ---------------------------------------
		// Clean up - delete all items
		// ---------------------------------------
		logger.info("Deleting items ...");
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		List<Item> items = bdItem.findAll(Item.class);
		for (Item item : items)
		{
			bdItem.deleteAll(item, false);
		}
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * Test parsing an example file.
	 */
	@Test
	public void testParseFile() throws Exception
	{
		WorldDataTarget target = parseFile_1_3(BASE_TEST_DIR + File.separator
				+ SCHOOL_TEST_FILE_1_3);
		World world = target.getWorld();
		// Test that all school fields are correctly set in country #1, school
		// #2
		// (we know there's one country with one state and two schools in this
		// input
		// file).
		Assert.assertEquals(2, world.getChildren().size());

		// Select the first country (U.S.)
		Country us = (Country) world.findChild(new Country("United States", null, 0));
		Assert.assertEquals(1, us.getChildren().size());

		// Select the State of Alabama
		State alabama = (State) us.findChild(new State("Alabama", null));
		Assert.assertEquals(2, alabama.getChildren().size());

		// Select the city
		City city = (City) alabama.findChild(new State("Abbeville", null));
		Assert.assertEquals(1, city.getChildren().size());

		// Select the school
		School school = (School) city
				.findChild(new School("Abbeville High School", null));
		Assert.assertNotNull(school);

		// Original XML for this school:
		// <institution-type>K_12</institution-type>
		// <url>http://www.vrml.k12.al.us/ahs/</url>
		// <name>Abbeville High School</name>
		// <comment>This is a High School.</comment>
		// <address>300 Trawick St</address>
		// <address>Second Address Line Goes Here.</address>
		// <address>Third Address Line Goes Here.</address>
		// <city>Abbeville</city>
		// <zip-code>36310</zip-code>
		// <county>Henry</county>
		// <district>My District</district>
		// <phone>(334) 585-2065</phone>
		// <phone>(123) 456-7890</phone>
		// <fax>(321) 123-4567</fax>
		Assert.assertEquals(InstitutionType.K_12, school.getInstitutionType());
		Assert.assertEquals("http://www.vrml.k12.al.us/ahs/", school.getUrl());
		Assert.assertEquals("Abbeville High School", school.getName());
		Assert.assertEquals("This is a High School.", school.getComment());
		Assert.assertEquals("300 Trawick St", school.getAddress1());
		Assert.assertEquals(
				"Second Address Line Goes Here.\nThird Address Line Goes Here.", school
						.getAddress2());
		Assert.assertEquals("36310", school.getZipCode());
		Assert.assertEquals("Henry", school.getCounty());
		Assert.assertEquals("My District", school.getDistrict());
		Assert.assertEquals("(334) 585-2065", school.getPhone1());
		Assert.assertEquals("(123) 456-7890", school.getPhone2());
		Assert.assertEquals("(321) 123-4567", school.getFax());
	}

	/**
	 * Test parsing an example file and saving it to a database.
	 */
	@Test
	public void testParseFileAndSaveToDatabase() throws Exception
	{
		WorldDataTarget target = parseFile_1_3(BASE_TEST_DIR + File.separator
				+ SCHOOL_TEST_FILE_1_3);
		World world = target.getWorld();

		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		bdItem.updateAll(world);
	}

	/**
	 * Test parsing an example file and saving it to a database.
	 */
	@Test
	public void testMerge() throws Exception
	{
		World world = ItemDemoUtil
				.createBase(World.class, ContentNames.UNIQUE_NAME.WORLD);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ContentPrinter printer = new ContentPrinter(world, stream, new AsciiItemPrinter());
		printer.print(true);
		// logger.debug("Original world:\n" + stream);

		WorldDataTarget target = parseFile_1_3(BASE_TEST_DIR + File.separator
				+ SCHOOL_TEST_FILE_1_3);

		NodeUtil.merge(target.getWorld(), world);
		stream = new ByteArrayOutputStream();
		printer = new ContentPrinter(world, stream, new AsciiItemPrinter());
		printer.print(true);
		// logger.debug("Merged world:\n" + stream);

		// Check total item counts
		ItemCounter counter = new ItemCounter(world);
		Map<ItemType, Integer> expectedCount = new HashMap<ItemType, Integer>();
		expectedCount.put(ItemType.COUNTRY, 1);
		expectedCount.put(ItemType.FEDERATION, 1);
		expectedCount.put(ItemType.WORLD, 1);
		expectedCount.put(ItemType.STATE, 2);
		expectedCount.put(ItemType.CITY, 5);
		expectedCount.put(ItemType.SCHOOL, 8);
		Assert.assertEquals(expectedCount, counter.getCount());

		// =============================
		// Check merged school data
		// =============================
		// Select the first country (U.S.)
		Country israel = (Country) world.findChild(new Country("Israel", null, 0));
		Assert.assertEquals(1, israel.getChildren().size());

		// Select the city
		City city = (City) israel.findChild(new City("Ariel", null));
		Assert.assertEquals(1, city.getChildren().size());

		// Select the school

		School school = (School) city.findChild(new School("College of Judea and Smaria",
				null));
		Assert.assertNotNull(school);

		// SCHOOL # 1: College of Judea and Smaria
		// Institution Type: K_12
		// Phone #1: 1-111-1111
		Assert.assertEquals("College of Judea and Smaria", school.getName());
		Assert.assertEquals(InstitutionType.K_12, school.getInstitutionType());
		Assert.assertNull(school.getSector());
		Assert.assertEquals("1-111-1111", school.getPhone1());
	}

	/**
	 * Test parsing a data file with Alabama school information.
	 */
	@Test
	public void testParseAlabamaFile() throws Exception
	{
		/* WorldDataTarget target = */parseFile_1_3(BASE_TEST_DIR + File.separator
				+ "Alabama.xml");
		// logger.debug("Target\n" + target);
		// World world = target.getWorld();
	}

	/**
	 * Test parsing a file with four schools that fell between the cracks.
	 */
	@Test
	public void testParseAlaskaFile() throws Exception
	{
		/* WorldDataTarget target = */parseFile_1_3(BASE_TEST_DIR + File.separator
				+ "Alaska.xml");
		// logger.debug("Target\n" + target);
		// World world = target.getWorld();
	}

	/**
	 * Test parsing a file with schools that have duplicate names.
	 */
	@Test
	public void testParseArkansasFile() throws Exception
	{
		/* WorldDataTarget target = */parseFile_1_3(BASE_TEST_DIR + File.separator
				+ "Arkansas.xml");
		// logger.debug("Target\n" + target);
		// World world = target.getWorld();
	}

	/**
	 * Test parsing a big data file with U.S. school information.
	 */
	@Test
	public void testParseUnitedStatesFile() throws Exception
	{
		WorldDataTarget target = parseFile_1_3(BASE_TEST_DIR + File.separator
				+ "United_States.xml_1_3");
		// logger.debug("Target\n" + target);
		// World world = target.getWorld();

		// Display total school counts in states
		// Select the first country (U.S.)
		Country us = (Country) target.getWorld().findChild(
				new Country("United States", null, 0));
		StringBuffer s = TextUtil.emptyStringBuffer();
		for (Node child : us.getChildren())
		{
			State state = (State) child;
			ItemCounter counter = new ItemCounter(state);
			s.append(state.getName() + " " + counter.getCount(ItemType.SCHOOL)
					+ CommonNames.MISC.NEW_LINE_CHAR);
		}
		logger.debug("%%%%%%%%%%%%% Total school counts: %%%%%%%%%%%%%\n" + s);
	}

	/**
	 * Test exporting a parsed content management into an XML stream.
	 */
	@Test
	public void testXmlExportTestFile() throws Exception
	{
		logger.debug("%%%%%%%%%%%%% Parsing input file 1.3 format %%%%%%%%%%%%%");
		// Parse using 1.3 standard
		WorldDataTarget target_1_3 = parseFile_1_3(BASE_TEST_DIR + File.separator
				+ SCHOOL_TEST_FILE_1_3);
		// logger.debug("Target\n" + target_1_3);

		// Export in XML format
		logger.debug("%%%%%%%%%%%%% Exporting to XML stream 1.4 format %%%%%%%%%%%%%");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ContentPrinter printer = new XmlContentPrinter(target_1_3.getWorld(), stream);
		printer.print(false);
		// logger.debug("XML format:\n" + stream + "\n");

		// // Export in XML format to a new file
		// logger.debug("%%%%%%%%%%%%% Exporting to 1.4 output file
		// %%%%%%%%%%%%");
		// FileOutputStream writer = new FileOutputStream(BASE_TEST_DIR +
		// File.separator
		// + "schools_1_4.xml_1_4_old", false);
		// printer = new XmlContentPrinter(target_1_3.getWorld(), writer);
		// printer.print(false);
		// writer.close();

		// Parse using 1.4 standard
		logger.debug("%%%%%%%%%%%%% Parsing input stream 1.4 format %%%%%%%%%%%%%");
		InputStream inputStream = new ByteArrayInputStream(stream.toByteArray());
		WorldDataTarget target_1_4 = parseFile(inputStream);
		Assert.assertEquals(0, target_1_4.getNumErrors());

		// Export in XML format to a new file
		// logger.debug("%%%%%%%%%%%%% Exporting to 1.4 output file
		// %%%%%%%%%%%%%");
		// writer = new FileOutputStream(BASE_TEST_DIR + File.separator +
		// "schools_1_4.xml",
		// false);
		// printer = new XmlContentPrinter(target_1_4.getWorld(), writer);
		// printer.print(false);
		// writer.close();
	}

	/**
	 * Test converting U.S. data from 1.3 to 1.4 format, and writing the result
	 * into an XML stream/file.
	 * <p>
	 * Disabled for now because it's a big memory consumer.
	 */
	public void xxxtestXmlExportUnitedStates() throws Exception
	{
		// Parse using 1.3 standard
		logger.debug("%%%%%%%%%%%%% Parsing input file 1.3 format %%%%%%%%%%%%%");
		WorldDataTarget target_1_3 = parseFile_1_3(BASE_TEST_DIR + File.separator
				+ "United_States.xml_1_3");
		// logger.debug("Target\n" + target_1_3);

		// Export in XML format
		logger.debug("%%%%%%%%%%%%% Exporting to XML stream 1.4 format %%%%%%%%%%%%%");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ContentPrinter printer = new XmlContentPrinter(target_1_3.getWorld(), stream);
		printer.print(false);
		// logger.debug("XML format:\n" + stream + "\n");

		// Export in XML format to a new file
		// logger.debug("%%%%%%%%%%%%% Exporting to 1.4 output file
		// %%%%%%%%%%%%");
		// FileOutputStream writer = new FileOutputStream(BASE_TEST_DIR +
		// File.separator
		// + "United_States.xml_1_4_old", false);
		// printer = new XmlContentPrinter(target_1_3.getWorld(), writer);
		// printer.print(false);
		// writer.close();

		// Parse using 1.4 standard
		logger.debug("%%%%%%%%%%%%% Parsing input stream 1.4 format %%%%%%%%%%%%");
		InputStream inputStream = new ByteArrayInputStream(stream.toByteArray());
		WorldDataTarget target_1_4 = parseFile(inputStream);
		Assert.assertEquals(0, target_1_4.getNumErrors());

		// Export in XML format to a new file
		// logger.debug("%%%%%%%%%%%%% Exporting to 1.4 output file
		// %%%%%%%%%%%%");
		// writer = new FileOutputStream(BASE_TEST_DIR + File.separator
		// + "United_States.xml_1_4", false);
		// printer = new XmlContentPrinter(target_1_4.getWorld(), writer);
		// printer.print(false);
		// writer.close();
	}

	// ========================= PRIVATE METHODS ============================

	/**
	 * Parse an example file using the World 1.3 XSD parser.
	 * 
	 * @param fileName
	 *            input file name
	 * @return parser target
	 * @throws Exception
	 */
	private WorldDataTarget parseFile_1_3(final String fileName) throws Exception
	{
		WorldDataParser_1_3 parser = new WorldDataParser_1_3();
		String baseDir = System.getProperty(CommonNames.JUNIT.ENVIRONMENT.BASE_DIR);
		String absoluteFileName = (baseDir != null) ? baseDir + File.separator + fileName
				: fileName;
		WorldDataTarget target = parser.getTarget();
		// logger.debug("<<<<<<<<<<<<<<<<< Parsing ... >>>>>>>>>>>>>>>>>>>");
		try
		{
			parser.parse(absoluteFileName);
		}
		catch (SAXParseException spe)
		{
			StringBuffer sb = new StringBuffer(spe.toString());
			sb.append("\n Line number: " + spe.getLineNumber());
			sb.append("\nColumn number: " + spe.getColumnNumber());
			sb.append("\n Public ID: " + spe.getPublicId());
			sb.append("\n System ID: " + spe.getSystemId() + "\n");
			logger.error(sb.toString());
		}
		catch (Exception e)
		{
			logger.error("Malformed world data file");
			e.printStackTrace();
			throw new Exception(e);
		}
		// logger.debug("Target\n" + target);
		logger
				.debug("Item counts:\n"
						+ ItemCounter.generateItemCounts(target.getWorld()));
		return target;
	}

	/**
	 * Parse an example file using the World XSD parser (1.4+).
	 * 
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	private WorldDataTarget parseFile(final InputStream stream) throws Exception
	{
		WorldDataParser parser = new WorldDataParser();
		// String baseDir =
		// System.getProperty(CommonNames.JUNIT.ENVIRONMENT.BASE_DIR);
		// String absoluteFileName = (baseDir != null) ? baseDir +
		// File.separator +
		// fileName
		// : fileName;
		WorldDataTarget target = parser.getTarget();
		// logger.debug("<<<<<<<<<<<<<<<<< Parsing ... >>>>>>>>>>>>>>>>>>>");
		try
		{
			parser.parse(new InputSource(stream));
		}
		catch (SAXParseException spe)
		{
			StringBuffer sb = new StringBuffer(spe.toString());
			sb.append("\n Line number: " + spe.getLineNumber());
			sb.append("\nColumn number: " + spe.getColumnNumber());
			sb.append("\n Public ID: " + spe.getPublicId());
			sb.append("\n System ID: " + spe.getSystemId() + "\n");
			logger.error(sb.toString());
		}
		catch (Exception e)
		{
			logger.error("Malformed world data file");
			e.printStackTrace();
			throw new Exception(e);
		}
		// logger.debug("Target\n" + target);
		logger
				.debug("Item counts:\n"
						+ ItemCounter.generateItemCounts(target.getWorld()));
		return target;
	}
}
