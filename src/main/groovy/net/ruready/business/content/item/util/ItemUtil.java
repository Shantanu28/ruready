/*****************************************************************************************
 * Source File: ItemUtil.java
 ****************************************************************************************/
package net.ruready.business.content.item.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.ruready.business.common.tree.entity.Node;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Centralizes utilities related to item naming, copying and merging.
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
 * @version Jul 29, 2007
 */
public class ItemUtil implements Utility
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ItemUtil.class);

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This library consists of static methods only and cannot be instantiated.
	 */
	private ItemUtil()
	{

	}

	// ========================= STATIC METHODS ============================

	/**
	 * Generate a new valid child name given a name space item list. This is
	 * used as a renaming convention when moving/copying a new child under this
	 * item.
	 * 
	 * @param requestedName
	 *            requested name of the new child
	 * @param nameSpace
	 *            list of items to verify the new name against.
	 * @return the original name of the new child, or the name with a proper
	 *         appending of a number (like "name [2]" or "name [3]") given the
	 *         current children names, so that the new child name will be
	 *         different from all existing child names. if the child name is
	 *         invalid, returns <code>null</code>.
	 */
	public static final String generateNewChildName(final String requestedName,
			final Collection<? extends Node> nameSpace)
	{
		// Item's entered name cannot contain "[" or "]" (alphanumeric +
		// spaces).
		// Thus, first split requestedName into the prefix (the "real name") and
		// an optional system-added "[#]" suffix.
		final String[] partsRequestedName = requestedName.split(" \\[");
		final String requestedLegalName = partsRequestedName[0];
		try
		{
			final StringBuffer generatedName = new StringBuffer(requestedLegalName);
			final String regExp = generatedName + "(\\s\\[[0-9]*\\])?";
			final Pattern p = Pattern.compile(regExp);
			int value = 0;
			int maxValue = 0;
			for (Node child : nameSpace)
			{
				final String childName = child.getName().trim();
				final Matcher m = p.matcher(childName);
				if (childName.equals(requestedLegalName.trim()))
				{
					// childName = requestedName up to spaces
					maxValue = 1;
					// logger.debug("childName '" + childName
					// + "' equals up to spaces, maxValue " + maxValue);
				}
				else if (m.matches())
				{
					// childName = requestedName [#] where # = some number.
					// Isolate the
					// number.
					String[] parts1 = childName.split(requestedLegalName + " \\[");
					String[] parts2 = parts1[1].split("\\]");
					try
					{
						value = Integer.parseInt(parts2[0]);
						maxValue = Math.max(value, maxValue);
						// logger.debug("childName '" + childName + "' matches
						// pattern "
						// + regExp + ", maxValue " + maxValue);
					}
					catch (NumberFormatException e)
					{

					}
				}
				// else
				// {
				// logger.debug("childName '" + childName
				// + "' does not match pattern " + regExp);
				// }
			}
			if (maxValue > 0)
			{
				generatedName.append(" [").append(maxValue + 1).append("]");
			}
			return generatedName.toString();
		}
		catch (PatternSyntaxException e)
		{
			throw new ApplicationException("Could not generate new child name: "
					+ e.toString());
		}
	}

	// ========================= PRIVATE METHODS ===========================
}
