package net.ruready.web.menugroup.entity;

import net.ruready.common.discrete.Identifiable;
import net.ruready.common.discrete.Identifier;
import net.ruready.common.pointer.PubliclyCloneable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A useful struct that holds data required to construct and stylize a drop-down
 * menu.
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
 * @version Nov 7, 2007
 */
public class MenuProperties<ID extends Identifier, T extends Identifiable<ID>> implements
		PubliclyCloneable
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(MenuProperties.class);

	// ========================= FIELDS ====================================

	// =============================
	// Required properties
	// =============================

	/**
	 * Unique identifier of the menu within a menu group.
	 */
	public ID identifier;

	/**
	 * Is a selection required on the menu.
	 */
	public boolean selectionRequired;

	/**
	 * i18n key for no selection option, if exists on the menu.
	 */
	public String noSelectionKey;

	/**
	 * Type of objects represented by menu option values (e.g. an Item class).
	 */
	public Class<? extends T> clazz;

	/**
	 * A parent class that contains the domain of objects represented by menu
	 * option values (e.g. Catalog class)
	 */
	public ID parentIdentifier;

	/**
	 * Menu's name. A unique identifier within a menu group.
	 */
	public String name;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize no fields.
	 */
	public MenuProperties()
	{

	}

	/**
	 * Initialize minimal fields.
	 * 
	 * @param identifier
	 *            Unique identifier of the menu
	 * @param selectionRequired
	 *            Is a selection required on the menu
	 * @param noSelectionKey
	 *            i18n key for no selection option, if exists on the menu
	 */
	public MenuProperties(final ID identifier, final boolean selectionRequired,
			final String noSelectionKey)
	{
		this(identifier, selectionRequired, noSelectionKey, null, null, null);
	}

	/**
	 * Initialize all fields.
	 * 
	 * @param identifier
	 *            Unique identifier of the menu
	 * @param selectionRequired
	 *            Is a selection required on the menu
	 * @param noSelectionKey
	 *            i18n key for no selection option, if exists on the menu
	 * @param clazz
	 *            Type of objects represented by menu option values (e.g. an
	 *            Item class)
	 * @param parentIdentifier
	 *            A parent class that contains the domain of objects represented
	 *            by menu option values (e.g. Catalog class)
	 */
	public MenuProperties(final ID identifier, final boolean selectionRequired,
			final String noSelectionKey, final Class<? extends T> clazz,
			final ID parentIdentifier)
	{
		this(identifier, selectionRequired, noSelectionKey, parentIdentifier, clazz, null);
	}

	/**
	 * @param identifier
	 *            Unique identifier of the menu
	 * @param selectionRequired
	 *            Is a selection required on the menu
	 * @param noSelectionKey
	 *            i18n key for no selection option, if exists on the menu
	 * @param clazz
	 *            Type of objects represented by menu option values (e.g. an
	 *            Item class)
	 * @param parentIdentifier
	 *            A parent class that contains the domain of objects represented
	 *            by menu option values (e.g. Catalog class)
	 * @param name
	 *            Menu's name. A unique identifier within a menu group
	 */
	public MenuProperties(final ID identifier, final boolean selectionRequired,
			final String noSelectionKey, final ID parentIdentifier,
			final Class<? extends T> clazz, final String name)
	{
		super();
		this.identifier = identifier;
		this.clazz = clazz;
		this.selectionRequired = selectionRequired;
		this.noSelectionKey = noSelectionKey;
		this.parentIdentifier = parentIdentifier;
		this.name = name;
	}

	// ========================= IMPLEMENTATION: Object =====================

	/**
	 * Result of equality of two menu property holders, based on their names.
	 * 
	 * @param o
	 *            The other <code>MenuProperty</code> object.
	 * @return boolean The result of equality.
	 */
	@Override
	public boolean equals(Object obj)
	{
		// Standard checks to ensure the adherence to the general contract of
		// the equals() method
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass()))
		{
			return false;
		}
		// Cast to friendlier version
		MenuProperties<?, ?> other = (MenuProperties<?, ?>) obj;

		// Do not use compareTo() == 0 for a generic type because of unchecked
		// warning
		return (name == null) ? false : name.equals(other.name);
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Return a deep copy of this object. Must be implemented for this object to
	 * serve as a target (or part of a target) of an assembly.
	 * 
	 * @return a deep copy of this object.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MenuProperties<ID, T> clone()
	{
		try
		{
			return (MenuProperties<ID, T>) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// This shouldn't happen, because we are Cloneable
			throw new InternalError("clone() failed: " + e.getMessage());
		}
	}
	// ========================= GETTERS & SETTERS =========================

}
