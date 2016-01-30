/*****************************************************************************************
 * Source File: ElementComparator.java
 ****************************************************************************************/
package net.ruready.port.xml.content;

import java.util.Comparator;

import net.ruready.common.parser.xml.TagAttachment;

/**
 * Equalizer of tag elements.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 11, 2007
 */
class ElementComparator implements Comparator<Object>
{
	// ========================= IMPLEMENTATION: Comparator<Object> ==================

	/**
	 * Compare two objects. If they are not both instances of {@link TagAttachment},
	 * returns 1. Otherwise, it returns 0 if and only if the elements' keys are equal.
	 * 
	 * @param element1
	 *            left operand to be compared
	 * @param element2
	 *            right operand to be compared
	 * @return the result of comparison
	 */
	public int compare(Object element1, Object element2)
	{
		if ((element1 == null) || (!(element1 instanceof TagAttachment))
				|| (element2 == null) || (!(element2 instanceof TagAttachment)))
		{
			return 1;
		}
		TagAttachment e1 = (TagAttachment) element1;
		TagAttachment e2 = (TagAttachment) element2;
		return (e1.getTagName() == null) ? ((e2.getTagName() == null) ? 0 : 1) : e1
				.getTagName().compareTo(e2.getTagName());
	}

	// ========================= IMPLEMENTATION: Identifier ===================
}
