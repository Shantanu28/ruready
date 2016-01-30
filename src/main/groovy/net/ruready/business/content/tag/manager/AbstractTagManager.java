/*****************************************************************************************
 * Source File: AbstractTagManager.java
 ****************************************************************************************/
package net.ruready.business.content.tag.manager;

import java.util.List;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.tag.entity.TagItem;
import net.ruready.common.rl.BusinessManager;

/**
 * This service allows database queries within the {@link TagItem} hierarchy, as well as
 * creating a mock interest hierarchy in the database.
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
 * @version Jul 25, 2007
 */
public interface AbstractTagManager extends BusinessManager
{
	// ========================= ABSTRACT METHODS ==========================

	/**
	 * List all tags of a certain type in the database of all descendants of a course.
	 * 
	 * @param <T>
	 *            tag type
	 * @param course
	 *            course to search in
	 * @param tagClass
	 *            tag class
	 * @return a list of tags
	 * 
	 *             if there a DAO problem occurred
	 */
	<T extends TagItem> List<T> findTagsUnderCourse(final Course course, Class<T> tagClass);
}
