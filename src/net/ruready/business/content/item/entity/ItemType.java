/*****************************************************************************************
 * Source File: ItemType.java
 ****************************************************************************************/
package net.ruready.business.content.item.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.ruready.business.content.catalog.entity.Catalog;
import net.ruready.business.content.catalog.entity.ContentKnowledge;
import net.ruready.business.content.catalog.entity.ContentType;
import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.catalog.entity.Expectation;
import net.ruready.business.content.catalog.entity.ExpectationCategory;
import net.ruready.business.content.catalog.entity.ExpectationSet;
import net.ruready.business.content.catalog.entity.SubTopic;
import net.ruready.business.content.catalog.entity.Subject;
import net.ruready.business.content.catalog.entity.Topic;
import net.ruready.business.content.concept.entity.Concept;
import net.ruready.business.content.concept.entity.ConceptCollection;
import net.ruready.business.content.document.entity.Document;
import net.ruready.business.content.document.entity.DocumentCabinet;
import net.ruready.business.content.document.entity.File;
import net.ruready.business.content.document.entity.Folder;
import net.ruready.business.content.interest.entity.Interest;
import net.ruready.business.content.interest.entity.InterestCollection;
import net.ruready.business.content.interest.entity.SubInterest;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.skill.entity.Skill;
import net.ruready.business.content.skill.entity.SkillCollection;
import net.ruready.business.content.tag.entity.MainTagItem;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.world.entity.City;
import net.ruready.business.content.world.entity.Country;
import net.ruready.business.content.world.entity.Federation;
import net.ruready.business.content.world.entity.School;
import net.ruready.business.content.world.entity.State;
import net.ruready.business.content.world.entity.World;
import net.ruready.common.discrete.Identifier;
import net.ruready.common.exception.UnsupportedOpException;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.common.tree.ImmutableTreeNode;

/**
 * Serves as an <code>Item</code> enumerated factory that instantiates
 * different types of items based on their identifier type.
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
 * @version Aug 4, 2007
 */
public enum ItemType implements Identifier, ImmutableTreeNode<ItemType, ItemType>
{
	// ========================= ENUMERATED TYPES ==========================

	// #####################################################################
	// HIERARCHY ADMINISTRATION (TOP NODES)
	// #####################################################################

	ITEM
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Item(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Item.class;
		}

		/**
		 * Return the child type for this item.
		 */
		@Override
		public ItemType getChildType()
		{
			return ITEM;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(ITEM);
			return types;
		}

		/**
		 * @return
		 * @see net.ruready.business.content.item.entity.ItemType#getCamelCaseName()
		 */
		@Override
		public String getCamelCaseName()
		{
			return "item";
		}

	},

	/**
	 * This corresponds to an abstract item class.
	 */
	MAIN_ITEM
	{

		/**
		 * @param name
		 * @param comment
		 * @return
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return MainItem.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	/**
	 * This item type corresponds to a unique item.
	 */
	ROOT
	{
		@Override
		public Item createItem(String name, String comment)
		{
			return new Root(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Root.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return MAIN_ITEM;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getAllValidChildren()
		 */
		@Override
		public List<ItemType> getAllValidChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			// Don't allow creating items that are unique; their creation
			// is a liability of the site initialization procedure.
			types.add(CATALOG);
			types.add(WORLD);
			types.add(DOCUMENT_CABINET);
			types.add(TAG_CABINET);
			types.add(DEFAULT_TRASH);
			return types;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			// Don't allow creating items that are unique; their creation
			// is a liability of the site initialization procedure.
			types.add(CATALOG);
			types.add(DOCUMENT_CABINET);
			return types;
		}
	},

	/**
	 * This item type corresponds to a unique item.
	 */
	DEFAULT_TRASH
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new DefaultTrash(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return DefaultTrash.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return ITEM;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(ITEM);
			return types;
		}
	},

	// #####################################################################
	// CATALOG HIERARCHY
	// #####################################################################

	CATALOG
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Catalog(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Catalog.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return SUBJECT;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(SUBJECT);
			return types;
		}
	},

	SUBJECT
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Subject(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Subject.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return COURSE;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(COURSE);
			return types;
		}
	},

	COURSE
	{
		@Override
		public Item createItem(String name, String comment)
		{
			return new Course(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Course.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return CONTENT_TYPE;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(CONTENT_KNOWLEDGE);
			types.add(EXPECTATION_SET);
			return types;
		}
	},

	/**
	 * This corresponds to an abstract item class.
	 */
	CONTENT_TYPE
	{
		/**
		 * @param name
		 * @param comment
		 * @return
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return ContentType.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	CONTENT_KNOWLEDGE
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new ContentKnowledge(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return ContentKnowledge.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return TOPIC;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(TOPIC);
			return types;
		}
	},

	TOPIC
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Topic(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Topic.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return SUB_TOPIC;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(SUB_TOPIC);
			return types;
		}
	},

	SUB_TOPIC
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new SubTopic(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return SubTopic.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return QUESTION;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(QUESTION);
			return types;
		}
	},

	QUESTION
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Question(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Question.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	EXPECTATION_SET
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new ExpectationSet(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return ExpectationSet.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return EXPECTATION_CATEGORY;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(EXPECTATION_CATEGORY);
			return types;
		}
	},

	EXPECTATION_CATEGORY
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new ExpectationCategory(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return ExpectationCategory.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return EXPECTATION;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(EXPECTATION);
			return types;
		}
	},

	EXPECTATION
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Expectation(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Expectation.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	// #####################################################################
	// WORLD HIERARCHY
	// #####################################################################

	WORLD
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new World(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return World.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return COUNTRY;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(COUNTRY);
			types.add(FEDERATION);
			return types;
		}
	},

	COUNTRY
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Country(name, comment, 0);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Country.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return CITY;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(CITY);
			return types;
		}
	},

	FEDERATION
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Federation(name, comment, 0);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Federation.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return STATE;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(STATE);
			return types;
		}
	},

	STATE
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new State(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return State.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return CITY;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(CITY);
			return types;
		}
	},

	CITY
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new City(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return City.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return SCHOOL;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(SCHOOL);
			return types;
		}
	},

	SCHOOL
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new School(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return School.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	// #####################################################################
	// DOCUMENT HIERARCHY
	// #####################################################################

	DOCUMENT_CABINET
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new DocumentCabinet(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return DocumentCabinet.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return FILE;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(FOLDER);
			types.add(DOCUMENT);
			return types;
		}
	},

	/**
	 * This corresponds to an abstract item class.
	 */
	FILE
	{

		/**
		 * @param name
		 * @param comment
		 * @return
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return File.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	FOLDER
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Folder(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Folder.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return FILE;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(FOLDER);
			types.add(DOCUMENT);
			return types;
		}
	},

	DOCUMENT
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Document(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Document.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	// #####################################################################
	// TAG HIERARCHY
	// #####################################################################

	TAG_CABINET
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new TagCabinet(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return TagCabinet.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return MAIN_TAG_ITEM;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(INTEREST_COLLECTION);
			types.add(CONCEPT_COLLECTION);
			types.add(SKILL_COLLECTION);
			return types;
		}
	},

	// /**
	// * This corresponds to an abstract item class.
	// */
	// TAG_FILE
	// {
	// @Override
	// public Item createItem(String name, String comment)
	// {
	// return null;
	// }
	//
	// /**
	// * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
	// */
	// @Override
	// public Class<? extends Item> getItemClass()
	// {
	// return TagFile.class;
	// }
	//
	// @Override
	// public ItemType getChildType()
	// {
	// return null;
	// }
	//
	// @Override
	// public List<ItemType> getChildren()
	// {
	// return new ArrayList<ItemType>();
	// }
	// },

	/**
	 * This corresponds to an abstract item class.
	 */
	MAIN_TAG_ITEM
	{

		/**
		 * @param name
		 * @param comment
		 * @return
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return MainTagItem.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	// TAG
	// {
	// @Override
	// public Item createItem(String name, String comment)
	// {
	// return new Tag(name, comment);
	// }
	//
	// /**
	// * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
	// */
	// @Override
	// public Class<? extends Item> getItemClass()
	// {
	// return Tag.class;
	// }
	//
	// @Override
	// public ItemType getChildType()
	// {
	// return null;
	// }
	//
	// @Override
	// public List<ItemType> getChildren()
	// {
	// return new ArrayList<ItemType>();
	// }
	// },

	// #####################################################################
	// INTEREST HIERARCHY
	// #####################################################################

	INTEREST_COLLECTION
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new InterestCollection(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return InterestCollection.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return INTEREST;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(INTEREST);
			return types;
		}
	},

	INTEREST
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Interest(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Interest.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return SUB_INTEREST;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(SUB_INTEREST);
			return types;
		}
	},

	SUB_INTEREST
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new SubInterest(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return SubInterest.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	// #####################################################################
	// CONCEPT HIERARCHY
	// #####################################################################

	CONCEPT_COLLECTION
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new ConceptCollection(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return ConceptCollection.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return CONCEPT;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(CONCEPT);
			return types;
		}
	},

	CONCEPT
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Concept(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Concept.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	},

	// #####################################################################
	// SKILL HIERARCHY
	// #####################################################################

	SKILL_COLLECTION
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new SkillCollection(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return SkillCollection.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return SKILL;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			List<ItemType> types = new ArrayList<ItemType>();
			types.add(SKILL);
			return types;
		}
	},

	SKILL
	{
		/**
		 * @see net.ruready.business.content.item.entity.ItemType#createItem(java.lang.String,
		 *      java.lang.String)
		 */
		@Override
		public Item createItem(String name, String comment)
		{
			return new Skill(name, comment);
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getItemClass()
		 */
		@Override
		public Class<? extends Item> getItemClass()
		{
			return Skill.class;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildType()
		 */
		@Override
		public ItemType getChildType()
		{
			return null;
		}

		/**
		 * @see net.ruready.business.content.item.entity.ItemType#getChildren()
		 */
		@Override
		public List<ItemType> getChildren()
		{
			return new ArrayList<ItemType>();
		}
	};

	// ========================= CONSTANTS ====================================

	// ========================= CONSTRUCTORS ==============================
	//
	// /**
	// * Create an item type by value. The numerical value of item types is
	// greater or
	// equal
	// * than Value_BASE and is a running index.
	// *
	// * @param value
	// * numerical value of the role (for comparisons and instantiations by
	// * value)
	// */
	// private ItemType(final long value)
	// {
	// this.value = value;
	// }

	// /**
	// * Create an item type by value.
	// *
	// * @param value
	// * numerical value of the role (for comparisons and instantiations by
	// * value)
	// * @return corresponding item type of <code>null</code> if not found
	// */
	// public static ItemType create(final long value)
	// {
	// // TODO: optimize using quick search because item types are ordered
	// // by their values (this is the convention we use in the enumerated types
	// above).
	// // Values are simply a a 0-based running index over types.
	// int numValues = ItemType.values().length;
	// if ((value < 0) || (value >= numValues))
	// {
	// return null;
	// }
	// for (ItemType itemType : ItemType.values())
	// {
	// if (itemType.getValue() == value)
	// {
	// return itemType;
	// }
	// }
	// return null;
	// }

	// ========================= FIELDS ====================================
	//
	// /**
	// * he numerical value of this item type. Must be greater or equal than
	// * {@link VALUE_BASE}.
	// */
	// private final long value;

	// ========================= STATIC METHODS ============================

	// ========================= IMPLEMENTATION: Identifier ===================

	/**
	 * Return the object's type. This would normally be the object's
	 * <code>getClass().getSimpleName()</code>, but objects might be wrapped
	 * by Hibernate to return unexpected names. As a rule, the type string
	 * should not contain spaces and special characters.
	 */
	public String getType()
	{
		// The enumerated type's name satisfies all the type criteria, so use
		// it.
		return name();
	}

	// ========================= IMPLEMENTATION: ImmutableTreeNode =========

	/**
	 * Return the data represented by this {@link ItemType}.
	 * 
	 * @return this object
	 */
	public ItemType getData()
	{
		return this;
	}

	/**
	 * Return a collection of permissible child types that can exist under this
	 * item. Returns an empty list if the item is not allowed to have children.
	 */
	public List<ItemType> getAllValidChildren()
	{
		return getChildren();
	}

	/**
	 * Return a collection of permissible child types that can exist under this
	 * item and can be added by a non-system operation (e.g. vi a auser
	 * interface). Returns an empty list if the item is not allowed to have
	 * children.
	 */
	abstract public List<ItemType> getChildren();

	/**
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#getNumChildren()
	 */
	public int getNumChildren()
	{
		return getChildren().size();
	}

	/**
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#getParent()
	 */
	public ItemType getParent()
	{
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#getSiblings()
	 */
	public Collection<ItemType> getSiblings()
	{
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#getSize()
	 */
	public int getSize()
	{
		throw new UnsupportedOpException(
				"Traversing an ItemType tree is not supported yet");
	}

	/**
	 * @param height
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#getSuperParent(int)
	 */
	public ItemType getSuperParent(int height)
	{
		return null;
	}

	/**
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#hasChildren()
	 */
	public boolean hasChildren()
	{
		return getSize() > 0;
	}

	/**
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#printData()
	 */
	public String printData()
	{
		return toString();
	}

	/**
	 * @return
	 * @see net.ruready.common.tree.ImmutableTreeNode#shallowClone()
	 */
	public Object shallowClone()
	{
		return ItemType.valueOf(getType());
	}

	/**
	 * @param destination
	 * @return
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	public void mergeInto(final ShallowCloneable destination)
	{
		throw new UnsupportedOpException(
				"Cannot merge into an enumerated type (ItemType)");
	}

	// ========================= METHODS ===================================

	/**
	 * A factory method that creates an item corresponding to this type.
	 * 
	 * @param name
	 *            name of this item
	 * @param comment
	 *            comment on this item
	 * @return an item instance with the specified name and comment
	 */
	abstract public Item createItem(String name, String comment);

	/**
	 * Return the class type of this item. Must be or be a sub-class of
	 * {@link Item}.
	 */
	abstract public Class<? extends Item> getItemClass();

	/**
	 * Return the child type of this item. Returns <code>null</code> if the
	 * item is not allowed to have children.
	 */
	abstract public ItemType getChildType();

	/**
	 * Return the camel case string representation of this item type. For
	 * instance, if ItemType is <code>SUB_TOPIC</code>, the camel case form
	 * is "subTopic". The returned string always starts with a lower case
	 * letter. The separation of strings into parts is done by splitting around
	 * <code>CommonNames.MISC.SEPARATOR</code>.
	 * 
	 * @return the camel case string representation of this item type
	 */
	public String getCamelCaseName()
	{
		String parts[] = name().split(CommonNames.MISC.SEPARATOR);
		StringBuffer result = TextUtil.emptyStringBuffer();
		int count = 0;
		for (String part : parts)
		{
			String formattedPart = part.toLowerCase();
			if ((count > 0) && (formattedPart.length() > 0))
			{
				formattedPart = Character.toUpperCase(formattedPart.charAt(0))
						+ formattedPart.substring(1);
			}
			result.append(formattedPart);
			count++;
		}
		return result.toString();
	}

	// ========================= GETTERS & SETTERS =========================

	// /**
	// * Return the numerical value of this itemType.
	// *
	// * @return numerical value of this itemType
	// */
	// public long getValue()
	// {
	// return value;
	// }

}
