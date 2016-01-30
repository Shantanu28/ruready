/*****************************************************************************************
 * Source File: DefaultMainItemManager.java
 ****************************************************************************************/
package net.ruready.business.content.main.manager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruready.business.content.demo.util.ItemDemoUtil;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.manager.AbstractEditItemManager;
import net.ruready.business.content.item.manager.DefaultEditItemManager;
import net.ruready.business.content.item.util.ItemCounter;
import net.ruready.business.content.main.entity.MainItem;
import net.ruready.business.content.main.entity.Root;
import net.ruready.business.content.main.entity.UniqueItem;
import net.ruready.business.content.main.exception.InvalidItemTypeException;
import net.ruready.business.content.rl.ContentNames;
import net.ruready.business.content.tag.entity.TagCabinet;
import net.ruready.business.content.trash.entity.DefaultTrash;
import net.ruready.business.content.world.entity.World;
import net.ruready.business.user.entity.User;
import net.ruready.business.user.entity.system.SystemUserFactory;
import net.ruready.business.user.entity.system.SystemUserID;
import net.ruready.common.eis.exception.RecordNotFoundException;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.rl.ResourceLocator;
import net.ruready.eis.common.tree.NodeDAO;
import net.ruready.eis.content.item.ItemDAO;
import net.ruready.port.xml.content.WorldDataParser;
import net.ruready.port.xml.content.WorldDataTarget;
import net.ruready.web.common.rl.WebAppNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * This service allows item manipulation: creating a new item, updating an
 * existing item, deleting an item, and listing items by certain criteria.
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
 * @version Aug 11, 2007
 */
public class DefaultMainItemManager implements AbstractMainItemManager
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DefaultMainItemManager.class);

	/**
	 * A map of unique item type to its unique name in the database.
	 */
	protected static Map<Class<? extends UniqueItem>, String> uniqueNameMap = new HashMap<Class<? extends UniqueItem>, String>();

	static
	{
		// Initialize unique item name map
		uniqueNameMap.put(Root.class, ContentNames.UNIQUE_NAME.ROOT);
		uniqueNameMap.put(DefaultTrash.class, ContentNames.UNIQUE_NAME.TRASH);
		uniqueNameMap.put(World.class, ContentNames.UNIQUE_NAME.WORLD);
		uniqueNameMap.put(TagCabinet.class, ContentNames.UNIQUE_NAME.TAG_CABINET);
	}

	// ========================= FIELDS ====================================

	/**
	 * Locator of the DAO factory that creates DAOs for {@link Item} classes.
	 */
	protected final ResourceLocator resourceLocator;

	/**
	 * Retrieved from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects and other useful properties.
	 */
	protected final ApplicationContext context;

	/**
	 * The user requesting DAO operations.
	 */
	protected final User user;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create a service that will use a resource locator and a DAO factory to
	 * read/write items to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            web application context
	 * @param user
	 *            user requesting the operations
	 */
	public DefaultMainItemManager(final ResourceLocator resourceLocator,
			final ApplicationContext context, final User user)

	{
		this.resourceLocator = resourceLocator;
		this.eisManager = this.resourceLocator.getDAOFactory();
		this.context = context;
		this.user = user;
	}

	// ========================= IMPLEMENTATION: AbstractMainItemManager ===

	/**
	 * Create a base object of this main item type in the database if not found.
	 * The base hierarchy consists of some example items for testing purposes.
	 * 
	 * @param <T>
	 *            type of main item
	 * @param class
	 *            of main item, must match <code>T</code>
	 * @param parentId
	 *            base hierarchy's parent (root) node's id
	 * @param uniqueName
	 *            base hierarchy's name
	 * @param fromFile
	 *            if true, loads base from file. Otherwise, uses hard-coded data
	 * @param depenencies
	 *            main items that this main item depend on. See the constructors
	 *            of the individual demo creator classes in this package.
	 * @return the base main item
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#createBase(java.lang.Class,
	 *      long, java.lang.String, boolean,
	 *      net.ruready.business.content.main.entity.MainItem[])
	 */
	@SuppressWarnings("unchecked")
	public <T extends MainItem> T createBase(Class<T> itemClass, long parentId,
			final String uniqueName, final boolean fromFile,
			final MainItem... depenencies)
	{
		// Find all items of this type
		AbstractEditItemManager<T> managerItem = new DefaultEditItemManager<T>(itemClass,
				resourceLocator, context, user);
		List<T> mainItems = managerItem.findAll(itemClass);

		// Create base mainItem only if no items of this type exist
		if (mainItems.isEmpty())
		{
			// Load data
			T dataToSave = ((itemClass == World.class) && fromFile) ? (T) loadBaseFromFile(
					uniqueName, ContentNames.FILE.WORLD_DATA_FILE)
					: ItemDemoUtil.createBase(itemClass, uniqueName, depenencies);
			return createBase(itemClass, parentId, uniqueName, false, dataToSave,
					depenencies);
		}
		else
		{
			// If the item type is not unique, we don't know which one to
			// return;
			// if it is, return the unique item of this type
			T firstItem = mainItems.get(0);
			return firstItem.isUnique() ? firstItem : null;
		}
	}

	/**
	 * @param <T>
	 *            Was originally <T extends MainItem & UniqueItem>, but reduced
	 *            to <T extends MainItem> so that we can cast the itemClass
	 *            argument in some calls to this method.
	 * @param itemClass
	 * @param parentId
	 * @return
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#createUnique(java.lang.Class,
	 *      long)
	 */
	public <T extends MainItem> T createUnique(Class<T> itemClass, long parentId)
	{
		if (!UniqueItem.class.isAssignableFrom(itemClass))
		{
			throw new InvalidItemTypeException("Item type " + itemClass
					+ " is not unique", itemClass);
		}

		// Look for an existing unique item of this type
		T item = this.findUnique(itemClass);
		if (item == null)
		{
			// Item can not found, Create a one
			logger.info("Creating a unique node of type " + itemClass.getSimpleName());
			String name = uniqueNameMap.get(itemClass);
			if (name == null)
			{
				throw new IllegalStateException(
						"Cannot create unique instance of "
								+ itemClass
								+ " because there is no unique name entry for it in the standard naming map (uniqueNameMap)");
			}
			try
			{
				item = itemClass.getConstructor(String.class, String.class).newInstance(
						name, name);
			}
			catch (Exception e)
			{
				throw new IllegalStateException(
						"Cannot create an item instance with the standard item constructor!");
			}

			if (parentId == CommonNames.MISC.INVALID_VALUE_INTEGER)
			{
				// ========================================================
				// Save new item under nothing in the database (root node)
				// ========================================================
				User systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
				ItemDAO<Item> itemDAO = (ItemDAO<Item>) eisManager.getDAO(Item.class,
						context);
				itemDAO.update(item, systemUser.getId());
			}
			else
			{
				// ========================================================
				// Save new item under the parent in the database
				// ========================================================
				User systemUser = SystemUserFactory.getSystemUser(SystemUserID.SYSTEM);
				AbstractEditItemManager<Item> itemManager = new DefaultEditItemManager<Item>(
						Item.class, resourceLocator, context, systemUser);
				itemManager.createUnder(parentId, item);
			}
		}
		return item;
	}

	/**
	 * Find the unique main item of a specified type object in the database.
	 * 
	 * @param <T>
	 *            main item type
	 * @param itemClass
	 *            main item class
	 * @return unique main item
	 * @throws RecordNotFoundException
	 *             if a record with this id was not found
	 * 
	 * if a DAO problem has occurred
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#findUnique(java.lang.Class)
	 */
	public <T extends MainItem> T readUnique(Class<T> itemClass)
	{
		return this.findUnique(itemClass, true);
	}

	/**
	 * Find the unique main item of a specified type object in the database.
	 * 
	 * @param <T>
	 *            main item type
	 * @param itemClass
	 *            main item class
	 * @return unique main item
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#findUnique(java.lang.Class)
	 */
	public <T extends MainItem> T findUnique(Class<T> itemClass)
	{
		return this.findUnique(itemClass, false);
	}

	/**
	 * Find the unique object of this type the database.
	 * 
	 * @param <T>
	 *            type of unique main item
	 * @param class
	 *            of main item, must match <code>T</code>
	 * @return the unique object of this type
	 * @throws RecordNotFoundException
	 *             if <T> does not extend MainItem, or if a record with this id
	 *             was not found
	 * 
	 * if a DAO problem has occurred
	 */
	@SuppressWarnings("unchecked")
	public Item readUniqueByType(final ItemType itemType)
	{
		if (itemType == null)
		{
			throw new InvalidItemTypeException("No item type supplied", itemType);
		}
		try
		{
			return this.readUnique((Class<? extends MainItem>) itemType.getItemClass());
		}
		catch (ClassCastException e)
		{
			throw new InvalidItemTypeException("Invalid item type", itemType);
		}
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Create a base mainItem in the database. The demo mainItem consists of
	 * some example items for testing purposes. The item's unique name is taken
	 * from the map {@link #uniqueNameMap} of hard-coded names.
	 * 
	 * @param <T>
	 * @param itemClass
	 * @param parentId
	 *            base mainItem's parent (root) node's id
	 * @param uniqueName
	 *            base mainItem's name
	 * @param override
	 *            if true, will override existing mainItem with that name
	 * @param dataToSave
	 *            base main item hierarchy to save if not found in the database
	 *            yet
	 * @param depenencies
	 *            main items that this main item depend on. See the constructors
	 *            of the individual demo creator classes in this package.
	 * @return base mainItem
	 */
	private <T extends MainItem> T createBase(Class<T> itemClass, final long parentId,
			final String uniqueName, final boolean override, final T dataToSave,
			final MainItem... depenencies)
	{
		AbstractEditItemManager<Item> managerItem = new DefaultEditItemManager<Item>(
				Item.class, resourceLocator, context, user);
		Item parent = managerItem.read(Item.class, parentId);
		if (parent == null)
		{
			throw new ApplicationException("createUnder(): parent (root) node not found");
		}
		// Attempt to find previous mainItem with this name in the database
		T mainItem = managerItem
				.findByUniqueProperty(itemClass, NodeDAO.NAME, uniqueName);

		if (override)
		{
			// Override existing mainItem if found
			if (mainItem != null)
			{
				logger.info("Deleting existing base mainItem (id " + mainItem.getId()
						+ " )");
				managerItem.delete(mainItem, false);
			}

			// Create a new base mainItem
			logger.info("Creating a base mainItem");
			mainItem = ItemDemoUtil.createBase(itemClass, uniqueName, depenencies);

			// Save mainItem to database
			logger.info("Saving mainItem to database");
			parent.addChild(mainItem);
			managerItem.updateAll(parent);
		}
		else
		{
			// Don't override existing mainItem
			if (mainItem == null)
			{
				// Create a new base mainItem
				logger.info("Creating a base mainItem");
				mainItem = dataToSave;

				// Save mainItem to database
				logger.info("Saving mainItem to database");
				parent.addChild(mainItem);
				managerItem.updateAll(parent);
			}
		}

		return mainItem;
	}

	/**
	 * Find the unique main item of a specified type object in the database.
	 * 
	 * @param <T>
	 *            main item type
	 * @param itemClass
	 *            main item class
	 * @param throwExceptionIfNotFound
	 *            throws a {@link RecordNotFoundException} if item not found
	 * @return unique main item
	 * @see net.ruready.business.content.main.manager.AbstractMainItemManager#findUnique(java.lang.Class)
	 */
	private <T extends MainItem> T findUnique(Class<T> itemClass,
			final boolean throwExceptionIfNotFound)
	{
		AbstractEditItemManager<T> itemManager = new DefaultEditItemManager<T>(itemClass,
				resourceLocator, context, user);
		// Assuming that there is only one World object in the database; this
		// means that when supporting multiple languages, each language should
		// have a separate database.
		List<T> items = itemManager.findAll(itemClass);
		if (items.isEmpty())
		{
			if (throwExceptionIfNotFound)
			{
				throw new RecordNotFoundException(null, "Unique item not found",
						WebAppNames.KEY.EXCEPTION.RECORD_EXISTS_EDIT_ITEM, itemClass);
			}
			else
			{
				return null;
			}
		}
		T item = items.get(0);
		item = itemManager.read(itemClass, item.getId());

		// This an the alternative design:
		// MainItem world = bdItem.findByUniqueProperty(MainItem.class,
		// NodeDAO.NAME, ContentNames.UNIQUE_NAME.WORLD);

		return item;
	}

	/**
	 * Load a world data file using the World 1.4 XSD parser.
	 * 
	 * @param fileName
	 *            input file name
	 * @return parser target
	 * @throws Exception
	 */
	private World loadBaseFromFile(final String uniqueName, final String fileName)
	{
		WorldDataParser parser = new WorldDataParser();
		WorldDataTarget target = parser.getTarget();
		// logger.debug("<<<<<<<<<<<<<<<<< Parsing ... >>>>>>>>>>>>>>>>>>>");
		try
		{
			String dir = resourceLocator
					.getProperty(WebAppNames.RESOURCE_LOCATOR.PROPERTY.DATA_DIR);
			String fullFileName = dir + File.separator + fileName;
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fullFileName);
			parser.parse(new InputSource(is));
		}
		catch (SAXParseException spe)
		{
			StringBuffer sb = new StringBuffer(spe.toString());
			sb.append("\n Line number: " + spe.getLineNumber());
			sb.append("\nColumn number: " + spe.getColumnNumber());
			sb.append("\n Public ID: " + spe.getPublicId());
			sb.append("\n System ID: " + spe.getSystemId() + "\n");
			logger.error(sb.toString());
			throw new ApplicationException(sb.toString());
		}
		catch (IOException e)
		{
			throw new ApplicationException(e, e.toString());
		}
		catch (Exception e)
		{
			throw new ApplicationException(e, "Malformed world data file");
		}

		// logger.debug("Target\n" + target);
		logger
				.debug("Item counts:\n"
						+ ItemCounter.generateItemCounts(target.getWorld()));

		// Rename world to unique name
		World world = target.getWorld();
		world.setName(uniqueName);

		return world;
	}
}
