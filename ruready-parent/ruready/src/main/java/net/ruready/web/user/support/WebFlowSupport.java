package net.ruready.web.user.support;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.content.world.exports.AbstractWorldBD;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.business.user.exports.AbstractUserBD;
import net.ruready.business.user.exports.AbstractUserGroupBD;
import net.ruready.business.user.exports.AbstractUserRoleBD;
import net.ruready.web.common.support.DefaultWebFlowSupport;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.content.item.imports.StrutsWorldBD;
import net.ruready.web.ta.imports.StrutsStudentTranscriptBD;
import net.ruready.web.user.imports.StrutsUserBD;
import net.ruready.web.user.imports.StrutsUserGroupBD;
import net.ruready.web.user.imports.StrutsUserRoleBD;

import org.springframework.webflow.execution.RequestContext;

/**
 * Various utility methods for integrating Spring Web Flow and Struts, as well as
 * some convenience methods for getting to the business delegates.
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
* @author Jeremy Lund
* @version Oct 12, 2007
*/
public class WebFlowSupport extends DefaultWebFlowSupport
{
	private static final String SELF_ERROR_MESSAGE = ".self.error.message";
	
	/**
	 * Convenience method for adding an error message for a self-service operation
	 * @param context context the request context
	 * @return the message key for the self-service message
	 * @throws Exception
	 */
	public final String getSelfErrorMessage(final RequestContext context) throws Exception
	{
		return getMessageKey(context, SELF_ERROR_MESSAGE);
	}
	
	/**
	 * Given the request context, returns a Course business delegate
	 * @param context the request context
	 * @return the Course business delegate
	 */
	public final AbstractEditItemBD<Course> getCourseBD(final RequestContext context)
	{
		return new StrutsEditItemBD<Course>(Course.class,getWebApplicationContext(context),getSystemUser());
	}
	
	/**
	 * Given the request context, returns a User business delegate
	 * @param context the request context
	 * @return the User business delegate
	 */
	public final AbstractUserBD getUserBD(final RequestContext context)
	{
		return new StrutsUserBD(getWebApplicationContext(context));
	}
	
	/**
	 * Given the request context, returns a UserGroup business delegate
	 * @param context the request context
	 * @return the UserGroup business delegate
	 */
	public final AbstractUserGroupBD getUserGroupBD(final RequestContext context)
	{
		return new StrutsUserGroupBD(getWebApplicationContext(context));
	}
	
	/**
	 * Given the request context, returns a UserRole business delegate
	 * @param context the request context
	 * @return the UserRole business delegate
	 */
	public final AbstractUserRoleBD getUserRoleBD(final RequestContext context)
	{
		return new StrutsUserRoleBD(getWebApplicationContext(context));
	}
	
	/**
	 * Given the request context, returns a World business delegate
	 * @param context the request context
	 * @return the World business delegate
	 */
	public final AbstractWorldBD getWorldBD(final RequestContext context)
	{
		return new StrutsWorldBD(getSystemUser(), getWebApplicationContext(context));
	}
	
	/**
	 * Given the request context, returns a Student Transcript business delegate
	 * @param context the request context
	 * @return the Student Transcript business delegate
	 */
	public final AbstractStudentTranscriptBD getStudentTranscriptBD(final RequestContext context)
	{
		return new StrutsStudentTranscriptBD(getWebApplicationContext(context));
	}	
}
