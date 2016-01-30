/*****************************************************************************************
 * Source File: AbstractWorldManager.java
 ****************************************************************************************/
package net.ruready.business.content.world.manager;

import java.util.List;

import net.ruready.business.content.world.entity.School;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.BusinessManager;

/**
 * This service allows database queries within the World hierarchy: get schools, teachers
 * within a school, etc., as well as creating a mock world in the database.
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
public interface AbstractWorldManager extends BusinessManager
{
	// ========================= ABSTRACT METHODS ==========================

	// ========================= LOCATION-RELATED METHODS ==================

	/**
	 * List all schools in a world hierarchy.
	 * 
	 * @param worldUniqueName
	 *            root node's unique identifier
	 * @return a list of all schools in this world
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public List<School> findSchoolsInWorld(final String worldUniqueName);

	/**
	 * Search for the teacher link that matches a unique ID identifier. If this teacher
	 * link is not found, this method will not throw an exception, but when we try to
	 * access the returned object, Hibernate will throw an
	 * <code>org.hibernate.ObjectNotFoundException</code>.
	 * 
	 * @param id
	 *            item ID to search by
	 * @return teacher link, if found
	 * @throws ApplicationException
	 *             if there a DAO problem occurred
	 */
	public School findSchoolById(final long id);
	
	/**
	 * Search for schools whose name begins with a search string
	 * @param searchString the search string to match against 
	 * @return the list of matching schools
	 */
	public List<School> findSchoolByPartialName(final String searchString);
	
	// ========================= UTILITY AND TESTING METHODS ===============
}
