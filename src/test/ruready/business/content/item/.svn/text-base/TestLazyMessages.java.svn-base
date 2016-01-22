/*****************************************************************************************
 * Source File: TestLazyMessages.java
 ****************************************************************************************/
package test.ruready.business.content.item;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.audit.AuditAction;
import net.ruready.business.content.item.entity.audit.AuditMessage;
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
 * Test lazy loading of audit messages of an item. This test shows that
 * <code>item.addMessage()</code> (more generally, calling a collection's
 * <code>add()</code> method) DOES NOT load the entire array. Here's an
 * example of the SQL output of this test case when Hibernate SQL debugging is
 * turned on:
 * 
 * <pre>
 *  15:21:13,765 DEBUG DefaultEditItemManager   :469 - Finding item by id 1
 *  15:21:13,765 DEBUG SQL                      :401 - select item0_.id as id0_3_, item0_.comment as comment0_3_, item0_.comparator_type as comparator4_0_3_, item0_.depth as depth0_3_, item0_.name as name0_3_, item0_.parent_id as parent32_0_3_, item0_.serial_no as serial7_0_3_, item0_.version as version0_3_, item0_.latest_message as latest33_0_3_, item0_.read_only as read9_0_3_, item0_.unique_name as unique10_0_3_, item0_.univ_catalog_abbreviation as univ11_0_3_, item0_.syllabusUrl as syllabu12_0_3_, item0_.univ_catalog_number as univ13_0_3_, item0_.concept_text as concept14_0_3_, item0_.formulation as formula15_0_3_, item0_.interests as interests0_3_, item0_.level as level0_3_, item0_.number_of_choices as number18_0_3_, item0_.parameters as parameters0_3_, item0_.question_precision as question20_0_3_, item0_.question_type as question21_0_3_, item0_.last_modified as last22_0_3_, item0_.stateid as stateid0_3_, item0_.variables as variables0_3_, item0_.negative as negative0_3_, item0_.phone_code as phone26_0_3_, item0_.abbreviation as abbrevi27_0_3_, item0_.phone as phone0_3_, item0_.address as address0_3_, item0_.zip_code as zip30_0_3_, item0_.content as content0_3_, item0_.item_type as item1_0_3_, node1_.id as id0_0_, node1_.comment as comment0_0_, node1_.comparator_type as comparator4_0_0_, node1_.depth as depth0_0_, node1_.name as name0_0_, node1_.parent_id as parent32_0_0_, node1_.serial_no as serial7_0_0_, node1_.version as version0_0_, node1_.latest_message as latest33_0_0_, node1_.read_only as read9_0_0_, node1_.unique_name as unique10_0_0_, node1_.univ_catalog_abbreviation as univ11_0_0_, node1_.syllabusUrl as syllabu12_0_0_, node1_.univ_catalog_number as univ13_0_0_, node1_.concept_text as concept14_0_0_, node1_.formulation as formula15_0_0_, node1_.interests as interests0_0_, node1_.level as level0_0_, node1_.number_of_choices as number18_0_0_, node1_.parameters as parameters0_0_, node1_.question_precision as question20_0_0_, node1_.question_type as question21_0_0_, node1_.last_modified as last22_0_0_, node1_.stateid as stateid0_0_, node1_.variables as variables0_0_, node1_.negative as negative0_0_, node1_.phone_code as phone26_0_0_, node1_.abbreviation as abbrevi27_0_0_, node1_.phone as phone0_0_, node1_.address as address0_0_, node1_.zip_code as zip30_0_0_, node1_.content as content0_0_, node1_.item_type as item1_0_0_, auditmessa2_.id as id5_1_, auditmessa2_.comment as comment5_1_, auditmessa2_.date as date5_1_, auditmessa2_.action as action5_1_, auditmessa2_.email as email5_1_, auditmessa2_.first_name as first6_5_1_, auditmessa2_.last_name as last7_5_1_, auditmessa2_.middle_initial as middle8_5_1_, auditmessa2_.version as version5_1_, tags3_.node as node5_, tagitem4_.id as tags5_, tagitem4_.id as id0_2_, tagitem4_.comment as comment0_2_, tagitem4_.comparator_type as comparator4_0_2_, tagitem4_.depth as depth0_2_, tagitem4_.name as name0_2_, tagitem4_.parent_id as parent32_0_2_, tagitem4_.serial_no as serial7_0_2_, tagitem4_.version as version0_2_, tagitem4_.latest_message as latest33_0_2_, tagitem4_.read_only as read9_0_2_, tagitem4_.item_type as item1_0_2_ from node item0_ left outer join node node1_ on item0_.parent_id=node1_.id left outer join audit_message auditmessa2_ on node1_.latest_message=auditmessa2_.id left outer join node_tags tags3_ on node1_.id=tags3_.node left outer join node tagitem4_ on tags3_.tags=tagitem4_.id where item0_.id=? and item0_.item_type in ('Item', 'Root', 'Catalog', 'World', 'DocumentCabinet', 'TagCabinet', 'DefaultTrash', 'ContentKnowledge', 'ExpectationSet', 'Federation', 'Folder', 'Document', 'InterestCollection', 'Interest', 'SubInterest', 'ConceptCollection', 'SkillCollection', 'Skill', 'Concept', 'MainItem', 'Subject', 'Course', 'ContentType', 'Topic', 'SubTopic', 'Question', 'ExpectationCategory', 'Expectation', 'Country', 'State',  'School', 'File')
 *  15:21:13,765 DEBUG SQL                      :401 - select auditmessa0_.id as id5_0_, auditmessa0_.comment as comment5_0_, auditmessa0_.date as date5_0_, auditmessa0_.action as action5_0_, auditmessa0_.email as email5_0_, auditmessa0_.first_name as first6_5_0_, auditmessa0_.last_name as last7_5_0_, auditmessa0_.middle_initial as middle8_5_0_, auditmessa0_.version as version5_0_ from audit_message auditmessa0_ where auditmessa0_.id=?
 *  15:21:13,765 DEBUG SQL                      :401 - select tags0_.node as node3_, tags0_.tags as tags3_, tagitem1_.id as id0_0_, tagitem1_.comment as comment0_0_, tagitem1_.comparator_type as comparator4_0_0_, tagitem1_.depth as depth0_0_, tagitem1_.name as name0_0_, tagitem1_.parent_id as parent32_0_0_, tagitem1_.serial_no as serial7_0_0_, tagitem1_.version as version0_0_, tagitem1_.latest_message as latest33_0_0_, tagitem1_.read_only as read9_0_0_, tagitem1_.item_type as item1_0_0_, node2_.id as id0_1_, node2_.comment as comment0_1_, node2_.comparator_type as comparator4_0_1_, node2_.depth as depth0_1_, node2_.name as name0_1_, node2_.parent_id as parent32_0_1_, node2_.serial_no as serial7_0_1_, node2_.version as version0_1_, node2_.latest_message as latest33_0_1_, node2_.read_only as read9_0_1_, node2_.unique_name as unique10_0_1_, node2_.univ_catalog_abbreviation as univ11_0_1_, node2_.syllabusUrl as syllabu12_0_1_, node2_.univ_catalog_number as univ13_0_1_, node2_.concept_text as concept14_0_1_, node2_.formulation as formula15_0_1_, node2_.interests as interests0_1_, node2_.level as level0_1_, node2_.number_of_choices as number18_0_1_, node2_.parameters as parameters0_1_, node2_.question_precision as question20_0_1_, node2_.question_type as question21_0_1_, node2_.last_modified as last22_0_1_, node2_.stateid as stateid0_1_, node2_.variables as variables0_1_, node2_.negative as negative0_1_, node2_.phone_code as phone26_0_1_, node2_.abbreviation as abbrevi27_0_1_, node2_.phone as phone0_1_, node2_.address as address0_1_, node2_.zip_code as zip30_0_1_, node2_.content as content0_1_, node2_.item_type as item1_0_1_, auditmessa3_.id as id5_2_, auditmessa3_.comment as comment5_2_, auditmessa3_.date as date5_2_, auditmessa3_.action as action5_2_, auditmessa3_.email as email5_2_, auditmessa3_.first_name as first6_5_2_, auditmessa3_.last_name as last7_5_2_, auditmessa3_.middle_initial as middle8_5_2_, auditmessa3_.version as version5_2_ from node_tags tags0_ left outer join node tagitem1_ on tags0_.tags=tagitem1_.id left outer join node node2_ on tagitem1_.parent_id=node2_.id left outer join audit_message auditmessa3_ on node2_.latest_message=auditmessa3_.id where tags0_.node=?
 *  15:21:13,765 DEBUG SQL                      :401 - select messages0_.node as node1_, messages0_.messages as messages1_, messages0_.index_message as index3_1_, auditmessa1_.id as id5_0_, auditmessa1_.comment as comment5_0_, auditmessa1_.date as date5_0_, auditmessa1_.action as action5_0_, auditmessa1_.email as email5_0_, auditmessa1_.first_name as first6_5_0_, auditmessa1_.last_name as last7_5_0_, auditmessa1_.middle_initial as middle8_5_0_, auditmessa1_.version as version5_0_ from node_messages messages0_ left outer join audit_message auditmessa1_ on messages0_.messages=auditmessa1_.id where messages0_.node=?
 *  15:21:13,781 DEBUG DefaultEditItemManager   :172 - Saving/Updating item Root parent NULL
 *  15:21:13,781 DEBUG HibernateItemDAO         :664 - update(): entity [Root ID 1] Root READ ONLY
 *  15:21:13,781 DEBUG HibernateItemDAO         :697 - Before saveOrUpdate()
 *  15:21:13,781 DEBUG SQL                      :401 - insert into audit_message (comment, date, action, email, first_name, last_name, middle_initial, version) values (?, ?, ?, ?, ?, ?, ?, ?)
 *  15:21:13,781 DEBUG SQL                      :401 - insert into audit_message (comment, date, action, email, first_name, last_name, middle_initial, version) values (?, ?, ?, ?, ?, ?, ?, ?)
 *  15:21:13,796 DEBUG SQL                      :401 - update node set comment=?, comparator_type=?, depth=?, name=?, parent_id=?, serial_no=?, version=?, latest_message=?, read_only=?, unique_name=? where id=? and version=?
 *  15:21:13,796 DEBUG SQL                      :401 - insert into node_messages (node, index_message, messages) values (?, ?, ?)
 *  15:21:13,796 DEBUG SQL                      :401 - insert into node_messages (node, index_message, messages) values (?, ?, ?)
 *  15:21:13,796 DEBUG HibernateItemDAO         :701 - Saved/updated entity 'Root' new version 4 audit message ID 105
 * </pre>
 * 
 * Compare with {@link TestLazyChildren}: there is no <code>select 1 ...</code>
 * query here.
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
public class TestLazyMessages extends TestEnvTestBase
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
	 * 
	 */
	@Test
	public void testAddMessage()
	{
		// Add a lot of messages
		AbstractEditItemBD<Item> bdItem = new StandAloneEditItemBD<Item>(Item.class,
				environment.getContext(), systemUser);
		Item item = getRoot();
		final int numMessages = 100;
		for (int i = 0; i < numMessages; i++)
		{
			item.addMessage(new AuditMessage(item, systemUser, AuditAction.UPDATED,
					"Message #" + i, 0));
		}
		bdItem.update(item, false);
		environment.getSession().evict(item);

		// Add another message
		logger.info("################### adding message ##################");
		item = bdItem.findById(Item.class, item.getId());
		item.addMessage(new AuditMessage(item, systemUser, AuditAction.UPDATED,
				"New Message", 0));
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
