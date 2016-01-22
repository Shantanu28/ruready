package net.ruready.web.ta.support;

import net.ruready.business.content.catalog.entity.Course;
import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.exports.AbstractEditItemBD;
import net.ruready.business.ta.entity.StudentTranscript;
import net.ruready.business.ta.exports.AbstractAssessmentBD;
import net.ruready.business.ta.exports.AbstractStudentTranscriptBD;
import net.ruready.web.common.support.DefaultWebFlowSupport;
import net.ruready.web.content.item.imports.StrutsEditItemBD;
import net.ruready.web.ta.beans.TranscriptViewBean;
import net.ruready.web.ta.imports.StrutsAssessmentBD;
import net.ruready.web.ta.imports.StrutsStudentTranscriptBD;

import org.springframework.webflow.execution.RequestContext;
/**
 * Test Administration-specific support methods for Web Flow
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
* @version Nov 27, 2007
*/
public class WebFlowSupport extends DefaultWebFlowSupport
{

	private static final String TRANSCRIPT_BEAN_NAME = "viewBean";
	
	public final AbstractAssessmentBD getAssessmentBD(final RequestContext context)
	{
		return new StrutsAssessmentBD(getWebApplicationContext(context));
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
	 * Given the request context, returns a Student Transcript business delegate
	 * @param context the request context
	 * @return the Student Transcript business delegate
	 */
	public final AbstractStudentTranscriptBD getStudentTranscriptBD(final RequestContext context)
	{
		return new StrutsStudentTranscriptBD(getWebApplicationContext(context));
	}
	
	public final AbstractEditItemBD<Item> getEditItemBD(final RequestContext context)
	{
		return new StrutsEditItemBD<Item>(Item.class, getWebApplicationContext(context), getSystemUser());
	}
	
	public final StudentTranscript getTranscriptFromConversation(final RequestContext context) throws Exception
	{
		final TranscriptViewBean viewBean = (TranscriptViewBean) context.getConversationScope().get(TRANSCRIPT_BEAN_NAME, TranscriptViewBean.class);
		if (viewBean != null)
		{
			return viewBean.getTranscript();
		}
		return null;
	}
	
	public final void storeTranscriptInConversation(final RequestContext context, final StudentTranscript transcript) throws Exception
	{
		context.getConversationScope().put(TRANSCRIPT_BEAN_NAME, new TranscriptViewBean(transcript));
	}
}
