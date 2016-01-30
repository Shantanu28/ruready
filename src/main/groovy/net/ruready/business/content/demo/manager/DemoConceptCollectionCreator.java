/*****************************************************************************************
 * Source File: DemoCatalogCreator.java
 ****************************************************************************************/
package net.ruready.business.content.demo.manager;

import net.ruready.business.content.concept.entity.Concept;
import net.ruready.business.content.concept.entity.ConceptCollection;
import net.ruready.business.content.item.entity.Item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A factory of a mock concept collection created for testing and upon site
 * initialization.
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
 * @version Aug 1, 2007
 */
class DemoConceptCollectionCreator implements DemoItemCreator<ConceptCollection>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(DemoConceptCollectionCreator.class);

	// ========================= FIELDS ====================================

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * In the future: we might hide constructor in this utility class and use a
	 * facade instead.
	 * </p>
	 */
	public DemoConceptCollectionCreator()
	{

	}

	// ========================= IMPLEMENTATION: DemoItemCreator ==============

	/**
	 * Initialize a base catalog using a hard-coded tree of nodes. This is
	 * useful for testing and site initialization defaulting.
	 * 
	 * @param uniqueName
	 *            catalog's uniqueName identifier
	 * @see net.ruready.business.content.demo.manager.DemoItemCreator#createItem(java.lang.String)
	 */
	public ConceptCollection createItem(String uniqueName)
	{
		// Create some items. Because we only add items,
		// the tree automatically refreshes and appears sorted.
		logger.info("Creating base concept collection");
		ConceptCollection conceptCollection = null;
		Item parent = null;
		Item child = null;

		conceptCollection = new ConceptCollection(uniqueName, "Math Concepts");
		child = conceptCollection;

		// =======================================
		// Add some concepts
		// =======================================
		String[] conceptNames =
		{ "Quadratic equation", "Expression", "Equation", "Linear equation", "Factor",
				"Number", "Rational number", "Real number", "Complex number" };

		parent = child;
		for (String conceptName : conceptNames)
		{
			child = new Concept(conceptName, null);
			parent.addChild(child);
		}

		parent = parent.getParent();

		// This will sort children, assign serial #s, etc.
		// conceptCollection.refreshAll();

		return conceptCollection;
	}
}
