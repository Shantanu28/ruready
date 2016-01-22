/*****************************************************************************************
 * Source File: InactiveTestCreateDemoCatalog.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.exports.AbstractMainItemBD;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import test.ruready.business.content.catalog.TestPersistenceCatalog;
import test.ruready.imports.StandAloneEditItemBD;
import test.ruready.imports.StandAloneMainItemBD;
import test.ruready.rl.TestEnvTestBase;

/**
 * Test lazy loading of children of an item. This test shows that
 * <code>item.add()</code> (more generally, calling a collection's
 * <code>add()</code> method) loads the entire array. Here's an example of the
 * SQL output of this test case when Hibernate SQL debugging is turned on:
 * 
 * <pre>
 *  15:13:21,531  INFO TestPersistenceCatalog   :128 - ################### adding message ##################
 *  15:13:21,531 DEBUG DefaultEditItemManager   :469 - Finding item by id 1503
 *  15:13:21,531 DEBUG SQL                      :401 - select item0_.id as id0_3_, item0_.comment as comment0_3_, item0_.comparator_type as comparator4_0_3_, item0_.depth as depth0_3_, item0_.name as name0_3_, item0_.parent_id as parent32_0_3_, item0_.serial_no as serial7_0_3_, item0_.version as version0_3_, item0_.latest_message as latest33_0_3_, item0_.read_only as read9_0_3_, item0_.unique_name as unique10_0_3_, item0_.univ_catalog_abbreviation as univ11_0_3_, item0_.syllabusUrl as syllabu12_0_3_, item0_.univ_catalog_number as univ13_0_3_, item0_.concept_text as concept14_0_3_, item0_.formulation as formula15_0_3_, item0_.interests as interests0_3_, item0_.level as level0_3_, item0_.number_of_choices as number18_0_3_, item0_.parameters as parameters0_3_, item0_.question_precision as question20_0_3_, item0_.question_type as question21_0_3_, item0_.last_modified as last22_0_3_, item0_.stateid as stateid0_3_, item0_.variables as variables0_3_, item0_.negative as negative0_3_, item0_.phone_code as phone26_0_3_, item0_.abbreviation as abbrevi27_0_3_, item0_.phone as phone0_3_, item0_.address as address0_3_, item0_.zip_code as zip30_0_3_, item0_.content as content0_3_, item0_.item_type as item1_0_3_, node1_.id as id0_0_, node1_.comment as comment0_0_, node1_.comparator_type as comparator4_0_0_, node1_.depth as depth0_0_, node1_.name as name0_0_, node1_.parent_id as parent32_0_0_, node1_.serial_no as serial7_0_0_, node1_.version as version0_0_, node1_.latest_message as latest33_0_0_, node1_.read_only as read9_0_0_, node1_.unique_name as unique10_0_0_, node1_.univ_catalog_abbreviation as univ11_0_0_, node1_.syllabusUrl as syllabu12_0_0_, node1_.univ_catalog_number as univ13_0_0_, node1_.concept_text as concept14_0_0_, node1_.formulation as formula15_0_0_, node1_.interests as interests0_0_, node1_.level as level0_0_, node1_.number_of_choices as number18_0_0_, node1_.parameters as parameters0_0_, node1_.question_precision as question20_0_0_, node1_.question_type as question21_0_0_, node1_.last_modified as last22_0_0_, node1_.stateid as stateid0_0_, node1_.variables as variables0_0_, node1_.negative as negative0_0_, node1_.phone_code as phone26_0_0_, node1_.abbreviation as abbrevi27_0_0_, node1_.phone as phone0_0_, node1_.address as address0_0_, node1_.zip_code as zip30_0_0_, node1_.content as content0_0_, node1_.item_type as item1_0_0_, auditmessa2_.id as id5_1_, auditmessa2_.comment as comment5_1_, auditmessa2_.date as date5_1_, auditmessa2_.action as action5_1_, auditmessa2_.email as email5_1_, auditmessa2_.item as item5_1_, auditmessa2_.first_name as first6_5_1_, auditmessa2_.last_name as last7_5_1_, auditmessa2_.middle_initial as middle8_5_1_, auditmessa2_.version as version5_1_, tags3_.node as node5_, tagitem4_.id as tags5_, tagitem4_.id as id0_2_, tagitem4_.comment as comment0_2_, tagitem4_.comparator_type as comparator4_0_2_, tagitem4_.depth as depth0_2_, tagitem4_.name as name0_2_, tagitem4_.parent_id as parent32_0_2_, tagitem4_.serial_no as serial7_0_2_, tagitem4_.version as version0_2_, tagitem4_.latest_message as latest33_0_2_, tagitem4_.read_only as read9_0_2_, tagitem4_.item_type as item1_0_2_ from node item0_ left outer join node node1_ on item0_.parent_id=node1_.id left outer join audit_message auditmessa2_ on node1_.latest_message=auditmessa2_.id left outer join node_tags tags3_ on node1_.id=tags3_.node left outer join node tagitem4_ on tags3_.tags=tagitem4_.id where item0_.id=? and item0_.item_type in ('Item', 'Root', 'Catalog', 'World', 'DocumentCabinet', 'TagCabinet', 'DefaultTrash', 'ContentKnowledge', 'ExpectationSet', 'Federation', 'Folder', 'Document', 'InterestCollection', 'Interest', 'SubInterest', 'ConceptCollection', 'SkillCollection', 'Skill', 'Concept', 'MainItem', 'Subject', 'Course', 'ContentType', 'Topic', 'SubTopic', 'Question', 'ExpectationCategory', 'Expectation', 'Country', 'State', 'School', 'File')
 *  15:13:21,531 DEBUG SQL                      :401 - select auditmessa0_.id as id5_4_, auditmessa0_.comment as comment5_4_, auditmessa0_.date as date5_4_, auditmessa0_.action as action5_4_, auditmessa0_.email as email5_4_, auditmessa0_.item as item5_4_, auditmessa0_.first_name as first6_5_4_, auditmessa0_.last_name as last7_5_4_, auditmessa0_.middle_initial as middle8_5_4_, auditmessa0_.version as version5_4_, item1_.id as id0_0_, item1_.comment as comment0_0_, item1_.comparator_type as comparator4_0_0_, item1_.depth as depth0_0_, item1_.name as name0_0_, item1_.parent_id as parent32_0_0_, item1_.serial_no as serial7_0_0_, item1_.version as version0_0_, item1_.latest_message as latest33_0_0_, item1_.read_only as read9_0_0_, item1_.unique_name as unique10_0_0_, item1_.univ_catalog_abbreviation as univ11_0_0_, item1_.syllabusUrl as syllabu12_0_0_, item1_.univ_catalog_number as univ13_0_0_, item1_.concept_text as concept14_0_0_, item1_.formulation as formula15_0_0_, item1_.interests as interests0_0_, item1_.level as level0_0_, item1_.number_of_choices as number18_0_0_, item1_.parameters as parameters0_0_, item1_.question_precision as question20_0_0_, item1_.question_type as question21_0_0_, item1_.last_modified as last22_0_0_, item1_.stateid as stateid0_0_, item1_.variables as variables0_0_, item1_.negative as negative0_0_, item1_.phone_code as phone26_0_0_, item1_.abbreviation as abbrevi27_0_0_, item1_.phone as phone0_0_, item1_.address as address0_0_, item1_.zip_code as zip30_0_0_, item1_.content as content0_0_, item1_.item_type as item1_0_0_, node2_.id as id0_1_, node2_.comment as comment0_1_, node2_.comparator_type as comparator4_0_1_, node2_.depth as depth0_1_, node2_.name as name0_1_, node2_.parent_id as parent32_0_1_, node2_.serial_no as serial7_0_1_, node2_.version as version0_1_, node2_.latest_message as latest33_0_1_, node2_.read_only as read9_0_1_, node2_.unique_name as unique10_0_1_, node2_.univ_catalog_abbreviation as univ11_0_1_, node2_.syllabusUrl as syllabu12_0_1_, node2_.univ_catalog_number as univ13_0_1_, node2_.concept_text as concept14_0_1_, node2_.formulation as formula15_0_1_, node2_.interests as interests0_1_, node2_.level as level0_1_, node2_.number_of_choices as number18_0_1_, node2_.parameters as parameters0_1_, node2_.question_precision as question20_0_1_, node2_.question_type as question21_0_1_, node2_.last_modified as last22_0_1_, node2_.stateid as stateid0_1_, node2_.variables as variables0_1_, node2_.negative as negative0_1_, node2_.phone_code as phone26_0_1_, node2_.abbreviation as abbrevi27_0_1_, node2_.phone as phone0_1_, node2_.address as address0_1_, node2_.zip_code as zip30_0_1_, node2_.content as content0_1_, node2_.item_type as item1_0_1_, auditmessa3_.id as id5_2_, auditmessa3_.comment as comment5_2_, auditmessa3_.date as date5_2_, auditmessa3_.action as action5_2_, auditmessa3_.email as email5_2_, auditmessa3_.item as item5_2_, auditmessa3_.first_name as first6_5_2_, auditmessa3_.last_name as last7_5_2_, auditmessa3_.middle_initial as middle8_5_2_, auditmessa3_.version as version5_2_, tags4_.node as node6_, tagitem5_.id as tags6_, tagitem5_.id as id0_3_, tagitem5_.comment as comment0_3_, tagitem5_.comparator_type as comparator4_0_3_, tagitem5_.depth as depth0_3_, tagitem5_.name as name0_3_, tagitem5_.parent_id as parent32_0_3_, tagitem5_.serial_no as serial7_0_3_, tagitem5_.version as version0_3_, tagitem5_.latest_message as latest33_0_3_, tagitem5_.read_only as read9_0_3_, tagitem5_.item_type as item1_0_3_ from audit_message auditmessa0_ left outer join node item1_ on auditmessa0_.item=item1_.id left outer join node node2_ on item1_.parent_id=node2_.id left outer join audit_message auditmessa3_ on item1_.latest_message=auditmessa3_.id left outer join node_tags tags4_ on item1_.id=tags4_.node left outer join node tagitem5_ on tags4_.tags=tagitem5_.id where auditmessa0_.id=?
 *  15:13:21,531 DEBUG SQL                      :401 - select 1 from node where parent_id =? and id =?
 *  15:13:21,531 DEBUG DefaultEditItemManager   :172 - Saving/Updating item Root parent NULL
 *  15:13:21,531 DEBUG HibernateItemDAO         :664 - update(): entity [Root ID 1503] Root READ ONLY
 *  15:13:21,531 DEBUG SQL                      :401 - select 1 from audit_message where item =? and id =?
 *  15:13:21,531 DEBUG HibernateItemDAO         :697 - Before saveOrUpdate()
 * </pre>
 * <code>select 1 ...</code> loads the entire array.
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
 * @version Aug 3, 2007
 */
public class TestLazyChildren extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestPersistenceCatalog.class);

	// ========================= FIELDS ====================================

	/**
	 * System user used in all item operations.
	 */
	private User systemUser;

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
	}

	// ========================= TESTING METHODS ===========================

	/**
	 * @param args
	 */
	@Test
	public void testAddChild()
	{
		// Add a lot of messages
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		Item item = getRoot();
		final int numChilds = 100;
		for (int i = 0; i < numChilds; i++)
		{
			item.addChild(new Catalog("Child #" + i, "Comment #" + i));
		}
		bdItem.update(item, false);
		environment.getSession().evict(item);

		// Add another message
		logger.info("################### adding message ##################");
		item = bdItem.findById(Item.class, item.getId());
		item.addChild(new Catalog("New Child", "New Comment"));
		bdItem.update(item, false);
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * @return
	 */
	private Item getRoot()
	{
		AbstractMainItemBD bdMainItem = new StandAloneMainItemBD(
				environment.getContext(), systemUser);
		Item root = bdMainItem.readUnique(Root.class);
		return root;
	}
}
